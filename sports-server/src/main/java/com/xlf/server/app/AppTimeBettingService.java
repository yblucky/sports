package com.xlf.server.app;

import com.xlf.common.vo.app.TimeBettingVo;

import java.math.BigDecimal;

/**
 * 时时彩投注业务类
 */
public interface AppTimeBettingService {

    public  void save(String businessNumber,String issueNo,String userId,Integer lotteryOne,Integer lotteryTwo,Integer lotteryThree,Integer lotteryFour,Integer lotteryFive,Integer multiple) throws Exception;

    public void timeBettingService(String userId, TimeBettingVo vo, BigDecimal totalPrice) throws Exception;

}
