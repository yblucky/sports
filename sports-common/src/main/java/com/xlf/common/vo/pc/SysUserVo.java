/* 
 * 文件名：SysUserVo.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月6日
 * 版本号：v1.0
*/
package com.xlf.common.vo.pc;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户Vo类
 * 
 * @author qsy
 * @version v1.0
 * @date 2017年6月6日
 */
public class SysUserVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1644042216784707890L;
	/**
	 * 主键编号
	 */
	private String id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 密码盐
	 */
	private String salt;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 角色编号
	 */
	private String roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 用户头像
	 */
	private String userIcon;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后登录时间
	 */
	private Date lastTime;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 代理等级ID
	 */
	private String agentLevelId;
	/**
	 * 角色类型
	 */
	private String roleType;

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
	 * @param roleId
	 *            the roleId to set
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
	 * @param roleName
	 *            the roleName to set
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

	public String getAgentLevelId() {
		return agentLevelId;
	}

	public void setAgentLevelId(String agentLevelId) {
		this.agentLevelId = agentLevelId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
}
