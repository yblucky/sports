package com.xlf.common.vo.pc;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 营收列表Vo类
 * Created by Administrator on 2017/8/17.
 */
public class RevenueVo implements Serializable{

    /**
     * 代理编号
     * */
    private String id;
    /**
     * 代理等级
     * */
    private String agentName;
    /**
     * 代理昵称
     * */
    private String userName;
    /**
     *手机号
     * */
    private String mobile;
    /**
     * 投注人数
     * */
    private int betCount;
    /**
     *投注积分
     * */
    private int betScore;
    /**
     * 会员结果
     * */
    private int memberRs;
    /**
     * 备注
     * */
    private int remark;
    /**
     * 实际盈亏
     * */
    private int agentProfit;
    /**
     * 代理编号
     * */
    private String userId;
    /**
     * 时间类型 10 今日 20 昨天 30 上周 40 本周 50 上月 60 本月
     * */
    private String dateType;
    /**
     * 时间类型 10 重慶時時彩 20 北京賽車
     * */
    private String gameType;
    /**
     * 时间相差天数
     * */
    private int startDiff;
    /**
     * 时间相差天数
     * */
    private int endDiff;
    /**
     * '开始时间'
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    /**
     * '结束时间'
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getBetCount() {
        return betCount;
    }

    public void setBetCount(int betCount) {
        this.betCount = betCount;
    }

    public int getBetScore() {
        return betScore;
    }

    public void setBetScore(int betScore) {
        this.betScore = betScore;
    }

    public int getMemberRs() {
        return memberRs;
    }

    public void setMemberRs(int memberRs) {
        this.memberRs = memberRs;
    }

    public int getRemark() {
        return remark;
    }

    public void setRemark(int remark) {
        this.remark = remark;
    }

    public int getAgentProfit() {
        return agentProfit;
    }

    public void setAgentProfit(int agentProfit) {
        this.agentProfit = agentProfit;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public int getStartDiff() {
        return startDiff;
    }

    public void setStartDiff(int startDiff) {
        this.startDiff = startDiff;
    }

    public int getEndDiff() {
        return endDiff;
    }

    public void setEndDiff(int endDiff) {
        this.endDiff = endDiff;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
