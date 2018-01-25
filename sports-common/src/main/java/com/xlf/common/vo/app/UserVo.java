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
     * '邀请人手机号
     */
    private String inviteMobile;
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
     * 'e余额'
     */
    private BigDecimal balance;
    /**
     * '冻结ep余额'
     */
    private BigDecimal blockedBalance;
    /**
     * 10 -正常
     * 20 - 已禁用
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
     * 图片验证码
     */
    private String imgKey;

    /**
     * 图片验证码的值
     */
    private String imgKeyValue;

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBlockedBalance() {
        return blockedBalance;
    }

    public void setBlockedBalance(BigDecimal blockedBalance) {
        this.blockedBalance = blockedBalance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getInviteMobile() {
        return inviteMobile;
    }

    public void setInviteMobile(String inviteMobile) {
        this.inviteMobile = inviteMobile;
    }
}
