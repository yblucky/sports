package com.pf.common.vo.pc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

public class AppUserMessageVo {
	 /**
     * 主键编号
     */
    @Id
    private String id;
    /**
     * 用户uid
     */
    
    private String uid;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    private String userId;
    private String remark;
    
    private String state;
    
    
    
    
    
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
