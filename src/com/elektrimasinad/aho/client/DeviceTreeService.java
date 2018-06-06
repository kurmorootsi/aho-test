package com.elektrimasinad.aho.client;

import java.util.List;
import com.elektrimasinad.aho.shared.Company;
import com.elektrimasinad.aho.shared.Device;
import com.elektrimasinad.aho.shared.Measurement;
import com.elektrimasinad.aho.shared.Raport;
import com.elektrimasinad.aho.shared.Unit;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("deviceTree")
public interface DeviceTreeService extends RemoteService {
	
	String storeCompany(Company company) throws IllegalArgumentException;
	List<Company> getCompanies() throws IllegalArgumentException;
	String updateCompany(Company updatedCompany) throws IllegalArgumentException;
	String deleteCompany(String companyKeyString) throws IllegalArgumentException;
	
	String storeUnit(Unit unit) throws IllegalArgumentException;
	List<Unit> getUnits(String companyKeyString) throws IllegalArgumentException;
	String updateUnit(Unit updatedUnit) throws IllegalArgumentException;
	String deleteUnit(String unitKeyString) throws IllegalArgumentException;
	
	String storeDevice(Device device) throws IllegalArgumentException;
	List<Device> getDevices(String unitKeyString) throws IllegalArgumentException;
	String updateDevice(Device updatedDevice) throws IllegalArgumentException;
	String deleteDevice(String deviceKeyString) throws IllegalArgumentException;
	
	String storeMeasurement(Measurement measurement) throws IllegalArgumentException;
	List<Measurement> getMeasurements(String deviceKeyString) throws IllegalArgumentException;
	Measurement getMeasurement(String measurementKeyString) throws IllegalArgumentException;
	Measurement getLastMeasurement(String deviceKeyString) throws IllegalArgumentException;
	String updateMeasurement(Measurement updatedMeasurement) throws IllegalArgumentException;
	String deleteMeasurement(String measurementKeyString) throws IllegalArgumentException;
	
	String storeRaport(Raport raport) throws IllegalArgumentException;
	List<Raport> getRaports(String unitKey) throws IllegalArgumentException;
	Raport getRaport(String raportKeyString) throws IllegalArgumentException;
	List<Measurement> getRaportData(Raport raport) throws IllegalArgumentException;
}
