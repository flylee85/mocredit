package com.mocredit.integral.adapter;

/**
 * Created by ytq on 2015/11/10.
 */
public interface IntegralBankAdapter {
    /**
     * 获取积分消费地址
     * @param channel
     * @return
     */
    String getPayment(String channel);

    /**
     * 获取积分撤销地址
     * @param channel
     * @return
     */
    String getPaymentRevoke(String channel);

    /**
     *获取积分冲正地址
     * @param channel
     * @return
     */
    String getPaymentReserval(String channel);

    /**
     * 获取积分撤销冲正地址
     * @param channel
     * @return
     */
    String getPaymentRevokeReserval(String channel);

    /**
     * 获取积分查询地址
     * @param channel
     * @return
     */
    String getConfirmInfo(String channel);
}
