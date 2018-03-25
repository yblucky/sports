package com.xlf.task.app;


import com.xlf.common.po.AppRacingLotteryPo;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.server.app.AppRacingLotteryService;
import com.xlf.server.app.AppTimeBettingService;
import com.xlf.server.app.AppTimeLotteryService;
import com.xlf.server.app.AppUserService;
import com.xlf.server.web.ParameterService;
import com.xlf.server.web.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

public class ClearToddyReturnWaterScheduleTask extends BaseScheduleTask {
    private static final Logger log = LoggerFactory.getLogger(ClearToddyReturnWaterScheduleTask.class);
    @Resource
    private SysUserService sysUserService;
    @Resource
    private AppUserService appUserService;
    @Resource
    private ParameterService parameterService;
    @Resource
    private AppRacingLotteryService appRacingLotteryService;


    @Override
    protected void doSpecificTask() {
        try {
            //清理用户的返水
            sysUserService.updateClearTotayReturnWater ();
            appUserService.updateClearTodayBettingAmoutTodayWiningAmout();
            AppRacingLotteryPo po=appRacingLotteryService.loadAwardNumber();
            if (po!=null && !StringUtils.isEmpty(po.getIssueNo())){
                parameterService.updateParameterByName("yesterdayRacingIssuNo",po.getIssueNo());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
