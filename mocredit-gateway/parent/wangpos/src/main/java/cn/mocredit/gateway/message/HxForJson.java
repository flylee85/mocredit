package cn.mocredit.gateway.message;

public class HxForJson {
	private HxData data;
	public HxData getData() {
		return data;
	}
	public void setData(HxData data) {
		this.data = data;
	}
	private String errorCode;
	private String errorMsg;
	private String success;
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
