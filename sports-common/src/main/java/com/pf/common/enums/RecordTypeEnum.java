package com.pf.common.enums;

/**
 * 数据是否有效
 * @author qsy
 * @version v1.0
 * @date 2016年11月27日
 */
public enum RecordTypeEnum {
	INCOME("10","收入"),
	EXPENDITURE("20","支出");
	private String code;
	private String name;

	private RecordTypeEnum(String code, String name) {
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
		for(RecordTypeEnum enums : RecordTypeEnum.values()){
			if (enums.getCode().equals(code)) {
				return enums.getName();
			}
		}
		return code;
	}
}
