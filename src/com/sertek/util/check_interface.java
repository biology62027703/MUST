package com.sertek.util;

interface check_interface {
	public String checkNull(String data,String default_value);
	public int checkNull(int data,int default_value);
	public Integer checkNull(Integer data,Integer default_value);
	public Float checkNull(Float data,Float default_value);
	public Double checkNull(Double data,Double default_value);
	public Object checkNull(Object data,Object default_value);
}