package com.elektrimasinad.aho.client;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

public class AhoWidgets {

	/**
	 * Create content header panel.
	 * @param labelText	- content header text.
	 * @return HorizontalPanel content header panel.
	 */
	public static HorizontalPanel createContentHeader(String labelText) {
		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.setStyleName("aho-measurementHeaderPanel");
		Label lDeviceHeader = new Label(labelText);
		lDeviceHeader.setStyleName("aho-label2");
		headerPanel.add(lDeviceHeader);
		
		return headerPanel;
	}
	
	/**
	 * Create content header panel.
	 * @param labelText	- content header text.
	 * @return HorizontalPanel content header panel.
	 */
	public static HorizontalPanel createThinContentHeader(String labelText) {
		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.setStyleName("aho-measurementHeaderPanel thin");
		Label lDeviceHeader = new Label(labelText);
		lDeviceHeader.setStyleName("aho-label2 thin");
		headerPanel.add(lDeviceHeader);
		
		return headerPanel;
	}
	
	/**
	 * Create label using input style with input text and align it according to input alignment constant.
	 * @param labelText
	 * @param styleName
	 * @param alignment
	 * @return
	 */
	public static Label createLabel(String labelText, String styleName, HorizontalAlignmentConstant alignment) {
		Label label = new Label(labelText);
		label.setStyleName(styleName);
		if (alignment != null) {
			label.setHorizontalAlignment(alignment);
		}
		return label;
	}
	
	/**
	 * Create textbox using input style and input text.
	 * @param styleName
	 * @param text
	 * @return
	 */
	public static TextBox createTextbox(String styleName, String text) {
		TextBox tbox = new TextBox();
		tbox.setStyleName(styleName);
		tbox.addStyleName("grayPlaceholder");
		tbox.setText(text);
		return tbox;
	}
	
	/**
	 * Create textbox using input style, input text and placeholder.
	 * @param styleName
	 * @param text
	 * @param placeholder
	 * @return
	 */
	public static TextBox createTextbox(String styleName, String text, String placeholder) {
		TextBox tbox = new TextBox();
		tbox.setStyleName(styleName);
		tbox.getElement().setAttribute("placeholder", placeholder);
		tbox.addStyleName("grayPlaceholder");
		tbox.setText(text);
		return tbox;
	}
	
	/**
	 * Create single row data panel with input widgets and style.
	 * @param leftWidget
	 * @param rightWidget
	 * @param styleName
	 * @return
	 */
	public static HorizontalPanel createSingleRowDataPanel(Widget leftWidget, Widget rightWidget, String styleName) {
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setStyleName(styleName);
		hPanel.setWidth("100%");
		hPanel.add(leftWidget);
		hPanel.add(rightWidget);
		hPanel.setCellHorizontalAlignment(rightWidget, HasHorizontalAlignment.ALIGN_RIGHT);
		
		return hPanel;
	}
	
	/**
	 * Method for easy creation of AHO letter images.
	 * @param imageLetter - A,H or O letter for corresponding image.
	 * @param size - image width and height.
	 * @return corresponding image with input size.
	 */
	public static Image getAHOImage(String imageLetter, int size) {
		Image image = new Image("res/aho_" + imageLetter + ".png");
		image.setPixelSize(size, size);
		image.setAltText(imageLetter.toUpperCase());
		return image;
	}
}
