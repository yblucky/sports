package com.xlf.server.mapper;


import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 时时彩投注
 * @version v1.0
 * @date 2017年6月14日
 */
public interface AppTimeBettingMapper extends BaseMapper<AppTimeBettingPo> {
    @Select("SELECT * FROM `app_time_betting` where issueNo=#{issueNo} and lotteryFlag=#{lotteryFlag}  ORDER BY createTime asc")
    List<AppTimeBettingPo> list(@Param("issuNo") String issuNo,@Param("lotteryFlag") Integer lotteryFlag, RowBounds rowBounds);

    @Select("SELECT count(id) FROM `app_time_betting` where issueNo=#{issueNo} and lotteryFlag=#{lotteryFlag}")
   Integer count(@Param("issuNo") String issuNo,@Param("lotteryFlag") Integer lotteryFlag);

    Integer wininggCount(@Param("issuNo")String issuNo,@Param("lotteryFlag") Integer lotteryFlag,@Param("digital")  Integer digital,@Param("seat")  Integer seat);

    List<AppTimeBettingPo> listWininggByIssuNo(@Param("issuNo")String issuNo,@Param("lotteryFlag") Integer lotteryFlag,@Param("digital")  Integer digital,@Param("seat")  Integer seat, RowBounds rowBounds);
}
