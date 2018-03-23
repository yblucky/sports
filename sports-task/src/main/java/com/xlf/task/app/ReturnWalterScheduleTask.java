package com.xlf.task.app;

import com.xlf.common.enums.BusnessTypeEnum;
import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.po.AppBillRecordPo;
import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysAgentSettingPo;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.pc.SysUserVo;
import com.xlf.server.app.AppTimeLotteryService;
import com.xlf.server.app.AppUserService;
import com.xlf.server.app.SysAgentSettingService;
import com.xlf.server.web.SysUserService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ReturnWalterScheduleTask extends BaseScheduleTask {
    @Resource
    private AppUserService appUserService;


    @Override
    protected void doSpecificTask() {
        try {
            if (appUserService.agentRetunWaterService ()) return;
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }




}
