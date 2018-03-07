
package com.sertek.util;

public class util_IniEntry
{
    private String Entry;
    private String Value;

    public  util_IniEntry (String E,String V)
    {
      setup(E,V);
    }
    public  util_IniEntry (String E,int V)
    {
      setup(E,String.valueOf(V));
    }

    public void setup(String E , String V )
    {
      Entry = E;
      Value = V;
    }
    public String getEntry() { return Entry; }
    public void setValue(String sValue)
    {
        Value = sValue;
    }
    public void setValue(int iValue)
    {
        Value = String.valueOf(iValue);
    }
    public String getValue() { return Value; }
    public String toString() { return Value; }
}