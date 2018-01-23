package com.pf.server.interceptor;

import com.pf.common.annotation.SystemControllerLog;
import com.pf.common.service.RedisService;
import com.pf.common.util.LogUtils;
import com.pf.common.util.NetworkUtil;
import com.pf.common.util.ToolUtils;
import com.pf.common.vo.pc.SysLogsVo;
import com.pf.common.vo.pc.SysUserVo;
import com.pf.server.common.SysLogsService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统日志拦截器
 * 
 * @author qsy
 * @version v1.0
 * @date 2016年11月28日
 */
@Aspect
@Component
public class SystemLogAspect {
	@Resource
	private SysLogsService logService;
	@Resource
	private RedisService redisService;

	/**
	 * Controller切入点
	 */
	@Pointcut("@annotation(com.pf.common.annotation.SystemControllerLog)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * @param joinPoint 切点
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		SysLogsVo logVo = new SysLogsVo();
		try{
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			//请求的IP    
	        String ip = NetworkUtil.getIpAddress(request);
	        //设置IP
	        logVo.setIp(ip);
	        //获取token
	        String token = request.getHeader("token");
	        if(token==null){
	        	token = request.getParameter("token");
	        }else{
	        	//获取用户名
		        Object obj = redisService.getObj(token);
		        if(obj != null && obj instanceof SysUserVo){
		        	SysUserVo userVo = (SysUserVo) obj;
		        	logVo.setUsername(userVo.getUserName());
		        }
	        }
	        //设置请求路径
	        logVo.setOptModule(request.getRequestURI());
	        //获取请求参数
	        Object[] args = joinPoint.getArgs();
	        List<Object> list = new ArrayList<>();
	        //过滤Errors对象,不然序列号会报异常
	        for(Object obj: args){
	        	if(!(obj instanceof Errors || obj instanceof HttpServletRequest)){
	        		list.add(obj);
	        	}
	        }
	        String argInfo = ToolUtils.toJson(list);
	        logVo.setLogDetail(argInfo);
	        //设置操作类型
	        logVo.setOptType(getControllerMethodDescription(joinPoint));
	        //保存到数据库
	        logService.save(logVo);
		}catch(Exception ex){
			LogUtils.error("写入日志出错",ex);
		}
	}
	
	 @SuppressWarnings("rawtypes")
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}
}
