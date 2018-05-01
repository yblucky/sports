package com.xlf.server.app.impl;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.RedisKeyEnum;
import com.xlf.common.enums.StateEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.language.AppMessage;
import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.service.RedisService;
import com.xlf.common.util.ConfUtils;
import com.xlf.common.util.CryptUtils;
import com.xlf.common.util.LanguageUtil;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.UserVo;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.app.AppUserService;
import com.xlf.server.app.SysAgentSettingService;
import com.xlf.server.mapper.AppUserMapper;
import com.xlf.server.mapper.SysKeyWordsMapper;
import com.xlf.server.vo.TaskReturnWaterVo;
import com.xlf.server.web.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4 0004.
 */
@Service
public class AppUserServiceImpl implements AppUserService {
    @Resource
    private LanguageUtil msgUtil;
    @Resource
    private AppUserMapper appUserMapper;
    @Resource
    private ConfUtils confUtils;
    @Resource
    private SysKeyWordsMapper sysKeyWordsMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private AppBillRecordService billRecordService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysAgentSettingService sysAgentSettingService;


    @Override
    public AppUserPo findUserById(String id) throws Exception {
        return appUserMapper.findUserById(id);
    }

    @Override
    public AppUserPo getUserByToken(String token) throws Exception {
        AppUserPo user = null;
        Object obj = redisService.getObj(token);
        if (obj != null && obj instanceof AppUserPo) {
            user = (AppUserPo) obj;
        }
        return user;
    }


    @Override
    public AppUserPo findUserByMobile(String mobile) throws Exception {
        return appUserMapper.findUserByMobile(mobile);
    }

    @Override
    public AppUserPo findUserByParentId(String parentId) throws Exception {
        return appUserMapper.findUserByParentId(parentId);
    }

    @Override
    public AppUserPo findUserByNickName(String nickName) throws Exception {
        return appUserMapper.findUserByNickName(nickName);
    }

    /**
     * 是否含有关键词
     *
     * @param nickName 昵称
     * @return >0 表示含有关键词汇
     */
    @Override
    public int findKeyWords(String nickName) {
        return sysKeyWordsMapper.findKeyWords(nickName);
    }

