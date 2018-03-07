package com.sertek.report;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;

import com.sertek.db.SelectResult;
import com.sertek.util.util;

public class rptutil {
	
	private static String className = rptutil.class.getName();
	private Hashtable iniContent = new Hashtable();
	public int splitLength_ = 0;

	public rptutil() {
	}

	/**
	 * ��������r��
	 * @param str �n������r����
	 * @param len �`�@����
	 * @return ����᪺�r��
	 */
	public String getAlignStr(String str, int len) throws Exception {
		try {
			int strBLen = getBLen(str.trim());
			int strSize = str.trim().length();
			int fillSize = (int) Math.floor((len - strBLen) / (strSize - 1));

			StringBuffer tmpStr = new StringBuffer();
			for (int i = 0; i < str.length() - 1; i++)
				tmpStr.append(str.substring(i, i + 1) + util.cloneStr(' ', fillSize));
			tmpStr.append(str.substring(str.length() - 1, str.length()));
			return tmpStr.toString();
		} catch (Exception err) {
			throw new Exception(className + ".getAlignStr Exception: \r\n" + err.toString());
		}
	}

	/**
	 * ���XTag������ Exp: [abc] --> abc
	 * @param str �]�tTag����
	 * @return ���X�᪺��
	 */
	public static String parseTag(String str) throws Exception {
		try {
			if (isTag(str))
				return str.substring(1, str.length() - 1);
			else
				throw new Exception(className + ".parseTag Exception: �i�ǤJ�r�ꤣ�]�t '[]' : " + str + "�j");
		} catch (Exception err) {
			throw new Exception(className + ".parseTag Exception: \r\n" + err.toString());
		}
	}

	/**
	 * �P�_�O�_�]�t'[]'�� Tag
	 * @param str �n�P�_����
	 * @return �O�Χ_
	 */
	public static boolean isTag(String str) throws Exception {
		try {
			if (str.indexOf("[") != -1 && str.indexOf("]") != -1)
				return true;
			else
				return false;
		} catch (Exception err) {
			throw new Exception(className + ".isTag Exception: \r\n" + err.toString());
		}
	}

	/**
	 * �NSelectResultE�ഫ��Hashtable
	 * @param ht �ΨӨ��NTag���
	 */
	public static Hashtable srToHt(String[] columnName, SelectResult sr) throws Exception {
		try {
			Hashtable tmpHt = new Hashtable();
			for (int i = 0; i < columnName.length; i++)
				tmpHt.put(columnName[i], sr.getStr(columnName[i]));

			return tmpHt;
		} catch (Exception err) {
			throw new Exception(className + ".srToHt Exception: \r\n" + err.toString());
		}
	}

	/**
	 * �NINI�ɮ׳]�w���e���Hashtable��
	 */
	public void setIniContent(String iniFilePath, Hashtable iniContent) throws Exception {
		try {
			this.iniContent = iniContent;
			BufferedReader bufReader = new BufferedReader(new FileReader(iniFilePath));
			
			Hashtable ht = new Hashtable();
			String tmpTag = "";
			int i = 0;
			String lineStr = "";

			while (lineStr != null) // �P�_�O�_�������
			{
				lineStr = bufReader.readLine();
				if (lineStr == null || (!isTag(lineStr) && lineStr.indexOf("=") == -1))
					continue;
				if (isTag(lineStr) && lineStr.indexOf("[") == 0) {
					if (i != 0)
						iniContent.put(tmpTag, ht);
					tmpTag = parseTag(lineStr);
					ht = new Hashtable();
				} else {
					String[] tmpContent = util.split(lineStr, "=");
					if (tmpContent.length == 1)
						ht.put(tmpContent[0].trim(), "");
					else
						ht.put(tmpContent[0].trim(), lineStr.substring(lineStr.indexOf("=") + 1));
				}
				i++;
			}
			bufReader.close();
			iniContent.put(tmpTag, ht);
		} catch (Exception err) {
			throw new Exception(className + ".setIniContent Exception: \r\n" + err.toString());
		}
	}

