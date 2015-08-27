package com.mocredit.integral.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

	public static String parseYYYYMMDD(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDays[w];
	}

	public static String dateToStr(java.util.Date aDteValue, String aFmtDate) {
		String strRtn = null;
		if (aFmtDate.length() == 0) {
			aFmtDate = "yyyyMMddHHmmss";
		}
		Format fmtDate = new SimpleDateFormat(aFmtDate);
		try {
			strRtn = fmtDate.format(aDteValue);
		} catch (Exception e) {
		}
		return strRtn;
	}

	public static String getDate(String format) {
		Date a = new Date();
		return dateToStr(a, format);
	}

	public static String getDate() {
		Date a = new Date();
		return dateToStr(a, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getDate(String format, int addday) {
		Date a = new Date();
		return dateToStr(addday(a, addday), format);
	}

	public static Date strToDate(String aStrValue, String aFmtDate)
			throws ParseException {
		Date aDateRtn = new Date();
		if (aFmtDate.trim().length() == 0) {
			aFmtDate = "yyyy-MM-dd";
		}
		SimpleDateFormat fmtDate = new SimpleDateFormat(aFmtDate);
		try {
			aDateRtn.setTime(fmtDate.parse(aStrValue).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw e;
		}
		return aDateRtn;
	}

	public static Date addSecond(Date strdate, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strdate);
		cal.add(Calendar.MINUTE, second);
		return cal.getTime();
	}

	public static Date addMinute(Date strdate, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strdate);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	public static Date addHour(Date strdate, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strdate);
		cal.add(Calendar.HOUR, hour);
		return cal.getTime();
	}

	public static Date addday(Date strdate, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strdate);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	public static Date addMonth(Date strdate, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strdate);
		cal.add(Calendar.MONTH, num);
		return cal.getTime();

	}

	public static Date addYear(Date strdate, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strdate);
		cal.add(Calendar.YEAR, num);
		return cal.getTime();
	}

	public static String getMinDayOfCurrentWeek(String fmate) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK,
				calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
		SimpleDateFormat format = new SimpleDateFormat(fmate);
		return format.format(calendar.getTime());
	}

	public static String getMaxDayOfCurrentWeek(String fmate) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK,
				calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
		SimpleDateFormat format = new SimpleDateFormat(fmate);
		return format.format(calendar.getTime());
	}

	public static String getMinDayOfCurrentMonth(String fmate) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat format = new SimpleDateFormat(fmate);
		return format.format(calendar.getTime());
	}

	public static String getMaxDayOfCurrentMonth(String fmate) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat format = new SimpleDateFormat(fmate);
		return format.format(calendar.getTime());
	}

	/**
	 * 
	 * Description: 获取前七天的时间
	 * 
	 * @Version1.0 2010-8-27 下午04:45:13 by 凡红恩（fanhongen@emay.cn）创建
	 * @param strdate
	 * @param day
	 * @return
	 */
	public static String getDateOfPre(int day) {
		Date date = addday(new Date(), day);
		return dateToStr(date, "yyyy-MM-dd");
	}

	/**
	 * 获得两个日期间的差
	 * 
	 * @param sdate
	 *            起始时间
	 * @param edate
	 *            终止时间
	 * @param bettype
	 *            Calendar.DAY_OF_YEAR 求查的单位
	 * @return
	 */
	public static int numBetweenTwoDate(Date sdate, Date edate, int bettype) {
		if (bettype == Calendar.MONTH) {
			Calendar aCalendar = Calendar.getInstance();
			aCalendar.setTime(sdate);
			int day1 = aCalendar.get(bettype);
			aCalendar.setTime(edate);
			int day2 = aCalendar.get(bettype);
			return day2 - day1;
		} else if (bettype == Calendar.HOUR || bettype == Calendar.HOUR_OF_DAY) {
			// String ssdate=DateTimeUtils.dateToStr(sdate, "yyyyMMddHH");
			// String sedate=DateTimeUtils.dateToStr(edate, "yyyyMMddHH");
			// Integer ssmonth=Integer.parseInt(ssdate.substring(6,8));
			// Integer semonth=Integer.parseInt(sedate.substring(6,8));
			// if(ssmonth==semonth){
			// Integer sshour=Integer.parseInt(ssdate.substring(8,10));
			// Integer sehour=Integer.parseInt(sedate.substring(8,10));
			// return sehour-sshour;
			// }
			long seconds = (edate.getTime() - sdate.getTime()) / 1000;
			return (int) seconds / (60 * 60);// 相差的小时数
		} else if (bettype == Calendar.DAY_OF_YEAR) {
			long seconds = (edate.getTime() - sdate.getTime()) / 1000;
			return (int) seconds / (24 * 60 * 60); // 相差的天数
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * Description:
	 * 
	 * @Version1.0 2011-2-28 下午03:47:55 by 凡红恩（fanhongen@emay.cn）创建
	 * @param strDateStart
	 * @param strDateEnd
	 * @return
	 * @throws ParseException
	 */
	public static long getDateofpoor(String strDateStart, String strDateEnd)
			throws ParseException {
		long quot = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date_start = sdf.parse(strDateStart);
			Date date_end = sdf.parse(strDateEnd);
			quot = date_end.getTime() - date_start.getTime();
			quot /= 1000 * 60 * 60 * 24;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return quot;
	}

	public static String betweendate(String statetime, String endtime)
			throws ParseException {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date begin = dfs.parse(statetime);
		java.util.Date end = dfs.parse(endtime);
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		long day1 = between / (24 * 3600);
		long hour1 = between % (24 * 3600) / 3600;
		long minute1 = between % 3600 / 60;
		long second1 = between % 60;
		return "" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒";
	}

	/**
	 * 两个时间差值（单位为秒）
	 * 
	 * @param statetime
	 *            开始时间
	 * @param endtime
	 *            结束时间
	 * @param format
	 *            格式（yyyy-MM-dd HH:mm:ss 或 yyyyMMddHHmmssSSS）
	 * @return
	 * @throws ParseException
	 */
	public static long betweensecond(String statetime, String endtime,
			String format) throws ParseException {
		SimpleDateFormat dfs = new SimpleDateFormat(format);
		java.util.Date begin = dfs.parse(statetime);
		java.util.Date end = dfs.parse(endtime);
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		// long day1 = between / (24 * 3600);
		// long hour1 = between % (24 * 3600) / 3600;
		// long minute1 = between % 3600 / 60;
		// long second1 = between % 60;
		return between;
	}

	/**
	 * 获取淘宝增量时间段
	 * 
	 * @param minute
	 *            间隔时间单位分钟,最小为10分钟
	 * @return
	 */
	public static Date[] getTaoBaoSandE(int minute) {
		if (minute < 10) {
			minute = 10;
		}
		Date end = new Date();
		String e = getDate("yyyy-MM-dd");
		Date start = addMinute(end, -1 * minute);
		String s = dateToStr(start, "yyyy-MM-dd");
		if (s.equals(e)) {
			return new Date[] { start, end };
		} else {
			Date st = null;
			try {
				e += " 00:00:00";
				st = strToDate(e, "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			Date end2 = addday(new Date(), -1);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(end2);
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			end2 = calendar.getTime();
			Date st2 = addMinute(end2, -1 * minute);
			return new Date[] { st, end, st2, end2 };
		}
	}

	/**
	 * 将yyyy-MM-dd时间字符串格式化成yyyy年MM月dd日格式字符串。
	 * 
	 * @param s
	 * @return
	 */
	public static String formateCNDate(String s) {
		StringBuilder result = new StringBuilder();
		result.append(s.substring(0, 4)).append("年").append(s.substring(5, 7))
				.append("月").append(s.substring(8, 10)).append("日");
		return result.toString();
	}

	public static void main(String[] args) throws ParseException {
		// System.out.println("本月第一天是：" +
		// DateTimeUtils.getMinDayOfCurrentMonth("yyyy-MM-dd"));
		// System.out.println("本月最后一天是：" +
		// DateTimeUtils.getMaxDayOfCurrentMonth("yyyy-MM-dd"));
		// System.out.println("本周第一天是：" +
		// DateTimeUtils.getMinDayOfCurrentWeek("yyyy-MM-dd"));
		// System.out.println("本周最后一天是：" +
		// DateTimeUtils.getMaxDayOfCurrentWeek("yyyy-MM-dd"));
		// SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date state = new Date();
		// Date begin=dfs.parse("2011-01-01 00:00:00");
		// System.out.println(state-begin);
		// System.out.println(getDateofpoor("2011-06-01","2011-05-01 14:11:11"));
		//
		// Date[] s = DateTimeUtils.getTaoBaoSandE(30);
		// System.out.println(s[0]);
		// System.out.println(s[1]);
		// if (s.length == 4) {
		// System.out.println(s[2]);
		// System.out.println(s[3]);
		// }
		System.out.println(DateTimeUtils
				.betweensecond("20120906203212122",
						DateTimeUtils.getDate("yyyyMMddHHmmssSSS"),
						"yyyyMMddHHmmssSSS"));
	}

	public static String addDay(String format, int addday) {
		Date a = new Date();
		return dateToStr(addday(a, addday), format);
	}

	// 活动是否已到期
	public static boolean compareDate(String src) {
		if (!"".equals(src) && null != src) {
			src += " 23:59:59";
		} else {
			return false;
		}
		Date endtime = null;
		Date now = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			endtime = sdf.parse(src);
			now = new Date();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now.compareTo(endtime) > 0;
	}

	/**
	 * 获取上月的的第一天和最后一天
	 * 
	 * @return
	 */
	public static String[] getLastMonth() {
		// 取得系统当前时间
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		// 取得系统当前时间所在月第一天时间对象
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// 日期减一,取得上月最后一天时间对象
		cal.add(Calendar.DAY_OF_MONTH, -1);
		// 输出上月最后一天日期
		int day = cal.get(Calendar.DAY_OF_MONTH);

		String months = "";
		String days = "";

		if (month > 1) {
			month--;
		} else {
			year--;
			month = 12;
		}
		if (!(String.valueOf(month).length() > 1)) {
			months = "0" + month;
		} else {
			months = String.valueOf(month);
		}
		if (!(String.valueOf(day).length() > 1)) {
			days = "0" + day;
		} else {
			days = String.valueOf(day);
		}
		String firstDay = "" + year + "-" + months + "-01";
		String lastDay = "" + year + "-" + months + "-" + days;

		String[] lastMonth = new String[2];
		lastMonth[0] = firstDay;
		lastMonth[1] = lastDay;
		return lastMonth;
	}

	public static String nowDateToStr() {
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
		Date date = new Date();
		return String.valueOf(date.getTime()); // sdf.format(date);
	}

}
