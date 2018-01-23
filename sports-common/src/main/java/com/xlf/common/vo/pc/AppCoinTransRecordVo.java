package com.xlf.common.vo.pc;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数字货币交易记录Po类
 * Created by Administrator on 2017/8/17.
 */
@Table(name = "app_coin_trans_record")
public class AppCoinTransRecordVo {

    /**
     * '主键编号'
     */
    @Id
    private String id;
    /**
     * '交易编号'
     */
    private String transactionId;
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
     * 可以是手机号、UID、钱包地址
     */
    private String targetName;
    /**
     * '交易金额'
     */
    private BigDecimal amount;
    /**
     * '剩余金额'
     */
    private BigDecimal userBalance;
    /**
     * '交易时间'
     */
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(BigDecimal userBalance) {
        this.userBalance = userBalance;
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
    
    
}
