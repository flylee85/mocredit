package com.mocredit.manage.constant;

/**
 * 门店业务状态
 * 
 * @author lenovo
 *
 */
public enum StoreBusinessStatus {
	/**
	 * 正常
	 */
	NORMAL("正常", 1),
	/**
	 * 调整中
	 */
	ADJUSTING("调整中", 1),
	/**
	 * 闭店
	 */
	CLOSED("闭店", 1),
	/**
	 * 不参加
	 */
	NO_JOIN("不参加", 1);
	private String name;
	private int value;

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	private StoreBusinessStatus(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public static String getName(int status) {
		for (StoreBusinessStatus st : values()) {
			if (status == st.getValue()) {
				return st.getName();
			}
		}
		return "";
	}
}
