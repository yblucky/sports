package com.pf.app.controller;

import com.pf.common.annotation.SystemControllerLog;
import com.pf.common.enums.RespCodeEnum;
import com.pf.common.enums.StateEnum;
import com.pf.common.exception.CommException;
import com.pf.common.language.AppMessage;
import com.pf.common.po.AppBankCardPo;
import com.pf.common.po.AppBankTypePo;
import com.pf.common.po.AppUserPo;
import com.pf.common.resp.RespBody;
import com.pf.common.service.RedisService;
import com.pf.common.util.*;
import com.pf.common.vo.app.BankCardVo;
import com.pf.common.vo.app.UpdateUserVo;
import com.pf.common.vo.app.UserVo;
import com.pf.server.app.AppUserService;
import com.pf.server.app.SettingService;
import com.pf.server.common.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 设置模块控制器
 *
 * @author jay
 * @version v1.0
 * @date 2017年8月11日
 */
@RestController
@RequestMapping("/setting")
public class SettingController {
    @Resource
    private SettingService settingService;
    @Resource
    private AppUserService userService;
    @Resource
    private CommonService commonService;
    @Resource
    private ScpUtil scpUtil;
    @Resource
    private LanguageUtil languageUtil;
    @Resource
    private ConfUtils confUtils;
    @Resource
    private RedisService redisService;

    /**
     * 修改支付密码
     *
     * @param vo vo类型
     * @return 响应对象
     */
    @PostMapping("/user/modfiyPayPwd")
    @SystemControllerLog(description = "修改支付密码")
//    @RepeatControllerLog
    public RespBody modfiyPwd(@RequestBody UpdateUserVo vo) {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {

            //验签
            Boolean flag = commonService.checkSign(vo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            AppUserPo user = commonService.checkToken();
            //验证
            if (StringUtils.isEmpty(vo.getOldPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.OLDPAYPWD_NOT_NULL, "旧支付密码不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(vo.getNewPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.NEWPAYPWD_NOT_NULL, "新支付密码不能为空"));
                return respBody;
            }
            //根据盐加密旧登录密码，验证旧密码是否正确
            String oldPayPw = CryptUtils.hmacSHA1Encrypt(vo.getOldPwd(), user.getPayStal());
            if (!user.getPayPwd().equals(oldPayPw)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.OLDPAYPWD_ERROR, "旧支付密码错误"));
                return respBody;
            }
            //验证通过，更改支付密码
            AppUserPo model = new AppUserPo();
            //每次更新密码都要刷新盐
            String salt = ToolUtils.getUUID();
            //根据新盐加密支付密码
            String payPw = CryptUtils.hmacSHA1Encrypt(vo.getNewPwd(), salt);
            model.setPayPwd(payPw);
            model.setPayStal(salt);
            //调用通用的更新方法
            userService.updateById(model, user.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), languageUtil.getMsg(AppMessage.MODIFY_PAY_PASS_SUCCEE, "修改支付密码成功"));
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error("修改支付密码失败！", ex);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.MODIFY_PAY_PASS_FAIL, "修改支付密码失败"));
            LogUtils.error("修改支付密码失败！", ex);
        }
        return respBody;
    }

    /**
     * 修改登录密码
     *
     * @param vo vo类型
     * @return 响应对象
     */
    @PostMapping("/user/modfiyLoginPwd")
    @SystemControllerLog(description = "修改登录密码")
//    @RepeatControllerLog
    public RespBody modfiyLoginPwd(@RequestBody UpdateUserVo vo) {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {
            //验签
            Boolean flag = commonService.checkSign(vo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            AppUserPo user = commonService.checkToken();
            //验证
            if (StringUtils.isEmpty(vo.getOldLoginPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.OLDLOGINPWD_NOT_NULL, "旧登录密码不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(vo.getNewLoginPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.NEWLOGINPWD_NOT_NULL, "新登录密码不能为空"));
                return respBody;
            }
            //根据盐加密旧登录密码，验证旧密码是否正确
            String oldLoginPw = CryptUtils.hmacSHA1Encrypt(vo.getOldLoginPwd(), user.getPwdStal());
            if (!user.getLoginPwd().equals(oldLoginPw)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.OLD_LOGINPWD_ERROR, "旧登录密码错误"));
                return respBody;
            }
            //验证通过，更改登录密码
            AppUserPo model = new AppUserPo();
            //每次更新密码都要刷新盐
            String salt = ToolUtils.getUUID();
            //根据新盐加密登录密码
            String loginPw = CryptUtils.hmacSHA1Encrypt(vo.getNewLoginPwd(), salt);
            model.setLoginPwd(loginPw);
            model.setPwdStal(salt);
            //调用通用的更新方法
            userService.updateById(model, user.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), languageUtil.getMsg(AppMessage.MODIFY_LOGIN_PASS_SUCCESS, "修改登录密码成功"));
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error("修改登录密码失败！", ex);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.MODIFY_LOGIN_PASS_FAIL, "修改登录密码失败"));
            LogUtils.error("修改登录密码失败！", ex);
        }
        return respBody;
    }

    /**
     * 修改昵称
     *
     * @param vo vo类型
     * @return 响应对象
     */
    @PostMapping("/user/upNickName")
    @SystemControllerLog(description = "修改昵称")
