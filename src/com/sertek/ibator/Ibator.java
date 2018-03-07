package com.sertek.ibator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sertek.db.DBUtility;

/**
 * automatic generate iBATIS sqlMapXml and javaModel
 * @author Eason
 *
 */
public class Ibator {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private DBUtility db;
	private StringBuffer sql = new StringBuffer();
	private String owner = "je";
	private String javaModelDir = "D:\\workspace\\coje\\src\\com\\sertek\\table\\";
	private String sqlMapXmlDir = "D:\\workspace\\coje\\WebRoot\\WEB-INF\\ibatis-xml\\table\\";
	private String javaModelExtendsClass = "com.sertek.table.BaseTable";
	private String javaModelPackage = "com.sertek.table";
	private boolean isBakFile = false;
	
	private Map rcvTable = new HashMap();
	private Map crmTable = new HashMap();
	
	private Map rcvSysidTable = new HashMap();
	private Map crmSysidTable = new HashMap();
	
	public Ibator(DBUtility db, String owner) {
		this.db = db;
		this.owner = owner.toLowerCase();
		boolean isTemp = false;
		if (owner.toUpperCase().startsWith("TEMP")) {
			isTemp = true;
		}
		if (isTemp) {
			this.owner = "tempcoje";
			this.javaModelDir = "D:\\workspace\\coje\\src\\com\\sertek\\table\\temp\\";
			this.sqlMapXmlDir = "D:\\workspace\\coje\\WebRoot\\WEB-INF\\ibatis-xml\\temp\\";
			this.javaModelPackage = "com.sertek.table.temp";
		}
		
		rcvTable.put("C01", "");
		rcvTable.put("C60", "");
		rcvTable.put("C61", "");
		rcvTable.put("C62", "");
		rcvTable.put("C65", "");
		rcvTable.put("C6A", "");
		rcvTable.put("C6B", "");
		rcvTable.put("X20", "");
		
		crmTable.put("C01", "");
		crmTable.put("C60", "");
		crmTable.put("C61", "");
		crmTable.put("C62", "");
		crmTable.put("C65", "");
		crmTable.put("C6A", "");
		crmTable.put("C6B", "");
		crmTable.put("X20", "");
		
		rcvSysidTable.put("C11", "");
		rcvSysidTable.put("C61", "");
		rcvSysidTable.put("C62", "");
		rcvSysidTable.put("C6A", "");
		rcvSysidTable.put("C6B", "");
		
		crmSysidTable.put("C11", "");
		crmSysidTable.put("C61", "");
		crmSysidTable.put("C62", "");
		crmSysidTable.put("C6A", "");
		crmSysidTable.put("C6B", "");
	}
	
