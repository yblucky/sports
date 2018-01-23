package com.xlf.server.app;

import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.AppBillRecordVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 流水记录业务
 * Created by Administrator on 2018/1/4 0004.
 */
public interface AppBillRecordService {

    public void saveBillRecord(String businessNumber, String userId, Integer busnessType, Integer currencyType, BigDecimal amount, BigDecimal beforeAmout, BigDecimal afterAmout, String remark,String extend) throws Exception;

    Integer  countCurrentDayWithDraw(String userId) throws Exception;


    /**
     * 根据货币类型查找用户流水记录
     */
    List<AppBillRecordVo> findBillRecord(Integer currencyType, Integer busnessType, String userId, Paging paging);

    /**
     * 根据货币类型统计用户流水数量
     * @param currencyType
     * @param busnessType
     * @param userId
     * @return
     */
    int billRecordTotal(Integer currencyType, Integer busnessType, String userId);
    
    public void add(AppBillRecordPo po) throws Exception;

    /**
     * 根据货币类型统计用户流水数量
     * @param currencyType
     * @param busnessTypeList
     * @param userId
     * @return
     */
    Integer billRecordListTotal(String userId,List<Integer> busnessTypeList,Integer currencyType);
    /**
     * 根据货币类型查找用户流水记录
     */
    List<AppBillRecordVo> findBillRecordList(String userId, List<Integer> busnessTypeList,Integer currencyType, Paging paging);


}
