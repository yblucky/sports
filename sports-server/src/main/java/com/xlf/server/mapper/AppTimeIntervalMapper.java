package com.xlf.server.mapper;

import com.xlf.common.po.AppTimeIntervalPo;
import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface AppTimeIntervalMapper extends BaseMapper<AppTimeIntervalPo> {

    @Select("SELECT * FROM `app_time_interval` where type=#{type} and issueNo=#{issuNo}")
    public AppTimeIntervalPo findByIssNo(@Param("issuNo") Integer issuNo,@Param("type") Integer type);

    @Select("SELECT * FROM `app_time_interval` where type=#{type} and time=#{time}")
    public AppTimeIntervalPo findByTime(@Param("time") String time,@Param("type") Integer type);

}
