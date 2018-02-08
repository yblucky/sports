package com.xlf.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.vo.pc.WebStatisticsVo;
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
	private WebWithDrawService webWithDrawService;
	@Resource
	private WebUserService appUserService;
	@Resource
	private WebBillRecordService webBillRecordService;
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
			//今日统计根据流水类型分组
			List<WebStatisticsVo> list = webBillRecordService.todayDataSum();
			Map<String,BigDecimal> totdayMap=new HashMap<String, BigDecimal>();
			for(WebStatisticsVo model:list){
				totdayMap.put(BusnessTypeEnum.getEgName(model.getBusnessType()),model.getBalance());
			}
			result.put("todayData",totdayMap);
			result.put("registerUserCount",appUserService.SUMCount());

			//用户统计（ep,候鸟积分，e资产，用户数量）
			result.put("userSum",appUserService.homeSUM());

			WebBillRecordVo appbillRecordVo2 = new WebBillRecordVo();
			
			
			//EP充值总计
			list = webBillRecordService.SUMCount();
			Map<String,BigDecimal> sumMap=new HashMap<String, BigDecimal>();
			for(WebStatisticsVo model:list){
				sumMap.put(BusnessTypeEnum.getEgName(model.getBusnessType()),model.getBalance());
			}
			result.put("totalData",sumMap);

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
