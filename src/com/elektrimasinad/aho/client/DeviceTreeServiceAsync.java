package com.elektrimasinad.aho.client;

import java.util.List;
import com.elektrimasinad.aho.shared.Company;
import com.elektrimasinad.aho.shared.Device;
import com.elektrimasinad.aho.shared.Measurement;
import com.elektrimasinad.aho.shared.Raport;
import com.elektrimasinad.aho.shared.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>DeviceTreeService</code>.
 */
public interface DeviceTreeServiceAsync {
	
	void storeCompany(Company company, AsyncCallback<String> callback) throws IllegalArgumentException;
	void getCompanies(AsyncCallback<List<Company>> callback) throws IllegalArgumentException;
	void updateCompany(Company updatedCompany, AsyncCallback<String> updateCompanyCallback) throws IllegalArgumentException;
	void deleteCompany(String companyKeyString, AsyncCallback<String> removeCompanyCallback) throws IllegalArgumentException;
	
	void storeUnit(Unit unit, AsyncCallback<String> callback) throws IllegalArgumentException;
	void getUnits(String companyKeyString, AsyncCallback<List<Unit>> callback) throws IllegalArgumentException;
	void updateUnit(Unit updatedUnit, AsyncCallback<String> updateUnitCallback) throws IllegalArgumentException;
	void deleteUnit(String unitKeyString, AsyncCallback<String> removeUnitCallback) throws IllegalArgumentException;
	
	void storeDevice(Device device, AsyncCallback<String> callback) throws IllegalArgumentException;
	void getDevices(String unitKeyString, AsyncCallback<List<Device>> callback) throws IllegalArgumentException;
	void updateDevice(Device updatedDevice, AsyncCallback<String> updateDeviceCallback) throws IllegalArgumentException;
	void deleteDevice(String deviceKeyString, AsyncCallback<String> removeDeviceCallback) throws IllegalArgumentException;
	
	void storeMeasurement(Measurement measurement, AsyncCallback<String> callback) throws IllegalArgumentException;
	void getMeasurements(String deviceKeyString, AsyncCallback<List<Measurement>> callback) throws IllegalArgumentException;
	void getMeasurement(String measurementKeyString, AsyncCallback<Measurement> callback) throws IllegalArgumentException;
	void getLastMeasurement(String deviceKeyString, AsyncCallback<Measurement> callback) throws IllegalArgumentException;
	void updateMeasurement(Measurement updatedMeasurement, AsyncCallback<String> updateMeasurementCallback) throws IllegalArgumentException;
	void deleteMeasurement(String measurementKeyString, AsyncCallback<String> removeMeasurementCallback) throws IllegalArgumentException;
	
	void storeRaport(Raport raport, AsyncCallback<String> callback) throws IllegalArgumentException;
	void getRaports(String unitKey, AsyncCallback<List<Raport>> callback) throws IllegalArgumentException;
	void getRaport(String raportKeyString, AsyncCallback<Raport> callback) throws IllegalArgumentException;
	void getRaportData(Raport raport, AsyncCallback<List<Measurement>> callback) throws IllegalArgumentException;
}
