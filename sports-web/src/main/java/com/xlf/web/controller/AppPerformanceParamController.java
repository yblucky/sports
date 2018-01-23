package com.xlf.web.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.po.AppPerformanceParamPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.AppPerformanceRecordVo;
import com.xlf.server.web.AppPerformanceParamService;
import com.xlf.server.web.AppPerformanceRecordService;

@RestController
@RequestMapping(value = "/appPerformance")
public class AppPerformanceParamController {
	
	@Resource
	private AppPerformanceParamService appPerformanceParamService;
	
	@Resource
	private AppPerformanceRecordService appPerformanceRecordService;
	
	@GetMapping("/findAll")
	public RespBody findAll(Paging paging){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有用户业绩等级信息数据成功", appPerformanceParamService.findAll(paging));
			//保存分页对象
			paging.setTotalCount(appPerformanceParamService.findCount());
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有用户业绩信息数据失败");
			LogUtils.error("查找所有用户业绩等级信息数据失败！",ex);
		}
		return respBody;
	}
	
	
	@PostMapping("/delete")
	public RespBody delete(@RequestBody AppPerformanceParamPo appPerformanceParamPo){
		RespBody respBody = new RespBody();
		try{
			appPerformanceParamService.delete(appPerformanceParamPo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "业绩删除成功");
		}catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "业绩删除失败");
			LogUtils.error("业绩删除失败！",ex);
		}
		
		return respBody;
	}
	
	@PostMapping("/update")
	public RespBody update(@RequestBody AppPerformanceParamPo appPerformanceParamPo){
		RespBody respBody = new RespBody();
		try{
			appPerformanceParamService.update(appPerformanceParamPo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "业绩修改成功");
		}catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "业绩修改失败");
			LogUtils.error("业绩修改失败！",ex);
		}
		
		return respBody;
	}
	
	
	@PostMapping("/add")
	public RespBody add(@RequestBody AppPerformanceParamPo appPerformanceParamPo){
		RespBody respBody = new RespBody();
		try{
			appPerformanceParamService.add(appPerformanceParamPo);
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "业绩添加成功");
		}catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "业绩添加失败");
			LogUtils.error("业绩添加失败！",ex);
		}
		
		return respBody;
	}
	
	
	
	@GetMapping("/findAllRerocd")
	public RespBody findAllRerocd(AppPerformanceRecordVo vo  ,Paging paging){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查找所有用户业绩记录数据成功", appPerformanceRecordService.findAll(vo,paging));
			//保存分页对象
			paging.setTotalCount(appPerformanceRecordService.findCount(vo));
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查找所有用户业绩记录数据失败");
			LogUtils.error("查找所有用户业绩记录信息数据失败！",ex);
		}
		return respBody;
	}
	
	

}
