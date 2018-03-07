package com.sertek.table;

import com.sertek.util.CryptoUtil;

public class BaseTable {
	
	private String rowid = "";

	/**
	 * @return the rowid
	 */
	public String getRowid() {
		return rowid;
	}

	/**
	 * @param rowid the rowid to set
	 */
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	/**
	 * 將明碼轉成密碼
	 * @param cleartext
	 * @return
	 */
	public String encrypt(String cleartext) {
		return CryptoUtil.encrypt(cleartext);
	}

	/**
	 * 將密碼轉成明碼
	 * @param ciphertext
	 * @return
	 */
	public String decrypt(String ciphertext) {
		return CryptoUtil.decryptSilent(ciphertext);
	}
}