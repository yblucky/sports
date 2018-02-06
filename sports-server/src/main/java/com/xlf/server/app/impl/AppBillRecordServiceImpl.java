package com.xlf.server.app.impl;

import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.AppBillRecordVo;
import com.xlf.common.vo.task.ReturnWaterVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.mapper.AppBillRecordMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 流水记录业务
 * Created by Administrator on 2018/1/4 0004.
 */
@Service
public class AppBillRecordServiceImpl implements AppBillRecordService {

    @Resource
    private AppBillRecordMapper billRecordMapper;
    @Override
    public void saveBillRecord(String businessNumber, String userId, Integer busnessType, BigDecimal amount, BigDecimal beforeAmout, BigDecimal afterAmout, String remark,String extend) throws Exception {
        AppBillRecordPo billRecordPo = new AppBillRecordPo();
        billRecordPo.setBusinessNumber(businessNumber);
        billRecordPo.setBalance(amount);
        billRecordPo.setId(ToolUtils.getUUID());
        billRecordPo.setBusnessType(busnessType);
       
        billRecordPo.setUserId(userId);
        billRecordPo.setCreateTime(new Date());
        billRecordPo.setBeforeBalance(beforeAmout);
        billRecordPo.setAfterBalance(afterAmout);
        billRecordPo.setRemark(remark);
        billRecordPo.setExtend(extend);
        billRecordMapper.insert(billRecordPo);
    }

    @Override
    public Integer countCurrentDayWithDraw(String userId) throws Exception {
        return billRecordMapper.countCurrentDayWithDraw(userId);
    }

    /**
     * 查看用户交易流水记录总数
     */
    @Override
    public int billRecordTotal(   Integer busnessType, String id) {
        AppBillRecordPo po = new AppBillRecordPo();
        
        po.setBusnessType(busnessType);
        po.setUserId(id);
        return billRecordMapper.selectCount(po);
    }

    /**
     * 查看用户交易流水记录
     */
    @Override
    public List<AppBillRecordVo> findBillRecord(   Integer busnessType, String userId, Paging paging) {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        AppBillRecordPo po = new AppBillRecordPo();
        
        po.setBusnessType(busnessType);
        po.setUserId(userId);
        List<AppBillRecordVo> list = billRecordMapper.findBillRecord(po, rowBounds);
        return list;
    }


    /**
     * 查看用户交易流水记录总数
     */
    @Override
    public Integer billRecordListTotal(String userId, List busnessTypeList,Integer currencyType) {
        Integer count =billRecordMapper.billRecordListTotal(userId,busnessTypeList,currencyType);
        if (count==null){
            count=0;
        }
        return count;
    }


    /**
     * 查看用户交易流水记录
     */
    @Override
    public List<AppBillRecordVo> findBillRecordList(String userId, List busnessTypeList, Paging paging) {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        List<AppBillRecordVo> list = billRecordMapper.findBillRecordList(userId,busnessTypeList, rowBounds);
        if (list==null|| CollectionUtils.isEmpty(list)){
            list= Collections.emptyList();
        }
        return list;
    }

	@Override
	public void add(AppBillRecordPo po) throws Exception {
		billRecordMapper.insert(po);
	}

    @Override
    public Integer batchSaveKickBackAmoutRecord(List<AppBillRecordPo> list) {
        return billRecordMapper.batchSaveKickBackAmoutRecord(list);
    }
}
