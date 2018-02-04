package com.xlf.server.web;

import java.math.BigDecimal;
import java.util.List;

import com.xlf.common.po.AppWithDrawPo;
import com.xlf.common.resp.Paging;

import com.xlf.common.vo.pc.AppWithDrawVo;

public interface WebWithDrawService {
	
	 public List<AppWithDrawVo> findAll(AppWithDrawVo vo,Paging paging);
		
	 public Integer findCount(AppWithDrawVo vo);
	 
	 
	 public void update(AppWithDrawPo Po );
	 
	 
	 public BigDecimal findSUM(AppWithDrawVo vo);

    int successState(String id);

	void erroeState(String id) throws Exception;
}
