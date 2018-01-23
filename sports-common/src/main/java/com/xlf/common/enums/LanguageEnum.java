package com.xlf.common.enums;

/**
 * 多语言枚举
 *
 * @author jay.zheng
 * @version v1.0
 * @date 2017年9月20日
 */
public enum LanguageEnum {
    ZH_CN("10", "中国"),
    EN_US("20", "国际");
    private String code;
    private String name;

    private LanguageEnum(String code, String name) {
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getName(String code) {
        if (code == null) {
            return "";
        }
        for (LanguageEnum enums : LanguageEnum.values()) {
            if (enums.getCode().equals(code)) {
                return enums.getName();
            }
        }
        return code;
    }
}
