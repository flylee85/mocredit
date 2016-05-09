package cn.m.mt.po;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Deviceversion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "deviceversion")
public class Deviceversion implements java.io.Serializable {

	// Fields

	private Long id;
	private String versionname;
	private String versionno;
	private String versionurl;
	private Integer type;
	private String descr;
	private String oldversionno;
	private String createtime;
	private Set<Deviceofupdate> deviceofupdates = new HashSet<Deviceofupdate>(0);
	private Set<Devicemodify> devicemodifies = new HashSet<Devicemodify>(0);
	private Set<Device> devices = new HashSet<Device>(0);

	// Constructors

	/** default constructor */
	public Deviceversion() {
	}

	/** full constructor */
	public Deviceversion(String versionname, String versionno,
			String versionurl, Integer type, String descr, String oldversionno,
			String createtime, Set<Deviceofupdate> deviceofupdates,
			Set<Devicemodify> devicemodifies, Set<Device> devices) {
		this.versionname = versionname;
		this.versionno = versionno;
		this.versionurl = versionurl;
		this.type = type;
		this.descr = descr;
		this.oldversionno = oldversionno;
		this.createtime = createtime;
		this.deviceofupdates = deviceofupdates;
		this.devicemodifies = devicemodifies;
		this.devices = devices;
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

	@Column(name = "versionname", length = 20)
	public String getVersionname() {
		return this.versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	@Column(name = "versionno", length = 10)
	public String getVersionno() {
		return this.versionno;
	}

	public void setVersionno(String versionno) {
		this.versionno = versionno;
	}

	@Column(name = "versionurl", length = 100)
	public String getVersionurl() {
		return this.versionurl;
	}

	public void setVersionurl(String versionurl) {
		this.versionurl = versionurl;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "descr", length = 200)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "oldversionno", length = 10)
	public String getOldversionno() {
		return this.oldversionno;
	}

	public void setOldversionno(String oldversionno) {
		this.oldversionno = oldversionno;
	}

	@Column(name = "createtime", length = 30)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deviceversion")
	public Set<Deviceofupdate> getDeviceofupdates() {
		return this.deviceofupdates;
	}

	public void setDeviceofupdates(Set<Deviceofupdate> deviceofupdates) {
		this.deviceofupdates = deviceofupdates;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deviceversion")
	public Set<Devicemodify> getDevicemodifies() {
		return this.devicemodifies;
	}

	public void setDevicemodifies(Set<Devicemodify> devicemodifies) {
		this.devicemodifies = devicemodifies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deviceversion")
	public Set<Device> getDevices() {
		return this.devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

}