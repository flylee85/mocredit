package com.mocredit.integral.dto;

public class PaymentDto {
    /**
     * orderId    订单号
     * cardNum    卡号
     * tranAmt    消费积分
     * code       银行活动代码\\内部活动编码
     * cardExpDate    卡有效期
     */
    private String orderId;
    private String cardNum;
    private String tranAmt;
    private String code;
    private String cardExpDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(String tranAmt) {
        this.tranAmt = tranAmt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCardExpDate() {
        return cardExpDate;
    }

    public void setCardExpDate(String cardExpDate) {
        this.cardExpDate = cardExpDate;
    }
}
