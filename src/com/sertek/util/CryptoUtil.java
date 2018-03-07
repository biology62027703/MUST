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

public class CryptoUtil {
	
	private static Logger logger = Logger.getLogger(CryptoUtil.class);
	
	private final static String algorithm = "DES";
	
	private final static String transformation = "DES/ECB/PKCS5Padding";
	
	private final static String keystring = "recA!123";
	
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
		}
		return cipher;
	}
	
	public static void copyFile(String fileIn, String fileOut) throws Exception {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			byte[] buffer = new byte[bufferSize];

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
			fis.close();
			fos.close();
		}
	}
	
	public static void encryptFile(String fileIn, String fileOut) throws Exception {
		FileInputStream fis = null;
		CipherOutputStream cos = null;

		try {
			Cipher cipher = createCipher(Cipher.ENCRYPT_MODE);

			byte[] buffer = new byte[bufferSize];

			fis = new FileInputStream(fileIn);
			cos = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(fileOut)), cipher);

			printFileSize(fis.available());

			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				cos.write(buffer, 0, length);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			fis.close();
			cos.close();
		}
	}
	
	public static void decryptFile(String fileIn, String fileOut) throws Exception {
		FileInputStream fis = null;
		CipherOutputStream cos = null;
		try {
			Cipher cipher = createCipher(Cipher.DECRYPT_MODE);

			byte[] buffer = new byte[bufferSize];

			fis = new FileInputStream(fileIn);
			cos = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(fileOut)), cipher);

			printFileSize(fis.available());

			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				cos.write(buffer, 0, length);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			fis.close();
			cos.close();
		}
	}
	
	/**
	 * 將明碼轉成密碼
	 * @param cleartext
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String cleartext) {
		if ("".equals(cleartext)) {
			return "";
		}
		
		try {
			Cipher cipher = createCipher(Cipher.ENCRYPT_MODE);

			byte[] ciphertext = cipher.doFinal(cleartext.getBytes(charset));

			return byte2hex(ciphertext);
		} catch (Exception e) {
			logger.error("cleartext = " + cleartext);
			logger.error("CryptoUtil.encrypt Error : ", e);
			return cleartext;
		}
	}
	
	/**
	 * 將密碼轉成明碼
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String ciphertext) {
		if ("".equals(ciphertext)) {
			return "";
		}
		try {
			Cipher cipher = createCipher(Cipher.DECRYPT_MODE);
			
			byte[] cleartext = cipher.doFinal(hex2byte(ciphertext));
			
			return new String(cleartext, charset);
		} catch (IllegalArgumentException iae){
			return ciphertext;
		} catch (Exception e) {
			logger.error("ciphertext = " + ciphertext);
			logger.error("CryptoUtil.decrypt Error", e);
			return ciphertext;
		}
	}
	
	/**
	 * 將密碼轉成明碼(ignored exception)
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
	public static String decryptSilent(String ciphertext) {
		if ("".equals(ciphertext)) {
			return "";
		}
		try {
			Cipher cipher = createCipher(Cipher.DECRYPT_MODE);
			
			byte[] cleartext = cipher.doFinal(hex2byte(ciphertext));
			
			return new String(cleartext, charset);
		} catch (IllegalArgumentException iae){
			return ciphertext;
		} catch (Exception e) {
			//logger.error("ciphertext = " + ciphertext);
			//logger.error("CryptoUtil.decrypt Error", e);
			return ciphertext;
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

			String cleartext = "宏碁管理者";
			System.out.println(cleartext + " = " + CryptoUtil.encrypt(cleartext));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
