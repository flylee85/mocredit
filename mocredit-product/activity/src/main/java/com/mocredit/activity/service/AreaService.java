package com.mocredit.activity.service;

import java.util.List;

import com.mocredit.manage.model.Area;

public interface AreaService {
	/**
	 * 获得所有平级地区
	 * 
	 * @param id
	 * @return
	 */
	List<Area> getSiblings(String id);

	/**
	 * 获得所有下级地区
	 * 
	 * @param pid
	 * @return
	 */
	List<Area> getSubArea(String pid);

}
