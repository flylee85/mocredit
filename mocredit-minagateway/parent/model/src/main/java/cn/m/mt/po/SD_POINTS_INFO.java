package cn.m.mt.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SD_POINTS_INFO implements java.io.Serializable {

	private static final long serialVersionUID = 567970915488813867L;
	private Long id;
	private String ACTIVTY_ID;
	private String BANK_ID;
	private String MERCH_ID;
	private String TREM_ID;
	private String PRODUCT_ID;
	private String PRODUCT_NAME;
	private String ACTIVTY_TYPE;
	private String POINTS;
	private String RULES;
	private String STATUS;
	private String SHOW_ORDER;
	private String SOME_DESC;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getACTIVTY_ID() {
		return ACTIVTY_ID;
	}

	public void setACTIVTY_ID(String aCTIVTY_ID) {
		ACTIVTY_ID = aCTIVTY_ID;
	}

	public String getBANK_ID() {
		return BANK_ID;
	}

	public void setBANK_ID(String bANK_ID) {
		BANK_ID = bANK_ID;
	}

	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(String pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}

	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}

	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}

	public String getMERCH_ID() {
		return MERCH_ID;
	}

	public void setMERCH_ID(String mERCH_ID) {
		MERCH_ID = mERCH_ID;
	}

	public String getTREM_ID() {
		return TREM_ID;
	}

	public void setTREM_ID(String tREM_ID) {
		TREM_ID = tREM_ID;
	}

	public String getACTIVTY_TYPE() {
		return ACTIVTY_TYPE;
	}

	public void setACTIVTY_TYPE(String aCTIVTY_TYPE) {
		ACTIVTY_TYPE = aCTIVTY_TYPE;
	}

	public String getPOINTS() {
		return POINTS;
	}

	public void setPOINTS(String pOINTS) {
		POINTS = pOINTS;
	}

	public String getRULES() {
		return RULES;
	}

	public void setRULES(String rULES) {
		RULES = rULES;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getSHOW_ORDER() {
		return SHOW_ORDER;
	}

	public void setSHOW_ORDER(String sHOW_ORDER) {
		SHOW_ORDER = sHOW_ORDER;
	}

	public String getSOME_DESC() {
		return SOME_DESC;
	}

	public void setSOME_DESC(String sOME_DESC) {
		SOME_DESC = sOME_DESC;
	}

}