package com.xlf.server.app;

import com.xlf.common.enums.TimeSeatEnum;
import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.TimeBettingVo;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

/**
 * 时时彩投注业务类
 */
public interface AppTimeBettingService {

    public void save(String businessNumber, String issueNo, String userId, Integer lotteryOne, Integer lotteryTwo, Integer lotteryThree, Integer lotteryFour, Integer lotteryFive, Integer multiple,Integer betType,String timeBetContent,Integer serialNumber) throws Exception;

    public void timeBettingService(String userId, TimeBettingVo vo, BigDecimal totalPrice) throws Exception;

    public List<AppTimeBettingPo> listByIssuNo(String issuNo, Integer lotteryFlag, Paging paging);

    Integer count(String issuNo, Integer lotteryFlag);

    public List<AppTimeBettingPo> listWininggByIssuNo(String issuNo, Integer lotteryFlag, Paging paging, Integer digital, TimeSeatEnum seat);

    Integer wininggCount(String issuNo, Integer lotteryFlag, Integer digital, TimeSeatEnum seat);

    public Integer updateLotteryFlagAndWingAmoutById(String id, Integer lotteryFlag,BigDecimal winingAmout);

    public Integer updateBatchLotteryFlag(String issueNo);

    Integer recordListTotal(String id, String businessNumber);

    List<AppTimeBettingPo> findRecordList(String userId, String businessNumber, Paging paging);

    AppTimeBettingPo findById(String id);

    Boolean undoTimeBettingService(String userId, String bettingId) throws Exception;

    Integer countBettingByUserIdAndIssueNoAndContent(String userId, String issueNo, String bettingContent,Integer betTpye)throws Exception;

    List<AppTimeBettingPo> findListByUserIdAndIssueNoAndContent(String userId, String issueNo, String bettingContent,Integer betTpye,  Paging paging)throws Exception;

    public List<AppTimeBettingPo> listWininggByIssuNoAndWingConent(String issuNo, Integer lotteryFlag,Integer betType, Paging paging, List<String> winingList);

    Integer wininggCountAndWingConent(String issuNo, Integer lotteryFlag,Integer betType,List<String> winingList);

    public BigDecimal sumUnLotteryByUserId(String userId,Integer betType);
}
