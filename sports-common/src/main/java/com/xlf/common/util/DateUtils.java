package com.xlf.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO
 * @author qsy
 * @version v1.0
 * @date 2016年11月25日
 */
public class DateUtils {
	public static final String DATE_TIME_FORMAT1="yyyy-MM-dd|HH:mm:ss";
	public static final String DATE_TIME_FORMAT2="yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMAT3="yyyy/MM/dd HH:mm:ss";
	public static final String DATE_TIME_FORMAT4="yyyyMMddHHmmss";
	public static final String DATE_TIME_FORMAT5="yyyyMMdd HH:mm:ss";
	public static final String DATE_TIME_FROMAT6="yyyy-MM-dd-HH";
	public static final String DATE_TIME_FORMAT7="yyyyMMddHHmmssSSS";
	public static final String DATE_TIME_FORMAT8="yyyy年MM月dd日";
	
	public static final String DATE_FORMAT1="yyyy-MM-dd";
	public static final String DATE_FORMAT2="yyyyMMdd";
	public static final String DATE_FORMAT3="yyyy.MM.dd";
	public static final String DATE_FORMAT4="yyyy/MM/dd";
	
	/**
	 * 格式化当前日期
	 * @param pattern 格式化类型
	 * @return 格式化后的日期字符
	 */
	public static String formatDateTime(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String now = sdf.format(new Date());
		return now;
	}
	
	/**
	 * 格式化指定日期
	 * @param pattern 格式化类型
	 * @param date 日期类型
	 * @return 格式化后的日期字符
	 */
	public static String formatDateTime(String pattern,Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String now = sdf.format(date);
		return now;
	}
	
	/**
	 * 格式化指定日期
	 * @param pattern 格式化类型
	 * @param time 日期long类型
	 * @return 格式化后的日期字符
	 */
	public static String formatDateTime(String pattern, long time) {
		return formatDateTime(pattern, new Date(time));
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
	 * 获取当前日期是星期几<br>
	 *
	 * @param date
	 * @return 当前日期是星期几
	 */
	public static int getWeekOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) ;
		if (w < 0){
			w = 0;
		}
		return w;
	}


}
