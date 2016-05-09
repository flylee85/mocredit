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
 * Smsrecharge entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "smsrecharge")
public class Smsrecharge implements java.io.Serializable {

	// Fields

	private Long id;
	private Enterprise enterprise;
	private String inputtime;
	private Integer balance;
	private String descr;
	private Integer num;
	private Integer type;

	// Constructors

	/** default constructor */
	public Smsrecharge() {
	}

	/** full constructor */
	public Smsrecharge(Enterprise enterprise, String inputtime,
			Integer balance, String descr, Integer num, Integer type) {
		this.enterprise = enterprise;
		this.inputtime = inputtime;
		this.balance = balance;
		this.descr = descr;
		this.num = num;
		this.type = type;
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
	@JoinColumn(name = "entid")
	public Enterprise getEnterprise() {
		return this.enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	@Column(name = "inputtime", length = 20)
	public String getInputtime() {
		return this.inputtime;
	}

	public void setInputtime(String inputtime) {
		this.inputtime = inputtime;
	}

	@Column(name = "balance")
	public Integer getBalance() {
		return this.balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	@Column(name = "descr", length = 100)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}