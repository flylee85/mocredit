package com.mocredit.sendcode.constant;

/**
 * @author ytq部分
 */
public enum BatchStatus {
    //batch 00：已删除 01：已提码，未导入联系人  02：已导入联系人，待送码  03：已送码，待发码 04：已发码
    DEL("00", "已删除"), TI_CODE_NOT_IMPORT("01", "已提码，未导入联系人 "), IMPORT_NOT_CARRY("02", "已导入联系人，待送码"),
    CARRY_NOT_SEND("03", "已送码，待发码"), ALREADY_SEND("04", "已发码"),PART_ALREADY_SEND("05", "部分已发码");
    private String value;
    private String text;

    BatchStatus(String value, String text) {
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
