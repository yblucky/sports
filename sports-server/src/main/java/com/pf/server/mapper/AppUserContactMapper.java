package com.pf.server.mapper;


import com.pf.common.po.AppPerformanceParamPo;
import com.pf.common.po.AppUserContactPo;
import com.pf.common.po.AppUserPo;
import com.pf.common.vo.pc.AppUserContactVo;
import com.pf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 *用户接点人mapper
 */
@Repository
public interface AppUserContactMapper extends BaseMapper<AppUserContactPo> {

    /**
     * 根据userId查询用户
     * @param userId
     * @return
     */
    @Select("select * from app_user_contact where userId=#{userId}")
    public AppUserContactPo findUserByUserId(@Param("userId") String userId);

    /**
     * 根据parentId查询用户
     * @param parentId
     * @return
     */
    @Select("select * from app_user where parentId=#{parentId} limit 1")
    public AppUserContactPo findUserByParentId(@Param("parentId") String parentId);

    /**
     * 批量更新用户业绩   
     * @param ids
     * @param amount
     * @return
     */
    public Integer updatePerformanceListA(@Param("ids")List<String> ids, @Param("amount") BigDecimal amount);

    /**
     * 批量更新用户业绩
     * @param ids
     * @param amount
     * @return
     */
    public Integer updatePerformanceListB(@Param("ids")List<String> ids, @Param("amount") BigDecimal amount);
    
    
    /**
     * 根据parentId查询用户
     * @param parentId
     * @return
     */
    @Select("select c.*,u.uid,u.mobile from app_user_contact c  left join app_user u  on c.userId = u.id  where c.parentId=#{parentId}")
    public List<AppUserContactVo> webFindUserByParentId(@Param("parentId") String parentId);
    
    
    @Select("select c.*,u.uid,u.mobile from app_user_contact c  left join app_user u  on c.userId = u.id  where c.userId=#{userId}")
    public AppUserContactVo webfindUserByUserId(@Param("userId") String userId);

}
