package com.mocredit.bank.service;

import java.util.List;

import com.mocredit.bank.entity.TiShopMerchant;

public interface ShopMerchantService {
	List<TiShopMerchant> getMerchantsByBank(String bank);
	List<TiShopMerchant> getValidMerchantsByBank(String bank);
	TiShopMerchant getMerchantByTermId(String terminalId);
	int updateToken(TiShopMerchant merchant);
	int save(TiShopMerchant merchant);
}
