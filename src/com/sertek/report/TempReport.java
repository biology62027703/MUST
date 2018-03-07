package com.sertek.report;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Vector;

import com.sertek.util.dtutil;
import com.sertek.util.util;
import com.sertek.util.util_date;

public class TempReport
{
	private	static	String	className	= "com.sertek.report.TempReport";

	//修改換行符號為不由系統抓取寫死 \r\n 解決多印空白頁問題 2004/1/9 (秋菊, 泰亨)
	//private	static	String	LineSeparator	= System.getProperty("line.separator") == null ? "\n" : System.getProperty("line.separator");	//換行符號
	private	static	String	LineSeparator	= "\r\n";	//換行符號

	private	String		iniFilePath		= "";			//設定檔路徑
	private	String		reportFilePath	= "";			//報表存檔路徑
	private	Hashtable	iniContent	= new Hashtable();	//存放ini檔設定
	private	rptutil		rptutil;

	public	int			rptLine;			//一頁幾筆資料換頁
	public	String		rptID;				//一頁幾筆資料換頁
	public	String		rptFillLine;		//是否最後一頁填滿每頁設定行數
	public	String		rptGroupPage;		//是否依群組換頁
	public	String		rptGroupField;		//群組欄位
	private	String		reportRptid;		//報表編號(DB用)
	private	String		rptJump;			//是否須插入換頁符號
	public	String		sql;				//SQL
	public	String[]	columnName;			//表格欄位名稱陣列(程式用)
	private	int			headLineBefore;		//表頭前要幾行
	public	int			headLine;			//表頭共幾行
	private	String[]	headAry;			//表頭資料陣列
	private	int			secondHeadLineBefore;//表頭前要幾行
	public	int			secondHeadLine;		//表頭共幾行
	private	String[]	secondHeadAry;		//表頭資料陣列
	public	int			bodyLine;			//表身共幾行
	public	String		bodyLineStr;		//表身行的格式
	private	String[]	bodyAry;			//表身資料陣列
	public	int			secondBodyLine;			//表身共幾行
	public	String		secondBodyLineStr;		//表身行的格式
	private	String[]	secondBodyAry;			//表身資料陣列
	public	int			footLine;			//表尾共幾行
	private	String[]	footAry;			//表尾資料陣列
	public	int			groupfootLine;			//表尾共幾行
	private	String[]	groupfootAry;			//表尾資料陣列
	public	int			lastFootLine;		//最後表尾共幾行
	private	String[]	lastFootAry;		//最後表尾資料陣列
	private	int			fieldCount		= 0;//設定變數共幾個
	public	int			pageNumber		= 0;//頁次
	public	int			currentRow		= 0;//目前筆數(流水號)
	private	int			fillLength		= 0;//要填滿的長度
	public	int			totalRows		= 0;//總共筆數
	public	int			totalPageNum	= 0;//總共頁數
	public	int			grouptotalRows	= 0;//總共筆數
	public	boolean		isEnd		= false;//總共筆數
	private	Vector		contect		= new Vector();	//報表內容
	public String		headFont = "";		//表頭字型控制碼
	public String		bodyFont = "";		//表身字型控制碼
	public String		footFont = "";		//表尾字型控制碼
	public String		headContent = "";	//表頭內容
	public String		footContent = "";	//表尾內容

