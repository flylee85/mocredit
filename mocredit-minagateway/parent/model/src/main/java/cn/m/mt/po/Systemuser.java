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
 * Systemuser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "systemuser")
public class Systemuser implements java.io.Serializable {

	// Fields

	private Long id;
	private Shop shop;
	private Store store;
	private Enterprise enterprise;
	private Agent agent;
	private String username;
	private String password;
	private String name;
	private String email;
	private Integer purview;
	private String lastloginip;
	private String lastlogintime;
	private String createtime;
	private String descr;
	private Integer audit;
	private Set<Role> roles = new HashSet<Role>(0);
	private Set<Roleuser> roleusers = new HashSet<Roleuser>(0);

	// Constructors

	/** default constructor */
	public Systemuser() {
	}

	/** minimal constructor */
	public Systemuser(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/** full constructor */
	public Systemuser(Shop shop, Store store, Enterprise enterprise,
			Agent agent, String username, String password, String name,
			String email, Integer purview, String lastloginip,
			String lastlogintime, String createtime, String descr,
			Integer audit, Set<Role> roles, Set<Roleuser> roleusers) {
		this.shop = shop;
		this.store = store;
		this.enterprise = enterprise;
		this.agent = agent;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.purview = purview;
		this.lastloginip = lastloginip;
		this.lastlogintime = lastlogintime;
		this.createtime = createtime;
		this.descr = descr;
		this.audit = audit;
		this.roles = roles;
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
	@JoinColumn(name = "shopid")
	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeid")
	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entid")
	public Enterprise getEnterprise() {
		return this.enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agentid")
	public Agent getAgent() {
		return this.agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Column(name = "username", nullable = false, length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "purview")
	public Integer getPurview() {
		return this.purview;
	}

	public void setPurview(Integer purview) {
		this.purview = purview;
	}

	@Column(name = "lastloginip", length = 20)
	public String getLastloginip() {
		return this.lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	@Column(name = "lastlogintime", length = 20)
	public String getLastlogintime() {
		return this.lastlogintime;
	}

	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "descr", length = 100)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "audit")
	public Integer getAudit() {
		return this.audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemuser")
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemuser")
	public Set<Roleuser> getRoleusers() {
		return this.roleusers;
	}

	public void setRoleusers(Set<Roleuser> roleusers) {
		this.roleusers = roleusers;
	}

}