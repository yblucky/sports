package com.xlf.common.vo.app;

import java.util.Date;

/**
 * Created by Administrator on 2018/3/6 0006.
 */
public class AppTimeLotteryVo {

    private String id;
    private String issueNo;
    private Integer lotteryOne;
    private Integer lotteryTwo;
    private Integer lotteryThree;
    private Integer lotteryFour;
    private Integer lotteryFive;
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
