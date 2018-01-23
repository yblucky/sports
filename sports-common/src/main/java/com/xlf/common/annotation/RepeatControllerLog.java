package com.xlf.common.annotation;

import java.lang.annotation.*;

/**
 * 防重复操作注解，适用于控制器层的方法
 * @author jay.zheng
 * @version v1.0
 * @date 2017年09月15日
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented
public @interface RepeatControllerLog {
//	String description()  default "";
}
