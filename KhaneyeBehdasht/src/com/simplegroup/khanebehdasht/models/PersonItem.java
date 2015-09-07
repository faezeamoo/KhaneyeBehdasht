package com.simplegroup.khanebehdasht.models;

public class PersonItem {

	/*
	 * #########################################################################
	 * ########################## get and set functions ########################
	 * #########################################################################
	 */

	public void setNameAndFamily(String value) {
		nameAndFamily = value;
	}

	public String getNameAndFamily() {
		return nameAndFamily;
	}

	public boolean setNationalCode(String value) {
		if (value.length() > 10 || value.length() < 10) {
			return false;
		} else {
			nationalCode = value;
			return true;
		}
	}

	public String getNationalCode() {
		return nationalCode;
	}

	public boolean setFatherCode(String value) {
		if (value.length() > 10 || value.length() < 10) {
			return false;
		} else {
			fatherCode = value;
			return true;
		}
	}

	public String getFatherCode() {
		return fatherCode;
	}

	public boolean setMotherCode(String value) {
		if (value.length() > 10 || value.length() < 10) {
			return false;
		} else {
			motherCode = value;
			return true;
		}
	}

	public String getMotherCode() {
		return motherCode;
	}

	public void setFamilyCode(int value) {
		familyCode = value;
	}

	public int getFamilyCode() {
		return familyCode;
	}

	public void setBimeCode(int value) {
		bimeCode = value;
	}

	public int getBimeCode() {
		return bimeCode;
	}

	public void setPolicyCode(String value) {
		policyCode = value;
	}

	public String getPolicyCode() {
		return policyCode;
	}

	public void setIsAlive(boolean value) {
		isAlive = String.valueOf(value);
	}

	public boolean getIsAlive() {
		return Boolean.valueOf(isAlive);
	}

	public String getIsAliveAsString() {
		return isAlive;
	}

	/*
	 * #########################################################################
	 * ########################## attributes ###################################
	 * #########################################################################
	 */

	private String nameAndFamily;
	private String nationalCode;
	private String fatherCode;
	private String motherCode;
	private int familyCode;
	private int bimeCode;
	private String policyCode;
	private String isAlive; // Boolean
}
