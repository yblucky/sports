package com.xlf.task.app;


import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.server.app.AppTimeBettingService;
import com.xlf.server.app.AppTimeLotteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

public class TimeBettingScheduleTaskUnUse extends BaseScheduleTask {
    private static final Logger log = LoggerFactory.getLogger(TimeBettingScheduleTaskUnUse.class);
    @Resource
    private AppTimeLotteryService appTimeLotteryService;
    @Resource
    private AppTimeBettingService appTimeBettingService;


    @Override
    protected void doSpecificTask() {
        try {
            AppTimeLotteryPo lotteryPo = appTimeLotteryService.findLast();
            if (lotteryPo == null) {
                log.info("没有待结算的投注订单");
                //修改本期为全部已结算完成
                appTimeLotteryService.updateFlagById(lotteryPo.getId());
                return;
            }
            List<AppTimeBettingPo> list = null;

            Boolean flag = false;
            flag = appTimeLotteryService.batchTimeLotteryHandleService(lotteryPo, flag);
            if (!flag) {
                //本期时时彩全部设置为未中奖
                appTimeBettingService.updateBatchLotteryFlag(lotteryPo.getIssueNo());
            }
            //修改本期为全部已结算完成
            appTimeLotteryService.updateFlagById(lotteryPo.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
