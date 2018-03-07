package com.sertek.file;
import com.sertek.util.*;
import java.util.*;
import java.io.*;
import java.text.*;

//處理檔案相關公用程式

public class fileUtil {
	//private util_date ud = new util_date();
	private utility ut = new utility();

	public fileUtil() {
	}

	/**
	參數:String path
	說明:將路徑中的'\'(即"\\")轉為'/'
	*/
	public String changeSlopeToRL(String path) {
		return path.replace('\\', '/');
	}

	/**
	參數:String path
	說明:將路徑的'/'與'\\'轉為'\'
	*/
	public String changeSlopeToLR(String path) {
		return path.replace('/', '\\');
	}

	/**
	參數:String path, String cpath, String spath
	說明:將[for文采]的路徑轉為[for AP Server]的路徑(參考cpath與spath更改來源,並統一'/'與'\')
	spath => exp:/nw/k/type, 可由User.readString(...)取得
	cpath => exp:N:\type, 可由User.readString(...)取得
	如傳入路徑已為client path, 則不改變
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
	參數:String path, String cpath, String spath
	說明:將[for AP Server]的路徑轉為[for文采]的路徑(參考cpath與spath更改來源,並統一'/'與'\')
	spath => exp:/nw/k/type, 可由User.readString(...)取得
	cpath => exp:N:\type, 可由User.readString(...)取得
	如傳入路徑已為client path, 則不改變
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
	參數:無
	說明:查看執行環境之os為linux或win
	*/
	public String checkOS() {
		String s = "linux";
		String s1 = System.getProperty("file.separator");
		if (s1.equals("\\"))
			s = "win";
		return s;
	}

	/**
	參數:String path, String cpath, String spath
	說明:將[for AP Server]的路徑轉為[for文采]的路徑
	spath => exp:/nw/k/type, 可由User.readString(...)取得
	cpath => exp:N:\type, 可由User.readString(...)取得
	*/
	public String modifyPath(String path, String cpath, String spath) {
		if ("win".equals(checkOS()))
			return changeToClientPath(path, cpath, spath);
		else
			return changeToServerPath(path, cpath, spath);
	}

