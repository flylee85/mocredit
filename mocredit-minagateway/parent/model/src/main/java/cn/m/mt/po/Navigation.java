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
 * Navigation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "navigation")
public class Navigation implements java.io.Serializable {

	// Fields

	private Long id;
	private Navigation navigation;
	private String name;
	private String url;
	private String descr;
	private Integer store;
	private Integer type;
	private Integer status;
	private String purview;
	private Set<Navigation> navigations = new HashSet<Navigation>(0);

	// Constructors

	/** default constructor */
	public Navigation() {
	}

	/** full constructor */
	public Navigation(Navigation navigation, String name, String url,
			String descr, Integer store, Integer type, Integer status,
			String purview, Set<Navigation> navigations) {
		this.navigation = navigation;
		this.name = name;
		this.url = url;
		this.descr = descr;
		this.store = store;
		this.type = type;
		this.status = status;
		this.purview = purview;
		this.navigations = navigations;
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
	@JoinColumn(name = "parntid")
	public Navigation getNavigation() {
		return this.navigation;
	}

	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "url", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "descr", length = 100)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "store")
	public Integer getStore() {
		return this.store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "purview", length = 20)
	public String getPurview() {
		return this.purview;
	}

	public void setPurview(String purview) {
		this.purview = purview;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "navigation")
	public Set<Navigation> getNavigations() {
		return this.navigations;
	}

	public void setNavigations(Set<Navigation> navigations) {
		this.navigations = navigations;
	}

}