package com.xlf.task.app;

import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.po.AppRacingLotteryPo;
import com.xlf.common.util.ToolUtils;
import com.xlf.server.app.AppRacingLotteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


public class PayRacingLotteryResultScheduleTask extends BaseScheduleTask {
    private static final Logger log = LoggerFactory.getLogger (PayRacingLotteryResultScheduleTask.class);
    @Resource
    private AppRacingLotteryService appRacingLotteryService;


    @Override
    protected void doSpecificTask() {

        List<AppRacingLotteryPo> lotteryPos = appRacingLotteryService.lotteryListCurrentDayByPayUrl ();
        if (CollectionUtils.isEmpty (lotteryPos)) {
            return;
        }
        for (AppRacingLotteryPo po:lotteryPos) {
            //将正确的开奖结果写入数据库
            AppRacingLotteryPo model = appRacingLotteryService.findAppRacingLotteryPoByIssuNo (po.getIssueNo ());
            if (model != null) {
                break;
            } else {
                po.setId (ToolUtils.getUUID ());
                po.setFlag(LotteryFlagEnum.NO.getCode());
                appRacingLotteryService.save (po);
            }
        }
    }



}
