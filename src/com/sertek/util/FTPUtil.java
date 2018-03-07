package com.sertek.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.sertek.util.FTPFileFilter;
import com.sertek.file.fileUtil;

// require commons-net-3.0.1.jar
public class FTPUtil {

	public final static String FTP_METHOD_PUT = new String("PUT");
	public final static String FTP_METHOD_GET = new String("GET");
	
	public final static String FTP_CONNECTION_MODE_ACTIVE = new String("1");
	public final static String FTP_CONNECTION_MODE_PASV = new String("2");
	
	public final static String FTP_TRANSFER_TYPE_ASCII = new String("1");
	public final static String FTP_TRANSFER_TYPE_BINARY = new String("2");
	
	private fileUtil fileUtil = null;

	private String ftpip = "";
	
	private String ftpmethod = "";
	
	private String port = "";
	
	private String timeout = "";
	
	private String connectionmode = "";
	
	private String userid = "";
	
	private String password = "";
	
	private String transfertype = "";
	
	private String ftppath = "";
	
	private String localpath = "";
	
	private String filter = "";
	
	private String deletefile = "";
	
	private String subdir = "";
	
	private String zok = "";
	
	private FTPClient ftp = null;
	
	private List replyList = new ArrayList();
	
	private String lastReplyString = "";
	
	private String sourcePath = "";
	
	private String targetPath = "";
	
	public FTPUtil() {
		ftp = new FTPClient();
		fileUtil = new fileUtil();
	}
	
