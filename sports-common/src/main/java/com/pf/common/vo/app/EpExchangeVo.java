package com.pf.common.vo.app;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付vo类
 * Created by Administrator on 2017/8/21.
 */
public class EpExchangeVo implements Serializable {

    private static final long serialVersionUID = 1435L;

    /**
     * 转账金额
     */
    private BigDecimal amount;
    /**
     * 支付密码
     */
    private String payPwd;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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
}
