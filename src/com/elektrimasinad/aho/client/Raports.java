package com.elektrimasinad.aho.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.elektrimasinad.aho.shared.Company;
import com.elektrimasinad.aho.shared.Measurement;
import com.elektrimasinad.aho.shared.Raport;
import com.elektrimasinad.aho.shared.Unit;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Raports implements EntryPoint {
	
	private static final DeviceTreeServiceAsync deviceTreeService = DeviceCard.getDevicetreeservice();
	private AsyncCallback<String> storeRaportCallback;
	private AsyncCallback<List<Raport>> getRaportsCallback;
	protected AsyncCallback<List<Measurement>> getRaportDataCallback;
	
	private int MAIN_WIDTH = 900;
	private int CONTENT_WIDTH = (int) (MAIN_WIDTH * 0.9);
	
	private List<Raport> raports = new ArrayList<Raport>();
	
	private static class information {
	      private final String address;

	      public information(String address) {
	         this.address = address;
	      }
	   }
	private static List<Measurement> raportDataList;

	private DeviceTree devTree;
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel raportPanel = new VerticalPanel();
	private DeckPanel contentPanel;
	private VerticalPanel treePanel;
	private VerticalPanel terePanel;
	private VerticalPanel tablePanel;
	private VerticalPanel unitPanel = new VerticalPanel();
	
	private Unit selectedUnit;
	private static Raport selectedRaport;
	protected Company selectedCompany;
	private Widget inputRaportNr;
	private Widget inputMeasurerName;
	private Widget inputMeasurerPhone;
	private Widget inputDate;

	

	@Override
	public void onModuleLoad() {
		storeRaportCallback = new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String name) {
				System.out.println(name);
				deviceTreeService.getRaports(selectedUnit.getUnitKey(), getRaportsCallback);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		getRaportsCallback = new AsyncCallback<List<Raport>>() {
			
			@Override
			public void onSuccess(List<Raport> raportList) {
				//System.out.println(name);
				if (raportList != null) {
					raports = raportList;
				}
				createUnitPanel();
				contentPanel.showWidget(contentPanel.getWidgetIndex(unitPanel));
				updateWidgetSizes();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		getRaportDataCallback = new AsyncCallback<List<Measurement>>() {
			
			@Override
			public void onSuccess(List<Measurement> measurementList) {
				//System.out.println(name);
				if (measurementList != null) {
					raportDataList = measurementList;
				}
				if (selectedRaport.getRaportKey().equals("")) {
					createRaport(true);
				} else {
					createRaport(false);
				}
				updateWidgetSizes();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
		RootPanel root = RootPanel.get();
		root.setStyleName("mainBackground2");
		
		mainPanel.setSize(MAIN_WIDTH + "px", "900px");
		mainPanel.setStyleName("panelBackground");
		
		AbsolutePanel headerPanel = new AbsolutePanel();
		headerPanel.setStyleName("headerBackground");
		mainPanel.add(headerPanel);
		
		contentPanel = new DeckPanel();
		mainPanel.add(contentPanel);
		mainPanel.setCellHeight(contentPanel, "100%");
		mainPanel.setCellHorizontalAlignment(contentPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		root.add(mainPanel);
		
		init();
		updateWidgetSizes();
	}
	
	private void updateWidgetSizes() {
		mainPanel.setWidth(MAIN_WIDTH + "px");
		mainPanel.setHeight(Window.getClientHeight() + "px");
		contentPanel.setWidth(CONTENT_WIDTH + "px");
	}
	
	/**
	 * Initialize raport view.
	 * Create blank raport page.
	 */
	private void init() {
		
//		createTreePanel();
//		createTerePanel();
//		contentPanel.add(treePanel);
//		contentPanel.add(unitPanel);
		contentPanel.add(terePanel);
//		contentPanel.add(terePanel);
//		contentPanel.showWidget(contentPanel.getWidgetIndex(treePanel));
//		contentPanel.showWidget(contentPanel.getWidgetIndex(terePanel));
		contentPanel.showWidget(contentPanel.getWidgetIndex(terePanel));

		
	}
	private void createTerePanel() {
		terePanel = new VerticalPanel();
		terePanel.setWidth("100%");
		
		Label lLabel1 = new Label("Tere");
		lLabel1.setStyleName("backSaveLabel noPointer");
		terePanel.add(lLabel1);
	}
	
	private void createTreePanel() {
		treePanel = new VerticalPanel();
		treePanel.setWidth("100%");
		
		Label lLabel1 = new Label("Raportid2");
		lLabel1.setStyleName("backSaveLabel noPointer");
		
		devTree = new DeviceTree(deviceTreeService);
		devTree.getElement().addClassName("gwt-Tree");
		devTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				System.out.println("valitud: " + event.getSelectedItem().getText());
				TreeItem item = (TreeItem) event.getSelectedItem();
				if (item.getUserObject() instanceof Unit && item.getChildCount() == 0) {
					System.out.println(item.getText() + " selected..");
					selectedUnit = (Unit)item.getUserObject();
					deviceTreeService.getRaports(selectedUnit.getUnitKey(), getRaportsCallback);
				} else {
					if (item.getUserObject() instanceof Company && item.getChildCount() == 0) {
						selectedCompany = (Company)item.getUserObject();
						System.out.println(item.getText() + " selected..");
						devTree.fetchUnits((Company)item.getUserObject());
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
		
		treePanel.add(lLabel1);
		treePanel.add(devTree);
	}
	
	private void createUnitPanel() {
		unitPanel.clear();
		unitPanel.setWidth("100%");
		
		Label lNewRaport = new Label("Uus raportike");
		lNewRaport.setStyleName("backSaveLabel");
		lNewRaport.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Date date = new Date();
				DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy");
				selectedRaport = new Raport();
				selectedRaport.setCompanyName(selectedCompany.getCompanyName());
				selectedRaport.setUnitName(selectedUnit.getUnit());
				selectedRaport.setUnitKey(selectedUnit.getUnitKey());
				selectedRaport.setDate(dtf.format(date, TimeZone.createTimeZone(0)));
				deviceTreeService.getRaportData(selectedRaport, getRaportDataCallback);
			}
			
		});
		Label lBack = new Label("Tagasi");
		lBack.setStyleName("backSaveLabel");
		final Button lBackButton = new Button();
		lBackButton.setStyleName("backButton");
		lBackButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				contentPanel.showWidget(contentPanel.getWidgetIndex(treePanel));
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
		buttonsPanel.add(lNewRaport);
		buttonsPanel.setCellWidth(lBackButton, "7%");
		buttonsPanel.setCellWidth(lBack, "43%");
		buttonsPanel.setCellWidth(lNewRaport, "50%");
		buttonsPanel.setCellHorizontalAlignment(lNewRaport, HasHorizontalAlignment.ALIGN_RIGHT);
		unitPanel.add(buttonsPanel);
		
		//Header Panel
		HorizontalPanel headerPanel = AhoWidgets.createThinContentHeader(selectedUnit.getUnit());
		unitPanel.add(headerPanel);
		
		//Raports list
		for (final Raport raport : raports) {
			Label lRaport = new Label("Raport " + raport.getRaportID() + "  " + raport.getDate());
			lRaport.setStyleName("aho-listItem");
			lRaport.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					//createLocationListPanel(((Label)(event.getSource())).getText());
					selectedRaport = raports.get(raports.indexOf(raport));
					deviceTreeService.getRaportData(selectedRaport, getRaportDataCallback);
				}
				
			});
			unitPanel.add(lRaport);
		}
		
		contentPanel.showWidget(contentPanel.getWidgetIndex(unitPanel));
	}
	
	/**
	 * Create editable new raport view and populate it with data.
	 */
	private void createRaport(boolean isEditable) {
		raportPanel.clear();
		raportPanel.setWidth("100%");
		
		//Back panel
		Label lBack = new Label("Tagasi");
		lBack.setStyleName("backSaveLabel");
		final Button lBackButton = new Button();
		lBackButton.setStyleName("backButton");
		lBackButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				contentPanel.showWidget(contentPanel.getWidgetIndex(unitPanel));
			}
			
		});
		lBack.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lBackButton.fireEvent(event);
			}
			
		});
		Label lSave = new Label("Salvesta");
		lSave.setStyleName("backSaveLabel");
		lSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				selectedRaport.setRaportID(((TextBox)inputRaportNr).getText());
				selectedRaport.setMeasurerName(((TextBox)inputMeasurerName).getText());
				selectedRaport.setMeasurerPhone(((TextBox)inputMeasurerPhone).getText());
				selectedRaport.setDate(((TextBox)inputDate).getText());
				deviceTreeService.storeRaport(selectedRaport, storeRaportCallback);
			}
			
		});
		Label lPdf = new Label("PDF");
		lPdf.setStyleName("backSaveLabel");
		lPdf.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				//String url = GWT.getModuleBaseURL() + "downloadService?fileInfo1=" + fileInfo1;
				//String url = Window.Location.getHref().replace("Raports.html", "getRaport");
				//Window.open( url, "_blank", "status=0,toolbar=0,menubar=0,location=0");
				Window.open(Window.Location.getHref().replace("Raports.html", "raport.html?raport=" + selectedRaport.getRaportKey()), "_blank", "");
			}
			
		});
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.setStyleName("backSavePanel");
		buttonsPanel.add(lBackButton);
		buttonsPanel.add(lBack);
		buttonsPanel.setCellWidth(lBackButton, "7%");
		buttonsPanel.setCellWidth(lBack, "43%");
		if (isEditable) {
			buttonsPanel.add(lSave);
			buttonsPanel.setCellWidth(lSave, "50%");
			buttonsPanel.setCellHorizontalAlignment(lSave, HasHorizontalAlignment.ALIGN_RIGHT);
		} else {
			buttonsPanel.add(lPdf);
			buttonsPanel.setCellWidth(lPdf, "50%");
			buttonsPanel.setCellHorizontalAlignment(lPdf, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		raportPanel.add(buttonsPanel);
		
		//Header panel
		HorizontalPanel raportHeaderPanel = AhoWidgets.createContentHeader("Raport");
		raportPanel.add(raportHeaderPanel);
		
		//Top info panel
		Widget lCompanyName = AhoWidgets.createLabel("Ettev\u00F5te", "aho-label1", null);
		Widget inputCompanyName = AhoWidgets.createLabel(selectedRaport.getCompanyName(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
		Widget lRaportNr = AhoWidgets.createLabel("Esmane raport nr.", "aho-label1", null);
		if (isEditable) {
			inputRaportNr = AhoWidgets.createTextbox("aho-textbox1", selectedRaport.getRaportID(), "Raporti nr.");
		} else {
			inputRaportNr = AhoWidgets.createLabel(selectedRaport.getRaportID(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
		}
		Widget lUnitName = AhoWidgets.createLabel("\u00DCksus", "aho-label1", null);
		Widget inputUnitName = AhoWidgets.createLabel(selectedRaport.getUnitName(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
		HorizontalPanel companyNamePanel = AhoWidgets.createSingleRowDataPanel(lCompanyName, inputCompanyName, "aho-panel1");
		raportPanel.add(companyNamePanel);
		HorizontalPanel raportNrPanel = AhoWidgets.createSingleRowDataPanel(lRaportNr, inputRaportNr, "aho-panel1");
		raportPanel.add(raportNrPanel);
		HorizontalPanel measuringLocationPanel = AhoWidgets.createSingleRowDataPanel(lUnitName, inputUnitName, "aho-panel1");
		raportPanel.add(measuringLocationPanel);
		
		//Measurement results panel
		VerticalPanel reportDataTable = createDataTable();
		raportPanel.add(reportDataTable);

		
		VerticalPanel reportNewDataTable = createNewDataTable();
		raportPanel.add(reportNewDataTable);
		
		//Bottom info panel
		Widget lMeasurerInfo = AhoWidgets.createLabel("M\u00F5\u00F5tmisi teostas:", "aho-label1", null);
		Widget lDate = AhoWidgets.createLabel("Kuup\u00E4ev", "aho-label1", null);
		if (isEditable) {
			inputMeasurerName = AhoWidgets.createTextbox("aho-textbox1", selectedRaport.getMeasurerName());
			inputMeasurerName.getElement().setPropertyString("placeholder", "Nimi");
			inputMeasurerPhone = AhoWidgets.createTextbox("aho-textbox1", selectedRaport.getMeasurerPhone());
			inputMeasurerPhone.getElement().setPropertyString("placeholder", "Telefoni nr.");
			inputDate = AhoWidgets.createTextbox("aho-textbox1", selectedRaport.getDate());
			inputDate.getElement().setPropertyString("placeholder", "Kuup\u00E4ev");
		} else {
			inputMeasurerName = AhoWidgets.createLabel(selectedRaport.getMeasurerName(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
			inputMeasurerPhone = AhoWidgets.createLabel(selectedRaport.getMeasurerPhone(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
			inputDate = AhoWidgets.createLabel(selectedRaport.getDate(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
		}
		HorizontalPanel measurerPanel = AhoWidgets.createSingleRowDataPanel(lMeasurerInfo, inputMeasurerName, "aho-panel1 closeBottom");
		raportPanel.add(measurerPanel);
		HorizontalPanel measurerPhonePanel = AhoWidgets.createSingleRowDataPanel(AhoWidgets.createLabel("", "aho-label1", null), inputMeasurerPhone, "aho-panel1");
		raportPanel.add(measurerPhonePanel);
		HorizontalPanel dateTimePanel = AhoWidgets.createSingleRowDataPanel(lDate, inputDate, "aho-panel1");
		raportPanel.add(dateTimePanel);
		
		AbsolutePanel emptyPanel = new AbsolutePanel();
		emptyPanel.setStyleName("aho-markingBlankPanel");
		raportPanel.add(emptyPanel);
		
		contentPanel.showWidget(contentPanel.getWidgetIndex(raportPanel));
	}
	
	/**
	 * Create data table with measurement data.
	 * @return AbsolutePanel containing measurement data table.
	 */
	private VerticalPanel createDataTable() {
		CellTable.Resources historyTableRes = GWT.create(TableRes.class);
		tablePanel = new VerticalPanel();
		tablePanel.setStyleName("aho-panel1 table2");
		tablePanel.setWidth("100%");
		// Create a CellTable with corresponding columns.
	    CellTable<Measurement> table = new CellTable<Measurement>(raportDataList.size(), historyTableRes);
	    TextColumn<Measurement> idColumn = new TextColumn<Measurement>() {
	    	@Override
	    	public String getValue(Measurement measurement) {
	    		return "" + measurement.getDeviceID();
	    	}
	    };
	    TextColumn<Measurement> deviceNameColumn = new TextColumn<Measurement>() {
	    	@Override
	    	public String getValue(Measurement measurement) {
	    		return measurement.getDeviceName();
	    	}
	    };
	    Column<Measurement, SafeHtml> markingAColumn = new Column<Measurement, SafeHtml>(new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(Measurement measurement) {
				return measurement.getMarking().equals("alarm") ? SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("a", 24).toString()): null;
			}
	    	
	    };
	    Column<Measurement, SafeHtml> markingHColumn = new Column<Measurement, SafeHtml>(new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(Measurement measurement) {
				return measurement.getMarking().equals("hoiatus") ? SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("h", 24).toString()): null;
			}
	    	
	    };
	    Column<Measurement, SafeHtml> markingOColumn = new Column<Measurement, SafeHtml>(new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(Measurement measurement) {
				return measurement.getMarking().equals("ok") ? SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("o", 24).toString()): null;
			}
	    	
	    };
	    TextColumn<Measurement> commentColumn = new TextColumn<Measurement>() {
	    	@Override
	    	public String getValue(Measurement measurement) {
	    		return measurement.getComment() + " " + measurement.getDEcomment() + " " + measurement.getNDEcomment() + " " +
	    				measurement.getMPcomment() + " " + measurement.getTPcomment();
	    	}
	    };
	    
	    // Add the columns.
	    idColumn.setCellStyleNames("idCell");
	    table.addColumn(idColumn, "ID number.");
	    table.addColumn(deviceNameColumn, "Seadme nimetus");
	    //table.addColumn(markingAColumn, "A");
	    markingAColumn.setCellStyleNames("markingCell");
	    markingHColumn.setCellStyleNames("markingCell");
	    markingOColumn.setCellStyleNames("markingCell");
	    table.addColumn(markingAColumn, SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("a", 24).toString()));
	    table.addColumn(markingHColumn, SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("h", 24).toString()));
	    table.addColumn(markingOColumn, SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("o", 24).toString()));
	    table.addColumn(commentColumn, "Kommentaar");

	    // Set the width of the table and put the table in fixed width mode.
	    table.setWidth("100%", true);

	    // Set the width of each column.
	    table.setColumnWidth(0, "100px");
	    table.setColumnWidth(1, "28%");
	    table.setColumnWidth(2, "35px");
	    table.setColumnWidth(3, "35px");
	    table.setColumnWidth(4, "35px");
	    table.setColumnWidth(5, "72%");

	    // Set the total row count. This isn't strictly necessary, but it affects
	    // paging calculations, so its good habit to keep the row count up to date.
	    //table.setRowCount(raportDataList.size(), true);

	    // Push the data into the widget.
	    table.setRowData(0, raportDataList);
	    //scrollPanel = new ScrollPanel();
	    //scrollPanel.add(table);
	    //scrollPanel.setWidth("92%");
	    tablePanel.add(table);
	    tablePanel.setCellHorizontalAlignment(table, HasHorizontalAlignment.ALIGN_CENTER);
	    tablePanel.setWidth("100%");
	    
	    //Marking definitions panel
	    AbsolutePanel markingPanel = new AbsolutePanel();
	    markingPanel.setSize("100%", "50px");
		markingPanel.add(AhoWidgets.getAHOImage("a", 14), 0, 5);
		markingPanel.add(AhoWidgets.getAHOImage("h", 14), 0, 20);
		markingPanel.add(AhoWidgets.getAHOImage("o", 14), 0, 35);
	    Label markingA = new Label("Alarm. Oluline k\u00F5rvalekalle normist. Soovitatav tegevus");
	    Label markingH = new Label("Hoiatus. T\u00E4heldatav k\u00F5rvalekalle normist. V\u00E4lja selgitada p\u00F5hjus v\u00F5i j\u00E4lgida arengut.");
	    Label markingO = new Label("N\u00E4itajad normi piirides");
	    markingA.setStyleName("smallTextLabel");
	    markingH.setStyleName("smallTextLabel");
	    markingO.setStyleName("smallTextLabel");
	    markingPanel.add(markingA, 25, 5);
	    markingPanel.add(markingH, 25, 20);
	    markingPanel.add(markingO, 25, 35);
	    tablePanel.add(markingPanel);
	    return tablePanel;
	}
	
	/**
	 * Create data table with measurement data.
	 * @return AbsolutePanel containing measurement data table.
	 */
	private VerticalPanel createNewDataTable() {
		CellTable.Resources historyTableRes = GWT.create(TableRes.class);
		VerticalPanel tablePanel = new VerticalPanel();
		tablePanel.setStyleName("aho-panel1 table2");
		tablePanel.setWidth("100%");
		// Create a CellTable with corresponding columns.
	    CellTable<Measurement> table = new CellTable<Measurement>(raportDataList.size(), historyTableRes);
	    TextColumn<Measurement> idColumn = new TextColumn<Measurement>() {
	    	@Override
	    	public String getValue(Measurement measurement) {
	    		return "" + measurement.getDeviceID();
	    	}
	    };
	    TextColumn<Measurement> deviceNameColumn = new TextColumn<Measurement>() {
	    	@Override
	    	public String getValue(Measurement measurement) {
	    		return measurement.getDeviceName();
	    	}
	    };
	    TextColumn<Measurement> companyname = new TextColumn<Measurement>() {
	    	@Override
	    	public String getValue(Measurement measurement) {
	    			return raports.get(0).getCompanyName();
	    	}
	    };
	    Column<Measurement, SafeHtml> markingAColumn = new Column<Measurement, SafeHtml>(new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(Measurement measurement) {
				return measurement.getMarking().equals("alarm") ? SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("a", 24).toString()): null;
			}
	    	
	    };
	    Column<Measurement, SafeHtml> markingHColumn = new Column<Measurement, SafeHtml>(new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(Measurement measurement) {
				return measurement.getMarking().equals("hoiatus") ? SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("h", 24).toString()): null;
			}
	    	
	    };
	    Column<Measurement, SafeHtml> markingOColumn = new Column<Measurement, SafeHtml>(new SafeHtmlCell()) {

			@Override
			public SafeHtml getValue(Measurement measurement) {
				return measurement.getMarking().equals("ok") ? SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("o", 24).toString()): null;
			}
	    	
	    };
	    TextColumn<Measurement> commentColumn = new TextColumn<Measurement>() {
	    	@Override
	    	public String getValue(Measurement measurement) {
	    		return measurement.getComment() + " " + measurement.getDEcomment() + " " + measurement.getNDEcomment() + " " +
	    				measurement.getMPcomment() + " " + measurement.getTPcomment();
	    	}
	    };
	    // Add the columns.
	    table.addColumn(markingAColumn, SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("a", 24).toString()));
	    table.addColumn(markingHColumn, SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("h", 24).toString()));
	    table.addColumn(markingOColumn, SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("o", 24).toString()));
	    table.addColumn(companyname, "Osakond");
	    table.addColumn(deviceNameColumn, "\u00DCksus");
	    table.addColumn(idColumn, "ID nr.");
	    table.addColumn(deviceNameColumn, "Seade");
	    //table.addColumn(markingAColumn, "A");
	    markingAColumn.setCellStyleNames("markingCell");
	    markingHColumn.setCellStyleNames("markingCell");
	    markingOColumn.setCellStyleNames("markingCell");
	    
	    table.addColumn(commentColumn, "Kommentaar");

	    // Set the width of the table and put the table in fixed width mode.
	    table.setWidth("100%", true);

	    // Set the width of each column.
	    table.setColumnWidth(0, "35px"); //id nr.
	    table.setColumnWidth(1, "35px"); //seadme nimetus
	    table.setColumnWidth(2, "35px"); //
	    table.setColumnWidth(3, "100px");//
	    table.setColumnWidth(4, "100px");//	
	    table.setColumnWidth(5, "72%");//kommentaar

	    // Set the total row count. This isn't strictly necessary, but it affects
	    // paging calculations, so its good habit to keep the row count up to date.
	    //table.setRowCount(raportDataList.size(), true);

	    // Push the data into the widget.
	    table.setRowData(0, raportDataList);
	    //scrollPanel = new ScrollPanel();
	    //scrollPanel.add(table);
	    //scrollPanel.setWidth("92%");
	    tablePanel.add(table);
	    tablePanel.setCellHorizontalAlignment(table, HasHorizontalAlignment.ALIGN_CENTER);
	    tablePanel.setWidth("100%");
	    
	    //Marking definitions panel
	    AbsolutePanel markingPanel = new AbsolutePanel();
	    markingPanel.setSize("100%", "50px");
		markingPanel.add(AhoWidgets.getAHOImage("a", 14), 0, 5);
		markingPanel.add(AhoWidgets.getAHOImage("h", 14), 0, 20);
		markingPanel.add(AhoWidgets.getAHOImage("o", 14), 0, 35);
	    Label markingA = new Label("Alarm. Oluline k\u00F5rvalekalle normist. Soovitatav tegevus");
	    Label markingH = new Label("Hoiatus. T\u00E4heldatav k\u00F5rvalekalle normist. V\u00E4lja selgitada p\u00F5hjus v\u00F5i j\u00E4lgida arengut.");
	    Label markingO = new Label("N\u00E4itajad normi piirides");
	    markingA.setStyleName("smallTextLabel");
	    markingH.setStyleName("smallTextLabel");
	    markingO.setStyleName("smallTextLabel");
	    markingPanel.add(markingA, 25, 5);
	    markingPanel.add(markingH, 25, 20);
	    markingPanel.add(markingO, 25, 35);
	    tablePanel.add(markingPanel);
	    return tablePanel;
	}
	
	public static Raport getSelectedRaport() {
		return selectedRaport;
	}
	
	public static List<Measurement> getRaportData() {
		return raportDataList;
	}
	
	/**
	 * Table resources interface for deeper GWT table customization.
	 * @author rando
	 *
	 */
	public interface TableRes extends CellTable.Resources {

		@NotStrict
		@Source({CellTable.Style.DEFAULT_CSS, "Table.css"})
		TableStyle cellTableStyle();
		
		interface TableStyle extends CellTable.Style {}
		}

}
