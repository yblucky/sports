package com.xlf.server.web.impl;

import java.util.List;

import javax.annotation.Resource;

import com.xlf.server.web.UseImageService;
import org.springframework.stereotype.Service;

import com.xlf.common.po.UseImagePo;
import com.xlf.server.mapper.UseImageMapper;

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
