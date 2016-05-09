package cn.m.mt.mocreditservice.bo;

public class CodeBillBo implements java.io.Serializable {
	public String isSuccess;
	public String errorMsg;
	public String codeinfo;
	public String orderno;
	public String amt;
	public String ordertime;
	public String errorCode;
	public String posno;
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getCodeinfo() {
		return codeinfo;
	}
	public void setCodeinfo(String codeinfo) {
		this.codeinfo = codeinfo;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getPosno() {
		return posno;
	}
	public void setPosno(String posno) {
		this.posno = posno;
	}
	
}
