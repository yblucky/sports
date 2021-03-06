package com.xlf.server.app.impl;

import com.xlf.common.enums.BetTypeEnum;
import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.enums.RacingSeatEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.po.AppRacingBettingPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.BettingBaseVo;
import com.xlf.common.vo.app.RacingBettingBaseVo;
import com.xlf.common.vo.app.RacingBettingVo;
import com.xlf.common.vo.pc.LotteryVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.app.AppRacingBettingService;
import com.xlf.server.app.AppUserService;
import com.xlf.server.mapper.AppRacingBettingMapper;
import com.xlf.server.utils.LotteryUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.xlf.server.utils.LotteryUtils.makeRaceingCountMapWithAlllist;

/**
 * 时时彩投注业务类
 */
@Service
public class AppRacingBettingServiceImpl implements AppRacingBettingService {
    @Resource
    private AppRacingBettingMapper appRacingBettingMapper;

    @Resource
    private AppUserService appUserService;
    @Resource
    private AppBillRecordService appBillRecordService;

    @Override
    public void save(String businessNumber, String issueNo, String userId, Integer lotteryOne, Integer lotteryTwo, Integer lotteryThree, Integer lotteryFour, Integer lotteryFive, Integer lotterySix, Integer lotterySeven, Integer lotteryEight, Integer lotteryNine, Integer lotteryTen, Integer multiple, String bettingContent, Integer serialNumber) throws Exception {
        AppRacingBettingPo model = new AppRacingBettingPo();
        model.setId(ToolUtils.getUUID());
        model.setIssueNo(issueNo);
        model.setBusinessNumber(businessNumber);
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
        model.setBettingContent(bettingContent);
        model.setSerialNumber(serialNumber);
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
        appBillRecordService.saveBillRecord(businessNumber, userId, BusnessTypeEnum.RACING_BETTING.getCode(), totalPrice.multiply(new BigDecimal("-1")), before, after, "用户" + userPo.getMobile() + "北京赛车下注", vo.getIssueNo());
        for (RacingBettingBaseVo base : vo.getRaingList()) {
            this.save(businessNumber, vo.getIssueNo(), userId, base.getLotteryOne(), base.getLotteryTwo(), base.getLotteryThree(), base.getLotteryFour(), base.getLotteryFive(), base.getLotterySix(), base.getLotterySeven(), base.getLotteryEight(), base.getLotteryNine(), base.getLotteryTen(), base.getMultiple(), base.getBettingContent(), vo.getSerialNumber());
        }
        appUserService.updateBettingAmoutById(userId, totalPrice);
        //盈亏衡量
        BigDecimal afterKick = userPo.getKickBackAmount().add(totalPrice).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        appUserService.updateKickBackAmountById(userId, totalPrice);
        appBillRecordService.saveBillRecord(businessNumber, userPo.getId(), BusnessTypeEnum.ADD_KICKBACKAMOUNT_RECORD.getCode(), totalPrice, userPo.getKickBackAmount(), afterKick, userPo.getMobile() + "【" + userPo.getNickName() + "】" + "PK10下注后返水增加", vo.getIssueNo());
        appUserService.updateTodayBettingAmoutTodayWiningAmout(userId, totalPrice, BigDecimal.ZERO);
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
        return appRacingBettingMapper.updateLotteryFlagById(id, winingAmout);
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

    @Override
    public List<LotteryVo> findAll(LotteryVo vo, Paging paging) {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        return appRacingBettingMapper.findAll(vo, rowBounds);
    }

    @Override
    public Integer recordListTotal(String id, String businessNumber) {
        Integer count = 0;
        count = appRacingBettingMapper.recordListTotal(id, businessNumber);
        if (count == null) {
            count = 0;
        }
        return count;
    }

    @Override
    public List<AppRacingBettingPo> findRecordList(String userId, String businessNumber, Paging paging) {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), 9999);
        if (StringUtils.isEmpty(businessNumber) || StringUtils.isEmpty(userId)) {
            return Collections.emptyList();
        }
        List<AppRacingBettingPo> list = appRacingBettingMapper.findRecordList(userId, businessNumber, rowBounds);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    @Override
    public Integer countBettingByUserIdAndIssueNoAndContent(String userId, String issueNo, String bettingContent, Integer betTpye) throws Exception {
        Integer count = 0;
        count = appRacingBettingMapper.countBettingByUserIdAndIssueNoAndContent(userId, issueNo, bettingContent, betTpye);
        if (count == null) {
            count = 0;
        }
        return count;
    }

