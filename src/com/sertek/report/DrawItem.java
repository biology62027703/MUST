package com.sertek.report;

/**
 * Title:        Report Builder
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Sertek Inc.
 * @author Shima Liao
 * @version 2.0
 */
import com.sertek.report.*;
import java.io.*;

public class DrawItem {
	private int Position;
	private String Item;
	private int TagSize;
	private String SeparateStr="¢x";
	private boolean HideLine=false;
	public boolean SpecialStr;

//------------------------------------------------------------------------------

	public DrawItem() {
		SpecialStr=false;
	}

//------------------------------------------------------------------------------

	public void DrawSetProperty(String Item,int Position,int TagSize) {
		this.Position=Position;
		this.Item=Item.trim();
		this.TagSize=TagSize;
	}

//------------------------------------------------------------------------------

	public String DrawStr() {
		if (Item.equals("¢u¢s") || Item.equals("¢u¢t") || Item.equals("¢s¢t") || Item.equals("¢s¢s") ||
			Item.equals("¢u¢r") || Item.equals("¢r¢r") || Item.equals("¢r¢t") ||
			Item.equals("¢q¢r") || Item.equals("¢q¢s") || Item.equals("¢q¢t") ||
			Item.equals("¢u¢q") || Item.equals("¢r¢q") || Item.equals("¢s¢q")) {
			SpecialStr=true;
			return (DrawSpecialStr(Item,0,TagSize+1));
		}else{
			SpecialStr=false;
			switch (Position) {
				case 1:
					return DrawStrMiddle();
				case 2:
					return DrawStrRight();
				default:
					return DrawStrLeft();
			}
		}
	}

//------------------------------------------------------------------------------

	private String DrawStrLeft() {
		StringBuffer sTmpStr=new StringBuffer();
		try {
			int SpaceCount=TagSize-Item.getBytes("MS950").length-1;
			sTmpStr.append(Item);
			sTmpStr.append(fillSpace(SpaceCount));
			sTmpStr.append(SeparateStr);
		}catch(UnsupportedEncodingException e) {}
		return sTmpStr.toString();
	}

//------------------------------------------------------------------------------

	private String DrawStrRight() {
		StringBuffer sTmpStr=new StringBuffer();
		try {
			int SpaceCount=TagSize-Item.getBytes("MS950").length-1;
			sTmpStr.append(fillSpace(SpaceCount));
			sTmpStr.append(Item);
			sTmpStr.append(SeparateStr);
		}catch(UnsupportedEncodingException e) {}
		return sTmpStr.toString();
	}

//------------------------------------------------------------------------------

	private String DrawStrMiddle() {
		StringBuffer sTmpStr=new StringBuffer();
		try {
			int StrSize=Item.getBytes("MS950").length;
			int SpaceCount=TagSize-StrSize-1;
			int move=SpaceCount/2;
			int mods=SpaceCount%2;
			sTmpStr.append(fillSpace(move+mods));
			sTmpStr.append(Item);
			sTmpStr.append(fillSpace(move));
			sTmpStr.append(SeparateStr);
		} catch(UnsupportedEncodingException e) {}
		return sTmpStr.toString();
	}

//------------------------------------------------------------------------------

	private String fillSpace(int cnt)  {
		StringBuffer sTmp=new StringBuffer();
		for(int i=1;i<=cnt;i++)
			sTmp.append(" ");
		return sTmp.toString();
	}

//------------------------------------------------------------------------------

	private String DrawSpecialStr(String FrameStr,int start,int end)  {
		int Pos[]={start,end};
		DrawLine TmpDrawLine=new DrawLine(Pos);
		if(!HideLine)
			return TmpDrawLine.DrawIt(FrameStr.substring(0,1),"¢w",FrameStr.substring(1,2));
		else
			return fillSpace(end-start+1);
	}

//------------------------------------------------------------------------------

	public void SetShowLine(boolean flag){
		if(flag){
			SeparateStr="¢x";
			HideLine=false;
		}else {
			SeparateStr="  ";
			HideLine=true;
		}
	}

//------------------------------------------------------------------------------

}