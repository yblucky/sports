/* 
 * 文件名：MainController.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：邱深友  
 * 创建时间：2017年6月14日
 * 版本号：v1.0
*/
package com.xlf.web.controller;

import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.AppBankCardVo;
import com.xlf.common.vo.pc.LotteryVo;
import com.xlf.server.app.AppRacingBettingService;
import com.xlf.server.common.CommonService;
import com.xlf.server.web.WebBankCardService;
import com.xlf.server.web.WebBankTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 财务控制器
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
@RestController
@RequestMapping(value = "/finance")
public class FinanceController {
	@Resource
	private AppRacingBettingService  appRacingBettingService;
	@Resource
	private CommonService commonService;
	
	/**
	 * 加载银行卡类型
	 * @return 响应对象
	 */
	@GetMapping("/findAll")
	public RespBody findAll(LotteryVo vo, Paging paging){
		RespBody respBody = new RespBody();
		try {
			respBody.add(RespCodeEnum.SUCCESS.getCode(), "加载银行成功",appRacingBettingService.findAll(vo,paging));
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "加载银行失败");
			LogUtils.error("加载银行失败！",ex);
		}
		return respBody;
	}


}




