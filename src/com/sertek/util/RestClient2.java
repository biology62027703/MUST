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
 * �I�s REST WebService
 * �Ω� jersey 1.13 ( + jdk 1.6)
 * <pre>
 * �ϥνd�ҡG
 * RestClient client = new RestClient();
 * client.addParam("name","wangccwkio");			//�ǤJ�ϥΪ̦W��
 * String url ="http://localhost:8080/REST/resource/helloworld/testJson";
 *
 * StudentBean bean = (StudentBean)client.getStrJsonToBean(rul,StudentBean.class);		//�ϥ� GET method �ǰe��� �z�L web service ���^  bean
 * System.out.println( bean.getName() );		//�L�X�W�r
 * System.out.println( bean.getAge() );			//�L�X�~��
 * 
 * 
 *�ϥΨ��o�}�C
 *  client.clearAllParams();
 *  url = "http://localhost:8080/REST2/resource/helloworld/jsonList";
 * List a = client.getJsonToBeanArray(url, new GenericType<List<StudentBean>>(){});
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
public class RestClient2 {
	private static int GET = 0;
	private static int POST = 1;
	ClientConfig config = new DefaultClientConfig();
	Client client = null;
	
	MultivaluedMap queryParams = new MultivaluedMapImpl();

	//WebResource webResource = null;
	public RestClient2(){
		
		// jdk 1.6 + jersey 1.13 �ɡAconfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE); �n���}
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		
		client = Client.create(config);

	}
	
	/**
	 * �M���Ѽ�
	 */
	public void clearAllParams(){
		queryParams.clear();
	}
	
	/**
	 * �W�[�ǰe�� webservice �W�����
	 * @param key
	 * @param value
	 */
	public void addParam(String key,String value){
		queryParams.add(key, value);
	}
	
	/**
	 * �ϥ� GET ���o string
	 * @param url
	 * @return
	 */
	public String get(String url){
		return get(url,GET);
	}
	
	//�ϥ� POST ���o bean array
	/**
	 *  �ϥ� GET ,���o json �r��A�A�N����ন map �H��K����
	 * @param url
	 * @return
	 */
	public Map getJsonStrToMap(String url){
		String str =  get(url,GET);
		return JsonUtil.jsonStrToMap(str);
	}
	
	/**
	 * �ϥ� POST �覡 ,�N���o json �r��A�A�ন map �A��K�ϥΪ̾ާ@
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
	 * �ϥ� POST �覡, ���o REST �����^�Ǫ� string
	 * @param url
	 * @return
	 */
	public String post(String url){
		return get(url,POST);
	}
	/**
	 * ���o json�r��A�A�ন java pojo bean
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
	 * �ϥ� GET �覡�A���^ json�r��A�A�ন pojo javabean 
	 * @param url
	 * @param pojoClass
	 * @return
	 */
	public Object getJsonStrToBean(String url,Class pojoClass){
		return jsonStrToBean(url,pojoClass,GET);
	}
	
	/**
	 * �ϥ� POST �覡�A���^ json�r��A�A�ন pojo javabean 
	 * @param url
	 * @param pojoClass
	 * @return
	 */
	public Object postJsonStrToBean(String url,Class pojoClass){
		return jsonStrToBean(url,pojoClass,POST);
	}
	
	/**
	 *  ���o �^�Ǫ� MIME media type =  application/json ����A�A�ন java pojo bean
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
	
	/**
	 * �ϥ� GET ���o �^�Ǫ� MIME media type =  application/json ����A�A�ন pojo java bean 
	 * @param url
	 * @param pojoClass
	 * @return
	 */
	public Object getJsonToBean(String url,Class pojoClass){
		return jsonToBean(url,pojoClass,GET);
	}
	
	/**
	 * �ϥ� POST ���o bean 
	 * @param url
	 * @param pojoClass
	 * @return
	 */
	public Object postJsonToBean(String url,Class pojoClass){
		return jsonToBean(url,pojoClass,POST);
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
	 * �ϥ� GET �覡�A���o json �r��A�A�ন pojo javabean Array
	 * @param url
	 * @param pojoClass
	 * @return
	 */
	public List getJsonStrToBeanArray(String url,Class pojoClass){
		return jsonStrToBeanArray(url,pojoClass,GET);
	}
	
	/**
	 * �ϥ� POST �覡�A���o json �r��A�A�ন pojo javabean Array
	 * @param url
	 * @param pojoClass
	 * @return
	 */
	public List postJsonStrToBeanArray(String url,Class pojoClass){
		return jsonStrToBeanArray(url,pojoClass,POST);
	}
	

	private List jsonToBeanArray(String url,GenericType genType,int method){
		WebResource webResource = client.resource(url);
		List list =null;
		if (method==GET)
			list = (List) webResource.queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(genType);
		else
			list= (List) webResource.queryParams(queryParams).accept(MediaType.APPLICATION_JSON).post(genType);
	
		return list;
	}

	/**
	 * �ϥ� GET �覡�A���o �^�Ǫ� MIME media type =  application/json ����A�A�ন pojo javabean Array
	 * @param url
	 * @param genType
	 * @return
	 */
	public List getJsonToBeanArray(String url,GenericType genType){
		return jsonToBeanArray(url,genType,GET);
	}
	
	/**
	 * �ϥ� POST�覡�A���o �^�Ǫ� MIME media type =  application/json ����A�A�ন pojo javabean Array
	 * @param url
	 * @param genType
	 * @return
	 */
	public List postJsonToBeanArray(String url,GenericType genType){
		return jsonToBeanArray(url,genType,POST);
	}

	
}
