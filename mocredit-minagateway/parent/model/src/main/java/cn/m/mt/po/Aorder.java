package cn.m.mt.po;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Aorder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "aorder")
public class Aorder implements java.io.Serializable {

	// Fields

	private Long id;
	private Shop shop;
	private Store store;
	private User user;
	private Barcode barcode;
	private String alipayid;
	private String serialnum;
	private String requestid;
	private Double payment;
	private String code;
	private String status;
	private String errormessage;
	private String createtime;
	private String refundtime;
	private Integer paytype;
	private String printdate;
	private String imei;
	private String posno;
	private String batchno;
	private String searchno;

	// Constructors

	/** default constructor */
	public Aorder() {
	}

	/** full constructor */
	public Aorder(Shop shop, Store store, User user, String alipayid,
			String serialnum, String requestid, Double payment, String code,
			String status, String errormessage, String createtime,
			String refundtime, Integer paytype, Barcode barcode,
			String printdate, String imei, String posno, String batchno,
			String searchno) {
		this.shop = shop;
		this.store = store;
		this.user = user;
		this.alipayid = alipayid;
		this.serialnum = serialnum;
		this.requestid = requestid;
		this.payment = payment;
		this.code = code;
		this.status = status;
		this.errormessage = errormessage;
		this.createtime = createtime;
		this.refundtime = refundtime;
		this.paytype = paytype;
		this.barcode = barcode;
		this.printdate = printdate;
		this.imei = imei;
		this.posno = posno;
		this.batchno = batchno;
		this.searchno = searchno;
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
	@JoinColumn(name = "storeid")
	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "barcodeid")
	public Barcode getBarcode() {
		return this.barcode;
	}

	public void setBarcode(Barcode barcode) {
		this.barcode = barcode;
	}

	@Column(name = "alipayid", length = 40)
	public String getAlipayid() {
		return this.alipayid;
	}

	public void setAlipayid(String alipayid) {
		this.alipayid = alipayid;
	}

	@Column(name = "serialnum", length = 40)
	public String getSerialnum() {
		return this.serialnum;
	}

	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}

	@Column(name = "requestid", length = 40)
	public String getRequestid() {
		return this.requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}

	@Column(name = "payment", precision = 22, scale = 0)
	public Double getPayment() {
		return this.payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Column(name = "code", length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "status", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "errormessage", length = 40)
	public String getErrormessage() {
		return this.errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "refundtime", length = 20)
	public String getRefundtime() {
		return this.refundtime;
	}

	public void setRefundtime(String refundtime) {
		this.refundtime = refundtime;
	}

	@Column(name = "paytype")
	public Integer getPaytype() {
		return this.paytype;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	@Column(name = "printdate", length = 20)
	public String getPrintdate() {
		return this.printdate;
	}

	public void setPrintdate(String printdate) {
		this.printdate = printdate;
	}

	@Column(name = "imei", length = 50)
	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "posno", length = 12)
	public String getPosno() {
		return posno;
	}

	public void setPosno(String posno) {
		this.posno = posno;
	}

	@Column(name = "batchno", length = 6)
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	@Column(name = "searchno", length = 6)
	public String getSearchno() {
		return searchno;
	}

	public void setSearchno(String searchno) {
		this.searchno = searchno;
	}
	
}