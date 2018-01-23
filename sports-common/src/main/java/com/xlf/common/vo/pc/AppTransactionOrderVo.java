package com.xlf.common.vo.pc;

import java.math.BigDecimal;
import java.util.Date;

import com.xlf.common.enums.OrderTypeEnum;

/**
 * Created  2017/8/17 0017.  交易订单
 */
public class AppTransactionOrderVo {
    /**
     * 主键编号
     */
    private String id;
    /**
     * 订单编号
     */
    private String  orderId;
    /**
     *'用户主键编号
     */
    private String userId ;
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
     * '持卡人姓名'
     */
    private String name;
    /**
     * '银行卡号'
     */
    private String bankCard;
    /**
     * '开户银行名称'
     */
    private String bankName;
    /**
     *交易金额
     */
    private BigDecimal amount;
    /**
     *RMB金额
     */
    private BigDecimal  rmbAmount;
    /**
     *目标订单
     */
    private String targetOrderId ;
    /**
     *描述
     */
    private String description ;
    /**
     *'10 - 订单已创建   20 - 已选择打款人/收款人  30 - 支付保证金 40 -  线下支付成功  50 - 等待打款人/收款人确认 60 - 充值成功 70 - 交易失败
     */
    private String transactionState ;
    /**
     *适用于24内自动扣除  10 - 是  20 - 否
     */
    private String deductState ;
    /**
     *交易时间
     */
    private Date createTime;
    /**
     *24小时失效
     */
    private Date flowTime;
    /**
     *10 - 充值\r\n            20 - 提现
     */
    private  String orderType;
    /**
     *10 - 匹配订单号
     */
    private  String targetOrder;
    
    /**
     *10 - 是否纠纷
     */
    private  Boolean isDispute=false;
    /**
     * 对方订单状态
     *'10 - 订单已创建   20 - 已选择打款人/收款人  30 - 支付保证金 40 -  线下支付成功  50 - 等待打款人/收款人确认 60 - 充值成功 70 - 交易失败
     */
    private String targetTransactionState;
    
    /**
     *对方订单24小时失效
     */
    private Date targetflowTime;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

    public BigDecimal getRmbAmount() {
        return rmbAmount;
    }

    public void setRmbAmount(BigDecimal rmbAmount) {
        this.rmbAmount = rmbAmount;
    }

    public String getTargetOrderId() {
        return targetOrderId;
    }

    public void setTargetOrderId(String targetOrderId) {
        this.targetOrderId = targetOrderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionState() {
        return transactionState;
    }

    public void setTransactionState(String transactionState) {
        this.transactionState = transactionState;
    }

    public String getDeductState() {
        return deductState;
    }

    public void setDeductState(String deductState) {
        this.deductState = deductState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(Date flowTime) {
        this.flowTime = flowTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

	public String getTargetOrder() {
		return targetOrder;
	}

	public void setTargetOrder(String targetOrder) {
		this.targetOrder = targetOrder;
	}

	public Boolean getIsDispute() {
		if(orderType.equals(OrderTypeEnum.RECHARGE.getCode())){//充值
			if(null!=flowTime&&null!=transactionState&&transactionState.equals("20")
					||transactionState.equals("30")
					||transactionState.equals("40")
					||transactionState.equals("50")){
				Date now=new Date();
				long time=1000*60*60*24;
				if((now.getTime()-flowTime.getTime())>time){
					isDispute=true;
				}
			}
		}else{//提现
			if(null!=targetTransactionState){
				if(null!=targetflowTime&&targetTransactionState.equals("20")
						||targetTransactionState.equals("30")
						||targetTransactionState.equals("40")
						||targetTransactionState.equals("50")){
					Date now=new Date();
					long time=1000*60*60*24;
					if((now.getTime()-targetflowTime.getTime())>time){
						isDispute=true;
					}
				}
			}
		}
		return isDispute;
	}

	public void setIsDispute(Boolean isDispute) {
		this.isDispute = isDispute;
	}

	public String getTargetTransactionState() {
		return targetTransactionState;
	}

	public void setTargetTransactionState(String targetTransactionState) {
		this.targetTransactionState = targetTransactionState;
	}

	public Date getTargetflowTime() {
		return targetflowTime;
	}

	public void setTargetflowTime(Date targetflowTime) {
		this.targetflowTime = targetflowTime;
	}
    
	
}
