package com.xlf.app.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.IsAllowedEnum;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.enums.StateEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppPerformanceParamPo;
import com.xlf.common.po.AppUserContactPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.server.app.*;
import com.xlf.server.app.impl.AppBillRecordServiceImpl;
import com.xlf.server.common.CommonService;
import com.xlf.common.util.*;
import com.xlf.common.vo.app.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
    private WalletService walletService;
    @Resource
    private AppPerformanceParamService appPerformanceParamService;
    @Resource
    private AppPerformanceRecordService appPerformanceRecordService;
    @Resource
    private AppUserContactService appUserContactService;
    @Resource
    private LanguageUtil languageUtil;


    @PostMapping("/epwithdraw")
    @SystemControllerLog(description = "提现")
    public RespBody epWithDraw(HttpServletRequest request, @RequestBody DrawVo vo) throws Exception {
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
            if (vo.getAmount() == null || vo.getAmount().compareTo(userPo.getEpBalance()) == 1) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.EP_BALANCE_NOT_ENOUGH, "提现EP余额不足"));
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
     * Ep转账
     *
     * @param paymentVo
     * @return
     */
    @PostMapping(value = "/eptransfer")
    @SystemControllerLog(description = "Ep转账")
    public RespBody eptransfer(@RequestBody PaymentVo paymentVo) {

        RespBody respBody = new RespBody();
        try {
            Boolean flag = commonService.checkSign(paymentVo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            if (org.springframework.util.StringUtils.isEmpty(paymentVo.getAmount())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYEE_AMOUNT_NOT_NULL, "收款金额不能为空"));
                return respBody;
            }
            if ((new BigDecimal(paymentVo.getAmount())).compareTo(BigDecimal.ZERO) <= 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PARAM_ERROR, "参数有误"));
                return respBody;
            }
            if (org.springframework.util.StringUtils.isEmpty(paymentVo.getPayPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYPWD_NOT_NULL, "支付密码不能为空"));
                return respBody;
            }
            if (org.springframework.util.StringUtils.isEmpty(paymentVo.getMobile())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYEE_NOT_NULL, "收款人不能为空"));
                return respBody;
            }

            //获取当前登录用户
            AppUserPo appUserPo = commonService.checkToken();

            //判断支付密码是否正确
            String payPwd = CryptUtils.hmacSHA1Encrypt(paymentVo.getPayPwd(), appUserPo.getPayStal());
            if (!payPwd.equals(appUserPo.getPayPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYPWD_ERROR, "支付密码错误"));
                return respBody;
            }

            //根据用户手机号查询收款用户
            AppUserPo payeeUser = appUserService.findUserByMobile(paymentVo.getMobile());

            if (payeeUser == null || payeeUser.getUid().equals(10000)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYEE_INVAIL, "收款人不存在"));
                return respBody;
            }
            if (appUserPo.getId().equals(payeeUser.getId())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYEE_NOT_SELF, "收款人不能是自己"));
                return respBody;
            }
            if (StateEnum.DISABLE.getCode().equals(payeeUser.getState())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYEE_DISABLE, "收款人已被禁用"));
                return respBody;
            }
            if (appUserPo.getEpBalance().doubleValue() < Double.valueOf(paymentVo.getAmount())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.BALANCE_ERROR, "账户余额不足，请先兑换余额"));
                return respBody;
            }

            //处理转账业务
            walletService.payment(appUserPo, payeeUser, paymentVo.getAmount());

            respBody.add(RespCodeEnum.SUCCESS.getCode(), "转账成功");

        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.EXCHANGE_FAIL, "转账失败"));
            LogUtils.error("转账失败", ex);
        }
        return respBody;
    }

    /**
     * 候鸟积分转账
     *
     * @param paymentVo
     * @return
     */
    @PostMapping(value = "/birdtransfer")
    @SystemControllerLog(description = "候鸟积分转账")
    public RespBody birdtransfer(@RequestBody PaymentVo paymentVo) {

        RespBody respBody = new RespBody();
        try {
            Boolean flag = commonService.checkSign(paymentVo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            if (org.springframework.util.StringUtils.isEmpty(paymentVo.getAmount())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYEE_AMOUNT_NOT_NULL, "收款金额不能为空"));
                return respBody;
            }
            if ((new BigDecimal(paymentVo.getAmount())).compareTo(BigDecimal.ZERO) <= 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PARAM_ERROR, "参数有误"));
                return respBody;
            }
            if (org.springframework.util.StringUtils.isEmpty(paymentVo.getPayPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYPWD_NOT_NULL, "支付密码不能为空"));
                return respBody;
            }
            if (org.springframework.util.StringUtils.isEmpty(paymentVo.getMobile())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYEE_NOT_NULL, "收款人不能为空"));
                return respBody;
            }

            //获取当前登录用户
            AppUserPo appUserPo = commonService.checkToken();


            //判断支付密码是否正确
            String payPwd = CryptUtils.hmacSHA1Encrypt(paymentVo.getPayPwd(), appUserPo.getPayStal());
            if (!payPwd.equals(appUserPo.getPayPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYPWD_ERROR, "支付密码错误"));
                return respBody;
            }

            //根据用户手机号查询收款用户
            AppUserPo payeeUser = appUserService.findUserByMobile(paymentVo.getMobile());

            if (payeeUser == null || payeeUser.getUid().equals(10000)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYEE_INVAIL, "收款人不存在"));
                return respBody;
            }
            if (appUserPo.getId().equals(payeeUser.getId())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYEE_NOT_SELF, "收款人不能是自己"));
                return respBody;
            }
            if (StateEnum.DISABLE.getCode().equals(payeeUser.getState())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYEE_DISABLE, "收款人已被禁用"));
                return respBody;
            }
            if (appUserPo.getBirdScore().doubleValue() < Double.valueOf(paymentVo.getAmount())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.BIRDSCORE_LESS, "候鸟积分不足"));
                return respBody;
            }

            //判断当前用户是否允许转让候鸟积分
            if (IsAllowedEnum.YES.getCode().intValue() != payeeUser.getIsAllowed().intValue()) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.BIRDSCORE_ISALLOWED, "不允许转让候鸟积分"));
                return respBody;
            }
            //处理转账业务
            walletService.birdtransfer(appUserPo, payeeUser, paymentVo.getAmount());

            respBody.add(RespCodeEnum.SUCCESS.getCode(), "转账成功");


        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.EXCHANGE_FAIL, "转账失败"));
            LogUtils.error("转账失败", ex);
        }
        return respBody;
    }

    /**
     * Ep兑换
     *
     * @param vo
     * @return
     */
    @PostMapping(value = "/epexchange")
    @SystemControllerLog(description = "Ep兑换")
    public RespBody payment(@RequestBody EpExchangeVo vo) {

        RespBody respBody = new RespBody();
        try {
            Boolean flag = commonService.checkSign(vo);
            if (!flag){
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            if (org.springframework.util.StringUtils.isEmpty(vo.getAmount())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.EP_EXCHANAGE_AMOUNT_NOT_NULL, "ep兑换金额不能为空"));
                return respBody;
            }
            BigDecimal min = new BigDecimal(commonService.findParameter("exchangeMinAmount"));
            BigDecimal max = new BigDecimal(commonService.findParameter("exchangeMaxAmount"));
            if (vo.getAmount().compareTo(BigDecimal.ZERO) <= 0 || vo.getAmount().compareTo(min) < 0 || vo.getAmount().compareTo(max) > 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PARAM_ERROR, "参数有误"));
                return respBody;
            }
            if (org.springframework.util.StringUtils.isEmpty(vo.getPayPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYPWD_NOT_NULL, "支付密码不能为空"));
                return respBody;
            }

            //获取当前登录用户
            AppUserPo appUserPo = commonService.checkToken();

            //判断支付密码是否正确
            String payPwd = CryptUtils.hmacSHA1Encrypt(vo.getPayPwd(), appUserPo.getPayStal());
            if (!payPwd.equals(appUserPo.getPayPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYPWD_ERROR, "支付密码错误"));
                return respBody;
            }

            if (appUserPo.getEpBalance().compareTo(vo.getAmount()) < 1) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.BALANCE_ERROR, "账户EP余额不足"));
                return respBody;
            }
            Boolean f = walletService.epExchange(appUserPo.getId(), vo.getAmount());
            if (f) {
                respBody.add(RespCodeEnum.SUCCESS.getCode(), msgUtil.getMsg(AppMessage.EXCHANGE_SUCCESS, "兑换成功"));
            } else {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.EXCHANGE_FAIL, "兑换失败"));
            }
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.EXCHANGE_FAIL, "兑换失败"));
            LogUtils.error("兑换失败", ex);
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
                list = billRecordService.findBillRecordList(appUserPo.getId(), busnessTypeList, currencyType, paging);
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
     * 根据token获取每日释放金额
     *
     * @return
     */
    @GetMapping("/sign")
    @SystemControllerLog(description = "每日释放金额")
