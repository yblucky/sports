package com.xlf.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class DateTimeUtil {
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public final static String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public final static String PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /**
     * HH:mm
     */
    public final static String PATTERN_HH_MM = "HH:mm";
    /**
     * yyyyMM
     */
    public final static String PATTERN_YYYYMM = "yyyyMM";
    /**
     * yyyyMMdd
     */
    public final static String PATTERN_YYYYMMDD = "yyyyMMdd";
    /**
     * yyyy-MM-dd
     */
    public final static String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * HH-mm-ss
     */
    public final static String PATTERN_HH_MM_SS = "HH:mm:ss";

    /**
     * 格式化日期
     *
     * @param date    需要格式化的 日期对象
     * @param pattern 日期样式
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 格式化日期
     *
     * @param day     相对当前时间的日期偏移,-1:日期向前偏移一天/1:日期向后偏移一天
     * @param pattern 日期样式
     */
    public static String formatDate(int day, String pattern) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, day);
        return formatDate(c.getTime(), pattern);
    }

    /**
     * 计算相对偏移日期午夜的秒数
     *
     * @param day 相对当前时间的日期偏移,-1:日期向前偏移一天/1:日期向后偏移一天
     */
    public static int getExpiration(int day) {
        Calendar c = Calendar.getInstance();
        long midnight = c.getTimeInMillis();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DAY_OF_MONTH, day);
        return (int) ((c.getTimeInMillis() - midnight) / 1000);
    }

    /**
     * 返回日期年月日整型，如20140318
     */
    public static int getYearMonthDay(Calendar calendar) {
        return calendar.get(Calendar.YEAR) * 10000
                + (calendar.get(Calendar.MONTH) + 1) * 100
                + calendar.get(Calendar.DATE);
    }

    /**
     * 获取日期的年期，如201401， addPeriodCount月份偏移量
     */
    public static int getYearPeriod(Date date, int addPeriodCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, addPeriodCount);
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        return y * 100 + m;
    }

    /**
     * 获取当月第一天
     */
    public static Date getMonthFirst() {
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.set(Calendar.DAY_OF_MONTH, 1);
        cal_1.set(Calendar.HOUR_OF_DAY, 0);
        cal_1.set(Calendar.MINUTE, 0);
        cal_1.set(Calendar.SECOND, 0);
        return new Date(cal_1.getTimeInMillis());
    }

    /**
     * 获取今日0点
     */
    public static Date getDayFirst() {
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.set(Calendar.HOUR_OF_DAY, 0);
        cal_1.set(Calendar.MINUTE, 0);
        cal_1.set(Calendar.SECOND, 0);
        return new Date(cal_1.getTimeInMillis());
    }

    /**
     * 加上多少天,并进行格式化
     */
    public static String getDayAddWithPattern(int day,String pattern) {
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.set(Calendar.DATE, cal_1.get(Calendar.DATE) + day);
        cal_1.set(Calendar.HOUR_OF_DAY, 0);
        cal_1.set(Calendar.MINUTE, 0);
        cal_1.set(Calendar.SECOND, 0);
        return DateTimeUtil.formatDate (new Date(cal_1.getTimeInMillis()),pattern);
    }

    /**
     * 减去多少天,并进行格式化
     */
    public static String getDayMinusWithPattern(int day,String pattern) {
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.set(Calendar.DATE, cal_1.get(Calendar.DATE) - day);
        cal_1.set(Calendar.HOUR_OF_DAY, 0);
        cal_1.set(Calendar.MINUTE, 0);
        cal_1.set(Calendar.SECOND, 0);
        return DateTimeUtil.formatDate (new Date(cal_1.getTimeInMillis()),pattern);
    }


    /**
     * 减去多少天
     */
    public static Date getDayMinus(int day) {
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.set(Calendar.DATE, cal_1.get(Calendar.DATE) - day);
        cal_1.set(Calendar.HOUR_OF_DAY, 0);
        cal_1.set(Calendar.MINUTE, 0);
        cal_1.set(Calendar.SECOND, 0);
        return new Date(cal_1.getTimeInMillis());
    }

    /**
     * 获取当月时间加30天
     */

    public static Date getDayThirty() {
        Calendar cal_1 = Calendar.getInstance();
        cal_1.add(Calendar.MONTH, 1);
        return new Date(cal_1.getTimeInMillis());

    }

    /**
     * 获取当月时间加30天
     */

    public static Date getDayThirty(Date date) {
        Calendar cal_1 = Calendar.getInstance();
        cal_1.setTime(date);
        cal_1.add(Calendar.MONTH, 1);
        return new Date(cal_1.getTimeInMillis());
    }

    //获取当月时间,减30天，加30天
    public static Map<String, Date> getDayThirtyMap() {
        Map<String, Date> timeMap = new HashMap<>();
        Calendar cal_1 = Calendar.getInstance();

        //获取当前时间
        Date nowDate = new Date(cal_1.getTimeInMillis());

        //获取当月时间减30天
        cal_1.add(Calendar.MONTH, -1);
        Date beforeDate = new Date(cal_1.getTimeInMillis());

        //获取当月时间加30天
        cal_1.add(Calendar.MONTH, 2);
        Date afterDate = new Date(cal_1.getTimeInMillis());
        timeMap.put("nowDate", nowDate);
        timeMap.put("beforeDate", beforeDate);
        timeMap.put("afterDate", afterDate);
        return timeMap;
    }


    /**
     * 字符串转时间
     */

    public static Date parseDateFromStr(String dateStr, String pattern) {
        if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(pattern)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 格式化当前时间,取间隔分钟区间值：以开奖时间为准
     */

    public static String parseCurrentDateMinuteIntervalToStr( String pattern,Integer interval) {
        if (StringUtils.isEmpty(pattern)) {
            return null;
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE,interval*(1+calendar.get(Calendar.MINUTE)/interval));
        Date date=calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return  format.format(date);
    }


    /**
     * 格式化当前时间,取间隔分钟区间值：以开始投注时间为准
     */

    public static String parseCurrentDateMinuteIntervalStartBettingToStr( String pattern,Integer interval) {
        if (StringUtils.isEmpty(pattern)) {
            return null;
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE,interval*(calendar.get(Calendar.MINUTE)/interval));
        Date date=calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return  format.format(date);
    }





    public static void createRacingInterval() {
        String id;
        int issueNo;
        String time;
        int type;
        String str="INSERT INTO app_time_interval(id,issueNo,time,type) VALUES({id},{issueNo},{time},20)";
        int minute=5;
        Map<Integer,String > map=new HashMap<>();
        Calendar c = Calendar.getInstance();
        c.set(2018,2,2,9,0,0);
        StringBuffer stringBuffer=new StringBuffer();

        for (int i=1;i<180;i++){
            c.add(Calendar.MINUTE,5);
            String mmss=formatDate(c.getTime(), DateTimeUtil.PATTERN_HH_MM);
            map.put(i,mmss);
        }

        System.out.println("9999999999999999999999999");
        System.out.println(map.size());

        for (Map.Entry m:map.entrySet()){
            System.out.println(m.getValue());
            String s=str.replace("{id}", "'"+ ToolUtils.getUUID()+"'").replace("{issueNo}", m.getKey().toString()).replace("{time}", "'"+(String)m.getValue()+"'");
           stringBuffer.append(s).append(";");
        }
        System.out.println("8888888888888888888888888");

        System.out.println("6666666666666666666666666");
        System.out.println(stringBuffer.toString());
    }




    public static void createTimeInterval() {
        String id;
        int issueNo;
        String time;
        int type;
        String str="INSERT INTO app_time_interval(id,issueNo,time,type) VALUES({id},{issueNo},{time},10)";
        int minute=5;
        Map<Integer,String > map=new HashMap<>();
        Calendar c = Calendar.getInstance();
        c.set(2018,2,2,0,0,0);
        StringBuffer stringBuffer=new StringBuffer();

        for (int i=1;i<121;i++){
            if (i<24){
                c.add(Calendar.MINUTE,5);
            }else if (i<=96){
                if (i==24){
                    c.set(2018,2,2,10,0,0);
                }else {
                    c.add(Calendar.MINUTE,10);
                }

            }else if (i<121){
                c.add(Calendar.MINUTE,5);
            }

            String mmss=formatDate(c.getTime(), DateTimeUtil.PATTERN_HH_MM);
            map.put(i,mmss);
        }

        System.out.println("9999999999999999999999999");
        System.out.println(map.size());

        for (Map.Entry m:map.entrySet()){
            System.out.println(m.getValue());
            String s=str.replace("{id}", "'"+ ToolUtils.getUUID()+"'").replace("{issueNo}", m.getKey().toString()).replace("{time}", "'"+(String)m.getValue()+"'");
           stringBuffer.append(s).append(";");
        }
        System.out.println("8888888888888888888888888");

        System.out.println("6666666666666666666666666");
        System.out.println(stringBuffer.toString());
    }

    public static Long getLongTimeByDatrStr(String  time) {
        String dateStr= DateTimeUtil.formatDate (new Date (),DateTimeUtil.PATTERN_YYYY_MM_DD)+" "+time;
        Date date  = DateTimeUtil.parseDateFromStr (dateStr,DateTimeUtil.PATTERN_YYYY_MM_DD_HH_MM);
        return date.getTime ();
    }

    //获取上周的开始时间和结束时间
    public static Map<String,String> getLastWeekTime() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil.PATTERN_YYYY_MM_DD);
        // System.out.println(sdf.format(calendar1.getTime()));// last Monday
        String lastBeginDate = sdf.format(calendar1.getTime());
        // System.out.println(sdf.format(calendar2.getTime()));// last Sunday
        String lastEndDate = sdf.format(calendar2.getTime());

        Map<String,String> dateMap = new HashMap<>();
        dateMap.put("startTime",lastBeginDate + " 00:00:00");
        dateMap.put("endTime",lastEndDate + " 23:59:59");

        return  dateMap;
    }

    //获取上月的开始时间和结束时间
    public static Map<String,String> getLastMonthTime() {
        //获取前一个月第一天
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH,1);
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil.PATTERN_YYYY_MM_DD);
        String firstDay = sdf.format(calendar1.getTime());
        //获取前一个月最后一天
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 0);
        String lastDay = sdf.format(calendar2.getTime());

        Map<String,String> dateMap = new HashMap<>();
        dateMap.put("startTime",firstDay + " 00:00:00");
        dateMap.put("endTime",lastDay + " 23:59:59");

        return  dateMap;
    }

    //获取本周第一天和第七天
    public static Map<String,String> getCurrentWeekTime() {
        Calendar cal = Calendar.getInstance();
        //cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil.PATTERN_YYYY_MM_DD);
        String imptimeBegin = sdf.format(cal.getTime());
        // System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        // System.out.println("所在周星期日的日期：" + imptimeEnd);

        Map<String,String> dateMap = new HashMap<>();
        dateMap.put("startTime",imptimeBegin + " 00:00:00");
        dateMap.put("endTime",imptimeEnd + " 23:59:59");

        return  dateMap;
    }

    //获取本月第一天和最后一天
    public static Map<String,String> getCurrentMonthTime() {
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.set(Calendar.DAY_OF_MONTH, 1);
        cal_1.set(Calendar.HOUR_OF_DAY, 0);
        cal_1.set(Calendar.MINUTE, 0);
        cal_1.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil.PATTERN_YYYY_MM_DD);
        String currentMonthFirst = sdf.format(cal_1.getTimeInMillis());

        //Calendar ca = Calendar.getInstance();
        cal_1.set(Calendar.DAY_OF_MONTH, cal_1.getActualMaximum(Calendar.DAY_OF_MONTH));
        String currentMonthEnd = sdf.format(cal_1.getTime());

        Map<String,String> dateMap = new HashMap<>();
        dateMap.put("startTime",currentMonthFirst + " 00:00:00");
        dateMap.put("endTime",currentMonthEnd + " 23:59:59");

        return  dateMap;
    }

    //获取本天的开始时间和结束时间
    public static Map<String,String> getCurrentDayTime(){
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil.PATTERN_YYYY_MM_DD);
        String day = sdf.format(new Date());
        System.out.println(sdf.format(new Date()) + " 23:59:59");
        Map<String,String> dateMap = new HashMap<>();
        dateMap.put("startTime",day + " 00:00:00");
        dateMap.put("endTime",day + " 23:59:59");
        //凌晨两点内算昨天那期
        String startDay = "";
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(hour <= 2){
            calendar.add(Calendar.DATE,-1);
            startDay = sdf.format(calendar.getTime());
            dateMap.put("startTime",startDay + " 00:00:00");
        }

        return  dateMap;
    }


    public static void main(String[] args) {
        //System.out.println (DateTimeUtil.parseCurrentDateMinuteIntervalToStr(DateTimeUtil.PATTERN_HH_MM, 5));
        //System.out.println (DateTimeUtil.parseCurrentDateMinuteIntervalToStr(DateTimeUtil.PATTERN_HH_MM, 10));
//        createTimeInterval();
//        createTimeInterval();
//        parseCurrentDateMinuteIntervalToStr(DateTimeUtil.PATTERN_HH_MM,5);
//        System.out.println(parseCurrentDateMinuteIntervalToStr(DateTimeUtil.PATTERN_HH_MM,5));
//        System.out.println(parseCurrentDateMinuteIntervalToStr(DateTimeUtil.PATTERN_HH_MM,-10));
//        System.out.println(parseCurrentDateMinuteIntervalToStr(DateTimeUtil.PATTERN_HH_MM,10));
//        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil.PATTERN_YYYY_MM_DD);
//        System.out.println(sdf.format(new Date()) + " 23:59:59");
//        System.out.println(DateTimeUtil.getDayAddWithPattern(1,DateTimeUtil.PATTERN_YYYYMMDD));
        Map<String,String> dateMap =  getCurrentDayTime();
        System.out.println (ToolUtils.toJson (dateMap));
    }
}
