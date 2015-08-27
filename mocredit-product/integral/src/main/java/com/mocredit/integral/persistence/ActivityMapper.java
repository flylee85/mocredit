package com.mocredit.integral.persistence;

import org.apache.ibatis.annotations.Param;

import com.mocredit.integral.entity.Activity;

/**
 * @author ytq
 * 
 */
public interface ActivityMapper {
	/**
	 * 保存活动信息
	 * 
	 * @param t
	 * @return
	 */
	int save(@Param(value = "t") Activity t);
}
