package com.stb.vpay.task.app;


import com.xlf.common.po.AppRacingLotteryPo;
import com.xlf.common.po.AppTimeBettingPo;
import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.server.app.AppRacingBettingService;
import com.xlf.server.app.AppRacingLotteryService;
import com.xlf.server.app.AppTimeBettingService;
import com.xlf.server.app.AppTimeLotteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

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
                return;
            }
            List<AppTimeBettingPo> list = null;

            Boolean flag = false;
            flag = appRacingLotteryService.batchRacingLotteryHandleService(lotteryPo, flag);
            if (!flag) {
                //本期北京赛车全部设置为未中奖
                appRacingLotteryService.updateBatchLotteryFlag(lotteryPo.getIssueNo());
                //修改本期为全部已结算完成
                appRacingLotteryService.updateFlagById(lotteryPo.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
