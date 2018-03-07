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

public class MEMBERController extends BaseAbstractCommandController {
	
	public void doQuery(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		List ls = this.SqlDBUtility_MUST_WEB.queryForList("MEMBER.CHECKIN_QUERY", form);
		System.out.println("----會員資料----"+ls.size());
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
}
