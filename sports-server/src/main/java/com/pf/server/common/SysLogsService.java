package com.pf.server.common;

import com.pf.common.vo.pc.SysLogsVo;

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

}
