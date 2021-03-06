package com.xlf.server.web.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.xlf.common.vo.pc.WebStatisticsVo;
import com.xlf.server.mapper.AppBillRecordMapper;
import com.xlf.server.web.WebBillRecordService;
import org.springframework.stereotype.Service;

import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.pc.WebBillRecordVo;

@Service
public class WebBillRecordServiceImpl implements WebBillRecordService {
	
	
	@Resource
    private AppBillRecordMapper appBillRecordMapper;
	
	@Override
	public List<WebBillRecordVo> findAll(WebBillRecordVo vo, Paging paging) {
		
		int startRow=0;int pageSize=0;
		if(null!=paging){
			startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
			pageSize=paging.getPageSize();
		}else{
			pageSize=Integer.MAX_VALUE;
		}
		return appBillRecordMapper.findAll(vo, startRow, pageSize);
		//return null;
	}

	@Override
	public Integer findCount(WebBillRecordVo vo){
		return appBillRecordMapper.findCount(vo);
		//return null;
	}
	
	@Override
	public List<String> getCurrencyType() {
		// TODO Auto-generated method stub
		return appBillRecordMapper.getCurrencyType();
	}
	
	

	@Override
	public List<String> getBusnessType() {
		// TODO Auto-generated method stub
		return appBillRecordMapper.getBusnessType();
	}

	@Override
	public void add(AppBillRecordPo po) throws Exception {
		
		po.setId(ToolUtils.getUUID());
	
		po.setCreateTime(new Date());
	
		appBillRecordMapper.insert(po);
		
	}

	@Override
	public List<WebStatisticsVo> SUMCount() {
		// TODO Auto-generated method stub
		return appBillRecordMapper.SUMCount();
	}

	@Override
	public List<WebStatisticsVo> todayDataSum() {
		return appBillRecordMapper.selectSumByBusnessType();
	}
}
