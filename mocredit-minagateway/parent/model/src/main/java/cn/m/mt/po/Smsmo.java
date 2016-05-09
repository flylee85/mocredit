package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Smsmo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "smsmo")
public class Smsmo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String mobile;
	private String content;
	private String sendtime;
	private String createtime;

	// Constructors

	/** default constructor */
	public Smsmo() {
	}

	/** full constructor */
	public Smsmo(String mobile, String content, String createtime) {
		this.mobile = mobile;
		this.content = content;
		this.createtime = createtime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "mobile", length = 13)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "content", length = 16777215)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	@Column(name = "sendtime", length = 20)
	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

}