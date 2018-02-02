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
}
