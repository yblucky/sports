package com.pf.server.web.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pf.common.resp.Paging;
import com.pf.common.vo.app.BankCardVo;
import com.pf.common.vo.pc.AppBankCardVo;
import com.pf.server.mapper.AppBankCardMapper;
import com.pf.server.web.BankCardService;

/**
 * 银行卡业务层实现类
 * @author yyr
 * @version v1.0
 * @date 2017年6月14日
 */
@Service
public class BankCardServiceImpl implements BankCardService {
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
