package com.xlf.server.mapper;


import com.xlf.common.po.AppRacingLotteryPo;
import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 赛车开奖
 *
 * @version v1.0
 * @date 2017年6月14日
 */
public interface AppRacingLotteryMapper extends BaseMapper<AppRacingLotteryPo> {
    @Select("SELECT * FROM `app_racing_lottery` ORDER BY createTime asc LIMIT 1")
    AppRacingLotteryPo findLast();

    Integer updateFlagById(@Param("id") String id);

    @Select("SELECT * FROM `app_racing_lottery` where issueNo=#{issueNo}")
    AppRacingLotteryPo findAppRacingLotteryPoByIssuNo(String issuNo);
}
