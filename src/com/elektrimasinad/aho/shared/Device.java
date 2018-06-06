package com.elektrimasinad.aho.shared;

import java.io.Serializable;

public class Device implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -664059249789813575L;
	private String locationKey;
	private String deviceKey;
	//Motor data
	private String id;
	private String deviceName;
	private String locationName;
	private String deviceNickname = "El.mootor";
	private String devicekWrpm;
	private String deviceType;
	private String deviceManufacturer;
	
	private String DElaager;
	private String DEsimmer;
	private String DEVtihend;
	private String DEnotes;
	private String NDElaager;
	private String NDEsimmer;
	private String NDEVtihend;
	private String NDEnotes;
	
	//Coupled device data
	private String coupledDeviceName;
	private String coupledDeviceType;
	private String coupledDeviceManufacturer;
	
	private String MPlaager;
	private String MPsimmer;
	private String MPVtihend;
	private String MPnotes;
	private String TPlaager;
	private String TPsimmer;
	private String TPVtihend;
	private String TPnotes;
	
	//Other data
	public boolean hasCoupledDevice = false;
	
	public Device() {
		this.locationKey = "";
		this.id = "";
		this.deviceName = "";
		this.locationName = "";
		this.devicekWrpm = "";
		this.deviceType = "";
		this.deviceManufacturer = "";
		this.DElaager = "";
		this.DEsimmer = "";
		this.DEVtihend = "";
		this.DEnotes = "";
		this.NDElaager = "";
		this.NDEsimmer = "";
		this.NDEVtihend = "";
		this.NDEnotes = "";
		this.coupledDeviceName = "";
		this.coupledDeviceType = "";
		this.coupledDeviceManufacturer = "";
		this.MPlaager = "";
		this.MPsimmer = "";
		this.MPVtihend = "";
		this.MPnotes = "";
		this.TPlaager = "";
		this.TPsimmer = "";
		this.TPVtihend = "";
		this.TPnotes = "";
		hasCoupledDevice = false;
	}
	
	public Device(String locationKey) {
		this.locationKey = locationKey;
		this.id = "";
		this.deviceName = "";
		this.locationName = "";
		this.devicekWrpm = "";
		this.deviceType = "";
		this.deviceManufacturer = "";
		this.DElaager = "";
		this.DEsimmer = "";
		this.DEVtihend = "";
		this.DEnotes = "";
		this.NDElaager = "";
		this.NDEsimmer = "";
		this.NDEVtihend = "";
		this.NDEnotes = "";
		this.coupledDeviceName = "";
		this.coupledDeviceType = "";
		this.coupledDeviceManufacturer = "";
		this.MPlaager = "";
		this.MPsimmer = "";
		this.MPVtihend = "";
		this.MPnotes = "";
		this.TPlaager = "";
		this.TPsimmer = "";
		this.TPVtihend = "";
		this.TPnotes = "";
		hasCoupledDevice = false;
	}

	/**
	 * Constructor for device without coupled device.
	 * @param companyName
	 * @param occupation
	 * @param deviceName
	 * @param devicekWrpm
	 * @param deviceType
	 * @param deviceManufacturer
	 * @param DElaager
	 * @param DEsimmer
	 * @param DEVtihend
	 * @param DEnotes
	 * @param NDElaager
	 * @param NDEsimmer
	 * @param NDEVtihend
	 * @param NDEnotes
	 */
	public Device(String locationKey, String id, String deviceName, String locationName, String devicekWrpm, String deviceType,
			String deviceManufacturer, String DElaager, String DEsimmer, String DEVtihend, String DEnotes,
			String NDElaager, String NDEsimmer, String NDEVtihend, String NDEnotes) {
		this.locationKey = locationKey;
		this.id = id;
		this.deviceName = deviceName;
		this.locationName = locationName;
		this.devicekWrpm = devicekWrpm;
		this.deviceType = deviceType;
		this.deviceManufacturer = deviceManufacturer;
		this.DElaager = DElaager;
		this.DEsimmer = DEsimmer;
		this.DEVtihend = DEVtihend;
		this.DEnotes = DEnotes;
		this.NDElaager = NDElaager;
		this.NDEsimmer = NDEsimmer;
		this.NDEVtihend = NDEVtihend;
		this.NDEnotes = NDEnotes;
	}

	/**
	 * Constructor for device with coupled device.
	 * @param companyName
	 * @param occupation
	 * @param deviceName
	 * @param devicekWrpm
	 * @param deviceType
	 * @param deviceManufacturer
	 * @param DElaager
	 * @param DEsimmer
	 * @param DEVtihend
	 * @param DEnotes
	 * @param NDElaager
	 * @param NDEsimmer
	 * @param NDEVtihend
	 * @param NDEnotes
	 * @param coupledDeviceName
	 * @param coupledDeviceType
	 * @param coupledDeviceManufacturer
	 * @param MPlaager
	 * @param MPsimmer
	 * @param MPVtihend
	 * @param MPnotes
	 * @param TPlaager
	 * @param TPsimmer
	 * @param TPVtihend
	 * @param TPnotes
	 */
	public Device(String locationKey, String id, String deviceName, String locationName, String devicekWrpm, String deviceType,
			String deviceManufacturer, String DElaager, String DEsimmer, String DEVtihend, String DEnotes,
			String NDElaager, String NDEsimmer, String NDEVtihend, String NDEnotes, String coupledDeviceName,
			String coupledDeviceType, String coupledDeviceManufacturer, String MPlaager, String MPsimmer,
			String MPVtihend, String MPnotes, String TPlaager, String TPsimmer, String TPVtihend, String TPnotes) {
		this.locationKey = locationKey;
		this.id = id;
		this.deviceName = deviceName;
		this.locationName = locationName;
		this.devicekWrpm = devicekWrpm;
		this.deviceType = deviceType;
		this.deviceManufacturer = deviceManufacturer;
		this.DElaager = DElaager;
		this.DEsimmer = DEsimmer;
		this.DEVtihend = DEVtihend;
		this.DEnotes = DEnotes;
		this.NDElaager = NDElaager;
		this.NDEsimmer = NDEsimmer;
		this.NDEVtihend = NDEVtihend;
		this.NDEnotes = NDEnotes;
		this.coupledDeviceName = coupledDeviceName;
		this.coupledDeviceType = coupledDeviceType;
		this.coupledDeviceManufacturer = coupledDeviceManufacturer;
		this.MPlaager = MPlaager;
		this.MPsimmer = MPsimmer;
		this.MPVtihend = MPVtihend;
		this.MPnotes = MPnotes;
		this.TPlaager = TPlaager;
		this.TPsimmer = TPsimmer;
		this.TPVtihend = TPVtihend;
		this.TPnotes = TPnotes;
		hasCoupledDevice = true;
	}

	/**
	 * @return the locationKey
	 */
	public String getUnitKey() {
		return locationKey;
	}

	/**
	 * @param locationKey the locationKey to set
	 */
	public void setUnitKey(String locationKey) {
		this.locationKey = locationKey;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return deviceKey;
	}

	/**
	 * @param deviceKey the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getDeviceNickname() {
		return deviceNickname;
	}

	public void setDeviceNickname(String deviceNickname) {
		this.deviceNickname = deviceNickname;
	}

	public String getDevicekWrpm() {
		return devicekWrpm;
	}

	public void setDevicekWrpm(String devicekWrpm) {
		this.devicekWrpm = devicekWrpm;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	public String getDElaager() {
		return DElaager;
	}

	public void setDElaager(String dElaager) {
		DElaager = dElaager;
	}

	public String getDEsimmer() {
		return DEsimmer;
	}

	public void setDEsimmer(String dEsimmer) {
		DEsimmer = dEsimmer;
	}

	public String getDEVtihend() {
		return DEVtihend;
	}

	public void setDEVtihend(String dEVtihend) {
		DEVtihend = dEVtihend;
	}

	public String getDEnotes() {
		return DEnotes;
	}

	public void setDEnotes(String dEnotes) {
		DEnotes = dEnotes;
	}

	public String getNDElaager() {
		return NDElaager;
	}

	public void setNDElaager(String nDElaager) {
		NDElaager = nDElaager;
	}

	public String getNDEsimmer() {
		return NDEsimmer;
	}

	public void setNDEsimmer(String nDEsimmer) {
		NDEsimmer = nDEsimmer;
	}

	public String getNDEVtihend() {
		return NDEVtihend;
	}

	public void setNDEVtihend(String nDEVtihend) {
		NDEVtihend = nDEVtihend;
	}

	public String getNDEnotes() {
		return NDEnotes;
	}

	public void setNDEnotes(String nDEnotes) {
		NDEnotes = nDEnotes;
	}

	public String getCoupledDeviceName() {
		return coupledDeviceName;
	}

	public void setCoupledDeviceName(String coupledDeviceName) {
		this.coupledDeviceName = coupledDeviceName;
		hasCoupledDevice = true;
	}

	public String getCoupledDeviceType() {
		return coupledDeviceType;
	}

	public void setCoupledDeviceType(String coupledDeviceType) {
		this.coupledDeviceType = coupledDeviceType;
		hasCoupledDevice = true;
	}

	public String getCoupledDeviceManufacturer() {
		return coupledDeviceManufacturer;
	}

	public void setCoupledDeviceManufacturer(String coupledDeviceManufacturer) {
		this.coupledDeviceManufacturer = coupledDeviceManufacturer;
		hasCoupledDevice = true;
	}

	public String getMPlaager() {
		return MPlaager;
	}

	public void setMPlaager(String mPlaager) {
		MPlaager = mPlaager;
	}

	public String getMPsimmer() {
		return MPsimmer;
	}

	public void setMPsimmer(String mPsimmer) {
		MPsimmer = mPsimmer;
	}

	public String getMPVtihend() {
		return MPVtihend;
	}

	public void setMPVtihend(String mPVtihend) {
		MPVtihend = mPVtihend;
	}

	public String getMPnotes() {
		return MPnotes;
	}

	public void setMPnotes(String mPnotes) {
		MPnotes = mPnotes;
	}

	public String getTPlaager() {
		return TPlaager;
	}

	public void setTPlaager(String tPlaager) {
		TPlaager = tPlaager;
	}

	public String getTPsimmer() {
		return TPsimmer;
	}

	public void setTPsimmer(String tPsimmer) {
		TPsimmer = tPsimmer;
	}

	public String getTPVtihend() {
		return TPVtihend;
	}

	public void setTPVtihend(String tPVtihend) {
		TPVtihend = tPVtihend;
	}

	public String getTPnotes() {
		return TPnotes;
	}

	public void setTPnotes(String tPnotes) {
		TPnotes = tPnotes;
	}

	public boolean hasCoupledDevice() {
		return hasCoupledDevice;
	}
}
