package com.pf.server.web;

import com.pf.common.resp.RespBody;
import com.pf.common.vo.pc.LoginVo;

/**
 * 用户登录业务层接口
 * 
 * @author qsy
 * @version v1.0
 * @date 2016年11月27日
 */
public interface LoginService {

	/**
	 * 用户登录
	 * @param loginVo 登录信息
	 * @return
	 * @throws Exception
	 */
	public RespBody LoginIn(LoginVo loginVo) throws Exception;
	

	/**
	 * 用户登出
	 * @param loginVo 登录信息
	 * @return
	 * @throws Exception
	 */
	public RespBody LoginOut(String token) throws Exception;
}
