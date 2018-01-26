package com.xlf.common.vo.app;

import java.util.ArrayList;
import java.util.List;

public class RacingBettingVo {
    private String issueNo;
    private String payPwd;
    private List timeList=new ArrayList<TimeBettingBaseVo>();

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

    public List getTimeList() {
        return timeList;
    }

    public void setTimeList(List timeList) {
        this.timeList = timeList;
    }
}
