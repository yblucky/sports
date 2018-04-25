package com.xlf.server.mapper;


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


    Integer wininggCountAndWingConent(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag,@Param ("betType") Integer betType, @Param("list") List<String> winingList);

    List<AppTimeBettingPo> listWininggByIssuNoAndWingConent(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag,@Param ("betType") Integer betType, @Param("list") List<String> winingList, RowBounds rowBounds);

    @Update("update  `app_time_betting` set lotteryFlag=#{lotteryFlag} , winningAmount=#{winingAmout} where id=#{id}")
    Integer updateLotteryFlagAndWingAmoutById(@Param("id") String id,@Param("lotteryFlag") Integer lotteryFlag, @Param("winingAmout") BigDecimal winingAmout);

    //批量删除下单
    Integer updateLotteryFlagAndWingAmoutByIds(@Param("ids") String[] ids,@Param("lotteryFlag") Integer lotteryFlag, @Param("winingAmout") BigDecimal winingAmout);

    Integer updateBatchLotteryFlag(@Param("issueNo") String issueNo);

    @Select("SELECT COUNT(*) FROM `app_time_betting` WHERE userId=#{userId} and businessNumber=#{businessNumber}")
    Integer recordListTotal(@Param("userId") String userId, @Param("businessNumber") String businessNumber);

    @Select("SELECT * FROM `app_time_betting` WHERE userId=#{userId} and businessNumber=#{businessNumber} ORDER BY winningAmount desc,bettingContent asc")
    List<AppTimeBettingPo> findRecordList(@Param("userId") String userId, @Param("businessNumber") String businessNumber, RowBounds rowBounds);

    Integer countBettingByUserIdAndIssueNoAndContent(@Param("userId") String userId, @Param("issueNo") String issueNo, @Param("bettingContent") String bettingContent,@Param ("betType") Integer betType);

    List<AppTimeBettingPo> findListByUserIdAndIssueNoAndContent(@Param("userId") String userId, @Param("issueNo") String issueNo, @Param("bettingContent") String bettingContent,@Param ("betType") Integer betType, RowBounds rowBounds);

    @Select("SELECT SUM(multiple) FROM `app_time_betting` where userId=#{userId} and lotteryFlag=10 and betType=#{betType}")
    public  BigDecimal sumUnLotteryByUserId(@Param("userId") String userId,@Param("betType") Integer betType);

    //根据用户id和下注订单状态查询是否有未开奖订单
    @Select("SELECT COUNT(*) FROM `app_time_betting` WHERE userId=#{userId} and issueNo != #{issueNo} and lotteryFlag=10")
    public Integer findCountOrderByUserId(@Param("userId") String userId,@Param("issueNo") String issueNo);
}
