package com.xlf.common.vo.pc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created  2017/8/17 0017.  审判VO
 */
public class JudgeOrderVo {
    /**
     * 主键编号
     */
    private String id;
    /**
     * 订单编号
     */
    private String  orderId;
    /**
     *目标订单
     */
    private String targetOrderId ;
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
     * 目标订单ID
     */
    private String targetId;
    /**
     *10 - 匹配订单号
     */
    private  String targetOrder;
    /**
     *10 - 匹配用户ID
     */
    private  String targetuserid;
    /**
     *10 - 匹配用户UID
     */
    private  Integer targetuid;
    /**
     *10 - 匹配用户昵称
     */
    private  String targetnickName;
    /**
     *10 - 匹配用户手机
     */
    private  String targetmobile;
    
    /**
     *交易金额
     */
    private BigDecimal amount;
    /**
     *RMB金额
     */
    private BigDecimal  rmbAmount;
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
     *对方订单号
     */
    private String targetTransactionState;
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
	public String getTargetOrderId() {
		return targetOrderId;
	}
	public void setTargetOrderId(String targetOrderId) {
		this.targetOrderId = targetOrderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getTargetOrder() {
		return targetOrder;
	}
	public void setTargetOrder(String targetOrder) {
		this.targetOrder = targetOrder;
	}
	public String getTargetuserid() {
		return targetuserid;
	}
	public void setTargetuserid(String targetuserid) {
		this.targetuserid = targetuserid;
	}
	public Integer getTargetuid() {
		return targetuid;
	}
	public void setTargetuid(Integer targetuid) {
		this.targetuid = targetuid;
	}
	public String getTargetnickName() {
		return targetnickName;
	}
	public void setTargetnickName(String targetnickName) {
		this.targetnickName = targetnickName;
	}
	public String getTargetmobile() {
		return targetmobile;
	}
	public void setTargetmobile(String targetmobile) {
		this.targetmobile = targetmobile;
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
	public String getTargetTransactionState() {
		return targetTransactionState;
	}
	public void setTargetTransactionState(String targetTransactionState) {
		this.targetTransactionState = targetTransactionState;
	}
    
    
    
}
