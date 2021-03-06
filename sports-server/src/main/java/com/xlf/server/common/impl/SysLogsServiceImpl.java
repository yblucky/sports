package com.xlf.server.common.impl;


import com.xlf.common.po.SysLogsPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.util.LogUtils;
import com.xlf.common.util.MyBeanUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.pc.SysLogsVo;
import com.xlf.server.common.SysLogsService;
import com.xlf.server.mapper.SysLogsMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 日志信息业务层实现
 * 
 * @author qsy
 * @version v1.0
 * @date 2016年11月26日
 */
@Service
public class SysLogsServiceImpl implements SysLogsService {
	@Resource
	private SysLogsMapper mapper;

	/**
	 * 保存数据
	 * 
	 * @param vo
	 * @throws Exception
	 */
	@Transactional
	@Override
	public void save(SysLogsVo vo) throws Exception {
		try {
			SysLogsPo po = MyBeanUtils.copyProperties(vo, SysLogsPo.class);
			po.setId(ToolUtils.getUUID());
			po.setOptDate(vo.getOptDate());
			if(po.getLogDetail() != null && po.getLogDetail().length()>1024){
				po.setLogDetail(po.getLogDetail().substring(0, 1023));
			}
			mapper.insert(po);
		} catch (Exception ex) {
			LogUtils.error("日志信息保存失败！", ex);
			throw new Exception("日志信息保存失败！");
		}
	}

	@Override
	public List<SysLogsPo> findAll(Paging paging) {
		//创建分页插件对象
		RowBounds rowBounds = new RowBounds(paging.getPageNumber(),paging.getPageSize());
		return mapper.findAll(rowBounds);
	}

	@Override
	public long findCount() {
		return mapper.findCount();
	}
}
