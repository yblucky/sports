package com.xlf.common.vo.app;


import com.xlf.common.enums.PerformanceTypeEnum;
import com.xlf.common.po.AppBillRecordPo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 流水记录vo类
 * Created by Administrator on 2017/8/17.
 */
public class PerformanceRecordVo extends AppBillRecordPo {
    private String uid;
    private String mobile;
    private String nickName;
    private String imgPath;
    private BigDecimal amount;
    private Date ceateTime;
    private Integer type;
    private String  typeDesc;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCeateTime() {
        return ceateTime;
    }

    public void setCeateTime(Date ceateTime) {
        this.ceateTime = ceateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = PerformanceTypeEnum.getName(this.type.toString()) ;
    }
}
