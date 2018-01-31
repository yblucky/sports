package com.xlf.common.po;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 北京赛车投注表po类
 * Created by Administrator on 2017/8/17.
 */
@Table(name="app_racing_betting")
public class AppRacingBettingPo {
    @Id
    private String id;
    private String issueNo;
    private String userId;
    private Integer lotteryOne;
    private Integer lotteryTwo;
    private Integer lotteryThree;
    private Integer lotteryFour;
    private Integer lotteryFive;
    private Integer lotterySix;
    private Integer lotterySeven;
    private Integer lotteryEight;
    private Integer lotteryNine;
    private Integer lotteryTen;
    private Integer lotteryFlag;
    private BigDecimal winningAmount;
    private Integer multiple;
    private Date createTime;
    private String businessNumber;
    /**
     * 投注类型
     * */
    private Integer betType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLotteryOne() {
        return lotteryOne;
    }

    public void setLotteryOne(Integer lotteryOne) {
        this.lotteryOne = lotteryOne;
    }

    public Integer getLotteryTwo() {
        return lotteryTwo;
    }

    public void setLotteryTwo(Integer lotteryTwo) {
        this.lotteryTwo = lotteryTwo;
    }

    public Integer getLotteryThree() {
        return lotteryThree;
    }

    public void setLotteryThree(Integer lotteryThree) {
        this.lotteryThree = lotteryThree;
    }

    public Integer getLotteryFour() {
        return lotteryFour;
    }

    public void setLotteryFour(Integer lotteryFour) {
        this.lotteryFour = lotteryFour;
    }

    public Integer getLotteryFive() {
        return lotteryFive;
    }

    public void setLotteryFive(Integer lotteryFive) {
        this.lotteryFive = lotteryFive;
    }

    public Integer getLotterySix() {
        return lotterySix;
    }

    public void setLotterySix(Integer lotterySix) {
        this.lotterySix = lotterySix;
    }

    public Integer getLotterySeven() {
        return lotterySeven;
    }

    public void setLotterySeven(Integer lotterySeven) {
        this.lotterySeven = lotterySeven;
    }

    public Integer getLotteryEight() {
        return lotteryEight;
    }

    public void setLotteryEight(Integer lotteryEight) {
        this.lotteryEight = lotteryEight;
    }

    public Integer getLotteryNine() {
        return lotteryNine;
    }

    public void setLotteryNine(Integer lotteryNine) {
        this.lotteryNine = lotteryNine;
    }

    public Integer getLotteryTen() {
        return lotteryTen;
    }

    public void setLotteryTen(Integer lotteryTen) {
        this.lotteryTen = lotteryTen;
    }

    public Integer getLotteryFlag() {
        return lotteryFlag;
    }

    public void setLotteryFlag(Integer lotteryFlag) {
        this.lotteryFlag = lotteryFlag;
    }

    public BigDecimal getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(BigDecimal winningAmount) {
        this.winningAmount = winningAmount;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public Integer getBetType() {
        return betType;
    }

    public void setBetType(Integer betType) {
        this.betType = betType;
    }
}
