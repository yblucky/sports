package com.pf.server.web;

import java.math.BigDecimal;
import java.util.List;

import com.pf.common.po.AppWithDrawPo;
import com.pf.common.resp.Paging;

import com.pf.common.vo.pc.AppWithDrawVo;

public interface AppWithDrawService {
	
	 public List<AppWithDrawVo> findAll(AppWithDrawVo vo,Paging paging);
		
	 public Integer findCount(AppWithDrawVo vo);
	 
	 
	 public void update(AppWithDrawPo Po );
	 
	 
	 public BigDecimal findSUM(AppWithDrawVo vo);

}
