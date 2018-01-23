package com.pf.server.mapper;

import com.pf.common.po.AppUserPo;
import com.pf.common.vo.app.UserInfoVo;
import com.pf.common.vo.pc.AppBillRecordVo1;
import com.pf.common.vo.pc.HomeUser;
import com.pf.common.vo.pc.StatisticsVo;
import com.pf.server.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4 0004.
 */
@Repository
public interface AppUserMapper extends BaseMapper<AppUserPo> {

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Select("select * from app_user where id=#{id}")
    public AppUserPo findUserById(@Param("id") String id);

    /**
     * 根据接点人父id查询用户
     *
     * @param parentId
     * @return
     */
    @Select("SELECT  u.id,u.nickName,u.state,u.mobile,c.level,c.performanceA,c.performanceB,c.parentId,c.currentArea FROM `app_user` u INNER JOIN app_user_contact  c on  u.id=c.userId where c.parentId=#{parentId}")
    public List<UserInfoVo> findUserByContactParentId(@Param("parentId") String parentId);


    /**
     * 根据接点人userId查询用户
     *
     * @param userId
     * @return
     */
    @Select("SELECT  u.id,u.nickName,u.state,u.mobile,c.level,c.performanceA,c.performanceB,c.parentId,c.currentArea FROM `app_user` u INNER JOIN app_user_contact  c on  u.id=c.userId where c.userId=#{userId}")
    public UserInfoVo findUserByContactUserId(@Param("userId") String userId);


    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    @Select("select * from app_user where mobile=#{mobile}")
    public AppUserPo findUserByMobile(@Param("mobile") String mobile);

    /**
     * 根据昵称查询用户
     *
     * @param nickName
     * @return
     */
    @Select("select * from app_user where nickName=#{nickName} limit 1")
    public AppUserPo findUserByNickName(@Param("nickName") String nickName);

    /**
     * 根据id更新用户信息
     *
     * @param appUserPo
     * @return
     */
    public int updateById(@Param("model") AppUserPo appUserPo, @Param("id") String id);

    /**
     * 修改用户ep余额
     *
     * @param epBalance
     * @param id
     * @return
     */
    public int updateEpBalanceById(@Param("epBalance") BigDecimal epBalance, @Param("id") String id);

    /**
     * 修改用户冻结ep余额
     *
     * @param blockedEpBalance
     * @param id
     * @return
     */
    public int updateEpBlockBalanceById(@Param("blockedEpBalance") BigDecimal blockedEpBalance, @Param("id") String id);

    /**
     * 修改用户候鸟
     *
     * @param birdScore
     * @param id
     * @return
     */
    public int updateBirdScoreById(@Param("birdScore") BigDecimal birdScore, @Param("id") String id);

    /**
     * 修改用户e资产
     *
     * @param assets
     * @param id
     * @return
     */
    public int updateAssetsById(@Param("assets") BigDecimal assets, @Param("id") String id);


    /**
     * 修改用户激活次数
     *
     * @param id
     * @param activeNo
     * @return
     */
    public Integer updateActiveNoById(@Param("id") String id, @Param("activeNo") Integer activeNo);

    public Integer updateUserStateById(@Param("id") String id, @Param("state") Integer state);

    /**
     * 根据条件查询用户列表
     *
     * @param userPo
     * @return
     */
    public List<AppUserPo> getPoList(@Param("model") AppUserPo userPo, @Param("startRow") int startRow, @Param("pageSize") int pageSize);

    /**
     * 查询用户列表总数
     * @param userPo
     * @return
     */
    public Integer findPoListCount(@Param("model") AppUserPo userPo);

    /**
     * 统计数据
     *
     * @return
     */
    @Select("SELECT SUM(balance) AS balance,SUM(score) AS score,SUM(shadowScore) AS shadowScore,SUM(virtualCoin) AS virtualCoin,COUNT(id) AS userCount FROM app_user WHERE state=10 OR state=20")
    StatisticsVo userStatistics() throws Exception;

    /**
     * 今日新增用户
     *
     * @return
     */
    @Select("SELECT COUNT(1) FROM app_user WHERE createTime>=#{starTime} AND createTime<#{endTime}")
    Integer getUserBytime(@Param("starTime") Date StarTime, @Param("endTime") Date endTime);

    /**
     * 查询该用户的下线
     *
     * @param parentId
     * @return
     */

    @Select("select * from app_user where parentId=#{parentId}")
    public List<AppUserPo> getLowerPoList(@Param("parentId") String parentId);
    
    /**
     * 根据uid查询用户
     * 
     * 
     */
    @Select("select * from app_user where uid=#{uid} or mobile=#{uid}")
    public AppUserPo findUid(@Param("uid") String uid);
    
    
    public HomeUser homeSUM();
    
    public Integer SUMCount(@Param("model") AppUserPo po); 
    
    
    @Update("update app_user set activeNo=activeNo+#{activeNo} where id = #{id}")
    int updateActiveNoCount(@Param("activeNo") Integer activeNo, @Param("id") String id);
    

}
