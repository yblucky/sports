package com.xlf.app.controller;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.BetTypeEnum;
import com.xlf.common.enums.OddsEnum;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.resp.RespBody;
import com.xlf.common.service.RedisService;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.*;
import com.xlf.server.common.CommonService;
import com.xlf.server.common.KaptchaService;
import com.xlf.server.web.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用控制器
 *
 * @author qsy
 * @version v1.0
 * @date 2017年8月16日
 */
@RestController
@RequestMapping("/common")
public class CommonController {
    @Resource
    private RedisService redisService;
    @Resource
    private CommonService commonService;
    @Resource
    private AppUserService appUserService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysAgentSettingService sysAgentSettingService;
    @Resource
    private AppTimeBettingService appTimeBettingService;
    @Resource
    private AppRacingBettingService appRacingBettingService;
    @Resource
    private AppTimeLotteryService appTimeLotteryService;
    @Resource
    private KaptchaService kaptchaService;


    /**
     * 加载图片验证码
     *
     * @return
     */
    @GetMapping("/loadImgCode")
    public RespBody loadImgCode() {
        // 创建返回对象
        RespBody respBody = new RespBody ();
        try {
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取图片验证码成功", kaptchaService.createCodeImg ());
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "获取图片验证码失败");
            LogUtils.error ("用户登录失败！", ex);
        }
        return respBody;
    }


    /**
     * 获取手机验证码
     *
     * @param mobile 手机
     * @return 响应对象
     */
    @GetMapping("/registerSms")
    public RespBody registerSms(String mobile, String areaNum) {
        // 创建返回对象
        RespBody respBody = new RespBody ();
        try {
            //验证
            if (hasMobileErrors (mobile, respBody)) {
                if (StringUtils.isEmpty (areaNum)) {
                    //默认是中国区号
                    areaNum = "86";
                }
                //根据手机号获取用户信息
                AppUserPo appUserPo = appUserService.findUserByMobile (mobile);
                if (appUserPo != null) {
                    respBody.add (RespCodeEnum.ERROR.getCode (), "手机号已被注册");
                    return respBody;
                }
                commonService.sendSms (mobile, areaNum);
            }
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "获取手机验证码失败");
            LogUtils.error ("获取手机验证码失败！", ex);
        }
        String code = redisService.getString (mobile);
        respBody.add ("code", code);
        return respBody;
    }


    /**
     * 获取手机验证码,使用于找回密码
     *
     * @param mobile 手机
     * @return 响应对象
     */
    @GetMapping("/sendSmsByMobile")
    public RespBody sendSmsByMobile(String mobile) {
        // 创建返回对象
        RespBody respBody = new RespBody ();
        try {

            //进行验签
            Map<String, String> signMap = new HashMap<> ();
            signMap.put ("mobile", mobile);
            //验签
          /*  Boolean flag1 = commonService.checkSignByGet(signMap);
            if (!flag1) {
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.INVALID_SIGN, "无效签名"));
                return respBody;
            }*/

            // 验证
            if (StringUtils.isEmpty (mobile)) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "手机号不能为空");
                return respBody;
            }
            // 先获取手机号
            AppUserPo appUserPo = appUserService.findUserByMobile (mobile);
            // 判断手机号是否存在
            if (appUserPo == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "该用户不存在");
                return respBody;
            }
            // 验证通过，调用业务层
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "获取手机验证码失败");
            LogUtils.error ("获取手机验证码失败！", ex);
        }
        return respBody;
    }

    /**
     * 验证提交数据
     *
     * @param mobile
     */
    private boolean hasMobileErrors(String mobile, RespBody respBody) {
        if (StringUtils.isEmpty (mobile)) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "手机号不能为空");
            return false;
        }
        String reg = "^\\d+$";
        if (!mobile.matches (reg)) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "请输入正确的手机号码");
            return false;
        }
        return true;
    }

    /**
     * 根据参数名获取参数值
     *
     * @param paraName 参数名
     * @return 响应对象
     */
    @GetMapping("/findParameter")
    public RespBody findParameter(String paraName) {
        // 创建返回对象
        RespBody respBody = new RespBody ();
        try {
            //验证
            if (StringUtils.isEmpty (paraName)) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "参数名不能为空");
                return respBody;
            }
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取参数成功", commonService.findParameter (paraName));
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "获取参数失败");
            LogUtils.error ("获取参数失败！", ex);
        }
        return respBody;
    }

    /**
     * 根据参数名获取参数值
     *
     * @return 响应对象
     */
    @GetMapping("/loadOdds")
    public RespBody loadTimeLotteryOdds() {
        // 创建返回对象
        RespBody respBody = new RespBody ();
        try {
            Map<String, String> resultMap = new HashMap<> ();
            resultMap.put ("timelotteryodds_1", commonService.findParameter (OddsEnum.TIMELOTTERYODDS_1.getEgName ()));
            resultMap.put ("timelotteryodds_2", commonService.findParameter (OddsEnum.TIMELOTTERYODDS_2.getEgName ()));
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取参数成功", resultMap);
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "获取参数失败");
            LogUtils.error ("获取参数失败！", ex);
        }
        return respBody;
    }

    /**
     * 获取服务器时间戳
     *
     * @return 响应对象
     */
    @GetMapping("/timestamp")
    public RespBody timestamp() {
        // 创建返回对象
        RespBody respBody = new RespBody ();
        try {
            Long timeStamp = System.currentTimeMillis ();
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取成功", timeStamp);
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "获取时间戳失败");
            LogUtils.error ("获取时间戳失败！", ex);
        }
        return respBody;
    }

    /**
     * 获取服务器时间戳
     *
     * @return 响应对象
     */
    @GetMapping("/test")
    public RespBody test(String id) {
        // 创建返回对象
        RespBody respBody = new RespBody ();
        try {
            AppTimeBettingPo model = appTimeBettingService.findById (id);
            Boolean f = appTimeLotteryService.timeLotteryHandleService (model);
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "SUCCESS", f);
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "error");
            LogUtils.error ("error！", ex);
        }
        return respBody;
    }


    @GetMapping("/regex")
    @SystemControllerLog(description = "投注规则限制")
    public RespBody timeInfo(HttpServletRequest request) throws Exception {
        RespBody respBody = new RespBody ();
        try {
            AppUserPo userPo = commonService.checkToken ();
            SysUserVo sysUserVo = sysUserService.findById (userPo.getParentId ());
            SysAgentSettingPo agentSettingPo = sysAgentSettingService.findById (sysUserVo.getAgentLevelId ());
            if (agentSettingPo == null) {
                respBody.add (RespCodeEnum.ERROR.getCode (), "获取代理设置参数有误");
                return respBody;
            }
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取投注规则成功!", agentSettingPo);
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "获取投注规则失败!");
            LogUtils.error ("获取投注规则失败！", ex);
        }
        return respBody;
    }

    /**
     * 获取公告
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/loadNotice")
    public RespBody loadNotice(HttpServletRequest request,String id) throws Exception {
        RespBody respBody = new RespBody ();
        try {
            String notice = commonService.findParameter("timeNotice");
            respBody.add (RespCodeEnum.SUCCESS.getCode (), "获取时时彩公告成功!", notice);
        } catch (Exception ex) {
            respBody.add (RespCodeEnum.ERROR.getCode (), "获取时时彩公告失败!");
            LogUtils.error ("获取时时彩公告失败！", ex);
        }
        return respBody;
    }


}
