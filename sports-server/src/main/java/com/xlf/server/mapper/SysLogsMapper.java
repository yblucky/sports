package com.xlf.server.mapper;


import com.xlf.common.po.SysLogsPo;
import com.xlf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 日志信息表操作类
 * @author qsy
 * @version v1.0
 * @date 2016年11月26日
 */
public interface SysLogsMapper extends BaseMapper<SysLogsPo> {

    @Select("select * from sys_logs")
    List<SysLogsPo> findAll(RowBounds rowBounds);

    @Select("select count(1) from sys_logs")
    long findCount();

}
