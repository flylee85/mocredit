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
 * Wemmsmt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "wemmsmt")
public class Wemmsmt implements java.io.Serializable {

	// Fields

	private Long id;
	private Eorder eorder;
	private Batch batch;
	private Enterprise enterprise;
	private String mobile;
	private Integer packageid;
	private String customer;
	private String numberpwd;
	private String extfield1;
	private String extfield2;
	private String extfield3;
	private Integer status;
	private Integer isresend;
	private String sendno;
	private String errorinfo;
	private String statuscode;
	private String tid;
	private String createtime;
	private String sendtime;
	private String charcode;
	private Integer barcodeno;
	private String updatetime;
	private Integer type;
	private String sendername;

	// Constructors

	/** default constructor */
	public Wemmsmt() {
	}

	/** minimal constructor */
	public Wemmsmt(String mobile, Integer packageid, Integer status,
			String createtime, String charcode) {
		this.mobile = mobile;
		this.packageid = packageid;
		this.status = status;
		this.createtime = createtime;
		this.charcode = charcode;
	}

	/** full constructor */
	public Wemmsmt(Eorder eorder, Batch batch, Enterprise enterprise,
			String mobile, Integer packageid, String customer,
			String numberpwd, String extfield1, String extfield2,
			String extfield3, Integer status, Integer isresend, String sendno,
			String errorinfo, String statuscode, String tid, String createtime,
			String sendtime, String charcode, Integer barcodeno,
			String updatetime, Integer type, String sendername) {
		this.eorder = eorder;
		this.batch = batch;
		this.enterprise = enterprise;
		this.mobile = mobile;
		this.packageid = packageid;
		this.customer = customer;
		this.numberpwd = numberpwd;
		this.extfield1 = extfield1;
		this.extfield2 = extfield2;
		this.extfield3 = extfield3;
		this.status = status;
		this.isresend = isresend;
		this.sendno = sendno;
		this.errorinfo = errorinfo;
		this.statuscode = statuscode;
		this.tid = tid;
		this.createtime = createtime;
		this.sendtime = sendtime;
		this.charcode = charcode;
		this.barcodeno = barcodeno;
		this.updatetime = updatetime;
		this.type = type;
		this.sendername = sendername;
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
	@JoinColumn(name = "eorderid")
	public Eorder getEorder() {
		return this.eorder;
	}

	public void setEorder(Eorder eorder) {
		this.eorder = eorder;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batchid")
	public Batch getBatch() {
		return this.batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entid")
	public Enterprise getEnterprise() {
		return this.enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	@Column(name = "mobile", nullable = false, length = 11)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "packageid", nullable = false)
	public Integer getPackageid() {
		return this.packageid;
	}

	public void setPackageid(Integer packageid) {
		this.packageid = packageid;
	}

	@Column(name = "customer", length = 20)
	public String getCustomer() {
		return this.customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Column(name = "numberpwd", length = 20)
	public String getNumberpwd() {
		return this.numberpwd;
	}

	public void setNumberpwd(String numberpwd) {
		this.numberpwd = numberpwd;
	}

	@Column(name = "extfield1", length = 100)
	public String getExtfield1() {
		return this.extfield1;
	}

	public void setExtfield1(String extfield1) {
		this.extfield1 = extfield1;
	}

	@Column(name = "extfield2", length = 100)
	public String getExtfield2() {
		return this.extfield2;
	}

	public void setExtfield2(String extfield2) {
		this.extfield2 = extfield2;
	}

	@Column(name = "extfield3", length = 100)
	public String getExtfield3() {
		return this.extfield3;
	}

	public void setExtfield3(String extfield3) {
		this.extfield3 = extfield3;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "isresend")
	public Integer getIsresend() {
		return this.isresend;
	}

	public void setIsresend(Integer isresend) {
		this.isresend = isresend;
	}

	@Column(name = "sendno", length = 24)
	public String getSendno() {
		return this.sendno;
	}

	public void setSendno(String sendno) {
		this.sendno = sendno;
	}

	@Column(name = "errorinfo", length = 100)
	public String getErrorinfo() {
		return this.errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}

	@Column(name = "statuscode", length = 10)
	public String getStatuscode() {
		return this.statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	@Column(name = "tid", length = 40)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "createtime", nullable = false, length = 19)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "sendtime", length = 19)
	public String getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	@Column(name = "charcode", nullable = false, length = 20)
	public String getCharcode() {
		return this.charcode;
	}

	public void setCharcode(String charcode) {
		this.charcode = charcode;
	}

	@Column(name = "barcodeno")
	public Integer getBarcodeno() {
		return this.barcodeno;
	}

	public void setBarcodeno(Integer barcodeno) {
		this.barcodeno = barcodeno;
	}

	@Column(name = "updatetime", length = 19)
	public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "sendername", length = 20)
	public String getSendername() {
		return this.sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

}