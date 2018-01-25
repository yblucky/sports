package com.xlf.common.enums.sports;


import com.xlf.common.enums.StateEnum;

/**
 * 状态是否枚举
 * @author qsy
 * @version v1.0
 * @date 2017年7月18日
 */
public enum YNEnum {
	YES(10,"是"),
	NO(20,"否");
	private Integer code;
	private String name;
	
	private YNEnum(Integer code, String name) {
		this.name = name;
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static String getName(String code){
		if (code == null) {
			return "";
		}
		for(StateEnum enums : StateEnum.values()){
			if (enums.getCode().equals(code)) {
				return enums.getName();
			}
		}
		return code;
	}
}
