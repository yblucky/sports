package com.xlf.server.app;

import com.xlf.common.po.AppTimeIntervalPo;

public interface AppTimeIntervalService {
    public AppTimeIntervalPo findById(String id);

    public AppTimeIntervalPo findByIssNo(String issuNo, Integer type);

    public AppTimeIntervalPo findByTime(String time, Integer type);
}
