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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Commodity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "commodity")
public class Commodity implements java.io.Serializable {

	// Fields

	private Long id;
	private Long shopid;
	private String name;
	private Double price;
	private Double rebate;
	private String status;
	private String descr;
	private String createtime;
	private Set<Shopbilldetail> shopbilldetails = new HashSet<Shopbilldetail>(0);
	private Set<Eitem> eitems = new HashSet<Eitem>(0);

	// Constructors

	/** default constructor */
	public Commodity() {
	}

	/** full constructor */
	public Commodity(Long shopid, String name, Double price, Double rebate,
			String status, String descr, String createtime,
			Set<Shopbilldetail> shopbilldetails, Set<Eitem> eitems) {
		this.shopid = shopid;
		this.name = name;
		this.price = price;
		this.rebate = rebate;
		this.status = status;
		this.descr = descr;
		this.createtime = createtime;
		this.shopbilldetails = shopbilldetails;
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

	@Column(name = "shopid")
	public Long getShopid() {
		return this.shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "price", precision = 22, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "rebate", precision = 22, scale = 0)
	public Double getRebate() {
		return this.rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	@Column(name = "status", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "descr")
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "createtime", length = 50)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "commodity")
	public Set<Shopbilldetail> getShopbilldetails() {
		return this.shopbilldetails;
	}

	public void setShopbilldetails(Set<Shopbilldetail> shopbilldetails) {
		this.shopbilldetails = shopbilldetails;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "commodity")
	public Set<Eitem> getEitems() {
		return this.eitems;
	}

	public void setEitems(Set<Eitem> eitems) {
		this.eitems = eitems;
	}

}