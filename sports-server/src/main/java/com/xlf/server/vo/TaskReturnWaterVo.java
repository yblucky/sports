package com.xlf.server.vo;


import java.math.BigDecimal;

public class TaskReturnWaterVo {
    private String id;
    private Integer uid;
    private String parentId;
    /**
     * '历史累计返水衡量值'
     */
    private BigDecimal kickBackAmount;
    /**
     * '历史累计返水衡量值'(会员合并后)
     */
    private BigDecimal sumKickBackAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public BigDecimal getKickBackAmount() {
        return kickBackAmount;
    }

    public void setKickBackAmount(BigDecimal kickBackAmount) {
        this.kickBackAmount = kickBackAmount;
    }

    public BigDecimal getSumKickBackAmount() {
        return sumKickBackAmount;
    }

    public void setSumKickBackAmount(BigDecimal sumKickBackAmount) {
        this.sumKickBackAmount = sumKickBackAmount;
    }
}
