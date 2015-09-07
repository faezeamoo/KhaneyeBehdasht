package com.simplegroup.khanebehdasht.models;

public class FamilyItem {
	/*
	 * #########################################################################
	 * ########################### basic methods ###############################
	 * #########################################################################
	 */
	
	public void setCode(int value) {
		code = value;
	}
	
	public int getCode(){
		return code;
	}
	
	public void setFatherCode(String value) {
		fatherCode = value;
	}
	
	public String getFahterCode() {
		return fatherCode;
	}
	
	public void setFatherNameAndFamily(String value) {
		fatherNameAndFamily = value;
	}
	
	public String getFatherNameAndFamily() {
		return fatherNameAndFamily;
	}
	
	public void setKhaneBehdashtCode(int value) {
		khaneBehdashtCode = value;
	}
	
	public int getKhaneBehdashtCode() {
		return khaneBehdashtCode;
	}
	
	public void setKhaneBehdashtName(String value) {
		khaneBehdashtName = value;
	}
	
	public String getKhaneBehdashtName() {
		return khaneBehdashtName;
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
	/*
	 * #########################################################################
	 * ############################ Attributes #################################
	 * #########################################################################
	 */
	private int code;
	private String fatherCode;
	private String fatherNameAndFamily;
	private int khaneBehdashtCode;
	private String khaneBehdashtName;
	private String isAvailable;
	
	/*
	 * #########################################################################
	 * #########################################################################
	 * #########################################################################
	 */
}
