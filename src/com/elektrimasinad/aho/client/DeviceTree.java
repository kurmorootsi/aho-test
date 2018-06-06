package com.elektrimasinad.aho.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.elektrimasinad.aho.shared.Company;
import com.elektrimasinad.aho.shared.Device;
import com.elektrimasinad.aho.shared.Measurement;
import com.elektrimasinad.aho.shared.Unit;
import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class DeviceTree extends Tree {
	private DeviceTreeServiceAsync deviceTreeService;
	
	private AsyncCallback<List<Company>> getCompanyListCallback;
	private AsyncCallback<List<Unit>> getUnitListCallback;
	private AsyncCallback<List<Device>> getDeviceListCallback;
	
	//private List<Company> companyList = new ArrayList<Company>();
	//private HashMap<Key, Unit> unitMap = new HashMap<Key, Unit>();
	//private HashMap<Key, Device> deviceMap = new HashMap<Key, Device>();
	
	public DeviceTree(DeviceTreeServiceAsync deviceTreeService) {
		super();
		this.deviceTreeService = deviceTreeService;
		getCompanyListCallback = new AsyncCallback<List<Company>>() {
			
			@Override
			public void onSuccess(List<Company> companies) {
				addCompaniesToTree(companies);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
		getUnitListCallback = new AsyncCallback<List<Unit>>() {
			
			@Override
			public void onSuccess(List<Unit> units) {
				addUnitsToTree(units);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};

		getDeviceListCallback = new AsyncCallback<List<Device>>() {
			
			@Override
			public void onSuccess(List<Device> devices) {
				addDevicesToTree(devices);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
		getElement().addClassName("gwt-Tree");
		refreshTree();
	}
	
	public void refreshTree() {
		clear();
		fetchCompanies();
		
		
	}
	
	private void fetchCompanies() {
		deviceTreeService.getCompanies(getCompanyListCallback);
	}
	public void fetchUnits(Company company) {
		deviceTreeService.getUnits(company.getCompanyKey(), getUnitListCallback);
	}
	
	public void fetchDevices(Unit unit) {
		deviceTreeService.getDevices(unit.getUnitKey(), getDeviceListCallback);
	}
	
	private void addCompaniesToTree(List<Company> companies) {
		for (Company company : companies) {
			TreeItem companyItem = new TreeItem();
			companyItem.getElement().addClassName("gwt-TreeNode");
			companyItem.setText(company.getCompanyName());
			companyItem.setUserObject(company);
			this.addItem(companyItem);
			
		}
	}
	
	private void addUnitsToTree(List<Unit> units) {
		TreeItem selItem = null;
		for (Unit unit : units) {
			for (int i = 0; i < getItemCount(); i++) {
				if (getItem(i).getUserObject() instanceof Company) {
					if (((Company)getItem(i).getUserObject()).getCompanyKey().equals(unit.getCompanyKey())) {
						TreeItem unitItem = new TreeItem();
						unitItem.getElement().addClassName("gwt-TreeNode");
						unitItem.setText(unit.getUnit());
						unitItem.setUserObject(unit);
						getItem(i).addItem(unitItem);
						
						selItem = getItem(i);
					}
				}
			}
		}
		openSelectedItem(selItem);
	}
	
	private void addDevicesToTree(List<Device> devices) {
		TreeItem selItem = null;
		for (Device device : devices) {
			for (int i = 0; i < getItemCount(); i++) {
				for (int j = 0; j < getItem(i).getChildCount(); j++) {
					if (((Unit)getItem(i).getChild(j).getUserObject()).getUnitKey().equals(device.getUnitKey())) {
						TreeItem deviceItem = new TreeItem();
						deviceItem.setText(device.getId() + " " + device.getDeviceName());
						deviceItem.setUserObject(device);
						styleDeviceItem(deviceItem);
						getItem(i).getChild(j).addItem(deviceItem);
						
						selItem = getItem(i).getChild(j);
					}
				}
			}
		}
		openSelectedItem(selItem);
	}
	
	private void styleDeviceItem(final TreeItem item) {
		deviceTreeService.getLastMeasurement(((Device)item.getUserObject()).getDeviceKey(), new AsyncCallback<Measurement>() {
			
			@Override
			public void onSuccess(Measurement lastMeasurement) {
				//System.out.println(name);
				if (lastMeasurement != null) {
					if (lastMeasurement.getMarking().equalsIgnoreCase("alarm")) item.getElement().addClassName("gwt-TreeNode2A");
					else if (lastMeasurement.getMarking().equalsIgnoreCase("hoiatus")) item.getElement().addClassName("gwt-TreeNode2H");
					else if (lastMeasurement.getMarking().equalsIgnoreCase("ok")) item.getElement().addClassName("gwt-TreeNode2O");
					else item.getElement().addClassName("gwt-TreeNode2M");
				} else {
					item.getElement().addClassName("gwt-TreeNode2");
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		});
	}
	
	private void openSelectedItem(TreeItem selItem) {
		if (selItem != null) {
			boolean state = selItem.getState();
		    TreeItem parent = selItem.getParentItem();
		    selItem.getTree().setSelectedItem(parent, false); // null is ok
		    if(parent != null)
		        parent.setSelected(false);  // not compulsory
		    selItem.setState(!state, false);
		}
	}
	
	/*public TreeItem addDeviceItem(String company, String location, String device) {
		addCompany(company).addLocation(location).addDevice(device);
		TreeItem item = getDeviceItem(company, location, device);
		if (item == null) {
			item = addLocationItem(company, location).addTextItem(device);
			item.getElement().addClassName("gwt-TreeNode2");
			item.setUserObject("device");
		}
		return item;
		
	}
	public TreeItem addLocationItem(String company, String location) {
		addCompany(company).addLocation(location);
		TreeItem item = getLocationItem(company, location);
		if (item == null) {
			item = addCompanyItem(company).addTextItem(location);
			item.getElement().addClassName("gwt-TreeNode");
		}
		return item;
	}
	public TreeItem addCompanyItem(String company) {
		addCompany(company);
		TreeItem item = getCompanyItem(company);
		if (item == null) {
			item = addTextItem(company);
			item.getElement().addClassName("gwt-TreeNode");
		}
		return item;
	}
	
	public TreeItem getDeviceItem(String company, String location, String device) {
		TreeItem locationItem = getLocationItem(company, location);
		if (locationItem != null) {
			for (int k = 0; k < locationItem.getChildCount(); k++) {
				if (locationItem.getChild(k).getText() == device) {
					return locationItem.getChild(k);
				}
			}
		}
		return null;
	}
	public TreeItem getLocationItem(String company, String location) {
		TreeItem companyItem = getCompanyItem(company);
		if (companyItem != null) {
			for (int j = 0; j < companyItem.getChildCount(); j++) {
				if (companyItem.getChild(j).getText() == location) {
					return companyItem.getChild(j);
				}
			}
		}
		return null;
	}
	public TreeItem getCompanyItem(String company) {
		for (int i = 0; i < getItemCount(); i ++) {
			if (getItem(i).getText() == company) {
				return getItem(i);
			}
		}
		return null;
	}*/
}
