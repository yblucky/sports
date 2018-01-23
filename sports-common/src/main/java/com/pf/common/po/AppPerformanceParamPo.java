package com.pf.common.po;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 业绩参数Po类
 * Created by Administrator on 2017/8/17.
 */
@Table(name = "app_performance_param")
public class AppPerformanceParamPo  implements Serializable{

    /**
     * 主键编号
     */
    @Id
    private String id;
    /**
     * '业绩小区间'
     */
    private BigDecimal minRange;
    /**
     * '业绩大区间'
     */
    private BigDecimal maxRange;
    /**
     * '兑换/释放倍数'
     */
    private BigDecimal rate;
    /**
     * '兑换（10）/释放 （20）'
     */
    private Integer type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getMinRange() {
        return minRange;
    }

    public void setMinRange(BigDecimal minRange) {
        this.minRange = minRange;
    }

    public BigDecimal getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(BigDecimal maxRange) {
        this.maxRange = maxRange;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
