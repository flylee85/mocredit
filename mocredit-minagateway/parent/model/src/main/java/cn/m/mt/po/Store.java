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
 * Store entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "store")
public class Store implements java.io.Serializable {

	// Fields

	private Long id;
	private Shop shop;
	private String name;
	private String address;
	private String guid;
	private String province;
	private String city;
	private String zone;
	private String district;
	private String mobile;
	private String username;
	private String password;
	private Integer status;
	private String phone;
	private String mapurl;
	private Double longitude;
	private Double latitude;
	private String createtime;
	private Set<Usedrecord> usedrecords = new HashSet<Usedrecord>(0);
	private Set<Aflowlog> aflowlogs = new HashSet<Aflowlog>(0);
	private Set<Eitemstore> eitemstores = new HashSet<Eitemstore>(0);
	private Set<Device> devices = new HashSet<Device>(0);
	private Set<Aorder> aorders = new HashSet<Aorder>(0);
	private Set<Systemuser> systemusers = new HashSet<Systemuser>(0);
	private Set<Checklog> checklogs = new HashSet<Checklog>(0);

	// Constructors

	/** default constructor */
	public Store() {
	}

	/** minimal constructor */
	public Store(String mobile, String username, String password) {
		this.mobile = mobile;
		this.username = username;
		this.password = password;
	}

	/** full constructor */
	public Store(Shop shop, String name, String address, String guid,
			String province, String city, String zone, String district,
			String mobile, String username, String password, Integer status,
			String phone, String mapurl, Double longitude, Double latitude,
			String createtime, Set<Usedrecord> usedrecords,
			Set<Aflowlog> aflowlogs, Set<Eitemstore> eitemstores,
			Set<Device> devices, Set<Aorder> aorders,
			Set<Systemuser> systemusers, Set<Checklog> checklogs) {
		this.shop = shop;
		this.name = name;
		this.address = address;
		this.guid = guid;
		this.province = province;
		this.city = city;
		this.zone = zone;
		this.district = district;
		this.mobile = mobile;
		this.username = username;
		this.password = password;
		this.status = status;
		this.phone = phone;
		this.mapurl = mapurl;
		this.longitude = longitude;
		this.latitude = latitude;
		this.createtime = createtime;
		this.usedrecords = usedrecords;
		this.aflowlogs = aflowlogs;
		this.eitemstores = eitemstores;
		this.devices = devices;
		this.aorders = aorders;
		this.systemusers = systemusers;
		this.checklogs = checklogs;
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
	@JoinColumn(name = "shopid")
	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address", length = 50)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "guid", length = 36)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
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

	@Column(name = "district", length = 50)
	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "mobile", nullable = false, length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "mapurl", length = 50)
	public String getMapurl() {
		return this.mapurl;
	}

	public void setMapurl(String mapurl) {
		this.mapurl = mapurl;
	}

	@Column(name = "longitude", precision = 22, scale = 0)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", precision = 22, scale = 0)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "store")
	public Set<Usedrecord> getUsedrecords() {
		return this.usedrecords;
	}

	public void setUsedrecords(Set<Usedrecord> usedrecords) {
		this.usedrecords = usedrecords;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "store")
	public Set<Aflowlog> getAflowlogs() {
		return this.aflowlogs;
	}

	public void setAflowlogs(Set<Aflowlog> aflowlogs) {
		this.aflowlogs = aflowlogs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "store")
	public Set<Eitemstore> getEitemstores() {
		return this.eitemstores;
	}

	public void setEitemstores(Set<Eitemstore> eitemstores) {
		this.eitemstores = eitemstores;
	}

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "store")
	public Set<Device> getDevices() {
		return this.devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "store")
	public Set<Aorder> getAorders() {
		return this.aorders;
	}

	public void setAorders(Set<Aorder> aorders) {
		this.aorders = aorders;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "store")
	public Set<Systemuser> getSystemusers() {
		return this.systemusers;
	}

	public void setSystemusers(Set<Systemuser> systemusers) {
		this.systemusers = systemusers;
	}

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "store")
	public Set<Checklog> getChecklogs() {
		return this.checklogs;
	}

	public void setChecklogs(Set<Checklog> checklogs) {
		this.checklogs = checklogs;
	}

}