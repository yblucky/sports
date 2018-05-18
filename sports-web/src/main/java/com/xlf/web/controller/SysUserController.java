package com.xlf.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.xlf.common.enums.RoleTypeEnum;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.po.SysUserPo;
import com.xlf.common.util.ToolUtils;
import com.xlf.server.app.AppUserService;
import com.xlf.server.app.SysAgentSettingService;
import com.xlf.server.common.CommonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.enums.StateEnum;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.service.RedisService;
import com.xlf.common.util.ConfUtils;
import com.xlf.common.util.CryptUtils;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.common.vo.pc.UpdatePwVo;
import com.xlf.server.web.LoginService;
import com.xlf.server.web.SysUserService;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * 用户控制器
 * @author qsy
 * @version v1.0
 * @date 2017年6月12日
 */
@RestController
@RequestMapping(value = "/sysUser")
public class SysUserController {
	@Resource
	private SysUserService sysUserService;//用户业务层
	@Resource
	private HttpServletRequest request;
	@Resource
	private ConfUtils confUtils;
	@Resource
	private RedisService redisService;
	@Resource
	private CommonService commonService;
	@Resource
	private SysAgentSettingService sysAgentSettingService;
	@Resource
	private AppUserService appUserService;
	@Resource(name="webLogin")
	private LoginService loginService;
	