    /**
     * 注册用户业务
     *
     * @param userVo
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public Boolean add(UserVo userVo) throws Exception {

        AppUserPo appUserPo = new AppUserPo();
        //获取盐
        String pwdStal = ToolUtils.getUUID();
        String payStal = ToolUtils.getUUID();
        //设置登录密码
        String loginPw = CryptUtils.hmacSHA1Encrypt(userVo.getLoginPwd(), pwdStal);
        //设置支付密码
        String payPwd = CryptUtils.hmacSHA1Encrypt(userVo.getPayPwd(), payStal);

        appUserPo.setMobile(userVo.getMobile());
        appUserPo.setNickName(userVo.getNickName());
        appUserPo.setParentId(userVo.getParentId());
        appUserPo.setLoginPwd(loginPw);
        appUserPo.setPayPwd(payPwd);
        appUserPo.setPwdStal(pwdStal);
        appUserPo.setPayStal(payStal);
        appUserPo.setId(ToolUtils.getUUID());
        appUserPo.setCreateTime(new Date());
        appUserPo.setState(StateEnum.NORMAL.getCode());
        appUserPo.setBalance(BigDecimal.ZERO);
        appUserPo.setBettingAmout(BigDecimal.ZERO);
        appUserPo.setBlockedBalance(BigDecimal.ZERO);
        appUserPo.setCurrentProfit(BigDecimal.ZERO);
        appUserPo.setKickBackAmount(BigDecimal.ZERO);
        appUserPo.setWiningAmout(BigDecimal.ZERO);
        appUserPo.setErrorNo(5);
        appUserPo.setTodayBettingAmout(BigDecimal.ZERO);
        appUserPo.setTodayWiningAmout(BigDecimal.ZERO);

        int count = appUserMapper.insert(appUserPo);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String login(AppUserPo appUserPo) throws Exception {

        //获取token
        String token = RedisKeyEnum.TOKEN_API.getKey() + ToolUtils.getUUID();
        // 登录成功,将用户信息存储到redis中
        String msg = redisService.putObj(token, appUserPo, confUtils.getSessionTimeout());
        if (!msg.equalsIgnoreCase("ok")) {
            // 缓存用户信息失败
            throw new CommException("设置token到redis失败");
        } else {
            //redis是否存在
            String token_key = redisService.getString(appUserPo.getId());
            if (!StringUtils.isEmpty(token_key)) {
                //删除token_key值
                redisService.del(token_key);
            }
            //用另外一个redis去保存token_key,保证用户登录只有一个token_key
            redisService.putString(appUserPo.getId(), token, confUtils.getSessionTimeout());
            //登录成功，修改登录时间
            AppUserPo model = new AppUserPo();
            model.setLoginTime(new Date());
            appUserMapper.updateById(model, appUserPo.getId());
            return token;
        }
    }

    @Override
    public Boolean LoginOut(String userId) throws Exception {

        String token_key = redisService.getString(userId);

        if (null != userId && !StringUtils.isEmpty(token_key)) {
            //删除保存token的值
            redisService.del(userId);
            //删除token_key值
            redisService.del(token_key);
        }
        return true;
    }

    @Override
    public int delUser(String userId) throws Exception {
        if (StringUtils.isEmpty(userId)) {
            return 0;
        }
        int rows = 0;
        if (rows < 1) {
            throw new CommException(msgUtil.getMsg(AppMessage.DELUSER_FAILURE, "删除用户失败"));
        }
        rows = appUserMapper.deleteByPrimaryKey(userId);
        if (rows < 1) {
            throw new CommException(msgUtil.getMsg(AppMessage.DELUSER_FAILURE, "删除用户失败"));
        }
        return rows;
    }

    /**
     * 根据用户id修改用户信息
     *
     * @param userPo
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public int updateById(AppUserPo userPo, String userId) throws Exception {
        return appUserMapper.updateById(userPo, userId);
    }


    @Override
    public int updateBalanceById(String id, BigDecimal balance) {
        return appUserMapper.updateBalanceById(id, balance);
    }

    @Override
    public int updateBlockBalanceById(String id, BigDecimal blockedBalance) {
        return appUserMapper.updateBlockBalanceById(id, blockedBalance);
    }

    @Override
    public int updateBettingAmoutById(String id, BigDecimal bettingAmout) {
        return appUserMapper.updateBettingAmoutById(id, bettingAmout);
    }

    @Override
    public int updateCurrentProfitById(String id, BigDecimal currentProfit) {
        return appUserMapper.updateCurrentProfitById(id, currentProfit);
    }

    @Override
    public Integer updateWiningAmoutById(String id, BigDecimal winingAmout) {
        return appUserMapper.updateWiningAmoutById(id, winingAmout);
    }

    @Override
    public Integer updateKickBackAmountById(String id, BigDecimal kickBackAmount) {
        return appUserMapper.updateKickBackAmountById(id, kickBackAmount);
    }

    @Override
    public Integer updateUserStateById(String id, Integer state) {
        return appUserMapper.updateUserStateById(id, state);
    }

    @Override
    public Integer updateLoginTimeById(String id, Date loginTime) {
        return appUserMapper.updateLoginTimeById(id, loginTime);
    }

    @Override
    public List<AppUserPo> listWaitingReturnWaterUserByParentId(String parentId) throws Exception {
        List<AppUserPo> list = null;
        list = appUserMapper.listWaitingReturnWaterUserByParentId(parentId);
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    @Override
    public List<TaskReturnWaterVo> listWaitingReturnWaterUser() throws Exception {
        List<TaskReturnWaterVo> list = null;
        list = appUserMapper.listWaitingReturnWaterUser();
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    @Override
    public Integer countWaitingReturnWaterUser() throws Exception {
        Integer count = 0;
        count = appUserMapper.countWaitingReturnWaterUser();
        if (count == null) {
            return 0;
        }
        return count;
    }

    @Override
    public Integer batchUpdateKickBackAmout(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        return appUserMapper.batchUpdateKickBackAmout(ids);
    }

    @Override
    public Boolean returnWaterService(List<AppBillRecordPo> waterList, List<String> userIds) {
        //已返水的清零处理
        this.batchUpdateKickBackAmout(userIds);
        //插入返水的流水
        billRecordService.batchSaveKickBackAmoutRecord(waterList);
        return true;
    }

    @Override
    @Transactional
    public boolean agentRetunWaterService() throws Exception {

        Integer count = this.countWaitingReturnWaterUser();
        if (count == 0) {
            return true;
        }
        List<AppBillRecordPo> waterList = new ArrayList<>();
        List<String> userIds = new ArrayList<>();
        List<TaskReturnWaterVo> appUserPoList = this.listWaitingReturnWaterUser();
        String bunessNum = ToolUtils.getUUID();
        for (TaskReturnWaterVo po : appUserPoList) {
            SysUserVo sysUserVo = sysUserService.findById(po.getParentId());
            SysAgentSettingPo sysAgentSettingPo = sysAgentSettingService.findById(sysUserVo.getAgentLevelId());
            //获取返水比率
            BigDecimal rate = sysAgentSettingPo.getReturnWaterScale();
            BigDecimal returnAmount = po.getSumKickBackAmount().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            BigDecimal beforTotal = sysUserVo.getTotalReturnWater();
            BigDecimal afterTotal = beforTotal.add(returnAmount);
            AppBillRecordPo billRecordPo = new AppBillRecordPo();
            billRecordPo.setId(ToolUtils.getUUID());
            billRecordPo.setUserId(sysUserVo.getId());
            billRecordPo.setBeforeBalance(beforTotal);
            billRecordPo.setAfterBalance(afterTotal);
            billRecordPo.setBalance(returnAmount);
            billRecordPo.setBusinessNumber(bunessNum);
            billRecordPo.setBusnessType(BusnessTypeEnum.RETURN_WATER.getCode());
            billRecordPo.setCreateTime(new Date());
            billRecordPo.setRemark("代理返水结算,此次返水基数是:" + po.getSumKickBackAmount() + "，返水比例是:" + sysAgentSettingPo.getReturnWaterScale());
            billRecordPo.setExtend("");
            waterList.add(billRecordPo);
            List<AppUserPo> childUserList = this.listWaitingReturnWaterUserByParentId(po.getParentId());
            if (!CollectionUtils.isEmpty(childUserList)) {
                for (AppUserPo childPo : childUserList) {
                    BigDecimal beforTotalChildPo = childPo.getKickBackAmount();
                    BigDecimal afterTotalChildPo = BigDecimal.ZERO;
                    AppBillRecordPo childBillRecordPo = new AppBillRecordPo();
                    childBillRecordPo.setId(ToolUtils.getUUID());
                    childBillRecordPo.setUserId(childPo.getId());
                    childBillRecordPo.setBeforeBalance(beforTotalChildPo);
                    childBillRecordPo.setAfterBalance(afterTotalChildPo);
                    childBillRecordPo.setBalance(beforTotalChildPo);
                    childBillRecordPo.setBusinessNumber(bunessNum);
                    childBillRecordPo.setBusnessType(BusnessTypeEnum.REDUCE_KICKBACKAMOUNT_RECORD.getCode());
                    childBillRecordPo.setCreateTime(new Date());
                    childBillRecordPo.setRemark("返水结算,对冲返水衡量值");
                    childBillRecordPo.setExtend(sysUserVo.getLoginName());
                    waterList.add(childBillRecordPo);
                    userIds.add(childPo.getId());
                }
            }

            Integer row = sysUserService.updateReturnWater(sysUserVo.getId(), returnAmount, returnAmount);
            if (row == null || row == 0) {
                throw new CommException("更新代理今日和累计返水错误");
            }
            /*row= sysUserService.updateBalance(sysUserVo.getId(),returnAmount);
            if (row == null || row == 0) {
                throw new CommException("更新代理返水余额错误");
            }*/
        }
        this.returnWaterService(waterList, userIds);
        return false;
    }

    @Override
    public void updateStateByParentId(Integer state, String parentId) {
        appUserMapper.updateStateByParentId(state, parentId);
    }


    @Override
    public Integer updateClearTodayBettingAmoutTodayWiningAmout() {
        return appUserMapper.updateClearTodayBettingAmoutTodayWiningAmout();
    }


    @Override
    public Integer updateTodayBettingAmoutTodayWiningAmout(String id, BigDecimal todayBettingAmout, BigDecimal todayWiningAmout) {
        return appUserMapper.updateTodayBettingAmoutTodayWiningAmout(id, todayBettingAmout, todayWiningAmout);
    }
}
