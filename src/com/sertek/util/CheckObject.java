package com.sertek.util;


public class CheckObject {
	/**
		*有關物件部份的null檢核，若屬null的狀況，則傳回defaultvalue的值
		@author 	Ellin Chen
		@Date		2001.06.22
	*/
	public CheckObject() {
	
	}

	
	
	/**
		*有關Object部份的null檢核，若屬null的狀況，則傳回defaultvalue的值
		@param		o	原始的Object
		@param		defaultvalue	預設的Object
		@return		Object
	*/
	public Object checkNull(Object o,Object defaultvalue) {
		if(o == null || ((String)o).length() == 0) 
			return defaultvalue;
		else 
			return o;
	}
	/**
		*有關Object部份的null檢核，若屬null的狀況，則傳回defaultvalue的值，主要是用在資料庫部份，若該欄<BR>
		*位屬空值時，補指定l長度的defaultvalue(半形空白)給資料庫，以利更新或新增資料之用。 
		@param		o	原始的Object
		@param		defaultvalue	預設的Object
		@param		defaultvalue要連續幾個組成一個值回傳
		@return		Object
	*/
	public String checkNull(String o,String defaultvalue,int l) {
		String result = "";
		//o = o.trim();
		if(o == null || o.length() == 0) 
			//return defaultvalue;
			for(int i=0;i<l;i++) {
				result += defaultvalue;	
			}
		else 
			result = o;
		return result;
	}
	
	public String checkNull(String s, String s1, boolean flag)
    {
        if(s != null && flag)
            s = s.trim();
        if(s == null || s.length() == 0)
            return s1;
        else
            return s;
    }
	/**
		*將字串中的值，前補0的字串刪除後顯示純數字部份字串<BR>
		*主要將00070改為70回傳
		@param		o	原始字串
		@param		defaultvalue	若該字串為非數值型態或是空字串時，預設的值
		@return		回傳預設值或純數字部份字串
	*/
	public String checkInt(String o,String defaultvalue) {
		String result = "";
		result = (String)checkNull(o,defaultvalue);
		try {
			result = "" + Integer.parseInt(result);
		} catch(NumberFormatException e) {
			result = defaultvalue;
		}
		return result;
	}
	
	/**
		*將字串中的值，前補0的字串刪除後顯示純數字部份字串<BR>
		*主要將00070改為70回傳
		@param		o	原始字串
		@param		defaultvalue	若該字串為非數值型態或是空字串時，預設的值
		@return		回傳預設值或純數字部份字串
	*/
	public String checkInt(Object o,Object defaultvalue) {
		String result = "";
		result = (String)checkNull(o,defaultvalue);
		try {
			result = "" + Integer.parseInt(result);
		} catch(NumberFormatException e) {
			result = (String)defaultvalue;
		}
		return result;
	}
	
	public int checkLength(String s)
    {
        int i = 0;
        i = s.trim().length();
        return i;
    }

    public boolean checkEquals(String s, String s1, int i)
    {
        boolean flag = true;
        flag = i != 0 ? s.equalsIgnoreCase(s1) : s.equals(s1);
        return flag;
    }
    
}