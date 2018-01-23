package com.xlf.server.app;

import com.xlf.common.po.AppUserContactPo;

/**
 * 用户接点人业务
 * Created by Administrator on 2018/1/4 0004.
 */
public interface AppUserContactService {

    public AppUserContactPo findUserByUserId(String userId);

}
