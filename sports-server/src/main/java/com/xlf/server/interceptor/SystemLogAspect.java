package com.xlf.server.interceptor;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.RedisKeyEnum;
import com.xlf.common.service.RedisService;
import com.xlf.common.thread.ThreadPoolManager;
import com.xlf.common.util.LogUtils;
import com.xlf.common.util.NetworkUtil;
import com.xlf.common.util.SerializeUtil;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.pc.SysLogsVo;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.common.SysLogsService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
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
	@Pointcut("@annotation(com.xlf.common.annotation.SystemControllerLog)")
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
			logVo.setOptDate(new Date());
			logVo.setId (ToolUtils.getUUID ());
			if (StringUtils.isEmpty (logVo.getUsername ())){
				logVo.setUsername ("");
			}
			//先缓存到redis中
//			redisService.rpush(SerializeUtil.serialize(RedisKeyEnum.SYSLOG_FLAG.getKey()), SerializeUtil.serialize(logVo));
			redisService.rpush(RedisKeyEnum.SYSLOG_FLAG.getKey().getBytes (), SerializeUtil.serialize(logVo));
			//日志线程是否正在运行
			String isRun = redisService.getString(RedisKeyEnum.SYSLOG_FLAG.getKey()+"_isRun");
			if(!StringUtils.isEmpty(isRun)){
				return;
			}
			//开启线程去跑存储日志的功能
			ThreadPoolManager.getThreadPoolExecutor().execute(new Runnable() {
				@Override
				public void run() {
					redisService.putString(RedisKeyEnum.SYSLOG_FLAG.getKey()+"_isRun","true",3600000);
					doSaveLog();
				}
			});
		}catch(Exception ex){
			LogUtils.error("写入日志出错",ex);
		}
	}


	//递归调用保存日志
	public void doSaveLog() {
		try {
			String key = RedisKeyEnum.SYSLOG_FLAG.getKey();
			byte[] bytes = redisService.lpop(SerializeUtil.serialize(key));
			if (bytes == null) {
				//线程已跑完，关闭正在跑的线程提示
				redisService.del(RedisKeyEnum.SYSLOG_FLAG.getKey()+"_isRun");
				return;
			}
			SysLogsVo logVo = (SysLogsVo) SerializeUtil.unserialize(bytes);
			if (logVo != null) {
				//保存到数据库
				logService.save(logVo);
				//递归调用保存用户操作日志
				doSaveLog();
			}
		} catch (Exception ex) {
			//不抛出异常
			//线程已跑完，关闭正在跑的线程提示
			redisService.del(RedisKeyEnum.SYSLOG_FLAG.getKey()+"_isRun");
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
