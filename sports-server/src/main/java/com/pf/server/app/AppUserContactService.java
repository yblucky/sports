package com.pf.server.app;

import com.pf.common.po.AppUserContactPo;
import com.pf.common.po.AppUserPo;

/**
 * 用户接点人业务
 * Created by Administrator on 2018/1/4 0004.
 */
public interface AppUserContactService {

    public AppUserContactPo findUserByUserId(String userId);

}
