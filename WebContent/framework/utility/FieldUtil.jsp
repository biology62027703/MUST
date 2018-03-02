<%!
private	void initCustomField(java.util.HashMap hmCustomField, java.util.HashMap req) throws Exception
{
	HashMap	field	=	null;
	//LCRMNM
	field	=	new HashMap();
	field.put("FieldName","案類");
	field.put("FieldSql","(SELECT LCRMNM FROM L.LCRMTYPE WHERE LCRMKD=@C60.KD AND ROWNUM=1) AS F01,@C60.KD AS F01SORT");
	field.put("ScreenWidth","150");
	field.put("PrintWidth","30");
	hmCustomField.put("F01",field);
	//CRMYYIDNO
	field	=	new HashMap();
	field.put("FieldName","案號");
	field.put("FieldSql","@C60.CRMYY||'年'||RPAD(ltrim(rtrim(@C60.CRMID)),20,' ')||'字第'||LPAD(ROUND(ltrim(rtrim(@C60.CRMNO))),10,' ')||'號' AS F02");
	field.put("ScreenWidth","430");
	field.put("PrintWidth","41");
	hmCustomField.put("F02",field);
	//RSN
	field	=	new HashMap();
	field.put("FieldName","案由");
	field.put("FieldSql","@C60.RSN AS F03");
	field.put("ScreenWidth","200");
	field.put("PrintWidth","20");
	hmCustomField.put("F03",field);
	//CRMDT
	field	=	new HashMap();
	field.put("FieldName","收案日期");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C60.CRMDT))) = 7 THEN SUBSTR(@C60.CRMDT,1,3)||'/'||SUBSTR(@C60.CRMDT,4,2)||'/'||SUBSTR(@C60.CRMDT,6,2) ELSE ltrim(rtrim(@C60.CRMDT)) END AS F04");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","9");
	hmCustomField.put("F04",field);
	//JUDDT
	field	=	new HashMap();
	field.put("FieldName","裁定日期");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C60.JUDDT))) = 7 THEN SUBSTR(@C60.JUDDT,1,3)||'/'||SUBSTR(@C60.JUDDT,4,2)||'/'||SUBSTR(@C60.JUDDT,6,2) ELSE ltrim(rtrim(@C60.JUDDT)) END AS F05");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","9");
	hmCustomField.put("F05",field);
	//SURDT
	field	=	new HashMap();
	field.put("FieldName","確定日期");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C60.SURDT))) = 7 THEN SUBSTR(@C60.SURDT,1,3)||'/'||SUBSTR(@C60.SURDT,4,2)||'/'||SUBSTR(@C60.SURDT,6,2) ELSE ltrim(rtrim(@C60.SURDT)) END AS F06");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","9");
	hmCustomField.put("F06",field);
	//STADT
	field	=	new HashMap();
	field.put("FieldName","執行始期");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C60.STADT))) = 7 THEN SUBSTR(@C60.STADT,1,3)||'/'||SUBSTR(@C60.STADT,4,2)||'/'||SUBSTR(@C60.STADT,6,2) ELSE ltrim(rtrim(@C60.STADT)) END AS F07");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","9");
	hmCustomField.put("F07",field);
	//ENDDT
	field	=	new HashMap();
	field.put("FieldName","執行終期");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C60.ENDDT))) = 7 THEN SUBSTR(@C60.ENDDT,1,3)||'/'||SUBSTR(@C60.ENDDT,4,2)||'/'||SUBSTR(@C60.ENDDT,6,2) ELSE ltrim(rtrim(@C60.ENDDT)) END AS F08");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","9");
	hmCustomField.put("F08",field);
	//DUDT
	field	=	new HashMap();
	field.put("FieldName","開庭日期");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C60.DUDT))) = 7 THEN SUBSTR(@C60.DUDT,1,3)||'/'||SUBSTR(@C60.DUDT,4,2)||'/'||SUBSTR(@C60.DUDT,6,2) ELSE ltrim(rtrim(@C60.DUDT)) END AS F09");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","9");
	hmCustomField.put("F09",field);
	//DUTM
	field	=	new HashMap();
	field.put("FieldName","開庭時間");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C60.DUTM))) = 4 THEN SUBSTR(@C60.DUTM,1,2)||':'||SUBSTR(@C60.DUTM,3,2) ELSE @C60.DUTM END AS F10");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","5");
	hmCustomField.put("F10",field);
	//CMPDT
	field	=	new HashMap();
	field.put("FieldName","結案日期");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C60.CMPDT))) = 7 THEN SUBSTR(@C60.CMPDT,1,3)||'/'||SUBSTR(@C60.CMPDT,4,2)||'/'||SUBSTR(@C60.CMPDT,6,2) ELSE ltrim(rtrim(@C60.CMPDT)) END AS F11");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","9");
	hmCustomField.put("F11",field);
	//STATUS
	field	=	new HashMap();
	field.put("FieldName","少年狀態");
	field.put("FieldSql","@C60.STATUS AS F12");
	field.put("ScreenWidth","150");
	field.put("PrintWidth","50");
	hmCustomField.put("F12",field);
	//VOLNM
	field	=	new HashMap();
	field.put("FieldName","志工");
	field.put("FieldSql","(SELECT VOLNM FROM L.C51 b, L.C54 c WHERE b.VOLNO=c.VOLNO AND c.SYS=@C60.SYS AND c.CRMYY=@C60.CRMYY AND c.CRMID=@C60.CRMID AND c.CRMNO=@C60.CRMNO AND ROWNUM=1) AS F13");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","30");
	hmCustomField.put("F13",field);
	//LABOR
	field	=	new HashMap();
	field.put("FieldName","勞動時數");
	field.put("FieldSql","(SELECT SUM(LABOR) FROM "+Utility.dbTable("L.C96",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) GROUP BY CRMYY,CRMID,CRMNO) AS F14");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","30");
	hmCustomField.put("F14",field);
	//CMOG
	field	=	new HashMap();
	field.put("FieldName","裁判法院");
	field.put("FieldSql","@C60.CMOG AS F15");
	field.put("ScreenWidth","200");
	field.put("PrintWidth","40");
	hmCustomField.put("F15",field);
	//OROG
	field	=	new HashMap();
	field.put("FieldName","移送單位");
	field.put("FieldSql","@C60.OROG AS F16");
	field.put("ScreenWidth","200");
	field.put("PrintWidth","40");
	hmCustomField.put("F16",field);
	//CMPTM
	field	=	new HashMap();
	field.put("FieldName","終結事項");
	field.put("FieldSql","(SELECT CMPTM FROM "+Utility.dbTable("L.C63",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND ROWNUM=1) AS F17");
	field.put("ScreenWidth","150");
	field.put("PrintWidth","80");
	hmCustomField.put("F17",field);
	//JUSG
	field	=	new HashMap();
	field.put("FieldName","處遇意見");
	field.put("FieldSql","(SELECT JUSG FROM "+Utility.dbTable("L.C63",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND ROWNUM=1) AS F18");
	field.put("ScreenWidth","150");
	field.put("PrintWidth","80");
	hmCustomField.put("F18",field);
	//ORYYIDNO
	field	=	new HashMap();
	field.put("FieldName","裁判書字號");
	field.put("FieldSql","(SELECT ORYY||'年'||RPAD(ltrim(rtrim(ORID)),20,' ')||'字第'||LPAD(ROUND(ltrim(rtrim(ORNO))),10,' ')||'號' FROM "+Utility.dbTable("L.C65",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND CRMKD='2' AND ROWNUM=1) AS F19");
	field.put("ScreenWidth","390");
	field.put("PrintWidth","41");
	hmCustomField.put("F19",field);
	//ORYYIDNO
	field	=	new HashMap();
	field.put("FieldName","執行書字號");
	field.put("FieldSql","(SELECT ORYY||'年'||RPAD(ltrim(rtrim(ORID)),20,' ')||'字第'||LPAD(ROUND(ltrim(rtrim(ORNO))),10,' ')||'號' FROM "+Utility.dbTable("L.C65",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND CRMKD='3' AND ROWNUM=1) AS F20");
	field.put("ScreenWidth","390");
	field.put("PrintWidth","41");
	hmCustomField.put("F20",field);
	//DURDT
	field	=	new HashMap();
	field.put("FieldName","最後一次進行日期");
	field.put("FieldSql","(SELECT CASE WHEN LENGTH(ltrim(rtrim(DURDT))) = 7 THEN SUBSTR(DURDT,1,3)||'/'||SUBSTR(DURDT,4,2)||'/'||SUBSTR(DURDT,6,2) ELSE DURDT END FROM "+Utility.dbTable("L.C66",req)+" b WHERE DURDT||DURTM = (SELECT MAX(DURDT||DURTM) FROM "+Utility.dbTable("L.C66",req)+" WHERE CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO) AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) AND ROWNUM=1) AS F21");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","9");
	hmCustomField.put("F21",field);
	//DURNM
	field	=	new HashMap();
	field.put("FieldName","最後一次進行事項");
	field.put("FieldSql","(SELECT DURNM FROM "+Utility.dbTable("L.C66",req)+" b WHERE DURDT||DURTM = (SELECT MAX(DURDT||DURTM) FROM "+Utility.dbTable("L.C66",req)+" WHERE CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO) AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) AND ROWNUM=1) AS F22");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","40");
	hmCustomField.put("F22",field);
	//ATDT
	field	=	new HashMap();
	field.put("FieldName","最近一次報到日期");
	field.put("FieldSql","(SELECT CASE WHEN LENGTH(ltrim(rtrim(ATDT))) = 7 THEN SUBSTR(ATDT,1,3)||'/'||SUBSTR(ATDT,4,2)||'/'||SUBSTR(ATDT,6,2) ELSE ATDT END FROM "+Utility.dbTable("L.C96",req)+" b WHERE ATDT||ATTM = (SELECT MAX(ATDT||ATTM) FROM "+Utility.dbTable("L.C96",req)+" WHERE CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) ) AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) AND ROWNUM=1) AS F23");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","9");
	hmCustomField.put("F23",field);
	//ISAT
	field	=	new HashMap();
	field.put("FieldName","最近一次報到情形");
	field.put("FieldSql","(SELECT CASE WHEN ltrim(rtrim(ISAT)) in ('Y','y') THEN '到' WHEN ltrim(rtrim(ISAT)) in ('N','n') THEN '未到' ELSE ISAT END FROM "+Utility.dbTable("L.C96",req)+" b WHERE ATDT||ATTM = (SELECT MAX(ATDT||ATTM) FROM "+Utility.dbTable("L.C96",req)+" WHERE CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) ) AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) AND ROWNUM=1) AS F24");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","4");
	hmCustomField.put("F24",field);
	//ATDT
	field	=	new HashMap();
	field.put("FieldName","最近一次未到日期");
	field.put("FieldSql","(SELECT CASE WHEN LENGTH(ltrim(rtrim(ATDT))) = 7 THEN SUBSTR(ATDT,1,3)||'/'||SUBSTR(ATDT,4,2)||'/'||SUBSTR(ATDT,6,2) ELSE ATDT END FROM "+Utility.dbTable("L.C96",req)+" b WHERE ATDT||ATTM = (SELECT MAX(ATDT||ATTM) FROM "+Utility.dbTable("L.C96",req)+" WHERE ISAT IN ('N','n') AND CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO) AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) AND ROWNUM=1) AS F25");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","9");
	hmCustomField.put("F25",field);
	//CLNM
	field	=	new HashMap();
	field.put("FieldName","姓名");
	field.put("FieldSql","(SELECT CLNM FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F26");
	field.put("ScreenWidth","150");
	field.put("PrintWidth","80");
	hmCustomField.put("F26",field);
	//SERNO
	field	=	new HashMap();
	field.put("FieldName","流水號");
	field.put("FieldSql","@C60.SERNO AS F27");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","8");
	hmCustomField.put("F27",field);
	//IDNO
	field	=	new HashMap();
	field.put("FieldName","身份證字號");
	field.put("FieldSql","(SELECT IDNO FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F28");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","40");
	hmCustomField.put("F28",field);
	//SEX
	field	=	new HashMap();
	field.put("FieldName","性別");
	field.put("FieldSql","(SELECT CASE WHEN ltrim(rtrim(SEX)) IN ('M','m') THEN '男' WHEN ltrim(rtrim(SEX)) IN ('F','f') THEN '女' ELSE SEX END FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F29");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","2");
	hmCustomField.put("F29",field);
	//BIRDT
	field	=	new HashMap();
	field.put("FieldName","出生日期");
	field.put("FieldSql","(SELECT CASE WHEN LENGTH(ltrim(rtrim(BIRDT))) = 7 THEN SUBSTR(BIRDT,1,3)||'/'||SUBSTR(BIRDT,4,2)||'/'||SUBSTR(BIRDT,6,2) ELSE BIRDT END FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F30");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","9");
	hmCustomField.put("F30",field);
	//PFS
	field	=	new HashMap();
	field.put("FieldName","職業");
	field.put("FieldSql","(SELECT PFS FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F31");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","24");
	hmCustomField.put("F31",field);
	//DEGREE
	field	=	new HashMap();
	field.put("FieldName","學歷");
	field.put("FieldSql","(SELECT DEGREE FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F32");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","24");
	hmCustomField.put("F32",field);
	//TEL1
	field	=	new HashMap();
	field.put("FieldName","電話1");
	field.put("FieldSql","(SELECT TEL1 FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F33");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","15");
	hmCustomField.put("F33",field);
	//TEL2
	field	=	new HashMap();
	field.put("FieldName","電話2");
	field.put("FieldSql","(SELECT TEL2 FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F34");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","15");
	hmCustomField.put("F34",field);
	//CELL
	field	=	new HashMap();
	field.put("FieldName","手機");
	field.put("FieldSql","(SELECT CELL FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F35");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","15");
	hmCustomField.put("F35",field);
	//AD
	field	=	new HashMap();
	field.put("FieldName","住所地址");
	field.put("FieldSql","(SELECT AD FROM "+Utility.dbTable("L.C62",req)+" b WHERE DAKD='1' AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND SYSID=(SELECT SYSID FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AND ROWNUM=1) AS F36");
	field.put("ScreenWidth","300");
	field.put("PrintWidth","320");
	hmCustomField.put("F36",field);
	//AD
	field	=	new HashMap();
	field.put("FieldName","居住地址");
	field.put("FieldSql","(SELECT AD FROM "+Utility.dbTable("L.C62",req)+" b WHERE DAKD='2' AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND SYSID=(SELECT SYSID FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AND ROWNUM=1) AS F37");
	field.put("ScreenWidth","300");
	field.put("PrintWidth","320");
	hmCustomField.put("F37",field);
	//AD
	field	=	new HashMap();
	field.put("FieldName","送達地址");
	field.put("FieldSql","(SELECT AD FROM "+Utility.dbTable("L.C62",req)+" b WHERE DAKD='3' AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND SYSID=(SELECT SYSID FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AND ROWNUM=1) AS F38");
	field.put("ScreenWidth","300");
	field.put("PrintWidth","320");
	hmCustomField.put("F38",field);
	//TITLE
	field	=	new HashMap();
	field.put("FieldName","學校名稱");
	field.put("FieldSql","(SELECT SCNM FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F39");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","60");
	hmCustomField.put("F39",field);
	//TEL
	field	=	new HashMap();
	field.put("FieldName","學校電話");
	field.put("FieldSql","(SELECT SCTEL FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F40");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","15");
	hmCustomField.put("F40",field);
	//AD
	field	=	new HashMap();
	field.put("FieldName","學校地址");
	field.put("FieldSql","(SELECT AD FROM "+Utility.dbTable("L.C62",req)+" b WHERE DAKD='4' AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND SYSID=(SELECT SYSID FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AND ROWNUM=1) AS F41");
	field.put("ScreenWidth","300");
	field.put("PrintWidth","320");
	hmCustomField.put("F41",field);
	//TITLE
	field	=	new HashMap();
	field.put("FieldName","公司名稱");
	field.put("FieldSql","(SELECT CMNM FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F42");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","60");
	hmCustomField.put("F42",field);
	//TEL
	field	=	new HashMap();
	field.put("FieldName","公司電話");
	field.put("FieldSql","(SELECT CMTEL FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F43");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","15");
	hmCustomField.put("F43",field);
	//AD
	field	=	new HashMap();
	field.put("FieldName","公司地址");
	field.put("FieldSql","(SELECT AD FROM "+Utility.dbTable("L.C62",req)+" b WHERE DAKD='5' AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND SYSID=(SELECT SYSID FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AND ROWNUM=1) AS F44");
	field.put("ScreenWidth","300");
	field.put("PrintWidth","320");
	hmCustomField.put("F44",field);
	//CLNM
	field	=	new HashMap();
	field.put("FieldName","法定代理人");
	field.put("FieldSql","(SELECT (SELECT CLNM FROM "+Utility.dbTable("L.C6A",req)+" WHERE ROWNUM=1 AND CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND IDAPT=c.AGIDAPT AND IDNM=c.AGIDNM AND SYSID=c.AGSYSID) AS CLNM FROM "+Utility.dbTable("L.C61",req)+" b,"+Utility.dbTable("L.C6B",req)+" c WHERE b.CRMYY=@C60.CRMYY AND b.CRMID=@C60.CRMID AND b.CRMNO=@C60.CRMNO AND b.IDAPT LIKE '202%' AND b.CRMYY=c.CRMYY(+) AND b.CRMID=c.CRMID(+) AND b.CRMNO=c.CRMNO(+) AND b.IDAPT=c.CLIDAPT(+) AND b.SYSID=c.CLSYSID(+) AND c.AGIDNM='法定代理人' AND ROWNUM=1) AS F45");
	field.put("ScreenWidth","150");
	field.put("PrintWidth","80");
	hmCustomField.put("F45",field);
	//TEL1
	field	=	new HashMap();
	field.put("FieldName","法代電話");
	field.put("FieldSql","(SELECT (SELECT TEL1 FROM "+Utility.dbTable("L.C6A",req)+" WHERE ROWNUM=1 AND CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND IDAPT=c.AGIDAPT AND IDNM=c.AGIDNM AND SYSID=c.AGSYSID) AS CLNM FROM "+Utility.dbTable("L.C61",req)+" b,"+Utility.dbTable("L.C6B",req)+" c WHERE b.CRMYY=@C60.CRMYY AND b.CRMID=@C60.CRMID AND b.CRMNO=@C60.CRMNO AND b.IDAPT LIKE '202%' AND b.CRMYY=c.CRMYY(+) AND b.CRMID=c.CRMID(+) AND b.CRMNO=c.CRMNO(+) AND b.IDAPT=c.CLIDAPT(+) AND b.SYSID=c.CLSYSID(+) AND c.AGIDNM='法定代理人' AND ROWNUM=1) AS F46");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","15");
	hmCustomField.put("F46",field);
	//CLNM
	field	=	new HashMap();
	field.put("FieldName","保護少年之人");
	field.put("FieldSql","(SELECT (SELECT CLNM FROM "+Utility.dbTable("L.C6A",req)+" WHERE ROWNUM=1 AND CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND IDAPT=c.AGIDAPT AND IDNM=c.AGIDNM AND SYSID=c.AGSYSID) AS CLNM FROM "+Utility.dbTable("L.C61",req)+" b,"+Utility.dbTable("L.C6B",req)+" c WHERE b.CRMYY=@C60.CRMYY AND b.CRMID=@C60.CRMID AND b.CRMNO=@C60.CRMNO AND b.IDAPT LIKE '202%' AND b.CRMYY=c.CRMYY(+) AND b.CRMID=c.CRMID(+) AND b.CRMNO=c.CRMNO(+) AND b.IDAPT=c.CLIDAPT(+) AND b.SYSID=c.CLSYSID(+) AND c.AGIDNM='保護少年之人' AND ROWNUM=1) AS F47");
	field.put("ScreenWidth","200");
	field.put("PrintWidth","80");
	hmCustomField.put("F47",field);
	//TEL1
	field	=	new HashMap();
	field.put("FieldName","保護少年之人電話");
	field.put("FieldSql","(SELECT (SELECT TEL1 FROM "+Utility.dbTable("L.C6A",req)+" WHERE ROWNUM=1 AND CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND IDAPT=c.AGIDAPT AND IDNM=c.AGIDNM AND SYSID=c.AGSYSID) AS CLNM FROM "+Utility.dbTable("L.C61",req)+" b,"+Utility.dbTable("L.C6B",req)+" c WHERE b.CRMYY=@C60.CRMYY AND b.CRMID=@C60.CRMID AND b.CRMNO=@C60.CRMNO AND b.IDAPT LIKE '202%' AND b.CRMYY=c.CRMYY(+) AND b.CRMID=c.CRMID(+) AND b.CRMNO=c.CRMNO(+) AND b.IDAPT=c.CLIDAPT(+) AND b.SYSID=c.CLSYSID(+) AND c.AGIDNM='保護少年之人' AND ROWNUM=1) AS F48");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","15");
	hmCustomField.put("F48",field);
	//CLNM
	field	=	new HashMap();
	field.put("FieldName","具保責付之人");
	field.put("FieldSql","(SELECT (SELECT CLNM FROM "+Utility.dbTable("L.C6A",req)+" WHERE ROWNUM=1 AND CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND IDAPT=c.AGIDAPT AND IDNM=c.AGIDNM AND SYSID=c.AGSYSID) AS CLNM FROM "+Utility.dbTable("L.C61",req)+" b,"+Utility.dbTable("L.C6B",req)+" c WHERE b.CRMYY=@C60.CRMYY AND b.CRMID=@C60.CRMID AND b.CRMNO=@C60.CRMNO AND b.IDAPT LIKE '202%' AND b.CRMYY=c.CRMYY(+) AND b.CRMID=c.CRMID(+) AND b.CRMNO=c.CRMNO(+) AND b.IDAPT=c.CLIDAPT(+) AND b.SYSID=c.CLSYSID(+) AND c.AGIDNM='具保責付之人' AND ROWNUM=1) AS F49");
	field.put("ScreenWidth","200");
	field.put("PrintWidth","80");
	hmCustomField.put("F49",field);
	//TEL1
	field	=	new HashMap();
	field.put("FieldName","具保責付之人電話");
	field.put("FieldSql","(SELECT (SELECT TEL1 FROM "+Utility.dbTable("L.C6A",req)+" WHERE ROWNUM=1 AND CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND IDAPT=c.AGIDAPT AND IDNM=c.AGIDNM AND SYSID=c.AGSYSID) AS CLNM FROM "+Utility.dbTable("L.C61",req)+" b,"+Utility.dbTable("L.C6B",req)+" c WHERE b.CRMYY=@C60.CRMYY AND b.CRMID=@C60.CRMID AND b.CRMNO=@C60.CRMNO AND b.IDAPT LIKE '202%' AND b.CRMYY=c.CRMYY(+) AND b.CRMID=c.CRMID(+) AND b.CRMNO=c.CRMNO(+) AND b.IDAPT=c.CLIDAPT(+) AND b.SYSID=c.CLSYSID(+) AND c.AGIDNM='具保責付之人' AND ROWNUM=1) AS F50");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","15");
	hmCustomField.put("F50",field);
	//DPT
	field	=	new HashMap();
	field.put("FieldName","股別");
	field.put("FieldSql","@C60.DPT AS F51");
	field.put("ScreenWidth","60");
	field.put("PrintWidth","4");
	hmCustomField.put("F51",field);
	//CRMYYIDNO
	field	=	new HashMap();
	field.put("FieldName","案號");
	field.put("FieldSql","@C60.CRMYY||'.'||ltrim(rtrim(@C60.CRMID))||'.'||ROUND(ltrim(rtrim(@C60.CRMNO))) AS F52");
	field.put("ScreenWidth","430");
	field.put("PrintWidth","41");
	hmCustomField.put("F52",field);	
	//RMK
	field	=	new HashMap();
	field.put("FieldName","當事人備考");
	field.put("FieldSql","(SELECT RMK FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS F53");
	field.put("ScreenWidth","150");
	field.put("PrintWidth","50");
	hmCustomField.put("F53",field);
	//PEDT
	field	=	new HashMap();
	field.put("FieldName","最近分案日");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C60.PEDT))) = 7 THEN SUBSTR(@C60.PEDT,1,3)||'/'||SUBSTR(@C60.PEDT,4,2)||'/'||SUBSTR(@C60.PEDT,6,2) ELSE ltrim(rtrim(@C60.PEDT)) END AS D01");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","9");
	hmCustomField.put("D01",field);
	//ATDT
	field	=	new HashMap();
	field.put("FieldName","報到日期");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C96.ATDT))) = 7 THEN SUBSTR(@C96.ATDT,1,3)||'/'||SUBSTR(@C96.ATDT,4,2)||'/'||SUBSTR(@C96.ATDT,6,2) ELSE ltrim(rtrim(@C96.ATDT)) END AS D02");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","9");
	hmCustomField.put("D02",field);
	//ATTM
	field	=	new HashMap();
	field.put("FieldName","報到時間");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C96.ATTM))) = 4 THEN SUBSTR(@C96.ATTM,1,2)||':'||SUBSTR(@C96.ATTM,3,2) ELSE @C96.ATTM END AS D03");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","5");
	hmCustomField.put("D03",field);
	//ISAT
	field	=	new HashMap();
	field.put("FieldName","報到狀況");
	field.put("FieldSql","CASE WHEN ltrim(rtrim(@C96.ISAT)) in ('Y','y') THEN '到' WHEN ltrim(rtrim(@C96.ISAT)) in ('N','n') THEN '未到' ELSE @C96.ISAT END AS D04");
	field.put("ScreenWidth","100");
	field.put("PrintWidth","4");
	hmCustomField.put("D04",field);
	//NATDT
	field	=	new HashMap();
	field.put("FieldName","下次報到日期");
	field.put("FieldSql","(SELECT CASE WHEN LENGTH(ltrim(rtrim(MIN(ATDT)))) = 7 THEN SUBSTR(MIN(ATDT),1,3)||'/'||SUBSTR(MIN(ATDT),4,2)||'/'||SUBSTR(MIN(ATDT),6,2) ELSE MIN(ATDT) END FROM "+Utility.dbTable("L.C96",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND ((ATDT > @C96.ATDT) OR (ATDT = @C96.ATDT AND ATTM > @C96.ATTM))) AS D05");
	//field.put("FieldFormat","DT");
	field.put("ScreenWidth","120");
	field.put("PrintWidth","9");
	hmCustomField.put("D05",field);
	//NATTM
	field	=	new HashMap();
	field.put("FieldName","下次報到時間");
	field.put("FieldSql","(SELECT CASE WHEN LENGTH(ltrim(rtrim(SUBSTR(MIN(LPAD(ATDT,7,' ')||ATTM),8)))) = 4 THEN SUBSTR(SUBSTR(MIN(LPAD(ATDT,7,' ')||ATTM),8),1,2)||':'||SUBSTR(SUBSTR(MIN(LPAD(ATDT,7,' ')||ATTM),8),3,2) ELSE SUBSTR(MIN(LPAD(ATDT,7,' ')||ATTM),8) END FROM "+Utility.dbTable("L.C96",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND ((ATDT > @C96.ATDT) OR (ATDT = @C96.ATDT AND ATTM > @C96.ATTM))) AS D06");
	//field.put("FieldFormat","TM");
	field.put("ScreenWidth","120");
	field.put("PrintWidth","5");
	hmCustomField.put("D06",field);
	//ATRMK
	field	=	new HashMap();
	field.put("FieldName","備註");
	field.put("FieldSql","@C96.ATRMK AS D07");
	field.put("ScreenWidth","150");
	field.put("PrintWidth","50");
	hmCustomField.put("D07",field);
	//DURDT
	field	=	new HashMap();
	field.put("FieldName","最後勸導日期");
	field.put("FieldSql","(SELECT CASE WHEN LENGTH(ltrim(rtrim(DURDT))) = 7 THEN SUBSTR(DURDT,1,3)||'/'||SUBSTR(DURDT,4,2)||'/'||SUBSTR(DURDT,6,2) ELSE ltrim(rtrim(DURDT)) END FROM "+Utility.dbTable("L.C66",req)+" b WHERE DURDT||DURTM = (SELECT MAX(DURDT||DURTM) FROM "+Utility.dbTable("L.C66",req)+" WHERE CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND (DURNM LIKE '%勸導%' OR RMK LIKE '%勸導%') "+(Utility.isEmpty(req.get("SDURDT"))?"":" AND DURDT >= '"+Utility.dbStr(req.get("SDURDT"))+"'")+(Utility.isEmpty(req.get("EDURDT"))?"":" AND DURDT <= '"+Utility.dbStr(req.get("EDURDT"))+"'")+") AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) AND ROWNUM=1) AS D08");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","9");
	hmCustomField.put("D08",field);
	//COUNTC66
	field	=	new HashMap();
	field.put("FieldName","勸導次數");
	field.put("FieldSql","(SELECT COUNT(1) FROM "+Utility.dbTable("L.C66",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) AND (DURNM LIKE '%勸導%' OR RMK LIKE '%勸導%') "+(Utility.isEmpty(req.get("SDURDT"))?"":" AND DURDT >= '"+Utility.dbStr(req.get("SDURDT"))+"'")+(Utility.isEmpty(req.get("EDURDT"))?"":" AND DURDT <= '"+Utility.dbStr(req.get("EDURDT"))+"'")+") AS D09");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","3");
	hmCustomField.put("D09",field);
	//ALERT
	field	=	new HashMap();
	field.put("FieldName","警示天數");
	field.put("FieldSql","(SELECT CASE WHEN (TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD')-ADD_MONTHS(TO_DATE(ROUND(BIRDT)+19110000,'YYYYMMDD'),12*21))*-1 < 0 THEN '超過 '||(TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD')-ADD_MONTHS(TO_DATE(ROUND(BIRDT)+19110000,'YYYYMMDD'),12*21))||' 天' ELSE '尚有 '||(ADD_MONTHS(TO_DATE(ROUND(BIRDT)+19110000,'YYYYMMDD'),12*21)-TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD'))||' 天' END FROM "+Utility.dbTable("L.C61",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND IDAPT LIKE '202%' AND ROWNUM=1) AS D10");
	field.put("ScreenWidth","120");
	field.put("PrintWidth","5");
	hmCustomField.put("D10",field);
	//LASTATDT
	field	=	new HashMap();
	field.put("FieldName","第二次未到日");
	field.put("FieldSql","(SELECT CASE WHEN LENGTH(ltrim(rtrim(ATDT))) = 7 THEN SUBSTR(ATDT,1,3)||'/'||SUBSTR(ATDT,4,2)||'/'||SUBSTR(ATDT,6,2) ELSE ATDT END FROM "+Utility.dbTable("L.C96",req)+" b WHERE ATDT||ATTM = (SELECT MAX(ATDT||ATTM) FROM "+Utility.dbTable("L.C96",req)+" WHERE CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND ISAT='N' AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) ) AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND ISAT='N' AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) AND ROWNUM=1) AS D11");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","9");
	hmCustomField.put("D11",field);
	//LAST2ATDT
	field	=	new HashMap();
	field.put("FieldName","第一次未到日");
	field.put("FieldSql","(SELECT CASE WHEN LENGTH(ltrim(rtrim(MAX(ATDT)))) = 7 THEN SUBSTR(MAX(ATDT),1,3)||'/'||SUBSTR(MAX(ATDT),4,2)||'/'||SUBSTR(MAX(ATDT),6,2) ELSE MAX(ATDT) END FROM "+Utility.dbTable("L.C96",req)+" b WHERE ATDT||ATTM||ROWID <> (SELECT MAX(ATDT||ATTM||ROWID) FROM "+Utility.dbTable("L.C96",req)+" WHERE CRMYY=b.CRMYY AND CRMID=b.CRMID AND CRMNO=b.CRMNO AND ISAT='N' AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) ) AND CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND ISAT='N' AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"'))) AS D12");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","9");
	hmCustomField.put("D12",field);
	//COUNTISATY
	field	=	new HashMap();
	field.put("FieldName","已到次數");
	field.put("FieldSql","(SELECT COUNT(1) FROM "+Utility.dbTable("L.C96",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) AND UPPER(ISAT) = 'Y') AS D13");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","9");
	hmCustomField.put("D13",field);
	//COUNTISATN
	field	=	new HashMap();
	field.put("FieldName","未到次數");
	field.put("FieldSql","(SELECT COUNT(1) FROM "+Utility.dbTable("L.C96",req)+" WHERE CRMYY=@C60.CRMYY AND CRMID=@C60.CRMID AND CRMNO=@C60.CRMNO AND (((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND (SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) LIKE '%,'||ID||',%') OR ((SELECT ARGVL FROM L.S08 WHERE PRGID='CL2' AND ARGNM='調保官辦案簿' AND ROWNUM=1) NOT LIKE '%,"+Utility.dbStr(req.get("S03ID"))+",%' AND ID = '"+Utility.dbStr(req.get("S03ID"))+"')) AND UPPER(ISAT) = 'N') AS D14");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","9");
	hmCustomField.put("D14",field);
	//ENDDT/DUDT ALERT
	field	=	new HashMap();
	field.put("FieldName","警示天數");
	field.put("FieldSql","CASE WHEN (TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD')-TO_DATE(ROUND(CASE WHEN @C60.KD='1' THEN @C60.DUDT ELSE @C60.ENDDT END)+19110000,'YYYYMMDD')) > 0 THEN '超過 '||(TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD')-TO_DATE(ROUND(CASE WHEN @C60.KD='1' THEN @C60.DUDT ELSE @C60.ENDDT END)+19110000,'YYYYMMDD'))||' 天' ELSE '尚有 '||(TO_DATE(ROUND(CASE WHEN @C60.KD='1' THEN @C60.DUDT ELSE @C60.ENDDT END)+19110000,'YYYYMMDD')-TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD'))||' 天' END AS D15");
	field.put("ScreenWidth","120");
	field.put("PrintWidth","5");
	hmCustomField.put("D15",field);
	//AJDT
	field	=	new HashMap();
	field.put("FieldName","收容日期");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C67.AJDT))) = 7 THEN SUBSTR(@C67.AJDT,1,3)||'/'||SUBSTR(@C67.AJDT,4,2)||'/'||SUBSTR(@C67.AJDT,6,2) ELSE ltrim(rtrim(@C67.AJDT)) END AS D16");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","9");
	hmCustomField.put("D16",field);
	//CRMYYIDNO
	field	=	new HashMap();
	field.put("FieldName","少年法庭案號");
	field.put("FieldSql","@C67.CRMYY||'年'||RPAD(ltrim(rtrim(@C67.CRMID)),20,' ')||'字第'||LPAD(ROUND(ltrim(rtrim(@C67.CRMNO))),10,' ')||'號' AS D17");
	field.put("ScreenWidth","430");
	field.put("PrintWidth","41");
	hmCustomField.put("D17",field);
	//IDPT
	field	=	new HashMap();
	field.put("FieldName","少年法庭股別");
	field.put("FieldSql","@C60.IDPT AS D18");
	field.put("ScreenWidth","120");
	field.put("PrintWidth","4");
	hmCustomField.put("D18",field);
	//DUDT
	field	=	new HashMap();
	field.put("FieldName","開庭日期");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C69.DUDT))) = 7 THEN SUBSTR(@C69.DUDT,1,3)||'/'||SUBSTR(@C69.DUDT,4,2)||'/'||SUBSTR(@C69.DUDT,6,2) ELSE ltrim(rtrim(@C69.DUDT)) END AS D19");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","9");
	hmCustomField.put("D19",field);
	//WARNDT
	field	=	new HashMap();
	field.put("FieldName","警示日期");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C66.WARNDT))) = 7 THEN SUBSTR(@C66.WARNDT,1,3)||'/'||SUBSTR(@C66.WARNDT,4,2)||'/'||SUBSTR(@C66.WARNDT,6,2) ELSE ltrim(rtrim(@C66.WARNDT)) END AS D20");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","9");
	hmCustomField.put("D20",field);
	//DURDT
	field	=	new HashMap();
	field.put("FieldName","進行日期");
	field.put("FieldSql","CASE WHEN LENGTH(ltrim(rtrim(@C66.DURDT))) = 7 THEN SUBSTR(@C66.DURDT,1,3)||'/'||SUBSTR(@C66.DURDT,4,2)||'/'||SUBSTR(@C66.DURDT,6,2) ELSE ltrim(rtrim(@C66.DURDT)) END AS D21");
	field.put("ScreenWidth","90");
	field.put("PrintWidth","9");
	hmCustomField.put("D21",field);
	//DURNM
	field	=	new HashMap();
	field.put("FieldName","進行事項");
	field.put("FieldSql","@C66.DURNM AS D22");
	field.put("ScreenWidth","200");
	field.put("PrintWidth","20");
	hmCustomField.put("D22",field);

}

private	String getFieldArray(java.util.HashMap req) throws Exception
{
	HashMap hmCustomField = new HashMap();
	initCustomField(hmCustomField, req);
	Iterator i	=	hmCustomField.keySet().iterator();
	StringBuffer js	=	new StringBuffer();

	js.append("var _field = new Array();\n");

	while (i.hasNext())
	{
	    Object obj = i.next();
	    if (obj.toString().startsWith("F"))
	    {
			js.append("_field['"+obj.toString()+"'] = new Array('"+obj.toString()+"','"+Utility.getHashString(hmCustomField.get(obj.toString()),"FieldName","JS")+"',0,false);\n");
		}
	}
	return js.toString();
}

private String getFieldSql(String sql, java.util.HashMap hmTableAlias) throws Exception
{
	Iterator i	=	hmTableAlias.keySet().iterator();
	while (i.hasNext())
	{
	    Object obj = i.next();
	    sql = sql.replaceAll("@"+obj.toString(), Utility.checkNull(hmTableAlias.get(obj.toString())));
	}
	return sql;
}
%>