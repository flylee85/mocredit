package com.mocredit.base.enums;

/**
 * Created by YHL on 15/7/9 15:20.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public enum VerifyCodeStatus {
    /** 验码核销 **/
    VERIFYCODE("验码核销",0),
    /** 冲正 **/
    CORRECT("冲正",1),
    /** 撤销**/
    REVOKE("撤销",2)
    ;

    private String name;
    private int value;

    private VerifyCodeStatus(String _name, int _value){
        this.name=_name;
        this.value=_value;
    }


    //-------静态方法-----------

    public static String getNameByValue(int _value){
        for (VerifyCodeStatus c : VerifyCodeStatus.values()) {
            if (c.getValue() == _value) {
                return c.getName();
            }
        }
        return null;
    }

    public static VerifyCodeStatus getEnumTypeByValue(int _value){
        for (VerifyCodeStatus c : VerifyCodeStatus.values()) {
            if (c.getValue() == _value) {
                return c;
            }
        }
        return null;
    }

    //---------GETTER/SETTER------------\\

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
