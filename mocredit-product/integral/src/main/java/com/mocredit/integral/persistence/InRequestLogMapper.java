package com.mocredit.integral.persistence;

import com.mocredit.integral.entity.InRequestLog;

/**
 * @author ytq 2015年8月21日
 */
public interface InRequestLogMapper {
	/**
	 * 保存请求日志
	 * 
	 * @param t
	 * @return
	 */
	int save(InRequestLog t);

}
