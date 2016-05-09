package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Paymentcodelibrary entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "paymentcodelibrary", uniqueConstraints = {
		@UniqueConstraint(columnNames = "numcode"),
		@UniqueConstraint(columnNames = "charcode") })
public class Paymentcodelibrary implements java.io.Serializable {

	// Fields

	private Long id;
	private String numcode;
	private String charcode;
	private Integer status;

	// Constructors

	/** default constructor */
	public Paymentcodelibrary() {
	}

	/** full constructor */
	public Paymentcodelibrary(String numcode, String charcode, Integer status) {
		this.numcode = numcode;
		this.charcode = charcode;
		this.status = status;
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

	@Column(name = "numcode", unique = true, nullable = false, length = 20)
	public String getNumcode() {
		return this.numcode;
	}

	public void setNumcode(String numcode) {
		this.numcode = numcode;
	}

	@Column(name = "charcode", unique = true, nullable = false, length = 20)
	public String getCharcode() {
		return this.charcode;
	}

	public void setCharcode(String charcode) {
		this.charcode = charcode;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}