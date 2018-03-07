package com.sertek.util;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.params.*;
import org.apache.commons.httpclient.methods.multipart.*;
import java.util.*;
import java.io.*;
import java.net.*;
/**
 * �ϥ� HttpClient 
 * @author wengchi
 * @version 1.0
 * <pre>
 * �ϥνd�ҡG
 * �@��ǰe���(�b webshpere 3.5 �ܺC)�G
 * HttpObj h = new HttpObj();
 * h.addParam("key1","value1");
 * h.addParam("key2","value2");
 * if (h.post("http://localhost/test.jsp")==true){
 * 		out.println(h.getRequest());	//���o�^�Ǻ���
 * }else{
 * 		out.println(h.getLastErr());	//���o���~�T��
 * }
 * 
 * 
 * �@��ǰe��ơG
 * HttpObj h = new HttpObj();
 * h.get("http://localhost/test.jsp?my=aaa");
 * out.println(h.getRequest());	//���o�^�Ǻ���
 * 
 * 
 * �ɮפW��(�b webshpere 3.5 �ܺC)�G�G
 * HttpObj h = new HttpObj(HttpObj.FILEUPLOAD);
 * h.addParam("key1","value");		//�Ѽ�
 * File f = new File("c:/test.zip");	
 * h.addFile("key2",f);				//�ɮ�
 * h.fileUpload("http://localhost/upload.jsp");
 * 
 * 
 * �ɮפU��(�b webshpere 3.5 �ܺC)�G
 * HttpObj h = new HttpObj(HttpObj.FILEDOWNLOAD);
 * h.fileDonwload("http://localhost/test.zip","c:/test.zip");
 * 
 * 
 * �ɮפU���G
 * HttpObj client = new HttpObj();
 * client.get("http://localhost/test.zip", "c:/test.zip");
 * 
 * 
 * �Ƶ��G
 * ���B�b webSphere 3.5 �����p���U�A�s�u�ܺC�A�кɶq�ϥ� get �覡
 *    �䥦����AP�L�����D�C
 * ���B
 * </pre>
 */

public class HttpObj {
	private String encode = "MS950";
	public static int POST = 1;
	public static int FILEUPLOAD = 2;
	public static int FILEDOWNLOAD = 3;
	private Vector param = new Vector();
	private String errStr = "";
	private String dou = "\r\n";
	private int type = POST;		//�O���ثe������O�ɮפW�ǩάO�@�������ƶǰe
	private StringBuffer Body = new StringBuffer();
	private int timeout = 20;	//�s�u�O�ɬ�� 
	private int readtimeout = 0;	//�s�u�O�ɬ�� 
	public HttpObj(){	
	}
	
