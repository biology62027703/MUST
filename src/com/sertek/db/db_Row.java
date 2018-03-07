/*
 **************************************************************************************************
 *                         		      Sertek, Inc.
 *
 * 	CONFIDENTIAL AND PROPRIETARY SOURCE CODE OF Sertek INC.
 *
 * 	Copyright (c) 2000 Sertek, Inc. All Rights Reserved.
 * 	Use of this Source Code is subject to the terms of the applicable
 * 	license agreement from Sertek, Inc.  The c opyright notice(s) 
 * 	in this Source Code does not indicate actual or intended publication 
 * 	of this Source Code.
 *
 *	File Name     : db_Row.java    
 *	Author        : Felix Lin              	
 *	Created Date  : 2001/5/26          	     
 *	Modified By   : Felix Lin              	
 *	Last Modified : 2001/5/26
 *	Description   : �A�i�H�� resultSet ����Ʀs�i�� 
 *                      �A�Ω��ƶq���p���α`�ϥΪ� table �ާ@
 *
 *     
 ***************************************************************************************************** 
*/
package com.sertek.db;

import java.sql.*;
import java.util.*;
import com.sertek.sys.*;

/**
 *   
 *   �i�N resultSet ����Ʀs�i�� <BR>
 *   �A�Ω��ƶq���p���α`�ϥΪ� table �ާ@
 *
 *   db_Row row = new db_Row(); <BR>
 *   try {                      <BR>
 *       row.setResult(rs);     <BR>
 *   ....                       <BR>
 *
 *   �A�]�i�H �~�� extend db_Row ���U�ؤ��P���γ~
 *   �ѷ� com.sertek.sys.sys_User
 *
 */
public class db_Row {

  private int size;
  private int cursor;
  private int cols;
  private ResultSetMetaData meta;
  protected Hashtable table;



  // the same with db_Db charset setting
  private static String dbCharset = null;
  private static String clientCharset = "UTF-8";
  private static boolean _charset = false; // to see whether char need transfer

  // static initializer
  static {

      dbCharset = (String) sys_cfg.getValue("db_charset");
      if( dbCharset == null ) dbCharset = "ISO8859_1" ;
      dbCharset = dbCharset.toUpperCase();
      if( dbCharset.indexOf("8859") != -1 )
      dbCharset = "ISO8859_1";
      if( !dbCharset.equals( clientCharset ))
      _charset = true;
  }

  /**
  *<pre><br>
     db_Row  Constructor<br> 
     �Ѽ�:�L  <br>
     �Ǧ^:�L  <br>
     ����:�إ� db_Row �غc�l<br>
  *</pre>
  */	  
  public db_Row() {}

  /**
  *<pre><br>
     db_Row  Constructor<br> 
     �Ѽ�:ResultSet rs  �ǤJ�q��Ʈw�Ǧ^�� ResultSet<br>
     �Ǧ^:�L  <br>
     ����:�إ� db_Row �غc�l<br>
  *</pre>
  */	  
  public db_Row (ResultSet rs) throws Exception
  {
      setResultSet(rs);
  }
  
  /**
  *<pre><br>
     db_Row  Constructor<br> 
     �Ѽ�:ResultSet rs  �ǤJ�q��Ʈw�Ǧ^�� ResultSet<br>
     �Ǧ^:�L  <br>
     ����: �]�w ResultSet<br>
  *</pre>
  */	
  public void setResultSet(ResultSet rs)throws Exception
  {

    try {
      meta = rs.getMetaData();
      cols = meta.getColumnCount();
      table = new Hashtable();

      for(int i=1 ; i<= cols;i++)
      {
        table.put(meta.getColumnName(i),new Vector());
      }

      size = 0;
      while(rs.next())
      {
        for(int i= 1 ; i <= cols ; i++)
        {
            Vector v = (Vector) table.get(meta.getColumnName(i));
            Object obj = rs.getObject(i);
            if( _charset )
            {
              if( obj != null )
              {
                if( obj instanceof String )
                {
                  String tmp = (String) obj;
                  tmp=tmp.trim();
                  tmp = new String( tmp.getBytes(dbCharset),clientCharset );
                  if( tmp == null) tmp="";
                  obj = tmp;
                }
              }
              else
              {
                  String tmp = (String) obj;
                  if( tmp == null) tmp="";
                  obj = tmp;
              }
            }
            v.addElement(obj);
        }
        size++;
      }
      cursor = 0; // put cursor to first position
    } catch (Exception e) {
      if (table != null)
      {
        table.clear();
        table = null;
      }
    throw e;
    }
  }

  /**
  *<pre><br>
     �Ѽ�:�L  <br>
     �Ǧ^:�L  <br>
     ����: �Ǧ^ ResultSetMetaData <br>
  *</pre>
  */	 
  public ResultSetMetaData getMeta()
  {
       return meta;
  }

