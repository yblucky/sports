package com.xlf.server.app;

import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.vo.app.UserVo;
import com.xlf.server.vo.TaskReturnWaterVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户相关业务
 * Created by Administrator on 2018/1/4 0004.
 */
public interface AppUserService {


    /**
     * 根据用户id查询用户
     *
     * @param id
     * @return
     * @throws Exception
     */
    public AppUserPo findUserById(String id) throws Exception;

    /**
     * 根据token查询用户信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    public AppUserPo getUserByToken(String token) throws Exception;

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     * @throws Exception
     */
    public AppUserPo findUserByMobile(String mobile) throws Exception;

    /**
     * 根据昵称查询用户
     *
     * @param nickName
     * @return
     * @throws Exception
     */
    public AppUserPo findUserByNickName(String nickName) throws Exception;

    /**
     * 是否是关键词汇
     */
    int findKeyWords(String nickName);

    /**
     * 注册用户
     *
     * @param userVo
     * @throws Exception
     */
    public Boolean add(UserVo userVo) throws Exception;

    /**
     * 用户登录
     *
     * @param appUserPo
     * @throws Exception
     */
    public String login(AppUserPo appUserPo) throws Exception;

    /**
     * 用户登出
     * @return
     * @throws Exception
     */
    public Boolean LoginOut(String token) throws Exception;

    /**
     * 删除用户
     */
    public int delUser(String userId) throws Exception;


    public AppUserPo findUserByParentId(String parentId) throws Exception;

    /**
     * 根据用户id修改用户信息
     *
     * @param userPo
     * @param userId
     * @return
     * @throws Exception
     */
    public int updateById(AppUserPo userPo, String userId) throws Exception;


    /**
     * 修改用户余额
     *
     * @param balance
     * @param id
     * @return
     */
    public int updateBalanceById(String id, BigDecimal balance);

    /**
     * 修改用户冻结余额
     *
     * @param blockedBalance
     * @param id
     * @return
     */
    public int updateBlockBalanceById(String id, BigDecimal blockedBalance);

    /**
     * 修改用户累计投注金额
     *
     * @param bettingAmout
     * @param id
     * @return
     */
    public int updateBettingAmoutById(String id, BigDecimal bettingAmout);

    /**
     * 修改用户当天盈亏：每日凌晨清零
     *
     * @param currentProfit
     * @param id
     * @return
     */
    public int updateCurrentProfitById(String id, BigDecimal currentProfit);


    /**
     * 修改用户累计中奖金额
     *
     * @param id
     * @param winingAmout
     * @return
     */
    public Integer updateWiningAmoutById(String id, BigDecimal winingAmout);


    /**
     * 修改用户历史累计返水衡量值
     *
     * @param id
     * @param kickBackAmount
     * @return
     */
    public Integer updateKickBackAmountById(String id, BigDecimal kickBackAmount);


    public Integer updateUserStateById(String id, Integer state);


    public Integer updateLoginTimeById(String id, Date loginTime);


    /**
     * 根据需要返水的用户
     *
     * @return
     * @throws Exception
     */
    public List<AppUserPo> listWaitingReturnWaterUserByParentId(String parentId) throws Exception;


    /**
     * 根据需要返水的用户
     *
     * @return
     * @throws Exception
     */
    public List<TaskReturnWaterVo> listWaitingReturnWaterUser() throws Exception;

    public Integer countWaitingReturnWaterUser() throws Exception;


    Integer batchUpdateKickBackAmout(List<String> ids);

    public Boolean returnWaterService(List<AppBillRecordPo> waterList, List<String> userIds);

    public boolean agentRetunWaterService() throws Exception;


    void updateStateByParentId(Integer code, String id);

    Integer updateClearTodayBettingAmoutTodayWiningAmout();

    Integer updateTodayBettingAmoutTodayWiningAmout(String id,BigDecimal  todayBettingAmout,BigDecimal todayWiningAmout);

}