	/**
	 * ���o�}�C���ȩҹ�������m(��Ū�]�w�ȥ�)
	 * @param strAry �}�C��
	 * @param str ������
	 * @return ��������m
	 */
	public int getAryIndex(String[] strAry, String str) throws Exception {
		try {
			for (int j = 1; j < strAry.length; j += 2) {
				if (strAry[j].trim().toUpperCase().equals(str.toUpperCase()))
					return j + 1;
			}
			return 0;
		} catch (Exception err) {
			throw new Exception(className + ".getAryIndex Exception: \r\n" + err.toString());
		}
	}

	/**
	 * ���o���N�r�ꪺ��
	 * @param lineStr �o�@��r��(�w���NTag)
	 * @param maxLength ���N�r��̤j����
	 * @param replaceStr ���N�r��
	 * @return ��������(�r��)
	 */
	public String getReplaceStr(String lineStr, int maxLength, String replaceStr) throws Exception {
		try {
			if ((lineStr.indexOf(replaceStr.trim()) + replaceStr.trim().length()) != util.rTrim(lineStr).length())
				return replaceStr + util.cloneStr(' ', maxLength - replaceStr.length());
			else
				return replaceStr.trim();

		} catch (Exception err) {
			throw new Exception(className + ".getReplaceStr Exception: \r\n" + err.toString());
		}
	}

	/**
	 * ���oHashtable����������
	 * @param key1 ����[tag]
	 * @param key2 ���� a = b --> a
	 * @return ��������(Object)
	 */
	public Object getKeyObject(String key1, String key2) throws Exception {
		try {
			return ((Hashtable) iniContent.get(key1)).get(key2);
		} catch (Exception err) {
			return null;
		}
	}

	/**
	 * ���oHashtable����������
	 * @param key1 ����[tag]
	 * @param key2 ���� a = b --> a
	 * @return ��������(�r��)
	 */
	public String getKeyStr(String key1, String key2) throws Exception {
		try {
			return getKeyObject(key1, key2).toString();
		} catch (Exception err) {
			throw new Exception(className + ".getKeyStr Exception: key1:" + key1 + ", key2 " + key2 + " \r\n" + err.toString());
		}
	}

	/**
	 * ���oHashtable����������
	 * @param key1 ����[tag]
	 * @param key2 ���� a = b --> a
	 * @return ��������(Int)
	 */
	public int getKeyInt(String key1, String key2) throws Exception {
		try {
			return Integer.parseInt(getKeyStr(key1, key2));
		} catch (Exception err) {
			throw new Exception(className + ".getKeyInt Exception: key1:" + key1 + ", key2 " + key2 + " \r\n" + err.toString());
		}
	}

	/**
	 * ��JHashtable��
	 * @param key1 ����[tag]
	 * @param key2 ���� a = b --> a
	 * @param str �n��J����
	 * @return ��������(�r��)
	 */
	public void setKeyStr(String key1, String key2, Object str) throws Exception {
		try {
			((Hashtable) iniContent.get(key1)).put(key2, str);
		} catch (Exception err) {
			throw new Exception(className + ".setKeyStr Exception: \r\n" + err.toString());
		}
	}

	/**
	 * �񺡪ťզr��(�C�L��) Exp:1 --> '1 '
	 * @param strNum �Ʀr�r��
	 * @param defaultvalue �w�]��String
	 * @return String
	 */
	public String fillStrP(String str, int len) throws Exception {
		try {
			return str + util.cloneStr(' ', len - getBLen(str));
		} catch (Exception err) {
			throw new Exception(className + ".fillStrP Exception: \r\n" + err.toString());
		}
	}

