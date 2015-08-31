package com.mocredit.bank.persistence;

import java.util.List;
import java.util.Map;

import com.mocredit.bank.entity.TiShopMerchant;

public interface TiShopMerchantMapper {

	TiShopMerchant selectByShopId(int shopId);
	List<TiShopMerchant> selectMerchantsByColumn(Map<String, Object> params);
	List<TiShopMerchant> selectValidMerchants(String bank);
	int updateToken(TiShopMerchant merchant);
	int save(TiShopMerchant merchant);
}