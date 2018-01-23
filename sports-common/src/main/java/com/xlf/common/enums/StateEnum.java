package com.xlf.common.enums;

/**
 * 数据是否有效
 * @author qsy
 * @version v1.0
 * @date 2016年11月27日
 */
public enum StateEnum {
	NO_ACTIVE(10,"未激活"),
	NORMAL(20,"正常"),
	DISABLE(30,"禁用");
	private Integer code;
	private String name;
	
	private StateEnum(Integer code, String name) {
		this.name = name;
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static String getName(Integer code){
		if (code == null) {
			return "";
		}
		for(StateEnum enums : StateEnum.values()){
			if (enums.getCode().equals(code)) {
				return enums.getName();
			}
		}
		return String.valueOf(code);
	}
}
