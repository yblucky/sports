package com.xlf.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xlf.common.annotation.SystemControllerLog;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.LoginVo;
import com.xlf.server.common.KaptchaService;
import com.xlf.server.web.LoginService;

/**
 * 用户登陆控制器
 * 
 * @author qsy
 * @version v1.0
 * @date 2016年11月27日
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {
	@Resource(name="webLogin")
	private LoginService loginService;
	@Resource
	private KaptchaService kaptchaService;

	/**
	 * 用户登录
	 * @param loginVo 接收Vo
	 * @return 响应对象
	 */
	@PostMapping("/loginIn")
	@SystemControllerLog(description="用户登录")
	public RespBody longIn(@RequestBody LoginVo loginVo) {
		// 创建返回对象
		RespBody respBody = new RespBody();
		try {
			//验证FormBean
			if(hasErrors(loginVo,respBody)){
				// 验证通过，调用业务层，实现登录验证处理
				respBody = loginService.LoginIn(loginVo);
			}
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户登录失败");
			LogUtils.error("用户登录失败！",ex);
		}
		return respBody;
	}
	
	/**
	 * 加载图片验证码
	 * @return
	 */
	@GetMapping("/loadImgCode")
	public RespBody loadImgCode() {
		// 创建返回对象
		RespBody respBody = new RespBody();
		try {
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "获取图片验证码成功", kaptchaService.createCodeImg());
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "获取图片验证码失败");
			LogUtils.error("用户登录失败！",ex);
		}
		return respBody;
	}
	
	/**
	 * 退出登录
	 * @param loginVo 接收Vo
	 * @return 响应对象
	 */
	@PostMapping("/loginOut")
	@SystemControllerLog(description="退出登录")
	public RespBody loginOut(HttpServletRequest request) {
		String token=request.getHeader("token");
		RespBody respBody = new RespBody();
		try {
			respBody = loginService.LoginOut(token);
		} catch (Exception e) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户登出失败");
			LogUtils.error("用户登录出败！",e);
		}
		return respBody;
	}
	
	/**
	 * 验证formBean
	 * @param loginVo
	 * @param respBody
	 * @return
	 */
	private boolean hasErrors(LoginVo loginVo,RespBody respBody){
		if(StringUtils.isEmpty(loginVo.getLoginName())){
			respBody.add(RespCodeEnum.ERROR.getCode(), "用户名不能为空！");
			return false;
		}
		if(StringUtils.isEmpty(loginVo.getPassword())){
			respBody.add(RespCodeEnum.ERROR.getCode(), "密码不能为空！");
			return false;
		}
		if(StringUtils.isEmpty(loginVo.getPicCode())){
			respBody.add(RespCodeEnum.ERROR.getCode(), "验证码不能为空！");
			return false;
		}
		if(loginVo.getRoleType() == null){
			respBody.add(RespCodeEnum.ERROR.getCode(), "参数不合法！");
			return false;
		}
		return true;
	}

}
