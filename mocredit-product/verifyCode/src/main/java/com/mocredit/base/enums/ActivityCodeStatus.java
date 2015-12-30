package com.mocredit.base.enums;

/**
 * 枚举类。
 * 表示 码的状态：未使用、已使用、已禁用等等
 * Created by YHL on 15/7/17 14:18.
 *
 * @version 1.0
 * @Auth liaoy
 */
public enum ActivityCodeStatus {
	NOT_USED("未使用","01"),
    USED("已使用","02"),
    DISABLED("已禁用","03");

    private ActivityCodeStatus(String name, String value ) {
		this.value = value;
		this.name = name;
	}

	/**  值 **/
    private String value;

    private  String name ;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    

}
