package com.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间格式化工具类
 */
public class DateUtil {

	public static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
	public static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	
	/**
	 * 格式化日期时间
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return dateTimeFormat.format(date);
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}
	
	/**
	 * 格式化月份
	 * @param date
	 * @return
	 */
	public static String formatMonth(Date date) {
		return monthFormat.format(date);
	}
	
	/**
	 * 格式化时间
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {
		return timeFormat.format(date);
	}
	
	
	/**
	 * 解析日期
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 解析月份
	 * @param month
	 * @return
	 */
	public static Date parseMonth(String month) {
		try {
			return monthFormat.parse(month);
		} catch (ParseException e) {
			return null;
		}
	}

	
	/**
	 * 今天日期
	 * @return
	 */
	public static String today() {
		return formatDate(new Date());
	}
	
	/**
	 * 昨天日期
	 * @return
	 */
	public static String yesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return formatDate(calendar.getTime());
	}
	
}