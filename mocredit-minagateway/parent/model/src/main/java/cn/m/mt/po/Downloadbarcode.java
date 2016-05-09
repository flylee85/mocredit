package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Downloadbarcode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "downloadbarcode")
public class Downloadbarcode implements java.io.Serializable {

	// Fields

	private Long id;
	private String date;
	private Integer no;
	private String txturl;
	private String picurl;
	private String createtime;

	// Constructors

	/** default constructor */
	public Downloadbarcode() {
	}

	/** full constructor */
	public Downloadbarcode(String date, Integer no, String txturl,
			String picurl, String createtime) {
		this.date = date;
		this.no = no;
		this.txturl = txturl;
		this.picurl = picurl;
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

	@Column(name = "date", nullable = false, length = 8)
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(name = "no", nullable = false)
	public Integer getNo() {
		return this.no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	@Column(name = "txturl", nullable = false)
	public String getTxturl() {
		return this.txturl;
	}

	public void setTxturl(String txturl) {
		this.txturl = txturl;
	}

	@Column(name = "picurl", nullable = false)
	public String getPicurl() {
		return this.picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	@Column(name = "createtime", nullable = false, length = 19)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}