package com.pf.server.web;

import java.util.List;

import com.pf.common.po.AppBankTypePo;

/**
 * 银行卡业务层接口
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
public interface BankTypeService {
	List<AppBankTypePo> getall();
}
