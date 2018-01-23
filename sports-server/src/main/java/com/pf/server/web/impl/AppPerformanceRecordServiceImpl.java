package com.pf.server.web.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.pf.common.resp.Paging;
import com.pf.common.vo.pc.AppPerformanceRecordVo;
import com.pf.server.mapper.AppPerformanceRecordMapper;
import com.pf.server.web.AppPerformanceRecordService;

@Service("appPerformanceRecord")
public class AppPerformanceRecordServiceImpl implements AppPerformanceRecordService {
	
	@Resource
	AppPerformanceRecordMapper appPerformanceRecordMapper;
	
	@Override
	public List<AppPerformanceRecordVo> findAll(AppPerformanceRecordVo vo, Paging paging) {
		int startRow=0;int pageSize=0;
		if(null!=paging){
			startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
			pageSize=paging.getPageSize();
		}else{
			pageSize=Integer.MAX_VALUE;
		}
		return appPerformanceRecordMapper.findAll(vo, startRow, pageSize);
	}

	@Override
	public Integer findCount(AppPerformanceRecordVo vo) {
	
		return appPerformanceRecordMapper.findCount(vo);
	}

}
