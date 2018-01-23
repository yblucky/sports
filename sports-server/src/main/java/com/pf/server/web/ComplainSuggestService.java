package com.pf.server.web;

import java.util.List;

import com.pf.common.resp.Paging;
import com.pf.common.vo.pc.UseComplainSuggestVo;

/**
 * 投诉与建议业务层接口
 * @author yyr
 * @version v1.0
 * @date 2017年6月15日
 */
public interface ComplainSuggestService {
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
	public List<UseComplainSuggestVo> findAll(Paging paging) throws Exception;
	
	public List<UseComplainSuggestVo> getInfoByTime(UseComplainSuggestVo vo,Paging paging);
}
