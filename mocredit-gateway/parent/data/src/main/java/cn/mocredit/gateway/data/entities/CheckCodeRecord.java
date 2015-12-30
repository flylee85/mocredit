package cn.mocredit.gateway.data.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "check_code_record")
public class CheckCodeRecord extends Id implements java.io.Serializable {
	private String code;
	private String imei;
	private String batchno;
	private String searchno;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getSearchno() {
		return searchno;
	}

	public void setSearchno(String searchno) {
		this.searchno = searchno;
	}

}
