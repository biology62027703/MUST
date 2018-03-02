<%@page import="java.util.*"%>
<%!
private	String getCodeArray(String code, String name, com.sertek.db.DBUtility db) throws Exception
{
	java.sql.ResultSet		rs			=	null;
	Exception		ex			=	null;
	String[]		template	= 	null;
	StringBuffer	js			=	null;

	try
	{
		js	=	new StringBuffer();

		template = getCodeTemplate(code, name);

		if (!template[0].equals(""))
		{
			rs = db.doSqlSelect(template[0]);

			js.append("var _code_").append(name).append(" = new Array('").append(template[1]).append("','").append(template[2]).append("','").append(template[3]).append("','").append(template[4]).append("','").append(template[5]).append("');\n");

			while (rs.next())
			{
				for (int i = 1 ; i <= Integer.parseInt(template[2]) ; i++)
				{
					js.append("_code_").append(name).append(".push('");
					if (rs.getString(i) != null)
					{
						js.append(db.transCharset4db2Client(rs.getString(i)));
					}
					js.append("');\n");
				}
			}
			rs.close();
		}
	}
	catch (Exception e)
	{
		ex = e;
	}
	finally
	{
		if (rs != null)
		{
			rs.close();
		}
	}

	if (ex != null)
	{
		throw ex;
	}

	return js.toString();
}

private String[] getCodeTemplate(String code, String name) throws Exception
{
	String[] template = new String[6];
	
	switch(Integer.parseInt(code))
	{
		case 1 : //MP1A01 速別
			template[0] = "SELECT ID, DATA FROM P.PHRASE WHERE NAME = '" + name + "' ORDER BY ID";
			template[1] = "P." + name;
			template[2] = "2"; //欄位長度
			template[3] = "2"; //開窗顯示的欄位
			template[4] = "2"; //回傳值的欄位
			template[5] = "2"; //搜尋值的欄位
			break;
		case 2 : //MP1A01 來文機關
			template[0] = "SELECT ID, DATA FROM P.PHRASE WHERE NAME = '" + name + "' ORDER BY ID";
			template[1] = "P." + name;
			template[2] = "2";
			template[3] = "1,2";
			template[4] = "1,2";
			template[5] = "1,2";
			break;
		case 3 : //MP1A01 文別
			template[0] = "SELECT ID, DATA FROM P.PHRASE WHERE NAME = '" + name + "' ORDER BY ID";
			template[1] = "P." + name;
			template[2] = "2"; //欄位長度
			template[3] = "1,2"; //開窗顯示的欄位
			template[4] = "1,2"; //回傳值的欄位
			template[5] = "1,2"; //搜尋值的欄位
			break;
		case 4 : //MP1A01 附件件數
			template[0] = "SELECT ID, DATA FROM P.PHRASE WHERE NAME = '" + name + "' ORDER BY ID";
			template[1] = "P." + name;
			template[2] = "2"; //欄位長度
			template[3] = "2"; //開窗顯示的欄位
			template[4] = "2"; //回傳值的欄位
			template[5] = "2"; //搜尋值的欄位
			break;
		case 5 : //MP1A01 主旨摘要
			template[0] = "SELECT ID, DATA FROM P.PHRASE WHERE NAME = '" + name + "' ORDER BY ID";
			template[1] = "P." + name;
			template[2] = "2"; //欄位長度
			template[3] = "1,2"; //開窗顯示的欄位
			template[4] = "1,2"; //回傳值的欄位
			template[5] = "1,2"; //搜尋值的欄位
			break;
	}
	return template;
}

%>
