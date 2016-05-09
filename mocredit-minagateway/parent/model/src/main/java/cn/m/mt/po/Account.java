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
 * Account entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "account")
public class Account implements java.io.Serializable {

	// Fields

	private Long id;
	private Shop shop;
	private Enterprise enterprise;
	private User user;
	private Double balance;
	private Double totalmoneyin;
	private String password;
	private Double totalmoneyout;
	private Set<Charge> charges = new HashSet<Charge>(0);

	// Constructors

	/** default constructor */
	public Account() {
	}

	/** full constructor */
	public Account(Shop shop, Enterprise enterprise, User user, Double balance,
			Double totalmoneyin, String password, Double totalmoneyout,
			Set<Charge> charges) {
		this.shop = shop;
		this.enterprise = enterprise;
		this.user = user;
		this.balance = balance;
		this.totalmoneyin = totalmoneyin;
		this.password = password;
		this.totalmoneyout = totalmoneyout;
		this.charges = charges;
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
	@JoinColumn(name = "entid")
	public Enterprise getEnterprise() {
		return this.enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "balance", precision = 22, scale = 0)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Column(name = "totalmoneyin", precision = 22, scale = 0)
	public Double getTotalmoneyin() {
		return this.totalmoneyin;
	}

	public void setTotalmoneyin(Double totalmoneyin) {
		this.totalmoneyin = totalmoneyin;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "totalmoneyout", precision = 22, scale = 0)
	public Double getTotalmoneyout() {
		return this.totalmoneyout;
	}

	public void setTotalmoneyout(Double totalmoneyout) {
		this.totalmoneyout = totalmoneyout;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
	public Set<Charge> getCharges() {
		return this.charges;
	}

	public void setCharges(Set<Charge> charges) {
		this.charges = charges;
	}

}