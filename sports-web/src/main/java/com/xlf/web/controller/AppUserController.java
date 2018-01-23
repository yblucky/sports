/* 
 * 文件名：MainController.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月14日
 * 版本号：v1.0
*/
package com.xlf.web.controller;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.enums.StateEnum;
import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.service.RedisService;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.app.BankCardVo;
import com.xlf.common.vo.pc.AppUserContactVo;
import com.xlf.common.vo.pc.LevelCompartor;
import com.xlf.server.web.AppUserContactService;
import com.xlf.server.web.WebBillRecordService;
import com.xlf.server.mapper.AppBillRecordMapper;
import com.xlf.server.web.AppUserService;
import com.xlf.server.web.BankCardService;
import com.xlf.server.web.LoginService;

import tk.mybatis.mapper.util.StringUtil;

/**
 * 用户管理控制器
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
@RestController
@RequestMapping(value = "/appUser")
public class AppUserController {
	@Resource
	private AppUserService webAppUserService;
	@Resource
	private RedisService redisService;
	@Resource(name="webLogin")
	private LoginService loginService;
	@Resource
    private AppBillRecordMapper appBillRecordMapper;
	@Resource
	private BankCardService  bankCardService;
	
	@Resource
	private AppUserContactService appUserContactService;
	@Resource
	private WebBillRecordService webBillRecordService;
	//private AppBillRecordService appUserContactService;
	
	/**
	 * 加载用户菜单
	 * @param roleId 权限编号
	 * @return 响应对象
	 */
	@GetMapping("/userTab")
	public RespBody userTab(AppUserPo po,Paging paging){
		RespBody respBody = new RespBody();
		try {
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载列表成功",webAppUserService.getPoList(po, paging));
			paging.setTotalCount(webAppUserService.findPoListCount(po));
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "加载列表失败");
			LogUtils.error("加载列表失败！",ex);
		}
		return respBody;
	}
	
	@GetMapping("/findUid")
	public RespBody findUser(AppUserPo po,Paging paging){
		RespBody respBody = new RespBody();
		try {
			AppUserPo appUserPo =webAppUserService.findUid(po.getUid().toString());
			if(appUserPo == null){
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "该用户不存在", appUserPo);	
			}else{
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载列表成功",appUserPo);
			}
			paging.setTotalCount(webAppUserService.findPoListCount(po));
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "加载列表失败");
			LogUtils.error("加载列表失败！",ex);
		}
		return respBody;
	}
	
	
	
	
	/**
	 * 加载用户币种钱包菜单
	 * 
	 * @return 响应对象
	 */
