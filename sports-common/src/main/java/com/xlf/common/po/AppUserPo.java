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
     * '释放时间'
     */
    private Date releaseTime;
    /**
     * '用户层级'
     */
    private Integer level;
    /**
     * 用户头像
     */
    private String imgPath;

    /**
     * 激活次数
     */
    private Integer activeNo;

    /**
     * 区域代码
     */
    private String areaNum;

    /**
     * 是否允许释放候鸟积分
     */
    private Integer isAllowed;

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

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
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

    public Integer getActiveNo() {
        return activeNo;
    }

    public void setActiveNo(Integer activeNo) {
        this.activeNo = activeNo;
    }

    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }

    public Integer getIsAllowed() {
        return isAllowed;
    }

    public void setIsAllowed(Integer isAllowed) {
        this.isAllowed = isAllowed;
    }
}
