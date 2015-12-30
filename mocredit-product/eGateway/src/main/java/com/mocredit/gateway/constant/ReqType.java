package com.mocredit.gateway.constant;

public enum ReqType {
    PAYMENT("01", "消费"),
    PAYMENT_REVOKE("02", "消费撤销"),
    PAYMENT_REVERSAL("03", "冲正"),
    PAYMENT_REVERSAL_REVOKE("04", "冲正撤销");
    private String value;
    private String text;

    ReqType(String value, String text) {
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
