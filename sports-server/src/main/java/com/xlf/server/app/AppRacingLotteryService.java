package com.xlf.server.app;

import com.xlf.common.po.AppRacingLotteryPo;

/**
 * 时时彩投注业务类
 */
public interface AppRacingLotteryService {
    public AppRacingLotteryPo findLast();

    public AppRacingLotteryPo findById(String id);

    Integer updateFlagById(String id);
}
