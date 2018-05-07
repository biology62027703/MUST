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

public class CALENDARController extends BaseAbstractCommandController {
	public void doQuery(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		List ls = this.SqlDBUtility_ERP.queryForList("CALENDAR.QUERY", form);		
		ArrayList ret = new ArrayList();
		HashMap color = new HashMap();
		color.put("red", "#FF3333");
		color.put("yellow", "#BBBB00");
		color.put("green", "#227700");
		color.put("blue", "#0044BB");
		color.put("orange", "#AA7700");
		color.put("black", "#000000");
		for (int i=0;i<ls.size();i++) {
			HashMap hm = (HashMap)ls.get(i);
			HashMap hm1 = new HashMap();
			hm1.put("title", hm.get("TITLE"));
			hm1.put("start", hm.get("START"));
			hm1.put("end", hm.get("END"));
			hm1.put("backgroundColor", color.get(hm.get("COLOR")));
			//System.out.println(hm1);
			ret.add(hm1);
			//hm1.clear();
		}
		System.out.println(ret);
		data.put("DATA", ret);
		//ls.isEmpty();		
		responseBean.setAjaxData(data);	
	}
	
}
