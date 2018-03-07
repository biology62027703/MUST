package com.sertek.util;
//com.sertek.util.*,demo.rest.*,com.sun.jersey.api.client.WebResource.*,,,
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import com.sertek.util.JsonUtil;
import com.sun.jersey.api.client.WebResource.*;
import com.sun.jersey.api.client.*;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import com.sun.jersey.core.util.*;
import javax.ws.rs.core.*;
import com.sun.jersey.api.client.config.*;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * 呼叫 REST WebService
 * 使用 jersey 1.2 版，( + jdk1.5)
 * <pre>
 * 使用範例：
 * RestClient client = new RestClient();
 * client.addParam("name","wangccwkio");			//傳入使用者名稱
 * String url ="http://localhost:8080/REST/resource/helloworld/jsonStr";
 *
 * StudentBean bean = (StudentBean)client.getStrJsonToBean(rul,StudentBean.class);		//使用 GET method 傳送資料 透過 web service 取回  bean
 * System.out.println( bean.getName() );		//印出名字
 * System.out.println( bean.getAge() );			//印出年齡
 * 
 * 
 *使用取得陣列
 *  client.clearAllParams();
 *  url = "http://localhost:8080/REST/resource/helloworld/jsonStrList";
 * List a = client.getStrJsonToBeanArray(url,StudentBean.class);
 * for(int i=0;i<a.size();i++){
*	   StudentBean b = (StudentBean)a.get(i);
*	   out.println(b.getName()+"<BR>");
*	   out.println(b.getAddress()+"<BR>");
*	   out.println(b.getAge()+"<BR>");
*   }
 * </pre>
 * 
 * @author wengchi
 *
 */
public class RestClient {
	private static int GET = 0;
	private static int POST = 1;
	ClientConfig config = new DefaultClientConfig();
	
	//config1.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
	Client client = null;
	
	MultivaluedMap queryParams = new MultivaluedMapImpl();

	//WebResource webResource = null;
	public RestClient(){
		
		// jdk 1.6 + jersey 1.13 時，config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE); 要打開
		//config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		
		client = Client.create(config);

	}
	
	/**
	 * 清除參數
	 */
	public void clearAllParams(){
		queryParams.clear();
	}
	
	/**
	 * 增加傳送到 webservice 上的資料
	 * @param key
	 * @param value
	 */
	public void addParam(String key,String value){
		queryParams.add(key, value);
	}
	
	/**
	 * 使用 GET 取得 string
	 * @param url
	 * @return
	 */
	public String get(String url){
		return get(url,GET);
	}
	
	//使用 POST 取得 bean array
	/**
	 *  使用 GET ,取得 json 字串，再將資料轉成 map 以方便取用
	 * @param url
	 * @return
	 */
	public Map getJsonStrToMap(String url){
		String str =  get(url,GET);
		return JsonUtil.jsonStrToMap(str);
	}
	
	/**
	 * 使用 POST 方式 ,將取得 json 字串，再轉成 map ，方便使用者操作
	 * @param url
	 * @return
	 */
	public Map postJsonStrToMap(String url){
		String str =  get(url,POST);
		return JsonUtil.jsonStrToMap(str);
	}
	
	private String get(String url,int method){
		WebResource webResource = client.resource(url);
		String str = "";
		if (method==POST)
			str = webResource.queryParams(queryParams).post(String.class);
		else
			str = webResource.queryParams(queryParams).get(String.class);
			
		webResource = null;
		return str;
	}
	
	/**
	 * 使用 POST 方式, 取得 REST 網站回傳的 string
	 * @param url
	 * @return
	 */
	public String post(String url){
		return get(url,POST);
	}
	/**
	 * 取得 json字串，再轉成 java pojo bean
	 * @param url
	 * @param pojoClass
	 * @param method
	 * @return
	 */
	private Object jsonStrToBean(String url,Class pojoClass,int method){
		WebResource webResource = client.resource(url);
		String jsonStr = "";
		if (method==GET)
			jsonStr = webResource.queryParams(queryParams).get(String.class);
		else
			jsonStr = webResource.queryParams(queryParams).post(String.class);
			
		return JsonUtil.getJSONObject(jsonStr, pojoClass);
	}
	
	/**
	 * 使用 GET 方式，取回 json字串，再轉成 pojo javabean 
	 * @param url
	 * @param pojoClass
	 * @return
	 */
	public Object getJsonStrToBean(String url,Class pojoClass){
		return jsonStrToBean(url,pojoClass,GET);
	}
	
	/**
	 * 使用 POST 方式，取回 json字串，再轉成 pojo javabean 
	 * @param url
	 * @param pojoClass
	 * @return
	 */
	public Object postJsonStrToBean(String url,Class pojoClass){
		return jsonStrToBean(url,pojoClass,POST);
	}
	
	/**
	 *  取得 回傳的 MIME media type =  application/json 物件，再轉成 java pojo bean
	 * @param url
	 * @param pojoClass
	 * @param method
	 * @return
	 */
	private Object jsonToBean(String url,Class pojoClass,int method){
		WebResource webResource = client.resource(url);
		Object o ;
		String jsonStr = "";
		if (method==GET)
			o = webResource.queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(pojoClass);
		else
			o = webResource.queryParams(queryParams).accept(MediaType.APPLICATION_JSON).post(pojoClass);
			
		return o;
	}
	
	
	

	private List jsonStrToBeanArray(String url,Class pojoClass,int method){
		WebResource webResource = client.resource(url);
		String jsonStr = "";
		if (method==GET)
			jsonStr = webResource.queryParams(queryParams).get(String.class);
		else
			jsonStr = webResource.queryParams(queryParams).post(String.class);
		
		return JsonUtil.getJsonArray(jsonStr, pojoClass);
	}
	
	/**
	 * 使用 GET 方式，取得 json 字串，再轉成 pojo javabean Array
	 * @param url
	 * @param pojoClass
	 * @return
	 */
	public List getJsonStrToBeanArray(String url,Class pojoClass){
		return jsonStrToBeanArray(url,pojoClass,GET);
	}
	
	/**
	 * 使用 POST 方式，取得 json 字串，再轉成 pojo javabean Array
	 * @param url
	 * @param pojoClass
	 * @return
	 */
	public List postJsonStrToBeanArray(String url,Class pojoClass){
		return jsonStrToBeanArray(url,pojoClass,POST);
	}
	

	
}
