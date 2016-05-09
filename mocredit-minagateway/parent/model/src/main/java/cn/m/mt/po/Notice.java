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
 * Notice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "notice")
public class Notice implements java.io.Serializable {

	// Fields

	private Long id;
	private Agent agent;
	private String name;
	private String content;
	private String creattime;
	private Integer status;
	private Integer type;

	// Constructors

	/** default constructor */
	public Notice() {
	}

	/** minimal constructor */
	public Notice(Integer status) {
		this.status = status;
	}

	/** full constructor */
	public Notice(Agent agent, String name, String content, String creattime,
			Integer status, Integer type) {
		this.agent = agent;
		this.name = name;
		this.content = content;
		this.creattime = creattime;
		this.status = status;
		this.type = type;
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
	@JoinColumn(name = "agentid")
	public Agent getAgent() {
		return this.agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "content", length = 200)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "creattime", length = 20)
	public String getCreattime() {
		return this.creattime;
	}

	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}