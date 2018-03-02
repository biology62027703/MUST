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
		case 1 : //MP1A01 �t�O
			template[0] = "SELECT ID, DATA FROM P.PHRASE WHERE NAME = '" + name + "' ORDER BY ID";
			template[1] = "P." + name;
			template[2] = "2"; //������
			template[3] = "2"; //�}����ܪ����
			template[4] = "2"; //�^�ǭȪ����
			template[5] = "2"; //�j�M�Ȫ����
			break;
		case 2 : //MP1A01 �Ӥ����
			template[0] = "SELECT ID, DATA FROM P.PHRASE WHERE NAME = '" + name + "' ORDER BY ID";
			template[1] = "P." + name;
			template[2] = "2";
			template[3] = "1,2";
			template[4] = "1,2";
			template[5] = "1,2";
			break;
		case 3 : //MP1A01 ��O
			template[0] = "SELECT ID, DATA FROM P.PHRASE WHERE NAME = '" + name + "' ORDER BY ID";
			template[1] = "P." + name;
			template[2] = "2"; //������
			template[3] = "1,2"; //�}����ܪ����
			template[4] = "1,2"; //�^�ǭȪ����
			template[5] = "1,2"; //�j�M�Ȫ����
			break;
		case 4 : //MP1A01 ������
			template[0] = "SELECT ID, DATA FROM P.PHRASE WHERE NAME = '" + name + "' ORDER BY ID";
			template[1] = "P." + name;
			template[2] = "2"; //������
			template[3] = "2"; //�}����ܪ����
			template[4] = "2"; //�^�ǭȪ����
			template[5] = "2"; //�j�M�Ȫ����
			break;
		case 5 : //MP1A01 �D���K�n
			template[0] = "SELECT ID, DATA FROM P.PHRASE WHERE NAME = '" + name + "' ORDER BY ID";
			template[1] = "P." + name;
			template[2] = "2"; //������
			template[3] = "1,2"; //�}����ܪ����
			template[4] = "1,2"; //�^�ǭȪ����
			template[5] = "1,2"; //�j�M�Ȫ����
			break;
	}
	return template;
}

%>
