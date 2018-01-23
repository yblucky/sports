package com.xlf.app.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.enums.StateEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.resp.RespBody;
import com.xlf.common.service.RedisService;
import com.xlf.common.util.CryptUtils;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.app.ActiveVo;
import com.xlf.common.vo.app.DelUserVo;
import com.xlf.common.vo.app.UserInfoVo;
import com.xlf.common.vo.app.UserVo;
import com.xlf.server.app.AppUserService;
import com.xlf.server.common.CommonService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/8/18.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private AppUserService userService;
    @Resource
    private RedisService redisService;
    @Resource
    private CommonService commonService;
    @Resource
    private LanguageUtil msgUtil;
    @Resource
    private HttpServletRequest request;

    @PostMapping("/register")
        @SystemControllerLog(description = "用户注册")
    public RespBody register(@RequestBody UserVo userVo) {
        RespBody respBody = new RespBody();
        try {
            AppUserPo appUserPo = commonService.checkToken();

            if (StringUtils.isEmpty(userVo.getMobile())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PHONE_NOT_NULL, "手机号不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(userVo.getLoginPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.LOGINPWD_NOT_NULL, "登录密码不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(userVo.getNickName())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.NICKNAME_NOT_NULL, "用户昵称不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(userVo.getPayPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYPWD_NOT_NULL, "支付密码不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(userVo.getSmsCode())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.VERIFY_NOT_NULL, "短信验证码不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(userVo.getAreaNum())) {
                userVo.setAreaNum("86");
            }
            if (StringUtils.isEmpty(userVo.getContactId())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.CONTACT_PERSON_NOT_NULL, "接点人不能为空"));
                return respBody;
            }

            //判断短信验证码是否正确
            String redisSmsCode = redisService.getString(userVo.getMobile());
            if (redisSmsCode == null && StringUtils.isEmpty(redisSmsCode)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.VERIFY_INVALID, "短信验证码已失效"));
                return respBody;
            }
            if (!redisSmsCode.equals(userVo.getSmsCode())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.VARIFY_FAIL, "短信验证码输入不正确"));
                return respBody;
            }
            Boolean flag = commonService.checkSign(userVo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }
            //判断当前昵称是否有人使用
            AppUserPo po = userService.findUserByNickName(userVo.getNickName());
            if (po != null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.NICKNAME_HAS_USE, "用户昵称已被使用"));
                return respBody;
            }

            //是否含有关键词
            int count = userService.findKeyWords(userVo.getNickName());
            if (count > 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.HAS_KEY_WORDS, "昵称含有关键词，请重新输入"));
                return respBody;
            }

            //推荐人id为当前登录用户，保存推荐人的唯一ID
            userVo.setParentId(appUserPo.getId());
            //判断用户层数
            userVo.setLevel(appUserPo.getLevel().intValue() + 1);

            AppUserPo contactUser = userService.findUserById(userVo.getContactId());
            if (contactUser == null) {
                //根据父节点手机号查询
                contactUser = userService.findUserByMobile(userVo.getContactId());
            }
            /*if (parentUser == null || StateEnum.DISABLE.getCode().equals(parentUser.getState())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.INVITECODE_HAS_USE, "邀请人不存在或者已被禁用"));
                return respBody;
            }*/
            //新增业务：禁用用户可以作为邀请人
            if (contactUser == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.INVITECODE_HAS_USE, "接点人不存在"));
                return respBody;
            }

            //保存接点人唯一id
            userVo.setContactId(contactUser.getId());

            //判断用户是否存在
            AppUserPo Po = userService.findUserByMobile(userVo.getMobile());

            if (Po == null) {
                //新增用户
                if (userService.add(userVo)) {
                    AppUserPo result = userService.findUserByMobile(userVo.getMobile());
                    respBody.add(RespCodeEnum.SUCCESS.getCode(), "用户信息保存成功", result);
                } else {
                    respBody.add(RespCodeEnum.ERROR.getCode(), "用户信息保存失败");
                }

            } else {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PHONE_HAS_USE, "该手机号已被注册"));
            }

        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
            LogUtils.error(ex.getMessage(), ex);
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "用户信息保存失败");
            LogUtils.error("用户信息保存失败！", ex);
        }
        return respBody;
    }


    @PostMapping("/active")
    @SystemControllerLog(description = "用户激活")
    public RespBody active(@RequestBody ActiveVo vo) {
        RespBody respBody = new RespBody();
        try {
            AppUserPo userPo = commonService.checkToken();

            if (StringUtils.isEmpty(vo.getMobile())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PHONE_NOT_NULL, "手机号不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(vo.getPayPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYPWD_NOT_NULL, "支付密码不能为空"));
                return respBody;
            }
            if (userPo.getActiveNo() <= 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.ACTIVENO_NOT_ENOUGH, "用户激活次数不足"));
                return respBody;
            }
            if (!userPo.getPayPwd().equals(CryptUtils.hmacSHA1Encrypt(vo.getPayPwd(), userPo.getPayStal()))) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PAYPWD_ERROR, "支付密码错误"));
                return respBody;
            }
            AppUserPo activeUserPo = userService.findUserByMobile(vo.getMobile());
            if (activeUserPo == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PHONE_NOT_EXIST, "该手机号不存在"));
                return respBody;
            }
            if (!StateEnum.NO_ACTIVE.getCode().equals(activeUserPo.getState())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.USER_IS_NOT_UNACTIVE, "用户不是未激活状态"));
                return respBody;
            }
            Boolean flag = commonService.checkSign(vo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }
            vo.setActiveUserId(activeUserPo.getId());
            userService.active(userPo, vo);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), msgUtil.getMsg(AppMessage.ACTIVE_SUCCESS, "激活成功"));
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.ACTIVE_FAILE, "激活失败"));
            LogUtils.error("激活失败！", ex);
        }
        return respBody;
    }

    @PostMapping("/login")
    @SystemControllerLog(description = "用户登录")
    public RespBody login(@RequestBody UserVo userVo) {
        RespBody respBody = new RespBody();
        try {

            if (StringUtils.isEmpty(userVo.getMobile())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.LOGINNAME_NOT_NULL, "登录名不能为空"));
                return respBody;
            }
            if (StringUtils.isEmpty(userVo.getLoginPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.LOGINPWD_NOT_NULL, "登录密码不能为空"));
                return respBody;
            }
            //判断图片   验证码是否正确
            if (StringUtils.isEmpty(userVo.getImgKey()) && StringUtils.isEmpty(userVo.getImgKeyValue())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.VERIFY_NOT_NULL_PICTRUE, "图片验证码不能为空"));
                return respBody;
            }
            String pictureCode = redisService.getString(userVo.getImgKey());
            if (pictureCode == null && StringUtils.isEmpty(pictureCode)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.VERIFY_INVALID_PICTRUE, "图片验证码已失效"));
                return respBody;
            }
            if (!userVo.getImgKeyValue().equals(pictureCode)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.VARIFY_FAIL_PICTRUE, "图片验证码输入错误"));
                return respBody;
            }

            Boolean signFlag = commonService.checkSign(userVo);
            if (!signFlag){
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            //根据手机查询用户信息
            AppUserPo appUserPo = userService.findUserByMobile(userVo.getMobile());
            if (appUserPo == null || StringUtils.isEmpty(appUserPo)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.USER_INVALID, "用户不存在"));
                return respBody;
            }

            if (StateEnum.DISABLE.getCode().equals(appUserPo.getState())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.USER_DISABLE, "用户已禁用"));
                return respBody;
            }

            if (StateEnum.NO_ACTIVE.getCode().equals(appUserPo.getState())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.USER_UNACTIVE, "用户未激活"));
                return respBody;
            }

            // 用户有效，对输入密码进行加密
            String loginPwd = CryptUtils.hmacSHA1Encrypt(userVo.getLoginPwd(), appUserPo.getPwdStal());
            // 验证密码是否正确
            if (!loginPwd.equals(appUserPo.getLoginPwd())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.LOGINPWD_FAIL, "登录密码错误"));
                return respBody;
            }

            //登录业务
            String token = userService.login(appUserPo);
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "登录成功", token);

        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.LOGIN_FAIL, "用户登录不成功"));
        }
        return respBody;
    }


    @GetMapping("/findUserInfoByMobile")
    @SystemControllerLog(description = "手机查询")
    public RespBody findUserInfoByMobile(String mobile) {
        RespBody respBody = new RespBody();
        try {

            if (StringUtils.isEmpty(mobile)) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.PHONE_NOT_NULL, "手机号不能为空"));
                return respBody;
            }
            AppUserPo appUserPo = userService.findUserByMobile(mobile);
            if (appUserPo==null){
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.USER_INVALID, "用户不存在"));
                return respBody;
            }
            UserInfoVo vo = new UserInfoVo();
            vo.setNickName(StringUtils.isEmpty(appUserPo.getNickName()) ? "" : appUserPo.getNickName());
            vo.setName(StringUtils.isEmpty(appUserPo.getName()) ? "" : appUserPo.getName());
            vo.setMobile(StringUtils.isEmpty(appUserPo.getMobile()) ? "" : appUserPo.getMobile());
            vo.setState(appUserPo.getState());
            vo.setIsAllowed(appUserPo.getIsAllowed());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "", vo);
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.REQUEST_INVALID, "请求失败"));
        }
        return respBody;
    }

    private boolean checkUserState(RespBody respBody, AppUserPo appUserPo) {
        if (appUserPo == null || StringUtils.isEmpty(appUserPo)) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.USER_INVALID, "用户不存在"));
            return true;
        }
        if (StateEnum.DISABLE.getCode().equals(appUserPo.getState())) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.USER_DISABLE, "用户已禁用"));
            return true;
        }
        if (StateEnum.NORMAL.getCode().equals(appUserPo.getState())) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.USER_DISABLE, "用户已激活"));
            return true;
        }
        return false;
    }

    /**
     * 删除账号
     *
     * @return
     */
    @PostMapping("/delUser")
    @SystemControllerLog(description = "删除用户")
    public RespBody delUser(@RequestBody DelUserVo vo) {
        RespBody respBody = new RespBody();
        try {
            AppUserPo appUserPo = commonService.checkToken();
            if (StringUtils.isEmpty(vo.getUserId())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.DELUSERID_NOT_NULL, "需要删除的用户ID不能为空"));
                return respBody;
            }
            AppUserPo delUserPo = userService.findUserById(vo.getUserId());

            //判断如果还是空的话就返回错误
            if (delUserPo == null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.USER_INVALID, "用户不存在"));
                return respBody;
            }

            //判断用户是否激活
            if (delUserPo.getState() != StateEnum.NO_ACTIVE.getCode().intValue()) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.DELUSER_IS_NOT_ACTIVE, "用户不是未激活状态，无法删除"));
                return respBody;
            }
            Boolean flag = commonService.checkSign(vo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }

            //进行删除
            int count = userService.delUser(vo.getUserId());
            if (count <= 0) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.DELUSER_FAILURE, "删除用户失败"));
                return respBody;
            }
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "删除用户成功");
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.DELUSER_FAILURE, "删除用户失败"));
        }
        return respBody;
    }


    /**
     * 根据token获取用户信息
     *
     * @return
     */
    @GetMapping("/findUserInfo")
    public RespBody findUserInfo() {
        RespBody respBody = new RespBody();
        try {
            AppUserPo appUserPo = commonService.checkToken();
            UserVo vo = new UserVo();
            UserInfoVo contactPo = userService.findUserByContactUserId(appUserPo.getId());
            contactPo.setName(appUserPo.getName());
            BeanUtils.copyProperties(vo, appUserPo);
            BeanUtils.copyProperties(vo, contactPo);
            vo.setIsAllowed(appUserPo.getIsAllowed());
            vo.setPayPwd("*");
            vo.setLoginPwd("*");
            vo.setPayStal("*");
            vo.setPayPwd("*");

            respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取成功", vo);
        } catch (CommException ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), ex.getMessage());
        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.LOGIN_FAIL, "用户登录不成功"));
        }
        return respBody;
    }


}
