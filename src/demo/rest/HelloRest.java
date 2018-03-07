package demo.rest;

import java.util.*;


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

@Path("/helloworld")	// sets the path for this service
public class HelloRest {
	
	
	@GET
	//@Produces("text/html") // content type to output
	//MediaType
	@Produces(MediaType.TEXT_PLAIN)
	//測試 http://localhost:8080/REST/resource/helloworld?name=aaaa@age=16
	public String getClientMessage( @QueryParam("name") String name ,@QueryParam("age") int age){
		return name + " is " + age;
	}
	
	@Path("/jsonStrList")
	@GET
	//@Produces("text/html") // content type to output
	//MediaType
	@Produces("application/json")
	//測試json http://localhost:8080/REST/resource/helloworld/jsonStrList
	//輸出字串
	public String getClientMessage1( @QueryParam("name") String name ){
			List<StudentBean> list = new ArrayList<StudentBean>();
			
			for (int i=0;i<2;i++){
				StudentBean bean = new StudentBean();
				bean.setName("王文奇 "+ i);
				bean.setAddress("wengchi address");
				bean.setAge(i);
				list.add(bean);	
			}
			return JSONSerializer.toJSON(list).toString();

	}
	
	@Path("/jsonList")
	@GET
	//@Produces("text/html") // content type to output
	//MediaType
	@Produces(MediaType.APPLICATION_JSON)
	//輸出 json 物件陣列
	//測試json http://localhost:8080/REST/resource/helloworld/jsonList
	public List<StudentBean> getClientMessage2(){
			/**
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name","wengchi");
			map.put("age","16");
			return JSONSerializer.toJSON(map).toString();
			*/
			List<StudentBean> list = new ArrayList<StudentBean>();
			
			for (int i=0;i<3;i++){
				StudentBean bean = new StudentBean();
				bean.setName("王文奇   "+ i);
				bean.setAddress("wengchi address");
				bean.setAge(i);
				bean.setRmk("rmk");
				list.add(bean);	
			}
			return list;

	}
	/**
	 * 
	 * @param jsonStr 傳入 json 字串
	 */
	@POST
	@Path("/getjson")
	//@HttpMethod
	@Consumes("application/x-www-form-urlencoded")
	//測試 傳入 json 字串後，團成物件操作  http://localhost:8080/REST/SENDJSON2REST.jsp
	public void getJson( @FormParam("jsonStr") String jsonStr){
		String retVal = "";
		JSONArray  jsonArray = JSONArray.fromObject(jsonStr);
		
		StudentBean[] stus = (StudentBean[])JSONArray.toArray(jsonArray,StudentBean.class);
		for(int i=0;i<stus.length;i++){
			StudentBean bean = (StudentBean)stus[i];
			System.out.println(bean.getName());
			System.out.println(bean.getAge());
			System.out.println(bean.getAddress());
			System.out.println("----------------------------");
		}
		
		
		for(int i=0;i<jsonArray.size();i++){
			JSONObject j = jsonArray.getJSONObject(i);
			System.out.println(j.getString("name"));
			System.out.println(j.getInt("age"));
			System.out.println(j.getString("address"));
			
		}
		//StudentBean[] stus = (StudentBean[])JSONArray.toArray(jsonArray,Map.class);
		jsonArray = null;
		//Student[] stus = (Student[]) JSONArray.toArray(jsonArray, Student.class);
		//JSONSerializer.toJava(json)
		
	}

	@GET
	@Path("/jsonStr")
	@Produces("application/json")
	//測試json http://localhost:8080/REST/resource/helloworld/testJson
	public String useJSONLIB2STR222(){
		JSONObject j = new JSONObject();
		j.element("name", "王文奇").element("age",16);
		return JSONSerializer.toJSON(j).toString();
	}

	@GET
	@Path("/json")
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
	//測試json http://localhost:8080/REST/resource/helloworld/json
	//輸出 json 物件
	public StudentBean useJSONLIB2STR(){
		//JSONObject j = new JSONObject();
		//j.element("name", "王文奇").element("age",16);
		StudentBean bean = new StudentBean();
		bean.setName("環文奇");
		bean.setAge(13);
		return bean;
		
	}
	
	
	@GET
	@Path("/getJsonValue")
	@Produces("application/json")
	//測試j取得json的某個值 http://localhost:8080/REST/resource/helloworld/getJsonValue
	public String getJSONValue(){
		
		String jsonStr = "{'name':'王文奇','age':16}";
		JSONObject j = JSONObject.fromObject(jsonStr);
		
		return "name="+(String)j.get("name")+ " "+"age=" + j.getInt("age") ;		
	}
	
	
}
