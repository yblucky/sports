package com.pf.server.web;

import java.util.List;

import com.pf.common.vo.pc.SysResourceVo;
import com.pf.common.vo.pc.SysRolePermissionVo;

/**
 * 权限管理业务层接口
 * @author qsy
 * @version v1.0
 * @date 2017年6月21日
 */
public interface RoleService {

	/**
	 * 获取所有菜单列表
	 * @param roleId 
	 * @return
	 */
	public List<SysResourceVo> findMenus(String roleId);

	/**
	 * 保存角色权限关系
	 * @param rolePerms
	 * @return
	 * @throws Exception 
	 */
	public void saveRolePerm(SysRolePermissionVo rolePerm) throws Exception;

}
