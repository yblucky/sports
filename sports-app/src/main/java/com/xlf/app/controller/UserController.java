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
import com.xlf.common.vo.app.UserVo;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.AppUserService;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.SysUserService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户相关
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private AppUserService userService;
    @Resource
    private RedisService redisService;
    @Resource
    private CommonService commonService;
    @Resource
    private LanguageUtil msgUtil;

    @PostMapping("/register")
    @SystemControllerLog(description = "用户注册")
    public RespBody register(@RequestBody UserVo userVo) {
        RespBody respBody = new RespBody();
        try {
            //AppUserPo appUserPo = commonService.checkToken();

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

            if (StringUtils.isEmpty(userVo.getInviteMobile())) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.CONTACT_PERSON_NOT_NULL, "代理手機號不能为空"));
                return respBody;
            }


/*            Boolean flag = commonService.checkSign(userVo);
            if (!flag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }*/
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

            SysUserVo sysUserVo = sysUserService.findByMobile(userVo.getInviteMobile());
            if (sysUserVo == null) {
                //根据父节点手机号查询
                respBody.add(RespCodeEnum.ERROR.getCode(), "代理上级不存在");
                return respBody;
            }
            AppUserPo parentUser = userService.findUserByParentId(sysUserVo.getId());
            if (parentUser != null) {
                respBody.add(RespCodeEnum.ERROR.getCode(), "该代理已有下级会员");
                return respBody;
            }
            userVo.setParentId(sysUserVo.getId());

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

            //验签
/*            Boolean signFlag = commonService.checkSign(userVo);
            if (!signFlag) {
                respBody.add(RespCodeEnum.ERROR.getCode(), msgUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }*/

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
            BeanUtils.copyProperties(vo, appUserPo);
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

    /**
     * 退出登录
     * @return 响应对象
     */
    @PostMapping("/loginOut")
    @SystemControllerLog(description="退出登录")
    public RespBody loginOut(HttpServletRequest request) {
        RespBody respBody = new RespBody();
        try {
            AppUserPo appUserPo = commonService.checkToken();
            userService.LoginOut(appUserPo.getId());
            respBody.add(RespCodeEnum.SUCCESS.getCode(), "用户登出成功");
        } catch (Exception e) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "用户登出失败");
            LogUtils.error("用户登录出败！",e);
        }
        return respBody;
    }


    public static void main(String[] args) {
        String loginPwd = CryptUtils.hmacSHA1Encrypt("123456", "123456");
        System.out.println(loginPwd);
    }

}
