package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bank entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CONFIG_PROPERTIES")
public class CONFIG_PROPERTIES implements java.io.Serializable {

	private static final long serialVersionUID = 1069491884578368095L;
	private Long id;
	private String thekey;
	private String thevalue;
	private String theremark;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getThekey() {
		return thekey;
	}

	public void setThekey(String thekey) {
		this.thekey = thekey;
	}

	public String getThevalue() {
		return thevalue;
	}

	public void setThevalue(String thevalue) {
		this.thevalue = thevalue;
	}

	public String getTheremark() {
		return theremark;
	}

	public void setTheremark(String theremark) {
		this.theremark = theremark;
	}

}