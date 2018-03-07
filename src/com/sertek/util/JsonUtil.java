package com.sertek.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
/**
 * 處理 Json
 * @author wengchi
 * 需要json-lib-2.4-jdk13.jar,commons-logging-1.1.1.jar,ezmorph-1.0.6.jar,commons-beanutils-1.8.3.jar,commons-collections-3.2.1.jar
 *
 */
public class JsonUtil {
	
	/**
	 * 取得 json 陣列
	 * <pre>
	 * 例子：
	 *  String jsonStr = "[{'address':'wengchi address','age':0,'name':'王文奇','rmk':''},{'address':'wengchi address','age':1,'name':'王文奇1','rmk':''}]";
	 *  JSONArray jsonArray = JsonUtil.getJsonArray(jsonStr);
	 *  	for(int i=0;i<jsonArray.size();i++){
	*		    JSONObject j = jsonArray.getJSONObject(i);
	*		    System.out.println(j.getString("name"));
	*		    System.out.println(j.getInt("age"));
	*		    System.out.println(j.getString("address"));
	*	   }
	 * </pre>
	 * @param jsonStr json字串
	 * @return
	 */
	public static JSONArray getJsonArray( String jsonStr ){
		  return JSONArray.fromObject(jsonStr);
	}
	
	/**
	 * json 轉成 java 物件陣列
	 * <pre>
	 * 範例：
	 * String jsonStr = "[{'address':'中山北路走九遍','age':34,'name':'王文奇','rmk':''},{'address':'wengchi address','age':12,'name':'王文奇1','rmk':''}]";
	 * List stus = JsonUtil.getJsonArray(jsonStr,StudentBean.class);
	 * for(int i=0;i<stus.size();i++){
	*		StudentBean bean = (StudentBean)stus.get(i);
	*		System.out.println(bean.getName());
	*		System.out.println(bean.getAge());
	*		System.out.println(bean.getAddress());
	*	}
	*</pre>
	 * @param jsonStr
	 * @param pojoClass beanClass
	 * @return List pojoClass陣列
	 */
	public static List   getJsonArray( String jsonStr,Class pojoClass ){
			List l = new ArrayList();
			JSONArray jsonArray = getJsonArray(jsonStr);
			for(int i=0;i<jsonArray.size();i++){
				JSONObject j = jsonArray.getJSONObject(i);
				l.add( JSONObject.toBean(j, pojoClass) );
			}
			return l;
	}
	
	/***
	 * 取得單一 JSONObject 物件
	 * <pre>
	 * String jsonStr = "{'name':'王文奇','age':16}";
	 * JSONObject j = JsonUtil.getJSONObject(jsonStr);
	 * System.out.println((String)j.get("name"));
	 * System.out.println(j.getInt("age"));
	 * </pre>
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject getJSONObject(String jsonStr){
		return   JSONObject.fromObject(jsonStr);
	}
	
	/**
	 * 將 json 字串轉成 map 物件
	 * <pre>
	 * String jsonStr = "{'name':'王文奇','age':16}";
	 * Map m =  JsonUtil.jsonStrToMap(jsonStr);
	 * System.out.println(m.get("name"));
	 * System.out.println(m.get("age"));
	 * </pre>
	 * @param jsonStr
	 * @return
	 */
	public static Map jsonStrToMap(String jsonStr){
		Map retVal = new HashMap();
		JSONObject jsonObject =   JSONObject.fromObject(jsonStr);
		Iterator keyIter  =  jsonObject.keys();
		String key = "";
		Object value;
		while ( keyIter.hasNext()) {
			 key = (String)keyIter.next();
			 value = jsonObject.get(key);
			 retVal.put(key, value);
		 }
		return retVal;
	}
	/***
	 * 取得單一JAVA 物件
	 * <pre>
	 * String jsonStr = "{'name':'王文奇','age':16}";
	 * StudentBean j = (StudentBean)JsonUtil.getJSONObject(jsonStr,StudentBean.class);
	 * System.out.println((String)j.getName());
	 * System.out.println(j.getAge());
	 * </pre>
	 * @param jsonStr
	 * @return
	 */
	public static Object getJSONObject(String jsonStr,Class pojoClass){
		return JSONObject.toBean(JSONObject.fromObject(jsonStr),pojoClass );
	}
	/**
	 * 將 map 裡面的值轉成 json 字串
	 * <pre>
	*		Map<String, Object> map = new HashMap<String, Object>();
	*		map.put("name","wengchi");
	*		map.put("age","16");
	*		System.out.println(JsonUtil.toJsonStr(map));
	 * </pre>
	 * @param map
	 * @return
	 */
	public static String toJsonStr(Map map){
		return JSONSerializer.toJSON(map).toString();
	}
	
	
	/**
	 * 將 List 裡面的值轉成 json 字串
	 * <pre>
	 * List<StudentBean> list = new ArrayList<StudentBean>();	
	*		for (int i=0;i<2;i++){
	*			StudentBean bean = new StudentBean();
	*			bean.setName("wengchi "+ i);
	*			bean.setAddress("wengchi address");
	*			bean.setAge(i);
	*			list.add(bean);	
	*		}
	* System.out.println(JsonUtil.toJsonStr(list));
	*
	 * </pre>
	 * @param list
	 * @return
	 */
	public static String toJsonStr(List list){
		return JSONSerializer.toJSON(list).toString();
	}
	
}
