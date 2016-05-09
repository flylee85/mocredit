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
 * Xauthtoken entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "xauthtoken")
public class Xauthtoken implements java.io.Serializable {

	// Fields

	private Long id;
	private User user;
	private String accesstoken;
	private String createtime;

	// Constructors

	/** default constructor */
	public Xauthtoken() {
	}

	/** minimal constructor */
	public Xauthtoken(User user, String accesstoken) {
		this.user = user;
		this.accesstoken = accesstoken;
	}

	/** full constructor */
	public Xauthtoken(User user, String accesstoken, String createtime) {
		this.user = user;
		this.accesstoken = accesstoken;
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
	@JoinColumn(name = "userid", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "accesstoken", nullable = false, length = 32)
	public String getAccesstoken() {
		return this.accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	@Column(name = "createtime", length = 19)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}