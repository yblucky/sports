package com.pf.web.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pf.common.enums.RespCodeEnum;
import com.pf.common.resp.RespBody;
import com.pf.common.util.LogUtils;
import com.pf.common.vo.pc.SysRolePermissionVo;
import com.pf.server.web.RoleService;

/**
 * 权限管理控制器
 * @author qsy
 * @version v1.0
 * @date 2017年6月21日
 */
@RestController
@RequestMapping("/role")
public class RoleController {
	@Resource
	private RoleService roleService;
	
	/**
	 * 加载菜单集合
	 * @param roleId
	 * @return
	 */
	@GetMapping("/findMenus")
	public RespBody findMenus(String roleId){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有菜单数据成功", roleService.findMenus(roleId));
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有菜单数据失败");
			LogUtils.error("查找所菜单数据失败！",ex);
		}
		return respBody;
	}
	
	/**
	 * 保存角色权限关系
	 * @return
	 */
	@PostMapping("/saveRolePerm")
	public RespBody saveRolePerm(@RequestBody SysRolePermissionVo rolePerm){
		RespBody respBody = new RespBody();
		try {
			//调用业务层保存数据
			roleService.saveRolePerm(rolePerm);
			//返回客户端
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "保存角色权限成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "保存角色权限失败");
			LogUtils.error("保存角色权限失败！",ex);
		}
		return respBody;
	}
	
}
