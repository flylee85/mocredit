package cn.mocredit.gateway.thirdparty.bo;

public class VerifyCodeResponse {
    private String rtnFlag;
    private String errorMes;
    private String orderId;
    private String ymOrderId;
    private String ticketType;
    private String expData;
    private String des;
    private String amount;
    private String issueEnterpriseId;
    private String issueEnterpriseName;

    public String getRtnFlag() {
        return rtnFlag;
    }

    public void setRtnFlag(String rtnFlag) {
        this.rtnFlag = rtnFlag;
    }

    public String getErrorMes() {
        return errorMes;
    }

    public void setErrorMes(String errorMes) {
        this.errorMes = errorMes;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getYmOrderId() {
        return ymOrderId;
    }

    public void setYmOrderId(String ymOrderId) {
        this.ymOrderId = ymOrderId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getExpData() {
        return expData;
    }

    public void setExpData(String expData) {
        this.expData = expData;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIssueEnterpriseId() {
        return issueEnterpriseId;
    }

    public void setIssueEnterpriseId(String issueEnterpriseId) {
        this.issueEnterpriseId = issueEnterpriseId;
    }

    public String getIssueEnterpriseName() {
        return issueEnterpriseName;
    }

    public void setIssueEnterpriseName(String issueEnterpriseName) {
        this.issueEnterpriseName = issueEnterpriseName;
    }
}
