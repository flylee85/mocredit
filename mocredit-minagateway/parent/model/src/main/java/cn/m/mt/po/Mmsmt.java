package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mmsmt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mmsmt")
public class Mmsmt implements java.io.Serializable {

	// Fields

	private Long id;
	private Long shopid;
	private String mobile;
	private String content;
	private Integer state;
	private String errorinfo;
	private String createtime;
	private String sendtime;
	private String coderurl;
	private String picurl;
	private Long couponid;
	private String tid;

	// Constructors

	/** default constructor */
	public Mmsmt() {
	}

	/** full constructor */
	public Mmsmt(Long shopid, String mobile, String content, Integer state,
			String errorinfo, String createtime, String sendtime,
			String coderurl, String picurl, Long couponid, String tid) {
		this.shopid = shopid;
		this.mobile = mobile;
		this.content = content;
		this.state = state;
		this.errorinfo = errorinfo;
		this.createtime = createtime;
		this.sendtime = sendtime;
		this.coderurl = coderurl;
		this.picurl = picurl;
		this.couponid = couponid;
		this.tid = tid;
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

	@Column(name = "coderurl", length = 100)
	public String getCoderurl() {
		return this.coderurl;
	}

	public void setCoderurl(String coderurl) {
		this.coderurl = coderurl;
	}

	@Column(name = "picurl", length = 100)
	public String getPicurl() {
		return this.picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	@Column(name = "couponid")
	public Long getCouponid() {
		return this.couponid;
	}

	public void setCouponid(Long couponid) {
		this.couponid = couponid;
	}

	@Column(name = "tid", length = 40)
	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

}