	/**
	參數:String path
	說明:根據傳入之檔案位置，判斷此目錄下之特定檔案是否存在
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
	參數:String path
	說明:根據傳入之檔案位置，判斷此目錄下之特定檔案是否存在
	若不存在, 則建立此一目錄與檔案, 當檔案建立失敗時, 傳回false
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
	參數:String path, String type
	說明:根據傳入之檔案位置，判斷此目錄下之特定檔案長度為多少
	當type="byte", 則傳回檔案所佔之byte長度, 當type="char", 則傳回檔案所佔之字元數(含換行等字元,一個中文字算一個字元)
	若檔案不存在,則以-1為傳回值
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
	參數:String path, String type
	說明:根據傳入之檔案位置，判斷此目錄下之特定檔案修改日期，並依參數設定傳回
	參數type="0":傳回七碼民國年月日, type="1":傳回六碼時間時分秒(24時制), type="3":兩者皆傳回, 中間以逗號(,)分隔
	若檔案不存在,則以"0000000","000000","000000,000000"為傳回值
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
			//大小寫表示意義不同
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
	參數:String oldPath, String newPath
	說明:改變檔案名稱與所在位置
	若執行失敗(例:目錄無法存取,原檔案不存在等),則以false為傳回值
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
	參數:String path
	說明:根據傳入之檔案位置，將此目錄下之特定檔案刪除(並不刪除目錄)
	當檔案刪除失敗時, 傳回false
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
	參數:String path
	說明:根據傳入之目錄位置，將此目錄刪除(若目錄內有檔案則不刪除目錄)
	當目錄刪除失敗時, 傳回false
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
	參數:String path
	說明:根據傳入之目錄位置，建立此目錄
	當目錄建立失敗時, 傳回false
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
	參數:String sourcePath, String targetPath
	說明:檔案由sourcePath複製到targetPath,日期不改
	若執行失敗(例:目錄無法存取,原檔案不存在等,目的地檔案已存在等),則以false為傳回值
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
	參數:String path, String chkString
	說明:檢查path所指之檔案的第一行有無指定的字串(chkString)
	若執行失敗(例:目錄無法存取,原檔案不存在等,目的地檔案已存在等),則以false為傳回值
	chkString可接由逗號(,)分隔各元素之字串
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
	參數:File myFile, String chkString
	說明:檢查myFile所代表之檔案的第一行有無指定的字串(chkString)
	若執行失敗(例:目錄無法存取,原檔案不存在等,目的地檔案已存在等),則以false為傳回值
	chkString可接由逗號(,)分隔各元素之字串
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
	參數:String path, String times
	說明:檢查path所指之檔案中,完整之中華民國日期是否出現特定次數(times) 
	若執行失敗(例:原檔案不存在等),則以false為傳回值
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
	參數:String path, String times
	說明:檢查path所指之檔案中,完整之中華民國日期是否出現特定次數(times) 
	若執行失敗(例:原檔案不存在等),則以false為傳回值
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
	參數:String path
	說明:檢查字串是否為完整之中華民國日期(有依序有"中華民國","年","月","日"等字串)
	若執行失敗(例:原檔案不存在等),則以false為傳回值
	*/
	public boolean checkOneLineChimeseDate(String oneLine) {
		try {
			oneLine = ut.noAnyBlank(oneLine);
			int titleSet = 0;
			int yearSet = 0;
			int monthSet = 0;
			int daySet = 0;

			if ((titleSet = oneLine.indexOf("中華民國")) != 0)
				return false;

			if ((yearSet = oneLine.indexOf("年")) == -1)
				return false;

			if ((monthSet = oneLine.indexOf("月")) == -1)
				return false;

			if ((daySet = oneLine.indexOf("日")) == -1)
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

	//把"中華民國ｙｙｙ年ｍｍ月ｄｄ日"轉為"yyymmdd"
	public String convertDate(String line) {
		try {
			String temps = ut.noAnyBlank(line);
			String t =
				getNumFromChinese(
					temps.substring(
						temps.indexOf("國") + 1,
						temps.indexOf("年")));
			if (t.length() == 2) {
				t = "0" + t;
			}
			t =
				t
					+ getNumFromChinese(
						temps.substring(
							temps.indexOf("年") + 1,
							temps.indexOf("月")));
			t =
				t
					+ getNumFromChinese(
						temps.substring(
							temps.indexOf("月") + 1,
							temps.indexOf("日")));
			return t;

		} catch (Exception e) {
			System.out.println("EXCEPTION:convertDate()" + e);
			return "";
		}

	}

	private String getNumFromChinese(String d) {
		Hashtable ht = new Hashtable();
		ht.put("一", "1");
		ht.put("二", "2");
		ht.put("三", "3");
		ht.put("四", "4");
		ht.put("五", "5");
		ht.put("六", "6");
		ht.put("七", "7");
		ht.put("八", "8");
		ht.put("九", "9");

		int sum = 0;
		String temps = "";
		if (d.indexOf("千") != -1) {
			temps = d.substring(d.indexOf("千") - 1, d.indexOf("千"));
			int ti = Integer.parseInt((String) ht.get(temps)) * 1000;
			sum = sum + ti;
		}

		if (d.indexOf("百") != -1) {
			temps = d.substring(d.indexOf("百") - 1, d.indexOf("百"));
			int ti = Integer.parseInt((String) ht.get(temps)) * 100;
			sum = sum + ti;
		}
		if (d.indexOf("十") != -1) {
			if (d.indexOf("十") == 0) {
				sum = sum + 10;
			} else {
				temps = d.substring(d.indexOf("十") - 1, d.indexOf("十"));
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
	參數:String srcString, String chkString
	說明:檢查srcString所代表之字串有無指定的字串(chkString),若有,則傳回指定字傳之前的部分,
	否則傳回原字串,chkString可接由逗號(,)分隔各元素之字串
	*/
	public String cutSpecialString(String srcString, String chkString) {
		if (chkString.indexOf(',') == -1) {
			if (srcString.indexOf(chkString) != -1)
				return srcString.substring(0, srcString.indexOf(chkString));
		} else {
			String chkStrings[] = ut.toStringArray(chkString, ",");
			return cutSpecialString(srcString, chkStrings);
		}

		return srcString; //這行應該不會執行到
	}

	/**
	參數:String srcString, String chkStrings[]
	說明:檢查srcString所代表之字串有無指定的字串(chkString),若有,則傳回指定字傳之前的部分,
	否則傳回原字串,chkString可接由逗號(,)分隔各元素之字串
	*/
	public String cutSpecialString(String srcString, String[] chkStrings) {
		for (int i = 0; i < chkStrings.length; i++)
			if (srcString.indexOf(chkStrings[i]) != -1)
				return srcString.substring(0, srcString.indexOf(chkStrings[i]));
		return srcString;
	}

	/**
	參數:BufferedReader in, BufferedWriter out, String chkString
	說明:檢查輸入檔案(in)在主文前所有指定的字串(chkString)及其之後至行尾切掉,並輸出至指定處(out)
	若無"主文"兩字,則至檔案結束為止,chkString可接由逗號(,)分隔各元素之字串
	ps:"主文"兩字會輸出,本函式輸入與輸出來源均由使用者自行在外部確定,函式本身不包含開關檔動作
	*/
	public boolean cutSpecialString(
		BufferedReader in,
		BufferedWriter out,
		String chkString) {
		String oneLine = "";
		try {
			while ((oneLine = in.readLine()) != null
				|| ut.noAnyBlank(oneLine).indexOf("主文") == -1) {
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
	參數:BufferedReader in, BufferedWriter out, String[] chkStrings
	說明:檢查輸入檔案(in)在主文前所有指定的字串(chkString)及其之後至行尾切掉,並輸出至指定處(out)
	若無"主文"兩字,則至檔案結束為止,chkString可接由逗號(,)分隔各元素之字串
	ps:"主文"兩字會輸出,本函式輸入與輸出來源均由使用者自行在外部確定,函式本身不包含開關檔動作
	*/
	public boolean cutSpecialString(
		BufferedReader in,
		BufferedWriter out,
		String[] chkStrings) {
		String oneLine = "";
		try {
			while ((oneLine = in.readLine()) != null
				|| ut.noAnyBlank(oneLine).indexOf("主文") == -1) {
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
	參數:BufferedReader in, BufferedWriter out, Vector vr
	說明:檢查輸入檔案(in)在主文前所有指定的字串(chkString)及其之後至行尾切掉,並輸出至指定處(out)
	若無"主文"兩字,則至檔案結束為止,chkString可接由逗號(,)分隔各元素之字串
	ps:"主文"兩字會輸出,本函式輸入與輸出來源均由使用者自行在外部確定,函式本身不包含開關檔動作
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
			do{		//讀取檔案內容
				filecontent=filecontent+str+"\r\n";
				str=lnr.readLine();
			} while (str!= null);
			lnr.close();
			fin.close();
		}
		return filecontent;
	}

	/**
	參數:String path
	說明:根據傳入之檔案位置，將此目錄下之特定檔案刪除(並不刪除目錄)
	當檔案刪除失敗時, 傳回false
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
