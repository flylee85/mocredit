package com.mocredit.sendcode.constant;

/**
 * @author ytq
 */
public enum SendType {
    BREAKPOINT_SEND("01", "断点续发"), ALL_SEND("02", "全部发送");
    private String value;
    private String text;

    SendType(String value, String text) {
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
