package cn.m.mt.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SD_TERM_KEY implements java.io.Serializable {

	private static final long serialVersionUID = 7175499727685195379L;
	private Long id;
	private String TERM_ID;
	private String MERCH_ID;
	private String TERM_TYPE;
	private String OPER_ID;
	private String LMK_TMK;
	private String TMK_CHECK_VALUE;
	private String LMK_TPK;
	private String LMK_TAK;
	private String TMK_TAK;
	private String UPDATE_TIME;

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

	public String getLMK_TMK() {
		return LMK_TMK;
	}

	public void setLMK_TMK(String lMK_TMK) {
		LMK_TMK = lMK_TMK;
	}

	public String getTMK_CHECK_VALUE() {
		return TMK_CHECK_VALUE;
	}

	public void setTMK_CHECK_VALUE(String tMK_CHECK_VALUE) {
		TMK_CHECK_VALUE = tMK_CHECK_VALUE;
	}

	public String getLMK_TPK() {
		return LMK_TPK;
	}

	public void setLMK_TPK(String lMK_TPK) {
		LMK_TPK = lMK_TPK;
	}

	public String getLMK_TAK() {
		return LMK_TAK;
	}

	public void setLMK_TAK(String lMK_TAK) {
		LMK_TAK = lMK_TAK;
	}

	public String getTMK_TAK() {
		return TMK_TAK;
	}

	public void setTMK_TAK(String tMK_TAK) {
		TMK_TAK = tMK_TAK;
	}

	public String getUPDATE_TIME() {
		return UPDATE_TIME;
	}

	public void setUPDATE_TIME(String uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}

}