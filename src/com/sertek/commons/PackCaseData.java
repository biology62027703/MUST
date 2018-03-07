package com.sertek.commons;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sertek.file.FileCopy;
import com.sertek.form.SOLHelper;
import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.sys.Project;
import com.sertek.sys.StaticObj;
import com.sertek.table.C60_ATT;
import com.sertek.table.ENSET;
import com.sertek.util.AZip;
import com.sertek.util.CheckObject;
import com.sertek.util.CryptoFile;
import com.sertek.util.CryptoUtil;
import com.sertek.util.util_File;
import com.sertek.util.util_date;

public class PackCaseData  {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private CheckObject check = new CheckObject();
	
	private util_date ud = new util_date();

	private SqlDBUtility sqlDBUtility = null;
	
	private HashMap param = new HashMap();

	public PackCaseData(SqlDBUtility sqlDBUtility, HashMap param) {
		this.sqlDBUtility = sqlDBUtility;
		this.param = param;
		run();
	}

	public void run() {
		
		try {
			// 打包資料
			String tempPath = check.checkNull(param.get("tempPath"), "").toString();
			String zipFilenm = check.checkNull(param.get("zipFilenm"), "").toString();
			String encZipFilenm = check.checkNull(param.get("encZipFilenm"), "").toString();
			
			// 準備暫存目錄
			FileCopy fc = new FileCopy();
			fc.rmDir(tempPath);
			fc.mkdirs(tempPath);
			fc.delete(zipFilenm);
			
			// create C60_ATT.zip
			createAttZip(tempPath);
			
			// create sndSO2C_SQL.txt
			createSndFile(tempPath);
			
			// 壓縮
			AZip zip = new AZip(false);
			zip.createZip(zipFilenm);
			zip.addFile(tempPath);
			zip.writeZip();
			
			// 加密壓縮檔
			CryptoFile.encryptFile(zipFilenm, encZipFilenm);
			
			fc.rmDir(tempPath);
			
			// copy檔案
			String bakDir = Project.getCaseDataDir();
			fc.copyFiles(zipFilenm, bakDir + check.checkNull(param.get("doc_no"), "") + ".zip");
			fc.copyFiles(encZipFilenm, bakDir + check.checkNull(param.get("doc_no"), "") + ".ok");
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
	
	private void createAttZip(String tempPath) throws Exception {
		String attZipnm = "C60_ATT";
		String attPath = tempPath + attZipnm + File.separator;
		FileCopy fc = new FileCopy();
		fc.mkdirs(attPath);

		List attList = this.getAttList();
		for (int i = 0; i < attList.size(); i++) {
			C60_ATT att = (C60_ATT) attList.get(i);
			fc.copyFiles(att.getS_filenm(), attPath + att.getS_file());
		}

		AZip zip = new AZip(false);
		zip.createZip(tempPath + attZipnm + ".zip");
		zip.addFile(attPath);
		zip.writeZip();

		fc.rmDir(attPath);
	}
	
	private List getAttList() {
		HashMap attKey = new HashMap();
		attKey.put("crtid", check.checkNull(param.get("crtid"), ""));
		attKey.put("sys", check.checkNull(param.get("sys"), ""));
		attKey.put("c_no", check.checkNull(param.get("c_no"), ""));
		attKey.put("s_seq", check.checkNull(param.get("s_seq"), ""));
		attKey.put("orderByKey", "s_updt, s_uptm");
		List attList = sqlDBUtility.queryForList("C60_ATT.queryByKey", attKey);
		return attList;
	}
	
	private void createSndFile(String tempPath) throws Exception {
		String sndFilenm = tempPath + "sndSO2C_SQL.txt";
		util_File sndFile = null;
		try {
			sndFile = new util_File(sndFilenm, "w");
			writeC60_SQL(sndFile);
			writeC61_SQL(sndFile);
			writeC6A_SQL(sndFile);
			writeC6B_SQL(sndFile);
			writeC60_ATT_SQL(sndFile);
			writeC6M(sndFile);
		} catch (Exception e) {
			throw e;
		} finally {
			sndFile.close();
		}
	}
	
	private void writeC60_SQL(util_File sndFile) throws Exception {
		String tableName = "C60";
		String columnName = "OLNO VARCHAR2(1),CRTID VARCHAR2(3),SYS VARCHAR2(1),S_KD VARCHAR2(10),DOC_NO VARCHAR2(18),ODOC_NO VARCHAR2(18),RCVDT VARCHAR2(7),RCVTM VARCHAR2(6),USRNM VARCHAR2(40),IDAPTNM VARCHAR2(10),IDNM VARCHAR2(40),CRT_APV VARCHAR2(1),DLV_DTM VARCHAR2(13),FMCRTID VARCHAR2(3),FMSYS VARCHAR2(1),FMSTATUS VARCHAR2(10),FMSDDTM VARCHAR2(13),FMDOC_NO VARCHAR2(18),FMCRMYY VARCHAR2(3),FMCRMID VARCHAR2(20),FMCRMNO VARCHAR2(6)";
		writeSndSQL(sndFile, tableName, columnName);
	}
	
	private void writeC61_SQL(util_File sndFile) throws Exception {
		String tableName = "C61";
		String columnName = "CRTID VARCHAR2(3),SYS VARCHAR2(1),DOC_NO VARCHAR2(18),SYSID VARCHAR2(6),IDNM VARCHAR2(24),CLNM VARCHAR2(40),CLNM_E VARCHAR2(40),IDNO VARCHAR2(15),SEX VARCHAR2(4),BIRDT VARCHAR2(7),EMAIL VARCHAR2(40),PFS VARCHAR2(20),TITLE VARCHAR2(30),TITLE_E VARCHAR2(30),TEL VARCHAR2(30),FAX VARCHAR2(15),ZIP VARCHAR2(5),ADDR VARCHAR2(85),ADDR_E VARCHAR2(60),ISQRY VARCHAR2(1),USRID VARCHAR2(20)";
		writeSndSQL(sndFile, tableName, columnName);
	}
	
	private void writeC6A_SQL(util_File sndFile) throws Exception {
		String tableName = "C6A";
		String columnName = "CRTID VARCHAR2(3),SYS VARCHAR2(1),DOC_NO VARCHAR2(18),SYSID VARCHAR2(6),AGSYSID VARCHAR2(6),CLNM VARCHAR2(40),ZIP VARCHAR2(5),ADDR VARCHAR2(85)";
		writeSndSQL(sndFile, tableName, columnName);
	}
	
	private void writeC6B_SQL(util_File sndFile) throws Exception {
		String tableName = "C6B";
		String columnName = "CRTID VARCHAR2(3),SYS VARCHAR2(1),DOC_NO VARCHAR2(18),SYSID VARCHAR2(6),AGSYSID VARCHAR2(6),IDNM VARCHAR2(24),CLNM VARCHAR2(40),CLNM_E VARCHAR2(40),IDNO VARCHAR2(15),SEX VARCHAR2(4),BIRDT VARCHAR2(7),EMAIL VARCHAR2(40),PFS VARCHAR2(20),TITLE VARCHAR2(30),TITLE_E VARCHAR2(30),TEL VARCHAR2(30),FAX VARCHAR2(15),ZIP VARCHAR2(5),ADDR VARCHAR2(85),ADDR_E VARCHAR2(60),ISQRY VARCHAR2(1),USRID VARCHAR2(20)";
		writeSndSQL(sndFile, tableName, columnName);
	}
	
	private void writeC60_ATT_SQL(util_File sndFile) throws Exception {
		String tableName = "C60_ATT";
		String columnName = "CRTID VARCHAR2(3),SYS VARCHAR2(1),DOC_NO VARCHAR2(18),S_SEQ VARCHAR2(5),S_FTYPE VARCHAR2(1),S_FILESEQ VARCHAR2(3),S_FILE VARCHAR2(260),S_FILEDS VARCHAR2(200),S_FILESZ VARCHAR2(20),S_UPDT VARCHAR2(7),S_UPTM VARCHAR2(4)";		
		writeSndSQL(sndFile, tableName, columnName);
	}
	
	private void writeC6M(util_File sndFile) throws Exception {
		String tableName = "C6M";
		String columnName = "CRTID VARCHAR2(3),SYS VARCHAR2(1),DOC_NO VARCHAR2(18),ORG_KD VARCHAR2(1),ORG_ID VARCHAR2(10),ORG_NM VARCHAR2(100),ORG_DATE VARCHAR2(7),ORG_NO VARCHAR2(64)";		
		writeSndSQL(sndFile, tableName, columnName);
	}
	
	private void writeSndSQL(util_File sndFile, String tableName, String columnName) throws Exception {
		HashMap encMap = StaticObj.getEnsetMap(tableName);
		String[] columns = getColumns(columnName);
		
		HashMap keyMap = new HashMap();
		keyMap.put("tblnm", tableName);
		keyMap.put("crtid", check.checkNull(param.get("crtid"), ""));
		keyMap.put("sys", check.checkNull(param.get("sys"), ""));
		keyMap.put("c_no", check.checkNull(param.get("c_no"), ""));
			
		
		List mapList = sqlDBUtility.queryForList( 
				tableName.equals("C60") ? "FSO1A04.SND_C60_Query"  : "FSO1A04.SND_Query", 
				keyMap
			);
		
		sndFile.writeln("TABLE=" + tableName);
		sndFile.writeln("COLUMN_NAME=" + columnName);
		
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < mapList.size(); i++) {
			HashMap map = (HashMap) mapList.get(i);
			System.out.println(map);
			str.setLength(0);
			str.append("$");
			for (int c = 0; c < columns.length; c++) {
				if ("OLNO".equals(columns[c])) {
					if ("I".equals(check.checkNull(map.get("SYSKD"), ""))) {
						str.append("0"); // 智財
					} else if ("T".equals(check.checkNull(map.get("SYSKD"), ""))) {
						str.append("1"); // 稅行
					} else {
						str.append("3"); // 稅行
					}
				} else if ("S_KD".equals(columns[c])) {					
					str.append(StaticObj.getSuitkd(check.checkNull(map.get(columns[c]), "").toString()));
				} else if ("USRNM".equals(columns[c])) {
					str.append(check.checkNull(param.get("usrnm"), ""));
				} else if ("DOC_NO".equals(columns[c])) {
					str.append(check.checkNull(param.get("doc_no"), ""));
				} else if ("SEX".equals(columns[c])) {
					str.append(SOLHelper.getSexnm(check.checkNull(map.get(columns[c]), "").toString()));
				} else if ("BIRDT".equals(columns[c])) {
					String birdt = this.getValue(encMap, map, "BIRDT");
					str.append(ud.parseWDate2C(birdt));
				} else if ("ADDR".equals(columns[c])) {
					String city = this.getValue(encMap, map, "CITY");
					String area = this.getValue(encMap, map, "AREA");
					String addr = this.getValue(encMap, map, "ADDR");					
					str.append(city + area + addr);
				} else {
					str.append(this.getValue(encMap, map, columns[c]));
				}
				
				if (c < columns.length - 1) {
					str.append("|,|");
				}
			}
			str.append("$|$");

			sndFile.writeln(str.toString());
		}
		sndFile.writeln();
	}
	
	private String getValue(HashMap encMap, HashMap valueMap, String key) {
		String value = check.checkNull(valueMap.get(key), "").toString();
		if (encMap.containsKey(key)) {
			value = CryptoUtil.decryptSilent(value);
		}
		return value;
	}
	
	private String[] getColumns(String columnName) throws Exception {
		String[] columns = columnName.split(",");
		for (int i = 0; i < columns.length; i++) {
			int pos = columns[i].indexOf(" ");
			if (pos > 0) {
				columns[i] = columns[i].substring(0, pos);
			}
		}
		return columns;
	}
}