package com.xlf.server.interceptor;

import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.resp.RespBody;
import com.xlf.common.service.RedisService;
import com.xlf.common.util.ConfUtils;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.LogUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.common.CommonService;
import com.xlf.server.common.SysUrlRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * 请求拦截器，校验用户是否登录
 * @author qsy
 * @version v1.0
 * @date 2016年11月28日
 */
public class LoginAuthInterceptor extends HandlerInterceptorAdapter{
	@Resource
	private SysUrlRecordService recordService;
	@Resource
	private RedisService redisService;
	@Resource
	private CommonService commonService;
	@Resource
	private ConfUtils confUtils;
	@Resource
	private LanguageUtil languageUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();
        //获取请求URL
        String url = requestUri.substring(contextPath.length());
        if (url.lastIndexOf("?") > 0) {
            url = url.substring(0, url.lastIndexOf("?"));
        }
        LogUtils.info("1时间戳为:"+request.getHeader("timestamp"));
        LogUtils.info("验签为:"+request.getHeader("sign"));
        LogUtils.info("2时间戳为:"+request.getParameter("timestamp"));

        //获取请求token
        String token = request.getHeader("token");
        if (null == token) {
            token = request.getParameter("token");
        }
        LogUtils.info("请求Token:" + token);

        //以下记录控制重复提交
        String key = url + token;
        if (!StringUtils.isEmpty(redisService.getString(key))) {
            RespBody respBody = new RespBody();
            respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.REPEAT_ACTION, "请不要重复操作！"));
            output(response, respBody);
            return false;
        }

        //进行接口验签
        if (checkSign(request, response, url)){
            return false;
        }

        //查找不被拦截URL
        List<String> urls = recordService.findUrl();
        if (urls.contains(url)) {
            return true;
        }

        //token为空
        if (token == null || token.trim().length() == 0 || token.equals("null")) {
            RespBody respBody = new RespBody();
            respBody.add(RespCodeEnum.NOLOGIN.getCode(), languageUtil.getMsg(AppMessage.INVAILD_REQUEST, "非法访问，系统自动退出"));
            errorOut(response, respBody);
            return false;
        }

        //redis是否存在
        Object obj = redisService.getObj(token);
        if (obj == null || !(obj instanceof SysUserVo || obj instanceof AppUserPo)) {
            RespBody respBody = new RespBody();
            respBody.add(RespCodeEnum.NOLOGIN.getCode(), languageUtil.getMsg(AppMessage.LOGIN_TIEMOUT, "过长时间没有操作，页面过期，请重新登录"));
            errorOut(response, respBody);
            return false;
        }
        //更新时间
        redisService.expire(token, confUtils.getSessionTimeout());


        //查找需要放重复操作的url
        List<String> repeatUrls = recordService.findRepeatUrl();
        if (repeatUrls.contains(url)) {
            //防重复操作的缓冲时间（默认10秒）
            int timeout = ToolUtils.parseInt(commonService.findParameter("repeatTimeout"), 10);
            redisService.putString(key, key, timeout);
        }
        return true;
    }

    //验签
    private boolean checkSign(HttpServletRequest request, HttpServletResponse response, String url) {
        List<String> enCodeUrls = recordService.findEnCodeUrl();
        if (enCodeUrls != null && enCodeUrls.contains(url)) {
            //获取请求timestamp
            String timestamp = request.getHeader("timestamp");
            if (StringUtils.isEmpty(timestamp)) {
                timestamp = request.getParameter("timestamp");
            }
            if (StringUtils.isEmpty(timestamp)) {
                LogUtils.info("时间戳为空，不符合请求规则:");
                return true;
            }
            long timeStamp = Long.valueOf(timestamp);
            long timeStampTimeout = ToolUtils.parseInt(commonService.findParameter("timeStampTimeout"), 10);
            System.out.println("时间为："+(System.currentTimeMillis()-timeStamp));
            if (((System.currentTimeMillis()-timeStamp) / 1000)>timeStampTimeout){
                RespBody respBody = new RespBody();
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.REQUEST_INVALID, "请求超时！"));
                output(response, respBody);
                LogUtils.info("请求timestamp失效:");
                return true;
            }
            //获取请求timestamp
            String sign = request.getHeader("sign");
            if (StringUtils.isEmpty(sign)) {
                sign = request.getParameter("sign");
            }
            if (StringUtils.isEmpty(sign)) {
                LogUtils.info("sign签名为空，不符合请求规则:");
                return true;
            }
            int signTimeout = ToolUtils.parseInt(commonService.findParameter("signTimeout"), 10);
            String redisSign = redisService.getString(sign);
            if (StringUtils.isNotEmpty(redisSign)){
                LogUtils.info("sign签名单次请求有效,服务端已经存在,不符合请求规则:");
                RespBody respBody = new RespBody();
                respBody.add(RespCodeEnum.ERROR.getCode(), languageUtil.getMsg(AppMessage.REQUEST_INVALID, "请求超时！"));
                output(response, respBody);
                return false;
            }
            redisService.putString(sign, sign, signTimeout);
        }
        return false;
    }

    private void errorOut(HttpServletResponse response, RespBody res) {
        response.setStatus(608);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(ToolUtils.toJson(res));
        } catch (IOException e) {
            LogUtils.error("响应异常", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
                out = null;
            }
        }
    }

    private void output(HttpServletResponse response, RespBody res) {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(ToolUtils.toJson(res));
        } catch (IOException e) {
            LogUtils.error("响应异常", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
                out = null;
            }
        }
    }

}
