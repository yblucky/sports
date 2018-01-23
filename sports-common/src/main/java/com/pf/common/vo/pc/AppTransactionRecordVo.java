package com.pf.common.vo.pc;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created  2017/8/17 0017.  适用于转账和收款
 */
@Table(name = "app_transaction_record")
public class AppTransactionRecordVo {
    /**
     * 主键编号
     */
    @Id
    private String id;
    /**
     *  '交易编号'
     */
    private String  transactionId;
    /**
     *'收款人编号'
     */
    private String payeeId ;
    /**
     *'收款人UID'
     */
    private Integer payeeuid;
    /**
     *'收款人昵称'
     */
    private String payeeNike ;
    /**
     *'收款人手机'
     */
    private String payeemobile ;
    /**
     *'收款人剩余金额'
     */
    private BigDecimal payeeBalance ;

    /**
     *'款人编号'
     */
    private String payId ;
    /**
     *'打款人UID'
     */
    private Integer payUid;
    /**
     *'打款人昵称'
     */
    private String payNoke ;
    /**
     *'打款人手机'
     */
    private String paymobile ;
    /**
     *'打款人剩余金额'
     */
    private BigDecimal payBalance ;
    /**
     *交易金额
     */
    private BigDecimal  amount;
    /**
     *交易时间
     */
    private Date createTime;
    /**
     *10 - 转账\r\n            20 - 二维码
     */
    private  String transactionType;

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

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String getPayeeNike() {
        return payeeNike;
    }

    public void setPayeeNike(String payeeNike) {
        this.payeeNike = payeeNike;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayNoke() {
        return payNoke;
    }

    public void setPayNoke(String payNoke) {
        this.payNoke = payNoke;
    }

    public BigDecimal getPayeeBalance() {
        return payeeBalance;
    }

    public void setPayeeBalance(BigDecimal payeeBalance) {
        this.payeeBalance = payeeBalance;
    }

    public BigDecimal getPayBalance() {
        return payBalance;
    }

    public void setPayBalance(BigDecimal payBalance) {
        this.payBalance = payBalance;
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

	public Integer getPayeeuid() {
		return payeeuid;
	}

	public void setPayeeuid(Integer payeeuid) {
		this.payeeuid = payeeuid;
	}

	public String getPayeemobile() {
		return payeemobile;
	}

	public void setPayeemobile(String payeemobile) {
		this.payeemobile = payeemobile;
	}

	public Integer getPayUid() {
		return payUid;
	}

	public void setPayUid(Integer payUid) {
		this.payUid = payUid;
	}

	public String getPaymobile() {
		return paymobile;
	}

	public void setPaymobile(String paymobile) {
		this.paymobile = paymobile;
	}
    
}
