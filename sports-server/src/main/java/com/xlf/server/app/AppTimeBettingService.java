package com.xlf.server.app;

import com.xlf.common.enums.TimeSeatEnum;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.TimeBettingVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 时时彩投注业务类
 */
public interface AppTimeBettingService {

    public void save(String businessNumber, String issueNo, String userId, Integer lotteryOne, Integer lotteryTwo, Integer lotteryThree, Integer lotteryFour, Integer lotteryFive, Integer multiple) throws Exception;

    public void timeBettingService(String userId, TimeBettingVo vo, BigDecimal totalPrice) throws Exception;

    public List<AppTimeBettingPo> listByIssuNo(String issuNo, Integer lotteryFlag, Paging paging);

    Integer count(String issuNo, Integer lotteryFlag);

    public List<AppTimeBettingPo> listWininggByIssuNo(String issuNo, Integer lotteryFlag, Paging paging, Integer digital, TimeSeatEnum seat);

    Integer wininggCount(String issuNo, Integer lotteryFlag, Integer digital, TimeSeatEnum seat);

    public Integer updateLotteryFlagById(String id, BigDecimal winingAmout);

    public Integer updateBatchLotteryFlag(String issueNo);

    Integer recordListTotal(String id, String businessNumber);

    List<AppTimeBettingPo> findRecordList(String userId, String businessNumber, Paging paging);

    AppTimeBettingPo findById(String id);

    Boolean undoTimeBettingService(String userId,String bettingId) throws Exception;
}
