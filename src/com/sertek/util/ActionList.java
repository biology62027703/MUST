package com.sertek.util;


public class ActionList{
	/**
		*�������ѵ{���H���ۦ�wĳ�����ʧ@��������M�W�١C���W�٬��G���}�C�ȡC<BR>
		*ex:<br>
		*	action={{"insert","�s�W"},{"update","�ק�"}};
		*�����b�U�ӵ{�����A�����s�W�έק�ɪ��W�����
		@author		Ellin Chen
		@Date		2001.06.22
		
	*/
	public ActionList(String[][] action) {
		this.action = action;
	}
	
	/**
		*String[][] action = {{"insert","�s�W"},{"update","�ק�"}};
		*�����w�]action��
	*/
	public ActionList() {
	
	}
	
	/**
		*���o����action����������W��
		@param		key	�brequest�������^��ȡA�pinsert�Aupdate
		@return		���媺�ʧ@�W��
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
	
	String[][] action = {{"insert","�s�W"},
			     {"update","�ק�"}};
}