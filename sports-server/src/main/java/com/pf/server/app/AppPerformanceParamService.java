package com.pf.server.app;

import com.pf.common.po.AppPerformanceParamPo;
import com.pf.common.resp.Paging;
import com.pf.common.vo.app.AppBillRecordVo;
import com.pf.common.vo.app.PerformanceRecordVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 业绩相关参数
 * Created by Administrator on 2018/1/4 0004.
 */
@Service
public interface AppPerformanceParamService {

    public AppPerformanceParamPo findOneAppPerformanceParamPo(BigDecimal performance ,Integer type );


}
