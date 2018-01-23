package com.xlf.common.enums;

/**
 * 数据是否有效
 * @author qsy
 * @version v1.0
 * @date 2016年11月27日
 */
public enum BankEnum {
	NORMAL("10","默认"),
	DISABLE("20","非默认");
	private String code;
	private String name;

	private BankEnum(String code, String name) {
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
		for(BankEnum enums : BankEnum.values()){
			if (enums.getCode().toString().equals(code)) {
				return enums.getName();
			}
		}
		return code;
	}
}
