package com.xlf.server.app.impl;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.enums.TimeSeatEnum;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.TimeBettingBaseVo;
import com.xlf.common.vo.app.TimeBettingVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.app.AppTimeBettingService;
import com.xlf.server.app.AppUserService;
import com.xlf.server.mapper.AppTimeBettingMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
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
@Service
public class AppTimeBettingServiceImpl implements AppTimeBettingService {

    @Resource
    private AppTimeBettingMapper appTimeBettingMapper;

    @Resource
    private AppUserService appUserService;
    @Resource
    private AppBillRecordService appBillRecordService;


    @Override
    public void save(String businessNumber, String issueNo, String userId, Integer lotteryOne, Integer lotteryTwo, Integer lotteryThree, Integer lotteryFour, Integer lotteryFive, Integer multiple) throws Exception {
        AppTimeBettingPo model = new AppTimeBettingPo();
        model.setId(ToolUtils.getUUID());
        model.setUserId(userId);
        model.setLotteryOne(lotteryOne);
        model.setLotteryTwo(lotteryTwo);
        model.setLotteryThree(lotteryThree);
        model.setLotteryFour(lotteryFour);
        model.setLotteryFive(lotteryFive);
        model.setLotteryFlag(LotteryFlagEnum.NO.getCode());
        model.setCreateTime(new Date());
        model.setWinningAmount(BigDecimal.ZERO);
        model.setMultiple(multiple);
        appTimeBettingMapper.insert(model);
    }


    @Override
    @Transactional
    public void timeBettingService(String userId, TimeBettingVo vo, BigDecimal totalPrice) throws Exception {
        AppUserPo userPo = appUserService.findUserById(userId);
        BigDecimal before = userPo.getBalance();
        BigDecimal after = userPo.getBalance().subtract(totalPrice);
        String businessNumber = ToolUtils.getUUID();
        appUserService.updateBalanceById(userId, totalPrice.multiply(new BigDecimal("-1")));
        appUserService.updateBettingAmoutById(userId, totalPrice);
        appBillRecordService.saveBillRecord(businessNumber, userId, BusnessTypeEnum.TIME_BETTING.getCode(), totalPrice.multiply(new BigDecimal("-1")), before, after, "用户" + userPo.getMobile() + "时时彩下注", "");
        for (TimeBettingBaseVo base : vo.getTimeList()) {
            this.save(businessNumber, vo.getIssueNo(), userId, base.getLotteryOne(), base.getLotteryTwo(), base.getLotteryThree(), base.getLotteryFour(), base.getLotteryFive(), base.getMultiple());
        }
    }


    @Override
    public List<AppTimeBettingPo> listByIssuNo(String issuNo, Integer lotteryFlag, Paging paging) {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        if (StringUtils.isEmpty(issuNo)) {
            return Collections.emptyList();
        }
        List<AppTimeBettingPo> list = appTimeBettingMapper.list(issuNo, lotteryFlag, rowBounds);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    @Override
    public Integer count(String issuNo, Integer lotteryFlag) {
        Integer count = 0;
        count = appTimeBettingMapper.count(issuNo, lotteryFlag);
        if (count == null) {
            count = 0;
        }
        return count;
    }


    @Override
    public List<AppTimeBettingPo> listWininggByIssuNo(String issuNo, Integer lotteryFlag, Paging paging, Integer digital, TimeSeatEnum seat) {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        if (StringUtils.isEmpty(issuNo)) {
            return Collections.emptyList();
        }
        List<AppTimeBettingPo> list = appTimeBettingMapper.listWininggByIssuNo(issuNo, lotteryFlag, digital, seat.getCode(), rowBounds);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    @Override
    public Integer wininggCount(String issuNo, Integer lotteryFlag, Integer digital, TimeSeatEnum seat) {
        Integer count = 0;
        count = appTimeBettingMapper.wininggCount(issuNo, lotteryFlag, digital, seat.getCode());
        if (count == null) {
            count = 0;
        }
        return count;
    }

    @Override
    public Integer updateLotteryFlagById(String id, BigDecimal winingAmout) {
        return appTimeBettingMapper.updateLotteryFlagById(id, winingAmout);
    }


    @Override
    public Integer updateBatchLotteryFlagByIds(List<String> list) {
        return appTimeBettingMapper.updateBatchLotteryFlagByIds(list);
    }

    @Override
    public Integer updateBatchLotteryFlag(String issueNo) {
        return appTimeBettingMapper.updateBatchLotteryFlag(issueNo);
    }
}
