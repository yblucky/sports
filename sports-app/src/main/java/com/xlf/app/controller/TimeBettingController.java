package com.xlf.app.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.app.TimeBettingBaseVo;
import com.xlf.common.vo.app.TimeBettingVo;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.AppSysAgentSettingService;
import com.xlf.server.app.AppTimeBettingService;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.SysUserService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            Map<Integer, Set<Integer>> map = new HashMap<> ();
            Integer totalBettingNo = 0;

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
            respBody.add (RespCodeEnum.SUCCESS.getCode (), msgUtil.getMsg (AppMessage.WAIT_PAYING, "投注成功,等待开奖"));
        } catch (CommException ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), msgUtil.getMsg (AppMessage.APPLICATION_FAIL, "投注失败"));
            LogUtils.error ("投注失败！", ex);
        }
        return respBody;
    }


}
