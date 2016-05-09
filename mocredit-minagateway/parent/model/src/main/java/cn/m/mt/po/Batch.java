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
 * Batch entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "batch")
public class Batch implements java.io.Serializable {

	// Fields

	private Long id;
	private Eitem eitem;
	private Integer uploadnum;
	private Integer sendsmsnum;
	private Integer successsmsnum;
	private String createtime;
	private Integer sendmmsnum;
	private Integer successmmsnum;
	private Integer status;
	private String type;
	private String descr;
	private String sendtime;
	private Integer sendtype;
	private String username;
	private Set<Contacts> contactses = new HashSet<Contacts>(0);
	private Set<Smsmt> smsmts = new HashSet<Smsmt>(0);
	private Set<Barcode> barcodes = new HashSet<Barcode>(0);
	private Set<Wemmsmt> wemmsmts = new HashSet<Wemmsmt>(0);
	private String refusereason;
	private String refusetime;

	// Constructors

	/** default constructor */
	public Batch() {
	}

	/** minimal constructor */
	public Batch(Eitem eitem, Integer uploadnum, Integer status) {
		this.eitem = eitem;
		this.uploadnum = uploadnum;
		this.status = status;
	}

	/** full constructor */
	public Batch(Eitem eitem, Integer uploadnum, Integer sendsmsnum,
			Integer successsmsnum, String createtime, Integer sendmmsnum,
			Integer successmmsnum, Integer status, String type, String descr,
			String sendtime, Integer sendtype, String username,
			Set<Contacts> contactses, Set<Smsmt> smsmts, Set<Barcode> barcodes,
			Set<Wemmsmt> wemmsmts) {
		this.eitem = eitem;
		this.uploadnum = uploadnum;
		this.sendsmsnum = sendsmsnum;
		this.successsmsnum = successsmsnum;
		this.createtime = createtime;
		this.sendmmsnum = sendmmsnum;
		this.successmmsnum = successmmsnum;
		this.status = status;
		this.type = type;
		this.descr = descr;
		this.sendtime = sendtime;
		this.sendtype = sendtype;
		this.username = username;
		this.contactses = contactses;
		this.smsmts = smsmts;
		this.barcodes = barcodes;
		this.wemmsmts = wemmsmts;
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
	@JoinColumn(name = "eitemid", nullable = false)
	public Eitem getEitem() {
		return this.eitem;
	}

	public void setEitem(Eitem eitem) {
		this.eitem = eitem;
	}

	@Column(name = "uploadnum", nullable = false)
	public Integer getUploadnum() {
		return this.uploadnum;
	}

	public void setUploadnum(Integer uploadnum) {
		this.uploadnum = uploadnum;
	}

	@Column(name = "sendsmsnum")
	public Integer getSendsmsnum() {
		return this.sendsmsnum;
	}

	public void setSendsmsnum(Integer sendsmsnum) {
		this.sendsmsnum = sendsmsnum;
	}

	@Column(name = "successsmsnum")
	public Integer getSuccesssmsnum() {
		return this.successsmsnum;
	}

	public void setSuccesssmsnum(Integer successsmsnum) {
		this.successsmsnum = successsmsnum;
	}

	@Column(name = "createtime", length = 19)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "sendmmsnum")
	public Integer getSendmmsnum() {
		return this.sendmmsnum;
	}

	public void setSendmmsnum(Integer sendmmsnum) {
		this.sendmmsnum = sendmmsnum;
	}

	@Column(name = "successmmsnum")
	public Integer getSuccessmmsnum() {
		return this.successmmsnum;
	}

	public void setSuccessmmsnum(Integer successmmsnum) {
		this.successmmsnum = successmmsnum;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "type", length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "descr", length = 200)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "sendtime", length = 20)
	public String getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	@Column(name = "sendtype")
	public Integer getSendtype() {
		return this.sendtype;
	}

	public void setSendtype(Integer sendtype) {
		this.sendtype = sendtype;
	}

	@Column(name = "username", length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "batch")
	public Set<Contacts> getContactses() {
		return this.contactses;
	}

	public void setContactses(Set<Contacts> contactses) {
		this.contactses = contactses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "batch")
	public Set<Smsmt> getSmsmts() {
		return this.smsmts;
	}

	public void setSmsmts(Set<Smsmt> smsmts) {
		this.smsmts = smsmts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "batch")
	public Set<Barcode> getBarcodes() {
		return this.barcodes;
	}

	public void setBarcodes(Set<Barcode> barcodes) {
		this.barcodes = barcodes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "batch")
	public Set<Wemmsmt> getWemmsmts() {
		return this.wemmsmts;
	}

	public void setWemmsmts(Set<Wemmsmt> wemmsmts) {
		this.wemmsmts = wemmsmts;
	}

	@Column(name = "refusereason", length = 200)
	public String getRefusereason() {
		return refusereason;
	}

	public void setRefusereason(String refusereason) {
		this.refusereason = refusereason;
	}

	@Column(name = "refusetime", length = 20)
	public String getRefusetime() {
		return refusetime;
	}

	public void setRefusetime(String refusetime) {
		this.refusetime = refusetime;
	}

}