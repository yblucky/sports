package com.xlf.server.app;

import com.xlf.common.po.AppTimeLotteryPo;

/**
 * 时时彩投注业务类
 */
public interface AppTimeLotteryService {
    public AppTimeLotteryPo findLast();

    public AppTimeLotteryPo findById(String id);

    Integer updateFlagById(String id);
}
