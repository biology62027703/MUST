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
	�N��޸��ର \'
	@param	str	��J�r��
	@return ����᪺�r��
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
	���o���~�T�����ҩw�q�� Tag ��, ���w�q Tag �Ǧ^�w�]�����~�T��
	@param	errMsg		���~�T��
	@param	defaultMsg	�w�]���T��
	@return ���X�᪺�}�C
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
	���o Tag �w�q���Ҧ��Ȱ}�C Exp: this is �iaa�j a test �ibb�j --> return {"aa", "bb"}
	@param	str	���X Tag ��ƪ��r��
	@return ���X�᪺�}�C
	*/
	public static String[] splitTag(String str) throws Exception
	{
		try
		{
			String[]	tmpAry		= util.split(str, "�j");
			String[]	resultAry	= new String[tmpAry.length];
			int		startPos	= 0;
			int		endPos		= 0;

			for (int i = 0; i < tmpAry.length; i++)
			{
				startPos	= tmpAry[i].lastIndexOf("�i") + 1;
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
	�M�� str �r�ꥪ�䪺�ť�
	@param	str	�n�M���ťժ���l�r��
	@return �M������ťի᪺�r��
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
	�M�� str �r��k�䪺�ť�
	@param	str	�n�M���ťժ���l�r��
	@return �M���k��ťի᪺�r��
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
	�ˬd�ǤJ�r�ꪺ���׬O�_���W�L�]�w����
	@param	str	��l�r��
	@param	strLen	�ˮ֪�����
	@return �ˬd�����G
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
	�N���w str ��J���w���� strLen ��������m�A��L�S�ɺ�����m�H�b�ΪťեN��
	@param	str	���w�r�ꤺ�e
	@param	strLen	���w�Ǧ^�r�ꪺ���פj�p
	@return	�m��󤤶���m�����w���צr��
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
	�Ǧ^���w���ת��b�Ϊťզr��C
	@param 	len		���w����
	@param	spaceType	�ťպ��� 0 - Html �ť� &nbsp;&nbsp;, 1 - �b�Ϊť�
	@return �Ǧ^���w���ת��b�Ϊťզr��C
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
					throw new Exception(className + ".space Exception: \r\n �S���o�تťպ���:" + spaceType);
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
	�Ǧ^�@�S�w���ת����Ʀr���r��
	@param 	str	���w�n���Ъ��r�ꤺ�e
	@param 	len 	���w�n���Ъ�����
	@return �Ǧ^���Ц��ƫ᪺�r�ꤺ�e
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
	�Ǧ^�@�S�w���ת����Ʀr���r��
	@param 	charStr	���w�n���Ъ��r�ꤺ�e
	@param 	len 	���w�n���Ъ�����
	@return �Ǧ^���Ц��ƫ᪺�r�ꤺ�e
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
	�N�r�ꤺ�e str �H���w���� strLen �ǥX�A�������ת̡A�H���w�r�� fillChar �ɨ�
	@param 	str		�r�ꪺ��l���e
	@param 	strLen		�Ǧ^���r����w����
	@param 	fillChar	���w�r��
	@return �Ǧ^�ɨ���r���ơC
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
	�N�t���b�Ϊ��r�� str �אּ���Ϊ��Ǧ^
	@param	charStr	�t���b�Ϊ��r��
	@return �Ǧ^�w�אּ���Ϊ��r��
	*/
	public static String charToFullSize(char charStr) throws Exception
	{
		try
		{
			String		result		= charStr+"";
			StringBuffer	strBuffer	= new StringBuffer();
			strBuffer.append(charStr);
			byte[]	byteAry	= strBuffer.toString().getBytes();

			//�����Φr�h�Ǧ^�ۤv
			if (byteAry.length >= 2)
				result	= strBuffer.toString();
			else
			{
				byte[]	resultByteAry	= {0, 0};
				resultByteAry[0]	= -94;
				int	currByteNum	= Integer.parseInt(Byte.toString(byteAry[0]));

				//0 - 9 --> �� - ��
				if (currByteNum >= 48 && currByteNum <= 57)
					resultByteAry[1]	= Byte.parseByte(Integer.toString(currByteNum - 129));

				//a - z, A - Z --> �� - ��
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
	      			result	= "�A";
	      		if (charStr == '(')
	      			result	= "�]";
	      		if (charStr == ')')
	      			result	= "�^";
	      		if (charStr == ' ')
	      			result	= "�@";
	        	return result;
		}
		catch(Exception err)
		{
			throw new Exception(className + ".charToFullSize Exception: \r\n" + err.toString());
		}
	}

	/**
	�N�ǤJ���r�ꤤ�A���b�Ϊ��^��μƦr�אּ���Ϋ�ǥX
	@param 	str	��l���r�ꤺ�e
	@return �Ǧ^�N�b�Χאּ���Ϊ��r��ǥX
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
	�N�ǤJ�� str �r�ꤤ�A�� oldStr �̡A�H newStr ���N��ǥX
	@param 	str	��l���r�ꤺ�e
	@param 	oldStr	�n�Q���N���r�ꤺ�e
	@param 	newStr 	�n���N�᪺�r�ꤺ�e
	@return �Ǧ^���N�᪺�r��
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
	*�N�r���ন�@���}�C
	@param str	�����Ϊ��r��
	@param delim	�r�ꪺ���j�Ÿ�
	@return �^�Ǥ@���r��}�C
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
	����Object������null�ˮ֡A�Y��null�����p�A�h�Ǧ^defaultvalue����
	@param		o	        ��l��Object
	@param		defaultvalue	�w�]��String
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
	����Object������null�ˮ֡A�Y��null�����p�A�h�Ǧ^ true
	@param		o	        ��l��Object
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
	�N�r�ꤤ���ȡA�e��0���r��R������ܯ¼Ʀr�����r��, ��checkInt("00070", "0"), �h�^�� "70", ���Y checkInt(xxx, "0") ��ǤJ�� xxx �� null, �h�^�� "0"
	@param		o	        ��l�r��
	@param		defaultvalue	�w�]��String
	@return		�^�ǹw�]�ȩί¼Ʀr�����r��
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
	�I�s������ [�ϥ� Get �覡, �t��: �a�ݸ�(?)�覡], �N�Ѽ� URLEncoder.encode() �B�z
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
	�N�Ʀr�r���ഫ�����媺�Ʀr��Ʈ榡
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
	�N�Ʀr�r���ഫ�����媺�Ʀr��Ʈ榡
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
	�N�Ʀr�r���ഫ�����媺�Ʀr��Ʈ榡
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
	�N�Ʀr�r���ഫ�����媺�Ʀr��Ʈ榡 Exp: 12345.06 ���� 0:�@�U�G�d�s�|�Q���I�s��, 1:���U�L�a�s�v�B���I�s��, 2:�@�U�G�d�s�|�Q���I����, 1234506 3:�@�G���|������
	���`�N����'3'�@�묰�ഫ�����Ʀr���i��J�D�Ʀr���A���
	@param	strNum		�r��榡���Ʀr���e
	@param	cNumType	�ഫ�榡
	@return	�Ǧ^���媺�Ʀr�r�X
	*/
	public static String getCNum(String strNum, int cNumType) throws Exception
	{
		if (strNum.equals(""))
			return "";

		StringBuffer	cStrNumSection		= new StringBuffer();		//����Ʀr���q
		StringBuffer	resultStr		= new StringBuffer();		//�^�ǭ�
		try
		{
			String	chineseNum		= "";				//�]�w��
			String	chineseNum0		= "�s�@�G�T�|�����C�K�E";	//����Ʀr���A�@
			String	chineseNum1		= "�s���L�Ѹv��m�èh";	//����Ʀr���A�G
			String	chineseNum2		= "���@�G�T�|�����C�K�E";	//����Ʀr���A�T
			String	chineseNum3		= "���@�G�T�|�����C�K�E";	//����Ʀr���A�|(�����ഫ)

			String	tmpStrNum		= strNum.toString();		//�Ȧs�ǤJ���Ʀr
			String	tmpIntStrNum		= "";				//�Ȧs�ǤJ���Ʀr(��Ƴ���)
			String	tmpStrNumSection	= "";				//�Ʀr���q

			int	posOfDecimalPoint	= 0;				//�p���I��m
			int	digit			= 0;				//���
			boolean	inZero			= true;				//�O�_���["�s"
			boolean	minus			= true;				//�O�_�t��

			//�̺����P�O����Ʀr���A
			if(cNumType == 0)
				chineseNum	= chineseNum0;
			if(cNumType == 1)
				chineseNum	= chineseNum1;
			if(cNumType == 2)
				chineseNum	= chineseNum2;
			if(cNumType == 3)	//�M���B�z�����ഫ
			{
				chineseNum	= chineseNum3;

				for(int i = -1; i < tmpStrNum.length() - 1; i++)
				{
					if (tmpStrNum.substring(i + 1, i + 2).equals("."))
					{
						resultStr.append("�E");
					}
					else
					{
						try
						{
							digit	= Integer.parseInt(tmpStrNum.substring(i + 1, i + 2));
						}
						catch(Exception e)
						{
							throw new Exception(className + ".getCNum() Exception: �i�ǤJ�Ѽƥ]�t�D�Ʀr���A���,  strNum:" + strNum + "�j");
						}
						resultStr.append(chineseNum.substring(digit, digit + 1));
					}
				}
				return	resultStr.toString();
			}

			//�P�O�O�_�t��
			if (tmpStrNum.charAt(0) == '-')	//�t��
			{
				minus		= true;
				tmpStrNum	= tmpStrNum.substring(1, tmpStrNum.length());
			}
			else	//����
				minus	= false;

			//���o�p���I����m
			posOfDecimalPoint	=  tmpStrNum.indexOf(".");

			//���B�z��ƪ�����
			if (posOfDecimalPoint <= 0)
				tmpIntStrNum	= ConvertStr(tmpStrNum);
			else
				tmpIntStrNum	= ConvertStr(tmpStrNum.substring(0, posOfDecimalPoint));

			//�q�Ӧ�ư_�H�C�|��Ƭ��@�p�`
			for(int sectionIndex = 0; sectionIndex <= (tmpIntStrNum.length() - 1) / 4; sectionIndex++)
			{
				//���o�|��ƪ��r����
				if(sectionIndex * 4 + 4 < tmpIntStrNum.length())
					tmpStrNumSection	= tmpIntStrNum.substring(sectionIndex * 4, sectionIndex * 4 + 4);
				else
					tmpStrNumSection	= tmpIntStrNum.substring(sectionIndex * 4, tmpIntStrNum.length());

				//�M�ť|��ƪ�����Ʀr���
				cStrNumSection.setLength(0);

				/* �H�U�� i ����: �ӤQ�ʤd��|�Ӧ�� */
				for(int i = 0; i < tmpStrNumSection.length(); i++)
				{
					digit	= Integer.parseInt(tmpStrNumSection.substring(i, i + 1));
					if (digit == 0)
					{
						// 1. �קK '�s' �����ХX�{
						// 2. �Ӧ�ƪ� 0 �����ন '�s'
						if (!inZero && i != 0)
							cStrNumSection.insert(0, "�s");
						inZero	= true;
					}
					else
					{
						if(cNumType == 1)
						{
							switch(i)
							{
								case 1 :
									cStrNumSection.insert(0, "�B");
									break;
								case 2 :
									cStrNumSection.insert(0, "��");
									break;
								case 3 :
									cStrNumSection.insert(0, "�a");
									break;
							}
						}
						else
						{
							switch(i)
							{
								case 1 :
									cStrNumSection.insert(0, "�Q");
									break;
								case 2 :
									cStrNumSection.insert(0, "��");
									break;
								case 3 :
									cStrNumSection.insert(0, "�d");
									break;
							}
						}
						cStrNumSection.insert(0, chineseNum.substring(digit, digit + 1));
						inZero	= false;
					}
				}

				//�[�W�Ӥp�`�����
				if (cStrNumSection.length() == 0)
				{
					if (resultStr.length() > 0 && resultStr.substring(0, 1).equals("�s"))
						resultStr.insert(0, "�s");
				}
				else
				{
					switch(sectionIndex)
					{
						case 0:
							resultStr.append(cStrNumSection.toString());
							break;
						case 1:
							resultStr.insert(0, cStrNumSection.toString() + "�U");
							break;
						case 2:
							resultStr.insert(0, cStrNumSection.toString() + "��");
							break;
						case 3:
							resultStr.insert(0, cStrNumSection.toString() + "��");
							break;
					}
				}
			}

			//�B�z�p���I�k�䪺����
			if (posOfDecimalPoint > 0)
			{
				resultStr.append("�I");
				for(int i = posOfDecimalPoint; i < tmpStrNum.length() - 1; i++)
				{
					digit		= Integer.parseInt(tmpStrNum.substring(i + 1, i + 2));
					resultStr.append(chineseNum.substring(digit, digit + 1));
				}
			}

			//��L�ҥ~���p���B�z
			if (resultStr.length() == 0)
				resultStr.append("�s");
			if (resultStr.length()>= 2 && (resultStr.toString().substring(0, 2).equals("�@�Q") || resultStr.toString().substring(0, 2).equals("���Q")))
			{
				resultStr.deleteCharAt(0);
			}
			if (resultStr.toString().substring(0, 1).equals("�I"))
				resultStr.insert(0, "�s");

			/* �O�_���t�� */
			if (minus)
				resultStr.insert(0, "�t");

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
	�N�r��ϦV, �Ҧp: �ǤJ '1234', �Ǧ^ '4321'
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
	�N�Ʀr�r�ꪺ�s�h���Ǧ^ Exp:001 --> 1
	@param		strNum	        �Ʀr�r��
	@param		defaultvalue	�w�]��String
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
	�N����榡�Ƭ��C�L�Τ�� Exp: 0920101 --> �E�Q�G�~�@��@��
	@param		dateStr	        ���
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

			return yy + "�~" + mm + "��" + dd + "��";
		}
		catch(Exception err)
		{
			throw new Exception(className + ".formatCDateForPrint(double, int) Exception: \r\n" + err.toString());
		}
	}
	   /**
     * 		�N�ǤJ��eSource�r�ꤤ�A��OldString�̡A�HNewString���N��ǥX
     *@param 	sSource		��l���r�ꤺ�e
     *@param 	OldString	�n�Q���N���r�ꤺ�e
     *@param 	NewString 	�n���N�᪺�r�ꤺ�e
     *@return 			�Ǧ^���N��NewString�᪺eSource
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
       //�^�ǭ�
       return result_0;
   }

}