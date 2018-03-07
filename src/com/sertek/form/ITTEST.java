package com.sertek.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ITTEST {
	
	public static void main(String args[]) {
		ArrayList al = new ArrayList ();
		HashSet hs = new HashSet();
		hs.add(1);
		hs.add(2);
		hs.add(3);
		hs.add(1);
		al.add(1);
		al.add(2);
		al.add(3);
		al.add(1);
		System.out.println(hs);
		System.out.println(al);
	}
	
}