	/**
	Constructor
	1. 用來初始化檔案路徑
	2. 將INI檔案設定內容塞至Hashtable中
	3. 設定報表列印需要的值
	@param	iniFilePath	設定檔路徑
	@param	reportFilePath	報表存檔路徑
	*/
	public TempReport(String iniFilePath, String reportFilePath) throws Exception
	{
		try
		{
			rptutil	= new rptutil();

			rptID	= "REPORT";
			this.iniFilePath	= iniFilePath;
			this.reportFilePath	= reportFilePath;

			//將INI檔案設定內容塞至Hashtable中
			rptutil.setIniContent(iniFilePath, iniContent);

			//設定報表列印需要的值
			this.rptID		= rptutil.getKeyStr("REPORT", "RPTID");
			this.rptLine		= rptutil.getKeyInt("REPORT", "LINE");
			this.rptFillLine	= rptutil.getKeyStr("REPORT", "FILLLINE");
			this.rptJump		= rptutil.getKeyStr("REPORT", "JUMP");
			this.rptGroupPage	= rptutil.getKeyStr("REPORT", "GROUPPAGE");
			//if (rptGroupPage.equals("Y"))
			this.rptGroupField	= rptutil.getKeyStr("REPORT", "GROUPFIELD");

			//表格欄位名稱(SQL用)
			if (rptutil.getKeyObject("REPORT", "SQL") != null)
				sql	= rptutil.getKeyStr("REPORT", "SQL");

			//表格欄位名稱陣列(程式用)
			if (rptutil.getKeyObject("REPORT", "COLUMNNAME") != null)
			{
				columnName	= util.split(rptutil.getKeyStr("REPORT", "COLUMNNAME"), ",");
				for (int i = 0; i < columnName.length; i++)
					columnName[i]	= columnName[i].trim();
			}

			//HEAD
			this.headLineBefore	= rptutil.getKeyInt("HEAD", "LINEBEFORE");
			this.headLine		= rptutil.getKeyInt("HEAD", "LINE");
			this.headAry		= new String[this.headLine];
			for (int i = 0; i < this.headAry.length; i++)
				this.headAry[i]	= rptutil.getKeyStr("HEAD", "HEAD" + Integer.toString(i + 1));
			
			//SECONDHEAD
			if(rptutil.getKeyObject("SECONDHEAD", "LINE") != null)
			{
				this.secondHeadLine	= rptutil.getKeyInt("SECONDHEAD", "LINE");
				this.secondHeadAry	= new String[this.secondHeadLine];
				for (int i = 0; i < this.secondHeadAry.length; i++)
					this.secondHeadAry[i]	= rptutil.getKeyStr("SECONDHEAD", "HEAD" + Integer.toString(i + 1));
			}

			//BODY
			this.bodyLine		= rptutil.getKeyInt("BODY", "LINE");
			this.bodyLineStr	= rptutil.getKeyStr("BODY", "LINESTR");
			this.bodyAry		= new String[this.bodyLine];
			for (int i = 0; i < this.bodyAry.length; i++)
				this.bodyAry[i]	= rptutil.getKeyStr("BODY", "BODY" + Integer.toString(i + 1));
			
			//SECONDBODY
			if(rptutil.getKeyObject("SECONDBODY", "LINE") != null)
			{
				this.secondBodyLine	= rptutil.getKeyInt("SECONDBODY", "LINE");
				this.secondBodyAry	= new String[this.secondBodyLine];
				for (int i = 0; i < this.secondBodyAry.length; i++)
					this.secondBodyAry[i]	= rptutil.getKeyStr("SECONDBODY", "BODY" + Integer.toString(i + 1));
			}

			//FOOT
			this.footLine		= rptutil.getKeyInt("FOOT", "LINE");
			this.footAry		= new String[this.footLine];
			for (int i = 0; i < this.footAry.length; i++)
				this.footAry[i]	= rptutil.getKeyStr("FOOT", "FOOT" + Integer.toString(i + 1));

			//有設定 GROUPFOOT
			if (rptutil.getKeyObject("GROUPFOOT", "LINE") != null)
			{
				this.groupfootLine		= rptutil.getKeyInt("GROUPFOOT", "LINE");
				this.groupfootAry		= new String[this.groupfootLine];
				for (int i = 0; i < this.groupfootAry.length; i++)
					this.groupfootAry[i]	= rptutil.getKeyStr("GROUPFOOT", "FOOT" + Integer.toString(i + 1));
			}

			//有設定 LASTFOOT
			if (rptutil.getKeyObject("LASTFOOT", "LINE") != null)
			{
				this.lastFootLine	= rptutil.getKeyInt("LASTFOOT", "LINE");
				this.lastFootAry	= new String[this.lastFootLine];
				for (int i = 0; i < this.lastFootAry.length; i++)
					this.lastFootAry[i]	= rptutil.getKeyStr("LASTFOOT", "FOOT" + Integer.toString(i + 1));
			}

			//FIELD
			this.fieldCount	= rptutil.getKeyInt("FIELD", "COUNT");
			for (int i = 0; i < this.fieldCount; i++)
			{
				String[]	fieldAry	= util.split(rptutil.getKeyStr("FIELD", "F" + Integer.toString(i + 1)), ",");
				rptutil.setKeyStr("FIELD", "F" + Integer.toString(i + 1), fieldAry);
			}
		}
		catch (Exception err)
		{
			throw new Exception(className + ".TempReport Exception: \r\n" + err.toString());
		}
	}
	/**
	畫標題部分報表
	@param	ht		用來取代Tag資料
	*/
	public void drawHeader(Hashtable ht) throws Exception
	{
		try
		{
			String	strContent	= "";
			pageNumber++;
			//畫表頭前行數
			skipLine(headLineBefore);
			//畫表頭
			for (int i = 0; i < this.headAry.length; i++)
			{
				//解析報表內容
				strContent	= parseContent(headAry[i], ht);
				contect.add(strContent);
			}
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawHeader Exception: \r\n" + err.toString());
		}
	}
	
