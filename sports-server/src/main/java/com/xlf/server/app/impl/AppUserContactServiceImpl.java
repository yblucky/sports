package com.xlf.server.app.impl;

import com.xlf.common.po.AppUserContactPo;
import com.xlf.server.app.AppUserContactService;
import com.xlf.server.mapper.AppUserContactMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户接点人业务
 * Created by Administrator on 2018/1/4 0004.
 */
@Service
public class AppUserContactServiceImpl implements AppUserContactService {

    @Resource
    private AppUserContactMapper userContactMapper;

    @Override
    public AppUserContactPo findUserByUserId(String userId) {
        return userContactMapper.findUserByUserId(userId);
    }
}
