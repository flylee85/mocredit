package com.mocredit.manage.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mocredit.base.exception.BusinessException;
import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.IDUtil;
import com.mocredit.manage.constant.Constant;
import com.mocredit.manage.model.Enterprise;
import com.mocredit.manage.persitence.ContractMapper;
import com.mocredit.manage.persitence.EnterpriseMapper;
import com.mocredit.manage.service.EnterpriseService;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
	@Autowired
	private EnterpriseMapper enterpriseMapper;
	@Autowired
	private ContractMapper contractMapper;

	@Override
	public PageInfo<Enterprise> getPage(String key, String startTime, String endTime, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		if (null != key) {
			key = key.trim();
			if (!key.isEmpty()) {
				key = "%" + key + "%";
			} else {
				key = null;
			}
		}
		Date startDate = null;
		Date endDate = null;
		if (!StringUtils.isEmpty(startTime)) {
			startDate = DateUtil.strToDate(startTime, "yyyy-MM-dd");
		}
		if (!StringUtils.isEmpty(endTime)) {
			endDate = DateUtil.strToDate(endTime, "yyyy-MM-dd HH:mm:ss");
		}
		List<Enterprise> list = enterpriseMapper.selectAllForPage(key, startDate, endDate);
		return new PageInfo<Enterprise>(list);
	}

	@Override
	public int add(Enterprise enterprise) {
		enterprise.setCreateTime(new Date());
		enterprise.setStatus(Enterprise.STATUS_ACTIVED);
		enterprise.setMmschannle(Constant.MMS_CHANNEL_DEFAULT);
		if (!check(enterprise)) {
			throw new BusinessException("企业名已存在");
		}
		enterprise.setId(IDUtil.getID());
		return enterpriseMapper.insert(enterprise);
	}

	@Override
	public int update(Enterprise enterprise) {
		if (!check(enterprise)) {
			throw new BusinessException("企业名已存在");
		}
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
		int count = enterpriseMapper.deleteById(list);
		if (count > 0) {
			for (String enterpriseId : ids) {
				contractMapper.deleteByEnterpriseId(enterpriseId);
			}
		}
		return count;
	}

	@Override
	public Enterprise getEnterpriseById(String id) {
		return enterpriseMapper.selectOne(id);
	}

	/**
	 * 编辑校验
	 * 
	 * @param enterprise
	 * @return
	 */
	private boolean check(Enterprise enterprise) {
		Map<String, Object> param = new HashMap<>();

		param.put("name", enterprise.getName());
		if (null != enterprise.getId()) {
			param.put("id", enterprise.getId());
		}
		return null == enterpriseMapper.selectOneByName(param);
	}
}
