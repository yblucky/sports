package com.xlf.task.app;


import com.xlf.common.po.AppRacingLotteryPo;
import com.xlf.server.app.AppRacingBettingService;
import com.xlf.server.app.AppRacingLotteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class RacingBettingScheduleTask extends BaseScheduleTask {
    private static final Logger log = LoggerFactory.getLogger(RacingBettingScheduleTask.class);
    @Resource
    private AppRacingBettingService appRacingBettingService;
    @Resource
    private AppRacingLotteryService appRacingLotteryService;


    @Override
    protected void doSpecificTask() {
        try {
            AppRacingLotteryPo lotteryPo = appRacingLotteryService.findLast();
            if (lotteryPo == null) {
                log.info("没有待结算的投注订单");
                //修改本期为全部已结算完成
                appRacingLotteryService.updateFlagById(lotteryPo.getId());
                return;
            }
            Boolean flag = false;
            flag = appRacingLotteryService.batchRacingLotteryHandleService(lotteryPo, flag);
            if (!flag) {
                //本期北京赛车全部设置为未中奖
                appRacingBettingService.updateBatchLotteryFlag(lotteryPo.getIssueNo());
            }
            //修改本期为全部已结算完成
            appRacingLotteryService.updateFlagById(lotteryPo.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
