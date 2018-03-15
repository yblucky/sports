package com.xlf.common.vo.app;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行卡管理po类
 * Created by Administrator on 2017/8/17.
 */
public class AppWithDrawVo {

    /**
     * 主键编号
     */
    private String id;
    /**
     * '提现人主键编号'
     */
    private String userId;
    /**
     * '提现前EP金额'
     */
    private BigDecimal beforeBalance;
    /**
     * '提现EP金额'
     */
    private BigDecimal amount;
    /**
     * '提现手续费'
     */
    private BigDecimal fee;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 银行卡编号id
     */
    private String bankCardId;
    /**
     * 银行卡名称
     */
    private  String bankCardName;
    /**
     * updateTime
     */
    private Date updateTime;

    /**
     * createTime
     */
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(BigDecimal beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }
}
