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
 * Contacts entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "contacts")
public class Contacts implements java.io.Serializable {

	// Fields

	private Long id;
	private Batch batch;
	private String mobile;
	private String customer;
	private String extfield1;
	private String extfield2;
	private String extfield3;
	private Integer status;
	private String importtime;
	private Integer num;

	// Constructors

	/** default constructor */
	public Contacts() {
	}

	/** minimal constructor */
	public Contacts(String mobile, Integer status) {
		this.mobile = mobile;
		this.status = status;
	}

	/** full constructor */
	public Contacts(Batch batch, String mobile, String customer,
			String extfield1, String extfield2, String extfield3,
			Integer status, String importtime, Integer num) {
		this.batch = batch;
		this.mobile = mobile;
		this.customer = customer;
		this.extfield1 = extfield1;
		this.extfield2 = extfield2;
		this.extfield3 = extfield3;
		this.status = status;
		this.importtime = importtime;
		this.num = num;
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
	@JoinColumn(name = "batchid")
	public Batch getBatch() {
		return this.batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	@Column(name = "mobile", nullable = false, length = 11)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "customer", length = 20)
	public String getCustomer() {
		return this.customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Column(name = "extfield1", length = 100)
	public String getExtfield1() {
		return this.extfield1;
	}

	public void setExtfield1(String extfield1) {
		this.extfield1 = extfield1;
	}

	@Column(name = "extfield2", length = 100)
	public String getExtfield2() {
		return this.extfield2;
	}

	public void setExtfield2(String extfield2) {
		this.extfield2 = extfield2;
	}

	@Column(name = "extfield3", length = 100)
	public String getExtfield3() {
		return this.extfield3;
	}

	public void setExtfield3(String extfield3) {
		this.extfield3 = extfield3;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "importtime", length = 19)
	public String getImporttime() {
		return this.importtime;
	}

	public void setImporttime(String importtime) {
		this.importtime = importtime;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}