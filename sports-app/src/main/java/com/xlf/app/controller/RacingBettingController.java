package com.xlf.app.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.enums.LotteryTypeEnum;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.*;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.DateTimeUtil;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.app.BettingInfoVo;
import com.xlf.common.vo.app.RacingBettingBaseVo;
import com.xlf.common.vo.app.RacingBettingVo;
import com.xlf.common.vo.app.UndoBettingVo;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.*;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 用户资产相关
 */
@RestController
@RequestMapping("/racing")
public class RacingBettingController {
    private static Logger logger = LoggerFactory.getLogger (RacingBettingController.class);
    @Resource
    private CommonService commonService;
    @Resource
    private LanguageUtil msgUtil;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private LanguageUtil languageUtil;
    @Resource
    private AppSysAgentSettingService appSysAgentSettingService;
    @Resource
    private AppRacingBettingService appRacingBettingService;
    @Resource
    private AppTimeIntervalService appTimeIntervalService;
    @Resource
    private AppRacingLotteryService appRacingLotteryService;
    @Resource
    private KeyService keyService;


    @GetMapping("/racingInfo")
    @SystemControllerLog(description = "赛车投注信息")
    public RespBody racingInfo(HttpServletRequest request) throws Exception {
        RespBody respBody = new RespBody ();
        try {
            Calendar calendar = Calendar.getInstance ();
            calendar.setTime (new Date ());
            Integer hour = calendar.get (Calendar.HOUR_OF_DAY);
            String hhmm = DateTimeUtil.parseCurrentDateMinuteIntervalToStr (DateTimeUtil.PATTERN_HH_MM, 10);
            AppTimeIntervalPo intervalPo = appTimeIntervalService.findByTime (hhmm, LotteryTypeEnum.RACING.getCode ());
            if (intervalPo == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "非投注时间");
                return respBody;
            }
            if (hour >= 2 && hour <= 10) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "非投注时间");
                return respBody;
            }
            String yesterdayRacingIssuNo = commonService.findParameter ("yesterdayRacingIssuNo");
            if (StringUtils.isEmpty (yesterdayRacingIssuNo)) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "获取昨日北京赛车最后期数失败，须检查参数配置");
                return respBody;
            }
            String historyPreIssuNo = (Integer.valueOf (yesterdayRacingIssuNo) + Integer.valueOf (intervalPo.getIssueNo ()) - 1) + "";
            //本期期号
            String historyIssuNo = (Integer.valueOf (yesterdayRacingIssuNo) + Integer.valueOf (intervalPo.getIssueNo ())) + "";
            String nextIssuNo = (Integer.valueOf (yesterdayRacingIssuNo) + Integer.valueOf (intervalPo.getIssueNo ()) + 1) + "";
            //本期投注截止时间
            String endDateStr = DateTimeUtil.formatDate (new Date (), DateTimeUtil.PATTERN_YYYY_MM_DD) + " " + hhmm;
            Date endDate = DateTimeUtil.parseDateFromStr (endDateStr, DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM);
            Long end = endDate.getTime () - 30 * 1000;
            Long start = endDate.getTime () - 10 * 60 * 1000 + 30 * 1000;
            Long open = endDate.getTime () + 3 * 60 * 1000;
            Date bettingEnd = new Date (endDate.getTime () - 30 * 1000);
            Date bettingStart = new Date (endDate.getTime () - 10 * 60 * 1000 + 30 * 1000);
            Date bettingOpen = new Date (endDate.getTime () + 3 * 60 * 1000);
            BettingInfoVo infoVo = new BettingInfoVo ();
            infoVo.setHhmm (hhmm);
            infoVo.setHistoryIssuNo (historyIssuNo);
            infoVo.setCurrentIssueNo (intervalPo.getIssueNo ().toString ());
            infoVo.setNextIssuNo (nextIssuNo);
            infoVo.setEndDateStr (endDateStr);
            infoVo.setEndDate (endDate);
            infoVo.setEnd (end);
            infoVo.setStart (start);
            infoVo.setOpen (open);
            infoVo.setBettingStart (DateTimeUtil.formatDate (bettingStart, DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
            infoVo.setBettingEnd (DateTimeUtil.formatDate (bettingEnd, DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
            infoVo.setBettingOpen (DateTimeUtil.formatDate (bettingOpen, DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
            if (System.currentTimeMillis () > end && System.currentTimeMillis () < endDate.getTime ()) {
                infoVo.setRestTime (0L);
            } else {
                infoVo.setRestTime (end - System.currentTimeMillis ());
            }
            //查询上期的开奖结果
            AppRacingLotteryPo appRacingLotteryPo = appRacingLotteryService.findAppRacingLotteryPoByIssuNo (historyPreIssuNo);
            if(appRacingLotteryPo == null){
                appRacingLotteryPo = new AppRacingLotteryPo();
                appRacingLotteryPo.setId("1234567890");
                appRacingLotteryPo.setIssueNo("20180207110");
                appRacingLotteryPo.setFlag(LotteryFlagEnum.NO.getCode());
                appRacingLotteryPo.setLotteryOne(3);
                appRacingLotteryPo.setLotteryTwo(2);
                appRacingLotteryPo.setLotteryThree(5);
                appRacingLotteryPo.setLotteryFour(3);
                appRacingLotteryPo.setLotteryFive(8);
                appRacingLotteryPo.setLotterySix(9);
                appRacingLotteryPo.setLotterySeven(7);
                appRacingLotteryPo.setLotteryEight(4);
                appRacingLotteryPo.setLotteryNine(1);
                appRacingLotteryPo.setLotteryTen(6);
                appRacingLotteryPo.setLotteryTime(new Date());
            }
            infoVo.setAppRacingLotteryPo(appRacingLotteryPo);
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取北京赛车信息成功", infoVo);
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "获取北京赛车信息失败");
            LogUtils.error ("获取北京赛车信息失败！", ex);
        }
        return respBody;
    }

    @PostMapping("/racingbetting")
    @SystemControllerLog(description = "北京赛车投注")
    public RespBody racingBetting(HttpServletRequest request, @RequestBody RacingBettingVo vo) throws Exception {
        RespBody respBody = new RespBody ();
        try {
            //验签
/*            Boolean flag = commonService.checkSign (vo);
            if (!flag) {
                respBody.add (RespCodeEnum.ERROR.getCode (), languageUtil.getMsg (AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }*/
            if (vo.getSerialNumber () == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "下注参数有误");
                return respBody;
            }
            AppTimeIntervalPo timeIntervalPo = appTimeIntervalService.findByIssNo (vo.getSerialNumber (), 20);
            Integer yesterdayRacingIssuNo = Integer.valueOf (commonService.findParameter ("yesterdayRacingIssuNo"));
            String historyIssuNo = (yesterdayRacingIssuNo + vo.getSerialNumber ()) + "";
            if (!historyIssuNo.equals (vo.getIssueNo ())) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "下注参数有误");
                return respBody;
            }
            Long longDate = DateTimeUtil.getLongTimeByDatrStr (timeIntervalPo.getTime());
            if (System.currentTimeMillis () > (longDate - 60 * 1000)) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "本期投注已截止");
                return respBody;
            }
            AppUserPo userPo = commonService.checkToken ();
            SysUserVo sysUserVo = sysUserService.findById (userPo.getParentId ());
            SysAgentSettingPo agentSettingPo = appSysAgentSettingService.findById (sysUserVo.getAgentLevelId ());
            if (agentSettingPo == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "下注参数有误");
                return respBody;
            }
            if (CollectionUtils.isEmpty (vo.getRaingList ())) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "下注号码为空");
                return respBody;
            }
            if (vo.getRaingList ().size () > agentSettingPo.getMaxBetSeats ()) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "每期最多下注位数为" + agentSettingPo.getMaxBetSeats ());
                return respBody;
            }
            if (userPo.getCurrentProfit ().compareTo (agentSettingPo.getMaxProfitPerDay ()) > 0) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "已达到当日最大盈利额度，今日不可再下注");
                return respBody;
            }
            Integer totalBettingNo = 0;
            Integer length = vo.getRaingList ().size ();
            Integer[][] bettArray = new Integer[length][11];
            for (int j = 0; j < length; j++) {
                RacingBettingBaseVo base = vo.getRaingList ().get (j);
                bettArray[j][0] = base.getLotteryOne ();
                bettArray[j][1] = base.getLotteryTwo ();
                bettArray[j][2] = base.getLotteryThree ();
                bettArray[j][3] = base.getLotteryFour ();
                bettArray[j][4] = base.getLotteryFive ();
                bettArray[j][5] = base.getLotterySix ();
                bettArray[j][6] = base.getLotterySeven ();
                bettArray[j][7] = base.getLotteryEight ();
                bettArray[j][8] = base.getLotteryNine ();
                bettArray[j][9] = base.getLotteryTen ();
                bettArray[j][10] = base.getMultiple ();
                if (base.getMultiple () < agentSettingPo.getMinBetNoPerDigital () || base.getMultiple () > agentSettingPo.getMaxBetNoPerDigital ()) {
                    respBody.add (RespCodeEnum.ERROR.getCode (), "单个位数最小投注范围为【" + agentSettingPo.getMinBetNoPerDigital () + "," + agentSettingPo.getMaxBetNoPerDigital () + "】注");
                    return respBody;
                }
                totalBettingNo += base.getMultiple ();
            }
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < 10; k++) {
                    Set<Integer> horizontal = new HashSet<> ();
                    horizontal.add (bettArray[j][k]);
                    if (k == 9) {
                        if (horizontal.size () > 1) {
                            respBody.add (RespCodeEnum.ERROR.getCode (), "不符合投注规则,单注只能选择1-10中的一个数字");
                            return respBody;
                        }
                    }
                }
            }
            /*for (int j = 0; j < 10; j++) {
                for (int k = 0; k < length; k++) {
                    List<Integer> verticalList = new ArrayList<> ();
                    Set<Integer> verticalSet = new HashSet<> ();
                    if (bettArray[k][j] > 0) {
                        verticalList.add (bettArray[k][j]);
                        verticalSet.add (bettArray[k][j]);
                    }
                    if (k == length - 1) {
                        if (verticalSet.size () > agentSettingPo.getMaxBetDigitalNoPerSeat ()) {
                            respBody.add (RespCodeEnum.ERROR.getCode (), "不符合投注规则,每个赛道最多压注" + agentSettingPo.getMaxBetDigitalNoPerSeat () + "个不同的数字");
                            return respBody;
                        }
                        if (verticalList.size () != verticalSet.size ()) {
                            respBody.add (RespCodeEnum.ERROR.getCode (), "不符合投注规则,单个赛道如果压注同一个数字,请合并投注,进行倍投");
                            return respBody;
                        }
                    }
                    //记录历史的每个位投注的数字集合
                    Set<String> set = keyService.getRacingSetMembers (userPo.getId (), vo.getSerialNumber (), j);
                    if (set.size () > agentSettingPo.getMaxBetDigitalNoPerSeat ()) {
                        respBody.add (RespCodeEnum.ERROR.getCode (), "不符合投注规则,每个位最多压注" + agentSettingPo.getMaxBetDigitalNoPerSeat () + "个不同的数字");
                        return respBody;
                    }
                    keyService.saddRacingSetMember (userPo.getId (), vo.getSerialNumber (), k, bettArray[k][j]);
                    //记录每个数字投了多少注
                    Long count = keyService.racingBettingHget (userPo.getId (), vo.getSerialNumber (), bettArray[k][j]);
                    Long currentCount = count + Long.valueOf (bettArray[k][5]);
                    if (currentCount < agentSettingPo.getMinBetNoPerDigital () || currentCount > agentSettingPo.getMaxBetNoPerDigital ()) {
                        respBody.add (RespCodeEnum.ERROR.getCode (), "单个位数最小投注范围为【" + agentSettingPo.getMinBetNoPerDigital () + "," + agentSettingPo.getMaxBetNoPerDigital () + "】注");
                        return respBody;
                    }
                    keyService.racingBettingHset (userPo.getId (), vo.getSerialNumber (), bettArray[k][j], currentCount);
                }
            }*/
            //最大可能中奖金额
            if (userPo.getBalance ().compareTo (new BigDecimal (totalBettingNo.toString ())) == -1) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "用户余额不足，无法完成下注");
                return respBody;
            }
            BigDecimal maximumAward = new BigDecimal (totalBettingNo).multiply (agentSettingPo.getOdds ());
            appRacingBettingService.racingBettingService (userPo.getId (), vo, new BigDecimal (totalBettingNo));
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "投注成功,等待开奖");
        } catch (
                CommException ex)

        {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (
                Exception ex)

        {
            respBody.add (RespCodeEnum.ERROR.getCode (), "投注失败");
            LogUtils.error ("投注失败！", ex);
        }
        return respBody;
    }

    @PostMapping("/undobetting")
    @SystemControllerLog(description = "北京赛车撤单")
    public RespBody undoBetting(HttpServletRequest request, @RequestBody UndoBettingVo vo) throws Exception {
        RespBody respBody = new RespBody ();
        try {

            if (vo.getId () == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "撤单参数有误");
                return respBody;
            }
            AppRacingBettingPo bettingPo = appRacingBettingService.findById (vo.getId ());
            if (bettingPo == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "找不到下单记录");
                return respBody;
            }

            AppTimeIntervalPo timeIntervalPo = appTimeIntervalService.findByIssNo (bettingPo.getSerialNumber (), 20);
            if (timeIntervalPo == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "撤单参数有误");
                return respBody;
            }
            Long openDate = DateTimeUtil.getLongTimeByDatrStr (DateTimeUtil.formatDate (new Date (), DateTimeUtil.PATTERN_YYYY_MM_DD) + timeIntervalPo.getTime ());
            if ((System.currentTimeMillis () + 60 * 1000) >= openDate) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "开奖不足一分钟,无法撤单");
                return respBody;
            }
            AppUserPo userPo = commonService.checkToken ();
            appRacingBettingService.undoRacingBettingService (userPo.getId (), vo.getId ());
            respBody.add (RespCodeEnum.SUCCESS.getCode (), msgUtil.getMsg (AppMessage.WAIT_PAYING, "撤单成功"));
        } catch (CommException ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "撤单失败");
            LogUtils.error ("撤单失败！", ex);
        }
        return respBody;
    }

    private void getLongTimeByDatrStr(String time) {
        String dateStr = DateTimeUtil.formatDate (new Date (), DateTimeUtil.PATTERN_YYYYMMDD) + time;
        Date date = DateTimeUtil.parseDateFromStr (dateStr, DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM);
        Long dateLong = date.getTime ();
    }


}
