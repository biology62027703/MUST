package com.sertek.util;

import java.util.HashMap;
import java.util.Map;

public class MapHelper {
	private Map map = new HashMap();
	
	public MapHelper(Map map) {
		this.map = map;		
	}	
	
	/**
	 * 空值傳回 ""
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String ret = "";
		if(map.get(key) != null) {
			ret = (String)map.get(key);
		}
		return ret;
	}	
	public String get(Object key) {
		String ret = "";
		if(map.get(key) != null) {
			ret = (String)map.get(key);
		}
		return ret;
	}
	public Object getObj(Object key) {
		return map.get(key);
	}
	
	/**
	 * 空值回傳 null
	 * @param key
	 * @return
	 */
	public String getNull(String key) {
		return (String)map.get(key);
	}
	public String getNull(Object key) {		
		return (String)map.get(key);
	}
	
	public void put(String key, String value) {
		map.put(key, value);
	}
	
	public void put(Object key, Object value) {
		map.put(key, value);
	}
}
