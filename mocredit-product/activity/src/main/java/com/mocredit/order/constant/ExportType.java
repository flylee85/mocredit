/**
 * 
 */
package com.mocredit.order.constant;

/**
 * @author ytq
 * 
 */
public enum ExportType {
	XLSX("XLSX", "xlsx格式"), CSV("CSV", "csv格式");
	private String value;
	private String text;

	ExportType(String value, String text) {
		this.value = value;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
