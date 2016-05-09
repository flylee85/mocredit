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
 * Overorderlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "overorderlog")
public class Overorderlog implements java.io.Serializable {

	// Fields

	private Long id;
	private Eitem eitem;
	private String tid;
	private Double payment;
	private String createtime;

	// Constructors

	/** default constructor */
	public Overorderlog() {
	}

	/** minimal constructor */
	public Overorderlog(String tid) {
		this.tid = tid;
	}

	/** full constructor */
	public Overorderlog(Eitem eitem, String tid, Double payment,
			String createtime) {
		this.eitem = eitem;
		this.tid = tid;
		this.payment = payment;
		this.createtime = createtime;
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
	@JoinColumn(name = "eitemid")
	public Eitem getEitem() {
		return this.eitem;
	}

	public void setEitem(Eitem eitem) {
		this.eitem = eitem;
	}

	@Column(name = "tid", nullable = false, length = 40)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "payment", precision = 22, scale = 0)
	public Double getPayment() {
		return this.payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}