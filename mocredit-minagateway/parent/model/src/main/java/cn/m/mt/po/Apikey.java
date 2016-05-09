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
 * Apikey entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "apikey")
public class Apikey implements java.io.Serializable {

	// Fields

	private Long id;
	private Shop shop;
	private Agent agent;
	private String apikey;
	private String createtime;
	private Integer struts;
	private String desrc;
	private String domain;
	private String ip;
	private Integer type;
	private Integer purview;
	private Set<Viewrecord> viewrecords = new HashSet<Viewrecord>(0);

	// Constructors

	/** default constructor */
	public Apikey() {
	}

	/** full constructor */
	public Apikey(Shop shop, Agent agent, String apikey, String createtime,
			Integer struts, String desrc, String domain, String ip,
			Integer type, Integer purview, Set<Viewrecord> viewrecords) {
		this.shop = shop;
		this.agent = agent;
		this.apikey = apikey;
		this.createtime = createtime;
		this.struts = struts;
		this.desrc = desrc;
		this.domain = domain;
		this.ip = ip;
		this.type = type;
		this.purview = purview;
		this.viewrecords = viewrecords;
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
	@JoinColumn(name = "agentid")
	public Agent getAgent() {
		return this.agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Column(name = "apikey", length = 36)
	public String getApikey() {
		return this.apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "struts")
	public Integer getStruts() {
		return this.struts;
	}

	public void setStruts(Integer struts) {
		this.struts = struts;
	}

	@Column(name = "desrc", length = 200)
	public String getDesrc() {
		return this.desrc;
	}

	public void setDesrc(String desrc) {
		this.desrc = desrc;
	}

	@Column(name = "domain", length = 200)
	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Column(name = "ip", length = 50)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "purview")
	public Integer getPurview() {
		return this.purview;
	}

	public void setPurview(Integer purview) {
		this.purview = purview;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "apikey")
	public Set<Viewrecord> getViewrecords() {
		return this.viewrecords;
	}

	public void setViewrecords(Set<Viewrecord> viewrecords) {
		this.viewrecords = viewrecords;
	}

}