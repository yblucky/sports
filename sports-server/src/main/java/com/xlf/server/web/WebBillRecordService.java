package com.xlf.server.web;

import java.math.BigDecimal;
import java.util.List;

import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.pc.WebBillRecordVo;
import com.xlf.common.vo.pc.WebStatisticsVo;


public interface WebBillRecordService {
	public List<WebBillRecordVo> findAll(WebBillRecordVo vo, Paging paging);
	
	public Integer findCount(WebBillRecordVo vo);
	
	public List<String> getCurrencyType();
	
	public List<String> getBusnessType();
	
	public void add(AppBillRecordPo appBillRecordPo) throws Exception;
	
	public  List<WebStatisticsVo> SUMCount();


    List<WebStatisticsVo> todayDataSum();
}
