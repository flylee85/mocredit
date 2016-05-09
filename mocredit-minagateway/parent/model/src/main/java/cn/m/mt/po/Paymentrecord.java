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
 * Paymentrecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "paymentrecord")
public class Paymentrecord implements java.io.Serializable {

	// Fields

	private Long id;
	private Shoporder shoporder;
	private String paymenttype;
	private String tradeno;
	private String tradestatus;
	private String gmtcreate;
	private String gmtpaymen;
	private String gmtClose;
	private String refundstatus;
	private String gmtrefund;
	private String selleremail;
	private String buyeremail;
	private String sellerid;
	private String buyerid;
	private String bankseqno;
	private String outchanneltype;
	private String outchannelamount;
	private String createtime;

	// Constructors

	/** default constructor */
	public Paymentrecord() {
	}

	/** full constructor */
	public Paymentrecord(Shoporder shoporder, String paymenttype,
			String tradeno, String tradestatus, String gmtcreate,
			String gmtpaymen, String gmtClose, String refundstatus,
			String gmtrefund, String selleremail, String buyeremail,
			String sellerid, String buyerid, String bankseqno,
			String outchanneltype, String outchannelamount, String createtime) {
		this.shoporder = shoporder;
		this.paymenttype = paymenttype;
		this.tradeno = tradeno;
		this.tradestatus = tradestatus;
		this.gmtcreate = gmtcreate;
		this.gmtpaymen = gmtpaymen;
		this.gmtClose = gmtClose;
		this.refundstatus = refundstatus;
		this.gmtrefund = gmtrefund;
		this.selleremail = selleremail;
		this.buyeremail = buyeremail;
		this.sellerid = sellerid;
		this.buyerid = buyerid;
		this.bankseqno = bankseqno;
		this.outchanneltype = outchanneltype;
		this.outchannelamount = outchannelamount;
		this.createtime = createtime;
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
	@JoinColumn(name = "shoporderid")
	public Shoporder getShoporder() {
		return this.shoporder;
	}

	public void setShoporder(Shoporder shoporder) {
		this.shoporder = shoporder;
	}

	@Column(name = "paymenttype", length = 4)
	public String getPaymenttype() {
		return this.paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	@Column(name = "tradeno", length = 64)
	public String getTradeno() {
		return this.tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}

	@Column(name = "tradestatus")
	public String getTradestatus() {
		return this.tradestatus;
	}

	public void setTradestatus(String tradestatus) {
		this.tradestatus = tradestatus;
	}

	@Column(name = "gmtcreate", length = 20)
	public String getGmtcreate() {
		return this.gmtcreate;
	}

	public void setGmtcreate(String gmtcreate) {
		this.gmtcreate = gmtcreate;
	}

	@Column(name = "gmtpaymen", length = 20)
	public String getGmtpaymen() {
		return this.gmtpaymen;
	}

	public void setGmtpaymen(String gmtpaymen) {
		this.gmtpaymen = gmtpaymen;
	}

	@Column(name = "gmt_close", length = 20)
	public String getGmtClose() {
		return this.gmtClose;
	}

	public void setGmtClose(String gmtClose) {
		this.gmtClose = gmtClose;
	}

	@Column(name = "refundstatus")
	public String getRefundstatus() {
		return this.refundstatus;
	}

	public void setRefundstatus(String refundstatus) {
		this.refundstatus = refundstatus;
	}

	@Column(name = "gmtrefund", length = 20)
	public String getGmtrefund() {
		return this.gmtrefund;
	}

	public void setGmtrefund(String gmtrefund) {
		this.gmtrefund = gmtrefund;
	}

	@Column(name = "selleremail", length = 100)
	public String getSelleremail() {
		return this.selleremail;
	}

	public void setSelleremail(String selleremail) {
		this.selleremail = selleremail;
	}

	@Column(name = "buyeremail", length = 100)
	public String getBuyeremail() {
		return this.buyeremail;
	}

	public void setBuyeremail(String buyeremail) {
		this.buyeremail = buyeremail;
	}

	@Column(name = "sellerid", length = 30)
	public String getSellerid() {
		return this.sellerid;
	}

	public void setSellerid(String sellerid) {
		this.sellerid = sellerid;
	}

	@Column(name = "buyerid", length = 30)
	public String getBuyerid() {
		return this.buyerid;
	}

	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
	}

	@Column(name = "bankseqno", length = 64)
	public String getBankseqno() {
		return this.bankseqno;
	}

	public void setBankseqno(String bankseqno) {
		this.bankseqno = bankseqno;
	}

	@Column(name = "outchanneltype")
	public String getOutchanneltype() {
		return this.outchanneltype;
	}

	public void setOutchanneltype(String outchanneltype) {
		this.outchanneltype = outchanneltype;
	}

	@Column(name = "outchannelamount")
	public String getOutchannelamount() {
		return this.outchannelamount;
	}

	public void setOutchannelamount(String outchannelamount) {
		this.outchannelamount = outchannelamount;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}