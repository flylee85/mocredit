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
 * Mmsframe entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mmsframe")
public class Mmsframe implements java.io.Serializable {

	// Fields

	private Long id;
	private Eitem eitem;
	private String pic;
	private String pictype;
	private String txt;
	private Integer no;
	private String createtime;

	// Constructors

	/** default constructor */
	public Mmsframe() {
	}

	/** minimal constructor */
	public Mmsframe(Eitem eitem, Integer no) {
		this.eitem = eitem;
		this.no = no;
	}

	/** full constructor */
	public Mmsframe(Eitem eitem, String pic, String pictype, String txt,
			Integer no, String createtime) {
		this.eitem = eitem;
		this.pic = pic;
		this.pictype = pictype;
		this.txt = txt;
		this.no = no;
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
	@JoinColumn(name = "eitemid", nullable = false)
	public Eitem getEitem() {
		return this.eitem;
	}

	public void setEitem(Eitem eitem) {
		this.eitem = eitem;
	}

	@Column(name = "pic", length = 65535)
	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Column(name = "pictype", length = 10)
	public String getPictype() {
		return this.pictype;
	}

	public void setPictype(String pictype) {
		this.pictype = pictype;
	}

	@Column(name = "txt", length = 65535)
	public String getTxt() {
		return this.txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	@Column(name = "no", nullable = false)
	public Integer getNo() {
		return this.no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	@Column(name = "createtime", length = 19)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}