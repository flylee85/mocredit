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
 * Eshopkey entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "eshopkey")
public class Eshopkey implements java.io.Serializable {

	// Fields

	private Long id;
	private Shop shop;
	private String appkey;
	private String appsecret;
	private Integer status;
	private String taobaoshop;
	private String taobaourl;
	private String descr;
	private Set<Eitem> eitems = new HashSet<Eitem>(0);

	// Constructors

	/** default constructor */
	public Eshopkey() {
	}

	/** minimal constructor */
	public Eshopkey(String appkey, String appsecret, Integer status) {
		this.appkey = appkey;
		this.appsecret = appsecret;
		this.status = status;
	}

	/** full constructor */
	public Eshopkey(Shop shop, String appkey, String appsecret, Integer status,
			String taobaoshop, String taobaourl, String descr, Set<Eitem> eitems) {
		this.shop = shop;
		this.appkey = appkey;
		this.appsecret = appsecret;
		this.status = status;
		this.taobaoshop = taobaoshop;
		this.taobaourl = taobaourl;
		this.descr = descr;
		this.eitems = eitems;
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

	@Column(name = "appkey", nullable = false, length = 20)
	public String getAppkey() {
		return this.appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	@Column(name = "appsecret", nullable = false, length = 50)
	public String getAppsecret() {
		return this.appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "taobaoshop", length = 100)
	public String getTaobaoshop() {
		return this.taobaoshop;
	}

	public void setTaobaoshop(String taobaoshop) {
		this.taobaoshop = taobaoshop;
	}

	@Column(name = "taobaourl")
	public String getTaobaourl() {
		return this.taobaourl;
	}

	public void setTaobaourl(String taobaourl) {
		this.taobaourl = taobaourl;
	}

	@Column(name = "descr", length = 100)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eshopkey")
	public Set<Eitem> getEitems() {
		return this.eitems;
	}

	public void setEitems(Set<Eitem> eitems) {
		this.eitems = eitems;
	}

}