	public void doGenerateTable(String tableName) {
		try {
			Table table = new Table();
			table.setTableName(tableName);
			table.setTableComment(this.getTableComment(tableName));
			this.setColumns(table);
			this.setPrimaryKeys(table);
			genSqlMapXml(table);
			genJavaModel(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void genSqlMapXml(Table table) {
		List content = new ArrayList();
		
		String tableObject = javaModelPackage + "." + table.getTableName();
		String hashMapObj = "java.util.HashMap";
		//String stringObj = "java.lang.String";
		
		List columnList = table.getTabColumnList();
		List pkList = table.getTabPrimaryKeyList();
		
		StringBuffer selectColumn = new StringBuffer();
		selectColumn.append("rowid, ");
		for (int i = 0; i < columnList.size(); i++) {
			TabColumn column = (TabColumn) columnList.get(i);
			selectColumn.append(column.getColumnName().toLowerCase());
			if (i < columnList.size() - 1) {
				selectColumn.append(", ");
			}
		}
		
		List pkWhereClause = new ArrayList();
		for (int i = 0; i < pkList.size(); i++) {
			TabPrimaryKey pk = (TabPrimaryKey) pkList.get(i);
			pkWhereClause.add("			<isNotEqual prepend=\"and\" property=\"" + pk.getColumnName().toLowerCase() + "\" compareValue=\"\">");
			pkWhereClause.add("				" + pk.getColumnName().toLowerCase() + " = #" + pk.getColumnName().toLowerCase() + "#");
			pkWhereClause.add("			</isNotEqual>");
		}
		
		List keyWhereClause = new ArrayList();
		for (int i = 0; i < columnList.size(); i++) {
			TabColumn column = (TabColumn) columnList.get(i);
			keyWhereClause.add("			<isNotNull prepend=\"and\" property=\"" + column.getColumnName().toLowerCase() + "\">");
			keyWhereClause.add("				" + column.getColumnName().toLowerCase() + " = #" + column.getColumnName().toLowerCase() + "#");
			keyWhereClause.add("			</isNotNull>");
		}
		
		StringBuffer orderByClause = new StringBuffer();
		for (int i = 0; i < pkList.size(); i++) {
			TabPrimaryKey pk = (TabPrimaryKey) pkList.get(i);
			orderByClause.append(pk.getColumnName().toLowerCase());
			if (i < pkList.size() - 1) {
				orderByClause.append(", ");
			}
		}
		
		List orderByKey = new ArrayList();
		orderByKey.add("		<isNotNull prepend=\"\" property=\"orderByKey\">");
		orderByKey.add("			order by $orderByKey$");
		orderByKey.add("		</isNotNull>");
		
		StringBuffer allInsertColumn = new StringBuffer();
		StringBuffer allInsertValue = new StringBuffer();
		List insertColumn = new ArrayList();
		List insertValue = new ArrayList();
		List updateColumn = new ArrayList();
		List updateColumnByRcv = new ArrayList();
		List updateColumnByCrm = new ArrayList();
		List updateColumnByRcvSysid = new ArrayList();
		List updateColumnByCrmSysid = new ArrayList();
		for (int i = 0; i < columnList.size(); i++) {
			TabColumn column = (TabColumn) columnList.get(i);
			
			allInsertColumn.append(column.getColumnName().toLowerCase());
			allInsertValue.append("#" + column.getColumnName().toLowerCase()+ "#");
			if (i < columnList.size() - 1) {
				allInsertColumn.append(", ");
				allInsertValue.append(", ");
			}
			
			insertColumn.add("			<isNotNull prepend=\",\" property=\"" + column.getColumnName().toLowerCase() + "\">");
			insertColumn.add("				" + column.getColumnName().toLowerCase());
			insertColumn.add("			</isNotNull>");
			
			insertValue.add("			<isNotNull prepend=\",\" property=\"" + column.getColumnName().toLowerCase() + "\">");
			insertValue.add("				#" + column.getColumnName().toLowerCase() + "#");
			insertValue.add("			</isNotNull>");
			
			updateColumn.add("			<isNotNull prepend=\",\" property=\"" + column.getColumnName().toLowerCase() + "\">");
			updateColumn.add("				" + column.getColumnName().toLowerCase() + " = #" + column.getColumnName().toLowerCase() + "#");
			updateColumn.add("			</isNotNull>");
			
			if (!"rcvyy".equals(column.getColumnName().toLowerCase()) &&
				!"rcvno".equals(column.getColumnName().toLowerCase())) {
				updateColumnByRcv.add("			<isNotNull prepend=\",\" property=\"" + column.getColumnName().toLowerCase() + "\">");
				updateColumnByRcv.add("				" + column.getColumnName().toLowerCase() + " = #" + column.getColumnName().toLowerCase() + "#");
				updateColumnByRcv.add("			</isNotNull>");
			}
			
			if (!"crmyy".equals(column.getColumnName().toLowerCase()) &&
				!"crmid".equals(column.getColumnName().toLowerCase()) &&
				!"crmno".equals(column.getColumnName().toLowerCase())) {
				updateColumnByCrm.add("			<isNotNull prepend=\",\" property=\"" + column.getColumnName().toLowerCase() + "\">");
				updateColumnByCrm.add("				" + column.getColumnName().toLowerCase() + " = #" + column.getColumnName().toLowerCase() + "#");
				updateColumnByCrm.add("			</isNotNull>");
			}
			
			if (!"rcvyy".equals(column.getColumnName().toLowerCase()) &&
				!"rcvno".equals(column.getColumnName().toLowerCase()) &&
				!"sysid".equals(column.getColumnName().toLowerCase())) {
				updateColumnByRcvSysid.add("			<isNotNull prepend=\",\" property=\"" + column.getColumnName().toLowerCase() + "\">");
				updateColumnByRcvSysid.add("				" + column.getColumnName().toLowerCase() + " = #" + column.getColumnName().toLowerCase() + "#");
				updateColumnByRcvSysid.add("			</isNotNull>");
			}
			
			if (!"crmyy".equals(column.getColumnName().toLowerCase()) &&
				!"crmid".equals(column.getColumnName().toLowerCase()) &&
				!"crmno".equals(column.getColumnName().toLowerCase()) &&
				!"sysid".equals(column.getColumnName().toLowerCase())) {
				updateColumnByCrmSysid.add("			<isNotNull prepend=\",\" property=\"" + column.getColumnName().toLowerCase() + "\">");
				updateColumnByCrmSysid.add("				" + column.getColumnName().toLowerCase() + " = #" + column.getColumnName().toLowerCase() + "#");
				updateColumnByCrmSysid.add("			</isNotNull>");
			}
		}
		
		content.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");  
		content.add("<!DOCTYPE sqlMap PUBLIC \"-//iBATIS.com//DTD SQL Map 2.0//EN\" \"http://www.ibatis.com/dtd/sql-map-2.dtd\">");
	    content.add("<!--");
	    content.add("  " + owner.toUpperCase() + "." + table.getTableName() + " " + table.getTableComment());
	    content.add("  generated by " + this.getClass().getSimpleName() + " on " + new java.sql.Timestamp(System.currentTimeMillis()));
	    content.add("-->");
		content.add("<sqlMap namespace=\"" + table.getTableName() + "\">");
		
		content.add("");
		
		content.add("	<select id=\"queryAll\" resultClass=\"" + tableObject + "\">");
		content.add("		select " + selectColumn);
		content.add("		from " + owner + "." + table.getTableName().toLowerCase());
		if (orderByClause.length() > 0) {
			content.add("		order by " + orderByClause);
		}
		content.add("	</select>");
		
		content.add("");

		if (pkList.size() > 0) {
			content.add("	<select id=\"queryByPrimaryKey\" resultClass=\"" + tableObject + "\"  parameterClass=\"" + tableObject + "\">");
			content.add("		select " + selectColumn);
			content.add("		from " + owner + "." + table.getTableName().toLowerCase());
			content.add("		<dynamic prepend=\"where\">");
			content.addAll(pkWhereClause);
			content.add("		</dynamic>");
			if (orderByClause.length() > 0) {
				content.add("		order by " + orderByClause);
			}
			content.add("	</select>");

			content.add("");
		}
		
		content.add("	<select id=\"queryByKey\" resultClass=\"" + tableObject + "\"  parameterClass=\"" + hashMapObj + "\">");
		content.add("		select " + selectColumn);
		content.add("		from " + owner + "." + table.getTableName().toLowerCase());
		content.add("		<dynamic prepend=\"where\">");
		content.addAll(keyWhereClause);
		content.add("		</dynamic>");
		if (orderByClause.length() > 0) {
			content.add("		order by " + orderByClause);
		} else {
			content.addAll(orderByKey);
		}
		content.add("	</select>");
		
		content.add("");
		
		content.add("	<select id=\"queryByRowid\" resultClass=\"" + tableObject + "\"  parameterClass=\"" + hashMapObj + "\">");
		content.add("		select " + selectColumn);
		content.add("		from " + owner + "." + table.getTableName().toLowerCase());
		content.add("		where rowid = #rowid#");
		content.add("	</select>");
		
		content.add("");
		
		content.add("	<update id=\"updateByRowid\" parameterClass=\"" + hashMapObj + "\">");
		content.add("		update " + owner + "." + table.getTableName().toLowerCase());
		content.add("		<dynamic prepend=\"set\">");
		content.addAll(updateColumn);
		content.add("		</dynamic>");
		content.add("		where rowid = #rowid#");
		content.add("	</update>");
		
		content.add("");
		
		if (rcvTable.containsKey(table.getTableName().toUpperCase())) {
			content.add("	<update id=\"updateByRcv\" parameterClass=\"" + hashMapObj + "\">");
			content.add("		update " + owner + "." + table.getTableName().toLowerCase());
			content.add("		<dynamic prepend=\"set\">");
			content.addAll(updateColumnByRcv);
			content.add("		</dynamic>");
			content.add("		where rcvyy = #rcvyy#");
			content.add("		  and rcvno = #rcvno#");
			content.add("	</update>");

			content.add("");
		}
		
		if (crmTable.containsKey(table.getTableName().toUpperCase())) {
			content.add("	<update id=\"updateByCrm\" parameterClass=\"" + hashMapObj + "\">");
			content.add("		update " + owner + "." + table.getTableName().toLowerCase());
			content.add("		<dynamic prepend=\"set\">");
			content.addAll(updateColumnByCrm);
			content.add("		</dynamic>");
			content.add("		where crmyy = #crmyy#");
			content.add("		  and crmid = #crmid#");
			content.add("		  and crmno = #crmno#");
			content.add("	</update>");

			content.add("");
		}
		
		if (rcvSysidTable.containsKey(table.getTableName().toUpperCase())) {
			content.add("	<update id=\"updateByRcvSysid\" parameterClass=\"" + hashMapObj + "\">");
			content.add("		update " + owner + "." + table.getTableName().toLowerCase());
			content.add("		<dynamic prepend=\"set\">");
			content.addAll(updateColumnByRcvSysid);
			content.add("		</dynamic>");
			content.add("		where rcvyy = #rcvyy#");
			content.add("		  and rcvno = #rcvno#");
			content.add("		  and sysid = #sysid#");
			content.add("	</update>");

			content.add("");
		}
		
		if (crmSysidTable.containsKey(table.getTableName().toUpperCase())) {
			content.add("	<update id=\"updateByCrmSysid\" parameterClass=\"" + hashMapObj + "\">");
			content.add("		update " + owner + "." + table.getTableName().toLowerCase());
			content.add("		<dynamic prepend=\"set\">");
			content.addAll(updateColumnByCrmSysid);
			content.add("		</dynamic>");
			content.add("		where crmyy = #crmyy#");
			content.add("		  and crmid = #crmid#");
			content.add("		  and crmno = #crmno#");
			content.add("		  and sysid = #sysid#");
			content.add("	</update>");

			content.add("");
		}

		content.add("	<delete id=\"deleteByRowid\" parameterClass=\"" + hashMapObj + "\">");
		content.add("		delete " + owner + "." + table.getTableName().toLowerCase());
		content.add("		where rowid = #rowid#");
		content.add("	</delete>");
		
		content.add("");
		
		content.add("	<delete id=\"deleteByKey\" parameterClass=\"" + hashMapObj + "\">");
		content.add("		delete " + owner + "." + table.getTableName().toLowerCase());
		content.add("		<dynamic prepend=\"where\">");
		content.addAll(keyWhereClause);
		content.add("		</dynamic>");
		content.add("	</delete>");
		
		content.add("");
		
		content.add("	<insert id=\"insert\" parameterClass=\"" + tableObject + "\">");
		content.add("		insert into " + owner + "." + table.getTableName().toLowerCase());
		content.add("			(" + allInsertColumn + ")");
		content.add("		values");
		content.add("			(" + allInsertValue + ")");
		content.add("	</insert>");
		
		content.add("");
		
		content.add("	<insert id=\"insertSelective\" parameterClass=\"" + hashMapObj + "\">");
		content.add("		insert into " + owner + "." + table.getTableName().toLowerCase());
		content.add("		<dynamic prepend=\"(\" >");
		content.addAll(insertColumn);
		content.add("			)");
		content.add("		</dynamic>");
		content.add("		values");
		content.add("		<dynamic prepend=\"(\" >");
		content.addAll(insertValue);
		content.add("			)");
		content.add("		</dynamic>");
		content.add("	</insert>");
		
		content.add("");
		
		content.add("</sqlMap>");

		String filenm = sqlMapXmlDir + table.getTableName() + ".xml";
		writeFile(filenm, content);
	}
	
	private void genJavaModel(Table table) {
		List content = new ArrayList();
		
		List columnList = table.getTabColumnList();
		
		String extendsClassPackage = "";
		String extendsClass = javaModelExtendsClass;
		int extendsClassPackageLastIndex = javaModelExtendsClass.lastIndexOf(".");
		if (extendsClassPackageLastIndex > 0) {
			extendsClassPackage = javaModelExtendsClass.substring(0, extendsClassPackageLastIndex);
			extendsClass = javaModelExtendsClass.substring(extendsClassPackageLastIndex + 1);
		}
		
		content.add("package " + javaModelPackage + ";");
		content.add("");
		
		if (extendsClassPackage.length() > 0 && !extendsClassPackage.equals(javaModelPackage)) {
			content.add("import " + javaModelExtendsClass + ";");
			content.add("");
		}
		
		StringBuffer classDeclaration = new StringBuffer();
		classDeclaration.append("public class " + table.getTableName());
		if (extendsClass.length() > 0) {
			classDeclaration.append(" extends " + extendsClass);
		}
		classDeclaration.append(" {");
		
		content.add("/**");
		content.add(" * " + owner.toUpperCase() + "." + table.getTableName() + " " + table.getTableComment() + "<br>");
		content.add(" * generated by " + this.getClass().getSimpleName() + " on " + new java.sql.Timestamp(System.currentTimeMillis()) + "<br>");
		content.add(" * ");
		content.add(" */");
		content.add(classDeclaration);
		content.add("");
		
		List fieldDeclaration = new ArrayList();
		List getterAndSetterMethod = new ArrayList();
		for (int i = 0; i < columnList.size(); i++) {
			TabColumn column = (TabColumn) columnList.get(i);
			
			fieldDeclaration.add("	/**");
			fieldDeclaration.add("	 * " + owner.toUpperCase() + "." + table.getTableName() + "." + column.getColumnName() + " " + column.getColumnComment());
			fieldDeclaration.add("	 */");
			fieldDeclaration.add("	private String " + column.getColumnName().toLowerCase() + " = \"\";");
			fieldDeclaration.add("");
			
			String MethodName = column.getColumnName().substring(0, 1).toUpperCase() + column.getColumnName().substring(1).toLowerCase();
			
			getterAndSetterMethod.add("	/**");
			getterAndSetterMethod.add("	 * @return " + owner.toUpperCase() + "." + table.getTableName() + "." + column.getColumnName() + " " + column.getColumnComment());
			getterAndSetterMethod.add("	 */");
			getterAndSetterMethod.add("	public String get" + MethodName + "() {");
			getterAndSetterMethod.add("		return " + column.getColumnName().toLowerCase() + ";");
			getterAndSetterMethod.add("	}");
			getterAndSetterMethod.add("");
			
			getterAndSetterMethod.add("	/**");
			getterAndSetterMethod.add("	 * @param " + column.getColumnName().toLowerCase() + " " + owner.toUpperCase() + "." + table.getTableName() + "." + column.getColumnName() + " " + column.getColumnComment());
			getterAndSetterMethod.add("	 */");
			getterAndSetterMethod.add("	public void set" + MethodName + "(String " + column.getColumnName().toLowerCase() + ") {");
			getterAndSetterMethod.add("		this." + column.getColumnName().toLowerCase() + " = " + column.getColumnName().toLowerCase() + ";");
			getterAndSetterMethod.add("	}");
			getterAndSetterMethod.add("");
		}
		
		content.addAll(fieldDeclaration);
		content.addAll(getterAndSetterMethod);
		
		content.add("}");

		String filenm = javaModelDir + table.getTableName() + ".java";
		writeFile(filenm, content);
	}
	
	private void writeFile(String filenm, List contentList) {
		try {
			logger.info("generate file " + filenm);
			
			String bakFilenm = filenm + ".bak";
			if (isBakFile) {
				logger.info("bak file " + bakFilenm);
			}
			File file = new File(filenm);
			File bakFile = new File(bakFilenm);
			if (file.exists()) {
				if (isBakFile) {
					if (bakFile.exists()) {
						bakFile.delete();
					}
					file.renameTo(bakFile);
				}
				file.delete();
			}

			BufferedWriter bufWriter = new BufferedWriter(new FileWriter(filenm));
			for (int i = 0; i < contentList.size(); i++) {
				bufWriter.write(contentList.get(i).toString());
				bufWriter.newLine();
			}
			bufWriter.close();
			
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
	
	public List getTables() {
		int columnNum = 1;
		sql.setLength(0);
		sql.append("select table_name from all_tables where owner = '" + owner.toUpperCase() + "' order by table_name");
		logger.info(sql);
		return db.doSqlSelect(sql.toString(), columnNum, false);
	}
	
	private String getTableComment(String tableName) {
		String tableComment = "";
		int columnNum = 1;
		sql.setLength(0);
		sql.append("select comments ");
		sql.append("from all_tab_comments ");
		sql.append("where owner = '" + owner.toUpperCase() + "' ");
		sql.append("and table_name = '" + tableName + "' ");
		logger.info(sql);
		Vector vt = db.doSqlSelect(sql.toString(), columnNum, false);
		if (vt.size() > 0) {
			tableComment = vt.get(0).toString();
		}
		return tableComment.replaceAll("--", "-");
	}
	
	private void setColumns(Table table) {
		int columnNum = 4;
		sql.setLength(0);
		sql.append("select column_name, ");
		sql.append("(select comments from all_col_comments where owner = a.owner and table_name = a.table_name and column_name = a.column_name and rownum <= 1) as comments, ");
		sql.append("data_type, ");
		sql.append("data_length ");
		sql.append("from all_tab_columns a ");
		sql.append("where owner = '" + owner.toUpperCase() + "' ");
		sql.append("and table_name = '" + table.getTableName() + "' ");
		sql.append("order by column_id ");
		logger.info(sql);
		Vector vt = db.doSqlSelect(sql.toString(), columnNum, false);
		for (int i = 0; i < vt.size() / columnNum; i++) {
			TabColumn column = new TabColumn();
			column.setColumnName(vt.get(i * columnNum + 0).toString());
			column.setColumnComment(vt.get(i * columnNum + 1).toString().replaceAll("--", "-"));
			column.setDataType(vt.get(i * columnNum + 2).toString());
			column.setDataLength(vt.get(i * columnNum + 3).toString());
			table.addColumn(column);
		}
	}
	
	private void setPrimaryKeys(Table table) {
		int columnNum = 3;
		sql.setLength(0);
		sql.append("select column_name, ");
		sql.append("(select comments from all_col_comments where owner = a.owner and table_name = a.table_name and column_name = a.column_name and rownum <= 1) as comments, ");
		sql.append("(select data_type from all_tab_columns where owner = a.owner and table_name = a.table_name and column_name = a.column_name and rownum <= 1) as dataType ");
		sql.append("from all_cons_columns a ");
		sql.append("where owner = '" + owner.toUpperCase() + "' ");
		sql.append("and table_name = '" + table.getTableName() + "' ");
		sql.append("and constraint_name = '" + table.getTableName() + "_PK' ");
		sql.append("order by position ");
		logger.info(sql);
		Vector vt = db.doSqlSelect(sql.toString(), columnNum, false);
		for (int i = 0; i < vt.size() / columnNum; i++) {
			TabPrimaryKey pk = new TabPrimaryKey();
			pk.setColumnName(vt.get(i * columnNum + 0).toString());
			pk.setColumnComment(vt.get(i * columnNum + 1).toString());
			pk.setDataType(vt.get(i * columnNum + 2).toString());
			table.addPrimaryKey(pk);
		}
	}
}
