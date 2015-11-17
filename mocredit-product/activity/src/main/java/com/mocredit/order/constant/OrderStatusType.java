/**
 *
 */
package com.mocredit.order.constant;

/**
 * @author ytq
 */
public enum OrderStatusType {
    SEND("01", "已发送"), EXCHANGE("02", "已兑换"), REVOCATION("03", "已撤回");
    private String value;
    private String text;

    OrderStatusType(String value, String text) {
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
