package com.xlf.server.app;

import com.xlf.common.enums.RacingSeatEnum;
import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.RacingBettingVo;
import com.xlf.common.vo.pc.LotteryVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 赛车投注业务类
 */
public interface AppRacingBettingService {
    public void save(String businessNumber, String issueNo, String userId, Integer lotteryOne, Integer lotteryTwo, Integer lotteryThree, Integer lotteryFour, Integer lotteryFive, Integer lotterySix, Integer lotterySeven, Integer lotteryEight, Integer lotteryNine, Integer lotteryTen, Integer multiple,String bettingContent) throws Exception;

    public void racingBettingService(String userId, RacingBettingVo vo, BigDecimal totalPrice) throws Exception;

    public List<AppRacingBettingPo> listByIssuNo(String issuNo, Integer lotteryFlag, Paging paging);

    Integer count(String issuNo, Integer lotteryFlag);

    public Integer updateLotteryFlagById(String id, BigDecimal winingAmout);

    public List<AppRacingBettingPo> listWininggByIssuNo(String issuNo, Integer lotteryFlag, Paging paging, Integer digital, RacingSeatEnum seat);

    Integer wininggCount(String issuNo, Integer lotteryFlag, Integer digital, RacingSeatEnum seat);

    public Integer updateBatchLotteryFlag(String issueNo);

    List<LotteryVo> findAll(LotteryVo vo, Paging paging);


    Integer recordListTotal(String id, String businessNumber);

    List<AppRacingBettingPo> findRecordList(String userId, String businessNumber, Paging paging);

    AppRacingBettingPo findById(String id);

    Boolean undoRacingBettingService(String userId, String bettingId) throws Exception;

    Integer countBettingByUserIdAndIssueNoAndContent(String userId, String issueNo, String bettingContent, Integer betTpye) throws Exception;

    List<AppRacingBettingPo> findListByUserIdAndIssueNoAndContent(String userId, String issueNo, String bettingContent, Integer betTpye, Paging paging) throws Exception;

    int findAllCount(LotteryVo vo);

    public List<AppRacingBettingPo> listWininggByIssuNoAndWingConent(String issuNo, Integer lotteryFlag, Integer betType, Paging paging, List<String> winingList);

    Integer wininggCountAndWingConent(String issuNo, Integer lotteryFlag, Integer betType, List<String> winingList);

    public BigDecimal sumUnLotteryByUserId(String userId);
}
