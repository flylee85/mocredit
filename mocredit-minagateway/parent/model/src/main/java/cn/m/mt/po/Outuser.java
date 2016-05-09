package cn.m.mt.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "outuser")
public class Outuser implements java.io.Serializable {
	private static final long serialVersionUID = -8447022206509768620L;

	private Long id;
	private String username;
	private String password;
	private String eitemids;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEitemids() {
		return eitemids;
	}

	public void setEitemids(String eitemids) {
		this.eitemids = eitemids;
	}

}