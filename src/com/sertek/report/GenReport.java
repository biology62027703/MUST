package com.sertek.report;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import com.sertek.db.SelectResult;
import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.util.CheckObject;
import com.sertek.util.util;

public class GenReport
{
	private	static	String	className	= GenReport.class.getName();

	private SqlDBUtility sqlDBUtility = null;
	private	String		owner		= null;
	private CheckObject check = new CheckObject();
	
	private	TempReport	rpt;
	private	Hashtable	ht;
	private	SelectResult	sr;
	public	String		reportFilePath	= "";
	public	String		iniFilePath	= "";
	public	String[]	setName;
	public	String[]	setValue;
	public	int		printCount	= 1;
	public	String		errMessage	= "無符合資料提供列印!!";
    public  int iSTART_PAGE_NUMBER = 0; // add by Peric 2005.04.14
    public boolean firstpage = true;

	/**
	Constructor
	*/
	public GenReport(SqlDBUtility sqlDBUtility, String owner, String iniFilePath, String reportFilePath) throws Exception
	{
		try
		{
			this.sqlDBUtility = sqlDBUtility;
			this.owner		= owner;

			rpt	= new TempReport(iniFilePath, reportFilePath);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".GenReport Exception: \r\n" + err.toString());
		}
	}
	
	public void setRptGroupPage(String rptGroupPage) {
		rpt.setRptGroupPage(rptGroupPage);
	}
	
//	/**
//	用來產生報表 - 依傳入的SQL條件
//	@param	sql		SQL條件
//	@param	columnName	欄位名稱陣列
//	*/
//	public void sqlReport(String sql, String[] columnName) throws Exception
//	{
//		try
//		{
//
//			MultiTable	MultiTable	= new MultiTable(db);
//			sr		= MultiTable.doSelect(sql, columnName);
//
//
//			if (!sr.next())
//			{
//				throw new Exception(className + ".sqlReport(String, String[]) Exception: 【" + errMessage + "】");
//			}
//			else
//			{
//				Vector vr = new Vector();
//				sql = "SELECT HFONT,BFONT,TFONT,HCONT,REPLACE(REPLACE(TCONT,',','\t'),';','\r\n'),BTREC FROM "+this.owner+".Z05 WHERE ROWNUM=1 AND RPTID='"+rpt.rptID+"'";
//
//			      vr = db.doSqlSelect(sql,6,false);
//
//			    
//			    if (vr.size() == 6)
//			    {
//			    	rpt.headFont = vr.elementAt(0).toString();
//			    	rpt.bodyFont = vr.elementAt(1).toString();
//			    	rpt.footFont = vr.elementAt(2).toString();
//			    	rpt.headContent = vr.elementAt(3).toString();
//			    	rpt.footContent = vr.elementAt(4).toString();
//			    	try
//			    	{
//				    	rpt.rptLine = Integer.parseInt(vr.elementAt(5).toString());
//				    }
//				    catch(Exception ex)
//				    {
//				    }
//			    }
//			}
//
//			rpt.totalRows		= sr.count();
//
//			rpt.totalPageNum	= (sr.count() % rpt.rptLine == 0)?sr.count() / rpt.rptLine:(sr.count() - (sr.count() % rpt.rptLine))/ rpt.rptLine + 1;
//
//			for (int i = 0; i < printCount; i ++)
//			{   //2005.04.14 Peric 修改 : 提供可以由外部設定起始頁碼
//				//rpt.pageNumber = 0;
//				rpt.pageNumber	= this.iSTART_PAGE_NUMBER;
//				rpt.isEnd	= false;
//				rpt.currentRow	= 0;
//				drawBody(columnName);
//
//				if (i != printCount - 1)
//					rpt.newPage();
//			}
//			rpt.print();
//		}
//		catch(Exception err)
//		{
//			throw new Exception(className + ".sqlReport(String, String[]) Exception: \r\n" + err.toString());
//		}
//	}

	/**
	用來產生報表 - 依自動傳入 SelectResultE 及 columnName 陣列
	@param	sr		SelectResult 結果
	@param	columnName	欄位陣列值
	*/
	public void report(SelectResult sr, String[] columnName) throws Exception
	{
		try
		{
			this.sr	= sr;
			sr.first();

			if (!sr.next())
			{
				throw new Exception(className + ".report(SelectResultE, String[]) Exception: 【" + errMessage + "】");
			}
			else
			{
				StringBuffer sql = new StringBuffer();
				sql.setLength(0);
				sql.append("select hfont, ");
				sql.append("bfont, ");
				sql.append("tfont, ");
				sql.append("hcont, ");
				sql.append("replace(replace(tcont, ',', '\t'), ';', '\r\n') as tcont, ");
				sql.append("btrec ");
				sql.append("from " + owner + ".z05 ");
				sql.append("where rptid = '" + rpt.rptID + "' ");
				List ls = sqlDBUtility.doSqlSelect(sql.toString());
			    
			    if (ls.size() > 0)
			    {
			    	HashMap map = (HashMap) ls.get(0);
			    	rpt.headFont = check.checkNull(map.get("HFONT"), "").toString();
			    	rpt.bodyFont = check.checkNull(map.get("BFONT"), "").toString();
			    	rpt.footFont = check.checkNull(map.get("TFONT"), "").toString();
			    	rpt.headContent = check.checkNull(map.get("HCONT"), "").toString();
			    	rpt.footContent = check.checkNull(map.get("TCONT"), "").toString();
			    	try
			    	{
				    	rpt.rptLine = Integer.parseInt(check.checkNull(map.get("BTREC"), "").toString());
				    }
				    catch(Exception ex)
				    {
				    }			    	
			    }
			}

			rpt.totalRows		= sr.count();

			rpt.totalPageNum	= (sr.count() % rpt.rptLine == 0)?sr.count() / rpt.rptLine:(sr.count() - (sr.count() % rpt.rptLine))/ rpt.rptLine + 1;

			for (int i = 0; i < printCount; i ++)
			{   //2005.04.14 Peric 修改 : 提供可以由外部設定起始頁碼
				//rpt.pageNumber = 0;
				rpt.pageNumber	= this.iSTART_PAGE_NUMBER;
				rpt.isEnd	= false;
				rpt.currentRow	= 0;
				drawBody(columnName);

				if (i != printCount - 1)
					rpt.newPage();
			}
			rpt.print();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".report(SelectResultE, String[]) Exception: \r\n" + err.toString());
		}
	}

