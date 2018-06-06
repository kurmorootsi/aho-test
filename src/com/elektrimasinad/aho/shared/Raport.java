package com.elektrimasinad.aho.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Raport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3300463405852540883L;

	private String unitKey = "";
	private String raportKey = "";
	private String companyName = "";
	private String unitName = "";
	private String raportID = "";
	private String date = "";
	private String measurerName = "";
	private String measurerPhone = "";
	private List<Measurement> raportData = new ArrayList<Measurement>();
	
	public Raport() {
		
	}

	public String getUnitKey() {
		return unitKey;
	}

	public void setUnitKey(String unitKey) {
		this.unitKey = unitKey;
	}

	public String getRaportKey() {
		return raportKey;
	}

	public void setRaportKey(String raportKey) {
		this.raportKey = raportKey;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getRaportID() {
		return raportID;
	}

	public void setRaportID(String raportID) {
		this.raportID = raportID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMeasurerName() {
		return measurerName;
	}

	public void setMeasurerName(String measurerName) {
		this.measurerName = measurerName;
	}

	public String getMeasurerPhone() {
		return measurerPhone;
	}

	public void setMeasurerPhone(String measurerPhone) {
		this.measurerPhone = measurerPhone;
	}

	public List<Measurement> getRaportData() {
		return raportData;
	}

	public void setRaportData(List<Measurement> measurementList) {
		this.raportData = measurementList;
	}
}