//    @RepeatControllerLog
    public RespBody upNickName(@RequestBody UpdateUserVo vo) {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {
            //验签
            Boolean flag = commonService.checkSign(vo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            AppUserPo user = commonService.checkToken();
            //验证
            if (StringUtils.isEmpty(vo.getNickName())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.NICKNAME_NOT_NULL, "昵称不能为空"));
                return respBody;
            }
            if (ToolUtils.containsEmoji(vo.getNickName())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.NICKNAME_EMOJI_ERROR, "昵称不能含有emoji表情"));
                return respBody;
            }
            //判断当前昵称是否有人使用
            AppUserPo po = userService.findUserByNickName(vo.getNickName());
            if (!user.getNickName().equals(vo.getNickName()) && po != null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.NICKNAME_HAS_USE, "用户昵称已被使用"));
                return respBody;
            }
            //是否含有关键词
            int count = userService.findKeyWords(vo.getNickName());
            if (count > 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.HAS_KEY_WORDS, "昵称含有关键词，请重新输入"));
                return respBody;
            }
            //验证通过，更改昵称
            AppUserPo model = new AppUserPo();
            model.setNickName(vo.getNickName());
            //调用通用的更新方法
            userService.updateById(model, user.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), languageUtil.getMsg(AppMessage.MODIFY_NICKNAME_SUCCESS, "修改昵称成功"));
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error("修改昵称失败！", ex);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.MODIFY_NICKNAME_SUCCESS_FAIL, "修改昵称失败"));
            LogUtils.error("修改昵称失败！", ex);
        }
        return respBody;
    }

    /**
     * 更换头像
     *
     * @param vo vo类型
     * @return 响应对象
     */
    @PostMapping("/user/upImg")
    @SystemControllerLog(description = "更换头像")
