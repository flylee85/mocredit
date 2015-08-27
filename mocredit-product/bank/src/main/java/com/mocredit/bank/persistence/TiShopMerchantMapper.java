package com.mocredit.bank.persistence;

import java.util.List;

import com.mocredit.bank.entity.TiShopMerchant;

public interface TiShopMerchantMapper {

	TiShopMerchant selectByShopId(int shopId);
	List<TiShopMerchant> selectAllMerchants();
	List<TiShopMerchant> selectValidMerchants();
	int updateToken(TiShopMerchant merchant);
}