package com.sertek.sys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.table.COURT;
import com.sertek.table.SUITKD;
import com.sertek.util.CheckObject;

public class sys_Helper implements Serializable {
	
	private SqlDBUtility sqlDBUtility;
	
	private CheckObject check = new CheckObject();
	
	private HashMap courtMap = new HashMap();
	
	private HashMap suitkdMap = new HashMap();
	
	private List courtList = new ArrayList();
	
	private List suitkdList = new ArrayList();

	public sys_Helper(HttpServletRequest request, SqlDBUtility sqlDBUtility) throws Exception {
		this.sqlDBUtility = sqlDBUtility;

		setCourtMap();
		setSuitkdMap();
	}
	
	private void setCourtMap() {
		HashMap keyMap = new HashMap();
		keyMap.put("orderByKey", "crtseq");
		courtList = sqlDBUtility.queryForList("COURT.queryByKey", keyMap);
		for (int i = 0; i < courtList.size(); i++) {
			COURT court = (COURT) courtList.get(i);
			courtMap.put(court.getCrtid(), court.getCrtnm());
		}
	}
	
	private void setSuitkdMap() {
		HashMap keyMap = new HashMap();
		suitkdList = sqlDBUtility.queryForList("SUITKD.queryByKey", keyMap);
		for (int i = 0; i < suitkdList.size(); i++) {
			SUITKD suitkd = (SUITKD) suitkdList.get(i);
			if ("S".equals(suitkd.getS_kd())) {
				suitkdMap.put("S", "°_¶Dª¬");
			} else if ("C".equals(suitkd.getS_kd())) {
				suitkdMap.put("C", "Án½Ðª¬");
			} else {
				suitkdMap.put(suitkd.getS_kd(), suitkd.getSuit_nm());
			}
		}
	}
	
	public List getCourtList() {
		return courtList;
	}
	
	public String getCrtnm(String crtid) {
		String crtnm = "";
		String key = crtid;

		crtnm = check.checkNull(courtMap.get(key), "").toString();
		return crtnm;
	}

	public String getSuitnm(String s_kd) {
		String suitnm = "";
		String key = s_kd;

		suitnm = check.checkNull(suitkdMap.get(key), "").toString();
		return suitnm;
	}
}