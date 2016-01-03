package cn.mocredit.gateway.message;

public class CxForJson {
	private HxData Data;
	private String errorMsg;
	private String success;
	private String errorCode;
	public HxData getData() {
		return Data;
	}
	public void setData(HxData data) {
		Data = data;
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
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
