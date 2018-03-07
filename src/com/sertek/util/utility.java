 /*
 **************************************************************************************************
 *                         		      Sertek, Inc.
 *
 * 	CONFIDENTIAL AND PROPRIETARY SOURCE CODE OF Sertek INC.
 *
 * 	Copyright (c) 2000 Sertek, Inc. All Rights Reserved.
 * 	Use of this Source Code is subject to the terms of the applicable
 * 	license agreement from Sertek, Inc.  The copyright notice(s)
 * 	in this Source Code does not indicate actual or intended publication
 * 	of this Source Code.
 *
 *	File Name     : utility.java
 *	Author        : Felix Lin
 *	Created Date  : 2000/11/07
 *	Modified By   : Felix Lin
 *	Last Modified : 2000/11/07
 *	Description   : This program is a class of utility
 *
 *
 *****************************************************************************************************
*/
package com.sertek.util;

import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.*;

import javax.servlet.http.*;
/*
--------------------------------------------------------------------------------
問題單號：Bug #1951 - PCD0MH0950003 
修改摘要：卷面標的金額顯示
修改人員：Lewis Wang
修改日期：0960504
更新版本：V9608
--------------------------------------------------------------------------------
問題單號：Bug #701-KSHMC0930009、TNHMC0940004、TNHMC0940008
修改摘要：核章欄以上，增設送上訴（抗告）及確定日期及和解成立及撤回之總件數及其案號
修改人員：Kevin Lin
修改日期：0950620
更新版本：V9506
--------------------------------------------------------------------------------

*/
public class utility
  {

    private static String[] NumCode1 = new String[101];//造字檔第一組，101是為了方便對應
	private static String[] NumCode2 = new String[101];//造字檔第二組，101是為了方便對應

	private static String strNUMCODE1 = "";
	private static String strNUMCODE2 = "";


   /**
    *	建構子
   */
   public utility()
    {
        get_NumCode();
    }


/********************************************TEXT 處理部分*****************************/
   /**
    * 	清除str_i字串左邊的空白
    *@param	str_i	要清除空白的原始字串
    *@return 		清除左邊空白後的字串
   */
   public String Ltrim(String str_i) throws IOException
     {
      int i=0;
      try
       {
        while (str_i.charAt(i)==' ')
         {
          i = i+1;
         }
       }
      catch(Exception ex)
       {}
      //回傳值
      return str_i.substring(i,str_i.length());
     }

   /**
    * 	清除str_i字串右邊的空白
    *@param	str_i 	要清除空白的原始字串
    *@return 		清除右邊空白後的字串
   */
   public String Rtrim(String str_i) throws IOException
     {
      int i=str_i.length();
      try
       {
        while (str_i.charAt(i)==' ')
         {
          i = i-1;
         }
       }
      catch(Exception ex)
       {}
      //回傳值
      return str_i.substring(0,i);
     }


  /**
    **清除str_i字串前後的空白
    *@param 	str_i 	要清除空白的原始字串
    *@return 		清除前後邊空白後的字串
   */
   public String alltrim(String str_i) throws IOException
     {
       String result_0="";
       try
       {
        result_0=Ltrim(Rtrim(str_i));
        //	result_0 = str_i.trim();
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

   /**
     * 		將指定str_i放入指定長度length_i的中間位置，其他沒補滿的位置以半形空白代替
     *@param 	str_i 		指定字串內容
     *@param 	length_i 	指定傳回字串的長度大小
     *@return 			置放於中間位置的指定長度字串
   */
   public String put_center(String str_i,int length_i) throws IOException
     {
      String r="";
       try
       {
        	if(ChkLen(str_i,length_i)) {
        		int i = (length_i - str_i.length()) / 2;
        		r = space(i,1) + str_i + space((length_i-str_i.length()-i),1);
        	} else {
        		r = "";
        	}

       }
      catch(Exception ex)
       {}

       //回傳值
       return r;
   }

   /**
     *EX: "abcabcabc" = remaker_str("abc",3)

     * 		將指定的字串內容重覆指定次數後傳回
     *@param 	marker 		指定要重覆的字串內容
     *@param 	length_i 	指定要重覆的次數
     *@return 			傳回marker重覆次數後的字串內容
   */
   public String remarker_str(String marker,int length_i) throws IOException
     {
      String result_0="";
       try
       {
        	for(int i=0;i<length_i;i++) {
        		result_0 = result_0 + marker;
        	}
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }


   /**
     * 	將字串內的全形與半形空白全部去除
     *@param 	str_i 	字串的原始內容
     *@return 		傳回已去除所有空白後的字串
   */
   public String noAnyBlank(String str_i) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	char[] char1 = str_i.toCharArray();
        	for(int i=0;i<char1.length;i++) {
        		//  094/07/14 wang 加入    字元的檢查，在文采它也是空白
        		if(char1[i] != ' ' && char1[i] != '　' ) {
        			result_0 = result_0 + char1[i];
        		}
        	}

       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }


   /**
     * 	將字串內的半形空白全部去除
     *@param 	str_i 	字串的原始內容
     *@return 		傳回已去除所有半形空白後的字串
   */
   public String noblank(String str_i) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	char[] c = str_i.toCharArray();
        	for(int i=0;i<c.length;i++) {
        		//if(c[i] != ' ' && c[i] != '　') {
        		if(c[i] != ' ') {
        			result_0 = result_0 + c[i];
        		}
        	}
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }


   /**
     * 	將字串前的空白以0取代
     *@param 	str_i 	字串的原始內容
     *@return 		傳回已取代前面空白為0的字串內容
   */
   public String fillzero(String str_i) throws IOException
     {
      String result_0="";
      int length = str_i.length();
       try
       {
       		//implement part
        	/*int index = str_i.lastIndexOf(" ");
        	if(index > 0) {
        		result_0 = remarker_str("0",index) + str_i.substring((index+1),str_i.length());
        	} else {
        		result_0 = str_i;
        	}*/
        	if(length > 0) {
        		result_0 = fillFrontzero(Ltrim(str_i),length);
        	}
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

   /**
     * 	將字串內容以指定長度傳出，不足長度者，在字串後面以0補足指定長度。
     *@param 	str_i 	字串的原始內容
     *@param 	len 	傳回的字串指定長度
     *@return 		傳回指定長度的str_i的內容，不足長度者，在字串後面以0補足。
   */
   public String fillBackzero(String str_i,int len) throws IOException {
   	String result_0 = "";
   	try {
   		result_0 = fillzero((String) String.valueOf(str_i),len,1);
   	} catch(Exception ex) {}
   	return result_0;
   }

  /**
     * 	將字串內容以指定長度傳出，不足長度者，在前面以0補足指定長度。
     *@param 	str_i 	字串的原始內容
     *@param 	len 	傳回的字串指定長度
     *@return 		傳回指定長度的str_i的內容，不足長度者，以0補足。
   */
   public String fillFrontzero(String str_i,int len) throws IOException {
   	String result_0 = "";
   	try {
   		result_0 = fillzero((String) String.valueOf(str_i),len,0);
   	}catch(Exception ex) {}
   	return result_0 ;
   }

   /**
     * 	將字串內容以指定長度傳出，不足長度者，以0補足指定長度。
     *@param 	str_i 	字串的原始內容
     *@param 	len 	傳回的字串指定長度
     *@param 	flag  	0為在前面補足0，非0為在後面補足0
     *@return 		傳回指定長度的str_i的內容，不足長度者，以0補足。
   */
   public String fillzero(String str_i,int len,int flag) throws IOException {
   	String result_0 = "";
   	try {
   		//if(str_i.length() >= len) result_0 = str_i;
   		result_0 = str_i;

   		if(result_0.length() < len) {
   			for(int i=0;i<(len-str_i.length());i++) {
   				if(flag == 0) result_0 = "0" + result_0;
   				else result_0 = result_0 + "0";
   			}
   			//result_0 = result_0 + str_i;
   		}
   	} catch(Exception ex) {}
   	return result_0;
   }

  /**
     * 		將字串內容以指定長度傳出，不足長度者，以半形空白補足
     *@param 	str_i 		字串的原始內容
     *@param 	length_i 	傳回的字串指定長度
     *@param 	flag_i  	0為在前面補足半形空白，非0為在後面補足半形空白
     *@return 			傳回指定長度的str_i的內容，不足長度者，以半形空白補足。
   */
   public String Fillblank(String str_i,int Length_i,int flag_i) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	if(ChkLen(str_i,Length_i)) {
  			if(flag_i == 0) {
  				try {
  					result_0 = space(Length_i-str_i.length(),0) + str_i;
  				} catch(Exception ex) {}
  			} else {
  				try {
  					result_0 = str_i + space(Length_i-str_i.length(),0);
  				} catch(Exception ex) {}

  			}
        	} else {
        		result_0 = str_i;
        	}
       }catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

   /**
     * 		將字串內容以指定長度傳出，不足長度者，以半形空白補足
     *@param 	str_i 		字串的原始內容
     *@param 	length_i 	傳回的字串指定長度
     *@param 	flag_i  	0為在前面補足半形空白，非0為在後面補足半形空白
     *@return 			傳回指定長度的str_i的內容，不足長度者，以半形空白補足。
   */
   public String Fillspace(String str_i,int Length_i,int flag_i) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	if(ChkLen(str_i,Length_i,"UTF-8")) {
  			if(flag_i == 0) {
  				try {
  					result_0 = space(Length_i-str_i.getBytes("MS950").length,-1) + str_i;
  				} catch(Exception ex) {}
  			} else {
  				try {
  					result_0 = str_i + space(Length_i-str_i.getBytes("MS950").length,-1);
  				} catch(Exception ex) {}

  			}
        	} else {
        		result_0 = str_i;
        	}
       }catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

   /**
     * 		傳回指定長度的半形空白字串。
     *@param 	length_i 	指定長度
     *@return 			傳回指定長度的半形空白字串。
   */
   public String space(int Length_i,int flag) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
    	   	if(flag==-1)
    	   		result_0 = remarker_str("　",Length_i/2);
    	   	else if(flag==0)
        	      result_0 = remarker_str("&nbsp;&nbsp;",Length_i);
        	else
        	      result_0 = remarker_str(" ",Length_i);
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }


   /**
     * 	將半形的字改為全形的字傳回
     *@param 	ck_i	半形的char
     *@return 		傳回全形的char
   */
   public String _2fullsize(char ck_i) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	/*Hashtable ht = getfullString();
        	Character c = new Character(ck_i);
        	if(ht.get(c.toString())!=null)
        		result_0 = result_0 + ht.get(c.toString());
        	*/
        	/*
        	if(ck_i == 'a' || ck_i == 'A') result_0 = result_0 + "Ａ";
        	else if(ck_i == 'b' || ck_i == 'B') result_0 = result_0 + "Ｂ";
        	else if(ck_i == 'c' || ck_i == 'C') result_0 = result_0 + "Ｃ";
        	else if(ck_i == 'd' || ck_i == 'D') result_0 = result_0 + "Ｄ";
        	else if(ck_i == 'e' || ck_i == 'E') result_0 = result_0 + "Ｅ";
        	else if(ck_i == 'f' || ck_i == 'F') result_0 = result_0 + "Ｆ";
        	else if(ck_i == 'g' || ck_i == 'G') result_0 = result_0 + "Ｇ";
        	else if(ck_i == 'h' || ck_i == 'H') result_0 = result_0 + "Ｈ";
        	else if(ck_i == 'i' || ck_i == 'I') result_0 = result_0 + "Ｉ";
        	else if(ck_i == 'j' || ck_i == 'J') result_0 = result_0 + "Ｊ";
        	else if(ck_i == 'k' || ck_i == 'K') result_0 = result_0 + "Ｋ";
        	else if(ck_i == 'l' || ck_i == 'L') result_0 = result_0 + "Ｌ";
        	else if(ck_i == 'm' || ck_i == 'M') result_0 = result_0 + "Ｍ";
        	else if(ck_i == 'n' || ck_i == 'N') result_0 = result_0 + "Ｎ";
        	else if(ck_i == 'o' || ck_i == 'O') result_0 = result_0 + "Ｏ";
        	else if(ck_i == 'p' || ck_i == 'P') result_0 = result_0 + "Ｐ";
        	else if(ck_i == 'q' || ck_i == 'Q') result_0 = result_0 + "Ｑ";
        	else if(ck_i == 'r' || ck_i == 'R') result_0 = result_0 + "Ｒ";
        	else if(ck_i == 's' || ck_i == 'S') result_0 = result_0 + "Ｓ";
        	else if(ck_i == 't' || ck_i == 'T') result_0 = result_0 + "Ｔ";
        	else if(ck_i == 'u' || ck_i == 'U') result_0 = result_0 + "Ｕ";
        	else if(ck_i == 'v' || ck_i == 'V') result_0 = result_0 + "Ｖ";
        	else if(ck_i == 'w' || ck_i == 'W') result_0 = result_0 + "Ｗ";
        	else if(ck_i == 'x' || ck_i == 'X') result_0 = result_0 + "Ｘ";
        	else if(ck_i == 'y' || ck_i == 'Y') result_0 = result_0 + "Ｙ";
        	else if(ck_i == 'z' || ck_i == 'Z') result_0 = result_0 + "Ｚ";
        	else if(ck_i == '1') result_0 = result_0 + "１";
        	else if(ck_i == '2') result_0 = result_0 + "２";
        	else if(ck_i == '3') result_0 = result_0 + "３";
        	else if(ck_i == '4') result_0 = result_0 + "４";
        	else if(ck_i == '5') result_0 = result_0 + "５";
        	else if(ck_i == '6') result_0 = result_0 + "６";
        	else if(ck_i == '7') result_0 = result_0 + "７";
        	else if(ck_i == '8') result_0 = result_0 + "８";
        	else if(ck_i == '9') result_0 = result_0 + "９";
        	else if(ck_i == '0') result_0 = result_0 + "０";
        	else result_0 = "";
        	*/
        	switch (ck_i) {
        	case 'a' :
        	case 'A' :
        		result_0 = result_0 + "Ａ";
        		break;
        	case 'b' :
        	case 'B' :
        		result_0 = result_0 + "Ｂ";
        		break;
        	case 'c' :
        	case 'C' :
        		result_0 = result_0 + "Ｃ";
        		break;
        	case 'd' :
        	case 'D' :
        		result_0 = result_0 + "Ｄ";
        		break;
        	case 'e' :
        	case 'E' :
        	  	result_0 = result_0 + "Ｅ";
        	  	break;
        	case 'f' :
        	case 'F' :
        		result_0 = result_0 + "Ｆ";
        		break;
        	case 'g' :
        	case 'G' :
        		result_0 = result_0 + "Ｇ";
        		break;
        	case 'h' :
        	case 'H' :
        		result_0 = result_0 + "Ｈ";
        		break;
        	case 'i' :
        	case 'I' :
        		result_0 = result_0 + "Ｉ";
        		break;
        	case 'j' :
        	case 'J' :
        		result_0 = result_0 + "Ｊ";
        		break;
        	case 'k' :
        	case 'K' :
        		result_0 = result_0 + "Ｋ";
        		break;
        	case 'l' :
        	case 'L' :
        		result_0 = result_0 + "Ｌ";
        		break;
        	case 'm' :
        	case 'M' :
        		result_0 = result_0 + "Ｍ";
        		break;
        	case 'n' :
        	case 'N' :
        		result_0 = result_0 + "Ｎ";
        		break;
        	case 'o' :
        	case 'O' :
        		result_0 = result_0 + "Ｏ";
        		break;
        	case 'p' :
        	case 'P' :
        		result_0 = result_0 + "Ｐ";
        		break;
        	case 'q' :
        	case 'Q' :
        		result_0 = result_0 + "Ｑ";
        		break;
        	case 'r' :
        	case 'R' :
        		result_0 = result_0 + "Ｒ";
        		break;
        	case 's' :
        	case 'S' :
        		result_0 = result_0 + "Ｓ";
        		break;
        	case 't' :
        	case 'T' :
        		result_0 = result_0 + "Ｔ";
        		break;
        	case 'u' :
        	case 'U' :
        		result_0 = result_0 + "Ｕ";
        		break;
        	case 'v' :
        	case 'V' :
        		result_0 = result_0 + "Ｖ";
        		break;
        	case 'w' :
        	case 'W' :
        		result_0 = result_0 + "Ｗ";
        		break;
        	case 'x' :
        	case 'X' :
        		result_0 = result_0 + "Ｘ";
        		break;
        	case 'y' :
        	case 'Y' :
        		result_0 = result_0 + "Ｙ";
        		break;
        	case 'z' :
        	case 'Z' :
        		result_0 = result_0 + "Ｚ";
        		break;
        	case '1' :
        		result_0 = result_0 + "１";
        		break;
        	case '2' :
        		result_0 = result_0 + "２";
        		break;
        	case '3' :
        		result_0 = result_0 + "３";
        		break;
        	case '4' :
        		result_0 = result_0 + "４";
        		break;
        	case '5' :
        		result_0 = result_0 + "５";
        		break;
        	case '6' :
        		result_0 = result_0 + "６";
        		break;
        	case '7' :
        		result_0 = result_0 + "７";
        		break;
        	case '8' :
        		result_0 = result_0 + "８";
        		break;
        	case '9' :
        		result_0 = result_0 + "９";
        		break;
        	case '0' :
        		result_0 = result_0 + "０";
        		break;
        	default :
        		result_0 = "";
        		break;
        	}
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }


   /**
     * 	將英文字與數字的全形與半形存入hashtableu以便取用
     *@param
     *@return 		傳回Hashtable
   */
   private Hashtable getfullString() throws IOException {
   	Hashtable ht = new Hashtable();
   	String[] lowerapher = {"a","b","c","d","e","f","g","h","i","j","k",
   	                       "l","m","n","o","p","q","r","s","t","u","v",
   	                       "w","x","y","z"};
   	String[] upperapher = {"A","B","C","D","E","F","G","H","I","J","K",
   	                       "L","M","N","O","P","Q","R","S","T","U","V",
   	                       "W","X","Y","Z"};
   	String[] fullapher = {"Ａ","Ｂ","Ｃ","Ｄ","Ｅ","Ｆ","Ｇ","Ｈ","Ｉ",
   			      "Ｊ","Ｋ","Ｌ","Ｍ","Ｎ","Ｏ","Ｐ","Ｑ","Ｒ",
   			      "Ｓ","Ｔ","Ｕ","Ｖ","Ｗ","Ｘ","Ｙ","Ｚ"};
	String[] number = {"1","2","3","4","5","6","7","8","9","0"};
	String[] fullnumber = {"１","２","３","４","５","６","７","８","９","０"};
   	for(int i=0;i<26;i++) {
   		ht.put(lowerapher[i],fullapher[i]);
   		ht.put(upperapher[i],fullapher[i]);
   	}
   	for(int i=0;i<10;i++) {
   		ht.put(number[i],fullnumber[i]);
   	}
   	return ht;
   }

   /**
     * 	將傳入的字串中，有半形的英文或數字改為全形後傳出
     *@param 	str_i	原始的字串內容
     *@return 		傳回將半形改為全形的字串傳出
   */
   public String str2fullsize(String str_i) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	char[] c = str_i.toCharArray();
        	for(int i=0;i<c.length;i++) {
        		result_0 = result_0 + _2fullsize(c[i]);
        	}
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

   /**
     * 		將傳入的eSource字串中，有OldString者，以NewString取代後傳出
     *@param 	sSource		原始的字串內容
     *@param 	OldString	要被取代的字串內容
     *@param 	NewString 	要取代後的字串內容
     *@return 			傳回取代成NewString後的eSource
   */
   public String StrTran(String inStr,String oldStr,String newStr) throws IOException
     {
      String result_0="";
       try
       {
         Vector vector1 = new Vector();

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

       //回傳值
       return result_0;
   }

   /**
     * 		利用中介字元  保有資料中原有的 ';'，使list能正常運作
     *@param 	direction_i	IN or OUT
     *@param 	str_i		原始字串內容
     *@return 			傳回保有資料中原有的 ';'
   */
   public String Marker(String direction_i,String str_i) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	String s = "；";
        	if(direction_i.toUpperCase().equals("IN")) {
        		result_0 = StrTran(str_i,";",s);
        	}
        	if(direction_i.toUpperCase().equals("OUT")) {
        		result_0 = StrTran(str_i,s,";");
        	}
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }


  /**
     * 		利用中介字元  保有資料中原有的 ';'，使list能正常運作
     *@param 	direction_i	IN or OUT
     *@param 	str_i		原始字串內容
     *@return 			傳回保有資料中原有的 ';'
   */
   public String addMask(String str_i,String mask_i) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }
