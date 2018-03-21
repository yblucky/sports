package com.xlf.common.enums;


/**
 * 投注类型
 * @author qsy
 * @version v1.0
 * @date 2017年7月18日
 */
public enum BetTypeEnum {
	TIME_ONE(10,"时时彩一字定"),
	TIME_TWO(20,"时时彩二字定"),
	RACE_ONE(30,"PK10一字定");
	private Integer code;
	private String name;

	private BetTypeEnum(Integer code, String name) {
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
