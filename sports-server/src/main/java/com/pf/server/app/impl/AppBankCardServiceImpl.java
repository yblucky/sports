package com.pf.server.app.impl;

import com.pf.common.vo.app.BankCardVo;
import com.pf.server.app.AppBankCardService;
import com.pf.server.app.AppUserService;
import com.pf.server.mapper.AppBankCardMapper;
import com.pf.server.mapper.AppUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 银行卡相关业务
 * Created by Administrator on 2018/1/4 0004.
 */
@Service
public class AppBankCardServiceImpl implements AppBankCardService {

    @Resource
    private AppBankCardMapper appBankCardMapper;
    @Override
    public BankCardVo findKey(String id) {
        return appBankCardMapper.findKey(id);
    }
}
