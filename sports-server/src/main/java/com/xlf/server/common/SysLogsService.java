package com.xlf.server.common;

import com.xlf.common.po.SysLogsPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.pc.SysLogsVo;

import java.util.List;

/**
 * 日志信息业务层接口
 * @author qsy
 * @version v1.0
 * @date 2016年11月26日
 */
public interface SysLogsService {

	/**
	 * 保存数据
	 * @param vo 系统日志对象
	 * @throws Exception 
	 */
	public void save(SysLogsVo vo) throws Exception;

	List<SysLogsPo> findAll(Paging paging);

	long findCount();

}
