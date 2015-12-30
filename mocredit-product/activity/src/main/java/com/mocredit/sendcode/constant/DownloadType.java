package com.mocredit.sendcode.constant;

/**
 * @author ytq
 */
public enum DownloadType {
    CODE("01", "验码"), BATCH("02", "批次"), ACTIVITY("03", "活动");
    private String value;
    private String text;

    DownloadType(String value, String text) {
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
