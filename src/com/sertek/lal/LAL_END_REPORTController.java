package com.sertek.lal;

import com.sertek.sys.sys_User;
import com.sertek.table.*;
import com.sertek.util.CryptoUtil;
import com.sertek.util.CutBook;
import com.sertek.util.ReadWriteExcel;
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
import com.sertek.form.BaseAbstractCommandController;
import com.sertek.form.REPORT_FORMAT;

public class LAL_END_REPORTController extends BaseAbstractCommandController {
	
	private String[] LAL_END_REPORT3_TITLE = {"序號","持證人","授權起始日期","授權到期日期","台數","機台廠牌","機台編號"};
	private String[] LAL_END_REPORT3_COLUNM = {"SEQ","USER_CNAME","CONT_BDATE","CONT_EDATE","MAC_COUNT","MAC_NAME","MAC_NO"};
	private String[] LAL_END_REPORT1_TITLE = {"序號","收據編號","發票編號","持證人","發票名稱","統編","兌現日期","匯款人","銀行別","MÜST應收金額","MÜST稅額","MÜST兌現金額","MÜST2017年授權金額","MÜST2017年授權稅額","MÜST2017年授權總額","MÜST2018年授權金額","MÜST2018年授權稅額","MÜST2018年授權總額","MÜST授權起始日期","MÜST授權到期日期","二合一授權起始日期","二合一授權到期日期","起","迄","2017天數","起","迄","2018天數","2017","2018","營業使用地址","台數"};
	private String[] LAL_END_REPORT2_TILTE={"序號","客戶名稱","授權地址(營業地址)","年度總金額","年度總金額(含稅)","母合約台數","MUST(中華)","MUST(含稅)","服務費","稅(5%)","應請款金額","TMCS(台協)","TMCS(含稅)","服務費","稅(5%)","應請款金額","應請款總金額","起始日期","到期日期","廠牌","機台編號","貼證流水號"};
	/*public void END_REPORT3(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean)throws Exception{		
		HashMap data = new HashMap();
		List ls = this.SqlDBUtility_51.queryForList("LAL_END_REPORT.LAL_QUERY_END_REPORT3", form);	
		System.out.println(ls);	
		data.put("DATA", ls);
		//data.put("MSG","OK");
		responseBean.setAjaxData(data);	
	}*/
	
	public void END_REPORT3(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		List ls = this.SqlDBUtility_51.queryForList("LAL_END_REPORT.LAL_QUERY_END_REPORT3", form);
		System.out.println("----報表資料筆數----"+ls.size());
		System.out.println(form.get("INONE_SOURCE"));	
		if (ls.size()>0) {
			ReadWriteExcel ReadWriteExcel = new ReadWriteExcel();		
			ReadWriteExcel.writeXLSXFile("D:/LAL_REPORT/END_REPORT/"+form.get("INONE_SOURCE")+"_REPORT3.xlsx", "", ls,LAL_END_REPORT3_TITLE,LAL_END_REPORT3_COLUNM);
			data.put("MSG", "報表產生完畢！");
			responseBean.setAjaxData(data);	
		} else {
			data.put("MSG", "查無資料，請確認查詢條件!");
			responseBean.setAjaxData(data);
		}
		
	}
}
