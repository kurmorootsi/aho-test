package com.elektrimasinad.aho.server;

import java.util.ArrayList;
import java.util.List;


import com.elektrimasinad.aho.client.DeviceTreeService;
import com.elektrimasinad.aho.shared.Company;
import com.elektrimasinad.aho.shared.Device;
import com.elektrimasinad.aho.shared.Measurement;
import com.elektrimasinad.aho.shared.Raport;
import com.elektrimasinad.aho.shared.Unit;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DeviceTreeServiceImpl extends RemoteServiceServlet implements DeviceTreeService {
	private DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	private String userCompanyName = "Elektrimasinad";

	@Override
	public String storeCompany(Company company) throws IllegalArgumentException {
		//Check if company with specified name already exists
		List<Company> companyList = getCompanies();
		for (Company c : companyList) {
			if (c.getCompanyName().equalsIgnoreCase(company.getCompanyName())) {
				return "Company already exists!"; //TODO exception instead?
			}
		}
		
		//Create new company if company does not already exist
		Key userCompanyKey = KeyFactory.createKey("Companies", userCompanyName);
		
		Entity e = new Entity("Company", userCompanyKey);
		e.setProperty("Name", company.getCompanyName());
		
		ds.put(e);
		
		return "Company stored: " + company.getCompanyName();
	}

	@Override
	public List<Company> getCompanies() throws IllegalArgumentException {
		Key userCompanyKey = KeyFactory.createKey("Companies", userCompanyName);
		
		List<Company> companyList = new ArrayList<Company>();
		Query query = new Query("Company", userCompanyKey).setAncestor(userCompanyKey).addSort("Name", Query.SortDirection.ASCENDING);
		for (Entity e : ds.prepare(query).asIterable()) {
			Company c = new Company();
			if (e.getKey() != null && e.getProperty("Name").toString() != null) {
				c.setCompanyKey(KeyFactory.keyToString(e.getKey()));
				c.setCompanyName(e.getProperty("Name").toString());
				companyList.add(c);
			}
		}
		
		return companyList;
	}
	
	@Override
	public String updateCompany(Company updatedCompany) throws IllegalArgumentException {
		Entity e;
		try {
			e = ds.get(KeyFactory.stringToKey(updatedCompany.getCompanyKey()));
			//Check if company name has been changed
			if (!e.getProperty("Name").toString().equalsIgnoreCase(updatedCompany.getCompanyName())) {
				//Check if company with specified name already exists
				List<Company> companyList = getCompanies();
				for (Company c : companyList) {
					if (c.getCompanyName().equalsIgnoreCase(updatedCompany.getCompanyName())) {
						return "Company with that name already exists!"; //TODO exception instead?
					}
				}
			}
			
			//Update company if company does not already exist
			e.setProperty("Name", updatedCompany.getCompanyName());
			ds.put(e);
		} catch (EntityNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "Company not found!";
		}
		return "Company updated!";
	}
	
	@Override
	public String deleteCompany(String companyKeyString) throws IllegalArgumentException {
		Key companyKey = KeyFactory.stringToKey(companyKeyString);
		
		Query q = new Query().setAncestor(companyKey).setKeysOnly();
		
		List<Key> keys = new ArrayList<Key>();
		for (Entity e : ds.prepare(q).asIterable()) {
			keys.add(e.getKey());
		}
		
		ds.delete(keys);
		return "Company deleted!";
	}
	
	@Override
	public String storeUnit(Unit unit) throws IllegalArgumentException {
		//Check if unit with specified name already exists
		List<Unit> unitList = getUnits(unit.getCompanyKey());
		for (Unit c : unitList) {
			if (c.getUnit().equalsIgnoreCase(unit.getUnit())) {
				return "Unit already exists!"; //TODO exception instead?
			}
		}
		
		//Create new unit if unit does not already exist
		Key companyKey = KeyFactory.stringToKey(unit.getCompanyKey());
		Entity e = new Entity("Unit", companyKey);
		e.setProperty("Unit", unit.getUnit());
		
		ds.put(e);
		
		return "Unit stored: " + unit.getUnit();
	}

	@Override
	public List<Unit> getUnits(String companyKeyString) throws IllegalArgumentException {
		Key companyKey = KeyFactory.stringToKey(companyKeyString);
		List<Unit> locationList = new ArrayList<Unit>();
		Query query = new Query("Unit", companyKey).setAncestor(companyKey).addSort("Unit", Query.SortDirection.ASCENDING);
		for (Entity e : ds.prepare(query).asIterable()) {
			Unit c = new Unit();
			if (e.getKey() != null && e.getProperty("Unit").toString() != null) {
				c.setUnitKey(KeyFactory.keyToString(e.getKey()));
				c.setCompanyKey(companyKeyString);
				c.setUnit(e.getProperty("Unit").toString());
				locationList.add(c);
			}
		}
		
		return locationList;
	}

	@Override
	public String updateUnit(Unit updatedUnit) throws IllegalArgumentException {
		Entity e;
		try {
			e = ds.get(KeyFactory.stringToKey(updatedUnit.getUnitKey()));
			//Check if unit name has been changed
			if (!e.getProperty("Unit").toString().equalsIgnoreCase(updatedUnit.getUnit())) {
				//Check if unit with specified name already exists
				List<Unit> unitList = getUnits(updatedUnit.getCompanyKey());
				for (Unit c : unitList) {
					if (c.getUnit().equalsIgnoreCase(updatedUnit.getUnit())) {
						return "Unit with that name already exists!"; //TODO exception instead?
					}
				}
			}
			
			//Update unit if unit does not already exist
			e.setProperty("Unit", updatedUnit.getUnit());
			ds.put(e);
		} catch (EntityNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "Unit not found!";
		}
		return "Unit updated!";
	}

	@Override
	public String deleteUnit(String unitKeyString) throws IllegalArgumentException {
		Key unitKey = KeyFactory.stringToKey(unitKeyString);
		
		Query q = new Query().setAncestor(unitKey).setKeysOnly();
		
		List<Key> keys = new ArrayList<Key>();
		for (Entity e : ds.prepare(q).asIterable()) {
			keys.add(e.getKey());
		}
		
		ds.delete(keys);
		return "Unit deleted!";
	}

	@Override
	public String storeDevice(Device device) throws IllegalArgumentException {
		//Check if device with specified ID already exists
		List<Device> deviceList = getDevices(device.getUnitKey());
		for (Device c : deviceList) {
			if (c.getId().equalsIgnoreCase(device.getId())) {
				return "Device already exists!"; //TODO exception instead?
			}
		}
		
		//Create new device if device does not already exist
		Key locationKey = KeyFactory.stringToKey(device.getUnitKey());
		
		Entity e = new Entity("Device", locationKey);
		e.setProperty("DeviceId", device.getId());
		e.setProperty("DeviceName", device.getDeviceName());
		e.setProperty("Location", device.getLocationName());
		e.setProperty("DeviceNickname", device.getDeviceNickname());
		e.setProperty("kWrpm", device.getDevicekWrpm());
		e.setProperty("DeviceType", device.getDeviceType());
		e.setProperty("DeviceManufacturer", device.getDeviceManufacturer());
		e.setProperty("DElaager", device.getDElaager());
		e.setProperty("DEsimmer", device.getDEsimmer());
		e.setProperty("DEVtihend", device.getDEVtihend());
		e.setProperty("DEnotes", device.getDEnotes());
		e.setProperty("NDElaager", device.getNDElaager());
		e.setProperty("NDEsimmer", device.getNDEsimmer());
		e.setProperty("NDEVtihend", device.getNDEVtihend());
		e.setProperty("NDEnotes", device.getNDEnotes());
			e.setProperty("CoupledDeviceName", device.getCoupledDeviceName());
			e.setProperty("CoupledDeviceType", device.getCoupledDeviceType());
			e.setProperty("CoupledDeviceManufacturer", device.getCoupledDeviceManufacturer());
			e.setProperty("MPlaager", device.getMPlaager());
			e.setProperty("MPsimmer", device.getMPsimmer());
			e.setProperty("MPVtihend", device.getMPVtihend());
			e.setProperty("MPnotes", device.getMPnotes());
			e.setProperty("TPlaager", device.getTPlaager());
			e.setProperty("TPsimmer", device.getTPsimmer());
			e.setProperty("TPVtihend", device.getTPVtihend());
			e.setProperty("TPnotes", device.getTPnotes());
		e.setProperty("HasCoupledDevice", device.hasCoupledDevice());
		
		ds.put(e);
		
		return "Device stored: " + device.getDeviceName();
	}

	@Override
	public List<Device> getDevices(String unitKeyString) throws IllegalArgumentException {
		Key locationKey = KeyFactory.stringToKey(unitKeyString);
		List<Device> deviceList = new ArrayList<Device>();
		//Filter filter = new FilterPredicate("LocationKey", FilterOperator.EQUAL, location.getLocationKey());
		Query query = new Query("Device", locationKey).setAncestor(locationKey).addSort("DeviceId", Query.SortDirection.ASCENDING);
		for (Entity e : ds.prepare(query).asIterable()) {
			Device c = new Device();
			if (e.getKey() != null && e.getProperty("DeviceName").toString() != null) {
				c.setDeviceKey(KeyFactory.keyToString(e.getKey()));
				c.setUnitKey(unitKeyString);
				c.setId(e.getProperty("DeviceId").toString());
				c.setDeviceName(e.getProperty("DeviceName").toString());
				c.setLocationName(e.getProperty("Location").toString());
				c.setDeviceNickname(e.getProperty("DeviceNickname").toString());
				c.setDevicekWrpm(e.getProperty("kWrpm").toString());
				c.setDeviceType(e.getProperty("DeviceType").toString());
				c.setDeviceManufacturer(e.getProperty("DeviceManufacturer").toString());
				c.setDElaager(e.getProperty("DElaager").toString());
				c.setDEsimmer(e.getProperty("DEsimmer").toString());
				c.setDEVtihend(e.getProperty("DEVtihend").toString());
				c.setDEnotes(e.getProperty("DEnotes").toString());
				c.setNDElaager(e.getProperty("NDElaager").toString());
				c.setNDEsimmer(e.getProperty("NDEsimmer").toString());
				c.setNDEVtihend(e.getProperty("NDEVtihend").toString());
				c.setNDEnotes(e.getProperty("NDEnotes").toString());
				if ((boolean) e.getProperty("HasCoupledDevice")) {
					c.setCoupledDeviceName(e.getProperty("CoupledDeviceName").toString());
					c.setCoupledDeviceType(e.getProperty("CoupledDeviceType").toString());
					c.setCoupledDeviceManufacturer(e.getProperty("CoupledDeviceManufacturer").toString());
					c.setMPlaager(e.getProperty("MPlaager").toString());
					c.setMPsimmer(e.getProperty("MPsimmer").toString());
					c.setMPVtihend(e.getProperty("MPVtihend").toString());
					c.setMPnotes(e.getProperty("MPnotes").toString());
					c.setTPlaager(e.getProperty("TPlaager").toString());
					c.setTPsimmer(e.getProperty("TPsimmer").toString());
					c.setTPVtihend(e.getProperty("TPVtihend").toString());
					c.setTPnotes(e.getProperty("TPnotes").toString());
				}
				
				deviceList.add(c);
			}
		}
		
		return deviceList;
	}

	@Override
	public String updateDevice(Device updatedDevice) throws IllegalArgumentException {
		Entity e;
		try {
			e = ds.get(KeyFactory.stringToKey(updatedDevice.getDeviceKey()));
			//Check if device id has been changed
			if (!e.getProperty("DeviceId").toString().equalsIgnoreCase(updatedDevice.getId())) {
				//Check if device with specified id already exists
				List<Device> deviceList = getDevices(updatedDevice.getUnitKey());
				for (Device c : deviceList) {
					if (c.getId().equals(updatedDevice.getId())) {
						return "Device with that ID already exists!"; //TODO exception instead?
					}
				}
			}
			
			//Update device if device does not already exist
			e.setProperty("DeviceId", updatedDevice.getId());
			e.setProperty("DeviceName", updatedDevice.getDeviceName());
			e.setProperty("Location", updatedDevice.getLocationName());
			e.setProperty("DeviceNickname", updatedDevice.getDeviceNickname());
			e.setProperty("kWrpm", updatedDevice.getDevicekWrpm());
			e.setProperty("DeviceType", updatedDevice.getDeviceType());
			e.setProperty("DeviceManufacturer", updatedDevice.getDeviceManufacturer());
			e.setProperty("DElaager", updatedDevice.getDElaager());
			e.setProperty("DEsimmer", updatedDevice.getDEsimmer());
			e.setProperty("DEVtihend", updatedDevice.getDEVtihend());
			e.setProperty("DEnotes", updatedDevice.getDEnotes());
			e.setProperty("NDElaager", updatedDevice.getNDElaager());
			e.setProperty("NDEsimmer", updatedDevice.getNDEsimmer());
			e.setProperty("NDEVtihend", updatedDevice.getNDEVtihend());
			e.setProperty("NDEnotes", updatedDevice.getNDEnotes());
				e.setProperty("CoupledDeviceName", updatedDevice.getCoupledDeviceName());
				e.setProperty("CoupledDeviceType", updatedDevice.getCoupledDeviceType());
				e.setProperty("CoupledDeviceManufacturer", updatedDevice.getCoupledDeviceManufacturer());
				e.setProperty("MPlaager", updatedDevice.getMPlaager());
				e.setProperty("MPsimmer", updatedDevice.getMPsimmer());
				e.setProperty("MPVtihend", updatedDevice.getMPVtihend());
				e.setProperty("MPnotes", updatedDevice.getMPnotes());
				e.setProperty("TPlaager", updatedDevice.getTPlaager());
				e.setProperty("TPsimmer", updatedDevice.getTPsimmer());
				e.setProperty("TPVtihend", updatedDevice.getTPVtihend());
				e.setProperty("TPnotes", updatedDevice.getTPnotes());
			e.setProperty("HasCoupledDevice", updatedDevice.hasCoupledDevice());
			ds.put(e);
		} catch (EntityNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "Device not found!";
		}
		return "Device updated!";
	}

	@Override
	public String deleteDevice(String deviceKeyString) throws IllegalArgumentException {
		Key deviceKey = KeyFactory.stringToKey(deviceKeyString);
		
		/*Query q = new Query().setAncestor(deviceKey).setKeysOnly();
		
		List<Key> keys = new ArrayList<Key>();
		for (Entity e : ds.prepare(q).asIterable()) {
			keys.add(e.getKey());
		}
		
		ds.delete(keys);*/
		
		ds.delete(deviceKey);
		return "Device deleted!";
	}

	@Override
	public String storeMeasurement(Measurement measurement) throws IllegalArgumentException {
		Entity e;
		
		if (measurement.getMeasurementKey().equals("")) {
			//Create new measurement
			Key deviceKey = KeyFactory.stringToKey(measurement.getDeviceKey());
			
			e = new Entity("Measurement", deviceKey);
		} else {
			try {
				e = ds.get(KeyFactory.stringToKey(measurement.getMeasurementKey()));
			} catch (EntityNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//return "Measurement not found!";
				Key deviceKey = KeyFactory.stringToKey(measurement.getDeviceKey());
				
				e = new Entity("Measurement", deviceKey);
			}
		}
		
		e.setProperty("DeviceID", measurement.getDeviceID());
		e.setProperty("DeviceName", measurement.getDeviceName());
		e.setProperty("RaportKey", measurement.getRaportKey());
		e.setProperty("Date", measurement.getDate());
		e.setProperty("Marking", measurement.getMarking());
		//e.setProperty("Comment", measurement.getComment() + " " + measurement.getNDEcomment() + " " + measurement.getDEcomment() + 
		//		" " + measurement.getMPcomment() + " " + measurement.getTPcomment());
		e.setProperty("Comment", measurement.getComment());
		e.setProperty("NDEmms", measurement.getNDEmms());
		e.setProperty("NDEge", measurement.getNDEge());
		e.setProperty("NDEcomment", measurement.getNDEcomment());
		e.setProperty("DEmms", measurement.getDEmms());
		e.setProperty("DEge", measurement.getDEge());
		e.setProperty("DEcomment", measurement.getDEcomment());
		e.setProperty("MPmms", measurement.getMPmms());
		e.setProperty("MPge", measurement.getMPge());
		e.setProperty("MPcomment", measurement.getMPcomment());
		e.setProperty("TPmms", measurement.getTPmms());
		e.setProperty("TPge", measurement.getTPge());
		e.setProperty("TPcomment", measurement.getTPcomment());
		
		ds.put(e);
		
		return "Measurement stored: " + measurement.getDate();
	}

	@Override
	public List<Measurement> getMeasurements(String deviceKeyString) throws IllegalArgumentException {
		Key deviceKey = KeyFactory.stringToKey(deviceKeyString);
		List<Measurement> measurementList = new ArrayList<Measurement>();
		Query query = new Query("Measurement", deviceKey).setAncestor(deviceKey).addSort("Date", Query.SortDirection.DESCENDING);
		for (Entity e : ds.prepare(query).asIterable()) {
			Measurement m = new Measurement();
			m.setDeviceKey(deviceKeyString);
			//if (e.hasProperty("DeviceID")) {
			//	m.setDeviceID(e.getProperty("DeviceID").toString());
			//	m.setDeviceName(e.getProperty("DeviceName").toString());
			//}
			m.setMeasurementKey(KeyFactory.keyToString(e.getKey()));
			m.setRaportKey(e.getProperty("RaportKey").toString());
			m.setDate(e.getProperty("Date").toString());
			m.setComment(e.getProperty("Comment").toString());
			m.setMarking(e.getProperty("Marking").toString());
			m.setNDEmms(e.getProperty("NDEmms").toString());
			m.setNDEge(e.getProperty("NDEge").toString());
			m.setNDEcomment(e.getProperty("NDEcomment").toString());
			m.setDEmms(e.getProperty("DEmms").toString());
			m.setDEge(e.getProperty("DEge").toString());
			m.setDEcomment(e.getProperty("DEcomment").toString());
			m.setMPmms(e.getProperty("MPmms").toString());
			m.setMPge(e.getProperty("MPge").toString());
			m.setMPcomment(e.getProperty("MPcomment").toString());
			m.setTPmms(e.getProperty("TPmms").toString());
			m.setTPge(e.getProperty("TPge").toString());
			m.setTPcomment(e.getProperty("TPcomment").toString());
			measurementList.add(m);
		}
		
		return measurementList;
	}
	
	@Override
	public Measurement getMeasurement(String measurementKeyString) throws IllegalArgumentException {
		Key measurementKey = KeyFactory.stringToKey(measurementKeyString);
		Entity e;
		Measurement m = new Measurement();
		try {
			e = ds.get(measurementKey);
			//if (e.hasProperty("DeviceID")) {
			//	m.setDeviceID(e.getProperty("DeviceID").toString());
			//	m.setDeviceName(e.getProperty("DeviceName").toString());
			//}
			m.setMeasurementKey(KeyFactory.keyToString(e.getKey()));
			m.setRaportKey(e.getProperty("RaportKey").toString());
			m.setDate(e.getProperty("Date").toString());
			m.setComment(e.getProperty("Comment").toString());
			m.setMarking(e.getProperty("Marking").toString());
			m.setNDEmms(e.getProperty("NDEmms").toString());
			m.setNDEge(e.getProperty("NDEge").toString());
			m.setNDEcomment(e.getProperty("NDEcomment").toString());
			m.setDEmms(e.getProperty("DEmms").toString());
			m.setDEge(e.getProperty("DEge").toString());
			m.setDEcomment(e.getProperty("DEcomment").toString());
			m.setMPmms(e.getProperty("MPmms").toString());
			m.setMPge(e.getProperty("MPge").toString());
			m.setMPcomment(e.getProperty("MPcomment").toString());
			m.setTPmms(e.getProperty("TPmms").toString());
			m.setTPge(e.getProperty("TPge").toString());
			m.setTPcomment(e.getProperty("TPcomment").toString());
		} catch (EntityNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		return m;
	}

	@Override
	public Measurement getLastMeasurement(String deviceKeyString) throws IllegalArgumentException {
		Key deviceKey = KeyFactory.stringToKey(deviceKeyString);
		List<Measurement> measurementList = new ArrayList<Measurement>();
		Filter filter = new FilterPredicate("RaportKey", FilterOperator.EQUAL, "");
		Query query = new Query("Measurement", deviceKey).setAncestor(deviceKey).setFilter(filter).addSort("Date", Query.SortDirection.DESCENDING);
		for (Entity e : ds.prepare(query).asIterable()) {
			Measurement m = new Measurement();
			m.setDeviceKey(deviceKeyString);
			//if (e.hasProperty("DeviceID")) {
			//	m.setDeviceID(e.getProperty("DeviceID").toString());
			//	m.setDeviceName(e.getProperty("DeviceName").toString());
			//}
			m.setMeasurementKey(KeyFactory.keyToString(e.getKey()));
			m.setRaportKey(e.getProperty("RaportKey").toString());
			m.setDate(e.getProperty("Date").toString());
			m.setComment(e.getProperty("Comment").toString());
			m.setMarking(e.getProperty("Marking").toString());
			m.setNDEmms(e.getProperty("NDEmms").toString());
			m.setNDEge(e.getProperty("NDEge").toString());
			m.setNDEcomment(e.getProperty("NDEcomment").toString());
			m.setDEmms(e.getProperty("DEmms").toString());
			m.setDEge(e.getProperty("DEge").toString());
			m.setDEcomment(e.getProperty("DEcomment").toString());
			m.setMPmms(e.getProperty("MPmms").toString());
			m.setMPge(e.getProperty("MPge").toString());
			m.setMPcomment(e.getProperty("MPcomment").toString());
			m.setTPmms(e.getProperty("TPmms").toString());
			m.setTPge(e.getProperty("TPge").toString());
			m.setTPcomment(e.getProperty("TPcomment").toString());
			measurementList.add(m);
		}
		if (measurementList.size() > 0) {
			return measurementList.get(0);
		}
		return null;
	}

	@Override
	public String updateMeasurement(Measurement updatedMeasurement) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteMeasurement(String measurementKeyString) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String storeRaport(Raport raport) throws IllegalArgumentException {
		Key unitKey = KeyFactory.stringToKey(raport.getUnitKey());
		Entity e;
		
		if (raport.getRaportKey().equals("")) {
			//Create new measurement
			e = new Entity("Raport", unitKey);
		} else {
			try {
				e = ds.get(KeyFactory.stringToKey(raport.getRaportKey()));
			} catch (EntityNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//return "Measurement not found!";
				
				e = new Entity("Raport", unitKey);
			}
		}
		
		e.setProperty("UnitKey", raport.getUnitKey());
		e.setProperty("CompanyName", raport.getCompanyName());
		e.setProperty("UnitName", raport.getUnitName());
		e.setProperty("RaportID", raport.getRaportID());
		e.setProperty("Date", raport.getDate());
		e.setProperty("MeasurerName", raport.getMeasurerName());
		e.setProperty("MeasurerPhone", raport.getMeasurerPhone());
		
		String raportKey = KeyFactory.keyToString(ds.put(e));
		
		Filter filter = new FilterPredicate("RaportKey", FilterOperator.EQUAL, "");
		Query query = new Query("Measurement", unitKey).setAncestor(unitKey).setFilter(filter);
		List<Entity> measurements = ds.prepare(query).asList(FetchOptions.Builder.withDefaults());
		for (Entity d : measurements) {
			d.setProperty("RaportKey", raportKey);
		}
		ds.put(measurements);
		
		return "Raport stored: " + raport.getRaportID();
	}

	@Override
	public List<Raport> getRaports(String unitKeyString) throws IllegalArgumentException {
		Key unitKey = KeyFactory.stringToKey(unitKeyString);
		List<Raport> raportList = new ArrayList<Raport>();
		//Filter filter = new FilterPredicate("LocationKey", FilterOperator.EQUAL, location.getLocationKey());
		Query query = new Query("Raport", unitKey).setAncestor(unitKey).addSort("RaportID", Query.SortDirection.DESCENDING);
		for (Entity e : ds.prepare(query).asIterable()) {
			Raport c = new Raport();
			if (e.getKey() != null && e.getProperty("RaportID").toString() != null) {
				c.setRaportKey(KeyFactory.keyToString(e.getKey()));
				c.setUnitKey(unitKeyString);
				c.setCompanyName(e.getProperty("CompanyName").toString());
				c.setUnitName(e.getProperty("UnitName").toString());
				c.setRaportID(e.getProperty("RaportID").toString());
				c.setDate(e.getProperty("Date").toString());
				c.setMeasurerName(e.getProperty("MeasurerName").toString());
				c.setMeasurerPhone(e.getProperty("MeasurerPhone").toString());
				
				raportList.add(c);
			}
		}
		
		return raportList;
	}
	
	@Override
	public Raport getRaport(String raportKeyString) throws IllegalArgumentException {
		Key raportKey = KeyFactory.stringToKey(raportKeyString);
		Raport r = new Raport();
		Entity e;
		try {
			e = ds.get(raportKey);
			r.setRaportKey(raportKeyString);
			r.setUnitKey(e.getProperty("UnitKey").toString());
			r.setCompanyName(e.getProperty("CompanyName").toString());
			r.setUnitName(e.getProperty("UnitName").toString());
			r.setRaportID(e.getProperty("RaportID").toString());
			r.setDate(e.getProperty("Date").toString());
			r.setMeasurerName(e.getProperty("MeasurerName").toString());
			r.setMeasurerPhone(e.getProperty("MeasurerPhone").toString());
		} catch (EntityNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return r;
	}

	@Override
	public List<Measurement> getRaportData(Raport raport) throws IllegalArgumentException {
		List<Measurement> measurementList = new ArrayList<Measurement>();
		Filter filter = new FilterPredicate("RaportKey", FilterOperator.EQUAL, raport.getRaportKey());
		Query query;
		Key unitKey = KeyFactory.stringToKey(raport.getUnitKey());
		query = new Query("Measurement").setAncestor(unitKey).setFilter(filter).addSort("DeviceID", Query.SortDirection.ASCENDING);
		for (Entity e : ds.prepare(query).asIterable()) {
			Measurement m = new Measurement();
			try {
				Entity device = ds.get(e.getParent());
				m.setDeviceName(device.getProperty("DeviceName").toString());
				m.setDeviceID(device.getProperty("DeviceId").toString());
			} catch (EntityNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				m.setDeviceName(e.getProperty("DeviceName").toString());
				m.setDeviceID(e.getProperty("DeviceID").toString());
			}
			m.setMeasurementKey(KeyFactory.keyToString(e.getKey()));
			m.setRaportKey(e.getProperty("RaportKey").toString());
			m.setDate(e.getProperty("Date").toString());
			m.setComment(e.getProperty("Comment").toString());
			m.setMarking(e.getProperty("Marking").toString());
			m.setNDEmms(e.getProperty("NDEmms").toString());
			m.setNDEge(e.getProperty("NDEge").toString());
			m.setNDEcomment(e.getProperty("NDEcomment").toString());
			m.setDEmms(e.getProperty("DEmms").toString());
			m.setDEge(e.getProperty("DEge").toString());
			m.setDEcomment(e.getProperty("DEcomment").toString());
			m.setMPmms(e.getProperty("MPmms").toString());
			m.setMPge(e.getProperty("MPge").toString());
			m.setMPcomment(e.getProperty("MPcomment").toString());
			m.setTPmms(e.getProperty("TPmms").toString());
			m.setTPge(e.getProperty("TPge").toString());
			m.setTPcomment(e.getProperty("TPcomment").toString());
			measurementList.add(m);
		}
		return measurementList;
	}
	
}
