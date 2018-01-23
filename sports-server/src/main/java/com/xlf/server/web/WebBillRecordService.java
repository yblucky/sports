package com.xlf.server.web;

import java.math.BigDecimal;
import java.util.List;

import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.pc.AppBillRecordVo1;


public interface WebBillRecordService {
	public List<AppBillRecordVo1> findAll(AppBillRecordVo1 vo,Paging paging);
	
	public Integer findCount(AppBillRecordVo1 vo);
	
	public List<String> getCurrencyType();
	
	public List<String> getBusnessType();
	
	public void add(AppBillRecordPo appBillRecordPo) throws Exception;
	
	public  BigDecimal SUMCount(AppBillRecordVo1 vo);
	

}