	/**
	畫第二頁標題部分報表
	@param	ht		用來取代Tag資料
	*/
	public void drawSecondHeader(Hashtable ht) throws Exception
	{
		try
		{
			String	strContent	= "";
			//有設定 SECONDHEAD
			if (rptutil.getKeyObject("SECONDHEAD", "LINE") != null)
			{
				pageNumber++;
				//畫表頭前行數
				skipLine(secondHeadLineBefore);
				//畫表頭
				for (int i = 0; i < this.secondHeadAry.length; i++)
				{
					//解析報表內容
					strContent	= parseContent(secondHeadAry[i], ht);
					contect.add(strContent);
				}
			}
			else
				drawHeader(ht);

		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawSecondHeader Exception: \r\n" + err.toString());
		}
	}

	/**
	畫表身部分報表
	@param	ht		用來取代Tag資料
	*/
	public void drawBody(Hashtable ht) throws Exception
	{
		try
		{
			String	strContent	= "";
			currentRow++;
			grouptotalRows++;
			//畫表身
			for (int i = 0; i < this.bodyAry.length; i++)
			{
				//解析報表內容
				strContent	= parseContent(bodyAry[i], ht);
				contect.add(strContent);
			}
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawBody Exception: \r\n" + err.toString());
		}
	}

	/**
	畫表身部分報表
	@param	ht		用來取代Tag資料
	*/
	public void drawSecondBody(Hashtable ht) throws Exception
	{
		try
		{
			String	strContent	= "";
			//有設定 SECONDHEAD
			if (rptutil.getKeyObject("SECONDBODY", "LINE") != null)
			{
				currentRow++;
				grouptotalRows++;
				//畫表頭
				for (int i = 0; i < this.secondBodyAry.length; i++)
				{
					//解析報表內容
					strContent	= parseContent(secondBodyAry[i], ht);
					contect.add(strContent);
				}
			}
			else
				drawBody(ht);

		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawSecondBody Exception: \r\n" + err.toString());
		}
	}

	/**
	畫表身部分報表(不須印資料)
	@param	ht		用來取代Tag資料
	*/
	public void drawEmptyBody() throws Exception
	{
		try
		{
			//畫表身
			String[]	tmpStr	= util.split(bodyLineStr, "～");
			for (int i = 0; i < tmpStr.length; i++)
				contect.add(tmpStr[i]);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawEmptyBody Exception: \r\n" + err.toString());
		}
	}

	/**
	畫表尾部分報表
	@param	ht		用來取代Tag資料
	*/
	public void drawFoot(Hashtable ht) throws Exception
	{
		try
		{
			String	strContent	= "";
			//畫表身
			for (int i = 0; i < this.footAry.length; i++)
			{
				if (footAry[i].indexOf("&GTOTAL") == -1)
				{
					//解析報表內容
					strContent	= parseContent(footAry[i], ht);
					contect.add(strContent);
				}
			}
			if (rptJump.equals("Y") && !isEnd)
				newPage();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawFoot Exception: \r\n" + err.toString());
		}
	}
	
	/**
	畫表尾部分報表
	@param	ht		用來取代Tag資料
	*/
	public void drawGroupFoot(Hashtable ht,String G) throws Exception
	{
		try
		{
			if (rptutil.getKeyObject("GROUPFOOT", "LINE") != null) {
				String	strContent	= "";
				//畫表身
				for (int i = 0; i < this.groupfootAry.length; i++)
				{
					//解析報表內容
					strContent	= parseContent(groupfootAry[i], ht);
					contect.add(strContent);
				}
				grouptotalRows = 0;
				if (rptJump.equals("Y") && !isEnd)
					newPage();
			}else{
				drawFoot(ht, G);
			}
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawFoot Exception: \r\n" + err.toString());
		}
	}
	
