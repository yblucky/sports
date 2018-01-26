package com.xlf.app.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.CryptUtils;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.LogUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.*;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.*;
import com.xlf.server.app.impl.AppBillRecordServiceImpl;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.SysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/assets")
public class AssetsController {
    @Resource
    private CommonService commonService;
    @Resource
    private AppBillRecordServiceImpl billRecordService;
    @Resource
    private LanguageUtil msgUtil;
    @Resource
    private AppWithDrawService appWithDrawService;
    @Resource
    private AppBankCardService appBankCardService;
    @Resource
    private AppUserService appUserService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private LanguageUtil languageUtil;
    @Resource
    private AppSysAgentSettingService appSysAgentSettingService;
    @Resource
    private AppTimeBettingService appTimeBettingService;

    @PostMapping("/timebetting")
    @SystemControllerLog(description = " ")
    public RespBody timeBetting(HttpServletRequest request, @RequestBody TimeBettingVo vo) throws Exception {
        RespBody respBody = new RespBody();
        try {
            //验签
            Boolean flag = commonService.checkSign(vo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }
            AppUserPo userPo = commonService.checkToken();
            SysUserVo sysUserVo = sysUserService.findById(userPo.getParentId());
            SysAgentSettingPo agentSettingPo = appSysAgentSettingService.findById(userPo.getParentId());
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
            Map<Integer,Set<Integer>> map = new HashMap<>();

            Integer totalBettingNo = 0;
            for (TimeBettingBaseVo base : vo.getTimeList()) {
                if (base.getMultiple() < agentSettingPo.getMinBetNoPerDigital() || base.getMultiple() > agentSettingPo.getMaxBetNoPerDigital()) {
                    respBody.add(RespCodeEnum.ERROR.getCode(), "单个位数最小投注范围为【" + agentSettingPo.getMinBetNoPerDigital() + "," + agentSettingPo.getMaxBetNoPerDigital() + "】注");
                    return respBody;
                }
                totalBettingNo += base.getMultiple();
                if (base.getLotteryOne() >= 0) {
                    Set<Integer> set = new HashSet<>();
                    set.add(base.getLotteryOne());
                    map.put(1,set);
                }
                if (base.getLotteryTwo() >= 0) {
                    Set<Integer> set = new HashSet<>();
                    set.add(base.getLotteryTwo());
                    map.put(2,set);
                }
                if (base.getLotteryThree() >= 0) {
                    Set<Integer> set = new HashSet<>();
                    set.add(base.getLotteryThree());
                    map.put(3,set);
                }
                if (base.getLotteryFour() >= 0) {
                    Set<Integer> set = new HashSet<>();
                    set.add(base.getLotteryFour());
                    map.put(4,set);
                }
                if (base.getLotteryFive() >= 0) {
                    Set<Integer> set = new HashSet<>();
                    set.add(base.getLotteryFive());
                    map.put(5,set);
                }
            }
            if (map.size() > agentSettingPo.getMaxBetSeats()) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "每期最多下注位数为" + agentSettingPo.getMaxBetSeats());
                return respBody;
            }
            for (Set<Integer> set:map.values()){
                if (set.size()>agentSettingPo.getMaxBetDigitalNoPerSeat()){
                    respBody.add(RespCodeEnum.ERROR.getCode(), "每期单个位数最多下注不同数字个数为" + agentSettingPo.getMaxBetDigitalNoPerSeat());
                    return respBody;
                }
            }
            //最大可能中奖金额
            if (userPo.getBalance().compareTo(new BigDecimal(totalBettingNo.toString()))==-1){
                respBody.add(RespCodeEnum.ERROR.getCode(), "用户余额不足，无法完成下注");
                return respBody;
            }
            BigDecimal maximumAward = new BigDecimal(totalBettingNo).multiply(agentSettingPo.getOdds());
            appTimeBettingService.timeBettingService(userPo.getId(),vo, new BigDecimal(totalBettingNo));
            respBody.add(RespCodeEnum.SUCCESS.getCode(), msgUtil.getMsg(AppMessage.WAIT_PAYING, "投注成功,等待开奖"));
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.APPLICATION_FAIL, "投注失败"));
            LogUtils.error("投注失败！", ex);
        }
        return respBody;
    }



    @PostMapping("/withdrawals")
    @SystemControllerLog(description = "提现")
    public RespBody withdrawals(HttpServletRequest request, @RequestBody DrawVo vo) throws Exception {
        RespBody respBody = new RespBody();
        try {
            //验签
            Boolean flag = commonService.checkSign(vo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }
            AppUserPo userPo = commonService.checkToken();
            BigDecimal withdrawMinAmount = new BigDecimal(commonService.findParameter("withdrawMinAmount"));
            BigDecimal withdrawMaxAmount = new BigDecimal(commonService.findParameter("withdrawMaxAmount"));
            Integer withdrawMaxCount = Integer.valueOf(commonService.findParameter("withdrawMaxCount"));
            if (!ToolUtils.is100Mutiple(vo.getAmount().doubleValue())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.AMOUNT_IS_100_MLUTIPLE, "单笔兑换余额必须是100整数倍"));
                return respBody;
            }
            if (vo.getAmount() == null || vo.getAmount().compareTo(withdrawMinAmount) == -1 || vo.getAmount().compareTo(withdrawMaxAmount) == 1) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.EP_BALANCE_BETWEEN + "[" + withdrawMinAmount + "," + withdrawMaxAmount + "]", "单笔兑换余额必须在[" + withdrawMinAmount + "," + withdrawMaxAmount + "]"));
                return respBody;
            }
            if (StringUtils.isEmpty(vo.getBankCardId())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.CARD_ID_NOT_NULL, "银行卡信息id不能为空"));
                return respBody;
            }
            BankCardVo bankCardVo = appBankCardService.findKey(vo.getBankCardId());
            if (bankCardVo == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.CAN_NOT_FIND_CARDINFO, "查询不到用户绑定的银行卡信息"));
                return respBody;
            }
            if (StringUtils.isEmpty(vo.getPayPwd()) || !userPo.getPayPwd().equals(CryptUtils.hmacSHA1Encrypt(vo.getPayPwd(), userPo.getPayStal()))) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYPWD_ERROR, "支付密码错误"));
                return respBody;
            }
            Integer hasExchange = billRecordService.countCurrentDayWithDraw(userPo.getId());
            if (withdrawMaxCount != null && hasExchange >= withdrawMaxCount) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.NUMBER_PER_DAY + " " + withdrawMaxCount, "每天最多提现" + withdrawMaxCount + "笔"));
                return respBody;
            }
            appWithDrawService.epWithDraw(userPo.getId(), vo.getBankCardId(), vo.getAmount());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), msgUtil.getMsg(AppMessage.WAIT_PAYING, "提现申请成功，等待审核打款"));
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.APPLICATION_FAIL, "提现失败"));
            LogUtils.error("提现失败！", ex);
        }
        return respBody;
    }

    /**
     * 用户流水记录
     */
    @GetMapping(value = "/record")
    public RespBody findUserRecord(HttpServletRequest request, Paging paging, Integer currencyType) {
        RespBody respBody = new RespBody();
        try {
            //根据用户id获取用户信息
            List<AppBillRecordVo> list = null;
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken();
            String[] busnessTypes = request.getParameterValues("busnessType");
            if (ArrayUtils.isEmpty(busnessTypes) || currencyType == null) {
                paging.setTotalCount(0);
                respBody.add(RespCodeEnum.ERROR.getCode(), AppMessage.PARAM_ERROR, "参数不合法");
            }
            List<Integer> busnessTypeList = new ArrayList<>();
            for (String b : busnessTypes) {
                busnessTypeList.add(Integer.valueOf(b));
            }
            //获取总记录数量
            int total = billRecordService.billRecordListTotal(appUserPo.getId(), busnessTypeList, currencyType);
            if (total > 0) {
                list = billRecordService.findBillRecordList(appUserPo.getId(), busnessTypeList, paging);
            }
            //返回前端总记录
            paging.setTotalCount(total);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), msgUtil.getMsg(AppMessage.GET_RECORD_SUCCESS, "获取用户记录成功"), paging, list);
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.GET_RECORD_FAILE, "获取用户记录失败"));
            LogUtils.error("获取用户记录失败！", ex);
        }
        return respBody;
    }


    /**
     * 用户流水记录
     */
    @GetMapping(value = "/withdrawRecord")
    public RespBody withdrawRecord(HttpServletRequest request, Paging paging) {
        RespBody respBody = new RespBody();
        try {
            //根据用户id获取用户信息
            List<DrawRecordVo> list = Collections.emptyList();
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken();
            //获取总记录数量
            int total = appWithDrawService.drawRecordTotal(appUserPo.getId());
            if (total > 0) {
                list = appWithDrawService.drawRecordList(appUserPo.getId(), paging);
            }
            //返回前端总记录
            paging.setTotalCount(total);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), msgUtil.getMsg(AppMessage.GET_RECORD_SUCCESS, "获取用户记录成功"), paging, list);
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.GET_RECORD_FAILE, "获取用户记录失败"));
            LogUtils.error("获取用户记录失败！", ex);
        }
        return respBody;
    }


}
