package com.xlf.task.app;


import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.server.app.AppTimeBettingService;
import com.xlf.server.app.AppTimeLotteryService;
import com.xlf.server.web.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

public class ClearToddyReturnWaterScheduleTask extends BaseScheduleTask {
    private static final Logger log = LoggerFactory.getLogger(ClearToddyReturnWaterScheduleTask.class);
    @Resource
    private SysUserService sysUserService;


    @Override
    protected void doSpecificTask() {
        try {
            //清理用户的返水
            sysUserService.updateClearTotayReturnWater ();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
