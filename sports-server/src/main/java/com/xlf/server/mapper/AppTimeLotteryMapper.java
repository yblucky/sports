package com.xlf.server.mapper;


import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 时时彩开奖
 * @version v1.0
 * @date 2017年6月14日
 */
public interface AppTimeLotteryMapper extends BaseMapper<AppTimeLotteryPo> {

    @Select("SELECT * FROM `app_time_lottery` where flag=10 ORDER BY createTime asc LIMIT 1")
    AppTimeLotteryPo findLast();

    Integer updateFlagById(@Param("id") String id);

    @Select("SELECT * FROM `app_time_lottery` where issueNo=#{issueNo}")
    AppTimeLotteryPo findAppTimeLotteryPoByIssuNo(@Param("issueNo") String issuNo);
}
