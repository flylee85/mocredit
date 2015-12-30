package cn.mocredit.gateway.thirdparty.bo;

public class ReceiveFromVerifyCodeModel {
    private ReceiveFromVerifyCodeModelDate data;
    private String errorCode;
    private String errorMsg;
    private String success;

    public ReceiveFromVerifyCodeModelDate getData() {
        return data;
    }

    public void setData(ReceiveFromVerifyCodeModelDate data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
