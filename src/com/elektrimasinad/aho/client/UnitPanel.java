package com.elektrimasinad.aho.client;

import java.util.List;

import com.elektrimasinad.aho.shared.Company;
import com.elektrimasinad.aho.shared.Unit;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UnitPanel extends VerticalPanel {
	private TextBox unitText;
	private Unit unitOld;
	private Company company;
	
	
	public UnitPanel() {
		super();
		
		//createNewLocationPanel();
	}

	private void createNewLocationPanel() {
		super.clear();
		unitOld = new Unit("", "");
		
		//Header
		HorizontalPanel headerPanel = AhoWidgets.createContentHeader(company.getCompanyName());
		add(headerPanel);
		
		//Content
		unitText = AhoWidgets.createTextbox("aho-textbox1 large", "");
		HorizontalPanel unitPanel = new HorizontalPanel();
		unitPanel.setStyleName("aho-panel1");
		unitPanel.add(AhoWidgets.createLabel("\u00DCksus", "aho-label1", null));
		unitPanel.add(unitText);
		unitPanel.setCellHorizontalAlignment(unitText, HasHorizontalAlignment.ALIGN_RIGHT);
		add(unitPanel);
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

		    @Override
		    public void execute() {
		    	unitText.setFocus(true);
		    }
		});
	}

	public void createEditLocationPanel(Unit selectedLocation) {
		unitOld = selectedLocation;
		unitText.setText(selectedLocation.getUnit());
	}
	
	public void clear(Company company) {
		this.company = company;
		createNewLocationPanel();
	}
	
	public void saveUnit(List<Unit> unitList, AsyncCallback<String> storeUnitCallback) {
		//TODO save location to datastore and remove locationList logic from this method (reload companies from datastore)
		if (unitText.getText().isEmpty()) {
			return;
		}
		for (Unit unit : unitList) {
			if (unit.getUnit() == unitText.getText()) {
				if (unitOld.getUnit() != unitText.getText()) {
					//TODO unit already exists exception
					return;
				}
			}
		}
		if (unitOld.getUnit() == "") {
			Unit unit = new Unit(company.getCompanyKey(), unitText.getText());
			DeviceCard.getDevicetreeservice().storeUnit(unit, storeUnitCallback);
		} else {
			for (Unit unit : unitList) {
				if (unit.getUnitKey().equalsIgnoreCase(unitOld.getUnitKey())) {
					Unit updatedUnit = unitList.get(unitList.indexOf(unit));
					updatedUnit.setUnit(unitText.getText());
					DeviceCard.getDevicetreeservice().updateUnit(updatedUnit, storeUnitCallback);
					return;
				}
			}
		}
	}
}