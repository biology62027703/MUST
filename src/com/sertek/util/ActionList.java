package com.sertek.util;


public class ActionList{
	/**
		*此部份由程式人員自行定議有關動作的相關對映名稱。此名稱為二維陣列值。<BR>
		*ex:<br>
		*	action={{"insert","新增"},{"update","修改"}};
		*有關在各個程式中，有關新增或修改時的名稱顯示
		@author		Ellin Chen
		@Date		2001.06.22
		
	*/
	public ActionList(String[][] action) {
		this.action = action;
	}
	
	/**
		*String[][] action = {{"insert","新增"},{"update","修改"}};
		*此有預設action值
	*/
	public ActionList() {
	
	}
	
	/**
		*取得有關action的相關中文名稱
		@param		key	在request部份的英文值，如insert，update
		@return		中文的動作名稱
	*/
	public String getActionName(String key) {
		String result = "";
		System.out.println("key = " + key);
		for(int i=0;i<action.length;i++) {
			if(key.equals(action[i][0])) {
				result = action[i][1];
			}
		}
		System.out.println("result = " + result);
		return result;
	}
	
	String[][] action = {{"insert","新增"},
			     {"update","修改"}};
}