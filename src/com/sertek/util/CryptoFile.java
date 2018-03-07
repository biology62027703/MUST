package com.sertek.util;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class CryptoFile {
	
	private static Logger logger = Logger.getLogger(CryptoFile.class);
	
	private final static String algorithm = "AES";
	
	private final static String transformation = "AES/ECB/PKCS5Padding";
	
	private final static String keystring = "3141592653589793"; // AES要16個byte
	
	private final static int bufferSize = 1024 * 4;
	
	private final static String charset = "MS950";
	
	public static boolean checkKeystring(String inputKeystring) {
		if (inputKeystring.equals(keystring)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * dynamically register provider
	 * 
	 * @throws Exception
	 */
	private static void addProvider() throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}
	
	private static Cipher createCipher(int mode) {
		Cipher cipher = null;
		
		try {
			addProvider();

			SecretKey key = new SecretKeySpec(keystring.getBytes(charset), algorithm);
			
			cipher = Cipher.getInstance(transformation);
			
			cipher.init(mode, key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
		
		return cipher;
	}
	
	public static void copyFile(String fileIn, String fileOut) throws Exception {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		byte[] buffer;
		
		try {
			buffer = new byte[bufferSize];

			fis = new FileInputStream(fileIn);
			fos = new FileOutputStream(fileOut);

			printFileSize(fis.available());

			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, length);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				buffer = null;
				fis.close();
				fos.close();
			} catch (Exception ignored) {
			}
		}
	}
	
	/**
	 * 檔案加密
	 * @param fileIn String
	 * @param fileOut String
	 * @throws Exception
	 */
	public static void encryptFile(String fileIn, String fileOut) throws Exception {
		encryptFile(new FileInputStream(fileIn), fileOut);
	}
	
	/**
	 * 檔案加密
	 * @param fis InputStream
	 * @param fileOut String
	 * @throws Exception
	 */
	public static void encryptFile(InputStream fis, String fileOut) throws Exception {
		CipherOutputStream cos = null;
		Cipher cipher;
		
		try {
			cipher = createCipher(Cipher.ENCRYPT_MODE);

			byte[] buffer = new byte[bufferSize];

			cos = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(fileOut)), cipher);

			printFileSize(fis.available());

			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				cos.write(buffer, 0, length);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				cipher = null;
				fis.close();
				cos.close();
			} catch (Exception ignored) {
			}
		}
	}
	
	/**
	 * 檔案解密
	 * @param fileIn String
	 * @param fileOut String
	 * @throws Exception
	 */
	public static void decryptFile(String fileIn, String fileOut) throws Exception {
		decryptFile(new FileInputStream(fileIn), fileOut);
	}
	
	/**
	 * 檔案解密
	 * @param fis InputStream
	 * @param fileOut String
	 * @throws Exception
	 */
	public static void decryptFile(InputStream fis, String fileOut) throws Exception {
		CipherOutputStream cos = null;
		Cipher cipher ;
		
		try {
			cipher = createCipher(Cipher.DECRYPT_MODE);

			byte[] buffer = new byte[bufferSize];

			cos = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(fileOut)), cipher);

			printFileSize(fis.available());

			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				cos.write(buffer, 0, length);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				cipher = null; 
				fis.close();
				cos.close();
			} catch (Exception ignored) {
			}
		}
	}
	
	/**
	 * 將明碼轉成密碼
	 * @param cleartext
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String cleartext) throws Exception {
		Cipher cipher;
		
		try {
			cipher = createCipher(Cipher.ENCRYPT_MODE);

			byte[] ciphertext = cipher.doFinal(cleartext.getBytes(charset));

			return byte2hex(ciphertext);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("cleartext = " + cleartext);
			logger.error("CryptoUtil.encrypt Error : ", e);
			return cleartext;
		}finally{
			cipher = null;
		}
	}
	
	/**
	 * 將密碼轉成明碼
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String ciphertext) throws Exception {
		Cipher cipher;
		
		try {
			cipher = createCipher(Cipher.DECRYPT_MODE);
			
			byte[] cleartext = cipher.doFinal(hex2byte(ciphertext));
			
			return new String(cleartext, charset);
		} catch (IllegalArgumentException iae){
			return ciphertext;
		} catch (Exception e) {
			logger.error("ciphertext = " + ciphertext);
			logger.error("CryptoUtil.decrypt Error", e);
			return ciphertext;
		}finally{
			cipher = null;
		}
	}
	
	/**
	 * Convert hex string to byte array
	 * @param strhex
	 * @return
	 * @throws Exception
	 */
	private static byte[] hex2byte(String strhex) throws Exception {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}

	/**
	 * Convert byte array to hex string
	 * @param b
	 * @return
	 * @throws Exception
	 */
	private static String byte2hex(byte[] b) throws Exception {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	
	private static void printFileSize(int bytes) {
		String sizeMB = new BigDecimal(bytes).divide(new BigDecimal(1024 * 1024), 2, BigDecimal.ROUND_HALF_UP).toString();
		logger.info("file size : " + sizeMB + " MB");
	}
	
	public static String getMD5Checksum(InputStream fis) throws Exception {
		byte[] b = createChecksum(fis);
		String result = "";

		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
	
	// see this How-to for a faster way to convert
	// a byte array to a HEX string
	public static String getMD5Checksum(String filename) throws Exception {
		byte[] b = createChecksum(new FileInputStream(filename));
		String result = "";

		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
	
	private static byte[] createChecksum(InputStream fis) throws Exception {
		//InputStream fis = new FileInputStream(filename);

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		return complete.digest();
	}
	
	public static void main(String[] args) {
		try {
			/*
			String fileIn = "D:\\nw\\so\\IPC\\10303\\1030318172858000130";
			String fileOut = "D:\\nw\\so\\IPC\\10303\\SafeSync for Enterprise 2.0.pptx";
			long start = 0;
			long end = 0;
			long elapse = 0;
			
			start = System.currentTimeMillis();
			System.out.println("開始\t:->" + new java.sql.Timestamp(start));
			
			CryptoFile.decryptFile(fileIn, fileOut);
			
			end = System.currentTimeMillis();
			System.out.println("結束\t:->" + new java.sql.Timestamp(end));
			elapse = end - start;
			System.out.println("耗時 " + elapse + " 毫秒\n     " + elapse / 1000 + " 秒");
			*/
			
			String str = "1234567890123456";
			System.out.println("DES = " + CryptoUtil.encrypt(str));
			System.out.println("AES = " + CryptoFile.encrypt(str));
			
			
			/*
			String fileIn = "";
			String fileOut = "";
			long start = 0;
			long end = 0;
			long elapse = 0;
			*/
			
			/*
			String clearText = "一二三四五六七八九十";
			
			System.out.println("明文\t:->" + clearText + "<-");
			start = System.currentTimeMillis();
			//System.out.println("開始\t:->" + new java.sql.Timestamp(start));
			
			String cipherText = CryptoUtil.encrypt(clearText);
			
			end = System.currentTimeMillis();
			System.out.println("密文\t:->" + cipherText + "<-");
			//System.out.println("結束\t:->" + new java.sql.Timestamp(end));
			elapse = end - start;
			System.out.println("耗時 " + elapse + " 毫秒\n     " + elapse / 1000 + " 秒");
			*/
			
			/*
			System.out.println("加密檔案");
			fileIn = "C:\\HERO.mp3";
			fileOut = "C:\\HERO_encode.rec";
			start = System.currentTimeMillis();
			System.out.println("開始\t:->" + new java.sql.Timestamp(start));
			
			CryptoUtil.encryptFile(fileIn, fileOut);
			
			end = System.currentTimeMillis();
			System.out.println("結束\t:->" + new java.sql.Timestamp(end));
			elapse = end - start;
			System.out.println("耗時 " + elapse + " 毫秒\n     " + elapse / 1000 + " 秒");
			
			
			fileIn = "C:\\PerfactStranger.mp3";
			fileOut = "C:\\PerfactStranger_encode.rec";
			start = System.currentTimeMillis();
			System.out.println("開始\t:->" + new java.sql.Timestamp(start));
			
			CryptoUtil.encryptFile(fileIn, fileOut);
			
			end = System.currentTimeMillis();
			System.out.println("結束\t:->" + new java.sql.Timestamp(end));
			elapse = end - start;
			System.out.println("耗時 " + elapse + " 毫秒\n     " + elapse / 1000 + " 秒");
			*/
			
			/*
			fileIn = "C:\\HERO_encode.rec";
			fileOut = "C:\\HERO_decode.mp3";
			System.out.println("解密檔案:->" + fileIn + "<-");
			start = System.currentTimeMillis();
			//System.out.println("開始\t:->" + new java.sql.Timestamp(start));
			CryptoUtil.decryptFile(fileIn, fileOut);
			end = System.currentTimeMillis();
			//System.out.println("結束\t:->" + new java.sql.Timestamp(end));
			elapse = end - start;
			System.out.println("耗時 " + elapse + " 毫秒\n     " + elapse / 1000 + " 秒");
			
			fileIn = "C:\\PerfactStranger_encode.rec";
			fileOut = "C:\\PerfactStranger_decode.mp3";
			System.out.println("解密檔案:->" + fileIn + "<-");
			start = System.currentTimeMillis();
			//System.out.println("開始\t:->" + new java.sql.Timestamp(start));
			CryptoUtil.decryptFile(fileIn, fileOut);
			end = System.currentTimeMillis();
			//System.out.println("結束\t:->" + new java.sql.Timestamp(end));
			elapse = end - start;
			System.out.println("耗時 " + elapse + " 毫秒\n     " + elapse / 1000 + " 秒");
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
