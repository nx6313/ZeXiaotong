package cn.gk.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateFormatUtil {
	/**
	 * 日期格式"yyyy-MM-dd"
	 */
	public static final String YYYYMMDD = "yyyy-MM-dd";

	/**
	 * 日期格式"yyyy-MM"
	 */
	public static final String YYYYMM = "yyyy-MM";
	/**
	 * 日期格式"yyyyMM"
	 */
	public static final String yyyymm = "yyyyMM";

	/**
	 * 日期格式"yyyy"
	 */
	public static final String YYYY = "yyyy";

	/**
	 * 日期格式"MM"
	 */
	public static final String MM = "MM";
	/**
	 * 日期格式"dd"
	 */
	public static final String dd = "dd";

	/**
	 * 时间格式"HH:mm:ss"
	 */
	public static final String HHMMSS = "HH:mm:ss";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String TYPE = "yyyy-MM-dd HH:mm:ss";

	/**
	 * "HH:mm"
	 */
	public static final String HHMM = "HH:mm";

	/**
	 * "MM-dd"
	 */
	public static final String MMDD = "MM-dd";

	/**
	 * "MM月dd日"
	 */
	public static final String MMDD1 = "MM月dd日";

	/**
	 * yyyy年MM月dd日 HH时mm分
	 */
	public static final String TYPE1 = "yyyy年MM月dd日 HH时mm分";

	/**
	 * yyyy年MM月dd日
	 */
	public static final String TYPE1_ = "yyyy 年 MM 月 dd 日";

	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static final String TYPE2 = "yyyy-MM-dd HH:mm";

	/**
	 * yyyyMMddHHmm
	 */
	public static final String TYPE3 = "yyyyMMddHHmm";
	/**
	 * yyyyMMddHHmmss
	 */
	public static final String TYPE4 = "yyyyMMddHHmmss";
	/**
	 * yy年MM月dd日 HH:mm:ss
	 */
	public static final String TYPE5 = "yy年MM月dd日 HH:mm:ss";
	/**
	 * yy-MM-dd HH:mm:ss
	 */
	public static final String TYPE6 = "yy-MM-dd HH:mm:ss";
	/**
	 * yyMMddHHmmss
	 */
	public static final String TYPE7 = "yyMdHHmmssSSS";

	/**
	 * yyyyMMddHHmmssSSS
	 */
	public static final String NUM_TYPE = "yyyyMMddHHmmssSSS";
	/**
	 * yyyy/MM/dd HH:mm
	 */
	public static final String NEW_TYPE1 = "yyyy/MM/dd HH:mm";
	/**
	 * yyyy/MM/dd
	 */
	public static final String NEW_TYPE2 = "yyyy/MM/dd";
	/**
	 * yyyy/MM/dd HH:mm:ss
	 */
	public static final String NEW_TYPE3 = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 将字符串转化为日期
	 * 
	 * @param dateStr
	 *            字符串的值
	 * @param dateType
	 *            要转化的类型
	 * @return
	 */
	public static Date strToDate(String dateStr, String dateType) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateType);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期转换为字符串格式
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            要转化的格式
	 * @return
	 */
	public static String dateToStr(Date date, String dateType) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateType);
		return sdf.format(date);
	}

	/**
	 * 获取昨日日期
	 * 
	 * @param date
	 * @param dateType
	 * @return
	 */
	public static String getYesterday(Date date, String dateType) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(dateType);
		return sdf.format(date);
	}

	/**
	 * 获取明天日期
	 * 
	 * @param date
	 * @param dateType
	 * @return
	 */
	public static String getTomorrow(Date date, String dateType) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(dateType);
		return sdf.format(date);
	}

	/**
	 * 获取本周一
	 * 
	 * @param date
	 * @param dateType
	 * @return
	 */
	public static String getThisWeekFirst(Date date, String dateType) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(dateType);
		return sdf.format(date);
	}

	/**
	 * 获取本周日
	 * 
	 * @param date
	 * @param dateType
	 * @return
	 */
	public static String getThisWeekLast(Date date, String dateType) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		calendar.add(Calendar.DATE, 7 - day_of_week);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(dateType);
		return sdf.format(date);
	}

	/**
	 * 获取两个日期之间的所有日期
	 * 
	 * @param startDate
	 * @param endDate
	 * @param dateType
	 * @return
	 */
	public static List<String> getDateListBetweenTowDate(Date startDate,
			Date endDate, String dateType) {
		List<String> result = new ArrayList<String>();
		if (startDate.before(endDate)) {
			Date tomorrowDate = startDate;
			while (!dateToStr(tomorrowDate, dateType).equals(
					dateToStr(endDate, dateType))) {
				result.add(dateToStr(tomorrowDate, dateType));
				tomorrowDate = strToDate(getTomorrow(tomorrowDate, dateType),
						dateType);
			}
			result.add(dateToStr(tomorrowDate, dateType));
		}
		return result;
	}

	/**
	 * 获取年份
	 * 
	 * @param date
	 *            2016-06-22
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取日
	 * 
	 * @param date
	 *            2016-06-22
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 *            2016-06-22
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取上月最后一天
	 * 
	 * @param date
	 *            2016-06-22
	 * @return
	 */
	public static String getLastMonth(String dateType) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		Date lastDateOfPrevMonth = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat(dateType);
		return dateFormat.format(lastDateOfPrevMonth);
	}

	/**
	 * 并0
	 * 
	 * @param s
	 * @return
	 */
	public static String andZero(String s) {
		if (Integer.parseInt(s) < 10) {
			return "0" + s;
		} else {
			return s;
		}
	}

	/**
	 * 判断当前时间是否在指定时间段内，只判断到年月日
	 * 
	 * @param startDate
	 * @param endDate
	 * @return <0:之前 =0:之内 >0:之后
	 */
	public static int checkCurrentDateInOutDatePart(Date startDate, Date endDate) {
		Date current = strToDate(dateToStr(new Date(), YYYYMMDD), YYYYMMDD);
		if (current.compareTo(startDate) < 0) {
			return -1;
		} else if (current.compareTo(endDate) > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * 比较两个日期的大小
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(String date1, String date2, String dateType) {
		DateFormat df = new SimpleDateFormat(dateType);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
}
