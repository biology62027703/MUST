package com.sertek.form;
import com.sertek.util.CryptoUtil;
import com.sertek.util.CutBook;
import com.sertek.util.ReadWriteExcel;
import com.sertek.util.CheckObject;
import com.sertek.util.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sertek.form.ResponseBean;
import com.sertek.form.BaseAbstractCommandController;

public class SEND_STATEMENT_EMAILController extends BaseAbstractCommandController {
	public void doPost(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean)throws Exception {
		String folder="D:/EXCEL_FILES";		
		fileUpload.uploadFile(folder);		
		ReadWriteExcel ReadWriteExcel = new ReadWriteExcel();
		ReadWriteExcel.setKeys(new String[] {"SEQ","DIST_NO","IP_BASE_NO"});
		ArrayList<?> column_val = ReadWriteExcel.readXLSXFile("D:/EXCEL_FILES");
		NumberFormat nf = NumberFormat.getInstance();
		for (Object O:column_val) {
			HashMap hm = (HashMap)O;			
			List ls = this.SqlDBUtility_MUST_WEB.queryForList("STATEMENTS.QUERY", hm);
			System.out.println("LS:"+ls);
			if(ls.size()>0) {
				HashMap ht = (HashMap)ls.get(0);
				String dist_desc = "20"+ht.get("DIST_NO").toString().substring(1,3)+"第"+ht.get("DIST_NO").toString().substring(3)+"次";
				String case_type=ht.get("DIST_NO").toString().substring(0,1);
				String dist_kind = "";
			    switch( case_type){
				    case "P":       
				         dist_kind="公開播送";
						break;
				    case "I":
				         dist_kind="公開傳輸";
				         break;
				    case "O":
				         dist_kind="海外匯入款";
				        break;
				    case "M":
				         dist_kind="重製權";
				         break;    
			    }
			    String Statement_Report = dist_desc + dist_kind + "權利金明細表";
			    String P_Report = dist_desc + dist_kind + "電子CRD(P/F)檔";			     
			    String Dist_Report = dist_desc + dist_kind + "分配說明";
				StringBuffer htmlCode = new StringBuffer();
				htmlCode.append
				(
					"親愛的會員<font color='red'>"+ht.get("CUST_NAME").toString()+"</font> 您好：<br>" +
					"<font color='red'> "+ ht.get("DIST_NO") + "</font>的使用報酬<font color='red'>NT$"+ nf.format(Double.parseDouble(ht.get("NET_PAYMENT").toString())) +"</font>  已於<font color='red'>"+ht.get("PAYMENT_DATE").toString()+"</font>分發，" +
					"明細表等相關檔案亦已上傳於MUST會員專區。(團體會員收到此通知表示MUST已收到發票，無須再次開立。) <br><br>"+
					"下載方式： <br>"+
					"1. 點選下方「附件」連結後下載 <br>"+
					"2. 登入MUST網站 <a href='https://www.must.org.tw/tw/members/index.aspx' target='_blank'>「會員專區」</a>進行下載 <br><br>"+
					"提醒： <br>"+
					"1. 權利金明細表解壓縮密碼：<br>"+
					" 個人會員為身份證字號 / 護照號碼 / 居留證號 <br>"+
					" 團體會員為公司統一編號。 <br>"+
					"2. 任何單次分配給付<font color='blue'>個人會員</font>金額少於新台幣500元者，將累積達新台幣500元後才予以合併付款。<font color='blue'>團體會員</font>不受此限；<font color='blue'>海外匯款</font>累積達新台幣2000元以美金付款。 <br><br>"+
					"如有任何疑問，歡迎來電/信洽詢。 <br> "+
					"TEL：(02)2511-0869 #360  Ingrid 吳小姐 <br> "+
					"FAX：(02)2511-0759 <br> "+
					"Email：member@must.org.tw <br><br>"+
					"附件： <br>"
				);
				if(ht.get("IP_BASE_NO").toString().equals("I0018911378") || ht.get("IP_BASE_NO").toString().equals("I0023068940") || ht.get("IP_BASE_NO").toString().equals("I0002840299") || ht.get("IP_BASE_NO").toString().equals("I0023068984") || ht.get("IP_BASE_NO").toString().equals("I0002351677") || ht.get("IP_BASE_NO").toString().equals("I0018911345") || ht.get("IP_BASE_NO").toString().equals("I0002372225") || ht.get("IP_BASE_NO").toString().equals("I0002078717") || ht.get("IP_BASE_NO").toString().equals("I0020356072")) {
					htmlCode.append(
						"<a href='https://www.must.org.tw/tw/news/data_download.aspx?dkey="+ht.get("EMAIL_KEY").toString()+"&dtype=em&send_type=fml&drpt=st&dist_id="+ht.get("DIST_ID").toString()+"' target='_blank'>"+Statement_Report+"</a><br>"+
						"<a href='https://www.must.org.tw/tw/news/data_download.aspx?dkey="+ht.get("EMAIL_KEY").toString()+"&dtype=em&send_type=fml&drpt=p&dist_id="+ht.get("DIST_ID").toString()+"' target='_blank'>"+P_Report+"</a><br>"+					
						"<a href='https://www.must.org.tw/tw/news/data_download.aspx?dkey="+ht.get("EMAIL_KEY").toString()+"&dtype=em&send_type=fml&drpt=ds&dist_id="+ht.get("DIST_ID").toString()+"' target='_blank'>"+Dist_Report+"</a><br>"
					);
				} else {
					htmlCode.append(
						"<a href='https://www.must.org.tw/tw/news/data_download.aspx?dkey="+ht.get("EMAIL_KEY").toString()+"&dtype=em&send_type=fml&drpt=st&dist_id="+ht.get("DIST_ID").toString()+"' target='_blank'>"+Statement_Report+"</a><br>"+
						"<a href='https://www.must.org.tw/tw/news/data_download.aspx?dkey="+ht.get("EMAIL_KEY").toString()+"&dtype=em&send_type=fml&drpt=ds&dist_id="+ht.get("DIST_ID").toString()+"' target='_blank'>"+Dist_Report+"</a><br>"
					);
				}				
				String recipient = ht.get("PRIVATE_EMAIL").toString();
				//額外要撈取的名單
				List<?> ls1=this.SqlDBUtility_MUST_WEB.queryForList("STATEMENTS.QUERY_EXTRAEMAIL", hm);
				for(Object EMAIL:ls1) {
					recipient = recipient+","+((HashMap<?, ?>) EMAIL).get("EMAIL").toString();
				}
				System.out.println("recipient:"+recipient);
				recipient = "james.huang@must.org.tw";
				new SEND_EMAIL("member@must.org.tw","MUST會員通知",recipient,"MUST "+ht.get("DIST_NO").toString()+"權利金通知信：分發",htmlCode);
				
			} else {
				System.out.println("找不到相關資料:"+O.toString());
			}
		}
		responseBean.setGotoUrl("SEND_STATEMENT_EMAIL.jsp");
	}
}

