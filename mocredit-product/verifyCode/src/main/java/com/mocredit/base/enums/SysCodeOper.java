package com.mocredit.base.enums;

/**
 * 系统操作码 代码
 * 
 * @author liaoy
 *
 */
public enum SysCodeOper {
	REVOKE("码撤销", 1), DELAY("码延期", 2), DEFAULT("默认操作 撤销", 999);

	/** 值 **/
	private int value;

	private String name;

	SysCodeOper(String name, int value) {
		this.value = value;
		this.name = name;
	}

	// -------静态方法-----------

	public static String getNameByValue(int _value) {
		for (SysCodeOper c : SysCodeOper.values()) {
			if (c.getValue() == _value) {
				return c.getName();
			}
		}
		return DEFAULT.getName();
	}

	public static SysCodeOper getEnumTypeByValue(int _value) {
		for (SysCodeOper c : SysCodeOper.values()) {
			if (c.getValue() == _value) {
				return c;
			}
		}
		return DEFAULT;
	}

	// -----------GETTER/SETTER------------\\
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
