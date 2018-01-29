package com.xlf.server.app.impl;

import com.xlf.common.po.AppRacingLotteryPo;
import com.xlf.server.app.AppRacingLotteryService;
import com.xlf.server.mapper.AppRacingLotteryMapper;

import javax.annotation.Resource;

/**
 * 赛车开奖业务类
 */
public class AppRacingLotteryServiceImpl implements AppRacingLotteryService {
    @Resource
    private AppRacingLotteryMapper appRacingLotteryMapper;

    @Override
    public AppRacingLotteryPo findLast() {
        return appRacingLotteryMapper.findLast();
    }

    @Override
    public AppRacingLotteryPo findById(String id) {
        return appRacingLotteryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateFlagById(String id) {
        return appRacingLotteryMapper.updateFlagById(id);
    }
}
