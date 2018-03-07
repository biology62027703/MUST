package com.sertek.util;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/*
----------------------------------------------------------------------------------------
問題單號：Bug #3107 - 登入方式變動
修改摘要：LDAPUtil
更新版本：平台轉換
修改人員：eason
修改日期：0970606
----------------------------------------------------------------------------------------
*/

/**
 * For ADCHK01.jsp noad 時 自己輸入AD帳號密碼
 *
 */
public class LDAPUtil {
	
	private DirContext ctx = null;
	
	private Hashtable env = env = new Hashtable();

	String port = "389";

	private String auth = "Simple";
	
	private String errMsg = "";
	
	/**
	 * 開啟LDAP
	 * @param adip
	 * @param adusrid
	 * @param adpswd
	 * @throws Exception
	 */
	public void openConnection(String adip, String adusrid, String adpswd) throws Exception {
		try {
			
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://" + adip + ":" + port);
			//env.put("com.sun.jndi.ldap.connect.pool", "true");
			env.put(Context.SECURITY_PRINCIPAL, adusrid);
			env.put(Context.SECURITY_CREDENTIALS, adpswd);
			env.put(Context.SECURITY_AUTHENTICATION, auth);
			
			ctx = new InitialDirContext(env);
		} catch (AuthenticationException ae) {
			errMsg = "AD Server認證失敗,請洽資訊室!";
			throw ae;
		} catch (NamingException ne) {
			errMsg = "AD Server連線失敗,請洽資訊室!";
			throw ne;
		}
	}
	
	/**
	 * 關閉LDAP
	 *
	 */
	public void closeConnection() {
		try {
			if (ctx != null)
				ctx.close();
		} catch (Exception ignore) {
		}
	}
	
	/**
	 * 查詢資料
	 * 例:getBindName("DC=ke9020,DC=acer,DC=com", "(CN=eason)");
	 * @param baseDN
	 * @param searchName
	 * @return
	 * @throws Exception
	 */
	public String getBindName(String baseDN, String searchName) throws Exception{
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration search = ctx.search(baseDN, searchName, constraints);
			while (search.hasMore()) {
				SearchResult searchResult = (SearchResult) search.next();
				return searchResult.getName();
			}
		} catch (PartialResultException ignore) {
			//System.out.println(ignore.toString());
		} catch (NamingException ne) {
			errMsg = "AD Server取得帳號失敗,請洽資訊室!";
			throw ne;
		}

		return "";
	}
	
	public void searchAll(String baseDN) {
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration search = ctx.search(baseDN, "CN=*", constraints);
			while (search.hasMore()) {
				SearchResult searchResult = (SearchResult) search.next();
				searchResult.getName();
			}
		} catch (Exception ignore) {
		}
	}
	
	/**
	 * 查詢全部資料(測試用)
	 * 例:getAllBindName("DC=ke9020,DC=acer,DC=com");
	 * @param baseDN
	 * @return
	 * @throws Exception
	 */
	public void getAllBindName(String baseDN) throws Exception{
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration search = ctx.search(baseDN, "CN=*", constraints);
			while (search.hasMore()) {
				SearchResult searchResult = (SearchResult) search.next();
				System.out.println("searchResult.getName() = " + searchResult.getName());
			}
		} catch (PartialResultException ignore) {
		} catch (NamingException ne) {
			throw ne;
		}
	}
	
	public String getErrMsg() {
		return this.errMsg;
	}
	
	public static void main(String[] args) {
		LDAPUtil ldap = new LDAPUtil();
		try {
			String adip = "172.16.80.6";
			String adusrid = "CN=acer_tpj,OU=TPJ,DC=AD,DC=INTRAJ";
			String adpswd = "Judl@acer";
			adusrid = "CN=administrator,CN=Users,DC=AD,DC=INTRAJ";
			adpswd = "ad123";
			//adusrid = "CN=acer_test,OU=TPD,DC=AD,DC=INTRAJ";
			//adpswd = "acer_test";
			//adusrid = "CN=ab000000\\ ,OU=TPD,DC=AD,DC=INTRAJ";
			//adpswd = "ab000000";
			String baseDN = "DC=AD,DC=INTRAJ";
			ldap.openConnection(adip, adusrid, adpswd);
			System.out.println(ldap.ctx.getAttributes("CN=ab000000,OU=TPD,DC=AD,DC=INTRAJ"));
			
			//ldap.ctx.rename("CN=ab000000\\ ,OU=TPD,DC=AD,DC=INTRAJ", "CN=ab000000,OU=TPD,DC=AD,DC=INTRAJ");
			/*
			//ldap.getAllBindName(baseDN);
			String bindName = ldap.getBindName(baseDN, "CN=ab000000 ");
			System.out.println("bindName = " + bindName);
			int index = bindName.indexOf("\\");
			if(index > 0){
				if(bindName.indexOf("OU") > 0){
					bindName = bindName.substring(0, index + 1) + " " + bindName.substring(index + 1);
				}else{
					bindName = bindName.substring(0, index + 1) + " " + bindName.substring(index + 2);
				}
			}
			//bindName = "CN=ab000000\\ ,OU=TPD";
			System.out.println("bindName = " + bindName);
			ldap.openConnection(adip, bindName + "," + baseDN, "ab000000");
			String bindName2 = ldap.getBindName(baseDN, "CN=ab000000 ");
			System.out.println("bindName2 = " + bindName2);
			System.out.println(bindName2.equals("ab000000\\\\"));
			*/
		} catch (Exception e) {
			System.out.println(e.toString());
			//e.printStackTrace();
		}
		String errMsg = ldap.getErrMsg();
		System.out.println("errMsg = " + errMsg);
		/*
		LDAPUtil ldap = new LDAPUtil();
		try {
			String adip = "10.24.11.104";
			String adusrid = "CN=noad,OU=TPD,DC=ke9020,DC=acer,DC=com";
			String adpswd = "ACER123";
			ldap.openConnection(adip, adusrid, adpswd);
			//System.out.println(ldap.ctx.getAttributes("CN=noad,OU=TPD,DC=ke9020,DC=acer,DC=com"));
			//ldap.getAllBindName("DC=ke9020,DC=acer,DC=com");
			String bindName = ldap.getBindName("DC=ke9020,DC=acer,DC=com", "CN=testa");
			System.out.println("bindName = " + bindName);
		} catch (Exception e) {
			System.out.println(e.toString());
			//e.printStackTrace();
		}
		String errMsg = ldap.getErrMsg();
		System.out.println("errMsg = " + errMsg);
		*/
	}
	
}
