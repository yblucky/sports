package com.xlf.server.mapper;


import com.xlf.common.po.AppRacingLotteryPo;
import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.common.vo.app.AppTimeLotteryVo;
import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 赛车开奖
 *
 * @version v1.0
 * @date 2017年6月14日
 */
public interface AppRacingLotteryMapper extends BaseMapper<AppRacingLotteryPo> {
    @Select("SELECT * FROM `app_racing_lottery` where flag=10   ORDER BY createTime asc LIMIT 1")
    AppRacingLotteryPo findLast();

    Integer updateFlagById(@Param("id") String id);

    @Select("SELECT * FROM `app_racing_lottery` where issueNo=#{issueNo}")
    AppRacingLotteryPo findAppRacingLotteryPoByIssuNo(String issuNo);

    @Select("SELECT * FROM `app_racing_lottery` order by lotteryTime desc LIMIT 1")
    public AppRacingLotteryPo loadAwardNumber() throws Exception;

    //获取开奖号码列表
    public List<AppRacingLotteryPo> loadLotteryInfoList(@Param("startTime")String startTime, @Param("endTime")String endTime, RowBounds rowBounds) throws Exception;

    //获取开奖号码列表
    public Integer countLotteryInfoTotal(@Param("startTime")String startTime,@Param("endTime")String endTime) throws Exception;
}
