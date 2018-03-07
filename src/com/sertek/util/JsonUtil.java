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
 * �B�z Json
 * @author wengchi
 * �ݭnjson-lib-2.4-jdk13.jar,commons-logging-1.1.1.jar,ezmorph-1.0.6.jar,commons-beanutils-1.8.3.jar,commons-collections-3.2.1.jar
 *
 */
public class JsonUtil {
	
	/**
	 * ���o json �}�C
	 * <pre>
	 * �Ҥl�G
	 *  String jsonStr = "[{'address':'wengchi address','age':0,'name':'����_','rmk':''},{'address':'wengchi address','age':1,'name':'����_1','rmk':''}]";
	 *  JSONArray jsonArray = JsonUtil.getJsonArray(jsonStr);
	 *  	for(int i=0;i<jsonArray.size();i++){
	*		    JSONObject j = jsonArray.getJSONObject(i);
	*		    System.out.println(j.getString("name"));
	*		    System.out.println(j.getInt("age"));
	*		    System.out.println(j.getString("address"));
	*	   }
	 * </pre>
	 * @param jsonStr json�r��
	 * @return
	 */
	public static JSONArray getJsonArray( String jsonStr ){
		  return JSONArray.fromObject(jsonStr);
	}
	
	/**
	 * json �ন java ����}�C
	 * <pre>
	 * �d�ҡG
	 * String jsonStr = "[{'address':'���s�_�����E�M','age':34,'name':'����_','rmk':''},{'address':'wengchi address','age':12,'name':'����_1','rmk':''}]";
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
	 * @return List pojoClass�}�C
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
	 * ���o��@ JSONObject ����
	 * <pre>
	 * String jsonStr = "{'name':'����_','age':16}";
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
	 * �N json �r���ন map ����
	 * <pre>
	 * String jsonStr = "{'name':'����_','age':16}";
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
	 * ���o��@JAVA ����
	 * <pre>
	 * String jsonStr = "{'name':'����_','age':16}";
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
	 * �N map �̭������ন json �r��
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
	 * �N List �̭������ন json �r��
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
