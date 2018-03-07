package com.sertek.util;

import java.io.IOException;
import java.net.*;
import java.util.Vector;

public class util
{
	private	static	String	className	= "com.sertek.util.util";

	public static void main(String[] args)
	{
		try
		{
			System.out.println(util.getCNum(111111, 3));
		}
		catch(Exception err)
		{
			System.out.println(err.toString());
		}
	}

	/**
	將單引號轉為 \'
	@param	str	輸入字串
	@return 號轉後的字串
	*/
	public static String formStr(String str) throws Exception
	{
		try
		{
			return replace(str, "'", "\\'");
		}
		catch(Exception err)
		{
			throw new Exception(className + ".formStr Exception: \r\n" + err.toString());
		}
	}

	/**
	取得錯誤訊息中所定義的 Tag 值, 未定義 Tag 傳回預設的錯誤訊息
	@param	errMsg		錯誤訊息
	@param	defaultMsg	預設的訊息
	@return 取出後的陣列
	*/
	public static String parseErrorMsg(String errMsg, String defaultMsg) throws Exception
	{
		try
		{
			String	tagError	= util.splitTag(errMsg)[0];

			if (errMsg.equals(tagError))
				return defaultMsg;
			else
				return tagError;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".parseErrorMsg Exception: \r\n" + err.toString());
		}
	}

	/**
	取得 Tag 定義中所有值陣列 Exp: this is 【aa】 a test 【bb】 --> return {"aa", "bb"}
	@param	str	取出 Tag 資料的字串
	@return 取出後的陣列
	*/
	public static String[] splitTag(String str) throws Exception
	{
		try
		{
			String[]	tmpAry		= util.split(str, "】");
			String[]	resultAry	= new String[tmpAry.length];
			int		startPos	= 0;
			int		endPos		= 0;

			for (int i = 0; i < tmpAry.length; i++)
			{
				startPos	= tmpAry[i].lastIndexOf("【") + 1;
				endPos		= tmpAry[i].length();

				resultAry[i]	= tmpAry[i].substring(startPos, endPos);
			}

			return resultAry;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".splitTag Exception: \r\n" + err.toString());
		}
	}

	/**
	清除 str 字串左邊的空白
	@param	str	要清除空白的原始字串
	@return 清除左邊空白後的字串
	*/
	public static String lTrim(String str) throws Exception
	{
		int	i	= 0;
		try
		{
			while (str.charAt(i) == ' ')
			{
				i++;
			}

			return str.substring(i, str.length());
		}
		catch(Exception err)
		{
			throw new Exception(className + ".lTrim Exception: \r\n" + err.toString());
		}
	}

	/**
	清除 str 字串右邊的空白
	@param	str	要清除空白的原始字串
	@return 清除右邊空白後的字串
	*/
	public static String rTrim(String str) throws Exception
	{
		int	i	= str.length();
		try
		{
			while (str.charAt(i - 1) == ' ')
			{
				i--;
			}
			return str.substring(0, i);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".rTrim Exception: \r\n" + err.toString());
		}
	}

	/**
	檢查傳入字串的長度是否未超過設定長度
	@param	str	原始字串
	@param	strLen	檢核的長度
	@return 檢查的結果
	*/
	public static boolean chkLen(String str, int strLen) throws Exception
	{
		try
		{
			if(str.length() <= strLen)
				return true;
			else
				return false;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".chkLen Exception: \r\n" + err.toString());
		}
	}

	/**
	Exp: putCenter("X", 3) --> ' X '
	將指定 str 放入指定長度 strLen 的中間位置，其他沒補滿的位置以半形空白代替
	@param	str	指定字串內容
	@param	strLen	指定傳回字串的長度大小
	@return	置放於中間位置的指定長度字串
	*/
	public static String putCenter(String str, int strLen) throws Exception
	{
		String	result	= "";
		try
		{
			if(chkLen(str, strLen))
			{
				int	i	= (strLen - str.length()) / 2;

				result	= space(i, 1) + str + space((strLen - str.length() - i), 1);
			}

			return result;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".putCenter Exception: \r\n" + err.toString());
		}

	}

	/**
	傳回指定長度的半形空白字串。
	@param 	len		指定長度
	@param	spaceType	空白種類 0 - Html 空白 &nbsp;&nbsp;, 1 - 半形空白
	@return 傳回指定長度的半形空白字串。
	*/
	public static String space(int len, int spaceType) throws Exception
	{
		String	result	= "";
		try
		{
			switch(spaceType)
			{
				case 0:
					result	= cloneStr("&nbsp;&nbsp;", len);
					break;
				case 1:
					result	=  cloneStr(" ", len);
					break;
				default:
					throw new Exception(className + ".space Exception: \r\n 沒有這種空白種類:" + spaceType);
			}

			return result;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".space Exception: \r\n" + err.toString());
		}
	}

	/**
	*EX: "abcabcabc" = cloneStr("abc", 3)
	傳回一特定長度的重複字元字串
	@param 	str	指定要重覆的字串內容
	@param 	len 	指定要重覆的次數
	@return 傳回重覆次數後的字串內容
	*/
	public static String cloneStr(String str, int len) throws Exception
	{
		StringBuffer	strBuffer	= new StringBuffer();
		int		i		= len;
		try
		{
			while (i > 0)
			{
				i--;
				strBuffer.append(str);
			}
			return strBuffer.toString();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".cloneStr(String, int) Exception: \r\n" + err.toString());
		}
	}

	/**
	*EX: "000" = cloneStr('0', 3)
	傳回一特定長度的重複字元字串
	@param 	charStr	指定要重覆的字串內容
	@param 	len 	指定要重覆的次數
	@return 傳回重覆次數後的字串內容
	*/
	public static String cloneStr(char charStr, int len) throws Exception
	{
		StringBuffer	strBuffer	= new StringBuffer();
		int		i		= len;
		try
		{
			while (i > 0)
			{
				i--;
				strBuffer.append(charStr);
			}
			return strBuffer.toString();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".cloneStr(char, int) Exception: \r\n" + err.toString());
		}
	}

	/**
	將字串內容 str 以指定長度 strLen 傳出，不足長度者，以指定字元 fillChar 補足
	@param 	str		字串的原始內容
	@param 	strLen		傳回的字串指定長度
	@param 	fillChar	指定字元
	@return 傳回補足後字串資料。
	*/
	public static String fillStr(String str, int strLen, char fillChar) throws Exception
	{
		try
		{
			if(chkLen(str, strLen))
				return cloneStr(fillChar, strLen - str.length()) + str;
			else
				return str;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".fillStr Exception: \r\n" + err.toString());
		}
	}

	/**
	將含有半形的字元 str 改為全形的傳回
	@param	charStr	含有半形的字元
	@return 傳回已改為全形的字元
	*/
	public static String charToFullSize(char charStr) throws Exception
	{
		try
		{
			String		result		= charStr+"";
			StringBuffer	strBuffer	= new StringBuffer();
			strBuffer.append(charStr);
			byte[]	byteAry	= strBuffer.toString().getBytes();

			//為全形字則傳回自己
			if (byteAry.length >= 2)
				result	= strBuffer.toString();
			else
			{
				byte[]	resultByteAry	= {0, 0};
				resultByteAry[0]	= -94;
				int	currByteNum	= Integer.parseInt(Byte.toString(byteAry[0]));

				//0 - 9 --> ０ - ９
				if (currByteNum >= 48 && currByteNum <= 57)
					resultByteAry[1]	= Byte.parseByte(Integer.toString(currByteNum - 129));

				//a - z, A - Z --> Ａ - Ｚ
				if ((currByteNum >= 97 && currByteNum <= 122) || (currByteNum >= 65 && currByteNum <= 90))
				{
					//a - z
					if (currByteNum >= 97 && currByteNum <= 122)
						resultByteAry[1]	= Byte.parseByte(Integer.toString(currByteNum - 146));
					else	//A - Z
						resultByteAry[1]	= Byte.parseByte(Integer.toString(currByteNum - 114));
				}
				result	= new String(resultByteAry, "MS950");
	      		}
	      		if (charStr == ',')
	      			result	= "，";
	      		if (charStr == '(')
	      			result	= "（";
	      		if (charStr == ')')
	      			result	= "）";
	      		if (charStr == ' ')
	      			result	= "　";
	        	return result;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".charToFullSize Exception: \r\n" + err.toString());
		}
	}

	/**
	將傳入的字串中，有半形的英文或數字改為全形後傳出
	@param 	str	原始的字串內容
	@return 傳回將半形改為全形的字串傳出
	*/
	public static String strToFullSize(String str) throws Exception
	{
		if (str.trim().equals(""))
			return "";

		StringBuffer	strBuffer	= new StringBuffer();
		try
		{
			char[]	charAry	= str.toCharArray();

			for(int i = 0; i < charAry.length; i++)
			{
				strBuffer.append(charToFullSize(charAry[i]));
			}

			return strBuffer.toString();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".strToFullSize Exception: \r\n" + err.toString());
		}
	}

	/**
	將傳入的 str 字串中，有 oldStr 者，以 newStr 取代後傳出
	@param 	str	原始的字串內容
	@param 	oldStr	要被取代的字串內容
	@param 	newStr 	要取代後的字串內容
	@return 傳回取代後的字串
	*/
	public static String replace(String str, String oldStr, String newStr) throws Exception
	{
		try
		{
			String		tmpStr		= str;
			StringBuffer	strBuffer	= new StringBuffer();
			int		pos1		= 0;
			int		pos2		= 0;

			while(tmpStr.indexOf(oldStr) != -1)
			{
				pos1	= tmpStr.indexOf(oldStr);
				pos2	= pos1 + oldStr.length();
				strBuffer.append(tmpStr.substring(0, pos1));
				strBuffer.append(newStr);
				tmpStr	= tmpStr.substring(pos2);
			}
			strBuffer.append(tmpStr);

			return strBuffer.toString();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".replace Exception: \r\n" + err.toString());
		}
	}

	/**
	*將字串轉成一維陣列
	@param str	欲切割的字串
	@param delim	字串的分隔符號
	@return 回傳一維字串陣列
	*/
	public static String[] split(String str, String delim) throws Exception
	{
		try
		{
			int		aryLength	= 1;
			int		strIndex	= -1;
			while (str.indexOf(delim, strIndex) != -1)
			{
				strIndex	= str.indexOf(delim, strIndex) + 1;
				aryLength++;
			}

			String[]	strAry		= new String[aryLength];
			int		aryIndex	= 0;
			strIndex	= -1;
			if (str.indexOf(delim) == -1)
				strAry[0]	= str;
			else
				strAry[0]	= str.substring(0, str.indexOf(delim));

			while (str.indexOf(delim, strIndex) != -1)
			{
				aryIndex++;
				strIndex	= str.indexOf(delim, strIndex) + 1;
				if (str.indexOf(delim, strIndex) == -1)
					strAry[aryIndex]	= str.substring(strIndex + delim.length() - 1);
				else
					strAry[aryIndex]	= str.substring(strIndex + delim.length() - 1, str.indexOf(delim, strIndex));
			}
			return strAry;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".split Exception: \r\n" + err.toString());
		}
	}


	/**
	有關Object部份的null檢核，若屬null的狀況，則傳回defaultvalue的值
	@param		o	        原始的Object
	@param		defaultvalue	預設的String
	@return		String
	*/
	public static String checkNull(Object o, String defaultvalue) throws Exception
	{
		try
		{
			if ( o == null || ((String)o).trim().length() == 0 || ((String)o).equals("null") )
				return defaultvalue;
			else
				return o.toString();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".checkNull Exception: \r\n" + err.toString());
		}
	}

	/**
	有關Object部份的null檢核，若屬null的狀況，則傳回 true
	@param		o	        原始的Object
	@return		boolean
	*/
	public static boolean checkNull(Object o) throws Exception
	{
		try
		{
			if ( o == null || ((String)o).trim().length() == 0 || ((String)o).equals("null") )
				return true;
			else
				return false;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".checkNull Exception: \r\n" + err.toString());
		}
	}


	/**
	將字串中的值，前補0的字串刪除後顯示純數字部份字串, 例checkInt("00070", "0"), 則回傳 "70", 但若 checkInt(xxx, "0") 其傳入值 xxx 為 null, 則回傳 "0"
	@param		o	        原始字串
	@param		defaultvalue	預設的String
	@return		回傳預設值或純數字部份字串
	*/
	public static String checkInt(Object o, String defaultvalue) throws Exception
	{
		String result = checkNull(o, defaultvalue);
		try
		{
			result = "" + Integer.parseInt(result);
			return result;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".checkInt Exception: \r\n" + err.toString());
		}
	}

	/**
	呼叫網頁時 [使用 Get 方式, 另解: 帶問號(?)方式], 將參數 URLEncoder.encode() 處理
	@param  Object o
	@return String
	@throws Exception
	*/
	public static String encode(Object o) throws Exception
	{
		try
		{
			return URLEncoder.encode(checkNull(o, ""));
		}
		catch(Exception err)
		{
			throw new Exception(className + ".encode() Exception: \r\n" + err.toString());
		}
	}

	/**
	將數字字串轉換為中文的數字資料格式
	*/
	public static String getCNum(int strNum, int cNumType) throws Exception
	{
		try
		{
			return getCNum(Integer.toString(strNum), cNumType);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".getCNum(int, int) Exception: \r\n" + err.toString());
		}
	}

	/**
	將數字字串轉換為中文的數字資料格式
	*/
	public static String getCNum(double strNum, int cNumType) throws Exception
	{
		try
		{
			return getCNum(Double.toString(strNum), cNumType);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".getCNum(double, int) Exception: \r\n" + err.toString());
		}
	}

	/**
	將數字字串轉換為中文的數字資料格式
	*/
	public static String getCNum(long strNum, int cNumType) throws Exception
	{
		try
		{
			return getCNum(Long.toString(strNum), cNumType);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".getCNum(double, int) Exception: \r\n" + err.toString());
		}
	}

	/**
	將數字字串轉換為中文的數字資料格式 Exp: 12345.06 種類 0:一萬二千零四十五點零六, 1:壹萬貳仟零肆拾伍點零陸, 2:一萬二千零四十五點０六, 1234506 3:一二０四五０六
	須注意種類'3'一般為轉換號的數字不可輸入非數字型態資料
	@param	strNum		字串格式的數字內容
	@param	cNumType	轉換格式
	@return	傳回中文的數字字碼
	*/
	public static String getCNum(String strNum, int cNumType) throws Exception
	{
		if (strNum.equals(""))
			return "";

		StringBuffer	cStrNumSection		= new StringBuffer();		//中文數字片段
		StringBuffer	resultStr		= new StringBuffer();		//回傳值
		try
		{
			String	chineseNum		= "";				//設定用
			String	chineseNum0		= "零一二三四五六七八九";	//中文數字型態一
			String	chineseNum1		= "零壹貳參肆伍陸柒捌玖";	//中文數字型態二
			String	chineseNum2		= "０一二三四五六七八九";	//中文數字型態三
			String	chineseNum3		= "０一二三四五六七八九";	//中文數字型態四(直接轉換)

			String	tmpStrNum		= strNum.toString();		//暫存傳入的數字
			String	tmpIntStrNum		= "";				//暫存傳入的數字(整數部份)
			String	tmpStrNumSection	= "";				//數字片段

			int	posOfDecimalPoint	= 0;				//小數點位置
			int	digit			= 0;				//位數
			boolean	inZero			= true;				//是否須加"零"
			boolean	minus			= true;				//是否負數

			//依種類判別中文數字型態
			if(cNumType == 0)
				chineseNum	= chineseNum0;
			if(cNumType == 1)
				chineseNum	= chineseNum1;
			if(cNumType == 2)
				chineseNum	= chineseNum2;
			if(cNumType == 3)	//專門處理號的轉換
			{
				chineseNum	= chineseNum3;

				for(int i = -1; i < tmpStrNum.length() - 1; i++)
				{
					if (tmpStrNum.substring(i + 1, i + 2).equals("."))
					{
						resultStr.append("‧");
					}
					else
					{
						try
						{
							digit	= Integer.parseInt(tmpStrNum.substring(i + 1, i + 2));
						}
						catch(Exception e)
						{
							throw new Exception(className + ".getCNum() Exception: 【傳入參數包含非數字型態資料,  strNum:" + strNum + "】");
						}
						resultStr.append(chineseNum.substring(digit, digit + 1));
					}
				}
				return	resultStr.toString();
			}

			//判別是否負數
			if (tmpStrNum.charAt(0) == '-')	//負數
			{
				minus		= true;
				tmpStrNum	= tmpStrNum.substring(1, tmpStrNum.length());
			}
			else	//正數
				minus	= false;

			//取得小數點的位置
			posOfDecimalPoint	=  tmpStrNum.indexOf(".");

			//先處理整數的部分
			if (posOfDecimalPoint <= 0)
				tmpIntStrNum	= ConvertStr(tmpStrNum);
			else
				tmpIntStrNum	= ConvertStr(tmpStrNum.substring(0, posOfDecimalPoint));

			//從個位數起以每四位數為一小節
			for(int sectionIndex = 0; sectionIndex <= (tmpIntStrNum.length() - 1) / 4; sectionIndex++)
			{
				//取得四位數的字串資料
				if(sectionIndex * 4 + 4 < tmpIntStrNum.length())
					tmpStrNumSection	= tmpIntStrNum.substring(sectionIndex * 4, sectionIndex * 4 + 4);
				else
					tmpStrNumSection	= tmpIntStrNum.substring(sectionIndex * 4, tmpIntStrNum.length());

				//清空四位數的中文數字資料
				cStrNumSection.setLength(0);

				/* 以下的 i 控制: 個十百千位四個位數 */
				for(int i = 0; i < tmpStrNumSection.length(); i++)
				{
					digit	= Integer.parseInt(tmpStrNumSection.substring(i, i + 1));
					if (digit == 0)
					{
						// 1. 避免 '零' 的重覆出現
						// 2. 個位數的 0 不必轉成 '零'
						if (!inZero && i != 0)
							cStrNumSection.insert(0, "零");
						inZero	= true;
					}
					else
					{
						if(cNumType == 1)
						{
							switch(i)
							{
								case 1 :
									cStrNumSection.insert(0, "拾");
									break;
								case 2 :
									cStrNumSection.insert(0, "佰");
									break;
								case 3 :
									cStrNumSection.insert(0, "仟");
									break;
							}
						}
						else
						{
							switch(i)
							{
								case 1 :
									cStrNumSection.insert(0, "十");
									break;
								case 2 :
									cStrNumSection.insert(0, "百");
									break;
								case 3 :
									cStrNumSection.insert(0, "千");
									break;
							}
						}
						cStrNumSection.insert(0, chineseNum.substring(digit, digit + 1));
						inZero	= false;
					}
				}

				//加上該小節的位數
				if (cStrNumSection.length() == 0)
				{
					if (resultStr.length() > 0 && resultStr.substring(0, 1).equals("零"))
						resultStr.insert(0, "零");
				}
				else
				{
					switch(sectionIndex)
					{
						case 0:
							resultStr.append(cStrNumSection.toString());
							break;
						case 1:
							resultStr.insert(0, cStrNumSection.toString() + "萬");
							break;
						case 2:
							resultStr.insert(0, cStrNumSection.toString() + "億");
							break;
						case 3:
							resultStr.insert(0, cStrNumSection.toString() + "兆");
							break;
					}
				}
			}

			//處理小數點右邊的部分
			if (posOfDecimalPoint > 0)
			{
				resultStr.append("點");
				for(int i = posOfDecimalPoint; i < tmpStrNum.length() - 1; i++)
				{
					digit		= Integer.parseInt(tmpStrNum.substring(i + 1, i + 2));
					resultStr.append(chineseNum.substring(digit, digit + 1));
				}
			}

			//其他例外狀況的處理
			if (resultStr.length() == 0)
				resultStr.append("零");
			if (resultStr.length()>= 2 && (resultStr.toString().substring(0, 2).equals("一十") || resultStr.toString().substring(0, 2).equals("壹十")))
			{
				resultStr.deleteCharAt(0);
			}
			if (resultStr.toString().substring(0, 1).equals("點"))
				resultStr.insert(0, "零");

			/* 是否為負數 */
			if (minus)
				resultStr.insert(0, "負");

			return resultStr.toString();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".getCNum(String, int) Exception: \r\n" + err.toString());
		}
		finally
		{
			resultStr	= null;
			cStrNumSection	= null;
		}
	}

	/*
	將字串反向, 例如: 傳入 '1234', 傳回 '4321'
	*/
	public static String ConvertStr(String str) throws Exception
	{
		StringBuffer	resultStr	= new StringBuffer();
		try
		{
			for(int i = str.length() - 1; i >= 0; i--)
				resultStr.append(str.charAt(i));

			return resultStr.toString();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".ConvertStr() Exception: \r\n" + err.toString());
		}
		finally
		{
			resultStr	= null;
		}
	}

	/**
	將數字字串的零去掉傳回 Exp:001 --> 1
	@param		strNum	        數字字串
	@param		defaultvalue	預設的String
	@return		String
	*/
	public static String getNumStr(String strNum) throws Exception
	{
		try
		{
			if (strNum.equals(""))
				return strNum;

			return Long.toString(Long.parseLong(strNum));
		}
		catch(Exception err)
		{
			throw new Exception(className + ".getCNum(double, int) Exception: \r\n" + err.toString());
		}
	}

	/**
	將日期格式化為列印用日期 Exp: 0920101 --> 九十二年一月一日
	@param		dateStr	        日期
	@return		String
	*/
	public static String formatCDateForPrint(String dateStr) throws Exception
	{
		try
		{
			if (dateStr.length() < 7)
	      			return dateStr;

			String	yy	=util.getCNum(util.getNumStr(dateStr.substring(0, 3)), 2);
			String	mm	=util.getCNum(util.getNumStr(dateStr.substring(3, 5)), 2);
			String	dd	=util.getCNum(util.getNumStr(dateStr.substring(5)), 2);

			return yy + "年" + mm + "月" + dd + "日";
		}
		catch(Exception err)
		{
			throw new Exception(className + ".formatCDateForPrint(double, int) Exception: \r\n" + err.toString());
		}
	}
	   /**
     * 		將傳入的eSource字串中，有OldString者，以NewString取代後傳出
     *@param 	sSource		原始的字串內容
     *@param 	OldString	要被取代的字串內容
     *@param 	NewString 	要取代後的字串內容
     *@return 			傳回取代成NewString後的eSource
   */
   public static String StrTran(String inStr,String oldStr,String newStr) throws IOException
     {
      String result_0="";
      Vector vector1 = new Vector();
       try
       {
         

         int p1 = 0;
         while((p1=inStr.indexOf(oldStr)) != -1){
            int p2 = p1 + oldStr.length();
            String leftStr = inStr.substring(0,p1);
            vector1.addElement(leftStr);
            inStr = inStr.substring(p2);
         }
         vector1.addElement(inStr);

         int lenOfVec = vector1.size();
         if(lenOfVec!=0){
               String retString = (String)vector1.get(0);
               for(int i=1;i<lenOfVec;i++){
                   retString += newStr + vector1.get(i);
               }
               return retString;
         }else{
              return inStr;
         }
         
         
         
         
       }
      catch(Exception ex)
       {}
      vector1 = null;
       //回傳值
       return result_0;
   }

}