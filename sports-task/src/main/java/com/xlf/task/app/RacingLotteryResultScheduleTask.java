package com.xlf.task.app;

import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.enums.LotteryTypeEnum;
import com.xlf.common.po.AppRacingLotteryPo;
import com.xlf.common.po.AppTimeIntervalPo;
import com.xlf.common.util.DateTimeUtil;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.task.RacingLotteryVo;
import com.xlf.server.app.AppRacingLotteryService;
import com.xlf.server.app.AppTimeIntervalService;
import com.xlf.server.common.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;


public class RacingLotteryResultScheduleTask extends BaseScheduleTask {
    private static final Logger log = LoggerFactory.getLogger (RacingLotteryResultScheduleTask.class);
    @Resource
    private AppRacingLotteryService appRacingLotteryService;
    @Resource
    private AppTimeIntervalService appTimeIntervalService;
    @Resource
    private CommonService commonService;


    @Override
    protected void doSpecificTask() {
        String yesterdayRacingIssuNo = commonService.findParameter ("yesterdayRacingIssuNo");
        if (StringUtils.isEmpty (yesterdayRacingIssuNo)) {
            log.error ("获取昨日北京赛车最后期数失败，须检查参数配置");
            return;
        }
        RacingLotteryVo lotteryVo = appRacingLotteryService.getLatestRacingLottery ();
        if (lotteryVo == null || StringUtils.isEmpty (lotteryVo.getIssue ())) {
            log.error ("获取第三方开奖结果失败");
            return;
        }
        String hhmm = DateTimeUtil.parseCurrentDateMinuteIntervalToStr (DateTimeUtil.PATTERN_HH_MM, 5);
        AppTimeIntervalPo timeIntervalPo = appTimeIntervalService.findByTime (hhmm, LotteryTypeEnum.RACING.getCode ());
        if (timeIntervalPo == null) {
            log.error ("获取北京赛车投注时间间隔失败");
            return;
        }
        //组合生成最新一期已开奖的开奖期数，此处做对比，防止获取结果和当期开奖期数不一致，导致错开
        String lastIssuNo = (Integer.valueOf (yesterdayRacingIssuNo) + Integer.valueOf (timeIntervalPo.getIssueNo ())) + "";
        if (!lastIssuNo.equals (lotteryVo.getPreIssue ())) {
            log.error ("获取北京赛车投注结果的期数和本平台不一致，无法正常开奖");
            return;
        }
        if (timeIntervalPo.getIssueNo () == 179) {
            commonService.updateParameterByName ("yesterdayRacingIssuNo", lastIssuNo);
        }
        //将正确的开奖结果写入数据库
        AppRacingLotteryPo model = appRacingLotteryService.findById (lotteryVo.getPreIssue ());
        if (model != null) {
            return;
        } else {
            AppRacingLotteryPo po = new AppRacingLotteryPo ();
            po.setId (ToolUtils.getUUID ());
            po.setLotteryTime (null);
            po.setFlag (LotteryFlagEnum.NO.getCode ());
            po.setCreateTime (new Date (lotteryVo.getOpenDateTime ()));
            po.setLotteryTime (null);
            po.setLotteryOne (lotteryVo.getOpenNum ().get (0));
            po.setLotteryTwo (lotteryVo.getOpenNum ().get (1));
            po.setLotteryThree (lotteryVo.getOpenNum ().get (2));
            po.setLotteryFour (lotteryVo.getOpenNum ().get (3));
            po.setLotteryFive (lotteryVo.getOpenNum ().get (4));
            po.setLotterySix (lotteryVo.getOpenNum ().get (5));
            po.setLotterySeven (lotteryVo.getOpenNum ().get (6));
            po.setLotteryEight (lotteryVo.getOpenNum ().get (7));
            po.setLotteryNine (lotteryVo.getOpenNum ().get (8));
            po.setLotteryTen (lotteryVo.getOpenNum ().get (9));
            appRacingLotteryService.save (po);
        }
    }


}
