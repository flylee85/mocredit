package com.mocredit.order.constant;

/**
 * Created by ytq on 2014/11/19.
 */
public enum BaseExportTitle {
    ORDER_ID("订单编号"), PUB_ENTERPRISE("企业"), SUP_ENTERPRISE("商户"), STORE("门店"), ACTIVITY("活动名称"),
    ORDER_TIME("时间"), STATUS("状态"), CODE("码"), CARD_NUM("银行卡号"), INTEGRAL("积分"), TEL("电话"), MSG("验证结果");

    private String text;

    BaseExportTitle(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
