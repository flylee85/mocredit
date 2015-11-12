package com.mocredit.manage.persitence;

import java.util.List;

import com.mocredit.manage.model.Area;

public interface AreaMapper {
	List<Area> selectSiblings(String id);

	List<Area> selectByParentId(String pid);
}