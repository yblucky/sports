package com.pf.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pf.common.enums.RespCodeEnum;
import com.pf.common.enums.StateEnum;
import com.pf.common.resp.Paging;
import com.pf.common.resp.RespBody;
import com.pf.common.service.RedisService;
import com.pf.common.util.ConfUtils;
import com.pf.common.util.CryptUtils;
import com.pf.common.util.LogUtils;
import com.pf.common.vo.pc.SysUserVo;
import com.pf.common.vo.pc.UpdatePwVo;
import com.pf.server.web.LoginService;
import com.pf.server.web.UserService;

/**
 * 用户控制器
 * @author qsy
 * @version v1.0
 * @date 2017年6月12日
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
	@Resource
	private UserService webUserService;//用户业务层
	@Resource
	private HttpServletRequest request;
	@Resource
	private ConfUtils confUtils;
	@Resource
	private RedisService redisService;
	@Resource(name="webLogin")
	private LoginService loginService;
	
	@GetMapping("/findUser")
	private RespBody findUser(){
		RespBody respBody = new RespBody();
		try {
			String token = request.getHeader("token");
			//读取用户信息
			SysUserVo userVo = webUserService.SysUserVo(token);
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
	public RespBody findAll(Paging paging){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有用户信息数据成功", webUserService.findAll(paging));
			//保存分页对象
			paging.setTotalCount(webUserService.findCount());
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有用户信息数据失败");
			LogUtils.error("查找所有用户信息数据失败！",ex);
		}
		return respBody;
	}
	
	@GetMapping("/findRoles")
	public RespBody findRoles(){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有角色数据成功", webUserService.findRoles());
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
			SysUserVo findUser = webUserService.findByLoginName(userVo.getLoginName());
			if(findUser == null){
				webUserService.add(userVo);
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
	
	@PostMapping("/update")
	public RespBody update(@RequestBody SysUserVo userVo){
		RespBody respBody = new RespBody();
		try {
			webUserService.update(userVo);
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
			webUserService.delete(userVo);
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
	
	@PostMapping("/disable")
	public RespBody disable(@RequestBody SysUserVo userVo){
		RespBody respBody = new RespBody();
		try {
			userVo.setState(String.valueOf(StateEnum.DISABLE.getCode()));
			webUserService.update(userVo);
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
			webUserService.update(userVo);
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
			SysUserVo userVo = webUserService.SysUserVo(token);
			// 对输入密码进行加密
			String oldPw = CryptUtils.hmacSHA1Encrypt(updatePwVo.getOldPw(), userVo.getSalt());
			if(userVo.getPassword().equals(oldPw)){
				//对新密码进行加密
				String newPw = CryptUtils.hmacSHA1Encrypt(updatePwVo.getNewPw(), userVo.getSalt());
				//旧密码正确，调用业务层执行密码更新
				webUserService.updatePw(newPw,userVo.getId());
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


	
}
