package com.mocredit.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mocredit.manage.model.Area;
import com.mocredit.manage.persitence.AreaMapper;
import com.mocredit.manage.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaMapper areaMapper;

	@Override
	public List<Area> getSiblings(String id) {
		if (null == id || id.isEmpty()) {
			return null;
		}
		return areaMapper.selectSiblings(id);
	}

	@Override
	public List<Area> getSubArea(String pid) {
		if (null == pid || pid.isEmpty()) {
			return null;
		}
		return areaMapper.selectByParentId(pid);
	}

}
