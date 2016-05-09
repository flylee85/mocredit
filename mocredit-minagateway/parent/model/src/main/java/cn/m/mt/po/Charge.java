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
 * Charge entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "charge")
public class Charge implements java.io.Serializable {

	// Fields

	private Long id;
	private Account account;
	private Shoporder shoporder;
	private String tid;
	private String name;
	private Double amount;
	private Double balance;
	private String source;
	private String descr;
	private String createtime;
	private Integer type;
	private Integer status;
	private String paymenttype;

	// Constructors

	/** default constructor */
	public Charge() {
	}

	/** minimal constructor */
	public Charge(Account account, Double amount, String createtime) {
		this.account = account;
		this.amount = amount;
		this.createtime = createtime;
	}

	/** full constructor */
	public Charge(Account account, Shoporder shoporder, String tid,
			String name, Double amount, Double balance, String source,
			String descr, String createtime, Integer type, Integer status,
			String paymenttype) {
		this.account = account;
		this.shoporder = shoporder;
		this.tid = tid;
		this.name = name;
		this.amount = amount;
		this.balance = balance;
		this.source = source;
		this.descr = descr;
		this.createtime = createtime;
		this.type = type;
		this.status = status;
		this.paymenttype = paymenttype;
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
	@JoinColumn(name = "accountid", nullable = false)
	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shoporderid")
	public Shoporder getShoporder() {
		return this.shoporder;
	}

	public void setShoporder(Shoporder shoporder) {
		this.shoporder = shoporder;
	}

	@Column(name = "tid", length = 30)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "amount", nullable = false, precision = 22, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "balance", precision = 22, scale = 0)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Column(name = "source", length = 20)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "descr", length = 100)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "createtime", nullable = false, length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "paymenttype", length = 50)
	public String getPaymenttype() {
		return this.paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

}