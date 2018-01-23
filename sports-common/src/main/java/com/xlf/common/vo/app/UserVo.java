package com.xlf.common.vo.app;

import java.math.BigDecimal;

/**
 * 用户vo类
 * Created by Administrator on 2018/1/4 0004.
 */
public class UserVo {

    /**
     * 主键编号
     */
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
     * '登录密码'
     */
    private String loginPwd;
    /**
     * '登录密码盐'
     */
    private String pwdStal;
    /**
     * '支付密码'
     */
    private String payPwd;
    /**
     * '支付密码盐'
     */
    private String payStal;
    /**
     * 'eq余额'
     */
    private BigDecimal epBalance;
    /**
     * '冻结ep余额'
     */
    private BigDecimal blockedEpBalance;
    /**
     * '候鸟积分'
     */
    private BigDecimal birdScore;
    /**
     * 'e资产'
     */
    private BigDecimal assets;
    /**
     * 10 -未激活
     * 20 - 正常
     * 30 - 已禁用
     **/
    private String state;

    /**
     * 用户头像
     */
    private String imgPath;
    /**
     * 手机验证码
     */
    private String smsCode;

    /**
     * 国家代码
     */
    private String areaNum;
    /**
     * 接点人层级
     */
    private Integer contactLevel;
    /**
     * 图片验证码
     */
    private String imgKey;

    /**
     * 图片验证码的值
     */
    private String imgKeyValue;
    /**
     * 主键编号
     */
    private String contactId;
    /**
     * 'A区业绩'
     */
    private BigDecimal performanceA;
    /**
     * 'B区业绩'
     */
    private BigDecimal performanceB;
    /**
     * '上级编号'
     */
    private String parentId;
    /**
     * '层级'
     */
    private Integer level;
    /**
     * '候鸟积分转账'
     */
    private Integer isAllowed;
    /**
     * '激活次数'
     */
    private Integer activeNo;
    /**
     * 所在分区
     */
    private String currentArea;

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

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getPwdStal() {
        return pwdStal;
    }

    public void setPwdStal(String pwdStal) {
        this.pwdStal = pwdStal;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getPayStal() {
        return payStal;
    }

    public void setPayStal(String payStal) {
        this.payStal = payStal;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public BigDecimal getEpBalance() {
        return epBalance;
    }

    public void setEpBalance(BigDecimal epBalance) {
        this.epBalance = epBalance;
    }

    public BigDecimal getBlockedEpBalance() {
        return blockedEpBalance;
    }

    public void setBlockedEpBalance(BigDecimal blockedEpBalance) {
        this.blockedEpBalance = blockedEpBalance;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(String currentArea) {
        this.currentArea = currentArea;
    }

    public Integer getContactLevel() {
        return contactLevel;
    }

    public void setContactLevel(Integer contactLevel) {
        this.contactLevel = contactLevel;
    }

    public String getImgKey() {
        return imgKey;
    }

    public void setImgKey(String imgKey) {
        this.imgKey = imgKey;
    }

    public String getImgKeyValue() {
        return imgKeyValue;
    }

    public void setImgKeyValue(String imgKeyValue) {
        this.imgKeyValue = imgKeyValue;
    }

    public BigDecimal getPerformanceA() {
        return performanceA;
    }

    public void setPerformanceA(BigDecimal performanceA) {
        this.performanceA = performanceA;
    }

    public BigDecimal getPerformanceB() {
        return performanceB;
    }

    public void setPerformanceB(BigDecimal performanceB) {
        this.performanceB = performanceB;
    }

    public Integer getIsAllowed() {
        return isAllowed;
    }

    public void setIsAllowed(Integer isAllowed) {
        this.isAllowed = isAllowed;
    }

    public Integer getActiveNo() {
        return activeNo;
    }

    public void setActiveNo(Integer activeNo) {
        this.activeNo = activeNo;
    }
}
