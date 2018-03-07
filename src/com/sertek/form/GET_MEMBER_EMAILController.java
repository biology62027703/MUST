package com.sertek.form;

import com.sertek.sys.sys_User;
import com.sertek.table.*;
import com.sertek.util.CryptoUtil;
import com.sertek.util.CutBook;
import com.sertek.util.CheckObject;
import com.sertek.util.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sertek.form.ResponseBean;
import com.sertek.form.BaseAbstractCommandController;
import com.sertek.sys.sys_User;
import com.sertek.util.CryptoUtil;

public class GET_MEMBER_EMAILController extends BaseAbstractCommandController {
	StringBuffer mail = new StringBuffer();
	public void doQuery(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		List ls = this.SqlDBUtility_MUST_WEB.queryForList("MEMBER.QUERY_EMAIL", form);
		System.out.println("----會員EMAIL資料筆數----"+ls.size());
		System.out.println(ls);
		
		for(int i=0;i<ls.size();i++) {
			HashMap hm = (HashMap)ls.get(i);
			mail.append(hm.get("PRIVATE_EMAIL").toString()+";");
		}
		
		data.put("DATA", ls);		
		responseBean.setAjaxData(data);	
	}
	
	public void doQuery_Extra(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		List ls = this.SqlDBUtility_MUST_WEB.queryForList("MEMBER.QUERY_EXTRA_EMAIL", form);
		System.out.println("----額外要寄信的EMAIL資料筆數----"+ls.size());
		System.out.println(ls);
		//StringBuffer mail = new StringBuffer();
		for(int i=0;i<ls.size();i++) {
			HashMap hm = (HashMap)ls.get(i);
			mail.append(hm.get("EMAIL").toString()+";");
		}
		CutBook CB = new CutBook();
		CB.setBookContents(mail.toString());
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
}
