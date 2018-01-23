package com.xlf.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.CurrencyTypeEnum;
import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.AppBillRecordVo1;
import com.xlf.server.web.WebBillRecordService;



@RestController
@RequestMapping("/appBill")
public class WebBillRecordController {
	@Resource
	private WebBillRecordService appBillRecordService;
	
	@GetMapping("/findAll")
	public RespBody findAll(AppBillRecordVo1 vo,Paging paging){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			List<AppBillRecordVo1> list=appBillRecordService.findAll(vo, paging);
			for(AppBillRecordVo1 vo1:list){
				vo1.setBusnessTypeName(BusnessTypeEnum.getName(vo1.getBusnessType()));
				vo1.setCurrencyTypeName(CurrencyTypeEnum.getName(vo1.getCurrencyType()));
				
			}
	
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查询记录数据成功", list);
			//保存分页对象
			paging.setTotalCount(appBillRecordService.findCount(vo));
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查询记录数据失败");
			LogUtils.error("查找所有数据失败！",ex);
		}
		return respBody;
	}
	
	

	/**
	 * 查找参数类型数据
	 *
	 * @return 响应信息
	 */
	@GetMapping("/findBusnessType")
	public RespBody findBusnessType(){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			List<Map<String,String>> list=new ArrayList<Map<String,String>>();
			
			for(String str:appBillRecordService.getBusnessType()){
				Map map = new  HashMap<String,String>();
				map.put("key",str);
				map.put("val",BusnessTypeEnum.getName(Integer.parseInt(str)));
				list.add(map);
			}

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "参数类型查找所有数据成功",list);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "参数类型查找所有数据失败");
			LogUtils.error("参数类型查找所有数据失败！",ex);
		}
		return respBody;
	}
	
	/**
	 * 查找参数类型数据
	 *
	 * @return 响应信息
	 */
	@GetMapping("/findCurrencyType")
	public RespBody findCurrencyType(){
		RespBody respBody = new RespBody();
		try {
			//保存返回数据
			
			List<Map<String,String>> list=new ArrayList<Map<String,String>>();
			
			for(String str:appBillRecordService.getCurrencyType()){
				Map map = new  HashMap<String,String>();
				map.put("key",str);
				map.put("val",CurrencyTypeEnum.getName(Integer.parseInt(str)));
				list.add(map);
			}

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "参数类型查找所有数据成功", list);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "参数类型查找所有数据失败");
			LogUtils.error("参数类型查找所有数据失败！",ex);
		}
		return respBody;
	}
	
	
	
	
	
	

}
