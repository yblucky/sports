package com.xlf.common.enums;

/**
 * 数据是否有效
 * @author qsy
 * @version v1.0
 * @date 2016年11月27日
 */
public enum DateTypeEnum {
	TODAY("10","今日"),
	YESTERDAY("20","昨天"),
	LASTWEEK("30","上周"),
	THISWEEK("40","本周"),
	LASTMONTH("50","上月"),
	THISMONTH("60","本月");
	private String code;
	private String name;

	private DateTypeEnum(String code, String name) {
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
		for(DateTypeEnum enums : DateTypeEnum.values()){
			if (enums.getCode().toString().equals(code)) {
				return enums.getName();
			}
		}
		return code;
	}
}
