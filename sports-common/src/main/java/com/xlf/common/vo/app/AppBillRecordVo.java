package com.xlf.common.vo.app;


import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.po.AppBillRecordPo;

/**
 * 流水记录vo类
 * Created by Administrator on 2017/8/17.
 */
public class AppBillRecordVo extends AppBillRecordPo {
    private Integer uid;
    private String mobile;
    private String imgPath;
    private String nickName;
    private String busnessTypeName;
    private String busnessTypeEgName;
    private String extend;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getBusnessTypeName() {
        return busnessTypeName;
    }

    public void setBusnessTypeName(String busnessTypeName) {
        this.busnessTypeName = busnessTypeName;
    }

    public String getBusnessTypeEgName() {
        return busnessTypeEgName;
    }

    public void setBusnessTypeEgName(String busnessTypeEgName) {
        this.busnessTypeEgName = busnessTypeEgName;
    }

    @Override
    public void setBusnessType(Integer busnessType) {
        super.setBusnessType(busnessType);
        this.setBusnessTypeName(BusnessTypeEnum.getName(busnessType));
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

    @Override
    public String getExtend() {
        return extend;
    }

    @Override
    public void setExtend(String extend) {
        this.extend = extend;
    }
}
