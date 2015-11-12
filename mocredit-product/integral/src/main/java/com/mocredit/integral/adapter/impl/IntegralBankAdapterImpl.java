package com.mocredit.integral.adapter.impl;

import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.integral.adapter.IntegralBankAdapter;
import org.springframework.stereotype.Service;

/**
 * Created by ytq on 2015/11/10.
 */
@Service
public class IntegralBankAdapterImpl implements IntegralBankAdapter {
    @Override
    public String getPayment(String channel) {
        return PropertiesUtil.getValue(channel + "." + "payment");
    }

    @Override
    public String getPaymentRevoke(String channel) {
        return PropertiesUtil.getValue(channel + "." + "paymentRevoke");
    }

    @Override
    public String getPaymentReserval(String channel) {
        return PropertiesUtil.getValue(channel + "." + "paymentReserval");
    }

    @Override
    public String getPaymentRevokeReserval(String channel) {
        return PropertiesUtil.getValue(channel + "." + "paymentRevokeReserval");
    }

    @Override
    public String getConfirmInfo(String channel) {
        return PropertiesUtil.getValue(channel + "." + "confirmInfo");
    }
}
