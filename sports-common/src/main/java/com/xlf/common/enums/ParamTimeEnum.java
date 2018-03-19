package com.xlf.common.enums;

/**
 * 流水业务类型
 * Created by Administrator on 2017/8/17.
 */
public enum ParamTimeEnum {

    CURRENTDAY(11, "当天报表", "currentDay"),
    LASTWEEK(12, "上周报表", "lastWeek"),
    CURRENTWEEK(13, "本周报表", "currentWeek"),
    LASTMONTH(14, "上月报表", "lastMonth"),
    CURRENTMONTH (15, "本月报表", "currentMonth"),
    OTHERTIME (16, "其他时间报表", "otherTime");

    private Integer code;
    private String name;
    private String egName;

    private ParamTimeEnum(Integer code, String name, String egName) {
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
        for (ParamTimeEnum enums : ParamTimeEnum.values ()) {
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
        for (ParamTimeEnum enums : ParamTimeEnum.values ()) {
            if (enums.getCode ().equals (code)) {
                return enums.getEgName ();
            }
        }
        return String.valueOf (code);
    }
}
