package com.xlf.common.vo.app;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class TimeBettingVo {
    private Integer betType;
    private String issueNo;
    private Integer serialNumber;
    private String payPwd;
    private List<TimeBettingBaseVo> timeList=new ArrayList<>();

    public Integer getBetType() {
        return betType;
    }

    public void setBetType(Integer betType) {
        this.betType = betType;
    }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public List<TimeBettingBaseVo> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<TimeBettingBaseVo> timeList) {
        this.timeList = timeList;
    }
}
