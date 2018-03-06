package com.xlf.common.enums;

/**
 * 流水业务类型
 * Created by Administrator on 2017/8/17.
 */
public enum OddsEnum {

    TIMELOTTERYODDS_1(11, "时时彩一字定赔率", "timelotteryodds_1"),
    TIMELOTTERYODDS_2 (12, "时时彩二字定赔率", "timelotteryodds_2");

    private Integer code;
    private String name;
    private String egName;

    private OddsEnum(Integer code, String name, String egName) {
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
        for (OddsEnum enums : OddsEnum.values ()) {
            if (enums.getCode ().equals (code)) {
                return enums.getName ();
            }
        }
        return String.valueOf (code);
    }
    public static String getEgName(Integer code) {
        if (code == null) {
            return "";
        }
        for (OddsEnum enums : OddsEnum.values ()) {
            if (enums.getCode ().equals (code)) {
                return enums.getEgName ();
            }
        }
        return String.valueOf (code);
    }
}
