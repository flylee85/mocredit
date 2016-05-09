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
 * Shopbilldetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shopbilldetail")
public class Shopbilldetail implements java.io.Serializable {

	// Fields

	private Long id;
	private Shopbill shopbill;
	private Commodity commodity;
	private Long num;
	private Double accountcharge;
	private String createtime;
	private Double sumcharge;
	private Double rebate;
	private Double price;

	// Constructors

	/** default constructor */
	public Shopbilldetail() {
	}

	/** full constructor */
	public Shopbilldetail(Shopbill shopbill, Commodity commodity, Long num,
			Double accountcharge, String createtime, Double sumcharge,
			Double rebate, Double price) {
		this.shopbill = shopbill;
		this.commodity = commodity;
		this.num = num;
		this.accountcharge = accountcharge;
		this.createtime = createtime;
		this.sumcharge = sumcharge;
		this.rebate = rebate;
		this.price = price;
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
	@JoinColumn(name = "shopbillid")
	public Shopbill getShopbill() {
		return this.shopbill;
	}

	public void setShopbill(Shopbill shopbill) {
		this.shopbill = shopbill;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commodityid")
	public Commodity getCommodity() {
		return this.commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	@Column(name = "num")
	public Long getNum() {
		return this.num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	@Column(name = "accountcharge", precision = 22, scale = 0)
	public Double getAccountcharge() {
		return this.accountcharge;
	}

	public void setAccountcharge(Double accountcharge) {
		this.accountcharge = accountcharge;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "sumcharge", precision = 22, scale = 0)
	public Double getSumcharge() {
		return this.sumcharge;
	}

	public void setSumcharge(Double sumcharge) {
		this.sumcharge = sumcharge;
	}

	@Column(name = "rebate", precision = 22, scale = 0)
	public Double getRebate() {
		return this.rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	@Column(name = "price", precision = 22, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}