package com.xlf.server.web;

import java.util.List;

import com.xlf.common.resp.Paging;
import com.xlf.common.vo.pc.AppPerformanceRecordVo;

public interface AppPerformanceRecordService {
    public List<AppPerformanceRecordVo> findAll(AppPerformanceRecordVo vo,Paging paging);
	
	public Integer findCount(AppPerformanceRecordVo vo);

}