//	/**
//	用來產生報表 - 依SQL條件
//	@param	condition	SQL條件
//	*/
//	public void report(String condition) throws Exception
//	{
//		try
//		{
//			String		sql		= util.replace(rpt.sql.toUpperCase(), "<OWNER>", owner);
//			String[]	columnName	= rpt.columnName;
//			if(condition.trim().length() > 0)
//				condition	= " Where " + condition;
//
//			if (setName != null)
//			{
//				for (int i = 0; i < setName.length; i++)
//					sql	= util.replace(sql, "<" + util.checkNull(setName[i], "").toUpperCase() + ">", setValue[i]);
//			}
//
//			sqlReport(sql + condition, columnName);
//		}
//		catch(Exception err)
//		{
//			throw new Exception(className + ".report(String) Exception: \r\n" + err.toString());
//		}
//	}

//	/**
//	用來產生報表 - 依 陣列內容內容取代設定SQL
//	@param	setName		設定名稱
//	@param	setValue	設定值
//	*/
//	public void report(String[] setName, String[] setValue, String condition) throws Exception
//	{
//		try
//		{
//			this.setName	= setName;
//			this.setValue	= setValue;
//			report(condition);
//		}
//		catch(Exception err)
//		{
//			throw new Exception(className + ".report(String[], String[], condition) Exception: \r\n" + err.toString());
//		}
//	}

//	/**
//	用來產生報表 - 依 陣列內容內容取代設定SQL
//	@param	setName		設定名稱
//	@param	setValue	設定值
//	*/
//	public void report(String[] setName, String[] setValue) throws Exception
//	{
//		try
//		{
//			this.setName	= setName;
//			this.setValue	= setValue;
//			report("");
//		}
//		catch(Exception err)
//		{
//			throw new Exception(className + ".report(String[], String[]) Exception: \r\n" + err.toString());
//		}
//	}

