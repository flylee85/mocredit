package com.mocredit.manage.service;

import com.mocredit.manage.model.Merchant;

/**
 * Created by ytq on 2015/11/4.
 */
public interface MerchantService {
    /**
     * 根据企业id查询商户(企业和商户一对一)
     *
     * @param enterpriseId
     * @return
     */
    Merchant selectByEnterpriseId(String enterpriseId);
}
