package com.sertek.file;
import com.sertek.util.*;
import java.util.*;
import java.io.*;
import java.text.*;

//�B�z�ɮ׬������ε{��

public class fileUtil {
	//private util_date ud = new util_date();
	private utility ut = new utility();

	public fileUtil() {
	}

	/**
	�Ѽ�:String path
	����:�N���|����'\'(�Y"\\")�ର'/'
	*/
	public String changeSlopeToRL(String path) {
		return path.replace('\\', '/');
	}

	/**
	�Ѽ�:String path
	����:�N���|��'/'�P'\\'�ର'\'
	*/
	public String changeSlopeToLR(String path) {
		return path.replace('/', '\\');
	}

	/**
	�Ѽ�:String path, String cpath, String spath
	����:�N[for���]�����|�ର[for AP Server]�����|(�Ѧ�cpath�Pspath���ӷ�,�òΤ@'/'�P'\')
	spath => exp:/nw/k/type, �i��User.readString(...)���o
	cpath => exp:N:\type, �i��User.readString(...)���o
	�p�ǤJ���|�w��client path, �h������
	*/
	public String changeToServerPath(String path, String cpath, String spath) {
		spath = changeSlopeToRL(spath);
		cpath = changeSlopeToRL(cpath);
		path = changeSlopeToRL(path);
		if (path.indexOf(spath) != 0) {
			if (path.indexOf(cpath) == 0)
				path = spath + path.substring(cpath.length());
			else
				path = spath + path;
		}

		return path;
	}

	/**
	�Ѽ�:String path, String cpath, String spath
	����:�N[for AP Server]�����|�ର[for���]�����|(�Ѧ�cpath�Pspath���ӷ�,�òΤ@'/'�P'\')
	spath => exp:/nw/k/type, �i��User.readString(...)���o
	cpath => exp:N:\type, �i��User.readString(...)���o
	�p�ǤJ���|�w��client path, �h������
	*/
	public String changeToClientPath(String path, String cpath, String spath) {
		spath = changeSlopeToRL(spath);
		cpath = changeSlopeToRL(cpath);
		path = changeSlopeToRL(path);
		if (path.indexOf(cpath) != 0) {
			if (path.indexOf(spath) == 0)
				path = cpath + path.substring(spath.length());
			else
				path = cpath + path;
		}

		return path;
	}

	/**
	�Ѽ�:�L
	����:�d�ݰ������Ҥ�os��linux��win
	*/
	public String checkOS() {
		String s = "linux";
		String s1 = System.getProperty("file.separator");
		if (s1.equals("\\"))
			s = "win";
		return s;
	}

	/**
	�Ѽ�:String path, String cpath, String spath
	����:�N[for AP Server]�����|�ର[for���]�����|
	spath => exp:/nw/k/type, �i��User.readString(...)���o
	cpath => exp:N:\type, �i��User.readString(...)���o
	*/
	public String modifyPath(String path, String cpath, String spath) {
		if ("win".equals(checkOS()))
			return changeToClientPath(path, cpath, spath);
		else
			return changeToServerPath(path, cpath, spath);
	}

	/**
	�Ѽ�:String path
	����:�ھڶǤJ���ɮצ�m�A�P�_���ؿ��U���S�w�ɮ׬O�_�s�b
	*/
	public boolean checkFile(String path) {
		path = changeSlopeToRL(path);
		int index = path.lastIndexOf('/');
		String dir = path.substring(0, index);
		String file = path.substring(index + 1);
		File myFile = new File(dir, file);
		return myFile.exists();
	}

	/**
	�Ѽ�:String path
	����:�ھڶǤJ���ɮצ�m�A�P�_���ؿ��U���S�w�ɮ׬O�_�s�b
	�Y���s�b, �h�إߦ��@�ؿ��P�ɮ�, ���ɮ׫إߥ��Ѯ�, �Ǧ^false
	*/
	public boolean setFile(String path) {
		path = changeSlopeToRL(path);
		int index = path.lastIndexOf('/');
		String dir = path.substring(0, index);
		String file = path.substring(index + 1);
		File myFile = new File(dir, file);
		if (myFile.exists())
			return true;
		else {
			File newDir = new File(dir);
			if (!newDir.exists())
				if (!newDir.mkdirs())
					return false;
			try {
				return myFile.createNewFile();
			} catch (Exception ex) {
				System.out.println("fileUtil.setFile error:" + ex);
				return false;
			}

		}
	}

