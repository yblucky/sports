package com.xlf.server.mapper;


import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

/**
 * 时时彩投注
 *
 * @version v1.0
 * @date 2017年6月14日
 */
public interface AppTimeBettingMapper extends BaseMapper<AppTimeBettingPo> {
    @Select("SELECT * FROM `app_time_betting` where issueNo=#{issueNo} and lotteryFlag=#{lotteryFlag}  ORDER BY createTime asc")
    List<AppTimeBettingPo> list(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag, RowBounds rowBounds);

    @Select("SELECT count(id) FROM `app_time_betting` where issueNo=#{issueNo} and lotteryFlag=#{lotteryFlag}")
    Integer count(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag);

    Integer wininggCount(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag, @Param("digital") Integer digital, @Param("seat") Integer seat);

    List<AppTimeBettingPo> listWininggByIssuNo(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag, @Param("digital") Integer digital, @Param("seat") Integer seat, RowBounds rowBounds);

    @Update("update  `app_time_betting` set lotteryFlag=20 and  winningAmount=#{winingAmout} where id=#{id}")
    Integer updateLotteryFlagById(@Param("id") String id, @Param("winingAmout") BigDecimal winingAmout);


    Integer updateBatchLotteryFlag(@Param("issueNo") String issueNo);

    @Select("SELECT COUNT(*) FROM `app_time_betting` WHERE userId=#{userId} and businessNumber=#{businessNumber}")
    Integer recordListTotal(@Param("userId") String userId, @Param("businessNumber") String businessNumber);

    @Select("SELECT * FROM `app_time_betting` WHERE userId=#{userId} and businessNumber=#{businessNumber}")
    List<AppTimeBettingPo> findRecordList(@Param("userId") String userId, @Param("businessNumber") String businessNumber, RowBounds rowBounds);

    Integer countBettingByUserIdAndIssueNoAndContent(@Param("userId") String userId, @Param("issueNo") String issueNo, @Param("bettingContent") String bettingContent);

    List<AppTimeBettingPo> findListByUserIdAndIssueNoAndContent(@Param("userId") String userId, @Param("issueNo") String issueNo, @Param("bettingContent") String bettingContent, RowBounds rowBounds);
}
