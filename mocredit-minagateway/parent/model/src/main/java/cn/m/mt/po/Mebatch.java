package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mebatch entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mebatch")
public class Mebatch implements java.io.Serializable {

	// Fields

	private Long id;
	private Long uploadnum;
	private String createtime;
	private String type;
	private String descr;

	// Constructors

	/** default constructor */
	public Mebatch() {
	}

	/** full constructor */
	public Mebatch(Long uploadnum, String createtime, String type, String descr) {
		this.uploadnum = uploadnum;
		this.createtime = createtime;
		this.type = type;
		this.descr = descr;
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

	@Column(name = "uploadnum")
	public Long getUploadnum() {
		return this.uploadnum;
	}

	public void setUploadnum(Long uploadnum) {
		this.uploadnum = uploadnum;
	}

	@Column(name = "createtime", length = 19)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "type", length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "descr", length = 200)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

}