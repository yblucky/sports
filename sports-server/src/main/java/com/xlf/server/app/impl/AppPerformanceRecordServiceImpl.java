package com.xlf.server.app.impl;

import com.xlf.common.po.AppPerformanceRecordPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.PerformanceRecordVo;
import com.xlf.server.app.AppPerformanceRecordService;
import com.xlf.server.mapper.AppPerformanceRecordMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 业绩记录
 * Created by Administrator on 2018/1/4 0004.
 */
@Service
public class AppPerformanceRecordServiceImpl implements AppPerformanceRecordService {

    @Resource
    private AppPerformanceRecordMapper performanceRecordMapper;
    @Override
    public Integer insertPerformanceRecordList(List<AppPerformanceRecordPo> list) {
        return performanceRecordMapper.insertPerformanceRecordList(list);
    }


    /**
     * 查看用户交易流水记录总数
     */
    @Override
    public Integer performanceTotal(String userId, String area) {
        Integer count =performanceRecordMapper.performanceTotal(userId,area);
        if (count==null){
            count=0;
        }
        return count;
    }


    /**
     * 查看用户交易流水记录
     */
    @Override
    public List<PerformanceRecordVo> performanceList(String userId, String area, Paging paging) {
        RowBounds rowBounds = new RowBounds(paging.getPageNumber(), paging.getPageSize());
        List<PerformanceRecordVo> list = performanceRecordMapper.performanceList(userId,area, rowBounds);
        if (list==null|| CollectionUtils.isEmpty(list)){
            list= Collections.emptyList();
        }
        return list;
    }
}
