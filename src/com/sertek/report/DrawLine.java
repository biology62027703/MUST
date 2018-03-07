package com.sertek.report;

/**
 * Title:        Report Builder
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Sertek Inc.
 * @author Shima Liao
 * @version 2.0
 */

public class DrawLine {
	private int Pos[]=null;
	private int PrePos[]=null;
	private boolean DiffPos;

//------------------------------------------------------------------------------

	public DrawLine() {
		DiffPos=false;
	}

//------------------------------------------------------------------------------

	public DrawLine(int Pos[]) {
		this.Pos=Pos;
		DiffPos=false;
	}

//------------------------------------------------------------------------------

	public void setEnd() {
		DiffPos=false;
		// Pos=null;
	}

//------------------------------------------------------------------------------

	public void setPos(int Pos[]) {
		if(this.Pos != null) {
			PrePos=this.Pos;
			DiffPos=true;
		}
		this.Pos=Pos;
	}

//------------------------------------------------------------------------------

	public String DrawIt(String LeftChar,String MiddleChar,String RightChar) {
		if (!DiffPos)
			return DrawSamePos(LeftChar,MiddleChar,RightChar);
		else
			return DrawDiffPos(LeftChar,RightChar);
	}

//------------------------------------------------------------------------------

	public String DrawSamePos(String LeftChar,String MiddleChar,String RightChar) {
		StringBuffer sTmp=new StringBuffer();
		int iSize=Pos.length;
		int iStart=Pos[0];
		int iEnd;
		sTmp.append(fillChar(" ",Pos[0]-1));
		sTmp.append(LeftChar);
		for(int i=1;i<iSize-1;i++) {
			iEnd=Pos[i];
			sTmp.append(fillChar("¢w",(iEnd-iStart)/2-1));
			sTmp.append(MiddleChar);
			iStart=iEnd;
		}
		sTmp.append(fillChar("¢w",(Pos[iSize-1]-iStart)/2-1));
		sTmp.append(RightChar);
		DiffPos=false;
		return sTmp.toString();
	}

//------------------------------------------------------------------------------

	private String DrawDiffPos(String LeftChar,String RightChar) {
		StringBuffer sTmp=new StringBuffer();
		String MiddleStr;
		int iSize1=Pos.length;
		int iSize2=PrePos.length;
		int iStart=Pos[0];
		int iEnd,i=1,j=1;
		sTmp.append(fillChar(" ",Pos[0]-1));
		sTmp.append(LeftChar);
		while (i < iSize1 && j < iSize2) {
			if(Pos[i] > PrePos[j]) {
				iEnd=PrePos[j];
				j++;  MiddleStr="¢r";
			}else if(Pos[i] < PrePos[j]) {
				iEnd=Pos[i];
				i++;  MiddleStr="¢s";
			}else {
				iEnd=Pos[i];
				i++; j++; MiddleStr="¢q";
			}
			sTmp.append(fillChar("¢w",(iEnd-iStart)/2-1));
			sTmp.append(MiddleStr);
			iStart=iEnd;
		}
		sTmp.delete(sTmp.length()-1,sTmp.length());
		sTmp.append(RightChar);
		DiffPos=false;
		return sTmp.toString();
	}

//------------------------------------------------------------------------------

	private String fillChar(String ch,int cnt)  {
		StringBuffer sTmp=new StringBuffer();
		for(int i=1;i<=cnt;i++)
			sTmp.append(ch);
		return sTmp.toString();
	}

//------------------------------------------------------------------------------

}