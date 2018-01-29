package com.xlf.server.mapper;


import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

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
}
