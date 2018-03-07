package com.sertek.report;

/**
 * Title:        Report Builder
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Sertek Inc.
 * @author Shima Liao
 * @version 2.6
 */

import java.util.*;
import java.io.*;

public class ReportBuilder {
	private DrawLine RptDrawLine=new DrawLine();
	private DrawItem RptDrawItem=new DrawItem();
	private int Pos[],prePos[];
	private int Mov[];
	private String SeparateStr="│";
	private Page PageSet=new Page();
	private boolean HideLine=false,PreisLine,isLine=false,isBegin=false;
	private boolean WrNewPageEnd=true,overwrite=false,RptInit=false,WriteFile=false,SpecialLine=false;
	private boolean MultiLine=false,WritePageNo=false,PageMode=false;
	private String LineSeparator,PageFile;
    private String PreTmpStr;//Henry add!!
    //private boolean PreLine;	//Henry marked
	private int LineCount=0,PageCount=1,MaxLine=0,PagePosition=0,HaveLine=1;
	private Vector OutputVector=new Vector();
	private StringBuffer global_sb = new StringBuffer();
	

//------------------------------------------------------------------------------

	public ReportBuilder() {

        PreTmpStr = null;
		LineSeparator=System.getProperty("line.separator")==null ? "\n" : System.getProperty("line.separator");
		PagePosition=1;
		this.MaxLine=2147483647; // INT 最大值
		this.LineCount=0;

	}

//------------------------------------------------------------------------------

	public void RPTBegin(int Pos[]) throws IOException {
		if(!RptInit){
			this.Pos=Pos;
			RptDrawLine.setPos(null);
			RptDrawLine.setPos(Pos);
			RptDrawLine.setEnd();
			StringBuffer sTmp=new StringBuffer();
			isBegin=true;
			if(!this.HideLine){ sTmp.append(RptDrawLine.DrawIt("┌","┬","┐"));
				if (LineCount+1 >= MaxLine-HaveLine){
					this.OutputRPTBuffer(this.LineSeparator);
					this.PrintPageNo();
					this.LineCount=0;
				}
				this.OutputRPTBuffer(sTmp.toString()+LineSeparator);
				LineCount=LineCount+1;
			}
			this.prePos=(int [])this.Pos.clone();
			RptInit=true;
		}else{
			throw new IOException("ReportBuilder already begin the report !!");
		}
	}

//------------------------------------------------------------------------------

	public void RPTEnd() throws IOException  {
		if(RptInit) {
			StringBuffer sTmp=new StringBuffer();
			RptDrawLine.setEnd();
			if(!this.HideLine) {
				//Henry Marked
				/*
				sTmp.append(RptDrawLine.DrawIt("└","┴","┘"));
				this.OutputRPTBuffer(sTmp.toString()+LineSeparator);
				LineCount=LineCount+1;
				*/
				//Henry add!!
                if(isLine){
                    OutputVector.removeElementAt(OutputVector.size() - 1);
                    LineCount = LineCount - 1;
                }
                if(PreTmpStr == null){
                    sTmp.append(RptDrawLine.DrawIt("└","┴","┘"));
                } else {
                    sTmp.append(PreTmpStr);
                    PreTmpStr = null;
                }
                if(LineCount > 0){
                    OutputRPTBuffer(String.valueOf(sTmp.toString()) + String.valueOf(LineSeparator));
                    LineCount = LineCount + 1;
                }
			}
			RptInit=false;
		}else {
			throw new IOException("ReportBuilder never begin the report !!");
		}
	}

//------------------------------------------------------------------------------

	public void RPTLine() throws IOException {
		if(RptInit) {
			RptDrawLine.setPos(Pos);
			StringBuffer sTmp=new StringBuffer();
			if(!this.HideLine) {
				sTmp.append(RptDrawLine.DrawIt("├","┼","┤"));
				PreisLine=isLine;
				isLine=true;
				PrintPageLine(sTmp.toString()+LineSeparator);
				isBegin=false;
			}
			this.prePos=(int [])this.Pos.clone();
		}else{
			throw new IOException("ReportBuilder must begin the report !!");
		}
	}

//------------------------------------------------------------------------------

