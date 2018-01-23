package com.xlf.server.app;

import com.xlf.common.po.AppPerformanceRecordPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.PerformanceRecordVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业绩记录
 * Created by Administrator on 2018/1/4 0004.
 */
@Service
public interface AppPerformanceRecordService {
    public Integer insertPerformanceRecordList(List<AppPerformanceRecordPo> list);

    public List<PerformanceRecordVo> performanceList(String userId, String area, Paging paging);

    public Integer performanceTotal(String userId, String area);
}
