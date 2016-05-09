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
 * Agent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "agent")
public class Agent implements java.io.Serializable {

	// Fields

	private Long id;
	private Agent agent;
	private String company;
	private String address;
	private String contact;
	private String companyphone;
	private String phone;
	private String email;
	private String province;
	private String city;
	private Integer level;
	private Integer status;
	private String createtime;
	private Long smsbalance;
	private Long mmsbalance;
	private Long smstotal;
	private Long mmstotal;
	private Double moneybalance;
	private Integer discount;
	private Double moneytotal;
	private Double virtualbalance;
	private String fax;
	private String firmsize;
	private Integer downloadtotal;
	private Integer downbalance;
	private String zone;
	private String guid;
	private Set<Systemuser> systemusers = new HashSet<Systemuser>(0);
	private Set<Notice> notices = new HashSet<Notice>(0);
	private Set<Device> devices = new HashSet<Device>(0);
	private Set<Agent> agents = new HashSet<Agent>(0);
	private Set<Apikey> apikeies = new HashSet<Apikey>(0);
	private Set<Shop> shops = new HashSet<Shop>(0);

	// Constructors

	/** default constructor */
	public Agent() {
	}

	/** minimal constructor */
	public Agent(String company, Integer level) {
		this.company = company;
		this.level = level;
	}

	/** full constructor */
	public Agent(Agent agent, String company, String address, String contact,
			String companyphone, String phone, String email, String province,
			String city, Integer level, Integer status, String createtime,
			Long smsbalance, Long mmsbalance, Long smstotal, Long mmstotal,
			Double moneybalance, Integer discount, Double moneytotal,
			Double virtualbalance, String fax, String firmsize,
			Integer downloadtotal, Integer downbalance, String zone,
			String guid, Set<Systemuser> systemusers, Set<Notice> notices,
			Set<Device> devices, Set<Agent> agents, Set<Apikey> apikeies,
			Set<Shop> shops) {
		this.agent = agent;
		this.company = company;
		this.address = address;
		this.contact = contact;
		this.companyphone = companyphone;
		this.phone = phone;
		this.email = email;
		this.province = province;
		this.city = city;
		this.level = level;
		this.status = status;
		this.createtime = createtime;
		this.smsbalance = smsbalance;
		this.mmsbalance = mmsbalance;
		this.smstotal = smstotal;
		this.mmstotal = mmstotal;
		this.moneybalance = moneybalance;
		this.discount = discount;
		this.moneytotal = moneytotal;
		this.virtualbalance = virtualbalance;
		this.fax = fax;
		this.firmsize = firmsize;
		this.downloadtotal = downloadtotal;
		this.downbalance = downbalance;
		this.zone = zone;
		this.guid = guid;
		this.systemusers = systemusers;
		this.notices = notices;
		this.devices = devices;
		this.agents = agents;
		this.apikeies = apikeies;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public Agent getAgent() {
		return this.agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Column(name = "company", nullable = false, length = 50)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "contact", length = 20)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "companyphone", length = 20)
	public String getCompanyphone() {
		return this.companyphone;
	}

	public void setCompanyphone(String companyphone) {
		this.companyphone = companyphone;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Column(name = "level", nullable = false)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "smsbalance")
	public Long getSmsbalance() {
		return this.smsbalance;
	}

	public void setSmsbalance(Long smsbalance) {
		this.smsbalance = smsbalance;
	}

	@Column(name = "mmsbalance")
	public Long getMmsbalance() {
		return this.mmsbalance;
	}

	public void setMmsbalance(Long mmsbalance) {
		this.mmsbalance = mmsbalance;
	}

	@Column(name = "smstotal")
	public Long getSmstotal() {
		return this.smstotal;
	}

	public void setSmstotal(Long smstotal) {
		this.smstotal = smstotal;
	}

	@Column(name = "mmstotal")
	public Long getMmstotal() {
		return this.mmstotal;
	}

	public void setMmstotal(Long mmstotal) {
		this.mmstotal = mmstotal;
	}

	@Column(name = "moneybalance", precision = 11, scale = 0)
	public Double getMoneybalance() {
		return this.moneybalance;
	}

	public void setMoneybalance(Double moneybalance) {
		this.moneybalance = moneybalance;
	}

	@Column(name = "discount")
	public Integer getDiscount() {
		return this.discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	@Column(name = "moneytotal", precision = 11, scale = 0)
	public Double getMoneytotal() {
		return this.moneytotal;
	}

	public void setMoneytotal(Double moneytotal) {
		this.moneytotal = moneytotal;
	}

	@Column(name = "virtualbalance", precision = 11, scale = 0)
	public Double getVirtualbalance() {
		return this.virtualbalance;
	}

	public void setVirtualbalance(Double virtualbalance) {
		this.virtualbalance = virtualbalance;
	}

	@Column(name = "fax", length = 20)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "firmsize", length = 50)
	public String getFirmsize() {
		return this.firmsize;
	}

	public void setFirmsize(String firmsize) {
		this.firmsize = firmsize;
	}

	@Column(name = "downloadtotal")
	public Integer getDownloadtotal() {
		return this.downloadtotal;
	}

	public void setDownloadtotal(Integer downloadtotal) {
		this.downloadtotal = downloadtotal;
	}

	@Column(name = "downbalance")
	public Integer getDownbalance() {
		return this.downbalance;
	}

	public void setDownbalance(Integer downbalance) {
		this.downbalance = downbalance;
	}

	@Column(name = "zone", length = 20)
	public String getZone() {
		return this.zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	@Column(name = "guid", length = 100)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agent")
	public Set<Systemuser> getSystemusers() {
		return this.systemusers;
	}

	public void setSystemusers(Set<Systemuser> systemusers) {
		this.systemusers = systemusers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agent")
	public Set<Notice> getNotices() {
		return this.notices;
	}

	public void setNotices(Set<Notice> notices) {
		this.notices = notices;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agent")
	public Set<Device> getDevices() {
		return this.devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agent")
	public Set<Agent> getAgents() {
		return this.agents;
	}

	public void setAgents(Set<Agent> agents) {
		this.agents = agents;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agent")
	public Set<Apikey> getApikeies() {
		return this.apikeies;
	}

	public void setApikeies(Set<Apikey> apikeies) {
		this.apikeies = apikeies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agent")
	public Set<Shop> getShops() {
		return this.shops;
	}

	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}

}