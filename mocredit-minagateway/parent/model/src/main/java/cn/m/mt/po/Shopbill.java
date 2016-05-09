package cn.m.mt.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Shopbill entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shopbill")
public class Shopbill implements java.io.Serializable {

	// Fields

	private Long id;
	private Shop shop;
	private String accounttime;
	private Double totalcharge;
	private String createtime;
	private Set<Shopbilldetail> shopbilldetails = new HashSet<Shopbilldetail>(0);

	// Constructors

	/** default constructor */
	public Shopbill() {
	}

	/** minimal constructor */
	public Shopbill(Long id) {
		this.id = id;
	}

	/** full constructor */
	public Shopbill(Long id, Shop shop, String accounttime, Double totalcharge,
			String createtime, Set<Shopbilldetail> shopbilldetails) {
		this.id = id;
		this.shop = shop;
		this.accounttime = accounttime;
		this.totalcharge = totalcharge;
		this.createtime = createtime;
		this.shopbilldetails = shopbilldetails;
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

	@Column(name = "accounttime", length = 20)
	public String getAccounttime() {
		return this.accounttime;
	}

	public void setAccounttime(String accounttime) {
		this.accounttime = accounttime;
	}

	@Column(name = "totalcharge", precision = 22, scale = 0)
	public Double getTotalcharge() {
		return this.totalcharge;
	}

	public void setTotalcharge(Double totalcharge) {
		this.totalcharge = totalcharge;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shopbill")
	public Set<Shopbilldetail> getShopbilldetails() {
		return this.shopbilldetails;
	}

	public void setShopbilldetails(Set<Shopbilldetail> shopbilldetails) {
		this.shopbilldetails = shopbilldetails;
	}

}