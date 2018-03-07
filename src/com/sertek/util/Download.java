package com.sertek.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
 
import org.apache.commons.io.FileUtils;
public class Download {

	 
 public static void main(String[] args) {
  try
  {
   String strUrl = "你要下載的檔案網址";
   URL source = new URL(strUrl);
   String theStrDestDir = "D:/";
   File theStockDest = new File(theStrDestDir);
   FileUtils.forceMkdir(theStockDest);
    
   File destination = new File(theStrDestDir + "TTT.xlsx");
    
   FileUtils.copyURLToFile(source, destination);
   //File file = new File(".");
   System.out.println("File Downloaded!");
  } catch (MalformedURLException e)
  {
   e.printStackTrace();
  } catch (IOException e)
  {
   e.printStackTrace();
  }
 }
	 
	
}
