/* 
 * 文件名：SysRoleVo.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月20日
 * 版本号：v1.0
*/
package com.xlf.common.vo.pc;

/**
 * 角色Vo类
 * 
 * @author qsy
 * @version v1.0
 * @date 2017年6月20日
 */
public class SysRoleVo {
	/**
	 * 主键编号
	 */
	private String id;
	/**
	 * 角色名
	 */
	private String roleName;

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

}
