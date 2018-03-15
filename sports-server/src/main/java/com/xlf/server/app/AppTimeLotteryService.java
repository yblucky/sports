package com.xlf.server.app;

import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.AppTimeLotteryVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 时时彩投注业务类
 */
public interface AppTimeLotteryService {
    public AppTimeLotteryPo findLast();

    public AppTimeLotteryPo findById(String id);

    Integer updateFlagById(String id);

    public Boolean timeLotteryHandleService(AppTimeBettingPo bettingPo) throws Exception;

    public Boolean batchTimeLotteryHandleService(AppTimeLotteryPo lotteryPo, Boolean flag) throws Exception;

    public Boolean batchTimeLotteryHandleWayTwoService(AppTimeLotteryPo lotteryPo, Boolean flag,Integer betType) throws Exception;

    public List<AppTimeLotteryPo> lotteryListCurrentDay();

    public List<AppTimeLotteryPo> lotteryListCurrentDayByPayUrl();

    public AppTimeLotteryPo findAppTimeLotteryPoByIssuNo(String issuNo);

    Integer save(AppTimeLotteryPo po);

    //获取开奖号码列表
    public List<AppTimeLotteryVo> loadLotteryInfoList(Paging paging,String startTime,String endTime) throws Exception;

    //获取开奖号码列表
    public Integer countLotteryInfoTotal(String startTime,String endTime) throws Exception;

    public AppTimeLotteryVo loadAwardNumber(String issueNo) throws Exception;
}
