package cn.mocredit.gateway.wangpos.bo;

public class BankBo implements java.io.Serializable {
	public String isSuccess;
	public String errorCode;
	public String errorMsg;
	public String bankname;
	public String bankid;
	public String eitemname;
	public String outerid;
	public String expointtype;
	// 0:"否",1:"输入积分兑换",2:"输入金额兑换",3:"固定积分"
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
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public String getEitemname() {
		return eitemname;
	}
	public void setEitemname(String eitemname) {
		this.eitemname = eitemname;
	}
	public String getOuterid() {
		return outerid;
	}
	public void setOuterid(String outerid) {
		this.outerid = outerid;
	}
	public String getExpointtype() {
		return expointtype;
	}
	public void setExpointtype(String expointtype) {
		this.expointtype = expointtype;
	}
	
}
