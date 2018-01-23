package com.xlf.common.vo.app;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户激活vo类
 * Created by Administrator on 2018/1/4 0004.
 */
public class ActiveVo {
    /**
     * '手机号
     */
    private String mobile;
    /**
     * '支付密码'
     */
    private String payPwd;

    private String activeUserId;

    public String getActiveUserId() {
        return activeUserId;
    }

    public void setActiveUserId(String activeUserId) {
        this.activeUserId = activeUserId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }
}
