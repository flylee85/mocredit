package com.mocredit.gateway.service;

import com.mocredit.gateway.entity.Record;
import com.mocredit.gateway.entity.Response;

/**
 * Created by ytq on 2015/12/22.
 */
public interface RecordService extends BaseService<Record> {
    /**
     * 保存兑换记录
     *
     * @param record
     * @param resp
     * @return
     */
    boolean save(Record record, Response resp);

    /**
     * 保存撤销记录
     *
     * @param record
     * @param resp
     * @return
     */
    boolean savePaymentRevoke(Record record, Response resp);

}
