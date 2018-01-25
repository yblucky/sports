package com.xlf.server.web;

import java.util.List;

import com.xlf.common.po.AppBankTypePo;

/**
 * 银行卡业务层接口
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
public interface WebBankTypeService {
	List<AppBankTypePo> getall();
}
