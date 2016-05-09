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
 * Categorygroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "categorygroup")
public class Categorygroup implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private Set<Category> categories = new HashSet<Category>(0);

	// Constructors

	/** default constructor */
	public Categorygroup() {
	}

	/** minimal constructor */
	public Categorygroup(String name) {
		this.name = name;
	}

	/** full constructor */
	public Categorygroup(String name, Set<Category> categories) {
		this.name = name;
		this.categories = categories;
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

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "categorygroup")
	public Set<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

}