package com.elektrimasinad.aho.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.elektrimasinad.aho.shared.Company;
import com.elektrimasinad.aho.shared.Device;
import com.elektrimasinad.aho.shared.Measurement;
import com.elektrimasinad.aho.shared.Raport;
import com.elektrimasinad.aho.shared.Unit;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DeviceCard implements EntryPoint {
	
	private static final DeviceTreeServiceAsync deviceTreeService = GWT.create(DeviceTreeService.class);
	private AsyncCallback<List<Company>> getCompanyListCallback;
	private AsyncCallback<List<Unit>> getUnitListCallback;
	private AsyncCallback<List<Device>> getDeviceListCallback;
	private AsyncCallback<String> storeCompanyCallback;
	private AsyncCallback<String> storeUnitCallback;
	private AsyncCallback<String> storeDeviceCallback;
	private AsyncCallback<Measurement> getLastMeasurementCallback;
	private AsyncCallback<String> storeMeasurementCallback;
	
	private int MAIN_WIDTH = 900;
	private int CONTENT_WIDTH = 800;
	private List<Company> companyList = new ArrayList<Company>();
	private List<Unit> unitList = new ArrayList<Unit>();
	private List<Device> deviceList = new ArrayList<Device>();
	
	private VerticalPanel mainPanel;
	private DeviceCardPanel deviceCardPanel = new DeviceCardPanel();;
	private VerticalPanel lastMeasurementPanel = new VerticalPanel();
	private AbsolutePanel headerPanel;
	private DeckPanel contentPanel;
	//private Device device;
	private String selectedDeviceName;
	private Company selectedCompany;
	private Unit selectedUnit;
	private Device selectedDevice;
	private Measurement measurement;
	
	private VerticalPanel companyListPanel = new VerticalPanel();
	private CompanyPanel newCompanyPanel = new CompanyPanel();
	private VerticalPanel unitListPanel = new VerticalPanel();
	private VerticalPanel deviceListPanel = new VerticalPanel();
	private UnitPanel newUnitPanel = new UnitPanel();
	private Panel measurementListPanel = new VerticalPanel();
	private AsyncCallback<List<Measurement>> getMeasurementsCallback;
	protected List<Measurement> measurements;
	

	@Override
	public void onModuleLoad() {
		getCompanyListCallback = new AsyncCallback<List<Company>>() {
			
			@Override
			public void onSuccess(List<Company> companies) {
				//System.out.println(name);
				companyList = companies;
				createCompanyListPanel();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
		getUnitListCallback = new AsyncCallback<List<Unit>>() {
			
			@Override
			public void onSuccess(List<Unit> locations) {
				//System.out.println(name);
				unitList = locations;
				createUnitListPanel();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};

		getDeviceListCallback = new AsyncCallback<List<Device>>() {
			
			@Override
			public void onSuccess(List<Device> devices) {
				//System.out.println(name);
				deviceList = devices;
				createDeviceListPanel();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
		storeCompanyCallback = new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String name) {
				System.out.println(name);
				fetchCompanies();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
		storeUnitCallback = new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String name) {
				System.out.println(name);
				fetchUnits();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
		storeDeviceCallback = new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String name) {
				System.out.println(name);
				fetchDevices();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
		getLastMeasurementCallback = new AsyncCallback<Measurement>() {
			
			@Override
			public void onSuccess(Measurement lastMeasurement) {
				measurement = lastMeasurement;
				createDeviceMeasurementsPanel();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
		storeMeasurementCallback = new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String name) {
				System.out.println(name);
				contentPanel.showWidget(contentPanel.getWidgetIndex(measurementListPanel));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
		getMeasurementsCallback = new AsyncCallback<List<Measurement>>() {
			
			@Override
			public void onSuccess(List<Measurement> measurementList) {
				//System.out.println(name);
				if (measurementList != null) {
					measurements = measurementList;
				}
				createMeasurementListPanel();
				contentPanel.showWidget(contentPanel.getWidgetIndex(measurementListPanel));
				updateWidgetSizes();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
		
		RootPanel root = RootPanel.get();
		root.setStyleName("mainBackground2");
		
		mainPanel = new VerticalPanel();
		mainPanel.setStyleName("panelBackground");
		
		headerPanel = new AbsolutePanel();
		headerPanel.setStyleName("headerBackground");
		mainPanel.add(headerPanel);
		
		contentPanel = new DeckPanel();
		mainPanel.add(contentPanel);
		mainPanel.setCellHeight(contentPanel, "100%");
		
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setStyleName("mainBackground2");
		rootPanel.add(mainPanel);
		
		init();
		updateWidgetSizes();
	}
	
	private void updateWidgetSizes() {
		mainPanel.setWidth(MAIN_WIDTH + "px");
		mainPanel.setHeight(Window.getClientHeight() + "px");
		contentPanel.setWidth(CONTENT_WIDTH + "px");
	}

	private void init() {
		//generateDemoData();
		fetchCompanies();
		contentPanel.add(companyListPanel);
		contentPanel.add(unitListPanel);
		contentPanel.add(deviceListPanel);
		contentPanel.add(newCompanyPanel);
		contentPanel.add(newUnitPanel);
		contentPanel.add(measurementListPanel);
		contentPanel.add(lastMeasurementPanel);
		contentPanel.add(deviceCardPanel);
		
		//devTree = new DeviceTree();
		//generateDemoTreeData();
		//device = new Device("Tartu Graanul", "Graanuli tootmine", "Tehas1", "Haamerveski Champion", "110/1500", "1LE1503", "SIEMENS",
		//		"6319/C3", "", "", "", "6319/C3", "", "", "", "Haamerveski", "", "", "22322", "", "", "", "22322", "", "", "");
		//createDeviceTreePanel();
		
		//contentPanel.add(deviceTreePanel);
		//contentPanel.showWidget(contentPanel.getWidgetIndex(deviceTreePanel));
		mainPanel.setCellHorizontalAlignment(contentPanel, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	private void fetchCompanies() {
		deviceTreeService.getCompanies(getCompanyListCallback);
	}
	
	private void fetchUnits() {
		deviceTreeService.getUnits(selectedCompany.getCompanyKey(), getUnitListCallback);
	}
	
	private void fetchDevices() {
		deviceTreeService.getDevices(selectedUnit.getUnitKey(), getDeviceListCallback);
	}

	/*private void generateDemoData() {
		//XXX demo data generator
		
		Company c1 = new Company("Firma1");
		Location c1l1 = new Location("Pumbajaam1");
		Device c1l1d1 = new Device("Pump1", "110/1500", "1LE1503", "SIEMENS",
				"6319/C3", "", "", "", "6319/C3", "", "", "", "Pump", "", "", "22322", "", "", "", "22322", "", "", "");
		Device c1l1d2 = new Device("Pump2", "110/1500", "1LE1503", "SIEMENS",
				"6319/C3", "", "", "", "6319/C3", "", "", "", "Pump", "", "", "22322", "", "", "", "22322", "", "", "");
		Device c1l1d3 = new Device("Pump3", "110/1500", "1LE1503", "SIEMENS",
				"6319/C3", "", "", "", "6319/C3", "", "", "", "Pump", "", "", "22322", "", "", "", "22322", "", "", "");
		c1l1.addDevice(c1l1d1);
		c1l1.addDevice(c1l1d2);
		c1l1.addDevice(c1l1d3);
		c1.addLocation(c1l1);
		Location c1l2 = new Location("Pumbajaam2");
		Device c1l2d1 = new Device("Pump4", "110/1500", "1LE1503", "SIEMENS",
				"6319/C3", "", "", "", "6319/C3", "", "", "", "Pump", "", "", "22322", "", "", "", "22322", "", "", "");
		Device c1l2d2 = new Device("Pump5", "110/1500", "1LE1503", "SIEMENS",
				"6319/C3", "", "", "", "6319/C3", "", "", "", "Pump", "", "", "22322", "", "", "", "22322", "", "", "");
		Device c1l2d3 = new Device("Pump6", "110/1500", "1LE1503", "SIEMENS",
				"6319/C3", "", "", "", "6319/C3", "", "", "", "Pump", "", "", "22322", "", "", "", "22322", "", "", "");
		c1l2.addDevice(c1l2d1);
		c1l2.addDevice(c1l2d2);
		c1l2.addDevice(c1l2d3);
		c1.addLocation(c1l2);
		
		Company c2 = new Company("Firma2");
		Location c2l1 = new Location("Saeveski");
		c2.addLocation(c2l1);
		
		Company c3 = new Company("Firma3");
		Location c3l1 = new Location("Tootmishoone");
		c3.addLocation(c3l1);
		
		companyList.add(c1);
		companyList.add(c2);
		companyList.add(c3);
	}*/
	
	private VerticalPanel createCompanyListPanel() {
		companyListPanel.clear();
		companyListPanel.setWidth("100%");
		
		//Buttons panel
		Label lLabel1 = new Label("");
		lLabel1.setStyleName("backSaveLabel noPointer");
		
		Label lNewCompany = new Label("Lisa ettev\u00F5te");
		lNewCompany.setStyleName("backSaveLabel wide");
		lNewCompany.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				createNewCompanyView();
			}
			
		});
		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.setStyleName("backSavePanel");
		buttonsPanel.add(lLabel1);
		buttonsPanel.setCellWidth(lLabel1, "50%");
		buttonsPanel.add(lNewCompany);
		buttonsPanel.setCellWidth(lNewCompany, "50%");
		buttonsPanel.setCellHorizontalAlignment(lNewCompany, HasHorizontalAlignment.ALIGN_RIGHT);
		companyListPanel.add(buttonsPanel);
		
		//Header Panel
		HorizontalPanel headerPanel = AhoWidgets.createThinContentHeader("Ettev\u00F5tted");
		companyListPanel.add(headerPanel);
		
		//Companies list
		for (final Company company : companyList) {
			Label lCompany = new Label(company.getCompanyName());
			lCompany.setStyleName("aho-listItem");
			lCompany.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					//createLocationListPanel(((Label)(event.getSource())).getText());
					selectedCompany = companyList.get(companyList.indexOf(company));
					fetchUnits();
				}
				
			});
			companyListPanel.add(lCompany);
		}
		
		contentPanel.showWidget(contentPanel.getWidgetIndex(companyListPanel));
		
		return companyListPanel;
	}
	
	private VerticalPanel createUnitListPanel() {
		unitListPanel.clear();
		
		//Buttons panel
		final Label lBack = new Label("Tagasi");
		lBack.setStyleName("backSaveLabel");
		lBack.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				contentPanel.showWidget(contentPanel.getWidgetIndex(companyListPanel));
			}
			
		});
		Button lBackButton = new Button();
		lBackButton.setStyleName("backButton");
		lBackButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lBack.fireEvent(event);
			}
			
		});
		Label lDelete = new Label("Kustuta");
		lDelete.setStyleName("backSaveLabel");
		lDelete.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				deviceTreeService.deleteCompany(selectedCompany.getCompanyKey(), storeCompanyCallback);
			}
			
		});
		
		Label lEdit = new Label("Andmed");
		lEdit.setStyleName("backSaveLabel");
		lEdit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				createEditCompanyView();
			}
			
		});
		Label lNewLocation = new Label("Lisa \u00FCksus");
		lNewLocation.setStyleName("backSaveLabel wide");
		lNewLocation.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				createNewLocationView();
			}
			
		});
		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.setStyleName("backSavePanel");
		buttonsPanel.add(lBackButton);
		buttonsPanel.add(lBack);
		buttonsPanel.setCellWidth(lBackButton, "7%");
		buttonsPanel.setCellWidth(lBack, "43%");
		buttonsPanel.add(lEdit);
		buttonsPanel.setCellWidth(lEdit, "15%");
		buttonsPanel.add(lDelete);
		buttonsPanel.setCellWidth(lDelete, "15%");
		buttonsPanel.add(lNewLocation);
		buttonsPanel.setCellHorizontalAlignment(lNewLocation, HasHorizontalAlignment.ALIGN_RIGHT);
		buttonsPanel.setCellWidth(lNewLocation, "20%");
		unitListPanel.add(buttonsPanel);
		
		//Header Panel
		HorizontalPanel headerPanel = AhoWidgets.createThinContentHeader(selectedCompany.getCompanyName());
		unitListPanel.add(headerPanel);
		
		//Locations list
		for (final Unit location : unitList) {
			Label lLocation = new Label(location.getUnit());
			lLocation.setStyleName("aho-listItem");
			lLocation.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					selectedUnit = unitList.get(unitList.indexOf(location));
					fetchDevices();
				}
				
			});
			unitListPanel.add(lLocation);
		}
		
		contentPanel.showWidget(contentPanel.getWidgetIndex(unitListPanel));
		
		return unitListPanel;
	}

	private VerticalPanel createDeviceListPanel() {
		deviceListPanel.clear();
		
		//Buttons panel
		final Label lBack = new Label("Tagasi");
		lBack.setStyleName("backSaveLabel");
		lBack.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				contentPanel.showWidget(contentPanel.getWidgetIndex(unitListPanel));
			}
			
		});
		Button lBackButton = new Button();
		lBackButton.setStyleName("backButton");
		lBackButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lBack.fireEvent(event);
			}
			
		});
		Label lDelete = new Label("Kustuta");
		lDelete.setStyleName("backSaveLabel");
		lDelete.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				deviceTreeService.deleteUnit(selectedUnit.getUnitKey(), storeUnitCallback);
			}
			
		});
		
		Label lEdit = new Label("Andmed");
		lEdit.setStyleName("backSaveLabel");
		lEdit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				createEditLocationView();
			}
			
		});
		Label lNewDevice = new Label("Lisa seade");
		lNewDevice.setStyleName("backSaveLabel wide");
		lNewDevice.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showAddDeviceView();
			}
			
		});
		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.setStyleName("backSavePanel");
		buttonsPanel.add(lBackButton);
		buttonsPanel.add(lBack);
		buttonsPanel.setCellWidth(lBackButton, "7%");
		buttonsPanel.setCellWidth(lBack, "43%");
		buttonsPanel.add(lEdit);
		buttonsPanel.setCellWidth(lEdit, "15%");
		buttonsPanel.add(lDelete);
		buttonsPanel.setCellWidth(lDelete, "15%");
		buttonsPanel.add(lNewDevice);
		buttonsPanel.setCellHorizontalAlignment(lNewDevice, HasHorizontalAlignment.ALIGN_RIGHT);
		buttonsPanel.setCellWidth(lNewDevice, "20%");
		deviceListPanel.add(buttonsPanel);
		
		//Header Panel
		HorizontalPanel headerPanel = AhoWidgets.createThinContentHeader(selectedUnit.getUnit());
		deviceListPanel.add(headerPanel);
		
		//Device list
		for (final Device device : deviceList) {
			Label lDevice = new Label(device.getId() + " " + device.getDeviceName());
			lDevice.setStyleName("aho-listItem");
			lDevice.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					selectedDevice = deviceList.get(deviceList.indexOf(device));
					showDeviceCardView(selectedDevice.getDeviceName());
				}
				
			});
			deviceListPanel.add(lDevice);
		}
		
		contentPanel.showWidget(contentPanel.getWidgetIndex(deviceListPanel));
		
		return deviceListPanel;
	}
	
	private void createMeasurementListPanel() {
		measurementListPanel.clear();
		measurementListPanel.setWidth("100%");
		
		Label lBack = new Label("Tagasi");
		lBack.setStyleName("backSaveLabel");
		final Button lBackButton = new Button();
		lBackButton.setStyleName("backButton");
		lBackButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				contentPanel.showWidget(contentPanel.getWidgetIndex(deviceCardPanel));
			}
			
		});
		lBack.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lBackButton.fireEvent(event);
			}
			
		});
		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.setStyleName("backSavePanel");
		buttonsPanel.add(lBackButton);
		buttonsPanel.add(lBack);
		buttonsPanel.setCellWidth(lBackButton, "7%");
		buttonsPanel.setCellWidth(lBack, "93%");
		measurementListPanel.add(buttonsPanel);
		
		//Header Panel
		HorizontalPanel headerPanel = AhoWidgets.createThinContentHeader(selectedDevice.getId() + " " + selectedDevice.getDeviceName());
		measurementListPanel.add(headerPanel);
		
		//Measurements list
		for (final Measurement measurement : measurements) {
			Label lMeasurement = new Label(measurement.getDate());
			lMeasurement.setStyleName("aho-listItem");
			lMeasurement.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					deviceTreeService.getMeasurement(measurement.getMeasurementKey(), getLastMeasurementCallback);
				}
				
			});
			measurementListPanel.add(lMeasurement);
		}
		
		contentPanel.showWidget(contentPanel.getWidgetIndex(measurementListPanel));
	}

	private CompanyPanel createNewCompanyView() {
		newCompanyPanel.clear();
		
		//Buttons panel
		final Label lBack = new Label("T\u00FChista");
		lBack.setStyleName("backSaveLabel");
		lBack.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				contentPanel.showWidget(contentPanel.getWidgetIndex(companyListPanel));
			}
			
		});
		Button lBackButton = new Button();
		lBackButton.setStyleName("backButton");
		lBackButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lBack.fireEvent(event);
			}
			
		});
		
		Label lSave = new Label("Salvesta");
		lSave.setStyleName("backSaveLabel wide");
		lSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				newCompanyPanel.saveCompany(companyList, storeCompanyCallback);
			}

			
			
		});
		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.setStyleName("backSavePanel");
		buttonsPanel.add(lBackButton);
		buttonsPanel.add(lBack);
		buttonsPanel.setCellWidth(lBackButton, "7%");
		buttonsPanel.setCellWidth(lBack, "43%");
		buttonsPanel.add(lSave);
		buttonsPanel.setCellHorizontalAlignment(lSave, HasHorizontalAlignment.ALIGN_RIGHT);
		buttonsPanel.setCellWidth(lSave, "50%");
		newCompanyPanel.insert(buttonsPanel, 0);
		
		contentPanel.showWidget(contentPanel.getWidgetIndex(newCompanyPanel));
		
		return newCompanyPanel;
	}
	
	private void createEditCompanyView() {
		createNewCompanyView();
		newCompanyPanel.createEditCompanyPanel(selectedCompany);
	}

	private UnitPanel createNewLocationView() {
		newUnitPanel.clear(selectedCompany);
		
		//Buttons panel
		final Label lBack = new Label("T\u00FChista");
		lBack.setStyleName("backSaveLabel");
		lBack.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				contentPanel.showWidget(contentPanel.getWidgetIndex(unitListPanel));
			}
			
		});
		Button lBackButton = new Button();
		lBackButton.setStyleName("backButton");
		lBackButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lBack.fireEvent(event);
			}
			
		});
		
		Label lSave = new Label("Salvesta");
		lSave.setStyleName("backSaveLabel wide");
		lSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				newUnitPanel.saveUnit(unitList, storeUnitCallback);
			}
			
		});
		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.setStyleName("backSavePanel");
		buttonsPanel.add(lBackButton);
		buttonsPanel.add(lBack);
		buttonsPanel.setCellWidth(lBackButton, "7%");
		buttonsPanel.setCellWidth(lBack, "43%");
		buttonsPanel.add(lSave);
		buttonsPanel.setCellHorizontalAlignment(lSave, HasHorizontalAlignment.ALIGN_RIGHT);
		buttonsPanel.setCellWidth(lSave, "50%");
		newUnitPanel.insert(buttonsPanel, 0);
		
		contentPanel.showWidget(contentPanel.getWidgetIndex(newUnitPanel));
		
		return newUnitPanel ;
	}
	
	private void createEditLocationView() {
		createNewLocationView();
		newUnitPanel.createEditLocationPanel(selectedUnit);
	}
	
	
	
	
	

	private void showDeviceCardView(String deviceName) {
		deviceCardPanel.clear();
		
		selectedDeviceName = deviceName;
		
		//Back/edit panel
		final Label lBack = new Label("Tagasi");
		lBack.setStyleName("backSaveLabel");
		lBack.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				contentPanel.showWidget(contentPanel.getWidgetIndex(deviceListPanel));
			}
			
		});
		Button lBackButton = new Button();
		lBackButton.setStyleName("backButton");
		lBackButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lBack.fireEvent(event);
			}
			
		});
		
		Label lMeasurements = new Label("M\u00F5\u00F5tmiste tulemused");
		lMeasurements.setStyleName("backSaveLabel");
		lMeasurements.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				deviceTreeService.getMeasurements(selectedDevice.getDeviceKey(), getMeasurementsCallback);
			}
			
		});
		Label lDeleteDevice = new Label("Kustuta");
		lDeleteDevice.setStyleName("backSaveLabel");
		lDeleteDevice.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				deviceTreeService.deleteDevice(selectedDevice.getDeviceKey(), storeDeviceCallback);
			}
			
		});
		
		Label lEditDevice = new Label("Muuda");
		lEditDevice.setStyleName("backSaveLabel");
		lEditDevice.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showEditDeviceView();
			}
			
		});
		
		HorizontalPanel backSavePanel = new HorizontalPanel();
		backSavePanel.setStyleName("backSavePanel");
		backSavePanel.add(lBackButton);
		backSavePanel.add(lBack);
		backSavePanel.setCellWidth(lBackButton, "7%");
		backSavePanel.setCellWidth(lBack, "43%");
		backSavePanel.add(lMeasurements);
		backSavePanel.setCellHorizontalAlignment(lMeasurements, HasHorizontalAlignment.ALIGN_RIGHT);
		backSavePanel.setCellWidth(lMeasurements, "18%");
		backSavePanel.add(lEditDevice);
		backSavePanel.setCellHorizontalAlignment(lEditDevice, HasHorizontalAlignment.ALIGN_RIGHT);
		backSavePanel.setCellWidth(lEditDevice, "16%");
		backSavePanel.add(lDeleteDevice);
		backSavePanel.setCellHorizontalAlignment(lDeleteDevice, HasHorizontalAlignment.ALIGN_RIGHT);
		backSavePanel.setCellWidth(lDeleteDevice, "16%");

		deviceCardPanel.createDeviceView(selectedCompany, selectedUnit, selectedDevice);
		deviceCardPanel.insert(backSavePanel, 0);
		contentPanel.showWidget(contentPanel.getWidgetIndex(deviceCardPanel));
	}
	private void showEditDeviceView() {
		deviceCardPanel.clear();
		
		//Cancel/save panel
		final Label lCancel = new Label("T\u00FChista");
		lCancel.setStyleName("backSaveLabel");
		lCancel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				cancelDeviceEditing();
				
			}
			
		});
		Button lBackButton = new Button();
		lBackButton.setStyleName("backButton");
		lBackButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lCancel.fireEvent(event);
			}
			
		});
		
		Label lSaveDevice = new Label("Salvesta");
		lSaveDevice.setStyleName("backSaveLabel");
		lSaveDevice.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				deviceCardPanel.updateDeviceData(selectedDevice, storeDeviceCallback);
			}
			
		});
		
		HorizontalPanel backSavePanel = new HorizontalPanel();
		backSavePanel.setStyleName("backSavePanel");
		backSavePanel.add(lBackButton);
		backSavePanel.add(lCancel);
		backSavePanel.setCellWidth(lBackButton, "7%");
		backSavePanel.setCellWidth(lCancel, "43%");
		backSavePanel.add(lSaveDevice);
		backSavePanel.setCellHorizontalAlignment(lSaveDevice, HasHorizontalAlignment.ALIGN_RIGHT);
		backSavePanel.setCellWidth(lSaveDevice, "50%");
		
		deviceCardPanel.createEditDeviceView(selectedCompany, selectedUnit, selectedDevice);
		deviceCardPanel.insert(backSavePanel, 0);
		contentPanel.showWidget(contentPanel.getWidgetIndex(deviceCardPanel));
	}
	
	private void cancelDeviceEditing() {
		showDeviceCardView(selectedDeviceName);
	}
	
	private void showAddDeviceView() {
		deviceCardPanel.clear();
		
		//Back/save/edit panel
		final Label lCancel = new Label("T\u00FChista");
		lCancel.setStyleName("backSaveLabel");
		lCancel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				contentPanel.showWidget(contentPanel.getWidgetIndex(deviceListPanel));
			}
			
		});
		Button lBackButton = new Button();
		lBackButton.setStyleName("backButton");
		lBackButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lCancel.fireEvent(event);
			}
			
		});
		
		Label lNewDevice = new Label("Salvesta");
		lNewDevice.setStyleName("backSaveLabel");
		lNewDevice.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				deviceCardPanel.createDevice(deviceList, storeDeviceCallback);
			}
			
		});
		
		HorizontalPanel backSavePanel = new HorizontalPanel();
		backSavePanel.setStyleName("backSavePanel");
		backSavePanel.add(lBackButton);
		backSavePanel.add(lCancel);
		backSavePanel.setCellWidth(lBackButton, "7%");
		backSavePanel.setCellWidth(lCancel, "43%");
		backSavePanel.add(lNewDevice);
		backSavePanel.setCellHorizontalAlignment(lNewDevice, HasHorizontalAlignment.ALIGN_RIGHT);
		backSavePanel.setCellWidth(lNewDevice, "50%");
		deviceCardPanel.createNewDeviceView(selectedCompany, selectedUnit);
		deviceCardPanel.insert(backSavePanel, 0);
		contentPanel.showWidget(contentPanel.getWidgetIndex(deviceCardPanel));
	}
	
	

	

	/**
	 * Create panel for viewing measurements taken on said device.
	 */
	private void createDeviceMeasurementsPanel() {
		// TODO panel with measurements taken on said device.
		lastMeasurementPanel.clear();
		lastMeasurementPanel.setWidth("100%");
		
		final ExtendedMeasurementView measurementPanel = new ExtendedMeasurementView(selectedDevice, measurement, storeMeasurementCallback);
		
		//Back/save panel
		final Label lBack = new Label("Tagasi");
		lBack.setStyleName("backSaveLabel");
		lBack.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				contentPanel.showWidget(contentPanel.getWidgetIndex(measurementListPanel));
			}
			
		});
		Button lBackButton = new Button();
		lBackButton.setStyleName("backButton");
		lBackButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lBack.fireEvent(event);
			}
			
		});
		
		Label lSave = new Label("Salvesta");
		lSave.setStyleName("backSaveLabel");
		lSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				measurementPanel.saveMeasurement();
			}
			
		});
		
		HorizontalPanel backSavePanel = new HorizontalPanel();
		backSavePanel.setStyleName("backSavePanel");
		backSavePanel.add(lBackButton);
		backSavePanel.add(lBack);
		backSavePanel.setCellWidth(lBackButton, "7%");
		backSavePanel.setCellWidth(lBack, "43%");
		backSavePanel.add(lSave);
		backSavePanel.setCellHorizontalAlignment(lSave, HasHorizontalAlignment.ALIGN_RIGHT);
		backSavePanel.setCellWidth(lSave, "50%");
		lastMeasurementPanel.add(backSavePanel);
		lastMeasurementPanel.add(measurementPanel);
		
		AbsolutePanel emptyPanel = new AbsolutePanel();
		emptyPanel.setStyleName("aho-markingBlankPanel");
		lastMeasurementPanel.add(emptyPanel);
		
		contentPanel.showWidget(contentPanel.getWidgetIndex(lastMeasurementPanel));
		measurementPanel.focusInputField();
	}

	public static DeviceTreeServiceAsync getDevicetreeservice() {
		return deviceTreeService;
	}
}