	/**
	�Ѽ�:String path, String type
	����:�ھڶǤJ���ɮצ�m�A�P�_���ؿ��U���S�w�ɮת��׬��h��
	��type="byte", �h�Ǧ^�ɮשҦ���byte����, ��type="char", �h�Ǧ^�ɮשҦ����r����(�t���浥�r��,�@�Ӥ���r��@�Ӧr��)
	�Y�ɮפ��s�b,�h�H-1���Ǧ^��
	*/
	public long checkFileSize(String path, String type) {
		path = changeSlopeToRL(path);
		int index = path.lastIndexOf('/');
		String dir = path.substring(0, index);
		String file = path.substring(index + 1);
		File myFile = new File(changeSlopeToLR(dir), changeSlopeToLR(file));
		if (myFile.exists()) {
			if ("byte".equals(type))
				return myFile.length();
			else {
				try {
					System.out.println("start");
					BufferedReader in =
						new BufferedReader(
							new InputStreamReader(
								new FileInputStream(myFile),
								"MS950"),
							32768);
					long size = 0;
					do {
						if (in.read() == -1)
							break;
						else
							size++;
					} while (true);

					in.close();
					return size;
				} catch (Exception ex) {
					System.out.println("fileUtil.checkFileSize error:" + ex);
					return -1;
				}
			}
		} else
			return -1;
	}

	/**
	�Ѽ�:String path, String type
	����:�ھڶǤJ���ɮצ�m�A�P�_���ؿ��U���S�w�ɮ׭ק����A�ḛ̀ѼƳ]�w�Ǧ^
	�Ѽ�type="0":�Ǧ^�C�X����~���, type="1":�Ǧ^���X�ɶ��ɤ���(24�ɨ�), type="3":��̬ҶǦ^, �����H�r��(,)���j
	�Y�ɮפ��s�b,�h�H"0000000","000000","000000,000000"���Ǧ^��
	*/
	public String checkFileDate(String path, String type) {
		path = changeSlopeToRL(path);
		int index = path.lastIndexOf('/');
		String dir = path.substring(0, index);
		String file = path.substring(index + 1);
		File myFile = new File(dir, file);
		if (myFile.exists()) {
			java.util.Date fileDate = new java.util.Date(myFile.lastModified());
			SimpleDateFormat tf = new SimpleDateFormat("yyyyMMdd,HHmmss");
			//�j�p�g��ܷN�q���P
			String year =
				""
					+ (Integer.parseInt(tf.format(fileDate).substring(0, 4))
						- 1911);
			if (year.length() == 2)
				year = "0" + year;
			else if (year.length() == 1)
				year = "00" + year;

			if ("0".equals(type))
				return year + tf.format(fileDate).substring(4, 8);
			else if ("1".equals(type))
				return tf.format(fileDate).substring(9);
			else
				return year + tf.format(fileDate).substring(4);
		} else {
			if ("0".equals(type))
				return "0000000";
			else if ("1".equals(type))
				return "000000";
			else
				return "0000000,000000";
		}
	}

	/**
	�Ѽ�:String oldPath, String newPath
	����:�����ɮצW�ٻP�Ҧb��m
	�Y���楢��(��:�ؿ��L�k�s��,���ɮפ��s�b��),�h�Hfalse���Ǧ^��
	*/
	public boolean changeNewPath(String oldPath, String newPath) {
		if (oldPath != null)
			if (oldPath.equals(newPath))
				return false;

		oldPath = changeSlopeToRL(oldPath);
		int index = oldPath.lastIndexOf('/');
		String dir1 = oldPath.substring(0, index);
		String file1 = oldPath.substring(index + 1);
		File myFile1 = new File(dir1, file1);

		newPath = changeSlopeToRL(newPath);
		index = newPath.lastIndexOf('/');
		String dir2 = newPath.substring(0, index);
		String file2 = newPath.substring(index + 1);
		File newDir = new File(dir2);
		File myFile2 = new File(dir2, file2);

		if (!myFile1.exists() || myFile2.exists())
			return false;

		if (!newDir.exists())
			if (!newDir.mkdirs())
				return false;

		try {
			return myFile1.renameTo(myFile2);
		} catch (Exception ex) {
			System.out.println("fileUtil.changeNewPath error:" + ex);
			return false;
		}
	}

