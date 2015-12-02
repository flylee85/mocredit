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
import com.mocredit.manage.model.Merchant;
import com.mocredit.manage.model.Store;
import com.mocredit.manage.persitence.MerchantMapper;
import com.mocredit.manage.persitence.StoreMapper;
import com.mocredit.manage.service.MerchantService;
import com.mocredit.manage.service.StoreService;

@Service
public class MerchantServiceImpl implements MerchantService {
	@Autowired
	private MerchantMapper merchantMapper;
	@Autowired
	private StoreMapper storeMapper;
	@Autowired
	private StoreService storeService;

	@Override
	public PageInfo<Merchant> getPage(String key, String contractId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		if (null != key) {
			key = key.trim();
			if (!key.isEmpty()) {
				key = "%" + key + "%";
			} else {
				key = null;
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("key", key);
		map.put("contractId", contractId);
		List<Merchant> list = merchantMapper.selectAllForPage(map);
		return new PageInfo<Merchant>(list);
	}

	@Override
	public int add(Merchant merchant) {
		merchant.setId(IDUtil.getID());
		merchant.setCreateTime(new Date());
		merchant.setStatus(Merchant.STATUS_ACTIVED);
		return merchantMapper.insert(merchant);
	}

	@Override
	public int update(Merchant merchant) {
		return merchantMapper.update(merchant);
	}

	@Transactional
	@Override
	public int delete(String id) {
		if (null == id || id.isEmpty()) {
			return 0;
		}
		String[] ids = id.split(",");
		List<String> list = new ArrayList<>();
		Collections.addAll(list, ids);
		int count = merchantMapper.deleteById(list);
		if (count > 0) {
			// 删除商户后删除商户下的门店和机具，并触发同步
			for (String merchantId : ids) {
				Map<String, Object> storeParam = new HashMap<>();
				storeParam.put("merchantId", merchantId);
				List<Store> stores = storeMapper.selectAllForPage(storeParam);
				for (Store store : stores) {
					storeService.delete(store.getId());
				}
			}
		}
		return count;
	}

	@Override
	public Merchant getMerchantById(String id) {
		return merchantMapper.selectOne(id);
	}

	@Override
	public List<Merchant> getAll() {
		// TODO Auto-generated method stub
		return merchantMapper.selectAll();
	}
}
