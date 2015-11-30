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
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.exception.BusinessException;
import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.IDUtil;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.manage.constant.Gateway;
import com.mocredit.manage.constant.OperType;
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
		int insert = 0;
		if (checkSnCode(terminal.getSnCode(), null)) {
			insert = terminalMapper.insert(terminal);
			if ("1".equals(PropertiesUtil.getValue("syn.switch"))) {
				synGateway(terminal, OperType.ADD);
				synIntegral(terminal, null, OperType.ADD);
			}
		} else {
			throw new BusinessException("EN号已存在");
		}
		return insert;
	}

	@Override
	@Transactional
	public int update(Terminal terminal) {
		Terminal oldTerminal = terminalMapper.selectOne(terminal.getId());
		int update = 0;
		if (checkSnCode(terminal.getSnCode(), terminal.getId())) {
			update = terminalMapper.update(terminal);
			if ("1".equals(PropertiesUtil.getValue("syn.switch"))) {
				synGateway(terminal, OperType.UPDATE);
				synIntegral(terminal, oldTerminal, OperType.UPDATE);
			}
		} else {
			throw new BusinessException("EN号已存在");
		}
		return update;
	}

	@Override
	@Transactional
	public int delete(String id) {
		if (null == id || id.isEmpty()) {
			return 0;
		}
		Terminal terminal = terminalMapper.selectOne(id);
		String[] ids = id.split(",");
		List<String> list = new ArrayList<>();
		Collections.addAll(list, ids);
		int deleteById = terminalMapper.deleteById(list);
		if ("1".equals(PropertiesUtil.getValue("syn.switch"))) {
			synGateway(id, OperType.DELETE);
			synIntegral(terminal, null, OperType.DELETE);
		}
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
	 * 
	 *            <br>
	 *            接口参数格式： 新增：{ oper:1 id:1, enCode: } 修改：{ oper:2, id:1, enCode
	 *            } 删除：{ oper:3, id:1,2,3 }
	 */
	private void synGateway(Object newTerminal, OperType oper) {
		String importUrl = PropertiesUtil.getValue("syn.gateway.deviceImport");
		Map<String, Object> postMap = new HashMap<>();
		postMap.put("oper", oper.getValue());
		switch (oper) {
		case ADD:
		case UPDATE:
			Terminal terminal = (Terminal) newTerminal;
			// 只同步新网关机具
			if (!Gateway.NEW.getValue().equals(terminal.getGateway())) {
				return;
			}
			postMap.put("id", terminal.getId());
			postMap.put("enCode", terminal.getSnCode());
			break;
		case DELETE:
			postMap.put("id", newTerminal.toString());
			break;
		}
		String returnstr = HttpUtil.doRestfulByHttpConnection(importUrl, JSON.toJSONString(postMap));
		if (!returnstr.equals("0")) {
			throw new BusinessException("向新网关同步机具信息失败");
		}
	}

	/**
	 * 同步到积分核销接口 <br>
	 * 新增:{oper:1,enCode,storeId} <br>
	 * 修改:{oper:2,enCode,oldEnCode} <br>
	 * 删除:{oper:3,enCode}
	 * 
	 * @param newTerminal
	 * @param oldTerminal
	 * @param oper
	 */
	private void synIntegral(Terminal newTerminal, Terminal oldTerminal, OperType oper) {
		String importUrl = PropertiesUtil.getValue("syn.integral.deviceImport");
		Map<String, Object> postMap = new HashMap<>();
		postMap.put("oper", oper.getValue());
		switch (oper) {
		case ADD:
			postMap.put("enCode", newTerminal.getSnCode());
			postMap.put("storeId", newTerminal.getStoreId());
			break;
		case UPDATE:
			postMap.put("enCode", newTerminal.getSnCode());
			postMap.put("oldEnCode", oldTerminal.getSnCode());
			break;
		case DELETE:
			postMap.put("enCode", newTerminal.getSnCode());
			break;
		}
		String returnstr = HttpUtil.doRestfulByHttpConnection(importUrl, JSON.toJSONString(postMap));
		@SuppressWarnings("unchecked")
		Map<String, Object> returnMap = JSON.parseObject(returnstr, Map.class);
		boolean isSuccess = Boolean.parseBoolean(String.valueOf(returnMap.get("success")));
		if (!isSuccess) {
			throw new BusinessException("向积分核销同步机具信息失败");
		}
	}

	/**
	 * 校验SN号
	 * 
	 * @param snCode
	 * @param id
	 * @return
	 */
	private boolean checkSnCode(String snCode, String id) {
		Map<String, Object> param = new HashMap<>();
		param.put("snCode", snCode);
		if (!StringUtils.isEmpty(id)) {
			param.put("id", id);
		}
		return null == terminalMapper.checkSnCode(param);
	}
}
