package com.xlf.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xlf.common.enums.RespCodeEnum;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.util.LogUtils;
import com.xlf.common.vo.pc.WebBillRecordVo;
import com.xlf.common.vo.pc.AppWithDrawVo;
import com.xlf.server.web.WebUserService;
import com.xlf.server.web.WebWithDrawService;
import com.xlf.server.web.WebBillRecordService;

/**
 * 
 * @author 首页统计
 *	zyc
 *  2018-01-13
 */


@RestController
@RequestMapping("/home")
public class HomeController {
	
	
	@Resource
	private WebBillRecordService webBillRecordService;
	
	@Resource
	private WebWithDrawService webWithDrawService;
	@Resource
	private WebUserService appUserService;
	/**
	 * 首页记录
	 * @param vo
	 * @param paging
	 * @return
	 */
	@GetMapping("/findAll")
	public RespBody findAll(WebBillRecordVo vo, Paging paging){
		RespBody respBody = new RespBody();
		try {
			Map<String,Object> result=new HashMap<String, Object>();
			
			
			/**
			 * 今日统计
			 */
			vo.setStartTime(getStarttime(new Date().getTime()));
			
			//今日充值
			vo.setBusnessType(11);
			BigDecimal recharge = webBillRecordService.SUMCount(vo);
			result.put("recharge",recharge);
			
			
			
			
			
			
			
			
			//ep转账
			vo.setBusnessType(12);
			BigDecimal ep = webBillRecordService.SUMCount(vo);
			result.put("epTran",ep);
			
			
			//候鸟积分
			
			vo.setBusnessType(20);
			BigDecimal birdScore = webBillRecordService.SUMCount(vo);
			result.put("birdScore",birdScore);
			
			//E资产释放
			WebBillRecordVo vo2 = new WebBillRecordVo();
			vo2.setBusnessType(18);
			vo2.setStartTime(getStarttime(new Date().getTime()));
			BigDecimal epRelease = webBillRecordService.SUMCount(vo2);
			result.put("epRelease",epRelease.abs());
			
			//今日注册用户
			AppUserPo appuser = new AppUserPo();
			appuser.setCreateTime(getStarttime(new Date().getTime()));
			result.put("registerUserCount",appUserService.SUMCount(appuser));
			//今日激活用户
			appuser.setState(20);
			appuser.setCreateTime(getStarttime(new Date().getTime()));
			result.put("activeUserCount",appUserService.SUMCount(appuser));
			
			
			//今日ep转账记录
			WebBillRecordVo vo4 = new WebBillRecordVo();
			vo4.setBusnessType(12);
			vo4.setStartTime(getStarttime(new Date().getTime()));
			
			result.put("todayEpTranCount",webBillRecordService.findCount(vo4));
			//今日ep释放记录
			WebBillRecordVo vo5 = new WebBillRecordVo();
			vo5.setBusnessType(18);
			vo5.setStartTime(getStarttime(new Date().getTime()));
			result.put("todayEpReleaseCount",webBillRecordService.findCount(vo5));
			
			//今日ep充值记录
			WebBillRecordVo vo6 = new WebBillRecordVo();
			
			vo6.setBusnessType(11);
			vo6.setStartTime(getStarttime(new Date().getTime()));
			result.put("rechargeCount",webBillRecordService.findCount(vo6));
			

			
			/**
			 * 统计全部
			 */

			//提现笔数
			AppWithDrawVo appWithDrawVo = new AppWithDrawVo();
			appWithDrawVo.setState(10);
			//appWithDrawVo.setStartTime(getStarttime(new Date().getTime()));
			
			result.put("appWidthDraw", webWithDrawService.findCount(appWithDrawVo));
			
			//提现总数
			appWithDrawVo.setState(20);
			result.put("appWidthDrawSUM", webWithDrawService.findSUM(appWithDrawVo));
			
			//用户统计（ep,候鸟积分，e资产，用户数量）
			result.put("homeSUM",appUserService.homeSUM());
			
			
			
			WebBillRecordVo appbillRecordVo2 = new WebBillRecordVo();
			
			
			//EP充值总计
			appbillRecordVo2.setBusnessType(11);
			BigDecimal epRechargeAll = webBillRecordService.SUMCount(appbillRecordVo2);
			result.put("epRechargeAll",epRechargeAll);
			
			
			//E资产释放
			
			WebBillRecordVo appbillRecordVo3 = new WebBillRecordVo();
			appbillRecordVo3.setBusnessType(18);
			BigDecimal epReleaseAll = webBillRecordService.SUMCount(appbillRecordVo3);
			
			result.put("epReleaseAll",epReleaseAll.abs());

			respBody.add(RespCodeEnum.SUCCESS.getCode(), "查询记录数据成功",result);
			//保存分页对象
			
			
			paging.setTotalCount(webBillRecordService.findCount(vo));
			respBody.setPage(paging);
		} catch (Exception ex) {
			respBody.add(RespCodeEnum.ERROR.getCode(), "查询记录数据失败");
			LogUtils.error("查找所有数据失败！",ex);
		}
		return respBody;
	}
	
	
	//开始时间处理
	public static Date getStarttime(Long startTime){
		Date endTime = new Date(startTime);
		try {
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			Calendar date = Calendar.getInstance();
			date.setTime(endTime);
			date.set(Calendar.DATE, date.get(Calendar.DATE));
			endTime = dft.parse(dft.format(date.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return endTime;
	}				
		
	

}