	/**
	 * �񺡪ťզr��(�C�L��) Exp:1 --> ' 1'
	 * @param strNum �Ʀr�r��
	 * @param defaultvalue �w�]��String
	 * @return String
	 */
	public String fillStrP1(String str, int len) throws Exception {
		try {
			return util.cloneStr(' ', len - getBLen(str)) + str;
		} catch (Exception err) {
			throw new Exception(className + ".fillStrP1 Exception: \r\n" + err.toString());
		}
	}

	/**
	 * �]�w���Φr��(���嬰2�X) - �s�a�B�z����ʧ@
	 * @param String str �n���Ϊ��r��
	 * @param int len ���Φ�����
	 * @param int currLen �n���o�ĴX�q�����(�ثe�Ҧb�C��)
	 */
	public String getSplitStr1(String str, int len, int currLen) throws Exception {
		try {
			String tmpStr = util.replace(str, "\r\n", "\n");
			String[] tmpAry = util.split(tmpStr, "\n");
			int lineNo = 0;
			String resultStr = "";
			int tmpLen = 0;

			for (int i = 0; i < tmpAry.length; i++) {
				getSplitStr(tmpAry[i], len, 0);
				tmpLen = splitLength_;
				for (int j = 0; j <= tmpLen; j++) {
					if (lineNo == currLen)
						resultStr = getSplitStr(tmpAry[i], len, j);

					lineNo++;
				}
			}
			splitLength_ = lineNo - 1;

			return resultStr + util.cloneStr(' ', len - getBLen(resultStr));
		} catch (Exception err) {
			throw new Exception(className + ".getSplitStr1 Exception: \r\n" + err.toString());
		}
	}

	/**
	 * �]�w���Φr��(���嬰2�X)
	 * @param String str �n���Ϊ��r��
	 * @param int len ���Φ�����
	 * @param int currLen �n���o�ĴX�q�����(�ثe�Ҧb�C��)
	 */
	public String getSplitStr(String str, int len, int currLen) throws Exception {
		str = util.replace(util.replace(str, "\r\n", ""), "\n", "");

		StringBuffer currStr = new StringBuffer();
		try {
			int charLength = 0;
			//int splitLength = 0;
			int currLength = 0;
			int lineNo = 0;

			for (int i = 0; i < str.length(); i++) {
				if (i == str.length() - 1)
					charLength = str.substring(i).getBytes().length;
				else
					charLength = str.substring(i, i + 1).getBytes().length;

				currLength += charLength;

				if (currLength > len) {
					if (lineNo == currLen)
						currStr.append(" ");
					lineNo++;
				}

				if ((i == str.length() - 1) && lineNo == currLen)
					currStr.append(str.substring(i));
				else if (lineNo == currLen)
					currStr.append(str.substring(i, i + 1));

				if (currLength == len && i != str.length() - 1) {
					currLength = 0;
					lineNo++;
				} else if (currLength > len)
					currLength = 2;
			}

			splitLength_ = lineNo;

			return currStr.toString();
		} catch (Exception err) {
			throw new Exception(className + ".getSplitStr Exception: \r\n" + err.toString());
		} finally {
			currStr = null;
		}
	}

	/**
	 * ���o�ɮת���(���嬰��X)
	 * @param String str �P�_���ת��r��
	 * @return �Ǧ^����
	 */
	public int getBLen(String str) throws Exception {
		try {
			return str.getBytes("MS950").length;
		} catch (Exception err) {
			throw new Exception(className + ".getBLen Exception: \r\n" + err.toString());
		}
	}

	/**
	 * ���o�m���r��
	 */
	public String getCenterStr(String str, int maxLen, int lineLen) throws Exception {
		StringBuffer resultStr = new StringBuffer();
		try {
			getSplitStr1(str, lineLen, 0);

			int strLineLen = splitLength_;
			for (int i = 1; i <= Math.round((maxLen - strLineLen) / 2); i++)
				resultStr.append("\r\n");

			resultStr.append(str);

			return resultStr.toString();
		} catch (Exception err) {
			throw new Exception(className + ".getCenterStr Exception: \r\n" + err.toString());
		} finally {
			resultStr = null;
		}
	}
}