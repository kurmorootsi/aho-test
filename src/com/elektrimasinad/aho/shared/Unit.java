package com.elektrimasinad.aho.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Unit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -342640462355108428L;
	private String unitKey;
	private String companyKey;
	private String unit;
	private List<Device> deviceList = new ArrayList<Device>();
	
	public Unit() {}
	
	public Unit(String companyKey, String unit) {
		this.companyKey = companyKey;
		this.unit = unit;
	}

	/**
	 * @return the locationKey
	 */
	public String getUnitKey() {
		return unitKey;
	}

	/**
	 * @param locationKey the locationKey to set
	 */
	public void setUnitKey(String locationKey) {
		this.unitKey = locationKey;
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
	
	/**
	 * @return the occupation
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param occupation the occupation to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void addDevice(Device device) {
		if(!deviceList.contains(device)) {
			deviceList.add(device);
		}
		//TODO device already exists exception
	}
	
	public void removeDevice(Device device) {
		if(deviceList.contains(device)) {
			deviceList.remove(device);
		}
		//TODO no such device exception
	}
	
	public List<Device> getDeviceList() {
		return deviceList;
	}
}
