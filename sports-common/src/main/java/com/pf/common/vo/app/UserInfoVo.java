package com.pf.common.vo.app;

import java.math.BigDecimal;

/**
 * 用户vo类
 * Created by Administrator on 2018/1/4 0004.
 */
public class UserInfoVo {
    /**
     * 主键编号
     */
    private String id;

    /**
     * '用户编号'
     */
    private Integer uid;
    /**
     * '手机号
     */
    private String mobile;
    /**
     * '昵称'
     */
    private String nickName;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 10 -未激活
     * 20 - 正常
     * 30 - 已禁用
     **/
    private Integer state;
    /**
     * 用户头像
     */
    private String imgPath;

    /**
     * 主键编号
     */
    private String contactId;
    /**
     * 'A区业绩'
     */
    private BigDecimal performanceA;
    /**
     * 'B区业绩'
     */
    private BigDecimal performanceB;
    /**
     * '上级编号'
     */
    private String parentId;
    /**
     * '层级'
     */
    private Integer level;
    /**
     * 所在分区
     */
    private String currentArea;

    /**
     * 是否允许释放候鸟积分
     */
    private Integer isAllowed;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(String currentArea) {
        this.currentArea = currentArea;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public BigDecimal getPerformanceA() {
        return performanceA;
    }

    public void setPerformanceA(BigDecimal performanceA) {
        this.performanceA = performanceA;
    }

    public BigDecimal getPerformanceB() {
        return performanceB;
    }

    public void setPerformanceB(BigDecimal performanceB) {
        this.performanceB = performanceB;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getIsAllowed() {
        return isAllowed;
    }

    public void setIsAllowed(Integer isAllowed) {
        this.isAllowed = isAllowed;
    }
}
