package com.sertek.util;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class RequestUtil {
	
	protected static Logger logger = Logger.getLogger("com.sertek.util.RequestUtil"); 

    public RequestUtil() {
    }

    public static String getParameter(HttpServletRequest request, String keyname, String defaultvalue) {
        String ret = request.getParameter(keyname);
        if(ret == null)
            ret = defaultvalue;
        return ret;
    }
    
    /**
     * <pre>
     * e.g. key = {"crmyy", "crmid", "crmno"} <br>
     * 		RequestUtil.toURLParams(key, request)  <br> 
     * 		回傳 "&crmyy=096&crmid=如果是中文就會是編碼過的&crmno=000001"
     * </pre> 
     * @param key
     * @param request
     * @return
     */
    public static String toURLParams(String[] key, HttpServletRequest request) {
    	return toURLParams(key, request, true);
    }
    
    public static String toURLParams(String[] key, HttpServletRequest request, boolean isEncode) {
    	Map map = new HashMap();
    	Enumeration enu = request.getParameterNames();		
		while(enu.hasMoreElements()){
			String param = (String)enu.nextElement();
			if (request.getParameter(param)!=null) {
				map.put(param,request.getParameter(param));
			} else {
				map.put(param, "");
			}		
		}
    	return toURLParams(key, map, isEncode);
    }    
    
    /**
     * 可吃 Hashtable... 只要是 implements Map 的都吃<br>
     * <pre>
     * e.g. key = {"crmyy", "crmid", "crmno"} <br>
     * 		RequestUtil.toURLParams(key, request)  <br> 
     * 		回傳 "&crmyy=096&crmid=如果是中文就會是編碼過的&crmno=000001"
     * </pre> 
     * @param key
     * @param request
     * @return
     */
    public static String toURLParams(String[] key, Map map) {
    	return toURLParams(key, map, true);
    }
    
    public static String toURLParams(String[] key, Map map, boolean isEncode) {
    	StringBuffer ret = new StringBuffer();
    	for(int i=0; i<key.length; i++) {
    		Object value = map.get(key[i]) == null ? "" : map.get(key[i]);
    		if(value instanceof String[]) {
    			String[] valueArr = (String[])value;
    			for(int v=0; v<valueArr.length; v++) {
    				ret.append(getKeyValuePair(key[i], valueArr[v], isEncode));
    			}
    		} else {
    			ret.append(getKeyValuePair(key[i], (String)value, isEncode));
    		}    		
    	}    	
    	logger.debug("toURLParams->" + ret.toString());
    	return ret.toString();
    }
    
    private static String getKeyValuePair(String key, String keyValue, boolean isEncode) {
    	String ret;
    	if(isEncode == true && keyValue.length() != keyValue.getBytes().length) {
			ret = "&" + key + "=" + URLEncoder.encode(keyValue);
		} else {
			ret = "&" + key + "=" + keyValue;
		}
    	return ret;
    }    
}
