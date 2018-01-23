package com.xlf.common.vo.app;

import java.math.BigDecimal;

/**
 * 用户提现vo类
 * Created by Administrator on 2018/1/4 0004.
 */
public class DrawVo {
    /**
     * '提现金额
     */
    private BigDecimal amount;
    /**
     * '支付密码'
     */
    private String payPwd;
    /**
     * '银行卡id'
     */
    private String bankCardId;


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }
}
