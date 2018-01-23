package com.pf.common.enums;

/**
 * 提现
 * @author qsy
 * @version v1.0
 * @date 2016年11月27日
 */
public enum WithDrawEnum {
	PENDING(10,"待打款"),
	SUCCESS(20,"成功"),
	DENIED(30,"驳回");
	private Integer code;
	private String name;

	private WithDrawEnum(Integer code, String name) {
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
		for(WithDrawEnum enums : WithDrawEnum.values()){
			if (enums.getCode().equals(code)) {
				return enums.getName();
			}
		}
		return String.valueOf(code);
	}
}