  /**
  *<pre><br>
     �Ѽ�:�L  <br>
     �Ǧ^:�L  <br>
     ����: �ˬd cursor �� table �O�_�����D <br>
  *</pre>
  */	
  public boolean check()
  {
     if ( table == null ) return false;
     if (( size < 0 )||(cursor >= size)) return false;
     return true;
  }

 
  /**
  *<pre><br>
     �Ѽ�:String field  :���W��<br>
     �Ǧ^:�L  <br>
     ����: �ұo�쪺�O�ثe ROW �� "Column" field <br>
  *</pre>
  */	
  public Object getColumn(String field)
  {
       
     if (!check()) return null;
     field = field.toUpperCase();
     Vector v = (Vector) table.get(field);
     if (v==null) return null;
     if(( cursor >= 0) && (cursor < size))
        return v.elementAt(cursor);
     return null;
  }
   
  /**
  *<pre><br>
     �Ѽ�:int idx:����m  <br>
     �Ǧ^:�L  <br>
     ����: �ұo�쪺�O�ثe ROW �� "Column" field <br>
           idx is between 0 to ColumnCount-1<br>
  *</pre>
  */	 
  public Object getColumn(int idx)
  {
      try {
          String name = meta.getColumnName(idx+1);
          return getColumn(name);
      }catch(Exception e) {
          return null;
      }
  }
  /**
  *<pre><br>
     �Ѽ�:�L  <br>
     �Ǧ^:�L  <br>
     ����: �o����쪺 �ƥ� <br>
  *</pre>
  */	 
  public int getColumCount()
    {
        try {
            return meta.getColumnCount();
        }catch(Exception e){
            return -1;
        }
    }

  /**
  *<pre><br>
     �Ѽ�:�L  <br>
     �Ǧ^:�L  <br>
     ����: ���ղ��ʨ�ĴX���|���|���\<br>
           ���ä��n�u���� cursor �� move <br>
  *</pre>
  */	   
  public boolean test(int idx)
  {
     if (!check()) return false;
     if ((idx >= 0) && (idx < size)) {
          return true;
     }
     return false;
  }

  /**
  *<pre><br>
     �Ѽ�:�L  <br>
     �Ǧ^:�L  <br>
     ����: ���ʨ�ĴX�� , between<br>
           0 �M size()-1 <br>
  *</pre>
  */	 
  public boolean absolute(int idx)
  {
     if ( test(idx) )
     {
        cursor = idx;
        return true;
     }
     return false;
  }
  
  /**
  *<pre><br>
     �Ѽ�:�L  <br>
     �Ǧ^:�L  <br>
     ����: ���ʨ�̫�@��<br>
  *</pre>
  */	 
  public boolean last()
  {
      if (test(size()-1))
      {
          cursor = size() - 1;
          return true;
      }
      return false;
  }

  /**
  *<pre><br>
     �Ѽ�:�L  <br>
     �Ǧ^:�L  <br>
     ����:table �� ROW size<br>
  *</pre>
  */	
  public int size()
  {
     return size;
  }

   
  /**
  *<pre><br>
     �Ѽ�:�L  <br>
     �Ǧ^:�L  <br>
     ����: ����Ĥ@��<br>
  *</pre>
  */	
  public boolean first()
  {
     if(!check()) return false;
     cursor = 0;
     return true;
  }
  
  /**
  *<pre><br>
     �Ѽ�:�L  <br>
     �Ǧ^:�L  <br>
     ����: ����e�@��<br>
  *</pre>
  */	
  public boolean previous()
  {
     if ( test(cursor-1) )
     {
        cursor--;
        return true;
     }
     return false;
  }

  /**
  *<pre><br>
     �Ѽ�:�L  <br>
     �Ǧ^:�L  <br>
     ����: ����U�@��<br>
  *</pre>
  */	
  public boolean next ()
  {
     if (( cursor+1 ) < size )
     {
        cursor++;
        return true;
     }
     return false;
  }

  /**
  *<pre><br>
     �Ѽ�:�L  <br>
     �Ǧ^:�L  <br>
     ����: �Ǧ^�ثe Cursor �� index number<br>
  *</pre>
  */	
  
  public int getrow()
  {
     return cursor;
  }

  /**
  *<pre><br>
     �Ѽ�:String SQL :�d�M��ƪ� SQL ���O<br>
     �Ǧ^:�L  <br>
     ����: �ǤJ�@ SQL ���O,�Ǧ^��M�� db_Row<br>
  *</pre>
  */	
  public static db_Row executeSQL (String SQL) throws Exception
  {
      db_Row row = null;
      Connection oConnect  = null;
      Statement stmt = null;
      ResultSet rs = null;
      try {
          oConnect = ConPool.getConnection();
          stmt = oConnect.createStatement();
          stmt.executeQuery(SQL);
          rs = stmt.getResultSet();
          row = new db_Row(rs);
          return row;
      } catch (Exception e) {
          throw e;
      }finally {
          if ( rs != null ) try { rs.close(); } catch ( Exception ignore) {}
          if ( stmt != null ) try { stmt.close(); } catch(Exception ignore1){}
          if ( oConnect != null ) try { oConnect.close(); } catch (Exception ignore2){}
      }
  }

}