/*	@GetMapping("/appUserCoinAddress")
	public RespBody appUserCoinAddress(AppUserCoinAddress appUserCoinAddress,Paging paging){
		RespBody respBody = new RespBody();
		try {
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载列表成功",webAppUserService.appUserCoinAddress(appUserCoinAddress, paging));
			paging.setTotalCount(webAppUserService.appUserCoinAddress(appUserCoinAddress,null).size());
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "加载列表失败");
			LogUtils.error("加载列表失败！",ex);
		}
		return respBody;
	}*/

	
	/**
	 * 禁用启用(删除)用户
	 * @return 响应对象
	 */
	@PostMapping("/upUserState")
	public RespBody upUserState(@RequestBody AppUserPo po){
		RespBody respBody = new RespBody();
		try {
			AppUserPo find=webAppUserService.findUserById(po.getId());
			if(null==find){
				respBody.add(RespCodeEnum.ERROR.getCode(), "用户错误");
			}
			if((StateEnum.NORMAL.getCode().equals(find.getState())||StateEnum.DISABLE.getCode().equals(find.getState()) ||StateEnum.NO_ACTIVE.getCode().equals(find.getState()))){
				int count=webAppUserService.updateById(po, po.getId());
				if(count>0){
					if(StateEnum.DISABLE.getCode().equals(po.getState())){
						//取出用户Token  退出其登录
						String token_key = redisService.getString(po.getId());
		                if(!org.apache.commons.lang3.StringUtils.isEmpty(token_key)){
		                    //删除token_key值
		                    redisService.del(token_key);
		                }
					}
					respBody.add(RespCodeEnum.SUCCESS.getCode(), "修改成功");
				}else{
					respBody.add(RespCodeEnum.ERROR.getCode(), "修改失败");
				}
			}else{
				respBody.add(RespCodeEnum.ERROR.getCode(), "用户状态不正常");
			}
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "修改失败");
			LogUtils.error("修改用户状态失败",ex);
		}
		return respBody;
	}
	
	
	
	/**
	 * 修改用户激活次数
	 * @return 响应对象
	 */
	@PostMapping("/updateActiceVoCount")
	public RespBody updateActiceVoCount(@RequestBody AppUserPo po){
		RespBody respBody = new RespBody();
		try {
			AppUserPo find=webAppUserService.findUid(po.getUid().toString());
			
			if(null==find){
				respBody.add(RespCodeEnum.ERROR.getCode(), "用户错误");
			}
			if(find.getState() == 10){
				respBody.add(RespCodeEnum.ERROR.getCode(), "未激活用户，无法添加激活次数");
			}else{
			
			
			
				int count=webAppUserService.updateActiveNoCount(po.getActiveNo(),find.getId());
				if(count>0){
					
					
					//流水记录
					AppBillRecordPo appBillRecordPo = new AppBillRecordPo();
					
					appBillRecordPo.setUserId(find.getId());
					appBillRecordPo.setBalance(new BigDecimal(po.getActiveNo()));
					appBillRecordPo.setBusinessNumber(find.getId());
					appBillRecordPo.setBeforeBalance(new BigDecimal(find.getActiveNo()));
					appBillRecordPo.setAfterBalance(new BigDecimal(find.getActiveNo()+po.getActiveNo()));
					
					appBillRecordPo.setCurrencyType(40);
					appBillRecordPo.setBusnessType(23);
					appBillRecordPo.setRemark("充值激活次数");
					webBillRecordService.add(appBillRecordPo);

					
					if(StateEnum.DISABLE.getCode().equals(po.getState())){
						//取出用户Token  退出其登录
						String token_key = redisService.getString(find.getId());
		                if(!org.apache.commons.lang3.StringUtils.isEmpty(token_key)){
		                    //删除token_key值
		                    redisService.del(token_key);
		                }
					}
					respBody.add(RespCodeEnum.SUCCESS.getCode(), "修改成功");
				}else{
					respBody.add(RespCodeEnum.ERROR.getCode(), "修改失败");
				}
			}	
			
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "修改失败");
			LogUtils.error("修改用户状态失败",ex);
		}
		return respBody;
	}
	
	/**
	 * 修改用户姓名
	 * @return 响应对象
	 */
	@PostMapping("/upUserName")
	public RespBody upUserName(@RequestBody AppUserPo po){
		RespBody respBody = new RespBody();
		try {
			if(StringUtil.isEmpty(po.getName())){
				respBody.add(RespCodeEnum.ERROR.getCode(), "姓名错误!");
			}
			AppUserPo find=webAppUserService.findUserById(po.getId());
			if(null==find){
				respBody.add(RespCodeEnum.ERROR.getCode(), "用户错误");
			}
			if((StateEnum.NORMAL.getCode().equals(find.getState())||StateEnum.DISABLE.getCode().equals(find.getState())||StateEnum.NO_ACTIVE.getCode().equals(find.getState()))){
				AppUserPo upPo=new AppUserPo();
				upPo.setName(po.getName());
				int count=webAppUserService.updateById(upPo, po.getId());
				if(count>0){
					BankCardVo bankCardVo = new BankCardVo();
					bankCardVo.setName(po.getName());
					bankCardVo.setUserId(po.getId());
					bankCardService.update(bankCardVo);
					respBody.add(RespCodeEnum.SUCCESS.getCode(), "修改成功");
				}else{
					respBody.add(RespCodeEnum.ERROR.getCode(), "修改失败");
				}
			}else{
				respBody.add(RespCodeEnum.ERROR.getCode(), "用户状态不正常");
			}
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "修改失败");
			LogUtils.error("修改用户状态失败",ex);
		}
		return respBody;
	}
	
	/**
	 * 获取用户的上级
	 */
	@GetMapping("/loadParent")
	public RespBody loadParent(String id){
		RespBody respBody = new RespBody();
		try {
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载数据成功",webAppUserService.findUserById(id));
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "加载列表失败");
			LogUtils.error("加载列表失败！",ex);
		}
		return respBody;
	}
	
	
	
	/**
	 * 获取用户的下级
	 */
	@GetMapping("/loadLower")
	public RespBody loadLower(String parentId){
		RespBody respBody = new RespBody();
		try {
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载数据成功",webAppUserService.getLowerPoList(parentId));
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "加载列表失败");
			LogUtils.error("加载列表失败！",ex);
		}
		return respBody;
	}
	
	
	/**
	 * 
	 * @param po
	 * @param paging
	 * @return
	 */
	@GetMapping("/userParentId")
	public RespBody userParentId(AppUserPo po,Paging paging){
		RespBody respBody = new RespBody();
		List<AppUserContactVo> resultUsers= new ArrayList<>();
		try {
			if(po.getUid() == null){
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "");
				
			}else{
				AppUserPo user =  webAppUserService.findUid(po.getUid().toString());
				
				
				if(null==user){
					respBody.add(RespCodeEnum.SUCCESS.getCode(), "找不到该uid");
				}else{
						
					
					List<AppUserContactVo> list1 = appUserContactService.webFindUserByParentId(user.getId());
					
					resultUsers.add(appUserContactService.webfindUserByUserId(user.getId()));
					
					if (!CollectionUtils.isEmpty(list1)){
						//第一层
						for(AppUserContactVo vo1:list1){
							resultUsers.add(vo1);
							List<AppUserContactVo> list2 = appUserContactService.webFindUserByParentId(vo1.getUserId());
							if (!CollectionUtils.isEmpty(list2)){
								//第二层	
								
								for (AppUserContactVo vo2:list2){
									resultUsers.add(vo2);
									//第三层
									List<AppUserContactVo> list3 = appUserContactService.webFindUserByParentId(vo2.getUserId());
									if (!CollectionUtils.isEmpty(list3)){
										//第四层	
										
										for (AppUserContactVo vo3:list3){
											resultUsers.add(vo3);
											//第五层
											List<AppUserContactVo> list4 = appUserContactService.webFindUserByParentId(vo3.getUserId());
											if (!CollectionUtils.isEmpty(list4)){
												for (AppUserContactVo vo4:list4){
													resultUsers.add(vo4);	
												}
											}
											
										}
									}
									
								}
							}
							
							
						}
					}
					 
					if (resultUsers.size()>0) {
						Collections.sort(resultUsers, new LevelCompartor());;
						
					} 
				}
				
				
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载列表成功",resultUsers);
				paging.setTotalCount(resultUsers.size());
				respBody.setPage(paging);
				
			}
			
			paging.setTotalCount(webAppUserService.findPoListCount(po));
			respBody.setPage(paging);
			
			
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "加载列表失败");
			LogUtils.error("加载列表失败！",ex);
		}
		return respBody;
	}
	
	
	
	
	
	
}

