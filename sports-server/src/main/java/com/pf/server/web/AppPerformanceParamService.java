package com.pf.server.web;

import java.util.List;

import com.pf.common.po.AppPerformanceParamPo;
import com.pf.common.resp.Paging;

public interface AppPerformanceParamService {
	
	/**
	 * 查找总记录数
	 * @return
	 */
	public long findCount();
	/**
	 * 查找所有数据
	 * @param paging 分页
	 * @return 集合
	 * @throws Exception 
	 */
	public List<AppPerformanceParamPo> findAll(Paging paging) throws Exception;
	
	/**
	 * 新增
	 * @param appUserStarLevelPo
	 * @throws Exception
	 */
	
	public void add(AppPerformanceParamPo appPerformanceParamPo) throws Exception;
	
	/**
	 *删除
	 * @param appUserStarLevelPo
	 * @throws Exception
	 */
	
	public void delete(AppPerformanceParamPo appPerformanceParamPo) throws Exception;
	
	/**
	 *修改
	 * @param appUserStarLevelPo
	 * @throws Exception
	 */
	
	public void  update(AppPerformanceParamPo appPerformanceParamPo) throws Exception;
	

}