/*
{ =============================================
  傳入字串 和 mask 回傳經mask後的 字串
  對於空字串的處理也需注意
  ============================================ }
function  TDM1._addMask( str_i, mask_i : string): string;
var
  msk_, i_ : smallint;
  result_o   : string;
begin
  try
    i_     := 0;
    result_o := '';
    // 由1起算
    if (_alltrim(uppercase(mask_i)) <> 'NULL') then begin
      for msk_ := 1 to length(mask_i) do begin
        if ((uppercase(mask_i[msk_]) = 'C') or
            (uppercase(mask_i[msk_]) = '9'))
        then begin
          i_ := i_+ 1;
          if (i_ <= length(str_i)) then begin
            result_o := result_o+ str_i[i_];
          end else begin
            // 為了顯示制定格式
            result_o := result_o+ ' ';
          end;{_if}
        end else begin
          result_o := result_o+ mask_i[msk_];
        end;{_if}
      end;{_for}
    end else begin
      result_o := str_i;
    end;{_if}
    //
    result := result_o;
  except
  end;{_try}
end;{_addMask}
*/

   /**
     * 		利用中介字元  保有資料中原有的 ';'，使list能正常運作
     *@param 	direction_i	IN or OUT
     *@param 	str_i		原始字串內容
     *@return 			傳回保有資料中原有的 ';'
   */
   public String noMask(String str_i,String mask_i) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

