/* 
 * 文件名：MainController.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月14日
 * 版本号：v1.0
*/
package com.xlf.web.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.AppBankCardVo;
import com.xlf.server.web.BankCardService;
import com.xlf.server.web.BankTypeService;

/**
 * 银行卡控制器
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
@RestController
@RequestMapping(value = "/bankcard")
public class BankCardController {
	@Resource
	private BankCardService bankCardService;
	@Resource
	private BankTypeService bankTypeService;
	
	/**
	 * 加载银行卡类型
	 * @return 响应对象
	 */
	@GetMapping("/allBankType")
	public RespBody allBankType(){
		RespBody respBody = new RespBody();
		try {
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载银行成功",bankTypeService.getall());
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "加载银行失败");
			LogUtils.error("加载银行失败！",ex);
		}
		return respBody;
	}
	
	/**
	 * 加载银行卡数据
	 * @return 响应对象
	 */
	@GetMapping("/bankCardInfo")
	public RespBody bankCardInfo(AppBankCardVo vo,Paging paging){
		RespBody respBody = new RespBody();
		try {
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载银行成功",bankCardService.getPoList(vo, paging));
			paging.setTotalCount(bankCardService.getPoList(vo,null).size());
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "加载银行失败");
			LogUtils.error("加载银行失败！",ex);
		}
		return respBody;
	}
}
