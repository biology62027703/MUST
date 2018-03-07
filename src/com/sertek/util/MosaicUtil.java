package com.sertek.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.sertek.db.DBUtility;

import de.idyl.crypto.zip.impl.CentralDirectoryEntry;

import jregex.Matcher;
import jregex.Pattern;

/*
 ----------------------------------------------------------------------------------------
 問題單號：Bug #4064 - 裁判書遮隱
 修改摘要：裁判書遮隱
 更新版本：V9806-裁判書遮隱專版
 修改人員：Eason
 修改日期：0980429
--------------------------------------------------------------------------------
 問題單號：Bug #4817 - 裁判書遮隱需求調整
 修改摘要：在判斷少年事件之前, 多加判斷不上傳案件之條件。
 更新版本：V981101-裁判書遮隱專版
 修改人員：nicole
 修改日期：0981217
--------------------------------------------------------------------------------
 問題單號：Bug #4907 - 09901180012
 修改摘要：修改檔案內容過大, 造成 StackOverflowError 錯誤的問題。transSpace()
 更新版本：V9904
 修改人員：nicole
 修改日期：0991120
 --------------------------------------------------------------------------------
問題單號：Bug #5144 - 09903020010裁判書遮隱
 修改摘要：若人名有*會造成遮隱失敗，在遮隱人名前要先把*去除
 更新版本：V9904
 修改人員：Eason
 修改日期：0990408Bug 
--------------------------------------------------------------------------------
問題單號：Bug #5142 - JUD0CY0990011
 修改摘要：配合二科需求，請將裁判書遮隱作業之電腦遮隱程式能把更名前後的名字也能遮隱，例如：李葳綺(原名李麗香)，請研究當事人姓名後面接'左括號+原名'時把後面的名字作遮隱
 		李葳綺(原名李麗香)正確的遮隱結果應是甲OO(原名乙OO)
 更新版本：V9904
 修改人員：Eason
 修改日期：0990416
--------------------------------------------------------------------------------
問題單號：#7545 - CHD0CC1000009 
 修改摘要：增加生日遮隱 (民國XXX年XX月XX日生)
 更新版本：V10104
 修改人員：Carl
 修改日期：1010514
--------------------------------------------------------------------------------
問題單號：Bug #8143 - 諮詢單10110170049 書記官裁判書遮隱
 修改摘要：遮隱地址修正
 更新版本：V10112
 修改人員：Carl
 修改日期：1011103
--------------------------------------------------------------------------------
問題單號：Bug #8205 - JUD0CY1010015-書記官裁判書遮隱
 修改摘要：書記官裁判書遮隱，增加遮隱項及調整
 更新版本：V10112
 修改人員：Carl
 修改日期：1011108
修改程式：CHD1W01_01.jsp,CHD1E02_01.jsp,CHD1E02_04.jsp,MosaicUtil.java,MosaicRule.java
--------------------------------------------------------------------------------
問題單號：Bug #8277 - JUD0MC1010016、JUD0CC1010015
修改摘要：1.遮隱規則同裁判書遮隱
          2.主文維護按[存檔]時，出現警示〔經電腦判斷，主文疑有個人資訊未遮，是否進行遮隱？〕，若為〔是〕，由螢幕顯示遮隱後主文！若為〔否〕則直接存檔！ 
更新版本：JUD1011115_裁判書遮隱
修改人員：Carl
修改日期：1011128
--------------------------------------------------------------------------------
問題單號：Bug #8276 - JUD0CY1010015_書記官遮隱-整合測試問題
修改摘要：修改主文前內容遮隱後有問題
更新版本：JUD1011115_裁判書遮隱
修改人員：Carl
修改日期：1011128
--------------------------------------------------------------------------------
問題單號：Bug #8306 - 10112100004裁判書遮隱修正
修改摘要：需修正"臺中市敦化路491巷5號" 遮不到問題
更新版本：V10112
修改人員：Carl
修改日期：1011211
--------------------------------------------------------------------------------
問題單號：Bug #8575 - JUD0MC1020004_JUD0MY1020003
 修改摘要：於裁判書遮隱作業中增加 停止緊急安置、強制住院 等關鍵字詞為裁判書不公開關鍵字詞
 更新版本：V1020325-裁判書上傳
 修改人員：Carl
 修改日期：1020313
 修改程式：CHD1W01_01.jsp,CHD1E02_01.jsp,CHD1E02_04.jsp,MosaicUtil.java,MosaicRule.java
--------------------------------------------------------------------------------
問題單號：Bug #8629 - TPH0MC1020001
 修改摘要：當cmptm為 視為上傳要旨 時 不在判斷視為不上傳終結要旨 
 更新版本：V10204
 修改人員：Carl
 修改日期：1020411
--------------------------------------------------------------------------------
 */
public class MosaicUtil {
	// jdk1.4 才有 regex 所以改用 jregex1.2_01.jar
	private List ignoredPosList = new ArrayList();

	private final static String charsetName = "MS950";

	private String noHideNameRegex = "";

	private List cutWordList = new ArrayList();
	
	private List noEndWordList = new ArrayList(); //非結尾字詞

	private final static String isFind = "isFind";

	private static com.sertek.util.utility util = new com.sertek.util.utility();

	private String iscoverName=""; //Y：遮姓名  N:不遮姓名


	/**
	 * 取得遮隱狀態
	 *
	 * @param file
	 * @param sys
	 * @return
	 */
	public static String getMosaicStatus(File file, String sys) {
		String result = "";
		if ("I".equals(sys) || isJuvenileCase(file)) {
			result = "P"; // 少年案件 不公開
		} else if (isSexCase(file)) {
			result = "Y"; // 性侵害或人口販運案件 遮隱後公開
		} else {
			result = "N"; // 依原公開機制公開
		}
		return result;
	}

	/**
	 * 取得遮隱狀態關鍵字
	 *
	 * @param file
	 * @param mosaicStatus
	 * @return
	 */
	public static String getMosaicMatchStr(File file, String mosaicStatus) {
		String result = "";
		if ("P".equals(mosaicStatus)) {
			// 少年案件關鍵字
			result = getJuvenileCaseMatchStr(file);
		} else if ("Y".equals(mosaicStatus)) {
			// 性侵害或人口販運案件關鍵字
			result = getSexCaseMatchStr(file);
		} else {
			// nothing
			result = "";
		}
		return result;
	}

	/**
	 * 是否為和解筆錄
	 *
	 * @param inputStr
	 * @return
	 */

	public static boolean isreconciliationCase(DBUtility db,String inputStr) {
		boolean check = false;

		String[] notitle = gets08title(db,"上傳院內抬頭");
		String lineSeparator = System.getProperty("line.separator"); //要取得第1行
		int    p 		 = 0;
		String firstLine = "";

		if(notitle!=null){
			if(!lineSeparator.equals("") && !inputStr.equals("")){
				p = inputStr.indexOf(lineSeparator);
				if(p>0){
				    try {
					    firstLine = util.noAnyBlank(inputStr.substring(0,p));
					    System.out.println("firstLine="+firstLine);
					} catch (IOException e) {
				        System.out.println(e);
            		}
					for (int i = 0; i < notitle.length; i++) {
						//System.out.println("notitle["+i+"]="+notitle[i]);
						if (firstLine.indexOf(notitle[i]) != -1) {
							System.out.println("此案為上傳院內抬頭");
							check=true;
							break;
						}
					}
				}
			}
		}
        /*
		String keyWord = getIsreconciliation(inputStr,notitle).trim();
		String newStr = "";

        System.out.println("keyWord="+keyWord);
        System.out.println("inputStr="+inputStr);

		//判斷斷行
		for(int i=0;i<keyWord.length();i++){
			int keycode =(new Character(keyWord.charAt(i))).hashCode();
			if(keycode!=10 && keycode!=32) newStr+=(keyWord.charAt(i)+"");
		}
		if(!notitle.equals("") && !newStr.equals("")){
			for (int i = 0; i < notitle.length; i++) {
				//System.out.println("notitle["+i+"]="+notitle[i]);
				if (newStr.indexOf(notitle[i]) != -1) {
					System.out.println("此案上傳抬頭="+newStr);
					check=true;
					break;
				}
			}
		}
		*/
		return check;
	}

	/**是否含人口販運關鍵詞
	 * 
	 * @param db
	 * @param inputStr
	 * @return
	 */
	public static boolean istraffickonCase(DBUtility db,String inputStr) {
		String result = "";

		String CaseRegex = MosaicRule.getTrafficCaseRegex();
		System.out.println("str="+CaseRegex);
		
		String str = matcherString(inputStr,CaseRegex);
		
		if (!"".equals(str)) {
			System.out.println("macher="+str);
			return true;
		}

		return false;
	}

	/**
	 * 是否為少年案件
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isJuvenileCase(File file) {
		return isJuvenileCase(getFileContent(file));
	}

	/**
	 * 是否為少年案件
	 *
	 * @param inputStr
	 * @return
	 */
	public static boolean isJuvenileCase(String inputStr) {
		if ("".equals(getJuvenileCaseMatchStr(inputStr))) {
			return false;
		} else {
			return true;
		}
	}
	
	/**[A]Carl #8205
	 * 是否為家暴案件
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isViolenceCase(File file) {
		if ("".equals(getViolenceCaseMatchStr(getFileContent(file)))) {
			return false;
		} else {
			return true;
		}
	}
	
	/**[A]Carl #8575
	 * 是否有其他不公開關鍵字詞
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isOtherNonDisclosureWords(File file) {
		if ("".equals(getOtherNonDisclosureWordsMatchStr(getFileContent(file)))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 取得少年案件關鍵字
	 *
	 * @param file
	 * @return
	 */
	public static String getJuvenileCaseMatchStr(File file) {
		return getJuvenileCaseMatchStr(getFileContent(file));
	}

	/**
	 * 取得少年案件關鍵字
	 *
	 * @param inputStr
	 * @return
	 */
	public static String getJuvenileCaseMatchStr(String inputStr) {
		return matcherString(inputStr,MosaicRule.getJuvenileCaseRegex());
	}
	
