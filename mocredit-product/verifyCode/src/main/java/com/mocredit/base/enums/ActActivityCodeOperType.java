package com.mocredit.base.enums;

/**
 * 枚举类。
 * 表示 活动通过接口请求的时候，传递一个要操作的类型：码导入、码删除、活动取消等等
 * Created by YHL on 15/7/17 14:18.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public enum ActActivityCodeOperType {
    IMPORT("码导入",1),
    UPDATE("活动更新",2),
    CANCEL("活动取消",3),
    START_USING("启用",4)
    ;


    /**  值 **/
    private int value;

    private  String name ;

    ActActivityCodeOperType(String name,int value){
        this.value=value;
        this.name=name;
    }


    //-------静态方法-----------

    public static String getNameByValue(int _value){
        for (ActActivityCodeOperType c : ActActivityCodeOperType.values()) {
            if (c.getValue() == _value) {
                return c.getName();
            }
        }
        return null;
    }

    public static ActActivityCodeOperType getEnumTypeByValue(int _value){
        for (ActActivityCodeOperType c : ActActivityCodeOperType.values()) {
            if (c.getValue() == _value) {
                return c;
            }
        }
        return null;
    }


    //-----------GETTER/SETTER------------\\
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
