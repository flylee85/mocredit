package com.mocredit.manage.service.impl;

import com.mocredit.manage.model.Merchant;
import com.mocredit.manage.persitence.MerchantMapper;
import com.mocredit.manage.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ytq on 2015/11/4.
 */
@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public Merchant selectByEnterpriseId(String enterpriseId) {
        return merchantMapper.selectByEnterpriseId(enterpriseId);
    }
}
