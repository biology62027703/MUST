package com.sertek.form;
/**********************************************************/

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import sun.font.FontManager;

import com.sertek.form.MEMBER_CHECKController;
import com.sertek.util.QRCodeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItextMerge {

	//static String[] files = {"C:\\會員大會通知(2016範本).pdf","C:\\c.pdf"};

    public static void main(String[] args) throws IOException, DocumentException {
    	MEMBER_CHECKController MEMBER_CHECKController = new MEMBER_CHECKController();
    	//List ls = MEMBER_CHECKController.DO_NOTE();
    	//System.out.println(ls);
    	PdfReader reader = new PdfReader("C:\\TEST.pdf");
    	Rectangle pageSize = reader.getPageSize(1);
    	System.out.println("pageSize.getWidth():"+pageSize.getWidth());
    	System.out.println("pageSize.getHeight():"+pageSize.getHeight());
    	Rectangle newSize = new Rectangle(pageSize.getWidth(), pageSize.getHeight());
    	//調整嵌入文字或圖片的X軸Y軸從哪邊開始
    	Document document_qucode = new Document(newSize, 36, 36, 600, 0);
    	int n = (int)Math.pow(2, 2);
    	PdfWriter writer = PdfWriter.getInstance(document_qucode, new FileOutputStream(String.format("O:\\IT\\MUST_MEMBER_CHECKIN_REF\\b.pdf", 4)));
    	
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
	    //BarcodeQRCode qrcode = new BarcodeQRCode("http://member.must.org.tw:8080/MUST/CHECKIN/INDEX.jsp?member_no=U0003", 1, 1, null);  
	    QRCodeUtil.getLogoQRCode("http://member.must.org.tw:8080/MUST/CHECKIN/INDEX.jsp?member_no=U0003", "");
	    Image qrcodeImage = Image.getInstance("C:\\TDC-test.png");
    	//QRCODE的位置
    	qrcodeImage.setAbsolutePosition(400,280); 
    	//QRCODE的大小
    	qrcodeImage.scalePercent(40);    	
    	//document_qucode.add(qrcodeImage);
    	cb.addImage(qrcodeImage);
    	//BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\mingliu.ttc,1", BaseFont.IDENTITY_H,  BaseFont.EMBEDDED);
    	
    	BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\KAIU.TTF", BaseFont.IDENTITY_H,  BaseFont.EMBEDDED);
    	
    	Font font = new Font( bf, 12,Font.BOLD );
    	StringBuffer word = new StringBuffer();
    	
    	word.append("\n貴會員 香港商華納音樂出版有限公司台灣分公司(會員編號U0003)，於本次大會之表決權總數為9999票\n");
    	word.append("貴公司登記於協會之會員代表為 987654321 ，如需更換會員代表，請填具後附之團體會員代表指派書，由指派會員代表出席。 \n");
    	//代表人在DIVA DB裡面的MUST.MBR_MEMBER_HEADER.BUSINESS_REG_NO
    	word.append("第三行團體會員代表人應攜帶出席證件\n");
    	word.append("第四行測試\n");
    	word.append("第五行備註\n");
    	Paragraph paragraph = new Paragraph(word.toString(),font);

		paragraph.setAlignment(Element.ALIGN_LEFT);
		
		document_qucode.add(paragraph);
    	
    	document_qucode.close();
    	reader.close();
    	/*Document document = new Document();
    	
    
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
        
        document.close();*/
    	//manipulatePdf("C:\\c.pdf", "C:\\d.pdf",2);
    	//----------------------------------------------------------------------------------------------------
    	reader = new PdfReader("C:\\agent_sample.pdf");
    	pageSize = reader.getPageSize(1);
    	System.out.println("pageSize.getWidth():"+pageSize.getWidth());
    	System.out.println("pageSize.getHeight():"+pageSize.getHeight());
    	newSize = new Rectangle(pageSize.getWidth(), pageSize.getHeight());
    	//調整嵌入文字或圖片的X軸Y軸從哪邊開始
    	document_qucode = new Document(newSize, 125, 36, 160, 0);
    	n = (int)Math.pow(2, 2);
    	writer = PdfWriter.getInstance(document_qucode, new FileOutputStream(String.format("O:\\IT\\MUST_MEMBER_CHECKIN_REF\\c.pdf", 4)));
    	
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
	    QRCodeUtil.getLogoQRCode("http://member.must.org.tw:8080/MUST/CHECKIN/INDEX.jsp?member_no=U0003", "");
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
    	
    	word.append("\n新加坡商新索國際版權股份有限公司台灣分公司(U0081)");
    	paragraph = new Paragraph(word.toString(),font);

		paragraph.setAlignment(Element.ALIGN_LEFT);
		
		document_qucode.add(paragraph);
    	
    	document_qucode.close();
    	reader.close();
    }
    
    /*@SuppressWarnings("deprecation")
	public void dopdf(HttpServletRequest request, HttpServletResponse response) throws Exception{		
    	PdfReader reader = new PdfReader("C:\\a.pdf");
    	Rectangle pageSize = reader.getPageSize(1);
    	Rectangle newSize = new Rectangle(pageSize.getWidth(), pageSize.getHeight());
    	Document document_qucode = new Document(newSize, 0, 0, 0, 0);
    	int n = (int)Math.pow(2, 2);
    	PdfWriter writer = PdfWriter.getInstance(document_qucode, new FileOutputStream(String.format("C:\\b.pdf", 4)));
	   
    	document_qucode.open();
    	BarcodeQRCode qrcode = new BarcodeQRCode("http://member.must.org.tw:8080/MUST/CHECKIN/INDEX.jsp?member_no=U0002", 1, 1, null);  
    	Image qrcodeImage = qrcode.getImage();  
    	qrcodeImage.setAbsolutePosition(420,100);  
    	qrcodeImage.scalePercent(200);    	
    	document_qucode.add(qrcodeImage);
    	
    	//BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\mingliu.ttc,1", BaseFont.IDENTITY_H,  BaseFont.EMBEDDED);
    	//Font font = new Font( bf, 12, Font.NORMAL );
    	
    	//Paragraph paragraph = new Paragraph("測試TEST",font);
		//paragraph.set
		//paragraph.setAlignment(Element.ALIGN_CENTER);
		//paragraph.setFirstLineIndent(500);
		//document_qucode.add(paragraph);
    	
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
    	manipulatePdf("C:\\c.pdf", "C:\\d.pdf",2);
    }
    
    public  void manipulatePdf(String src, String dest, int pow)
    	    throws IOException, DocumentException {
    	    // reader for the src file
    	    PdfReader reader = new PdfReader(src);
    	    // initializations
    	    Rectangle pageSize = reader.getPageSize(1);
    	    Rectangle newSize = (pow % 2) == 0 ? new Rectangle(pageSize.getWidth(), pageSize.getHeight()) :new Rectangle(pageSize.getHeight(), pageSize.getWidth());
    	    Rectangle unitSize = new Rectangle(pageSize.getWidth(), pageSize.getHeight());
    	    for (int i = 0; i < pow; i++) {
    	        unitSize = new Rectangle(unitSize.getHeight() / 2, unitSize.getWidth());
    	        System.out.println(unitSize);
    	    }
    	    int n = (int)Math.pow(2, pow);
    	    int r = (int)Math.pow(2, pow / 2);
    	    int c = n / r;
    	    // step 1
    	    Document document = new Document(newSize, 0, 0, 0, 0);
    	    // step 2
    	    System.out.println("n="+n);
    	    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(String.format(dest, n)));
    	    // step 3
    	    document.open();
    	    // step 4
    	    PdfContentByte cb = writer.getDirectContent();
    	    PdfImportedPage page;
    	    Rectangle currentSize;
    	   // float offsetX, offsetY, factor;
    	    int total = reader.getNumberOfPages();
    	    for (int i = 0; i < total; ) {
    	        if (i % n == 0) {
    	            document.newPage();
    	        }
    	    	currentSize = reader.getPageSize(++i);
     	        System.out.println("currentSize="+currentSize);
     	        page = writer.getImportedPage(reader, i);
     	        cb.addTemplate(page, 0, 0);

    	    	
    	    }
    	    // step 5
    	    document.close();
    	    reader.close();
    	}*/
}