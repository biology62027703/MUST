package com.sertek.service.server;
 

public interface PayService {
		
	//½u¤W°_¶D---------------------------------------------------------------
	public String payment(String p_payid, String p_status, String comdt, String comtm);
	
	public String payment(String p_payid, String p_status, String comdt, String comtm, String trntype);
	 
}

