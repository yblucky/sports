package com.xlf.common.enums;

/**
 * 是否开奖
 */
public enum RacingSeatEnum {
    ONE(1, "个位开奖号"),
    TWO(2, "十位开奖号"),
    THREE(3, "百位开奖号"),
    FOURE(4, "千位开奖号"),
    FIVE(5, "万位开奖号");

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
