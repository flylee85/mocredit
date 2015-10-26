package com.mocredit.base.util;

import java.util.List;

public class ExcelExportInfo {
	private List datas;

	private String columnHeader[];

	private String feilds[];

    private String defaultValue[];

    private String defaultFormat[];

	private String footer[][];

	private String title;

	private int width[];

	private String fileName = "ExcelExport.xls";

    public String[] getDefaultFormat() {
        return defaultFormat;
    }

    public void setDefaultFormat(String[] defaultFormat) {
        this.defaultFormat = defaultFormat;
    }

    public void setDefaultFormats(String defaultFormats) {
        if (defaultFormats != null)
			this.defaultFormat = defaultFormats.split(",");
		else
			this.defaultFormat = null;
    }

    public void setDefaultValue(String[] defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDefaultValues(String defaultValues){
        if (defaultValues != null)
			this.defaultValue = defaultValues.split(",");
		else
			this.defaultValue = null;
    }

    public String[] getDefaultValue() {
        return defaultValue;
    }

    public String[] getColumnHeader() {
		return columnHeader;
	}

	public void setColumnHeader(String[] columnHeader) {
		this.columnHeader = columnHeader;
	}

	public void setColumnHeader(String columnHeaders) {
		if (columnHeaders != null)
			this.columnHeader = columnHeaders.split(",");
		else
			this.columnHeader = null;
	}

	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas = datas;
	}

	public String[] getFeilds() {
		return feilds;
	}

	public void setFeilds(String[] feilds) {
		this.feilds = feilds;
	}

	public void setFeilds(String feilds) {
		if (feilds != null)
			this.feilds = feilds.split(",");
		else
			this.feilds = null;
	}

	public String[][] getFooter() {
		return footer;
	}

	public void setFooter(String[][] footer) {
		this.footer = footer;
	}

	public int[] getWidth() {
		return width;
	}

	public void setWidth(int[] width) {
		this.width = width;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