	public void RPTLine(int Pos[]) throws IOException {
		if(RptInit) {
			this.prePos=(int [])this.Pos.clone();
			this.Pos=Pos;
			RPTLine();
		}else{
			throw new IOException("ReportBuilder must begin the report !!");
		}
	}

//------------------------------------------------------------------------------

	public void RPTContents(int xPos[],Vector contents) throws IOException {
		if(xPos.length != (contents.size()+1))
			throw new IOException("Data and Position aren't match !!");
		if(RptInit) {
			this.prePos=(int [])this.Pos.clone();
			RptDrawLine.setPos(xPos);
			this.Pos=xPos;
			RPTContents(contents);
		}else{
			throw new IOException("ReportBuilder doesn't begin the report !!");
		}
	}

//------------------------------------------------------------------------------

	public void RPTContents(int xPos[],Vector contents,int xMov[]) throws IOException  {
		if(xPos.length != (contents.size()+1))
			throw new IOException("Data and Position aren't match !!");
		if(RptInit) {
			this.prePos=(int [])this.Pos.clone();
			RptDrawLine.setPos(xPos);
			this.Pos=xPos;
			this.Mov=xMov;
			RPTContents(contents);
		}else{
			throw new IOException("ReportBuilder must begin the report !!");
		}
	}

//------------------------------------------------------------------------------

	public void RPTContents(Vector contents,int xMov[]) throws IOException  {
		if(Pos.length != (contents.size()+1))
			throw new IOException("Data and Position aren't match !!");
		if(RptInit) {
			this.prePos=(int [])this.Pos.clone();
			this.Mov=xMov;
			RPTContents(contents);
		}
		else {
			throw new IOException("ReportBuilder doesn't begin the report !!");
		}
	}

//------------------------------------------------------------------------------

	public void RPTContents(Vector contents) throws IOException  {
		if(Pos.length != (contents.size()+1))
			throw new IOException("Data and Position aren't match !!");
		if(RptInit) {
			//System.out.println("contents.size():->" + contents.size() + "<-");
			
			String Str=CheckContents(contents);
			Str=(MultiLine) ? FindLineSeparator(Str): Str;
			PreisLine=isLine;
			isLine=false;
			PrintPageLine(Str+LineSeparator);
			isBegin=false;
			this.prePos=(int [])this.Pos.clone();
		}else{
			throw new IOException("ReportBuilder must begin the report !!");
		}
	}

//------------------------------------------------------------------------------
//notrim true 不將字串空白trim掉，汪汪新造的
    public void RPTContentsNoTrim(int xPos[], Vector contents, int xMov[]) throws IOException{
        if(xPos.length != contents.size() + 1)
            throw new IOException("Data and Position aren't match !!");
        if(RptInit){
            prePos = (int[])Pos.clone();
            RptDrawLine.setPos(xPos);
            Pos = xPos;
            Mov = xMov;
            RPTContentsNoTrim(contents);
        } else{
            throw new IOException("ReportBuilder must begin the report !!");
        }
    }

//------------------------------------------------------------------------------
//notrim true 不將字串空白trim掉，汪汪新造的
    public void RPTContentsNoTrim(Vector contents) throws IOException{
        if(Pos.length != contents.size() + 1)
            throw new IOException("Data and Position aren't match !!");
        if(RptInit){
            String Str = CheckContents(contents,true);
            Str = MultiLine ? FindLineSeparator(Str) : Str;
            PreisLine = isLine;
            isLine = false;
            PrintPageLine(String.valueOf(Str) + String.valueOf(LineSeparator));
            isBegin = false;
            prePos = (int[])Pos.clone();
        }else{
            throw new IOException("ReportBuilder must begin the report !!");
        }
    }

//------------------------------------------------------------------------------
//汪汪新造的
    private String CheckContents(Vector contents,boolean notrim){
        StringBuffer sTmp = new StringBuffer();
        int iSize = contents.size();
        Vector tmpVector1 = new Vector();
        Vector tmpVector2 = new Vector();
        boolean CutLine = false;
        for(int i = 0; i < iSize; i++){
            String TmpStr = "";
            String SourceStr = contents.elementAt(i).toString();
            if(SourceStr.indexOf(10) != 0 && !notrim)
                SourceStr = SourceStr.trim();
            SourceStr = DoReplacePageNo(SourceStr);
            int length = Pos[i + 1] - (Pos[i] + 1) - 1;
            tmpVector1.addElement(SourceStr);
            try{
                if(SourceStr.getBytes("MS950").length > length){
					if(!((SourceStr.length()==2) //add by HenryHou 當手動畫線在此被誤判為要切行
						&& "┌┬┐├┼┤└┴┘│─".indexOf(SourceStr.substring(0,1))>=0 
						&& "┌┬┐├┼┤└┴┘│─".indexOf(SourceStr.substring(1,2))>=0))
					{		
	                    CutLine = true;
	                    TmpStr = CutString(SourceStr, length);
	                    tmpVector1.setElementAt(TmpStr, i);
	                    TmpStr = SourceStr.substring(TmpStr.length());
					}
                }
            }catch(UnsupportedEncodingException unsupportedencodingexception) { }
            tmpVector2.addElement(TmpStr);
        }

        if(CutLine)
            sTmp.append(String.valueOf(LineSeparator) + String.valueOf(CheckContents(tmpVector2,false)));
        MultiLine = CutLine;
        return String.valueOf(DrawContents(tmpVector1)) + String.valueOf(sTmp.toString());
    }

//------------------------------------------------------------------------------

