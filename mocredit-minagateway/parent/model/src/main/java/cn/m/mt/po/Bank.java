package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bank entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bank")
public class Bank implements java.io.Serializable {

	// Fields

	private Long id;
	private String bankname;
	private String sname;

	// Constructors

	/** default constructor */
	public Bank() {
	}

	/** full constructor */
	public Bank(String bankname, String sname) {
		this.bankname = bankname;
		this.sname = sname;
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

	@Column(name = "bankname", length = 20)
	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	@Column(name = "sname", length = 20)
	public String getSname() {
		return this.sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

}