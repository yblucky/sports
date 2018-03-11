package com.xlf.server.app.impl;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.WithDrawEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.AppWithDrawPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.app.AppBillRecordVo;
import com.xlf.common.vo.app.DrawRecordVo;
import com.xlf.server.app.AppBillRecordService;
import com.xlf.server.app.AppUserService;
import com.xlf.server.app.AppWithDrawService;
import com.xlf.server.common.CommonService;
import com.xlf.server.mapper.AppUserMapper;
import com.xlf.server.mapper.AppWithDrawMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpHeaders.FROM;

/**
 * 提现
 * Created by Administrator on 2018/1/4 0004.
 */
@Service
public class AppWithDrawServiceImpl implements AppWithDrawService {

    @Resource
    private AppUserService userService;
    @Resource
    private AppBillRecordService billRecordService;
    @Resource
    private CommonService commonService;

    @Resource
    private AppUserMapper appUserMapper;

    @Resource
    private AppWithDrawMapper withDrawMapper;

    @Override
    public Boolean save(AppWithDrawPo model) {
        model.setId(ToolUtils.getUUID());
        model.setState(WithDrawEnum.PENDING.getCode());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        withDrawMapper.insertSelective(model);
        return true;
    }

    @Override
    @Transactional
    public Boolean epWithDraw(String userId, String bankId, BigDecimal amount) throws Exception {
        BigDecimal withdrawFee = new BigDecimal(commonService.findParameter("withdrawFee"));
        AppUserPo userPo = userService.findUserById(userId);
        if (userPo==null || userPo.getBalance ().compareTo (amount)<=0){
            throw  new CommException ("用户余额不足，无法提现");
        }
        appUserMapper.updateBalanceById(userId,amount.multiply(new BigDecimal("-1")));
        appUserMapper.updateBlockBalanceById(userId,amount);
        BigDecimal am=amount.multiply(new BigDecimal("-1")).setScale(4,BigDecimal.ROUND_HALF_EVEN);
        BigDecimal fee=amount.multiply(withdrawFee).setScale(4,BigDecimal.ROUND_HALF_EVEN);
        BigDecimal before=userPo.getBalance();
        BigDecimal after=userPo.getBalance().subtract(amount);

        AppWithDrawPo model = new AppWithDrawPo();
        model.setFee(fee);
        model.setAmount(amount);
        model.setUserId(userId);
        model.setBankCardId(bankId);
        model.setBeforeBalance(before);
        this.save(model);
        billRecordService.saveBillRecord(ToolUtils.getUUID(),userId, BusnessTypeEnum.WITHDRAWALS.getCode(),am,before,after,"用户提现",userPo.getNickName());
        return true;
    }


    /**
     * 查看用户交易流水记录总数
     */
    @Override
    public Integer drawRecordTotal(String userId) {
        Integer count =withDrawMapper.drawRecordTotal(userId);
        if (count==null){
            count=0;
        }
        return count;
    }


    /**
     * 查看用户交易流水记录
     */
    @Override
    public List<DrawRecordVo> drawRecordList(String userId, Paging paging) {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        List<DrawRecordVo> list = withDrawMapper.drawRecordList(userId, rowBounds);
        if (list==null|| CollectionUtils.isEmpty(list)){
            list= Collections.emptyList();
        }
        return list;
    }

    @Override
    public Double drawSumCurrentDay(String userId) {
        Double sum= withDrawMapper.drawSumCurrentDay (userId);
        if (sum==null){
            sum=0d;
        }
        return sum;
    }


    @Override
    public Integer drawRecordListTotal(String userId) {
        Integer count= withDrawMapper.drawRecordListTotal (userId);
        if (count==null){
            count=0;
        }
        return count;
    }

    @Override
    public List<AppWithDrawPo> withDrawRecordList(String userId, Paging paging) {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        List<AppWithDrawPo> list = withDrawMapper.withDrawRecordList(userId, rowBounds);
        if (list==null|| CollectionUtils.isEmpty(list)){
            list= Collections.emptyList();
        }
        return list;
    }

}
