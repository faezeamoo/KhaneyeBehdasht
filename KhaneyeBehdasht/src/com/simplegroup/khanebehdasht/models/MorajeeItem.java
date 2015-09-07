package com.simplegroup.khanebehdasht.models;

public class MorajeeItem {

	public void setPersonCode(String value) {
		personCode = value;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setDate(String value) {
		date = value;
	}

	public String getDate() {
		return date;
	}

	public void setMalady(int value) {
		maladyCode = value;
	}

	public int getMalady() {
		return maladyCode;
	}

	public void setSeeing(String value) {
		seeing = value;
	}

	public String getSeeing() {
		return seeing;
	}

	public void setTajviz(String value) {
		tajviz = value;
	}

	public String getTajviz() {
		return tajviz;
	}
	
	public void setMaladyName(String value) {
		maladyName = value;
	}
	
	public String getMaladyName() {
		return maladyName;
	}

	/*
	 * #########################################################################
	 * ############################## Attributes ###############################
	 * #########################################################################
	 */
	private String personCode;
	private String date;
	private int maladyCode;
	private String maladyName;
	private String seeing;
	private String tajviz;
}
