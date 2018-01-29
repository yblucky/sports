package com.xlf.server.app;

import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.RacingBettingVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 赛车投注业务类
 */
public interface AppRacingBettingService {
    public void save(String businessNumber, String issueNo, String userId, Integer lotteryOne, Integer lotteryTwo, Integer lotteryThree, Integer lotteryFour, Integer lotteryFive,  Integer lotterySix,Integer lotterySeven, Integer lotteryEight, Integer lotteryNine, Integer lotteryTen, Integer multiple) throws Exception;

    public void racingBettingService(String userId, RacingBettingVo vo, BigDecimal totalPrice) throws Exception;

    public List<AppRacingBettingPo> listByIssuNo(String issuNo,Integer lotteryFlag, Paging paging);


    Integer count( String issuNo,Integer lotteryFlag);
}