	/**
	�Ѽ�:String path
	����:�ھڶǤJ���ɮצ�m�A�N���ؿ��U���S�w�ɮקR��(�ä��R���ؿ�)
	���ɮקR�����Ѯ�, �Ǧ^false
	*/
	public boolean delFile(String path) {
		path = changeSlopeToRL(path);
		int index = path.lastIndexOf('/');
		String dir = path.substring(0, index);
		String file = path.substring(index + 1);
		File myFile = new File(dir, file);
		if (!myFile.exists() || !myFile.isFile())
			return false;
		else {
			try {
				return myFile.delete();
			} catch (Exception ex) {
				System.out.println("fileUtil.delFile error:" + ex);
				return false;
			}

		}
	}

	/**
	�Ѽ�:String path
	����:�ھڶǤJ���ؿ���m�A�N���ؿ��R��(�Y�ؿ������ɮ׫h���R���ؿ�)
	��ؿ��R�����Ѯ�, �Ǧ^false
	*/
	public boolean delDir(String path) {
		path = changeSlopeToRL(path);
		File myFile = new File(path);
		if (!myFile.exists() || !myFile.isDirectory())
			return false;
		else {
			try {
				return myFile.delete();
			} catch (Exception ex) {
				System.out.println("fileUtil.delDir error:" + ex);
				return false;
			}

		}
	}

	/**
	�Ѽ�:String path
	����:�ھڶǤJ���ؿ���m�A�إߦ��ؿ�
	��ؿ��إߥ��Ѯ�, �Ǧ^false
	*/
	public boolean mkDir(String path) {
		path = changeSlopeToRL(path);
		File myFile = new File(path);
		if (myFile.exists())
			return false;
		else {
			try {
				return myFile.mkdir();
			} catch (Exception ex) {
				System.out.println("fileUtil.mkDir error:" + ex);
				return false;
			}

		}
	}

	/**
	�Ѽ�:String sourcePath, String targetPath
	����:�ɮץ�sourcePath�ƻs��targetPath,�������
	�Y���楢��(��:�ؿ��L�k�s��,���ɮפ��s�b��,�ت��a�ɮפw�s�b��),�h�Hfalse���Ǧ^��
	*/
	public boolean copyFile(String sourcePath, String targetPath) {
		if (sourcePath != null)
			if (sourcePath.equals(targetPath))
				return false;

		sourcePath = changeSlopeToRL(sourcePath);
		int index = sourcePath.lastIndexOf('/');
		String dir1 = sourcePath.substring(0, index);
		String file1 = sourcePath.substring(index + 1);
		File myFile1 = new File(dir1, file1);

		targetPath = changeSlopeToRL(targetPath);
		index = targetPath.lastIndexOf('/');
		String dir2 = targetPath.substring(0, index);
		String file2 = targetPath.substring(index + 1);
		File newDir = new File(dir2);
		File myFile2 = new File(dir2, file2);

		if (!myFile1.exists() || myFile2.exists())
			return false;

		try {
			if (!newDir.exists())
				if (!newDir.mkdirs())
					return false;

			if (!myFile2.createNewFile())
				return false;

			int size = 32768;
			byte temp[] = new byte[32768];

			FileInputStream in = new FileInputStream(myFile1);
			FileOutputStream out = new FileOutputStream(myFile2);

			for (int i = 0; i < myFile1.length() / 32768 + 1; i++) {

				if (i == myFile1.length() / 32768)
					size = (int) (myFile1.length() - i * 32768);
				in.read(temp, 0, size);
				out.write(temp, 0, size);
			}

			in.close();
			out.flush();
			out.close();
			myFile2.setLastModified(myFile1.lastModified());
			return true;
		} catch (Exception e) {
			System.out.println("fileUtil.copyFile error:" + e);
			return false;
		}
	}

	/**
	�Ѽ�:String path, String chkString
	����:�ˬdpath�ҫ����ɮת��Ĥ@�榳�L���w���r��(chkString)
	�Y���楢��(��:�ؿ��L�k�s��,���ɮפ��s�b��,�ت��a�ɮפw�s�b��),�h�Hfalse���Ǧ^��
	chkString�i���ѳr��(,)���j�U�������r��
	*/
	public boolean checkStringInFirstLine(String path, String chkString) {
		if (chkString == null || "".equals(chkString))
			return false;

		path = changeSlopeToRL(path);
		int index = path.lastIndexOf('/');
		String dir = path.substring(0, index);
		String file = path.substring(index + 1);
		File myFile = new File(dir, file);

		if (!myFile.exists())
			return false;

		return checkStringInFirstLine(myFile, chkString);
	}

