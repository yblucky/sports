package com.pf.server.mapper;


import com.pf.common.po.AppPerformanceRecordPo;
import com.pf.common.vo.app.PerformanceRecordVo;
import com.pf.common.vo.pc.AppPerformanceRecordVo;
import com.pf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 业绩叠加记录mapper
 */
public interface AppPerformanceRecordMapper extends BaseMapper<AppPerformanceRecordPo> {

    public Integer insertPerformanceRecordList(@Param("list") List<AppPerformanceRecordPo> list);
    
    /**
	 * 查询业绩流水
	 */
    List<AppPerformanceRecordVo> findAll(@Param("model") AppPerformanceRecordVo vo,@Param("startRow")int startRow,@Param("pageSize")int pageSize);

    Integer findCount(@Param("model") AppPerformanceRecordVo vo);


    @Select("SELECT count(1)  FROM `app_performance_record` r INNER JOIN app_user u on u.id=r.userId and r.userId=#{userId} and r.department=#{department}")
    public Integer performanceTotal(@Param("userId") String userId,@Param("department") String area);

    /**
     * 业绩记录
     * @param userId
     * @param area
     * @param rowBounds
     * @return
     */
    @Select("SELECT u.imgPath,u.nickName,u.mobile,r.amount,r.type,r.createTime  FROM `app_performance_record` r INNER JOIN app_user u on u.id=r.userId and r.userId=#{userId} and r.department=#{department} order by r.createTime desc")
    public List<PerformanceRecordVo> performanceList(@Param("userId") String userId, @Param("department") String area, RowBounds rowBounds);

}
