package com.xlf.common.enums;

/**
 * 是否开奖
 */
public enum RacingSeatEnum {
    ONE(1, "第一名开奖号"),
    TWO(2, "第二名开奖号"),
    THREE(3, "第三名开奖号"),
    FOURE(4, "第四名开奖号"),
    FIVE(5, "第五名开奖号"),
    SIX(6, "第六名开奖号"),
    SEVEN(7, "第七名开奖号"),
    EIGHT(8, "第八名开奖号"),
    NINE(9, "第九名开奖号"),
    TEN(10, "第十名开奖号");

    RacingSeatEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;
    private String name;

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
