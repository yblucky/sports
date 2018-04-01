package com.xlf.task.app;

import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.po.AppRacingLotteryPo;
import com.xlf.common.util.DateTimeUtil;
import com.xlf.common.util.ToolUtils;
import com.xlf.server.app.AppRacingLotteryService;
import com.xlf.server.common.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


public class PayRacingLotteryResultScheduleTask extends BaseScheduleTask {
    private static final Logger log = LoggerFactory.getLogger (PayRacingLotteryResultScheduleTask.class);
    @Resource
    private AppRacingLotteryService appRacingLotteryService;
    @Resource
    private CommonService commonService;

    @Override
    protected void doSpecificTask() {

        List<AppRacingLotteryPo> lotteryPos = appRacingLotteryService.lotteryListCurrentDayByPayUrl ();
        if (CollectionUtils.isEmpty (lotteryPos)) {
            return;
        }
        for (AppRacingLotteryPo po:lotteryPos) {
            //将正确的开奖结果写入数据库
            AppRacingLotteryPo model = appRacingLotteryService.findAppRacingLotteryPoByIssuNo (po.getIssueNo ());
            String currentDate = DateTimeUtil.formatDate(new Date(), DateTimeUtil.PATTERN_YYYY_MM_DD);
            String hhmmStart=" 23:55:30";
            Date start=DateTimeUtil.parseDateFromStr(currentDate+hhmmStart,DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM_SS);
            Date end=new Date(start.getTime()+10*60*1000);
            if (model != null) {
                if (po.getCreateTime().after(start) && po.getCreateTime().before(end)){
                    commonService.updateParameterByName ("yesterdayRacingIssuNo", po.getIssueNo());
                }
                break;
            } else {
                po.setId (ToolUtils.getUUID ());
                po.setFlag(LotteryFlagEnum.NO.getCode());
                appRacingLotteryService.save (po);
                if (po.getCreateTime().after(start) && po.getCreateTime().before(end)){
                    commonService.updateParameterByName ("yesterdayRacingIssuNo", po.getIssueNo());
                }
            }
        }
    }
}
