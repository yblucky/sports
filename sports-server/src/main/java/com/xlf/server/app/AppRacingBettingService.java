package com.xlf.server.app;

import com.xlf.common.vo.app.BankCardVo;
import com.xlf.common.vo.app.RacingBettingVo;
import com.xlf.common.vo.app.TimeBettingVo;

import java.math.BigDecimal;

/**
 * 赛车投注业务类
 */
public interface AppRacingBettingService {
    public  void save(String businessNumber,String issueNo,String userId,Integer lotteryOne,Integer lotteryTwo,Integer lotteryThree,Integer lotteryFour,Integer lotteryFive,Integer multiple) throws Exception;

    public void racingBettingService(String userId, RacingBettingVo vo, BigDecimal totalPrice) throws Exception;
}
