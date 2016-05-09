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
import javax.persistence.UniqueConstraint;

/**
 * Mcsms entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mcsms", uniqueConstraints = @UniqueConstraint(columnNames = "barcode"))
public class Mcsms implements java.io.Serializable {

	// Fields

	private Long id;
	private Eitem eitem;
	private String phone;
	private String descr;
	private String barcode;
	private String endtime;
	private String sendtime;
	private String city;
	private Integer status;
	private Integer type;
	private String creattime;
	private Integer messagetype;
	private String charcode;
	
	// Constructors

	/** default constructor */
	public Mcsms() {
	}

	/** minimal constructor */
	public Mcsms(Integer status, Integer type) {
		this.status = status;
		this.type = type;
	}

	/** full constructor */
	public Mcsms(Eitem eitem, String phone, String descr, String barcode,
			String endtime, String sendtime, String city, Integer status,
			Integer type, String creattime, Integer messagetype, String charcode) {
		this.eitem = eitem;
		this.phone = phone;
		this.descr = descr;
		this.barcode = barcode;
		this.endtime = endtime;
		this.sendtime = sendtime;
		this.city = city;
		this.status = status;
		this.type = type;
		this.creattime = creattime;
		this.messagetype = messagetype;
		this.charcode = charcode;
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
	@JoinColumn(name = "eitmid")
	public Eitem getEitem() {
		return this.eitem;
	}

	public void setEitem(Eitem eitem) {
		this.eitem = eitem;
	}

	@Column(name = "phone", length = 30)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "descr", length = 2000)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "barcode", unique = true, length = 30)
	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Column(name = "endtime", length = 20)
	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	@Column(name = "sendtime", length = 20)
	public String getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	@Column(name = "city", length = 20)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "creattime", length = 20)
	public String getCreattime() {
		return this.creattime;
	}

	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}
	
	@Column(name = "messagetype")
	public Integer getMessagetype() {
		return this.messagetype;
	}

	public void setMessagetype(Integer messagetype) {
		this.messagetype = messagetype;
	}
	@Column(name = "charcode", length = 80)
	public String getCharcode() {
		return this.charcode;
	}

	public void setCharcode(String charcode) {
		this.charcode = charcode;
	}
}