	private String CheckContents(Vector contents)  {
		StringBuffer sTmp=new StringBuffer();
		int length,iSize=contents.size();
		String SourceStr,TmpStr;
		Vector tmpVector1=new Vector();
		Vector tmpVector2=new Vector();
		boolean CutLine=false;
		for(int i=0;i<iSize;i++) {
			TmpStr="";
			SourceStr=contents.elementAt(i).toString();
			if(SourceStr.indexOf('\n') != 0) //第一個字是 \n
				SourceStr=SourceStr.trim();
			SourceStr=this.DoReplacePageNo(SourceStr);
			length=Pos[i+1]-(Pos[i]+1)-1;
			tmpVector1.addElement(SourceStr);
			try {
				if(SourceStr.getBytes("MS950").length > length) {
					if(!((SourceStr.length()==2) //add by HenryHou 當手動畫線在此被誤判為要切行
						&& "┌┬┐├┼┤└┴┘│─".indexOf(SourceStr.substring(0,1))>=0 
						&& "┌┬┐├┼┤└┴┘│─".indexOf(SourceStr.substring(1,2))>=0))
					{		
						CutLine=true;
						TmpStr=CutString(SourceStr,length);
						tmpVector1.setElementAt(TmpStr,i);
						TmpStr=SourceStr.substring(TmpStr.length());
					}
				}
			}catch(UnsupportedEncodingException e) {}
			tmpVector2.addElement(TmpStr);
		}
		if(CutLine){
			sTmp.append(LineSeparator+CheckContents(tmpVector2));
		}
		MultiLine=CutLine;
		return DrawContents(tmpVector1)+sTmp.toString();
	}

//------------------------------------------------------------------------------

	private String CutString(String SourceStr,int length){
		int wordcnt=0;
		StringBuffer sTmp=new StringBuffer();
		StringBuffer SpecialTmp=new StringBuffer();
		String TmpStr=SourceStr;
		boolean NotBreakLine=true;
		if(SourceStr.length() <= 1)
			return TmpStr;
		try{
			do{
				TmpStr=SourceStr.substring(0,1);
				SourceStr=SourceStr.substring(1);
				wordcnt=wordcnt+TmpStr.getBytes("MS950").length;
				if(TmpStr.equals("\n")) {
					// SpecialTmp.append((wordcnt == 1) ? ' ' : '#'); // 補空白 讓字串回傳後計算長度不出錯
					NotBreakLine=false;
				}
				if(TmpStr.equals("\r")) {
					SpecialTmp.append('\r');   //  補空白 讓字串回傳後計算長度不出錯
					TmpStr="";
					wordcnt--;
				}
				if(wordcnt <= length) {
					sTmp.append(TmpStr);
				}
			}while (NotBreakLine && wordcnt <= length);
		} catch(UnsupportedEncodingException e) {}
		SpecialTmp.append(sTmp.toString()); // for 回切SourceStr 用DrawItem會Trim Space
		return SpecialTmp.toString();
	}

//------------------------------------------------------------------------------

