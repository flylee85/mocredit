package com.mocredit.gateway.service.impl;

import com.mocredit.gateway.entity.Record;
import com.mocredit.gateway.entity.TranRecord;
import com.mocredit.gateway.persistence.TranRecordMapper;
import com.mocredit.gateway.service.TranRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ytq on 2015/12/22.
 */
@Service
public class TranRecordServiceImpl implements TranRecordService {
    @Autowired
    private TranRecordMapper tranRecordMapper;

    @Override
    public boolean save(TranRecord tranRecord) {
        return tranRecordMapper.save(tranRecord) > 0;
    }

    @Override
    public boolean updateByExpireDate(String expireDate) {
        return tranRecordMapper.updateByExpireDate(expireDate) > 0;
    }

    @Override
    public List<TranRecord> getTranRecordByCardNum(String cardNum) {
        return tranRecordMapper.getTranRecordByCardNum(cardNum);
    }

    @Override
    public boolean minusCountByCardNum(String cardNum) {
        return tranRecordMapper.minusCountByCardNum(cardNum) > 0;
    }
}
