package cn.m.mt.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SD_POS_TERM implements java.io.Serializable {

	private static final long serialVersionUID = -6987607657588163954L;
	private Long id;
	private String TERM_ID;
	private String MERCH_ID;
	private String TERM_TYPE;
	private String OPER_ID;
	private String POS_NO;
	private String BATCH_NO;
	private String STATUS;
	private String UPDATE_TIME;
	private String RETCODE;
	private Shop shop;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTERM_ID() {
		return TERM_ID;
	}

	public void setTERM_ID(String tERM_ID) {
		TERM_ID = tERM_ID;
	}

	public String getMERCH_ID() {
		return MERCH_ID;
	}

	public void setMERCH_ID(String mERCH_ID) {
		MERCH_ID = mERCH_ID;
	}

	public String getTERM_TYPE() {
		return TERM_TYPE;
	}

	public void setTERM_TYPE(String tERM_TYPE) {
		TERM_TYPE = tERM_TYPE;
	}

	public String getOPER_ID() {
		return OPER_ID;
	}

	public void setOPER_ID(String oPER_ID) {
		OPER_ID = oPER_ID;
	}

	public String getPOS_NO() {
		return POS_NO;
	}

	public void setPOS_NO(String pOS_NO) {
		POS_NO = pOS_NO;
	}

	public String getBATCH_NO() {
		return BATCH_NO;
	}

	public void setBATCH_NO(String bATCH_NO) {
		BATCH_NO = bATCH_NO;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getUPDATE_TIME() {
		return UPDATE_TIME;
	}

	public void setUPDATE_TIME(String uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shopid")
	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getRETCODE() {
		return RETCODE;
	}

	public void setRETCODE(String rETCODE) {
		RETCODE = rETCODE;
	}

}