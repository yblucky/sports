package com.xlf.common.vo.pc;

import java.math.BigDecimal;

/**
 * 
 * @author zyc
 * 币种总数统计
 * 2017-11-27
 */
public class CoinManageVo {
	
	private String  currencyName;
	
	private BigDecimal coinSUM;
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public BigDecimal getCoinSUM() {
		return coinSUM;
	}
	public void setCoinSUM(BigDecimal coinSUM) {
		this.coinSUM = coinSUM;
	}
	
	
	
	

}
