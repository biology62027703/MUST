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

	//�קﴫ��Ÿ������Ѩt�Χ���g�� \r\n �ѨM�h�L�ťխ����D 2004/1/9 (���, ����)
	//private	static	String	LineSeparator	= System.getProperty("line.separator") == null ? "\n" : System.getProperty("line.separator");	//����Ÿ�
	private	static	String	LineSeparator	= "\r\n";	//����Ÿ�

	private	String		iniFilePath		= "";			//�]�w�ɸ��|
	private	String		reportFilePath	= "";			//����s�ɸ��|
	private	Hashtable	iniContent	= new Hashtable();	//�s��ini�ɳ]�w
	private	rptutil		rptutil;

	public	int			rptLine;			//�@���X����ƴ���
	public	String		rptID;				//�@���X����ƴ���
	public	String		rptFillLine;		//�O�_�̫�@���񺡨C���]�w���
	public	String		rptGroupPage;		//�O�_�̸s�մ���
	public	String		rptGroupField;		//�s�����
	private	String		reportRptid;		//����s��(DB��)
	private	String		rptJump;			//�O�_�����J�����Ÿ�
	public	String		sql;				//SQL
	public	String[]	columnName;			//������W�ٰ}�C(�{����)
	private	int			headLineBefore;		//���Y�e�n�X��
	public	int			headLine;			//���Y�@�X��
	private	String[]	headAry;			//���Y��ư}�C
	private	int			secondHeadLineBefore;//���Y�e�n�X��
	public	int			secondHeadLine;		//���Y�@�X��
	private	String[]	secondHeadAry;		//���Y��ư}�C
	public	int			bodyLine;			//���@�X��
	public	String		bodyLineStr;		//���檺�榡
	private	String[]	bodyAry;			//����ư}�C
	public	int			secondBodyLine;			//���@�X��
	public	String		secondBodyLineStr;		//���檺�榡
	private	String[]	secondBodyAry;			//����ư}�C
	public	int			footLine;			//����@�X��
	private	String[]	footAry;			//�����ư}�C
	public	int			groupfootLine;			//����@�X��
	private	String[]	groupfootAry;			//�����ư}�C
	public	int			lastFootLine;		//�̫����@�X��
	private	String[]	lastFootAry;		//�̫�����ư}�C
	private	int			fieldCount		= 0;//�]�w�ܼƦ@�X��
	public	int			pageNumber		= 0;//����
	public	int			currentRow		= 0;//�ثe����(�y����)
	private	int			fillLength		= 0;//�n�񺡪�����
	public	int			totalRows		= 0;//�`�@����
	public	int			totalPageNum	= 0;//�`�@����
	public	int			grouptotalRows	= 0;//�`�@����
	public	boolean		isEnd		= false;//�`�@����
	private	Vector		contect		= new Vector();	//�����e
	public String		headFont = "";		//���Y�r������X
	public String		bodyFont = "";		//���r������X
	public String		footFont = "";		//����r������X
	public String		headContent = "";	//���Y���e
	public String		footContent = "";	//������e

	/**
	Constructor
	1. �ΨӪ�l���ɮ׸��|
	2. �NINI�ɮ׳]�w���e���Hashtable��
	3. �]�w����C�L�ݭn����
	@param	iniFilePath	�]�w�ɸ��|
	@param	reportFilePath	����s�ɸ��|
	*/
	public TempReport(String iniFilePath, String reportFilePath) throws Exception
	{
		try
		{
			rptutil	= new rptutil();

			rptID	= "REPORT";
			this.iniFilePath	= iniFilePath;
			this.reportFilePath	= reportFilePath;

			//�NINI�ɮ׳]�w���e���Hashtable��
			rptutil.setIniContent(iniFilePath, iniContent);

			//�]�w����C�L�ݭn����
			this.rptID		= rptutil.getKeyStr("REPORT", "RPTID");
			this.rptLine		= rptutil.getKeyInt("REPORT", "LINE");
			this.rptFillLine	= rptutil.getKeyStr("REPORT", "FILLLINE");
			this.rptJump		= rptutil.getKeyStr("REPORT", "JUMP");
			this.rptGroupPage	= rptutil.getKeyStr("REPORT", "GROUPPAGE");
			//if (rptGroupPage.equals("Y"))
			this.rptGroupField	= rptutil.getKeyStr("REPORT", "GROUPFIELD");

			//������W��(SQL��)
			if (rptutil.getKeyObject("REPORT", "SQL") != null)
				sql	= rptutil.getKeyStr("REPORT", "SQL");

			//������W�ٰ}�C(�{����)
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

			//���]�w GROUPFOOT
			if (rptutil.getKeyObject("GROUPFOOT", "LINE") != null)
			{
				this.groupfootLine		= rptutil.getKeyInt("GROUPFOOT", "LINE");
				this.groupfootAry		= new String[this.groupfootLine];
				for (int i = 0; i < this.groupfootAry.length; i++)
					this.groupfootAry[i]	= rptutil.getKeyStr("GROUPFOOT", "FOOT" + Integer.toString(i + 1));
			}

			//���]�w LASTFOOT
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
	�e���D��������
	@param	ht		�ΨӨ��NTag���
	*/
	public void drawHeader(Hashtable ht) throws Exception
	{
		try
		{
			String	strContent	= "";
			pageNumber++;
			//�e���Y�e���
			skipLine(headLineBefore);
			//�e���Y
			for (int i = 0; i < this.headAry.length; i++)
			{
				//�ѪR�����e
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
	�e�ĤG�����D��������
	@param	ht		�ΨӨ��NTag���
	*/
	public void drawSecondHeader(Hashtable ht) throws Exception
	{
		try
		{
			String	strContent	= "";
			//���]�w SECONDHEAD
			if (rptutil.getKeyObject("SECONDHEAD", "LINE") != null)
			{
				pageNumber++;
				//�e���Y�e���
				skipLine(secondHeadLineBefore);
				//�e���Y
				for (int i = 0; i < this.secondHeadAry.length; i++)
				{
					//�ѪR�����e
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
	�e����������
	@param	ht		�ΨӨ��NTag���
	*/
	public void drawBody(Hashtable ht) throws Exception
	{
		try
		{
			String	strContent	= "";
			currentRow++;
			grouptotalRows++;
			//�e��
			for (int i = 0; i < this.bodyAry.length; i++)
			{
				//�ѪR�����e
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
	�e����������
	@param	ht		�ΨӨ��NTag���
	*/
	public void drawSecondBody(Hashtable ht) throws Exception
	{
		try
		{
			String	strContent	= "";
			//���]�w SECONDHEAD
			if (rptutil.getKeyObject("SECONDBODY", "LINE") != null)
			{
				currentRow++;
				grouptotalRows++;
				//�e���Y
				for (int i = 0; i < this.secondBodyAry.length; i++)
				{
					//�ѪR�����e
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
	�e����������(�����L���)
	@param	ht		�ΨӨ��NTag���
	*/
	public void drawEmptyBody() throws Exception
	{
		try
		{
			//�e��
			String[]	tmpStr	= util.split(bodyLineStr, "��");
			for (int i = 0; i < tmpStr.length; i++)
				contect.add(tmpStr[i]);
		}
		catch(Exception err)
		{
			throw new Exception(className + ".drawEmptyBody Exception: \r\n" + err.toString());
		}
	}

	/**
	�e�����������
	@param	ht		�ΨӨ��NTag���
	*/
	public void drawFoot(Hashtable ht) throws Exception
	{
		try
		{
			String	strContent	= "";
			//�e��
			for (int i = 0; i < this.footAry.length; i++)
			{
				if (footAry[i].indexOf("&GTOTAL") == -1)
				{
					//�ѪR�����e
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
	�e�����������
	@param	ht		�ΨӨ��NTag���
	*/
	public void drawGroupFoot(Hashtable ht,String G) throws Exception
	{
		try
		{
			if (rptutil.getKeyObject("GROUPFOOT", "LINE") != null) {
				String	strContent	= "";
				//�e��
				for (int i = 0; i < this.groupfootAry.length; i++)
				{
					//�ѪR�����e
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
	�e�����������
	@param	ht		�ΨӨ��NTag���
	*/
	public void drawFoot(Hashtable ht,String G) throws Exception
	{
		try
		{
			String	strContent	= "";
			//�e��
			for (int i = 0; i < this.footAry.length; i++)
			{
				//�ѪR�����e
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
	�e�̫�����������
	@param	ht		�ΨӨ��NTag���
	*/
	public void drawLastFoot(Hashtable ht) throws Exception
	{
		try
		{
			if (rptutil.getKeyObject("GROUPFOOT", "LINE") != null) {
				this.drawGroupFoot(ht, "G");
			}else{
				String	strContent	= "";
				//���]�w LASTFOOT
				if (rptutil.getKeyObject("LASTFOOT", "LINE") != null)
				{
					//�e��
					for (int i = 0; i < this.lastFootAry.length; i++)
					{
						//�ѪR�����e
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
	�����w�ƥت����
	@param	int	skipNumber	���w�ƥت����
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
	���ͳ���
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
	�g�J�ɮ�
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
	���J�����Ÿ�
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
	���J�����Ÿ�
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
	�ѪR�����e
	@param	lineStr		�����e
	@param	ht		�ΨӨ��NTag���
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

			//�B�z�t�ιw�]TAG
			//�t�Τ��
			if (lineStr.indexOf("&SYSDATE") != -1)
			{
				replaceStr	= rptutil.getReplaceStr(lineStr, 9, "&SYSDATE");
				lineStr		= util.replace(lineStr, replaceStr, dtutil.formatCDate(dtutil.getNowCDate()));
			}
			//�`�@����
			if (lineStr.indexOf("&TOTALPAGENUM") != -1)
			{
				replaceStr	= "&TOTALPAGENUM";
				lineStr		= util.replace(lineStr, replaceStr, Integer.toString(totalPageNum) + util.cloneStr(' ', replaceStr.length() - Integer.toString(totalPageNum).length()));
			}
			//����
			if (lineStr.indexOf("&PNO") != -1)
			{
				replaceStr	= "&PNO";
				lineStr		= util.replace(lineStr, replaceStr, Integer.toString(pageNumber) + util.cloneStr(' ', replaceStr.length() - Integer.toString(pageNumber).length()));
			}
			//�s���`�@����
			if (lineStr.indexOf("&GROUPTOTAL") != -1)
			{
				replaceStr	= "&GROUPTOTAL";
				lineStr		= util.replace(lineStr, replaceStr, Integer.toString(grouptotalRows) + util.cloneStr(' ', replaceStr.length() - Integer.toString(grouptotalRows).length()));
			}
			//�`�@����
			if (lineStr.indexOf("&TOTAL") != -1)
			{
				replaceStr	= "&TOTAL";
				lineStr		= util.replace(lineStr, replaceStr, Integer.toString(totalRows) + util.cloneStr(' ', replaceStr.length() - Integer.toString(totalRows).length()));
			}
			//���Y�r��
			if (lineStr.indexOf("&HFONT") != -1)
			{
				replaceStr	= "&HFONT";
				lineStr		= util.replace(lineStr, replaceStr, headFont);
			}
			//���r��
			if (lineStr.indexOf("&BFONT") != -1)
			{
				replaceStr	= "&BFONT";
				lineStr		= util.replace(lineStr, replaceStr, bodyFont);
			}
			//����r��
			if (lineStr.indexOf("&TFONT") != -1)
			{
				replaceStr	= "&TFONT";
				lineStr		= util.replace(lineStr, replaceStr, footFont);
			}
			//���Y���e
			if (lineStr.indexOf("&HCONT") != -1)
			{
				replaceStr	= "&HCONT";
				lineStr		= util.replace(lineStr, replaceStr, headContent);
			}
			//������e
			if (lineStr.indexOf("&TCONT") != -1)
			{
				replaceStr	= "&TCONT";
				lineStr		= util.replace(lineStr, replaceStr, footContent);
			}
			
			//�`�@����
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
				//�n���N���r�� &0, &1...
				replaceStr	= "&" + Integer.toString(i);

				//�r�ꤤ���]�t�n���N�r��h���B�z
				if (lineStr.indexOf(replaceStr) == -1)
					continue;

				//�B�z�]�w�ѼƳ���
				String[]	fieldAry	= (String[])rptutil.getKeyObject("FIELD", "F" + Integer.toString(i));
				fieldName	= fieldAry[0].trim();
				if (fieldName.equals("SERIALNO"))	//�y����
				{
					//���ƥ��L���ɦL�X�y����
					if (!isEnd)
						htValue	= Integer.toString(currentRow);
					else
						htValue	= "";
				}
				else if (fieldName.equals("CURRENTSUM"))	//�ثe�w�L�X����
				{
					htValue	= Integer.toString(currentRow);
				}
				else if (fieldName.equals("PAGENUMBER"))	//�ثe����
				{
					htValue	= Integer.toString(pageNumber);
				}
				else if (fieldName.equals("SYSDATE"))	//�t�Τ��
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
						throw new Exception(className + ".parseContent Exception: �i�Ѽƥ��]�w [FIELD] F" + Integer.toString(i) + ", fieldName: " + fieldName + "�j");
					}
				}

				//�񺡪���
				valIndex	= rptutil.getAryIndex(fieldAry, "ADD");
				if (valIndex > 0)
				{
					typeValue	= fieldAry[valIndex].trim();
					htValue		= Integer.toString(Integer.parseInt(htValue) + Integer.parseInt(typeValue));
				}

				//���ƥ��L���άO�`�ƤΥثe���Ʈɤ~�B�z
				//if (!isEnd || fieldName.equals("SUM") || fieldName.equals("CURRENTSUM"))
				//{

					//�񺡪���
					valIndex	= rptutil.getAryIndex(fieldAry, "FL");
					if (valIndex > 0)
					{
						//�ˬd���]�wFL
						if (rptutil.getAryIndex(fieldAry, "FS") <= 0)
							throw new Exception(className + ".parseContent Exception: �i�����]�w FS �Ѽơj");
						typeValue	= fieldAry[valIndex].trim();
						fillLength	= Integer.parseInt(typeValue);
					}

					//�񺡬Y�r��
					valIndex	= rptutil.getAryIndex(fieldAry, "FS");
					if (valIndex > 0)
					{
						//�ˬd���]�wFL
						if (rptutil.getAryIndex(fieldAry, "FL") <= 0)
							throw new Exception(className + ".parseContent Exception: �i�����]�w FL �Ѽơj");
						typeValue	= fieldAry[valIndex].trim();
						//�ˬd�O�_���r��
						if (typeValue.length() > 1)
							throw new Exception(className + ".parseContent Exception: �iFS �Ѽƭȥ������r�����: " + typeValue + "�j");
						htValue		= util.fillStr(htValue,	fillLength, typeValue.charAt(0));
					}

					//���o���媺�P��
					valIndex	= rptutil.getAryIndex(fieldAry, "CW");
					if (valIndex > 0)
					{
						typeValue	= fieldAry[valIndex].trim();
						if (typeValue.equals("1"))
							htValue	= dtutil.getCWeek(htValue);
					}

					//���o�W�ȤU��
					valIndex	= rptutil.getAryIndex(fieldAry, "DT");
					if (valIndex > 0)
					{
						typeValue	= fieldAry[valIndex].trim();
						if (typeValue.equals("1"))
						{
							if (Integer.parseInt(htValue) - 12 > 0)
								htValue	= "�U��";
							else
								htValue	= "�W��";
						}
					}

					//�榡�Ƥ���B�z
					valIndex	= rptutil.getAryIndex(fieldAry, "FD");
					if (valIndex > 0)
					{
						typeValue	= fieldAry[valIndex].trim();
						//����榡�� --> 092/01/01
						if (typeValue.equals("1"))
							htValue	= dtutil.formatCDate(htValue);
						//����榡�� --> �E�Q�G�~�@��@��
						else if (typeValue.equals("2"))
							htValue	= util.formatCDateForPrint(htValue);
						//����榡�� --> 092�~01��01��
						else if (typeValue.equals("3"))
							htValue = util_date.formatCDateWithCUnits(htValue, false);
//						����榡�� --> 92�~1��1��
						else if (typeValue.equals("4"))
							htValue = util_date.formatCDateWithCUnits(htValue, true);
					}
					
					valIndex	= rptutil.getAryIndex(fieldAry, "FT");
					if (valIndex > 0){
						typeValue	= fieldAry[valIndex].trim();
//						�ɶ��榡�� --> 15:30
						if(typeValue.equals("1"))
							htValue = dtutil.formatTime(htValue);
					}

					//�N�^�Ʀr���ର���Φr��
					valIndex	= rptutil.getAryIndex(fieldAry, "FUS");
					if (valIndex > 0)
					{
						htValue		= util.strToFullSize(htValue);
					}

					//�Ʀr�ର����Ʀr
					valIndex	= rptutil.getAryIndex(fieldAry, "CN");
					if (valIndex > 0)
					{
						typeValue	= fieldAry[valIndex].trim();
						htValue		= util.getCNum(htValue, Integer.parseInt(typeValue));
					}

					//�榡�ƼƦr�B�z
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
							throw new Exception(className + ".parseContent Exception: �i�Ʀr�榡�ƿ��~,  value:" + htValue + ", �榡��:" + typeValue + "�j");
						}
					}

					//�ثe���(����)
					valIndex	= rptutil.getAryIndex(fieldAry, "C");
					if (valIndex > 0)
					{
						typeValue	= fieldAry[valIndex].trim();
						currentLine	= Integer.parseInt(typeValue);
					}
				//}

				//�̤j�e��
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

					//�N�ثe��Ƥζ񺡦r���w�]��l��
					currentLine	= -1;
					fillLength	= 0;
				}

				//�̤j�e��
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

					//�N�ثe��Ƥζ񺡦r���w�]��l��
					currentLine	= -1;
					fillLength	= 0;
				}

				//�������ƦC
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

				//�ƦC�覡
				valIndex	= rptutil.getAryIndex(fieldAry, "A");
				if (valIndex > 0)
				{
					typeValue	= fieldAry[valIndex].trim();

					if (typeValue.equals("0"))	//�m��
						htValue	= htValue;
					else if (typeValue.equals("1"))	//�m��
					{
						int	leftSpaceLength		= (maxLength - rptutil.getBLen(htValue.trim())) % 2 == 0?(maxLength - rptutil.getBLen(htValue.trim())) / 2:(maxLength - rptutil.getBLen(htValue.trim())) / 2 + 1;
						int	rightSpaceLength	= (maxLength - rptutil.getBLen(htValue.trim())) % 2 == 0?(maxLength - rptutil.getBLen(htValue.trim())) / 2:(maxLength - rptutil.getBLen(htValue.trim())) / 2;
						htValue	= util.cloneStr(' ', leftSpaceLength) + htValue.trim() + util.cloneStr(' ', rightSpaceLength);
					}
					else if (typeValue.equals("2"))	//�m�k
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