	/**
	畫表尾部分報表
	@param	ht		用來取代Tag資料
	*/
	public void drawFoot(Hashtable ht,String G) throws Exception
	{
		try
		{
			String	strContent	= "";
			//畫表身
			for (int i = 0; i < this.footAry.length; i++)
			{
				//解析報表內容
				strContent	= parseContent(footAry[i], ht);
				contect.add(strContent);
			}
			if (rptJump.equals("Y") && !isEnd)
				newPage();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawFoot Exception: \r\n" + err.toString());
		}
	}

	/**
	畫最後表尾部分報表
	@param	ht		用來取代Tag資料
	*/
	public void drawLastFoot(Hashtable ht) throws Exception
	{
		try
		{
			if (rptutil.getKeyObject("GROUPFOOT", "LINE") != null) {
				this.drawGroupFoot(ht, "G");
			}else{
				String	strContent	= "";
				//有設定 LASTFOOT
				if (rptutil.getKeyObject("LASTFOOT", "LINE") != null)
				{
					//畫表身
					for (int i = 0; i < this.lastFootAry.length; i++)
					{
						//解析報表內容
						strContent	= parseContent(lastFootAry[i], ht);
						contect.add(strContent);
					}
				}
				else
					drawFoot(ht);
			}
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawLastFoot Exception: \r\n" + err.toString());
		}
	}

	/**
	換指定數目的行數
	@param	int	skipNumber	指定數目的行數
	*/
	public void skipLine(int skipNumber) throws Exception
	{
		try
		{
			for (int i = 0; i < skipNumber; i++)
				contect.add("");
		}
		catch (Exception err)
		{
			throw new Exception(className + ".skipLine(int) Exception: \r\n" + err.toString());
		}
	}

	/**
	產生報表
	*/
	public void print() throws Exception
	{
		try
		{
			writeFile();
		}
		catch (Exception err)
		{
			throw new Exception(className + ".print Exception: \r\n" + err.toString());
		}
	}

