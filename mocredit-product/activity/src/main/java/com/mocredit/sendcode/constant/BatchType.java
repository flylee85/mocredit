package com.mocredit.sendcode.constant;

/**
 * @author ytq部分
 */
public enum BatchType {
    MERGE_SMS("01", "合并发送"), NOT_MERGE_SMS("02", "不合并发送"), DOWNLOAD("03", "提码");
    private String value;
    private String text;

    BatchType(String value, String text) {
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
