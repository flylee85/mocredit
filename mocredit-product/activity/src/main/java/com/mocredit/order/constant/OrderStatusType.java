/**
 *
 */
package com.mocredit.order.constant;

/**
 * @author ytq
 */
public enum OrderStatusType {
    EXCHANGE("01", "已兑换"), SEND("02", "已发送"), REVOCATION("03", "已撤回");
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
