package com.xlf.server.app.impl;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.LotteryFlag;
import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.RacingBettingBaseVo;
import com.xlf.common.vo.app.RacingBettingVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.app.AppRacingBettingService;
import com.xlf.server.app.AppUserService;
import com.xlf.server.mapper.AppRacingBettingMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 时时彩投注业务类
 */
public class AppRacingBettingServiceImpl implements AppRacingBettingService {
    @Resource
    private AppRacingBettingMapper appRacingBettingMapper;

    @Resource
    private AppUserService appUserService;
    @Resource
    private AppBillRecordService appBillRecordService;

    @Override
    public void save(String businessNumber, String issueNo, String userId, Integer lotteryOne, Integer lotteryTwo, Integer lotteryThree, Integer lotteryFour, Integer lotteryFive, Integer lotterySix, Integer lotterySeven, Integer lotteryEight, Integer lotteryNine, Integer lotteryTen, Integer multiple) throws Exception {
        AppRacingBettingPo model = new AppRacingBettingPo();
        model.setId(ToolUtils.getUUID());
        model.setUserId(userId);
        model.setLotteryOne(lotteryOne);
        model.setLotteryTwo(lotteryTwo);
        model.setLotteryThree(lotteryThree);
        model.setLotteryFour(lotteryFour);
        model.setLotteryFive(lotteryFive);
        model.setLotterySix(0);
        model.setLotterySeven(0);
        model.setLotteryEight(0);
        model.setLotteryNine(0);
        model.setLotteryTen(0);
        model.setLotteryFlag(LotteryFlag.NO.getCode());
        model.setCreateTime(new Date());
        model.setWinningAmount(BigDecimal.ZERO);
        model.setMultiple(multiple);
        appRacingBettingMapper.insert(model);
    }


    @Override
    @Transactional
    public void racingBettingService(String userId, RacingBettingVo vo, BigDecimal totalPrice) throws Exception {
        AppUserPo userPo = appUserService.findUserById(userId);
        BigDecimal before = userPo.getBalance();
        BigDecimal after = userPo.getBalance().subtract(totalPrice);

        String businessNumber = ToolUtils.getUUID();
        appUserService.updateBalanceById(userId, totalPrice.multiply(new BigDecimal("-1")));
        appUserService.updateBettingAmoutById(userId, totalPrice);
        appBillRecordService.saveBillRecord(businessNumber, userId, BusnessTypeEnum.TIME_BETTING.getCode(), totalPrice, before, after, "用户下注", "");
        for (RacingBettingBaseVo base : vo.getRaingList()) {
//            this.save(businessNumber, vo.getIssueNo(), userId, base.getLotteryOne(), base.getLotteryTwo(), base.getLotteryThree(), base.getLotteryFour(), base.getLotteryFive(), base.getMultiple());
        }
    }
}
