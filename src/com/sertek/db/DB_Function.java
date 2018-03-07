// Decompiled by DJ v3.2.2.67 Copyright 2002 Atanas Neshkov  Date: 2004/6/2 ¤U¤È 11:24:03
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DB_Function.java

package com.sertek.db;

import com.sertek.util.CheckObject;
import java.util.Hashtable;
import java.util.Vector;

// Referenced classes of package com.sertek.db:
//            DBUtility

public class DB_Function
{

    public DB_Function()
    {
    }

    public int doCount(DBUtility db, String owner, String table, String condition)
    {
        int result = 0;
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) from " + owner + "." + table);
        if(condition.length() > 0)
            sql.append(" where " + condition);
        Vector vr = db.doSqlSelect(sql.toString(), 1, false);
        System.out.println(vr+""+sql);
        if(vr.size() > 0)
            try
            {
                result = Integer.parseInt((String)vr.elementAt(0));
            }
            catch(NumberFormatException _ex) { }
        vr = null;
        sql = null;
        return result;
    }

    public int doDelete(DBUtility db, String owner, String table, String condition)
    {
        int result = 0;
        String sql = "delete from " + owner + "." + table;
        if(condition.length() > 0)
            sql = sql + " where " + condition;
        System.out.println("delete = " + sql);
        result = db.doSqlUpdate(sql);
        sql = null;
        return result;
    }

    public int doInsert(DBUtility db, String owner, String table, Hashtable detail, String key[], String num[])
    {
        int result = 0;
        StringBuffer sql = new StringBuffer();
        String temp = "'";
        CheckObject check = new CheckObject();
        sql.append("insert into " + owner + "." + table + " (");
        for(int i = 0; i < key.length; i++)
        {
            sql.append(key[i]);
            if(i + 1 < key.length)
                sql.append(",");
        }

        sql.append(") values (");
        for(int i = 0; i < key.length; i++)
        {
            temp = "'";
            for(int j = 0; j < num.length; j++)
            {
                if(!key[i].equals(num[j]))
                    continue;
                temp = "";
                break;
            }

            sql.append(temp + (String)check.checkNull((String)detail.get(key[i]), "") + temp);
            if(i + 1 < key.length)
                sql.append(",");
        }

        sql.append(")");
        System.out.println("\u65B0\u589E = " + sql.toString());
        result = db.doSqlUpdate(sql.toString());
        sql = null;
        check = null;
        temp = null;
        return result;
    }

    public int doInsert(DBUtility db, String owner, String table, Hashtable detail, String key[][])
    {
        int result = 0;
        StringBuffer sql = new StringBuffer();
        String temp = "'";
        CheckObject check = new CheckObject();
        sql.append("insert into " + owner + "." + table + " (");
        for(int i = 0; i < key.length; i++)
        {
            sql.append(key[i][0]);
            if(i + 1 < key.length)
                sql.append(",");
        }

        sql.append(") values (");
        for(int i = 0; i < key.length; i++)
        {
            if(key[i][1].equals("n") || key[i][1].equals("N"))
                temp = "";
            else
                temp = "'";
            
            sql.append(temp + (String)check.checkNull((String)detail.get(key[i][0]), "") + temp);
            if(i + 1 < key.length)
                sql.append(",");
         
        }

        sql.append(")");
        System.out.println("\u65B0\u589E = " + sql.toString());
        result = db.doSqlUpdate(sql.toString());
        sql = null;
        check = null;
        temp = null;
        return result;
    }

    public String doMixSqlString(Vector column, String owner_table[][], String condition, String order)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        for(int i = 0; i < column.size(); i++)
        {
            String temp[] = (String[])column.elementAt(i);
            for(int j = 0; j < temp.length; j++)
            {
                sql.append(owner_table[i][2] + "." + temp[j]);
                if(i + 1 < column.size())
                    sql.append(",");
                else
                if(j + 1 < temp.length)
                    sql.append(",");
            }

        }

        sql.append(" from ");
        for(int i = 0; i < owner_table.length; i++)
        {
            sql.append(owner_table[i][0] + "." + owner_table[i][1] + " " + owner_table[i][2]);
            if(i + 1 < owner_table.length)
                sql.append(",");
        }

        if(condition.trim().length() > 0)
            sql.append(" where " + condition);
        if(order.trim().length() > 0)
            sql.append(" order by " + order);
        System.out.println("sql = " + sql);
        return sql.toString();
    }

    public Vector doSelect(DBUtility db, String owner, String table, String column[], String condition)
    {
        Vector result = new Vector();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        for(int i = 0; i < column.length; i++)
        {
            sql.append(column[i]);
            if(i + 1 < column.length)
                sql.append(",");
        }

        sql.append(" from " + owner + "." + table);
        if(condition.length() > 0)
            sql.append(" where " + condition);
        System.out.println("sql = " + sql);
        result = db.doSqlSelect(sql.toString(), column.length, false);
        sql = null;
        return result;
    }

    public Vector doSelect(DBUtility db, String owner, String table, String column[], String condition, int page, int row)
    {
        Vector result = new Vector();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        for(int i = 0; i < column.length; i++)
        {
            sql.append(column[i]);
            if(i + 1 < column.length)
                sql.append(",");
        }

        sql.append(" from " + owner + "." + table);
        if(condition.length() > 0)
            sql.append(" where " + condition);
        System.out.println("sql = " + sql);
        result = db.doSqlSelect(sql.toString(), column.length, page, row, false);
        sql = null;
        return result;
    }

    public Vector doSelect(DBUtility db, String owner, String table, String column[], String condition, String orderby)
    {
        Vector result = new Vector();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        for(int i = 0; i < column.length; i++)
        {
            sql.append(column[i]);
            if(i + 1 < column.length)
                sql.append(",");
        }

        sql.append(" from " + owner + "." + table);
        if(condition.length() > 0)
            sql.append(" where " + condition);
        if(orderby.length() > 0)
            sql.append(" " + orderby);
        System.out.println("sql = " + sql);
        result = db.doSqlSelect(sql.toString(), column.length, false);
        sql = null;
        return result;
    }

    public Vector doSelect(DBUtility db, Vector column, String owner_table[][], String condition, String order)
    {
        Vector result = new Vector();
        String sql = doMixSqlString(column, owner_table, condition, order);
        int field = getFieldCount(column);
        result = db.doSqlSelect(sql, field, false);
        return result;
    }

    public Vector doSelect(DBUtility db, Vector column, String owner_table[][], String condition, String order, int page, int row)
    {
        Vector result = new Vector();
        String sql = doMixSqlString(column, owner_table, condition, order);
        int field = getFieldCount(column);
        result = db.doSqlSelect(sql, field, page, row, false);
        return result;
    }

    public String doSelectMax(DBUtility db, String owner, String table, String column, String condition)
    {
        String result = "";
        Vector vr = new Vector();
        StringBuffer sql = new StringBuffer();
        sql.append("select max(" + column + ")");
        sql.append(" from " + owner + "." + table);
        if(condition.length() > 0)
            sql.append(" where " + condition);
        System.out.println("sql = " + sql);
        vr = db.doSqlSelect(sql.toString(), 1, false);
        if(vr.size() > 0)
            result = (String)vr.elementAt(0);
        sql = null;
        vr = null;
        return result;
    }

    public String doSelectMin(DBUtility db, String owner, String table, String column, String condition)
    {
        String result = "";
        Vector vr = new Vector();
        StringBuffer sql = new StringBuffer();
        sql.append("select min(" + column + ")");
        sql.append(" from " + owner + "." + table);
        if(condition.length() > 0)
            sql.append(" where " + condition);
        System.out.println("sql = " + sql);
        vr = db.doSqlSelect(sql.toString(), 1, false);
        if(vr.size() > 0)
            result = (String)vr.elementAt(0);
        sql = null;
        vr = null;
        return result;
    }

    public int doSelectToHash(DBUtility db, Vector column, String owner_table[][], String condition, String order, Hashtable req)
    {
        int result = 0;
        Vector vr = new Vector();
        CheckObject check = new CheckObject();
        vr = doSelect(db, column, owner_table, condition, order);
        result = vr.size();
        int count = 0;
        if(result > 0)
        {
            for(int i = 0; i < column.size(); i++)
            {
                String temp[] = (String[])column.elementAt(i);
                for(int j = 0; j < temp.length; j++)
                {
                    req.put(temp[j], vr.elementAt(count));
                    count++;
                }

            }

        }
        vr = null;
        return result;
    }

    public int doUpdate(DBUtility db, String owner, String table, Hashtable detail, String key[], String num[])
    {
        int result = 0;
        StringBuffer sql = new StringBuffer();
        CheckObject check = new CheckObject();
        String temp = "'";
        sql.append("update " + owner + "." + table + " set ");
        for(int i = 0; i < key.length; i++)
        {
            temp = "'";
            for(int j = 0; j < num.length; j++)
            {
                if(!key[i].equals(num[j]))
                    continue;
                temp = "";
                break;
            }

            sql.append(key[i] + " = " + temp + (String)check.checkNull((String)detail.get(key[i]), "") + temp);
            if(i + 1 < key.length)
                sql.append(",");
        }

        if(detail.get("rowid") != null)
            sql.append(" where rowid = '" + (String)detail.get("rowid") + "'");
        System.out.println("sql = " + sql.toString());
        result = db.doSqlUpdate(sql.toString());
        sql = null;
        check = null;
        temp = null;
        return result;
    }

    public int doUpdate(DBUtility db, String owner, String table, Hashtable detail, String key[][])
    {
        int result = 0;
        StringBuffer sql = new StringBuffer();
        CheckObject check = new CheckObject();
        String temp = "'";
        sql.append("update " + owner + "." + table + " set ");
        for(int i = 0; i < key.length; i++)
        {
            if(key[i][1].equals("n") || key[i][1].equals("N"))
                temp = "";
            else
                temp = "'";
            sql.append(key[i][0] + " = " + temp + (String)check.checkNull((String)detail.get(key[i][0]), "") + temp);
            if(i + 1 < key.length)
                sql.append(",");
        }

        if(detail.get("rowid") != null)
            sql.append(" where rowid = '" + (String)detail.get("rowid") + "'");
        System.out.println("sql = " + sql.toString());
        result = db.doSqlUpdate(sql.toString());
        sql = null;
        check = null;
        temp = null;
        return result;
    }

    public int doUpdate(DBUtility db, String owner, String table, Hashtable detail, String key[][], String condition)
    {
        int result = 0;
        StringBuffer sql = new StringBuffer();
        CheckObject check = new CheckObject();
        String temp = "'";
        sql.append("update " + owner + "." + table + " set ");
        for(int i = 0; i < key.length; i++)
        {
            if(key[i][1].equals("n") || key[i][1].equals("N"))
                temp = "";
            sql.append(key[i][0] + " = " + temp + (String)check.checkNull((String)detail.get(key[i][0]), "") + temp);
            if(i + 1 < key.length)
                sql.append(",");
        }

        if(condition.length() > 0)
            sql.append(" where " + condition);
        System.out.println("sql = " + sql.toString());
        result = db.doSqlUpdate(sql.toString());
        sql = null;
        check = null;
        temp = null;
        return result;
    }

    public int getFieldCount(Vector column)
    {
        int result = 0;
        for(int i = 0; i < column.size(); i++)
        {
            String temp[] = (String[])column.elementAt(i);
            result += temp.length;
        }

        return result;
    }
}