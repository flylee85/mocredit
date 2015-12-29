package cn.mocredit.gateway.wangpos.bo;

public class DemoData {
    private String activitId;
    private String amt;
    private String cardNo;
    private String exp_date;
    private String orderId;
    private String saltValue;

    public String getActivitId() {
        return activitId;
    }

    public void setActivitId(String activitId) {
        this.activitId = activitId;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSaltValue() {
        return saltValue;
    }

    public void setSaltValue(String saltValue) {
        this.saltValue = saltValue;
    }
}
