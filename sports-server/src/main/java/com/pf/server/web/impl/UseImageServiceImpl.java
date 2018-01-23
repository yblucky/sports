package com.pf.server.web.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pf.common.po.UseImagePo;
import com.pf.server.mapper.UseImageMapper;
import com.pf.server.web.UseImageService;

/**
 * 用户业务层实现类
 * @author yyr
 * @version v1.0
 * @date 2017年6月12日
 */
@Service("webUseImageService")
public class UseImageServiceImpl implements UseImageService {
	@Resource
	private UseImageMapper useImageMapper;

	@Override
	public List<UseImagePo> getByIds(List<String> ids) {
		return useImageMapper.getByIds(ids);
	}

	
}
