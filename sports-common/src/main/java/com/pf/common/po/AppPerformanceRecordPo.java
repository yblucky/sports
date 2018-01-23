package com.pf.common.po;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业绩参数Po类
 * Created by Administrator on 2017/8/17.
 */
@Table(name = "app_performance_record")
public class AppPerformanceRecordPo implements Serializable {

    /**
     * 主键编号
     */
    @Id
    private String id;
    /**
     * '来源'
     */
    private String orderId;
    /**
     * '用户id'
     */
    private String userId;
    /**
     * '增加业绩数量'
     */
    private BigDecimal amount;
    /**
     * '创建时间'
     */
    private Date createTime;
    /**
     * '业绩增加的部门'
     */
    private String department;

    /**
     * '类型：1:激活    2:兑换'
     */
    private Integer type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
