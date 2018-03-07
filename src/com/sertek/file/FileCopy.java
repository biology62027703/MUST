package com.sertek.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

public class FileCopy {

	private Logger log = Logger.getRootLogger();
	private String WebDir;
	private String Owner_ID;
	private String Owner_File;

	// --------------------------------------------------------------------------

	public FileCopy() {
		WebDir = "";
		Owner_ID = "Website_ID";
		Owner_File = "owner.jsp";
	}

	// --------------------------------------------------------------------------

	public Vector ListDirectory(String s) {
		Vector vector = new Vector();
		try {
			addDirectory(s, vector);
		} catch (IOException e3) {
		}
		return vector;
	}

	// --------------------------------------------------------------------------

	private static boolean addDirectory(String s, Vector vector)
			throws IOException {
		File file = new File(s);
		File afile[] = file.listFiles();

		for (int i = 0; i < afile.length; i++)
			if (afile[i].isDirectory()) {
				String s1 = afile[i].getAbsolutePath();
				vector.add(s1);

				System.out.println("path= " + s1);

				if (!addDirectory(s1, vector))
					return false;
			}

		return true;
	}

	// --------------------------------------------------------------------------

	public boolean delete(String s) {
		File file = new File(s);
		return file.delete();
	}

	// --------------------------------------------------------------------------

	public boolean DeletePath(String s) {
		java.util.Properties properties = System.getProperties();
		String s1 = properties.get("os.name").toString().toUpperCase();
		String s2 = "";
		if (s1.lastIndexOf("WINDOWS") != -1) {
			if (s.endsWith("\\"))
				s2 = "del /Q /S " + s;
			else
				s2 = "del /Q /S " + s + "\\";
		} else {
			s2 = "rm -rf " + s;
		}
		log.debug(s2);
		try {
			Process process = Runtime.getRuntime().exec(s2);
			try {
				process.waitFor();
			} catch (InterruptedException e2) {
				int i = process.exitValue();
				if (i != 0)
					log.debug("Process Exit Status = " + i);
				return false;
			}
			log.debug("Process Successful.");
			return true;
		} catch (IOException e3) {
			return false;
		}
	}

	// --------------------------------------------------------------------------

	public boolean mkdirs(String s) {
		File file = new File(s);
		if (!file.exists())
			return file.mkdirs();
		else
			return true;
	}

	// --------------------------------------------------------------------------

	public void setLastModified(String s) {
		Date date = Calendar.getInstance().getTime();
		File file = new File(s);
		file.setLastModified(date.getTime());
	}

	// --------------------------------------------------------------------------

	public void setWebsiteDir(String s) {
		WebDir = s;
	}

	// --------------------------------------------------------------------------

	public void setWebsiteFile(String s) {
		Owner_File = s;
	}

	// --------------------------------------------------------------------------

	public boolean CreateWebsite(String s, String s1) {
		java.util.Properties properties = System.getProperties();
		String s2 = properties.get("file.separator").toString();
		String s3 = new String(s1.toUpperCase());
		String s4 = s3.substring(0, 1) + s2 + s3.substring(0, 2) + s2 + s3;

		if (WebDir.endsWith(s2))
			s4 = WebDir + s4;
		else
			s4 = WebDir + s2 + s4;
		if (CopyPath(s, s4)) {
			Vector vector = new Vector();
			vector = ListDirectory(s);
			if (vector.isEmpty())
				return false;
			for (int i = 0; i < vector.size(); i++) {
				String s5 = "String " + Owner_ID + " = " + s1;
				String s6 = (String) vector.elementAt(i);
				if (s6.endsWith(s2))
					s6 = s6 + Owner_File;
				else
					s6 = s6 + s2 + Owner_File;
				if (!WriteFile(s6, s5))
					return false;
			}

		} else {
			return false;
		}
		return true;
	}

	// --------------------------------------------------------------------------

