package com.elektrimasinad.aho.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Index implements EntryPoint {
	
	private boolean isDevMode;
	private boolean isMobileView;
	private int MAIN_WIDTH;
	
	private VerticalPanel mainPanel;
	private AbsolutePanel headerPanel;
	private DeckPanel contentPanel;
	private VerticalPanel navPanel;

	@Override
	public void onModuleLoad() {
		if (Window.Location.getHref().contains("127.0.0.1")) isDevMode = true;
		else isDevMode = false;
		if (Window.getClientWidth() < 1000) {
			isMobileView = true;
		} else {
			isMobileView = false;
		}
		Window.addResizeHandler(new ResizeHandler() {

		    @Override
		    public void onResize(ResizeEvent event) {
		    	if (Window.getClientWidth() < 1000) {
					isMobileView = true;
				} else {
					isMobileView = false;
				}
		    	updateWidgetSizes();
		    }
		});
		mainPanel = new VerticalPanel();
		mainPanel.setStyleName("panelBackground");
		
		Image measurementsImage = new Image("res/Diagnostika.gif");
		measurementsImage.setStyleName("aho-navigationImage");
		measurementsImage.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (isDevMode) Window.Location.assign(Window.Location.getHref().replace("index", "Aho"));
				else Window.Location.assign("/Aho.html");
			}
		});
		HorizontalPanel navigationPanel = new HorizontalPanel();
		navigationPanel.setStyleName("aho-navigationPanel");
		AbsolutePanel blankPanel = new AbsolutePanel();
		blankPanel.setHeight("100%");
		blankPanel.setWidth("100%");
		navigationPanel.add(blankPanel);
		navigationPanel.add(measurementsImage);
		navigationPanel.setCellWidth(blankPanel, "100%");
		navigationPanel.setCellWidth(measurementsImage, "52px");
		headerPanel = new AbsolutePanel();
		headerPanel.setStyleName("headerBackground");
		headerPanel.add(navigationPanel);
		mainPanel.add(headerPanel);
		
		contentPanel = new DeckPanel();
		mainPanel.add(contentPanel);
		
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setStyleName("mainBackground2");
		rootPanel.add(mainPanel);
		
		init();
		updateWidgetSizes();
	}
	
	private void updateWidgetSizes() {
		String contentWidth = "90%";
		MAIN_WIDTH = 700;
		if (isMobileView) {
			MAIN_WIDTH = Window.getClientWidth();
			contentWidth = "95%";
		}
		mainPanel.setWidth(MAIN_WIDTH + "px");
		mainPanel.setHeight(Window.getClientHeight() + "px");
		contentPanel.setWidth(contentWidth);
		mainPanel.setCellHorizontalAlignment(contentPanel, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	private void init() {
		createNavigationPanel();
		contentPanel.add(navPanel);
		contentPanel.showWidget(contentPanel.getWidgetIndex(navPanel));
		mainPanel.setCellHorizontalAlignment(contentPanel, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellHeight(contentPanel, "100%");
	}
	
	private void createNavigationPanel() {
		navPanel = new VerticalPanel();
		navPanel.setWidth("100%");
		
		Label lLabel1 = new Label("");
		lLabel1.setStyleName("backSaveLabel noPointer");
		Label lMeasurements = new Label("M\u00F5\u00F5tmised");
		lMeasurements.setStyleName("backSaveLabel");
		lMeasurements.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (isDevMode) Window.Location.assign(Window.Location.getHref().replace("index", "Aho"));
				else Window.Location.assign("/Aho.html");
			}
		});
		Label lRaports = new Label("Raporti");
		lRaports.setStyleName("backSaveLabel");
		lRaports.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (isDevMode) Window.Location.assign(Window.Location.getHref().replace("index", "Raports"));
				else Window.Location.assign("/Raports.html");
			}
		});
		Label lDeviceCard = new Label("Seadmekaart");
		lDeviceCard.setStyleName("backSaveLabel");
		lDeviceCard.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (isDevMode) Window.Location.assign(Window.Location.getHref().replace("index", "DeviceCard"));
				else Window.Location.assign("/DeviceCard.html");
			}
		});
		Label lHooldus = new Label("Hooldus");
		lHooldus.setStyleName("backSaveLabel");
		lHooldus.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (isDevMode) Window.Location.assign(Window.Location.getHref().replace("index", "Hooldus"));
				else Window.Location.assign("/Hooldus.html");
			}
		});

		navPanel.add(lLabel1);
		navPanel.add(lMeasurements);
		navPanel.add(lRaports);
		navPanel.add(lDeviceCard);
		navPanel.add(lHooldus);
	}

}
