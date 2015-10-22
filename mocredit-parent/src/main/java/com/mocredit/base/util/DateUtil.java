package com.mocredit.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 常用时间处理方法
 * @author lishoukun
 * @date 2013/09/13
 */
public class DateUtil {

	/**
	  * 获取当前时间字符串
	  *  时间格式 yyyy-MM-dd
	  * @return
	  */
	public static String getCurDate(){
		return DateUtil.getCurDate("yyyy-MM-dd");
	}
	/**
	  * 获取当前时间字符串
	  *  时间格式 yyyy-MM-dd HH:mm:ss
	  * @return
	  */
	public static String getLongCurDate(){
		return DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 根据指定格式获取当前时间字符串
	 * @param format
	 * @return
	 */
	public static String getCurDate(String format){
		if( format == null ){
			return null;
		}
		return dateToStr(new Date(),format);
	}
	
	public static int getYear(){
		int year = 1900;
		Calendar date = Calendar.getInstance();
		year = date.get(Calendar.YEAR);
		return year;
	}
	
	/**
	 * 根据指定格式将日期转为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToStr(Date date, String format){
		if( date == null || format == null ){
			return "";
		}
		SimpleDateFormat simple = new SimpleDateFormat(format);
		return simple.format(date);
	}
	
	/**
	 * 将格式为(yyyy-MM-dd HH:mm:ss)的字串转换为日期
	 * @param date 日期字符串
	 * @return 日期类型的日期
	 */
	public static Date strToDate(String date) {
		return DateUtil.strToDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 将指定格式的字符串转换为日期
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date strToDate(String date,String format ){
		if( date == null || format == null ){
			return null;
		}
		DateFormat formater = new SimpleDateFormat(format);
		try {
			return formater.parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 将指定格式的字符串转换为日期
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date strToDate(Object date,String format ){
		String dateStr = String.valueOf(date);
		return strToDate(dateStr,format);
	}
	
	public static String formatStr(String date,String format){
		Date d = strToDate(date,format);
		return dateToStr(d,format);
	}
	
	/**
	 * 得到一天的开始时间
	 * 	如 2012-04-12 17:23:01 返回 2012-04-12 00:00:00
	 * @param currentDate
	 * @return
	 */
	public static Date getStartDate(Date currentDate) {
		final Calendar minCalendar = Calendar.getInstance();
		minCalendar.setTime(currentDate);
		minCalendar.set(Calendar.HOUR_OF_DAY, 0);
		minCalendar.set(Calendar.MINUTE, 0);
		minCalendar.set(Calendar.SECOND, 0);
		minCalendar.set(Calendar.MILLISECOND, 0);
		return minCalendar.getTime();
	}
	/**
	 * 获取之前的时间
	 * @param currentDate
	 * @return
	 */
	public static List getBeforeTimeList(String type,String theDate,String theFormat) {
		List timeList = new ArrayList(); 
		
		Calendar nowDate = Calendar.getInstance();
		nowDate.setTime(strToDate(theDate,theFormat));
		if("day".equals(type)){
			int defaultCount = 7;
			String defaultFormat = "yyyy-MM-dd";
			String defaultOrder = "asc";
			nowDate.set(Calendar.DAY_OF_MONTH, nowDate.get(Calendar.DAY_OF_MONTH)-7);
			for(int i =0;i<defaultCount;i++){
				nowDate.set(Calendar.DAY_OF_MONTH, nowDate.get(Calendar.DAY_OF_MONTH)+1);
				timeList.add(dateToStr(nowDate.getTime(),defaultFormat));
			}
		}else if("month".equals(type)){
			int defaultCount = 12;
			String defaultFormat = "yyyy-MM";
			String defaultOrder = "asc";
			nowDate.set(Calendar.MONTH, nowDate.get(Calendar.MONTH)-12);
			for(int i =0;i<defaultCount;i++){
				nowDate.set(Calendar.MONTH, nowDate.get(Calendar.MONTH)+1);
				timeList.add(dateToStr(nowDate.getTime(),defaultFormat));
			}
		}else if("year".equals(type)){
			int defaultCount = 5;
			String defaultFormat = "yyyy-MM";
			String defaultOrder = "asc";
			nowDate.set(Calendar.YEAR, nowDate.get(Calendar.YEAR)-5);
			for(int i =0;i<defaultCount;i++){
				nowDate.set(Calendar.YEAR, nowDate.get(Calendar.YEAR)+1);
				timeList.add(dateToStr(nowDate.getTime(),defaultFormat));
			}
		}
		return timeList;
	}
	/**
	 * 获取同比时间
	 * @param currentDate
	 * @return
	 */
	public static List getCompareTimeList(Map<String,String> paramMap) {
		String compareType = null;
		if(paramMap.get("compareType")!=null){
			compareType = String.valueOf(paramMap.get("compareType"));
		}
		String type = null;
		if(paramMap.get("type")!=null){
			type = String.valueOf(paramMap.get("type"));
		}
		String theDate = null;
		if(paramMap.get("theDate")!=null){
			theDate = String.valueOf(paramMap.get("theDate"));
		}
		String theFormat = null;
		if(paramMap.get("theFormat")!=null){
			theFormat = String.valueOf(paramMap.get("theFormat"));
		}
		int offset = 0;
		if(paramMap.get("offset")!=null){
			offset = Integer.valueOf(String.valueOf(paramMap.get("offset")));
		}
		List timeList = new ArrayList(); 
		Calendar nowDate = Calendar.getInstance();
		nowDate.setTime(strToDate(theDate,theFormat));
		if("year".equals(compareType)){
			if("day".equals(type)){
				int defaultCount = 5;
				String defaultFormat = "yyyy-MM-dd";
				String defaultOrder = "asc";
				nowDate.set(Calendar.DAY_OF_MONTH, nowDate.get(Calendar.DAY_OF_MONTH)+offset);
				nowDate.set(Calendar.YEAR, nowDate.get(Calendar.YEAR)-defaultCount);
				for(int i =0;i<defaultCount;i++){
					nowDate.set(Calendar.YEAR, nowDate.get(Calendar.YEAR)+1);
					timeList.add(dateToStr(nowDate.getTime(),defaultFormat));
				}
			}else if("month".equals(type)){
				int defaultCount = 5;
				String defaultFormat = "yyyy-MM";
				String defaultOrder = "asc";
				nowDate.set(Calendar.MONTH, nowDate.get(Calendar.MONTH)+offset);
				nowDate.set(Calendar.YEAR, nowDate.get(Calendar.YEAR)-defaultCount);
				for(int i =0;i<defaultCount;i++){
					nowDate.set(Calendar.YEAR, nowDate.get(Calendar.YEAR)+1);
					timeList.add(dateToStr(nowDate.getTime(),defaultFormat));
				}
			}
		}
		return timeList;
	}
	/**
	 * 得到一天的截止时间
	 *  如 2012-04-12 17:23:01 返回 2012-04-12 59:59:59
	 * @param currentDate
	 * @return
	 */
	public static Date getEndDate(Date currentDate) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	/**
	 * 得到本月最后一天
	 * @param date
	 * @return
	 */
	public static Date lastDayOfMonth(Date date) {
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       cal.set(Calendar.DAY_OF_MONTH, 1);
       cal.add(Calendar.MONTH, 1);
       cal.add(Calendar.DATE, -1);
       return cal.getTime();
   }
	
	/**
	 * 得到下一天
	 *  如 2012-04-12 17:23:01 返回 2012-04-13 17:23:01
	 * @param cdate
	 * @return
	 */
	public static Date nextDate(Date cdate) {
		Date date = null;

		Calendar cd = Calendar.getInstance();
		cd.setTime(cdate);
		cd.add(Calendar.DATE, 1);
		date = cd.getTime();

		return date;
	}
	/**
	 * 得到下一天
	 * @param cdate
	 * @param format
	 * @return
	 */
	public static Date nextDate(String cdate,String format) {
		Date date = null;

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cd = Calendar.getInstance();
		try {
			cd.setTime(sdf.parse(cdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cd.add(Calendar.DATE, 1);
		date = cd.getTime();

		return date;
	}
	
	/**
	 * 得到下一个时间点
	 * @param nextType 1天   2月  3年   
	 * @param date 当前日期
	 * @param amount 变更的数量
	 * @return
	 */
	public static Date nextDate(Date cdate,int nextType,int amount){
		Date date = null;

		Calendar cd = Calendar.getInstance();
		cd.setTime(cdate);
		
		if( nextType == 1 )
			cd.add(Calendar.DATE, amount);		//天
		else if( nextType == 2 )
			cd.add(Calendar.MONTH, amount); 	//月
		else if( nextType == 3 )
			cd.add(Calendar.YEAR, amount);		//年
			
		date = cd.getTime();

		return date;
	}
	
	/**
	 * 比较日期前后
	 *  如果date1在date2之前 返回1，date1==date2 返回0，date1在date2之后 返回-1
	 * @param
	 * @return
	 */
	public static int compareDate(Date date1, Date date2) {
		if (date1.after(date2)) {
			return -1;
		} else if (date1.before(date2)) {
			return  1;
		} else {
			return 0;
		}
	}
	
	/**
	 * 是否同一天
	 * 	不考虑时间  仅判断日期
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isEqualsDate(Date d1, Date d2) {
		String sd1 = new SimpleDateFormat("yyyy-MM-dd").format(d1);
		String sd2 = new SimpleDateFormat("yyyy-MM-dd").format(d2);
		if (sd1.equals(sd2)) {
			return true;
		} else {
			return false;
		}
	}
}
