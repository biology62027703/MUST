// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReportBuilder.java

package com.sertek.report;

import java.io.*;
import java.util.Vector;

// Referenced classes of package com.sertek.report:
//            DrawLine, DrawItem, Page

//ReportBuilder2  目的是修正
//當檔案太大時ReportBuilder所產生的Bug 

public class ReportBuilder2
{
    private BufferedWriter writer=null;
    public ReportBuilder2(String file)throws Exception
    {
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "MS950"));

        RptDrawLine = new DrawLine();
        RptDrawItem = new DrawItem();
        SeparateStr = "\u2502";
        PreTmpStr = null;
        PageSet = new Page();
        HideLine = false;
        isLine = false;
        isBegin = false;
        WrNewPageEnd = true;
        PreLine = false;
        overwrite = false;
        RptInit = false;
        WriteFile = false;
        SpecialLine = false;
        MultiLine = false;
        WritePageNo = false;
        PageMode = false;
        LineCount = 0;
        PageCount = 1;
        MaxLine = 0;
        PagePosition = 0;
        HaveLine = 1;
        OutputVector = new Vector();
        LineSeparator = System.getProperty("line.separator") != null ? System.getProperty("line.separator") : "\n";
        PagePosition = 1;
        MaxLine = 0x7fffffff;
        LineCount = 0;
    }

    public void RPTBegin(int Pos[])
        throws IOException
    {
        if(!RptInit)
        {
            this.Pos = Pos;
            RptDrawLine.setPos(null);
            RptDrawLine.setPos(Pos);
            RptDrawLine.setEnd();
            StringBuffer sTmp = new StringBuffer();
            isBegin = true;
            if(!HideLine)
            {
                sTmp.append(RptDrawLine.DrawIt("\u250C", "\u252C", "\u2510"));
                if(LineCount + 1 >= MaxLine - HaveLine)
                {
                    OutputRPTBuffer(LineSeparator);
                    PrintPageNo();
                    LineCount = 0;
                }
                OutputRPTBuffer(String.valueOf(sTmp.toString()) + String.valueOf(LineSeparator));
                LineCount = LineCount + 1;
            }
            prePos = (int[])this.Pos.clone();
            RptInit = true;
        } else
        {
            throw new IOException("ReportBuilder already begin the report !!");
        }
    }

    public void RPTEnd()
        throws IOException
    {
        if(RptInit)
        {
            StringBuffer sTmp = new StringBuffer();
            RptDrawLine.setEnd();
            if(!HideLine)
            {
                if(isLine)
                {
                    OutputVector.removeElementAt(OutputVector.size() - 1);
                    LineCount = LineCount - 1;
                }
                if(PreTmpStr == null)
                {
                    sTmp.append(RptDrawLine.DrawIt("\u2514", "\u2534", "\u2518"));
                } else
                {
                    sTmp.append(PreTmpStr);
                    PreTmpStr = null;
                }
                if(LineCount > 0)
                {
                    OutputRPTBuffer(String.valueOf(sTmp.toString()) + String.valueOf(LineSeparator));
                    LineCount = LineCount + 1;
                }
            }
            RptInit = false;
        } else
        {
            throw new IOException("ReportBuilder never begin the report !!");
        }
    }

    public void RPTLine()
        throws IOException
    {
        if(RptInit)
        {
            RptDrawLine.setPos(Pos);
            StringBuffer sTmp = new StringBuffer();
            if(!HideLine)
            {
                sTmp.append(RptDrawLine.DrawIt("\u251C", "\u253C", "\u2524"));
                PreisLine = isLine;
                isLine = true;
                PrintPageLine(String.valueOf(sTmp.toString()) + String.valueOf(LineSeparator));
                isBegin = false;
            }
            prePos = (int[])Pos.clone();
        } else
        {
            throw new IOException("ReportBuilder must begin the report !!");
        }
    }

    public void RPTLine(int Pos[])
        throws IOException
    {
        if(RptInit)
        {
            prePos = (int[])this.Pos.clone();
            this.Pos = Pos;
            RPTLine();
        } else
        {
            throw new IOException("ReportBuilder must begin the report !!");
        }
    }

    public void RPTContents(int xPos[], Vector contents)
        throws IOException
    {
        if(xPos.length != contents.size() + 1)
            throw new IOException("Data and Position aren't match !!");
        if(RptInit)
        {
            prePos = (int[])Pos.clone();
            RptDrawLine.setPos(xPos);
            Pos = xPos;
            RPTContents(contents);
        } else
        {
            throw new IOException("ReportBuilder doesn't begin the report !!");
        }
    }

    public void RPTContents(int xPos[], Vector contents, int xMov[])
        throws IOException
    {
        if(xPos.length != contents.size() + 1)
            throw new IOException("Data and Position aren't match !!");
        if(RptInit)
        {
            prePos = (int[])Pos.clone();
            RptDrawLine.setPos(xPos);
            Pos = xPos;
            Mov = xMov;
            RPTContents(contents);
        } else
        {
            throw new IOException("ReportBuilder must begin the report !!");
        }
    }

    public void RPTContents(Vector contents, int xMov[])
        throws IOException
    {
        if(Pos.length != contents.size() + 1)
            throw new IOException("Data and Position aren't match !!");
        if(RptInit)
        {
            prePos = (int[])Pos.clone();
            Mov = xMov;
            RPTContents(contents);
        } else
        {
            throw new IOException("ReportBuilder doesn't begin the report !!");
        }
    }

    public void RPTContents(Vector contents)
        throws IOException
    {
        if(Pos.length != contents.size() + 1)
            throw new IOException("Data and Position aren't match !!");
        if(RptInit)
        {
            String Str = CheckContents(contents,false);
            Str = MultiLine ? FindLineSeparator(Str) : Str;
            PreisLine = isLine;
            isLine = false;
            PrintPageLine(String.valueOf(Str) + String.valueOf(LineSeparator));
            isBegin = false;
            prePos = (int[])Pos.clone();
        } else
        {
            throw new IOException("ReportBuilder must begin the report !!");
        }
    }


    public void RPTContentsNoTrim(int xPos[], Vector contents, int xMov[])
        throws IOException
    {
        if(xPos.length != contents.size() + 1)
            throw new IOException("Data and Position aren't match !!");
        if(RptInit)
        {
            prePos = (int[])Pos.clone();
            RptDrawLine.setPos(xPos);
            Pos = xPos;
            Mov = xMov;
            RPTContentsNoTrim(contents);
        } else
        {
            throw new IOException("ReportBuilder must begin the report !!");
        }
    }

