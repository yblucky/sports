package com.xlf.common.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * app端用户VO类
 *
 * @author qsy
 * @version v1.0
 * @date 2017年8月15日
 */
@Table(name = "app_user")
public class AppUserPo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5588841707552227040L;

	/**
     * 主键编号
     */
    @Id
    private String id;

    /**
     * '用户编号'
     */
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * '上级编号'
     */
    private String parentId;
    /**
     * '余额'
     */
    private BigDecimal balance;
    /**
     * '冻结余额'
     */
    private BigDecimal blockedBalance;
    /**
     * 10 -正常
     * 20 - 已禁用
     **/
    private Integer state;
    /**
     * '创建时间'
     */
    private Date createTime;
    /**
     * '登录时间'
     */
    private Date loginTime;
    /**
     * 用户头像
     */
    private String imgPath;

    /**
     * 登录密码错误次数
     */
    private Integer errorNo;
    /**
     * '累计投注金额'
     */
    private BigDecimal bettingAmout;
    /**
     * '当天盈亏：每日凌晨清零'
     */
    private BigDecimal currentProfit;
    /**
     * '累计中奖金额'
     */
    private BigDecimal winingAmout;
    /**
     * '历史累计返水衡量值'
     */
    private BigDecimal kickBackAmount;


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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(Integer errorNo) {
        this.errorNo = errorNo;
    }

    public BigDecimal getBettingAmout() {
        return bettingAmout;
    }

    public void setBettingAmout(BigDecimal bettingAmout) {
        this.bettingAmout = bettingAmout;
    }

    public BigDecimal getCurrentProfit() {
        return currentProfit;
    }

    public void setCurrentProfit(BigDecimal currentProfit) {
        this.currentProfit = currentProfit;
    }

    public BigDecimal getWiningAmout() {
        return winingAmout;
    }

    public void setWiningAmout(BigDecimal winingAmout) {
        this.winingAmout = winingAmout;
    }

    public BigDecimal getKickBackAmount() {
        return kickBackAmount;
    }

    public void setKickBackAmount(BigDecimal kickBackAmount) {
        this.kickBackAmount = kickBackAmount;
    }
}
