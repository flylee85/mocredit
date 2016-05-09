package cn.m.mt.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "specialphone")
public class Specialphone implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5744486195838611240L;
	private Long id;
	private Enterprise enterprise;
	private String phone;
	private Date createtime;
	private Date sendBarcodeTime;
	private Date checktime;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name = "entid", nullable = false)
	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getChecktime() {
		return checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	public Date getSendBarcodeTime() {
		return sendBarcodeTime;
	}

	public void setSendBarcodeTime(Date sendBarcodeTime) {
		this.sendBarcodeTime = sendBarcodeTime;
	}
	
	
	
}
