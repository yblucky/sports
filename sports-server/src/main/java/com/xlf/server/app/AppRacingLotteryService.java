package com.xlf.server.app;

import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.po.AppRacingLotteryPo;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppTimeLotteryPo;

/**
 * 时时彩投注业务类
 */
public interface AppRacingLotteryService {
    public AppRacingLotteryPo findLast();

    public AppRacingLotteryPo findById(String id);

    Integer updateFlagById(String id);

    public Boolean racingLotteryHandleService(AppRacingBettingPo bettingPo) throws Exception;

    public Boolean batchRacingLotteryHandleService(AppRacingLotteryPo lotteryPo, Boolean flag) throws Exception;


}
