package com.sertek.report;

/**
 * Title:        Report Builder
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Sertek Inc.
 * @author Shima Liao
 * @version 1.3
 */
import java.util.*;

public class Page {
	public Vector Title,Head,Foot,PageEnd;
	private String LineSeparator;

//------------------------------------------------------------------------------

	public Page() {
		LineSeparator=System.getProperty("line.separator")==null ? "\n" : System.getProperty("line.separator");
		Title=new Vector();
		Head=new Vector();
		Foot=new Vector();
		PageEnd=new Vector();
	}

//------------------------------------------------------------------------------

	public String PrintHead() {
		return PrintFixLine(Head);
	}

//------------------------------------------------------------------------------

	public String PrintTitle() {
		return PrintFixLine(Title);
	}

//------------------------------------------------------------------------------

	public String PrintFoot() {
		return PrintFixLine(Foot);
	}

//------------------------------------------------------------------------------

	public String PrintPageEnd() {
		return PrintFixLine(PageEnd);
	}

//------------------------------------------------------------------------------

	private String PrintFixLine(Vector tmpVector){
		StringBuffer sTmp=new StringBuffer();
		int iSize=tmpVector.size();
		for(int i=0;i<iSize;i++)
			sTmp.append(tmpVector.elementAt(i).toString()+LineSeparator);
		return sTmp.toString();
	}

//------------------------------------------------------------------------------

}