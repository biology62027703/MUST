package com.sertek.util;

public class DatabaseUtils {
	private static String clientCharset = "UTF-8";
	// private static String dbCharset = "ISO8859_1";
	private static String dbCharset = "UTF-8";
	private static boolean _charset = true; // to see whether char need transfer

	/**
	 * <pre><br>
	 * 	   Method : transCharset4Client2db (2003.09.19 Peric Add)
	 * 	   �Ѽ�:1.String sSourceStr      :�ǤJ�n��X���r��
	 * <br>
	 * 	   �Ǧ^: String ���s�s�X�L���r��
	 * <br>
	 * 	   ����:�r����X
	 * <br>
	 *</pre>
	 */
	public static String transCharset4Client2db(String sSourceStr) {
		String sTargetStr = "";
		sSourceStr = (sSourceStr == null) ? "" : sSourceStr;
		if (_charset)
			sTargetStr = transCharset(sSourceStr, clientCharset, dbCharset);
		else
			sTargetStr = sSourceStr;
		sTargetStr = (sTargetStr == null) ? "" : sTargetStr;
		return sTargetStr;

	}// transCharset4Client2db()

	public static String transCharset4Client2db(Object sSourceStr) {
		String sTargetStr = "";
		sSourceStr = (sSourceStr == null) ? "" : sSourceStr;
		if (_charset)
			sTargetStr = transCharset((String) sSourceStr, clientCharset, dbCharset);
		else
			sTargetStr = (String) sSourceStr;
		sTargetStr = (sTargetStr == null) ? "" : sTargetStr;
		return sTargetStr;

	}// transCharset4Client2db()

	public static void transCharset4Client2db(Object[] obj) {

		if (obj != null) {
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] instanceof java.lang.String) {
					obj[i] = transCharset4Client2db((String) obj[i]);
				} else {
					obj[i] = obj[i];
				}
			}
		}

	}// transCharset4Client2db()

	/**
	 * <pre><br>
	 * 	   Method : transCharset4db2Client (2003.09.19 Peric Add)
	 * 	   �Ѽ�:1.String sSourceStr      :�ǤJ�n��X���r��
	 * <br>
	 * 	   �Ǧ^: String ���s�s�X�L���r��
	 * <br>
	 * 	   ����:�r����X
	 * <br>
	 *</pre>
	 */
	public static String transCharset4db2Client(String sSourceStr) {
		String sTargetStr = "";
		sSourceStr = (sSourceStr == null) ? "" : sSourceStr;
		if (_charset)
			sTargetStr = transCharset(sSourceStr, dbCharset, clientCharset);
		else
			sTargetStr = sSourceStr;
		sTargetStr = (sTargetStr == null) ? "" : sTargetStr;
		return sTargetStr;

	}// transCharset4db2Client()

	private static String transCharset(String sSourceStr, String sSourceCharset, String sTargetCharset) { // �`�N:��X�᪺�r�����ӭn�ηs���r���ܼƥh��,�Y�έ��ܼ�,�i��|�o���ण�L�h�����p,ex:jsp���|�ण�L�h.
		String sTargetStr = "";
		try {// 2003.10.03 �`�N!! �Ŧr�ꤣ�i�H��X
			if (sSourceStr != null && sSourceStr.length() > 0) {
				if ((sSourceCharset.equals("UTF-8") || sSourceCharset.equals("UTF-8")) && (sTargetCharset.equals("ISO8859-1") || sTargetCharset.equals("ISO8859_1"))) {
					StringBuffer sb = new StringBuffer();

					for (int i = 0; i < sSourceStr.length(); i++) {
						String s = sSourceStr.substring(i, i + 1);

						if (s.getBytes(sSourceCharset).length > 1) {
							sb.append(new String(s.getBytes(sSourceCharset), sTargetCharset));
						} else {
							sb.append(s);
						}
					}
					sTargetStr = sb.toString();
				} else {
					sTargetStr = new String(sSourceStr.getBytes(sSourceCharset), sTargetCharset);
				}
			}
		} catch (Exception e) {
			sTargetStr = sSourceStr;
			// log.error(e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}// end of try-catch
		sTargetStr = (sTargetStr == null) ? "" : sTargetStr;
		return sTargetStr;
	}// transCharset()
}
