package com.sertek.util;

import java.io.File;
import java.io.FilenameFilter;

public class HelpFileFilter implements FilenameFilter {

	private String name;

	private String extension;

	public HelpFileFilter(String name, String extension) {
		this.name = name;
		this.extension = extension;
	}

	public boolean accept(File dir, String filenm) {
		boolean result = true;
		if (name != null) {
			result &= filenm.startsWith(name);
		}

		if (extension != null) {
			result &= filenm.endsWith('.' + extension);
		}
		return result;
	}
}