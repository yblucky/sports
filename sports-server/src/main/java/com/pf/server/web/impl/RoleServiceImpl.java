package com.pf.server.web.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pf.common.po.SysRolePermissionPo;
import com.pf.common.vo.pc.SysResourceVo;
import com.pf.common.vo.pc.SysRolePermissionVo;
import com.pf.common.vo.pc.SysRolePermissionVo.RolePermVo;
import com.pf.server.mapper.SysResourceMapper;
import com.pf.server.mapper.SysRolePermissionMapper;
import com.pf.server.web.RoleService;

/**
 * 权限管理业务层实现类
 * @author qsy
 * @version v1.0
 * @date 2017年6月21日
 */
@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private SysResourceMapper resMapper;
	@Resource
	private SysRolePermissionMapper rolePermMapper;


	@Override
	public List<SysResourceVo> findMenus(String roleId) {
		return resMapper.findRoleMenus(roleId);
	}


	@Override
	public void saveRolePerm(SysRolePermissionVo rolePerm) throws Exception {
		//删除数据
		rolePermMapper.deleteByRoleId(rolePerm.getRoleId());
		//保存数据
		for(RolePermVo spVo:rolePerm.getLis()){
			SysRolePermissionPo rolePermPo = new SysRolePermissionPo();
			rolePermPo.setRoleId(rolePerm.getRoleId());
			rolePermPo.setResourceId(spVo.getResourceId());
			rolePermMapper.insert(rolePermPo);
		}
	}
	
}
