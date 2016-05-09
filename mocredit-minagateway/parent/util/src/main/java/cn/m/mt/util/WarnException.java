package cn.m.mt.util;

public class WarnException extends RuntimeException {
	private static final long serialVersionUID = 2812562713835483931L;
	String errorcode;
	String mes;
	public WarnException(String mes){
		super(mes);
		this.mes=mes;
	}
	public WarnException(String errorcode, String mes) {
		super(mes);
		this.errorcode = errorcode;
		this.mes = mes;
	}

	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	
	
	
}
