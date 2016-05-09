package cn.m.mt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Reconciliation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reconciliation", catalog = "mcntong")
public class Reconciliation implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer type;
	private String batchno;
	private String imei;
	private String posno;
	private String charcode;
	private Double amcout;
	private String searchno;
	private String createtime;

	// Constructors

	/** default constructor */
	public Reconciliation() {
	}

	/** minimal constructor */
	public Reconciliation(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Reconciliation(Integer id, Integer type, String batchno,
			String imei, String posno, String charcode, Double amcout,
			String searchno, String createtime) {
		this.id = id;
		this.type = type;
		this.batchno = batchno;
		this.imei = imei;
		this.posno = posno;
		this.charcode = charcode;
		this.amcout = amcout;
		this.searchno = searchno;
		this.createtime = createtime;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "batchno", length = 6)
	public String getBatchno() {
		return this.batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	@Column(name = "imei", length = 20)
	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "posno", length = 12)
	public String getPosno() {
		return this.posno;
	}

	public void setPosno(String posno) {
		this.posno = posno;
	}

	@Column(name = "charcode", length = 40)
	public String getCharcode() {
		return this.charcode;
	}

	public void setCharcode(String charcode) {
		this.charcode = charcode;
	}

	@Column(name = "amcout", precision = 22, scale = 0)
	public Double getAmcout() {
		return this.amcout;
	}

	public void setAmcout(Double amcout) {
		this.amcout = amcout;
	}

	@Column(name = "searchno", length = 6)
	public String getSearchno() {
		return this.searchno;
	}

	public void setSearchno(String searchno) {
		this.searchno = searchno;
	}

	@Column(name = "createtime", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}