	/**
	�Ѽ�:File myFile, String chkString
	����:�ˬdmyFile�ҥN���ɮת��Ĥ@�榳�L���w���r��(chkString)
	�Y���楢��(��:�ؿ��L�k�s��,���ɮפ��s�b��,�ت��a�ɮפw�s�b��),�h�Hfalse���Ǧ^��
	chkString�i���ѳr��(,)���j�U�������r��
	*/
	public boolean checkStringInFirstLine(File myFile, String chkString) {
		if (!myFile.exists())
			return false;

		try {
			BufferedReader in =
				new BufferedReader(
					new InputStreamReader(new FileInputStream(myFile), "MS950"),
					32768);
			String firstLine = in.readLine();
			in.close();

			if ("".equals(chkString.trim()))
				return true;

			if (chkString.indexOf(',') == -1) {
				if (firstLine.indexOf(chkString) != -1)
					return true;
				else
					return false;
			} else {
				int flag = 0;
				String chkStrings[] = ut.toStringArray(chkString, ",");
				for (int i = 0; i < chkStrings.length; i++) {
					if (firstLine.indexOf(chkStrings[i]) != -1) {
						flag++;
						break;
					}
				}

				if (flag > 0)
					return true;
				else
					return false;
			}
		} catch (Exception e) {
			System.out.println("fileUtil.checkStringInFirstLine error:" + e);
			return false;
		}

	}

	/**
	�Ѽ�:String path, String times
	����:�ˬdpath�ҫ����ɮפ�,���㤧���إ������O�_�X�{�S�w����(times) 
	�Y���楢��(��:���ɮפ��s�b��),�h�Hfalse���Ǧ^��
	*/
	public boolean checkChimeseDate(String path, int times) {
		if (times == 0)
			return true;

		path = changeSlopeToRL(path);
		int index = path.lastIndexOf('/');
		String dir = path.substring(0, index);
		String file = path.substring(index + 1);
		File myFile = new File(dir, file);

		if (!myFile.exists())
			return false;

		return checkChimeseDate(myFile, times);
	}

	/**
	�Ѽ�:String path, String times
	����:�ˬdpath�ҫ����ɮפ�,���㤧���إ������O�_�X�{�S�w����(times) 
	�Y���楢��(��:���ɮפ��s�b��),�h�Hfalse���Ǧ^��
	*/
	public boolean checkChimeseDate(File myFile, int times) {
		if (!myFile.exists())
			return false;
		try {
			int count = 0;
			//boolean EOF = false;
			String oneLine = new String();
			BufferedReader in =
				new BufferedReader(
					new InputStreamReader(new FileInputStream(myFile), "MS950"),
					32768);

			while ((oneLine = in.readLine()) != null) {
				if (checkOneLineChimeseDate(oneLine))
					count++;
			}

			//System.out.println("count="+count);
			in.close();

			if (count >= times)
				return true;
			else
				return false;
		} catch (Exception e) {
			System.out.println("fileUtil.checkChimeseDate error:" + e);
			return false;
		}

	}

	/**
	�Ѽ�:String path
	����:�ˬd�r��O�_�����㤧���إ�����(���̧Ǧ�"���إ���","�~","��","��"���r��)
	�Y���楢��(��:���ɮפ��s�b��),�h�Hfalse���Ǧ^��
	*/
	public boolean checkOneLineChimeseDate(String oneLine) {
		try {
			oneLine = ut.noAnyBlank(oneLine);
			int titleSet = 0;
			int yearSet = 0;
			int monthSet = 0;
			int daySet = 0;

			if ((titleSet = oneLine.indexOf("���إ���")) != 0)
				return false;

			if ((yearSet = oneLine.indexOf("�~")) == -1)
				return false;

			if ((monthSet = oneLine.indexOf("��")) == -1)
				return false;

			if ((daySet = oneLine.indexOf("��")) == -1)
				return false;

			if (titleSet < yearSet && yearSet < monthSet && monthSet < daySet)
				return true;
			else
				return false;
		} catch (Exception e) {
			System.out.println("fileUtil.checkOneLineChimeseDate error:" + e);
			return false;
		}
	}

