package cn.m.mt.mocreditservice.bo;

public class redNocardPayBo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public String isSuccess;
	public String errorCode;
	public String errorMsg;
	public String cardno;
	public String amt;
	public String cardtype;
	public String stdauthid;
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
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
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getStdauthid() {
		return stdauthid;
	}
	public void setStdauthid(String stdauthid) {
		this.stdauthid = stdauthid;
	}
	
}
