package com.sertek.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class EMIS {

	/**
	 * 測試群豐傳送程式
	 * @param sData_
	 * @param urlString_
	 * @param encoder_
	 * @param sbResult_
	 * @return
	 */
	public static boolean postString2URL(String sData_, String urlString_, String encoder_, StringBuffer sbResult_) {
	    boolean isOk = false;
	    if (sbResult_ == null) sbResult_ = new StringBuffer();

	    HttpClient httpclient = new HttpClient();
	    PostMethod post = new PostMethod(urlString_);
	    MultipartRequestEntity reqEntity;
	    try {
	      Part[] parts = {
	          new StringPart("XMLDATA", sData_, encoder_)
	      };
	      System.out.println("encoder = " + encoder_);
	      post.getParams().setContentCharset(encoder_);
	      post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoder_);
	      reqEntity = new MultipartRequestEntity(parts, post.getParams());

	      post.setRequestEntity(reqEntity);
	      
	      //post.setParameter("XMLDATA", sData_);
	      
	      int statusCode = httpclient.executeMethod(post);
	      if (statusCode != HttpStatus.SC_OK) {
	        sbResult_.append("Post failed: " + post.getStatusLine()+"-----");
	      }
	      //取得對方回覆內容
	      InputStream inputStream = post.getResponseBodyAsStream();
	      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
	      String str;
	      while((str = br.readLine()) != null){
	        sbResult_.append(str);
	      }
	      isOk = true;
	    } catch (HttpException e) {
	      isOk = false;
	    } catch (IOException e) {
	      isOk = false;      
	    } finally {
	      post.releaseConnection();
	    }
	    return isOk;
	  }

}
