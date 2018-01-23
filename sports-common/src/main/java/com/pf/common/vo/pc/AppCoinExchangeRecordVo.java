package com.pf.common.vo.pc;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数字货币兑换记录po类
 * Created by Administrator on 2017/8/17.
 */
@Table(name = "app_coin_exchange_record")
public class AppCoinExchangeRecordVo {

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
     * 用户Uid
     */
    private Integer uid;
    /**
     * 用户nickName
     */
    private String nickName;
    /**
     * 用户mobile
     */
    private String mobile;
    /**
     * '兑换数量'
     */
    private BigDecimal amount;
    /**
     * '汇率'
     */
    private BigDecimal exRate;
    /**
     * '兑换前余额'
     */
    private BigDecimal beforeBalance;
    /**
     * '兑换后余额'
     */
    private BigDecimal afterBalance;
    /**
     * '兑换前数字资产'
     */
    private BigDecimal beforerCoin;
    /**
     * '兑换后数字资产'
     */
    private BigDecimal afterCoin;
    /**
     * '10 - 兑换数字资产
     * 20 - 兑换余额'
     */
    private String exType;
    /**
     * 币种类型
     */
    
    private String coinType;
    
    /**
     * 币种名称
     */
    
    private String currencyName;

    /**
     * 兑换时间',
     */
    private Date createTime;
    /**
     * '兑换数量'
     */
    private BigDecimal stbAmount;
    
    
    
    
    public String getCoinType() {
		return coinType;
	}

	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getExRate() {
        return exRate;
    }

    public void setExRate(BigDecimal exRate) {
        this.exRate = exRate;
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

    public String getExType() {
        return exType;
    }

    public void setExType(String exType) {
        this.exType = exType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getStbAmount() {
		return stbAmount;
	}

	public void setStbAmount(BigDecimal stbAmount) {
		this.stbAmount = stbAmount;
	}
    
	
}
