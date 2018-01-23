package com.xlf.server.app.impl;

import com.xlf.common.po.AppPerformanceParamPo;
import com.xlf.server.app.AppPerformanceParamService;
import com.xlf.server.mapper.AppPerformanceParamMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

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
