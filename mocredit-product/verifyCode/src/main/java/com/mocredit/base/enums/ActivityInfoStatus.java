package com.mocredit.base.enums;

/**
 * 活动状态
 * Created by lenovo on 2016/1/27.
 */
public enum ActivityInfoStatus {
    STARTING("活动启用","01"),CANCELED("活动停用","02");
    private String name;
    private String value;

    ActivityInfoStatus(String name,String value){
        this.name=name;
        this.value=value;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
