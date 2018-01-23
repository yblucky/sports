package com.xlf.server.web.impl;

import java.util.List;

import javax.annotation.Resource;

import com.xlf.server.mapper.AppPerformanceParamMapper;
import com.xlf.server.web.AppPerformanceParamService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.xlf.common.po.AppPerformanceParamPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.util.ToolUtils;


@Service("appPerformance")
public class AppPerformanceParamServiceImpl implements AppPerformanceParamService {

	@Resource
	AppPerformanceParamMapper appPerformanceParamMapper ;
	
	@Override
	public long findCount() {
		// TODO Auto-generated method stub
		return appPerformanceParamMapper.findCount();
	}

	@Override
	public List<AppPerformanceParamPo> findAll(Paging paging) throws Exception {
		RowBounds rwoBounds = new RowBounds(paging.getPageNumber(),paging.getPageSize());
		return appPerformanceParamMapper.findAll(rwoBounds);
	}

	@Override
	public void add(AppPerformanceParamPo appPerformanceParamPo) throws Exception {
		appPerformanceParamPo.setId(ToolUtils.getUUID());
		appPerformanceParamMapper.insert(appPerformanceParamPo);

	}

	@Override
	public void delete(AppPerformanceParamPo appPerformanceParamPo) throws Exception {
		// TODO Auto-generated method stub
		appPerformanceParamMapper.deleteByPrimaryKey(appPerformanceParamPo);

	}

	@Override
	public void update(AppPerformanceParamPo appPerformanceParamPo) throws Exception {
		// TODO Auto-generated method stub
		appPerformanceParamMapper.updateByPrimaryKey(appPerformanceParamPo);

	}

}
