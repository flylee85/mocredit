package cn.mocredit.gateway.posp.bc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期操作的工具类
 * 
 * @author Superdo
 * 
 */
public class DateUtils {

	private static SimpleDateFormat dateForamt = new SimpleDateFormat(
			"yyyyMMdd");

	private static SimpleDateFormat timeForamt = new SimpleDateFormat("HHmmss");

	public static String parseDate(Date date) {

		return dateForamt.format(date);
	}

	public static Date strToDate(String sDate) {
		try {
			return dateForamt.parse(sDate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return dateForamt.format(new Date());
	}

	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return timeForamt.format(new Date());
	}

	/**
	 * 是否是润年
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapyear(int year) {
		boolean isproyear = false;
		if ((year % 400 == 0) | (year % 100 != 0 && year % 4 == 0)) {
			isproyear = true;
		} else {
			isproyear = false;
		}
		return isproyear;
	}

	public static String getFirstDayOfMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		return dateForamt.format(calendar.getTime());
	}	
	public static String getLastDayOfMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return dateForamt.format(calendar.getTime());
	}
	
	public static String getDateFormat(Date date) {
		return dateForamt.format(date);
	}

	public static String getDateFormat(Date date, SimpleDateFormat dateFormat) {
		return dateFormat.format(date);
	}

	public static String getFirstDayOfMonth(Integer year, Integer month) {
		Calendar calendar = new GregorianCalendar();
		if (year == null) {
			year = calendar.get(Calendar.YEAR);
		}
		if (month == null) {
			month = calendar.get(Calendar.MONTH);
		}
		calendar.set(year, month, 1);
		return dateForamt.format(calendar.getTime());
	}

	public static String getLastDayOfMonth(Integer year, Integer month) {
		Calendar calendar = new GregorianCalendar();
		if (year == null) {
			year = calendar.get(Calendar.YEAR);
		}
		if (month == null) {
			month = calendar.get(Calendar.MONTH);
		}
		calendar.set(year, month, 1);
		calendar.roll(Calendar.DATE, -1);
		return dateForamt.format(calendar.getTime());
	}

	public static String getLastDayOfMonth(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return dateForamt.format(calendar.getTime());
	}

	public static boolean isLastDayOfMonth(Date date) {
		String lastTime = getLastDayOfMonth(date);
		String time = dateForamt.format(date);
		if (time.equalsIgnoreCase(lastTime)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getFirstDayOfYear() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		return dateForamt.format(calendar.getTime());
	}

	public static String getLastDayOfYear() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		return dateForamt.format(calendar.getTime());
	}

	public static String getFirstDayOfYear(Integer year) {
		Calendar calendar = new GregorianCalendar();
		if (year == null) {
			year = calendar.get(Calendar.YEAR);
		}
		calendar.set(year, 0, 1);
		return dateForamt.format(calendar.getTime());
	}

	public static String getLastDayOfYear(Integer year) {
		Calendar calendar = new GregorianCalendar();
		if (year == null) {
			year = calendar.get(Calendar.YEAR);
		}
		calendar.set(year, 0, 1);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		return dateForamt.format(calendar.getTime());
	}

	public static String getLastDayOfYear(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		return dateForamt.format(calendar.getTime());
	}

	public static boolean isLastDayOfYear(Date date) {
		String lastTime = getLastDayOfYear(date);
		String time = dateForamt.format(date);
		if (time.equalsIgnoreCase(lastTime)) {
			return true;
		} else {
			return false;
		}
	}

	public static Date getNextDay(Date today) {
		Calendar ca = new GregorianCalendar();
		ca.setTime(today);
		ca.add(Calendar.DAY_OF_YEAR, 1);
		return ca.getTime();
	}

	public static Date getLastDay(Date today) {
		Calendar ca = new GregorianCalendar();
		ca.setTime(today);
		ca.add(Calendar.DAY_OF_YEAR, -1);
		return ca.getTime();
	}

	
	/**
	 * 获取指定间隔天数后的日期
	 * @param today
	 * @param num 间隔天数
	 * @return
	 */
	public static Date getAfterDays(Date today,int num) {
		Calendar ca = new GregorianCalendar();
		ca.setTime(today);
		ca.add(Calendar.DAY_OF_YEAR, num);
		return ca.getTime();
	}

	
	/**
	 * 获取指定日期的月份
	 * 
	 * @param today
	 * @return
	 */
	public static int getDayMonth(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定日期的年份
	 * 
	 * @param today
	 * @return
	 */
	public static int getDayYear(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取指定日期的星期
	 * 
	 * @param today
	 * @return
	 */
	public static int getDayWeek(Date aDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(aDate);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 获取某个日期是当月的第几天
	 * 
	 * @param today
	 * @return
	 */
	public static int getDayOfMonth(Date aDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(aDate);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 比较两个日期大小
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean dateCompare(String date1,String date2){
		boolean flag = false;
		try {
			Date d1 = dateForamt.parse(date1);
			Date d2 = dateForamt.parse(date2);
			if(d1.after(d2)){
				flag = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;
		
	}


}

