package com.xlf.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解，适用于控制器层的方法
 * @author qsy
 * @version v1.0
 * @date 2016年11月28日
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented
public @interface SystemControllerLog {
	String description()  default "";
}
