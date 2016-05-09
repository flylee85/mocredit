package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mcnsegment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mcnsegment")
public class Mcnsegment implements java.io.Serializable {

	// Fields

	private Long mcnsegmentid;
	private String segment;
	private String province;
	private String city;
	private String area;

	// Constructors

	/** default constructor */
	public Mcnsegment() {
	}

	/** minimal constructor */
	public Mcnsegment(Long mcnsegmentid) {
		this.mcnsegmentid = mcnsegmentid;
	}

	/** full constructor */
	public Mcnsegment(Long mcnsegmentid, String segment, String province,
			String city, String area) {
		this.mcnsegmentid = mcnsegmentid;
		this.segment = segment;
		this.province = province;
		this.city = city;
		this.area = area;
	}

	// Property accessors
	@Id
	@Column(name = "MCNSEGMENTID", unique = true, nullable = false)
	public Long getMcnsegmentid() {
		return this.mcnsegmentid;
	}

	public void setMcnsegmentid(Long mcnsegmentid) {
		this.mcnsegmentid = mcnsegmentid;
	}

	@Column(name = "SEGMENT")
	public String getSegment() {
		return this.segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	@Column(name = "PROVINCE", length = 10)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "CITY", length = 30)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "AREA")
	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}