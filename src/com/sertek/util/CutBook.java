package com.sertek.util;
import java.awt.Toolkit;  
import java.awt.datatransfer.ClipboardOwner;  
import java.awt.datatransfer.DataFlavor;  
import java.awt.datatransfer.StringSelection;  
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.Clipboard;

public class CutBook implements ClipboardOwner{  
    private Clipboard clipboard;  
  
    public CutBook() {  
        CutBookInit();  
    }  
  
    public void CutBookInit(){  
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();  
    }  
  
    /** 
     * 設定剪貼內容 
     * @param str 
     */      
    public void setBookContents(String str){  
        StringSelection contents = new StringSelection(str);  
        clipboard.setContents(contents, this);  
    }  
  
    /** 
     * 取出剪貼內容 
     * @return 
     */  
    public String getBookContents(){  
        Transferable content = clipboard.getContents(this);  
        try{  
            return (String) content.getTransferData(DataFlavor.stringFlavor);  
        }catch(Exception e){  
            e.printStackTrace();  
            //System.out.println(e);  
        }  
        return null;  
    }  
  
    public void lostOwnership(Clipboard clipboard, Transferable contents) {  
        //System.out.println("lostOwnership...");  
    }  
      
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        //印出目前剪貼簿內容  
          
        //CutBook cb = new CutBook();  
        //cb.setBookContents("AAAAA");
        //System.out.println(cb.getBookContents());  
  
    }  
  
}  
	

