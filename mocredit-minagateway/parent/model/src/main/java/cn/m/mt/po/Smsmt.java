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
 * Smsmt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "smsmt")
public class Smsmt implements java.io.Serializable {

	// Fields

	private Long id;
	private Eitem eitem;
	private Batch batch;
	private Enterprise enterprise;
	private Long shopid;
	private String mobile;
	private String content;
	private Integer state;
	private String errorinfo;
	private String createtime;
	private String sendtime;
	private Long couponid;
	private Integer mttype;
	private Long pushid;
	private String tid;
	private Integer isresend;
	private Long sendno;
	private String updatetime;
	private Integer type;
	private String sendername;
	private Integer num;

	// Constructors

	/** default constructor */
	public Smsmt() {
	}

	/** minimal constructor */
	public Smsmt(Integer mttype) {
		this.mttype = mttype;
	}

	/** full constructor */
	public Smsmt(Eitem eitem, Batch batch, Enterprise enterprise, Long shopid,
			String mobile, String content, Integer state, String errorinfo,
			String createtime, String sendtime, Long couponid, Integer mttype,
			Long pushid, String tid, Integer isresend, Long sendno,
			String updatetime, Integer type, String sendername, Integer num) {
		this.eitem = eitem;
		this.batch = batch;
		this.enterprise = enterprise;
		this.shopid = shopid;
		this.mobile = mobile;
		this.content = content;
		this.state = state;
		this.errorinfo = errorinfo;
		this.createtime = createtime;
		this.sendtime = sendtime;
		this.couponid = couponid;
		this.mttype = mttype;
		this.pushid = pushid;
		this.tid = tid;
		this.isresend = isresend;
		this.sendno = sendno;
		this.updatetime = updatetime;
		this.type = type;
		this.sendername = sendername;
		this.num = num;
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
	@JoinColumn(name = "eitemid")
	public Eitem getEitem() {
		return this.eitem;
	}

	public void setEitem(Eitem eitem) {
		this.eitem = eitem;
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

	@Column(name = "shopid")
	public Long getShopid() {
		return this.shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	@Column(name = "mobile", length = 65535)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "errorinfo", length = 100)
	public String getErrorinfo() {
		return this.errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "sendtime", length = 20)
	public String getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	@Column(name = "couponid")
	public Long getCouponid() {
		return this.couponid;
	}

	public void setCouponid(Long couponid) {
		this.couponid = couponid;
	}

	@Column(name = "mttype", nullable = false)
	public Integer getMttype() {
		return this.mttype;
	}

	public void setMttype(Integer mttype) {
		this.mttype = mttype;
	}

	@Column(name = "pushid")
	public Long getPushid() {
		return this.pushid;
	}

	public void setPushid(Long pushid) {
		this.pushid = pushid;
	}

	@Column(name = "tid", length = 40)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "isresend")
	public Integer getIsresend() {
		return this.isresend;
	}

	public void setIsresend(Integer isresend) {
		this.isresend = isresend;
	}

	@Column(name = "sendno")
	public Long getSendno() {
		return this.sendno;
	}

	public void setSendno(Long sendno) {
		this.sendno = sendno;
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

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}