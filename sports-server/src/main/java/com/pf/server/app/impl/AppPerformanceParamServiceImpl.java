package com.pf.server.app.impl;

import com.pf.common.po.AppPerformanceParamPo;
import com.pf.common.resp.Paging;
import com.pf.common.vo.app.AppBillRecordVo;
import com.pf.common.vo.app.PerformanceRecordVo;
import com.pf.server.app.AppBankCardService;
import com.pf.server.app.AppPerformanceParamService;
import com.pf.server.mapper.AppPerformanceParamMapper;
import com.pf.server.mapper.AppPerformanceRecordMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 业绩相关参数
 * Created by Administrator on 2018/1/4 0004.
 */
@Service
public class AppPerformanceParamServiceImpl implements AppPerformanceParamService {

    @Resource
    private AppPerformanceParamMapper appPerformanceParamMapper;
    @Override
    public AppPerformanceParamPo findOneAppPerformanceParamPo(BigDecimal performance, Integer type) {
        return appPerformanceParamMapper.findOneAppPerformanceParamPo(performance,type);
    }



}
