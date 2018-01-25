package com.xlf.common.enums;

/**
 * 是否开奖
 */
public enum LotteryFlag {
    YES(10,"未开奖"),
    NO(20,"已开奖");

    LotteryFlag(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private  Integer code;
    private  String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
