package com.mocredit.sendcode.constant;

/**
 * @author ytq
 */
public enum BatchCodeStatus {
    //batch_code 状态，状态暂定为01：已提码，02：已导入，03：已送码，未发码，04：已发码
    TI_CODE("01", "已提码"), IMPORT_CODE("02", "已导入"), CARRY_NOT_SEND("03", "已送码，未发码"), ALREADY_SEND("04", "已发码");
    private String value;
    private String text;

    BatchCodeStatus(String value, String text) {
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
