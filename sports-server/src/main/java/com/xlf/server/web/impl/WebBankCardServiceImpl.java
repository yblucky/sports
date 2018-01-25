package com.xlf.server.web.impl;

import java.util.List;

import javax.annotation.Resource;

import com.xlf.server.mapper.AppBankCardMapper;
import com.xlf.server.web.WebBankCardService;
import org.springframework.stereotype.Service;

import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.BankCardVo;
import com.xlf.common.vo.pc.AppBankCardVo;

/**
 * 银行卡业务层实现类
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
@Service
public class WebBankCardServiceImpl implements WebBankCardService {
	@Resource
	public AppBankCardMapper appBankCardMapper;

	@Override
	public List<AppBankCardVo> getPoList(AppBankCardVo vo, Paging paging) {
		int startRow=0;int pageSize=0;
		if(null!=paging){
			startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
			pageSize=paging.getPageSize();
		}else{
			pageSize=Integer.MAX_VALUE;
		}
		return appBankCardMapper.getPoList(vo, startRow, pageSize);
	}

	
	@Override
	public void update(BankCardVo bankCardVo) throws Exception {
		// TODO Auto-generated method stub
		 appBankCardMapper.update(bankCardVo);
	}



}
