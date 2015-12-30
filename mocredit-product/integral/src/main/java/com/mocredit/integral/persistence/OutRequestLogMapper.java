package com.mocredit.integral.persistence;

import org.apache.ibatis.annotations.Param;

import com.mocredit.integral.entity.OutRequestLog;

/**
 * @author ytq 2015年8月21日
 */
public interface OutRequestLogMapper {
	/**
	 * 保存请求日志
	 * 
	 * @param t
	 * @return
	 */
	int save(@Param(value = "t") OutRequestLog t);
}
