package com.xlf.common.enums;

/**
 * 数据是否有效
 * @author qsy
 * @version v1.0
 * @date 2016年11月27日
 */
public enum RoleTypeEnum {
	ADMIN(10,"超级管理员"),
	AGENT(20,"代理");
	private Integer code;
	private String name;

	private RoleTypeEnum(Integer code, String name) {
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
		for(RoleTypeEnum enums : RoleTypeEnum.values()){
			if (enums.getCode().equals(code)) {
				return enums.getName();
			}
		}
		return String.valueOf(code);
	}
}
