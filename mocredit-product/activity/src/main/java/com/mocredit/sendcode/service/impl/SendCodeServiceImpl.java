package com.mocredit.sendcode.service.impl;

import com.mocredit.base.util.ExcelTool;
import com.mocredit.sendcode.service.SendCodeService;

import java.util.List;

/**
 * Created by ytq on 2015/10/23.
 * 发码组建接口实现类
 */
public class SendCodeServiceImpl implements SendCodeService {
    @Override
    public List<Object> downloadList(String type, String id, Integer codeCount) {
        return null;
    }

    @Override
    public List<Object> getActBatchList(String actId, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public List<Object> getBatchDetailList(String batchId, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public Object uploadAndSend(String type, List<Object> customerMobileList) {
        return null;
    }

    @Override
    public Object sendCode(String type, String id) {
        return null;
    }
}
