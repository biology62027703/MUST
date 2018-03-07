package com.sertek.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class md5 {
	
	public static String getMD5Hash (String filepath) throws IOException {
		
		File file = new File(filepath);
		
		if(!file.exists())
		throw new IOException("The file is not exist."); 
		
		FileInputStream fis = null;
		DigestInputStream dis = null;
		byte[] buff = new byte[1024];
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			dis = new DigestInputStream(fis, md);
			
			while(dis.read(buff) != -1);
			
			byte[] md5Digests = md.digest();
			return byteArray2Hex(md5Digests);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			buff = null;
			if(fis != null) fis.close();
			if(dis != null) dis.close();
		}
		return null;
	}
	
	public static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}
}