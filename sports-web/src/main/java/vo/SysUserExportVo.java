package vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;


public class SysUserExportVo {
	/**
	 * 主键编号
	 */
	@Excel(name = "ID",height = 20,width = 20 ,isImportField = "true",orderNum = "50")
	private String id;
	/**
	 * 用户名
	 */
	@Excel (name = "用户名",height = 20,width = 20 ,isImportField = "true",orderNum = "1")
	private String userName;
	/**
	 * 登录名
	 */
	@Excel (name = "登录名",height = 20,width = 20 ,isImportField = "true",orderNum = "2")
	private String loginName;
	/**
	 * 密码
	 */
	@Excel (name = "密码",height = 20,width = 20 ,isImportField = "true",orderNum = "51")
	private String password;
	/**
	 * 密码盐
	 */
	@Excel (name = "密码盐",height = 20,width = 20 ,isImportField = "true",orderNum = "52")
	private String salt;
	/**
	 * 手机号
	 */
	@Excel (name = "手机号",height = 20,width = 20 ,isImportField = "true",orderNum = "3")
	private String mobile;
	/**
	 * 角色编号
	 */
	@Excel (name = "角色编号",height = 20,width = 20 ,isImportField = "true",orderNum = "53")
	private String roleId;
	/**
	 * 角色名称
	 */
	@Excel (name = "角色名称",height = 20,width = 20 ,isImportField = "true",orderNum = "5")
	private String roleName;
	/**
	 * 用户头像
	 */
	@Excel (name = "冻结余额",height = 20,width = 20 ,isImportField = "true",orderNum = "55")
	private String userIcon;
	/**
	 * 创建时间
	 */
	@Excel (name = "创建时间",height = 20,width = 20 ,isImportField = "true",exportFormat = "yyyy-MM-dd HH:mm:ss",orderNum = "6")
	private Date createTime;
	/**
	 * 返水时间
	 */
	@Excel (name = "创建时间",height = 20,width = 20 ,isImportField = "true",exportFormat = "yyyy-MM-dd HH:mm:ss",orderNum = "20")
	private Date returnWaterTime;
	/**
	 * 清理返水时间
	 */
	@Excel (name = "创建时间",height = 20,width = 20 ,isImportField = "true",exportFormat = "yyyy-MM-dd HH:mm:ss",orderNum = "21")
	private Date clearReturnWaterTime;
	/**
	 * 最后登录时间
	 */
	@Excel (name = "创建时间",height = 20,width = 20 ,isImportField = "true",exportFormat = "yyyy-MM-dd HH:mm:ss",orderNum = "22")
	private Date lastTime;
	/**
	 * 状态
	 */
	@Excel (name = "状态",height = 20,width = 20 ,isImportField = "true",orderNum = "7")
	private String state;
	/**
	 * 角色 10 超级管理员 20 代理
	 */
	private Integer roleType;
	/**
	 * 代理等级id
	 */
	@Excel (name = "代理等级id",height = 20,width = 20 ,isImportField = "true",orderNum = "16")
	private String agentLevelId;
	/**
	 * 今日返水：每日凌晨清零
	 */
	@Excel (name = "今日返水",height = 20,width = 20 ,isImportField = "true",orderNum = "8")
	private BigDecimal totayReturnWater;
	/**
	 * 累计返水
	 */
	@Excel (name = "累计返水",height = 20,width = 20 ,isImportField = "true",orderNum = "9")
	private BigDecimal totalReturnWater;
	/**
	 * 代理余额
	 */
	@Excel (name = "代理余额",height = 20,width = 20 ,isImportField = "true",orderNum = "10")
	private BigDecimal balance;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt
	 *            the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the userIcon
	 */
	public String getUserIcon() {
		return userIcon;
	}

	/**
	 * @param userIcon
	 *            the userIcon to set
	 */
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastTime
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * @param lastTime
	 *            the lastTime to set
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getAgentLevelId() {
		return agentLevelId;
	}

	public void setAgentLevelId(String agentLevelId) {
		this.agentLevelId = agentLevelId;
	}

	public BigDecimal getTotayReturnWater() {
		return totayReturnWater;
	}

	public void setTotayReturnWater(BigDecimal totayReturnWater) {
		this.totayReturnWater = totayReturnWater;
	}

	public BigDecimal getTotalReturnWater() {
		return totalReturnWater;
	}

	public void setTotalReturnWater(BigDecimal totalReturnWater) {
		this.totalReturnWater = totalReturnWater;
	}

	public Date getReturnWaterTime() {
		return returnWaterTime;
	}

	public void setReturnWaterTime(Date returnWaterTime) {
		this.returnWaterTime = returnWaterTime;
	}

	public Date getClearReturnWaterTime() {
		return clearReturnWaterTime;
	}

	public void setClearReturnWaterTime(Date clearReturnWaterTime) {
		this.clearReturnWaterTime = clearReturnWaterTime;
	}
}
