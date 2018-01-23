package com.pf.server.web.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pf.common.po.AppWithDrawPo;
import com.pf.common.resp.Paging;
import com.pf.common.vo.pc.AppWithDrawVo;
import com.pf.server.mapper.AppWithDrawMapper;
import com.pf.server.web.AppWithDrawService;


@Service("appWithDraw")
public class AppWithDrawServiceImpl implements AppWithDrawService {
	
	
	@Resource
	AppWithDrawMapper appWithDrawMapper;
	
	
	@Override
	public List<AppWithDrawVo> findAll(AppWithDrawVo vo, Paging paging) {
		int startRow=0;int pageSize=0;
		if(null!=paging){
			startRow=(paging.getPageNumber()>0)?(paging.getPageNumber()-1)*paging.getPageSize():0;
			pageSize=paging.getPageSize();
		}else{
			pageSize=Integer.MAX_VALUE;
		}
		return appWithDrawMapper.findAll(vo, startRow, pageSize);
	}

	@Override
	public Integer findCount(AppWithDrawVo vo) {
		// TODO Auto-generated method stub
		return appWithDrawMapper.findCount(vo);
	}

	@Override
	public void update(AppWithDrawPo po) {
		 
		 appWithDrawMapper.updateByPrimaryKey(po);
		
	}

	@Override
	public BigDecimal findSUM(AppWithDrawVo vo) {
		// TODO Auto-generated method stub
		return appWithDrawMapper.findSUM(vo);
	}

}
