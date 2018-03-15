package com.xlf.server.mapper;


import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.AppTimeLotteryVo;
import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

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

    //获取开奖号码列表
    public List<AppTimeLotteryVo> loadLotteryInfoList(@Param("startTime")String startTime,@Param("endTime")String endTime,RowBounds rowBounds) throws Exception;

    //获取开奖号码列表
    public Integer countLotteryInfoTotal(@Param("startTime")String startTime,@Param("endTime")String endTime) throws Exception;

    @Select("SELECT * FROM `app_time_lottery` where issueNo=#{issueNo}")
    public AppTimeLotteryVo loadAwardNumber(@Param("issueNo") String issueNo) throws Exception;
}
