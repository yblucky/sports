package com.xlf.common.vo.app;

import java.io.Serializable;

/**
 * 支付vo类
 * Created by Administrator on 2017/8/21.
 */
public class PaymentVo implements Serializable {

    private static final long serialVersionUID = 1435L;

    /**
     * 转账金额
     */
    private String amount;
    /**
     * 支付密码
     */
    private String payPwd;

    /**
     * 收款人手机号
     */
    private String mobile;

    /**
     * 转账类型 （10：转账类型 20：扫码支付）
     */
    private String transactionType;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
