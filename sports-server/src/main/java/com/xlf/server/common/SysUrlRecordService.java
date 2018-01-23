package com.xlf.server.common;

import com.xlf.common.resp.Paging;
import com.xlf.common.resp.RespBody;
import com.xlf.common.vo.pc.SysUrlRecordVo;

import java.util.List;

/**
 * URL不拦截记录操作业务层接口
 * @author qsy
 * @version v1.0
 * @date 2016年11月29日
 */
public interface SysUrlRecordService {

	/**
	 * 查询不被拦截的URL记录
	 * @return URL集合
	 */
	public List<String> findUrl();

	List<String> findRepeatUrl();

	List<String> findEnCodeUrl();

	/**
	 * 分页查询URL记录
	 * @param page
	 * @param respBody
	 * @throws Exception
	 */
	public void loadRecords(Paging page, RespBody respBody) throws Exception;
	
	/**
	 * 新增URL记录
	 * @param recordVo
	 * @throws Exception
	 */
	public void addRecord(SysUrlRecordVo recordVo, RespBody respBody) throws Exception;
	
	/**
	 * 修改URL记录
	 * @param recordVo
	 * @throws Exception
	 */
	public void updateRecord(SysUrlRecordVo recordVo, RespBody respBody) throws Exception;
	
	/**
	 * 删除单条URL记录
	 * @param recordVo
	 * @throws Exception
	 */
	public void deleteRecord(SysUrlRecordVo recordVo, RespBody respBody) throws Exception;
	
	/**
	 * 删除多条URL记录
	 * @param delIds
	 * @throws Exception
	 */
	public void deleteRecords(List<String> delIds, RespBody respBody) throws Exception;

}
