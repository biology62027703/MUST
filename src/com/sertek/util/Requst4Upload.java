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
	 * �ǤJ name �A���o FileItem ����
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
			String name = item.getFieldName(); // �o�̨��oparameter���W�r
			//String value = item.getString(); // ���oparameter���ƭ�
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
	
	
	//���o�ɮ��ݩʪ� FileItem �}�C
	public FileItem[] getFileFileItem(){		
		return this.FileFileItem;
	}
}
