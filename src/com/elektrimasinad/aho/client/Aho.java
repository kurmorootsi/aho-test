package com.elektrimasinad.aho.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.elektrimasinad.aho.shared.Company;
import com.elektrimasinad.aho.shared.Device;
import com.elektrimasinad.aho.shared.Measurement;
import com.elektrimasinad.aho.shared.Unit;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
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
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Aho implements EntryPoint {
	
	private static final DeviceTreeServiceAsync deviceTreeService = DeviceCard.getDevicetreeservice();
	private AsyncCallback<String> storeMeasurementCallback;
	private AsyncCallback<Measurement> getLastMeasurementCallback;

	private boolean isMobileView;
	private int MAIN_WIDTH;
	protected Integer currentFocusedInputField;
	private List<HasFocusHandlers> inputFieldList = new ArrayList<HasFocusHandlers>();
	private Device selectedDevice = new Device();
	private TreeItem selectedTreeItem;
	private Measurement measurement = new Measurement();
	
	private VerticalPanel mainPanel;
	private AbsolutePanel headerPanel;
	private VerticalPanel deviceTreePanel;
	private VerticalPanel measurementPanel;
	private Label lName;
	private HorizontalPanel prevNextPanel;
	private ScrollPanel markingScrollPanel;
	private DeckPanel contentPanel;

	private DeviceTree devTree;

	private String markingText;
	private ExtendedTextArea commentTextArea;
	private TextBox mmsNDE;
	private TextBox geNDE;
	private TextBox commentNDE;
	private TextBox mmsDE;
	private TextBox geDE;
	private TextBox commentDE;
	private TextBox mmsMP;
	private TextBox geMP;
	private TextBox commentMP;
	private TextBox mmsTP;
	private TextBox geTP;
	private TextBox commentTP;
	private Image imgA;
	private Image imgH;
	private Image imgO;
	
	public void onModuleLoad() {
		storeMeasurementCallback = new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String name) {
				System.out.println(name);
				if (measurement.getMarking().equalsIgnoreCase("alarm")) selectedTreeItem.getElement().setClassName("gwt-TreeNode2A");
				else if (measurement.getMarking().equalsIgnoreCase("hoiatus")) selectedTreeItem.getElement().setClassName("gwt-TreeNode2H");
				else if (measurement.getMarking().equalsIgnoreCase("ok")) selectedTreeItem.getElement().setClassName("gwt-TreeNode2O");
				else selectedTreeItem.getElement().setClassName("gwt-TreeNode2M");
				contentPanel.showWidget(contentPanel.getWidgetIndex(deviceTreePanel));
				prevNextPanel.setVisible(false);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		getLastMeasurementCallback = new AsyncCallback<Measurement>() {
			
			@Override
			public void onSuccess(Measurement lastMeasurement) {
				//System.out.println(name);
				if (lastMeasurement == null) {
					measurement = new Measurement();
				} else {
					measurement = lastMeasurement;
				}
				createMeasurementPanel();
				fillMeasurementData();
				contentPanel.showWidget(contentPanel.getWidgetIndex(measurementPanel));
				updateWidgetSizes();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
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
					prevNextPanel.setVisible(false);
				}
		    	updateWidgetSizes();
		    }
		});
		mainPanel = new VerticalPanel();
		mainPanel.setStyleName("panelBackground");
		
		headerPanel = new AbsolutePanel();
		headerPanel.setStyleName("headerBackground");
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
		if (isMobileView) {
			MAIN_WIDTH = Window.getClientWidth();
			contentWidth = "95%";
			int markingScrollHeight = Math.abs(Window.getClientHeight() - markingScrollPanel.getAbsoluteTop() - 105);
			if (markingScrollHeight < 400) {
				markingScrollHeight = 400;
			}
			markingScrollPanel.setSize("100%", markingScrollHeight + "px");
		} else {
			MAIN_WIDTH = 700;
			markingScrollPanel.setSize("100%", "auto");
		}
		mainPanel.setWidth(MAIN_WIDTH + "px");
		mainPanel.setHeight(Window.getClientHeight() + "px");
		contentPanel.setWidth(contentWidth);
		mainPanel.setCellHorizontalAlignment(contentPanel, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	private void init() {
		createDeviceTreePanel();
		measurementPanel = new VerticalPanel();
		createMeasurementPanel();
		createPrevNextPanel();
		contentPanel.add(deviceTreePanel);
		contentPanel.add(measurementPanel);
		contentPanel.showWidget(contentPanel.getWidgetIndex(deviceTreePanel));
		mainPanel.setCellHorizontalAlignment(contentPanel, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellHeight(contentPanel, "100%");
	}
	
	
	
	private void createDeviceTreePanel() {
		deviceTreePanel = new VerticalPanel();
		deviceTreePanel.setWidth("100%");
		
		Label lLabel1 = new Label("M\u00F5\u00F5tmised");
		lLabel1.setStyleName("backSaveLabel noPointer");
		
		devTree = new DeviceTree(deviceTreeService);
		devTree.getElement().addClassName("gwt-Tree");
		devTree.addSelectionHandler(new SelectionHandler<TreeItem>() {

			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				System.out.println("valitud: " + event.getSelectedItem().getText());
				selectedTreeItem = (TreeItem) event.getSelectedItem();
				if (selectedTreeItem.getUserObject() instanceof Device) {
					System.out.println(selectedTreeItem.getText() + " selected..");
					selectedDevice = (Device)selectedTreeItem.getUserObject();
					lName.setText(selectedTreeItem.getText());
					deviceTreeService.getLastMeasurement(selectedDevice.getDeviceKey(), getLastMeasurementCallback);
				} else {
					if (selectedTreeItem.getUserObject() instanceof Company && selectedTreeItem.getChildCount() == 0) {
						System.out.println(selectedTreeItem.getText() + " selected..");
						devTree.fetchUnits((Company)selectedTreeItem.getUserObject());
					} else if (selectedTreeItem.getUserObject() instanceof Unit && selectedTreeItem.getChildCount() == 0) {
						System.out.println(selectedTreeItem.getText() + " selected..");
						devTree.fetchDevices((Unit)selectedTreeItem.getUserObject());
					}
					TreeItem selItem = event.getSelectedItem();
					boolean state = selItem.getState();
				    TreeItem parent = selItem.getParentItem();
				    selItem.getTree().setSelectedItem(parent, false); // null is ok
				    if(parent != null)
				        parent.setSelected(false);  // not compulsory
				    selItem.setState(!state, false);
				}
			}
			
		});
		
		deviceTreePanel.add(lLabel1);
		deviceTreePanel.add(devTree);
	}
	
	private void createMeasurementPanel() {
		measurementPanel.clear();
		measurementPanel.setWidth("100%");
		
		markingScrollPanel = new ScrollPanel();
		markingScrollPanel.setStyleName("markingScrollPanel");
		final VerticalPanel measurementInputPanel = new VerticalPanel();
		measurementInputPanel.setStyleName("aho-panel4");
		
		final HorizontalPanel headerPanel = createMeasurementHeader();
		
		//final ListBox cbId = new ListBox();
		//cbId.setStyleName("fb-combobox");
		//cbId.addItem("ERP1");
		//cbId.addItem("ERP2");
		//cbId.addItem("ERP3");
		//cbId.addItem("ERP4");
		//idPanel.add(cbId, 376, 14);
		
		// final Image imgIdSaved = new Image("res/saved_icon.png");
		// imgIdSaved.setSize("17px", "12px");
		// idPanel.add(imgIdSaved, 460, 20);
		//
		// final TextBox tbId = new TextBox();
		// tbId.setStyleName("fb-textbox2");
		// tbId.setAlignment(TextAlignment.RIGHT);
		// tbId.setText(name);
		// tbId.addKeyDownHandler(new KeyDownHandler() {
		//
		// @Override
		// public void onKeyDown(KeyDownEvent event) {
		// imgIdSaved.setVisible(false);
		// }
		//
		// });
		// idPanel.add(tbId, 275, 12);
		
		final HorizontalPanel markingPanel = new HorizontalPanel();
		markingPanel.setStyleName("aho-panel1");
		
		final Label lmarking = new Label("M\u00E4rge");
		lmarking.setStyleName("aho-label1");
		markingPanel.add(lmarking);
		
		imgA = new Image("res/aho_a_0.png");
		imgA.setAltText("alarm");
		imgA.setStyleName("aho-markingImg");
		markingPanel.add(imgA);
		
		imgH = new Image("res/aho_h_0.png");
		imgH.setAltText("hoiatus");
		imgH.setStyleName("aho-markingImg");
		markingPanel.add(imgH);
		
		imgO = new Image("res/aho_o_0.png");
		imgO.setAltText("ok");
		imgO.setStyleName("aho-markingImg");
		markingPanel.add(imgO);
		
		markingPanel.setCellWidth(lmarking, "55%");
		markingPanel.setCellWidth(imgA, "15%");
		markingPanel.setCellWidth(imgH, "15%");
		markingPanel.setCellWidth(imgO, "15%");
		
		ClickHandler imgAHOClickHandler = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Image img = (Image) event.getSource();
				markingText = img.getAltText();
				if (markingText.equals("alarm")) {
					System.out.println(imgA.getUrl());
					if (imgA.getUrl().contains("res/aho_a.png")) {
						markingText = "";
						imgA.setUrl("res/aho_a_0.png");
					} else {
						imgA.setUrl("res/aho_a.png");
						imgH.setUrl("res/aho_h_0.png");
						imgO.setUrl("res/aho_o_0.png");
					}
				}
				else if (markingText.equals("hoiatus")) {
					if (imgH.getUrl().contains("res/aho_h.png")) {
						markingText = "";
						imgH.setUrl("res/aho_h_0.png");
					} else {
						imgA.setUrl("res/aho_a_0.png");
						imgH.setUrl("res/aho_h.png");
						imgO.setUrl("res/aho_o_0.png");
					}
				}
				else if (markingText.equals("ok")) {
					if (imgO.getUrl().contains("res/aho_o.png")) {
						markingText = "";
						imgO.setUrl("res/aho_o_0.png");
					} else {
						imgA.setUrl("res/aho_a_0.png");
						imgH.setUrl("res/aho_h_0.png");
						imgO.setUrl("res/aho_o.png");
					}
				}
			}
			
		};
		imgA.addClickHandler(imgAHOClickHandler);
		imgH.addClickHandler(imgAHOClickHandler);
		imgO.addClickHandler(imgAHOClickHandler);
		
		Label lSaveMeasurement = new Label("Salvesta");
		lSaveMeasurement.setStyleName("backSaveLabel");
		lSaveMeasurement.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				saveMeasurement();
			}
			
		});
		Label lBack = new Label("Tagasi");
		lBack.setStyleName("backSaveLabel");
		final Button lBackButton = new Button();
		lBackButton.setStyleName("backButton");
		lBackButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				contentPanel.showWidget(contentPanel.getWidgetIndex(deviceTreePanel));
				prevNextPanel.setVisible(false);
			}
			
		});
		lBack.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lBackButton.fireEvent(event);
			}
			
		});
		
		HorizontalPanel backSavePanel = new HorizontalPanel();
		backSavePanel.setStyleName("backSavePanel");
		backSavePanel.add(lBackButton);
		backSavePanel.add(lBack);
		backSavePanel.add(lSaveMeasurement);
		backSavePanel.setCellWidth(lBackButton, "7%");
		backSavePanel.setCellWidth(lBack, "43%");
		backSavePanel.setCellWidth(lSaveMeasurement, "50%");
		backSavePanel.setCellHorizontalAlignment(lSaveMeasurement, HasHorizontalAlignment.ALIGN_RIGHT);
		
		VerticalPanel commentPanel = createCommentTextArea("Kokkuv\u00F5te");
		mmsNDE = new TextBox();
		geNDE = new TextBox();
		commentNDE = new TextBox();
		VerticalPanel ndePanel = createMeasurementPanelUnit("NDE", mmsNDE, geNDE, commentNDE);
		mmsDE = new TextBox();
		geDE = new TextBox();
		commentDE = new TextBox();
		VerticalPanel dePanel = createMeasurementPanelUnit("DE", mmsDE, geDE, commentDE);
		mmsMP = new TextBox();
		geMP = new TextBox();
		commentMP = new TextBox();
		VerticalPanel mpPanel = createMeasurementPanelUnit("MP", mmsMP, geMP, commentMP);
		mmsTP = new TextBox();
		geTP = new TextBox();
		commentTP = new TextBox();
		VerticalPanel tpPanel = createMeasurementPanelUnit("TP", mmsTP, geTP, commentTP);
		AbsolutePanel emptyPanel = new AbsolutePanel();
		emptyPanel.setStyleName("aho-markingBlankPanel");
		
		measurementPanel.add(backSavePanel);
		measurementPanel.add(headerPanel);
		measurementInputPanel.add(ndePanel);
		measurementInputPanel.add(dePanel);
		measurementInputPanel.add(mpPanel);
		measurementInputPanel.add(tpPanel);
		measurementPanel.add(markingPanel);
		measurementPanel.add(commentPanel);
		markingScrollPanel.add(measurementInputPanel);
		measurementPanel.add(markingScrollPanel);
		measurementPanel.setCellHeight(markingScrollPanel, "100%");
		measurementPanel.add(emptyPanel);
	}
	
	private void fillMeasurementData() {
		markingText = measurement.getMarking();
		if (markingText.equals("alarm")) {
			imgA.setUrl("res/aho_a.png");
			imgH.setUrl("res/aho_h_0.png");
			imgO.setUrl("res/aho_o_0.png");
		}
		else if (markingText.equals("hoiatus")) {
			imgA.setUrl("res/aho_a_0.png");
			imgH.setUrl("res/aho_h.png");
			imgO.setUrl("res/aho_o_0.png");
		}
		else if (markingText.equals("ok")) {
			imgA.setUrl("res/aho_a_0.png");
			imgH.setUrl("res/aho_h_0.png");
			imgO.setUrl("res/aho_o.png");
		}
		commentTextArea.setText(measurement.getComment());
		mmsNDE.setText(measurement.getNDEmms());
		geNDE.setText(measurement.getNDEge());
		commentNDE.setText(measurement.getNDEcomment());
		mmsDE.setText(measurement.getDEmms());
		geDE.setText(measurement.getDEge());
		commentDE.setText(measurement.getDEcomment());
		mmsMP.setText(measurement.getMPmms());
		geMP.setText(measurement.getMPge());
		commentMP.setText(measurement.getMPcomment());
		mmsTP.setText(measurement.getTPmms());
		geTP.setText(measurement.getTPge());
		commentTP.setText(measurement.getTPcomment());
	}
	
	/**
	 * Create measurement header panel with device name and drag icon on it.
	 * @return FocusPanel measurement header.
	 */
	private HorizontalPanel createMeasurementHeader() {
		HorizontalPanel userPanel = new HorizontalPanel();
		userPanel.setStyleName("aho-measurementHeaderPanel");
		
		// Image imgArrow = new Image("res/drop_down_arrow1.png");
		// userPanel.add(imgArrow, 30, 20);
		
		lName = new Label(selectedDevice.getId() + " " + selectedDevice.getDeviceName());
		lName.setStyleName("aho-label2");
		userPanel.add(lName);
		
		Image imgDrag = new Image("res/drag_icon.png");
		imgDrag.setStyleName("aho-dragImg");
		userPanel.add(imgDrag);
		userPanel.setCellWidth(lName, "93%");
		userPanel.setCellWidth(imgDrag, "7%");
		
		return userPanel;
	}
	
	/**
	 * Create panel with label and textbox fields.
	 * @param labelText - label text.
	 * @param isSmallTextBox - for smaller textbox size.
	 * @return AbsolutePanel with label and textbox on it.
	 */
	private HorizontalPanel createTextInputPanel(String labelText, final TextBox textBox, boolean isSmallTextBox) {
		HorizontalPanel aPanel = new HorizontalPanel();
		aPanel.setStyleName("aho-panel2");
		aPanel.setWidth("100%");
		
		final Label lLabel = new Label(labelText);
		lLabel.setStyleName("aho-label1");
		aPanel.add(lLabel);
		
		
		textBox.setStyleName("aho-textbox1");
		aPanel.add(textBox);
		inputFieldList.add(textBox);
		textBox.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				currentFocusedInputField = inputFieldList.indexOf(textBox);
				if (isMobileView) {
					prevNextPanel.setVisible(true);
				}
			}
			
		});
		if (isSmallTextBox) {
			textBox.setWidth("280px");
		} else {
			textBox.setWidth("420px");
		}
		aPanel.setCellHorizontalAlignment(textBox, HasHorizontalAlignment.ALIGN_RIGHT);
		return aPanel;
	}
	
	/**
	 * Create auto-expanding text area.
	 * @param labelText.
	 * @return VerticalPanel auto-expanding text area.
	 */
	private VerticalPanel createCommentTextArea(String labelText) {
		final VerticalPanel aPanel = new VerticalPanel();
		aPanel.setStyleName("aho-panel1 expandable");
		
		final Label lLabel = new Label(labelText);
		lLabel.setStyleName("aho-label1");
		aPanel.add(lLabel);
		
		commentTextArea = new ExtendedTextArea();
		commentTextArea.setStyleName("aho-autoExtendingTextArea");
		commentTextArea.setAlignment(TextAlignment.RIGHT);
	    aPanel.add(commentTextArea);
		inputFieldList.add(commentTextArea);
		commentTextArea.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				currentFocusedInputField = inputFieldList.indexOf(commentTextArea);
				if (isMobileView) {
					prevNextPanel.setVisible(true);
				}
			}
			
		});

	    commentTextArea.setVisibleLines(1);
	    commentTextArea.addKeyUpHandler(new KeyUpHandler()
	    {
	        @Override
	        public void onKeyUp(KeyUpEvent event)
	        {
	           commentTextArea.setHeight("auto");
	           commentTextArea.setHeight(commentTextArea.getElement().getScrollHeight() + "px");
	           updateWidgetSizes();
	        }
	    });

	    commentTextArea.addValueChangeHandler(new ValueChangeHandler<String>() {

	        @Override
	        public void onValueChange(ValueChangeEvent<String> event) {
	        	commentTextArea.setHeight("auto");
	        	commentTextArea.setHeight(commentTextArea.getElement().getScrollHeight() + "px");
	        	updateWidgetSizes();
	        }
	    });
	    
	    return aPanel;
	}
	
	/**
	 * Create measurement panel unit.
	 * Example: DE panel with 2 input fields for taken measurements and 1 comment field.
	 * @param name - panel name (DE, NDE etc.)
	 * @return VerticalPanel with input fields.
	 */
	private VerticalPanel createMeasurementPanelUnit(String name, TextBox mms, TextBox ge, TextBox comment) {
		final VerticalPanel vPanel = new VerticalPanel();
		vPanel.setStyleName("aho-panel1");
		
		// final Image imgNameSaved = new Image("res/saved_icon.png");
		// imgNameSaved.setSize("17px", "12px");
		// namePanel.add(imgNameSaved, 460, 20);
		
		final HorizontalPanel valuePanelMMS = createTextInputPanel(name + " (mm/s)", mms, true);
		valuePanelMMS.setStyleName("");
		final HorizontalPanel valuePanelGE = createTextInputPanel(name + " (gE)", ge, true);
		valuePanelGE.setStyleName("");
		HorizontalPanel commentPanel = createTextInputPanel(name + " Kommentaar", comment, false);
		commentPanel.setStyleName("");
		
		vPanel.add(valuePanelMMS);
		vPanel.add(valuePanelGE);
		vPanel.add(commentPanel);
		
		return vPanel;
	}
	
	/*
	 * Creates fixed footer with prev. next. buttons for quick navigation between input fields.
	 */
	private void createPrevNextPanel() {
		prevNextPanel = new HorizontalPanel();
		mainPanel.add(prevNextPanel);
		prevNextPanel.setStyleName("prevNextPanel");
		Button prevButton = new Button("Eelmine");
		prevButton.setStyleName("prevNextButton");
		prevButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Integer cfif = currentFocusedInputField - 1;
				if (cfif < 0) {
					cfif = inputFieldList.size() + cfif;
				}
				((FocusWidget) inputFieldList.get(cfif)).setFocus(true);
			}
		});
		Button nextButton = new Button("J\u00E4rgmine");
		nextButton.setStyleName("prevNextButton");
		nextButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Integer cfif = currentFocusedInputField + 1;
				if (cfif >= inputFieldList.size()) {
					cfif = 0;
				}
				((FocusWidget) inputFieldList.get(cfif)).setFocus(true);
			}
		});
		
		prevNextPanel.add(prevButton);
		prevNextPanel.setCellWidth(prevButton, "50%");
		prevNextPanel.add(nextButton);
		prevNextPanel.setCellWidth(nextButton, "50%");
		prevNextPanel.setVisible(false);
	}
	
	private void saveMeasurement() {
		Date date = new Date();
		DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy");
		
		//Measurement measurement = new Measurement();
		measurement.setDeviceID(selectedDevice.getId());
		measurement.setDeviceName(selectedDevice.getDeviceName());
		measurement.setDeviceKey(selectedDevice.getDeviceKey());
		measurement.setDate(dtf.format(date, TimeZone.createTimeZone(0)));
		measurement.setMarking(markingText);
		measurement.setComment(commentTextArea.getText());
		measurement.setNDEmms(mmsNDE.getText());
		measurement.setNDEge(geNDE.getText());
		measurement.setNDEcomment(commentNDE.getText());
		measurement.setDEmms(mmsDE.getText());
		measurement.setDEge(geDE.getText());
		measurement.setDEcomment(commentDE.getText());
		measurement.setMPmms(mmsMP.getText());
		measurement.setMPge(geMP.getText());
		measurement.setMPcomment(commentMP.getText());
		measurement.setTPmms(mmsTP.getText());
		measurement.setTPge(geTP.getText());
		measurement.setTPcomment(commentTP.getText());
		
		deviceTreeService.storeMeasurement(measurement, storeMeasurementCallback);
	}
	
	public static DeviceTreeServiceAsync getDevicetreeservice() {
		return deviceTreeService;
	}
}
