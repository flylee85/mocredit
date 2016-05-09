package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Snrecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "snrecord")
public class Snrecord implements java.io.Serializable {

	// Fields

	private Long id;
	private String sn;
	private String serverno;
	private String password;
	private Integer balance;
	private Integer total;
	private String descr;
	private Integer addtotal;

	// Constructors

	/** default constructor */
	public Snrecord() {
	}

	/** full constructor */
	public Snrecord(String sn, String serverno, String password,
			Integer balance, Integer total, String descr, Integer addtotal) {
		this.sn = sn;
		this.serverno = serverno;
		this.password = password;
		this.balance = balance;
		this.total = total;
		this.descr = descr;
		this.addtotal = addtotal;
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

	@Column(name = "sn", length = 50)
	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name = "serverno", length = 20)
	public String getServerno() {
		return this.serverno;
	}

	public void setServerno(String serverno) {
		this.serverno = serverno;
	}

	@Column(name = "password", length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "balance")
	public Integer getBalance() {
		return this.balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	@Column(name = "total")
	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Column(name = "descr", length = 50)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "addtotal")
	public Integer getAddtotal() {
		return this.addtotal;
	}

	public void setAddtotal(Integer addtotal) {
		this.addtotal = addtotal;
	}

}