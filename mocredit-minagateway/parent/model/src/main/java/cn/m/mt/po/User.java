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
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable {

	// Fields

	private Long id;
	private Enterprise enterprise;
	private String phone;
	private String mail;
	private String password;
	private String province;
	private String city;
	private String name;
	private String gender;
	private Double amount;
	private String createtime;
	private String logintime;
	private String ip;
	private String zipcode;
	private String birthdate;
	private String address;
	private String tel;
	private String fax;
	private String company;
	private String dept;
	private String msn;
	private String qq;
	private String descr;
	private String post;
	private Integer inputtype;
	private String numcode;
	private String charcode;
	private String useruuid;
	private String codetime;
	
	private Set<Shoporder> shoporders = new HashSet<Shoporder>(0);
	private Set<Eorder> eorders = new HashSet<Eorder>(0);
	private Set<Aorder> aorders = new HashSet<Aorder>(0);
	private Set<Account> accounts = new HashSet<Account>(0);
	private Set<Xauthtoken> xauthtokens = new HashSet<Xauthtoken>(0);
	private String extuserid;
	private Integer exttype;
	private String username;
	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(Enterprise enterprise, String phone, String mail,
			String password, String province, String city, String name,
			String gender, Double amount, String createtime, String logintime,
			String ip, String zipcode, String birthdate, String address,
			String tel, String fax, String company, String dept, String msn,
			String qq, String descr, String post, Integer inputtype,
			String numcode, String charcode, String useruuid, String codetime,
			Set<Shoporder> shoporders, Set<Eorder> eorders,
			Set<Aorder> aorders, Set<Account> accounts,
			Set<Xauthtoken> xauthtokens) {
		this.enterprise = enterprise;
		this.phone = phone;
		this.mail = mail;
		this.password = password;
		this.province = province;
		this.city = city;
		this.name = name;
		this.gender = gender;
		this.amount = amount;
		this.createtime = createtime;
		this.logintime = logintime;
		this.ip = ip;
		this.zipcode = zipcode;
		this.birthdate = birthdate;
		this.address = address;
		this.tel = tel;
		this.fax = fax;
		this.company = company;
		this.dept = dept;
		this.msn = msn;
		this.qq = qq;
		this.descr = descr;
		this.post = post;
		this.inputtype = inputtype;
		this.numcode = numcode;
		this.charcode = charcode;
		this.useruuid = useruuid;
		this.codetime = codetime;
		this.shoporders = shoporders;
		this.eorders = eorders;
		this.aorders = aorders;
		this.accounts = accounts;
		this.xauthtokens = xauthtokens;
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

	@Column(name = "phone", length = 15)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "mail", length = 50)
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "province", length = 20)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city", length = 20)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "gender", length = 1)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "amount", precision = 10)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "logintime", length = 20)
	public String getLogintime() {
		return this.logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	@Column(name = "ip", length = 20)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "zipcode", length = 10)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "birthdate", length = 20)
	public String getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "tel", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "fax", length = 20)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "company", length = 20)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "dept", length = 20)
	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name = "msn", length = 20)
	public String getMsn() {
		return this.msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	@Column(name = "qq", length = 20)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "descr", length = 200)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "post", length = 20)
	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "inputtype")
	public Integer getInputtype() {
		return this.inputtype;
	}

	public void setInputtype(Integer inputtype) {
		this.inputtype = inputtype;
	}

	@Column(name = "numcode", length = 20)
	public String getNumcode() {
		return this.numcode;
	}

	public void setNumcode(String numcode) {
		this.numcode = numcode;
	}

	@Column(name = "charcode", length = 20)
	public String getCharcode() {
		return this.charcode;
	}

	public void setCharcode(String charcode) {
		this.charcode = charcode;
	}

	@Column(name = "useruuid", length = 50)
	public String getUseruuid() {
		return this.useruuid;
	}

	public void setUseruuid(String useruuid) {
		this.useruuid = useruuid;
	}

	@Column(name = "codetime", length = 20)
	public String getCodetime() {
		return this.codetime;
	}

	public void setCodetime(String codetime) {
		this.codetime = codetime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Shoporder> getShoporders() {
		return this.shoporders;
	}

	public void setShoporders(Set<Shoporder> shoporders) {
		this.shoporders = shoporders;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Eorder> getEorders() {
		return this.eorders;
	}

	public void setEorders(Set<Eorder> eorders) {
		this.eorders = eorders;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Aorder> getAorders() {
		return this.aorders;
	}

	public void setAorders(Set<Aorder> aorders) {
		this.aorders = aorders;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Xauthtoken> getXauthtokens() {
		return this.xauthtokens;
	}

	public void setXauthtokens(Set<Xauthtoken> xauthtokens) {
		this.xauthtokens = xauthtokens;
	}
	@Column(name = "extuserid", length = 20)
	public String getExtuserid() {
		return this.extuserid;
	}

	public void setExtuserid(String extuserid) {
		this.extuserid = extuserid;
	}

	@Column(name = "exttype")
	public Integer getExttype() {
		return this.exttype;
	}

	public void setExttype(Integer exttype) {
		this.exttype = exttype;
	}
	@Column(name = "username", nullable = false, length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}