//    @RepeatControllerLog
    public RespBody upImg(@RequestBody UpdateUserVo vo) {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {
            //验签
            Boolean flag = commonService.checkSign(vo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            AppUserPo user = commonService.checkToken();
            //验证
            if (StringUtils.isEmpty(vo.getImgPath())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.HEADIMG_NOT_NULL, "头像不能为空"));
                return respBody;
            }
            String imgName = ToolUtils.getUUID() + ".jpg";
            //转换图片
            String img = vo.getImgPath().substring(vo.getImgPath().indexOf(";base64,") + 8);
            //上传图片
            scpUtil.uploadFile(Base64.getDecoder().decode(img), scpUtil.getRemoteRootDir() + "/headImg", imgName);
            //已经上传的头像要删除掉
            if (!StringUtils.isEmpty(user.getImgPath())) {
                scpUtil.delFile(scpUtil.getRemoteRootDir() + "/" + user.getImgPath());
            }
            //验证通过，更换头像
            AppUserPo model = new AppUserPo();
            model.setImgPath("headImg/" + imgName);
            //调用通用的更新方法
            userService.updateById(model, user.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "更换头像成功");
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error("更换头像失败！", ex);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "更换头像失败");
            LogUtils.error("更换头像失败！", ex);
        }
        return respBody;
    }

    /**
     * 查询用户所有银行卡
     *
     * @return 响应对象
     */
    @GetMapping("/bank/findAll")
    @SystemControllerLog(description = "查询用户所有银行卡")
    public RespBody findAlluserBankCard() {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {
            AppUserPo user = commonService.checkToken();
            List<AppBankCardPo> list = settingService.findAll(user.getId());
            BankCardVo cardVo = null;
            AppBankTypePo typePo = null;
            List<BankCardVo> rsList = new ArrayList<BankCardVo>();
            for (AppBankCardPo po : list) {
                cardVo = MyBeanUtils.copyProperties(po, BankCardVo.class);
                typePo = settingService.findBankType(po.getBankTypeId());
                cardVo.setLogo(typePo.getLogo());
                cardVo.setBankName(typePo.getBankName());
                cardVo.setColor(typePo.getColor());
                rsList.add(cardVo);
            }
            respBody.add(RespCodeEnum.SUCCESS.getCode(), languageUtil.getMsg(AppMessage.QUERY_USER_BANK_CARD_LIST_SUCCESS, "查询用户所有银行卡成功"), rsList);
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error("查询用户所有银行卡失败！", ex);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.QUERY_USER_BANK_CARD_LIST_FAIL, "查询用户所有银行卡失败"));
            LogUtils.error("查询用户所有银行卡失败！", ex);
        }
        return respBody;
    }

    /**
     * 获取用户默认银行卡
     *
     * @return 响应对象
     */
    @GetMapping("/bank/defualt")
    @SystemControllerLog(description = "获取用户默认银行卡")
    public RespBody userDefualtBankCard() {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {
            AppUserPo user = commonService.checkToken();
            AppBankCardPo card = settingService.findUserDefualtCard(user.getId());
            if (card == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "用户未绑定银行卡");
                return respBody;
            }
            BankCardVo cardVo = MyBeanUtils.copyProperties(card, BankCardVo.class);
            AppBankTypePo typePo = settingService.findBankType(card.getBankTypeId());
            cardVo.setLogo(typePo.getLogo());
            cardVo.setBankName(typePo.getBankName());
            cardVo.setColor(typePo.getColor());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), languageUtil.getMsg(AppMessage.QUERY_USER_DEFAULT_BANK_CARD_SUCCESS, "获取用户默认银行卡成功"), cardVo);
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error("获取用户默认银行卡失败！", ex);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.QUERY_USER_DEFAULT_BANK_CARD_FAIL, "获取用户默认银行卡失败"));
            LogUtils.error("获取用户默认银行卡失败！", ex);
        }
        return respBody;
    }

    /**
     * 获取所有开户银行类型
     *
     * @return 响应对象
     */
    @GetMapping("/bank/typeList")
    @SystemControllerLog(description = "银行卡类型列表")
    public RespBody typeList() {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载银行卡类型列表成功", settingService.typeList());
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error("加载银行卡类型列表失败！", ex);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "加载银行卡类型列表失败");
            LogUtils.error("加载银行卡类型列表失败！", ex);
        }
        return respBody;
    }

    /**
     * 新增银行卡
     *
     * @return 响应对象
     */
    @PostMapping("/bank/add")
    @SystemControllerLog(description = "新增银行卡")
//    @RepeatControllerLog
    public RespBody addBankCard(HttpServletRequest request, @RequestBody BankCardVo cardVo) {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {
            //验签

            Boolean flag = commonService.checkSign(cardVo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }


            AppUserPo user = commonService.checkToken();
            if (StringUtils.isEmpty(cardVo.getBankCard())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.BANKCARD_NOT_NULL, "银行卡号不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(cardVo.getBankTypeId())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.BRANCH_NOT_NULL, "开户银行不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(cardVo.getName())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.USERNAME_NOT_NULL, "持卡人姓名不能为空"));
                return respBody;
            }
            if (ToolUtils.containsEmoji(cardVo.getName())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.USERNAME_EMOJI_ERROR, "持卡人姓名不能含有emoji表情"));
                return respBody;
            }
            if (ToolUtils.containsEmoji(cardVo.getBranch())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.BRANCH_EMOJI_ERROR, "开户支行不能含有emoji表情"));
                return respBody;
            }
            //设置银行卡绑定者id
            cardVo.setUserId(user.getId());
            //调用dao新增用户银行卡，true为新增动作
            settingService.callBankCard(cardVo, true);
            if (StringUtils.isEmpty(user.getName())) {
                String token = request.getHeader("token");
                if (null == token) {
                    token = request.getParameter("token");
                }
                AppUserPo updateModel = new AppUserPo();
                updateModel.setName(cardVo.getName());
                userService.updateById(updateModel, user.getId());
                AppUserPo po = userService.findUserById(user.getId());
                redisService.putObj(token, po, confUtils.getSessionTimeout());

            }
            respBody.add(RespCodeEnum.SUCCESS.getCode(), languageUtil.getMsg(AppMessage.ADD_BANK_CARD_SUCCESS, "新增银行卡成功"));
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error("新增银行卡失败！", ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.ADD_BANK_CARD_FAIL, "新增银行卡失败"));
            LogUtils.error("新增银行卡失败！", ex);
        }
        return respBody;
    }

    /**
     * 设置默认银行卡
     *
     * @return 响应对象
     */
    @PostMapping("/bank/defaultBankCard")
    @SystemControllerLog(description = "设置默认银行卡")
