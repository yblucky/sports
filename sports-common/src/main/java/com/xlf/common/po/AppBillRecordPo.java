package com.xlf.common.po;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 流水对账记录表po类
 * Created by Administrator on 2017/8/17.
 */
@Table(name = "app_bill_record")
public class AppBillRecordPo {

    /**
     * '主键编号'
     */
    @Id
    private String id;
    /**
     * '用户主键编号'
     */
    private String userId;
    /**
     * '金额'
     */
    private BigDecimal balance;
    /**
     * '货币类型'
     */
    private Integer currencyType;
    /**
     * '业务类型'
     */
    private Integer busnessType;
    /**
     * '创建时间'
     */
    private Date createTime;
    /**
     * '业务单号'
     */
    private String businessNumber;
    /**
     * '备注'
     */
    private String remark;
    /**
     * '资产前'
     */
    private BigDecimal beforeBalance;
    /**
     * '资产后'
     */
    private BigDecimal afterBalance;
    /**
     * '扩展'
     */
    private String  extend;

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(Integer currencyType) {
        this.currencyType = currencyType;
    }

    public Integer getBusnessType() {
        return busnessType;
    }

    public void setBusnessType(Integer busnessType) {
        this.busnessType = busnessType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(BigDecimal beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public BigDecimal getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(BigDecimal afterBalance) {
        this.afterBalance = afterBalance;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
