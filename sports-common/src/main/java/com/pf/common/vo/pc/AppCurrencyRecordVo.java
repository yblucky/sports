package com.pf.common.vo.pc;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppCurrencyRecordVo {
	 /**
     * '币种交易记录id'
     */
    @Id
    private String id;
    /**
     * '用户id'
     */
    private String userId;
    /**
     * '关联订单id'
     */
    private String currencyOrderId;
    /**
     * '币种Id'
     */
    private String currencyId;
    /**
     * '出售价格'
     */
    private BigDecimal salePrice;
    /**
     * '出售数量'
     */
    private BigDecimal saleNumber;
    /**
     * '总金额（出售价格 * 出售数量）'
     */
    private BigDecimal totalPrice;
    /**
     * '用户交易前的余额'
     */
    private BigDecimal beforeBalance;
    /**
     * '用户交易后的余额'
     */
    private BigDecimal afterBalance;
    /**
     * '用户交易前的币数量'
     */
    private BigDecimal beforerCoin;
    /**
     * '用户交易后的币种数量'
     */
    private BigDecimal afterCoin;
    /**
     * '交易记录类型(10：买入  20：卖出)'
     */
    private String recordType;
    /**
     * '创建时间'
     */
    private Date createTime;
    /**
     * '修改时间'
     */
    private Date updateTime;
    /**
     * '状态'
     */
    private String state;
    /**
     * '备注'
     */
    private String remark;
    
    
    /**
     * uid
     */
    private String uid;
    
    private String mobile;
    /**
     * '开始时间'
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    /**
     * '结束时间'
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    
    
    
    /**
     * 币种名称
     */
    private String currencyName;
    
    
    
    
    public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		if(null==endTime){
			return endTime;
		}else{
			return getEndtime(endTime.getTime());
		}
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	//结束时间处理
  	public static Date getEndtime(Long endtime){
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

	public String getCurrencyOrderId() {
		return currencyOrderId;
	}

	public void setCurrencyOrderId(String currencyOrderId) {
		this.currencyOrderId = currencyOrderId;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(BigDecimal saleNumber) {
		this.saleNumber = saleNumber;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
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

	public BigDecimal getBeforerCoin() {
		return beforerCoin;
	}

	public void setBeforerCoin(BigDecimal beforerCoin) {
		this.beforerCoin = beforerCoin;
	}

	public BigDecimal getAfterCoin() {
		return afterCoin;
	}

	public void setAfterCoin(BigDecimal afterCoin) {
		this.afterCoin = afterCoin;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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


}
