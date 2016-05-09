package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Subscribe entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "subscribe")
public class Subscribe implements java.io.Serializable {

	// Fields

	private Long id;
	private String target;
	private Integer struts;
	private String createtime;

	// Constructors

	/** default constructor */
	public Subscribe() {
	}

	/** full constructor */
	public Subscribe(String target, Integer struts, String createtime) {
		this.target = target;
		this.struts = struts;
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

	@Column(name = "target", length = 50)
	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Column(name = "struts")
	public Integer getStruts() {
		return this.struts;
	}

	public void setStruts(Integer struts) {
		this.struts = struts;
	}

	@Column(name = "createtime", length = 50)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}