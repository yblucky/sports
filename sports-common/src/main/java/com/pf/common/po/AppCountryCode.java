package com.pf.common.po;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 国家编码po类
 * Created by Administrator on 2017/8/17.
 */
@Table(name="app_country_code")
public class AppCountryCode {

    /**
     * 主键编号
     */
    @Id
    private String id;

    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * '英文名称'
     */
    private String egName;
    /**
     * '英文区域名称'
     */
    private String egAreaName;
    /**
     * '中文区域名称'
     */
    private String cnAreaName;
    /**
     * '中文名称'
     */
    private String cnName;
    /**
     * '简码'
     */
    private String simpleCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getEgName() {
        return egName;
    }

    public void setEgName(String egName) {
        this.egName = egName;
    }

    public String getEgAreaName() {
        return egAreaName;
    }

    public void setEgAreaName(String egAreaName) {
        this.egAreaName = egAreaName;
    }

    public String getCnAreaName() {
        return cnAreaName;
    }

    public void setCnAreaName(String cnAreaName) {
        this.cnAreaName = cnAreaName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getSimpleCode() {
        return simpleCode;
    }

    public void setSimpleCode(String simpleCode) {
        this.simpleCode = simpleCode;
    }
}
