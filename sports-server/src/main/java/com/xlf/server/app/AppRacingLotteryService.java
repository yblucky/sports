package com.xlf.server.app;

import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.po.AppRacingLotteryPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.AppTimeLotteryVo;
import com.xlf.common.vo.task.RacingLotteryVo;

import java.util.List;

/**
 * 时时彩投注业务类
 */
public interface AppRacingLotteryService {
    public AppRacingLotteryPo findLast();

    public AppRacingLotteryPo findById(String id);

    Integer updateFlagById(String id);

    public Boolean racingLotteryHandleService(AppRacingBettingPo bettingPo) throws Exception;

    public Boolean batchRacingLotteryHandleService(AppRacingLotteryPo lotteryPo, Boolean flag) throws Exception;

    public RacingLotteryVo getLatestRacingLottery();

    public List<AppRacingLotteryPo> lotteryListCurrentDayByPayUrl();

    public AppRacingLotteryPo findAppRacingLotteryPoByIssuNo(String issuNo);

    Integer save(AppRacingLotteryPo po);

    public AppRacingLotteryPo loadAwardNumber() throws Exception;

    //获取开奖号码列表
    public List<AppRacingLotteryPo> loadLotteryInfoList(Paging paging, String startTime, String endTime) throws Exception;

    //获取开奖号码列表
    public Integer countLotteryInfoTotal(String startTime, String endTime) throws Exception;

}
