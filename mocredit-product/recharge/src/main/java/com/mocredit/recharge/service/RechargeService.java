package com.mocredit.recharge.service;

import java.util.Map;

public interface RechargeService {
	public String rechargeByCode(String code, String phone, String server);

	public boolean notice(Map<String, Object> param, String channel);
}
