package com.pf.server.mapper;
import com.pf.common.po.AppCountryCode;
import com.pf.common.po.AppPerformanceParamPo;
import com.pf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

/**
 *业绩参数mapper
 */
public interface AppPerformanceParamMapper extends BaseMapper<AppPerformanceParamPo> {

    @Select("SELECT * FROM app_performance_param  WHERE  #{performance}>=minRange and  #{performance}<maxRange AND type=#{type} LIMIT 1")
    public  AppPerformanceParamPo findOneAppPerformanceParamPo(@Param("performance")BigDecimal amount ,@Param("type")Integer type );
    
    
    
  
    /**
     * 获取所有的用户星级
     */
    @Select("select * from app_performance_param")
    public List<AppPerformanceParamPo> readAll();
    
    
    @Select("select * from app_performance_param order by type asc ,  minRange  asc")
    public List<AppPerformanceParamPo> findAll(RowBounds rwoBounds);
    
    
    @Select("select count(1) from app_performance_param")
    public long findCount();



}
