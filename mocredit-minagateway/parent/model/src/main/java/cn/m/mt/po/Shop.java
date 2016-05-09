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
 * Shop entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shop")
public class Shop implements java.io.Serializable {

	// Fields

	private Long id;
	private Eshop eshop;
	private Agent agent;
	private String username;
	private String password;
	private String name;
	private String keyword;
	private String email;
	private String lastloginip;
	private String lastlogintime;
	private String createtime;
	private String address;
	private String contact;
	private String phone;
	private String province;
	private String city;
	private String zone;
	private String descr;
	private String district;
	private String shoppic2;
	private String shoppic3;
	private String shoppic1;
	private Integer level;
	private String begintime;
	private String lasttime;
	private Integer yearlimit;
	private Double longitude;
	private String uuid;
	private Double latitude;
	private String companyname;
	private Integer status;
	private String weburl;
	private String wapurl;
	private Long smsbalance;
	private Long smstotal;
	private Long mmsbalance;
	private Long mmstotal;
	private Integer pointbalance;
	private Integer pointotal;
	private String jobtype;
	private String subjobtype;
	private String shopphone;
	private String fax;
	private Integer packagetype;
	private Integer purview;
	private String auditdescr;
	private String newwapurl;
	private Integer auditstatus;
	private String alipayname;
	private Long shopid;
	private String route;
	private String cleartime;
	private Double clearamount;
	private Double rebate;
	private Set<Etbflow> etbflows = new HashSet<Etbflow>(0);
	private Set<Device> devices = new HashSet<Device>(0);
	private Set<Systemuser> systemusers = new HashSet<Systemuser>(0);
	private Set<Entshop> entshops = new HashSet<Entshop>(0);
	private Set<Eitem> eitems = new HashSet<Eitem>(0);
	private Set<Account> accounts = new HashSet<Account>(0);
	private Set<Store> stores = new HashSet<Store>(0);
	private Set<Viewrecord> viewrecords = new HashSet<Viewrecord>(0);
	private Set<Apikey> apikeies = new HashSet<Apikey>(0);
	private Set<Shopbill> shopbills = new HashSet<Shopbill>(0);
	private Set<Eshopkey> eshopkeies = new HashSet<Eshopkey>(0);
	private Set<Aorder> aorders = new HashSet<Aorder>(0);
	private Set<Usedrecord> usedrecords = new HashSet<Usedrecord>(0);

	// Constructors

	/** default constructor */
	public Shop() {
	}

	/** minimal constructor */
	public Shop(Agent agent, String username, String password) {
		this.agent = agent;
		this.username = username;
		this.password = password;
	}

