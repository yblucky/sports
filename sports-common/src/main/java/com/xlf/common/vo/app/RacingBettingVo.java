package com.xlf.common.vo.app;

import java.util.ArrayList;
import java.util.List;

public class RacingBettingVo {
    private String issueNo;
    private String payPwd;
    private Integer serialNumber;
    private List<RacingBettingBaseVo> raingList = new ArrayList<>();


    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<RacingBettingBaseVo> getRaingList() {
        return raingList;
    }

    public void setRaingList(List<RacingBettingBaseVo> raingList) {
        this.raingList = raingList;
    }
}
