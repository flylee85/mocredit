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
	public int add(Terminal terminal) {
		terminal.setId(IDUtil.getID());
		terminal.setCreateTime(new Date());
		terminal.setStatus(Enterprise.STATUS_ACTIVED);
		return terminalMapper.insert(terminal);
	}

	@Override
	public int update(Terminal terminal) {
		return terminalMapper.update(terminal);
	}

	@Override
	public int delete(String id) {
		if (null == id || id.isEmpty()) {
			return 0;
		}
		String[] ids = id.split(",");
		List<String> list = new ArrayList<>();
		Collections.addAll(list, ids);
		return terminalMapper.deleteById(list);
	}

	@Override
	public Terminal getTerminalById(String id) {
		return terminalMapper.selectOne(id);
	}
	@Override
	public Terminal getStoreInfo(String storeId) {
		return terminalMapper.selectStore(storeId);
	}
}