	@GetMapping("/findUser")
	private RespBody findUser(){
		RespBody respBody = new RespBody();
		try {
			String token = request.getHeader("token");
			//读取用户信息
			SysUserVo userVo = sysUserService.SysUserVo(token);
			//用户是否存在
			if(userVo != null){
				userVo.setPassword("");
				userVo.setSalt("");
				//存在
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取用户信息成功",userVo);
			}else{
				//不存在
				respBody.add(RespCodeEnum.ERROR.getCode(), "获取用户信息失败");
			}
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "获取用户信息异常");
			LogUtils.error("获取用户信息异常！",ex);
		}
		return respBody;
	}

	@GetMapping("/findAll")
	public RespBody findAll(Paging paging,SysUserVo vo){
		RespBody respBody = new RespBody();
		try {
			SysUserVo userVo = commonService.checkWebToken();
			if(RoleTypeEnum.AGENT.getCode().equals(userVo.getRoleType())){
				vo.setId(userVo.getId());
			}
			long total =sysUserService.findCount(vo);
			if(total >0) {
				//保存返回数据
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有用户信息数据成功", sysUserService.findAll(paging, vo));
				//保存分页对象
				paging.setTotalCount(total);
				respBody.setPage(paging);
			}else {
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有用户信息数据成功", Collections.emptyList());
			}
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有用户信息数据失败");
			LogUtils.error("查找所有用户信息数据失败！",ex);
		}
		return respBody;
	}

	@GetMapping("/loadAgentSetting")
	public RespBody loadAgentSetting(){
		RespBody respBody = new RespBody();
		try {
			commonService.checkWebToken();
			//保存返回数据
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载代理等级参数成功", sysAgentSettingService.loadAgentSetting());
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "加载代理等级参数失败");
			LogUtils.error("查找所有用户信息数据失败！",ex);
		}
		return respBody;
	}
	
	@GetMapping("/findRoles")
	public RespBody findRoles(){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有角色数据成功", sysUserService.findRoles());
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有角色数据失败");
			LogUtils.error("查找所有角色数据失败！",ex);
		}
		return respBody;
	}
	
	@PostMapping("/add")
	public RespBody add(@RequestBody SysUserVo userVo){
		RespBody respBody = new RespBody();
		try {
			//判断用户是否存在
			SysUserVo findUser = sysUserService.findByLoginName(userVo.getLoginName());
			if(findUser == null){
				if(userVo.getRoleType()!= null && userVo.getRoleType().intValue() ==20){
					//默认角色就是代理
					userVo.setRoleId("1001");
				}else {
					userVo.setRoleType(RoleTypeEnum.ADMIN.getCode());
				}
				sysUserService.add(userVo);
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "用户信息保存成功");
			}else{
				respBody.add(RespCodeEnum.ERROR.getCode(), "登录名已经存在");
			}
			
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户信息保存失败");
			LogUtils.error("用户信息保存失败！",ex);
		}
		return respBody;
	}

	@PostMapping("/addAgentLevel")
	public RespBody addAgentLevel(@RequestBody SysAgentSettingPo vo){
		RespBody respBody = new RespBody();
		try {
			//判断用户是否存在
			commonService.checkWebToken();
			sysAgentSettingService.add(vo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "用户信息保存成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户信息保存失败");
			LogUtils.error("用户信息保存失败！",ex);
		}
		return respBody;
	}
	
	@PostMapping("/update")
	public RespBody update(@RequestBody SysUserVo userVo){
		RespBody respBody = new RespBody();
		try {
			sysUserService.update(userVo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "用户信息修改成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户信息修改失败");
			LogUtils.error("用户信息修改失败！",ex);
		}
		return respBody;
	}

	@PostMapping("/updateAgentLevel")
	public RespBody updateAgentLevel(@RequestBody SysAgentSettingPo vo){
		RespBody respBody = new RespBody();
		try {
			sysAgentSettingService.update(vo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "用户信息修改成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户信息修改失败");
			LogUtils.error("用户信息修改失败！",ex);
		}
		return respBody;
	}
	
	@PostMapping("/delete")
	public RespBody delete(@RequestBody SysUserVo userVo){
		RespBody respBody = new RespBody();
		try {
			sysUserService.delete(userVo);
			//取出用户Token  退出其登录
			String outToken = redisService.getString(userVo.getId());
			if(null!=outToken&&!"".equals(outToken)){
				loginService.LoginOut(outToken);
			}
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "用户信息删除成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户信息删除失败");
			LogUtils.error("用户信息删除失败！",ex);
		}
		return respBody;
	}
	@PostMapping("/deleteAgentLevel")
	public RespBody deleteAgentLevel(@RequestBody SysAgentSettingPo vo){
		RespBody respBody = new RespBody();
		try {
			sysAgentSettingService.delete(vo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "用户信息删除成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户信息删除失败");
			LogUtils.error("用户信息删除失败！",ex);
		}
		return respBody;
	}
	
	@PostMapping("/disable")
	public RespBody disable(@RequestBody SysUserVo userVo){
		RespBody respBody = new RespBody();
		try {
			SysUserVo sysUser = sysUserService.findById(userVo.getId());
			//禁用代理需要同时禁用会员
			if(sysUser!=null && sysUser.getRoleType().intValue() == RoleTypeEnum.AGENT.getCode().intValue()){
				appUserService.updateStateByParentId(StateEnum.DISABLE.getCode(),sysUser.getId());
			}
			userVo.setState(String.valueOf(StateEnum.DISABLE.getCode()));
			sysUserService.update(userVo);
			//取出用户Token  退出其登录
			String outToken = redisService.getString(userVo.getId());
			if(null!=outToken&&!"".equals(outToken)){
				loginService.LoginOut(outToken);
			}
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "用户禁用成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户信息删除失败");
			LogUtils.error("用户禁用失败！",ex);
		}
		return respBody;
	}
	
	@PostMapping("/enabled")
	public RespBody enabled(@RequestBody SysUserVo userVo){
		RespBody respBody = new RespBody();
		try {
			userVo.setState(String.valueOf(StateEnum.NORMAL.getCode()));
			sysUserService.update(userVo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "用户启用成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户启用失败");
			LogUtils.error("用户禁用失败！",ex);
		}
		return respBody;
	}
	
	@PostMapping("/updatePw")
	public RespBody updatePw(@RequestBody UpdatePwVo updatePwVo){
		RespBody respBody = new RespBody();
		try {
			if(!updatePwVo.getNewPw().equals(updatePwVo.getOkPw())){
				respBody.add(RespCodeEnum.ERROR.getCode(), "新密码和确认码不一致");
				return respBody;
			}
			String token = request.getHeader("token");
			//读取用户信息
			SysUserVo userVo = sysUserService.SysUserVo(token);
			// 对输入密码进行加密
			String oldPw = CryptUtils.hmacSHA1Encrypt(updatePwVo.getOldPw(), userVo.getSalt());
			if(userVo.getPassword().equals(oldPw)){
				//对新密码进行加密
				String newPw = CryptUtils.hmacSHA1Encrypt(updatePwVo.getNewPw(), userVo.getSalt());
				//旧密码正确，调用业务层执行密码更新
				sysUserService.updatePw(newPw,userVo.getId());
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "修改密码成功");
				//更新redis数据
				userVo.setPassword(newPw);
				redisService.putObj(token, userVo, confUtils.getSessionTimeout());
			}else{
				//旧密码输入有误
				respBody.add(RespCodeEnum.ERROR.getCode(), "旧密码输入不正确");
			}
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "修改密码失败");
			LogUtils.error("修改密码失败！",ex);
		}
		return respBody;
	}

	/**
	 * 充值积分
	 *
	 * @return 响应对象
	 */
	@PostMapping("/recharge")
	public RespBody recharge(@RequestBody SysUserVo vo) {
		RespBody respBody = new RespBody();
		try {
			SysUserVo token = commonService.checkWebToken();
			if (token.getRoleType().intValue() == RoleTypeEnum.AGENT.getCode()) {
					respBody.add(RespCodeEnum.ERROR.getCode(), "不符合权限");
					return respBody;
			}
			SysUserVo find = sysUserService.findByMobile(vo.getMobile());
			if (null == find) {
				respBody.add(RespCodeEnum.ERROR.getCode(), "找不到用户");
				return respBody;
			}
			sysUserService.recharge(find,vo.getBalance());
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "充值成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "充值失败");
			LogUtils.error("充值失败", ex);
		}
		return respBody;
	}


	/**
	 * 修改登陆密码
	 *
	 * @return 响应对象
	 */
	@PostMapping("/upPwd")
	public RespBody upPwd(@RequestBody SysUserVo po) {
		RespBody respBody = new RespBody();
		try {
			SysUserVo token = commonService.checkWebToken();
			if (token.getRoleType().intValue() == RoleTypeEnum.AGENT.getCode()) {
				respBody.add(RespCodeEnum.ERROR.getCode(), "不符合权限");
				return respBody;
			}
			SysUserVo find = sysUserService.findByLoginName(po.getMobile());
			if (null == find) {
				find = sysUserService.findByMobile(po.getMobile());
			}
			if (null == find) {
				respBody.add(RespCodeEnum.ERROR.getCode(), "找不到用户");
				return respBody;
			}
			//根据旧盐加密登录密码
			String loginPw = CryptUtils.hmacSHA1Encrypt(po.getPassword(), find.getSalt());
			//调用通用的更新方法
			sysUserService.updatePw(loginPw,find.getId());
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "修改密码成功");
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "修改密码失败");
			LogUtils.error("修改密码失败", ex);
		}
		return respBody;
	}
}
