package com.pf.common.vo.pc;

import java.math.BigDecimal; 
public class AppUserContactVo {
	private String id;
    /**
     * '用户主键编号'
     */
    private String userId;
    /**
     * 'A区业绩'
     */
    private BigDecimal performanceA;
    /**
     * 'B区业绩'
     */
    private BigDecimal performanceB;
    /**
     * '上级编号'
     */
    private String   parentId;
    /**
     * '层级'
     */
    private Integer level;
    /**
     * 所在分区
     */
    private String currentArea;
    /**
     * uid
     */
    
    private String uid;
    
    
    private String mobile;


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


	public BigDecimal getPerformanceA() {
		return performanceA;
	}


	public void setPerformanceA(BigDecimal performanceA) {
		this.performanceA = performanceA;
	}


	public BigDecimal getPerformanceB() {
		return performanceB;
	}


	public void setPerformanceB(BigDecimal performanceB) {
		this.performanceB = performanceB;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public Integer getLevel() {
		return level;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}


	public String getCurrentArea() {
		return currentArea;
	}


	public void setCurrentArea(String currentArea) {
		this.currentArea = currentArea;
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
