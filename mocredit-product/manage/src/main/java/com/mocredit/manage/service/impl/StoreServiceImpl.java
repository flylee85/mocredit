package com.mocredit.manage.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.exception.BusinessException;
import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.IDUtil;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.manage.constant.OperType;
import com.mocredit.manage.constant.StoreBusinessStatus;
import com.mocredit.manage.model.Enterprise;
import com.mocredit.manage.model.Store;
import com.mocredit.manage.model.Terminal;
import com.mocredit.manage.persitence.StoreMapper;
import com.mocredit.manage.persitence.TerminalMapper;
import com.mocredit.manage.service.StoreService;
import com.mocredit.manage.service.TerminalService;

@Service
public class StoreServiceImpl implements StoreService {
	// 日志对象
	private static Logger logger = Logger.getLogger(StoreServiceImpl.class);
	@Autowired
	private StoreMapper storeMapper;
	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private TerminalService terminalService;

	@Override
	public PageInfo<Store> getPage(Map<String, Object> paramMap, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Object key = paramMap.get("key");
		if (null != key) {
			key = key.toString().trim();
			if (!key.toString().isEmpty()) {
				key = "%" + key + "%";
			} else {
				key = null;
			}
		}
		paramMap.put("key", key);
		List<Store> list = storeMapper.selectAllForPage(paramMap);
		if (null != list) {
			for (Store store : list) {
				store.setBusinessStatusName(StoreBusinessStatus.getName(store.getStatus()));
			}
		}
		return new PageInfo<Store>(list);
	}

	@Override
	public int add(Store store) {
		store.setId(IDUtil.getID());
		store.setStatus(Enterprise.STATUS_ACTIVED);
		store.setCreateTime(new Date());
		if (!checkCode(store.getCode(), null)) {
			throw new BusinessException("门店编号已存在");
		}
		return storeMapper.insert(store);
	}

	@Override
	public int update(Store store) {
		if (!checkCode(store.getCode(), store.getId())) {
			throw new BusinessException("门店编号已存在");
		}
		return storeMapper.update(store);
	}

	@Override
	@Transactional
	public int delete(String id) {
		if (null == id || id.isEmpty()) {
			return 0;
		}
		String[] ids = id.split(",");
		List<String> list = new ArrayList<>();
		Collections.addAll(list, ids);
		int count = storeMapper.deleteById(list);
		if (count > 0) {
			for (String storeId : ids) {
				Map<String, Object> terminalParam = new HashMap<>();
				terminalParam.put("storeId", storeId);
				List<Terminal> terminals = terminalMapper.selectAllForPage(terminalParam);
				for (Terminal terminal : terminals) {
					terminalService.delete(terminal.getId());
				}
			}
		}
		if (count > 0) {
			synIntegral(id, OperType.DELETE);
		}
		return count;
	}

	@Override
	public Store getStoreById(String id) {
		return storeMapper.selectOne(id);
	}

	/**
	 * 校验编号
	 * 
	 * @param snCode
	 * @param id
	 * @return
	 */
	private boolean checkCode(String code, String id) {
		Map<String, Object> param = new HashMap<>();
		param.put("code", code);
		if (!StringUtils.isEmpty(id)) {
			param.put("id", id);
		}
		return null == storeMapper.checkStoreCode(param);
	}

	/**
	 * 同步到积分核销接口 <br>
	 * 删除:{oper:3,storeId}
	 * 
	 * @param newTerminal
	 * @param oldTerminal
	 * @param oper
	 */
	@Override
	public void synIntegral(String storeId, OperType oper) {
		String importUrl = PropertiesUtil.getValue("syn.integral.storeImport");
		Map<String, Object> postMap = new HashMap<>();
		postMap.put("oper", oper.getValue());
		switch (oper) {
		case DELETE:
			postMap.put("storeId", storeId);
			break;
		}
		// 是否开启同步
		if (!"1".equals(PropertiesUtil.getValue("syn.switch"))) {
			logger.debug("connect url：" + importUrl);
			logger.debug("connect data：" + JSON.toJSONString(postMap));
			return;
		}
		String returnstr = HttpUtil.doRestfulByHttpConnection(importUrl, JSON.toJSONString(postMap));
		@SuppressWarnings("unchecked")
		Map<String, Object> returnMap = JSON.parseObject(returnstr, Map.class);
		boolean isSuccess = Boolean.parseBoolean(String.valueOf(returnMap.get("success")));
		if (!isSuccess) {
			throw new BusinessException("向积分核销同步机具信息失败");
		}
	}
}
