package com.mocredit.bank.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.bank.entity.TiShopMerchant;
import com.mocredit.bank.persistence.TiShopMerchantMapper;
import com.mocredit.bank.service.ShopMerchantService;

@Service("shopMerchantService")
public class ShopMerchantServiceImpl implements ShopMerchantService {
	@Autowired
	private TiShopMerchantMapper mapper;

	@Override
	public List<TiShopMerchant> getMerchantsByBank(String bank) {
		Map<String, Object> columns=new HashMap<String, Object>();
		columns.put("bank", bank);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("params", columns);
		return mapper.selectMerchantsByColumn(params);
	}
@Override
public List<TiShopMerchant> getValidMerchantsByBank(String bank) {
	Map<String, Object> columns=new HashMap<String, Object>();
	columns.put("bank", bank);
	columns.put("status", 1);
	Map<String, Object> params=new HashMap<String, Object>();
	params.put("params", columns);
	return mapper.selectMerchantsByColumn(params);
}
	@Override
	public int updateToken(TiShopMerchant merchant) {
		return mapper.updateToken(merchant);
	}
@Override
public TiShopMerchant getMerchantByTermId(String terminalId) {
	Map<String, Object> columns=new HashMap<String, Object>();
	columns.put("terminal_id", terminalId);
	Map<String, Object> params=new HashMap<String, Object>();
	params.put("params", columns);
	return mapper.selectMerchantsByColumn(params).get(0);
}
@Override
public int save(TiShopMerchant merchant) {
	return mapper.save(merchant);
}
}
