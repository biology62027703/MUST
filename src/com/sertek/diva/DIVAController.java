package com.sertek.diva;

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

public class DIVAController extends BaseAbstractCommandController {
	
	public void UNLOCK_USER(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		/*List ls = this.SqlDBUtility.queryForList("LAL_END_REPORT.LAL_QUERY_END_REPORT3", form);
		System.out.println("----報表資料筆數----"+ls.size());
		System.out.println(form.get("INONE_SOURCE"));	
		if (ls.size()>0) {			
			responseBean.setAjaxData(data);	
		} else {
			data.put("MSG", "查無資料，請確認查詢條件!");
			responseBean.setAjaxData(data);
		}*/
		
	}
	
	public void CHANGE_PASSWORD(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		/*List ls = this.SqlDBUtility.queryForList("LAL_END_REPORT.LAL_QUERY_END_REPORT3", form);
		System.out.println("----報表資料筆數----"+ls.size());
		System.out.println(form.get("INONE_SOURCE"));	
		if (ls.size()>0) {
			
			responseBean.setAjaxData(data);	
		} else {
			data.put("MSG", "查無資料，請確認查詢條件!");
			responseBean.setAjaxData(data);
		}*/
		
	}
}
