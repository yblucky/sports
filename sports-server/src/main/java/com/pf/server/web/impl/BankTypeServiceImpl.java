package com.pf.server.web.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pf.common.po.AppBankTypePo;
import com.pf.server.mapper.AppBankTypeMapper;
import com.pf.server.web.BankTypeService;

/**
 * 银行卡层实现类
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
@Service
public class BankTypeServiceImpl implements BankTypeService{
	@Resource
	public AppBankTypeMapper appBankTypeMapper;

	@Override
	public List<AppBankTypePo> getall() {
		return appBankTypeMapper.getall();
	}
	
}
