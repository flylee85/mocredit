package com.mocredit.gateway.persistence;

import com.mocredit.gateway.entity.InRequestLog;
import org.apache.ibatis.annotations.Param;

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
