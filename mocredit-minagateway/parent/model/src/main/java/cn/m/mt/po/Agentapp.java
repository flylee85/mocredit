package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Agentapp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "agentapp")
public class Agentapp implements java.io.Serializable {

	// Fields

	private Long id;
	private String descr;
	private String name;
	private String address;
	private String phone;
	private String email;
	private String contact;
	private String fax;
	private String scale;
	private String province;
	private String city;
	private String inputtime;

	// Constructors

	/** default constructor */
	public Agentapp() {
	}

	/** full constructor */
	public Agentapp(String descr, String name, String address, String phone,
			String email, String contact, String fax, String scale,
			String province, String city, String inputtime) {
		this.descr = descr;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.contact = contact;
		this.fax = fax;
		this.scale = scale;
		this.province = province;
		this.city = city;
		this.inputtime = inputtime;
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

	@Column(name = "descr", length = 200)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address", length = 50)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email", length = 20)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "contact", length = 8)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "fax", length = 20)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "scale", length = 10)
	public String getScale() {
		return this.scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	@Column(name = "province", length = 20)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city", length = 20)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "inputtime", length = 20)
	public String getInputtime() {
		return this.inputtime;
	}

	public void setInputtime(String inputtime) {
		this.inputtime = inputtime;
	}

}