/*
{ =============================================
  由格式字串中 移除 mask 成可存入資料庫的字串
  ============================================ }
function  TDM1._noMask( str_i, mask_i : string): string;
var
  msk_     : smallint;
  result_o : string;
begin
  try
    result_o := ''; // 確定要保持 空字串
    if (_alltrim(uppercase(mask_i)) <> 'NULL') then begin
      // 由1起算
      for msk_ := 1 to length(mask_i) do begin
        if ((uppercase(mask_i[msk_]) = 'C') or
            (uppercase(mask_i[msk_]) = '9'))
        then begin
          // 為了確保 maxlength 的需求
          // 但是會存留因 addmask顯示格式的空白
          // 且這些空白並非是使用者所輸入
          if (msk_ <= length(str_i)) then begin
            result_o := result_o+ str_i[msk_];
          end else begin
            // 不應用空白補足以免破壞原值
            // 讓外部呼叫的函數決定是否alltrim
            //result_o := result_o+ ' ';
          end;{_if}
        end;{_if}
      end;{_for}
      //
    end else begin
      if (length(str_i) > 0) then begin
        result_o := str_i;
      end else begin
        result_o := ' ';
      end;{_if}
    end;{_if}
    result := result_o;
  except
  end;{_try}
end;{_noMask}
*/

/*******************************數字處理部分***********************************/

   /**
     * 		將阿拉伯數字轉成中文數字字串
     *@param	dblArabic	0,1,2,3,4,5,6,7,8,9相關內容的字串
     *@return 			傳回中文的數字字碼{"零","一","二","三","四","五","六","七","八","九"}
   */
   public String NumtoCNum(String dblArabic) throws IOException {
   	String result_0="";
   	try {
   		result_0 = result_0 + NumtoCNum(dblArabic,0);
   	} catch(Exception ex) {}
   	return result_0;

   }

   /**
     * 		將阿拉伯數字轉成中文數字字串
     *@param	dblArabic	0,1,2,3,4,5,6,7,8,9相關內容的字串
     *@param	flag		0為取出{"零","一","二","三","四","五","六","七","八","九"}，非0取出{"零","壹","貳","參","肆","伍","陸","柒","捌","玖"};
     *@return 			傳回中文的數字字碼
   */
   public String NumtoCNum(String dblArabic,int flag) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	String[] numbern1 = {"零","一","二","三","四","五","六","七","八","九"};
        	String[] numberc  = {"零","壹","貳","參","肆","伍","陸","柒","捌","玖"};
        	String[] numbern2 = {"０","一","二","三","四","五","六","七","八","九"};
        	String[] number   = {"","@","","","|","","","","",""};;

        	switch (flag) {
        	   case 0 : number = numbern1;break;
        	   case 1 : number = numberc; break;
        	   case 2 : number = numbern2;break;
                }
        	result_0 = dblArabic;
        	for(int i=0;i<10;i++) {
        		result_0 = StrTran(result_0,String.valueOf(i),number[i]);

        	}
        	result_0 = StrTran(result_0,".","點");
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

   /**
     * 		將中文數字字串轉成阿拉伯數字
     *@param	dblArabic	0,1,2,3,4,5,6,7,8,9相關內容的字串
     *@param	flag		0為取出{"零","一","二","三","四","五","六","七","八","九"}，非0取出{"零","壹","貳","參","肆","伍","陸","柒","捌","玖"};
     *@return 			傳回中文的數字字碼
   */
   public String CNumtoNum(String dblArabic,int flag) throws IOException
     {
      String result_0="";
       try
       {
        //implement part
        	String[] numbern1 = {"零","一","二","三","四","五","六","七","八","九"};
        	String[] numberc  = {"零","壹","貳","參","肆","伍","陸","柒","捌","玖"};
        	String[] numbern2 = {"０","一","二","三","四","五","六","七","八","九"};
        	String[] number   = {"","@","","","|","","","","",""};;

        	switch (flag) {
        	   case 0 : number = numbern1;break;
        	   case 1 : number = numberc; break;
        	   case 2 : number = numbern2;break;
                }
        	result_0 = dblArabic;
        	for(int i=0;i<10;i++) {
        		result_0 = StrTran(result_0,String.valueOf(i),number[i]);

        	}
        	result_0 = StrTran(result_0,".","點");
       }
      catch(Exception ex)
       {}

       //回傳值
       return result_0;
   }

