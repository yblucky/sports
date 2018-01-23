package com.pf.web.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pf.common.annotation.SystemControllerLog;
import com.pf.common.enums.RespCodeEnum;
import com.pf.common.po.AppBillRecordPo;
import com.pf.common.po.AppUserPo;

import com.pf.common.resp.Paging;
import com.pf.common.resp.RespBody;
import com.pf.common.util.LogUtils;
import com.pf.common.vo.pc.AppBillRecordVo1;
import com.pf.common.vo.pc.RechangeVo;
import com.pf.server.web.AppUserService;
import com.pf.server.web.WebBillRecordService;

/**
 * 
 * @author 充值
 *	zyc
 *  2018-01-13
 */


@RestController
@RequestMapping("/rechange")
public class RechangeController {
	
	@Resource
	private WebBillRecordService webBillRecordService;
	
	@Resource
	private AppUserService appUserService;
	
	
	
	
	/**
	 * 充值记录
	 * @param vo
	 * @param paging
	 * @return
	 */
	@GetMapping("/findAll")
	public RespBody findAll(AppBillRecordVo1 vo,Paging paging){
		RespBody respBody = new RespBody();
		try {
			vo.setBusnessType(11);
			//保存返回数据
			List<AppBillRecordVo1> list=webBillRecordService.findAll(vo, paging);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查询记录数据成功", list);
			//保存分页对象
			paging.setTotalCount(webBillRecordService.findCount(vo));
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查询记录数据失败");
			LogUtils.error("查找所有数据失败！",ex);
		}
		return respBody;
	}
	
	
	
	@PostMapping("/add")
	@SystemControllerLog(description="充值")
	public RespBody erroeState(@RequestBody RechangeVo vo){
		RespBody respBody = new RespBody();
		try {
			
			
			 
			//appWithDrawService.update(po);
			/**
			 * 修改用户冻结资金（冻结资金 = 冻结资金-提现金额   用户ep余额 = 用户ep余额 +提现金额 ）
			 */
			AppUserPo appuser = appUserService.findUid(vo.getUid().toString());
			
			if(appuser == null){
				respBody.add(RespCodeEnum.ERROR.getCode(), "用户查询不到");
			}else{
				BigDecimal before = appuser.getEpBalance();
				
				appuser.setEpBalance(appuser.getEpBalance().add(vo.getBalance()));
				appUserService.updateById(appuser,appuser.getId());
				
				
				AppBillRecordPo appBillRecordPo = new AppBillRecordPo();
				
				appBillRecordPo.setUserId(appuser.getId());
				appBillRecordPo.setBalance(vo.getBalance());
				appBillRecordPo.setBusinessNumber(appuser.getId());
				appBillRecordPo.setBeforeBalance(before);
				appBillRecordPo.setAfterBalance(appuser.getEpBalance());
				
				appBillRecordPo.setCurrencyType(10);
				appBillRecordPo.setBusnessType(11);
				appBillRecordPo.setRemark("给用户"+appuser.getUid()+"充值"+vo.getBalance()+"ep");
				webBillRecordService.add(appBillRecordPo);
					
				respBody.add(RespCodeEnum.SUCCESS.getCode(), "操作成功");
			}
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "操作成功");
			LogUtils.error("操作成功！",ex);
		}
		return respBody;
	}
	
	
	
	
	
	
	

}
