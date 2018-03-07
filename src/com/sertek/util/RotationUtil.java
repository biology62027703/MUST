package com.sertek.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
-----------------------------------------------------------------------------------------------------
���D�渹�GBug #4744 - JBD1A01-�԰ȱƯZ
�ק�K�n�GA.�t�X�_����~��,�԰ȶ��ؼW�[�W/�U���ݩʡC
		B.��԰����O�B���اR�����[�j���B�z�C
��s�����G�}�o���q
�ק�H���Geason
�ק����G0990223
-----------------------------------------------------------------------------------------------------
*/
public class RotationUtil {
	
	private CheckObject check = new CheckObject();

	private List cpList = new ArrayList();
	
	private HashMap map = new HashMap();
	
	private int index = 0;
	
	private int initIndex = 0;
	
	private String cpid = "";
	
	public RotationUtil(List cpList, String useidSort) {
		this.cpList = cpList;
		this.index = cpList.size() - 1;
		if (!"".equals(useidSort) && cpList != null) {
			for (int i = 0; i < cpList.size(); i++) {
				map = (HashMap) cpList.get(i);
				String sort = check.checkNull(map.get("SORT"), "").toString();
				//System.out.println("i = " + i + ", sort = " + sort + ", useidSort = " + useidSort);
				if (sort.equals(useidSort)) {
					index = i;
					break;
				} else if (Integer.parseInt(sort) > Integer.parseInt(useidSort)) {
					index = i;
					if (index > 0) {
						index = index - 1;
					} else {
						index = cpList.size() - 1;
					}
					break;
				}
			}
		}
		initIndex = index;
		//System.out.println("initIndex = " + initIndex);
	}
	
	public void setUseid(int interval) {
		initIndex = initIndex + interval;
		while (initIndex < 0) {
			initIndex = initIndex + cpList.size();
		}
		while (initIndex >= cpList.size()) {
			initIndex = initIndex - cpList.size();
		}
		index = initIndex;
		
		if (cpList != null && cpList.size() > index) {
			map = (HashMap) cpList.get(index);
			cpid = check.checkNull(map.get("CPID"), "").toString();
		}
	}
	
	public void next() {
		index++;
		
		if (index >= cpList.size()) {
			index = 0;
		}
		
		if (cpList != null && cpList.size() > index) {
			map = (HashMap) cpList.get(index);
			cpid = check.checkNull(map.get("CPID"), "").toString();
		}
	}

	public String getCpid() {
		return this.cpid;
	}
}
