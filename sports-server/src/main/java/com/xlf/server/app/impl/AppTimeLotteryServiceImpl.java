package com.xlf.server.app.impl;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.enums.TimeSeatEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.util.DateTimeUtil;
import com.xlf.common.util.HttpUtils;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.*;
import com.xlf.server.mapper.AppTimeLotteryMapper;
import com.xlf.server.web.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时时彩开奖务类
 */
@Service
public class AppTimeLotteryServiceImpl implements AppTimeLotteryService {
    private static final Logger log = LoggerFactory.getLogger(AppTimeLotteryServiceImpl.class);
    @Resource
    private AppTimeLotteryMapper appTimeLotteryMapper;


    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysAgentSettingService sysAgentSettingService;

    @Resource
    private AppTimeBettingService appTimeBettingService;
    @Resource
    private AppUserService appUserService;
    @Resource
    private AppBillRecordService appBillRecordService;

    @Override
    public AppTimeLotteryPo findLast() {
        return appTimeLotteryMapper.findLast();
    }

    @Override
    public AppTimeLotteryPo findById(String id) {
        return appTimeLotteryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateFlagById(String id) {
        return appTimeLotteryMapper.updateFlagById(id);
    }

    @Override
    public Boolean batchTimeLotteryHandleService(AppTimeLotteryPo lotteryPo, Boolean flag) throws Exception {
        List<AppTimeBettingPo> list;
        for (TimeSeatEnum seat : TimeSeatEnum.values()) {
            Integer winingCount = appTimeBettingService.wininggCount(lotteryPo.getIssueNo(), LotteryFlagEnum.NO.getCode(), lotteryPo.getLotteryOne(), seat);
            if (winingCount > 10) {
                flag = true;
            }
            if (winingCount > 0) {
                list = appTimeBettingService.listWininggByIssuNo(lotteryPo.getIssueNo(), LotteryFlagEnum.NO.getCode(), new Paging(0, 10), lotteryPo.getLotteryOne(), seat);
                if (list.size() > 0) {
                    for (AppTimeBettingPo bettingPo : list) {
                        timeLotteryHandleService(bettingPo);
                    }
                }
            } else {
                flag = false;
            }
            log.info("时时彩第" + lotteryPo.getIssueNo() + "期开奖:" + seat.getName() + " 没有待结算的投注订单");
        }
        if (flag) {
            this.batchTimeLotteryHandleService(lotteryPo, flag);
        }
        return flag;
    }


    @Override
    @Transactional
    public Boolean timeLotteryHandleService(AppTimeBettingPo bettingPo) throws Exception {
        log.error("------------------------------------------时时彩订单开始处理中奖流程------------订单号：" + bettingPo.getId() + "------------------------------------------------------------------------------------------------------------------");
        AppUserPo userPo = appUserService.findUserById(bettingPo.getUserId());
        SysUserVo sysUserVo = sysUserService.findById(userPo.getId());
        SysAgentSettingPo sysAgentSettingPo = sysAgentSettingService.findById(sysUserVo.getAgentLevelId());
        //投注数量
        BigDecimal mutiple = new BigDecimal(bettingPo.getMultiple());
        //计算奖金
        BigDecimal award = mutiple.multiply(sysAgentSettingPo.getOdds()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        //中奖后用户奖金
        BigDecimal after = userPo.getBalance().add(award).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        //分发奖金
        appUserService.updateBalanceById(userPo.getId(), award);
        //写入派奖流水
        appBillRecordService.saveBillRecord(bettingPo.getBusinessNumber(), userPo.getId(), BusnessTypeEnum.TIME_LOTTERY.getCode(), award, userPo.getBalance(), after, "时时彩奖金派发", "");
        //更新用户累计中奖金额
        appUserService.updateWiningAmoutById(userPo.getId(), award);
        //更新用户盈亏返水衡量值
        appUserService.updateKickBackAmountById(userPo.getId(), award.multiply(new BigDecimal(-1)));
        //写入盈亏返水衡量值流水(此处酌情写入)
        appBillRecordService.saveBillRecord(bettingPo.getBusinessNumber(), userPo.getId(), BusnessTypeEnum.REDUCE_KICKBACKAMOUNT_RECORD.getCode(), award, userPo.getBalance(), after, "下级" + userPo.getMobile() + "【" + userPo.getNickName() + "】" + "中奖返水减少", "");
        //更新用户当天累计盈亏
        appUserService.updateCurrentProfitById(userPo.getId(), award);
        //更改投注状态为已开奖
        appTimeBettingService.updateLotteryFlagById(bettingPo.getId(), award);
        log.error("-------------------------------------------时时彩订单结束处理中奖流程-------------订单号：" + bettingPo.getId() + "----------------------------------------------------------------------------------------------------------------");
        return true;
    }


    @Override
    public AppTimeLotteryPo findAppTimeLotteryPoByIssuNo(String issuNo) {
        return appTimeLotteryMapper.findAppTimeLotteryPoByIssuNo(issuNo);
    }

    @Override
    public Integer save(AppTimeLotteryPo po) {
        return appTimeLotteryMapper.insert(po);
    }

    @Override
    public List<AppTimeLotteryPo> lotteryListCurrentDay() {
        String TIME_URL = "http://kaijiang.500.com/static/public/ssc/xml/qihaoxml/";
        String currentDate = DateTimeUtil.formatDate(new Date(), DateTimeUtil.PATTERN_YYYYMMDD);
        TIME_URL += currentDate;
        TIME_URL += ".xml";
        System.out.println(TIME_URL);
        String resxml = HttpUtils.sendGet(TIME_URL, TIME_URL);
        resxml = resxml.replace("<?xml version=\"1.0\" encoding=\"gb2312\" ?><xml>", "").replace("</xml>", "");
        resxml = resxml.replaceFirst("<row ", "");
        String[] rowArr = resxml.split("<row ");
        List<AppTimeLotteryPo> listPo = new ArrayList<>();
        for (String row : rowArr) {
            row = row.replace("/>", "");
            System.out.println("row.replace:" + row);
            AppTimeLotteryPo rowAppTimeLotteryPo = getAppTimeLotteryPo(row);
            listPo.add(rowAppTimeLotteryPo);
        }
        return listPo;
    }

    private static AppTimeLotteryPo getAppTimeLotteryPo(String str) {
        AppTimeLotteryPo po = new AppTimeLotteryPo();
        List<String> list = new ArrayList<String>();
        List<AppTimeLotteryPo> timeLotteryPoList = new ArrayList<AppTimeLotteryPo>();
        Pattern pattern = Pattern.compile("\"(.*?)(?<![^\\\\]\\\\)\"");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            list.add(matcher.group().replace("\"",""));
        }
        po.setCreateTime(DateTimeUtil.parseDateFromStr(list.get(2), DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM));
        po.setIssueNo(list.get(0));
        po.setFlag(LotteryFlagEnum.NO.getCode());
        String lottery = list.get(1);
        String[] lotteryArr = lottery.split(",");
        if (lotteryArr.length < 5) {
            throw new CommException("获取时时彩：" + po.getIssueNo() + " 开奖信息失败");
        }
        po.setLotteryOne(Integer.valueOf(lotteryArr[4]));
        po.setLotteryTwo(Integer.valueOf(lotteryArr[3]));
        po.setLotteryThree(Integer.valueOf(lotteryArr[2]));
        po.setLotteryFour(Integer.valueOf(lotteryArr[1]));
        po.setLotteryFive(Integer.valueOf(lotteryArr[0]));
        return po;
    }
}