	public boolean doTransfer() throws Exception {
		boolean result = false;
		try {
			// connect
			if (!"".equals(port)) {
				ftp.connect(ftpip, Integer.parseInt(port));
			} else {
				ftp.connect(ftpip);
			}
			addReply(ftp);

			// set timeout
			if (!"".equals(timeout)) {
				ftp.setConnectTimeout(Integer.parseInt(timeout) * 1000);
				addReply(ftp);
			}
			
			// login
			ftp.login(userid, password);
			addReply(ftp);
			
			// set connection mode
			if (FTPUtil.FTP_CONNECTION_MODE_ACTIVE.equals(connectionmode)) {
				ftp.enterLocalActiveMode();
			} else if (FTPUtil.FTP_CONNECTION_MODE_PASV.equals(connectionmode)) {
				ftp.enterLocalPassiveMode();
			}
			
			// set transfer type
			if (FTPUtil.FTP_TRANSFER_TYPE_ASCII.equals(transfertype)) {
				ftp.setFileType(FTP.ASCII_FILE_TYPE);
				addReply(ftp);
			} else if (FTPUtil.FTP_TRANSFER_TYPE_BINARY.equals(transfertype)) {
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
				addReply(ftp);
			}
			
			if (FTPUtil.FTP_METHOD_PUT.equals(ftpmethod)) {
				doPut();
				if ("Y".equals(zok)) {
					String zokPath = ftppath + "/" + "z.ok";
					addReply("create z.ok " + zokPath);
					ftp.storeFileStream(zokPath);
					addReply(ftp);
				}
			} else if (FTPUtil.FTP_METHOD_GET.equals(ftpmethod)) {
				doGet();
				if ("Y".equals(zok)) {
					String zokPath = localpath + "\\" + "z.ok";
					addReply("create z.ok " + zokPath);
					File zokFile = new File(zokPath);
					if (!zokFile.exists()) {
						zokFile.createNewFile();
					}
				}
			}

			try {
				ftp.noop(); // check that control connection is working OK
				addReply(ftp);

				ftp.logout();
				addReply(ftp);
			} catch (Exception ignored) {
				addReply(ignored.getMessage());
			}
            
			result = true;
		} catch (FTPException ftpe) {
			result = false;
		} catch (Exception e) {
			throw e;
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ignored) {
				}
			}
		}
		return result;
	}
	
	private void doPut() throws Exception {
		File[] localFiles = new File(localpath).listFiles();
		if (localFiles != null) {
			sourcePath = localpath;
			targetPath = ftppath;
			for (int i = 0; i < localFiles.length; i++) {
				File aFile = (File) localFiles[i];
				if (aFile.isDirectory()) {
					if ("Y".equals(subdir)) {
						if (sourcePath.endsWith("\\")) {
							sourcePath = sourcePath + aFile.getName();
						} else {
							sourcePath = sourcePath + "\\" + aFile.getName();
						}

						if (targetPath.endsWith("/")) {
							targetPath = targetPath + aFile.getName();
						} else {
							targetPath = targetPath + "/" + aFile.getName();
						}
						doPutDirectory(aFile);
					}
				} else if (aFile.isFile()) {
					sourcePath = localpath;
					targetPath = ftppath;
					doPutFile(aFile);
				}
			}
		}
	}
	
	private void doPutDirectory(File aDir) throws Exception {
		String tempSourcePath = sourcePath;
		String tempTargetPath = targetPath;

		if (ftp.listFiles(targetPath).length == 0) {
			addReply("makeDirectory " + targetPath);
			ftp.makeDirectory(targetPath);
			// addReply(ftp);
		}

		File[] files = aDir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File aFile = (File) files[i];
				if (aFile.isDirectory()) {
					if (sourcePath.endsWith("\\")) {
						sourcePath = sourcePath + aFile.getName();
					} else {
						sourcePath = sourcePath + "\\" + aFile.getName();
					}

					if (targetPath.endsWith("/")) {
						targetPath = targetPath + aFile.getName();
					} else {
						targetPath = targetPath + "/" + aFile.getName();
					}
					doPutDirectory(aFile);
				} else if (aFile.isFile()) {
					sourcePath = tempSourcePath;
					targetPath = tempTargetPath;
					doPutFile(aFile);
				}
			}
		}
	}
	
	private void doPutFile(File aFile) throws Exception {
		if (!FTPFileFilter.accept(aFile.getName(), filter)) {
			return;
		}
		
		String remote = "";
		if (targetPath.endsWith("/")) {
			remote = targetPath + aFile.getName();
		} else {
			remote = targetPath + "/" + aFile.getName();
		}

		String local = "";
		if (sourcePath.endsWith("\\")) {
			local = sourcePath + aFile.getName();
		} else {
			local = sourcePath + "\\" + aFile.getName();
		}
		InputStream localInputStream = new FileInputStream(local);

		addReply(local + " to " + remote);
		ftp.storeFile(remote, localInputStream);
		addReply(ftp);

		localInputStream.close();
		
		String modificationTime = getModificationTime(aFile.lastModified());
		addReply("modificationTime = " + modificationTime);
		boolean success = ftp.setModificationTime(remote, modificationTime);
		addReply("success = " + success);
		
		if ("Y".equals(deletefile)) {
			addReply("delete " + local);
			fileUtil.delFile(local);
		}
	}
	
	private String getModificationTime(long time) throws Exception {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.add(Calendar.HOUR, -8); //因為set上去的時間會被+0800, 所以先-8
		
		return format.format(cal.getTime());
	}
	
	private void doGet() throws Exception {
		FTPFile[] ftpFiles = ftp.listFiles(ftppath);
		if (ftpFiles != null) {
			sourcePath = ftppath;
			targetPath = localpath;
			for (int i = 0; i < ftpFiles.length; i++) {
				FTPFile aFtpFile = (FTPFile) ftpFiles[i];
				if (aFtpFile.getName().startsWith(".")) {
					continue;
				}
				if (aFtpFile.isDirectory()) {
					if ("Y".equals(subdir)) {
						if (sourcePath.endsWith("/")) {
							sourcePath = sourcePath + aFtpFile.getName();
						} else {
							sourcePath = sourcePath + "/" + aFtpFile.getName();
						}

						if (targetPath.endsWith("\\")) {
							targetPath = targetPath + aFtpFile.getName();
						} else {
							targetPath = targetPath + "\\" + aFtpFile.getName();
						}

						doGetDirectory(aFtpFile);
					}
				} else if (aFtpFile.isFile()) {
					sourcePath = ftppath;
					targetPath = localpath;
					doGetFile(aFtpFile);
				}
			}
		}
	}
	
	private void doGetDirectory(FTPFile aFtpDir) throws Exception {
		String tempSourcePath = sourcePath;
		String tempTargetPath = targetPath;

		File aDir = new File(targetPath);
		if (!aDir.exists()) {
			addReply("mkdir " + targetPath);
			aDir.mkdir();
		}

		FTPFile[] ftpFiles = ftp.listFiles(sourcePath);
		if (ftpFiles != null) {
			for (int i = 0; i < ftpFiles.length; i++) {
				FTPFile aFtpFile = (FTPFile) ftpFiles[i];
				if (aFtpFile.getName().startsWith(".")) {
					continue;
				}
				if (aFtpFile.isDirectory()) {
					if (sourcePath.endsWith("/")) {
						sourcePath = sourcePath + aFtpFile.getName();
					} else {
						sourcePath = sourcePath + "/" + aFtpFile.getName();
					}

					if (targetPath.endsWith("\\")) {
						targetPath = targetPath + aFtpFile.getName();
					} else {
						targetPath = targetPath + "\\" + aFtpFile.getName();
					}
					doGetDirectory(aFtpFile);
				} else if (aFtpFile.isFile()) {
					sourcePath = tempSourcePath;
					targetPath = tempTargetPath;
					doGetFile(aFtpFile);
				}
			}
		}
	}
	
	private void doGetFile(FTPFile aFtpFile) throws Exception {
		if (!FTPFileFilter.accept(aFtpFile.getName(), filter)) {
			return;
		}

		String remote = "";
		if (sourcePath.endsWith("/")) {
			remote = sourcePath + aFtpFile.getName();
		} else {
			remote = sourcePath + "/" + aFtpFile.getName();
		}

		String local = "";
		if (targetPath.endsWith("\\")) {
			local = targetPath + aFtpFile.getName();
		} else {
			local = targetPath + "\\" + aFtpFile.getName();
		}
		OutputStream localOutputStream = new FileOutputStream(local);
		
		addReply(remote + " to " + local);
		ftp.retrieveFile(remote, localOutputStream);
		addReply(ftp);

		localOutputStream.close();
		

		File file = new File(local);
		file.setLastModified(aFtpFile.getTimestamp().getTimeInMillis());

		if ("Y".equals(deletefile)) {
			addReply("delete " + remote);
			ftp.deleteFile(remote);
		}
	}
	
	public String getLastReplyString() {
		return lastReplyString;
	}
	
	private void addReply(FTPClient ftp) throws Exception {
		if (ftp.getReplyString().endsWith("\r\n")) {
			lastReplyString = ftp.getReplyString().substring(0, ftp.getReplyString().length() - 2);
		} else {
			lastReplyString = ftp.getReplyString();
		}
		if (ftp.getReplyCode() >= 300) {
			throw new FTPException(lastReplyString);
		} else {
			addReply(lastReplyString);
		}
	}

	private void addReply(String msg) {
		replyList.add(msg);
	}
	
	public List getReplyList() {
		return replyList;
	}
	
	public String getFtpip() {
		return ftpip;
	}

	public void setFtpip(String ftpip) {
		this.ftpip = ftpip;
	}

	public String getFtpmethod() {
		return ftpmethod;
	}

	public void setFtpmethod(String ftpmethod) {
		this.ftpmethod = ftpmethod;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getConnectionmode() {
		return connectionmode;
	}

	public void setConnectionmode(String connectionmode) {
		this.connectionmode = connectionmode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTransfertype() {
		return transfertype;
	}

	public void setTransfertype(String transfertype) {
		this.transfertype = transfertype;
	}

	public String getFtppath() {
		return ftppath;
	}

	public void setFtppath(String ftppath) {
		this.ftppath = ftppath;
	}

	public String getLocalpath() {
		return localpath;
	}

	public void setLocalpath(String localpath) {
		this.localpath = localpath;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getDeletefile() {
		return deletefile;
	}

	public void setDeletefile(String deletefile) {
		this.deletefile = deletefile;
	}

	public String getSubdir() {
		return subdir;
	}

	public void setSubdir(String subdir) {
		this.subdir = subdir;
	}

	public String getZok() {
		return zok;
	}

	public void setZok(String zok) {
		this.zok = zok;
	}
}
