package com.sertek.form;

import com.sertek.sys.sys_User;
import com.sertek.table.*;
import com.sertek.util.CryptoUtil;
import com.sertek.util.CutBook;
import com.sertek.util.CheckObject;
import com.sertek.util.util;

import java.io.FileOutputStream;
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
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.sertek.form.BaseAbstractCommandController;
import com.sertek.sys.sys_User;
import com.sertek.util.CryptoUtil;

public class BackstageCONTROLLER extends BaseAbstractCommandController {
	
	public void doQuery(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		List ls = this.SqlDBUtility_51.queryForList("BACKSTAGE.QUERY", form);
		System.out.println(ls);	
		data.put("DATA", ls);
		
		responseBean.setAjaxData(data);	
	}
	
	public void doQueryDetail(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		List ls = this.SqlDBUtility_MUST_WEB.queryForList("MEMBER.QUERY_EMAIL_DETAIL", form);
		System.out.println("----會員詳細資料----"+ls.size());
		System.out.println(ls);
		data.put("DATA", ls);		
		responseBean.setAjaxData(data);	
	}
	
	public void doCheckin(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		this.SqlDBUtility_MUST_WEB.update("MEMBER.CHECKIN", form);			
		responseBean.setAjaxData(data);	
	}
	
	public void dopdf(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		String[] files = {"C:\\a.pdf","C:\\b.pdf"};
		PdfReader reader = new PdfReader("C:\\a.pdf");
    	Rectangle pageSize = reader.getPageSize(1);
    	Rectangle newSize = new Rectangle(pageSize.getWidth(), pageSize.getHeight());
    	Document document_qucode = new Document(newSize, 70, 0, 600, 0);
    	int n = (int)Math.pow(2, 2);
    	PdfWriter writer = PdfWriter.getInstance(document_qucode, new FileOutputStream(String.format("C:\\b.pdf", 4)));
	   
    	document_qucode.open();
    	BarcodeQRCode qrcode = new BarcodeQRCode("http://member.must.org.tw:8080/MUST/CHECKIN/INDEX.jsp?member_no=U0002", 1, 1, null);  
    	Image qrcodeImage = qrcode.getImage();  
    	qrcodeImage.setAbsolutePosition(450,100);  
    	qrcodeImage.scalePercent(200);    	
    	document_qucode.add(qrcodeImage);
    	
    	//BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\mingliu.ttc,1", BaseFont.IDENTITY_H,  BaseFont.EMBEDDED);
    	
    	BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\KAIU.TTF", BaseFont.IDENTITY_H,  BaseFont.EMBEDDED);
    	
    	Font font = new Font( bf, 10, Font.BOLD );
    	StringBuffer word = new StringBuffer();
    	word.append("第一行會員名稱+票數\n");
    	word.append("第二行團體會員代表人\n");
    	word.append("第三行團體會員代表人應攜帶出席證件\n");
    	word.append("第四行測試\n");
    	word.append("第五行備註\n");
    	Paragraph paragraph = new Paragraph(word.toString(),font);
		//paragraph.setAlignment(Element.ALIGN_CENTER);
		document_qucode.add(paragraph);
    	
    	document_qucode.close();
    	Document document = new Document();
    	
    
        PdfCopy copy = new PdfCopy(document, new FileOutputStream("C:\\c.pdf"));

        document.open();
        for (int j=0;j<files.length;j++){
            reader = new PdfReader(files[j]);
            for (int i = 1; i <= reader.getNumberOfPages(); i++){
                // optionally write an if statement to include the page
                copy.addPage(copy.getImportedPage(reader, i));
            }
            copy.freeReader(reader);
            reader.close();
        }
        document.newPage();
        
        document.close();
        ItextMerge ItextMerge = new ItextMerge();
        //ItextMerge.manipulatePdf("C:\\c.pdf", "C:\\d.pdf",2);
		responseBean.setAjaxData("done");	
	}
}
