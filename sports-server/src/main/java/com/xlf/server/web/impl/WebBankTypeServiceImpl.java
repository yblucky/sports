package com.xlf.server.web.impl;

import java.util.List;

import javax.annotation.Resource;

import com.xlf.server.mapper.AppBankTypeMapper;
import com.xlf.server.web.WebBankTypeService;
import org.springframework.stereotype.Service;

import com.xlf.common.po.AppBankTypePo;

/**
 * 银行卡层实现类
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
@Service
public class WebBankTypeServiceImpl implements WebBankTypeService {
	@Resource
	public AppBankTypeMapper appBankTypeMapper;

	@Override
	public List<AppBankTypePo> getall() {
		return appBankTypeMapper.getall();
	}
	
}