	private String DrawContents(Vector contents)  {
		int iSize=contents.size();
		int iEnd,iStart=Pos[0]+1;
		String TargetString;
		StringBuffer sTmp=new StringBuffer();
		String ContentStr;
		ChaeckPosition(iSize);
		sTmp.append(fillSpace(Pos[0]-1));
		sTmp.append(SeparateStr);
		for(int i=1;i<=iSize;i++){
			TargetString=contents.elementAt(i-1).toString();
			iEnd=Pos[i];
			RptDrawItem.DrawSetProperty(TargetString,Mov[i-1],(iEnd-iStart));
			ContentStr=RptDrawItem.DrawStr();
			if(RptDrawItem.SpecialStr){
				sTmp.delete(sTmp.length()-1,sTmp.length());
				this.SpecialLine=true;
			}
			sTmp.append(ContentStr);
			iStart=iEnd+1;
		}
		return sTmp.toString();
	}

//------------------------------------------------------------------------------

	private void ChaeckPosition(int size) {
		if (Mov == null || Mov.length != size) {
			Mov=(int [])this.Pos.clone();
			for(int i=0;i<size;i++)
				Mov[i]=0;
		}
	}

//------------------------------------------------------------------------------

	private String fillSpace(int cnt){
		StringBuffer sTmp=new StringBuffer();
		for(int i=1;i<=cnt;i++)
			sTmp.append(" ");
		return sTmp.toString();
	}

//------------------------------------------------------------------------------

	public void Write(String file, boolean action){
		BufferedWriter writer;
		int iSize = OutputVector.size();
		
		try {
			if(!file.equals("")){
				writer = new BufferedWriter(
						new OutputStreamWriter(
						new FileOutputStream(file,action),"MS950"));
				for(int i=0;i<iSize;i++){
					writer.write(OutputVector.elementAt(i).toString());
					global_sb.append(OutputVector.elementAt(i).toString());
				}
			
				writer.close();
			}else{
				for(int i=0;i<iSize;i++){
					global_sb.append(OutputVector.elementAt(i).toString());
				}
			}
		}catch(IOException e) { }
		
		OutputVector.clear();
	}

//------------------------------------------------------------------------------

	public void Write(String file, boolean action, boolean clear){
		BufferedWriter writer;
		int iSize = OutputVector.size();
		
		try {
			if(!file.equals("")){
				writer = new BufferedWriter(
						new OutputStreamWriter(
						new FileOutputStream(file, action),"MS950"));
				
				for(int i=0;i<iSize;i++){
					writer.write(OutputVector.elementAt(i).toString());
					global_sb.append(OutputVector.elementAt(i).toString());
				}
				
				writer.close();
			}else{
				for(int i=0;i<iSize;i++){
					global_sb.append(OutputVector.elementAt(i).toString());
				}
			}
		}catch(IOException e) { }
		
		if(clear) OutputVector.clear();
	}


//------------------------------------------------------------------------------

	public void RPTString(String Str){
		try {
			RPTString(Str,1);
		} catch (IOException e) { }
	}

//------------------------------------------------------------------------------

	public void RPTString(int pos,String Str){
		try{
			RPTString(fillSpace(pos)+Str,1);
		}catch(IOException e){}
	}

//------------------------------------------------------------------------------

	public void RPTString(String Str,int cnt) throws IOException{
		int modLine=MaxLine-(LineCount+cnt);
		if (cnt > this.MaxLine-HaveLine){
			throw new IOException("The Page lines must less than "+Integer.toString(this.MaxLine-HaveLine)+" lines !!");
		}else{
			Str=this.DoReplacePageNo(Str);
			PreisLine=isLine;
			isLine=false;
			if (modLine <= 0) {
				this.PrintPageNo();
				this.OutputRPTBuffer(Str+this.LineSeparator);
				this.LineCount=cnt;
			}else if(modLine == 1 ){
				this.OutputRPTBuffer(Str+this.LineSeparator);
				this.PrintPageNo();
				this.LineCount=0;
			}else if(modLine == 2 ){
				this.OutputRPTBuffer(Str+this.LineSeparator);
				this.OutputRPTBuffer(this.LineSeparator);
				this.PrintPageNo();
				this.LineCount=0;
			}else{
				this.OutputRPTBuffer(Str+this.LineSeparator);
				this.LineCount=LineCount+cnt;
			}
			isBegin=false;
		}
	}

//------------------------------------------------------------------------------

