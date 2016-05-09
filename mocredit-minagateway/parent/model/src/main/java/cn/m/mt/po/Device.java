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
 * Device entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "device")
public class Device implements java.io.Serializable {

	// Fields

	private Long id;
	private String terminalid;
	private Deviceversion deviceversion;
	private Shop shop;
	private Store store;
	private Agent agent;
	private String devcode;
	private String createtime;
	private String updatetime;
	private String onlinetime;
	private Integer status;
	private String password;
	private Integer loginnum;
	private String updatetype;
	private String versionno;
	private String adminpassword;
	private String batchno;
	private String deskey;
	private String mackey;
	// Constructors

	/** default constructor */
	public Device() {
	}

	/** full constructor */
	public Device(String terminalid, Deviceversion deviceversion, Shop shop, Store store,
			Agent agent, String devcode, String createtime, String updatetime,
			String onlinetime, Integer status, String password,
			Integer loginnum, String updatetype, String versionno,
			String adminpassword, String batchno, String deskey,
			String mackey) {
		this.terminalid = terminalid;
		this.deviceversion = deviceversion;
		this.shop = shop;
		this.store = store;
		this.agent = agent;
		this.devcode = devcode;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.onlinetime = onlinetime;
		this.status = status;
		this.password = password;
		this.loginnum = loginnum;
		this.updatetype = updatetype;
		this.versionno = versionno;
		this.adminpassword = adminpassword;
		this.batchno = batchno;
		this.deskey = deskey;
		this.mackey = mackey;
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
	
	@Column(name = "terminalid", length = 50)
	public String getTerminalid() {
		return terminalid;
	}

	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "versionid")
	public Deviceversion getDeviceversion() {
		return this.deviceversion;
	}

	public void setDeviceversion(Deviceversion deviceversion) {
		this.deviceversion = deviceversion;
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
	@JoinColumn(name = "storeid")
	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agentid")
	public Agent getAgent() {
		return this.agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Column(name = "devcode", length = 50)
	public String getDevcode() {
		return this.devcode;
	}

	public void setDevcode(String devcode) {
		this.devcode = devcode;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "updatetime", length = 20)
	public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "onlinetime", length = 20)
	public String getOnlinetime() {
		return this.onlinetime;
	}

	public void setOnlinetime(String onlinetime) {
		this.onlinetime = onlinetime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "password", length = 40)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "loginnum")
	public Integer getLoginnum() {
		return this.loginnum;
	}

	public void setLoginnum(Integer loginnum) {
		this.loginnum = loginnum;
	}

	@Column(name = "updatetype", length = 10)
	public String getUpdatetype() {
		return this.updatetype;
	}

	public void setUpdatetype(String updatetype) {
		this.updatetype = updatetype;
	}

	@Column(name = "versionno", length = 5)
	public String getVersionno() {
		return this.versionno;
	}

	public void setVersionno(String versionno) {
		this.versionno = versionno;
	}

	@Column(name = "adminpassword", length = 50)
	public String getAdminpassword() {
		return this.adminpassword;
	}

	public void setAdminpassword(String adminpassword) {
		this.adminpassword = adminpassword;
	}

	@Column(name = "batchno", length = 6)
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	@Column(name = "deskey", length = 20)
	public String getDeskey() {
		return deskey;
	}

	public void setDeskey(String deskey) {
		this.deskey = deskey;
	}

	@Column(name = "mackey", length = 20)
	public String getMackey() {
		return mackey;
	}

	public void setMackey(String mackey) {
		this.mackey = mackey;
	}
}