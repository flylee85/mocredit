package com.mocredit.verifyCode.dao;

import com.mocredit.verifyCode.model.ActActivitySynLog;

import java.util.List;

/**
 * Created by YHL on 15/7/22 22:51.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public interface ActActivitySynLogMapper {

    /**
     * 添加一条日志记录
     * @return
     */
    public int save(ActActivitySynLog actActivitySynLog);

    /**
     * 批量保存日志记录
     * @param list
     * @return
     */
    public int batchSave(List<ActActivitySynLog> list);

}
