package com.sertek.form;

import com.sertek.sys.sys_User;
import com.sertek.table.*;
import com.sertek.util.CryptoUtil;
import com.sertek.util.CutBook;
import com.sertek.util.QRCodeUtil;
import com.sertek.util.CheckObject;
import com.sertek.util.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sertek.form.ResponseBean;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.sertek.form.BaseAbstractCommandController;
import com.sertek.sys.sys_User;
import com.sertek.util.CryptoUtil;

public class LICENSEController extends BaseAbstractCommandController {
	
	//@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doSave(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		
		form.put("cnt2_tel1", form.get("cnt2_tel1_1").toString()+"-"+form.get("cnt2_tel1_2").toString());
		form.put("cnt2_fax", (form.get("cnt2_fax_1").toString().equals(""))?"":form.get("cnt2_fax_1").toString()+"-"+form.get("cnt2_fax_2").toString());
		form.put("user_tel", form.get("user_tel_1").toString()+"-"+form.get("user_tel_2").toString());
		form.put("user_fax", (form.get("user_fax_1").toString().equals(""))?"":form.get("user_fax_1").toString()+"-"+form.get("user_fax_2").toString());
		form.put("cont_bdate", form.get("cont_bdate_yy").toString()+form.get("cont_bdate_mm").toString()+form.get("cont_bdate_dd").toString());
		form.put("cont_edate", form.get("cont_edate_yy").toString()+form.get("cont_edate_mm").toString()+form.get("cont_edate_dd").toString());		
		form.put("status", "1");
		//LICENSE SERVER新增資料
		if(((String)check.checkNull(form.get("doc_no"), "")).equals("")) {
			form.put("doc_no", ud.nowWDateTime_num()+String.valueOf(Math.random()).substring(3, 6));
			this.getSqlDBUtility_LICENSE().insert("LICENSE.INSERT_LICENSE", form);
		} else {//LICENSE SERVER修改資料
			this.getSqlDBUtility_LICENSE().update("LICENSE.UPDATE_LICENSE", form);
		}
		HashMap hm = new HashMap();
		hm.put("doc_no", form.get("doc_no"));
		this.getSqlDBUtility_LICENSE().insert("LICENSE.DELETE_MACHINE", hm);
		boolean check=true;
		if(form.get("factory").toString().indexOf("@")>-1) {
			String [] factoryarray = request.getParameterValues("factory[]");
			String [] machinearray = request.getParameterValues("machine[]");
			for(int i=0;i<factoryarray.length;i++) {
				if(!factoryarray[i].equals("")&&!machinearray[i].equals("")) {
					
					hm.put("factory",URLDecoder.decode(factoryarray[i], "utf-8").replaceAll("%20", " "));
					hm.put("machine", URLDecoder.decode(machinearray[i], "utf-8"));
					List ls = this.getSqlDBUtility_LICENSE().queryForList("LICENSE.QUERY_LICENSE_MAIN_MACHINE",hm);
					if(ls.size()>0) {
						this.getSqlDBUtility_LICENSE().insert("LICENSE.DELETE_MACHINE", hm);
						this.getSqlDBUtility_LICENSE().insert("LICENSE.DELETE_LICENSE", hm);
						data.put("msg", "機台號碼重複，請確認填寫資料。");
						responseBean.setAjaxData(data);	
						check=false;
						break;
					}
					this.getSqlDBUtility_LICENSE().insert("LICENSE.INSERT_MACHINE", hm);
				}
			}
		} else {
			this.getSqlDBUtility_LICENSE().insert("LICENSE.INSERT_MACHINE", form);
		}
		System.out.println(form);		
		if(check) {
			//寄給填表單的人
			StringBuffer htmlCode = new StringBuffer() ;
			htmlCode.append("敬啟者 您好,<BR>");
			htmlCode.append("您申請的電腦伴唱機線上授權流水編號為:"+form.get("doc_no")+"<BR>");
			htmlCode.append("我們會盡快回覆您審查結果，審查期間若有其他問題，<BR>");
			htmlCode.append("請與窗口【 陳佩君小姐, 電話: 02-25110869 分機260】連繫。");
			new SEND_EMAIL("must.license@must.org.tw","MUST授權部",form.get("cnt1_email").toString(),"MUST 線上授權申請",htmlCode);
			//寄給內部同事
			htmlCode = new StringBuffer() ;
			htmlCode.append("您好,<BR>");
			htmlCode.append("電腦伴唱機線上授權流水編號為:"+form.get("doc_no")+"<BR>");
			htmlCode.append("請點選以下網址確認:<BR>");
			htmlCode.append("<a href='http://imust.must.org.tw:9000/MUST/LICENSE_CONFIRM.jsp?doc_no="+form.get("doc_no")+"'>請點此進行MUST線上授權資料審核<br></a>");
			htmlCode.append("有任何問題請與姆斯聯絡!");
			new SEND_EMAIL("must.license@must.org.tw","MUST授權部","Pallas.chen@must.org.tw,james.huang@must.org.tw","MUST 線上授權申請需求單",htmlCode);
			data.put("msg", "");
			responseBean.setAjaxData(data);	
		}
		
	}
	
	//@SuppressWarnings({ "unchecked", "rawtypes" })
		public void doUpdate(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
			HashMap data = new HashMap();
			System.out.println(form);
			//退回
			if(form.get("status").toString().equals("0")) {
				this.getSqlDBUtility_LICENSE().insert("LICENSE.UPDATE_LICENSE_BACK", form);				
				StringBuffer htmlCode = new StringBuffer() ;
				htmlCode.append("敬啟者 您好,<BR>");
				htmlCode.append("您申請的線上授權流水號:"+form.get("doc_no")+"被退回<BR>");
				htmlCode.append("原因是:<BR>"+form.get("msg").toString()+"<BR>");
				htmlCode.append("請點選以下網址修正:<br><a href='http://license.must.org.tw:8080/MUST/MUST_LICENSE_APPLY_EDIT.jsp?doc_no="+form.get("doc_no")+"'>請點此進行MUST線上授權資料修改</a><br>");
				htmlCode.append("有任何問題請與窗口【 陳佩君小姐, 電話: 02-25110869 分機260】連繫。");
				new SEND_EMAIL("must.license@must.org.tw","MUST授權部",form.get("cnt1_email").toString(),"MUST 線上授權申請",htmlCode);
			}
			//同意
			if(form.get("status").toString().equals("2")) {
				//先檢核資料
				List ls = this.getSqlDBUtility_51().queryForList("LAL.QUERY_USER",form);		
				if(ls.size()>0) {
					data.put("MSG", "此客戶已經申請過授權!");
				} else {
					ls = this.getSqlDBUtility_51().queryForList("LAL.QUERY_MAX_USERNO",form);
					for(Object user_no:ls) {
						System.out.println(((HashMap<?, ?>) user_no).get("USER_NO").toString());
						String MAX_USER_NO=((HashMap<?, ?>) user_no).get("USER_NO").toString().substring(3);
						form.put("user_no", form.get("user_class").toString()+"0"+String.valueOf((Integer.parseInt(MAX_USER_NO)+1)));
						form.put("user_kdate", ud.nowWDateTime_num().substring(0, 8));
						form.put("user_kname", "陳佩君");
						form.put("user_cstatus", "0");
						form.put("cnt2_tel1", form.get("cnt2_tel1_1").toString()+"-"+form.get("cnt2_tel1_2").toString());
						form.put("cnt2_fax", (form.get("cnt2_fax_1").toString().equals(""))?"":form.get("cnt2_fax_1").toString()+"-"+form.get("cnt2_fax_2").toString());
						form.put("user_tel", form.get("user_tel_1").toString()+"-"+form.get("user_tel_2").toString());
						form.put("user_fax", (form.get("user_fax_1").toString().equals(""))?"":form.get("user_fax_1").toString()+"-"+form.get("user_fax_2").toString());
						form.put("cnt2_name", form.get("user_copman").toString());
						
						this.getSqlDBUtility_51().insert("LAL.INSERT_USER",form);
					}
					List ls1 = this.getSqlDBUtility_LICENSE().queryForList("LICENSE.QUERY_LICENSE_PRICE",form);
					System.out.println(ls1);
					if(ls.size()>0) {
						form.put("rec_dprice", ((HashMap) ls1.get(0)).get("REC_DPRICE").toString());
						form.put("price", ((HashMap) ls1.get(0)).get("PRICE").toString());
					} 
					StringBuffer htmlCode = new StringBuffer() ;
					htmlCode.append("敬啟者 您好,<BR>");
					htmlCode.append("您申請的線上授權流水號:"+form.get("doc_no")+"已通過審核，<BR>");
					if(!((String)check.checkNull(form.get("msg").toString(), "")).equals("")) {
						htmlCode.append("<font color='red'>審核人員備註:<BR>"+form.get("msg").toString()+"</font><BR>");
					}
					htmlCode.append("請於近日內撥冗匯款，以利本會發證，匯款資料如下:<BR><BR>");
					htmlCode.append("<div style='margin-left:5%'>");
					htmlCode.append("<ol>");
					htmlCode.append("<li>本次授權金額為<font color='red'>新台幣"+ut.addComma(form.get("rec_dprice").toString())+"元(含稅)</font>。<BR>");
					htmlCode.append("<li>繳費方式(擇一)：(※請勿自行內扣手續費)</li>");
					htmlCode.append("<ol type='a'>");
					htmlCode.append("<li>郵局劃撥---劃撥帳號：19321052，戶名：社團法人中華音樂著作權協會</li>");
					htmlCode.append("<li>銀行匯款---匯款銀行：台新國際商業銀行(銀行代號：812) 南京東路分行(分行代碼：0115)<br> 帳號：2011-01-00021680帳戶名：社團法人中華音樂著作權協會</li>");
					htmlCode.append("<li>開立7天內之即期支票 （檯頭：社團法人中華音樂著作權協會）。</li>");
					htmlCode.append("</ol>");
					htmlCode.append("<li>備妥上述劃撥單收據影本、匯款收據影本或即期支票傳真或掛號寄至本協會，以利本會後續結案發證作業。</li>");					
					htmlCode.append("</ol>");
					htmlCode.append("</div>");					
					htmlCode.append("有任何問題請與窗口【 陳佩君小姐, 電話: 02-25110869 分機260】連繫。");
					new SEND_EMAIL("must.license@must.org.tw","MUST授權部",form.get("cnt1_email").toString(),"MUST 線上授權申請",htmlCode);
					form.put("cont_bdate", form.get("cont_bdate_yy").toString()+form.get("cont_bdate_mm").toString()+form.get("cont_bdate_dd").toString());
					form.put("cont_edate", form.get("cont_edate_yy").toString()+form.get("cont_edate_mm").toString()+form.get("cont_edate_dd").toString());
					this.getSqlDBUtility_LICENSE().update("LICENSE.UPDATE_LICENSE_APPLY", form);					
					data.put("MSG", "資料已匯入法務授權系統!");
				}				
			}
			responseBean.setAjaxData(data);
			
		}
		
	
	public void doQuery(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();		
		List ls = this.getSqlDBUtility_LICENSE().queryForList("LICENSE.QUERY_LICENSE_MAIN",form);	
		responseBean.setAjaxData(ls);			
	}
	
	public void doQueryDetail(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();		
		List ls = this.getSqlDBUtility_LICENSE().queryForList("LICENSE.QUERY_LICENSE_MAIN_DETAIL",form);	
		responseBean.setAjaxData(ls);			
	}	

	public void doQueryMachine(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();		
		List ls = this.getSqlDBUtility_LICENSE().queryForList("LICENSE.QUERY_LICENSE_MAIN_MACHINE",form);	
		responseBean.setAjaxData(ls);			
	}
}
