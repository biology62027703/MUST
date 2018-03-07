/*
**********************************************************
* Author             : nono
* Finished Date      :
* Description        : dtutil.java
**********************************************************
* Modified By        : nono
* Last Modified Date : 2003/7/18
* Description        : 新增加 DateAdd, CDateAdd, DateDiff, CDateDiff四組
**********************************************************
* Modified By        : wenwen
* Last Modified Date : 2003/7/30
* Description        : 新增加 getNowCYear 取系統年
**********************************************************
* Modified By        : wenwen
* Last Modified Date : 2003/8/27
* Description        : 新增加 formatTime 格式化時間(mm:ss)
**********************************************************
* Modified By        : nono
* Last Modified Date : 2003/9/16
* Description        : 新增取得星期的中文
**********************************************************
*/

package com.sertek.util;

import java.util.*;
import java.text.*;

public class dtutil
{
	private	static	String	className	= "com.sertek.util.dtutil";

	/**
	用來測試用
	*/
	public static void main(String[] args) throws Exception
	{
		try
		{
			int[]	xx	= CDateDiff("0860513", "0920229");
			System.out.println(xx[0]);
			System.out.println(xx[1]);
			System.out.println(xx[2]);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".main Exception: \r\n" + err.toString());
		}
	}

	/**
	傳回內容為某個基準七碼中文日期加上數個時間間隔單位後的日期。 Exp: dtutil.CDateAdd("w", 1, "920228") --> 920307
	@param	interval	種類 d - 日, w - 星期, m - 月, y - 年
	@param	number		相加的數目
	@param	sdate		字串型態日期
	@return	比較後的時間差
	*/
	public static String CDateAdd(String interval, int number, String sdate) throws Exception
  	{
  		try
		{
			return dtutil.formatDate(dtutil.DateAdd(interval, number, dtutil.formatCDate(sdate, "yyyy/MM/dd")));
		}
		catch(Exception err)
		{
			throw new Exception(className + ".CDateAdd Exception: \r\n" + err.toString());
		}
   	}

	/**
	傳回內容為某個基準日期加上數個時間間隔單位後的日期。 Exp: dtutil.DateAdd("w", 1, "2003/2/28") --> 2003/3/7
	@param	interval	種類 d - 日, w - 星期, m - 月, y - 年
	@param	number		相加的數目
	@param	sdate		字串型態日期
	@return	比較後的時間差
	*/
	public static String DateAdd(String interval, int number, String sdate) throws Exception
  	{
  		try
		{
			SimpleDateFormat	df	= new SimpleDateFormat("yyyy/MM/dd");
			Date			_date	= null;

			try
			{
				_date	= df.parse(sdate);
			}
			catch (ParseException err)
			{
				throw new Exception(className + ".DateAdd Exception: 日期型態轉換失敗!! Date:" + sdate);
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(_date);
			if (interval.equals("d"))
				cal.add(Calendar.DAY_OF_YEAR, number);
			else if (interval.equals("w"))
				cal.add(Calendar.WEEK_OF_YEAR , number);
			else if (interval.equals("m"))
				cal.add(Calendar.MONTH, number);
			else if (interval.equals("y"))
				cal.add(Calendar.YEAR, number);
			else
				throw new Exception(className + ".DateAdd Exception: 參數傳入錯誤: interval: " + interval);

			return df.format(cal.getTime());
		}
		catch(Exception err)
		{
			throw new Exception(className + ".DateAdd Exception: \r\n" + err.toString());
		}
   	}

	/**
	傳回兩個七碼中文日期間相差的時間 Exp: dtutil.CDateDiff("0920501", "0920502") --> [0][0][1]
	@param	sdate1		字串型態日期一
	@param	sdate2		字串型態日期二
	@return	比較後的時間差陣列
	*/
   	public static int[] CDateDiff(String sdate1, String sdate2 ) throws Exception
  	{
  		try
		{
			String	tmpDate		= "";
			int[]	returnAry	= new int[3];
			//取得年差
			returnAry[0]	= CDateDiff("y", sdate1, sdate2);
			//扣除年差
			tmpDate	= CDateAdd("y", returnAry[0], sdate1);
			//取得月差
			returnAry[1]	= CDateDiff("m", tmpDate, sdate2);

			if (returnAry[1] < 0)
			{
				//取得年差
				returnAry[0]	= CDateDiff("y", sdate1, sdate2) - 1;

				//扣除年差
				tmpDate	= CDateAdd("y", returnAry[0], sdate1);

				//取得月差
				returnAry[1]	= CDateDiff("m", tmpDate, sdate2);

				//扣除月差
				tmpDate	= CDateAdd("m", returnAry[1], tmpDate);
			}
			else
			{
				//扣除月差
				tmpDate	= CDateAdd("m", returnAry[1], tmpDate);
			}

			//取得日差
			returnAry[2]	= CDateDiff("d", tmpDate, sdate2);

			if (returnAry[2] < 0)
			{
				//取得月差
				returnAry[1]	= CDateDiff("m", tmpDate, sdate2) - 1;

				if (returnAry[1] < 0)
				{
					//取得年差
					returnAry[0]	= CDateDiff("y", sdate1, sdate2) - 1;

					//扣除年差
					tmpDate	= CDateAdd("y", returnAry[0], sdate1);

					//取得月差
					returnAry[1]	= CDateDiff("m", tmpDate, sdate2) - 1;

					//扣除月差
					tmpDate	= CDateAdd("m", returnAry[1], tmpDate);
				}
				else
				{
					//扣除月差
					tmpDate	= CDateAdd("m", returnAry[1], tmpDate);
				}

				//取得日差
				returnAry[2]	= CDateDiff("d", tmpDate, sdate2);

				//扣除日差
				tmpDate	= CDateAdd("d", returnAry[2], tmpDate);
			}
			else
			{
				//扣除日差
				tmpDate	= CDateAdd("d", returnAry[2], tmpDate);
			}

			return returnAry;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".CDateDiff(String, String) Exception: \r\n" + err.toString());
		}
   	}

   	/**
	傳回兩個日期間相差的時間 Exp: dtutil.DateDiff("2003/5/1", "2003/5/2") --> [0][0][1]
	@param	sdate1		字串型態日期一
	@param	sdate2		字串型態日期二
	@return	比較後的時間差陣列
	*/
   	public static int[] DateDiff(String sdate1, String sdate2) throws Exception
  	{
  		try
		{
			String	tmpDate		= "";
			int[]	returnAry	= new int[3];
			//取得年差
			returnAry[0]	= DateDiff("y", sdate1, sdate2);
			//扣除年差
			tmpDate	= DateAdd("y", returnAry[0], sdate1);
			//取得月差
			returnAry[1]	= DateDiff("m", tmpDate, sdate2);
			//扣除月差
			tmpDate	= DateAdd("m", returnAry[1], tmpDate);
			//取得日差
			returnAry[2]	= DateDiff("d", tmpDate, sdate2);
			//扣除日差
			tmpDate	= DateAdd("d", returnAry[2], tmpDate);

			return returnAry;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".DateDiff(String, String) Exception: \r\n" + err.toString());
		}
   	}

	/**
	傳回兩個七碼中文日期間相差的時間間隔單位數目 Exp: dtutil.CDateDiff("d", "0920501", "0920502") --> 1
	@param	interval	比較種類 d - 日, w - 星期, m - 月, y - 年
	@param	sdate1		字串型態日期一
	@param	sdate2		字串型態日期二
	@return	比較後的時間差
	*/
   	public static int CDateDiff(String interval, String sdate1, String sdate2 ) throws Exception
  	{
  		try
		{
			return dtutil.DateDiff(interval, dtutil.formatCDate(sdate1, "yyyy/MM/dd"), dtutil.formatCDate(sdate2, "yyyy/MM/dd"));
		}
		catch(Exception err)
		{
			throw new Exception(className + ".CDateDiff Exception: \r\n" + err.toString());
		}
   	}

	/**
	傳回兩個日期間相差的時間間隔單位數目 Exp: dtutil.DateDiff("d", "2003/5/1", "2003/5/2") --> 1
	@param	interval	比較種類 d - 日, w - 星期, m - 月, y - 年
	@param	sdate1		字串型態日期一
	@param	sdate2		字串型態日期二
	@return	比較後的時間差
	*/
   	public static int DateDiff(String interval, String sdate1, String sdate2 ) throws Exception
  	{
  		try
		{
			SimpleDateFormat	df	= new SimpleDateFormat("yyyy/MM/dd");
			Date			date1	= null;
			Date			date2	= null;

			try
			{
				date1	= df.parse(sdate1);
				date2	= df.parse(sdate2);
			}
			catch (ParseException err)
			{
				throw new Exception(className + ".DateDiff Exception: 日期型態轉換失敗!! Date1:" + sdate1 + ", Date2:" + sdate2);
			}

			Calendar	cal1	= Calendar.getInstance();
			Calendar	cal2	= Calendar.getInstance();

			// different date might have different offset
			cal1.setTime(date1);
			long	ldate1	= date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);

			cal2.setTime(date2);
			long	ldate2	= date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);

			// Use integer calculation, truncate the decimals
			int	hr1	= (int)(ldate1 / 3600000); //60*60*1000
       			int	hr2	= (int)(ldate2 / 3600000);
			int	days1	= (int)hr1 / 24;
			int	days2	= (int)hr2 / 24;

			int	dateDiff	= days2 - days1;
			int	weekOffset	= (cal2.get(Calendar.DAY_OF_WEEK) - cal1.get(Calendar.DAY_OF_WEEK)) < 0 ? 1 : 0;
			int	weekDiff	= dateDiff / 7 + weekOffset;
			int	yearDiff	= cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
			int	monthDiff	= yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);

			if (interval.equals("d"))
				return dateDiff;
			else if (interval.equals("w"))
				return weekDiff;
			else if (interval.equals("m"))
				return monthDiff;
			else if (interval.equals("y"))
				return yearDiff;
			else
				throw new Exception(className + ".DateDiff Exception: 參數傳入錯誤: interval: " + interval);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".DateDiff Exception: \r\n" + err.toString());
		}
   	}

	/**
	取得系統時間，格式為 hhmm
	@return		hhmm
	*/
   	public static String getNowTime() throws Exception
  	{
  		try
		{
			Calendar	cal	= Calendar.getInstance();
			String		result	= "";
			String		hour	= util.fillStr(new Integer(cal.get(Calendar.HOUR_OF_DAY)).toString(), 2, '0');
			String		minute	= util.fillStr(new Integer(cal.get(Calendar.MINUTE)).toString(), 2, '0');
			//String		second	= util.fillStr(new Integer(cal.get(Calendar.SECOND)).toString(), 2, '0');

			return  hour + minute;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".getNowTime Exception: \r\n" + err.toString());
		}
   	}

   	/**
	取得系統時間，格式為 hhmmss
	@return		hhmmss
	*/
   	public static String getNowTime1() throws Exception
  	{
  		try
		{
			Calendar	cal	= Calendar.getInstance();
			String		result	= "";
			String		hour	= util.fillStr(new Integer(cal.get(Calendar.HOUR_OF_DAY)).toString(), 2, '0');
			String		minute	= util.fillStr(new Integer(cal.get(Calendar.MINUTE)).toString(), 2, '0');
			String		second	= util.fillStr(new Integer(cal.get(Calendar.SECOND)).toString(), 2, '0');

			return  hour + minute + second;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".getNowTime1 Exception: \r\n" + err.toString());
		}
   	}

	/**
	取得系統時間到微秒，格式為 hhmmssms
	@return		hhmmssms
	*/
   	public static String getNowTimeMs() throws Exception
  	{
  		try
		{
			Calendar	cal	= Calendar.getInstance();
			String		result	= "";
			String		hour	= util.fillStr(new Integer(cal.get(Calendar.HOUR_OF_DAY)).toString(), 2, '0');
			String		minute	= util.fillStr(new Integer(cal.get(Calendar.MINUTE)).toString(), 2, '0');
			String		second	= util.fillStr(new Integer(cal.get(Calendar.SECOND)).toString(), 2, '0');
			String		msecond	= util.fillStr(new Integer(cal.get(Calendar.MILLISECOND)).toString(), 3, '0');

			return  hour + minute + second + msecond;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".getNowTimeMs Exception: \r\n" + err.toString());
		}
   	}

	/**
	取得系統日期，格式為西元日期
	@param		無
	@return		西元日期yyyy/mm/dd
	*/
   	public static String getNowDate() throws Exception
  	{
  		try
		{
			SimpleDateFormat	formatter	= new SimpleDateFormat ("yyyy/MM/dd", Locale.US);
			Date			currentTime	= new Date();
			return formatter.format(currentTime);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".getNowDate Exception: \r\n" + err.toString());
		}
   	}

   	/**
	取得系統日期，格式為七碼中文日期.
	@return	七碼中文日期yyymmdd
	*/
   	public static String getNowCDate() throws Exception
  	{
  		try
		{
			return dtutil.formatDate(dtutil.getNowDate());
		}
		catch(Exception err)
		{
			throw new Exception(className + ".getNowCDate Exception: \r\n" + err.toString());
		}
   	}

   	/**
	將西元日期格式化為七碼中文日期
	@param	_date 西元日期
	@return	七碼中文日期yyymmdd
	*/
   	public static String formatDate(String _date) throws Exception
   	{
	      	try
	      	{
	      		if (_date.length() == 0)
	      			return _date;
	      		else
	      		{
		      		String[]	strAry	= util.split(_date, "/");
		      		String		year	= util.fillStr(Integer.toString(Integer.parseInt(strAry[0]) - 1911), 3, '0');
		      		String		month	= util.fillStr(strAry[1], 2, '0');
		      		String		day	= util.fillStr(strAry[2], 2, '0');

		      		return	year + month + day;
		      	}
	      	}
	      	catch(Exception err)
	      	{
	      		throw new Exception(className + ".formatCDate Exception: \r\n" + err.toString());
	      	}
   	}

   	/**
	將七碼中文日期格式化為西元日期
	@param	_date 		七碼中文日期
	@param	formatType	格式化種類
	@return	西元日期
	*/
   	public static String formatCDate(String _date, String formatType) throws Exception
   	{
	      	try
	      	{
	      		String			year		= Integer.toString(Integer.parseInt(_date.substring(0, 3)) + 1911);
	      		String			month		= _date.substring(3, 5);
	      		String			day		= _date.substring(5);

	      		SimpleDateFormat	df		= new SimpleDateFormat(formatType);
			Date			tnansDate	= df.parse(year + "/" + month + "/" + day);

			return df.format(tnansDate);
	      	}
	      	catch(Exception err)
	      	{
	      		throw new Exception(className + ".formatCDate(String, String) Exception: \r\n" + err.toString());
	      	}
   	}

   	/**
	將七碼中文日期格式化
	@param	_date 		七碼中文日期
	@return	中文日期 yyy/MM/dd
	*/
   	public static String formatCDate(String _date) throws Exception
   	{
	      	try
	      	{
	      		if (_date.length() < 7)
	      			return _date;
	      		else
	      		{
		      		String			year		= _date.substring(0, 3);
		      		String			month		= _date.substring(3, 5);
		      		String			day		= _date.substring(5);
		      		String			currentTime	= year + "/" + month + "/" + day;
				return currentTime;
			}
	      	}
	      	catch(Exception err)
	      	{
	      		throw new Exception(className + ".formatCDate(String) Exception: \r\n" + err.toString());
	      	}
   	}

  	/**
	取得系統年，格式為三碼中文年.
	@return	三碼中文年yyy
	*/
   	public static String getNowCYear() throws Exception
  	{
  		try
		{
			String getNowCYear = dtutil.getNowCDate().substring(0,3);

			return getNowCYear;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".getNowCYear Exception: \r\n" + err.toString());
		}
   	}

   	/**
	將四碼中文時間格式化
	@param	_time 		四碼中文時間
	@return	中文日期 mm:ss
	*/
   	public static String formatTime(String _time) throws Exception
   	{
	      	try
	      	{
	      		if (_time.length() == 0)
	      			return _time;
	      		else
	      		{
		      		String			hh		= _time.substring(0, 2);
		      		String			mm		= _time.substring(2, 4);
		      		String			currentTime	= hh + ":" + mm;
				return currentTime;
			}
	      	}
	      	catch(Exception err)
	      	{
	      		throw new Exception(className + ".formatTime(String) Exception: \r\n" + err.toString());
	      	}
   	}

	/**
	抓取七碼中文日期的星期
	@param	_date 		七碼中文日期
	@return	一 ~ 日
	*/
   	public static String getCWeek(String _date) throws Exception
   	{
	      	try
	      	{
	      		String			year		= Integer.toString(Integer.parseInt(_date.substring(0, 3)) + 1911);
	      		String			month		= _date.substring(3, 5);
	      		String			day		= _date.substring(5);

			SimpleDateFormat	df		= new SimpleDateFormat("yyyy/MM/dd");
	      		Date			tnansDate	= df.parse(year + "/" + month + "/" + day);

			String			resultStr	= "";
			switch (tnansDate.getDay())
			{
				case 1:
					resultStr	= "一";
					break;
				case 2:
					resultStr	= "二";
					break;
				case 3:
					resultStr	= "三";
					break;
				case 4:
					resultStr	= "四";
					break;
				case 5:
					resultStr	= "五";
					break;
				case 6:
					resultStr	= "六";
					break;
				case 0:
					resultStr	= "日";
					break;
				default:
					resultStr	= "";
					break;
			}
			return resultStr;
	      	}
	      	catch(Exception err)
	      	{
	      		throw new Exception(className + ".formatCDate(String, String) Exception: \r\n" + err.toString());
	      	}
   	}
}