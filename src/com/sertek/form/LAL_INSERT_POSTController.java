package com.sertek.form;

import com.sertek.sys.sys_User;
import com.sertek.table.*;
import com.sertek.util.CryptoUtil;
import com.sertek.util.CutBook;
import com.sertek.util.CheckObject;
import com.sertek.util.util;
import com.sertek.util.util_date;

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

public class LAL_INSERT_POSTController extends BaseAbstractCommandController {
	
	public void dopost(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		List ls = this.SqlDBUtility_51.queryForList("LAL.POST_EXCEL_FILE_QUERY", form);
		util_date ud = new util_date();
		String POST_KDATE=ud.nowWDateTime().replace("/", "").substring(0,8);
		if(ls.size()>0) {
			//ls.clear();
			for (int i=0;i<ls.size();i++) {
				HashMap hm = (HashMap)ls.get(i);
				List ls1 = this.SqlDBUtility_51.queryForList("LAL.POST_QUERY", hm);
				System.out.println("-------ls1:"+ls1);
				if(ls1.size()>0) {					
					HashMap hm_post = (HashMap)ls1.get(0);
					hm.put("USER_NO", hm_post.get("USER_NO").toString());
					hm.put("POST_SEQNO", Integer.parseInt(hm_post.get("POST_SEQNO").toString())+1);					
					hm.put("POST_KDATE", POST_KDATE);
					System.out.println("-------準備要INSERT的資料:"+hm);
					this.SqlDBUtility_51.update("LAL.POST_INSERT", hm);					
				}
			}			
			data.put("MSG", "done!");
		} else {
			data.put("MSG", "LAL.POST_EXCEL_FILE 無資料!");
		}	
		//System.out.println(data);	
		responseBean.setAjaxData(data);	
	}

}
