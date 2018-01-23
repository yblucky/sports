package com.xlf.common.enums;

/**
 * 币种类型
 * Created by Administrator on 2017/8/17.
 */
public enum CurrencyTypeEnum {

    EP_BALANCE(10, "EP余额", "EP_Balance"),
    E_ASSET(20, "E资产", "e_asset"),
    BIRDSCORE(30, "候鸟积分", "birdScore"),
    ACTIVATION_TIMES(40,"激活次数","activation_times");

    private Integer code;
    private String name;
    private String egName;

    private CurrencyTypeEnum(Integer code, String name, String egName) {
        this.name = name;
        this.code = code;
        this.egName = egName;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getEgName() {
        return egName;
    }

    public static String getName(Integer code) {
        if (code == null) {
            return "";
        }
        for (CurrencyTypeEnum enums : CurrencyTypeEnum.values()) {
            if (enums.getCode().equals(code)) {
                return enums.getName();
            }
        }
        return String.valueOf(code);
    }

    public static String getEgName(String code) {
        if (code == null) {
            return "";
        }
        for (CurrencyTypeEnum enums : CurrencyTypeEnum.values()) {
            if (enums.getCode().toString().equals(code)) {
                return enums.getEgName();
            }
        }
        return code;
    }

}
