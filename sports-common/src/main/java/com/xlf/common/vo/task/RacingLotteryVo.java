package com.xlf.common.vo.task;


import java.util.List;

public class RacingLotteryVo {
    private  String gameCode;
    private  String preIssue;
    private List<Integer> openNum;
    private  List<Integer> dragonTigerArr;
    private  List<Integer> sumArr;
    private  List<Integer> formArr;
    private  List<Integer> mimcryArr;
    private  List<Integer> zodiacArr;
    private  List<Integer> compareArr;
    private  String issue;
    private  Long currentOpenDateTime;
    private  Long openDateTime;
    private  Long serverTime;
    private  Integer openedCount;
    private  Integer dailyTotal;
    private  String sumType;
    private  String wuxing;

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getPreIssue() {
        return preIssue;
    }

    public void setPreIssue(String preIssue) {
        this.preIssue = preIssue;
    }

    public List<Integer> getOpenNum() {
        return openNum;
    }

    public void setOpenNum(List<Integer> openNum) {
        this.openNum = openNum;
    }

    public List<Integer> getDragonTigerArr() {
        return dragonTigerArr;
    }

    public void setDragonTigerArr(List<Integer> dragonTigerArr) {
        this.dragonTigerArr = dragonTigerArr;
    }

    public List<Integer> getSumArr() {
        return sumArr;
    }

    public void setSumArr(List<Integer> sumArr) {
        this.sumArr = sumArr;
    }

    public List<Integer> getFormArr() {
        return formArr;
    }

    public void setFormArr(List<Integer> formArr) {
        this.formArr = formArr;
    }

    public List<Integer> getMimcryArr() {
        return mimcryArr;
    }

    public void setMimcryArr(List<Integer> mimcryArr) {
        this.mimcryArr = mimcryArr;
    }

    public List<Integer> getZodiacArr() {
        return zodiacArr;
    }

    public void setZodiacArr(List<Integer> zodiacArr) {
        this.zodiacArr = zodiacArr;
    }

    public List<Integer> getCompareArr() {
        return compareArr;
    }

    public void setCompareArr(List<Integer> compareArr) {
        this.compareArr = compareArr;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Long getCurrentOpenDateTime() {
        return currentOpenDateTime;
    }

    public void setCurrentOpenDateTime(Long currentOpenDateTime) {
        this.currentOpenDateTime = currentOpenDateTime;
    }

    public Long getOpenDateTime() {
        return openDateTime;
    }

    public void setOpenDateTime(Long openDateTime) {
        this.openDateTime = openDateTime;
    }

    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.serverTime = serverTime;
    }

    public Integer getOpenedCount() {
        return openedCount;
    }

    public void setOpenedCount(Integer openedCount) {
        this.openedCount = openedCount;
    }

    public Integer getDailyTotal() {
        return dailyTotal;
    }

    public void setDailyTotal(Integer dailyTotal) {
        this.dailyTotal = dailyTotal;
    }

    public String getSumType() {
        return sumType;
    }

    public void setSumType(String sumType) {
        this.sumType = sumType;
    }

    public String getWuxing() {
        return wuxing;
    }

    public void setWuxing(String wuxing) {
        this.wuxing = wuxing;
    }
}
