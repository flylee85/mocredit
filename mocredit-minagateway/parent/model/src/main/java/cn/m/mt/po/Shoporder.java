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
 * Shoporder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shoporder")
public class Shoporder implements java.io.Serializable {

	// Fields

	private Long id;
	private User user;
	private String tid;
	private Long nummiid;
	private Double payment;
	private String status;
	private String createtime;
	private Integer shoptype;
	private Double alipaymoney;
	private Set<Eorder> eorders = new HashSet<Eorder>(0);
	private Set<Charge> charges = new HashSet<Charge>(0);
	private Set<Paymentrecord> paymentrecords = new HashSet<Paymentrecord>(0);

	// Constructors

	/** default constructor */
	public Shoporder() {
	}

	/** minimal constructor */
	public Shoporder(String tid) {
		this.tid = tid;
	}

	/** full constructor */
	public Shoporder(User user, String tid, Long nummiid, Double payment,
			String status, String createtime, Integer shoptype,
			Double alipaymoney, Set<Eorder> eorders, Set<Charge> charges,
			Set<Paymentrecord> paymentrecords) {
		this.user = user;
		this.tid = tid;
		this.nummiid = nummiid;
		this.payment = payment;
		this.status = status;
		this.createtime = createtime;
		this.shoptype = shoptype;
		this.alipaymoney = alipaymoney;
		this.eorders = eorders;
		this.charges = charges;
		this.paymentrecords = paymentrecords;
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
	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "tid", nullable = false, length = 40)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "nummiid")
	public Long getNummiid() {
		return this.nummiid;
	}

	public void setNummiid(Long nummiid) {
		this.nummiid = nummiid;
	}

	@Column(name = "payment", precision = 22, scale = 0)
	public Double getPayment() {
		return this.payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Column(name = "status", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "shoptype")
	public Integer getShoptype() {
		return this.shoptype;
	}

	public void setShoptype(Integer shoptype) {
		this.shoptype = shoptype;
	}

	@Column(name = "alipaymoney", precision = 22, scale = 0)
	public Double getAlipaymoney() {
		return this.alipaymoney;
	}

	public void setAlipaymoney(Double alipaymoney) {
		this.alipaymoney = alipaymoney;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shoporder")
	public Set<Eorder> getEorders() {
		return this.eorders;
	}

	public void setEorders(Set<Eorder> eorders) {
		this.eorders = eorders;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shoporder")
	public Set<Charge> getCharges() {
		return this.charges;
	}

	public void setCharges(Set<Charge> charges) {
		this.charges = charges;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shoporder")
	public Set<Paymentrecord> getPaymentrecords() {
		return this.paymentrecords;
	}

	public void setPaymentrecords(Set<Paymentrecord> paymentrecords) {
		this.paymentrecords = paymentrecords;
	}

}