package com.xlf.common.enums;

/**
 * 数据是否有效
 * @author qsy
 * @version v1.0
 * @date 2016年11月27日
 */
public enum PerformanceTypeEnum {
	ACTIVE(10,"激活"),
	EXCHANGE(20,"兑换");
	private Integer code;
	private String name;

	private PerformanceTypeEnum(Integer code, String name) {
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
		for(PerformanceTypeEnum enums : PerformanceTypeEnum.values()){
			if (enums.getCode().toString().equals(code)) {
				return enums.getName();
			}
		}
		return code;
	}
}
