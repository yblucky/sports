package com.pf.server.app;

import com.pf.common.po.AppUserPo;
import com.pf.common.vo.app.BankCardVo;

/**
 * 银行卡相关业务
 * Created by Administrator on 2018/1/4 0004.
 */
public interface AppBankCardService {

    public BankCardVo findKey(String id);
}
