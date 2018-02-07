package com.xlf.app.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.LotteryTypeEnum;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.*;
import com.xlf.common.resp.RespBody;
import com.xlf.common.service.RedisService;
import com.xlf.common.util.DateTimeUtil;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.app.BettingInfoVo;
import com.xlf.common.vo.app.TimeBettingBaseVo;
import com.xlf.common.vo.app.TimeBettingVo;
import com.xlf.common.vo.app.UndoBettingVo;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.AppSysAgentSettingService;
import com.xlf.server.app.AppTimeBettingService;
import com.xlf.server.app.AppTimeIntervalService;
import com.xlf.server.app.AppTimeLotteryService;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.SysUserService;
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
@RequestMapping("/time")
public class TimeBettingController {
    public static List<Integer> list = new ArrayList<> ();
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
    private AppTimeBettingService appTimeBettingService;
    @Resource
    private AppTimeIntervalService appTimeIntervalService;
    @Resource
    private RedisService redisService;
    @Resource
    private AppTimeLotteryService appTimeLotteryService;


    @GetMapping("/timeInfo")
    @SystemControllerLog(description = "时时彩投注信息")
    public RespBody timeInfo(HttpServletRequest request) throws Exception {
        RespBody respBody = new RespBody ();
        try {
            Calendar calendar = Calendar.getInstance ();
            calendar.setTime (new Date ());
            Integer hour = calendar.get (Calendar.HOUR_OF_DAY);
            if (hour >= 2 && hour <= 10) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "非投注时间");
                return respBody;
            }
            String hhmm = DateTimeUtil.parseCurrentDateMinuteIntervalToStr (DateTimeUtil.PATTERN_HH_MM, 5);
            AppTimeIntervalPo intervalPo = appTimeIntervalService.findByTime (hhmm, LotteryTypeEnum.RACING.getCode ());
            if (intervalPo == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "非投注时间");
                return respBody;
            }
            String currentDate = DateTimeUtil.formatDate (new Date (), DateTimeUtil.PATTERN_YYYYMMDD);
            //本期期号
            String historyIssuNo = currentDate + "-" + Integer.valueOf (intervalPo.getIssueNo ());
            String nextIssuNo = currentDate + "-" + Integer.valueOf (intervalPo.getIssueNo () + 1);
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
            if (System.currentTimeMillis ()>end && System.currentTimeMillis ()<endDate.getTime ()){
                infoVo.setRestTime (0L);
            }else {
                infoVo.setRestTime (end-System.currentTimeMillis ());
            }
            //查询上期的开奖结果
            AppTimeLotteryPo timeLotteryPo= appTimeLotteryService.findAppTimeLotteryPoByIssuNo (currentDate+(intervalPo.getIssueNo () - 1));
            infoVo.setAppTimeLotteryPo (timeLotteryPo);
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取时时彩信息成功!", infoVo);
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "获取时时彩信息失败!");
            LogUtils.error ("获取时时彩信息失败！", ex);
        }
        return respBody;
    }


    @PostMapping("/timebetting")
    @SystemControllerLog(description = "时时彩投注")
    public RespBody timeBetting(HttpServletRequest request, @RequestBody TimeBettingVo vo) throws Exception {
        RespBody respBody = new RespBody ();
        try {
            //验签
            Boolean flag = commonService.checkSign (vo);
            if (!flag) {
                respBody.add (RespCodeEnum.ERROR.getCode (), languageUtil.getMsg (AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }
            if (vo.getSerialNumber () == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "下注参数有误");
                return respBody;
            }
            AppTimeIntervalPo timeIntervalPo = appTimeIntervalService.findByIssNo (vo.getSerialNumber (), 10);
            Long longDate = DateTimeUtil.getLongTimeByDatrStr (timeIntervalPo.getTime ());
            if (System.currentTimeMillis () > (longDate - 60 * 1000)) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "本期投注已截止");
                return respBody;
            }
            AppUserPo userPo = commonService.checkToken ();
            SysUserVo sysUserVo = sysUserService.findById (userPo.getParentId ());
            SysAgentSettingPo agentSettingPo = appSysAgentSettingService.findById (userPo.getParentId ());
            if (agentSettingPo == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "下注参数有误");
                return respBody;
            }
            if (CollectionUtils.isEmpty (vo.getTimeList ())) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "下注号码为空");
                return respBody;
            }
            if (vo.getTimeList ().size () > agentSettingPo.getMaxBetSeats ()) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "每期最多下注位数为" + agentSettingPo.getMaxBetSeats ());
                return respBody;
            }
            if (userPo.getCurrentProfit ().compareTo (agentSettingPo.getMaxProfitPerDay ()) > 0) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "已达到当日最大盈利额度，今日不可再下注");
                return respBody;
            }
            Integer totalBettingNo = 0;

            //期数前缀：固定前缀+用户id+当天期数
