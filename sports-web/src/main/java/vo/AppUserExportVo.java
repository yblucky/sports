package vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class AppUserExportVo implements Serializable {


    private String id;

    /**
     * '用户编号'
     */
    @Excel (name = "UID",height = 20,width = 15 ,isImportField = "true",orderNum = "1")
    private Integer uid;
    /**
     * '手机号
     */
    @Excel (name = "手机号",height = 20,width = 20 ,isImportField = "true",orderNum = "2")
    private String mobile;
    /**
     * '昵称'
     */
    @Excel (name = "昵称",height = 20,width = 20 ,isImportField = "true",orderNum = "3")
    private String nickName;
    /**
     * 真实姓名
     */
    @Excel (name = "真实姓名",height = 20,width = 20 ,isImportField = "true",orderNum = "4")
    private String name;
    /**
     * '登录密码'
     */
    @Excel (name = "登录密码",height = 20,width = 50 ,isImportField = "true",orderNum = "49")
    private String loginPwd;
    /**
     * '登录密码盐'
     */
    @Excel (name = "登录密码盐",height = 20,width = 50 ,isImportField = "true",orderNum = "50")
    private String pwdStal;
    /**
     * '支付密码'
     */
    @Excel (name = "支付密码",height = 20,width = 50 ,isImportField = "true",orderNum = "51")
    private String payPwd;
    /**
     * '支付密码盐'
     */
    @Excel (name = "支付密码盐",height = 20,width = 50 ,isImportField = "true",orderNum = "52")
    private String payStal;
    /**
     * '上级编号'
     */
    @Excel (name = "上级编号",height = 20,width = 40 ,isImportField = "true",orderNum = "40")
    private String parentId;
    /**
     * '余额'
     */
    @Excel (name = "余额",height = 20,width = 20 ,isImportField = "true",orderNum = "4")
    private BigDecimal balance;
    /**
     * '冻结余额'
     */
    @Excel (name = "冻结余额",height = 20,width = 20 ,isImportField = "true",orderNum = "5")
    private BigDecimal blockedBalance;
    /**
     * 10 -正常
     * 20 - 已禁用
     **/
    @Excel (name = "状态",height = 20,width = 10 ,isImportField = "true",orderNum = "6")
    private Integer state;
    /**
     * '创建时间'
     */
    @Excel (name = "创建时间",height = 20,width = 25 ,isImportField = "true",exportFormat = "yyyy-MM-dd HH:mm:ss",orderNum = "20")
    private Date createTime;
    /**
     * '登录时间'
     */
    @Excel (name = "登录时间",height = 20,width = 25 ,isImportField = "true",exportFormat = "yyyy-MM-dd HH:mm:ss",orderNum = "21")
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
    @Excel (name = "累计投注金额",height = 20,width = 20 ,isImportField = "true",orderNum = "7")
    private BigDecimal bettingAmout;
    /**
     * '累计中奖金额'
     */
    @Excel (name = "累计中奖金额",height = 20,width = 20 ,isImportField = "true",orderNum = "8")
    private BigDecimal currentProfit;
    /**
     * '累计中奖金额'
     */
    @Excel (name = "累计中奖金额",height = 20,width = 20 ,isImportField = "true",orderNum = "9")
    private BigDecimal winingAmout;
    /**
     * '历史累计返水衡量值'
     */
    @Excel (name = "历史累计返水衡量值",height = 20,width = 20 ,isImportField = "true",orderNum = "10")
    private BigDecimal kickBackAmount;

    /**
     * '今日累计投注'
     */
    @Excel (name = "今日累计投注",height = 20,width = 20 ,isImportField = "true",orderNum = "11")
    private BigDecimal todayBettingAmout;

    /**
     * '今日累计中奖 '
     */
    @Excel (name = "今日累计中奖",height = 20,width = 20 ,isImportField = "true",orderNum = "12")
    private BigDecimal todayWiningAmout;


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

    public BigDecimal getTodayBettingAmout() {
        return todayBettingAmout;
    }

    public void setTodayBettingAmout(BigDecimal todayBettingAmout) {
        this.todayBettingAmout = todayBettingAmout;
    }

    public BigDecimal getTodayWiningAmout() {
        return todayWiningAmout;
    }

    public void setTodayWiningAmout(BigDecimal todayWiningAmout) {
        this.todayWiningAmout = todayWiningAmout;
    }
}
