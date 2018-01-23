/* 
 * 文件名：SysRolePermissionVo.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月21日
 * 版本号：v1.0
*/
package com.pf.common.vo.pc;

import java.util.List;

/**
 * 角色权限Vo类
 * @author qsy
 * @version v1.0
 * @date 2017年6月21日
 */
public class SysRolePermissionVo {
	/**
	 * 角色编号
	 */
	private String roleId;
	/**
	 * 菜单集合
	 */
	private List<RolePermVo> lis;
	
	/**
	 * 
	 * 封装对象
	 * @author qsy
	 * @version v1.0
	 * @date 2017年6月21日
	 */
	public static class RolePermVo{
		/**
		 * 资源编号数组
		 */
		private String resourceId;

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
	 * @return the lis
	 */
	public List<RolePermVo> getLis() {
		return lis;
	}
	/**
	 * @param lis the lis to set
	 */
	public void setLis(List<RolePermVo> lis) {
		this.lis = lis;
	}
}
