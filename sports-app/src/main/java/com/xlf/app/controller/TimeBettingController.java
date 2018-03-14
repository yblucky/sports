package com.xlf.app.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.contrants.Constrants;
import com.xlf.common.enums.*;
import com.xlf.common.exception.CommException;
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
import com.xlf.server.app.AppSysAgentSettingService;
import com.xlf.server.app.AppTimeBettingService;
import com.xlf.server.app.AppTimeIntervalService;
import com.xlf.server.app.AppTimeLotteryService;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.SysUserService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
    public static List<String> regexTimeTwoList = new ArrayList<>(Arrays.asList(Constrants.REG_TIME_ONE_DOUBLE, Constrants.REG_TIME_TWO_DOUBLE, Constrants.REG_TIME_THREE_DOUBLE, Constrants.REG_TIME_FOURE_DOUBLE, Constrants.REG_TIME_FIVE_DOUBLE
            , Constrants.REG_TIME_FIVE_DOUBLE, Constrants.REG_TIME_SIX_DOUBLE, Constrants.REG_TIME_SEVEN_DOUBLE, Constrants.REG_TIME_EIGHT_DOUBLE, Constrants.REG_TIME_NINE_DOUBLE, Constrants.REG_TIME_TEN_DOUBLE));

    @Resource
    private CommonService commonService;
    @Resource
    private LanguageUtil msgUtil;
    @Resource
    private SysUserService sysUserService;
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
        RespBody respBody = new RespBody();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour >= 2 && hour < 10) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "非投注时间");
                return respBody;
            }
            Integer inteval = 5;
            if (hour >= 10 && hour < 22) {
                inteval = 10;
            }
            String hhmm = DateTimeUtil.parseCurrentDateMinuteIntervalToStr(DateTimeUtil.PATTERN_HH_MM, inteval);
            AppTimeIntervalPo intervalPo = appTimeIntervalService.findByTime(hhmm, LotteryTypeEnum.TIME.getCode());
            if (intervalPo == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "非投注时间");
                return respBody;
            }
            String isOpen = commonService.findParameter("isOpen");
            if ("off".equals(isOpen)) {
                respBody.add(RespCodeEnum.TIME_STOP.getCode(), "时时彩暂停投注");
                return respBody;
            }
            String currentDate = DateTimeUtil.formatDate(new Date(), DateTimeUtil.PATTERN_YYYYMMDD);
            //本期期号
            String cur = (intervalPo.getIssueNo()) < 100 ? "0" + (intervalPo.getIssueNo()) : (intervalPo.getIssueNo()) + "";
            String nex = (intervalPo.getIssueNo()) < 100 ? "0" + (intervalPo.getIssueNo() + 1) : (intervalPo.getIssueNo() + 1) + "";
            String historyIssuNo = currentDate + cur;
            String nextIssuNo = currentDate + nex;


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

            //本期投注截止时间
            String endDateStr = DateTimeUtil.formatDate(new Date(), DateTimeUtil.PATTERN_YYYY_MM_DD) + " " + hhmm;
            Date endDate = DateTimeUtil.parseDateFromStr(endDateStr, DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM);
            Long end = endDate.getTime() - endBeforeInt * 1000;
            Long start = endDate.getTime() - inteval * 60 * 1000 + openStartInt * 1000;
            Long open = endDate.getTime() + lotteryOpenInt * 1000;
            Date bettingEnd = new Date(end);
            Date bettingStart = new Date(start);
            Date bettingOpen = new Date(open);
            BettingInfoVo infoVo = new BettingInfoVo();
            infoVo.setHhmm(hhmm);
            infoVo.setHistoryIssuNo(historyIssuNo);
            infoVo.setCurrentIssueNo(intervalPo.getIssueNo().toString());
            infoVo.setNextIssuNo(nextIssuNo);
            infoVo.setEndDateStr(endDateStr);
            infoVo.setEndDate(endDate);
            infoVo.setEnd(end);
            infoVo.setStart(start);
            infoVo.setOpen(open);
            infoVo.setBettingStart(DateTimeUtil.formatDate(bettingStart, DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
            infoVo.setBettingEnd(DateTimeUtil.formatDate(bettingEnd, DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
            infoVo.setBettingOpen(DateTimeUtil.formatDate(bettingOpen, DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
            if (System.currentTimeMillis() > end && System.currentTimeMillis() < endDate.getTime()) {
                infoVo.setRestTime(0L);
            } else {
                infoVo.setRestTime(end - System.currentTimeMillis());
            }
            //查询上期的开奖结果
            String pre = (intervalPo.getIssueNo() - 1) < 100 ? "0" + (intervalPo.getIssueNo() - 1) : (intervalPo.getIssueNo() - 1) + "";
            String prepre = (intervalPo.getIssueNo() - 2) < 100 ? "0" + (intervalPo.getIssueNo() - 2) : (intervalPo.getIssueNo() - 2) + "";


            if (Integer.valueOf(pre) < 10) {
                pre = "0" + pre;
            }
            if (Integer.valueOf(prepre) < 10) {
                prepre = "0" + prepre;
            }
            if (intervalPo.getIssueNo() == 1) {
                pre = DateTimeUtil.getDayMinusWithPattern(-1, DateTimeUtil.PATTERN_YYYYMMDD) + "119";
            }
            AppTimeLotteryPo timeLotteryPo = appTimeLotteryService.findAppTimeLotteryPoByIssuNo(currentDate + pre);
            if (timeLotteryPo == null) {
                timeLotteryPo = appTimeLotteryService.findAppTimeLotteryPoByIssuNo(currentDate + prepre);
            }
            if (timeLotteryPo == null) {
                if (intervalPo.getIssueNo() == 1) {
                    pre = DateTimeUtil.getDayMinusWithPattern(-1, DateTimeUtil.PATTERN_YYYYMMDD) + "118";
                }
                timeLotteryPo = new AppTimeLotteryPo();
                timeLotteryPo.setId("");
                timeLotteryPo.setIssueNo("系统维护");
                timeLotteryPo.setFlag(LotteryFlagEnum.NO.getCode());
                timeLotteryPo.setLotteryOne(10);
                timeLotteryPo.setLotteryTwo(10);
                timeLotteryPo.setLotteryThree(10);
                timeLotteryPo.setLotteryFour(10);
                timeLotteryPo.setLotteryFive(10);
                timeLotteryPo.setLotteryTime(new Date());
            }
            infoVo.setAppTimeLotteryPo(timeLotteryPo);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取时时彩信息成功!", infoVo);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "获取时时彩信息失败!");
            LogUtils.error("获取时时彩信息失败！", ex);
        }
        return respBody;
    }


    @PostMapping("/timebetting")
    @SystemControllerLog(description = "时时彩投注")
    public RespBody timeBetting(HttpServletRequest request, @RequestBody TimeBettingVo vo) throws Exception {
        RespBody respBody = new RespBody();
        try {

            if (vo.getSerialNumber() == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "下注参数有误");
                return respBody;
            }

            String endBefore = commonService.findParameter("endBefore");
            if (StringUtils.isEmpty(endBefore)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "时时彩系统投注参数有误");
                return respBody;
            }
            Integer endBeforeInt = Integer.valueOf(endBefore);
            AppTimeIntervalPo timeIntervalPo = appTimeIntervalService.findByIssNo(vo.getSerialNumber(), 10);
            Long longDate = DateTimeUtil.getLongTimeByDatrStr(timeIntervalPo.getTime());
            if (System.currentTimeMillis() > (longDate - endBeforeInt * 1000)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "本期投注已截止");
                return respBody;
            }
            AppUserPo userPo = commonService.checkToken();
            SysUserVo sysUserVo = sysUserService.findById(userPo.getParentId());
            SysAgentSettingPo agentSettingPo = appSysAgentSettingService.findById(sysUserVo.getAgentLevelId());
            if (agentSettingPo == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "下注参数有误");
                return respBody;
            }
            if (CollectionUtils.isEmpty(vo.getTimeList())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "下注号码为空");
                return respBody;
            }
            if (vo.getTimeList().size() > agentSettingPo.getMaxBetSeats()) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "每期最多下注位数为" + agentSettingPo.getMaxBetSeats());
                return respBody;
            }
            if (userPo.getCurrentProfit().compareTo(agentSettingPo.getMaxProfitPerDay()) > 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "已达到当日最大盈利额度，今日不可再下注");
                return respBody;
            }
            Integer totalBettingNo = 0;

            //期数前缀：固定前缀+用户id+当天期数
