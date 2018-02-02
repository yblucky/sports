package com.stb.vpay.task.app;

import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.common.util.DateTimeUtil;
import com.xlf.common.util.HttpUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.server.app.AppTimeLotteryService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class TimeLotteryResultScheduleTask extends BaseScheduleTask {
    @Resource
    private AppTimeLotteryService appTimeLotteryService;


    @Override
    protected void doSpecificTask() {
        List<AppTimeLotteryPo> list = appTimeLotteryService.lotteryListCurrentDay();
        for (AppTimeLotteryPo po : list) {
            AppTimeLotteryPo model = appTimeLotteryService.findById(po.getIssueNo());
            if (model != null) {
                break;
            } else {
                po.setId(ToolUtils.getUUID());
                po.setLotteryTime(null);
                appTimeLotteryService.save(po);
            }
        }
    }

    public static void main(String[] args) {
        String TIME_URL = "http://kaijiang.500.com/static/public/ssc/xml/qihaoxml/";
        String currentDate = DateTimeUtil.formatDate(new Date(), DateTimeUtil.PATTERN_YYYYMMDD);
        TIME_URL += currentDate;
        TIME_URL += ".xml";
        System.out.println(TIME_URL);
        String resxml = HttpUtils.sendGet(TIME_URL, "");
        resxml = resxml.replace("<?xml version=\"1.0\" encoding=\"gb2312\" ?><xml>", "").replace("</xml>", "");
        resxml = resxml.replaceFirst("<row ", "");
        String[] rowArr = resxml.split("<row ");
        List<Map> listMap = new ArrayList<>();
    }
}
