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
 * Enterprise entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "enterprise")
public class Enterprise implements java.io.Serializable {

	// Fields

	private Long id;
	private Enterprise enterprise;
	private String entname;
	private String entcode;
	private String linkman;
	private String linkphone;
	private String linkman2;
	private String linkphone2;
	private String descr;
	private Integer status;
	private String createtime;
	private Integer smsbalance;
	private Integer smstotal;
	private Integer mmsbalance;
	private Integer mmstotal;
	private Integer smswarning;
	private Integer mmswarning;
	private String cleartime;
	private Double clearamount;
	private String mmschannle;
	private Integer audit;
	private Set<Systemuser> systemusers = new HashSet<Systemuser>(0);
	private Set<Smsmt> smsmts = new HashSet<Smsmt>(0);
	private Set<User> users = new HashSet<User>(0);
	private Set<Eitem> eitems = new HashSet<Eitem>(0);
	private Set<Wemmsmt> wemmsmts = new HashSet<Wemmsmt>(0);
	private Set<Entshop> entshops = new HashSet<Entshop>(0);
	private Set<Enterprise> enterprises = new HashSet<Enterprise>(0);
	private Set<Account> accounts = new HashSet<Account>(0);
	private Set<Enterprisebill> enterprisebills = new HashSet<Enterprisebill>(0);
	private Set<Smsrecharge> smsrecharges = new HashSet<Smsrecharge>(0);

	// Constructors

	/** default constructor */
	public Enterprise() {
	}

	/** minimal constructor */
	public Enterprise(String entname, String entcode, Integer status,
			String createtime) {
		this.entname = entname;
		this.entcode = entcode;
		this.status = status;
		this.createtime = createtime;
	}

	/** full constructor */
	public Enterprise(Enterprise enterprise, String entname, String entcode,
			String linkman, String linkphone, String linkman2,
			String linkphone2, String descr, Integer status, String createtime,
			Integer smsbalance, Integer smstotal, Integer mmsbalance,
			Integer mmstotal, Integer smswarning, Integer mmswarning,
			String cleartime, Double clearamount, Set<Systemuser> systemusers,
			Set<Smsmt> smsmts, Set<User> users, Set<Eitem> eitems,
			Set<Wemmsmt> wemmsmts, Set<Entshop> entshops,
			Set<Enterprise> enterprises, Set<Account> accounts,
			String mmschannle, Integer audit,
			Set<Enterprisebill> enterprisebills, Set<Smsrecharge> smsrecharges) {
		this.enterprise = enterprise;
		this.entname = entname;
		this.entcode = entcode;
		this.linkman = linkman;
		this.linkphone = linkphone;
		this.linkman2 = linkman2;
		this.linkphone2 = linkphone2;
		this.descr = descr;
		this.status = status;
		this.createtime = createtime;
		this.smsbalance = smsbalance;
		this.smstotal = smstotal;
		this.mmsbalance = mmsbalance;
		this.mmstotal = mmstotal;
		this.smswarning = smswarning;
		this.mmswarning = mmswarning;
		this.cleartime = cleartime;
		this.clearamount = clearamount;
		this.systemusers = systemusers;
		this.smsmts = smsmts;
		this.users = users;
		this.eitems = eitems;
		this.wemmsmts = wemmsmts;
		this.entshops = entshops;
		this.enterprises = enterprises;
		this.accounts = accounts;
		this.enterprisebills = enterprisebills;
		this.smsrecharges = smsrecharges;
		this.mmschannle = mmschannle;
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
	@JoinColumn(name = "parentid")
	public Enterprise getEnterprise() {
		return this.enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	@Column(name = "entname", nullable = false, length = 50)
	public String getEntname() {
		return this.entname;
	}

	public void setEntname(String entname) {
		this.entname = entname;
	}

	@Column(name = "entcode", nullable = false, length = 32)
	public String getEntcode() {
		return this.entcode;
	}

	public void setEntcode(String entcode) {
		this.entcode = entcode;
	}

	@Column(name = "linkman", length = 20)
	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "linkphone", length = 20)
	public String getLinkphone() {
		return this.linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	@Column(name = "linkman2", length = 20)
	public String getLinkman2() {
		return this.linkman2;
	}

	public void setLinkman2(String linkman2) {
		this.linkman2 = linkman2;
	}

	@Column(name = "linkphone2", length = 20)
	public String getLinkphone2() {
		return this.linkphone2;
	}

	public void setLinkphone2(String linkphone2) {
		this.linkphone2 = linkphone2;
	}

	@Column(name = "descr", length = 100)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "createtime", nullable = false, length = 19)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "smsbalance")
	public Integer getSmsbalance() {
		return this.smsbalance;
	}

