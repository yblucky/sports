package com.xlf.server.app.impl;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.enums.RacingSeatEnum;
import com.xlf.common.enums.TimeSeatEnum;
import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.RacingBettingBaseVo;
import com.xlf.common.vo.app.RacingBettingVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.app.AppRacingBettingService;
import com.xlf.server.app.AppUserService;
import com.xlf.server.mapper.AppRacingBettingMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
        model.setLotterySix(lotterySix);
        model.setLotterySeven(lotterySeven);
        model.setLotteryEight(lotteryEight);
        model.setLotteryNine(lotteryNine);
        model.setLotteryTen(lotteryTen);
        model.setLotteryFlag(LotteryFlagEnum.NO.getCode());
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
        appBillRecordService.saveBillRecord(businessNumber, userId, BusnessTypeEnum.RACING_BETTING.getCode(), totalPrice, before, after, "用户" + userPo.getMobile() + "北京赛车下注", "");
        for (RacingBettingBaseVo base : vo.getRaingList()) {
            this.save(businessNumber, vo.getIssueNo(), userId, base.getLotteryOne(), base.getLotteryTwo(), base.getLotteryThree(), base.getLotteryFour(), base.getLotteryFive(), base.getLotterySix(), base.getLotterySeven(), base.getLotteryEight(), base.getLotteryNine(), base.getLotteryTen(), base.getMultiple());
        }
    }

    @Override
    public List<AppRacingBettingPo> listByIssuNo(String issuNo, Integer lotteryFlag, Paging paging) {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        if (StringUtils.isEmpty(issuNo)) {
            return Collections.emptyList();
        }
        AppRacingBettingPo model = new AppRacingBettingPo();
        model.setIssueNo(issuNo);
        List<AppRacingBettingPo> list = appRacingBettingMapper.list(issuNo, lotteryFlag, rowBounds);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    @Override
    public Integer count(String issuNo, Integer lotteryFlag) {
        Integer count = 0;
        count = appRacingBettingMapper.count(issuNo, lotteryFlag);
        if (count == null) {
            count = 0;
        }
        return count;
    }

    @Override
    public Integer updateLotteryFlagById(String id, BigDecimal winingAmout) {
        return appRacingBettingMapper.updateLotteryFlagById(id,winingAmout);
    }

    @Override
    public Integer updateBatchLotteryFlag(String issueNo) {
        return appRacingBettingMapper.updateBatchLotteryFlag(issueNo);
    }




    @Override
    public List<AppRacingBettingPo> listWininggByIssuNo(String issuNo, Integer lotteryFlag, Paging paging, Integer digital, RacingSeatEnum seat) {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        if (StringUtils.isEmpty(issuNo)) {
            return Collections.emptyList();
        }
        List<AppRacingBettingPo> list = appRacingBettingMapper.listWininggByIssuNo(issuNo, lotteryFlag, digital, seat.getCode(), rowBounds);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    @Override
    public Integer wininggCount(String issuNo, Integer lotteryFlag, Integer digital, RacingSeatEnum seat) {
        Integer count = 0;
        count = appRacingBettingMapper.wininggCount(issuNo, lotteryFlag, digital, seat.getCode());
        if (count == null) {
            count = 0;
        }
        return count;
    }
}