//            String keyprefix = RedisKeyUtil.getTimeBettingLocationPrefix (userPo.getId (), vo.getSerialNumber (), TimeSeatEnum.ONE);
            //key 每个位置投了哪些数字，set
            //key ，每个数字投了多少注

            Integer length = vo.getTimeList ().size ();
            Integer[][] bettArray = new Integer[length - 1][5];
            for (int j = 0; j < length; j++) {
                TimeBettingBaseVo base = vo.getTimeList ().get (j);
                bettArray[j][0] = base.getLotteryOne ();
                bettArray[j][1] = base.getLotteryTwo ();
                bettArray[j][2] = base.getLotteryThree ();
                bettArray[j][3] = base.getLotteryFour ();
                bettArray[j][4] = base.getLotteryFive ();
                bettArray[j][5] = base.getMultiple ();
                if (base.getMultiple () < agentSettingPo.getMinBetNoPerDigital () || base.getMultiple () > agentSettingPo.getMaxBetNoPerDigital ()) {
                    respBody.add (RespCodeEnum.ERROR.getCode (), "单个位数最小投注范围为【" + agentSettingPo.getMinBetNoPerDigital () + "," + agentSettingPo.getMaxBetNoPerDigital () + "】注");
                    return respBody;
                }
                totalBettingNo += base.getMultiple ();
            }
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < 5; k++) {
                    Set<Integer> horizontal = new HashSet<> ();
                    horizontal.add (bettArray[j][k]);
                    if (k == 4) {
                        if (horizontal.size () > 1) {
                            respBody.add (RespCodeEnum.ERROR.getCode (), "不符合投注规则,单注只能选择0-9中的一个数字");
                            return respBody;
                        }
                    }
                }
            }
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < length; k++) {
                    List<Integer> verticalList = new ArrayList<> ();
                    Set<Integer> verticalSet = new HashSet<> ();
                    if (bettArray[k][j] >= 0) {
                        verticalList.add (bettArray[k][j]);
                        verticalSet.add (bettArray[k][j]);
                    }
                    if (k == length - 1) {
                        if (verticalSet.size () > agentSettingPo.getMaxBetDigitalNoPerSeat ()) {
                            respBody.add (RespCodeEnum.ERROR.getCode (), "不符合投注规则,每个位最多压注" + agentSettingPo.getMaxBetDigitalNoPerSeat () + "个不同的数字");
                            return respBody;
                        }
                        if (verticalList.size () != verticalSet.size ()) {
                            respBody.add (RespCodeEnum.ERROR.getCode (), "不符合投注规则,单个位如果压注同一个数字,请合并投注,进行倍投");
                            return respBody;
                        }
                    }
                }
            }
            //最大可能中奖金额
            if (userPo.getBalance ().compareTo (new BigDecimal (totalBettingNo.toString ())) == -1) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "用户余额不足，无法完成下注");
                return respBody;
            }
            BigDecimal maximumAward = new BigDecimal (totalBettingNo).multiply (agentSettingPo.getOdds ());
            appTimeBettingService.timeBettingService (userPo.getId (), vo, new BigDecimal (totalBettingNo));
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "投注成功,等待开奖");
        } catch (CommException ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "投注失败");
            LogUtils.error ("投注失败！", ex);
        }
        return respBody;
    }


    @PostMapping("/undobetting")
    @SystemControllerLog(description = "时时彩撤单")
    public RespBody undoBetting(HttpServletRequest request, @RequestBody UndoBettingVo vo) throws Exception {
        RespBody respBody = new RespBody ();
        try {

            if (vo.getId () == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "撤单参数有误");
                return respBody;
            }
            AppTimeBettingPo bettingPo = appTimeBettingService.findById (vo.getId ());
            if (bettingPo == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "找不到下单记录");
                return respBody;
            }
            AppTimeIntervalPo timeIntervalPo = appTimeIntervalService.findByIssNo (bettingPo.getSerialNumber (), 10);
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
            appTimeBettingService.undoTimeBettingService (userPo.getId (), vo.getId ());
            respBody.add (RespCodeEnum.SUCCESS.getCode (), msgUtil.getMsg (AppMessage.WAIT_PAYING, "撤单成功"));
        } catch (CommException ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "撤单失败");
            LogUtils.error ("撤单失败！", ex);
        }
        return respBody;
    }
}
