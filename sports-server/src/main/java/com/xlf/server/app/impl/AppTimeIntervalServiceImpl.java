package com.xlf.server.app.impl;

import com.xlf.common.po.AppTimeIntervalPo;
import com.xlf.server.app.AppTimeIntervalService;
import com.xlf.server.mapper.AppTimeIntervalMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class AppTimeIntervalServiceImpl implements AppTimeIntervalService {

    @Resource
    private AppTimeIntervalMapper appTimeIntervalMapper;


    @Override
    public AppTimeIntervalPo findById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        return appTimeIntervalMapper.selectByPrimaryKey(id);
    }

    @Override
    public AppTimeIntervalPo findByIssNo(Integer issuNo, Integer type) {
        return appTimeIntervalMapper.findByIssNo(issuNo, type);
    }

    @Override
    public AppTimeIntervalPo findByTime(String time, Integer type) {
        return appTimeIntervalMapper.findByTime(time, type);
    }
}