	private boolean WriteFile(String s, String s1) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(s));

			bw.write(s1);
			bw.close();

			return true;
		} catch (IOException e3) {
			return false;
		}
	}

	// --------------------------------------------------------------------------

	public boolean CopyPath(String s, String s1) {
		java.util.Properties properties = System.getProperties();
		String s2 = properties.get("os.name").toString().toUpperCase();
		String s3 = "";
		String s4 = "";

		if (s2.lastIndexOf("WINDOWS") != -1) {
			if (!s1.endsWith("\\"))
				s3 = "xcopy.exe /Y " + s + " " + s1 + " /E/H/R";
			else
				s3 = "xcopy.exe /Y " + s + " " + s1 + "\\ /E/H/R";

			log.debug(s3);
		} else {
			s3 = "cp -rf " + s + " " + s1;
			s4 = "touch -r " + s + " " + s1;
			log.debug(s4);
		}

		try {
			Process process = Runtime.getRuntime().exec(s3);
			try {
				process.waitFor();
			} catch (InterruptedException e2) {
				int i = process.exitValue();
				if (i != 0)
					log.debug("Process Exit Status = " + i);
				return false;
			}

			if (!s4.equals("")) {

				process = Runtime.getRuntime().exec(s4);

				try {
					process.waitFor();
				} catch (InterruptedException e2) {
					int j = process.exitValue();
					if (j != 0)
						log.debug("Process Exit Status = " + j);
					return false;
				}
			}

			log.debug("Process Successful.");

			return true;
		} catch (IOException e3) {
			return false;
		}
	}

	// --------------------------------------------------------------------------

	public boolean copyDirectory(String s, String s1) throws IOException {
		File file = new File(s);
		File afile[] = file.listFiles();
		boolean flag = false;
		int l = 0;
		for (int i = 0; i < afile.length; i++)
			if (afile[i].isFile())
				l++;

		File afile1[] = new File[l];
		l = 0;
		for (int j = 0; j < afile.length; j++)
			if (afile[j].isFile()) {
				afile1[l] = afile[j];
				l++;
			}

		for (int k = 0; k < afile.length; k++)
			if (afile[k].isDirectory()) {
				String s2 = afile[k].getAbsolutePath();
				if (!copyDirectory(s2, s1 + File.separator + afile[k].getName()))
					return false;
			}

		File file1 = new File(s1);
		file1.mkdirs();
		return copyFiles(s1, afile1);
	}

	// --------------------------------------------------------------------------

	public boolean copyFiles(String s, File afile[]) throws IOException {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		for (int i = 0; i < afile.length; i++) {
			try {
				bis = new BufferedInputStream(new FileInputStream(afile[i]));

				String s1 = s + File.separator + afile[i].getName();

				log.debug("Copying " + afile[i] + " to " + s1);

				bos = new BufferedOutputStream(new FileOutputStream(s1));

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			do {
				int j = bis.read();
				if (j < 0)
					break;
				bos.write(j);
			} while (true);

			try {
				bis.close();
				bos.close();
			} catch (IOException e3) {
				e3.printStackTrace();
				return false;
			}
		}

		return true;
	}

	/**
	 * 移動檔案
	 * 
	 * @param s
	 * @param s1
	 * @return
	 * @throws IOException
	 */
	public boolean moveFile(String s, String s1) throws IOException {
		boolean retVal = false;
		if (copyFiles(s, s1, true) == true) {
			File f = new File(s);
			retVal = f.delete();
		}
		return retVal;
	}

	// --------------------------------------------------------------------------

	public boolean copyFiles(String s, String s1) throws IOException {
		return copyFiles(s, s1, false);
	}

	/**
	 * 
	 * @param s
	 * @param s1
	 * @param overWrite
	 *            目的檔存在時，是否要覆寫
	 * @return
	 * @throws IOException
	 */
	public boolean copyFiles(String s, String s1, boolean overWrite)
			throws IOException {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		long sDate; // 來源檔的日期

		File afile = new File(s);
		if (afile.exists()) {
			sDate = afile.lastModified();
			try {
				if (overWrite == true) {
					File desFile = new File(s1);
					if (desFile.exists()) {
						desFile.delete();
					}
					desFile = null;
				}
				bis = new BufferedInputStream(new FileInputStream(afile));

				log.debug("Copying " + s + " to " + s1);

				bos = new BufferedOutputStream(new FileOutputStream(s1));

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return false;
			}

			do {
				int j = bis.read();
				if (j < 0)
					break;
				bos.write(j);
			} while (true);

			try {
				bis.close();
				bos.close();

				// 修改日期
				File dFile = new File(s1);
				dFile.setLastModified(sDate);
				dFile = null;
			}

			catch (IOException e3) {
				e3.printStackTrace();
				return false;
			}
		} else {
			return false;
		}

		afile = null;
		return true;
	}

	// --------------------------------------------------------------------------

	public boolean rmDir(String s) throws IOException {
		try {
			File file = new File(s);
			if (!file.isDirectory())
				return false;
			File afile[] = file.listFiles();
			boolean flag = false;
			int l = 0;
			for (int i = 0; i < afile.length; i++)
				if (afile[i].isFile())
					l++;

			File afile1[] = new File[l];
			l = 0;
			for (int j = 0; j < afile.length; j++)
				if (afile[j].isFile()) {
					afile1[l] = afile[j];
					l++;
				}

			delFiles(afile1);
			for (int k = 0; k < afile.length; k++)
				if (afile[k].isDirectory()) {
					String s1 = afile[k].getAbsolutePath();
					if (!rmDir(s1))
						return false;
				}

			if (!file.delete())
				return false;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	// --------------------------------------------------------------------------

	public boolean delFiles(File afile[]) throws IOException {
		for (int i = 0; i < afile.length; i++)
			afile[i].delete();

		return true;
	}

	// --------------------------------------------------------------------------

}