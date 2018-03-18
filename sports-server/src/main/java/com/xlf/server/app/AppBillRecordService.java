package com.xlf.server.app;

import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.AppBillRecordVo;
import com.xlf.common.vo.pc.LotteryVo;
import com.xlf.common.vo.pc.RevenueVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 流水记录业务
 * Created by Administrator on 2018/1/4 0004.
 */
public interface AppBillRecordService {

    public void saveBillRecord(String businessNumber, String userId, Integer busnessType, BigDecimal amount, BigDecimal beforeAmout, BigDecimal afterAmout, String remark, String extend) throws Exception;

    Integer countCurrentDayWithDraw(String userId) throws Exception;


    /**
     * 根据货币类型查找用户流水记录
     */
    List<AppBillRecordVo> findBillRecord(Integer busnessType, String userId, Paging paging);

    /**
     * 根据货币类型统计用户流水数量
     *
     * @param busnessType
     * @param userId
     * @return
     */
    int billRecordTotal(Integer busnessType, String userId);

    public void add(AppBillRecordPo po) throws Exception;

    /**
     * 根据货币类型统计用户流水数量
     *
     * @param busnessTypeList
     * @param userId
     * @return
     */
    Integer billRecordListTotal(String userId, List<Integer> busnessTypeList, String startTime, String endTime);

    /**
     * 根据货币类型查找用户流水记录
     */
    List<AppBillRecordVo> findBillRecordList(String userId, List<Integer> busnessTypeList, Paging paging, String startTime, String endTime);

    Integer batchSaveKickBackAmoutRecord(List<AppBillRecordPo> list);


    List<RevenueVo> revenueList(RevenueVo vo, Paging paging);

    Double report( String userId,  List<Integer> busnessTypeList,  String startTime,   String endTime);

    public Integer reportCount(String userId, List<Integer> busnessTypeList, String startTime, String endTime);
}
