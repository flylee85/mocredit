package com.mocredit.bank.persistence;

import java.util.List;
import java.util.Map;

import com.mocredit.bank.entity.Payment;
import com.mocredit.bank.entity.TiPaymentReport;

public interface TiPaymentReportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ti_payment_report
     *
     * @mbggenerated Mon Aug 24 10:10:03 CST 2015
     */
    int save(TiPaymentReport record);
    
    TiPaymentReport selectByOrderId(String orderId);
    
    int updateStatus(TiPaymentReport record);
    
    /**
     * 根据商户ID查询当天消费记录
     * @param merchantId
     * @return
     */
    List<Payment> selectByMerchantId(Map<String, Object> param);
}