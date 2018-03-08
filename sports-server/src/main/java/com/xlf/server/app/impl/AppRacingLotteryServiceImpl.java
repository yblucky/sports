package com.xlf.server.app.impl;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.enums.RacingSeatEnum;
import com.xlf.common.enums.TimeSeatEnum;
import com.xlf.common.po.*;
import com.xlf.common.resp.Paging;
import com.xlf.common.util.DateTimeUtil;
import com.xlf.common.util.HttpUtils;
import com.xlf.common.util.LogUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.common.vo.task.RacingLotteryVo;
import com.xlf.server.app.*;
import com.xlf.server.common.CommonService;
import com.xlf.server.mapper.AppRacingLotteryMapper;
import com.xlf.server.mapper.AppTimeLotteryMapper;
import com.xlf.server.web.SysUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * 赛车开奖业务类
 */
@Service
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
    private CommonService commonService;
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
            log.info("PK10第" + lotteryPo.getIssueNo() + "期开奖:" + seat.getName() + " 没有待结算的投注订单");
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
            String RACING_URL = "https://www.cp9833.com/getLotteryBase.do";
            String json = HttpUtils.sendGet(RACING_URL, "gameCode=bjpk10");
            RacingLotteryVo vo = ToolUtils.toObject(json, RacingLotteryVo.class);
            return vo;
    }

    @Override
    public Integer save(AppRacingLotteryPo po) {
        return appRacingLotteryMapper.insert(po);
    }

    @Override
    public AppRacingLotteryPo findAppRacingLotteryPoByIssuNo(String issuNo) {
        return appRacingLotteryMapper.findAppRacingLotteryPoByIssuNo(issuNo);
    }


    public static void main(String[] args) {
        String RACING_URL = "https://www.cp9833.com/getLotteryBase.do";
//        String json = HttpUtils.sendGet(RACING_URL, "gameCode=bjpk10");
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = RACING_URL + "?" + "gameCode=bjpk10";
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println (key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader (new InputStreamReader (connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println ("发送GET请求出现异常！" + e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                System.out.println ("关闭输入流出现异常！" + ex);
            }
        }
        RacingLotteryVo vo = ToolUtils.toObject(result, RacingLotteryVo.class);
        result=result.replaceAll ("null","\"\"");
        System.out.println ("00000000000000");
        System.out.println (result);
        System.out.println ("\n"+"33333333333333333333333333333");
        System.out.println (DateTimeUtil.parseCurrentDateMinuteIntervalToStr (DateTimeUtil.PATTERN_HH_MM, 5));
//        RacingLotteryVo vo = ToolUtils.toObject(result, RacingLotteryVo.class);
//        System.out.println (ToolUtils.toJson (vo));
    }

    @Override
    public List<AppRacingLotteryPo> lotteryListCurrentDayByPayUrl() {
        String PK10_URL = commonService.findParameter ("pk10LotteryUrl");
        if (StringUtils.isEmpty (PK10_URL)) {
            log.error ("获取PK10第三方付费接口url配置错误");
            return null;
        }
        String json = HttpUtils.sendGet (PK10_URL, "");
        if (StringUtils.isEmpty (json)){
            log.error ("获取PK10第三方付费接口未返回开奖结果");
            return null;
        }
        JSONObject jsonResult=JSONObject.fromObject (json);
        if (!"bjpk10".equals (jsonResult.get ("code"))){
            log.error ("获取PK10第三方付费接口获取彩种错误");
            return null;
        }
        List<AppRacingLotteryPo> listPo = new ArrayList<> ();
        JSONArray jsonArray=jsonResult.getJSONArray ("data");
        for (int i=0;i<jsonArray.size ();i++){
            AppRacingLotteryPo model=new AppRacingLotteryPo();
            JSONObject rowJson= jsonArray.getJSONObject (i);
            model.setLotteryTime (new Date (rowJson.getLong ("opentimestamp")));
            model.setIssueNo (rowJson.getString ("expect"));
            String opencode=rowJson.getString ("opencode");
            String[] array=opencode.split (",");
            model.setLotteryOne (Integer.valueOf (array[0]));
            model.setLotteryTwo (Integer.valueOf (array[1]));
            model.setLotteryThree (Integer.valueOf (array[2]));
            model.setLotteryFour (Integer.valueOf (array[3]));
            model.setLotteryFive (Integer.valueOf (array[4]));
            model.setLotterySix (Integer.valueOf (array[5]));
            model.setLotterySeven (Integer.valueOf (array[6]));
            model.setLotteryEight (Integer.valueOf (array[7]));
            model.setLotteryNine (Integer.valueOf (array[8]));
            model.setLotteryTen (Integer.valueOf (array[9]));
            listPo.add (model);
        }
        return listPo;
    }
}