	/**
	寫入檔案
	*/
	private void writeFile() throws Exception
	{
		BufferedWriter	bufWriter;
		try
		{
			/*
			bufWriter	= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(reportFilePath, false), "MS950"));
			bufWriter.write("");
			bufWriter.close();
			bufWriter	= null;
			*/
			bufWriter	= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(reportFilePath, true), "MS950"));
			for(int i = 0; i < contect.size(); i++)
				bufWriter.write(contect.elementAt(i).toString() + LineSeparator);
			bufWriter.close();
		}
		catch (Exception err)
		{
			throw new Exception(className + ".writeFile Exception: \r\n" + err.toString());
		}
		finally
		{
			contect.clear();
			bufWriter	= null;
		}
	}

	/**
	插入換頁符號
	*/
	public void newPage() throws Exception
	{
		try
		{
			contect.add("\f");
		}
		catch (Exception err)
		{
			throw new Exception(className + ".NewPage Exception: \r\n" + err.toString());
		}
	}

	/**
	插入換頁符號
	*/
	public void end() throws Exception
	{
		try
		{
			this.isEnd	= true;
		}
		catch (Exception err)
		{
			throw new Exception(className + ".NewPage Exception: \r\n" + err.toString());
		}
	}

	/**
	解析報表內容
	@param	lineStr		報表內容
	@param	ht		用來取代Tag資料
	*/
	private String parseContent(String lineStr, Hashtable ht) throws Exception
	{
		try
		{
			String	fieldName	= "";
			String	fieldType	= "";
			String	typeValue	= "";
			String	htValue		= "";
			int	currentLine	= -1;
			int	maxLength	= -1;
			int	valIndex	= 0;
			String	replaceStr	= "";

			//處理系統預設TAG
			//系統日期
			if (lineStr.indexOf("&SYSDATE") != -1)
			{
				replaceStr	= rptutil.getReplaceStr(lineStr, 9, "&SYSDATE");
				lineStr		= util.replace(lineStr, replaceStr, dtutil.formatCDate(dtutil.getNowCDate()));
			}
			//總共頁數
			if (lineStr.indexOf("&TOTALPAGENUM") != -1)
			{
				replaceStr	= "&TOTALPAGENUM";
				lineStr		= util.replace(lineStr, replaceStr, Integer.toString(totalPageNum) + util.cloneStr(' ', replaceStr.length() - Integer.toString(totalPageNum).length()));
			}
			//頁次
			if (lineStr.indexOf("&PNO") != -1)
			{
				replaceStr	= "&PNO";
				lineStr		= util.replace(lineStr, replaceStr, Integer.toString(pageNumber) + util.cloneStr(' ', replaceStr.length() - Integer.toString(pageNumber).length()));
			}
			//群組總共筆數
			if (lineStr.indexOf("&GROUPTOTAL") != -1)
			{
				replaceStr	= "&GROUPTOTAL";
				lineStr		= util.replace(lineStr, replaceStr, Integer.toString(grouptotalRows) + util.cloneStr(' ', replaceStr.length() - Integer.toString(grouptotalRows).length()));
			}
			//總共筆數
			if (lineStr.indexOf("&TOTAL") != -1)
			{
				replaceStr	= "&TOTAL";
				lineStr		= util.replace(lineStr, replaceStr, Integer.toString(totalRows) + util.cloneStr(' ', replaceStr.length() - Integer.toString(totalRows).length()));
			}
			//表頭字型
			if (lineStr.indexOf("&HFONT") != -1)
			{
				replaceStr	= "&HFONT";
				lineStr		= util.replace(lineStr, replaceStr, headFont);
			}
			//表身字型
			if (lineStr.indexOf("&BFONT") != -1)
			{
				replaceStr	= "&BFONT";
				lineStr		= util.replace(lineStr, replaceStr, bodyFont);
			}
			//表尾字型
			if (lineStr.indexOf("&TFONT") != -1)
			{
				replaceStr	= "&TFONT";
				lineStr		= util.replace(lineStr, replaceStr, footFont);
			}
			//表頭內容
			if (lineStr.indexOf("&HCONT") != -1)
			{
				replaceStr	= "&HCONT";
				lineStr		= util.replace(lineStr, replaceStr, headContent);
			}
			//表尾內容
			if (lineStr.indexOf("&TCONT") != -1)
			{
				replaceStr	= "&TCONT";
				lineStr		= util.replace(lineStr, replaceStr, footContent);
			}
			
			//總共筆數
			if (lineStr.indexOf("&GTOTAL") != -1)
			{
				replaceStr	= "&GTOTAL";
				lineStr		= util.replace(lineStr, replaceStr, Integer.toString(currentRow) + util.cloneStr(' ', replaceStr.length() - Integer.toString(currentRow).length()));
			}
			
			if(lineStr.indexOf("&NEWPAGE") != -1)
			{
				replaceStr	= "&NEWPAGE";
				lineStr		= util.replace(lineStr, replaceStr, "\f");
			}

			for (int i = fieldCount; i >= 0; i--)
			{
				//要取代的字串 &0, &1...
				replaceStr	= "&" + Integer.toString(i);

				//字串中不包含要取代字串則不處理
				if (lineStr.indexOf(replaceStr) == -1)
					continue;

				//處理設定參數部分
				String[]	fieldAry	= (String[])rptutil.getKeyObject("FIELD", "F" + Integer.toString(i));
				fieldName	= fieldAry[0].trim();
				if (fieldName.equals("SERIALNO"))	//流水號
				{
					//當資料未印完時印出流水號
					if (!isEnd)
						htValue	= Integer.toString(currentRow);
					else
						htValue	= "";
				}
				else if (fieldName.equals("CURRENTSUM"))	//目前已印出筆數
				{
					htValue	= Integer.toString(currentRow);
				}
				else if (fieldName.equals("PAGENUMBER"))	//目前頁次
				{
					htValue	= Integer.toString(pageNumber);
				}
				else if (fieldName.equals("SYSDATE"))	//系統日期
				{
					htValue	= dtutil.getNowCDate();
				}
				else
				{
					try
					{
						htValue		= ht.get(fieldName).toString();
					}
					catch(Exception err)
					{
						throw new Exception(className + ".parseContent Exception: 【參數未設定 [FIELD] F" + Integer.toString(i) + ", fieldName: " + fieldName + "】");
					}
				}

				//填滿長度
				valIndex	= rptutil.getAryIndex(fieldAry, "ADD");
				if (valIndex > 0)
				{
					typeValue	= fieldAry[valIndex].trim();
					htValue		= Integer.toString(Integer.parseInt(htValue) + Integer.parseInt(typeValue));
				}

				//當資料未印完或是總數及目前筆數時才處理
				//if (!isEnd || fieldName.equals("SUM") || fieldName.equals("CURRENTSUM"))
				//{

					//填滿長度
					valIndex	= rptutil.getAryIndex(fieldAry, "FL");
					if (valIndex > 0)
					{
						//檢查未設定FL
						if (rptutil.getAryIndex(fieldAry, "FS") <= 0)
							throw new Exception(className + ".parseContent Exception: 【必須設定 FS 參數】");
						typeValue	= fieldAry[valIndex].trim();
						fillLength	= Integer.parseInt(typeValue);
					}

					//填滿某字元
					valIndex	= rptutil.getAryIndex(fieldAry, "FS");
					if (valIndex > 0)
					{
						//檢查未設定FL
						if (rptutil.getAryIndex(fieldAry, "FL") <= 0)
							throw new Exception(className + ".parseContent Exception: 【必須設定 FL 參數】");
						typeValue	= fieldAry[valIndex].trim();
						//檢查是否為字元
						if (typeValue.length() > 1)
							throw new Exception(className + ".parseContent Exception: 【FS 參數值必須為字元資料: " + typeValue + "】");
						htValue		= util.fillStr(htValue,	fillLength, typeValue.charAt(0));
					}

					//取得中文的星期
					valIndex	= rptutil.getAryIndex(fieldAry, "CW");
					if (valIndex > 0)
					{
						typeValue	= fieldAry[valIndex].trim();
						if (typeValue.equals("1"))
							htValue	= dtutil.getCWeek(htValue);
					}

					//取得上午下午
					valIndex	= rptutil.getAryIndex(fieldAry, "DT");
					if (valIndex > 0)
					{
						typeValue	= fieldAry[valIndex].trim();
						if (typeValue.equals("1"))
						{
							if (Integer.parseInt(htValue) - 12 > 0)
								htValue	= "下午";
							else
								htValue	= "上午";
						}
					}

					//格式化日期處理
					valIndex	= rptutil.getAryIndex(fieldAry, "FD");
					if (valIndex > 0)
					{
						typeValue	= fieldAry[valIndex].trim();
						//日期格式化 --> 092/01/01
						if (typeValue.equals("1"))
							htValue	= dtutil.formatCDate(htValue);
						//日期格式化 --> 九十二年一月一日
						else if (typeValue.equals("2"))
							htValue	= util.formatCDateForPrint(htValue);
						//日期格式化 --> 092年01月01日
						else if (typeValue.equals("3"))
							htValue = util_date.formatCDateWithCUnits(htValue, false);
//						日期格式化 --> 92年1月1日
						else if (typeValue.equals("4"))
							htValue = util_date.formatCDateWithCUnits(htValue, true);
					}
					
					valIndex	= rptutil.getAryIndex(fieldAry, "FT");
					if (valIndex > 0){
						typeValue	= fieldAry[valIndex].trim();
//						時間格式化 --> 15:30
						if(typeValue.equals("1"))
							htValue = dtutil.formatTime(htValue);
					}

					//將英數字串轉為全形字串
					valIndex	= rptutil.getAryIndex(fieldAry, "FUS");
					if (valIndex > 0)
					{
						htValue		= util.strToFullSize(htValue);
					}

					//數字轉為中文數字
					valIndex	= rptutil.getAryIndex(fieldAry, "CN");
					if (valIndex > 0)
					{
						typeValue	= fieldAry[valIndex].trim();
						htValue		= util.getCNum(htValue, Integer.parseInt(typeValue));
					}

					//格式化數字處理
					valIndex	= rptutil.getAryIndex(fieldAry, "FN");
					if (valIndex > 0)
					{
						typeValue	= fieldAry[valIndex].trim();
						try
						{
							DecimalFormat	formatter	= new DecimalFormat(typeValue);
							htValue	= formatter.format(Double.parseDouble(htValue));
						}
						catch(Exception err)
						{
							throw new Exception(className + ".parseContent Exception: 【數字格式化錯誤,  value:" + htValue + ", 格式化:" + typeValue + "】");
						}
					}

					//目前行數(折行用)
					valIndex	= rptutil.getAryIndex(fieldAry, "C");
					if (valIndex > 0)
					{
						typeValue	= fieldAry[valIndex].trim();
						currentLine	= Integer.parseInt(typeValue);
					}
				//}

				//最大寬度
				valIndex	= rptutil.getAryIndex(fieldAry, "M");
				if (valIndex > 0)
				{
					typeValue	= fieldAry[valIndex].trim();
					maxLength	= Integer.parseInt(typeValue);

					if (currentLine != -1)
						htValue	= rptutil.getSplitStr(htValue, maxLength, currentLine).trim();
					else if (rptutil.getBLen(htValue) > maxLength)
						htValue	= rptutil.getSplitStr(htValue, maxLength, 0).trim();

					htValue	= htValue + util.cloneStr(' ', maxLength - rptutil.getBLen(htValue));
					replaceStr	= rptutil.getReplaceStr(lineStr, maxLength, replaceStr);

					//將目前行數及填滿字元預設初始值
					currentLine	= -1;
					fillLength	= 0;
				}

				//最大寬度
				valIndex	= rptutil.getAryIndex(fieldAry, "M1");
				if (valIndex > 0)
				{
					typeValue	= fieldAry[valIndex].trim();
					maxLength	= Integer.parseInt(typeValue);

					if (currentLine != -1)
						htValue	= rptutil.getSplitStr1(htValue, maxLength, currentLine).trim();
					else if (rptutil.getBLen(htValue) > maxLength)
						htValue	= rptutil.getSplitStr1(htValue, maxLength, 0).trim();

					htValue	= htValue + util.cloneStr(' ', maxLength - rptutil.getBLen(htValue));
					replaceStr	= rptutil.getReplaceStr(lineStr, maxLength, replaceStr);

					//將目前行數及填滿字元預設初始值
					currentLine	= -1;
					fillLength	= 0;
				}

				//分散式排列
				valIndex	= rptutil.getAryIndex(fieldAry, "DA");

				if (valIndex > 0)
				{
					typeValue	= fieldAry[valIndex].trim();
					if (typeValue.equals("1"))
					{
						int		strBLen		= rptutil.getBLen(htValue.trim());
						int		strSize		= htValue.trim().length();
						int		fillSize	= (int)Math.floor((maxLength - strBLen) / (strSize - 1));

						StringBuffer	tmpStr		= new StringBuffer();
						for (int is = 0; is < strSize - 1; is++)
							tmpStr.append(htValue.substring(is, is + 1) + util.cloneStr(' ', fillSize));
						tmpStr.append(htValue.substring(strSize - 1, strSize));
						htValue	= tmpStr.toString();
					}
				}

				//排列方式
				valIndex	= rptutil.getAryIndex(fieldAry, "A");
				if (valIndex > 0)
				{
					typeValue	= fieldAry[valIndex].trim();

					if (typeValue.equals("0"))	//置左
						htValue	= htValue;
					else if (typeValue.equals("1"))	//置中
					{
						int	leftSpaceLength		= (maxLength - rptutil.getBLen(htValue.trim())) % 2 == 0?(maxLength - rptutil.getBLen(htValue.trim())) / 2:(maxLength - rptutil.getBLen(htValue.trim())) / 2 + 1;
						int	rightSpaceLength	= (maxLength - rptutil.getBLen(htValue.trim())) % 2 == 0?(maxLength - rptutil.getBLen(htValue.trim())) / 2:(maxLength - rptutil.getBLen(htValue.trim())) / 2;
						htValue	= util.cloneStr(' ', leftSpaceLength) + htValue.trim() + util.cloneStr(' ', rightSpaceLength);
					}
					else if (typeValue.equals("2"))	//置右
						htValue	= util.cloneStr(' ', maxLength - rptutil.getBLen(htValue.trim())) + htValue.trim();
				}
				lineStr	= util.replace(lineStr, replaceStr, htValue);
			}
			return lineStr;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".parseContent Exception: \r\n" + err.toString());
		}
	}
	public void setRptGroupPage(String rptGroupPage) {
		this.rptGroupPage = rptGroupPage;
	}
}