//    @RepeatControllerLog
    public RespBody openRedPacket() {
        RespBody respBody = new RespBody();
        try {
            AppUserPo appUserPo = commonService.checkToken();
            //今天已释放过的用户不再触发
            if (appUserPo.getReleaseTime() != null) {
                //获取今天的日期(YYYYMMDD)
                Integer d1 = DateUtils.getYearMonthDay(Calendar.getInstance());
                //用户释放的日期(YYYYMMDD)
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(appUserPo.getReleaseTime());
                Integer d2 = DateUtils.getYearMonthDay(calendar);
                //比较两者的大小
                if (d1.intValue() == d2.intValue()) {
                    //返回特殊编码，告诉前端此用户已签到过了
                    respBody.add("8888", "该用户已领取过释放金额");
                    return respBody;
                }
            }
            //用户今天签到的金额
            BigDecimal signAmout = appUserPo.getAssets();

            //根据用户id 查询A，B区业绩
            BigDecimal performance = new BigDecimal(0);
            AppUserContactPo appUserContactPo = appUserContactService.findUserByUserId(appUserPo.getId());
            performance = appUserContactPo.getPerformanceA();
            if (performance.compareTo(appUserContactPo.getPerformanceB()) > 0) {
                //如果A区业绩大于B区业绩，那就将B区业绩赋值到业绩中
                performance = appUserContactPo.getPerformanceB();
            }

            //判断每日释放比例
            AppPerformanceParamPo paramPo = appPerformanceParamService.findOneAppPerformanceParamPo(performance, 20);
            BigDecimal releaseParam = paramPo.getRate();

            //获取每日E资产释放到ep余额中的比例
            String param1 = commonService.findParameter("ereleaseToEP");
            BigDecimal epParam = new BigDecimal(param1);
            //获取每日E资产释放到候鸟积分中的比例
            String param2 = commonService.findParameter("ereleaseToBirdScore");
            BigDecimal birdParam = new BigDecimal(param2);

            //计算释放E资产数量
            BigDecimal releaseAmount = signAmout.multiply(releaseParam);
            //判断每日释放的数字货币最少0.1
            if (new BigDecimal(0.1).compareTo(releaseAmount) >= 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "领取的金额不符合领取规则");
                return respBody;
            }

            //计算E资产释放到ep余额的数量
            BigDecimal epAmount = releaseAmount.multiply(epParam).setScale(4, BigDecimal.ROUND_HALF_EVEN);
            BigDecimal birdScore = releaseAmount.multiply(birdParam).setScale(4, BigDecimal.ROUND_HALF_EVEN);

            //保存用户签到信息
            walletService.openRedPacket(releaseAmount, epAmount, birdScore, appUserPo);
            Map<String, BigDecimal> map = new HashedMap();
            map.put("epBlance", epAmount);
            map.put("birdScore", birdScore);

            respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取成功", map);

        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "用户领取红包失败");
            LogUtils.error("用户领取红包失败！", ex);
        }
        return respBody;
    }

    /**
     * 用户流水记录
     */
    @GetMapping(value = "/performancerecord")
    public RespBody performancerecord(HttpServletRequest request, Paging paging, String area) {
        RespBody respBody = new RespBody();
        try {
            //根据用户id获取用户信息
            List<PerformanceRecordVo> list = Collections.emptyList();
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken();
            if (org.springframework.util.StringUtils.isEmpty(area)) {
                paging.setTotalCount(0);
                respBody.add(RespCodeEnum.ERROR.getCode(), AppMessage.PARAM_ERROR, "参数不合法");
            }
            //获取总记录数量
            int total =appPerformanceRecordService.performanceTotal(appUserPo.getId(), area);
            if (total > 0) {
                list = appPerformanceRecordService.performanceList(appUserPo.getId(), area, paging);
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
     *EP兑换E资产参数
     * @param paraName 参数名
     * @return 响应对象
     */
    @GetMapping("/epToEParameter")
    public RespBody findParameter(String paraName) {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {

            //根据用户id获取用户信息
            List<PerformanceRecordVo> list = null;
            //检验用户是否登录
            AppUserPo appUserPo = commonService.checkToken();
            AppUserContactPo contactPo=appUserContactService.findUserByUserId(appUserPo.getId());
            if (contactPo==null){
                respBody.add(RespCodeEnum.ERROR.getCode(),msgUtil.getMsg(AppMessage.PARAM_ERROR, "获取参数失败"));
            }
            BigDecimal performance=contactPo.getPerformanceA();
            if (performance.compareTo(contactPo.getPerformanceB())==1){
                performance=contactPo.getPerformanceB();
            }
            AppPerformanceParamPo paramPo = appPerformanceParamService.findOneAppPerformanceParamPo(performance, 10);
            BigDecimal releaseParam = paramPo.getRate();
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取成功", releaseParam);
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(),ex.getMessage());
            LogUtils.error(ex.getMessage(), ex);
        }catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "获取参数失败");
            LogUtils.error("获取参数失败！", ex);
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
            int total =appWithDrawService.drawRecordTotal(appUserPo.getId());
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
