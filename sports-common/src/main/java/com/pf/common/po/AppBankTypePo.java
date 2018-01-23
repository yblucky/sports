package com.pf.common.po;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 银行卡类型表
 * Created by Administrator on 2017/8/17.
 */
@Table(name = "app_bank_type")
public class AppBankTypePo {

    /**
     * 主键编号
     */
    @Id
    private String id;
    /**
     * '开户银行名称'
     */
    private String bankName;
    /**
     * 'logo图标'
     */
    private String logo;
    /**
     *银行卡背景颜色
     */
    private String color;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
