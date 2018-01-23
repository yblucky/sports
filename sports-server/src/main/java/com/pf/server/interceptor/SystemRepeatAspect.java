package com.pf.server.interceptor;

import com.pf.common.enums.RespCodeEnum;
import com.pf.common.resp.RespBody;
import com.pf.common.service.RedisService;
import com.pf.common.util.ToolUtils;
import com.pf.server.common.CommonService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 重复操作拦截器
 *
 * @author qsy
 * @version v1.0
 * @date 2016年11月28日
 */
@Aspect
@Component
public class SystemRepeatAspect {
    @Resource
    private HttpServletRequest request;
    @Resource
    private RedisService redisService;
    @Resource
    private CommonService commonService;

    /**
     * Controller切入点
     */
    @Pointcut("@annotation(com.pf.common.annotation.RepeatControllerLog)")
    public void controllerAspect() {
    }

    /**
     * 环绕通知 用于拦截Controller层记录用户是否快速重复操作
     *
     * @param joinPoint 切点
     */
    @Around("controllerAspect()")
    public Object doBefore(ProceedingJoinPoint joinPoint) {
        RespBody result = null;
        try {
            Object obj = joinPoint.proceed();
            result = (RespBody) obj;
        } catch (Throwable throwable) {
            //不抛出异常，respbody已经包含异常code
        }
        if (result != null && RespCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            String contextPath = request.getContextPath();
            String requestUri = request.getRequestURI();
            //获取请求URL
            String url = requestUri.substring(contextPath.length());
            if (url.lastIndexOf("?") > 0) {
                url = url.substring(0, url.lastIndexOf("?"));
            }
            //获取请求token
            String token = request.getHeader("token");
            if (null == token) {
                token = request.getParameter("token");
            }
            //防重复操作的缓冲时间（默认10秒）
            int timeout = ToolUtils.parseInt(commonService.findParameter("repeatTimeout"), 10);
            String key = url + token;
            redisService.putString(key, timeout+"", timeout);
        }
        //必须返回值
        return result;
    }

}
