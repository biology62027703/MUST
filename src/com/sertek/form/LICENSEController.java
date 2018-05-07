package com.sertek.form;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sertek.form.ResponseBean;
import com.sertek.form.BaseAbstractCommandController;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class LICENSEController extends BaseAbstractCommandController {
	
	
	public void doSave(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap();
		
		form.put("cnt2_tel1", form.get("cnt2_tel1_1").toString()+"-"+form.get("cnt2_tel1_2").toString());
		form.put("cnt2_fax", (form.get("cnt2_fax_1").toString().equals(""))?"":form.get("cnt2_fax_1").toString()+"-"+form.get("cnt2_fax_2").toString());
		form.put("user_tel", form.get("user_tel_1").toString()+"-"+form.get("user_tel_2").toString());
		form.put("user_fax", (form.get("user_fax_1").toString().equals(""))?"":form.get("user_fax_1").toString()+"-"+form.get("user_fax_2").toString());
		form.put("cont_bdate", form.get("cont_bdate_yy").toString()+form.get("cont_bdate_mm").toString()+form.get("cont_bdate_dd").toString());
		form.put("cont_edate", form.get("cont_edate_yy").toString()+form.get("cont_edate_mm").toString()+form.get("cont_edate_dd").toString());		
		form.put("status", "1");
		if(((String)check.checkNull(form.get("nature"), "")).equals("")) {
			form.put("nature", "1");
		}
		//LICENSE SERVER新增資料
		if(((String)check.checkNull(form.get("doc_no"), "")).equals("")) {
			form.put("doc_no", ud.nowWDateTime_num()+String.valueOf(Math.random()).substring(3, 6));
			this.getSqlDBUtility_LICENSE().insert("LICENSE.INSERT_LICENSE", form);
		} else {//LICENSE SERVER修改資料
			this.getSqlDBUtility_LICENSE().update("LICENSE.UPDATE_LICENSE", form);
		}
		HashMap hm = new HashMap();
		hm.put("doc_no", form.get("doc_no"));
		this.getSqlDBUtility_LICENSE().insert("LICENSE.DELETE_MACHINE", hm);
		boolean check=true;
		if(form.get("factory").toString().indexOf("@")>-1) {
			String [] factoryarray = request.getParameterValues("factory[]");
			String [] machinearray = request.getParameterValues("machine[]");
			for(int i=0;i<factoryarray.length;i++) {
				if(!factoryarray[i].equals("")&&!machinearray[i].equals("")) {					
					hm.put("factory",URLDecoder.decode(factoryarray[i], "utf-8").replaceAll("%20", " "));
					hm.put("machine", URLDecoder.decode(machinearray[i], "utf-8"));
					List ls = this.getSqlDBUtility_LICENSE().queryForList("LICENSE.QUERY_LICENSE_MAIN_MACHINE",hm);
					if(ls.size()>0) {
						this.getSqlDBUtility_LICENSE().insert("LICENSE.DELETE_MACHINE", hm);
						this.getSqlDBUtility_LICENSE().insert("LICENSE.DELETE_LICENSE", hm);
						data.put("msg", "機台號碼重複，請確認填寫資料。");
						responseBean.setAjaxData(data);	
						check=false;
						break;
					}
					this.getSqlDBUtility_LICENSE().insert("LICENSE.INSERT_MACHINE", hm);
				}
			}
		} else {
			this.getSqlDBUtility_LICENSE().insert("LICENSE.INSERT_MACHINE", form);
		}
		System.out.println(form);		
		if(check) {
			//寄給填表單的人
			StringBuffer htmlCode = new StringBuffer() ;
			htmlCode.append("敬啟者 您好,<BR>");
			htmlCode.append("您申請的 "+form.get("user_cname")+" 電腦伴唱機線上授權流水編號為:"+form.get("doc_no")+"<BR>");
			htmlCode.append("我們會盡快回覆您審查結果，審查期間若有其他問題，<BR>");
			htmlCode.append("請與窗口【 陳佩君小姐, 電話: 02-25110869 分機260】連繫。");
			new SEND_EMAIL("must.license@must.org.tw","MUST授權部",form.get("cnt1_email").toString(),"MUST 線上授權申請",htmlCode);
			//寄給內部同事
			htmlCode = new StringBuffer() ;
			htmlCode.append("您好,<BR>");
			htmlCode.append("電腦伴唱機線上授權流水編號為:"+form.get("doc_no")+"<BR>");
			htmlCode.append("請點選以下網址確認:<BR>");
			/*htmlCode.append("<a href='http://192.168.1.128:8080/MUST/LICENSE_CONFIRM.jsp?doc_no="+form.get("doc_no")+"'>請點此進行MUST線上授權資料審核<br></a>");*/
			htmlCode.append("<a href='http://imust.must.org.tw:9000/MUST/LICENSE/LICENSE_CONFIRM.jsp?doc_no="+form.get("doc_no")+"'>請點此進行MUST線上授權資料審核<br></a>");
			htmlCode.append("有任何問題請與姆斯聯絡!");
			new SEND_EMAIL("must.license@must.org.tw","MUST授權部","Pallas.chen@must.org.tw,james.huang@must.org.tw","MUST 線上授權申請需求單",htmlCode);
			data.put("msg", "");
			responseBean.setAjaxData(data);	
		}
		
	}
	
		public void doUpdate(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
			HashMap data = new HashMap();
			System.out.println(form);
			//退回
			if(form.get("status").toString().equals("0")) {
				this.getSqlDBUtility_LICENSE().insert("LICENSE.UPDATE_LICENSE_BACK", form);				
				StringBuffer htmlCode = new StringBuffer() ;
				htmlCode.append("敬啟者 您好,<BR>");
				htmlCode.append("您申請的 "+form.get("user_cname")+" 線上授權流水號:"+form.get("doc_no")+"被退回<BR>");
				htmlCode.append("原因是:<BR>"+form.get("msg").toString()+"<BR>");
				htmlCode.append("請點選以下網址修正:<br><a href='http://license.must.org.tw:8080/MUST/MUST_LICENSE_APPLY_EDIT.jsp?doc_no="+form.get("doc_no")+"'>請點此進行MUST線上授權資料修改</a><br>");
				/*htmlCode.append("請點選以下網址修正:<br><a href='http://192.168.1.128:8080/MUST/MUST_LICENSE_APPLY_EDIT.jsp?doc_no="+form.get("doc_no")+"'>請點此進行MUST線上授權資料修改</a><br>");*/
				htmlCode.append("有任何問題請與窗口【 陳佩君小姐, 電話: 02-25110869 分機260】連繫。");
				new SEND_EMAIL("must.license@must.org.tw","MUST授權部",form.get("cnt1_email").toString(),"MUST 線上授權申請",htmlCode);
				new SEND_EMAIL("must.license@must.org.tw","MUST授權部","Pallas.chen@must.org.tw,james.huang@must.org.tw","MUST 線上授權申請",htmlCode);
			}
			//同意
			if(form.get("status").toString().equals("2")) {
				//先檢核資料
				//新增案件
				if(((String)check.checkNull(form.get("nature"), "")).equals("1")||((String)check.checkNull(form.get("nature"), "")).equals("")) {
					List ls = this.getSqlDBUtility_51().queryForList("LAL.QUERY_USER",form);		
					if(ls.size()>0) {
						data.put("MSG", "此客戶已經申請過授權!");
					} else {
						ls = this.getSqlDBUtility_51().queryForList("LAL.QUERY_MAX_USERNO",form);
						for(Object user_no:ls) {
							String MAX_USER_NO=((HashMap<?, ?>) user_no).get("USER_NO").toString().substring(3);
							form.put("user_no", form.get("user_class").toString()+"0"+String.valueOf((Integer.parseInt(MAX_USER_NO)+1)));
							form.put("user_kdate", ud.nowWDateTime_num().substring(0, 8));
							form.put("user_kname", "陳佩君");
							form.put("user_cstatus", "0");
							form.put("cnt2_tel1", form.get("cnt2_tel1_1").toString()+"-"+form.get("cnt2_tel1_2").toString());
							form.put("cnt2_fax", (form.get("cnt2_fax_1").toString().equals(""))?"":form.get("cnt2_fax_1").toString()+"-"+form.get("cnt2_fax_2").toString());
							form.put("user_tel", form.get("user_tel_1").toString()+"-"+form.get("user_tel_2").toString());
							form.put("user_fax", (form.get("user_fax_1").toString().equals(""))?"":form.get("user_fax_1").toString()+"-"+form.get("user_fax_2").toString());
							form.put("cnt2_name", form.get("user_copman").toString());
							
							this.getSqlDBUtility_51().insert("LAL.INSERT_USER",form);
						}
						List ls1 = this.getSqlDBUtility_LICENSE().queryForList("LICENSE.QUERY_LICENSE_PRICE",form);
						System.out.println(ls1);
						if(ls.size()>0) {
							form.put("rec_dprice", ((HashMap) ls1.get(0)).get("REC_DPRICE").toString());
							form.put("price", ((HashMap) ls1.get(0)).get("PRICE").toString());
						} 
						form.put("cont_bdate", form.get("cont_bdate_yy").toString()+form.get("cont_bdate_mm").toString()+form.get("cont_bdate_dd").toString());
						form.put("cont_edate", form.get("cont_edate_yy").toString()+form.get("cont_edate_mm").toString()+form.get("cont_edate_dd").toString());						
						this.getSqlDBUtility_LICENSE().update("LICENSE.UPDATE_LICENSE_APPLY", form);
						send_applymail(form);
						data.put("MSG", "資料已匯入法務授權系統!");
					}				
				} else {//非新增客戶					
					form.put("cont_bdate", form.get("cont_bdate_yy").toString()+form.get("cont_bdate_mm").toString()+form.get("cont_bdate_dd").toString());
					form.put("cont_edate", form.get("cont_edate_yy").toString()+form.get("cont_edate_mm").toString()+form.get("cont_edate_dd").toString());						
					List ls1 = this.getSqlDBUtility_LICENSE().queryForList("LICENSE.QUERY_LICENSE_PRICE",form);
					if(ls1.size()>0) {
						form.put("rec_dprice", ((HashMap) ls1.get(0)).get("REC_DPRICE").toString());
						form.put("price", ((HashMap) ls1.get(0)).get("PRICE").toString());
					}
					if(((String)check.checkNull(form.get("nature"), "")).equals("2")) {//續約
						List ls = this.getSqlDBUtility_LICENSE().queryForList("LICENSE.CHECK_LICENSE_MAIN",form);
						if(ls.size()>0) {
							data.put("MSG", "此用戶今年已續約過!");		
						} else {
							this.getSqlDBUtility_LICENSE().update("LICENSE.UPDATE_LICENSE_APPLY", form);
							send_applymail(form);
							data.put("MSG", "確認完成!");	
						}
					} else {//新增機台
						this.getSqlDBUtility_LICENSE().update("LICENSE.UPDATE_LICENSE_APPLY", form);
						send_applymail(form);
						data.put("MSG", "確認完成!");	
					}					
				}
			}
			responseBean.setAjaxData(data);
			
		}
	
	public void InsertCaseCont(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		HashMap data = new HashMap<Object, Object>();
		try {					
			/**
			 *變數宣告
			 */
			String today=ud.nowWDateTime_num().substring(0, 8);
			String doc_no[]=form.get("doc_no").toString().split(",");
			String user_no[]=form.get("user_no").toString().split(",");
			String user_cname[]=form.get("user_cname").toString().split(",");
			String user_title[]=form.get("user_title").toString().split(",");
			String cont_bdate[]=form.get("cont_bdate").toString().split(",");
			String cont_edate[]=form.get("cont_edate").toString().split(",");
			String kind[]=form.get("kind").toString().split(",");
			String rec_dprice[]=form.get("rec_dprice").toString().split(",");
			String nature[]=form.get("nature").toString().split(",");
			int mac_seqno;
			form.put("case_notitle", "C"+today.substring(2, 4));
			form.put("cont_notitle", form.get("cont_kind").toString()+today.substring(2, 4));
			form.put("case_kdate", today);
			form.put("status", "3");
			/**
			 *邏輯:
			 *用AD名稱去LAL撈出LOGINNAME
			 *撈出最大的CASE_NO及CONT_NO+1
			 *寫入資料前的必要欄位塞進MAP
			 *資料寫入到CASE,CONTRACT,RECEIVE，行業別是KAR要多寫入MACHINE
			 *參數名稱前面是case代表是個案，cont代表是合約
			 */
			List<?> ls = this.getSqlDBUtility_51().queryForList("LAL.QUERY_LOGIN_USER",form);
			if(ls.size()>0) {
				HashMap hm = (HashMap)ls.get(0);
				form.put("case_sname", hm.get("LOGIN_NAME").toString());	
				form.put("cont_name", hm.get("LOGIN_NAME").toString());
			}
			for(int i=0;i<doc_no.length;i++) {
				System.out.println("doc_no:"+doc_no[i]);
				if(!doc_no[i].trim().equals("")) {
					
					ls = this.getSqlDBUtility_51().queryForList("LAL.QUERY_MAX_CASE",form);
					if(ls.size()>0) {
						HashMap hm = (HashMap)ls.get(0);
						form.put("case_no", hm.get("CASE_NO").toString().substring(0,1)+(Integer.parseInt(hm.get("CASE_NO").toString().substring(1))+1));
					}				
					ls = this.getSqlDBUtility_51().queryForList("LAL.QUERY_MAX_CONT",form);	
					if(ls.size()>0) {
						HashMap hm = (HashMap)ls.get(0);
						form.put("cont_no", hm.get("CONT_NO").toString().substring(0,1)+(Integer.parseInt(hm.get("CONT_NO").toString().substring(1))+1));						
					}
					//共同欄位
					form.put("doc_no", doc_no[i]);
					form.put("user_no", user_no[i]);	
					form.put("year", today.substring(2,4));
					//以下是合約contract欄位
					form.put("cont_cname", user_cname[i]);//客戶名稱
					form.put("cont_rname", user_title[i]);//發票名稱
					form.put("cont_kdate", today);//建檔日期
					form.put("cont_bdate", cont_bdate[i]);
					form.put("cont_edate", cont_edate[i]);
					form.put("cont_sdate", doc_no[i].substring(0, 8));//簽約日期doc_no前八碼
					form.put("cont_cdate", ud.addDate(cont_edate[i], -2, "month"));//到期日期cont_edate前兩個月
					form.put("cont_rtotal", rec_dprice[i]);//總兌現金額	
					form.put("cont_itotal", (new Double(Integer.parseInt(rec_dprice[i])/1.05).intValue()));//總授權金
					form.put("cont_ytotal", (new Double(Integer.parseInt(rec_dprice[i])/1.05).intValue()));//應收總金額
					if(user_no[i].indexOf("KAR")>-1) {
						form.put("cont_rprice", (new Double(Integer.parseInt(rec_dprice[i])/1.05).intValue()));//每期金額
						form.put("cont_rmethod", "年付");//付款方式
						form.put("cont_rstage", "1");//收款期數
						form.put("cont_type", "概括授權");//合約類型
						switch(kind[i]) {
							case "電腦伴唱機音樂著作公開演出申請表":
								form.put("cont_atype", "營利");//活動性質
								break;
							case "電腦伴唱機(公益且無營利)音樂著作公開演出申請表":
								form.put("cont_atype", "非營利-公益");//活動性質
								break;
							case "電腦伴唱機(文化教育)音樂著作公開演出申請表":
								form.put("cont_atype", "營利-公益");//活動性質
								break;			
						}
						//以下是receive欄位

						form.put("rec_seqno", "1");
						form.put("rec_stageno", "1");
						form.put("rec_date", doc_no[i].substring(0, 8));
						form.put("rec_price", (new Double(Integer.parseInt(rec_dprice[i])/1.05)).intValue());
						form.put("rec_dprice",rec_dprice[i]);
						form.put("rec_duty", (Integer.parseInt(rec_dprice[i])-Integer.parseInt(form.get("rec_price").toString())));
					}

					List lsthisyear = this.SqlDBUtility_51.queryForList("LAL.CHECK_CONT_NO", form);//再檢查一次有沒有今年的合約
					if(lsthisyear.size()>0&&form.get("nature").toString().equals("2")) {
						HashMap hm = (HashMap)lsthisyear.get(0);
						form.put("case_no",hm.get("CASE_NO"));
						form.put("cont_no",hm.get("CONT_NO"));
						System.out.println("已經有合約!");
						this.getSqlDBUtility_LICENSE().update("LICENSE.UPDATE_LICENSE_APPLY", form);
					} else {
						
						this.getSqlDBUtility_51().insert("LAL.INSERT_CASE",form);		
						this.getSqlDBUtility_51().insert("LAL.INSERT_CONTRACT",form);
						this.getSqlDBUtility_51().insert("LAL.INSERT_RECEIVE",form);
						if(user_no[i].indexOf("KAR")>-1) {
							mac_seqno=1;
							
							ls=doQueryMachine(form);						
							if(ls.size()>0) {
								for(Object Machine:ls) {
									form.put("mac_no", ((HashMap<?, ?>) Machine).get("MACHINE").toString());
									form.put("mac_name", ((HashMap<?, ?>) Machine).get("FACTORY").toString());
									form.put("mac_seqno", mac_seqno);
									form.put("mac_kdate", today);
									this.getSqlDBUtility_51().insert("LAL.INSERT_MACHINENO",form);
									mac_seqno++;
								}
							}
							
						}
						this.getSqlDBUtility_LICENSE().update("LICENSE.UPDATE_LICENSE_APPLY", form);
					}
				}
			}
			data.put("MSG", "個案、合約建立完成!");
		} catch (Exception e) {
			//丟出例外就要刪掉CASE_NO、CONT_NO、RECEIVE、MACHINENO
			data.put("MSG", "個案、合約建立錯誤、請洽姆斯!");
			System.out.println("Exception="+e);
			/*this.getSqlDBUtility_51().update("LAL.DELETE_CASE",form);		
			this.getSqlDBUtility_51().update("LAL.DELETE_CONTRACT",form);
			this.getSqlDBUtility_51().update("LAL.DELETE_RECEIVE",form);
			this.getSqlDBUtility_51().update("LAL.DELETE_MACHINENO",form);*/
			
		} finally{
			responseBean.setAjaxData(data);	
		}
	}
		
	
	public void doQuery(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{				
		List ls = this.getSqlDBUtility_LICENSE().queryForList("LICENSE.QUERY_LICENSE_MAIN",form);	
		responseBean.setAjaxData(ls);			
	}
	
	public void doQueryDetail(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		List ls = this.getSqlDBUtility_LICENSE().queryForList("LICENSE.QUERY_LICENSE_MAIN_DETAIL",form);	
		responseBean.setAjaxData(ls);			
	}	

	public void doQueryMachine(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		
		List ls = doQueryMachine(form);	
		responseBean.setAjaxData(ls);			
	}

	private List<?> doQueryMachine(HashMap<?, ?> form){
		return this.getSqlDBUtility_LICENSE().queryForList("LICENSE.QUERY_LICENSE_MAIN_MACHINE",form);
	}
	
	private void send_applymail(HashMap form) throws IOException, ServletException, SQLException {
		StringBuffer htmlCode = new StringBuffer() ;
		htmlCode.append("敬啟者 您好,<BR>");
		htmlCode.append("您申請的 "+form.get("user_cname")+" 線上授權流水號:"+form.get("doc_no")+"已通過審核，<BR>");
		if(!((String)check.checkNull(form.get("msg").toString(), "")).equals("")) {
			htmlCode.append("<font color='red'>審核人員備註:<BR>"+form.get("msg").toString()+"</font><BR>");
		}
		htmlCode.append("請於近日內撥冗匯款，以利本會發證，匯款資料如下:<BR><BR>");
		htmlCode.append("<div style='margin-left:5%'>");
		htmlCode.append("<ol>");
		htmlCode.append("<li>本次授權金額為<font color='red'>新台幣"+ut.addComma(form.get("rec_dprice").toString())+"元(含稅)</font>。<BR>");
		htmlCode.append("<li>授權期間為<font color='red'>"+form.get("cont_bdate_yy").toString()+"/"+form.get("cont_bdate_mm").toString()+"/"+form.get("cont_bdate_dd").toString()+" 至 "+form.get("cont_edate_yy").toString()+"/"+form.get("cont_edate_mm").toString()+"/"+form.get("cont_edate_dd").toString()+"</font>。<BR>");
		htmlCode.append("<li>繳費方式(擇一)：(※請勿自行內扣手續費)</li>");
		htmlCode.append("<ol type='a'>");
		htmlCode.append("<li>郵局劃撥---劃撥帳號：19321052，戶名：社團法人中華音樂著作權協會</li>");
		htmlCode.append("<li>銀行匯款---匯款銀行：台新國際商業銀行(銀行代號：812) 南京東路分行(分行代碼：0115)<br> 帳號：2011-01-00021680帳戶名：社團法人中華音樂著作權協會</li>");
		htmlCode.append("<li>開立7天內之即期支票 （檯頭：社團法人中華音樂著作權協會）。</li>");
		htmlCode.append("</ol>");
		htmlCode.append("<li>備妥上述劃撥單收據影本、匯款收據影本或即期支票傳真或掛號寄至本協會，以利本會後續結案發證作業。</li>");					
		htmlCode.append("</ol>");
		htmlCode.append("</div>");					
		htmlCode.append("有任何問題請與窗口【 陳佩君小姐, 電話: 02-25110869 分機260】連繫。");
		new SEND_EMAIL("must.license@must.org.tw","MUST授權部",form.get("cnt1_email").toString(),"MUST 線上授權申請",htmlCode);
		new SEND_EMAIL("must.license@must.org.tw","MUST授權部","Pallas.chen@must.org.tw,james.huang@must.org.tw","MUST 線上授權申請",htmlCode);
		
	}
}
