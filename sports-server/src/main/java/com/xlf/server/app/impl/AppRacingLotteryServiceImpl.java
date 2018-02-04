package com.xlf.server.app.impl;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.enums.RacingSeatEnum;
import com.xlf.common.enums.TimeSeatEnum;
import com.xlf.common.po.*;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.common.vo.task.RacingLotteryVo;
import com.xlf.server.app.*;
import com.xlf.server.mapper.AppRacingLotteryMapper;
import com.xlf.server.mapper.AppTimeLotteryMapper;
import com.xlf.server.web.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * 赛车开奖业务类
 */
public class AppRacingLotteryServiceImpl implements AppRacingLotteryService {
    private static final Logger log = LoggerFactory.getLogger(AppRacingLotteryServiceImpl.class);
    @Resource
    private AppRacingLotteryMapper appRacingLotteryMapper;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysAgentSettingService sysAgentSettingService;

    @Resource
    private AppRacingBettingService appRacingBettingService;
    @Resource
    private AppRacingLotteryService appRacingLotteryService;
    @Resource
    private AppUserService appUserService;
    @Resource
    private AppBillRecordService appBillRecordService;

    @Override
    public AppRacingLotteryPo findLast() {
        return appRacingLotteryMapper.findLast();
    }

    @Override
    public AppRacingLotteryPo findById(String id) {
        return appRacingLotteryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateFlagById(String id) {
        return appRacingLotteryMapper.updateFlagById(id);
    }

    @Override
    public Boolean batchRacingLotteryHandleService(AppRacingLotteryPo lotteryPo, Boolean flag) throws Exception {
        List<AppRacingBettingPo> list;
        for (RacingSeatEnum seat : RacingSeatEnum.values()) {
            Integer winingCount = appRacingBettingService.wininggCount(lotteryPo.getIssueNo(), LotteryFlagEnum.NO.getCode(), lotteryPo.getLotteryOne(), seat);
            if (winingCount > 10) {
                flag = true;
            }
            if (winingCount > 0) {
                list = appRacingBettingService.listWininggByIssuNo(lotteryPo.getIssueNo(), LotteryFlagEnum.NO.getCode(), new Paging(0, 10), lotteryPo.getLotteryOne(), seat);
                if (list.size() > 0) {
                    for (AppRacingBettingPo bettingPo : list) {
                        racingLotteryHandleService(bettingPo);
                    }
                }
            } else {
                flag = false;
            }
            log.info("时时彩第" + lotteryPo.getIssueNo() + "期开奖:" + seat.getName() + " 没有待结算的投注订单");
        }
        if (flag) {
            this.batchRacingLotteryHandleService(lotteryPo, flag);
        }
        return flag;
    }

    @Override
    public Boolean racingLotteryHandleService(AppRacingBettingPo bettingPo) throws Exception {
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

        //盈亏衡量
        //中奖后用户奖金
        BigDecimal afterKick = userPo.getBalance().subtract (award).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        //分发奖金
        appUserService.updateBalanceById(userPo.getId(), award);
        //写入派奖流水
        appBillRecordService.saveBillRecord(bettingPo.getBusinessNumber(), userPo.getId(), BusnessTypeEnum.TIME_LOTTERY.getCode(), award, userPo.getBalance(), after, "时时彩奖金派发", "");
        //更新用户累计中奖金额
        appUserService.updateWiningAmoutById(userPo.getId(), award);
        //更新用户盈亏返水衡量值
        appUserService.updateKickBackAmountById(userPo.getId(), award.multiply(new BigDecimal(-1)));
        //写入盈亏返水衡量值流水(此处酌情写入)
        appBillRecordService.saveBillRecord(bettingPo.getBusinessNumber(), userPo.getId(), BusnessTypeEnum.REDUCE_KICKBACKAMOUNT_RECORD.getCode(), award.multiply (new BigDecimal ("-1")), userPo.getKickBackAmount (), afterKick, "下级" + userPo.getMobile() + "【" + userPo.getNickName() + "】" + "中奖返水减少", "");
        //更新用户当天累计盈亏
        appUserService.updateCurrentProfitById(userPo.getId(), award);
        //更改投注状态为已开奖
        appRacingBettingService.updateLotteryFlagById(bettingPo.getId(), award);
        log.error("-------------------------------------------时时彩订单结束处理中奖流程-------------订单号：" + bettingPo.getId() + "----------------------------------------------------------------------------------------------------------------");
        return true;
    }

    @Override
    public RacingLotteryVo getLatestRacingLottery() {
        return appRacingLotteryService.getLatestRacingLottery();
    }

    @Override
    public Integer save(AppRacingLotteryPo po) {
        return appRacingLotteryMapper.insert(po);
    }

    @Override
    public AppTimeLotteryPo findAppRacingLotteryPoByIssuNo(String issuNo) {
        return appRacingLotteryMapper.findAppRacingLotteryPoByIssuNo(issuNo);
    }


}
