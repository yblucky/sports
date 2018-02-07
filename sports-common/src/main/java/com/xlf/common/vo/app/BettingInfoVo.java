package com.xlf.common.vo.app;


import java.util.Date;

public class BettingInfoVo {
    private String hhmm;
    private String currentIssueNo;
    private String historyIssuNo;
    private String nextIssuNo;
    private Date endDate;
    private String endDateStr;
    private Long end;
    private Long start;
    private Long open;
    private String bettingEnd;
    private String bettingStart;
    private String bettingOpen;

    public String getHhmm() {
        return hhmm;
    }

    public void setHhmm(String hhmm) {
        this.hhmm = hhmm;
    }

    public String getCurrentIssueNo() {
        return currentIssueNo;
    }

    public void setCurrentIssueNo(String currentIssueNo) {
        this.currentIssueNo = currentIssueNo;
    }

    public String getHistoryIssuNo() {
        return historyIssuNo;
    }

    public void setHistoryIssuNo(String historyIssuNo) {
        this.historyIssuNo = historyIssuNo;
    }

    public String getNextIssuNo() {
        return nextIssuNo;
    }

    public void setNextIssuNo(String nextIssuNo) {
        this.nextIssuNo = nextIssuNo;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getOpen() {
        return open;
    }

    public void setOpen(Long open) {
        this.open = open;
    }

    public String getBettingEnd() {
        return bettingEnd;
    }

    public void setBettingEnd(String bettingEnd) {
        this.bettingEnd = bettingEnd;
    }

    public String getBettingStart() {
        return bettingStart;
    }

    public void setBettingStart(String bettingStart) {
        this.bettingStart = bettingStart;
    }

    public String getBettingOpen() {
        return bettingOpen;
    }

    public void setBettingOpen(String bettingOpen) {
        this.bettingOpen = bettingOpen;
    }
}
