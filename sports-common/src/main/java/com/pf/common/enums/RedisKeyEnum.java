package com.pf.common.enums;

/**
 * redis枚举类
 * Created by Administrator on 2017/8/21.
 */
public enum RedisKeyEnum {

    /** Token，保存1天 */
    TOKEN_API("token_api_", 24 * 60 * 60),
    ZH_CN("zh_cn_",60),
    EN_US("en_us_",60),
    SHORT_CURRENCY_TYPE_EN("short_currency_type_en", 24 * 60 * 60),
    CURRENCY_TYPE_EN("currency_type_en", 24 * 60 * 60),
    CURRENCY_TYPE_ZH("currency_type_zh", 24 * 60 * 60),
    CURRENCY_TYPE_TRANSACTION("currency_type_transaction_", 24 * 60 * 60);

    private final String key;

    private final int seconds;

    private RedisKeyEnum(String key, int seconds) {
        this.key = key;
        this.seconds = seconds;
    }

    public String getKey() {
        return key;
    }

    public int getSeconds() {
        return seconds;
    }


}
