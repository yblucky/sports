package com.pf.server.web;

import java.util.Date;
import java.util.List;

import com.pf.common.resp.Paging;
import com.pf.common.vo.pc.AppCoinTransRecordVo;
import com.pf.common.vo.pc.SearchVo;

/**
 * 量子币订单业务层接口
 *
 * @author yyr
 * @version v1.0
 * @date 2017年6月15日
 */
public interface CoinTransRecordService {
	public List<AppCoinTransRecordVo> order(SearchVo vo,Paging paging);
	Integer getCountByTime(Date StarTime,Date endTime);
}
