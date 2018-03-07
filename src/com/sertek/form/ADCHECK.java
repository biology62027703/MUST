package com.sertek.form;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.sertek.util.CheckObject;

public class ADCHECK {
	private String ldap_url = "ldap://192.168.1.60:3268";
	Hashtable MUST_GROUP = new Hashtable();
	private String username = "";
	private String password = "";
	private String[] GROUP= {"管理部 ADM","資料暨分配部 DD","法務暨授權部 LL","MGT","資訊部 IT"};
	Hashtable<String, String> env = new Hashtable<String,String>();
	public ADCHECK(String username, String password) {
		this.username = username;
		this.password = password;
		MUST_GROUP.put("管理部 ADM", "1");
		MUST_GROUP.put("資料暨分配部 DD", "2");
		MUST_GROUP.put("法務暨授權部 LL", "3");
		MUST_GROUP.put("MGT", "4");
		MUST_GROUP.put("資訊部 IT", "5");
	}
    public String LDAP_AUTH_AD() throws Exception {     
        
        String retval="";
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldap_url);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "MUST\\" + username);
        env.put(Context.SECURITY_CREDENTIALS, password);
		
        LdapContext ctx = null;
        try {
            ctx = new InitialLdapContext(env, null);            
        	ctx.setRequestControls(null);
            String filter = "(&(objectClass=user)(sAMAccountName="+username+"))";
            NamingEnumeration<?> namingEnum = ctx.search("dc=must,dc=org,dc=tw", filter, getSimpleSearchControls());
            while (namingEnum.hasMore ()) {
                SearchResult result = (SearchResult) namingEnum.next ();    
                for (int i=0;i<this.GROUP.length;i++) {
                	if(result.getName().indexOf(this.GROUP[i])>-1) {
                		CheckObject check = new CheckObject();
                		retval = (String)check.checkNull(MUST_GROUP.get(this.GROUP[i]).toString(), "");
                		if(retval.equals("")) {
                			retval = "該帳號權限無法進入此系統!";
                		}
                	}
                }                
            }
            namingEnum.close();
    		return retval;
        } catch (javax.naming.AuthenticationException e) {
        	retval = "登入失敗- [AD帳號] 或 [AD密碼] 輸入錯誤 !";
            //logger.warn(username + ":登入失敗- [員工編號] 或 [AD密碼] 輸入錯誤 ! => " + e.getMessage());
        } catch (javax.naming.CommunicationException e) {
        	retval = "登入失敗- 找不到認證主機 !";
            //logger.error(username + ":登入失敗- 找入到認證主機 ! => " + e.getMessage());
        } catch (Exception e) {         
        	retval = "登入失敗- 發生未知的錯誤，請洽系統管理員 !"+e;
            //logger.error(username + ":登入失敗- 發生未知的錯誤，請洽系統管理員 ! => " + e.getMessage());
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                    return retval;
                } catch (NamingException e) {
                	throw new Exception(username + ":ctx.close()發生錯誤 ! => " + e.getMessage());
                } 
            }
        }
		return retval;   
        
    }
    
    public String getgroup() throws NamingException {
    	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldap_url);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "MUST\\" + username);
        env.put(Context.SECURITY_CREDENTIALS, password);
        System.out.println(env.toString());
        String retval="";
        LdapContext ctx = null;
    	ctx.setRequestControls(null);
        String filter = "(&(objectClass=user)(sAMAccountName="+username+"))";
        NamingEnumeration<?> namingEnum = ctx.search("dc=must,dc=org,dc=tw", filter, getSimpleSearchControls());
        while (namingEnum.hasMore ()) {
            SearchResult result = (SearchResult) namingEnum.next ();    
            for (int i=0;i<this.GROUP.length;i++) {
            	if(result.getName().indexOf(this.GROUP[i])>-1) {
            		CheckObject check = new CheckObject();
            		retval = (String)check.checkNull(MUST_GROUP.get(this.GROUP[i]).toString(), "");
            		if(retval.equals("")) {
            			
            		}
            		break;
            	}
            }                
        }
        namingEnum.close();
		return retval;
    }
    
    private static SearchControls getSimpleSearchControls() {
    	SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setTimeLimit(30000);
        String[] attrIDs = {"objectGUID"};
        searchControls.setReturningAttributes(attrIDs);
        return searchControls;
    }
	
    
    
    
    public static void main(String[] args) {
		String ldapURL = "ldap://192.168.1.60:3268";
		String account = "james.Huang";
		String password = "f62027704F";
		Hashtable<String, String> env = new Hashtable<String,String>();
        String retval="";
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapURL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "MUST\\" + account);
        env.put(Context.SECURITY_CREDENTIALS, password);
        LdapContext ctx = null;
        try {
            ctx = new InitialLdapContext(env, null);
            ctx.setRequestControls(null);
            String filter = "(&(objectClass=group))";
            NamingEnumeration<?> namingEnum = ctx.search("dc=must,dc=org,dc=tw", filter, getSimpleSearchControls());
            while (namingEnum.hasMore ()) {
                SearchResult result = (SearchResult) namingEnum.next ();    
                Attributes attrs = result.getAttributes ();
                System.out.println(result.getName());
            }
            namingEnum.close();
        } catch (javax.naming.AuthenticationException e) {
        	retval = "登入失敗- [員工編號] 或 [AD密碼] 輸入錯誤 !";
            //logger.warn(username + ":登入失敗- [員工編號] 或 [AD密碼] 輸入錯誤 ! => " + e.getMessage());
        } catch (javax.naming.CommunicationException e) {
        	retval = "登入失敗- 找不到認證主機 !";
            //logger.error(username + ":登入失敗- 找入到認證主機 ! => " + e.getMessage());
        } catch (Exception e) {         
        	retval = "登入失敗- 發生未知的錯誤，請洽系統管理員 !"+e;
            //logger.error(username + ":登入失敗- 發生未知的錯誤，請洽系統管理員 ! => " + e.getMessage());
        }
	}
}
