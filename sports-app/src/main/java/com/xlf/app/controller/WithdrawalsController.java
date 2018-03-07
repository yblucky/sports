package com.xlf.app.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.CryptUtils;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.LogUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.BankCardVo;
import com.xlf.common.vo.app.DrawVo;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.AppBankCardService;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.app.AppWithDrawService;
import com.xlf.server.app.SysAgentSettingService;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 提现
 */
@RestController
@RequestMapping("/withdrawals")
public class WithdrawalsController {
    @Resource
    private CommonService commonService;
    @Resource
    private AppBillRecordService billRecordService;
    @Resource
    private LanguageUtil msgUtil;
    @Resource
    private AppWithDrawService appWithDrawService;
    @Resource
    private AppBankCardService appBankCardService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysAgentSettingService appSysAgentSettingService;


    @PostMapping("/draw")
    @SystemControllerLog(description = "提现")
    public RespBody withdrawals(HttpServletRequest request, @RequestBody DrawVo vo) throws Exception {
        RespBody respBody = new RespBody ();
        try {
            //验签
            /*Boolean flag = commonService.checkSign (vo);
            if (!flag) {
                respBody.add (RespCodeEnum.ERROR.getCode (), languageUtil.getMsg (AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }*/
            AppUserPo userPo = commonService.checkToken ();
            SysUserVo sysUserVo = sysUserService.findById (userPo.getParentId ());
            SysAgentSettingPo agentSettingPo = appSysAgentSettingService.findById (sysUserVo.getAgentLevelId ());
            BigDecimal withdrawMaxAmount = agentSettingPo.getMaxWithdrawPerDay ();
            if (!ToolUtils.is100Mutiple (vo.getAmount ().doubleValue ())) {
                respBody.add (RespCodeEnum.ERROR.getCode (), msgUtil.getMsg (AppMessage.AMOUNT_IS_100_MLUTIPLE, "单笔兑换余额必须是100整数倍"));
                return respBody;
            }
            if (vo.getAmount () == null || vo.getAmount ().compareTo (withdrawMaxAmount) == 1) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "每天最大提现额度为："+withdrawMaxAmount);
                return respBody;
            }
            if (StringUtils.isEmpty (vo.getBankCardId ())) {
                respBody.add (RespCodeEnum.ERROR.getCode (), msgUtil.getMsg (AppMessage.CARD_ID_NOT_NULL, "银行卡信息id不能为空"));
                return respBody;
            }
            BankCardVo bankCardVo = appBankCardService.findKey (vo.getBankCardId ());
            if (bankCardVo == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), msgUtil.getMsg (AppMessage.CAN_NOT_FIND_CARDINFO, "查询不到用户绑定的银行卡信息"));
                return respBody;
            }
            if (StringUtils.isEmpty (vo.getPayPwd ()) || !userPo.getPayPwd ().equals (CryptUtils.hmacSHA1Encrypt (vo.getPayPwd (), userPo.getPayStal ()))) {
                respBody.add (RespCodeEnum.ERROR.getCode (), msgUtil.getMsg (AppMessage.PAYPWD_ERROR, "支付密码错误"));
                return respBody;
            }
            appWithDrawService.epWithDraw (userPo.getId (), vo.getBankCardId (), vo.getAmount ());
            respBody.add (RespCodeEnum.SUCCESS.getCode (), msgUtil.getMsg (AppMessage.WAIT_PAYING, "提现申请成功，等待审核打款"));
        } catch (CommException ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), msgUtil.getMsg (AppMessage.APPLICATION_FAIL, "提现失败"));
            LogUtils.error ("提现失败！", ex);
        }
        return respBody;
    }


}
