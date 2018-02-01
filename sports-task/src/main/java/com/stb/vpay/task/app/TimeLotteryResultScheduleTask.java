package com.stb.vpay.task.app;

import com.xlf.common.util.DateTimeUtil;
import com.xlf.common.util.HttpUtils;

import java.util.Date;

/**
 * Created by Administrator on 2018/2/1 0001.
 */
public class TimeLotteryResultScheduleTask extends BaseScheduleTask {


    @Override
    protected void doSpecificTask() {
        String TIME_URL = "http://kaijiang.500.com/static/public/ssc/xml/qihaoxml/";
        String currentDate=DateTimeUtil.formatDate(new Date(),DateTimeUtil.PATTERN_YYYYMMDD);
        TIME_URL+=currentDate;
        TIME_URL+=".xml";
        HttpUtils.sendGet(TIME_URL, TIME_URL);
    }

    public static void main(String[] args) {
        String TIME_URL = "http://kaijiang.500.com/static/public/ssc/xml/qihaoxml/";
        String currentDate=DateTimeUtil.formatDate(new Date(),DateTimeUtil.PATTERN_YYYYMMDD);
        TIME_URL+=currentDate;
        TIME_URL+=".xml";
        System.out.println(TIME_URL);

        System.out.println(HttpUtils.sendGet(TIME_URL, TIME_URL));
    }
}
