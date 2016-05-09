package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Syslog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "syslog")
public class Syslog implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userid;
	private String username;
	private String name;
	private Integer usertype;
	private String class_;
	private String mothod;
	private String createtime;
	private String loglevel;
	private String msg;
	private String ip;

	// Constructors

	/** default constructor */
	public Syslog() {
	}

	/** minimal constructor */
	public Syslog(Long userid, String username) {
		this.userid = userid;
		this.username = username;
	}

	/** full constructor */
	public Syslog(Long userid, String username, String name, Integer usertype,
			String class_, String mothod, String createtime, String loglevel,
			String msg, String ip) {
		this.userid = userid;
		this.username = username;
		this.name = name;
		this.usertype = usertype;
		this.class_ = class_;
		this.mothod = mothod;
		this.createtime = createtime;
		this.loglevel = loglevel;
		this.msg = msg;
		this.ip = ip;
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

	@Column(name = "userid", nullable = false)
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "username", nullable = false, length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "usertype")
	public Integer getUsertype() {
		return this.usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	@Column(name = "class", length = 50)
	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	@Column(name = "mothod", length = 50)
	public String getMothod() {
		return this.mothod;
	}

	public void setMothod(String mothod) {
		this.mothod = mothod;
	}

	@Column(name = "createtime", length = 30)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "loglevel", length = 20)
	public String getLoglevel() {
		return this.loglevel;
	}

	public void setLoglevel(String loglevel) {
		this.loglevel = loglevel;
	}

	@Column(name = "msg", length = 500)
	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Column(name = "ip", length = 20)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}