	/** [A]Carl #8575
	 * 取得其他不公開關鍵字
	 *
	 * @param inputStr
	 * @return
	 */
	public static String getOtherNonDisclosureWordsMatchStr(String inputStr) {
		return matcherString(inputStr,MosaicRule.getOtherNonDisclosureWordsRegex());
	}
	
	/** [A]Carl #8205
	 * 取得家暴案件關鍵字
	 *
	 * @param inputStr
	 * @return
	 */
	public static String getViolenceCaseMatchStr(String inputStr) {
		return matcherString(inputStr,MosaicRule.getViolenceCaseRegex());
	}
	
	/**
	 * 取得和解筆錄關鍵字
	 *
	 * @param inputStr
	 * @return
	 */
	public static String getIsreconciliation(String inputStr,String[] notitle) {
		return matcherString(inputStr,MosaicRule.getIsreconciliationRegex(notitle));
	}
	/**
	 * 是否為性侵害或人口販運案件
	 *
	 * @param file
	 * @return
	 */
	public static boolean isSexCase(File file) {
		return isSexCase(getFileContent(file));
	}

	/**
	 * 是否為性侵害或人口販運案件
	 *
	 * @param inputStr
	 * @return
	 */
	public static boolean isSexCase(String inputStr) {
		if ("".equals(getSexCaseMatchStr(inputStr))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 取得性侵害或人口販運案件關鍵字
	 *
	 * @param file
	 * @return
	 */
	public static String getSexCaseMatchStr(File file) {
		return getSexCaseMatchStr(getFileContent(file));
	}

	/**
	 * 取得性侵害或人口販運案件關鍵字
	 *
	 * @param inputStr
	 * @return
	 */
	public static String getSexCaseMatchStr(String inputStr) {
		return matcherString(inputStr,MosaicRule.getSexCaseRegex());
	}

	/**
	 * 裁判書遮隱
	 * @param file
	 * @param clnm
	 * @param nohidenameFile
	 * @return
	 */
	public String doCover(DBUtility db, File file, String[] clnm) {
	    //getCoverName(db);
		return doCover(db, getFileContent(file), clnm);
	}

	/**[A]Carl #8277 比對此file是否有需要被遮隱，有的話回傳 true
	 * 
	 * @param db
	 * @param file
	 * @param clnm
	 * @return
	 */
	public boolean doCheckCover(DBUtility db, File file, String[] clnm)throws Exception {

		String formerFile = transSpace( getFileContent(file) ); //未遮前原文
		
		//共用遮隱
		System.out.println("===================doCover Start=====================");
		String completeFile = coverFunction(db, formerFile, clnm);//完成遮隱後的原文
		System.out.println("===================doCover End=====================");
		//比對
		if(!formerFile.equals(completeFile)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 裁判書遮隱
	 * @param inputStr
	 * @param clnm
	 * @param nohidenameFile
	 * @return
	 */
	public String doCover(DBUtility db, String inputStr, String[] clnm) {
		String result = inputStr;
		try {
			System.out.println("===================doCover Start=====================");
			
			//共用遮隱
			result = coverFunction(db, inputStr, clnm);
			//主文前當事人身分證等資料遮隱
			result = hideAddressX(result, noHideNameRegex, cutWordList, noEndWordList);
			
			System.out.println("===================doCover End=====================");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("doCover Exception : " + e.toString());
		}
		return result;
	}

	/**
	 * 共用遮隱function
	 * @param db
	 * @param inputStr
	 * @param clnm
	 * @return
	 * @throws Exception
	 */
	public String coverFunction(DBUtility db, String inputStr, String[] clnm) {
		String result = inputStr;
		try{
			getCoverName(inputStr); //[A]Carl #8205
			
			this.noHideNameRegex = MosaicRule.getNoHideNameRegex();
			
			this.cutWordList = MosaicRule.getCutWordList(db);
			
			this.noEndWordList = MosaicRule.getNoEndWordList(db);//非結尾字詞
			
			result = transSpace(result);
			
			checkIgnoredPos(result);
						
			result = coverCreditCard(result); //遮信用卡號
			System.out.println("1.遮信用卡號 完成！");
			
			result = coverBirthday(result); //[A]Carl[#7545] 生日遮隱
			System.out.println("2.遮生日遮隱 完成！");
			
			result = coverCarId(result); //[A]Carl [#8205]遮車牌號碼
			System.out.println("3.遮車牌號碼 完成！");
			
			result = coverCaseHistoryId(result); //[A]Carl [#8205]遮病歷號碼
			System.out.println("4.遮病歷號碼 完成！");

			result = coverIdNo(result); //[M]Carl #8205 遮身份證號及護照號碼
			System.out.println("5.遮身份證號及護照號碼 完成！");
			
			result = coverAddress(result); //遮地址
			System.out.println("6.遮地址 完成！");

			result = coverBankId(result); //[M]Carl #8205 遮銀行帳號及金融帳號
			System.out.println("7.遮銀行帳號及金融帳號 完成！");
	
			result = coverPhone(result); //遮電話號碼
			System.out.println("8.遮電話號碼 完成！");

			// 找數字 - ( ) \n 7碼以上的全遮掉，必免沒遮到另外沒遮到
			String bankIdRegex = "([\\d\\-\\(\\)\\\\r\\\\n\\s]{7,})";
			Pattern pattern = new Pattern(bankIdRegex);
			Matcher matcher = pattern.matcher(result);
			while (matcher.find()) { 
				String temp_matcher = replace(matcher.toString()," ", "");
				int spcount = 0;
				//查出有幾個 -號
				for(int i= 0;i<temp_matcher.length();i++){
					if("()-".indexOf(temp_matcher.charAt(i))>-1)
						spcount ++;
				}
				if(temp_matcher.indexOf("\n")>-1)
					spcount += 2;
				
				if(temp_matcher.length()-spcount >=7 )
					result = replace(result, matcher.toString(), stringToZer0(matcher.toString(),"N")); 
			}
			//System.out.println(result);
			System.out.println("9.找數字 - ( ) \\n ，7碼以上的全遮掉 完成！");
			
			//[A]Carl #8205
			if (iscoverName.equals("Y")){
				result = coverName(db, result, splitClnm(clnm));
				System.out.println("10.遮當事人姓名 完成！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("coverFuction Exception : " + e.toString());
		}
		return result;
	}
	
	/**
	 * 將更名前後的名字切成兩個當事人名字，例如：李葳綺(原名李麗香)
	 * @param clnms
	 * @return
	 */
	private String[] splitClnm(String[] clnms) {
		Vector result = new Vector();
		if (clnms != null) {
			for (int i = 0; i < clnms.length; i++) {
				int pos1 = clnms[i].indexOf("(原名");
				int pos2 = clnms[i].indexOf(")");
				if (pos1 > 0 && pos2 > 0) {
					String clnm1 = clnms[i].substring(0, pos1);
					String clnm2 = clnms[i].substring(pos1 + 3, pos2);
					//System.out.println("clnm1 = " + clnm1);
					//System.out.println("clnm2 = " + clnm2);
					result.add(clnm1);
					result.add(clnm2);
				} else {
					result.add(clnms[i]);
				}
			}
		}

		String[] splitClnm = new String[result.size()];
		for (int i = 0; i < result.size(); i++) {
			splitClnm[i] = result.get(i).toString();
		}
		return splitClnm;
	}

	/**
	 * 去除不當空白
	 * @param inputStr
	 * @return
	 * @throws Exception
	 */
	public String transSpace(String inputStr) throws Exception {
		StringBuffer result = new StringBuffer();

		String newLineRegex = "[\\r\\n]+";
		// String[] strAry = new
		// Pattern(newLineRegex).tokenizer(inputStr).split(); //(C)nicole[#4907]
		String newLineRegex2 = "\r"; // (A)nicole[#4907]======
		String[] strAry = null;
		if (!inputStr.equals("")) {
			strAry = util.toStringArray(inputStr, newLineRegex2);
		} // (A)nicole[#4907]======
		int i = strAry.length - 1;
		int j = 0;
		String tempStr = "";
		byte[] byteAry = null;

		// 20070522
		// 1.空白行去除
		// 2.非空白行前段空白超過個字，強制全長為個字
		while (j <= i) {
			if (!strAry[j].trim().equals("")) {
				String tmpStrAry = ""; // (A)nicole[#4907]======
				if (j == 0) {
					tmpStrAry = strAry[j];
				} else {
					tmpStrAry = strAry[j].substring(1, strAry[j].length());
				}
				tempStr = replace(tmpStrAry, "　", "  ");
				// System.out.println("transSpace() tempStr="+tempStr);
				// //(A)nicole[#4907]======
				// tempStr = replace(strAry[j], " ", " ");// 全形空白轉為兩個半形空白,
				// (C)nicole [#4907]
				if (tempStr == null) {
					tempStr = "";
				}
				if (tempStr.length() > 80 && tempStr.substring(0, 79).trim().equals("")) {
					byteAry = tempStr.getBytes(charsetName);
					if (byteAry.length < 56) {
						tempStr = tempStr.trim();
						for (int b = 0; b < (56 - byteAry.length); b++) {
							tempStr = " " + tempStr;
						}
						result.append(tempStr + "\r\n");
					} else {
						result.append(tempStr.trim() + "\r\n");
					}
				} else {
					while (tempStr.endsWith(" ") || tempStr.endsWith("　")) {
						tempStr = tempStr.substring(0, tempStr.length() - 1);
					}
					result.append(tempStr + "\r\n");
				}
			}
			j++;
		}
		return result.toString();
	}

	/**
	 * 找出不遮隱的部份（主要指公文字號）
	 *
	 * @param inputStr
	 */
	public void checkIgnoredPos(String inputStr) {
		ignoredPosList.clear();

		String ignoredRegex = "(字[\\r\\n\\s　]*第){1}([Ｏ0-9０-９一二三四五六七八九○零壹貳肆伍陸柒捌玖第號、\\r\\n\\s　]*)((號[\\r\\n\\s　]*函)|(號[\\r\\n\\s　]*書[\\r\\n\\s　]*函)){1}";

		Pattern pattern = new Pattern(ignoredRegex);
		Matcher matcher = pattern.matcher(inputStr);
		while (matcher.find()) {
			ignoredPosList.add(new int[] { matcher.start(), matcher.end() });
		}
	}

	/** 
	 * 遮隱身分證  格式  A123456789 帶出 Z000000000
	 * 遮護照號碼  格式  護照號碼M000000000 護照號碼:M000000000
	 * @param inputStr
	 * @return
	 */
	public String coverIdNo(String inputStr) {
		
		String result = inputStr;
		
		//[A]Carl #8205 
		//遮護照號碼  格式  護照號碼M000000000 護照號碼:M000000000
		String bankIdRegex = "(護[\\\\r\\\\n\\s　]*照[\\\\r\\\\n\\s　]*號[\\\\r\\\\n\\s　]*碼[\\\\r\\\\n\\s　]*[：|:]?[Ｏ0-9０-９一二三四五六七八九○a-zA-Z\uff41-\uff5a\uff21-\uff3a]+[\\\\r\\\\n\\s　]*[Ｏ0-9０-９一二三四五六七八九○a-zA-Z\uff41-\uff5a\uff21-\uff3a]+)"; // 前幾字為自造字之"零"
		Pattern pattern = new Pattern(bankIdRegex);
		Matcher matcher = pattern.matcher(result);
		//  先將英文固定改成M，在置換成0
		while (matcher.find()) { result = replace(result, matcher.toString(), stringToZer0( strToStr(matcher.toString(),"M") ,"N")); }
		
		//遮身份證
		String engCharRegex = "[a-zA-Zａ-ｚＡ-Ｚ]";
		String numCharRegex = "([\\r\\n\\s　]*[Ｏ0-9０-９一二三四五六七八九○]){9}"; // 前幾字為自造字之"零"
		String replacement = "Z000000000";
		pattern = new Pattern(engCharRegex + numCharRegex);
		matcher = pattern.matcher(result);
		while(matcher.find()){
			int zeroNum =0;
			Pattern pattern_cherk = new Pattern("([0])");
			Matcher matcher_check = pattern_cherk.matcher(matcher.toString());
			//找到的身份證號有9個0，表示該號不為身分證號
			if(matcher_check.length()!=9){
				result = replace(result, matcher.toString(), replacement);
			}
		}
		return result;
	}

	/**
	 * 遮隱銀行帳號
	 * 遮金融帳號       例 1234-111-2222  遮0000-000-0000
	 * @param input
	 * @param ignoredPosList
	 * @return
	 */
	public String coverBankId(String inputStr) throws Exception {
		String result = inputStr;
		//遮銀行帳號
		String excludeRegex = "[^\\-\\r\\n\\s]";
		String bankIdRegex = "((字[\\r\\n\\s　]*第){1}[\\r\\n\\s　]*){0,5}([\\r\\n\\s　\\-－]*[Ｏ0-9０-９一二三四五六七八九○零壹貳肆伍陸柒捌玖]){10,16}([\\r\\n\\s　]*((號[\\r\\n\\s　]*函)|(號[\\r\\n\\s　]*書[\\r\\n\\s　]*函)){1}){0,5}";
		Pattern pattern = new Pattern(bankIdRegex);
		Matcher matcher = pattern.matcher(result);
		while (matcher.find()) {
			if (isIgnoredPos(matcher.start())) {
				continue;
			}
			if (matcher.toString().indexOf("字") >= 0
					|| matcher.toString().indexOf("第") >= 0
					|| matcher.toString().indexOf("號") >= 0
					|| matcher.toString().indexOf("書") >= 0
					|| matcher.toString().indexOf("函") >= 0
					|| matcher.toString().indexOf("○○") >= -1) {
				continue;
			}
			String replacement = replaceByRegex(matcher.toString(), excludeRegex, "0");
			result = replace(result, matcher.toString(), replacement);
		}
		
		//[A]Carl #8205 
		//遮金融帳號
		bankIdRegex = "([Ｏ0-9０-９一二三四五六七八九○零壹貳參肆伍陸柒捌玖\\-]{10,})";
		pattern = new Pattern(bankIdRegex);
		matcher = pattern.matcher(result);
		while (matcher.find()) {
			String temp_matcher = replace(matcher.toString()," ","");
			int spcount = 0;
			//查出有幾個 -號
			for(int i= 0;i<temp_matcher.length();i++){
				if("-".equals(temp_matcher.charAt(i)))
					spcount ++;
			}
			
			//查出的字串長度 減   -號的長度須  10<= ?? >=16  才要遮
			if(temp_matcher.length()-spcount >= 10 && temp_matcher.length()-spcount <= 16){
				result = replace(result, matcher.toString(), stringToZer0(matcher.toString(), "N"));
			}
		}
		return result;
	}

	/**
	 * 遮隱電話號碼
	 *
	 * @param inputStr
	 * @return
	 */
	public String coverPhone(String inputStr) {
		String result = inputStr;

		String excludeRegex = "[^\\-()\\r\\n\\s]";

		String phoneRegex = "((字[\\r\\n\\s　]*第){1}[\\r\\n\\s　]*){0,5}((((\\(){1}([\\r\\n\\s　]*[Ｏ0-9０-９一二三四五六七八九○零壹貳肆伍陸柒捌玖]){1,4}(\\)){1})([\\-－\\r\\n\\s　]*[Ｏ0-9０-９一二三四五六七八九○零壹貳肆伍陸柒捌玖]){6,9})|(([\\-－\\r\\n\\s　]*[Ｏ0-9０-９一二三四五六七八九○零壹貳肆伍陸柒捌玖]){7,10}))([\\r\\n\\s　]*((號[\\r\\n\\s　]*函)|(號[\\r\\n\\s　]*書[\\r\\n\\s　]*函)){1}){0,5}";
		Pattern pattern = new Pattern(phoneRegex);
		Matcher matcher = pattern.matcher(result);
		while (matcher.find()) {
			if (isIgnoredPos(matcher.start())) {
				continue;
			}
			if (matcher.toString().indexOf("字") >= 0
					|| matcher.toString().indexOf("第") >= 0
					|| matcher.toString().indexOf("號") >= 0
					|| matcher.toString().indexOf("書") >= 0
					|| matcher.toString().indexOf("函") >= 0
					|| matcher.toString().indexOf("○○") >= -1) {
				continue;
			}

			String replacement = replaceByRegex(matcher.toString(), excludeRegex, "0");
			// 電話號碼(02)2784-1000因括號為regex的語法, 要加\\來當一般字串
			result = replace(result, chkRegex(matcher.toString()), replacement);
		}
		return result;
	}

	/**
	 * 遮隱信用卡號
	 *
	 * @param inputStr
	 * @return
	 */
	public String coverCreditCard(String inputStr) {
		String result = inputStr;

		String excludeRegex = "[^\\-－　\\r\\n\\s]";

		String creditCardRegex = "([\\r\\n\\s　\\-－]*[Ｏ0-9０-９一二三四五六七八九○零壹貳肆伍陸柒捌玖]){16}";
		Pattern pattern = new Pattern(creditCardRegex);
		Matcher matcher = pattern.matcher(result);
		while (matcher.find()) {
			String replacement = replace(matcher.toString(), excludeRegex, "0");
			result = replace(result, matcher.toString(), replacement);
		}
		return result;
	}

	/** 
	 * 遮隱地址
	 * 查找規則 開頭字為 [住設居位於縣市]之一者(由前往後比對) 至  以[號]結尾之字串處理
	 * @param inputStr
	 * @return
	 */
	public String coverAddress(String inputStr) {
		//[M]Carl #8306 修正地址沒找到導致沒遮隱
		String result = inputStr;
		String excludeRegex = "[^\\住設居位於縣市鄉鎮區村里鄰道路街段巷弄號地\\n\\r\\s　]+";
		
		//中文字及全型數字(\uff10-\uff19)大寫全型英文(Ａ\uff21-Ｚ\uff3a)小寫全型英文(ａ\uff41-ｚ\uff5a)
		String addrRegex = "([縣市][\u4e00-\u9fa5\uff10-\uff19\uff41-\uff5a\uff21-\uff3a\\(\\)\\-（）－\\d\\s\\W^｜　]+?號)";
		Pattern pattern = new Pattern(addrRegex);
		Matcher matcher = pattern.matcher(result);

		while (matcher.find()) {
			System.out.println(matcher.group(1));
			String toMatcher = matcher.group(1);
			
			//第二次確認地址(地址需含[區村里鄰道路街段巷弄]之一以上，才完整)
			String cityDoubleCheck = "[區村里鄰道路街段巷弄]";
			Pattern pattern1 = new Pattern(cityDoubleCheck);
			Matcher matcher1 = pattern1.matcher(toMatcher);
			if(matcher1.find()){
				//取得縣市鄉鎮文字內容
				String cityExcludeRegex = "[縣市鄉鎮]"; 
				Pattern pattern2 = new Pattern(cityExcludeRegex);
				Matcher matcher2 = pattern2.matcher(toMatcher);
	
				//[M]Carl [#8276]
				int bb = 1;
				while(matcher2.find()){
					if(bb<toMatcher.lastIndexOf(matcher2.toString()));
						bb = toMatcher.lastIndexOf(matcher2.toString())+1;
				}
				
				System.out.println("---->bb="+bb);
				try{
					String top_add = toMatcher.substring(0,bb); //縣市鄉鎮前字串
					String temp_add = replace(toMatcher,top_add,""); //將鄉鎮市縣排掉
					
					//判斷最後一個關建字到號之內容 限制30byte 並不可有[，。；]符號
					bb = 0;
					Pattern pattern3 = new Pattern(cityDoubleCheck);
					Matcher matcher3 = pattern3.matcher(temp_add);
					while(matcher3.find()){
						if(bb<temp_add.lastIndexOf(matcher3.toString()));
							bb = temp_add.lastIndexOf(matcher3.toString())+1;
					}
					String bottom_add = temp_add; //此為　最後一個關建字到號之間的內容　如：巷101號　取101號判斷
					if(bb>0){
						bottom_add = temp_add.substring(bb);
					}
					bottom_add = replace(replace(bottom_add,"\r\n",""),"\n",""); //移除斷行
					
					Pattern pattern4 = new Pattern("[，。；]");
					Matcher matcher4 = pattern4.matcher(bottom_add);
					boolean check符號 = matcher4.find();
					System.out.println("--將鄉鎮市縣排掉後剩餘內容  >"+temp_add);
					System.out.println("--最後一個關建字到號之間的內容--"+(bottom_add.getBytes()).length+">["+bottom_add+"]");
					
					//1.排除表格格線號
					//2.排除[縣市鄉鎮]~號之內容長度須在100byte內
					//3.第2點之內容中[區村里鄰道路街段巷弄]最後一個關鍵字~號間之內容須在30byte內 例：101號
					//4.第2點之內容不可含有[，。；]
					if(temp_add.indexOf("│")==-1 && (temp_add.getBytes()).length <=100 && (bottom_add.getBytes()).length <=32 
							&& !checkString(temp_add)){}
					else{continue;}
					
					System.out.println("將鄉鎮市縣排掉 temp_add-->"+temp_add);
					String temp_add1=replaceForAddr(temp_add, excludeRegex);//轉0
					System.out.println("將鄉鎮市縣排掉 轉0 temp_add1-->"+temp_add1);
					result = replace(result, temp_add, temp_add1);
					
					String str[] = replace(temp_add," ","").split(""); //去半型空白
					String cityAllRegex = "";
					for(int f=0;f<str.length;f++){
						if(!"\r\n".equals(str[f]) && !"\r".equals(str[f]) && !"\n".equals(str[f])){
							cityAllRegex += !"".equals(cityAllRegex) && f>0?"[\\\\r\\\\n\\s　]*":"";
							cityAllRegex += str[f];
						}
					}
					System.out.println(cityAllRegex);
					//System.out.println(result);
					
					Pattern pattern5 = new Pattern(cityAllRegex);
					Matcher matcher5 = pattern5.matcher(result);
					
					while(matcher5.find()){
						result = replace(result, matcher5.toString(), replaceForAddr(matcher5.toString(), excludeRegex));
					}
					
					System.out.println("與原文置換完成！");
				}catch(Exception e){
					System.out.println("coverAddress err:"+e.getMessage());
				}
			}
		}
		return result;
	}

	/** [A]Carl #8205 
	 * 遮隱生日
	 * 例 民國000年00月00日出生   民國:000年00月00日出生
	 * 例 民國000年00月00日出生   民國:000年00月00日生
	 * 例 出生於0000年00月00日  出生於:000年00月00日
	 * @param inputStr
	 * @return
	 */
	public String coverBirthday(String inputStr){
		String result = inputStr;
		String excludeRegex = "[Ｏ0-9０-９一二三四五六七八九○]{0,4}[\\\\r\\\\n\\s　]*[Ｏ0-9０-９一二三四五六七八九○]{0,4}[\\\\r\\\\n\\s　]*年[\\\\r\\\\n\\s　]*";
				excludeRegex +="[Ｏ0-9０-９一二三四五六七八九○]{0,2}[\\\\r\\\\n\\s　]*[Ｏ0-9０-９一二三四五六七八九○]{0,2}[\\\\r\\\\n\\s　]*月[\\\\r\\\\n\\s　]*";
				excludeRegex +="[Ｏ0-9０-９一二三四五六七八九○]{0,2}[\\\\r\\\\n\\s　]*[Ｏ0-9０-９一二三四五六七八九○]{0,2}[\\\\r\\\\n\\s　]*日[\\\\r\\\\n\\s　]*";
				
		//例 民國000年00月00日出生   民國:000年00月00日出生
		String addrRegex = "(民[\\\\r\\\\n\\s　]*國[\\\\r\\\\n\\s　]*[：|:]?[\\\\r\\\\n\\s　]*";
		       addrRegex +=excludeRegex;
		       addrRegex +="出[\\\\r\\\\n\\s　]*生[\\\\r\\\\n\\s　]*)";
		System.out.println(addrRegex);
		Pattern pattern = new Pattern(addrRegex);
		Matcher matcher = pattern.matcher(result);
		while (matcher.find()) { result = replace(result, matcher.toString(), stringToZer0(matcher.toString(),"N") ); }

		//例 民國000年00月00日出生   民國:000年00月00日生
		addrRegex = "(民[\\\\r\\\\n\\s　]*國[\\\\r\\\\n\\s　]*[：|:]?[\\\\r\\\\n\\s　]*";
		addrRegex +=excludeRegex;
		addrRegex +="生[\\\\r\\\\n\\s　]*)";
		pattern = new Pattern(addrRegex);
		matcher = pattern.matcher(result);
		while (matcher.find()) { result = replace(result, matcher.toString(), stringToZer0(matcher.toString(),"N") ); }

		//例 出生於0000年00月00日  出生於:000年00月00日
		addrRegex = "(出[\\\\r\\\\n\\s　]*生[\\\\r\\\\n\\s　]*於[\\\\r\\\\n\\s　]*[：|:]?[\\\\r\\\\n\\s　]*";
		addrRegex +=excludeRegex;
		addrRegex +=")";
		pattern = new Pattern(addrRegex);
		matcher = pattern.matcher(result);
		while (matcher.find()) { result = replace(result, matcher.toString(), stringToZer0(matcher.toString(),"N") ); }

		return result;		
	}
	
	/** [A]Carl #8205
	 * 遮車牌號碼
	 * 例 車牌號碼GB0-000   車牌號碼:GB0-000
	 * 例 車號00-GT   車號:00-GT
	 */
	public String coverCarId(String inputStr){
		String result = inputStr;
				
		//例 車牌號碼GB0-000   車牌號碼:GB0-000  大寫全型英文(Ａ\uff21-Ｚ\uff3a)小寫全型英文(ａ\uff41-ｚ\uff5a)  
		String addrRegex = "(車[\\\\r\\\\n\\s　]*牌[\\\\r\\\\n\\s　]*號[\\\\r\\\\n\\s　]*碼[\\\\r\\\\n\\s　]*[：|:]?[\\\\r\\\\n\\s　]*";
		     
		addrRegex +="[Ｏ0-9０-９一二三四五六七八九○a-zA-Z\uff41-\uff5a\uff21-\uff3a\\-－]+[\\\\r\\\\n\\s　]*[Ｏ0-9０-９一二三四五六七八九○a-zA-Z\uff41-\uff5a\uff21-\uff3a\\-－]+)";
		Pattern pattern = new Pattern(addrRegex);
		Matcher matcher = pattern.matcher(result);
		while (matcher.find()) { result = replace(result, matcher.toString(), stringToZer0(matcher.toString(),"A") ); }
		
		//例 車號00-GT   車號:00-GT
		addrRegex = "(車[\\\\r\\\\n\\s　]*號[\\\\r\\\\n\\s　]*[：|:]?[\\\\r\\\\n\\s　]*";
		addrRegex +="[Ｏ0-9０-９一二三四五六七八九○a-zA-Z\uff41-\uff5a\uff21-\uff3a\\-－]+[\\\\r\\\\n\\s　]*[Ｏ0-9０-９一二三四五六七八九○a-zA-Z\uff41-\uff5a\uff21-\uff3a\\-－]+)";
		pattern = new Pattern(addrRegex);
		matcher = pattern.matcher(result);
		while (matcher.find()) { result = replace(result, matcher.toString(), stringToZer0(matcher.toString(),"A") ); }
		
		return result;
	}
	
	/** [A]Carl #8205
	 * 遮病例號碼
	 * 例 病歷00000   病歷00000
	 * 例 病歷號碼00000   病歷號碼:00000 
	 * @param inputStr
	 * @return
	 */
	public String coverCaseHistoryId(String inputStr){
		String result = inputStr;
				
		//例 病歷00000   病歷00000
		String addrRegex = "(病[\\\\r\\\\n\\s　]*歷[\\\\r\\\\n\\s　]*[：|:]?[Ｏ0-9０-９一二三四五六七八九○a-zA-Z\uff41-\uff5a\uff21-\uff3a\\\\r\\\\n\\s　]+)";
		Pattern pattern = new Pattern(addrRegex);
		Matcher matcher = pattern.matcher(result);
		while (matcher.find()) { 
			result = replace(result, matcher.toString(), stringToZer0(matcher.toString(),"A") ); 
		}
		
		//例 病歷號碼00000   病歷號碼:00000
		addrRegex = "(病[\\\\r\\\\n\\s　]*歷[\\\\r\\\\n\\s　]*號[\\\\r\\\\n\\s　]*碼[\\\\r\\\\n\\s　]*[：|:]?[Ｏ0-9０-９一二三四五六七八九○a-zA-Z\uff41-\uff5a\uff21-\uff3a\\\\r\\\\n\\s　]+)";
		pattern = new Pattern(addrRegex);
		matcher = pattern.matcher(result);
		while (matcher.find()) { result = replace(result, matcher.toString(), stringToZer0(matcher.toString(),"A") ); }
		
		return result;
	}
	
	/**
	 * 遮隱姓名
	 * 只遮s08抬頭以下內容之姓名(s08未設時，全遮)
	 * @param inputStr
	 * @param clnm
	 * @return
	 */
	public String coverName(DBUtility db, String inputStr, String[] clnm) {
		String result_top = ""; //抬頭關鍵字以上
		String result_bottom = inputStr; //抬頭關鍵字以下(預設全部內容)
		if (clnm == null) {
			return result_bottom;
		}
		
		String reportTatle = "";//找到的主文抬頭詞
		String[] checkTatle = gets08title(db,"主文頭");
		if(checkTatle == null){
			checkTatle = new String[1];
			checkTatle[0] = "主文"; //找不到時的預設值
		}
		for(int i=0;i<checkTatle.length;i++){
			String addrRegex="";
			//將字串組成正規式
			String[] tStr = checkTatle[i].split("");
			for(int b=0;b<tStr.length;b++)
				addrRegex += ((b>0)?"[\\s　]*":"")+ tStr[b];
			
			System.out.println("===="+addrRegex);
			Pattern pattern = new Pattern(addrRegex);
			Matcher matcher = pattern.matcher(result_bottom);
			if (matcher.find()) { 
				reportTatle = matcher.toString();
				//找到後跳出此迴圈
				break;
			}
		}
		
		//頡取抬頭以下的所有字串內容
		if(!"".equals(reportTatle)){
			result_top = result_bottom.substring( 0,result_bottom.indexOf(reportTatle));
			result_bottom = result_bottom.substring( result_bottom.indexOf(reportTatle));
		}

		// 姓名判斷邏輯
		// 1.大於3byte
		// 2.去掉全形半形"("之後的資料
		// 3.某些保留字跳過不處理

		// 含有下列字詞，該行不處理
		// NOHIDENAME
		// "Ltd.|大使|大隊|大學|小吃|小學|工務|工程|工會|工業|工廠....."
		String excludeRegex = noHideNameRegex;
		Pattern excludePattern = new Pattern(excludeRegex);

		String replaceClnm = "";
		int nameSeq = 1;
		Map findMap = new HashMap();
		findMap.put(isFind, Boolean.FALSE);

		for (int i = 0; i < clnm.length; i++) {
			if (clnm[i] != "" && clnm[i].length() > 1) {
				replaceClnm = chkWorkingString(clnm[i].trim());
				if ("".equals(noHideNameRegex)?true:!excludePattern.matcher(replaceClnm).find()) {
					//replaceClnm = chkWorkingString(clnm[i].trim());
					//System.out.println("replaceClnm = " + replaceClnm);
					if (!"".equals(replaceClnm)) {
						if (((Boolean) findMap.get(isFind)).booleanValue()) {
							nameSeq++;
						}
						result_bottom = replaceOneName(result_bottom, replaceClnm, nameSeq, findMap);
					}
				}
			}
		}

		return result_top+result_bottom;
	}

	/**
	 * 電話號碼(02)2784-1000因括號為regex的語法, 要加\\來當一般字串
	 *
	 * @param regex
	 * @return
	 */
	public String chkRegex(String regex) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < regex.length(); i++) {
			if ("(".equals(regex.substring(i, i + 1)) || ")".equals(regex.substring(i, i + 1))) {
				result.append("\\" + regex.substring(i, i + 1));
			} else {
				result.append(regex.substring(i, i + 1));
			}
		}
		return result.toString();
	}

	public String chkWorkingString(String name) {
		String result = "";
		for (int i = 0; i < name.length(); i++) {
			if (" ".equals(name.substring(i, i + 1))
					|| "0".equals(name.substring(i, i + 1))
					|| "1".equals(name.substring(i, i + 1))
					|| "2".equals(name.substring(i, i + 1))
					|| "3".equals(name.substring(i, i + 1))
					|| "4".equals(name.substring(i, i + 1))
					|| "5".equals(name.substring(i, i + 1))
					|| "6".equals(name.substring(i, i + 1))
					|| "7".equals(name.substring(i, i + 1))
					|| "8".equals(name.substring(i, i + 1))
					|| "9".equals(name.substring(i, i + 1))
					|| "０".equals(name.substring(i, i + 1))
					|| "１".equals(name.substring(i, i + 1))
					|| "２".equals(name.substring(i, i + 1))
					|| "３".equals(name.substring(i, i + 1))
					|| "４".equals(name.substring(i, i + 1))
					|| "５".equals(name.substring(i, i + 1))
					|| "６".equals(name.substring(i, i + 1))
					|| "７".equals(name.substring(i, i + 1))
					|| "８".equals(name.substring(i, i + 1))
					|| "９".equals(name.substring(i, i + 1))) {
				continue;
			} else if ("(".equals(name.substring(i, i + 1))
					|| "（".equals(name.substring(i, i + 1))) {
				break;
			} else {
				result += name.substring(i, i + 1);
			}
		}
		return result;
	}

	private String replaceOneName(String inputStr, String name, int nameSeq, Map findMap) {
		String result = inputStr;
		String codeString = "甲乙丙丁戊己庚辛壬癸子丑寅卯辰巳午未申酉戌亥天地宇宙玄黃"
				+ "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"
				+ "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ";

		String nameRegex = chkEscape(name.substring(0, 1));
		name = name.trim();
		String replacement = "";
		if (nameSeq <= codeString.length()) {
			int codeStringIndex = (nameSeq - 1) % (codeString.length());
			replacement = codeString.substring(codeStringIndex, codeStringIndex + 1);
			for (int i = 1; i < name.length(); i++) {
				nameRegex = nameRegex + "[\\r\\n\\s　]{0,10}" + chkEscape(name.substring(i, i + 1));
				replacement = replacement + "○";
			}
		} else {
			int j = (nameSeq - codeString.length() - 1) / codeString.length();
			replacement = codeString.substring(j % (codeString.length()), j % (codeString.length()) + 1);
			for (int i = 1; i < name.length(); i++) {
				nameRegex = nameRegex + "[\\r\\n\\s　]{0,10}" + chkEscape(name.substring(i, i + 1));
				if (i == 1) {
					if ((nameSeq - j * codeString.length() - 1) == -1) {
						replacement = replacement + codeString.substring(codeString.length() - 1);
					} else {
						int codeStringIndex = (nameSeq - j * codeString.length() - 1) % (codeString.length());
						replacement = replacement + codeString.substring(codeStringIndex, codeStringIndex + 1);
					}
				} else {
					replacement = replacement + "○";
				}
			}
		}
		nameRegex = "(" + nameRegex + ")";
		result = replaceByRegex(result, nameRegex, replacement, findMap);
		return result;
	}

	public String chkEscape(String str) {
		String result = str;
		if (".".equals(result) ||
			"$".equals(result) ||
			"^".equals(result) ||
			"{".equals(result) ||
			"[".equals(result) ||
			"(".equals(result) ||
			"|".equals(result) ||
			")".equals(result) ||
			"*".equals(result) ||
			"+".equals(result) ||
			"?".equals(result) ||
			"\\".equals(result)){
			result = "\\" + result;
		}
		return result;
	}

	private boolean isIgnoredPos(int position) {
		if (ignoredPosList != null) {
			for (int i = 0; i < ignoredPosList.size(); i++) {
				int[] ignoredPosAry = (int[]) ignoredPosList.get(i);

				if (position >= ignoredPosAry[0]
						&& position <= ignoredPosAry[1]) {
					return true;
				}
			}
		}
		return false;
	}

	/** [A]Carl #8205 
	 * 英數置換成 ○ 例  123-AB ->  ○○○-○○
	 * @param inputStr
	 * @param set  N:只換數字  E:只換英文(全半形) A:英數全換
	 * @return
	 */
	public static String stringToZer0(String inputStr,String set){
		String result = inputStr;
		//只換置數字
		if("N".equals(set) || "A".equals(set)){
			String addrRegex = "([Ｏ0-9０-９一二三四五六七八九○零壹貳肆伍陸柒捌玖])";
			Pattern pattern = new Pattern(addrRegex);
			Matcher matcher = pattern.matcher(result);
			while (matcher.find()) { 
				String line = matcher.toString();
				if(line.charAt(0) <= 127)
					result = replace(result, line, "0"); 
				else
					result = replace(result, line, "○"); 
			}
		}
		
		//換置英文  大寫全型英文(Ａ\uff21-Ｚ\uff3a)小寫全型英文(ａ\uff41-ｚ\uff5a)  
		if("E".equals(set) || "A".equals(set)){
			
			String addrRegex = "([a-zA-Z\uff21-\uff3a\uff41-\uff5a])";
			Pattern pattern = new Pattern(addrRegex);
			Matcher matcher = pattern.matcher(result);
			while (matcher.find()) { result = replace(result, matcher.toString(), "0"); }
		}
		
		//[M]Carl #8306 地址資料為半型時，轉換成0其於為○
		return result;
	}
	
	/** [A]Carl #8205 
	 * 英文換成特定字元   
	 * @param inputStr
	 * @param upStr 換成特定字元
	 * @return
	 */
	public static String strToStr(String inputStr,String upStr){
		String result = inputStr;
		//英文換成特定字元
		String addrRegex = "([a-zA-Z])";
		Pattern pattern = new Pattern(addrRegex);
		Matcher matcher = pattern.matcher(result);
		while (matcher.find()) { result = replace(result, matcher.toString(), upStr); }
		return result;
	}
	
	public static String replace(String inputStr, String oldStr, String newStr) {
		String result = "";
		try {
			Vector vt = new Vector();

			int p1 = 0;
			while ((p1 = inputStr.indexOf(oldStr)) >= 0) {
				int p2 = p1 + oldStr.length();
				String leftStr = inputStr.substring(0, p1);
				vt.addElement(leftStr);
				inputStr = inputStr.substring(p2);
			}
			vt.addElement(inputStr);

			int lenOfVec = vt.size();
			if (lenOfVec != 0) {
				String retString = (String) vt.get(0);
				for (int i = 1; i < lenOfVec; i++) {
					retString += newStr + vt.get(i);
				}
				return retString;
			} else {
				return inputStr;
			}

		} catch (Exception ex) {
		}

		// 回傳值
		return result;
	}

	/**
	 * 替換字串
	 *
	 * @param inputStr
	 * @param regex
	 * @return
	 */
	public static String replaceForAddr(String inputStr, String regex) {
		StringBuffer result = new StringBuffer();
		Pattern pattern = new Pattern(regex);
		Matcher matcher = pattern.matcher(inputStr);
		int lastIndex = 0;
		while (matcher.find()) {
			String aMatchStr = matcher.toString();
			if (aMatchStr != null && !"".equals(aMatchStr)) {
				result.append(inputStr.substring(lastIndex, matcher.start()));
				//[M]Carl #8306 地址資料為半型時，轉換成0其於為○
				for (int i = 0; i < aMatchStr.length(); i++) {
					if(aMatchStr.charAt(i) <= 127)
						result.append("0");
					else
						result.append("○");
				}
				lastIndex = matcher.end();
			}
		}
		if (matcher.end() >= 0) {
			result.append(inputStr.substring(matcher.end()));
		} else {
			result.append(inputStr);
		}

		return result.toString();
	}

	/**
	 * 替換字串
	 * @param inputStr
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replaceByRegex(String inputStr, String regex, String replacement) {
		return replaceByRegex(inputStr, regex, replacement, new HashMap());
	}

	/**
	 * 替換字串
	 * @param inputStr
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replaceByRegex(String inputStr, String regex, String replacement, Map findMap) {
		StringBuffer result = new StringBuffer();

		Pattern pattern = new Pattern(regex);
		Matcher matcher = pattern.matcher(inputStr);
		int lastIndex = 0;
		findMap.put(isFind, Boolean.FALSE);
		while (matcher.find()) {
			findMap.put(isFind, Boolean.TRUE);
			String aMatchStr = matcher.toString();
			if (aMatchStr != null && !"".equals(aMatchStr)) {
				result.append(inputStr.substring(lastIndex, matcher.start()));
				result.append(replaceCharDigit(aMatchStr, replacement));
				lastIndex = matcher.end();
			}
		}

		if (matcher.end() >= 0) {
			result.append(inputStr.substring(matcher.end()));
		}else{
			result.append(inputStr);
		}

		return result.toString();
	}


	/**
	 * 處理折行的問題
	 *
	 * @param matchStr
	 * @param replaceStr
	 * @return
	 */
	private static String replaceCharDigit(String matchStr, String replaceStr) {
		//if (replaceStr.length() != matchStr.length()) {
		//	return matchStr;
		//}
		String replacement = "";
		char[] match = matchStr.toCharArray();
		char[] replace = replaceStr.toCharArray();
		int r = 0;
		for (int i = 0; i < match.length; i++) {
			// 全形空白,半形空白,換行符號保留原始字串
			if ((match[i] == '　') || (match[i] == (char) 32)
					|| (match[i] == (char) 13) || (match[i] == (char) 10)) {
				replacement = replacement + match[i];
				//System.out.println("match[i] = " + match[i]);
			} else {
				if (r > replace.length - 1) {
					System.out.println("matchStr = " + matchStr);
					System.out.println("replaceStr = " + replaceStr);
				}
				replacement = replacement + replace[r];
				//System.out.println("replace[r] = " + replace[r]);
				r++;
			}
		}
//		System.out.println("======= replaceCharDigit ======");
//		System.out.println("matchStr = " + matchStr);
//		System.out.println("replaceStr = " + replaceStr);
//		System.out.println("replacement = " + replacement);
//		System.out.println("======= replaceCharDigit ======");
		return replacement;
	}

	public static String hideAddressX(String inputStr, String nohidename, List cutWordList, List noEndWordList) {
		//return hideAddress(inputStr, "<span style=\"color:#e8e8e8\">.</span>", nohidename);
		return hideAddress(inputStr, ".", nohidename, cutWordList, noEndWordList);
	}

	public static String hideAddress(String inputStr, String hideMark, String nohidename, List cutWordList, List noEndWordList) {
		String result = inputStr;
		if (!"".equals(inputStr)) {
			// 讀出裁判書中當事人資料部分（主文之前）
			String mainTextRegex = "[\\r\\n]*[ 　]*主[ 　]*文[ 　]*[\\r\\n]*";
			Pattern pattern = new Pattern(mainTextRegex);
			Matcher matcher = pattern.matcher(inputStr);
			if (matcher.find()) {
				result = hideAddress2(result.substring(0, matcher.start()), hideMark, nohidename, cutWordList, noEndWordList) + matcher.toString() + result.substring(matcher.end());
			} else {
				result = hideAddress2(result, hideMark, nohidename, cutWordList, noEndWordList);
			}
		}
		return result;
	}

	private static String hideAddress2(String inputStr, String hideMark, String nohidename, List cutWordList, List noEndWordList) {
		String result = "";

		Pattern skipPattern = new Pattern(nohidename);
		Pattern hidePattern = new Pattern("[\\r\\n]+");

		boolean isFirstLine = true;
		boolean isEndLine   = false;
		boolean isUpLine    = false; //檢查是否有「上」關建字結尾
		boolean 開頭行數跳過  = true;
		boolean hasDraft    = inputStr.indexOf("【裁判要旨】")>=0;

		if (hidePattern.matcher(inputStr).find()) {
			String[] lines = hidePattern.tokenizer(inputStr).split();
			String aLine = "";

			//簡查是否有「上」關建字結尾
			for (int i = 0; i < lines.length; i++) {
				aLine = replace(lines[i], "  ", "　");
				if( 開頭行數跳過  && (i==0 || aLine.startsWith("　　　　　　　　　")) ) { // 9個"　"
					//跳過抬頭判斷，並跳過抬頭下前9個空白以上的內容
				} else if( aLine.startsWith("上") && !aLine.startsWith("上　訴　人") && !aLine.startsWith("上訴人") ){
					//[M]Carl #8025 如資料非上　[\\s]*列[\\s]*.*人　 才表示該行為結尾
					String cutRegex = "上[\\s]*.*[\\s]*.*人";
	         	    Pattern pattern = new Pattern(cutRegex);
	                Matcher matcher2 = pattern.matcher(aLine);
	                
	                if (!matcher2.find() || (aLine.startsWith("上") && (aLine.indexOf("事件") >= 0 || aLine.indexOf("案件") >= 0))) {
	                	isUpLine = true;
	                }
				}
			}
			System.out.println("----------------isUpLine["+isUpLine+"]----------------");
			
			for (int i = 0; i < lines.length; i++) {
				
				aLine = replace(lines[i], "  ", "　");
				
				boolean noEndWordLine = false; //此段是否有非結尾字詞設定 S1F中， 有：true
				for(int f = 0; f< noEndWordList.size();f++){
					if(aLine.indexOf((String)noEndWordList.get(f))>-1){
						noEndWordLine = true;
						break;
					}
				}
				
				System.out.println("--aLine        ->"+aLine);
				System.out.println("--isEndLine    ->"+isEndLine);
				System.out.println("--noEndWordLine->"+noEndWordLine);
				
				if (isFirstLine) {
					if (hasDraft && (!"【裁判全文】".equals(aLine.trim()))) {
						isFirstLine = true;
					} else {
						if ("【裁判全文】".equals(aLine.trim())) {
							hasDraft = false;
							isFirstLine = true;
						} else {
							isFirstLine = false;
						}
					}
				} else if( 開頭行數跳過  && (i==0 || aLine.startsWith("　　　　　　　　　")) ) { // 9個"　"
					//跳過抬頭判斷，並跳過抬頭下前9個空白以上的內容
				} else {
					開頭行數跳過 = false;
					if(isUpLine && (aLine.startsWith("上") && !aLine.startsWith("上　訴　人") && !aLine.startsWith("上訴人") && !aLine.startsWith("上　 訴　 人")) ){
						//[M]Carl #8025 如資料非上　[\\s]*列[\\s]*.*人　 才表示該行為結尾
						String cutRegex = "上[\\s]*.*[\\s]*.*人";
	             	    Pattern pattern = new Pattern(cutRegex);
	                    Matcher matcher2 = pattern.matcher(aLine);
	                    
	                    if (!matcher2.find() || (aLine.startsWith("上") && (aLine.indexOf("事件") >= 0 || aLine.indexOf("案件") >= 0)) ) {
	                    	isEndLine = true;
	                    }
	                    
					}else if(!isUpLine && 
					    (aLine.startsWith("右")
						|| aLine.startsWith("一、")
						|| aLine.indexOf("事件") >= 0 || aLine.indexOf("案件") >= 0 ) 
						&& !noEndWordLine ){
						isEndLine = true;
						
					}else if ((!isFirstLine) && (!isEndLine)) {
						if (aLine.startsWith("　　　　　　　　　")) { // 9個"　"
							continue; //主文前的當事人資料只留第一行
						}
						if(cutWordList != null){
							for (int c = 0; c < cutWordList.size(); c++) {
								int pos = aLine.indexOf(cutWordList.get(c).toString());
								if (pos >= 0) {
									aLine = aLine.substring(0, pos);
									break;
								}
							}
						}
						
						//[A]Carl [#8276]判斷是否要截字 (checkisCutString)
						if ( !checkisCutString(aLine) && aLine.length() > 14 && ("".equals(nohidename)?true:!skipPattern.matcher(aLine).find())) {
							if (aLine.indexOf("<!>") >= 0) {
								aLine = aLine.substring(0, aLine.indexOf("<!>"));
							} else {
								aLine = replace(aLine.substring(0, 15), "（", "") + hideMark;
							}
						}
					}
				}
				result += aLine + "\r\n";
				
				/*
				if ((aLine.startsWith("上") && !aLine.startsWith("上　訴　人") && !aLine.startsWith("上訴人") && !aLine.startsWith("上　 訴　 人"))		|| aLine.startsWith("右")
						|| aLine.startsWith("一、")
						|| aLine.startsWith("當事人")
						|| aLine.startsWith("抗告人")
						|| (aLine.startsWith("被上訴人") && !aLine.startsWith("被上訴人即附帶上訴人"))
						|| aLine.indexOf("事件") >= 0
						|| aLine.indexOf("案件") >= 0) {
					
					//[M]Carl #8025 如資料非上　[\\s]*列[\\s]*.*人　 才表示該行為結尾
					String cutRegex = "上[\\s]*.*人";
             	    Pattern pattern = new Pattern(cutRegex);
                    Matcher matcher2 = pattern.matcher(aLine);
                    
                    if (!matcher2.find()) {
                    	isEndLine = true;
                    }
				}else if ((!isFirstLine) && (!isEndLine)) {
					if (aLine.startsWith("　　　　　　　　　")) { // 9個"　"
						continue; //主文前的當事人資料只留第一行
					}
					if(cutWordList != null){
						for (int c = 0; c < cutWordList.size(); c++) {
							int pos = aLine.indexOf(cutWordList.get(c).toString());
							if (pos >= 0) {
								aLine = aLine.substring(0, pos);
								break;
							}
						}
					}
					
					//[A]Carl [#8276]判斷是否要截字 (checkisCutString)
					if ( !checkisCutString(aLine) && aLine.length() > 14 && ("".equals(nohidename)?true:!skipPattern.matcher(aLine).find())) {
						if (aLine.indexOf("<!>") >= 0) {
							aLine = aLine.substring(0, aLine.indexOf("<!>"));
						} else {
							aLine = replace(aLine.substring(0, 15), "（", "") + hideMark;
						}
					}
				}
				result += aLine + "\r\n";*/
			}
			result = result.substring(0, result.length() - 2);
		} else {
			result = inputStr;
		}
		return result;
	}
	//Ender 改 public
	public static String getFileContent(File file) {
		StringBuffer inputStr = new StringBuffer();
		BufferedReader bufReader = null;
		try {
			if (file.exists()) {
				bufReader = new BufferedReader(new FileReader(file));
				String aLine = null;
				while ((aLine = bufReader.readLine()) != null) {
					inputStr.append(aLine + "\n");
				}
				bufReader.close();
			} else {
				System.out.println(file.getPath() + " 不存在!");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("getFileContent IOException : " + ioe.toString());
		}
		return inputStr.toString();
	}

	/**
	 * 查檔案中字串出現幾次
	 * @param filenm 搜尋檔案路徑
	 * @param searchTag 搜尋字串
	 * @return
	 */
	public static int getFileContent(String filenm,String searchTag) {
		int i = 0 ;
		BufferedReader bufReader = null;
		try {
			File file = new File(filenm);
			if (file.exists()) {
				bufReader = new BufferedReader(new FileReader(file));
				String aLine = null;
				while ((aLine = bufReader.readLine()) != null) {
					if( aLine.indexOf(searchTag)!=-1){
						  i++;
					}
				}
				bufReader.close();
			} else {
				System.out.println(file.getPath() + " 不存在!");
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("getFileContent IOException : " + ioe.toString());
		}
		return i;
	}

	/**
	 * 更新R01.MOSAIC並記錄到MOSAICLOG
	 * @param db
	 * @param owner
	 * @param sys
	 * @param crmyy
	 * @param crmid
	 * @param crmno
	 * @param chkno
	 * @param nmosaic
	 * @param usrid
	 */
	public static void doUpdateR01Mosaic(DBUtility db, String owner,
			String sys, String crmyy, String crmid, String crmno, String chkno,
			String nmosaic, String usrid,String flag) {
		util_date ud = new util_date();
		StringBuffer sql = new StringBuffer();
		String omosaic = "";
		String history = "";

		sql.setLength(0);
		sql.append("select mosaic, ");
		sql.append("'' as history ");
		sql.append("from " + owner + ".r01 ");
		sql.append("where sys = '" + sys + "' ");
		sql.append("and crmyy = '" + crmyy + "' ");
		sql.append("and crmid = '" + crmid + "' ");
		sql.append("and crmno = '" + crmno + "' ");
		sql.append("and chkno = '" + chkno + "' ");

		sql.append("union ");

		sql.append("select mosaic, ");
		sql.append("'H' as history ");
		sql.append("from H" + owner + ".r01 ");
		sql.append("where sys = '" + sys + "' ");
		sql.append("and crmyy = '" + crmyy + "' ");
		sql.append("and crmid = '" + crmid + "' ");
		sql.append("and crmno = '" + crmno + "' ");
		sql.append("and chkno = '" + chkno + "' ");
		Vector vt = db.doSqlSelect(sql.toString(), 2, false);
		if (vt.size() > 0) {
			omosaic = vt.get(0).toString();
			history = vt.get(1).toString();
		}

		sql.setLength(0);
		sql.append("update " + history + owner + ".r01 ");
		sql.append("set mosaic = '" + nmosaic + "' ");
		sql.append("where sys = '" + sys + "' ");
		sql.append("and crmyy = '" + crmyy + "' ");
		sql.append("and crmid = '" + crmid + "' ");
		sql.append("and crmno = '" + crmno + "' ");
		sql.append("and chkno = '" + chkno + "' ");
		db.doSqlUpdate(sql.toString());

		sql.setLength(0);
		sql.append("insert into " + owner + ".mosaiclog ");
		sql.append("(sys, ");
		sql.append("crmyy, ");
		sql.append("crmid, ");
		sql.append("crmno, ");
		sql.append("chkno, ");
		sql.append("omosaic, ");
		sql.append("nmosaic, ");
		sql.append("usrid, ");
		sql.append("logdt, ");
		sql.append("logtm,flag) ");
		sql.append("values('" + sys + "', ");
		sql.append("'" + crmyy + "', ");
		sql.append("'" + crmid + "', ");
		sql.append("'" + crmno + "', ");
		sql.append("'" + chkno + "', ");
		sql.append("'" + omosaic + "', ");
		sql.append("'" + nmosaic + "', ");
		sql.append("'" + usrid + "', ");
		sql.append("'" + ud.nowCDate() + "', ");
		sql.append("'" + ud.nowTime().substring(0, 4) + "', ");
		sql.append("'" + flag + "') ");
		db.doSqlUpdate(sql.toString());
	}
	// 得到 G.s08的 上傳抬頭
	private static String[] gets08title(DBUtility db,String argnm) {
		String result = gets08argv1(db,argnm);
		if (result.equals(""))
			return null;
		else
			return util.toStringArray(result, ",");
	}

	private static String gets08argv1(DBUtility db,String argv1) {
		Vector tempV = new Vector();
		String tempValue = "";
		String sqltemp = "select argvl from G.s08 where prgid='SHD6B01' and argnm='" + argv1 + "'";
		System.out.println("g.s08抬頭="+sqltemp);
		tempV = db.doSqlSelect(sqltemp, 1, false);
		if (tempV.size() != 0) {
			for (int i = 0; i < tempV.size(); i++)
				tempValue += tempV.elementAt(i).toString();
			return tempValue;
		} else {
			return tempValue;
		}
	}
	
	// 得到 G.s08的 不上傳字別
	private static String[] getNOCRMID(DBUtility db,String owner) {
		String result = "";
		if ("V".equals(owner.toUpperCase())) {
			result = gets08argv1(db,"民事不上傳字別");
		} else if ("H".equals(owner.toUpperCase())) {
			result = gets08argv1(db,"刑事不上傳字別");
		} else {
			result = gets08argv1(db,"少年不上傳字別");
		}

		if ("".equals(result))
			return null;
		else
			return util.toStringArray(result, ",");
	}

	// 得到 G.s08的 不公開上傳字別
	private static String[] getNOOPENCRMID(DBUtility db) {
		String result = "";
		result = gets08argv1(db,"刑事不公開字別");

		if ("".equals(result))
			return null;
		else
			return util.toStringArray(result, ",");
	}

	//[A]Carl [Bug #8629] 得到 G.s08的 視為上傳要旨
	private static String[] getCMPTM(DBUtility db) {
		String result = gets08argv1(db,"視為上傳要旨");
		if (result.equals(""))
			return null;
		else
			return util.toStringArray(result, ",");
	}

	// 得到 G.s08的 不上傳終結要旨
	private static String[] getNOCMPTM(DBUtility db) {
		String result = gets08argv1(db,"不上傳終結要旨");
		if (result.equals(""))
			return null;
		else
			return util.toStringArray(result, ",");
	}

	// 得到 G.s08的 不上傳案由
	private static String[] getNORSN(DBUtility db,String owner) {
		String result = "";
		if ("V".equals(owner.toUpperCase())) {
			result = gets08argv1(db,"民事不上傳案由");
		} else if ("H".equals(owner.toUpperCase())) {
			result = gets08argv1(db,"刑事不上傳案由");
		} else {
			result = gets08argv1(db,"少年不上傳案由");
		}

		if (result.equals("")) {
			return null;
		} else
			return util.toStringArray(result, ",");
	}

	// 得到 G.s08的 不上傳字別
	private static String[] getNOID(DBUtility db,String owner) {
		String result = "";
		if ("V".equals(owner.toUpperCase())) {
			result = gets08argv1(db,"民事遮隱不詢問字別");
		} else if ("H".equals(owner.toUpperCase())) {
			result = gets08argv1(db,"刑事遮隱不詢問字別");
		} else {
			result = gets08argv1(db,"少年遮隱不詢問字別");
		}

		if ("".equals(result))
			return null;
		else
			return util.toStringArray(result, ",");
	}

	// 得到 G.s08的 不上傳抬頭
	private static String[] getNOTITLE(DBUtility db) {
		String result = gets08argv1(db,"不上傳抬頭");
		if (result.equals(""))
			return null;
		else
			return util.toStringArray(result, ",");
	}

	//原  得到 G.s08的 是否遮隱人名
	//改  取裁判書是否有相關詞，有就要遮 
	//private void getCoverName(DBUtility db) {
	private void getCoverName(String formerFile) {
		//this.iscoverName = gets08argv1(db,"遮隱人名");
		String result = "";
		String sexCaseRegex = MosaicRule.getSexCaseCoverNameRegex();

		Pattern pattern = new Pattern(sexCaseRegex);
		Matcher matcher = pattern.matcher( formerFile );
		if (matcher.find()) {
			result = matcher.toString();
		}
		
		System.out.println("------>查到是否要遮姓名的關鍵詞 ["+result+"]");
		
		//查不到關鍵詞即不遮姓名
		if("".equals(result)){
			this.iscoverName="N"; //不遮
		}else{
			this.iscoverName="Y"; //遮
		}
	}

	/*
	// 得到 G.s08的 上傳抬頭
	private static String[] getTITLE(DBUtility db) {
		String result = gets08argv1(db,"上傳抬頭");
		if (result.equals(""))
			return null;
		else
			return util.toStringArray(result, ",");
	}
	*/

	//判斷案件是否要上傳
	public static boolean isUpload(DBUtility db,String owner,String crmyy,String crmid,String crmno,
			Vector cmptm,String rsn,String inputStr) {
		boolean check = true;

		String[] nocrmid = getNOCRMID(db,owner); //不上傳字別
		if(nocrmid!=null){
			for (int i = 0; i < nocrmid.length; i++) {
				if (crmid.equals(nocrmid[i])) {
					System.out.println("此案字別為不上傳字別");
					check=false;
					break;
				}
			}
		}

		String[] noid = getNOID(db,owner); //不上傳字別
		if(noid!=null){
			for (int i = 0; i < noid.length; i++) {
				if (crmid.equals(noid[i])) {
					System.out.println("此案字別為不詢問字別");
					check=false;
					break;
				}
			}
		}
		//System.out.println("...1...");
		if ("H".equals(owner.toUpperCase())) {
			String[] noopencrmid = getNOOPENCRMID(db); //不公開字別
			if(noopencrmid!=null){
				for (int i = 0; i < noopencrmid.length; i++) {
					if (crmid.equals(noopencrmid[i])) {
						System.out.println("此案字別為不公開字別");
						check=false;
						break;
					}
				}
			}
		}
		
		//System.out.println("...2...");
		//[A]Carl [Bug #8629] 當cmptm為 視為上傳要旨 時 不在判斷視為不上傳終結要旨 
		String[] nocmptm = getNOCMPTM(db); //不上傳終結要旨
		String[] yescmptm = getCMPTM(db); //視為上傳要旨
		boolean chcmptm = false; //有無 視為上傳要旨 預設為無
		//System.out.println("cmptm.size=["+cmptm.size()+"],cmptm="+cmptm);
		//System.out.println("nocmptm="+nocmptm);
		if(yescmptm != null){
			for (int i = 0; i < cmptm.size(); i++) {
				for (int j = 0; j < yescmptm.length; j++) {
					if (((String)cmptm.elementAt(i)).indexOf(yescmptm[j]) != -1) {
						System.out.println("此案為視為上傳要旨");
						chcmptm=true;
						break;
					}
				}
			}
		}
			
		if(nocmptm!=null && !chcmptm){
			if(cmptm.size() > 0){
				for (int i = 0; i < cmptm.size(); i++) {
					for (int j = 0; j < nocmptm.length; j++) {
						//System.out.println("i=["+i+"],j=["+j+"],cmptm=["+(String)cmptm.elementAt(i)+"],nocmptm[j]=["+nocmptm[j]+"]");
						if (((String)cmptm.elementAt(i)).indexOf(nocmptm[j]) != -1) {
							System.out.println("此案為不上傳終結要旨");
							check=false;
							break;
						}
					}
				}
			}
		}
		
		//System.out.println("...3...");
		String[] norsn = getNORSN(db,owner); //不上傳案由
		if(norsn!=null){
			for (int i = 0; i < norsn.length; i++) {
				if (rsn.indexOf(norsn[i]) != -1) {
					System.out.println("此案為不上傳案由");
					check=false;
					break;
				}
			}
		}

		//System.out.println("...4...");
		String[] notitle = getNOTITLE(db); //不上傳抬頭
		//System.out.println("...4-1...");
		String lineSeparator = System.getProperty("line.separator"); //要取得第1行
		int    p 		 = 0;
		String firstLine = "";
		//System.out.println("...4-2...notitle=["+notitle);
		//System.out.println("...4-3...inputStr=["+inputStr+"]");
		if(notitle!=null){
			if(!lineSeparator.equals("") && !inputStr.equals("")){
				p = inputStr.indexOf(lineSeparator);
				if(p>0){
				    try {
					    firstLine = util.noAnyBlank(inputStr.substring(0,p));
					    System.out.println("firstLine="+firstLine);
					} catch (IOException e) {
				        System.out.println(e);
            		}
					for (int i = 0; i < notitle.length; i++) {
						//System.out.println("notitle["+i+"]="+notitle[i]);
						if (firstLine.indexOf(notitle[i]) != -1) {
							System.out.println("此案為不上傳抬頭");
							check=false;
							break;
						}
					}
				}
			}
		}

		//System.out.println("...5...");
		return check;
	}

	/** //[A]Carl [#8276]
	 * 判斷是否要截字 規則: 當發現有 "主文"、"上列"、"一、"時，則該行裁判書以下的字就無需再截字
	 * 加判斷直式橫書前書寫方式右列、右聲請、右被告、右上訴、右抗告、右當事
	 */
	private static boolean checkisCutString(String lines) {
		boolean retVal = true;
		try {
			lines = util.noAnyBlank(lines);
			String[] cutString = { "主文", "上列", "一、", "右列", "右聲請", "右被告", "右上訴", "右抗告", "右當事" };
			for (int i = 0; i < cutString.length; i++) {
				if (!lines.equals("") && !lines.equals("")) {
					if (lines.length() >= cutString[i].length()) { // 以防內文小於cutString會產生StringIndexOutOfBoundsException
						if (lines.substring(0, cutString[i].length()).equals(cutString[i])) {

						   //找上列X人 表示該行非結尾
						   String cutRegex = "上列.人";
                    	   Pattern pattern = new Pattern(cutRegex);
                           Matcher matcher = pattern.matcher(lines);
                           if (matcher.find()) {
                              System.out.println("match for"+lines);
							  break;
                           }
                           else {
                              retVal=false;
                              break;
                           }
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("checkisCutString Warn : "+e);
		}
		return retVal;

	}
	
	/** [A]Carl #8205 
	 * 正規表達式，取得CaseRegex在inputStr字串中出現的第一筆資料
	 * @param inputStr
	 * @param CaseRegex
	 * @return
	 */
	public static String matcherString(String inputStr,String CaseRegex){
		String result="";
		Pattern pattern = new Pattern(CaseRegex);
		Matcher matcher = pattern.matcher(inputStr);
	
		if (matcher.find()) {
			result = matcher.toString();
		}
	
		return result;
	}

	/**
	 * 判斷字串是否有這些符號存在
	 * @param inputStr
	 * @return
	 */
	public static boolean checkString(String inputStr){
		Pattern pattern = new Pattern("[，。；]");
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.find();
	}
	
	/**
	 * 判斷"行政訴訟"裁判書之案由，如含「檢舉獎金」則mosaic=P(不公開)
	 * @param db
	 * @param owner
	 * @param sys
	 * @param aCrmyy
	 * @param aCrmid
	 * @param aCrmno
	 * @return
	 */
	public static boolean getC01Rsn(DBUtility db,String owner,String sys,String aCrmyy,String aCrmid,String aCrmno){
		boolean result= false;
		if("A".equals(owner)){
			String rsn="";
			String sql = "select rsn from "+owner+".c01 where sys='"+sys+"' and crmyy='"+aCrmyy+"' and crmid='"+aCrmid+"' and crmno='"+aCrmno+"'";
			Vector vr = db.doSqlSelect(sql,1,false);
			if(vr.size()>0)
				rsn = (String)vr.elementAt(0);
			if(rsn.indexOf("檢舉獎金")>-1) 
				result = true;
		}
		return result;
	}	
	
	public static void main(String[] args) {
		DBUtility db = new DBUtility();
		try {
			db.openConnection("C:\\tran\\tran.ini");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filenm = "C:\\TPDM98AT.001";
		//filenm = "C:\\30083013.078";
		File file = new File(filenm);
		String mosaicStatus = MosaicUtil.getMosaicStatus(file, "H");
		String mosaicMatchStr = MosaicUtil.getMosaicMatchStr(file, mosaicStatus);
		System.out.println("mosaicStatus = " + mosaicStatus);
		System.out.println("mosaicMatchStr = " + mosaicMatchStr);
		//System.out.println(MosaicUtil.getFileContent(file));
		String[] clnm = {"李葳綺(原名李麗香)", "陳小忠", "麥克迪諾馬（Michael B. DeNoma）"};
		MosaicUtil mosaic = new MosaicUtil();
		System.out.println(mosaic.doCover(db, file, clnm));

//		String filenm = "";
//		filenm = "C:\\29192949.002";
//		String[] clnm = { "邱憲昌", "楊境龍", "陳嘉德", "簡如惠", "李憶萍", "交通大隊" };
//		StringBuffer inputStr = new StringBuffer();
//		inputStr.append("  債權人:邱憲昌" + "\n");
//		inputStr.append("  債權人:邱憲昌" + "\n");
//		inputStr.append("  債權人:楊境龍" + "\n");
//		inputStr.append("  債權人:陳嘉德" + "\n");
//		inputStr.append("  債權人:簡如惠" + "\n");
//		inputStr.append("  債權人:交通大隊" + "\n");
//		inputStr.append("  身分證:A123456789" + "\n");
//		inputStr.append("  銀行帳號:第0123456789號" + "\n");
//		inputStr.append("  電話號碼:(02)4555-4000" + "\n");
//		inputStr.append("  電話號碼:(02)2784-1000#2064" + "\n");
//		inputStr.append("  電話號碼:886-2-2562-0536" + "\n");
//		inputStr.append("  信用卡號:1111-2222-3333-4444" + "\n");
//		inputStr.append("  住址:住台北市大安區信義路四段6號9樓" + "\n");
//		inputStr.append("  住址:住台北縣汐止市新台五路一段88號8樓(221)" + "\n");
//		inputStr.append("  住址:住桃園縣龍潭鄉渴望路185號" + "\n");
//		inputStr.append("  刑事訴訟法第221條之1");
//		MosaicUtil mosaic = new MosaicUtil();
//		String result = "";
//		result = mosaic.doCover(new File(filenm), clnm);
//		//result = mosaic.doCover(inputStr.toString(), clnm);
//		System.out.println(" ====== result ======");
//		System.out.println(result);
//		boolean isSexCase = MosaicUtil.isSexCase(new File(filenm));
//		//boolean isSexCase = MosaicUtil.isSexCase(inputStr.toString());
//		System.out.println("isSexCase = " + isSexCase);
	}
}