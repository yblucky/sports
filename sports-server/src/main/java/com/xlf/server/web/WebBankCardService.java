package com.xlf.server.web;

import java.util.List;

import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.BankCardVo;
import com.xlf.common.vo.pc.AppBankCardVo;

/**
 * 银行卡业务层接口
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
public interface WebBankCardService {

	List<AppBankCardVo> getPoList(AppBankCardVo vo,Paging paging);

	void update(BankCardVo bankCardVo) throws Exception;

}
