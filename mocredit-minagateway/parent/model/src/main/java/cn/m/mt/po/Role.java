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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role")
public class Role implements java.io.Serializable {

	// Fields

	private Long id;
	private Systemuser systemuser;
	private String rolename;
	private String descr;
	private String createtime;
	private Set<Rolemenu> rolemenus = new HashSet<Rolemenu>(0);
	private Set<Roleuser> roleusers = new HashSet<Roleuser>(0);

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String rolename, String createtime) {
		this.rolename = rolename;
		this.createtime = createtime;
	}

	/** full constructor */
	public Role(Systemuser systemuser, String rolename, String descr,
			String createtime, Set<Rolemenu> rolemenus, Set<Roleuser> roleusers) {
		this.systemuser = systemuser;
		this.rolename = rolename;
		this.descr = descr;
		this.createtime = createtime;
		this.rolemenus = rolemenus;
		this.roleusers = roleusers;
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
	@JoinColumn(name = "systemuserid")
	public Systemuser getSystemuser() {
		return this.systemuser;
	}

	public void setSystemuser(Systemuser systemuser) {
		this.systemuser = systemuser;
	}

	@Column(name = "rolename", nullable = false, length = 40)
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Column(name = "descr", length = 200)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "createtime", nullable = false, length = 19)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	public Set<Rolemenu> getRolemenus() {
		return this.rolemenus;
	}

	public void setRolemenus(Set<Rolemenu> rolemenus) {
		this.rolemenus = rolemenus;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	public Set<Roleuser> getRoleusers() {
		return this.roleusers;
	}

	public void setRoleusers(Set<Roleuser> roleusers) {
		this.roleusers = roleusers;
	}

}