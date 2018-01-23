package com.pf.server.mapper;

import com.pf.common.po.SysRolePermissionPo;
import com.pf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 角色权限DAO
 * @author qsy
 * @version v1.0
 * @date 2017年6月21日
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermissionPo> {

	/**
	 * @param roleId
	 */
	@Select("delete from sys_role_permission where roleId=#{roleId}")
	public void deleteByRoleId(@Param("roleId") String roleId);
	
}