/*
//將阿拉伯數字轉成中文數字字串
function Tdm1._NumtoCNum(dblArabic: string): string;
var
   _ChineseNumeric : string;
   result_o        : string;
   i,iDigit          : integer;
begin
   _ChineseNumeric := '０一二三四五六七八九';
   for i:=1 to length(dblArabic) do begin
       if (dblArabic[i]>='0' )and (dblArabic[i]<='9' )then begin
           iDigit := Ord(dblArabic[i]) - 48;
           result_o:=result_o+Copy(_ChineseNumeric, 2 * iDigit + 1, 2);
       end else begin
           result_o:=result_o+dblArabic[i];
       end;{if}
   end;{for}
   result:=result_o;
end;*/



   /*
     *Num2CNum(10002.34) ==> 一萬零二點三四

     * 		將阿拉伯數字轉成中文數字字串
     *@param	dblArabic	double的數字內容
     *@return 			傳回中文的數字字碼
   */
   public String Num2CNum(double dblArabic) throws IOException {
   	String result_0 = "";
   	try {
   		result_0 = Num2CNum(dblArabic,0);
   	} catch(Exception ex) {}
   	return result_0;
   }
   /**
     *Num2CNum(10002.34) ==> 一萬零二點三四

     * 		將阿拉伯數字轉成中文數字字串
     *@param	dblArabic	double的數字內容
     *@param	flag		0為取出{"零","一","二","三","四","五","六","七","八","九"}，非0取出{"零","壹","貳","參","肆","伍","陸","柒","捌","玖"};
     *@return 			傳回中文的數字字碼
   */

    public String Num2CNum(double dblArabic,int type) throws IOException
    {
        String result_0="";
        try {

           String sArabic="";
           if((dblArabic-(int)(dblArabic))>0)
              sArabic=String.valueOf(dblArabic);
           else
             sArabic=String.valueOf((int)dblArabic);

           //if((dblArabic-(long)(dblArabic))>0.0)
           //   sArabic=new DecimalFormat("#############.00").format(dblArabic);
           //else
           //  sArabic=new  DecimalFormat("#############").format((long)dblArabic);

           result_0 = Num2CNum(sArabic,type);
         } catch(Exception ex) {}
   	return result_0;
    }



    /*
     *Num2CNum(10002.34) ==> 一萬零二點三四

     * 		將阿拉伯數字轉成中文數字字串
     *@param	dblArabic	String的數字內容
     *@return 			傳回中文的數字字碼
   */
   public String Num2CNum(String dblArabic) throws IOException {
   	String result_0 = "";
   	try {
   		if (!"0".equals(dblArabic)) {
   			result_0 = Num2CNum(dblArabic,0);
   		}
   	} catch(Exception ex) {}
   	return result_0;
   }
   /**
     *Num2CNum(10002.34) ==> 一萬零二點三四

     * 		將阿拉伯數字轉成中文數字字串
     *@param	dblArabic	double的數字內容
     *@param	flag		0為取出{"零","一","二","三","四","五","六","七","八","九"}，非0取出{"零","壹","貳","參","肆","伍","陸","柒","捌","玖"};
     *@return 			傳回中文的數字字碼
   */

        public String Num2CNum(String dblArabic,int type) throws IOException
        {
          String _ChineseNumeric =  "零一二三四五六七八九";
          String _ChineseNumeric0 = "零一二三四五六七八九";
          String _ChineseNumeric1 = "零壹貳肆伍陸柒捌玖";
          String _ChineseNumeric2 = "０一二三四五六七八九";
          String sArabic="";
          String sIntArabic="";
          int iPosOfDecimalPoint=0;
          int iDigit=0;
          String sSectionArabic="";
          String sSection="";
          boolean bInZero=true;
          boolean bMinus=true;

          String Result="";
          bInZero = true;
          sArabic=dblArabic;
          if(type==0)
            _ChineseNumeric=_ChineseNumeric0;
          if(type==1)
            _ChineseNumeric=_ChineseNumeric1;
          if(type==2)
            _ChineseNumeric=_ChineseNumeric2;

          if (sArabic.charAt(0)=='-')
          {
            bMinus = true;
            sArabic = sArabic.substring(1,sArabic.length());
          }
          else
            bMinus = false;
          iPosOfDecimalPoint = sArabic.indexOf(".");  /* 取得小數點的位置 */
          /* 先處理整數的部分 */
          if (iPosOfDecimalPoint <= 0)
            sIntArabic = ConvertStr(sArabic);
          else
            sIntArabic = ConvertStr(sArabic.substring(0, iPosOfDecimalPoint));
          /* 從個位數起以每四位數為一小節 */
          for(int iSection=0;iSection<=((sIntArabic.length()-1)/4);iSection++)
          {
            if((iSection * 4 + 4)<sIntArabic.length())
               sSectionArabic = sIntArabic.substring(iSection * 4 , iSection * 4 + 4);
            else
               sSectionArabic = sIntArabic.substring(iSection * 4 , sIntArabic.length());
            sSection = "";
            /* 以下的 i 控制: 個十百千位四個位數 */
            for(int i= 0; i<sSectionArabic.length();i++)
            {
              iDigit = Integer.parseInt(sSectionArabic.substring(i,i+1));
              if (iDigit == 0)
              {

                // 1. 避免 '零' 的重覆出現
                // 2. 個位數的 0 不必轉成 '零'
                if ((!bInZero)&&(i != 0))
                    sSection = "零" + sSection;
                bInZero = true;
              }
              else
              {

                if(type==1)
                {
                   switch(i)
                   {
                       case 1 : sSection='拾' + sSection;break;
                       case 2 : sSection='佰' + sSection;break;
                       case 3 : sSection='仟' + sSection;break;
                   }
                }
                else
                {
                   switch(i)
                   {
                       case 1 : sSection='十' + sSection;break;
                       case 2 : sSection='百' + sSection;break;
                       case 3 : sSection='千' + sSection;break;
                   }
                }
                sSection = _ChineseNumeric.substring(iDigit,iDigit+1) +sSection;
                bInZero = false;
               }
             }
             /* 加上該小節的位數 */
             if (sSection.length()== 0)
             {
               if ((Result.length()> 0)&&Result.substring(0,1).equals("零"))
                   Result = "零" + Result;
             }
             else
             {
              switch(iSection)
              {
                  case 0: Result = sSection;break;
                  case 1: Result = sSection + "萬" + Result;break;
                  case 2: Result = sSection + "億" + Result;break;
                  case 3: Result = sSection + "兆" + Result;break;
               }
             }
           }
           /* 處理小數點右邊的部分 */
           if (iPosOfDecimalPoint > 0)
           {
             Result=Result+ "點";
             for(int i=iPosOfDecimalPoint;i<sArabic.length()-1;i++)
             {
               iDigit =Integer.parseInt(sArabic.substring(i+1,i+2));
               Result=Result+ _ChineseNumeric.substring(iDigit,iDigit+1);
             }
           }

           /* 其他例外狀況的處理 */
           if (Result.length() == 0)  Result= "零";
           if ((Result.length()>=2)&&(Result.substring(0,2).equals("一十")||Result.substring(0,2).equals("壹十"))) Result = Result.substring(1,Result.length());
           if (Result.substring(0,1).equals("點"))   Result = "零" + Result;

           /* 是否為負數 */
           if (bMinus) Result= "負" + Result;


          return Result;
        }

        /* 將字串反向, 例如: 傳入 '1234', 傳回 '4321' */
       public String ConvertStr(String sBeConvert) throws IOException
        {
            String Result="";
            for(int i=sBeConvert.length()-1;i>=0;i--)
                  Result=Result+sBeConvert.charAt(i);
            return Result;
        }

       
   	public String Get_Dptcd(String crmyy,String crmid,String crmno,
			com.sertek.db.DBUtility db,String owner){
		Vector vr = new Vector();
		String sql = "";
		String dptcd = "";

		sql = "select dptcd from " + owner + "..c60 where crmyy='" +	crmyy +
			"' and crmid='" + crmid + "' and crmno='" + crmno + "'";
		vr = db.doSqlSelect(sql,1,false);

		if(vr.size()>0){
		    dptcd = (String)vr.get(0);
		}
		return dptcd;
	}
   	
   /**
     * 		將包含造字.....內容轉換成一般國字字串(一),(二)
     *@param	dblArabic	包含造字.....之字串
     *@return 			傳回置換後的字串
   */
   public String Bnum2CNum(String instr) throws IOException
     {
      String Result="";
       try
       {
        	for(int i=0;i<=instr.length()-1;i++)
                {
        	  String str=""+instr.charAt(i);

        	  if(str.equals(""))
        	  {
        		Result=Result+StrTran(str,"","(一)");
        	        continue;
        	  }
        	  if(str.equals(""))
        	  {
        	  	Result=Result+StrTran(str,"","(二)");
        	        continue;
        	  }
        	  if(str.equals(""))
        	  {
        	  	Result=Result+StrTran(str,"","(三)");
                        continue;
                  }
                  if(str.equals(""))
        	  {
        	  	Result=Result+StrTran(str,"","(四)");
        	        continue;
        	  }
        	  if(str.equals(""))
        	  {
        	  	Result=Result+StrTran(str,"","(五)");
        	        continue;
        	  }
        	  if(str.equals(""))
        	  {
        	  	Result=Result+StrTran(str,"","(六)");
        	        continue;
        	  }
        	  if(str.equals(""))
        	  {
        	  	Result=Result+StrTran(str,"","(七)");
        	        continue;
        	  }
        	  if(str.equals(""))
        	  {
        	  	Result=Result+StrTran(str,"","(八)");
        	        continue;
        	  }
        	  if(str.equals(""))
        	  {
        	  	Result=Result+StrTran(str,"","(九)");
        	        continue;
        	  }
        	  if(str.equals(""))
        	  {
        	  	Result=Result+StrTran(str,"","(十)");
        	        continue;
        	  }
        	  Result = Result+instr.charAt(i);
        	}

       }
      catch(Exception ex)
       {}

       //回傳值
       return Result;
   }

   /**
     * 		將包含造字(一),(二).....內容轉換成一般國字字串
     *@param	dblArabic	包含造字(一),(二).....之字串
     *@return 			傳回置換後的字串
   */
   public String CNum2Bnum(String instr) throws IOException
     {
      String Result=instr;
      String Result1=instr;
       try
       {

        		Result=StrTran(Result,"(一)","");

        	  	Result=StrTran(Result,"(二)","");

        	  	Result=StrTran(Result,"(三)","");

        	  	Result=StrTran(Result,"(四)","");

        	  	Result=StrTran(Result,"(五)","");

        	  	Result=StrTran(Result,"(六)","");

        	  	Result=StrTran(Result,"(七)","");

        	  	Result=StrTran(Result,"(八)","");

        	  	Result=StrTran(Result,"(九)","");

        	  	Result=StrTran(Result,"(十)","");


       }
      catch(Exception ex)
       {}

       //回傳值
       return Result;
   }

