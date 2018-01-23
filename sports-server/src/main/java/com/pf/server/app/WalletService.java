package com.pf.server.app;

import com.pf.common.po.AppUserPo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 转账业务接口
 * Created by Administrator on 2017/8/22.
 */
public interface WalletService {

    /**
     * ep转账
     * @param payUser
     * @param payeeUser
     * @param amount
     * @throws Exception
     */
    public void payment(AppUserPo payUser, AppUserPo payeeUser, String amount) throws Exception;

    /**
     * EP兑换
     * @param userId
     * @param amount
     * @return
     * @throws Exception
     */
    public Boolean epExchange(String userId,BigDecimal amount) throws Exception;

    /**
     * 候鸟积分转账
     * @param payUser
     * @param payeeUser
     * @param amount
     * @throws Exception
     */
    public void birdtransfer(AppUserPo payUser, AppUserPo payeeUser, String amount) throws Exception;


    /**
     * 保存用户签到信息
     * */
    void openRedPacket(BigDecimal releaseAmount,BigDecimal epAmount,BigDecimal birdScore,AppUserPo appUserPo) throws Exception;

}