	//��"���إ���B�B�B�~���������"�ର"yyymmdd"
	public String convertDate(String line) {
		try {
			String temps = ut.noAnyBlank(line);
			String t =
				getNumFromChinese(
					temps.substring(
						temps.indexOf("��") + 1,
						temps.indexOf("�~")));
			if (t.length() == 2) {
				t = "0" + t;
			}
			t =
				t
					+ getNumFromChinese(
						temps.substring(
							temps.indexOf("�~") + 1,
							temps.indexOf("��")));
			t =
				t
					+ getNumFromChinese(
						temps.substring(
							temps.indexOf("��") + 1,
							temps.indexOf("��")));
			return t;

		} catch (Exception e) {
			System.out.println("EXCEPTION:convertDate()" + e);
			return "";
		}

	}

	private String getNumFromChinese(String d) {
		Hashtable ht = new Hashtable();
		ht.put("�@", "1");
		ht.put("�G", "2");
		ht.put("�T", "3");
		ht.put("�|", "4");
		ht.put("��", "5");
		ht.put("��", "6");
		ht.put("�C", "7");
		ht.put("�K", "8");
		ht.put("�E", "9");

		int sum = 0;
		String temps = "";
		if (d.indexOf("�d") != -1) {
			temps = d.substring(d.indexOf("�d") - 1, d.indexOf("�d"));
			int ti = Integer.parseInt((String) ht.get(temps)) * 1000;
			sum = sum + ti;
		}

		if (d.indexOf("��") != -1) {
			temps = d.substring(d.indexOf("��") - 1, d.indexOf("��"));
			int ti = Integer.parseInt((String) ht.get(temps)) * 100;
			sum = sum + ti;
		}
		if (d.indexOf("�Q") != -1) {
			if (d.indexOf("�Q") == 0) {
				sum = sum + 10;
			} else {
				temps = d.substring(d.indexOf("�Q") - 1, d.indexOf("�Q"));
				int ti = Integer.parseInt((String) ht.get(temps)) * 10;
				sum = sum + ti;
			}
		}

		temps = d.substring(d.length() - 1);
		int ti = 0;
		if (ht.get(temps) != null)
			ti = Integer.parseInt((String) ht.get(temps));
		sum = sum + ti;
		String ts = new String("" + sum);
		if (ts.length() == 1) {
			ts = "0" + ts;
		}
		return ts;
	}

	/**
	�Ѽ�:String srcString, String chkString
	����:�ˬdsrcString�ҥN���r�꦳�L���w���r��(chkString),�Y��,�h�Ǧ^���w�r�Ǥ��e������,
	�_�h�Ǧ^��r��,chkString�i���ѳr��(,)���j�U�������r��
	*/
	public String cutSpecialString(String srcString, String chkString) {
		if (chkString.indexOf(',') == -1) {
			if (srcString.indexOf(chkString) != -1)
				return srcString.substring(0, srcString.indexOf(chkString));
		} else {
			String chkStrings[] = ut.toStringArray(chkString, ",");
			return cutSpecialString(srcString, chkStrings);
		}

		return srcString; //�o�����Ӥ��|�����
	}

	/**
	�Ѽ�:String srcString, String chkStrings[]
	����:�ˬdsrcString�ҥN���r�꦳�L���w���r��(chkString),�Y��,�h�Ǧ^���w�r�Ǥ��e������,
	�_�h�Ǧ^��r��,chkString�i���ѳr��(,)���j�U�������r��
	*/
	public String cutSpecialString(String srcString, String[] chkStrings) {
		for (int i = 0; i < chkStrings.length; i++)
			if (srcString.indexOf(chkStrings[i]) != -1)
				return srcString.substring(0, srcString.indexOf(chkStrings[i]));
		return srcString;
	}

