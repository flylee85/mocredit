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
 * Devicemodify entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "devicemodify")
public class Devicemodify implements java.io.Serializable {

	// Fields

	private Long id;
	private Deviceversion deviceversion;
	private String devicetype;
	private String devicecode;
	private String createtime;

	// Constructors

	/** default constructor */
	public Devicemodify() {
	}

	/** full constructor */
	public Devicemodify(Deviceversion deviceversion, String devicetype,
			String devicecode, String createtime) {
		this.deviceversion = deviceversion;
		this.devicetype = devicetype;
		this.devicecode = devicecode;
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
	@JoinColumn(name = "versionid")
	public Deviceversion getDeviceversion() {
		return this.deviceversion;
	}

	public void setDeviceversion(Deviceversion deviceversion) {
		this.deviceversion = deviceversion;
	}

	@Column(name = "devicetype", length = 10)
	public String getDevicetype() {
		return this.devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	@Column(name = "devicecode", length = 50)
	public String getDevicecode() {
		return this.devicecode;
	}

	public void setDevicecode(String devicecode) {
		this.devicecode = devicecode;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}