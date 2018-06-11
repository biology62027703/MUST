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

public class LAL_CONTRACT_EXTENSIONController extends BaseAbstractCommandController {

	/**
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @param responseBean
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes"})
	public void doInsert(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{				
		HashMap data = new HashMap();
		try {	
			String today=ud.nowWDateTime_num().substring(0, 8);
			String year=today.substring(2,4);
			form.put("year", String.valueOf((Integer.parseInt(year)-1)));
			form.put("cont_notitle", "L"+year);//合約編號L開頭
			List ls = this.SqlDBUtility_51.queryForList("LAL.CHECK_CONT_NO", form);//檢查一次有沒有去年的合約
			if(ls.size()==1) {
				form.put("year", year);
				List lsthisyear = this.SqlDBUtility_51.queryForList("LAL.CHECK_CONT_NO", form);//再檢查一次有沒有今年的合約
				if(lsthisyear.size()>0) {
					data.put("MSG", "已經有20"+year+"年合約!");
				} else {
					List lscase = this.SqlDBUtility_51.queryForList("LAL.CHECK_CONT_NO_BY_CASE_NO", form);//再檢查一次這個個案編號有沒有綁住合約
					if(lscase.size()==0) {
						HashMap hm = (HashMap)ls.get(0);	
						String LASTcont_no = hm.get("CONT_NO").toString();
						List ls1 = this.getSqlDBUtility_51().queryForList("LAL.QUERY_MAX_CONT",form);	
						if(ls1.size()>0) {
							HashMap hm1 = (HashMap)ls1.get(0);
							hm.put("CONT_NO", hm1.get("CONT_NO").toString().substring(0,1)+(Integer.parseInt(hm1.get("CONT_NO").toString().substring(1))+1));						
							form.put("cont_no", hm.get("CONT_NO"));
							hm.put("CONT_KDATE", today);//建檔日期
							hm.put("CONT_SDATE", today);//簽約日期
							hm.put("CASE_NO", form.get("case_no"));//個案編號
							hm.put("CONT_NAME", form.get("cont_name"));//承辦人員	
							hm.put("CONT_BDATE", ud.addDate(hm.get("CONT_EDATE").toString(), 1, "date"));//合約起日
							hm.put("CONT_EDATE", ud.addDate(hm.get("CONT_EDATE").toString(), 1, "year"));//合約迄日
							hm.put("CONT_CDATE", ud.addDate(hm.get("CONT_EDATE").toString(), -2, "month"));//續約日期
							if(hm.get("CONT_RSTAGE").toString().equals("")||hm.get("CONT_RSTAGE").toString().equals("0")) {
								hm.put("CONT_RSTAGE", "1");
							}
							this.SqlDBUtility_51.update("LAL.CONTRACT_EXTENSION", hm);							
							form.put("LASTcont_no", LASTcont_no);
							form.put("today", today);
							this.SqlDBUtility_51.update("LAL.RECEIVE_EXTENSION", form);
							if(form.get("user_no").toString().indexOf("KAR")>-1) {
								this.SqlDBUtility_51.update("LAL.MACHINENO_EXTENSION", form);
							}
							if(form.get("user_no").toString().indexOf("BUS")>-1) {
								this.SqlDBUtility_51.update("LAL.BUSNO_EXTENSION", form);
							}
							data.put("MSG", "續約完成!");
							data.put("CONT_NO", form.get("cont_no").toString());
						}
					} else {
						data.put("MSG", "此個案編號已經有綁訂合約編號故無法續約!");
					}
				}
			} else {
				if(ls.size()==0) {
					data.put("MSG", "查無20"+form.get("year")+"年度合約");
				} else {
					data.put("MSG", "查到多筆20"+form.get("year")+"年度合約");
				}
			}
		} catch(Exception e) {
			this.SqlDBUtility_51.update("LAL.DELETE_CONTRACT", form);
			this.SqlDBUtility_51.update("LAL.DELETE_RECEIVE", form);
			this.SqlDBUtility_51.update("LAL.DELETE_MACHINENO", form);
			this.SqlDBUtility_51.update("LAL.DELETE_BUSNO", form);
			data.put("MSG", "系統發生錯誤，請洽詹姆斯!");
		}finally {
			responseBean.setAjaxData(data);	
		}		
	}
}
