/**
 * 
 */
package com.wxj.test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**  
* @ClassName: DateUtils  
* @Description: TODO 日期工具类
* @author: wxj  
* @date: 2019年2月1日
* @Tel:18772118541
* @email:18772118541@163.com
*/
public class DateUtils {
	/**
	 * @Title: getCurrentMonthDays
	 * @Description: TODO 获取当前月份的天数
	 * @return int
	 * @date:2018-10-08 14:16
	 */
	public static int getCurrentMonthDays() {
		int month = getCurrentMonth();
		boolean flg = isLeapYear();
		int day = getDaysByMonth(month,flg);
		return day;
	}
	
	/**  
	* @Title: getDaysByMonth  
	* @Description: TODO 根据月，和年份获取天数
	* @param @param month
	* @param @param flg年是不回为闰年
	* @param @return   
	* @return int
	* @date:2018-10-08 14:52
	*/
	public static int getDaysByMonth(int month,boolean flg) {
		int day = 30;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			day = 30;
			break;
		case 2: 
			if(flg) {
				day = 28;
			}else {
				day = 29;
			}
			break;
		}
		return day;
	}
	/**
	 * @Title: isLeapYear
	 * @Description: TODO 当前年是否为闰年
	 * @param @return
	 * @return boolean
	 * @date:2018-10-08 14:35
	 */
	public static boolean isLeapYear() {
		int year = getCurrentYear();
		if (year % 4 == 0 && year % 100 != 0) {
			return true;
		} else if (year % 400 == 0) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: getCurrentMonth
	 * @Description: TODO 获取当前月
	 * @param @return
	 * @return String
	 * @date:2018-10-08 14:18
	 */
	public static int getCurrentMonth() {
		return Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
	}
	/**  
	* @Title: getCurrentMonthYear  
	* @Description: TODO 获取当前年
	* @param @return   
	* @return int
	* @date:2018-10-08 15:01
	*/
	public static int getCurrentYear() {
		return Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
	}

	/**  
	* @Title: getCurrentDate  
	* @Description: TODO 获取当前日期
	* @return   
	* @return String
	* @date:2018-10-10 12:39
	*/
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	/**  
	* @Title: geCurrentDateByFormat  
	* @Description: TODO 根据格式获取当前日期
	* @param format
	* @return   
	* @return String
	* @date:2018-10-10 13:49
	*/
	public static String geCurrentDateByFormat(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
	
	/**  
	* @Title: getDateByDateAndFormat  
	* @Description: TODO 根据传入的日期和格式获取一个日期
	* @param format
	* @param date
	* @return   
	* @date:2018-11-15 11:39
	*/
	public static String getDateByDateAndFormat(Date date,String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return new SimpleDateFormat(format).format(date);
	}
	/**  
	* @Title: pastHour  
	* @Description: TODO  获取过去的小时数
	* @param date
	* @return   
	* @date:2019-02-01 10:12
	*/
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 获取两个日期之间的天数
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
}
