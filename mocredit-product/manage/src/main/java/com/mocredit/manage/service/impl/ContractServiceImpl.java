package com.mocredit.manage.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.IDUtil;
import com.mocredit.manage.model.Contract;
import com.mocredit.manage.model.ContractMerchant;
import com.mocredit.manage.persitence.ContractMapper;
import com.mocredit.manage.persitence.ContractMerchantMapper;
import com.mocredit.manage.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {
	@Autowired
	private ContractMapper contractMapper;
	@Autowired
	private ContractMerchantMapper contractMerchantMapper;

	@Override
	public PageInfo<Contract> getPage(String key, String enterpriseId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		if (null != key) {
			key = key.trim();
			if (!key.isEmpty()) {
				key = "%" + key + "%";
			} else {
				key = null;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enterpriseId", key);
		map.put("enterpriseId", enterpriseId);
		List<Contract> list = contractMapper.selectAllForPage(map);
		return new PageInfo<Contract>(list);
	}

	@Override
	@Transactional
	public int add(Contract contract) {
		contract.setId(IDUtil.getID());
		contract.setCreateTime(new Date());
		contract.setStatus(Contract.STATUS_ACTIVED);
		// 合同商户关系
		if (null != contract.getMerchantList() && !contract.getMerchantList().isEmpty()) {
			for (ContractMerchant cm : contract.getMerchantList()) {
				cm.setContractId(contract.getId());
			}
			contractMerchantMapper.insertBatch(contract.getMerchantList());
		}
		return contractMapper.insert(contract);
	}

	@Override
	@Transactional
	public int update(Contract contract) {
		int update = contractMapper.update(contract);
		if (update > 0) {
			/* 先清空合同商户关系 */
			List<String> ids = new ArrayList<>();
			ids.add(contract.getId());
			contractMerchantMapper.deleteByContractId(ids);

			/* 再重新添加新的合同商户关系 */
			if (null != contract.getMerchantList() && !contract.getMerchantList().isEmpty()) {
				for (ContractMerchant cm : contract.getMerchantList()) {
					cm.setContractId(contract.getId());
				}
				contractMerchantMapper.insertBatch(contract.getMerchantList());
			}
		}
		return update;
	}

	@Override
	public int delete(String id) {
		if (null == id || id.isEmpty()) {
			return 0;
		}
		String[] ids = id.split(",");
		List<String> list = new ArrayList<>();
		Collections.addAll(list, ids);
		return contractMapper.deleteById(list);
	}

	@Override
	public Contract getContractById(String id) {
		Contract contract = contractMapper.selectOne(id);
		if (null != contract) {
			List<ContractMerchant> merchants = contractMerchantMapper.selectAllByContractId(id);
			contract.setMerchantList(merchants);
		}
		return contract;
	}
}
