package com.xlf.common.po;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *北京赛车投注表Po类
 * Created by Administrator on 2017/8/17.
 */
@Table(name = "app_racing_lottery")
public class AppRacingLotteryPo implements Serializable {
    @Id
    private String id;
    private String issueNo;
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
    private Date createTime;
    private Date lotteryTime;
    private Integer flag;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLotteryTime() {
        return lotteryTime;
    }

    public void setLotteryTime(Date lotteryTime) {
        this.lotteryTime = lotteryTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
