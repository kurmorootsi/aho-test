package com.elektrimasinad.aho.shared;

import java.io.Serializable;

public class Measurement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3279821589083129014L;
	
	private String deviceID = "";
	private String deviceName = "";
	private String deviceKey = "";
	private String raportKey = "";
	private String measurementKey = "";
	private String date = "";
	private String marking = "";
	private String comment = "";
	private String NDEmms = "";
	private String NDEge = "";
	private String NDEcomment = "";
	private String DEmms = "";
	private String DEge = "";
	private String DEcomment = "";
	private String MPmms = "";
	private String MPge = "";
	private String MPcomment = "";
	private String TPmms = "";
	private String TPge = "";
	private String TPcomment = "";

	public Measurement() {
		
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getRaportKey() {
		return raportKey;
	}

	public void setRaportKey(String raportKey) {
		this.raportKey = raportKey;
	}

	public String getMeasurementKey() {
		return measurementKey;
	}

	public void setMeasurementKey(String measurementKey) {
		this.measurementKey = measurementKey;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMarking() {
		return marking;
	}

	public void setMarking(String marking) {
		this.marking = marking;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getNDEmms() {
		return NDEmms;
	}

	public void setNDEmms(String nDEmms) {
		NDEmms = nDEmms;
	}

	public String getNDEge() {
		return NDEge;
	}

	public void setNDEge(String nDEge) {
		NDEge = nDEge;
	}

	public String getNDEcomment() {
		return NDEcomment;
	}

	public void setNDEcomment(String nDEcomment) {
		NDEcomment = nDEcomment;
	}

	public String getDEmms() {
		return DEmms;
	}

	public void setDEmms(String dEmms) {
		DEmms = dEmms;
	}

	public String getDEge() {
		return DEge;
	}

	public void setDEge(String dEge) {
		DEge = dEge;
	}

	public String getDEcomment() {
		return DEcomment;
	}

	public void setDEcomment(String dEcomment) {
		DEcomment = dEcomment;
	}

	public String getMPmms() {
		return MPmms;
	}

	public void setMPmms(String mPmms) {
		MPmms = mPmms;
	}

	public String getMPge() {
		return MPge;
	}

	public void setMPge(String mPge) {
		MPge = mPge;
	}

	public String getMPcomment() {
		return MPcomment;
	}

	public void setMPcomment(String mPcomment) {
		MPcomment = mPcomment;
	}

	public String getTPmms() {
		return TPmms;
	}

	public void setTPmms(String tPmms) {
		TPmms = tPmms;
	}

	public String getTPge() {
		return TPge;
	}

	public void setTPge(String tPge) {
		TPge = tPge;
	}

	public String getTPcomment() {
		return TPcomment;
	}

	public void setTPcomment(String tPcomment) {
		TPcomment = tPcomment;
	}
	
	

}
