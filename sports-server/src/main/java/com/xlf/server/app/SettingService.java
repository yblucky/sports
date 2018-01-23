/* 
 * 文件名：UserService.java  
 * 版权：Copyright 2016-2017 炎宝网络科技  All Rights Reserved by
 * 修改人：lxl  
 * 创建时间：2017年6月12日
 * 版本号：v1.0
*/
package com.xlf.server.app;

import com.xlf.common.po.AppBankCardPo;
import com.xlf.common.po.AppBankTypePo;
import com.xlf.common.vo.app.BankCardVo;

import java.util.List;

/**
 * 设置业务层接口
 *
 * @author jay
 * @version v1.0
 * @date 2017年8月18日
 */
public interface SettingService {

    /**
     * 新增|修改银行卡
     *
     * @param bank   银行卡信息
     * @param action true 新增 false 修改
     * @return
     * @throws Exception
     */
    public void callBankCard(BankCardVo bank, boolean action) throws Exception;

    /**
     * 修改默认银行卡
     * @param bankId
     * @param userId
     * @throws Exception
     */
    public void defaultBankCard(String bankId,String userId) throws Exception;

    /**
     * 查找银行卡
     *
     * @return 集合
     * @throws Exception
     */
    public List<AppBankCardPo> findAll(String userId) throws Exception;

    /**
     * 查询用户默认银行卡
     */
    AppBankCardPo findUserDefualtCard(String userId) throws Exception;

    /**
     * 删除用户银行卡
     * @param ids
     */
    int deleteBankCard(List<AppBankCardPo> ids) throws Exception;

    /**
     * 删除用户银行卡
     * @param cardId
     */
    int deleteBankCardById(String  cardId) throws Exception;

    AppBankTypePo findBankType(String typeId) throws Exception;

    List<AppBankTypePo> typeList() throws Exception;

}
