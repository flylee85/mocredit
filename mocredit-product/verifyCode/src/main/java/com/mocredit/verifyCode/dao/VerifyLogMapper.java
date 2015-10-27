package com.mocredit.verifyCode.dao;

import com.mocredit.verifyCode.model.TVerifyLog;

import java.util.List;

/**
 * 验码日志 操作mapper
 * Created by YHL on 15/7/21 22:50.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public interface VerifyLogMapper {

    /**
     * 添加一条日志记录
     * @return
     */
    public int save(TVerifyLog verifyLog);

    /**
     * 批量保存日志记录
     * @param list
     * @return
     */
    public int batchSave(List<TVerifyLog> list);

}
