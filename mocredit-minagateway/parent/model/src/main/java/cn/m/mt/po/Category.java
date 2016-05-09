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
 * Category entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "category")
public class Category implements java.io.Serializable {

	// Fields

	private Long id;
	private Categorygroup categorygroup;
	private String name;
	private String descr;

	// Constructors

	/** default constructor */
	public Category() {
	}

	/** minimal constructor */
	public Category(String name) {
		this.name = name;
	}

	/** full constructor */
	public Category(Categorygroup categorygroup, String name, String descr) {
		this.categorygroup = categorygroup;
		this.name = name;
		this.descr = descr;
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
	@JoinColumn(name = "groupid")
	public Categorygroup getCategorygroup() {
		return this.categorygroup;
	}

	public void setCategorygroup(Categorygroup categorygroup) {
		this.categorygroup = categorygroup;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "descr", length = 100)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

}