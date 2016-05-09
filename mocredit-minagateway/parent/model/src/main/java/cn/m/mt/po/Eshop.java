package cn.m.mt.po;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Eshop entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "eshop")
public class Eshop implements java.io.Serializable {

	// Fields

	private Long id;
	private String registNo;
	private String salerange;
	private String startdate;
	private String limitdate;
	private String header;
	private String phone;
	private String licenseurl;
	private String taxurl;
	private String status;
	private String reason;
	private String audittime;
	private Set<Shop> shops = new HashSet<Shop>(0);

	// Constructors

	/** default constructor */
	public Eshop() {
	}

	/** full constructor */
	public Eshop(String registNo, String salerange, String startdate,
			String limitdate, String header, String phone, String licenseurl,
			String taxurl, String status, String reason, String audittime,
			Set<Shop> shops) {
		this.registNo = registNo;
		this.salerange = salerange;
		this.startdate = startdate;
		this.limitdate = limitdate;
		this.header = header;
		this.phone = phone;
		this.licenseurl = licenseurl;
		this.taxurl = taxurl;
		this.status = status;
		this.reason = reason;
		this.audittime = audittime;
		this.shops = shops;
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

	@Column(name = "registNO", length = 50)
	public String getRegistNo() {
		return this.registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	@Column(name = "salerange", length = 200)
	public String getSalerange() {
		return this.salerange;
	}

	public void setSalerange(String salerange) {
		this.salerange = salerange;
	}

	@Column(name = "startdate", length = 20)
	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	@Column(name = "limitdate", length = 20)
	public String getLimitdate() {
		return this.limitdate;
	}

	public void setLimitdate(String limitdate) {
		this.limitdate = limitdate;
	}

	@Column(name = "header", length = 20)
	public String getHeader() {
		return this.header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "licenseurl", length = 100)
	public String getLicenseurl() {
		return this.licenseurl;
	}

	public void setLicenseurl(String licenseurl) {
		this.licenseurl = licenseurl;
	}

	@Column(name = "taxurl", length = 100)
	public String getTaxurl() {
		return this.taxurl;
	}

	public void setTaxurl(String taxurl) {
		this.taxurl = taxurl;
	}

	@Column(name = "status", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "reason", length = 200)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "audittime", length = 50)
	public String getAudittime() {
		return this.audittime;
	}

	public void setAudittime(String audittime) {
		this.audittime = audittime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eshop")
	public Set<Shop> getShops() {
		return this.shops;
	}

	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}

}