package com.xlf.app.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.contrants.Constrants;
import com.xlf.common.enums.*;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.*;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.service.RedisService;
import com.xlf.common.util.DateTimeUtil;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.LogUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private AppSysAgentSettingService appSysAgentSettingService;
    @Resource
    private AppRacingBettingService appRacingBettingService;
    @Resource
    private AppTimeIntervalService appTimeIntervalService;
    @Resource
    private AppRacingLotteryService appRacingLotteryService;
    @Resource
    private RedisService redisService;
    @Resource
    private AppTimeBettingService appTimeBettingService;


    @GetMapping("/racingInfo")
    @SystemControllerLog(description = "赛车投注信息")
    public RespBody racingInfo(HttpServletRequest request) throws Exception {
        RespBody respBody = new RespBody ();
        try {
            Calendar calendar = Calendar.getInstance ();
            calendar.setTime (new Date ());
            Integer hour = calendar.get (Calendar.HOUR_OF_DAY);
            Integer inteval = 10;
            String hhmm = DateTimeUtil.parseCurrentDateMinuteIntervalToStr (DateTimeUtil.PATTERN_HH_MM, inteval);
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

            String endBefore = commonService.findParameter("endBefore");
            String openStart = commonService.findParameter("openStart");
            String lotteryOpen = commonService.findParameter("lotteryOpen");
            if (StringUtils.isEmpty(endBefore) || StringUtils.isEmpty(openStart) || StringUtils.isEmpty(lotteryOpen)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "时时彩系统参数错误");
                return respBody;
            }
            Integer endBeforeInt = Integer.valueOf(endBefore);
            Integer openStartInt = Integer.valueOf(openStart);
            Integer lotteryOpenInt = Integer.valueOf(lotteryOpen);

            String historyPreIssuNo = (Integer.valueOf (yesterdayRacingIssuNo) + Integer.valueOf (intervalPo.getIssueNo ()) - 1) + "";
            //本期期号
            String historyIssuNo = (Integer.valueOf (yesterdayRacingIssuNo) + Integer.valueOf (intervalPo.getIssueNo ())) + "";
            String nextIssuNo = (Integer.valueOf (yesterdayRacingIssuNo) + Integer.valueOf (intervalPo.getIssueNo ()) + 1) + "";
            //本期投注截止时间
            String endDateStr = DateTimeUtil.formatDate (new Date (), DateTimeUtil.PATTERN_YYYY_MM_DD) + " " + hhmm;
            Date endDate = DateTimeUtil.parseDateFromStr (endDateStr, DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM);
            Long end = endDate.getTime () - endBeforeInt * 1000;
            Long start = endDate.getTime () - inteval * 60 * 1000 + openStartInt * 1000;
            Long open = endDate.getTime () + lotteryOpenInt * 60 * 1000;
            Date bettingEnd = new Date (end);
            Date bettingStart = new Date (start);
            Date bettingOpen = new Date (open);
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
            AppRacingLotteryPo appRacingLotteryPo = appRacingLotteryService.loadAwardNumber();
            if(appRacingLotteryPo == null){
                appRacingLotteryPo = new AppRacingLotteryPo();
                appRacingLotteryPo.setId("");
                appRacingLotteryPo.setIssueNo("");
                appRacingLotteryPo.setFlag(LotteryFlagEnum.NO.getCode());
                appRacingLotteryPo.setLotteryOne(null);
                appRacingLotteryPo.setLotteryTwo(null);
                appRacingLotteryPo.setLotteryThree(null);
                appRacingLotteryPo.setLotteryFour(null);
                appRacingLotteryPo.setLotteryFive(null);
                appRacingLotteryPo.setLotterySix(null);
                appRacingLotteryPo.setLotterySeven(null);
                appRacingLotteryPo.setLotteryEight(null);
                appRacingLotteryPo.setLotteryNine(null);
                appRacingLotteryPo.setLotteryTen(null);
                appRacingLotteryPo.setLotteryTime(new Date());
            }
            infoVo.setAppRacingLotteryPo(appRacingLotteryPo);
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取北京赛车信息成功", infoVo);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "获取北京赛车信息失败");
            LogUtils.error("获取北京赛车信息失败！", ex);
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




    /**
     * 一字定投注
     *
     * @param request
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping("/oneRaceBetting")
    @SystemControllerLog(description = "PK10投注")
    public RespBody oneRaceBetting(HttpServletRequest request, @RequestBody RacingBettingVo vo, Paging paging) throws Exception {
        RespBody respBody = new RespBody();
        try {
            //验签
            /*Boolean flag = commonService.checkSign (vo);
            if (!flag) {
                respBody.add (RespCodeEnum.ERROR.getCode (), languageUtil.getMsg (AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }*/
            if (vo.getSerialNumber() == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "下注参数有误");
                return respBody;
            }

            String endBefore = commonService.findParameter("endBefore");
            if (StringUtils.isEmpty(endBefore)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "PK10投注系统投注参数有误");
                return respBody;
            }
            Integer endBeforeInt = Integer.valueOf(endBefore);
            AppTimeIntervalPo timeIntervalPo = appTimeIntervalService.findByIssNo(vo.getSerialNumber(), 20);
            Long longDate = DateTimeUtil.getLongTimeByDatrStr(timeIntervalPo.getTime());
            if (System.currentTimeMillis() > (longDate - endBeforeInt * 1000)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "本期投注已截止");
                return respBody;
            }
            if (CollectionUtils.isEmpty(vo.getRaingList())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "下注号码为空");
                return respBody;
            }
            AppUserPo userPo = commonService.checkToken();
            SysUserVo sysUserVo = sysUserService.findById(userPo.getParentId());
            SysAgentSettingPo agentSettingPo = appSysAgentSettingService.findById(sysUserVo.getAgentLevelId());
            if (agentSettingPo == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "下注参数有误");
                return respBody;
            }
            if ((userPo.getTodayWiningAmout().subtract(userPo.getTodayBettingAmout())).compareTo(agentSettingPo.getMaxProfitPerDay()) > 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "已达到当日最大盈利额度，今日不可再下注");
                return respBody;
            }
            List<BettingBaseVo> allList = new ArrayList<>();
            Integer totalBettingNo = 0;
            Integer thisTotalBettingNo = 0;
            Integer hasBettingCount = appRacingBettingService.countBettingByUserIdAndIssueNoAndContent(userPo.getId(), vo.getIssueNo(), null, BetTypeEnum.RACE_ONE.getCode());
            for (RacingBettingBaseVo baseVo : vo.getRaingList()) {
                if (baseVo.getMultiple() < agentSettingPo.getMinBetNoPerDigitalRace() || baseVo.getMultiple() > agentSettingPo.getMaxBetNoPerDigitalRace()) {
                    respBody.add(RespCodeEnum.ERROR.getCode(), "单赛道单个数字最小投注范围为【" + agentSettingPo.getMinBetNoPerDigitalRace() + "," + agentSettingPo.getMaxBetNoPerDigitalRace() + "】注" + baseVo.getBettingContent() + "超限制");
                    return respBody;
                }
                if (baseVo.getBettingContent().replaceAll("\\d", "").length() != 9) {
                    respBody.add(RespCodeEnum.ERROR.getCode(), "非一字定投注");
                    return respBody;
                }
                if (hasBettingCount > 0) {
                    Integer count = appRacingBettingService.countBettingByUserIdAndIssueNoAndContent(userPo.getId(), vo.getIssueNo(), baseVo.getBettingContent(), BetTypeEnum.RACE_ONE.getCode());
                    if (count > 0) {
                        paging.setPageSize(30);
                        paging.setPageNumber(1);
                        List<AppRacingBettingPo> timeBettingPos = appRacingBettingService.findListByUserIdAndIssueNoAndContent(userPo.getId(), vo.getIssueNo(), baseVo.getBettingContent(), BetTypeEnum.RACE_ONE.getCode(), paging);
                        Integer total = 0;
                        for (AppRacingBettingPo po : timeBettingPos) {
                            total += baseVo.getMultiple();
                            total += po.getMultiple();
                            if (total < agentSettingPo.getMinBetNoPerDigitalRace() || total > agentSettingPo.getMaxBetNoPerDigitalRace()) {
                                respBody.add(RespCodeEnum.ERROR.getCode(), "单赛道单个数字最小投注范围为【" + agentSettingPo.getMinBetNoPerDigitalRace() + "," + agentSettingPo.getMaxBetNoPerDigitalRace() + "】注," + baseVo.getBettingContent() + "超限制");
                                return respBody;
                            }
                            BettingBaseVo bettingBaseVo = new BettingBaseVo();
                            bettingBaseVo.setMultiple(po.getMultiple());
                            bettingBaseVo.setBettingContent(po.getBettingContent());
                            allList.add(bettingBaseVo);
                        }
                    } else {
                        List<AppRacingBettingPo> timeBettingPos = appRacingBettingService.findListByUserIdAndIssueNoAndContent(userPo.getId(), vo.getIssueNo(), null, BetTypeEnum.RACE_ONE.getCode(), paging);
                        for (AppRacingBettingPo po : timeBettingPos) {
                            BettingBaseVo bettingBaseVo = new BettingBaseVo();
                            bettingBaseVo.setMultiple(po.getMultiple());
                            bettingBaseVo.setBettingContent(po.getBettingContent());
                            allList.add(bettingBaseVo);
                        }
                    }
                }
                BettingBaseVo bettingBaseVo = new BettingBaseVo();
                bettingBaseVo.setMultiple(baseVo.getMultiple());
                bettingBaseVo.setBettingContent(baseVo.getBettingContent());
                allList.add(bettingBaseVo);
                totalBettingNo += baseVo.getMultiple();
                thisTotalBettingNo += baseVo.getMultiple();
            }
            Map<Integer, Set<String>> map = new HashMap<>();
            Map<String, Set<String>> trackMap = new HashMap<>();


            for (BettingBaseVo bettingBaseVo : allList) {
                for (String regex:Constrants.racingRegexList){
                    if (ToolUtils.regex(bettingBaseVo.getBettingContent(),regex)) {
                        if (!trackMap.containsKey(regex)) {
                            trackMap.put(regex, new HashSet<String>());
                        }
                        trackMap.get(regex).add(bettingBaseVo.getBettingContent());
                        if (trackMap.get(regex).size() > agentSettingPo.getMaxBetNoPerRrack()) {
                            respBody.add(RespCodeEnum.ERROR.getCode(), "不符合投注规则,每个赛道最多压注" + agentSettingPo.getMaxBetNoPerRrack() + "个不同的数字");
                            return respBody;
                        }
                        if (trackMap.size() > agentSettingPo.getMaxBetRracks()) {
                            respBody.add(RespCodeEnum.ERROR.getCode(), "每期最多下注赛道为" + agentSettingPo.getMaxBetRracks());
                            return respBody;
                        }
                    }
                }
            }

            //最大可能中奖金额
            if (userPo.getBalance().compareTo(new BigDecimal(thisTotalBettingNo.toString())) == -1) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "用户余额不足，无法完成下注");
                return respBody;
            }

            BigDecimal timeOneWinRate = new BigDecimal(commonService.findParameter("timeOneWinRate"));
            BigDecimal timeDoubleWinRate = new BigDecimal(commonService.findParameter("timeDoubleWinRate"));
            BigDecimal pk10OneWinRate = new BigDecimal(commonService.findParameter("pk10OneWinRate"));
            BigDecimal currentProfitSum = userPo.getTodayWiningAmout().add(new BigDecimal(totalBettingNo).multiply(agentSettingPo.getRacingOdds()));
            BigDecimal timeSumOneUnOpen= appTimeBettingService.sumUnLotteryByUserId(userPo.getId(),BetTypeEnum.TIME_ONE.getCode());
            BigDecimal timeSumTwoUnOpen= appTimeBettingService.sumUnLotteryByUserId(userPo.getId(),BetTypeEnum.TIME_TWO.getCode());
            BigDecimal pk10SumUnOpen= appRacingBettingService.sumUnLotteryByUserId(userPo.getId());

            currentProfitSum=currentProfitSum.add(timeSumOneUnOpen.multiply(timeOneWinRate).multiply(agentSettingPo.getOdds()));
            currentProfitSum=currentProfitSum.add(timeSumTwoUnOpen.multiply(timeDoubleWinRate).multiply(agentSettingPo.getTimeDoubleOdds()));
            currentProfitSum=currentProfitSum.add(pk10SumUnOpen.multiply(pk10OneWinRate));


            if (currentProfitSum.compareTo(agentSettingPo.getMaxProfitPerDay()) == 1) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "盈利额度超限,无法完成下注");
                return respBody;
            }
            BigDecimal maximumAward = new BigDecimal(totalBettingNo).multiply(agentSettingPo.getOdds());
            appRacingBettingService.racingBettingService(userPo.getId(), vo, new BigDecimal(thisTotalBettingNo));
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "投注成功,等待开奖");
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "投注失败");
            LogUtils.error("投注失败！", ex);
        }
        return respBody;
    }


    /**
     * 开奖号码
     */
    @GetMapping(value = "/loadAwardNumber")
    public RespBody loadAwardNumber(HttpServletRequest request) {
        RespBody respBody = new RespBody();
        try {
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken();
            //根据用户id获取用户信息
            AppRacingLotteryPo po = appRacingLotteryService.loadAwardNumber();

            respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取上期开奖号码成功", po);
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "获取上期开奖号码成功");
            LogUtils.error("获取上期开奖号码成功", ex);
        }
        return respBody;
    }

    /**
     * 北京赛车开奖记录
     */
    @GetMapping(value = "/awardNumberList")
    public RespBody awardNumberList(HttpServletRequest request, Paging paging, String startTime, String endTime) {
        RespBody respBody = new RespBody();
        try {
            //根据用户id获取用户信息
            List<AppRacingLotteryPo> list = null;
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken();

            if (paging.getPageNumber() > 15) {
                paging.setPageNumber(15);
            }

            if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
                startTime = DateTimeUtil.formatDate(new Date(), DateTimeUtil.PATTERN_YYYY_MM_DD);
                endTime = DateTimeUtil.formatDate(new Date(), DateTimeUtil.PATTERN_YYYY_MM_DD);
            }
            endTime+=" "+"23:59:59";
            Date start = DateTimeUtil.parseDateFromStr(startTime, DateTimeUtil.PATTERN_YYYY_MM_DD);
            Date end = DateTimeUtil.parseDateFromStr(startTime, DateTimeUtil.PATTERN_YYYY_MM_DD);
            if (end.getTime() - start.getTime() > 7 * 24 * 60 * 60 * 1000) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "最大允许查询7天区间");
                return respBody;
            }
            //获取总记录数量
            int total = appRacingLotteryService.countLotteryInfoTotal(startTime, endTime);
            if (total > 0) {
                list = appRacingLotteryService.loadLotteryInfoList(paging, startTime, endTime);
            }
            //返回前端总记录
            paging.setTotalCount(total);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取开奖列表成功", paging, list);
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "获取开奖列表失败");
            LogUtils.error("获取开奖列  表失败！", ex);
        }
        return respBody;
    }


    public static void main(String args[]) {
        String a="1XXXXXXXXX";
        String regEx="\\dXXXXXXXXX";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        System.out.println( m.matches());
    }

}
