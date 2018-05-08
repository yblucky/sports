package com.xlf.app.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.AppWithDrawPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.CryptUtils;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.LogUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.AppBillRecordVo;
import com.xlf.common.vo.app.BankCardVo;
import com.xlf.common.vo.app.DrawVo;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.AppBankCardService;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.app.AppWithDrawService;
import com.xlf.server.app.SysAgentSettingService;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.SysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
            if (vo.getAmount () == null || vo.getAmount ().compareTo (withdrawMaxAmount) == 1) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "每天最大提现额度为："+withdrawMaxAmount);
                return respBody;
            }
             if (userPo==null || userPo.getBalance ().compareTo (vo.getAmount ()) < 0){
                throw  new CommException ("用户余额不足，无法提现");
            }
            BigDecimal currencySumDraw= new BigDecimal (appWithDrawService.drawSumCurrentDay (userPo.getId ()));
            if (currencySumDraw.compareTo (withdrawMaxAmount) == 1) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "每天最大提现额度为："+withdrawMaxAmount);
                return respBody;
            }
            if ((currencySumDraw.add (vo.getAmount ())).compareTo (withdrawMaxAmount) == 1) {
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
            appWithDrawService.epWithDraw (userPo.getId (), vo.getBankCardId (),bankCardVo.getBankName(),vo.getAmount ());
            respBody.add (RespCodeEnum.SUCCESS.getCode (), msgUtil.getMsg (AppMessage.WAIT_PAYING, "提现申请成功，等待审核打款"));
        } catch (CommException ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), msgUtil.getMsg (AppMessage.APPLICATION_FAIL, "提现失败"));
            LogUtils.error ("提现失败！", ex);
        }
        return respBody;
    }



    /**
     * 用户提现流水
     */
    @GetMapping(value = "/list")
    public RespBody list(HttpServletRequest request, Paging paging) {
        RespBody respBody = new RespBody ();
        try {
            //根据用户id获取用户信息
            List<AppWithDrawPo> list = null;
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken ();

            //获取总记录数量
            int total = appWithDrawService.drawRecordListTotal (appUserPo.getId ());
            if (total > 0) {
                list = appWithDrawService.withDrawRecordList (appUserPo.getId (), paging);
            }
            //返回前端总记录
            paging.setTotalCount (total);
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取用户记录成功", paging, list);
        } catch (CommException ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), ex.getMessage ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (),"获取用户记录失败");
            LogUtils.error ("获取用户记录失败！", ex);
        }
        return respBody;
    }

}
