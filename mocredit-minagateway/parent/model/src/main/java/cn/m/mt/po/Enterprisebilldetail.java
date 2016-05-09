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
 * Enterprisebilldetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "enterprisebilldetail")
public class Enterprisebilldetail implements java.io.Serializable {

	// Fields

	private Long id;
	private Eitem eitem;
	private Enterprisebill enterprisebill;
	private Double smscharge;
	private Double mmscharge;
	private Integer sendnum;
	private Integer checknum;
	private Double servicecharge;
	private Double commoditycharge;
	private Double totalcharge;
	private String createtime;
	private Double checkcharge;
	// Constructors

	/** default constructor */
	public Enterprisebilldetail() {
	}

	public Enterprisebilldetail(Eitem eitem, Enterprisebill enterprisebill,
			Double smscharge, Double mmscharge, Integer sendnum,
			Integer checknum, Double servicecharge, Double commoditycharge,
			Double totalcharge, String createtime, Double checkcharge) {
		this.id = id;
		this.eitem = eitem;
		this.enterprisebill = enterprisebill;
		this.smscharge = smscharge;
		this.mmscharge = mmscharge;
		this.sendnum = sendnum;
		this.checknum = checknum;
		this.servicecharge = servicecharge;
		this.commoditycharge = commoditycharge;
		this.totalcharge = totalcharge;
		this.createtime = createtime;
		this.checkcharge = checkcharge;
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
	@JoinColumn(name = "eitemid")
	public Eitem getEitem() {
		return this.eitem;
	}

	public void setEitem(Eitem eitem) {
		this.eitem = eitem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprisebillid")
	public Enterprisebill getEnterprisebill() {
		return this.enterprisebill;
	}

	public void setEnterprisebill(Enterprisebill enterprisebill) {
		this.enterprisebill = enterprisebill;
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

	@Column(name = "checkcharge", precision = 22, scale = 0)
	public Double getCheckcharge() {
		return checkcharge;
	}

	public void setCheckcharge(Double checkcharge) {
		this.checkcharge = checkcharge;
	}
}