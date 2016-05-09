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
 * Menu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "menu")
public class Menu implements java.io.Serializable {

	// Fields

	private Long id;
	private Menu menu;
	private String url;
	private String name;
	private String createtime;
	private String descr;
	private Integer permission;
	private String actionname;
	private String flag;
	private Integer sortorder;
	private Integer group;
	private String entidstr;
	private Set<Menu> menus = new HashSet<Menu>(0);
	private Set<Rolemenu> rolemenus = new HashSet<Rolemenu>(0);

	// Constructors

	/** default constructor */
	public Menu() {
	}

	/** minimal constructor */
	public Menu(String url, String name, String createtime, Integer group) {
		this.url = url;
		this.name = name;
		this.createtime = createtime;
		this.group = group;
	}

	/** full constructor */
	public Menu(Menu menu, String url, String name, String createtime,
			String descr, Integer permission, String actionname, String flag,
			Integer sortorder, Integer group, String entidstr, Set<Menu> menus,
			Set<Rolemenu> rolemenus) {
		this.menu = menu;
		this.url = url;
		this.name = name;
		this.createtime = createtime;
		this.descr = descr;
		this.permission = permission;
		this.actionname = actionname;
		this.flag = flag;
		this.sortorder = sortorder;
		this.group = group;
		this.entidstr = entidstr;
		this.menus = menus;
		this.rolemenus = rolemenus;
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
	@JoinColumn(name = "parentid")
	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Column(name = "url", nullable = false, length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "createtime", nullable = false, length = 19)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "descr", length = 200)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "permission")
	public Integer getPermission() {
		return this.permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	@Column(name = "actionname", length = 40)
	public String getActionname() {
		return this.actionname;
	}

	public void setActionname(String actionname) {
		this.actionname = actionname;
	}

	@Column(name = "flag", length = 10)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "sortorder")
	public Integer getSortorder() {
		return this.sortorder;
	}

	public void setSortorder(Integer sortorder) {
		this.sortorder = sortorder;
	}

	@Column(name = "group", nullable = false)
	public Integer getGroup() {
		return this.group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	@Column(name = "entidstr", length = 200)
	public String getEntidstr() {
		return this.entidstr;
	}

	public void setEntidstr(String entidstr) {
		this.entidstr = entidstr;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
	public Set<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
	public Set<Rolemenu> getRolemenus() {
		return this.rolemenus;
	}

	public void setRolemenus(Set<Rolemenu> rolemenus) {
		this.rolemenus = rolemenus;
	}

}