package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Ofcardrecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ofcardrecord")
public class Ofcardrecord implements java.io.Serializable {

	// Fields

	private Long id;
	private Barcode barcode;
	private String sporderid;
	private String cardid;
	private String creatime;
	private Integer status;
	private String errmessage;
	private Double cardnum;
	private String mctype;
	private String phone;
	private String returl;

	// Constructors

	/** default constructor */
	public Ofcardrecord() {
	}

	/** full constructor */
	public Ofcardrecord(Barcode barcode, String sporderid, String cardid,
			String creatime, Integer status, String errmessage, Double cardnum,
			String mctype, String phone, String returl) {
		this.barcode = barcode;
		this.sporderid = sporderid;
		this.cardid = cardid;
		this.creatime = creatime;
		this.status = status;
		this.errmessage = errmessage;
		this.cardnum = cardnum;
		this.mctype = mctype;
		this.phone = phone;
		this.returl = returl;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "barcodeid")
	public Barcode getBarcode() {
		return this.barcode;
	}

	public void setBarcode(Barcode barcode) {
		this.barcode = barcode;
	}

	@Column(name = "sporderid", length = 50)
	public String getSporderid() {
		return this.sporderid;
	}

	public void setSporderid(String sporderid) {
		this.sporderid = sporderid;
	}

	@Column(name = "cardid", length = 20)
	public String getCardid() {
		return this.cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	@Column(name = "creatime", length = 20)
	public String getCreatime() {
		return this.creatime;
	}

	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "errmessage", length = 100)
	public String getErrmessage() {
		return this.errmessage;
	}

	public void setErrmessage(String errmessage) {
		this.errmessage = errmessage;
	}

	@Column(name = "cardnum", precision = 22, scale = 0)
	public Double getCardnum() {
		return this.cardnum;
	}

	public void setCardnum(Double cardnum) {
		this.cardnum = cardnum;
	}

	@Column(name = "mctype", length = 20)
	public String getMctype() {
		return this.mctype;
	}

	public void setMctype(String mctype) {
		this.mctype = mctype;
	}

	@Column(name = "phone", length = 11)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "returl", length = 200)
	public String getReturl() {
		return this.returl;
	}

	public void setReturl(String returl) {
		this.returl = returl;
	}

}