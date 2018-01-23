package com.xlf.common.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 * @author qsy
 * @version v1.0
 * @date 2016年11月25日
 */
public class MyBeanUtils {
	
	/**
	 * 对象复制
	 * @param src 源对象
	 * @param cls 目标类型
	 * @return 目标对象
	 * @throws Exception
	 */
	public static <T> T copyProperties(Object src,Class<T> cls) throws Exception{
		T target = cls.newInstance();
		BeanUtils.copyProperties(src,target);
		return target;
	}
	
	/**
	 * list集合复制
	 * @param srcs 源集合
	 * @param cls 目标集合类型
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> copyList(List<?> srcs,Class<T> cls) throws Exception{
		List<T> target = null;
		if(srcs != null){
			for(Object o:srcs){
				if(target == null){
					target = new ArrayList<T>();
				}
				target.add(copyProperties(o,cls));
			}
		}
		return target;
	}

	/**
	 * 根据属性名获取属性值
	 * */
	public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			LogUtils.error(e.getMessage(),e);
			return null;
		}
	}

	/**
	 * 获取对象的所有属性值，返回一个对象数组
	 * */
	public static List<String> getFiledValues(Object o,List<String> fieldNames){
		List<String> value=new ArrayList<>();
		for(int i=0;i<fieldNames.size();i++){
			value.add(getFieldValueByName(fieldNames.get(i), o).toString());
		}
		return value;
	}

	/**
	 * 获取对象的所有属性值，返回一个对象数组(针对get请求)
	 * */
	public static List<String> getFiledValuesByGet(Map<String,String> param, List<String> fieldNames){
		List<String> value=new ArrayList<>();
		for(int i=0;i<fieldNames.size();i++){
			value.add(param.get(fieldNames.get(i)).toString());
		}
		return value;
	}




}
