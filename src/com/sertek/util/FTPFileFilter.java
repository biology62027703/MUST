package com.sertek.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FTPFileFilter {
	
	public static boolean accept(String filenm, String filter) throws Exception {
		boolean result = false;
		if ("".equals(filter)) {
			return false;
		}
		if ("*.*".equals(filter)) {
			return true;
		}

		filenm = filenm.toUpperCase();
		filter = filter.toUpperCase();
		String[] filters = filter.split(",");
		if (filter != null) {
			for (int i = 0; i < filters.length; i++) {
				//System.out.println("filenm = " + filenm + ", filter = " + filters[i]);
				if (filters[i].endsWith("*.*")) {
					String prefix = filters[i];
					int pos = filters[i].lastIndexOf("*.*");
					if (pos > 0) {
						prefix = filters[i].substring(0, pos);
					}
					if (filenm.startsWith(prefix)) {
						return true;
					}
				} else {
					Pattern pattern = Pattern.compile(replaceFilterToRegex(filters[i]));
					Matcher matcher = pattern.matcher(filenm);
					if(matcher.find()){
						return true;
					}
				}
			}
		}
		
		return result;
	}
	
	private static String replaceFilterToRegex(String filter) {
		String regex = filter;
		regex = regex.replaceAll("\\.", "\\\\.");
		regex = regex.replaceAll("\\*", ".");
		return regex;
	}
}