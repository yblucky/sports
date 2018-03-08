package com.xlf.task.app;

import com.xlf.common.enums.LotteryFlagEnum;
import com.xlf.common.exception.CommException;
import com.xlf.common.po.AppTimeLotteryPo;
import com.xlf.common.util.DateTimeUtil;
import com.xlf.common.util.HttpUtils;
import com.xlf.common.util.ToolUtils;
import com.xlf.server.app.AppTimeLotteryService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TimeLotteryResultScheduleTask extends BaseScheduleTask {
    @Resource
    private AppTimeLotteryService appTimeLotteryService;


    @Override
    protected void doSpecificTask() {
        List<AppTimeLotteryPo> list = appTimeLotteryService.lotteryListCurrentDay();
        for (AppTimeLotteryPo po : list) {
            AppTimeLotteryPo model = appTimeLotteryService.findAppTimeLotteryPoByIssuNo (po.getIssueNo());
            if (model != null) {
                break;
            } else {
                po.setId(ToolUtils.getUUID());
                po.setFlag(LotteryFlagEnum.NO.getCode());
                appTimeLotteryService.save(po);
            }
        }
    }

    public static void main(String[] args) {
        String TIME_URL = "http://kaijiang.500.com/static/public/ssc/xml/qihaoxml/";
        String currentDate = DateTimeUtil.formatDate(new Date(), DateTimeUtil.PATTERN_YYYYMMDD);
        TIME_URL += currentDate;
        TIME_URL += ".xml";
        String resxml = HttpUtils.sendGet(TIME_URL, TIME_URL);
        resxml = resxml.replace("<?xml version=\"1.0\" encoding=\"gb2312\" ?><xml>", "").replace("</xml>", "");
        resxml = resxml.replaceFirst("<row ", "");
        String[] rowArr = resxml.split("<row ");
        List<AppTimeLotteryPo> listPo = new ArrayList<>();
        for (String row : rowArr) {
            row = row.replace("/>", "");
            System.out.println("row.replace:" + row);
            AppTimeLotteryPo rowAppTimeLotteryPo = getAppTimeLotteryPo(row);
            listPo.add(rowAppTimeLotteryPo);
        }
        System.out.println(listPo.size());
    }

    private static AppTimeLotteryPo getAppTimeLotteryPo(String str) {
        AppTimeLotteryPo po = new AppTimeLotteryPo();
        List<String> list = new ArrayList<String>();
        List<AppTimeLotteryPo> timeLotteryPoList = new ArrayList<AppTimeLotteryPo>();
        Pattern pattern = Pattern.compile("\"(.*?)(?<![^\\\\]\\\\)\"");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            list.add(matcher.group().replace("\"",""));
        }
        po.setCreateTime(DateTimeUtil.parseDateFromStr(list.get(2), DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM));
        po.setIssueNo(list.get(0));
        po.setFlag(LotteryFlagEnum.NO.getCode());
        String lottery = list.get(1);
        String[] lotteryArr = lottery.split(",");
        if (lotteryArr.length < 5) {
            throw new CommException("获取时时彩：" + po.getIssueNo() + " 开奖信息失败");
        }
        po.setLotteryOne(Integer.valueOf(lotteryArr[4]));
        po.setLotteryTwo(Integer.valueOf(lotteryArr[3]));
        po.setLotteryThree(Integer.valueOf(lotteryArr[2]));
        po.setLotteryFour(Integer.valueOf(lotteryArr[1]));
        po.setLotteryFive(Integer.valueOf(lotteryArr[0]));
        return po;
    }

}
