package com.elektrimasinad.aho.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.elektrimasinad.aho.shared.Device;
import com.elektrimasinad.aho.shared.Measurement;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;

public class ExtendedMeasurementView extends VerticalPanel {
	
	private AsyncCallback<String> storeMeasurementCallback;
	private int IMAGES_PER_ROW = 5;
	private List<Image> images = new ArrayList<Image>();
	private Device device;
	private Measurement measurement;
	
	private String markingText;
	private TextBox dateInput;
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


	/**
	 * Blank extended measurement view constructor.
	 */
	public ExtendedMeasurementView() {
		//TODO: create blank extended measurement view.
	}
	
	/**
	 * Extended measurement view constructor.
	 * Pre-populates measurement view with last measurement data on input device.
	 * @param deviceName
	 */
	public ExtendedMeasurementView(Device device, Measurement measurement, AsyncCallback<String> storeMeasurementCallback) {
		this.storeMeasurementCallback = storeMeasurementCallback;
		this.device = device;
		this.measurement = measurement;
		//TODO: create pre-populated extended measurement view.
		createExtendedMeasurementPanel();
		fillMeasurementData();
	}
	
	private void fillMeasurementData() {
		dateInput.setText(measurement.getDate());
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
	
	private void createExtendedMeasurementPanel() {
		setWidth("100%");
		
		//Header panel
		HorizontalPanel deviceHeaderPanel = createContentHeader("Mõõtmiste tulemused");
		add(deviceHeaderPanel);
		
		//ID panel
		HorizontalPanel idPanel = new HorizontalPanel();
		idPanel.setStyleName("aho-panel1");
		Label lID = createLabel("ID nr.", "aho-label1", null);
		idPanel.add(lID);
		Label idInput = createLabel(device.getId(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
		idPanel.add(idInput);
		idPanel.setCellHorizontalAlignment(idInput, HasHorizontalAlignment.ALIGN_RIGHT);
		
		//Device name panel
		HorizontalPanel deviceNamePanel = new HorizontalPanel();
		deviceNamePanel.setStyleName("aho-panel1");
		Label lDeviceName = createLabel("Seade", "aho-label1", null);
		deviceNamePanel.add(lDeviceName);
		Label deviceNameInput = createLabel(device.getDeviceName(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
		deviceNamePanel.add(deviceNameInput);
		deviceNamePanel.setCellHorizontalAlignment(deviceNameInput, HasHorizontalAlignment.ALIGN_RIGHT);
		
		//Date panel
		HorizontalPanel datePanel = new HorizontalPanel();
		datePanel.setStyleName("aho-panel1");
		Label lDate = createLabel("Kuupäev", "aho-label1", null);
		datePanel.add(lDate);
		dateInput = new TextBox();
		dateInput.setStyleName("aho-textbox1 large");
		datePanel.add(dateInput);
		datePanel.setCellHorizontalAlignment(dateInput, HasHorizontalAlignment.ALIGN_RIGHT);
		
		//Marking panel
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
		
		imgO = new Image("res/aho_o.png");
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
		
		commentTextArea = new ExtendedTextArea();
		VerticalPanel commentPanel = createExpandableTextArea("Kokkuv\u00F5te", commentTextArea);
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
		VerticalPanel photosPanel = createPhotosPanel();
		
		add(idPanel);
		add(deviceNamePanel);
		add(datePanel);
		add(markingPanel);
		add(commentPanel);
		add(ndePanel);
		add(dePanel);
		add(mpPanel);
		add(tpPanel);
		add(photosPanel);
	}
	
	/**
	 * Create content header panel.
	 * @param labelText	- content header text.
	 * @return HorizontalPanel content header panel.
	 */
	private HorizontalPanel createContentHeader(String labelText) {
		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.setStyleName("aho-measurementHeaderPanel");
		Label lDeviceHeader = new Label(labelText);
		lDeviceHeader.setStyleName("aho-label2");
		headerPanel.add(lDeviceHeader);
		
		return headerPanel;
	}
	
	private Label createLabel(String labelText, String styleName, HorizontalAlignmentConstant alignment) {
		Label label = new Label(labelText);
		label.setStyleName(styleName);
		if (alignment != null) {
			label.setHorizontalAlignment(alignment);
		}
		return label;
	}
	
	/**
	 * Create auto-expanding text area.
	 * @param labelText.
	 * @return VerticalPanel auto-expanding text area.
	 */
	private VerticalPanel createExpandableTextArea(String labelText, final ExtendedTextArea textArea) {
		final VerticalPanel aPanel = new VerticalPanel();
		aPanel.setStyleName("aho-panel1 expandable");
		
		final Label lLabel = new Label(labelText);
		lLabel.setStyleName("aho-label1");
		aPanel.add(lLabel);
		
		textArea.setStyleName("aho-autoExtendingTextArea");
		textArea.setAlignment(TextAlignment.RIGHT);
	    aPanel.add(textArea);

	    textArea.setVisibleLines(1);
	    textArea.addKeyUpHandler(new KeyUpHandler()
	    {
	        @Override
	        public void onKeyUp(KeyUpEvent event)
	        {
	           textArea.setHeight("auto");
	           textArea.setHeight(textArea.getElement().getScrollHeight() + "px");
	        }
	    });

	    textArea.addValueChangeHandler(new ValueChangeHandler<String>() {

	        @Override
	        public void onValueChange(ValueChangeEvent<String> event) {
	        	textArea.setHeight("auto");
	        	textArea.setHeight(textArea.getElement().getScrollHeight() + "px");
	        }
	    });
	    
	    return aPanel;
	}
	
	/**
	 * Create photos panel and populate it with photos.
	 * @return VerticalPanel photos panel.
	 */
	private VerticalPanel createPhotosPanel() {
		VerticalPanel photosPanel = new VerticalPanel();
		photosPanel.setStyleName("aho-panel1");
		photosPanel.setWidth("100%");
		if (!images.isEmpty()) {
			Grid photosGrid = new Grid(IMAGES_PER_ROW, 1);
			for (int i = 0; i <= images.size() / IMAGES_PER_ROW; i++) {
				for (int j = 0; j < IMAGES_PER_ROW; j++) {
					int imageNr = i * IMAGES_PER_ROW + j;
					if (imageNr < images.size()) {
						Image image = images.get(imageNr);
						image.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								// TODO method for image zooming.
								
							}
							
						});
						photosGrid.setWidget(i, j, image);
					}
				}
			}
		} else {
			Label placeholder = new Label("Pildid puuduvad!");
			placeholder.setStyleName("aho-label1");
			photosPanel.add(placeholder);
		}
		
		return photosPanel;
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
		
		if (isSmallTextBox) {
			textBox.setWidth("280px");
		} else {
			textBox.setWidth("420px");
		}
		aPanel.setCellHorizontalAlignment(textBox, HasHorizontalAlignment.ALIGN_RIGHT);
		return aPanel;
	}
	
	public void focusInputField() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

		    @Override
		    public void execute() {
		    	dateInput.setFocus(true);
		    }
		});
	}
	
	public void saveMeasurement() {
		
		//Measurement measurement = new Measurement();
		measurement.setDeviceID(device.getId());
		measurement.setDeviceName(device.getDeviceName());
		measurement.setDate(dateInput.getText());
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
		
		DeviceCard.getDevicetreeservice().storeMeasurement(measurement, storeMeasurementCallback);
	}
}