//	/**
//	用來產生報表 - 依 ini 設定SQL
//	*/
//	public void report() throws Exception
//	{
//		try
//		{
//			report("");
//		}
//		catch(Exception err)
//		{
//			throw new Exception(className + ".report() Exception: \r\n" + err.toString());
//		}
//	}

	/**
	畫表頭
	*/
	private void drawHeader() throws Exception
	{
		try
		{
			if (rpt.pageNumber == 0) {
				rpt.drawHeader(ht);
				firstpage = true;
			} else {
				rpt.drawSecondHeader(ht);
				firstpage = false;
			}
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawHeader Exception: \r\n" + err.toString());
		}
	}

	/**
	抓取群組變數值
	*/
	private String getGroupValue() throws Exception
	{
		try
		{
			StringBuffer	groupField	= new StringBuffer();
			String[]	fieldAry	= util.split(rpt.rptGroupField, ",");

			for (int i = 0; i < fieldAry.length; i++)
			{
				groupField.append(sr.getStr(fieldAry[i].trim()));
			}
			return groupField.toString();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".getGroupValue Exception: \r\n" + err.toString());
		}
	}

	/**
	畫表尾(填滿設定行數)
	*/
	private void drawEndLine(int lastLine) throws Exception
	{
		try
		{
			for (int j = 0; j < rpt.rptLine - lastLine; j++)
				rpt.drawEmptyBody();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawEndLine Exception: \r\n" + err.toString());
		}
	}

	/**
	畫表身
	*/
	private void drawBody(String[] columnName) throws Exception
	{
		try
		{
			String	tmpGroup	= "";
			String	currGroup	= "";
			int	i		= 0;
			int	currIndex	= -1;

			for (i = 0; i < sr.count(); i++)
			{
				currIndex++;
				sr.move(i);

				if (rpt.rptGroupPage.equals("Y"))
					currGroup	= getGroupValue();

				ht		= rptutil.srToHt(columnName, sr);
				//針對表首及表尾
				if (rpt.rptGroupPage.equals("Y"))
				{
					if (i == 0)
					{
						drawHeader();
					}
					else if (!currGroup.equals(tmpGroup) || (currIndex % rpt.rptLine == 0 && currIndex != 0))
					{
						if (rpt.rptFillLine.equals("Y"))
							drawEndLine(currIndex);
						//回到正確值的筆數
						sr.move(i-1);
						ht		= rptutil.srToHt(columnName, sr);	
						if (!currGroup.equals(tmpGroup))
							drawFooter("G");
						else
							drawFooter();
							
						if (!currGroup.equals(tmpGroup)) {
							rpt.currentRow = 0;
							rpt.pageNumber = 0;
							rpt.totalPageNum = 0;
						}
						//2006.12.04 modified by daniel Bug #951修正依群組換頁時抓股別資料會抓到下一筆的問題 (將i恢復為原來的值)
						sr.move(i);
						ht		= rptutil.srToHt(columnName, sr);
						drawHeader();
						currIndex	= 0;
					}
				}
				else if (i == 0 || i % rpt.rptLine == 0)
				{
					if (i != 0)
					{
						drawFooter();
					}
					drawHeader();
				}

				//表身
				if (firstpage) {
					rpt.drawBody(ht);
				} else {
					rpt.drawSecondBody(ht);
				}

				if (rpt.rptGroupPage.equals("Y"))
					tmpGroup	= getGroupValue();
			}

			//針對表尾
			if (rpt.rptGroupPage.equals("Y"))
			{
				if (rpt.rptFillLine.equals("Y"))
					drawEndLine(currIndex + 1);
			}
			else if (rpt.rptFillLine.equals("Y"))
			{   //fix by Peric 2005.04.20
				//for (int j = 0; j < (rpt.rptLine * rpt.pageNumber) - i; j++)
				for (int j = 0; j < (rpt.rptLine * (rpt.pageNumber - this.iSTART_PAGE_NUMBER)) - i; j++)
					rpt.drawEmptyBody();
			}

			rpt.end();

			drawFooter();
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawBody Exception: \r\n" + err.toString());
		}
	}

	/**
	畫表尾
	*/
	private void drawFooter() throws Exception
	{
		try
		{
			if (rpt.isEnd)
				rpt.drawLastFoot(ht);
			else
				rpt.drawFoot(ht);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawFooter Exception: \r\n" + err.toString());
		}
	}

	/**
	畫表尾
	*/
	private void drawFooter(String G) throws Exception
	{
		try
		{
			if (rpt.isEnd)
				rpt.drawLastFoot(ht);
			else
				rpt.drawGroupFoot(ht, G);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawFooter Exception: \r\n" + err.toString());
		}
	}	
}