package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Payment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "payment")
public class Payment implements java.io.Serializable {

	// Fields

	private Long id;
	private String batchno;
	private String serialno;
	private String orderid;
	private String panstr;
	private String mobile;
	private String producttype;
	private String productnum;
	private String transamt;
	private String timestamp;
	private String authorizecode;
	private String authdate;
	private String authtime;
	private String retcode;
	private String commentres;
	private Short status;
	private String imei;
	private String payway;
	private String secondTrack;
	private String merchantid;
	private String merchantpassword;
	private String posno;
	private String searchno;
	// Constructors

	/** default constructor */
	public Payment() {
	}

	/** minimal constructor */
	public Payment(String batchno, String serialno, String orderid,
			String panstr, String producttype, String productnum,
			String transamt, String timestamp, Short status) {
		this.batchno = batchno;
		this.serialno = serialno;
		this.orderid = orderid;
		this.panstr = panstr;
		this.producttype = producttype;
		this.productnum = productnum;
		this.transamt = transamt;
		this.timestamp = timestamp;
		this.status = status;
	}

	/** full constructor */
	public Payment(String batchno, String serialno, String orderid,
			String panstr, String mobile, String producttype,
			String productnum, String transamt, String timestamp,
			String authorizecode, String authdate, String authtime,
			String retcode, String commentres, Short status, String imei,
			String payway, String secondTrack, String merchantid,
			String merchantpassword) {
		this.batchno = batchno;
		this.serialno = serialno;
		this.orderid = orderid;
		this.panstr = panstr;
		this.mobile = mobile;
		this.producttype = producttype;
		this.productnum = productnum;
		this.transamt = transamt;
		this.timestamp = timestamp;
		this.authorizecode = authorizecode;
		this.authdate = authdate;
		this.authtime = authtime;
		this.retcode = retcode;
		this.commentres = commentres;
		this.status = status;
		this.imei = imei;
		this.payway = payway;
		this.secondTrack = secondTrack;
		this.merchantid = merchantid;
		this.merchantpassword = merchantpassword;
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

	@Column(name = "batchno", nullable = false, length = 6)
	public String getBatchno() {
		return this.batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	@Column(name = "serialno", nullable = false, length = 6)
	public String getSerialno() {
		return this.serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	@Column(name = "orderid", nullable = false, length = 19)
	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	@Column(name = "panstr", nullable = false, length = 19)
	public String getPanstr() {
		return this.panstr;
	}

	public void setPanstr(String panstr) {
		this.panstr = panstr;
	}

	@Column(name = "mobile", length = 11)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "producttype", nullable = false, length = 10)
	public String getProducttype() {
		return this.producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	@Column(name = "productnum", nullable = false, length = 2)
	public String getProductnum() {
		return this.productnum;
	}

	public void setProductnum(String productnum) {
		this.productnum = productnum;
	}

	@Column(name = "transamt", nullable = false, length = 13)
	public String getTransamt() {
		return this.transamt;
	}

	public void setTransamt(String transamt) {
		this.transamt = transamt;
	}

	@Column(name = "timestamp", nullable = false, length = 17)
	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Column(name = "authorizecode", length = 6)
	public String getAuthorizecode() {
		return this.authorizecode;
	}

	public void setAuthorizecode(String authorizecode) {
		this.authorizecode = authorizecode;
	}

	@Column(name = "authdate", length = 8)
	public String getAuthdate() {
		return this.authdate;
	}

	public void setAuthdate(String authdate) {
		this.authdate = authdate;
	}

	@Column(name = "authtime", length = 8)
	public String getAuthtime() {
		return this.authtime;
	}

	public void setAuthtime(String authtime) {
		this.authtime = authtime;
	}

	@Column(name = "retcode", length = 7)
	public String getRetcode() {
		return this.retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	@Column(name = "commentres", length = 100)
	public String getCommentres() {
		return this.commentres;
	}

	public void setCommentres(String commentres) {
		this.commentres = commentres;
	}

	@Column(name = "status", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "imei", length = 20)
	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "payway", length = 2)
	public String getPayway() {
		return this.payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	@Column(name = "secondTrack", length = 100)
	public String getSecondTrack() {
		return this.secondTrack;
	}

	public void setSecondTrack(String secondTrack) {
		this.secondTrack = secondTrack;
	}

	@Column(name = "merchantid", length = 20)
	public String getMerchantid() {
		return this.merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	@Column(name = "merchantpassword", length = 20)
	public String getMerchantpassword() {
		return this.merchantpassword;
	}

	public void setMerchantpassword(String merchantpassword) {
		this.merchantpassword = merchantpassword;
	}
	@Column(name = "posno", length = 12)
	public String getPosno() {
		return posno;
	}

	public void setPosno(String posno) {
		this.posno = posno;
	}

	@Column(name = "searchno", length = 6)
	public String getSearchno() {
		return searchno;
	}

	public void setSearchno(String searchno) {
		this.searchno = searchno;
	}

}