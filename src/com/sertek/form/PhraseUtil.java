package com.sertek.form;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.sys.Project;
import com.sertek.util.CheckObject;

public class PhraseUtil {
	
	private static Logger logger = Logger.getLogger(PhraseUtil.class);
	
	private static CheckObject check = new CheckObject();
	
	public static void createJSFile(String code, String name, SqlDBUtility sqlDBUtility, HashMap param) {
		writeFile(Project.getScriptDir() + name + ".js", PhraseUtil.getCodeArray(code, name, sqlDBUtility, param));
	}

	private static void writeFile(String filenm, String content) {
		try {
			logger.info("generate file " + filenm);
			File file = new File(filenm);
			if (file.exists()) {
				file.delete();
			}

			BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filenm), "UTF-8"));
			bufWriter.write(content);

			bufWriter.close();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
	
	public static String getCodeArray(String code, String name, SqlDBUtility sqlDBUtility, HashMap param) {
		StringBuffer js = new StringBuffer();
		try {
			Object[] template = getCodeTemplate(code, name, param == null ? new HashMap() : param);

			/**
			 var JT = [{"SYS":"H","CDDS2":"�D��"},
		 				{"SYS":"V","CDDS2":"����"},
		 				{"SYS":"I","CDDS2":"�֦~"}];
		 	*/
			if (!template[0].equals("")) {
				List list = sqlDBUtility.doSqlSelect(template[0].toString());

				js.append("var _code_").append(name).append(" = [");

				String[] key = (String[]) template[1];
				for (int i = 0; i < list.size(); i++) {
					HashMap map = (HashMap) list.get(i);
					js.append("{");
					for (int j = 0; j < key.length; j++) {
						js.append("\"" + key[j] + "\"");
						js.append(":");
						js.append("\"" + map.get(key[j]) + "\"");
						if (j < key.length - 1) {
							js.append(",");
						}
					}
					js.append("}");
					if (i < list.size() - 1) {
						js.append(",\n");
					}
				}
				
				js.append("];\n");
			}
		} catch (Exception e) {
			logger.error(e, e);
		}

		return js.toString();
	}
	
	public static String getIdnmCodeArray(SqlDBUtility sqlDBUtility, String name, String argnm) {
		return getS08CodeArray(sqlDBUtility, name, "FSO1A02", argnm, "IDNM");
	}
	
	public static String getS08CodeArray(SqlDBUtility sqlDBUtility, String name, String prgid, String argnm, String fieldname) {
		StringBuffer js = new StringBuffer();
		try {
			String code = "1";
			//String name = "IDNM";
			
			HashMap param = new HashMap();
			param.put("PRGID", prgid);
			//param.put("ARGNM", "�����H�ٿ�");
			param.put("ARGNM", argnm);
			Object[] template = getCodeTemplate(code, name, param);

			if (!template[0].equals("")) {
				List list = sqlDBUtility.doSqlSelect(template[0].toString());
				String argvl = "";
				if (list.size() > 0) {
					HashMap map = (HashMap) list.get(0);
					String[] key = (String[]) template[1];
					argvl = check.checkNull(map.get(key[0]), "").toString();
				}
				String[] idnms = argvl.split(",");

				js.append("var _code_").append(name).append(" = [");

				if (idnms != null) {
					for (int i = 0; i < idnms.length; i++) {
						js.append("{");
						js.append("\""+fieldname+"\"");
						js.append(":");
						js.append("\"" + idnms[i] + "\"");
						js.append("}");
						
						if (i < idnms.length - 1) {
							js.append(",\n");
						}
					}
				}
				
				js.append("];\n");
			}
		} catch (Exception e) {
			logger.error(e, e);
		}

		return js.toString();
	}
	
	public static String getCityCodeArray(SqlDBUtility sqlDBUtility) {
		return PhraseUtil.getCodeArray("2", "CITY", sqlDBUtility, null);
	}

	public static String getCityAreaCodeArray(SqlDBUtility sqlDBUtility) {
		return PhraseUtil.getCodeArray("3", "CITYAREA", sqlDBUtility, null);
	}
	
	public static String getS61CodeArray(SqlDBUtility sqlDBUtility, HashMap param) {
		return PhraseUtil.getCodeArray("4", "S61", sqlDBUtility, param);
	}
	
	public static String getSuitkdCodeArray(SqlDBUtility sqlDBUtility) {
		return PhraseUtil.getCodeArray("5", "SUITKD", sqlDBUtility, null);
	}
	
	public static String getS_evdCodeArray(SqlDBUtility sqlDBUtility) {
		return PhraseUtil.getCodeArray("6", "S_EVD", sqlDBUtility, null);
	}

	private static Object[] getCodeTemplate(String code, String name, HashMap param) throws Exception {
		Object[] template = new Object[7];
		
		switch (Integer.parseInt(code)) {
		case 1: // phrase
			template[0] = "select argvl from so..s08 where prgid = '" + check.checkNull(param.get("PRGID"), "") + "' and argnm = '" + check.checkNull(param.get("ARGNM"), "") + "'";
			template[1] = new String[] { "ARGVL" }; // result HashMap �� key �`�N�j�p�g
			template[2] = "IDNM";
			template[3] = "1"; // ������
			template[4] = "1"; // �}����ܪ����
			template[5] = "1"; // �^�ǭȪ����
			template[6] = "1"; // �j�M�Ȫ����
			break;
		case 2: // CITY
			template[0] = "select min(zipcode) as zipcode, city from so..cityarea group by city order by zipcode";
			template[1] = new String[] { "ZIPCODE", "CITY" }; // result HashMap �� key �`�N�j�p�g
			template[2] = "CITY";
			template[3] = "2"; // ������
			template[4] = "1,2"; // �}����ܪ����
			template[5] = "1,2"; // �^�ǭȪ����
			template[6] = "1,2"; // �j�M�Ȫ����
			break;
		case 3: // CITYAREA
			template[0] = "select zipcode, city, area from so..cityarea order by zipcode";
			template[1] = new String[] { "ZIPCODE", "CITY", "AREA" }; // result HashMap �� key �`�N�j�p�g
			template[2] = "CITYAREA";
			template[3] = "3"; // ������
			template[4] = "1,2,3"; // �}����ܪ����
			template[5] = "1,2,3"; // �^�ǭȪ����
			template[6] = "1"; // �j�M�Ȫ����
			break;
		case 4: // S61
			template[0] = "select scd, clnm from bos..s61 where syskd = '" + check.checkNull(param.get("SYSKD"), "") + "' order by scd";
			template[1] = new String[] { "SCD", "CLNM" }; // result HashMap �� key �`�N�j�p�g
			template[2] = "S61";
			template[3] = "2"; // ������
			template[4] = "1,2"; // �}����ܪ����
			template[5] = "1,2"; // �^�ǭȪ����
			template[6] = "1"; // �j�M�Ȫ����
			break;
		case 5: // SUITKD
			template[0] = "select s_kd, suit_nm from so..suitkd where s_kd like 'A%' and s_kd <> 'AZ' and s_kd <> 'AY' order by suit_id";
			template[1] = new String[] { "S_KD", "SUIT_NM" }; // result HashMap �� key �`�N�j�p�g
			template[2] = "SUITKD";
			template[3] = "2"; // ������
			template[4] = "1,2"; // �}����ܪ����
			template[5] = "1,2"; // �^�ǭȪ����
			template[6] = "1,2"; // �j�M�Ȫ����
			break;
		}
		
		return template;
	}
}