    @Override
    public List<AppRacingBettingPo> findListByUserIdAndIssueNoAndContent(String userId, String issueNo, String bettingContent, Integer betTpye, Paging paging) throws Exception {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        if (StringUtils.isEmpty(issueNo) || StringUtils.isEmpty(userId)) {
            return Collections.emptyList();
        }
        List<AppRacingBettingPo> list = appRacingBettingMapper.findListByUserIdAndIssueNoAndContent(userId, issueNo, bettingContent, betTpye, rowBounds);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    @Override
    public int findAllCount(LotteryVo vo) {
        return appRacingBettingMapper.findAllCount(vo);
    }

    @Override
    public AppRacingBettingPo findById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        return appRacingBettingMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public Boolean undoRacingBettingService(String userId, String[] bettingIds) throws Exception {
        if (bettingIds == null && bettingIds.length <= 0) {
            throw new CommException("撤销参数有误");
        }

        AppRacingBettingPo bettingPo = null;
        //定义一个变量保存金额
        BigDecimal totalPrice = new BigDecimal(0);
        for (String bettingId : bettingIds) {
            bettingPo = this.findById(bettingIds[0]);
            if (!bettingPo.getUserId().equals(userId)) {
                throw new CommException("只能撤销自己的下注单");
            }
            if (!LotteryFlagEnum.NO.getCode().equals(bettingPo.getLotteryFlag())) {
                throw new CommException("不可撤销");
            }

            //计算金额

            totalPrice.add(new BigDecimal(bettingPo.getMultiple()));
        }

        AppUserPo userPo = appUserService.findUserById(userId);
        BigDecimal before = userPo.getBalance();
        BigDecimal after = userPo.getBalance().add(totalPrice);

        //盈亏衡量
        BigDecimal afterKick = userPo.getBalance().subtract(totalPrice).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        String businessNumber = bettingPo.getBusinessNumber();
        appUserService.updateBalanceById(userId, totalPrice);
        appUserService.updateBettingAmoutById(userId, totalPrice.multiply(new BigDecimal("-1")));
        appRacingBettingMapper.updateLotteryFlagAndWingAmoutByIds(bettingIds, LotteryFlagEnum.UNDO.getCode(), BigDecimal.ZERO);
        appBillRecordService.saveBillRecord(businessNumber, userId, BusnessTypeEnum.RACING_UNDO.getCode(), totalPrice, before, after, "用户" + userPo.getMobile() + "北京赛车下注后撤单", "");
        appUserService.updateKickBackAmountById(userId, totalPrice.multiply(new BigDecimal("-1")));
        appBillRecordService.saveBillRecord(bettingPo.getBusinessNumber(), userPo.getId(), BusnessTypeEnum.REDUCE_KICKBACKAMOUNT_RECORD.getCode(), totalPrice.multiply(new BigDecimal("-1")), userPo.getKickBackAmount(), afterKick, userPo.getMobile() + "【" + userPo.getNickName() + "】" + "下注后撤单返水减少", "");
        appUserService.updateCurrentProfitById(userId, totalPrice);
        return true;
    }


    @Override
    public List<AppRacingBettingPo> listWininggByIssuNoAndWingConent(String issuNo, Integer lotteryFlag, Integer betType, Paging paging, List<String> winingList) {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        if (StringUtils.isEmpty(issuNo)) {
            return Collections.emptyList();
        }
        List<AppRacingBettingPo> list = appRacingBettingMapper.listWininggByIssuNoAndWingConent(issuNo, lotteryFlag, betType, winingList, rowBounds);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    @Override
    public Integer wininggCountAndWingConent(String issuNo, Integer lotteryFlag, Integer betType, List<String> winingList) {
        Integer count = 0;
        count = appRacingBettingMapper.wininggCountAndWingConent(issuNo, lotteryFlag, betType, winingList);
        if (count == null) {
            count = 0;
        }
        return count;
    }


    @Override
    public BigDecimal sumUnLotteryByUserId(String userId) {
        BigDecimal sum = appRacingBettingMapper.sumUnLotteryByUserId(userId);
        if (sum == null) {
            sum = BigDecimal.ZERO;
        }
        return sum;
    }

    /**
     * 计算北京赛车一字定已投注未开奖的最大中奖额度
     *
     * @param userPo
     * @param issueNo
     * @param paging
     * @param agentSettingPo
     * @return
     * @throws Exception
     */
    @Override
    public BigDecimal oneRacingMaxWard(AppUserPo userPo, String issueNo, Paging paging, SysAgentSettingPo agentSettingPo) throws Exception {
        paging.setPageSize(10000);
        List<BettingBaseVo> allList = new ArrayList<>();
        Integer hasBettingCount = this.countBettingByUserIdAndIssueNoAndContent(userPo.getId(), issueNo, null, BetTypeEnum.RACE_ONE.getCode());
        if (hasBettingCount > 0) {
            List<AppRacingBettingPo> appRacingBettingPos = this.findListByUserIdAndIssueNoAndContent(userPo.getId(), issueNo, null, BetTypeEnum.RACE_ONE.getCode(), paging);
            this.makeAllList(allList, appRacingBettingPos);
        }
        Map<String, Map<String, Integer>> countMap = makeRaceingCountMapWithAlllist(allList);
        Integer sumMaxMutiple = LotteryUtils.oneRacingSumMaxMutiple(countMap);
        return new BigDecimal(sumMaxMutiple).multiply(agentSettingPo.getRacingOdds());
    }

    @Override
    public void makeAllList(List<BettingBaseVo> allList, List<AppRacingBettingPo> racingBettingPos) {
        for (AppRacingBettingPo po : racingBettingPos) {
            BettingBaseVo bettingBaseVo = new BettingBaseVo();
            bettingBaseVo.setMultiple(po.getMultiple());
            bettingBaseVo.setBettingContent(po.getBettingContent());
            allList.add(bettingBaseVo);
        }
    }
}
