package com.xlf.server.web.impl;

import java.util.List;

import javax.annotation.Resource;

import com.xlf.server.mapper.AppBankTypeMapper;
import com.xlf.server.web.BankTypeService;
import org.springframework.stereotype.Service;

import com.xlf.common.po.AppBankTypePo;

/**
 * 银行卡层实现类
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
@Service
public class BankTypeServiceImpl implements BankTypeService {
	@Resource
	public AppBankTypeMapper appBankTypeMapper;

	@Override
	public List<AppBankTypePo> getall() {
		return appBankTypeMapper.getall();
	}
	
}