/**********************************檢核部分****************************************/


   /**
     * 		檢查傳入字串的長度 要不超過設定長度
     *@param	str_i		原始字串
     *@param	len_i		檢核的長度
     *@return 			檢查的結果
   */
   public boolean ChkLen(String str_i,int len_i) throws IOException
     {
      	boolean b=false;
       	try {
       		if(str_i.length()<=len_i) b = true;
       	}
      	catch(Exception ex){}

       	//回傳值
       	return b;
     }

/*
{ =============================================
  檢查傳入字串的長度 要不超過設定長度
  ============================================= }
function TDM1._ChkLen(ck_i: String;
                     len_i: smallint):Boolean;
var
  i: smallint;
  result_o : boolean;
begin
  try
    result_o := false;
    if (length(ck_i) <= len_i) then begin
      result_o := true;
    end;
    {回傳值}
    result:= result_o;
  except
  end;{_try}
end;{_chknum}
*/
   /**
     * 		檢查傳入字串的長度 要不超過設定長度，要以encode的方式計算長度
     *@param	str_i		原始字串
     *@param	len_i		檢核的長度
     *@param	encode		encode的編碼方式
     *@return 			檢查的結果
   */
   public boolean ChkLen(String str_i,int len_i,String encode) throws UnsupportedEncodingException
   	{
   		String en = encode;
   		if(en.length()<=0) en = "UTF-8";
   		boolean result_o = false;
   		try {
   			byte[] b = str_i.getBytes(en);
   			if(b.length <= len_i) result_o = true;
   		} catch(Exception ex) {
   		}
   		return result_o;
   	}
   /**
     * 		檢核是否是數字
     *@param	str_i		原始字串
     *@return 			檢查的結果
   */
   public boolean chknum(String ck_i) throws IOException
     {
      boolean result_o=false;
      try
       {
        result_o = true;
        ck_i=alltrim(ck_i);
        //對字串處理
        if ((!ck_i.equals(""))&&(ck_i.length() > 0))
         {
          for(int i=0;i<ck_i.length();i++)
           {
            if ((!Character.isDigit(ck_i.charAt(i)))&&(ck_i.charAt(i)!='.'))
             {
              result_o= false;
              break;
             }
           }
         }
       }
      catch(Exception ex)
       {}
      //回傳值
      return result_o;
     }

   /**
     * 		檢核是否是英文字
     *@param	str_i		原始字串
     *@return 			檢查的結果
   */
   public boolean ChkAlpha(String ck_i) throws IOException
     {
      boolean result_o=false;
      try
       {
        result_o = true;
        //對字串處理
        if ((!ck_i.equals(""))&&(ck_i.length() > 0))
         {
          for(int i=0;i<ck_i.length();i++)
           {
            if ((Character.getType(ck_i.charAt(i))!=1)&&(Character.getType(ck_i.charAt(i))!=2))
             {
              result_o= false;
              break;
             }
           }
         }
       }
      catch(Exception ex)
       {}
      //回傳值
      return result_o;
     }

/************************************************************************************/

   /**
     * 		檢核身份證字號
     *@param	cID		身份證字號
     *@return 			檢查的結果
   */
   public boolean IsIDNUM(String cID) throws IOException
     {
      boolean result_o=false;
      String  str_tmp, head ;
      String  sex_tmp="sdfs";
      long    id_num;
      String  fs;
      try
       {
        str_tmp=cID.substring(1,cID.length());
        head=cID.substring(0,1);
        head= head.toUpperCase();
        sex_tmp= cID.substring(1,2);
        if ((cID.length()== 10) && chknum(str_tmp))// && _chkAlpha(head))
          if (sex_tmp.equals("1") || sex_tmp.equals("2"))
           {
            //
            // 改變第一碼

             head       = transform(head.charAt(0));
             fs         = head+ str_tmp;
             id_num     = Integer.parseInt(fs.substring(0,1))   +
                          Integer.parseInt(fs.substring(1,2)) *9+
                          Integer.parseInt(fs.substring(2,3)) *8+
                          Integer.parseInt(fs.substring(3,4)) *7+
                          Integer.parseInt(fs.substring(4,5)) *6+
                          Integer.parseInt(fs.substring(5,6)) *5+
                          Integer.parseInt(fs.substring(6,7)) *4+
                          Integer.parseInt(fs.substring(7,8)) *3+
                          Integer.parseInt(fs.substring(8,9)) *2+
                          Integer.parseInt(fs.substring(9,10))  +
                          Integer.parseInt(fs.substring(10,11));

             if ((id_num % 10) == 0)
              {
               // 是身份証字號
               result_o = true;
              }

           }
       }
      catch(Exception ex)
       {}
      return result_o;
     }

   /**
     *	Format字串成為數值　傳入值　要format的字串,小數點幾位
     *@param	d	double的數字內容
     *@param	l	要格式化的，小數點幾位數
     *@return		傳回格式化後的字串值
   */
   public String FormatNum(double d,int l) {
   	return FormatNum(String.valueOf(d),l);
   }

   /**
     *	Format字串成為數值　傳入值　要format的字串,小數點幾位
     *@param	s	double的數字內容
     *@param	l	要格式化的，小數點幾位數
     *@return		傳回格式化後的字串值
   */
   public String FormatNum(String s,int l) {
   	int index = s.indexOf(".");
   	String style = "";
   	for(int i = 0;i<index;i++) {
   		style = style + "#";
   	}
   	if(l > 0) {
   		style = style + ".";
   		for(int i=0;i<l;i++) {
   			style = style + "#";
   		}
   	}

   	DecimalFormat df = new DecimalFormat(style);
   	return df.format(Double.parseDouble(s));

   }

	//--------------------------------------------------------------------------
   //2007.05.04 add by lewiswang [#1951] 
   //先加一個算,的，以防原來的有別人用是正常的
	public String addComma1(String inStr) {
		String ret = "";
		if(inStr!=null){
			int len = inStr.length();
			//算是不是有.
			int len1 = inStr.length();	
			
			if (inStr.indexOf(".") > -1)
			{
				len1 = inStr.indexOf(".");	
			}
			
			int loop = len / 3;
			int loop1 = len1 / 3;

			if (loop1 == loop)
			{
				for(int i=0;i<loop;i++){
					ret = "," + inStr.substring(len-(i+1)*3,len-i*3) + ret;
				}
			}
			else
			{
				ret = inStr.substring(len1,len);
				for(int i=0;i<loop1;i++){
					ret = "," + inStr.substring(len1-(i+1)*3,len1-i*3) + ret;
				}
			}

			if( (len%3)!=0 ){
				ret = inStr.substring(0,len-(loop)*3) + ret;
			}
			if(ret.startsWith(",")){
				ret = ret.substring(1);
			}
		}
		return ret;
	}   
   
	public String addComma(String inStr) {
		String ret = "";
		if(inStr!=null){
			int len = inStr.length();
			int loop = len / 3;

			for(int i=0;i<loop;i++){
				ret = "," + inStr.substring(len-(i+1)*3,len-i*3) + ret;
			}
			if( (len%3)!=0 ){
				ret = inStr.substring(0,len-(loop)*3) + ret;
			}
			if(ret.startsWith(",")){
				ret = ret.substring(1);
			}
		}
		return ret;
	}


   /**
     **身份証對照表
     *@param	id	身份證前一碼英文
     *@return		傳回身份證對照表
   */
   private String transform(char id) throws IOException
     {
      String result="";
      try
       {
         switch(id)
          {
            case 'A': result= "10";break;
            case 'B': result= "11";break;
            case 'C': result= "12";break;
            case 'D': result= "13";break;
            case 'E': result= "14";break;
            case 'F': result= "15";break;
            case 'G': result= "16";break;
            case 'H': result= "17";break;
            case 'I': result= "34";break;
            case 'J': result= "18";break;
            case 'K': result= "19";break;
            case 'L': result= "20";break;
            case 'M': result= "21";break;
            case 'N': result= "22";break;
            case 'O': result= "35";break;
            case 'P': result= "23";break;
            case 'Q': result= "24";break;
            case 'R': result= "25";break;
            case 'S': result= "26";break;
            case 'T': result= "27";break;
            case 'U': result= "28";break;
            case 'V': result= "29";break;
            case 'W': result= "32";break;
            case 'X': result= "30";break;
            case 'Y': result= "31";break;
            case 'Z': result= "33";break;
          }

       }
      catch(Exception ex)
       {}
      return result;
     }

	/**
		*將陣列的內容組合成一個字串
		@param 	s 	要組合的陣列值
		@param 	type 	組合的分隔符號
		@return 	回傳的組合字串
	*/
	public String getElements(String[] s,String type) {
		StringBuffer sb = new StringBuffer();
		String temp = ",";
		for(int i=0;i<s.length;i++) {
			sb.append(type + s[i] + type);
			if((i+1) < s.length) sb.append(temp);
		}
		return sb.toString();

	}
	
	/**
	*將陣列的內容組合成一個字串
	@param 	ss 	要組合的陣列值
	@param 	type 	組合的分隔符號
	@return 	回傳的組合字串
*/
public String getElements2(String[] ss,String type) {
	StringBuffer sb = new StringBuffer();
	//String temp = ",";
	for(int i=0;i<ss.length;i++) {
		if((i+1) < ss.length) 
			sb.append(ss[i] + type);
		else
			sb.append(ss[i]);
	}
	return sb.toString();

}


	/**
		*將字串轉成一維陣列
		@param		s
		@param		type	字串的分隔符號，若傳入空字串，則預設以","分隔
		@return		回傳一維字串陣列
	*/
	public String[] toStringArray(String s,String type) {
		if(s.length()>0) {
			String temp = type;
			String stemp = s;
			Vector vr = new Vector();
			if(temp.length()==0) temp = ",";
			boolean flag = true;
			int len = type.length();
			/*StringTokenizer st = new StringTokenizer(s,temp);
			String[] result = new String[st.countTokens()];
			int i = 0;
			while(st.hasMoreTokens()) {
				result[i] = st.nextToken();
				i++;
			}*/
			int si = 0,ei = 0;
			while (flag == true) {
				si = stemp.indexOf(temp);
				//ei = s.indexOf(temp,(si + 1));
				//當第一個字元就屬type時，要第一個元素放空字串
				if(si == 0) {
					//ei = si + 1;
					vr.add(null);
					stemp = stemp.substring((si+len),stemp.length());
					//si = si +1;
					//ei = si + 1;
				} else if(si > -1 ){
					vr.add(stemp.substring(0,si));
					stemp = stemp.substring((si+len),stemp.length());

				} else if(si==-1) {
					if(stemp.length()>0) {
						vr.add(stemp);

					}
					break;
				}
				if(stemp.length() == 0 && s.lastIndexOf(temp)+len == s.length()) {
					vr.add(null);
				}

				//si = si+1;
				/*if(si > -1) {
					if(ei == -1) {
						ei = s.length();
						si = si + 1;
						flag = false;
					}
					if(si == (ei-1)) {
						vr.add(null);
					} else
						vr.add(s.substring(si,ei));

				}
				if(ei < s.length()) {
					stemp = s.substring(ei,s.length());
					si = ei ;
				}*/
			}
			String[] result = new String[vr.size()];
			for(int i=0;i<vr.size();i++) {
				result[i] = (String)vr.elementAt(i);
			}
			temp = null;
			stemp = null;
			//st = null;
			return result;
		} else {
			return new String[0];
		}
	}
