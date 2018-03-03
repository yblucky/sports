package com.xlf.common.po;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * app端用户VO类
 *
 * @author qsy
 * @version v1.0
 * @date 2017年8月15日
 */
@Table(name = "sys_agent_setting")
public class SysAgentSettingPo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 558884552227040L;

	/**
     * 主键编号
     */
    @Id
    private String id;

    /**
     * '代理别称'
     * */
    private String agentName;

    /**
     * '押金'
     * */
    private Integer deposit;

    /**
     * '会员每天最大盈利额度'
     * */
    private BigDecimal maxProfitPerDay;

    /**
     * '会员每期每个数字最少下注组数'
     * */
    private Integer minBetNoPerDigital;

    /**
     * '会员每期每个数字最大下注组数'
     * */
    private Integer maxBetNoPerDigital;

    /**
     * '代理返水比例'
     * */
    private BigDecimal returnWaterScale;

    /**
     * '会员每天最大提现额度'
     * */
    private Integer maxWithdrawPerDay;

    /**
     * ''会员每期最多下注位数''
     * */
    private Integer maxBetSeats;

    /**
     * '会员每期每个位最多下注多少号码'
     * */
    private Integer maxBetDigitalNoPerSeat;

    /**
     * '创建时间'
     * */
    private Date createTime;

    /**
     * '修改时间'
     * */
    private Date updateTime;

    /**
     * '状态'
     * */
    private Integer  state;

    /**
     * '赔率'
     * */
    private BigDecimal  odds;

    /**
     * '时时彩二星玩法赔率'
     * */
    private BigDecimal  timeDoubleOdds;

    /**
     * '时时彩二星玩法最多组合位数'
     * */
    private Integer  timeDoubleMaxBetSeats;

    /**
     * '时时彩二星单种位数组合100种情形中最大选30种'
     * */
    private Integer  timeDoubleMaxBetKindPerTwoSeats;

    /**
     * '时时彩二星单注最大下注注数'
     * */
    private Integer  timeDoubleMaxBetNoPerKind;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getMaxProfitPerDay() {
        return maxProfitPerDay;
    }

    public void setMaxProfitPerDay(BigDecimal maxProfitPerDay) {
        this.maxProfitPerDay = maxProfitPerDay;
    }

    public Integer getMinBetNoPerDigital() {
        return minBetNoPerDigital;
    }

    public void setMinBetNoPerDigital(Integer minBetNoPerDigital) {
        this.minBetNoPerDigital = minBetNoPerDigital;
    }

    public Integer getMaxBetNoPerDigital() {
        return maxBetNoPerDigital;
    }

    public void setMaxBetNoPerDigital(Integer maxBetNoPerDigital) {
        this.maxBetNoPerDigital = maxBetNoPerDigital;
    }

    public BigDecimal getReturnWaterScale() {
        return returnWaterScale;
    }

    public void setReturnWaterScale(BigDecimal returnWaterScale) {
        this.returnWaterScale = returnWaterScale;
    }

    public Integer getMaxWithdrawPerDay() {
        return maxWithdrawPerDay;
    }

    public void setMaxWithdrawPerDay(Integer maxWithdrawPerDay) {
        this.maxWithdrawPerDay = maxWithdrawPerDay;
    }

    public Integer getMaxBetSeats() {
        return maxBetSeats;
    }

    public void setMaxBetSeats(Integer maxBetSeats) {
        this.maxBetSeats = maxBetSeats;
    }

    public Integer getMaxBetDigitalNoPerSeat() {
        return maxBetDigitalNoPerSeat;
    }

    public void setMaxBetDigitalNoPerSeat(Integer maxBetDigitalNoPerSeat) {
        this.maxBetDigitalNoPerSeat = maxBetDigitalNoPerSeat;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public BigDecimal getTimeDoubleOdds() {
        return timeDoubleOdds;
    }

    public void setTimeDoubleOdds(BigDecimal timeDoubleOdds) {
        this.timeDoubleOdds = timeDoubleOdds;
    }


    public Integer getTimeDoubleMaxBetSeats() {
        return timeDoubleMaxBetSeats;
    }

    public void setTimeDoubleMaxBetSeats(Integer timeDoubleMaxBetSeats) {
        this.timeDoubleMaxBetSeats = timeDoubleMaxBetSeats;
    }

    public Integer getTimeDoubleMaxBetKindPerTwoSeats() {
        return timeDoubleMaxBetKindPerTwoSeats;
    }

    public void setTimeDoubleMaxBetKindPerTwoSeats(Integer timeDoubleMaxBetKindPerTwoSeats) {
        this.timeDoubleMaxBetKindPerTwoSeats = timeDoubleMaxBetKindPerTwoSeats;
    }

    public Integer getTimeDoubleMaxBetNoPerKind() {
        return timeDoubleMaxBetNoPerKind;
    }

    public void setTimeDoubleMaxBetNoPerKind(Integer timeDoubleMaxBetNoPerKind) {
        this.timeDoubleMaxBetNoPerKind = timeDoubleMaxBetNoPerKind;
    }
}