//    @RepeatControllerLog
    public RespBody defaultBankCard(@RequestBody BankCardVo cardVo) {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {
            //验签
            Boolean flag = commonService.checkSign(cardVo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            AppUserPo user = commonService.checkToken();
            if (StringUtils.isEmpty(cardVo.getId())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.NOT_SELECT_UPDBANK, "未选择要修改的银行卡"));
                return respBody;
            }
            //调用dao修改用户银行卡，false为修改动作
            settingService.defaultBankCard(cardVo.getId(), user.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), languageUtil.getMsg(AppMessage.SET_DEFAULT_CARD_SUCCESS, "设置默认银行卡成功"));
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error("修改银行卡失败！", ex);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.SET_DEFAULT_CARD_FAIL, "设置默认银行卡失败"));
            LogUtils.error("修改银行卡失败！", ex);
        }
        return respBody;
    }

    /**
     * 修改银行卡
     *
     * @return 响应对象
     */
    @PostMapping("/bank/update")
    @SystemControllerLog(description = "修改银行卡")
//    @RepeatControllerLog
    public RespBody updateBankCard(@RequestBody BankCardVo cardVo) {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {
            //验签
            Boolean flag = commonService.checkSign(cardVo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            AppUserPo user = commonService.checkToken();
            if (StringUtils.isEmpty(cardVo.getId())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.NOT_SELECT_UPDBANK, "未选择要修改的银行卡"));
                return respBody;
            }
            if (StringUtils.isEmpty(cardVo.getBankCard())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.BANKCARD_NOT_NULL, "银行卡号不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(cardVo.getBankTypeId())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.BRANCH_NOT_NULL, "开户银行不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(cardVo.getName())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.USERNAME_NOT_NULL, "持卡人姓名不能为空"));
                return respBody;
            }
            if (ToolUtils.containsEmoji(cardVo.getName())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.USERNAME_EMOJI_ERROR, "持卡人姓名不能含有emoji表情"));
                return respBody;
            }
            if (ToolUtils.containsEmoji(cardVo.getBranch())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.BRANCH_EMOJI_ERROR, "开户支行不能含有emoji表情"));
                return respBody;
            }
            //设置银行卡绑定者id
            cardVo.setUserId(user.getId());
            //调用dao修改用户银行卡，false为修改动作
            settingService.callBankCard(cardVo, false);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), languageUtil.getMsg(AppMessage.UPDATE_BANK_CARD_SUCCESS, "修改银行卡成功"));
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error("修改银行卡失败！", ex);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.UPDATE_BANK_CARD_FAIL, "修改银行卡失败"));
            LogUtils.error("修改银行卡失败！", ex);
        }
        return respBody;
    }

    /**
     * 删除银行卡
     *
     * @return 响应对象
     */
    @PostMapping("/bank/del")
    @SystemControllerLog(description = "删除银行卡")
//    @RepeatControllerLog
    public RespBody delBankCard(@RequestBody AppBankCardPo vo) {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {
            if (vo == null || StringUtils.isEmpty(vo.getId())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.NOT_SELECT_DELBANK, "未选择要删除的银行卡"));
                return respBody;
            }

            //进行验签
            Map<String, String> signMap = new HashMap<>();
            signMap.put("id", vo.getId());
            //验签
            Boolean flag = commonService.checkSign(vo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            AppUserPo user = commonService.checkToken();

            //调用dao删除银行卡
            settingService.deleteBankCardById(vo.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), languageUtil.getMsg(AppMessage.DELETE_BANK_CARD_SUCCESS, "删除银行卡成功"));
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error("删除银行卡失败！", ex);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.DELETE_BANK_CARD_FAIL, "删除银行卡失败"));
            LogUtils.error("删除银行卡失败！", ex);
        }
        return respBody;
    }

    /**
     * 找回支付密码
     *
     * @param userVo 接收Vo
     * @return 响应对象
     */
    @PostMapping("/user/backPayPwd")
    @SystemControllerLog(description = "找回支付密码")
