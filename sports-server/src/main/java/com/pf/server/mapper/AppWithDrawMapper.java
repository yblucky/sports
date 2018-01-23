package com.pf.server.mapper;


import com.pf.common.po.AppWithDrawPo;
import com.pf.common.vo.app.DrawRecordVo;
import com.pf.common.vo.pc.AppWithDrawVo;
import com.pf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

/**
 * 业绩提现mapper
 */
public interface AppWithDrawMapper extends BaseMapper<AppWithDrawPo> {


    /**
     * 查询所有提现记录
     *
     * @param
     * @return startRow，pageSize分页
     */
    List<AppWithDrawVo> findAll(@Param("model") AppWithDrawVo vo, @Param("startRow") int startRow, @Param("pageSize") int pageSize);

    /**
     * @param
     * @return
     */
    Integer findCount(@Param("model") AppWithDrawVo vo);

    @Select("SELECT count(1)  FROM `app_withdraw` r INNER JOIN app_user u on u.id=r.userId and r.userId=#{userId}")
    public Integer drawRecordTotal(@Param("userId") String userId);

    /**
     * 业绩记录
     *
     * @param userId
     * @param rowBounds
     * @return
     */
    @Select("SELECT u.imgPath,u.nickName,u.mobile,r.amount,r.state,r.createTime  FROM `app_withdraw` r INNER JOIN app_user u on u.id=r.userId and r.userId=#{userId} order by createTime desc ")
    public List<DrawRecordVo> drawRecordList(@Param("userId") String userId, RowBounds rowBounds);
    
    /**
     * 提现总金额
     * @param vo
     * @return
     */
    
    public BigDecimal findSUM(@Param("model") AppWithDrawVo vo);


}
