package com.sertek.util;
import java.util.*;
import org.apache.commons.fileupload.*;
public class Requst4Upload {
	List fileItems = null;	
	FileItem[] FileFileItem = null;
	Vector FileFileItem_vt = new Vector();
 	Hashtable ht = new Hashtable();
	public Requst4Upload(List fileItems){
		this.fileItems = fileItems;
		init();
	}
	/**
	 * 傳入 name ，取得 FileItem 物件
	 * @param name
	 * @return FileItem
	 */
	public FileItem getFileItem(String name){
		
		return (FileItem)ht.get(name);
	}
	private void init(){
		Iterator i = fileItems.iterator();
		while(i.hasNext() ){
			FileItem item = (FileItem) i.next();
			String name = item.getFieldName(); // 這裡取得parameter的名字
			//String value = item.getString(); // 取得parameter的數值
			ht.put(name,item);	
			
			if (!item.isFormField()) {
				FileFileItem_vt.add(item);
			}
		}
		
		if (FileFileItem_vt.size()>0){
				
			this.FileFileItem = new FileItem[FileFileItem_vt.size()];
			
			for(int j=0;j<FileFileItem_vt.size();j++ ){
				this.FileFileItem[j] =(FileItem) FileFileItem_vt.get(j);
			}
		}	
		//i=null;

	}
	
	
	//取得檔案屬性的 FileItem 陣列
	public FileItem[] getFileFileItem(){		
		return this.FileFileItem;
	}
}
