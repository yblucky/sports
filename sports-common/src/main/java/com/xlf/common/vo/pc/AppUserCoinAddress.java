package com.xlf.common.vo.pc;

import java.util.Date;


import javax.persistence.Id;

public class AppUserCoinAddress {
	
	
	/**
     * 主键编号
     */
    @Id
    private String id;
	 /**
     * '用户编号'
     */
    private Integer uid;
    /**
     * '手机号
     */
    private String mobile;
    /**
     * '昵称'
     */
    private String nickName;
    /**
     * 真实姓名
     */
    private String name;
	
	 /**
     * '创建时间'
     */
    private Date createTime;
    
    
    /**
     * 币种类型
     */
    private String Vpay;
    
    private String Bitcoin;
    
    private String Litecoin;
    
    private String Ethereum;
    
    private String Dogecoin;
    
    
    

	public String getVpay() {
		return Vpay;
	}

	public void setVpay(String vpay) {
		Vpay = vpay;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBitcoin() {
		return Bitcoin;
	}

	public void setBitcoin(String bitcoin) {
		Bitcoin = bitcoin;
	}

	public String getLitecoin() {
		return Litecoin;
	}

	public void setLitecoin(String litecoin) {
		Litecoin = litecoin;
	}

	public String getEthereum() {
		return Ethereum;
	}

	public void setEthereum(String ethereum) {
		Ethereum = ethereum;
	}

	public String getDogecoin() {
		return Dogecoin;
	}

	public void setDogecoin(String dogecoin) {
		Dogecoin = dogecoin;
	}
    
    
    
    
    
    
}