/************************************************************************************/
	/**
		*針對字別細類組合javascript的陣列
		@param		vr	由資料庫中找尋出來的資料。
		@param		field	由資料庫中找尋出來的資料，欄位數幾個。
		@param		array_name	javascript的陣列名稱
		@return		傳回字串。
	*/
	public String mixScriptArray(Vector vr,int field,String array_name) {
		StringBuffer result = new StringBuffer();
		result.append(array_name + " = new Array(\n");
		if(vr.size()>0) {

			for(int i=0;i<(vr.size()/field);i++) {
				result.append("[");
				for(int j=0;j<field;j++) {
					result.append("'" + (String)vr.elementAt(i*field+j) + "'");
					if((j+1) < field) result.append(",");
				}
				result.append("]\n");
				if((i+1)<(vr.size()/field)) result.append(",");
			}

		} else {
			result.append("['','']");
		}
		result.append(");");
		return result.toString();
	}

	/**
		*將Vector的值放到Hashtable中
		@param		vr	(Vector)
		@param		row	該vr中的第幾組資料
		@param		key	(String[])要放到hashtable中的key值
		@param		req	(Hashtable)要接值的Hashtable
	*/
	public void VrToHash(Vector vr,int row,String[] key,Hashtable req) {
		for(int i=0;i<key.length;i++) {
			if(vr.elementAt(row * key.length + i) != null)
				req.put(key[i],(String)vr.elementAt(row * key.length + i));
		}
	}

	/**
		*要比較的資料欄位名稱為col[]定義，而舊資料為 h + col[0]...的名稱
		*比較資料是不是有修改過
		@param		req	存有資料的hashtable
		@param		要比較的資料欄位名稱(String[])
		@return		String[]	有修改的資料欄位
	*/
	public String[] compareData(Hashtable req,String col[]) {
		String temp1 = "",temp2 = "";
		Vector result = new Vector();
		for(int i=0;i<col.length;i++) {
			if(req.get(col[i]) != null)
				temp1 = (String)req.get(col[i]);
			else
				temp1 = "";
			if(req.get("h" + col[i]) != null)
				temp2 = (String)req.get("h" + col[i]);
			else
				temp1 = "";
			if(!temp1.equals(temp2))
				result.add(col[i]);
		}
		String[] result1 = new String[(result.size())];
		for(int i=0;i<result.size();i++) {
			result1[i] = (String)result.elementAt(i);
		}
		return result1;
	}

	/**
		*vector的位置由0開始起算。
		*產生Form object Select。
		@param		vr	Vector(存有資料);
		@param		value	在Vector中的第幾個位置是value值
		@param		options	在Vector中的第幾個位置是options值
		@param		field	vector中共有幾個欄位數
		@param		defValue	預設的value
	*/
	public String getSelectList(Vector vr,int value,int options,int field,String defValu) {
		StringBuffer result = new StringBuffer();
		if(vr != null) {
			for(int i=0;i<(vr.size()/field);i++) {
				result.append("<option value='" + (String)vr.elementAt(i*field + value) + "'");
				if(defValu!=null && defValu.length()>0) {
					if(defValu.equals((String)vr.elementAt(i*field + value)))
						result.append(" selected");
				}
				result.append(">" + (String)vr.elementAt(i*field + options) + "</option>\n");
			}
		}
		return result.toString();
	}

 	/**
        *   將字串內容前端的0轉成空字串nbsp
        *@param  str_i   字串的原始內容
        *@return    傳回轉換後的字串。
        */
        public String zero2nbsp(String str_i)
        {
          StringBuffer buf=new StringBuffer();
          String str="";
          int i=0;
          try
          {
             for (i=0;i<str_i.length();i++)
             {
               if(str_i.charAt(i)=='0')
                  str=buf.append("&nbsp;&nbsp;").toString();
               else
                   break;
             }
             buf.setLength(0);
             str=buf.append(str).append(str_i.substring(i,str_i.length())).toString();
          }
          catch(Exception ex)
          {
             System.out.println(ex);
          }
          return str;
       }
       /**
        *   將字串內容前端的0轉成空白字串
        *@param  str_i   字串的原始內容
        *@return    傳回轉換後的字串。
       */
       public String zero2blank(String str_i)
       {
          StringBuffer buf=new StringBuffer();
          String str="";
          int i=0;
          try
          {
             for (i=0;i<str_i.length();i++)
             {
                if(str_i.charAt(i)!='0')
                    break;
             }
             buf.setLength(0);
             str=buf.append(str).append(str_i.substring(i,str_i.length())).toString();
          }
          catch(Exception ex)
          {
              System.out.println(ex);
          }
          return str;
        }
       
       public String zero2blank2(String str_i)
       {
          StringBuffer buf=new StringBuffer();
          String str="";
          int i=0;
          try
          {
             for (i=0;i<str_i.length();i++)
             {
          	   if(str_i.charAt(i)=='0') {
                     str=buf.append(" ").toString();
          	   }else {
                      break;
          	   }     
             }
             buf.setLength(0);
             str=buf.append(str).append(str_i.substring(i,str_i.length())).toString();
          }
          catch(Exception ex)
          {
              System.out.println(ex);
          }
          return str;
        }

        /**
         字串轉換
        @param str 要組合的字串
        @param replace1 要被替換掉的字元或字串
        @param replace2 要轉換成的字元或字串
        @return 回傳的組合字串
        */

        public String doStringtokenizer(String str,String replace1,String replace2)
        {
          StringBuffer retVal = new StringBuffer();
          int i= 1;
          if ((str.trim()).equals(""))
          return retVal.toString();

          StringTokenizer st = new StringTokenizer(str,replace1);

          while(st.hasMoreTokens())
          {
             if (i==1)
                 retVal.append(st.nextToken());
             else
                 retVal.append(replace2 + st.nextToken());
             i++;
          }


          return retVal.toString();
        }
        /**
	    *計算字串受限單行Byte數應轉折換行字元位置
	    *@param inStr 欲檢測字串
	    *@param byteLimit 欲轉折換行處(單位：byte)
	    *@return ret_BrlPt -1表不需換行，非-1值表應換行處最後一個字元
        */
        public int get_Byte_Length(String inStr,int byteLimit){
            int byteLength = 0;
            int ret_BrkPt = 0;
            String TmpStr = "";;
            if(inStr != null){
                try{
                     do{
                          TmpStr = inStr.substring(0,1);
                          inStr = inStr.substring(1);
                          byteLength += TmpStr.getBytes("MS950").length;
                          if(byteLength > byteLimit){
                          	  break;
                          }
                          ret_BrkPt++;
                       }while(byteLength < byteLimit);
                       return ret_BrkPt;
                   }catch(UnsupportedEncodingException e){
                      e.printStackTrace();
                      return -1;
                   }catch(StringIndexOutOfBoundsException e2){
				      return ret_BrkPt;
				   }
            }
            return -1;
        }

        /**
        *將字串轉成Vector
        @param		s
        @param		type	字串的分隔符號，若傳入空字串，則預設以","分隔
        @return		回傳Vector
        */
	public Vector toStringVector(String s,String type) {
		Vector result = new Vector();
		if(s.length()>0) {
			String temp = type;
			String stemp = s;
			//Vector vr = new Vector();
			if(temp.length()==0) temp = ",";
			boolean flag = true;
			int len = type.length();
			int si = 0,ei = 0;
			while (flag == true) {
				si = stemp.indexOf(temp);
				//當第一個字元就屬type時，要第一個元素放空字串
				if(si == 0) {
					result.add(null);
					stemp = stemp.substring((si+len),stemp.length());

				} else if(si > -1 ){
					result.add(stemp.substring(0,si));
					stemp = stemp.substring((si+len),stemp.length());

				} else if(si==-1) {
					if(stemp.length()>0) {
						result.add(stemp);

					}
					break;
				}
				if(stemp.length() == 0 && s.lastIndexOf(temp)+len == s.length()) {
					result.add(null);
				}

			}
			temp = null;
			stemp = null;
			return result;
		} else {
			return result;
		}
	}

	/**
        *取得不帶路徑的檔名
        @param		fullFilenm	完整路徑包含檔案名稱
        @return		回傳Vector
        */

	public String Get_ShortFilenm(String fullFilenm){
	   String ret_ShortFilenm = "";
	   try
	   {

		fullFilenm=StrTran(fullFilenm,"\\","/");
		int pos1 = fullFilenm.lastIndexOf("\\");
		int pos2 = fullFilenm.lastIndexOf("/");
		if(pos1>pos2){
		    ret_ShortFilenm = fullFilenm.substring(pos1+1);
		}else if(pos1<pos2){
		    ret_ShortFilenm = fullFilenm.substring(pos2+1);
		}
	   }
	   catch(Exception ex){ret_ShortFilenm="";}

	   return ret_ShortFilenm;
	}

	 /**
	    *計算字串受限單行Byte數應轉折換行字元位置，增加處理換行符號能力，遇換行直接return處理過字串
	    *@param inStr 欲檢測字串
	    *@param byteLimit 欲轉折換行處(單位：byte)
	    *@param cutType		0：回傳前半段   1：回傳後半段
        */
        public String cutString(String inStr,int byteLimit,int cutType){
            int byteLength = 0;
            int ret_BrkPt = 0;
            String TmpStr = "";
			String forStr = "";
			String backStr = "";
			int pos1 = 0;

            if(inStr != null){
				try{
					TmpStr = inStr;
					TmpStr = this.StrTran(TmpStr,"\r\n","#13#10");
					TmpStr = this.StrTran(TmpStr,"\n\r","#13#10");
					TmpStr = this.StrTran(TmpStr,"\n","#13#10");
					TmpStr = this.StrTran(TmpStr,"\r","#13#10");
					TmpStr = this.StrTran(TmpStr,"#13#10","\r\n");

					pos1 = TmpStr.indexOf("\r\n");

					if(pos1 != -1){
						if((TmpStr.substring(0,pos1)).getBytes("MS950").length<=byteLimit){
							forStr = TmpStr.substring(0,pos1);
							backStr = TmpStr.substring(pos1 + "\r\n".length());
						}else{
							forStr = TmpStr.substring(0,this.get_Byte_Length(TmpStr,byteLimit));
							if(this.get_Byte_Length(TmpStr,byteLimit)!=TmpStr.length()){
								backStr = TmpStr.substring(this.get_Byte_Length(TmpStr,byteLimit));
							}else{
								backStr = "";
							}
						}
					}else{
						forStr = TmpStr.substring(0,this.get_Byte_Length(TmpStr,byteLimit));
						if(this.get_Byte_Length(TmpStr,byteLimit)!=TmpStr.length()){
							backStr = TmpStr.substring(this.get_Byte_Length(TmpStr,byteLimit));
						}else{
							backStr = "";
						}
					}


					if(cutType==0){
						return forStr;
					}else{
						return backStr;
					}
				}catch(Exception e){
					e.printStackTrace();
					return "";
				}
			}else{
				return "";
			}
        }

	/**
		**清除str_i字串前後的空白(包含全形空白)
		*@param 	str_i 	要清除空白的原始字串
		*@return 		清除前後邊空白後的字串
	*/
	public String trimFullSP(String str_i){
		String result_0 = "";
		String tmpStr = "";
		int iter = 0;

		if(str_i != null){
			if(! str_i.trim().equals("")){
				try{
					tmpStr = str_i.trim();
					//由左邊先Trim()
					while((tmpStr.charAt(iter)=='　')||(tmpStr.charAt(iter)==' ')){
						iter++;
						if(iter == tmpStr.length())
							break;
					}
					tmpStr = tmpStr.substring(iter,tmpStr.length());

					//再由右邊Trim()
					if(! tmpStr.equals("")){
						iter = tmpStr.length();
						while((tmpStr.charAt(iter-1)=='　')||(tmpStr.charAt(iter-1)==' ')){
							iter--;
						}
						tmpStr = tmpStr.substring(0,iter);
					}
					result_0 = tmpStr;

				}catch(Exception e){
					result_0 = str_i;
					e.printStackTrace();
				}
			}
			return result_0;
		}else{
			return result_0;
		}

	}

