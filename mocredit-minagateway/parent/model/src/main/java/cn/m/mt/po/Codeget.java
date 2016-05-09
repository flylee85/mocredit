package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Codeget entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "codeget")
public class Codeget implements java.io.Serializable {

	// Fields

	private Long id;
	private String mobile;
	private String createtime;
	private String code;
	private Integer num;

	// Constructors

	/** default constructor */
	public Codeget() {
	}

	/** full constructor */
	public Codeget(String mobile, String createtime, String code, Integer num) {
		this.mobile = mobile;
		this.createtime = createtime;
		this.code = code;
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

	@Column(name = "mobile", length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "code", length = 6)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}