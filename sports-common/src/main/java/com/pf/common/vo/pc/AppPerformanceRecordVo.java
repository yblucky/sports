package com.pf.common.vo.pc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author 业绩记录查询
 *	zyc
 *	2018-01-10
 *
 */
public class AppPerformanceRecordVo {
	/**
     * 主键编号
     */
    @Id
    private String id;
    /**
     * '来源'
     */
    private String orderId;
    /**
     * '用户id'
     */
    private String userId;
    /**
     * '增加业绩数量'
     */
    private BigDecimal amount;
    /**
     * '创建时间'
     */
    private Date createTime;
    /**
     * '业绩增加的部门'
     */
    private String department;

    /**
     * '类型：1:激活    2:兑换'
     */
    private Integer type;
    
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
