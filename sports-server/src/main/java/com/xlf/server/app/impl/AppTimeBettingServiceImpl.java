package com.xlf.server.app.impl;

import com.xlf.common.enums.BetTypeEnum;
import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.enums.TimeSeatEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.po.AppRacingBettingPo;
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
    public void save(String businessNumber, String issueNo, String userId, Integer lotteryOne, Integer lotteryTwo, Integer lotteryThree, Integer lotteryFour, Integer lotteryFive, Integer multiple,Integer betType,String timeBetContent) throws Exception {
        AppTimeBettingPo model = new AppTimeBettingPo ();
        model.setId (ToolUtils.getUUID ());
        model.setIssueNo(issueNo);
        model.setBusinessNumber(businessNumber);
        model.setUserId (userId);
        model.setLotteryOne (lotteryOne);
        model.setLotteryTwo (lotteryTwo);
        model.setLotteryThree (lotteryThree);
        model.setLotteryFour (lotteryFour);
        model.setLotteryFive (lotteryFive);
        model.setLotteryFlag (LotteryFlagEnum.NO.getCode ());
        model.setCreateTime (new Date ());
        model.setWinningAmount (BigDecimal.ZERO);
        model.setBetType (betType);
        model.setBettingContent (timeBetContent);
        model.setMultiple (multiple);
        appTimeBettingMapper.insert (model);
    }


    @Override
    @Transactional
    public void timeBettingService(String userId, TimeBettingVo vo, BigDecimal totalPrice) throws Exception {
        AppUserPo userPo = appUserService.findUserById (userId);
        BigDecimal before = userPo.getBalance ();
        BigDecimal after = userPo.getBalance ().subtract (totalPrice);
        String businessNumber = ToolUtils.getUUID ();
        appUserService.updateBalanceById (userId, totalPrice.multiply (new BigDecimal ("-1")));
        appUserService.updateBettingAmoutById (userId, totalPrice);
        appBillRecordService.saveBillRecord (businessNumber, userId, BusnessTypeEnum.TIME_BETTING.getCode (), totalPrice.multiply (new BigDecimal ("-1")), before, after, "用户" + userPo.getMobile () + "时时彩下注", "");
        for (TimeBettingBaseVo base : vo.getTimeList ()) {
            this.save (businessNumber, vo.getIssueNo (), userId, base.getLotteryOne (), base.getLotteryTwo (), base.getLotteryThree (), base.getLotteryFour (), base.getLotteryFive (), base.getMultiple (),vo.getBetType (),base.getBettingContent ());
        }
    }


    @Override
    public List<AppTimeBettingPo> listByIssuNo(String issuNo, Integer lotteryFlag, Paging paging) {
        RowBounds rowBounds = new RowBounds (paging.getPageNumber (), paging.getPageSize ());
        if (StringUtils.isEmpty (issuNo)) {
            return Collections.emptyList ();
        }
        List<AppTimeBettingPo> list = appTimeBettingMapper.list (issuNo, lotteryFlag, rowBounds);
        if (list == null) {
            list = Collections.emptyList ();
        }
        return list;
    }

    @Override
    public Integer count(String issuNo, Integer lotteryFlag) {
        Integer count = 0;
        count = appTimeBettingMapper.count (issuNo, lotteryFlag);
        if (count == null) {
            count = 0;
        }
        return count;
    }


    @Override
    public List<AppTimeBettingPo> listWininggByIssuNo(String issuNo, Integer lotteryFlag, Paging paging, Integer digital, TimeSeatEnum seat) {
        RowBounds rowBounds = new RowBounds (paging.getPageNumber (), paging.getPageSize ());
        if (StringUtils.isEmpty (issuNo)) {
            return Collections.emptyList ();
        }
        List<AppTimeBettingPo> list = appTimeBettingMapper.listWininggByIssuNo (issuNo, lotteryFlag, digital, seat.getCode (), rowBounds);
        if (list == null) {
            list = Collections.emptyList ();
        }
        return list;
    }

    @Override
    public Integer wininggCount(String issuNo, Integer lotteryFlag, Integer digital, TimeSeatEnum seat) {
        Integer count = 0;
        count = appTimeBettingMapper.wininggCount (issuNo, lotteryFlag, digital, seat.getCode ());
        if (count == null) {
            count = 0;
        }
        return count;
    }

    @Override
    public Integer updateLotteryFlagById(String id, BigDecimal winingAmout) {
        return appTimeBettingMapper.updateLotteryFlagById (id, winingAmout);
    }


    @Override
    public Integer updateBatchLotteryFlag(String issueNo) {
        return appTimeBettingMapper.updateBatchLotteryFlag (issueNo);
    }

    @Override
    public Integer recordListTotal(String id, String businessNumber) {
        Integer count = 0;
        count = appTimeBettingMapper.recordListTotal(id, businessNumber);
        if (count == null) {
            count = 0;
        }
        return count;
    }

    @Override
    public List<AppTimeBettingPo> findRecordList(String userId, String businessNumber, Paging paging) {
        RowBounds rowBounds = new RowBounds (paging.getPageNumber (), paging.getPageSize ());
        if (StringUtils.isEmpty (businessNumber) || StringUtils.isEmpty (userId)) {
            return Collections.emptyList ();
        }
        List<AppTimeBettingPo> list = appTimeBettingMapper.findRecordList (userId, businessNumber, rowBounds);
        if (list == null) {
            list = Collections.emptyList ();
        }
        return list;
    }


    @Override
    public AppTimeBettingPo findById(String id) {
        if (StringUtils.isEmpty (id)) {
            return null;
        }
        return appTimeBettingMapper.selectByPrimaryKey (id);
    }

    @Override
    public Integer countBettingByUserIdAndIssueNoAndContent(String userId, String issueNo, String bettingContent) throws Exception {
        Integer count = 0;
        count = appTimeBettingMapper.countBettingByUserIdAndIssueNoAndContent (userId,issueNo,bettingContent);
        if (count == null) {
            count = 0;
        }
        return count;
    }

    @Override
    public List<AppTimeBettingPo> findListByUserIdAndIssueNoAndContent(String userId, String issueNo, String bettingContent, Paging paging) throws Exception {
        RowBounds rowBounds = new RowBounds (paging.getPageNumber (), paging.getPageSize ());
        if (StringUtils.isEmpty (issueNo) || StringUtils.isEmpty (userId) || StringUtils.isEmpty (bettingContent)) {
            return Collections.emptyList ();
        }
        List<AppTimeBettingPo> list = appTimeBettingMapper.findListByUserIdAndIssueNoAndContent (userId,issueNo,bettingContent, rowBounds);
        if (list == null) {
            list = Collections.emptyList ();
        }
        return list;
    }

    @Override
    @Transactional
    public Boolean undoTimeBettingService(String userId, String bettingId) throws Exception {
        AppTimeBettingPo bettingPo = this.findById (bettingId);
        if (!bettingPo.getUserId ().equals (userId)){
            throw new CommException ("只能撤销自己的下注单");
        }
        BigDecimal totalPrice = new BigDecimal (bettingPo.getMultiple ());
        AppUserPo userPo = appUserService.findUserById (userId);
        BigDecimal before = userPo.getBalance ();
        BigDecimal after = userPo.getBalance ().add (totalPrice);

        //盈亏衡量
        BigDecimal afterKick = userPo.getBalance ().subtract (totalPrice).setScale (2, BigDecimal.ROUND_HALF_EVEN);

        String businessNumber = bettingPo.getBusinessNumber ();
        appUserService.updateBalanceById (userId, totalPrice);
        appUserService.updateBettingAmoutById (userId, totalPrice.multiply (new BigDecimal ("-1")));
        appBillRecordService.saveBillRecord (businessNumber, userId, BusnessTypeEnum.TIME_UNDO.getCode (), totalPrice, before, after, "用户" + userPo.getMobile () + "时时彩下注后撤单", "");
        appUserService.updateKickBackAmountById (userId, totalPrice.multiply (new BigDecimal ("-1")));
        appBillRecordService.saveBillRecord (bettingPo.getBusinessNumber (), userPo.getId (), BusnessTypeEnum.REDUCE_KICKBACKAMOUNT_RECORD.getCode (), totalPrice.multiply (new BigDecimal ("-1")), userPo.getKickBackAmount (), afterKick, userPo.getMobile () + "【" + userPo.getNickName () + "】" + "下注后撤单返水减少", "");
        appUserService.updateCurrentProfitById (userId, totalPrice);
        return true;
    }
}