//--------------------------------------------------------------------------
	//tagPos要從0開始計算
	public Vector sortVR(Vector vr,int field,int tagPos){
		int vrCount = vr.size();
		int[][] tmpArr = new int[vrCount/field][2];
		Vector vr_Tmp = new Vector();
		//String[] tmpStr = new String[vrCount];
		String strTmp = "";
		for(int i=0;i<vrCount/field;i++){
			strTmp = (String)vr.get(i*field+tagPos);

		    tmpArr[i][0] = i;

			if(strTmp.equals("甲")){
			    tmpArr[i][1] = 5001;
			}else if(strTmp.equals("乙")){
			    tmpArr[i][1] = 5002;
			}else if(strTmp.equals("丙")){
			    tmpArr[i][1] = 5003;
			}else if(strTmp.equals("丁")){
			    tmpArr[i][1] = 5004;
			}else if(strTmp.equals("戊")){
			    tmpArr[i][1] = 5005;
			}else if(strTmp.equals("己")){
			    tmpArr[i][1] = 5006;
			}else if(strTmp.equals("庚")){
			    tmpArr[i][1] = 5007;
			}else if(strTmp.equals("辛")){
			    tmpArr[i][1] = 5008;
			}else if(strTmp.equals("壬")){
			    tmpArr[i][1] = 5009;
			}else if(strTmp.equals("癸")){
			    tmpArr[i][1] = 5010;
			}else if(strTmp.equals("子")){
			    tmpArr[i][1] = 5011;
			}else if(strTmp.equals("丑")){
			    tmpArr[i][1] = 5012;
			}else if(strTmp.equals("寅")){
			    tmpArr[i][1] = 5013;
			}else if(strTmp.equals("卯")){
			    tmpArr[i][1] = 5014;
			}else if(strTmp.equals("辰")){
			    tmpArr[i][1] = 5015;
			}else if(strTmp.equals("巳")){
			    tmpArr[i][1] = 5016;
			}else if(strTmp.equals("午")){
			    tmpArr[i][1] = 5017;
			}else if(strTmp.equals("未")){
			    tmpArr[i][1] = 5018;
			}else if(strTmp.equals("申")){
			    tmpArr[i][1] = 5019;
			}else if(strTmp.equals("酉")){
			    tmpArr[i][1] = 5020;
			}else if(strTmp.equals("戌")){
			    tmpArr[i][1] = 5021;
			}else if(strTmp.equals("亥")){
			    tmpArr[i][1] = 5022;
			}else if(strTmp.equals("鼠")){
			    tmpArr[i][1] = 5023;
			}else if(strTmp.equals("牛")){
			    tmpArr[i][1] = 5024;
			}else if(strTmp.equals("虎")){
			    tmpArr[i][1] = 5025;
			}else if(strTmp.equals("兔")){
			    tmpArr[i][1] = 5026;
			}else if(strTmp.equals("龍")){
			    tmpArr[i][1] = 5027;
			}else if(strTmp.equals("蛇")){
			    tmpArr[i][1] = 5028;
			}else if(strTmp.equals("馬")){
			    tmpArr[i][1] = 5029;
			}else if(strTmp.equals("羊")){
			    tmpArr[i][1] = 5030;
			}else if(strTmp.equals("猴")){
			    tmpArr[i][1] = 5031;
			}else if(strTmp.equals("雞")){
			    tmpArr[i][1] = 5032;
			}else if(strTmp.equals("狗")){
			    tmpArr[i][1] = 5033;
			}else if(strTmp.equals("豬")){
			    tmpArr[i][1] = 5034;
			}else if(strTmp.equals("　") || strTmp.equals(" ")){
			    tmpArr[i][1] = 0;
			}else{
				try{
					tmpArr[i][1] = Integer.parseInt(strTmp);
				}catch(Exception e){
				    tmpArr[i][1] = 5200 + i;
				}
			}
		}

		if(vrCount > 1){
			int tmp0 = 0;
			int tmp1 = 0;
			for(int i=0;i<vrCount/field-1;i++){
				for(int j=0;j<vrCount/field-1-i;j++){
					if(tmpArr[j][1] > tmpArr[j+1][1]){
						tmp0 = tmpArr[j][0];
						tmp1 = tmpArr[j][1];
						tmpArr[j][0] = tmpArr[j+1][0];
						tmpArr[j][1] = tmpArr[j+1][1];
						tmpArr[j+1][0] = tmp0;
						tmpArr[j+1][1] = tmp1;
					}
				}
			}
		}

		for(int i=0;i<vrCount/field;i++){
			for(int j=0;j<field;j++){
		        vr_Tmp.add(vr.get(tmpArr[i][0]*field+j));
			}
		}
		vr = null;
		vr = vr_Tmp;
		return vr;
	}
	//------------------- 補單引號 ---------------------------------------------


	public String addQuote(String s){
		String str = "";
		try{

			if (s == null){
				str = "''";
				return str;
			}
			if (s.length() != 0){
				if(s.indexOf(",") == -1){
					str = "'" + s + "'";
					return str;
				}
				String temp[] = toStringArray(s,",");
				for(int i=0;i<temp.length;i++){
					str = str + "'" + temp[i] + "',";
				}
				str = str.substring(0,str.length()-1);
			}else{
				str = "''";
			}

		}catch(Exception ex){
			System.out.println("addQuote = "+ex);
		}
		return str;
	}


