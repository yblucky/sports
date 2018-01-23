package com.pf.common.vo.pc;

import java.math.BigDecimal;

public class StatisticsVo {
	 private BigDecimal balance;
	 private BigDecimal score;
	 private BigDecimal virtualCoin;
	 private Integer userCount;
	 private BigDecimal shadowScore;
	 
	 
	 
	 
	 
	public BigDecimal getShadowScore() {
		return shadowScore;
	}
	public void setShadowScore(BigDecimal shadowScore) {
		this.shadowScore = shadowScore;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public BigDecimal getVirtualCoin() {
		return virtualCoin;
	}
	public void setVirtualCoin(BigDecimal virtualCoin) {
		this.virtualCoin = virtualCoin;
	}
	public Integer getUserCount() {
		return userCount;
	}
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
	 
	 
}