	public void RPTString(int pos,String Str,int cnt){
		try {
			RPTString(fillSpace(pos)+Str,cnt);
		} catch (IOException e) { }
	}

//------------------------------------------------------------------------------

	public void RPTDump(){
		int iSize = OutputVector.size();
		
		for(int i=0;i<iSize;i++){
			System.out.print(OutputVector.elementAt(i).toString());
		}
	}

//	------------------------------------------------------------------------------

	public String getRPTDump(){
		return global_sb.toString();
	}

//	------------------------------------------------------------------------------

	public void clrRPTDump(){
		global_sb.setLength(0);
	}

//------------------------------------------------------------------------------

	private void PrintPageLine(String PrintLine){
		int TmpPos[];
		if (LineCount+1 >= MaxLine-HaveLine){
			try{
				TmpPos=(int [])this.Pos.clone();
				this.RptDrawLine.setPos(prePos);
				if(isLine) {
					PrintLine=null;
					isLine = false;//Henry add
				}
				if(PreisLine){
                    PreTmpStr = NewRptEndLine(OutputVector.elementAt(OutputVector.size() - 1).toString());//泰亨加的!!
					this.OutputVector.removeElementAt(this.OutputVector.size()-1);
				}
				if((!HideLine) && isBegin){
					this.OutputVector.removeElementAt(this.OutputVector.size()-1);
					this.OutputVector.add(this.LineSeparator+this.LineSeparator);
					RptInit=false;
				}else{
					this.RPTEnd();
				}
				this.PrintPageNo();
				LineCount=0;
				this.RPTBegin((SpecialLine) ? prePos : Pos);
				this.Pos=(int [])TmpPos.clone();
				this.RptDrawLine.setPos(Pos);
			} catch (IOException e) {}
		}
		if(PrintLine!= null){
			this.OutputRPTBuffer(PrintLine);
			LineCount++;
		}
		this.SpecialLine=false;
	}

//------------------------------------------------------------------------------

	private String FindLineSeparator(String Str){
		int cut=0;
		String TmpStr=Str;
		while (true){
			cut=Str.indexOf(LineSeparator);
			if (cut==-1) break;
			TmpStr=Str.substring(0,cut+LineSeparator.length());
			Str=Str.substring(cut+LineSeparator.length());
			PreisLine=isLine;
			isLine=false;
			PrintPageLine(TmpStr);
			isBegin=false;
		}
		MultiLine=false;
		return Str;
	}

//------------------------------------------------------------------------------

	private void PrintPageNo(){
		OutputVector.addElement(this.DoReplacePageNo(PageSet.PrintFoot()));
		if(PreisLine) OutputVector.addElement(this.LineSeparator);
		if (this.WritePageNo)
			OutputVector.addElement(fillSpace(PagePosition)+"第"+Integer.toString(PageCount)+"頁"+LineSeparator);
		this.NewPage();
		PageCount++;
		this.Write(this.PageFile,this.WriteFile);
		OutputVector.addElement(this.DoReplacePageNo(PageSet.PrintHead()));
		this.WriteFile=(!WriteFile) ? true : WriteFile;
	}

//------------------------------------------------------------------------------

	public void OutputNewPage(){
		if (OutputVector.size() == 0)
			OutputVector.addElement(this.DoReplacePageNo(PageSet.PrintHead()));
		this.PrintPageNo();
		this.LineCount=0;
	}

//------------------------------------------------------------------------------

	public void PageStart(String file,int MaxLine,boolean WritePageNo,int PagePosition) throws IOException{
		if(!PageMode){
			this.WritePageNo=WritePageNo;
			this.MaxLine=(WritePageNo) ? MaxLine : (MaxLine+1);
			if(MaxLine < 4)
				throw new IOException("The Page lines must greater than "+Integer.toString(3+PageSet.Head.size()+PageSet.Foot.size())+" lines !!");
			this.PagePosition=PagePosition;
			this.PageFile=file;
			this.WriteFile=(!overwrite);
			this.PageMode=true;
			this.LineCount=0;
			this.PageCount=1;
			
			if(this.OutputVector.size() > 0){
				this.Write(this.PageFile,false);
				this.WriteFile=true;
				this.NewPage();
			}
			
			OutputVector.addElement(this.DoReplacePageNo(PageSet.PrintTitle()));
			OutputVector.addElement(this.DoReplacePageNo(this.PageSet.PrintHead()));
		}else{
			throw new IOException("ReportBuilder already begin the page !!");
		}
	}

//------------------------------------------------------------------------------

