package com.xlf.common.vo.app;

import java.io.Serializable;

/**
 * 用户
 * Created by Administrator on 2017/8/18.
 */
public class UpdateUserVo implements Serializable {


    private static final long serialVersionUID = 1434L;
    /**
     * 旧支付密码
     */
    private String oldPwd;
    /**
     * 新支付密码
     */
    private String newPwd;
    /**
     * 旧登录密码
     */
    private String oldLoginPwd;
    /**
     * 新登录密码
     */
    private String newLoginPwd;
    /**
     * 新昵称
     */
    private String nickName;
    /**
     * 新头像
     */
    private String imgPath;

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getOldLoginPwd() {
        return oldLoginPwd;
    }

    public void setOldLoginPwd(String oldLoginPwd) {
        this.oldLoginPwd = oldLoginPwd;
    }

    public String getNewLoginPwd() {
        return newLoginPwd;
    }

    public void setNewLoginPwd(String newLoginPwd) {
        this.newLoginPwd = newLoginPwd;
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
}
