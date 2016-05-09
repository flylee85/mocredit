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
 * Rolemenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "rolemenu")
public class Rolemenu implements java.io.Serializable {

	// Fields

	private Long id;
	private Menu menu;
	private Role role;
	private Integer permission;
	private String createtime;

	// Constructors

	/** default constructor */
	public Rolemenu() {
	}

	/** full constructor */
	public Rolemenu(Menu menu, Role role, Integer permission, String createtime) {
		this.menu = menu;
		this.role = role;
		this.permission = permission;
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
	@JoinColumn(name = "menuid", nullable = false)
	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid", nullable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "permission", nullable = false)
	public Integer getPermission() {
		return this.permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	@Column(name = "createtime", nullable = false, length = 19)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}