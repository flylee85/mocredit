package com.mocredit.manage.persitence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mocredit.manage.model.Store;

public interface StoreMapper {
	List<Store> selectAll(Store store);

	List<Store> selectAllofActivity(@Param("activityId") String activityId);
	
}