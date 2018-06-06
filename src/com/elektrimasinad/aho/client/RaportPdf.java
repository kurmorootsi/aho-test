package com.elektrimasinad.aho.client;

import java.util.List;

import com.elektrimasinad.aho.client.Raports.TableRes;
import com.elektrimasinad.aho.client.Raports.TableRes.TableStyle;
import com.elektrimasinad.aho.shared.Measurement;
import com.elektrimasinad.aho.shared.Raport;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RaportPdf implements EntryPoint {
	
	private static final DeviceTreeServiceAsync deviceTreeService = DeviceCard.getDevicetreeservice();
	protected AsyncCallback<Raport> getRaportCallback;
	protected AsyncCallback<List<Measurement>> getRaportDataCallback;
	RootPanel raportPanel;
	private List<Measurement> raportDataList;
	private Raport selectedRaport;

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
		getRaportCallback = new AsyncCallback<Raport>() {
			
			@Override
			public void onSuccess(Raport raport) {
				//System.out.println(name);
				if (raport != null) {
					selectedRaport = raport;
					deviceTreeService.getRaportData(selectedRaport, getRaportDataCallback);
				}
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
				createRaport();
				Timer timer = new Timer()
		        {
		            @Override
		            public void run()
		            {
		            	Window.print();
		            }
		        };

		        timer.schedule(1000);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught);
			}
			
		};
		
		raportPanel = RootPanel.get("content");
		//createRaport();
		String raportKeyString = Window.Location.getParameter("raport");
		deviceTreeService.getRaport(raportKeyString, getRaportCallback);
		
	}
	
	private void createRaport() {
		raportPanel.clear();
		raportPanel.setWidth("690px");
		
		//Header panel
		HorizontalPanel raportHeaderPanel = AhoWidgets.createContentHeader("Raport");
		raportPanel.add(raportHeaderPanel);
		
		//Top info panel
		Widget lCompanyName = AhoWidgets.createLabel("Ettev\u00F5te", "aho-label1", null);
		Widget inputCompanyName = AhoWidgets.createLabel(selectedRaport.getCompanyName(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
		Widget lRaportNr = AhoWidgets.createLabel("Esmane raport nr.", "aho-label1", null);
		Label inputRaportNr = AhoWidgets.createLabel(selectedRaport.getRaportID(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
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
		
		//Bottom info panel
		Widget lMeasurerInfo = AhoWidgets.createLabel("M\u00F5\u00F5tmisi teostas:", "aho-label1", null);
		Widget lDate = AhoWidgets.createLabel("Kuup\u00E4ev", "aho-label1", null);
		Label inputMeasurerName = AhoWidgets.createLabel(selectedRaport.getMeasurerName(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
		Label inputMeasurerPhone = AhoWidgets.createLabel(selectedRaport.getMeasurerPhone(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
		Label inputDate = AhoWidgets.createLabel(selectedRaport.getDate(), "aho-label1 alignRight", HasHorizontalAlignment.ALIGN_RIGHT);
		HorizontalPanel measurerPanel = AhoWidgets.createSingleRowDataPanel(lMeasurerInfo, inputMeasurerName, "aho-panel1 closeBottom");
		raportPanel.add(measurerPanel);
		HorizontalPanel measurerPhonePanel = AhoWidgets.createSingleRowDataPanel(AhoWidgets.createLabel("", "aho-label1", null), inputMeasurerPhone, "aho-panel1");
		raportPanel.add(measurerPhonePanel);
		HorizontalPanel dateTimePanel = AhoWidgets.createSingleRowDataPanel(lDate, inputDate, "aho-panel1");
		raportPanel.add(dateTimePanel);
		
		AbsolutePanel emptyPanel = new AbsolutePanel();
		emptyPanel.setStyleName("aho-markingBlankPanel");
		raportPanel.add(emptyPanel);
	}
	
	/**
	 * Create data table with measurement data.
	 * @return AbsolutePanel containing measurement data table.
	 */
	private VerticalPanel createDataTable() {
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
	    idColumn.setCellStyleNames("idCell bottomBorder");
	    table.addColumn(idColumn, "ID nr.");
	    deviceNameColumn.setCellStyleNames("bottomBorder");
	    table.addColumn(deviceNameColumn, "Seadme nimetus");
	    //table.addColumn(markingAColumn, "A");
	    markingAColumn.setCellStyleNames("markingCell bottomBorder");
	    markingHColumn.setCellStyleNames("markingCell bottomBorder");
	    markingOColumn.setCellStyleNames("markingCell bottomBorder");
	    table.setRowStyles(new RowStyles<Measurement>() {
	        @Override
	        public String getStyleNames(Measurement rowObject, int rowIndex) {
	            return "rowBorder";
	        }});
	    table.addColumn(markingAColumn, SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("a", 24).toString()));
	    table.addColumn(markingHColumn, SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("h", 24).toString()));
	    table.addColumn(markingOColumn, SafeHtmlUtils.fromTrustedString(AhoWidgets.getAHOImage("o", 24).toString()));
	    commentColumn.setCellStyleNames("bottomBorder");
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
