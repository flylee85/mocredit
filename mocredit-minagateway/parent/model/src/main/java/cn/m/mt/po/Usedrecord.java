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
 * Usedrecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "usedrecord")
public class Usedrecord implements java.io.Serializable {

	// Fields

	private Long id;
	private Shop shop;
	private Store store;
	private String storename;
	private String mobilemei;
	private String mobilephone;
	private String customer;
	private String extfield2;
	private String extfield1;
	private String extfield3;
	private String verifydatetime;
	private String verifyresult;
	private String verifymode;
	private Long couponid;
	private String couponname;
	private String shopname;
	private String email;
	private Integer usednum;

	// Constructors

	/** default constructor */
	public Usedrecord() {
	}

	/** minimal constructor */
	public Usedrecord(Integer usednum) {
		this.usednum = usednum;
	}

	/** full constructor */
	public Usedrecord(Shop shop, Store store, String storename,
			String mobilemei, String mobilephone, String customer,
			String extfield2, String extfield1, String extfield3,
			String verifydatetime, String verifyresult, String verifymode,
			Long couponid, String couponname, String shopname, String email,
			Integer usednum) {
		this.shop = shop;
		this.store = store;
		this.storename = storename;
		this.mobilemei = mobilemei;
		this.mobilephone = mobilephone;
		this.customer = customer;
		this.extfield2 = extfield2;
		this.extfield1 = extfield1;
		this.extfield3 = extfield3;
		this.verifydatetime = verifydatetime;
		this.verifyresult = verifyresult;
		this.verifymode = verifymode;
		this.couponid = couponid;
		this.couponname = couponname;
		this.shopname = shopname;
		this.email = email;
		this.usednum = usednum;
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
	@JoinColumn(name = "storeid")
	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Column(name = "storename", length = 50)
	public String getStorename() {
		return this.storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	@Column(name = "mobilemei", length = 50)
	public String getMobilemei() {
		return this.mobilemei;
	}

	public void setMobilemei(String mobilemei) {
		this.mobilemei = mobilemei;
	}

	@Column(name = "mobilephone", length = 11)
	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	@Column(name = "customer", length = 20)
	public String getCustomer() {
		return this.customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Column(name = "extfield2", length = 50)
	public String getExtfield2() {
		return this.extfield2;
	}

	public void setExtfield2(String extfield2) {
		this.extfield2 = extfield2;
	}

	@Column(name = "extfield1", length = 50)
	public String getExtfield1() {
		return this.extfield1;
	}

	public void setExtfield1(String extfield1) {
		this.extfield1 = extfield1;
	}

	@Column(name = "extfield3", length = 50)
	public String getExtfield3() {
		return this.extfield3;
	}

	public void setExtfield3(String extfield3) {
		this.extfield3 = extfield3;
	}

	@Column(name = "verifydatetime", length = 20)
	public String getVerifydatetime() {
		return this.verifydatetime;
	}

	public void setVerifydatetime(String verifydatetime) {
		this.verifydatetime = verifydatetime;
	}

	@Column(name = "verifyresult", length = 20)
	public String getVerifyresult() {
		return this.verifyresult;
	}

	public void setVerifyresult(String verifyresult) {
		this.verifyresult = verifyresult;
	}

	@Column(name = "verifymode", length = 20)
	public String getVerifymode() {
		return this.verifymode;
	}

	public void setVerifymode(String verifymode) {
		this.verifymode = verifymode;
	}

	@Column(name = "couponid")
	public Long getCouponid() {
		return this.couponid;
	}

	public void setCouponid(Long couponid) {
		this.couponid = couponid;
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

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "usednum", nullable = false)
	public Integer getUsednum() {
		return this.usednum;
	}

	public void setUsednum(Integer usednum) {
		this.usednum = usednum;
	}

}