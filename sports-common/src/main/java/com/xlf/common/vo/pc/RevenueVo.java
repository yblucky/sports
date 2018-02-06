package com.xlf.common.vo.pc;

/**
 * 营收列表Vo类
 * Created by Administrator on 2017/8/17.
 */
public class RevenueVo {

    /**
     * 代理编号
     * */
    private Integer uid;
    /**
     * 代理昵称
     * */
    private String nickName;
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

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
}
