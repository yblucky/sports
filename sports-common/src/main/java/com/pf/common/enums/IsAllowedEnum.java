package com.pf.common.enums;

/**
 * 数据是否有效
 * @author qsy
 * @version v1.0
 * @date 2016年11月27日
 */
public enum IsAllowedEnum {
	NO(10,"不允许"),
	YES(20,"允许");
	private Integer code;
	private String name;

	private IsAllowedEnum(Integer code, String name) {
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
		for(IsAllowedEnum enums : IsAllowedEnum.values()){
			if (enums.getCode().equals(code)) {
				return enums.getName();
			}
		}
		return String.valueOf(code);
	}
}