	public HttpObj(int type){
		this.type = type;
	}
	/**
	 * �M�ű��W�Ǫ��Ѽ�
	 * 
	 */
	public void clearParam(){
		param.clear();
	}
	/**
	 * �]�w�s�u�O�ɬ��
	 * @param timeout
	 */
	public void setTimeOut(int timeout){
		this.timeout = timeout;
	}
	/**
	 * �]�w�s�u�O�ɬ��
	 * @param timeout
	 */
	public void setReadTimeOut(int readtimeout){
		this.readtimeout = readtimeout;
	}
	/**
	 * �]�w�ǰe�覡
	 * @param type
	 */
	public void setMethod(int type){
		this.type = type;
	}
	/**
	 * �W�[�Ѽ�
	 * @param key
	 * @param value
	 */
	public void addParam(String key,String value){
		if (type==FILEUPLOAD){
			param.add(new StringPart(key, value,encode)); 
		}else{
		 	param.add( new NameValuePair(key,value));
		}	
	}
	/**
	 * �W�[�W���ɮ�
	 * @param key
	 * @param file
	 * @return
	 */
	public boolean addFile(String key,File file){
		boolean retVal = false;
		try{
			param.add(new FilePart(key, file.getName(), file,null,encode));
			retVal = true;
		}catch(IOException e){
			e.printStackTrace();
		}
		return retVal;
	}
	/**
	 * �U���ɮ�
	 * @param url �U�����}
	 * @param saveFile �^�s�ɮ�
	 * @return
	 */
	public boolean fileDownload(String url,String saveFile){
		Body.setLength(0);
		boolean retVal = false;	
		 HttpClient client = new HttpClient();
		 GetMethod filePost = new GetMethod(url);
		 try{  
			 
			 filePost.getParams().setContentCharset(encode); 	//�s�X
			 filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,encode);		   
			 client.setConnectionTimeout( timeout * 1000 );
			 if( readtimeout>0 )
				 client.setTimeout( readtimeout * 1000 );
			 
			    int status = client.executeMethod(filePost);

			    if (status == HttpStatus.SC_OK) {
			    	byte[] data = new byte[1]; 
			    	
			    	BufferedInputStream bi = new BufferedInputStream(filePost.getResponseBodyAsStream());
			    	BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(saveFile));
			    	while(bi.read(data) != -1) { 
			                bo.write(data); 
			        }
			    	System.out.println("fileDownload stop");
			    	System.out.println("");
			    	bo.flush();
			    	bi.close();
			    	bo.close();
			    	retVal = true;
			    } else {
			    	errStr = HttpStatus.getStatusText(status);
			    	retVal = false;
			    }
	     }catch(Exception e){
	    	 e.printStackTrace();
	    	 retVal = false;
	    	 errStr = e.getMessage();
	     }finally{
		    filePost.releaseConnection();
		    client = null;
	     }
		    return retVal;
	}

	/**
	 * �W���ɮ�
	 * 
	 * @param url
	 * @return
	 */
	public boolean fileUpload(String url) {
		Body.setLength(0);
		boolean retVal = false;
		HttpClient client = new HttpClient();
		client.setConnectionTimeout(timeout * 1000);
		 if( readtimeout>0 )
			 client.setTimeout( readtimeout * 1000 );
		PostMethod filePost = new PostMethod(url);
		try {

			filePost.getParams().setContentCharset(encode); // �s�X
			filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encode);
			// File targetFile = new File(file);
			if (param.size() > 0) {
				Part[] data = new Part[param.size()];
				// String classType = "";
				for (int i = 0; i < param.size(); i++) {
					// classType = (param.get(i)).getClass().toString();
					data[i] = (Part) param.get(i);
				}
				MultipartRequestEntity mrp = new MultipartRequestEntity(data, filePost.getParams());
				filePost.setRequestEntity(mrp);
			}

			int status = client.executeMethod(filePost);
			System.out.println(status);
			if (status == HttpStatus.SC_OK) {

				BufferedReader br = new BufferedReader(new InputStreamReader(filePost.getResponseBodyAsStream()));
				String line = "";
				while ((line = br.readLine()) != null) {
					if (!"".equals(line)) {
						Body.append(line);
						// System.out.println();
						Body.append(dou);
					}
				}
				br.close();
				retVal = true;
			} else {
				
				this.errStr = HttpStatus.getStatusText(status);
				retVal = false;
				System.out.println(Body.toString());
				
				BufferedReader br = new BufferedReader(new InputStreamReader(filePost.getResponseBodyAsStream()));
				String line = "";
				while ((line = br.readLine()) != null) {
					if (!"".equals(line)) {
						Body.append(line);
						// System.out.println();
						Body.append(dou);
					}
				}
				br.close(); 
				retVal = true;
				
				System.out.println(Body.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			retVal = false;
			Body.append(e.getMessage());
			this.errStr = e.getMessage();
		} finally {
			filePost.releaseConnection();
			client = null;
		}
		return retVal;

	}
	/**
	 * �ϥ� post �覡�ǰe���
	 * @param url
	 * @return
	 */
	public boolean post(String url){
		Body.setLength(0);
		 boolean retVal = false;	
		 HttpClient client = new HttpClient();
		 client.getParams().setParameter(HttpClientParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		 client.setConnectionTimeout( timeout * 1000 );
		 if( readtimeout>0 )
			 client.setTimeout( readtimeout * 1000 );
		 PostMethod filePost =  new PostMethod(url);
		 filePost.getParams().setContentCharset(encode); 
		 //filePost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,encode);		 
		 //filePost.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,  new DefaultHttpMethodRetryHandler());  
		 if (param.size()>0){
			NameValuePair[] data = new NameValuePair[param.size()];
			for (int i=0;i<param.size();i++){
				data[i]= (NameValuePair)param.get(i);
			}
			filePost.setRequestBody(data);
			
		}
		try{  
		 //client.setConnectionTimeout(10000);
			//System.out.println("post start 11 ");
		    int status = client.executeMethod(filePost);
		    //System.out.println("post start 22 ");
		    if (status == HttpStatus.SC_OK) {
		    	
		    	BufferedReader br = new BufferedReader(new InputStreamReader(filePost.getResponseBodyAsStream()) ,8192);
		    	String line = "";
		    	while ((line= br.readLine())!=null){
		    		Body.append(line);
		    		//System.out.println(line);
		    		Body.append(dou);
		    	}	
		    	br.close();

		    	retVal = true;
		    } else {
		    	Body.append( HttpStatus.getStatusText(status)); 
		    	retVal = false;
		    	this.errStr = HttpStatus.getStatusText(status);
		    }
		}catch(Exception e){
			e.printStackTrace();
			retVal = false;
			this.errStr = e.getMessage();
		}finally{
		    filePost.releaseConnection();
		    client = null;
		}    
		    return retVal;
	}
	/**
	 * 
	 * @param url ���}�A�]�t�ܼơA����Цۦ�URLEncoder�s�X�p�U�G
	 *            http://211.79.207.51/jud_k/wkw/GETC6X02.jsp?crmyy=093&crmid=" + URLEncoder.encode("��") + "&crmno=010139&crtid=SLD"
	 * @return ���Ѧ^�� false
	 */
	public boolean get(String url){
		return get(url,"",false);

	}
	/**
	 * �ɮפU��
	 * @param url �U�����} 
	 * @param saveFile �x�s�ɮ�
	 * @return
	 */
	public boolean get(String url,String saveFile){
		return get(url,saveFile,true);

	}
	private boolean get(String url,String saveFile,boolean download){
		 boolean retVal = false;	
			try{
				Body.setLength(0);	
				
				URL website = new URL(url);

				URLConnection connection = website.openConnection(); 
			      if (download){
			    	  byte[] data = new byte[1]; 
				    	BufferedInputStream bi = new BufferedInputStream(connection.getInputStream());
				    	BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(saveFile));
				    	while(bi.read(data) != -1) { 
				                bo.write(data); 
				        }
				    	bo.flush();
				    	bi.close();
				    	bo.close();
			      }else{
			    	  BufferedReader in = new BufferedReader(
				                new InputStreamReader(connection.getInputStream()));
				      String line=null;
				      String content="";
				      while( (line=in.readLine())!=null) {
				    	  Body.append(line);
				    	  Body.append(dou);
				      }
				      in.close();
			      }
			      retVal = true;
	      }catch(Exception e){
	      	e.printStackTrace();
	      	this.Body.append(e.getMessage());
	      	this.errStr =  e.getMessage();
	      }
			  return retVal; 
	}
	
	public String getRequest(){
		return this.Body.toString();
	}
	public StringBuffer getRequestBuffer(){
		return this.Body;
	}
	public String getLastErr(){
		return errStr;
	}
}
