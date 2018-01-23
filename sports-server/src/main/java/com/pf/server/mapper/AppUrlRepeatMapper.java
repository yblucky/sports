package com.pf.server.mapper;

import com.pf.common.po.AppUrlRepeatPo;
import com.pf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * URL不拦截记录数据操作类
 * @author qsy
 * @version v1.0
 * @date 2016年11月29日
 */
public interface AppUrlRepeatMapper extends BaseMapper<AppUrlRepeatPo> {
	
	@Select("select url from app_url_repeat where state='10'")
	public List<String> findUrl();

	@Select("select url from app_url_repeat where state='20'")
	public List<String> findEnCodeUrl();

}
