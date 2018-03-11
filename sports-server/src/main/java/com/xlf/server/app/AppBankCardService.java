package com.xlf.server.app;

import com.xlf.common.vo.app.BankCardVo;

/**
 * 银行卡相关业务
 * Created by Administrator on 2018/1/4 0004.
 */
public interface AppBankCardService {

    public BankCardVo findKey(String id);

    void update(BankCardVo bankCardVo) throws Exception;
}
