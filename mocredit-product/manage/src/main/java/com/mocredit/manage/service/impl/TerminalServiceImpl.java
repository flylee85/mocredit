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

import com.alibaba.fastjson.JSON;
import com.mocredit.base.exception.BusinessException;
import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.IDUtil;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.manage.constant.TerminalOperType;
import com.mocredit.manage.model.Enterprise;
import com.mocredit.manage.model.Terminal;
import com.mocredit.manage.persitence.TerminalMapper;
import com.mocredit.manage.service.TerminalService;

@Service
public class TerminalServiceImpl implements TerminalService {
	@Autowired
	private TerminalMapper terminalMapper;

	@Override
	public PageInfo<Terminal> getPage(String key, String storeId, int pageNum, int pageSize) {
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
		map.put("storeId", storeId);
		List<Terminal> list = terminalMapper.selectAllForPage(map);
		return new PageInfo<Terminal>(list);
	}

	@Override
	@Transactional
	public int add(Terminal terminal) {
		terminal.setId(IDUtil.getID());
		terminal.setCreateTime(new Date());
		terminal.setStatus(Enterprise.STATUS_ACTIVED);
		int insert = terminalMapper.insert(terminal);
		synGateway(terminal, TerminalOperType.ADD);
		return insert;
	}

	@Override
	public int update(Terminal terminal) {
		int update = terminalMapper.update(terminal);
		synGateway(terminal, TerminalOperType.UPDATE);
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
		int deleteById = terminalMapper.deleteById(list);
		synGateway(id, TerminalOperType.DELETE);
		return deleteById;
	}

	@Override
	public Terminal getTerminalById(String id) {
		return terminalMapper.selectOne(id);
	}

	@Override
	public Terminal getStoreInfo(String storeId) {
		return terminalMapper.selectStore(storeId);
	}

	/**
	 * 同步到网关
	 * 
	 * @param newTerminal
	 *            机具信息（terminal/id）
	 * @param oper
	 *            TerminalOperType
	 */
	private void synGateway(Object newTerminal, TerminalOperType oper) {
		String importUrl = PropertiesUtil.getValue("gateway.deviceImport");
		Map<String, Object> postMap = new HashMap<>();
		postMap.put("oper", oper.getValue());
		switch (oper) {
		case ADD:
		case UPDATE:
			Terminal terminal = (Terminal) newTerminal;
			postMap.put("id", terminal.getId());
			postMap.put("enCode", terminal.getSnCode());
			break;
		case DELETE:
			postMap.put("id", newTerminal.toString());
			break;
		}
		String returnJson = HttpUtil.doRestfulByHttpConnection(importUrl, JSON.toJSONString(postMap));
		Map<String, Object> returnMap = JSON.parseObject(returnJson, Map.class);
		boolean isSuccess = Boolean.parseBoolean(String.valueOf(returnMap.get("success")));
		if (!isSuccess) {
			throw new BusinessException("向新网关同步机具信息失败");
		}
	}
}
