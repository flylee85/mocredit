package com.mocredit.manage.vo;

import java.util.List;

import com.mocredit.manage.model.Contract;
import com.mocredit.manage.model.Merchant;

public class ContractVO extends Contract {
	private List<Merchant> allMerchant;

	public List<Merchant> getAllMerchant() {
		return allMerchant;
	}

	public void setAllMerchant(List<Merchant> allMerchant) {
		this.allMerchant = allMerchant;
	}
	
}
