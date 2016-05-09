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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Enterprisebill entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "enterprisebill")
public class Enterprisebill implements java.io.Serializable {

	// Fields

	private Long id;
	private Enterprise enterprise;
	private String accounttime;
	private Double smscharge;
	private Double mmscharge;
	private Integer sendnum;
	private Integer checknum;
	private Double servicecharge;
	private Double commoditycharge;
	private Double totalcharge;
	private String createtime;
	private Set<Enterprisebilldetail> enterprisebilldetails = new HashSet<Enterprisebilldetail>(
			0);

	// Constructors

	/** default constructor */
	public Enterprisebill() {
	}

	/** full constructor */
	public Enterprisebill(Enterprise enterprise, String accounttime,
			Double smscharge, Double mmscharge, Integer sendnum,
			Integer checknum, Double servicecharge, Double commoditycharge,
			Double totalcharge, String createtime,
			Set<Enterprisebilldetail> enterprisebilldetails) {
		this.enterprise = enterprise;
		this.accounttime = accounttime;
		this.smscharge = smscharge;
		this.mmscharge = mmscharge;
		this.sendnum = sendnum;
		this.checknum = checknum;
		this.servicecharge = servicecharge;
		this.commoditycharge = commoditycharge;
		this.totalcharge = totalcharge;
		this.createtime = createtime;
		this.enterprisebilldetails = enterprisebilldetails;
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
	@JoinColumn(name = "entid")
	public Enterprise getEnterprise() {
		return this.enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	@Column(name = "accounttime", length = 20)
	public String getAccounttime() {
		return this.accounttime;
	}

	public void setAccounttime(String accounttime) {
		this.accounttime = accounttime;
	}

	@Column(name = "smscharge", precision = 22, scale = 0)
	public Double getSmscharge() {
		return this.smscharge;
	}

	public void setSmscharge(Double smscharge) {
		this.smscharge = smscharge;
	}

	@Column(name = "mmscharge", precision = 22, scale = 0)
	public Double getMmscharge() {
		return this.mmscharge;
	}

	public void setMmscharge(Double mmscharge) {
		this.mmscharge = mmscharge;
	}

	@Column(name = "sendnum")
	public Integer getSendnum() {
		return this.sendnum;
	}

	public void setSendnum(Integer sendnum) {
		this.sendnum = sendnum;
	}

	@Column(name = "checknum")
	public Integer getChecknum() {
		return this.checknum;
	}

	public void setChecknum(Integer checknum) {
		this.checknum = checknum;
	}

	@Column(name = "servicecharge", precision = 22, scale = 0)
	public Double getServicecharge() {
		return this.servicecharge;
	}

	public void setServicecharge(Double servicecharge) {
		this.servicecharge = servicecharge;
	}

	@Column(name = "commoditycharge", precision = 22, scale = 0)
	public Double getCommoditycharge() {
		return this.commoditycharge;
	}

	public void setCommoditycharge(Double commoditycharge) {
		this.commoditycharge = commoditycharge;
	}

	@Column(name = "totalcharge", precision = 22, scale = 0)
	public Double getTotalcharge() {
		return this.totalcharge;
	}

	public void setTotalcharge(Double totalcharge) {
		this.totalcharge = totalcharge;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprisebill")
	public Set<Enterprisebilldetail> getEnterprisebilldetails() {
		return this.enterprisebilldetails;
	}

	public void setEnterprisebilldetails(
			Set<Enterprisebilldetail> enterprisebilldetails) {
		this.enterprisebilldetails = enterprisebilldetails;
	}

}