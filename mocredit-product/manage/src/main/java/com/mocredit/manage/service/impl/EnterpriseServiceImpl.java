package com.mocredit.manage.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.IDUtil;
import com.mocredit.manage.model.Enterprise;
import com.mocredit.manage.persitence.EnterpriseMapper;
import com.mocredit.manage.service.EnterpriseService;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
	@Autowired
	private EnterpriseMapper enterpriseMapper;

	@Override
	public PageInfo<Enterprise> getPage(String key, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		if(null!=key){
			key=key.trim();
			if(!key.isEmpty()){
				key="%"+key+"%";
			}else{
				key=null;
			}
		}
		List<Enterprise> list = enterpriseMapper.selectAllForPage(key);
		return new PageInfo<Enterprise>(list);
	}

	@Override
	public int add(Enterprise enterprise) {
		enterprise.setId(IDUtil.getID());
		enterprise.setCreateTime(new Date());
		enterprise.setStatus(Enterprise.STATUS_ACTIVED);
		return enterpriseMapper.insert(enterprise);
	}

	@Override
	public int update(Enterprise enterprise) {
		return enterpriseMapper.update(enterprise);
	}

	@Override
	public int delete(String id) {
		if (null == id || id.isEmpty()) {
			return 0;
		}
		String[] ids = id.split(",");
		List<String> list = new ArrayList<>();
		Collections.addAll(list, ids);
		return enterpriseMapper.deleteById(list);
	}

	@Override
	public Enterprise getEnterpriseById(String id) {
		return enterpriseMapper.selectOne(id);
	}
}
