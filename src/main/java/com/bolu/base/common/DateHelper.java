package com.bolu.base.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateHelper {

	public static String formatstr = "yyyy-MM-dd";

	/**
	 * 取当前长日期格式字符串
	 * @author: pc
	 * @return
	 * @ctime 2018/12/4
	 */
	public static String getNowDateLongStr(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		
		String re = format.format(d);
		return re;
	}


	/**
	 * 日期格式转换成字符串
	 * @author: pc
	 * @param date
	 * @param formatstr
	 * @return
	 * @ctime 2018/12/4
	 */
	public static String getDateToStr(Date date, String formatstr) {
		SimpleDateFormat format = new SimpleDateFormat(formatstr);
		return format.format(date);
	}




	/**
	 * 取当前长日期格式时间
	 * @author: pc
	 * @return
	 * @throws ParseException
	 * @ctime 2018/12/4
	 */
	public static Date getNowDate() throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = format.parse(getNowDateLongStr());
		return date;
	}

	/**
	 * 距离现在多少天的日期
	 * @author: pc
	 * @param day
	 * @return
	 * @ctime 2018/12/4
	 */
	public static String getNowAddDate(int day) {  
        Calendar calendar = Calendar.getInstance();    
        calendar.add(Calendar.DATE,day);  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String yestoday = sdf.format(calendar.getTime());  
        return yestoday;  
	}

	/**
	 * 距离现在多少天的日期-短格式12-02
	 * @author: pc
	 * @param day
	 * @return
	 * @ctime 2018/12/4
	 */
	public static String getNowAddDateShort(int day) {  
        Calendar calendar = Calendar.getInstance();    
        calendar.add(Calendar.DATE,day);  
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");  
        String yestoday = sdf.format(calendar.getTime());  
        return yestoday;  
	}

	/**
	 * 距离现在多少天的日期("2016-01-01")
	 * @author: pc
	 * @param strday
	 * @param day
	 * @return
	 * @throws ParseException
	 * @ctime 2018/12/4
	 */
	public static   String getNowAddDate(String strday,int day) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date ddate = sdf.parse(strday);
		
        Calendar calendar = Calendar.getInstance();   
        calendar.setTime(ddate);
        calendar.add(Calendar.DATE,day);  
         
        String reday = sdf.format(calendar.getTime());  
        return reday;
	}

	/**
	 * 字符串转日期
	 *
	 * @author pc
	 * @param time
	 * @param formatstr
	 * @return
	 * @ctime 2019/4/23
	 */
	public static Date StringToDate(String time,String formatstr) throws ParseException {
		Date date = null;
		// 注意format的格式要与日期String的格式相匹配
		DateFormat dateFormat = new SimpleDateFormat(formatstr);
		try {
			date = dateFormat.parse(time);
			System.out.println(date.toString());
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @author: pc
	 * @description  描述 特定时间经过多少秒
	 * @author: pc
	 * @date: 2018/11/30 16:15
	 * @return
	 * @ctime 2018/12/4
	 */
	public static int getDatePast(Date d){
		Date nd = new Date();
		Calendar cn = Calendar.getInstance();
		Calendar cd = Calendar.getInstance();
		
		cn.setTime(nd);
		cd.setTime(d);
		
		long diff = cn.getTimeInMillis()-cd.getTimeInMillis();
		int re = (int) (diff/1000);
		
		return re;
	}
	

	/**
	 * @author: pc
	 * @description  描述 比较d1 d2大小，大于零d1大
	 * @author: pc
	 * @date: 2018/11/30 16:14
	 * @return
	 * @ctime 2018/12/4
	 */
	public static int CompareDate(Date d1,Date d2){
		Calendar cd1 = Calendar.getInstance();
		Calendar cd2 = Calendar.getInstance();
		
		cd1.setTime(d1);
		cd2.setTime(d2);
		
		long diff = cd1.getTimeInMillis()-cd2.getTimeInMillis();
		int re = (int) (diff/1000);
		
		return re;
	}

	/**
	 * @author: pc
	 * @description  描述 获取一周前的全部日期-2016-12-01
	 * @author: pc
	 * @date: 2018/11/30 16:14
	 * @return
	 * @ctime 2018/12/4
	 */
	public static List<String> getOneWeekAgoList(){
		List<String> dayList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i=-1; i>-7; i--) {
			Calendar calendar = Calendar.getInstance(); 
			calendar.add(Calendar.DATE,i); 
			String strday = sdf.format(calendar.getTime()); 
			dayList.add(strday);
		}
		
		return dayList;
	}
	

	/**
	 * 获取一周前的全部日期-2016-12-01------顺序排列
	 * @author pc
	 * @return
	 * @ctime 2018/12/4
	 */
	public static List<String> getOneWeekAgoAscList(){
		List<String> dayList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i=-6; i<0; i++) {
			Calendar calendar = Calendar.getInstance(); 
			calendar.add(Calendar.DATE,i); 
			String strday = sdf.format(calendar.getTime()); 
			dayList.add(strday);
		}
		
		return dayList;
	}

	/***
	 * 获取一周前的全部日期-短日期 12-01 ----顺序排列
	 * @author: pc
	 * @return
	 * @ctime: 2018/12/4
	 */
	public static List<String> getOneWeekAgoShortList(){
		List<String> dayList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		for (int i=-6; i<0; i++) {
			Calendar calendar = Calendar.getInstance(); 
			calendar.add(Calendar.DATE,i); 
			String strday = sdf.format(calendar.getTime()); 
			dayList.add(strday);
		}
		
		return dayList;
	}


	/**
	 * 描述 获取一周或一个月的开始与结束日期,type： 1 周 ，其它 月
	 * @author: pc
	 * @date: 2018/11/30 16:13
	 * @return
	 * @ctime 2018/12/4
	 */
	public static List<String> getSEDay(int type,int addnum){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		
		List<String> re = new ArrayList<String>();
		if(type==1){
			Calendar cal =Calendar.getInstance();
			cal.add(Calendar.WEEK_OF_MONTH, addnum);
			
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			String start = df.format(cal.getTime());
			
			cal.add(Calendar.DATE,7);
			String end = df.format(cal.getTime());
			
			re.add(start);
			re.add(end);
		}
		else {
			Calendar cal =Calendar.getInstance();
			cal.add(Calendar.MONTH, addnum);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			String start = df.format(cal.getTime());
			
			cal.add(Calendar.MONTH,1);
			String end = df.format(cal.getTime());
			re.add(start);
			re.add(end);
		}
		
		return re;
	}

	/**
	 * @description  描述 获取上期时间：即上上周五到上周五
	 * @author: pc
	 * @date: 2018/11/30 16:13
	 * @return
	 * @ctime 2018/12/4
	 */
	public static List<String> getQiSEDay(){
		List<String> re = new ArrayList<String>();
		
		Calendar calendar = Calendar.getInstance(); 
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) { 
            calendar.add(Calendar.DAY_OF_WEEK, -1); 
        } 
        
        int n = -2;	//n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
        calendar.add(Calendar.DATE, n*7);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        String lastfriday = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        re.add(lastfriday);
        
        n = -1;
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, n*7);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        String llastfriday = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        re.add(llastfriday);
        
        return re;
	}
	
	/**
     * 获取当前日期是星期几
	 * @author: pc
     * @param dt
     * @return 当前日期是星期几
	 * @ctime 2018/12/4
     */
    public static String getWeekOfDate(Date dt) {
    	/*
    	 星期天Sunday 缩写Sun
    	 星期一Monday 缩写Mon
    	星期二Tuesday 缩写Tues
    	星期三Wednesday 缩写Wed
    	星期四Thurday 缩写Thur
    	星期五Friday 缩写Fri
    	星期六Saturday 缩写Sat
    	*/
        String[] weekDays = {"sun", "mon", "tues", "wed", "thur", "fri", "sat"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
    /**
     * 通过时间秒毫秒数判断两个时间的间隔
	 * @author: pc
     * @param date1
     * @param date2
     * @return
	 * @ctime 2018/12/4
     */
    public static int getDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

	/****
	 * 获取 某日期 之前的多少 天的日期
	 * @author: pc
	 * @param date
	 * @param day
	 * @return
	 * @ctime 2018/12/4
	 */
	public static Date getBeforeDay(Date date,Integer day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -day);
		date = calendar.getTime();
		return date;
	}

	/****
	 * 获取 某日期 之后的多少 天的日期
	 * @author: pc
	 * @param date
	 * @param day
	 * @return
	 * @ctime 2018/12/4
	 */
	public static Date getNextDay(Date date,Integer day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +day);
		date = calendar.getTime();
		return date;
	}

	/****
	 *
	 * 某日期，加分钟
	 * @author: pc
	 * @param date
	 * @param minute
	 * @return
	 * @ctime 2018/12/4
	 */
	public static Date addNextMinutes(Date date,Integer minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 得到当前时间的前N小时
	 * 
	 * @param ihour
	 * @return
	 */
	public static String getBeforeByHourTime(int ihour){
		String returnstr = "";
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - ihour);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		returnstr = df.format(calendar.getTime());
		return returnstr;
	}

}
