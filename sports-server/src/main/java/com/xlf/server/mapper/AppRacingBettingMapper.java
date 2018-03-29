package com.xlf.server.mapper;


import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.pc.LotteryVo;
import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

/**
 * 赛车投注
 *
 * @version v1.0
 * @date 2017年6月14日
 */
public interface AppRacingBettingMapper extends BaseMapper<AppRacingBettingPo> {
    @Select("SELECT * FROM `app_racing_betting` where issueNo=#{issueNo} and lotteryFlag=#{lotteryFlag} ORDER BY createTime asc")
    List<AppRacingBettingPo> list(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag, RowBounds rowBounds);

    @Select("SELECT count(id) FROM `app_racing_betting` where issueNo=#{issueNo} and lotteryFlag=#{lotteryFlag}")
    Integer count(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag);

    @Update("update  `app_racing_betting` set lotteryFlag=20 and  winningAmount=#{winingAmout} where id=#{id}")
    Integer updateLotteryFlagById(@Param("id") String id, @Param("winingAmout") BigDecimal winingAmout);

    Integer wininggCount(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag, @Param("digital") Integer digital, @Param("seat") Integer seat);

    List<AppRacingBettingPo> listWininggByIssuNo(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag, @Param("digital") Integer digital, @Param("seat") Integer seat, RowBounds rowBounds);

    Integer updateBatchLotteryFlag(@Param("issueNo") String issueNo);

    List<LotteryVo> findAll(@Param("model") LotteryVo vo, RowBounds rowBounds);
    int findAllCount(@Param("model") LotteryVo vo);

    @Select("SELECT COUNT(id) FROM `app_racing_betting` WHERE userId=#{userId} and businessNumber=#{businessNumber}")
    Integer recordListTotal(@Param("userId") String userId, @Param("businessNumber") String businessNumber);

    @Select("SELECT * FROM `app_racing_betting` WHERE userId=#{userId} and businessNumber=#{businessNumber}")
    List<AppRacingBettingPo> findRecordList(@Param("userId") String userId, @Param("businessNumber") String businessNumber, RowBounds rowBounds);

    Integer countBettingByUserIdAndIssueNoAndContent(@Param("userId") String userId, @Param("issueNo") String issueNo, @Param("bettingContent") String bettingContent, @Param("betTpye") Integer betTpye);

    List<AppRacingBettingPo> findListByUserIdAndIssueNoAndContent(@Param("userId") String userId, @Param("issueNo") String issueNo, @Param("bettingContent") String bettingContent,@Param("betTpye")Integer betTpye, RowBounds rowBounds);

    Integer wininggCountAndWingConent(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag,@Param ("betType") Integer betType, @Param("list") List<String> winingList);

    List<AppRacingBettingPo> listWininggByIssuNoAndWingConent(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag, @Param ("betType") Integer betType, @Param("list") List<String> winingList, RowBounds rowBounds);

    @Select("SELECT SUM(multiple) FROM `app_time_betting` where userId=#{userId} and lotteryFlag=10")
    public  BigDecimal sumUnLotteryByUserId(@Param("userId") String userId);

    //批量删除赛车下单
    Integer updateLotteryFlagAndWingAmoutByIds(@Param("ids") String[] ids,@Param("lotteryFlag") Integer lotteryFlag, @Param("winingAmout") BigDecimal winingAmout);
}
