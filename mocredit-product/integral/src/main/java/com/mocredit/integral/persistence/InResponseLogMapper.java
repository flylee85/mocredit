package com.mocredit.integral.persistence;

import org.apache.ibatis.annotations.Param;

import com.mocredit.integral.entity.InResponseLog;

/**
 * @author ytq 2015年8月21日
 */
public interface InResponseLogMapper {
	/**
	 * 保存响应日志
	 * 
	 * @param t
	 * @return
	 */
	int save(@Param(value = "t") InResponseLog t);
}
