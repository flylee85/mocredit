package cn.m.mt.mocreditservice.bo;

public class redPaymentBo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public String isSuccess;
	public String errorCode;
	public String errorMsg;
	public String validto;
	public String paymenttype;
	public String eitemname;
	public String descr;
	public String eitemid;
	public String point;
	public String getEitemid() {
		return eitemid;
	}
	public void setEitemid(String eitemid) {
		this.eitemid = eitemid;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
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
	public String getValidto() {
		return validto;
	}
	public void setValidto(String validto) {
		this.validto = validto;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getEitemname() {
		return eitemname;
	}
	public void setEitemname(String eitemname) {
		this.eitemname = eitemname;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
}
