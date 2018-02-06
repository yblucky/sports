package com.xlf.common.enums;

/**
 * redis枚举类
 * Created by Administrator on 2017/8/21.
 */
public enum RedisKeyEnum {

    /** Token，保存1天 */
    TOKEN_API("token_api_", 24 * 60 * 60),
    SYSLOG_FLAG("syslog_flag", 24 * 60 * 60),
    TIME_BETTIING_ISSUNO("time_bettiing", 5 * 60 * 60);

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