	public void PageStart(Page PageSet,String file,int MaxLine,boolean WritePageNo,int PagePosition,boolean overwrite) throws IOException{
		this.overwrite=overwrite;  // 是否overwrite file
		PageStart(PageSet,file,MaxLine,WritePageNo,PagePosition);
	}

//------------------------------------------------------------------------------

	public void PageStart(Page PageSet,String file,int MaxLine,boolean WritePageNo,int PagePosition,boolean overwrite,boolean WrNewEndPage) throws IOException{
		this.WrNewPageEnd=WrNewEndPage;  // 最後一頁是否要輸出換頁
		PageStart(PageSet,file,MaxLine,WritePageNo,PagePosition,overwrite);
	}

//------------------------------------------------------------------------------

	public void PageStart(Page PageSet,String file,int MaxLine,boolean WritePageNo,int PagePosition) throws IOException{
		this.PageSet.Title=PageSet.Title;
		this.PageSet.Head=PageSet.Head;
		this.PageSet.Foot=PageSet.Foot;
		this.PageSet.PageEnd=PageSet.PageEnd;
		MaxLine=MaxLine-(PageSet.Head.size()+PageSet.Foot.size());
		PageStart(file,MaxLine,WritePageNo,PagePosition);
	}

//------------------------------------------------------------------------------

	public void NewPage(){
		OutputVector.addElement("\f"+LineSeparator);
	}

//------------------------------------------------------------------------------

	public void PageEnd() throws IOException{
		if(PageMode){
			OutputVector.addElement(this.DoReplacePageNo(PageSet.PrintFoot()));
			OutputVector.addElement(this.DoReplacePageNo(PageSet.PrintPageEnd()));
			if (this.WritePageNo)
				OutputVector.addElement(fillSpace(PagePosition)+"第"+Integer.toString(PageCount)+"頁"+LineSeparator);
			if(this.WrNewPageEnd)
				this.NewPage();
			this.Write(this.PageFile,true);
			PagePosition=1;
			this.MaxLine=2147483647; // INT 最大值
			this.PageMode=false;
            //overwrite = false;	//Henry add
            WrNewPageEnd = true;//Henry add
		}else{
			throw new IOException("ReportBuilder must begin the page !!");
		}
	}

//------------------------------------------------------------------------------

	private void OutputRPTBuffer(String Str){
		OutputVector.addElement(Str);
	}

//------------------------------------------------------------------------------
	//消失的部份 Henry add
    private String NewRptEndLine(String Str){
        StringBuffer sTmp = new StringBuffer();
        int i = 1;
        String subStr = Str.substring(0, Str.indexOf("├"));
        Str = Str.trim();
        sTmp.append(subStr);
        sTmp.append("└");
        for(subStr = Str.substring(i, i + 1); !subStr.equals("┤"); subStr = Str.substring(i, i + 1))
        {
            i++;
            if(subStr.equals("┴") || subStr.equals("┼"))
                sTmp.append("┴");
            else
                sTmp.append("─");
        }

        sTmp.append("┘");
        return sTmp.toString();
    }

//------------------------------------------------------------------------------

	private String DoReplacePageNo(String Str){
		StringBuffer sTmp=new StringBuffer();
		String FrontStr,BackStr="",tmpStr=Str;
		int Found=0;
		Found=Str.indexOf("#@");
		if(Found >= 0){
			FrontStr=Str.substring(0,Found);
			if(Found+2 < Str.length())
				BackStr=this.DoReplacePageNo(Str.substring(Found+2));
			tmpStr=FrontStr+Integer.toString(this.PageCount)+BackStr;
		}
		sTmp.append(tmpStr);
		return sTmp.toString();
	}

//------------------------------------------------------------------------------

	public void ShowLine(){
		HaveLine=1;
		HideLine=false;
		SeparateStr="│";
		this.RptDrawItem.SetShowLine(true);
	}

//------------------------------------------------------------------------------

	public void HideLine(){
		HaveLine=0;
		HideLine=true;
		SeparateStr="  ";
		this.RptDrawItem.SetShowLine(false);
	}
//------------------------------------------------------------------------------

}