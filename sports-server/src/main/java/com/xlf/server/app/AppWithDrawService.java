package com.xlf.server.app;

import com.xlf.common.po.AppWithDrawPo;
import com.xlf.common.resp.Paging;
import com.xlf.common.vo.app.DrawRecordVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提现
 * Created by Administrator on 2018/1/4 0004.
 */
@Service
public interface AppWithDrawService {

    public  Boolean  epWithDraw(String userId,String  bankId, BigDecimal amount) throws  Exception;


    public Boolean save(AppWithDrawPo model);

    public List<DrawRecordVo> drawRecordList(String userId, Paging paging);

    public Integer drawRecordTotal(String userId);

}
