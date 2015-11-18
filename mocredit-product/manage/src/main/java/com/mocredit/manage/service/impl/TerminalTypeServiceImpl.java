package com.mocredit.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.manage.model.TerminalType;
import com.mocredit.manage.persitence.TerminalTypeMapper;
import com.mocredit.manage.service.TerminalTypeService;

/**
 * 
 * @author liaoy
 * @date 2015年11月18日
 */
@Service
public class TerminalTypeServiceImpl implements TerminalTypeService {
	@Autowired
	private TerminalTypeMapper typeMapper;

	@Override
	public List<TerminalType> getAll() {
		return typeMapper.selectAll();
	}
}