	/** full constructor */
	public Shop(Eshop eshop, Agent agent, String username, String password,
			String name, String keyword, String email, String lastloginip,
			String lastlogintime, String createtime, String address,
			String contact, String phone, String province, String city,
			String zone, String descr, String district, String shoppic2,
			String shoppic3, String shoppic1, Integer level, String begintime,
			String lasttime, Integer yearlimit, Double longitude, String uuid,
			Double latitude, String companyname, Integer status, String weburl,
			String wapurl, Long smsbalance, Long smstotal, Long mmsbalance,
			Long mmstotal, Integer pointbalance, Integer pointotal,
			String jobtype, String subjobtype, String shopphone, String fax,
			Integer packagetype, Integer purview, String auditdescr,
			String newwapurl, Integer auditstatus, String alipayname,
			Long shopid, String route, String cleartime, Double clearamount,
			Double rebate,
			Set<Etbflow> etbflows, Set<Device> devices,
			Set<Systemuser> systemusers, Set<Entshop> entshops,
			Set<Eitem> eitems, Set<Account> accounts, Set<Store> stores,
			Set<Viewrecord> viewrecords, Set<Apikey> apikeies,
			Set<Shopbill> shopbills, Set<Eshopkey> eshopkeies,
			Set<Aorder> aorders, Set<Usedrecord> usedrecords) {
		this.eshop = eshop;
		this.agent = agent;
		this.username = username;
		this.password = password;
		this.name = name;
		this.keyword = keyword;
		this.email = email;
		this.lastloginip = lastloginip;
		this.lastlogintime = lastlogintime;
		this.createtime = createtime;
		this.address = address;
		this.contact = contact;
		this.phone = phone;
		this.province = province;
		this.city = city;
		this.zone = zone;
		this.descr = descr;
		this.district = district;
		this.shoppic2 = shoppic2;
		this.shoppic3 = shoppic3;
		this.shoppic1 = shoppic1;
		this.level = level;
		this.begintime = begintime;
		this.lasttime = lasttime;
		this.yearlimit = yearlimit;
		this.longitude = longitude;
		this.uuid = uuid;
		this.latitude = latitude;
		this.companyname = companyname;
		this.status = status;
		this.weburl = weburl;
		this.wapurl = wapurl;
		this.smsbalance = smsbalance;
		this.smstotal = smstotal;
		this.mmsbalance = mmsbalance;
		this.mmstotal = mmstotal;
		this.pointbalance = pointbalance;
		this.pointotal = pointotal;
		this.jobtype = jobtype;
		this.subjobtype = subjobtype;
		this.shopphone = shopphone;
		this.fax = fax;
		this.packagetype = packagetype;
		this.purview = purview;
		this.auditdescr = auditdescr;
		this.newwapurl = newwapurl;
		this.auditstatus = auditstatus;
		this.alipayname = alipayname;
		this.shopid = shopid;
		this.route = route;
		this.cleartime = cleartime;
		this.clearamount = clearamount;
		this.rebate = rebate;
		this.etbflows = etbflows;
		this.devices = devices;
		this.systemusers = systemusers;
		this.entshops = entshops;
		this.eitems = eitems;
		this.accounts = accounts;
		this.stores = stores;
		this.viewrecords = viewrecords;
		this.apikeies = apikeies;
		this.shopbills = shopbills;
		this.eshopkeies = eshopkeies;
		this.aorders = aorders;
		this.usedrecords = usedrecords;
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
	@JoinColumn(name = "eshopid")
	public Eshop getEshop() {
		return this.eshop;
	}

	public void setEshop(Eshop eshop) {
		this.eshop = eshop;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agentid", nullable = false)
	public Agent getAgent() {
		return this.agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Column(name = "username", nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "keyword", length = 20)
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "lastloginip", length = 20)
	public String getLastloginip() {
		return this.lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	@Column(name = "lastlogintime", length = 100)
	public String getLastlogintime() {
		return this.lastlogintime;
	}

	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	@Column(name = "zone", length = 20)
	public String getZone() {
		return this.zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	@Column(name = "descr")
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "district", length = 50)
	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "shoppic2", length = 50)
	public String getShoppic2() {
		return this.shoppic2;
	}

	public void setShoppic2(String shoppic2) {
		this.shoppic2 = shoppic2;
	}

	@Column(name = "shoppic3", length = 250)
	public String getShoppic3() {
		return this.shoppic3;
	}

	public void setShoppic3(String shoppic3) {
		this.shoppic3 = shoppic3;
	}

	@Column(name = "shoppic1", length = 50)
	public String getShoppic1() {
		return this.shoppic1;
	}

	public void setShoppic1(String shoppic1) {
		this.shoppic1 = shoppic1;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "begintime", length = 20)
	public String getBegintime() {
		return this.begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	@Column(name = "lasttime", length = 20)
	public String getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	@Column(name = "yearlimit")
	public Integer getYearlimit() {
		return this.yearlimit;
	}

	public void setYearlimit(Integer yearlimit) {
		this.yearlimit = yearlimit;
	}

	@Column(name = "longitude", precision = 22, scale = 0)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "uuid", length = 36)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "latitude", precision = 22, scale = 0)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "companyname", length = 50)
	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "weburl")
	public String getWeburl() {
		return this.weburl;
	}

	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}

	@Column(name = "wapurl")
	public String getWapurl() {
		return this.wapurl;
	}

	public void setWapurl(String wapurl) {
		this.wapurl = wapurl;
	}

	@Column(name = "smsbalance")
	public Long getSmsbalance() {
		return this.smsbalance;
	}

	public void setSmsbalance(Long smsbalance) {
		this.smsbalance = smsbalance;
	}

	@Column(name = "smstotal")
	public Long getSmstotal() {
		return this.smstotal;
	}

	public void setSmstotal(Long smstotal) {
		this.smstotal = smstotal;
	}

	@Column(name = "mmsbalance")
	public Long getMmsbalance() {
		return this.mmsbalance;
	}

	public void setMmsbalance(Long mmsbalance) {
		this.mmsbalance = mmsbalance;
	}

	@Column(name = "mmstotal")
	public Long getMmstotal() {
		return this.mmstotal;
	}

	public void setMmstotal(Long mmstotal) {
		this.mmstotal = mmstotal;
	}

	@Column(name = "pointbalance")
	public Integer getPointbalance() {
		return this.pointbalance;
	}

	public void setPointbalance(Integer pointbalance) {
		this.pointbalance = pointbalance;
	}

	@Column(name = "pointotal")
	public Integer getPointotal() {
		return this.pointotal;
	}

	public void setPointotal(Integer pointotal) {
		this.pointotal = pointotal;
	}

	@Column(name = "jobtype", length = 50)
	public String getJobtype() {
		return this.jobtype;
	}

	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}

	@Column(name = "subjobtype", length = 50)
	public String getSubjobtype() {
		return this.subjobtype;
	}

	public void setSubjobtype(String subjobtype) {
		this.subjobtype = subjobtype;
	}

	@Column(name = "shopphone", length = 20)
	public String getShopphone() {
		return this.shopphone;
	}

	public void setShopphone(String shopphone) {
		this.shopphone = shopphone;
	}

	@Column(name = "fax", length = 20)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "packagetype")
	public Integer getPackagetype() {
		return this.packagetype;
	}

	public void setPackagetype(Integer packagetype) {
		this.packagetype = packagetype;
	}

	@Column(name = "purview")
	public Integer getPurview() {
		return this.purview;
	}

	public void setPurview(Integer purview) {
		this.purview = purview;
	}

	@Column(name = "auditdescr", length = 200)
	public String getAuditdescr() {
		return this.auditdescr;
	}

	public void setAuditdescr(String auditdescr) {
		this.auditdescr = auditdescr;
	}

	@Column(name = "newwapurl")
	public String getNewwapurl() {
		return this.newwapurl;
	}

	public void setNewwapurl(String newwapurl) {
		this.newwapurl = newwapurl;
	}

	@Column(name = "auditstatus")
	public Integer getAuditstatus() {
		return this.auditstatus;
	}

	public void setAuditstatus(Integer auditstatus) {
		this.auditstatus = auditstatus;
	}

	@Column(name = "alipayname", length = 100)
	public String getAlipayname() {
		return this.alipayname;
	}

	public void setAlipayname(String alipayname) {
		this.alipayname = alipayname;
	}

	@Column(name = "shopid")
	public Long getShopid() {
		return this.shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	@Column(name = "route")
	public String getRoute() {
		return this.route;
	}

	public void setRoute(String route) {
		this.route = route;
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

	@Column(name = "rebate", precision = 22, scale = 0)
	public Double getRebate() {
		return this.rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Etbflow> getEtbflows() {
		return this.etbflows;
	}

	public void setEtbflows(Set<Etbflow> etbflows) {
		this.etbflows = etbflows;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Device> getDevices() {
		return this.devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Systemuser> getSystemusers() {
		return this.systemusers;
	}

	public void setSystemusers(Set<Systemuser> systemusers) {
		this.systemusers = systemusers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Entshop> getEntshops() {
		return this.entshops;
	}

	public void setEntshops(Set<Entshop> entshops) {
		this.entshops = entshops;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Eitem> getEitems() {
		return this.eitems;
	}

	public void setEitems(Set<Eitem> eitems) {
		this.eitems = eitems;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Store> getStores() {
		return this.stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Viewrecord> getViewrecords() {
		return this.viewrecords;
	}

	public void setViewrecords(Set<Viewrecord> viewrecords) {
		this.viewrecords = viewrecords;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Apikey> getApikeies() {
		return this.apikeies;
	}

	public void setApikeies(Set<Apikey> apikeies) {
		this.apikeies = apikeies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Shopbill> getShopbills() {
		return this.shopbills;
	}

	public void setShopbills(Set<Shopbill> shopbills) {
		this.shopbills = shopbills;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Eshopkey> getEshopkeies() {
		return this.eshopkeies;
	}

	public void setEshopkeies(Set<Eshopkey> eshopkeies) {
		this.eshopkeies = eshopkeies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Aorder> getAorders() {
		return this.aorders;
	}

	public void setAorders(Set<Aorder> aorders) {
		this.aorders = aorders;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shop")
	public Set<Usedrecord> getUsedrecords() {
		return this.usedrecords;
	}

	public void setUsedrecords(Set<Usedrecord> usedrecords) {
		this.usedrecords = usedrecords;
	}

}