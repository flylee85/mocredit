package cn.m.mt.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SD_CARD_TYPE implements java.io.Serializable {

	private static final long serialVersionUID = 233841633906912880L;
	private Long id;
	private String CARD_BIN;
	private String BANK_ID;
	private String CARD_NAME;
	private String VALIAD_FLAG;
	private String remarks;
	private String CARD_BIN_TYPE;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCARD_BIN() {
		return CARD_BIN;
	}

	public void setCARD_BIN(String cARD_BIN) {
		CARD_BIN = cARD_BIN;
	}

	public String getBANK_ID() {
		return BANK_ID;
	}

	public void setBANK_ID(String bANK_ID) {
		BANK_ID = bANK_ID;
	}

	public String getCARD_NAME() {
		return CARD_NAME;
	}

	public void setCARD_NAME(String cARD_NAME) {
		CARD_NAME = cARD_NAME;
	}

	public String getVALIAD_FLAG() {
		return VALIAD_FLAG;
	}

	public void setVALIAD_FLAG(String vALIAD_FLAG) {
		VALIAD_FLAG = vALIAD_FLAG;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCARD_BIN_TYPE() {
		return CARD_BIN_TYPE;
	}

	public void setCARD_BIN_TYPE(String cARD_BIN_TYPE) {
		CARD_BIN_TYPE = cARD_BIN_TYPE;
	}

}