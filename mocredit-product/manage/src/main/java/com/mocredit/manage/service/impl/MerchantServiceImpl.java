package com.mocredit.manage.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.IDUtil;
import com.mocredit.manage.model.Merchant;
import com.mocredit.manage.persitence.MerchantMapper;
import com.mocredit.manage.service.MerchantService;

@Service
public class MerchantServiceImpl implements MerchantService {
	@Autowired
	private MerchantMapper merchantMapper;

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

	@Override
	public int delete(String id) {
		if (null == id || id.isEmpty()) {
			return 0;
		}
		String[] ids = id.split(",");
		List<String> list = new ArrayList<>();
		Collections.addAll(list, ids);
		return merchantMapper.deleteById(list);
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
