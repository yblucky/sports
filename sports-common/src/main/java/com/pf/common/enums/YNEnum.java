package com.pf.common.enums;



/**
 * 状态是否枚举
 * @author qsy
 * @version v1.0
 * @date 2017年7月18日
 */
public enum YNEnum {
	YES("10","是"),
	NO("20","否");
	private String code;
	private String name;
	
	private YNEnum(String code, String name) {
		this.name = name;
		this.code = code;
	}

	public String getCode() {
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
