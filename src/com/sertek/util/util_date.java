/*
 * ----------------------------------------------------------------------------------------
問題單號：Bug #942 - CHD0MA0950002 
修改摘要：修正 getDays 計算方式
更新版本：V9512
修改人員：daniel                                
修改日期：0951116  
----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------
問題單號：Bug #826 - TPD0EC0950005
修改摘要：修正 compareDate 計算方式
更新版本：V9509
修改人員：Richard
修改日期：0951013
----------------------------------------------------------------------------------------
問題單號：Bug #826 - TPD0EC0950005
修改摘要：新增 compareDateB(),除mills[2]為終止日期扣除起始日期,其餘與compareDate()均無異
修改人員：balasom                                
修改日期：95.08.14               
更新版本：v9509
----------------------------------------------------------------------------------------
*/
package com.sertek.util;

import java.io.*;
import java.util.*;
import java.text.*;
import com.sertek.util.utility;

public class util_date
  {
  	//private Calendar cal = Calendar.getInstance();
   /**
    *	建構子
   */
   public util_date()
    {
    }
/**********************************Date處理部分************************************/
   /**
  *
  * 編輯日期格式yyyy/mm/dd
  @param  cal Calendar
  @return  編輯過後的日期字串
 */
 public String parseDate(Calendar cal) {
  SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd");
  return formatter.format(cal.getTime());
 }

 /**
  * 編輯日期與時間格式yyyy/mm/dd 24h:mm:ss
  @param  cal Calendar
  @return  編輯過後的日期時間字串
 */
 public String parseDateTime(Calendar cal) {
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  return formatter.format(cal.getTime());
 }

 /**
  * 將西元的日期字串，轉為民國的日期字串，包含時間
  @param  wdate 西元的日期字串
  @return  民國的日期字串
 */
 public String parseCDateTime(String wdate) {
  String result = "";
  utility util = new utility();
  //Calendar cal = new GregorianCalendar(wdate.substring(0,4),wdate.substring(6,7),wdate.substring(9,10),wdate.substring(12,13),wdate.substring(15,16),wdate.substring(18,19),0);
  if(wdate.length()<=0)
   return "";

  String year = "" + (Integer.parseInt(wdate.substring(0,4)) - 1911);
  try {
   result = result + util.fillFrontzero(year,3) + "/" +
    util.fillFrontzero(wdate.substring(6,7),2) + "/" +
    util.fillFrontzero(wdate.substring(9,10),2) +
    " " +
    util.fillFrontzero(wdate.substring(12,13),2) + ":" +
    util.fillFrontzero(wdate.substring(15,16),2) + ":" +
    util.fillFrontzero(wdate.substring(18,19),2);
  } catch(IOException e) {

  }
  util = null;
  return result;
 }

 /**
  * 將西元的日期字串，轉為民國的日期字串，不包含時間
  @param  wdate
  @return  民國的日期字串
 */
 public String parseCDate(String wdate) {
  String result = "";
  utility util = new utility();
  //Calendar cal = new GregorianCalendar(wdate.substring(0,4),wdate.substring(6,7),wdate.substring(9,10),wdate.substring(12,13),wdate.substring(15,16),wdate.substring(18,19),0);
  if(wdate.length()<=0) return "";
  String year = "" + (Integer.parseInt(wdate.substring(0,4)) - 1911);
  try {
   result = result + util.fillFrontzero(year,3) +
    util.fillFrontzero(wdate.substring(5,7),2) +
    util.fillFrontzero(wdate.substring(8,10),2);
  } catch(IOException e) {

  }
  util = null;
  return result;
 }

 /**
  * 將民國的日期字串，轉為西元的日期字串，包含時間
  @param  cdate
  @return  西元的日期字串
 */
 public String parseWDateTime(String cdate) {
  String result = "";
  utility util = new utility();
  if(cdate.length()<=0) return result;
  String year = "" + (Integer.parseInt(cdate.substring(0,3)) + 1911);
  try {
   result = result + util.fillFrontzero(year,4) + "/" +
    util.fillFrontzero(cdate.substring(4,6),2) + "/" +
    util.fillFrontzero(cdate.substring(7,9),2) +
    " " +
    util.fillFrontzero(cdate.substring(10,12),2) + ":" +
    util.fillFrontzero(cdate.substring(13,15),2) + ":" +
    util.fillFrontzero(cdate.substring(16,18),2);
  } catch(IOException e) {

  }
  util = null;
  return result;

 }

 /**
  * 將民國的日期字串轉為西元的日期字串
  @param  cdate 民國的日期字串
  @return  西元的日期字串
 */
 public String parseWDate(String cdate) {
  utility util = new utility();
  String result = "";
  if(cdate.length()<=0) return result;
  String year = "" + (Integer.parseInt(cdate.substring(0,3)) + 1911);
  try {
   result = result + util.fillFrontzero(year,4) + "/" +
    util.fillFrontzero(cdate.substring(4,6),2) + "/" +
    util.fillFrontzero(cdate.substring(7,9),2) ;
  } catch(IOException e) {

  }
  util = null;
  return result;

 }

 /**
  * 取出設定的Calendar的西元日期字串，含時間
  @param  cal Calendar
  @return  西元的日期字串
 */
 public String nowWDateTime(Calendar wdate) {
  Calendar cal = wdate;
  utility util = new utility();
  String result = "";
  try {
   result = util.fillFrontzero(new Integer(cal.get(Calendar.YEAR)).toString(),4) + "/" +
    util.fillFrontzero(new Integer(cal.get(Calendar.MONTH)+1).toString(),2) + "/" +
    util.fillFrontzero(new Integer(cal.get(Calendar.DAY_OF_MONTH)).toString(),2) + " " +
    util.fillFrontzero(new Integer(cal.get(Calendar.HOUR_OF_DAY)).toString(),2) + ":" +
    util.fillFrontzero(new Integer(cal.get(Calendar.MINUTE)).toString(),2) + ":" +
    util.fillFrontzero(new Integer(cal.get(Calendar.SECOND)).toString(),2) ;
    //System.out.println("目前的西元日期reslt = " + result);
  } catch(IOException e) {

  }

  cal = null;
  util = null;
  return result;
 }
 
 /**
  * 取出設定的Calendar的西元日期字串，純數字
  @param  cal Calendar
  @return  西元的日期字串
 */
 public String nowWDateTime_num(Calendar wdate) {
  Calendar cal = wdate;
  utility util = new utility();
  String result = "";
  try {
   result = util.fillFrontzero(new Integer(cal.get(Calendar.YEAR)).toString(),4) + 
    util.fillFrontzero(new Integer(cal.get(Calendar.MONTH)+1).toString(),2) + 
    util.fillFrontzero(new Integer(cal.get(Calendar.DAY_OF_MONTH)).toString(),2) + 
    util.fillFrontzero(new Integer(cal.get(Calendar.HOUR_OF_DAY)).toString(),2) +
    util.fillFrontzero(new Integer(cal.get(Calendar.MINUTE)).toString(),2) +
    util.fillFrontzero(new Integer(cal.get(Calendar.SECOND)).toString(),2) ;
    //System.out.println("目前的西元日期reslt = " + result);
  } catch(IOException e) {

  }

  cal = null;
  util = null;
  return result;
 }


 /**
  * 取出目前的日期字串
  @param
  @return  西元的日期字串，含時間
 */
 public String nowWDateTime() {
  return nowWDateTime(Calendar.getInstance());
 }
 

 /**
  * 取出目前的日期字串
  @param
  @return  西元的日期字串，含時間
 */
 public String nowWDateTime_num() {
  return nowWDateTime_num(Calendar.getInstance());
 }

 /**
  * 取出目前的民國日期字串
  @param
  @return  民國的日期字串，含時間
 */
 public String nowCDateTime() {
  return  parseCDateTime(nowWDateTime());
 }

 /**
  * 取出設定的Calendar的民國日期字串，含時間
  @param  cal Calendar
  @param  民國的日期字串
 */
 public String nowCDateTime(Calendar cal) {
  return parseCDateTime(nowWDateTime(cal));
 }

 /**
  * 取出設定的Calendar的民國日期字串。不含時間
  @param  cal calendar
  @return  民國的日期字串
 */
 public String nowCDate(Calendar cal) {
  return parseCDate(nowWDateTime(cal));
 }

 /**
  * 取出目前的民國的日期字串
  @param
  @return  民國的日期字串
 */
 public String nowCDate() {
  return nowCDate(Calendar.getInstance());
  //return parseCDate(nowWDateTime());
 }

 /**
  * 取出所設定的Calendar的時間字串
  @param  cal calendar
  @return  目前時間的字串
 */
 public String nowTime(Calendar wdate) {
  Calendar cal = wdate;
  String result = "";
  utility util = new utility();
  try {
   result = result +
    util.fillFrontzero(new Integer(cal.get(Calendar.HOUR_OF_DAY)).toString(),2) +
     util.fillFrontzero(new Integer(cal.get(Calendar.MINUTE)).toString(),2) +
     util.fillFrontzero(new Integer(cal.get(Calendar.SECOND)).toString(),2) ;
  } catch(IOException e) {

  }
  util = null;
  return result;
 }

 /**
  * 取出所設定的Calendar的時間字串
  @param  cal calendar
  @return  目前時間的字串9 碼到
 */
 public String nowMilliTime(Calendar wdate) {
  Calendar cal = wdate;
  String result = "";
  utility util = new utility();
  try {
    result = result +
    util.fillFrontzero(new Integer(cal.get(Calendar.HOUR_OF_DAY)).toString(),2) +
    util.fillFrontzero(new Integer(cal.get(Calendar.MINUTE)).toString(),2) +
    util.fillFrontzero(new Integer(cal.get(Calendar.SECOND)).toString(),2) +
    util.fillFrontzero(new Integer(cal.get(Calendar.MILLISECOND)).toString(),6);
  } catch(IOException e) {

  }
  util = null;
  return result;
 }


 /**
  * 取出目前的時間字串
  @param
  @return  目前的時間字串
 */
 public String nowTime() {
  return nowTime(Calendar.getInstance());
 }

 /**
  * 取出目前的時間字串
  @param
  @return  目前的時間字串
 */
 public String nowMilliTime() {
  return nowMilliTime(Calendar.getInstance());
 }






















   /**
     * 		取得目前的日期instance
     *@param
     *@return 			傳回Calendar
   */
   public Calendar getDate() {
   	return Calendar.getInstance();
   }

   /**
     *Hashtable中存放西元年相關的參數日期。key為year,month,day,hour,minute,second。
     * 		取得有關西元日期相關的參數Hashtable
     *@param
     *@return 			傳回存有西元日期的參數的Hashtable
   */
   public Hashtable getWDateDetail() {
   	return getWDateDetail(getDate());
   }

   /**
     *Hashtable中存放民國年相關的參數日期。key為year,month,day,hour,minute,second。
     * 		取得有關民國日期相關的參數Hashtable
     *@param
     *@return 			傳回存有民國日期的參數的Hashtable
   */
   public Hashtable getCDateDetail() {
   	return getCDateDetail(getDate());
   }

   /**
     *Hashtable中存放西元年相關的參數日期。key為year,month,day,hour,minute,second。
     * 		取得有關指定Calendar的西元日期相關參數Hashtable
     *@param 	c		Calendar
     *@return 			傳回指定Calendar的存有西元日期的參數的Hashtable
   */
   public Hashtable getWDateDetail(Calendar c) {
   	Hashtable r = new Hashtable();
   	r.put("year",String.valueOf(c.get(Calendar.YEAR)));
   	r.put("month",String.valueOf((c.get(Calendar.MONTH)+1)));
   	r.put("day",String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
   	r.put("hour",String.valueOf(c.get(Calendar.HOUR_OF_DAY)));
   	r.put("minute",String.valueOf(c.get(Calendar.MINUTE)));
   	r.put("second",String.valueOf(c.get(Calendar.SECOND)));
   	return r;
   }

   /**
     *Hashtable中存放民國年相關的參數日期。key為year,month,day,hour,minute,second。
     * 		取得有關指定Calendar的民國日期相關參數Hashtable
     *@param 	c		Calendar
     *@return 			傳回指定Calendar的存有民國日期的參數的Hashtable
   */
   public Hashtable getCDateDetail(Calendar c) {
   	Hashtable r = getWDateDetail(c);
   	r.put("year",String.valueOf((Integer.parseInt((String)r.get("year"))-1911)));
   	return r;
   }

   /**
     * 		取得當月日數
     *@param
     *@return 			傳回指定Calendar的當月日數
   */
   public int getDays() {
   	return getDays(getDate());
   }

   /**
     * 		取得有關指定Calendar的當月日數
     *@param 	c		Calendar
     *@return 			傳回指定Calendar的當月日數
   */
   public int getDays(Calendar c) {
   	int r = 31;
   	GregorianCalendar gc = (GregorianCalendar) c;
   	boolean flag = gc.isLeapYear((int)c.get(Calendar.YEAR));
   	int m = c.get(Calendar.MONTH) + 1;

   	if(m==2){
   		if(flag == true) r = 29;
   		else r = 28;
	} else if(m < 7) {
		if((m%2) == 0) r = 30;
	} else {
		if((m%2) == 1) r = 30;
	}
	return r;
   }


   /**
     *此民國的樣式為yyymmdd，6碼
     **將依民國日期取出此月的日數
     *@param		str_cdate
     *@return
   */
   public int getDays(String str_cdate) throws IOException {
   	try {
   		return getDays(str_cdate,"");
   	} catch(Exception e) {
   		return -1;
   	}
   }

   /**
     *此民國的樣式為yyy/mm/dd，而分隔符號可系/或-，由tag來指明。
     **將依民國日期取出此月的日數
     *@param		str_cdate
     *@return
   */
   public int getDays(String str_cdate,String tag) {
   	try {

   		//String result_0 = StrTran(alltrim(str_cdate),tag,"");
   		//System.out.println("getDays開始");
   		String result_0 = str_cdate.trim();
   		//System.out.println("all trim");
   		////2006.10.19 modified by daniel [Bug #942]   修正 getDays 計算方式 tag為空字串時,不須要做 StrTran,否則會進入無窮回圈
   		if(!tag.equals("")){
   			result_0 = new utility().StrTran(result_0,tag,"");
   		}
   		//System.out.println("StrTran結束");
   		//System.out.println("解析完字串");
   		int result_i = 0;
   		if(result_0.length()<6) return -1 ;
   		
   		result_i = getDays(str2date(result_0));
   		return result_i ;
   	} catch(Exception e) {
   		return -1;
   	}
   }

   /**
     * 	檢核日期是否有錯誤
     *@param 	date	Calendar
     *@return 		傳回檢核的結果，正常傳回西元年日期字串，錯誤傳回空字串
   */
   public String checkDTnull(Calendar date) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	if(date == null) return result_0;
        	int d = getDays(date);
        	if(d >= (date.get(Calendar.DAY_OF_MONTH))) {
        		String style = "";
			if(style.length()<=0) style = "yyyy/MM/dd";
			SimpleDateFormat formatter = new SimpleDateFormat(style);
 			result_0 = formatter.format(date.getTime());

        	}
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

   /**
     * 	檢核日期是否有錯誤
     *@param 	w_date	Calendar
     *@param 	flag 	0為西元年，非0為民國年
     *@return 		傳回檢核的結果，正常傳回指定西元或民國格式的日期，錯誤傳回空字串
   */
   public String checkDTnull(Calendar w_date,int flag) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	if(w_date == null) return result_0;
        	int d = getDays(w_date);
        	if(d >= (w_date.get(Calendar.DAY_OF_MONTH))) {
        		String style = "";
			if(style.length()<=0) style = "yyyy/MM/dd";
			SimpleDateFormat formatter = new SimpleDateFormat(style);
 			result_0 = formatter.format(w_date.getTime());
 			if(flag != 0) {
        			result_0 = date2str(w_date);
        		}

        	}
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

   /**
     * 		檢核民國日期是否有錯誤
     *@param 	str_cdate	民國日期yyymmdd(含分隔符號亦可)
     *@param 	tag		民國日期的分隔方式/或-
     *@return 			傳回檢核的結果，正常傳回西元年日期字串，錯誤傳回空物件
   */
   public String checkDTnull(String str_cdate,String tag) throws IOException
     {
      Calendar result_cal = null;
      String result_0 = "";
       try
       {
        //implement part
        	result_0 = new utility().StrTran(str_cdate,tag,"");
        	if(result_0.length()<6) return "";
        	result_cal = str2date(result_0);
        	String temp = result_0.substring(0,result_0.length()-2)+"01";
        	//System.out.println("temp date = " + temp);
        	int d = getDays(temp);
        	int day = Integer.parseInt(result_0.substring((result_0.length()-2),result_0.length()));
        	if(d >= day) {
        		String style = "";
			if(style.length()<=0) style = "yyyy/MM/dd";
			SimpleDateFormat formatter = new SimpleDateFormat(style);
 			result_0 = formatter.format(result_cal.getTime());
 			//if(!result_0.equals(str_cdate)) result_0 = "";


        	} else {
        		result_0 = "";
        	}
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

   /**
     * 	將傳入的str_in西元日期字串組合成西元格式的yyyymmdd或yyyy年mm月dd日傳回
     *@param 	tag	在原始的str_in中，以何種方式分隔日期yyyy/mm/dd--(分隔符號為"/")或yyyymmdd--(分隔符號為"")
     *@param 	str_in	西元的日期字串
     *@param	kd	1以年月日分隔，非1為不分隔
     *@return 		傳回檢核的結果，正常傳回日期字串，錯誤傳回空字串
   */
   public String parseWDate(String tag,String str_in,int kd) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	String[] kds = {"","",""};
        	String ss = new utility().StrTran(str_in,tag,"");
        	if(kd == 1) {
        		kds[0] = "年";
        		kds[1] = "月";
        		kds[2] = "日";
        	}
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        	Date d = formatter.parse(ss);
        	Calendar cal = Calendar.getInstance();
        	cal.setTime(d);
        	result_0 = date2str(cal);
        	result_0 = result_0.substring(0,3) + kds[0] +
        		   result_0.substring(3,5) + kds[1] +
        		   result_0.substring(5,7);
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

/**
     * 	將傳入的str_in民國日期字串組合成民國格式的yyymmdd或yyy年mm月dd日傳回
     *@param 	tag	在原始的str_in中，以何種方式分隔日期yyyy/mm/dd--(分隔符號為"/")或yyyymmdd--(分隔符號為"")
     *@param 	str_in	民國的日期字串
     *@param	kd	1以年月日分隔，非1不分隔yyymmdd
     *@return 		傳回檢核的結果，正常傳回日期字串，錯誤傳回空字串
   */
   public String parseCDate(String tag,String str_in,int kd) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	String[] kds = {"","",""};
        	String ss = str_in;

        	if (!tag.equals(""))
        		ss = new utility().StrTran(str_in,tag,"");
        	
        	if(kd == 1) {
        		kds[0] = "年";
        		kds[1] = "月";
        		kds[2] = "日";
        	}
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        	//Date d = str2date(ss).getTime();
        	//Calendar cal = Calendar.getInstance();
        	//cal.setTime(d);
        	Calendar cal = str2date(ss);
        	result_0 = date2str(cal);
        	result_0 = result_0.substring(0,3) + kds[0] +
        		   result_0.substring(3,5) + kds[1] +
        		   result_0.substring(5,7) + kds[2];
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }


/*
{ --------------------------------
   由標準的日期字串來切割 1998/10/10
   選項 1 : yy/mm/dd -> 中文年月日 以1911 起算
   其他   : 六碼 yymmdd
  --------------------------------}
function TDM1._parseDate(tag, str_in: string;
                            kd: smallint): string;
var
  S, Year, Month, Day: string;
  i: integer;
begin
  if str_in <> '' then begin
     year:= inttostr(strtoint(copy(str_in, 1, 4))-1911);
     S:= Copy(str_in, 6, length(str_in));
     if (S[2] <> tag) then
       month := S[1]+ S[2]
     else
       month := '0'+S[1];
     day := Copy(S, Pos(tag, S)+1, length(S));
     if length(day) = 1 then
       day := '0'+ day;
     case kd of
     1: begin
         result:= year+ '年'+ month+ '月'+ day+ '日';
        end;
     else begin
         result:= year+ month+ day;
        end;
     end;{_case}
  end;{_if}
end;{_parseDate}
*/

   /**
     * 		取出民國日期字串
     *@param
     *@return 			傳回民國日期字串
   */
   public String todayStr() throws IOException {
   	String result_0="";
   	try {
   		result_0 = todayStr(getCDateDetail());
   	} catch(Exception ex) {}
   	return result_0;
   }

   /**
     *Hashtable內容key放置有year,month,day為三個必要,其他還有hour,minute,second。

     * 		取出民國日期字串
     *@param	ht		Hashtable存被有關日期相關參數的hashtable。
     *@return 			傳回民國日期字串
   */
   public String todayStr(Hashtable ht) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	//int y = Integer.parseInt((String)ht.get("year"));
        	//int m = Integer.parseInt((String)ht.get("month"));
        	//int d = Integer.parseInt((String)ht.get("day"));
        	//result_0 = fillzero(y,3) + fillzero(m,2) + fillzero(d,2);
        	utility util = new utility();
        	if(ht.get("year")!=null && ht.get("month")!=null && ht.get("day")!=null) {
        		result_0 = util.fillFrontzero((String)ht.get("year"),3) +
        		   	   util.fillFrontzero((String)ht.get("month"),2) +
        		   	   util.fillFrontzero((String)ht.get("day"),2);
        	}
        	util = null;

       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

   /**
     *Hashtable內容key放置有year,month,day為三個必要,其他還有hour,minute,second。

     * 		取出西元日期字串
     *@param	ht		Hashtable存被有關西元日期相關參數的hashtable。
     *@return 			傳回西元日期字串
   */
   public String nowstr() throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	result_0 = nowstr(getWDateDetail());
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

   /**
     *Hashtable內容key放置有hour,minute,second，其他還有year,month,day。

     * 		取出指定ht內容的時間字串
     *@param	ht		Hashtable存被有關時間相關參數的hashtable。
     *@return 			傳回時間字串
   */
   public String nowstr(Hashtable ht) throws IOException {
   	String result_0="";
       	try {
        //implement part
        	utility util = new utility();
        	if(ht.get("hour")!=null && ht.get("minute")!=null && ht.get("second")!=null) {
        		result_0 = util.fillFrontzero((String)ht.get("hour"),2) +
        			   util.fillFrontzero((String)ht.get("minute"),2) +
        			   util.fillFrontzero((String)ht.get("second"),2);
        	}
        	util = null;
       	}catch(Exception ex)
        {}

       	//回傳值
       	return result_0;
   }
/*
{ =============================================
  傳回  目前的時間 hhmmss
  ============================================ }
function TDM1._nowstr: string;
var
   tmp :string;
begin
  tmp:=_strtran(Copy(timetostr(time),4,8),':','');
  if ((copy(timetostr(time),1,2))='PM')then begin
     if Copy(timetostr(time),4,2)<>'12'  then begin
        result:=inttostr(strtoint(copy(tmp,1,2))+12)+
                copy(tmp,3,4);
     end else begin
        result:=inttostr(strtoint(copy(tmp,1,2)))+
                copy(tmp,3,4);
     end;{if}
  end else begin
     if Copy(timetostr(time),4,2)='12' then begin
        result:='00'+
                copy(tmp,3,4);
     end else begin
        result:= _strtran(Copy(timetostr(time),4,8),':','');
     end;{if}
  end;{if}
end;{nowstr}
*/

    /**
     *ex:"0900111" = daycal("0900101",10,"DATE");
     *ex:"0900201" = daycal("0900101",1,"MONTH");

     * 		依民國日期str_i傳入，以field指定要運算的欄位，以day_i指明要運算的數字
     *@param	str_i		原始的民國日期
     *@param 	day_i		要計算的數字
     *@param	filed		要計算的欄位
     *@return 			傳回計算完畢的民國日期
   */
    public String daycal(String str_i,int day_i,String field) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	if(str_i.length()>0) {
        		Calendar c = str2date(str_i);
        		if(c!=null) {
        			//c.add(Calendar.DATE,day_i);
        			if(field.equals("YEAR")) c.add(Calendar.YEAR,day_i);
        			else if(field.equals("MONTH")) c.add(Calendar.MONTH,day_i);
        			else if(field.equals("DATE")) c.add(Calendar.DATE,day_i);
        			else if(field.equals("HOUR")) c.add(Calendar.HOUR,day_i);
        			else if(field.equals("MINUTE")) c.add(Calendar.MINUTE,day_i);
        			else if(field.equals("SECOND")) c.add(Calendar.SECOND,day_i);
        			else c.add(Calendar.DATE,day_i);
        			result_0 = todayStr(getCDateDetail(c));
        		}
        	}
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

   /**
     *ex:"0900111" = daycal("0900101",10);

     * 		依民國日期str_i傳入以day_i指明要計算日期的數字
     *@param	str_i		原始的民國日期
     *@param 	day_i		要計算的數字
     *@return 			傳回計算完畢的民國日期
   */
   public String daycal(String str_i,int day_i) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	/*if(str_i.length()>0) {
        		Calendar c = str2date(str_i);
        		if(c!=null) {
        			c.add(Calendar.DATE,day_i);
        			result_0 = todayStr(getCDateDetail(c));
        		}
        	}*/
        	result_0 = daycal(str_i,day_i,"DATE");
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

/*
{ =============================================
  傳入日期六碼 並計算的天數 回傳六碼 民國日期
  ============================================ }
function  TDM1._daycal( str_i : string;
                      day_i : smallint): string;
var
  result_o : string;
begin
  try
    // 871213 -7 => 871205
    //
    result_o := inttostr(1911+
                  strtoint(
                    Copy(str_i,1,2)))+
                '/'+Copy(str_i,3,2)+
                '/'+Copy(str_i,5,2);
    // 計算日期
    result_o := _date2str(_str2date(str_i)+ day_i);
    //回傳值
    result := result_o;
  except
  end;{_try}
end;{_daycal}
*/


   /**
     * 		傳入6碼的民國日期，轉成Calendar的格式傳出
     *@param	str_i		6碼的民國日期
     *@return 			傳回西元的Calendar
   */
   public Calendar str2date(String str_i) throws IOException
     {
      Calendar result_0 = Calendar.getInstance();
       try
       {
        //implement part
        	if(str_i.length()>0) {
        		int[] d = {0,0,0};
        		String s = str_i.trim();
        		d[0] = Integer.parseInt(s.substring(0,3));
        		d[1] = Integer.parseInt(s.substring(3,5));
        		d[2] = Integer.parseInt(s.substring(5,7));
        		result_0 = new GregorianCalendar((d[0]+1911),(d[1]-1),d[2],0,0,0);

        	}
       }
      catch(Exception ex){
       } //return null;}
      return result_0;
       //回傳值
       //return ;
   }


/*
{ =============================================
    傳入 六碼 的年月日字串 並回傳 日期格式
  ============================================ }
function TDM1._str2date( str_i:string): TDateTime;
var
  ts: string;
  result_o : TDateTime;
begin
  try
    //預設為今天
    result_o:= date;
    if (str_i<> null) and
        _ChkNum(str_i) and
        _ChkLen(str_i,6) then begin
      result_o:= StrtoDate(
                 inttostr(1911+
                   strtoint(Copy(str_i,1,2)))+'/'+
                 Copy(str_i,3,2)+'/'+
                 Copy(str_i,5,2));
    end;{_if}
    result:= result_o;
  except
  end;{_try}
end;{_str2date}
*/

   /**
     * 		傳入Calendar日期，轉成6碼的民國年的格式傳出
     *@param	date_i		西元的Calendar物件
     *@return 			傳回6碼的民國年的格式
   */
   public String date2str(Calendar date_i) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	result_0 = todayStr(getCDateDetail(date_i));
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }
/*
{ =============================================
  傳入西元日期 回傳六碼民國日期
  ============================================ }
function  TDM1._date2str( date_i: TDateTime): string;
var
  ts       : string;
  result_o : string;
begin
  try
    // initial
    result_o := '';
    ts       := datetostr(date_i);
    result_o := inttostr((strtoint(
                Copy(ts,1,4))-1911))+
                _fillzero(
                _fillblank(Copy(ts,6,2),2,1)+
                _fillzero(
                _fillblank(Copy(ts,9,2),2,1)));
    // 回傳值
    result := result_o;
  except
  end;{_try}
end;{_date2str}
*/

   /**
     * 		計算兩個日期字串的差距，以日差計算。
     *@param	Start_i		起始的日期字串
     *@param	End_i		結束的日期字串
     *@return 			傳回起始與結束兩日期的差距
   */
   public String compareDate(String Start_i,String End_i) throws IOException {
   	String result_0 = "";

   	try {
   		result_0 = compareDate(Start_i,End_i,0);
   	} catch(Exception ex) {}
   	return result_0;
   }

  /**
     * 		計算兩個日期字串的差距以日差計算
     *@param	Start_i		起始的日期字串
     *@param	End_i		結束的日期字串
     *@param	l		小數點幾位數
     *@return 			傳回起始與結束兩日期的差距
   */
   public String compareDate(String Start_i,String End_i,int l) throws IOException {
   	String result_0 = "";

   	try {
   		result_0 = compareDate(Start_i,End_i,3,l);
   	} catch(Exception ex) {}
   	return result_0;
   }

   /**
     * 		計算兩個日期字串的差距
     *@param	Start_i		起始的日期字串
     *@param	End_i		結束的日期字串
     *@param	flag		0:秒差，1:分差，2:時差，3:日差，4:月差(標準日數30)，5:年差
     *@param	l		小數點幾位數
     *@return 			傳回起始與結束兩日期的差距
   */
   public String compareDate(String Start_i,String End_i,int flag,int l) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	long[] mills = {0,0,0};
        	double diff = 0;
        	Calendar cal1 = str2date(Start_i);
        	Calendar cal2 = str2date(End_i);
        	if(1==1) {
        		mills[0] = cal1.getTime().getTime();
        		mills[1] = cal2.getTime().getTime();
        		//if(mills[0] > mills[1]) {
        		//	mills[2] = (mills[0] - mills[1]);
        		//else
        		//	mills[2] = (mills[1] - mills[0]);
        		
        		mills[2] = (mills[0] - mills[1]);
        		/*switch(flag) {
        			case 0:
        				standart = 1000; break;		//秒差
        			case 1:
        				standart = 1000*60; break;		//分差
        			case 2:
        				standart = 1000*60*60; break;	//時差
        			case 3:
        				standart = 1000*60*60*24; break;	//日差
        			case 4:
        				standart = 1000*60*60*24*30; break;	//月差，標準日數30
        			default :
        				standart = 1000*60*60*24*365; break; //年差
        		}
        		System.out.println("mils = " + mills[2]);
        		System.out.println("standart = " + standart);
        		System.out.println("diff = " + (mills[2]/ standart));
        		result_0 = result_0 + FormatNum((double)(mills[2] / standart),l);*/
        		diff = mills[2];
        		if(flag >= 0) diff = diff / 1000;
        		if(flag >= 1) diff = diff / 60;
        		if(flag >= 2) diff = diff / 60;
        		if(flag >= 3) diff = diff / 24;
        		if(flag >= 4) diff = diff / 30;
        		if(flag >= 5) diff = diff / 12;
        		//System.out.println("diff = " + diff);
        		result_0 = new utility().FormatNum(diff,l);
        		//result_0 = result_0 + (double)(mills[2] / standart);
        		//result_0 = result_0 + mills[2] + "/" + standart + "=" + (double)(mills[2] / standart);
        		//result_0 = result_0 + mills[2];
        		//result_0 = result_0 + "find";
        	} //else result_0 = result_0 + "find2";
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

  //2006.08.14 modified by balasom [Bug #826 - TPD0EC0950005]
  //新增 compareDateB(),除mills[2]為終止日期扣除起始日期,其餘與compareDate()均無異
  public String compareDateB(String Start_i,String End_i,int flag,int l) throws IOException
    {
     String result_0="";
      try
      {
       //implement part
       	long[] mills = {0,0,0};
       	double diff = 0;
       	Calendar cal1 = str2date(Start_i);
       	Calendar cal2 = str2date(End_i);
       	if(1==1) {
       		mills[0] = cal1.getTime().getTime();
       		mills[1] = cal2.getTime().getTime();
       		mills[2] = (mills[1] - mills[0]);

       		diff = mills[2];
       		if(flag >= 0) diff = diff / 1000;
       		if(flag >= 1) diff = diff / 60;
       		if(flag >= 2) diff = diff / 60;
       		if(flag >= 3) diff = diff / 24;
       		if(flag >= 4) diff = diff / 30;
       		if(flag >= 5) diff = diff / 12;
       		result_0 = new utility().FormatNum(diff,l);
       	} 
      }
      catch(Exception ex){}

      //回傳值
      return result_0;
  }


	/**
		*將七碼的民國日期格式化。民國日期要確定七碼
		@param		date
		@param		format_s
		@return 	格式後的民國日期

	*/
	public String formatCDate(String date,String format_s) {
		String temp = date.trim();
		String result = "";
		if(temp.length()<=0) return result;
		if(temp.length() == 7) {
			result = temp.substring(0,3) + format_s + temp.substring(3,5) + format_s + temp.substring(5,7);
		}
		return result;
	}

	/**
		*將六碼的時間格式化。時間要確定六碼
		@param		time
		@param		format_s	分隔的符號
		@return 	格式後的時間

	*/
	public String formatTime(String time,String format_s) {
		String temp = time.trim();
		String result = "";
		if(temp.length()<=0) 
			return result;
		else
		{
			if (temp.length()==4)
				result = temp.substring(0,2) + format_s + temp.substring(2,4) ;
			if (temp.length()==6)
				result = temp.substring(0,2) + format_s + temp.substring(2,4) + format_s + temp.substring(4,6);
		}
		return result;
	}
	/**
	*將四碼的時間轉成中文時間,如1130 轉成11時30分,時間要確定四碼
	@param		time
	@return 	格式後的時間

*/
public String Time2CTime(String time) {

    return Time2CTime(time,0);

}

/**
*將四碼的時間轉成中文時間,如1130 轉成十一時三十分,時間要確定四碼
@param		time
@param type  0:轉成數字時間(如1130 轉成11時30分)  1:轉成國字時間(如1130 轉成十一時三十分)
@return 	格式後的時間

*/
public String Time2CTime(String time,int type) {

   String result = "";
   utility util = new utility();
try {
   if(time.length()==4)
   {
      if((Integer.parseInt(time.substring(0,2))-12)<0)
      {
      		if (type==1)	
              	result="上午"+util.Num2CNum(Integer.parseInt(time.substring(0,2)))+"時";
      		else
      			result="上午"+Integer.parseInt(time.substring(0,2))+"時";
      }else{
              if((Integer.parseInt(time.substring(0,2))-12)==0){
              	if (type==1)
              		result="下午"+util.Num2CNum(Integer.parseInt(time.substring(0,2)))+"時";
              	else
              		result="下午"+Integer.parseInt(time.substring(0,2))+"時";
              }else{
              	if (type==1)	
              		result="下午"+util.Num2CNum(Integer.parseInt(time.substring(0,2))-12)+"時";
              	else
              		result="下午"+(Integer.parseInt(time.substring(0,2))-12)+"時";
              }
       }
       if(!time.substring(2,4).equals("00")){
       		if (type==1){
       			result=result+util.Num2CNum(Integer.parseInt(time.substring(2,4)))+"分";
       		}else{
       			//result=result+Double.parseDouble(time.substring(2,4))+"分";
       			result=result+Integer.parseInt(time.substring(2,4))+"分";
       		}	
       }       
   }
} catch(Exception ex) {}
   return result;

}

	//把"中華民國ｙｙｙ年ｍｍ月ｄｄ日"轉為"yyymmdd"
	public String convertDate(String line)
	{
		utility util = new utility();
		try
		{
			String temps=util.noAnyBlank(line);
			String t=getNumFromChinese( temps.substring( temps.indexOf("國")+1,temps.indexOf("年") ) );
			if(t.length()==2)
			{
				t="0"+t;
			}
			t=t+getNumFromChinese( temps.substring( temps.indexOf("年")+1,temps.indexOf("月") ) );
			t=t+getNumFromChinese( temps.substring( temps.indexOf("月")+1,temps.indexOf("日") ) );
			return t;

		 }
		catch(Exception e)
		{
			System.out.println("EXCEPTION:convertDate()"+e);
			return "";
		}

	}


	private String getNumFromChinese(String d)
	{
		Hashtable ht=new Hashtable();
		ht.put("一","1");
		ht.put("二","2");
		ht.put("三","3");
		ht.put("四","4");
		ht.put("五","5");
		ht.put("六","6");
		ht.put("七","7");
		ht.put("八","8");
		ht.put("九","9");

		int sum=0;
		String temps="";
		if( d.indexOf("千")!=-1 )
		{
			temps=d.substring(d.indexOf("千")-1,d.indexOf("千"));
			int ti=Integer.parseInt((String)ht.get(temps))*1000;
			sum=sum+ti;
		}

	    	if( d.indexOf("百")!=-1 )
	    	{
			temps=d.substring(d.indexOf("百")-1,d.indexOf("百"));
			int ti=Integer.parseInt((String)ht.get(temps))*100;
			sum=sum+ti;
		}
		if( d.indexOf("十")!=-1 )
		{
			if(d.indexOf("十")==0)
			{
				sum=sum+10;
			}
			else
			{
				temps=d.substring(d.indexOf("十")-1,d.indexOf("十"));
				int ti=Integer.parseInt((String)ht.get(temps))*10;
				sum=sum+ti;
			}
		}

		temps=d.substring(d.length()-1);
		int ti=0;
		if (ht.get(temps)!=null)
			ti=Integer.parseInt((String)ht.get(temps));
		sum=sum+ti;
		String ts=new String(""+sum);
		if(ts.length()==1)
		{
			ts="0"+ts;
		}
		return ts;
	}
	
	/**
	 * 將民國日期加上中文單位,<br>
	 * 例01: 0920101 -->> 92年1月1日;<br>
	 * 
	 * @param dateStr
	 *          民國日期
	 * @return String
	 */
	public static String formatCDateWithCUnits(String dateStr) throws Exception {
		return formatCDateWithCUnits(dateStr, true);
	}

	/**
	 * 將民國日期加上中文單位,<br>
	 * 例01: <code>當 trimsZero = true時, </code>0920101 -->> 92年1月1日;<br>
	 * 例02: <code>當 trimsZero = false時, </code>0920101 -->> 092年01月01日<br>
	 * 
	 * @param dateStr
	 *          民國日期
	 * @return String
	 */
	public static String formatCDateWithCUnits(String dateStr, boolean trimsZero)
			throws Exception {
		String forDate = "";

		if (dateStr.length() < 7) {
			return dateStr;
		}

		try {
			String yyy = dateStr.substring(0, 3);
			String mm = dateStr.substring(3, 5);
			String dd = dateStr.substring(5);

			if (trimsZero) {
				forDate = util.getNumStr(yyy) + "年" + util.getNumStr(mm) + "月"
						+ util.getNumStr(dd) + "日";
			} else {
				forDate = yyy + "年" + mm + "月" + dd + "日";
			}
		} catch (Exception err) {
			throw new Exception(
					"util_date.formatCDateWithCUnits(String, boolean) Exception: \r\n"
							+ err.toString());
		}

		return forDate;
	}
	
	public String formatCDateUnit(String date) {
		String yyy = "";
		String mm = "";
		String dd = "";

		if (date.length() >= 7) {
			yyy = date.substring(0, 3);
			mm = date.substring(3, 5);
			dd = date.substring(5, 7);
			return yyy + "年" + mm + "月" + dd + "日";
		} else {
			return "";
		}
	}

	public String formatTimeUnit(String time) {
		String hh = "";
		String mm = "";

		if (time.length() >= 4) {
			hh = time.substring(0, 2);
			mm = time.substring(2, 4);
			return hh + "時" + mm + "分";
		} else {
			return "";
		}
	}
	
	public String getMaxDayOfMonth(String yyymm) {
		int maxDayOfMonth = 0;
		try {
			Calendar cal = str2date(yyymm + "01");
			maxDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
		}
		return "" + maxDayOfMonth;
	}
	
	public int getDayOfWeek(String cdate) {
		int dayOfWeek = 0;
		try {
			Calendar cal = str2date(cdate);
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		} catch (Exception e) {
		}
		return dayOfWeek;
	}
	
	public String getFirstDateOfWeek(String cdate) {
		String result = "";
		try {
			int dayOfWeek = getDayOfWeek(cdate);
			result = daycal(cdate, -(dayOfWeek - 1));
		} catch (Exception e) {
		}
		return result;
	}
	
	public String getLastDateOfWeek(String cdate) {
		String result = "";
		try {
			int dayOfWeek = getDayOfWeek(cdate);
			result = daycal(cdate, (7 - dayOfWeek));
		} catch (Exception e) {
		}
		return result;
	}
	
	/**
	 * 20140428 > 1030428
	 * @param wdate
	 * @return
	 */
	public String parseWDate2C(String wdate) {
		String result = "";
		utility ut = new utility();
		try {
			if (wdate.length() >= 8) {
				String yyy = ut.fillFrontzero(String.valueOf(Integer.parseInt(wdate.substring(0, 4)) - 1911), 3);
				String mm = wdate.substring(4, 6);
				String dd = wdate.substring(6, 8);
				result = yyy + mm + dd;
			}
		} catch (Exception e) {
		}
		
		return result;
	}
	
	/**
	 * 1030428 > 20140428
	 * @param cdate
	 * @return
	 */
	public String parseCDate2W(String cdate) {
		String result = "";
		utility ut = new utility();
		try {
			if (cdate.length() >= 7) {
				String yyyy = ut.fillFrontzero(String.valueOf(Integer.parseInt(cdate.substring(0, 3)) + 1911), 4);
				String mm = cdate.substring(3, 5);
				String dd = cdate.substring(5, 7);
				result = yyyy + mm + dd;
			}
		} catch (Exception e) {
		}
		
		return result;
	}
	
//	public String getDayOfWeek(String cdate) {
//		String result = "";
//		try {
//			Calendar c = str2date(cdate);
//			
//			switch (c.get(Calendar.DAY_OF_WEEK)) {
//			case Calendar.SUNDAY:
//				result = "日";
//				break;
//			case Calendar.MONDAY:
//				result = "一";
//				break;
//			case Calendar.TUESDAY:
//				result = "二";
//				break;
//			case Calendar.WEDNESDAY:
//				result = "三";
//				break;
//			case Calendar.THURSDAY:
//				result = "四";
//				break;
//			case Calendar.FRIDAY:
//				result = "五";
//				break;
//			case Calendar.SATURDAY:
//				result = "六";
//				break;
//			default:
//				result = "　";
//				break;
//			}
//		} catch (Exception ignored) {
//		}
//		return result;
//	}
}