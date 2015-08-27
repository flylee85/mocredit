package com.mocredit.bank.service;

import java.util.List;

import com.mocredit.bank.entity.TiShopMerchant;

public interface ShopMerchantService {
	List<TiShopMerchant> getAllMerchants();
	int updateToken(TiShopMerchant merchant);
}
