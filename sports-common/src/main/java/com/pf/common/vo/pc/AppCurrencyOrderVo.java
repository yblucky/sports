package com.pf.common.vo.pc;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 用户币种交易订单
 * Created by zyc on 2017/11/10.
 */
public class AppCurrencyOrderVo {
	
	   @Id
	    private String id;
	    /**
	     * '用户id'
	     */
	    private String userId;
	    /**
	     * '币种Id'
	     */
	    private String currencyId;
	    /**
	     * '当前价格(对比价格，供用户参考使用)'
	     */
	    private BigDecimal currentPrice;
	    /**
	     * '出售价格(用户出售价格)'
	     */
	    private BigDecimal salePrice;
	    /**
	     * '出售数量(限额)'
	     */
	    private BigDecimal saleNumber;
	    /**
	     * '已出售数量'
	     */
	    private BigDecimal alreadySoldNumber;
	    /**
	     * '订单类型(10 : 买入  20：卖出)'
	     */
	    private String orderType;
	    /**
	     * '订单创建时间'
	     */
	    private Date createTime;
	    /**
	     * '订单修改时间'
	     */
	    private Date updateTime;
	    /**
	     * '订单状态(10：进行中  20：已完成  30 : 撤销)'
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

		public String getCurrencyId() {
			return currencyId;
		}

		public void setCurrencyId(String currencyId) {
			this.currencyId = currencyId;
		}

		public BigDecimal getCurrentPrice() {
			return currentPrice;
		}

		public void setCurrentPrice(BigDecimal currentPrice) {
			this.currentPrice = currentPrice;
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

		public BigDecimal getAlreadySoldNumber() {
			return alreadySoldNumber;
		}

		public void setAlreadySoldNumber(BigDecimal alreadySoldNumber) {
			this.alreadySoldNumber = alreadySoldNumber;
		}

		public String getOrderType() {
			return orderType;
		}

		public void setOrderType(String orderType) {
			this.orderType = orderType;
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
