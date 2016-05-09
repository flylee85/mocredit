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
 * Roleuser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "roleuser")
public class Roleuser implements java.io.Serializable {

	// Fields

	private Long id;
	private Role role;
	private Systemuser systemuser;

	// Constructors

	/** default constructor */
	public Roleuser() {
	}

	/** minimal constructor */
	public Roleuser(Role role) {
		this.role = role;
	}

	/** full constructor */
	public Roleuser(Role role, Systemuser systemuser) {
		this.role = role;
		this.systemuser = systemuser;
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
	@JoinColumn(name = "roleid", nullable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "systemuserid")
	public Systemuser getSystemuser() {
		return this.systemuser;
	}

	public void setSystemuser(Systemuser systemuser) {
		this.systemuser = systemuser;
	}

}