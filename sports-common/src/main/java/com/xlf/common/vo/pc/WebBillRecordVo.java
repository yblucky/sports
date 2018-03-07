package com.xlf.common.vo.pc;

import com.xlf.common.enums.BusnessTypeEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WebBillRecordVo {

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


    private Integer busnessType;
    /**
     * '创建时间'
     */
    private Date createTime;

    private String uid;

    private String mobile;


    /**
     * '备注'
     */
    private String remark;

    /**
     * '开始时间'
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * '结束时间'
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;


    private String busnessTypeName;
    private String currencyTypeName;

    /**
     * '业务单号'
     */
    private String businessNumber;

    /**
     * '资产前'
     */
    private BigDecimal beforeBalance;
    /**
     * '资产后'
     */
    private BigDecimal afterBalance;
    /**
     * '手续费'
     */
    private BigDecimal fee;
    /**
     * '扩展'
     */
    private String extend;
    /**
     * '父类'
     */
    private String parentId;


    public String getBusnessTypeName() {
        return busnessTypeName;
    }

    public void setBusnessTypeName(String busnessTypeName) {
        this.busnessTypeName = busnessTypeName;
    }

    public String getCurrencyTypeName() {
        return currencyTypeName;
    }

    public void setCurrencyTypeName(String currencyTypeName) {
        this.currencyTypeName = currencyTypeName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        if (null == endTime) {
            return endTime;
        } else {
            return getEndtime(endTime.getTime());
        }
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

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


    public Date getCreateTime() {
        return createTime;
    }

    public Integer getBusnessType() {
        return busnessType;
    }

    public void setBusnessType(Integer busnessType) {
        this.busnessType = busnessType;
        setBusnessTypeName(BusnessTypeEnum.getName(busnessType));
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    //结束时间处理
    public static Date getEndtime(Long endtime) {
        Date endTime = new Date(endtime);
        try {
            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
            Calendar date = Calendar.getInstance();
            date.setTime(endTime);
            date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);
            endTime = dft.parse(dft.format(date.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return endTime;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
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

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
