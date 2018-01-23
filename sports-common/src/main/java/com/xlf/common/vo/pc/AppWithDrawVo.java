package com.xlf.common.vo.pc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

public class AppWithDrawVo {
	/**
     * 主键编号
     */
    @Id
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
     * updateTime
     */
    private Date updateTime;

    /**
     * createTime
     */
    private Date createTime;
    
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
     * 枚举类型
     * 
     */
    private String typeName;
    
    /**
     * 银行用户名
     */
    private String name;
    
    /**
     * 银行卡
     */
    private String bankCard;
    
    /**
     * 开户支行
     */
    
    
    private String branch;

    
    	
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
    
    
    

}
