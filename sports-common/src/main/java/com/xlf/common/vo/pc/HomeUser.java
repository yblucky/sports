package com.xlf.common.vo.pc;

import java.math.BigDecimal;

/**
 * 首页用户统计
 * @author Administrator
 *
 */
public class HomeUser {
	
	public BigDecimal epBalance;
	
	public BigDecimal birdScore;
	
	public BigDecimal assets;
	
	public Integer userCount;
	
	/**
	 * 今天注册用户
	 */
	public Integer registerUserCount;
	
	/**
	 * 
	 * 今天激活数量
	 */
	
	public Integer activeUserCount;
	
	
	
	
	

	public Integer getRegisterUserCount() {
		return registerUserCount;
	}

	public void setRegisterUserCount(Integer registerUserCount) {
		this.registerUserCount = registerUserCount;
	}

	public Integer getActiveUserCount() {
		return activeUserCount;
	}

	public void setActiveUserCount(Integer activeUserCount) {
		this.activeUserCount = activeUserCount;
	}

	public BigDecimal getEpBalance() {
		return epBalance;
	}

	public void setEpBalance(BigDecimal epBalance) {
		this.epBalance = epBalance;
	}

	public BigDecimal getBirdScore() {
		return birdScore;
	}

	public void setBirdScore(BigDecimal birdScore) {
		this.birdScore = birdScore;
	}

	public BigDecimal getAssets() {
		return assets;
	}

	public void setAssets(BigDecimal assets) {
		this.assets = assets;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
	
	
	

}
