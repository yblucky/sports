package com.xlf.server.mapper;

import com.xlf.common.po.AppUserPo;
import com.xlf.common.vo.app.UserInfoVo;
import com.xlf.common.vo.pc.HomeUser;
import com.xlf.common.vo.pc.StatisticsVo;
import com.xlf.common.vo.pc.WebStatisticsVo;
import com.xlf.server.base.BaseMapper;
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
     * 根据parentId查询用户
     *
     * @param parentId
     * @return
     */
    @Select("select * from app_user where parentId=#{parentId}")
    public AppUserPo findUserByParentId(@Param("parentId") String parentId);


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
     * 修改用户余额
     *
     * @param balance
     * @param id
     * @return
     */
    public int updateBalanceById(@Param("id") String id, @Param("balance") BigDecimal balance);

    /**
     * 修改用户冻结余额
     *
     * @param blockedBalance
     * @param id
     * @return
     */
    public int updateBlockBalanceById(@Param("id") String id, @Param("blockedBalance") BigDecimal blockedBalance);

    /**
     * 修改用户累计投注金额
     *
     * @param bettingAmout
     * @param id
     * @return
     */
    public int updateBettingAmoutById(@Param("id") String id, @Param("bettingAmout") BigDecimal bettingAmout);

    /**
     * 修改用户当天盈亏：每日凌晨清零
     *
     * @param currentProfit
     * @param id
     * @return
     */
    public int updateCurrentProfitById(@Param("id") String id, @Param("currentProfit") BigDecimal currentProfit);


    /**
     * 修改用户累计中奖金额
     *
     * @param id
     * @param winingAmout
     * @return
     */
    public Integer updateWiningAmoutById(@Param("id") String id, @Param("winingAmout") BigDecimal winingAmout);


    /**
     * 修改用户历史累计返水衡量值
     *
     * @param id
     * @param kickBackAmount
     * @return
     */
    public Integer updateKickBackAmountById(@Param("id") String id, @Param("kickBackAmount") BigDecimal kickBackAmount);


    public Integer updateUserStateById(@Param("id") String id, @Param("state") Integer state);


    public Integer updateLoginTimeById(@Param("id") String id, @Param("loginTime") Date loginTime);

    /**
     * 根据条件查询用户列表
     *
     * @param userPo
     * @return
     */
    public List<AppUserPo> getPoList(@Param("model") AppUserPo userPo, @Param("startRow") int startRow, @Param("pageSize") int pageSize);

    /**
     * 查询用户列表总数
     *
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
     */
    @Select("select * from app_user where uid=#{uid} or mobile=#{uid}")
    public AppUserPo findUid(@Param("uid") String uid);


    public WebStatisticsVo homeSUM();

    @Select("select ifnull(count(1),0) from app_user where datediff(now(),createTime)=0")
    public Integer SUMCount();


    @Update("update app_user set activeNo=activeNo+#{activeNo} where id = #{id}")
    int updateActiveNoCount(@Param("activeNo") Integer activeNo, @Param("id") String id);

    @Select("SELECT id,kickBackAmount FROM `app_user` WHERE kickBackAmount>0 LIMIT 10")
    List<AppUserPo> listWaitingReturnWaterUser();

    @Select("SELECT COUNT(id) FROM `app_user` WHERE kickBackAmount>0")
    Integer countWaitingReturnWaterUser();

    Integer batchUpdateKickBackAmout(@Param ("ids") List<String> ids);
}
