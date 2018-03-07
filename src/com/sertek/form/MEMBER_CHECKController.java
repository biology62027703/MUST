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

public class MEMBER_CHECKController extends BaseAbstractCommandController {
	
	//@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doQuery(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		List ls = this.SqlDBUtility_MUST_WEB.queryForList("MEMBER.CHECK_MEMBER_QUERY", form);
		System.out.println(ls);	
		data.put("DATA", ls);
		if(ls.size()>0) {
			
			HashMap hm = (HashMap)ls.get(0);
			System.out.println("hm.get(\"TICKET\").toString():"+hm.get("TICKET").toString());
			if(ls.size()>1) {
				data.put("MSG", "受委託人"+hm.get("CHINESE_NAME").toString()+"查詢到多筆資料，請重新輸入。");
			} else if(hm.get("TICKET").toString().equals("NULL")||hm.get("TICKET").toString().trim().equals("")) {
				data.put("MSG", "受委託人"+hm.get("CHINESE_NAME").toString()+"為準會員，無法接受委託。");
			} else if(!hm.get("AGENT").toString().equals("NULL")&&!hm.get("AGENT").toString().equals("")) {
				data.put("MSG", "受委託人"+hm.get("CHINESE_NAME").toString()+"非親自報到，無法接受委託。");
			} else {
				List ls1 = this.SqlDBUtility_MUST_WEB.queryForList("MEMBER.CHECK_AGENTMEMBER_QUERY", hm);		
				if(ls1.size()>0) {
					HashMap hm_agnet = (HashMap)ls1.get(0);
					data.put("MSG", "受委託人"+hm.get("CHINESE_NAME").toString()+"已接受會員"+hm_agnet.get("CHINESE_NAME").toString()+"委託，無法再接受委託。");
				} else {
					if(!hm.get("SITUATION").toString().equals("Y")) {
						data.put("MSG", "受委託人"+hm.get("CHINESE_NAME").toString()+"尚未報到!");
					}else {
						data.put("MSG","");
					}
				}
			}
		}
		responseBean.setAjaxData(data);	
	}
	
	public void doQueryDetail(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		List ls = this.SqlDBUtility_MUST_WEB.queryForList("MEMBER.QUERY_EMAIL_DETAIL", form);
		data.put("DATA", ls);		
		responseBean.setAjaxData(data);	
	}
	
	public void doCheckin(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		this.SqlDBUtility_MUST_WEB.update("MEMBER.CHECKIN", form);			
		responseBean.setAjaxData(data);	
	}
	
	public void DO_NOTE(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{
		HashMap data = new HashMap();
		List ls = this.SqlDBUtility_MUST_WEB.queryForList("MEMBER.DO_NOTE");
		System.out.println(ls);
		for(int i=0;i<ls.size();i++) {
			HashMap hm = (HashMap)ls.get(i);
			PdfReader reader = new PdfReader("C:\\2017member.pdf");
	    	Rectangle pageSize = reader.getPageSize(1);
	    	System.out.println("pageSize.getWidth():"+pageSize.getWidth());
	    	System.out.println("pageSize.getHeight():"+pageSize.getHeight());
	    	Rectangle newSize = new Rectangle(pageSize.getWidth(), pageSize.getHeight());
	    	//調整嵌入文字或圖片的X軸Y軸從哪邊開始
	    	Document document_qucode = new Document(newSize, 36, 36, 600, 0);
	    	int n = (int)Math.pow(2, 2);
	    	PdfWriter writer = PdfWriter.getInstance(document_qucode, new FileOutputStream(String.format("O:\\IT\\MUST_MEMBER_CHECKIN_REF\\"+hm.get("MEMBER_NO")+".pdf", 4)));
	    	
	    	document_qucode.open();
	    	PdfContentByte cb = writer.getDirectContent();
		    PdfImportedPage page;
		    Rectangle currentSize;
	    	
	    	int total = reader.getNumberOfPages();
		    //for (int i = 0; i < total; ) {
		       /* if (i % n == 0) {
		        	document_qucode.newPage();
		        }*/
		    	//currentSize = reader.getPageSize(++i);
	 	        //System.out.println("currentSize="+currentSize);
	 	        page = writer.getImportedPage(reader, 1);
	 	        cb.addTemplate(page, 0, 0);

		    //}
		    //BarcodeQRCode qrcode = new BarcodeQRCode("http://member.must.org.tw:8080/MUST/CHECKIN/INDEX.jsp?member_no="+hm.get("MEMBER_NO"), 1, 1, null);  
	 	    QRCodeUtil.getLogoQRCode("http://192.168.0.2:8080/MUST/CHECKIN/INDEX.jsp?member_no="+hm.get("MEMBER_NO"), "");
	 	    //QRCodeUtil.getLogoQRCode("http://member.must.org.tw:8080/MUST/CHECKIN/INDEX.jsp?member_no="+hm.get("MEMBER_NO"), "");
	 		Image qrcodeImage = Image.getInstance("C:\\TDC-test.png");
	    	//QRCODE的位置(第一象限)
	    	qrcodeImage.setAbsolutePosition(400,245); 
	    	//QRCODE的大小
	    	qrcodeImage.scalePercent(40);    	
	    	//document_qucode.add(qrcodeImage);
	    	cb.addImage(qrcodeImage);
	    	//BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\mingliu.ttc,1", BaseFont.IDENTITY_H,  BaseFont.EMBEDDED);
	    	
	    	BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\KAIU.TTF", BaseFont.IDENTITY_H,  BaseFont.EMBEDDED);
	    	
	    	Font font = new Font( bf, 12, Font.BOLD );
	    	StringBuffer word = new StringBuffer();

	    	word.append("\n貴會員 "+hm.get("CHINESE_NAME")+" 編號 "+hm.get("MEMBER_NO")+" 於本次大會之表決權總數為"+hm.get("TICKET")+"票。\n");
	    	if(hm.get("MEMBER_NO").toString().indexOf("U")>-1){
		    	word.append("\n貴公司登記於協會之會員代表為 "+hm.get("MEMBER_REF")+"，如需更換會員代表，請填具後附之團體會員代表指派書，由指派會員代表出席。\n");
		    	//代表人在DIVA DB裡面的MUST.MBR_MEMBER_HEADER.BUSINESS_REG_NO		    	
	    	}
	    	word.append("\n會員代表需攜帶有相片之身份證件及本開會通知以辦理出席報到手續。\n");
	    	//word.append("第四行測試\n");
	    	word.append("\n備註:本次會員大會後有頒獎典禮及晚宴，請於12/15(五)前回覆本會出席意願調查表。");
	    	//word.append("\n     2.會議與晚宴地點於飯店3樓，開車前往者可免費使用B3至B6停車場。\n");
	    	Paragraph paragraph = new Paragraph(word.toString(),font);

			paragraph.setAlignment(Element.ALIGN_LEFT);
			
			document_qucode.add(paragraph);
	    	
	    	document_qucode.close();
	    	reader.close();
	    	//-------------------------------------------------委任書-----------------------------------------------------------------------------    	
	    	//reader = new PdfReader("C:\\agent_sample.pdf");
	    	reader = new PdfReader("C:\\2017agent_sample.pdf");
	    	pageSize = reader.getPageSize(1);
	    	System.out.println("pageSize.getWidth():"+pageSize.getWidth());
	    	System.out.println("pageSize.getHeight():"+pageSize.getHeight());
	    	newSize = new Rectangle(pageSize.getWidth(), pageSize.getHeight());
	    	//調整嵌入文字或圖片的X軸Y軸從哪邊開始
	    	document_qucode = new Document(newSize, 125, 36, 145, 0);
	    	n = (int)Math.pow(2, 2);
	    	writer = PdfWriter.getInstance(document_qucode, new FileOutputStream(String.format("O:\\IT\\MUST_MEMBER_CHECKIN_REF\\"+hm.get("MEMBER_NO")+"_agent.pdf", 4)));
	    	
	    	document_qucode.open();
	    	cb = writer.getDirectContent();
		    total = reader.getNumberOfPages();
		    //for (int i = 0; i < total; ) {
		       /* if (i % n == 0) {
		        	document_qucode.newPage();
		        }*/
		    	//currentSize = reader.getPageSize(++i);
	 	        //System.out.println("currentSize="+currentSize);
	 	        page = writer.getImportedPage(reader, 1);
	 	        cb.addTemplate(page, 0, 0);

		    //}
		    //BarcodeQRCode qrcode = new BarcodeQRCode("http://member.must.org.tw:8080/MUST/CHECKIN/INDEX.jsp?member_no=U0003", 1, 1, null);  
		    QRCodeUtil.getLogoQRCode("http://192.168.0.2:8080/MUST/CHECKIN/INDEX.jsp?member_no="+hm.get("MEMBER_NO"), "");
	 	    //QRCodeUtil.getLogoQRCode("http://member.must.org.tw:8080/MUST/CHECKIN/INDEX.jsp?member_no="+hm.get("MEMBER_NO"), "");
	 	    qrcodeImage = Image.getInstance("C:\\TDC-test.png");
	    	//QRCODE的位置
	    	qrcodeImage.setAbsolutePosition(420,420); 
	    	//QRCODE的大小
	    	qrcodeImage.scalePercent(40);    	
	    	//document_qucode.add(qrcodeImage);
	    	cb.addImage(qrcodeImage);
	    	//BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\mingliu.ttc,1", BaseFont.IDENTITY_H,  BaseFont.EMBEDDED);
	    	
	    	bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\KAIU.TTF", BaseFont.IDENTITY_H,  BaseFont.EMBEDDED);
	    	
	    	font = new Font( bf, 14,Font.BOLD );
	    	word = new StringBuffer();
	    	
	    	//word.append("\n新加坡商新索國際版權股份有限公司台灣分公司(U0081)");
	    	word.append("\n"+hm.get("CHINESE_NAME")+" ("+hm.get("MEMBER_NO")+")\n");
	    	paragraph = new Paragraph(word.toString(),font);

			paragraph.setAlignment(Element.ALIGN_LEFT);
			
			document_qucode.add(paragraph);
	    	
	    	document_qucode.close();
	    	reader.close();
	    	
		}
    	
    	data.put("MSG","");
    	responseBean.setAjaxData(data);
	}
}
