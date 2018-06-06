package com.elektrimasinad.aho.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Company implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -711750541648786588L;
	private String companyKey;
	private String companyName;
	private List<Unit> locationList = new ArrayList<Unit>();
	
	public Company() {}
	
	public Company(String companyName) {
		this.setCompanyName(companyName);
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public void addLocation(Unit location) {
		if(!locationList.contains(location)) {
			locationList.add(location);
		}
		//TODO location already exists exception
	}
	
	public void removeLocation(Unit location) {
		if(locationList.contains(location)) {
			locationList.remove(location);
		}
		//TODO no such location exception
	}
	
	public List<Unit> getLocationList() {
		return locationList;
	}

	/**
	 * @return the companyKey
	 */
	public String getCompanyKey() {
		return companyKey;
	}

	/**
	 * @param companyKey the companyKey to set
	 */
	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}
}
