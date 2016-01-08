package com.mocredit.base.enums;

public enum VerifyLogCode {
	VERIFY_SUCCESS(1, "验码成功"),
	REVOKE_SUCCESS(2, "撤销成功"),
	VERIFY_NOT_STARTED(3,"活动还未开始"),
	VERIFY_HAS_ENDED(4,"活动已结束"),
	VERIFY_INVALID_WEEK(5,"不在适用星期范围内"),
	VERIFY_INVALID_STORE(6,"不适用于当前门店"),
	VERIFY_HAS_USED(7,"码已使用"),
	VERIFY_INVALID_CHANNEL(9,"不能用于该兑换方式"),
	VERIFY_INVALID_PHONE(10,"不适用的手机号"),
	VERIFY_HAS_DISBALED(11,"码已禁用"),
	
	REVOKE_HAS_REVOKED(8,"已撤销过");

	private int value;
	private String name;

	private VerifyLogCode(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static String getName(int value){
		for(VerifyLogCode code:values()){
			if(code.getValue()==value){
				return code.getName();
			}
		}
		return null;
	}
}
