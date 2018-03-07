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
	 * �N���X�ন�K�X
	 * @param cleartext
	 * @return
	 */
	public String encrypt(String cleartext) {
		return CryptoUtil.encrypt(cleartext);
	}

	/**
	 * �N�K�X�ন���X
	 * @param ciphertext
	 * @return
	 */
	public String decrypt(String ciphertext) {
		return CryptoUtil.decryptSilent(ciphertext);
	}
}