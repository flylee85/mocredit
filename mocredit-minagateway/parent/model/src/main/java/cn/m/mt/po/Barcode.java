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
 * Barcode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "barcode")
public class Barcode implements java.io.Serializable {

	// Fields

	private Long id;
	private Eitem eitem;
	private Eorder eorder;
	private Batch batch;
	private String numcode;
	private String charcode;
	private String source;
	private Long canusenum;
	private Long couponid;
	private String codetype;
	private String usetime;
	private String endate;
	private String creattime;
	private String bank;
	private Double amount;
	private Integer msgstatus;
	private Set<Ofcardrecord> ofcardrecords = new HashSet<Ofcardrecord>(0);

	// Constructors

	/** default constructor */
	public Barcode() {
	}

	/** minimal constructor */
	public Barcode(String numcode, String charcode, String source,
			Long canusenum, String codetype, String creattime) {
		this.numcode = numcode;
		this.charcode = charcode;
		this.source = source;
		this.canusenum = canusenum;
		this.codetype = codetype;
		this.creattime = creattime;
	}

	/** full constructor */
	public Barcode(Eitem eitem, Eorder eorder, Batch batch, String numcode,
			String charcode, String source, Long canusenum, Long couponid,
			String codetype, String usetime, String endate, String creattime,
			String bank, Double amount, Integer msgstatus,
			Set<Ofcardrecord> ofcardrecords) {
		this.eitem = eitem;
		this.eorder = eorder;
		this.batch = batch;
		this.numcode = numcode;
		this.charcode = charcode;
		this.source = source;
		this.canusenum = canusenum;
		this.couponid = couponid;
		this.codetype = codetype;
		this.usetime = usetime;
		this.endate = endate;
		this.creattime = creattime;
		this.bank = bank;
		this.amount = amount;
		this.msgstatus = msgstatus;
		this.ofcardrecords = ofcardrecords;
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
	@JoinColumn(name = "eorderid")
	public Eorder getEorder() {
		return this.eorder;
	}

	public void setEorder(Eorder eorder) {
		this.eorder = eorder;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batchid")
	public Batch getBatch() {
		return this.batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	@Column(name = "numcode", nullable = false, length = 20)
	public String getNumcode() {
		return this.numcode;
	}

	public void setNumcode(String numcode) {
		this.numcode = numcode;
	}

	@Column(name = "charcode", nullable = false, length = 80)
	public String getCharcode() {
		return this.charcode;
	}

	public void setCharcode(String charcode) {
		this.charcode = charcode;
	}

	@Column(name = "source", nullable = false, length = 20)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "canusenum", nullable = false)
	public Long getCanusenum() {
		return this.canusenum;
	}

	public void setCanusenum(Long canusenum) {
		this.canusenum = canusenum;
	}

	@Column(name = "couponid")
	public Long getCouponid() {
		return this.couponid;
	}

	public void setCouponid(Long couponid) {
		this.couponid = couponid;
	}

	@Column(name = "codetype", nullable = false, length = 10)
	public String getCodetype() {
		return this.codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	@Column(name = "usetime", length = 30)
	public String getUsetime() {
		return this.usetime;
	}

	public void setUsetime(String usetime) {
		this.usetime = usetime;
	}

	@Column(name = "endate", length = 30)
	public String getEndate() {
		return this.endate;
	}

	public void setEndate(String endate) {
		this.endate = endate;
	}

	@Column(name = "creattime", nullable = false, length = 30)
	public String getCreattime() {
		return this.creattime;
	}

	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}

	@Column(name = "bank", length = 10)
	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "amount", precision = 5, scale = 0)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "msgstatus")
	public Integer getMsgstatus() {
		return this.msgstatus;
	}

	public void setMsgstatus(Integer msgstatus) {
		this.msgstatus = msgstatus;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "barcode")
	public Set<Ofcardrecord> getOfcardrecords() {
		return this.ofcardrecords;
	}

	public void setOfcardrecords(Set<Ofcardrecord> ofcardrecords) {
		this.ofcardrecords = ofcardrecords;
	}

}