	/**
	�Ѽ�:BufferedReader in, BufferedWriter out, String chkString
	����:�ˬd��J�ɮ�(in)�b�D��e�Ҧ����w���r��(chkString)�Ψ䤧��ܦ������,�ÿ�X�ܫ��w�B(out)
	�Y�L"�D��"��r,�h���ɮ׵�������,chkString�i���ѳr��(,)���j�U�������r��
	ps:"�D��"��r�|��X,���禡��J�P��X�ӷ����ѨϥΪ̦ۦ�b�~���T�w,�禡�������]�t�}���ɰʧ@
	*/
	public boolean cutSpecialString(
		BufferedReader in,
		BufferedWriter out,
		String chkString) {
		String oneLine = "";
		try {
			while ((oneLine = in.readLine()) != null
				|| ut.noAnyBlank(oneLine).indexOf("�D��") == -1) {
				oneLine = cutSpecialString(oneLine, chkString);
				out.write(oneLine, 0, oneLine.length());
				out.newLine();
				out.flush();
			}

			if (oneLine != null) {
				out.write(oneLine, 0, oneLine.length());
				out.newLine();
				out.flush();
			}

			return true;
		} catch (Exception e) {
			System.out.println("fileUtil.cutSpecialString error:" + e);
			return false;
		}
	}

	/**
	�Ѽ�:BufferedReader in, BufferedWriter out, String[] chkStrings
	����:�ˬd��J�ɮ�(in)�b�D��e�Ҧ����w���r��(chkString)�Ψ䤧��ܦ������,�ÿ�X�ܫ��w�B(out)
	�Y�L"�D��"��r,�h���ɮ׵�������,chkString�i���ѳr��(,)���j�U�������r��
	ps:"�D��"��r�|��X,���禡��J�P��X�ӷ����ѨϥΪ̦ۦ�b�~���T�w,�禡�������]�t�}���ɰʧ@
	*/
	public boolean cutSpecialString(
		BufferedReader in,
		BufferedWriter out,
		String[] chkStrings) {
		String oneLine = "";
		try {
			while ((oneLine = in.readLine()) != null
				|| ut.noAnyBlank(oneLine).indexOf("�D��") == -1) {
				oneLine = cutSpecialString(oneLine, chkStrings);
				out.write(oneLine, 0, oneLine.length());
				out.newLine();
				out.flush();
			}

			if (oneLine != null) {
				out.write(oneLine, 0, oneLine.length());
				out.newLine();
				out.flush();
			}

			return true;
		} catch (Exception e) {
			System.out.println("fileUtil.cutSpecialString error:" + e);
			return false;
		}
	}

	/**
	�Ѽ�:BufferedReader in, BufferedWriter out, Vector vr
	����:�ˬd��J�ɮ�(in)�b�D��e�Ҧ����w���r��(chkString)�Ψ䤧��ܦ������,�ÿ�X�ܫ��w�B(out)
	�Y�L"�D��"��r,�h���ɮ׵�������,chkString�i���ѳr��(,)���j�U�������r��
	ps:"�D��"��r�|��X,���禡��J�P��X�ӷ����ѨϥΪ̦ۦ�b�~���T�w,�禡�������]�t�}���ɰʧ@
	*/
	public boolean cutSpecialString(
		BufferedReader in,
		BufferedWriter out,
		Vector vr) {
		String chkString = "";
		for (int i = 0; i < vr.size(); i++)
			chkString = chkString + "," + vr.elementAt(i).toString();
		chkString = chkString.substring(1);

		return cutSpecialString(in, out, chkString);
	}
	
	public String getFileContext(String filenm) throws IOException {
		
		String filecontent = "";
		File srcfile= new File(filenm);
		if (srcfile.exists()) {			
			FileReader fin= new FileReader(filenm);
			LineNumberReader lnr = new LineNumberReader(fin);
			String str=lnr.readLine();
			do{		//Ū���ɮפ��e
				filecontent=filecontent+str+"\r\n";
				str=lnr.readLine();
			} while (str!= null);
			lnr.close();
			fin.close();
		}
		return filecontent;
	}

	/**
	�Ѽ�:String path
	����:�ھڶǤJ���ɮצ�m�A�N���ؿ��U���S�w�ɮקR��(�ä��R���ؿ�)
	���ɮקR�����Ѯ�, �Ǧ^false
	*/
	public boolean rename(String filenm1, String filenm2) {
		File f1 = new File(filenm1);
	    File f2 = new File(filenm2);  
	    boolean ret = false;
	    
	    try{  	    	
	    	if( f1.isFile() )
	    		ret = f1.renameTo(f2);
	    }catch(Exception e){
	    	System.out.println(e.toString());
	    }
        return ret;
	}

}
