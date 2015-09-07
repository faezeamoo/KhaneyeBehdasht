package com.simplegroup.khanebehdasht.models;

public class BimeItem
{
	// Model Properties
	private int BimeCode;
	private String BimeName;
	private String BimeIsAvailable;

	// Methods
	public BimeItem()
	{
	}

	public int getCode()
	{
		return BimeCode;
	}

	public void setCode(int bimeCode)
	{
		BimeCode = bimeCode;
	}

	public String getName()
	{
		return BimeName;
	}

	public void setName(String bimeName)
	{
		BimeName = bimeName;
	}

	public boolean getIsAvailable()
	{
		return Boolean.valueOf(BimeIsAvailable);
	}
	
	public String getIsAvailableAsString()
	{
		return BimeIsAvailable;
	}

	public void setIsAvailable(boolean bimeAvailableState)
	{
		BimeIsAvailable = String.valueOf(bimeAvailableState);
	}
}
