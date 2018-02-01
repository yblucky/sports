package com.xlf.server.mapper;


import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.pc.LotteryVo;
import com.xlf.common.po.AppTimeBettingPo;
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
    Integer updateLotteryFlagById(@Param("id")String id,@Param("winingAmout") BigDecimal winingAmout);

    Integer wininggCount(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag, @Param("digital") Integer digital, @Param("seat") Integer seat);

    List<AppRacingBettingPo> listWininggByIssuNo(@Param("issuNo") String issuNo, @Param("lotteryFlag") Integer lotteryFlag, @Param("digital") Integer digital, @Param("seat") Integer seat, RowBounds rowBounds);

    Integer updateBatchLotteryFlag(@Param("issueNo") String issueNo);

    List<LotteryVo> findAll(@Param("model") LotteryVo vo, Paging paging);
}
