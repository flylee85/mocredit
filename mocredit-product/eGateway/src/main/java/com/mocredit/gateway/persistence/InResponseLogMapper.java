package com.mocredit.gateway.persistence;

import com.mocredit.gateway.entity.InResponseLog;
import org.apache.ibatis.annotations.Param;

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
