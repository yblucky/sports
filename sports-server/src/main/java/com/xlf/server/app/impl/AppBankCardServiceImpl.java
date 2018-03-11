package com.xlf.server.app.impl;

import com.xlf.common.vo.app.BankCardVo;
import com.xlf.server.app.AppBankCardService;
import com.xlf.server.mapper.AppBankCardMapper;
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


    @Override
    public void update(BankCardVo bankCardVo) throws Exception {
        // TODO Auto-generated method stub
        appBankCardMapper.update(bankCardVo);
    }
}
