package com.xlf.common.po;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色权限Po类
 * @author qsy
 * @version v1.0
 * @date 2017年6月21日
 */
@Table(name = "sys_role_permission")
public class SysRolePermissionPo {
	/**
	 * 角色编号
	 */
	@Id
	private String roleId;
	/**
	 * 资源编号
	 */
	@Id
	private String resourceId;
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
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	
}
