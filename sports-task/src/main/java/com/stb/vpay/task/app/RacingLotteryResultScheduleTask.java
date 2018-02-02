package com.stb.vpay.task.app;

import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.po.AppRacingLotteryPo;
import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.common.util.HttpUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.common.vo.task.RacingLotteryVo;
import com.xlf.server.app.AppRacingLotteryService;

import javax.annotation.Resource;
import java.util.Date;


public class RacingLotteryResultScheduleTask extends BaseScheduleTask {
    @Resource
    private AppRacingLotteryService appRacingLotteryService;


    @Override
    protected void doSpecificTask() {
        RacingLotteryVo lotteryVo = appRacingLotteryService.getLatestRacingLottery();

            AppRacingLotteryPo model = appRacingLotteryService.findById(lotteryVo.getPreIssue());
            if (model != null) {
                return;
            } else {
                AppRacingLotteryPo po=new AppRacingLotteryPo();
                po.setId(ToolUtils.getUUID());
                po.setLotteryTime(null);
                po.setFlag(LotteryFlagEnum.NO.getCode());
                po.setCreateTime(new Date(lotteryVo.getOpenDateTime()));
                po.setLotteryTime(null);
                po.setLotteryOne(lotteryVo.getOpenNum().get(0));
                po.setLotteryTwo(lotteryVo.getOpenNum().get(1));
                po.setLotteryThree(lotteryVo.getOpenNum().get(2));
                po.setLotteryFour(lotteryVo.getOpenNum().get(3));
                po.setLotteryFive(lotteryVo.getOpenNum().get(4));
                po.setLotterySix(lotteryVo.getOpenNum().get(5));
                po.setLotterySeven(lotteryVo.getOpenNum().get(6));
                po.setLotteryEight(lotteryVo.getOpenNum().get(7));
                po.setLotteryNine(lotteryVo.getOpenNum().get(8));
                po.setLotteryTen(lotteryVo.getOpenNum().get(9));
                appRacingLotteryService.save(po);
            }
    }


    public RacingLotteryVo getLatestRacingLottery() {
        String RACING_URL = "https://www.cp9833.com/getLotteryBase.do";
        String json = HttpUtils.sendGet(RACING_URL, "gameCode=bjpk10");
        RacingLotteryVo vo = ToolUtils.toObject(json, RacingLotteryVo.class);
        return vo;
    }
}