//    @RepeatControllerLog
    public RespBody backPayPwd(@RequestBody UserVo userVo) {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {
            //验签
            Boolean flag = commonService.checkSign(userVo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            AppUserPo appUserPo = commonService.checkToken();
            //验证
            if (StringUtils.isEmpty(userVo.getPayPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.NEW_PWD_NOT_NULL, "新密码不能为空！"));
                return respBody;
            }
            if (StringUtils.isEmpty(userVo.getSmsCode())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.VERIFY_NOT_NULL, "验证码不能为空！"));
                return respBody;
            }
            if (StringUtils.isEmpty(userVo.getMobile())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.PHONE_NOT_NULL, "手机号码不能为空！"));
                return respBody;
            }
            if (!appUserPo.getMobile().equals(userVo.getMobile())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.TWO_PHONE_EQUAL_FAIL, "输入的手机号码与绑定的手机号不一致！"));
                return respBody;
            }
            String smsCode = commonService.findCode(userVo.getMobile());
            if (StringUtils.isEmpty(smsCode) || !userVo.getSmsCode().equals(smsCode)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.VERIFY_ERROR, "验证码失效或输入验证码错误！"));
                return respBody;
            }
            //验证通过，更改支付密码
            AppUserPo model = new AppUserPo();
            //每次更新密码都要刷新盐
            String salt = ToolUtils.getUUID();
            //根据新盐加密登录密码
            String payPw = CryptUtils.hmacSHA1Encrypt(userVo.getPayPwd(), salt);
            model.setPayPwd(payPw);
            model.setPayStal(salt);
            //调用通用的更新方法
            userService.updateById(model, appUserPo.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), languageUtil.getMsg(AppMessage.RETRIEVE_PASS_SUCCESS, "找回支付密码成功"));
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error(ex.getMessage(), ex);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.RETRIEVE_PASS_FAIL, "找回支付密码失败"));
            LogUtils.error("找回支付密码失败！", ex);
        }
        return respBody;
    }

    /**
     * 找回密码
     *
     * @param userVo 接收Vo
     * @return 响应对象
     */
    @PostMapping("/backLoginPwd")
    @SystemControllerLog(description = "找回登录密码")
    public RespBody forgetPwd(@RequestBody UserVo userVo) {
        // 创建返回对象
        RespBody respBody = new RespBody();
        try {
            //验证FormBean
            if (forgetHasError(userVo, respBody)) {

                //验签
                Boolean flag = commonService.checkSign(userVo);
                if (!flag) {
                    respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                    return respBody;
                }

                AppUserPo appUserPo = userService.findUserByMobile(userVo.getMobile());
                if (appUserPo == null || org.springframework.util.StringUtils.isEmpty(appUserPo)) {
                    respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.USER_INVALID, "用户不存在"));
                    return respBody;
                }
                if (StateEnum.DISABLE.getCode().equals(appUserPo.getState())) {
                    respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.USER_NOT_UPPWD, "用户已被禁用不能修改密码"));
                    return respBody;
                }
                String smsCode = commonService.findCode(appUserPo.getMobile());
                if (org.springframework.util.StringUtils.isEmpty(smsCode) || !userVo.getSmsCode().equals(smsCode)) {
                    respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.VERIFY_ERROR, "验证码失效或输入验证码错误！"));
                    return respBody;
                }
                //验证通过，更改登录密码
                AppUserPo model = new AppUserPo();
                //每次更新密码都要刷新盐
                String salt = ToolUtils.getUUID();
                //根据新盐加密登录密码
                String loginPw = CryptUtils.hmacSHA1Encrypt(userVo.getLoginPwd(), salt);
                model.setLoginPwd(loginPw);
                model.setPwdStal(salt);
                //调用通用的更新方法
                userService.updateById(model, appUserPo.getId());
                respBody.add(RespCodeEnum.SUCCESS.getCode(), languageUtil.getMsg(AppMessage.RETRIEVE_PASS_SUCCESS, "找回密码成功"));
            }
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.RETRIEVE_PASS_FAIL, "找回密码失败"));
            LogUtils.error("找回密码失败！", ex);
        }
        return respBody;
    }

    /**
     * 找回登录密码业务验证输入参数
     */
    private boolean forgetHasError(UserVo userVo, RespBody respBody) {
        if (org.springframework.util.StringUtils.isEmpty(userVo.getMobile())) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.UID_PHONE_NOT_NULL, "用户UID或手机号不能为空！"));
            return false;
        }
        if (org.springframework.util.StringUtils.isEmpty(userVo.getLoginPwd())) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.NEW_PWD_NOT_NULL, "新密码不能为空！"));
            return false;
        }
        if (org.springframework.util.StringUtils.isEmpty(userVo.getSmsCode())) {
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.VERIFY_NOT_NULL, "验证码不能为空！"));
            return false;
        }
        return true;
    }

}
