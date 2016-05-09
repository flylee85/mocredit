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
 * Eorder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "eorder")
public class Eorder implements java.io.Serializable {

	// Fields

	private Long id;
	private Eitem eitem;
	private Shoporder shoporder;
	private User user;
	private String tid;
	private Long nummiid;
	private Integer num;
	private Double payment;
	private String phone;
	private String type;
	private String smscontent;
	private String validate;
	private String status;
	private String createtime;
	private Double refundamount;
	private Integer refundnum;
	private String buynick;
	private String refundtime;
	private Integer overnum;
	private Integer isSendToFriend;
	private String friendnick;
	private String friendmessage;
	private String extfield1;
	private String extfield2;
	private String extfield3;
	private Integer resendstatus;
	private String isdelnum;
	private Set<Barcode> barcodes = new HashSet<Barcode>(0);
	private Set<Wemmsmt> wemmsmts = new HashSet<Wemmsmt>(0);

	// Constructors

	/** default constructor */
	public Eorder() {
	}

	/** minimal constructor */
	public Eorder(String tid) {
		this.tid = tid;
	}

	/** full constructor */
	public Eorder(Eitem eitem, Shoporder shoporder, User user, String tid,
			Long nummiid, Integer num, Double payment, String phone,
			String type, String smscontent, String validate, String status,
			String createtime, Double refundamount, Integer refundnum,
			String buynick, String refundtime, Integer overnum,
			Integer isSendToFriend, String friendnick, String friendmessage,
			String extfield1, String extfield2, String extfield3,
			Integer resendstatus, Set<Barcode> barcodes, Set<Wemmsmt> wemmsmts) {
		this.eitem = eitem;
		this.shoporder = shoporder;
		this.user = user;
		this.tid = tid;
		this.nummiid = nummiid;
		this.num = num;
		this.payment = payment;
		this.phone = phone;
		this.type = type;
		this.smscontent = smscontent;
		this.validate = validate;
		this.status = status;
		this.createtime = createtime;
		this.refundamount = refundamount;
		this.refundnum = refundnum;
		this.buynick = buynick;
		this.refundtime = refundtime;
		this.overnum = overnum;
		this.isSendToFriend = isSendToFriend;
		this.friendnick = friendnick;
		this.friendmessage = friendmessage;
		this.extfield1 = extfield1;
		this.extfield2 = extfield2;
		this.extfield3 = extfield3;
		this.resendstatus = resendstatus;
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
	@JoinColumn(name = "eitemid")
	public Eitem getEitem() {
		return this.eitem;
	}

	public void setEitem(Eitem eitem) {
		this.eitem = eitem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shoporderid")
	public Shoporder getShoporder() {
		return this.shoporder;
	}

	public void setShoporder(Shoporder shoporder) {
		this.shoporder = shoporder;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "tid", nullable = false, length = 40)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "nummiid")
	public Long getNummiid() {
		return this.nummiid;
	}

	public void setNummiid(Long nummiid) {
		this.nummiid = nummiid;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "payment", precision = 22, scale = 0)
	public Double getPayment() {
		return this.payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "type", length = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "smscontent", length = 200)
	public String getSmscontent() {
		return this.smscontent;
	}

	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}

	@Column(name = "validate", length = 20)
	public String getValidate() {
		return this.validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	@Column(name = "status", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "refundamount", precision = 22, scale = 0)
	public Double getRefundamount() {
		return this.refundamount;
	}

	public void setRefundamount(Double refundamount) {
		this.refundamount = refundamount;
	}

	@Column(name = "refundnum")
	public Integer getRefundnum() {
		return this.refundnum;
	}

	public void setRefundnum(Integer refundnum) {
		this.refundnum = refundnum;
	}

	@Column(name = "buynick", length = 50)
	public String getBuynick() {
		return this.buynick;
	}

	public void setBuynick(String buynick) {
		this.buynick = buynick;
	}

	@Column(name = "refundtime", length = 20)
	public String getRefundtime() {
		return this.refundtime;
	}

	public void setRefundtime(String refundtime) {
		this.refundtime = refundtime;
	}

	@Column(name = "overnum")
	public Integer getOvernum() {
		return this.overnum;
	}

	public void setOvernum(Integer overnum) {
		this.overnum = overnum;
	}

	@Column(name = "isSendToFriend")
	public Integer getIsSendToFriend() {
		return this.isSendToFriend;
	}

	public void setIsSendToFriend(Integer isSendToFriend) {
		this.isSendToFriend = isSendToFriend;
	}

	@Column(name = "friendnick", length = 20)
	public String getFriendnick() {
		return this.friendnick;
	}

	public void setFriendnick(String friendnick) {
		this.friendnick = friendnick;
	}

	@Column(name = "friendmessage", length = 500)
	public String getFriendmessage() {
		return this.friendmessage;
	}

	public void setFriendmessage(String friendmessage) {
		this.friendmessage = friendmessage;
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

	@Column(name = "resendstatus")
	public Integer getResendstatus() {
		return this.resendstatus;
	}

	public void setResendstatus(Integer resendstatus) {
		this.resendstatus = resendstatus;
	}
	
	@Column(name = "isdelnum")
	public String getIsdelnum() {
		return isdelnum;
	}

	public void setIsdelnum(String isdelnum) {
		this.isdelnum = isdelnum;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eorder")
	public Set<Barcode> getBarcodes() {
		return this.barcodes;
	}

	public void setBarcodes(Set<Barcode> barcodes) {
		this.barcodes = barcodes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eorder")
	public Set<Wemmsmt> getWemmsmts() {
		return this.wemmsmts;
	}

	public void setWemmsmts(Set<Wemmsmt> wemmsmts) {
		this.wemmsmts = wemmsmts;
	}

}