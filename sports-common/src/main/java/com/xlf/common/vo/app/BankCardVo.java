package com.xlf.common.vo.app;

/**
 * 银行卡管理vo类
 * Created by Administrator on 2017/8/17.
 */
public class BankCardVo {

    /**
     * 主键编号
     */
    private String id;
    /**
     * '用户主键编号'
     */
    private String userId;
    /**
     * '持卡人姓名'
     */
    private String name;
    /**
     * '开户银行'
     */
    private String bankName;
    /**
     * '银行卡号'
     */
    private String bankCard;
    /**
     * '开户支行'
     */
    private String branch;
    /**
     *是否默认
     */
    private String defaultState;
    /**
     *银行默认logo
     */
    private String logo;
    /**
     *开户银行类型ID
     */
    private String bankTypeId;
    /**
     *银行类型背景颜色
     */
    private String color;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDefaultState() {
        return defaultState;
    }

    public void setDefaultState(String defaultState) {
        this.defaultState = defaultState;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBankTypeId() {
        return bankTypeId;
    }

    public void setBankTypeId(String bankTypeId) {
        this.bankTypeId = bankTypeId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