//notrim true 不將字串空白trim掉
    public void RPTContentsNoTrim(Vector contents)
        throws IOException
    {
        if(Pos.length != contents.size() + 1)
            throw new IOException("Data and Position aren't match !!");
        if(RptInit)
        {
            String Str = CheckContents(contents,true);
            Str = MultiLine ? FindLineSeparator(Str) : Str;
            PreisLine = isLine;
            isLine = false;
            PrintPageLine(String.valueOf(Str) + String.valueOf(LineSeparator));
            isBegin = false;
            prePos = (int[])Pos.clone();
        } else
        {
            throw new IOException("ReportBuilder must begin the report !!");
        }
    }

    private String CheckContents(Vector contents,boolean notrim)
    {
        StringBuffer sTmp = new StringBuffer();
        int iSize = contents.size();
        Vector tmpVector1 = new Vector();
        Vector tmpVector2 = new Vector();
        boolean CutLine = false;
        for(int i = 0; i < iSize; i++)
        {
            String TmpStr = "";
            String SourceStr = contents.elementAt(i).toString();
            if(SourceStr.indexOf(10) != 0 && !notrim)
                SourceStr = SourceStr.trim();
            SourceStr = DoReplacePageNo(SourceStr);
            int length = Pos[i + 1] - (Pos[i] + 1) - 1;
            tmpVector1.addElement(SourceStr);
            try
            {
                if(SourceStr.getBytes("MS950").length > length)
                {
                    CutLine = true;
                    TmpStr = CutString(SourceStr, length);
                    tmpVector1.setElementAt(TmpStr, i);
                    TmpStr = SourceStr.substring(TmpStr.length());
                }
            }
            catch(UnsupportedEncodingException unsupportedencodingexception) { }
            tmpVector2.addElement(TmpStr);
        }

        if(CutLine)
            sTmp.append(String.valueOf(LineSeparator) + String.valueOf(CheckContents(tmpVector2,false)));
        MultiLine = CutLine;
        return String.valueOf(DrawContents(tmpVector1)) + String.valueOf(sTmp.toString());
    }

    private String CutString(String SourceStr, int length)
    {
        int wordcnt = 0;
        StringBuffer sTmp = new StringBuffer();
        StringBuffer SpecialTmp = new StringBuffer();
        String TmpStr = SourceStr;
        boolean NotBreakLine = true;
        if(SourceStr.length() <= 1)
            return TmpStr;
        try
        {
            do
            {
                TmpStr = SourceStr.substring(0, 1);
                SourceStr = SourceStr.substring(1);
                wordcnt += TmpStr.getBytes("MS950").length;
                if(TmpStr.equals("\n"))
                    NotBreakLine = false;
                if(TmpStr.equals("\r"))
                {
                    SpecialTmp.append('\r');
                    TmpStr = "";
                    wordcnt--;
                }
                if(wordcnt <= length)
                    sTmp.append(TmpStr);
            } while(NotBreakLine && wordcnt <= length);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        SpecialTmp.append(sTmp.toString());
        return SpecialTmp.toString();
    }

    private String DrawContents(Vector contents)
    {
        int iSize = contents.size();
        int iStart = Pos[0] + 1;
        StringBuffer sTmp = new StringBuffer();
        ChaeckPosition(iSize);
        sTmp.append(fillSpace(Pos[0] - 1));
        sTmp.append(SeparateStr);
        for(int i = 1; i <= iSize; i++)
        {
            String TargetString = contents.elementAt(i - 1).toString();
            int iEnd = Pos[i];
            RptDrawItem.DrawSetProperty(TargetString, Mov[i - 1], iEnd - iStart);
            String ContentStr = RptDrawItem.DrawStr();
            if(RptDrawItem.SpecialStr)
            {
                sTmp.delete(sTmp.length() - 1, sTmp.length());
                SpecialLine = true;
            }
            sTmp.append(ContentStr);
            iStart = iEnd + 1;
        }

        return sTmp.toString();
    }

    private void ChaeckPosition(int size)
    {
        if(Mov == null || Mov.length != size)
        {
            Mov = (int[])Pos.clone();
            for(int i = 0; i < size; i++)
                Mov[i] = 0;

        }
    }

    private String fillSpace(int cnt)
    {
        StringBuffer sTmp = new StringBuffer();
        for(int i = 1; i <= cnt; i++)
            sTmp.append(" ");

        return sTmp.toString();
    }

    public void Write()
    {
        int iSize = OutputVector.size();
        try
        {
           for(int i = 0; i < iSize; i++)
                writer.write(OutputVector.elementAt(i).toString());
           
        }
        catch(IOException ioexception) { }
        OutputVector.clear();
    }
    
    public void close() throws Exception{
		  writer.close();
    }
    

    public void RPTString(String Str)
    {
        try
        {
            RPTString(Str, 1);
        }
        catch(IOException ioexception) { }
    }

    public void RPTString(int pos, String Str)
    {
        try
        {
            RPTString(String.valueOf(fillSpace(pos)) + String.valueOf(Str), 1);
        }
        catch(IOException ioexception) { }
    }

    public void RPTString(String Str, int cnt)
        throws IOException
    {
        int modLine = MaxLine - (LineCount + cnt);
        if(cnt > MaxLine - HaveLine)
            throw new IOException(String.valueOf(String.valueOf((new StringBuffer("The Page lines must less than ")).append(Integer.toString(MaxLine - HaveLine)).append(" lines !!"))));
        Str = DoReplacePageNo(Str);
        PreisLine = isLine;
        isLine = false;
        if(modLine <= 0)
        {
            PrintPageNo();
            OutputRPTBuffer(String.valueOf(Str) + String.valueOf(LineSeparator));
            LineCount = cnt;
        } else
        if(modLine == 1)
        {
            OutputRPTBuffer(String.valueOf(Str) + String.valueOf(LineSeparator));
            PrintPageNo();
            LineCount = 0;
        } else
        if(modLine == 2)
        {
            OutputRPTBuffer(String.valueOf(Str) + String.valueOf(LineSeparator));
            OutputRPTBuffer(LineSeparator);
            PrintPageNo();
            LineCount = 0;
        } else
        {
            OutputRPTBuffer(String.valueOf(Str) + String.valueOf(LineSeparator));
            LineCount = LineCount + cnt;
        }
        isBegin = false;
    }

    public void RPTString(int pos, String Str, int cnt)
    {
        try
        {
            RPTString(String.valueOf(fillSpace(pos)) + String.valueOf(Str), cnt);
        }
        catch(IOException ioexception) { }
    }

    public void RPTDump()
    {
        int iSize = OutputVector.size();
        for(int i = 0; i < iSize; i++)
            System.out.print(OutputVector.elementAt(i).toString());

    }

    private void PrintPageLine(String PrintLine)
    {
        if(LineCount + 1 >= MaxLine - HaveLine)
            try
            {
                int TmpPos[] = (int[])Pos.clone();
                RptDrawLine.setPos(prePos);
                if(isLine)
                {
                    PrintLine = null;
                    isLine = false;
                }
                if(PreisLine)
                {
                    PreTmpStr = NewRptEndLine(OutputVector.elementAt(OutputVector.size() - 1).toString());
                    OutputVector.removeElementAt(OutputVector.size() - 1);
                }
                if(!HideLine && isBegin)
                {
                    OutputVector.removeElementAt(OutputVector.size() - 1);
                    OutputVector.add(String.valueOf(LineSeparator) + String.valueOf(LineSeparator));
                    RptInit = false;
                } else
                {
                    RPTEnd();
                }
                PrintPageNo();
                LineCount = 0;
                RPTBegin(SpecialLine ? prePos : Pos);
                Pos = (int[])TmpPos.clone();
                RptDrawLine.setPos(Pos);
            }
            catch(IOException ioexception) { }
        if(PrintLine != null)
        {
            OutputRPTBuffer(PrintLine);
            LineCount++;
        }
        SpecialLine = false;
    }

    private String FindLineSeparator(String Str)
    {
        int cut = 0;
        String TmpStr = Str;
        do
        {
            cut = Str.indexOf(LineSeparator);
            if(cut != -1)
            {
                TmpStr = Str.substring(0, cut + LineSeparator.length());
                Str = Str.substring(cut + LineSeparator.length());
                PreisLine = isLine;
                isLine = false;
                PrintPageLine(TmpStr);
                isBegin = false;
            } else
            {
                MultiLine = false;
                return Str;
            }
        } while(true);
    }

    private void PrintPageNo()
    {
        OutputVector.addElement(DoReplacePageNo(PageSet.PrintFoot()));
        if(PreisLine)
            OutputVector.addElement(LineSeparator);
        if(WritePageNo)
            OutputVector.addElement(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(fillSpace(PagePosition))))).append("\u7B2C").append(Integer.toString(PageCount)).append("\u9801").append(LineSeparator))));
        NewPage();
        PageCount++;
        Write();
        OutputVector.addElement(DoReplacePageNo(PageSet.PrintHead()));
        WriteFile = WriteFile ? WriteFile : true;
    }

    public void OutputNewPage()
    {
        if(OutputVector.size() == 0)
            OutputVector.addElement(DoReplacePageNo(PageSet.PrintHead()));
        PrintPageNo();
        LineCount = 0;
    }

    public void PageStart(String file, int MaxLine, boolean WritePageNo, int PagePosition)
        throws IOException
    {
        if(!PageMode)
        {
            this.WritePageNo = WritePageNo;
            this.MaxLine = WritePageNo ? MaxLine : MaxLine + 1;
            if(MaxLine < 4)
                throw new IOException(String.valueOf(String.valueOf((new StringBuffer("The Page lines must greater than ")).append(Integer.toString(3 + PageSet.Head.size() + PageSet.Foot.size())).append(" lines !!"))));
            this.PagePosition = PagePosition;
            PageFile = file;
            WriteFile = !overwrite;
            PageMode = true;
            LineCount = 0;
            PageCount = 1;
            if(OutputVector.size() > 0)
            {
                Write();
                WriteFile = true;
                NewPage();
            }
            OutputVector.addElement(DoReplacePageNo(PageSet.PrintTitle()));
            OutputVector.addElement(DoReplacePageNo(PageSet.PrintHead()));
        } else
        {
            throw new IOException("ReportBuilder already begin the page !!");
        }
    }

    public void PageStart(Page PageSet, String file, int MaxLine, boolean WritePageNo, int PagePosition, boolean overwrite)
        throws IOException
    {
        this.overwrite = overwrite;
        PageStart(PageSet, file, MaxLine, WritePageNo, PagePosition);
    }

    public void PageStart(Page PageSet, String file, int MaxLine, boolean WritePageNo, int PagePosition, boolean overwrite, boolean WrNewPageEnd)
        throws IOException
    {
        this.WrNewPageEnd = WrNewPageEnd;
        PageStart(PageSet, file, MaxLine, WritePageNo, PagePosition, overwrite);
    }

    public void PageStart(Page PageSet, String file, int MaxLine, boolean WritePageNo, int PagePosition)
        throws IOException
    {
        this.PageSet.Title = PageSet.Title;
        this.PageSet.Head = PageSet.Head;
        this.PageSet.Foot = PageSet.Foot;
        this.PageSet.PageEnd = PageSet.PageEnd;
        MaxLine -= PageSet.Head.size() + PageSet.Foot.size();
        PageStart(file, MaxLine, WritePageNo, PagePosition);
    }

    public void NewPage()
    {
        OutputVector.addElement("\f".concat(String.valueOf(String.valueOf(LineSeparator))));
    }

    public void PageEnd()
        throws IOException
    {
        if(PageMode)
        {
            OutputVector.addElement(DoReplacePageNo(PageSet.PrintFoot()));
            OutputVector.addElement(DoReplacePageNo(PageSet.PrintPageEnd()));
            if(WritePageNo)
                OutputVector.addElement(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(fillSpace(PagePosition))))).append("\u7B2C").append(Integer.toString(PageCount)).append("\u9801").append(LineSeparator))));
            if(WrNewPageEnd)
                NewPage();
            Write();
            PagePosition = 1;
            MaxLine = 0x7fffffff;
            PageMode = false;
            overwrite = false;
            WrNewPageEnd = true;
        } else
        {
            throw new IOException("ReportBuilder must begin the page !!");
        }
    }

    private void OutputRPTBuffer(String Str)
    {
        OutputVector.addElement(Str);
    }

    private String NewRptEndLine(String Str)
    {
        StringBuffer sTmp = new StringBuffer();
        int i = 1;
        String subStr = Str.substring(0, Str.indexOf("\u251C"));
        Str = Str.trim();
        sTmp.append(subStr);
        sTmp.append("\u2514");
        for(subStr = Str.substring(i, i + 1); !subStr.equals("\u2524"); subStr = Str.substring(i, i + 1))
        {
            i++;
            if(subStr.equals("\u2534") || subStr.equals("\u253C"))
                sTmp.append("\u2534");
            else
                sTmp.append("\u2500");
        }

        sTmp.append("\u2518");
        return sTmp.toString();
    }

    private String DoReplacePageNo(String Str)
    {
        StringBuffer sTmp = new StringBuffer();
        String BackStr = "";
        String tmpStr = Str;
        int Found = 0;
        Found = Str.indexOf("#@");
        if(Found >= 0)
        {
            String FrontStr = Str.substring(0, Found);
            if(Found + 2 < Str.length())
                BackStr = DoReplacePageNo(Str.substring(Found + 2));
            tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(FrontStr)))).append(Integer.toString(PageCount)).append(BackStr)));
        }
        sTmp.append(tmpStr);
        return sTmp.toString();
    }

    public void ShowLine()
    {
        HaveLine = 1;
        HideLine = false;
        SeparateStr = "\u2502";
        RptDrawItem.SetShowLine(true);
    }

    public void HideLine()
    {
        HaveLine = 0;
        HideLine = true;
        SeparateStr = "  ";
        RptDrawItem.SetShowLine(false);
    }

    private DrawLine RptDrawLine;
    private DrawItem RptDrawItem;
    private int Pos[];
    private int prePos[];
    private int Mov[];
    private String SeparateStr;
    private String PreTmpStr;
    private Page PageSet;
    private boolean HideLine;
    private boolean PreisLine;
    private boolean isLine;
    private boolean isBegin;
    private boolean WrNewPageEnd;
    private boolean PreLine;
    private boolean overwrite;
    private boolean RptInit;
    private boolean WriteFile;
    private boolean SpecialLine;
    private boolean MultiLine;
    private boolean WritePageNo;
    private boolean PageMode;
    private String LineSeparator;
    private String PageFile;
    private int LineCount;
    private int PageCount;
    private int MaxLine;
    private int PagePosition;
    private int HaveLine;
    private Vector OutputVector;
}