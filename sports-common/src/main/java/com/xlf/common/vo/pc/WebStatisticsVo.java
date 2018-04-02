package com.xlf.common.vo.pc;

import java.math.BigDecimal;

/**
 * 营收列表Vo类
 * Created by Administrator on 2017/8/17.
 */
public class WebStatisticsVo {

    /**
     * 业务类型
     * */
    private Integer busnessType;
    /**
     * 总额
     * */
    private BigDecimal sumAmount;
    /**
     * 用户余额总额
     * */
    private BigDecimal balance;
    /**
     * 用户投注金额总额
     * */
    private BigDecimal bettingAmout;
    /**
     * 用户累计中奖金额
     * */
    private BigDecimal winingAmout;
    /**
     * 当天盈亏累计盈亏
     * */
    private BigDecimal currentProfit;
    /**
     * 用户总数
     * */
    private Integer userCount;
    /**
     * 用户总盈亏
     * */
    private BigDecimal totalProfit;

    public Integer getBusnessType() {
        return busnessType;
    }

    public void setBusnessType(Integer busnessType) {
        this.busnessType = busnessType;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBettingAmout() {
        return bettingAmout;
    }

    public void setBettingAmout(BigDecimal bettingAmout) {
        this.bettingAmout = bettingAmout;
    }

    public BigDecimal getWiningAmout() {
        return winingAmout;
    }

    public void setWiningAmout(BigDecimal winingAmout) {
        this.winingAmout = winingAmout;
    }

    public BigDecimal getCurrentProfit() {
        return currentProfit;
    }

    public void setCurrentProfit(BigDecimal currentProfit) {
        this.currentProfit = currentProfit;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }
}