//            String keyprefix = RedisKeyUtil.getTimeBettingLocationPrefix (userPo.getId (), vo.getSerialNumber (), TimeSeatEnum.ONE);
            //key 每个位置投了哪些数字，set
            //key ，每个数字投了多少注

            Integer length = vo.getTimeList().size();
            Integer[][] bettArray = new Integer[length][6];
            for (int j = 0; j < length; j++) {
                TimeBettingBaseVo base = vo.getTimeList().get(j);
                bettArray[j][0] = base.getLotteryOne();
                bettArray[j][1] = base.getLotteryTwo();
                bettArray[j][2] = base.getLotteryThree();
                bettArray[j][3] = base.getLotteryFour();
                bettArray[j][4] = base.getLotteryFive();
                bettArray[j][5] = base.getMultiple();
                if (base.getMultiple() < agentSettingPo.getMinBetNoPerDigital() || base.getMultiple() > agentSettingPo.getMaxBetNoPerDigital()) {
                    respBody.add(RespCodeEnum.ERROR.getCode(), "单个位数最小投注范围为【" + agentSettingPo.getMinBetNoPerDigital() + "," + agentSettingPo.getMaxBetNoPerDigital() + "】注");
                    return respBody;
                }
                totalBettingNo += base.getMultiple();
            }
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < 5; k++) {
                    Set<Integer> horizontal = new HashSet<>();
                    horizontal.add(bettArray[j][k]);
                    if (k == 4) {
                        if (horizontal.size() > 1) {
                            respBody.add(RespCodeEnum.ERROR.getCode(), "不符合投注规则,单注只能选择0-9中的一个数字");
                            return respBody;
                        }
                    }
                }
            }
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < length; k++) {
                    List<Integer> verticalList = new ArrayList<>();
                    Set<Integer> verticalSet = new HashSet<>();
                    if (bettArray[k][j] >= 0) {
                        verticalList.add(bettArray[k][j]);
                        verticalSet.add(bettArray[k][j]);
                    }
                    if (k == length - 1) {
                        if (verticalSet.size() > agentSettingPo.getMaxBetDigitalNoPerSeat()) {
                            respBody.add(RespCodeEnum.ERROR.getCode(), "不符合投注规则,每个位最多压注" + agentSettingPo.getMaxBetDigitalNoPerSeat() + "个不同的数字");
                            return respBody;
                        }
                        if (verticalList.size() != verticalSet.size()) {
                            respBody.add(RespCodeEnum.ERROR.getCode(), "不符合投注规则,单个位如果压注同一个数字,请合并投注,进行倍投");
                            return respBody;
                        }
                    }
                  /*  //记录历史的每个位投注的数字集合
                    Set<String> set = keyService.getTimeSetMembers (userPo.getId (), vo.getSerialNumber(), j);
                    if (set.size () > agentSettingPo.getMaxBetDigitalNoPerSeat ()) {
                        respBody.add (RespCodeEnum.ERROR.getCode (), "不符合投注规则,每个位最多压注" + agentSettingPo.getMaxBetDigitalNoPerSeat () + "个不同的数字");
                        return respBody;
                    }

                    //记录每个数字投了多少注
                    Long count = keyService.timebettingHget (userPo.getId (), vo.getSerialNumber (), bettArray[k][j]);
                    Long currentCount = count + Long.valueOf (bettArray[k][5]);
                    if (currentCount < agentSettingPo.getMinBetNoPerDigital () || currentCount > agentSettingPo.getMaxBetNoPerDigital ()) {
                        keyService.saddTimeSetMember (userPo.getId (), vo.getSerialNumber (), j, bettArray[k][j]);
                        respBody.add (RespCodeEnum.ERROR.getCode (), "单个位数最小投注范围为【" + agentSettingPo.getMinBetNoPerDigital () + "," + agentSettingPo.getMaxBetNoPerDigital () + "】注");
                        return respBody;
                    }
                    keyService.saddTimeSetMember (userPo.getId (), vo.getSerialNumber (), j, bettArray[k][j]);
                    keyService.timebettingHset (userPo.getId (), vo.getSerialNumber (), bettArray[k][j], currentCount);*/
                }
            }

            //最大可能中奖金额
            if (userPo.getBalance().compareTo(new BigDecimal(totalBettingNo.toString())) == -1) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "用户余额不足，无法完成下注");
                return respBody;
            }
            BigDecimal maximumAward = new BigDecimal(totalBettingNo).multiply(agentSettingPo.getOdds());
            appTimeBettingService.timeBettingService(userPo.getId(), vo, new BigDecimal(totalBettingNo));
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
     * 一字定投注
     *
     * @param request
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping("/oneTimeBetting")
    @SystemControllerLog(description = "一字定投注")
    public RespBody oneTimeBetting(HttpServletRequest request, @RequestBody TimeBettingVo vo, Paging paging) throws Exception {
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
            if (BetTypeEnum.TIME_ONE.getCode() != vo.getBetType()) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "非一字定投注");
                return respBody;
            }
            String endBefore = commonService.findParameter("endBefore");
            if (StringUtils.isEmpty(endBefore)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "时时彩系统投注参数有误");
                return respBody;
            }
            Integer endBeforeInt = Integer.valueOf(endBefore);
            AppTimeIntervalPo timeIntervalPo = appTimeIntervalService.findByIssNo(vo.getSerialNumber(), 10);
            Long longDate = DateTimeUtil.getLongTimeByDatrStr(timeIntervalPo.getTime());
            if (System.currentTimeMillis() > (longDate - endBeforeInt * 1000)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "本期投注已截止");
                return respBody;
            }
            if (CollectionUtils.isEmpty(vo.getTimeList())) {
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
         /*   if (!userPo.getPayPwd ().equals (CryptUtils.hmacSHA1Encrypt (vo.getPayPwd (), userPo.getPayStal ()))) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "支付密码错误");
                return respBody;
            }*/
            if (userPo.getCurrentProfit().compareTo(agentSettingPo.getMaxProfitPerDay()) > 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "已达到当日最大盈利额度，今日不可再下注");
                return respBody;
            }
            List<BettingBaseVo> allList = new ArrayList<>();
            Integer totalBettingNo = 0;
            Integer thisTotalBettingNo = 0;
            Integer hasBettingCount = appTimeBettingService.countBettingByUserIdAndIssueNoAndContent(userPo.getId(), vo.getIssueNo(), null, BetTypeEnum.TIME_ONE.getCode());
            for (TimeBettingBaseVo baseVo : vo.getTimeList()) {
                if (baseVo.getMultiple() < agentSettingPo.getMinBetNoPerDigital() || baseVo.getMultiple() > agentSettingPo.getMaxBetNoPerDigital()) {
                    respBody.add(RespCodeEnum.ERROR.getCode(), "单个数字最小投注范围为【" + agentSettingPo.getMinBetNoPerDigital() + "," + agentSettingPo.getMaxBetNoPerDigital() + "】注" + baseVo.getBettingContent() + "超限制");
                    return respBody;
                }
                if (baseVo.getBettingContent().replaceAll("\\d", "").length() != 4) {
                    respBody.add(RespCodeEnum.ERROR.getCode(), "非一字定投注");
                    return respBody;
                }
                if (hasBettingCount > 0) {
                    Integer count = appTimeBettingService.countBettingByUserIdAndIssueNoAndContent(userPo.getId(), vo.getIssueNo(), baseVo.getBettingContent(), BetTypeEnum.TIME_ONE.getCode());
                    if (count > 0) {
                        paging.setPageSize(30);
                        paging.setPageNumber(1);
                        List<AppTimeBettingPo> timeBettingPos = appTimeBettingService.findListByUserIdAndIssueNoAndContent(userPo.getId(), vo.getIssueNo(), baseVo.getBettingContent(), BetTypeEnum.TIME_ONE.getCode(), paging);
                        Integer total = 0;
                        for (AppTimeBettingPo po : timeBettingPos) {
                            total += baseVo.getMultiple();
                            total += po.getMultiple();
                            if (total < agentSettingPo.getMinBetNoPerDigital() || total > agentSettingPo.getMaxBetNoPerDigital()) {
                                respBody.add(RespCodeEnum.ERROR.getCode(), "单个数字最小投注范围为【" + agentSettingPo.getMinBetNoPerDigital() + "," + agentSettingPo.getMaxBetNoPerDigital() + "】注," + baseVo.getBettingContent() + "超限制");
                                return respBody;
                            }
                            BettingBaseVo bettingBaseVo = new BettingBaseVo();
                            bettingBaseVo.setMultiple(po.getMultiple());
                            bettingBaseVo.setBettingContent(po.getBettingContent());
                            allList.add(bettingBaseVo);
                        }
                    } else {
                        List<AppTimeBettingPo> timeBettingPos = appTimeBettingService.findListByUserIdAndIssueNoAndContent(userPo.getId(), vo.getIssueNo(), null, BetTypeEnum.TIME_ONE.getCode(), paging);
                        for (AppTimeBettingPo po : timeBettingPos) {
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

            for (BettingBaseVo bettingBaseVo : allList) {
                if (ToolUtils.regex(bettingBaseVo.getBettingContent(), Constrants.REG_TIME_ONE)) {
                    if (!map.containsKey(1)) {
                        map.put(1, new HashSet<String>());
                    }
                    map.get(1).add(bettingBaseVo.getBettingContent());
                    if (map.get(1).size() > agentSettingPo.getMaxBetDigitalNoPerSeat()) {
                        respBody.add(RespCodeEnum.ERROR.getCode(), "万位不符合投注规则,每个位最多压注" + agentSettingPo.getMaxBetDigitalNoPerSeat() + "个不同的数字");
                        return respBody;
                    }
                }
                if (ToolUtils.regex(bettingBaseVo.getBettingContent(), Constrants.REG_TIME_TWO)) {
                    if (!map.containsKey(2)) {
                        map.put(2, new HashSet<String>());
                    }
                    map.get(2).add(bettingBaseVo.getBettingContent());
                    if (map.get(2).size() > agentSettingPo.getMaxBetDigitalNoPerSeat()) {
                        respBody.add(RespCodeEnum.ERROR.getCode(), "千位不符合投注规则,每个位最多压注" + agentSettingPo.getMaxBetDigitalNoPerSeat() + "个不同的数字");
                        return respBody;
                    }
                }
                if (ToolUtils.regex(bettingBaseVo.getBettingContent(), Constrants.REG_TIME_THREE)) {
                    if (!map.containsKey(3)) {
                        map.put(3, new HashSet<String>());
                    }
                    map.get(3).add(bettingBaseVo.getBettingContent());
                    if (map.get(3).size() > agentSettingPo.getMaxBetDigitalNoPerSeat()) {
                        respBody.add(RespCodeEnum.ERROR.getCode(), "百位不符合投注规则,每个位最多压注" + agentSettingPo.getMaxBetDigitalNoPerSeat() + "个不同的数字");
                        return respBody;
                    }
                }
                if (ToolUtils.regex(bettingBaseVo.getBettingContent(), Constrants.REG_TIME_FOURE)) {
                    if (!map.containsKey(4)) {
                        map.put(4, new HashSet<String>());
                    }
                    map.get(4).add(bettingBaseVo.getBettingContent());
                    if (map.get(4).size() > agentSettingPo.getMaxBetDigitalNoPerSeat()) {
                        respBody.add(RespCodeEnum.ERROR.getCode(), "十位不符合投注规则,每个位最多压注" + agentSettingPo.getMaxBetDigitalNoPerSeat() + "个不同的数字");
                        return respBody;
                    }
                }
                if (ToolUtils.regex(bettingBaseVo.getBettingContent(), Constrants.REG_TIME_FIVE)) {
                    if (!map.containsKey(5)) {
                        map.put(5, new HashSet<String>());
                    }
                    map.get(5).add(bettingBaseVo.getBettingContent());
                    if (map.get(5).size() > agentSettingPo.getMaxBetDigitalNoPerSeat()) {
                        respBody.add(RespCodeEnum.ERROR.getCode(), "个位不符合投注规则,每个位最多压注" + agentSettingPo.getMaxBetDigitalNoPerSeat() + "个不同的数字");
                        return respBody;
                    }
                }
            }

            if (map.size() > agentSettingPo.getMaxBetSeats()) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "每期最多下注位数为" + agentSettingPo.getMaxBetSeats());
                return respBody;
            }
            //最大可能中奖金额
            if (userPo.getBalance().compareTo(new BigDecimal(thisTotalBettingNo.toString())) == -1) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "用户余额不足，无法完成下注");
                return respBody;
            }
            Object lastBettingTotalNoObj = redisService.getObj(RedisKeyEnum.TIME_BETTIING_ONE.getKey() + (Long.valueOf(vo.getIssueNo()) - 1) + userPo.getId());
            if (lastBettingTotalNoObj != null) {
                Integer lastBettingTotalNo = (Integer) lastBettingTotalNoObj;
                totalBettingNo += lastBettingTotalNo;
            }
            BigDecimal winRate = new BigDecimal(commonService.findParameter("winRate"));
            BigDecimal currentProfitSum = userPo.getCurrentProfit().add(new BigDecimal(totalBettingNo).multiply(agentSettingPo.getOdds()));
            if (currentProfitSum.multiply(winRate).compareTo(agentSettingPo.getMaxProfitPerDay()) == 1) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "盈利额度超限,无法完成下注");
                return respBody;
            }
            redisService.putObj(RedisKeyEnum.TIME_BETTIING_ONE.getKey() + vo.getIssueNo() + userPo.getId(), totalBettingNo, RedisKeyEnum.TIME_BETTIING_ONE.getSeconds());
            BigDecimal maximumAward = new BigDecimal(totalBettingNo).multiply(agentSettingPo.getOdds());
            appTimeBettingService.timeBettingService(userPo.getId(), vo, new BigDecimal(thisTotalBettingNo));
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
     * 二字定投注
     *
     * @param request
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping("/twoTimeBetting")
    @SystemControllerLog(description = "二字定投注")
    public RespBody twoTimeBetting(HttpServletRequest request, @RequestBody TimeBettingVo vo, Paging paging) throws Exception {
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
            if (BetTypeEnum.TIME_TWO.getCode() != vo.getBetType()) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "非二字定投注");
                return respBody;
            }
            String endBefore = commonService.findParameter("endBefore");
            if (StringUtils.isEmpty(endBefore)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "时时彩系统投注参数有误");
                return respBody;
            }
            Integer endBeforeInt = Integer.valueOf(endBefore);
            AppTimeIntervalPo timeIntervalPo = appTimeIntervalService.findByIssNo(vo.getSerialNumber(), 10);
            Long longDate = DateTimeUtil.getLongTimeByDatrStr(timeIntervalPo.getTime());
            if (System.currentTimeMillis() > (longDate - endBeforeInt * 1000)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "本期投注已截止");
                return respBody;
            }
            if (CollectionUtils.isEmpty(vo.getTimeList())) {
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
           /* if (!userPo.getPayPwd ().equals (CryptUtils.hmacSHA1Encrypt (vo.getPayPwd (), userPo.getPayStal ()))) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "支付密码错误");
                return respBody;
            }*/
            if (userPo.getCurrentProfit().compareTo(agentSettingPo.getMaxProfitPerDay()) > 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "已达到当日最大盈利额度，今日不可再下注");
                return respBody;
            }
            List<BettingBaseVo> allList = new ArrayList<>();
            Integer totalBettingNo = 0;
            Integer thisTotalBettingNo = 0;
            Integer hasBettingCount = appTimeBettingService.countBettingByUserIdAndIssueNoAndContent(userPo.getId(), vo.getIssueNo(), null, BetTypeEnum.TIME_TWO.getCode());
            for (TimeBettingBaseVo baseVo : vo.getTimeList()) {
                if (baseVo.getBettingContent().replaceAll("\\d", "").length() != 3) {
                    respBody.add(RespCodeEnum.ERROR.getCode(), "非二字定投注");
                    return respBody;
                }
                if (baseVo.getMultiple() < 1 || baseVo.getMultiple() > agentSettingPo.getTimeDoubleMaxBetNoPerKind()) {
                    respBody.add(RespCodeEnum.ERROR.getCode(), "相同两个数字单注投注范围为【" + "1," + agentSettingPo.getTimeDoubleMaxBetNoPerKind() + "】注" + baseVo.getBettingContent() + "超限制");
                    return respBody;
                }
                if (hasBettingCount > 0) {
                    Integer count = appTimeBettingService.countBettingByUserIdAndIssueNoAndContent(userPo.getId(), vo.getIssueNo(), baseVo.getBettingContent(), BetTypeEnum.TIME_TWO.getCode());
                    if (count > 0) {
                        paging.setPageSize(30);
                        paging.setPageNumber(1);
                        List<AppTimeBettingPo> timeBettingPos = appTimeBettingService.findListByUserIdAndIssueNoAndContent(userPo.getId(), vo.getIssueNo(), baseVo.getBettingContent(), BetTypeEnum.TIME_TWO.getCode(), paging);
                        Integer total = 0;
                        for (AppTimeBettingPo po : timeBettingPos) {
                            total += baseVo.getMultiple();
                            total += po.getMultiple();
                            if (total < 1 || total > agentSettingPo.getTimeDoubleMaxBetNoPerKind()) {
                                respBody.add(RespCodeEnum.ERROR.getCode(), "相同两个数字投注范围为【" + "1" + "," + agentSettingPo.getTimeDoubleMaxBetNoPerKind() + "】注," + baseVo.getBettingContent() + "超限制");
                                return respBody;
                            }
                            BettingBaseVo bettingBaseVo = new BettingBaseVo();
                            bettingBaseVo.setMultiple(po.getMultiple());
                            bettingBaseVo.setBettingContent(po.getBettingContent());
                            allList.add(bettingBaseVo);
                        }
                    } else {
                        List<AppTimeBettingPo> timeBettingPos = appTimeBettingService.findListByUserIdAndIssueNoAndContent(userPo.getId(), vo.getIssueNo(), null, BetTypeEnum.TIME_TWO.getCode(), paging);
                        for (AppTimeBettingPo po : timeBettingPos) {
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
            Set<String> twoGroupSet = new HashSet<>();
            Map<String, Integer> sigleGroupMap = new HashMap<>();
            for (BettingBaseVo bettingBaseVo : allList) {
                for (String regex : regexTimeTwoList) {
                    if (ToolUtils.regex(bettingBaseVo.getBettingContent(), regex)) {
                        twoGroupSet.add(regex);
                        if (sigleGroupMap.containsKey(regex)) {
                            sigleGroupMap.put(regex, sigleGroupMap.get(regex) + 1);
                        } else {
                            sigleGroupMap.put(regex, 1);
                        }
                        if (twoGroupSet.size() > 0 && twoGroupSet.size() > agentSettingPo.getTimeDoubleMaxBetKindPerTwoSeats()) {
                            respBody.add(RespCodeEnum.ERROR.getCode(), "二字定组合每期最多组合位数为" + agentSettingPo.getTimeDoubleMaxBetKindPerTwoSeats() + "种," + regex.replace("\\d", "号") + "组合超限");
                            return respBody;
                        }
                        if (twoGroupSet.size() > 0 && sigleGroupMap.get(regex) > agentSettingPo.getTimeDoubleMaxBetNoPerKind()) {
                            respBody.add(RespCodeEnum.ERROR.getCode(), "二字定每期两个位组合100种最多选取" + agentSettingPo.getTimeDoubleMaxBetNoPerKind() + "种," + regex.replace("\\d", "号") + "组合超限");
                            return respBody;
                        }
                    }
                }
            }
            //最大可能中奖金额
            if (userPo.getBalance().compareTo(new BigDecimal(thisTotalBettingNo.toString())) == -1) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "用户余额不足,无法完成下注");
                return respBody;
            }
            Object lastBettingTotalNoObj = redisService.getObj(RedisKeyEnum.TIME_BETTIING_TWO.getKey() + (Long.valueOf(vo.getIssueNo()) - 1) + userPo.getId());
            if (lastBettingTotalNoObj != null) {
                Integer lastBettingTotalNo = (Integer) lastBettingTotalNoObj;
                totalBettingNo += lastBettingTotalNo;
            }
            BigDecimal winRate = new BigDecimal(commonService.findParameter("winRate"));
            BigDecimal currentProfitSum = userPo.getCurrentProfit().add(new BigDecimal(totalBettingNo).multiply(agentSettingPo.getTimeDoubleOdds()));
            System.out.println(currentProfitSum.multiply(winRate));
            if (currentProfitSum.multiply(winRate).compareTo(agentSettingPo.getMaxProfitPerDay()) == 1) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "盈利额度超限,无法完成下注");
                return respBody;
            }
            redisService.putObj(RedisKeyEnum.TIME_BETTIING_TWO.getKey() + vo.getIssueNo() + userPo.getId(), totalBettingNo, RedisKeyEnum.TIME_BETTIING_TWO.getSeconds());
            BigDecimal maximumAward = new BigDecimal(totalBettingNo).multiply(agentSettingPo.getOdds());
            appTimeBettingService.timeBettingService(userPo.getId(), vo, new BigDecimal(thisTotalBettingNo));
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
     * 快选
     *
     * @param request
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping("/quickChoose")
    @SystemControllerLog(description = "快选")
    public RespBody quickChoose(HttpServletRequest request, @RequestBody TimeQuickChooseVo vo) throws Exception {
        RespBody respBody = new RespBody();
        try {
            //验签
            /*Boolean flag = commonService.checkSign (vo);
            if (!flag) {
                respBody.add (RespCodeEnum.ERROR.getCode (), languageUtil.getMsg (AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }*/
            if (vo.getMultiple() == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "快选下注数量有误");
                return respBody;
            }
            if (vo.getBetType() == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "快选类型有误");
                return respBody;
            }
            if (vo.getKindType() == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "快选取除类型有误");
                return respBody;
            }
            if (vo.getMap().size() == 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "快选下注组合有误");
                return respBody;
            }
            List<String> list = ToolUtils.quickChoose(vo.getBetType(), vo.getKindType(), vo.getMap());
            List<List<String>> lists = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                List innerliset = null;
                if (i % 9 == 0) {
                    innerliset = new ArrayList();
                    lists.add(innerliset);
                }
                int index = i / 9;
                lists.get(index).add(list.get(i));
            }
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "生成快选组合成功", lists);
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "生成有误");
            LogUtils.error("生成有误！", ex);
        }
        return respBody;
    }


    @PostMapping("/undobetting")
    @SystemControllerLog(description = "时时彩撤单")
    public RespBody undoBetting(HttpServletRequest request, @RequestBody UndoBettingVo vo) throws Exception {
        RespBody respBody = new RespBody();
        try {

            if (vo.getId() == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "撤单参数有误");
                return respBody;
            }
            AppTimeBettingPo bettingPo = appTimeBettingService.findById(vo.getId());
            if (bettingPo == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "找不到下单记录");
                return respBody;
            }
            AppTimeIntervalPo timeIntervalPo = appTimeIntervalService.findByIssNo(bettingPo.getSerialNumber(), 10);
            if (timeIntervalPo == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "撤单参数有误");
                return respBody;
            }
            Long openDate = DateTimeUtil.getLongTimeByDatrStr(timeIntervalPo.getTime());
            String undoBefore = commonService.findParameter("undoBefore");
            Integer undoBeforeInt = Integer.valueOf(undoBefore);
            if (StringUtils.isEmpty(undoBefore)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "撤单参数时间有误");
                return respBody;
            }
            if ((System.currentTimeMillis() + undoBeforeInt * 1000) >= openDate) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "开奖不足" + undoBeforeInt + "秒,无法撤单");
                return respBody;
            }
            AppUserPo userPo = commonService.checkToken();
            appTimeBettingService.undoTimeBettingService(userPo.getId(), vo.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "撤单成功");
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "撤单失败");
            LogUtils.error("撤单失败！", ex);
        }
        return respBody;
    }

    /**
     * 用户流水记录
     */
    @GetMapping(value = "/awardNumberList")
    public RespBody findUserRecord(HttpServletRequest request, Paging paging) {
        RespBody respBody = new RespBody();
        try {
            //根据用户id获取用户信息
            List<AppTimeLotteryVo> list = null;
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken();

            //获取总记录数量
            int total = appTimeLotteryService.countLotteryInfoTotal();
            if (total > 0) {
                list = appTimeLotteryService.loadLotteryInfoList(paging);
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


    public static void main(String[] args) {

    /*    String s = Constrants.REG_TIME_EIGHT_DOUBLE;
        System.out.println (s.replaceAll ("\\d", "口"));
        String hhmm = DateTimeUtil.parseCurrentDateMinuteIntervalToStr (DateTimeUtil.PATTERN_HH_MM, 10);

        Calendar calendar = Calendar.getInstance ();
        calendar.setTime (DateTimeUtil.parseDateFromStr ("2018-03-01 16:00:00", DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM_SS));
        calendar.set (Calendar.MINUTE, 10 * (calendar.get (Calendar.MINUTE) / 10));
        Date date1 = calendar.getTime ();
        SimpleDateFormat format = new SimpleDateFormat (DateTimeUtil.PATTERN_HH_MM);
        System.out.println (format.format (date1));*/


        Map m = new HashMap();
        m.put(1, "123456");
        m.put(4, "12345");
        List<String> list = ToolUtils.quickChoose(2, 1, m);
        List<List<String>> lists = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List innerliset = null;
            if (i % 10 == 0) {
                innerliset = new ArrayList();
                lists.add(innerliset);
            }
            int index = i / 10;
            lists.get(index).add(list.get(i));
        }
        System.out.println(lists.size());
    }
}

