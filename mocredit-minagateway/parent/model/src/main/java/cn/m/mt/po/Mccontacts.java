package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mccontacts entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mccontacts")
public class Mccontacts implements java.io.Serializable {

	// Fields

	private Long id;
	private String phone;
	private String city;
	private Integer status;
	private String numcode;
	private String sendtime;
	private Integer type;
	private String endtime;
	private String createtime;

	// Constructors

	/** default constructor */
	public Mccontacts() {
	}

	/** minimal constructor */
	public Mccontacts(String phone, String city, Integer status, Integer type) {
		this.phone = phone;
		this.city = city;
		this.status = status;
		this.type = type;
	}

	/** full constructor */
	public Mccontacts(String phone, String city, Integer status,
			String numcode, String sendtime, Integer type, String endtime,
			String createtime) {
		this.phone = phone;
		this.city = city;
		this.status = status;
		this.numcode = numcode;
		this.sendtime = sendtime;
		this.type = type;
		this.endtime = endtime;
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

	@Column(name = "phone", nullable = false, length = 11)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "city", nullable = false, length = 20)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "numcode", length = 20)
	public String getNumcode() {
		return this.numcode;
	}

	public void setNumcode(String numcode) {
		this.numcode = numcode;
	}

	@Column(name = "sendtime", length = 30)
	public String getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "endtime", length = 20)
	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	@Column(name = "createtime", length = 30)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}