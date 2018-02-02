package com.xlf.server.app;

import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppTimeLotteryPo;

import java.util.List;

/**
 * 时时彩投注业务类
 */
public interface AppTimeLotteryService {
    public AppTimeLotteryPo findLast();

    public AppTimeLotteryPo findById(String id);

    Integer updateFlagById(String id);

    public Boolean timeLotteryHandleService(AppTimeBettingPo bettingPo) throws Exception;

    public Boolean batchTimeLotteryHandleService(AppTimeLotteryPo lotteryPo, Boolean flag) throws Exception;

    public List<AppTimeLotteryPo> lotteryListCurrentDay();

    public AppTimeLotteryPo findAppTimeLotteryPoByIssuNo(String issuNo);

    Integer save(AppTimeLotteryPo po);
}
