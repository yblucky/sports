package com.xlf.server.app.impl;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.LotteryFlag;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.TimeBettingBaseVo;
import com.xlf.common.vo.app.TimeBettingVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.app.AppTimeBettingService;
import com.xlf.server.app.AppUserService;
import com.xlf.server.mapper.AppTimeBettingMapper;
import com.xlf.server.mapper.AppUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 时时彩投注业务类
 */
@Service
public class AppTimeBettingServiceImpl implements AppTimeBettingService {

    @Resource
    private AppTimeBettingMapper appTimeBettingMapper;

    @Resource
    private AppUserService appUserService;
    @Resource
    private AppBillRecordService appBillRecordService;

    @Override
    public void save(String businessNumber, String issueNo, String userId, Integer lotteryOne, Integer lotteryTwo, Integer lotteryThree, Integer lotteryFour, Integer lotteryFive, Integer multiple) throws Exception{
        AppTimeBettingPo model = new AppTimeBettingPo();
        model.setId(ToolUtils.getUUID());
        model.setUserId(userId);
        model.setLotteryOne(lotteryOne);
        model.setLotteryTwo(lotteryTwo);
        model.setLotteryThree(lotteryThree);
        model.setLotteryFour(lotteryFour);
        model.setLotteryFive(lotteryFive);
        model.setLotteryFlag(LotteryFlag.NO.getCode());
        model.setCreateTime(new Date());
        model.setWinningAmount(BigDecimal.ZERO);
        model.setMultiple(multiple);
        appTimeBettingMapper.insert(model);
    }


    @Override
    @Transactional
    public void timeBettingService(String userId, TimeBettingVo vo, BigDecimal totalPrice)throws Exception{
        AppUserPo userPo=appUserService.findUserById(userId);
        BigDecimal before=userPo.getBalance();
        BigDecimal after=userPo.getBalance().subtract(totalPrice);
        String businessNumber = ToolUtils.getUUID();
        appUserService.updateBalanceById(userId,totalPrice.multiply(new BigDecimal("-1")));
        appUserService.updateBettingAmoutById(userId,totalPrice);
        appBillRecordService.saveBillRecord(businessNumber,userId, BusnessTypeEnum.TIME_BETTING.getCode(),totalPrice.multiply(new BigDecimal("-1")),before,after,"用户"+userPo.getMobile()+"时时彩下注","");
        for (TimeBettingBaseVo base : vo.getTimeList()) {
            this.save(businessNumber, vo.getIssueNo(), userId, base.getLotteryOne(), base.getLotteryTwo(), base.getLotteryThree(), base.getLotteryFour(), base.getLotteryFive(), base.getMultiple());
        }
    }
}
