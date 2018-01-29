package com.xlf.server.app.impl;

import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.server.app.AppTimeLotteryService;
import com.xlf.server.mapper.AppTimeLotteryMapper;

import javax.annotation.Resource;

/**
 * 时时彩开奖务类
 */
public class AppTimeLotteryServiceImpl implements AppTimeLotteryService {
    @Resource
    private AppTimeLotteryMapper appTimeLotteryMapper;

    @Override
    public AppTimeLotteryPo findLast() {
        return appTimeLotteryMapper.findLast();
    }

    @Override
    public AppTimeLotteryPo findById(String id) {
        return appTimeLotteryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateFlagById(String id) {
        return appTimeLotteryMapper.updateFlagById(id);
    }
}
