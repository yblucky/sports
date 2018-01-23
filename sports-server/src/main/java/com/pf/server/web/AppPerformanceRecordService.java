package com.pf.server.web;

import java.util.List;

import com.pf.common.resp.Paging;
import com.pf.common.vo.pc.AppPerformanceRecordVo;

public interface AppPerformanceRecordService {
    public List<AppPerformanceRecordVo> findAll(AppPerformanceRecordVo vo,Paging paging);
	
	public Integer findCount(AppPerformanceRecordVo vo);

}
