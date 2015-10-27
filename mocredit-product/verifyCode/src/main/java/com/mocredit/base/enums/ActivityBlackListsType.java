package com.mocredit.base.enums;

/**
 *
 * 枚举类型：表示加入黑名单的类型以及信息
 * Created by YHL on 15/7/19 08:45.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public enum ActivityBlackListsType {

    CANCEL("取消活动",0),
    LOCKING("数据更新,暂时锁定",1)
    ;

    private int value=0;

    private String text;


    ActivityBlackListsType(String text,int value){
        this.text=text;
        this.value=value;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