	public void setSmsbalance(Integer smsbalance) {
		this.smsbalance = smsbalance;
	}

	@Column(name = "smstotal")
	public Integer getSmstotal() {
		return this.smstotal;
	}

	public void setSmstotal(Integer smstotal) {
		this.smstotal = smstotal;
	}

	@Column(name = "mmsbalance")
	public Integer getMmsbalance() {
		return this.mmsbalance;
	}

	public void setMmsbalance(Integer mmsbalance) {
		this.mmsbalance = mmsbalance;
	}

	@Column(name = "mmstotal")
	public Integer getMmstotal() {
		return this.mmstotal;
	}

	public void setMmstotal(Integer mmstotal) {
		this.mmstotal = mmstotal;
	}

	@Column(name = "smswarning")
	public Integer getSmswarning() {
		return this.smswarning;
	}

	public void setSmswarning(Integer smswarning) {
		this.smswarning = smswarning;
	}

	@Column(name = "mmswarning")
	public Integer getMmswarning() {
		return this.mmswarning;
	}

	public void setMmswarning(Integer mmswarning) {
		this.mmswarning = mmswarning;
	}

	@Column(name = "cleartime", length = 20)
	public String getCleartime() {
		return this.cleartime;
	}

	public void setCleartime(String cleartime) {
		this.cleartime = cleartime;
	}

	@Column(name = "clearamount", precision = 22, scale = 0)
	public Double getClearamount() {
		return this.clearamount;
	}

	public void setClearamount(Double clearamount) {
		this.clearamount = clearamount;
	}

	@Column(name = "audit")
	public Integer getAudit() {
		return this.audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
	public Set<Systemuser> getSystemusers() {
		return this.systemusers;
	}

	public void setSystemusers(Set<Systemuser> systemusers) {
		this.systemusers = systemusers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
	public Set<Smsmt> getSmsmts() {
		return this.smsmts;
	}

	public void setSmsmts(Set<Smsmt> smsmts) {
		this.smsmts = smsmts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
	public Set<Eitem> getEitems() {
		return this.eitems;
	}

	public void setEitems(Set<Eitem> eitems) {
		this.eitems = eitems;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
	public Set<Wemmsmt> getWemmsmts() {
		return this.wemmsmts;
	}

	public void setWemmsmts(Set<Wemmsmt> wemmsmts) {
		this.wemmsmts = wemmsmts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
	public Set<Entshop> getEntshops() {
		return this.entshops;
	}

	public void setEntshops(Set<Entshop> entshops) {
		this.entshops = entshops;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
	public Set<Enterprise> getEnterprises() {
		return this.enterprises;
	}

	public void setEnterprises(Set<Enterprise> enterprises) {
		this.enterprises = enterprises;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
	public Set<Enterprisebill> getEnterprisebills() {
		return this.enterprisebills;
	}

	public void setEnterprisebills(Set<Enterprisebill> enterprisebills) {
		this.enterprisebills = enterprisebills;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enterprise")
	public Set<Smsrecharge> getSmsrecharges() {
		return this.smsrecharges;
	}

	public void setSmsrecharges(Set<Smsrecharge> smsrecharges) {
		this.smsrecharges = smsrecharges;
	}

	@Column(name = "mmschannle", length = 10)
	public String getMmschannle() {
		return this.mmschannle;
	}

	public void setMmschannle(String mmschannle) {
		this.mmschannle = mmschannle;
	}
	
}