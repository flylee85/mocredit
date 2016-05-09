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
 * Viewrecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "viewrecord")
public class Viewrecord implements java.io.Serializable {

	// Fields

	private Long id;
	private Shop shop;
	private Apikey apikey;
	private String ip;
	private String createtime;
	private String province;
	private String city;
	private Long couponid;
	private String mobile;
	private String ua;
	private String source;
	private String couponname;
	private String shopname;
	private String imei;

	// Constructors

	/** default constructor */
	public Viewrecord() {
	}

	/** full constructor */
	public Viewrecord(Shop shop, Apikey apikey, String ip, String createtime,
			String province, String city, Long couponid, String mobile,
			String ua, String source, String couponname, String shopname,
			String imei) {
		this.shop = shop;
		this.apikey = apikey;
		this.ip = ip;
		this.createtime = createtime;
		this.province = province;
		this.city = city;
		this.couponid = couponid;
		this.mobile = mobile;
		this.ua = ua;
		this.source = source;
		this.couponname = couponname;
		this.shopname = shopname;
		this.imei = imei;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apikeyid")
	public Apikey getApikey() {
		return this.apikey;
	}

	public void setApikey(Apikey apikey) {
		this.apikey = apikey;
	}

	@Column(name = "ip", length = 30)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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

	@Column(name = "couponid")
	public Long getCouponid() {
		return this.couponid;
	}

	public void setCouponid(Long couponid) {
		this.couponid = couponid;
	}

	@Column(name = "mobile", length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "ua", length = 100)
	public String getUa() {
		return this.ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	@Column(name = "source", length = 250)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "couponname", length = 50)
	public String getCouponname() {
		return this.couponname;
	}

	public void setCouponname(String couponname) {
		this.couponname = couponname;
	}

	@Column(name = "shopname", length = 50)
	public String getShopname() {
		return this.shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	@Column(name = "imei", length = 50)
	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

}