//-----------------------準備造字檔字串陣列-------------------------------------

	private void get_NumCode(){
		try{
			if(strNUMCODE1.getBytes("MS950").length==200){
				NumCode1[0] = "";
				for(int i=0;i<100;i++){
					NumCode1[i+1] = cutString(strNUMCODE1,2,0);
					strNUMCODE1 = cutString(strNUMCODE1,2,1);
				}
			}

			if(strNUMCODE2.getBytes("MS950").length==200){
				NumCode2[0] = "";
				for(int i=0;i<100;i++){
					NumCode2[i+1] = cutString(strNUMCODE2,2,0);
					strNUMCODE2 = cutString(strNUMCODE2,2,1);
				}
			}
		}catch(Exception e){

		}
	}

//----------------------- 取出造字檔資料 ----------------------------------------

	public String getNo(String idx,int typeNo){
		int idx2 = 0;
		if(trimFullSP(idx).equals("")){
			return "";
		}else{
			try{
				idx2 = Integer.parseInt(idx);
			}catch(Exception e){
				return "";
			}
		}
		return getNo(idx2,typeNo);
	}

//----------------------- 取出造字檔資料 ----------------------------------------
	//typeNo 1:阿拉伯數字造字檔 2:含外圈的造字檔
	public String getNo(int idx,int typeNo){
		if(idx>0 && idx<=100){
			if(typeNo==1){
				return NumCode1[idx];
			}else{
				return NumCode2[idx];
			}
		}else if(idx>100){
			return "" + idx;
		}else{
			return "";
		}
	}

//------------------------------------------------------------------------------


	/**
	*取得cookie 值，如果 value  為中文 ，會有問題
	*@param request javax.servlet.http.HttpServletRequest
	*@param key 要取回的 key 值
	*/
	public String getCookieValue(javax.servlet.http.HttpServletRequest request,String key){
		String retVal = "";
		Cookie [] cookie = request.getCookies();
		if (cookie!=null)
		{
		        for(int i=0;i<cookie.length;i++)
		        {

		                if(key.equals(cookie[i].getName()))
		                {

		                        retVal = cookie[i].getValue();
		                        break;
		                }
		        }
		}
		return retVal;
	}
	/**
	*設定cookie 值，如果 value  為中文 ，會有問題
	*@param request javax.servlet.http.HttpServletRequest
	*@param key 要的儲存 key 值
	*@param value 要儲存的值
	*/
	public void setCookieValue(javax.servlet.http.HttpServletResponse response,String key,String value){
		Cookie c = new Cookie(key,value);
    		int time = 604800;//設定一週
    		c.setMaxAge(time);
    		response.addCookie(c);
	}
	public String formatNumber(String value,int patterns){
		String retVal = "";
		try{
			retVal = formatNumber(Double.parseDouble(value),patterns);
		}catch(Exception e){
		}
		return retVal;
	}
	/**
	*格式化 int
	*@param value 欲格式化的數字
	*@param patterns 小數點第幾位
	*@return 格式化後的字串，例如  123,456.00
	*/
	public String formatNumber(int value,int patterns){
		String retVal = "";
		try{
			retVal = formatNumber(Double.parseDouble(String.valueOf(value)),patterns);
		}catch(Exception e){
		}
		return retVal;
	}
	/**
	*格式化 double
	*@param value 欲格式化的數字
	*@param patterns 小數點第幾位
	*@return 格式化後的字串，例如  123,456.00
	*/
	public String formatNumber(double value,int patterns){
		String retVal = "";
		try{
			String format = "###,###,###,###,###";
			for(int i=0;i<patterns;i++) {
				if (i==0) format += ".";
				format +="0";
			}
			DecimalFormat d = new DecimalFormat(format);
			retVal = d.format(value);
		}catch(Exception e){
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	*格式化 int
	*@param value 欲格式化的數字
	*@param froamt 格式式內容，請自行參考 Class DecimalFormat
	*@return 格式化後的字串
	*/
	public String formatNumber(int value,String foramt){
		String retVal = "";
		try{
			retVal = formatNumber(Double.parseDouble(String.valueOf(value)),foramt);
		}catch(Exception e){
		}
		return retVal;
	}
	/**
	*格式化 double
	*@param value 欲格式化的數字
	*@param froamt 格式式內容，請自行參考 Class DecimalFormat
	*@return 格式化後的字串
	*/
	public String formatNumber(double value,String foramt){
		String retVal = "";
		try{
			DecimalFormat d = new DecimalFormat(foramt);
			retVal = d.format(value);
		}catch(Exception e){
			e.printStackTrace();
		}
		return retVal;
	}
    public boolean compareArray(String as[], String s)
    {
        boolean flag = false;
        for(int i = 0; i < Array.getLength(as); i++)
        {
            if(0 != s.indexOf(as[i]))
                continue;
            flag = true;
            break;
        }

        return flag;
    }

    public boolean WholeCompare(String as[], String s)
    {
        boolean flag = false;
        for(int i = 0; i < Array.getLength(as); i++)
        {
            if(!as[i].equals(s))
                continue;
            flag = true;
            break;
        }

        return flag;
    }

    public boolean CoverCompare(String as[], String s)
    {
        boolean flag = false;
        for(int i = 0; i < Array.getLength(as); i++)
        {
            if(0 != s.indexOf(as[i]))
                continue;
            flag = true;
            break;
        }

        return flag;
    }
    
	//年齡算足歲
	public String tranAge(String birdt) {
		String result = "";
		String basedt = new util_date().nowCDate();
		try {
			if (birdt.length() ==  basedt.length()) {
				result = fillFrontzero(String.valueOf(Integer.parseInt(basedt) - Integer.parseInt(birdt)), 7);
				result = String.valueOf(Integer.parseInt(result.substring(0, 3)));
			}
		} catch (Exception ignore) {
		}
		return result;
	}
	
	//--------------------------------------------------------------------------

	public String tranSlash(String inStr) {
		String strTmp = inStr;
		try {
			strTmp = this.StrTran(strTmp,"/","\\");
			while(strTmp.indexOf("\\\\")!=-1){
				strTmp = this.StrTran(strTmp,"\\\\","\\");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return strTmp;

	}

	//--------------------------------------------------------------------------

	public String tranSlash2(String inStr) {
		String strTmp = tranSlash(inStr);
		try {
			strTmp = this.StrTran(strTmp,"\\","\\\\");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return strTmp;
	}

	//--------------------------------------------------------------------------

	public String tranSlash4(String inStr) {
		String strTmp = tranSlash(inStr);
		try {
			strTmp = this.StrTran(strTmp,"\\","\\\\\\\\");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return strTmp;
	}

	//--------------------------------------------------------------------------

	public String tranSlash3(String inStr) {
		String strTmp = inStr;
		String slash = java.io.File.separator;
		
		try {
			if(slash.equals("/")){
				strTmp = this.StrTran(strTmp,"\\","/");
				while(strTmp.indexOf("//")!=-1){
					strTmp = this.StrTran(strTmp,"//","/");
				}
			}
			/*
			else{
				strTmp = this.StrTran(strTmp,"/","\\");
				while(strTmp.indexOf("\\\\")!=-1){
					strTmp = this.StrTran(strTmp,"\\\\","\\");
				}
			}
			*/
		} catch(Exception e) {
			e.printStackTrace();
		}
		return strTmp;
	}
	
	public String tranSlash4JS(String path) {
		try {
			path = StrTran(path, "/", "\\");
			path = StrTran(path, "\\", "\\\\");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	//--------------------------------------------------------------------------
}