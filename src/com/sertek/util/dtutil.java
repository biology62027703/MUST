/*
**********************************************************
* Author             : nono
* Finished Date      :
* Description        : dtutil.java
**********************************************************
* Modified By        : nono
* Last Modified Date : 2003/7/18
* Description        : �s�W�[ DateAdd, CDateAdd, DateDiff, CDateDiff�|��
**********************************************************
* Modified By        : wenwen
* Last Modified Date : 2003/7/30
* Description        : �s�W�[ getNowCYear ���t�Φ~
**********************************************************
* Modified By        : wenwen
* Last Modified Date : 2003/8/27
* Description        : �s�W�[ formatTime �榡�Ʈɶ�(mm:ss)
**********************************************************
* Modified By        : nono
* Last Modified Date : 2003/9/16
* Description        : �s�W���o�P��������
**********************************************************
*/

package com.sertek.util;

import java.util.*;
import java.text.*;

public class dtutil
{
	private	static	String	className	= "com.sertek.util.dtutil";

	/**
	�ΨӴ��ե�
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
	�Ǧ^���e���Y�Ӱ�ǤC�X�������[�W�ƭӮɶ����j���᪺����C Exp: dtutil.CDateAdd("w", 1, "920228") --> 920307
	@param	interval	���� d - ��, w - �P��, m - ��, y - �~
	@param	number		�ۥ[���ƥ�
	@param	sdate		�r�ꫬ�A���
	@return	����᪺�ɶ��t
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
	�Ǧ^���e���Y�Ӱ�Ǥ���[�W�ƭӮɶ����j���᪺����C Exp: dtutil.DateAdd("w", 1, "2003/2/28") --> 2003/3/7
	@param	interval	���� d - ��, w - �P��, m - ��, y - �~
	@param	number		�ۥ[���ƥ�
	@param	sdate		�r�ꫬ�A���
	@return	����᪺�ɶ��t
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
				throw new Exception(className + ".DateAdd Exception: ������A�ഫ����!! Date:" + sdate);
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
				throw new Exception(className + ".DateAdd Exception: �ѼƶǤJ���~: interval: " + interval);

			return df.format(cal.getTime());
		}
		catch(Exception err)
		{
			throw new Exception(className + ".DateAdd Exception: \r\n" + err.toString());
		}
   	}

	/**
	�Ǧ^��ӤC�X���������ۮt���ɶ� Exp: dtutil.CDateDiff("0920501", "0920502") --> [0][0][1]
	@param	sdate1		�r�ꫬ�A����@
	@param	sdate2		�r�ꫬ�A����G
	@return	����᪺�ɶ��t�}�C
	*/
   	public static int[] CDateDiff(String sdate1, String sdate2 ) throws Exception
  	{
  		try
		{
			String	tmpDate		= "";
			int[]	returnAry	= new int[3];
			//���o�~�t
			returnAry[0]	= CDateDiff("y", sdate1, sdate2);
			//�����~�t
			tmpDate	= CDateAdd("y", returnAry[0], sdate1);
			//���o��t
			returnAry[1]	= CDateDiff("m", tmpDate, sdate2);

			if (returnAry[1] < 0)
			{
				//���o�~�t
				returnAry[0]	= CDateDiff("y", sdate1, sdate2) - 1;

				//�����~�t
				tmpDate	= CDateAdd("y", returnAry[0], sdate1);

				//���o��t
				returnAry[1]	= CDateDiff("m", tmpDate, sdate2);

				//������t
				tmpDate	= CDateAdd("m", returnAry[1], tmpDate);
			}
			else
			{
				//������t
				tmpDate	= CDateAdd("m", returnAry[1], tmpDate);
			}

			//���o��t
			returnAry[2]	= CDateDiff("d", tmpDate, sdate2);

			if (returnAry[2] < 0)
			{
				//���o��t
				returnAry[1]	= CDateDiff("m", tmpDate, sdate2) - 1;

				if (returnAry[1] < 0)
				{
					//���o�~�t
					returnAry[0]	= CDateDiff("y", sdate1, sdate2) - 1;

					//�����~�t
					tmpDate	= CDateAdd("y", returnAry[0], sdate1);

					//���o��t
					returnAry[1]	= CDateDiff("m", tmpDate, sdate2) - 1;

					//������t
					tmpDate	= CDateAdd("m", returnAry[1], tmpDate);
				}
				else
				{
					//������t
					tmpDate	= CDateAdd("m", returnAry[1], tmpDate);
				}

				//���o��t
				returnAry[2]	= CDateDiff("d", tmpDate, sdate2);

				//������t
				tmpDate	= CDateAdd("d", returnAry[2], tmpDate);
			}
			else
			{
				//������t
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
	�Ǧ^��Ӥ�����ۮt���ɶ� Exp: dtutil.DateDiff("2003/5/1", "2003/5/2") --> [0][0][1]
	@param	sdate1		�r�ꫬ�A����@
	@param	sdate2		�r�ꫬ�A����G
	@return	����᪺�ɶ��t�}�C
	*/
   	public static int[] DateDiff(String sdate1, String sdate2) throws Exception
  	{
  		try
		{
			String	tmpDate		= "";
			int[]	returnAry	= new int[3];
			//���o�~�t
			returnAry[0]	= DateDiff("y", sdate1, sdate2);
			//�����~�t
			tmpDate	= DateAdd("y", returnAry[0], sdate1);
			//���o��t
			returnAry[1]	= DateDiff("m", tmpDate, sdate2);
			//������t
			tmpDate	= DateAdd("m", returnAry[1], tmpDate);
			//���o��t
			returnAry[2]	= DateDiff("d", tmpDate, sdate2);
			//������t
			tmpDate	= DateAdd("d", returnAry[2], tmpDate);

			return returnAry;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".DateDiff(String, String) Exception: \r\n" + err.toString());
		}
   	}

	/**
	�Ǧ^��ӤC�X���������ۮt���ɶ����j���ƥ� Exp: dtutil.CDateDiff("d", "0920501", "0920502") --> 1
	@param	interval	������� d - ��, w - �P��, m - ��, y - �~
	@param	sdate1		�r�ꫬ�A����@
	@param	sdate2		�r�ꫬ�A����G
	@return	����᪺�ɶ��t
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
	�Ǧ^��Ӥ�����ۮt���ɶ����j���ƥ� Exp: dtutil.DateDiff("d", "2003/5/1", "2003/5/2") --> 1
	@param	interval	������� d - ��, w - �P��, m - ��, y - �~
	@param	sdate1		�r�ꫬ�A����@
	@param	sdate2		�r�ꫬ�A����G
	@return	����᪺�ɶ��t
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
				throw new Exception(className + ".DateDiff Exception: ������A�ഫ����!! Date1:" + sdate1 + ", Date2:" + sdate2);
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
				throw new Exception(className + ".DateDiff Exception: �ѼƶǤJ���~: interval: " + interval);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".DateDiff Exception: \r\n" + err.toString());
		}
   	}

	/**
	���o�t�ήɶ��A�榡�� hhmm
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
	���o�t�ήɶ��A�榡�� hhmmss
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
	���o�t�ήɶ���L��A�榡�� hhmmssms
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
	���o�t�Τ���A�榡���褸���
	@param		�L
	@return		�褸���yyyy/mm/dd
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
	���o�t�Τ���A�榡���C�X������.
	@return	�C�X������yyymmdd
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
	�N�褸����榡�Ƭ��C�X������
	@param	_date �褸���
	@return	�C�X������yyymmdd
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
	�N�C�X�������榡�Ƭ��褸���
	@param	_date 		�C�X������
	@param	formatType	�榡�ƺ���
	@return	�褸���
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
	�N�C�X�������榡��
	@param	_date 		�C�X������
	@return	������ yyy/MM/dd
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
	���o�t�Φ~�A�榡���T�X����~.
	@return	�T�X����~yyy
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
	�N�|�X����ɶ��榡��
	@param	_time 		�|�X����ɶ�
	@return	������ mm:ss
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
	����C�X���������P��
	@param	_date 		�C�X������
	@return	�@ ~ ��
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
					resultStr	= "�@";
					break;
				case 2:
					resultStr	= "�G";
					break;
				case 3:
					resultStr	= "�T";
					break;
				case 4:
					resultStr	= "�|";
					break;
				case 5:
					resultStr	= "��";
					break;
				case 6:
					resultStr	= "��";
					break;
				case 0:
					resultStr	= "��";
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