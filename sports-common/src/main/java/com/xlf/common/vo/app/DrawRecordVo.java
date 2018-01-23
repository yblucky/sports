package com.xlf.common.vo.app;


import com.xlf.common.po.AppBillRecordPo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现记录vo类
 * Created by Administrator on 2017/8/17.
 */
public class DrawRecordVo extends AppBillRecordPo {
    private String uid;
    private String mobile;
    private String nickName;
    private String imgPath;
    private BigDecimal amount;
    private Date ceateTime;
    private Integer state;


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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
