package com.mocredit.bank.service.impl;

import java.util.List;

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
	public List<TiShopMerchant> getAllMerchants() {
		return mapper.selectAllMerchants();
	}

	@Override
	public int updateToken(TiShopMerchant merchant) {
		return mapper.updateToken(merchant);
	}

}
