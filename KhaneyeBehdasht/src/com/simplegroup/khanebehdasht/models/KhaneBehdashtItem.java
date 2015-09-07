package com.simplegroup.khanebehdasht.models;

public class KhaneBehdashtItem {
	// columns name 
	private int code;
	private String name;
	private String isAvailable;
	// functions
	public void setCode(int code)
	{
		this.code = code;
	}
	
	public int getCode()
	{
		return this.code;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setIsAvailable(boolean isAvailable)
	{
		this.isAvailable = String.valueOf(isAvailable);
	}
	
	public Boolean getIsAvailable()
	{
		return Boolean.valueOf(this.isAvailable);
	}
	
	public String getIsAvailableAsString()
	{
		return isAvailable;
	}
	
}
