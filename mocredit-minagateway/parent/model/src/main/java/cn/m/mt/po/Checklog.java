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
 * Checklog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "checklog")
public class Checklog implements java.io.Serializable {

	// Fields

	private Long id;
	private Eitem eitem;
	private Device device;
	private Store store;
	private Barcode barcode;
	private String imei;
	private String charcode;
	private Long couponid;
	private String couponuuid;
	private String storeuuid;
	private String checktype;
	private String createtime;
	private String printno;
	private String printresult;
	private String des;
	private Integer checkmode;
	private Integer checknum;
	private String phone;
	private String posno;
	private String batchno;
	private String searchno;

	// Constructors

	/** default constructor */
	public Checklog() {
	}

	/** minimal constructor */
	public Checklog(Barcode barcode, String charcode, String checktype,
			String createtime, Integer checkmode, Integer checknum) {
		this.barcode = barcode;
		this.charcode = charcode;
		this.checktype = checktype;
		this.createtime = createtime;
		this.checkmode = checkmode;
		this.checknum = checknum;
	}

	/** full constructor */
	public Checklog(Eitem eitem, Device device, Store store, Barcode barcode,
			String imei, String charcode, Long couponid, String couponuuid,
			String storeuuid, String checktype, String createtime,
			String printno, String printresult, String des, Integer checkmode,
			Integer checknum, String phone, String posno, String batchno,
			String searchno) {
		this.eitem = eitem;
		this.device = device;
		this.store = store;
		this.barcode = barcode;
		this.imei = imei;
		this.charcode = charcode;
		this.couponid = couponid;
		this.couponuuid = couponuuid;
		this.storeuuid = storeuuid;
		this.checktype = checktype;
		this.createtime = createtime;
		this.printno = printno;
		this.printresult = printresult;
		this.des = des;
		this.checkmode = checkmode;
		this.checknum = checknum;
		this.phone = phone;
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
	@JoinColumn(name = "eitemid")
	public Eitem getEitem() {
		return this.eitem;
	}

	public void setEitem(Eitem eitem) {
		this.eitem = eitem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deviceid")
	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
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
	@JoinColumn(name = "barcodeid")
	public Barcode getBarcode() {
		return this.barcode;
	}

	public void setBarcode(Barcode barcode) {
		this.barcode = barcode;
	}

	@Column(name = "imei", length = 50)
	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "charcode", nullable = false, length = 20)
	public String getCharcode() {
		return this.charcode;
	}

	public void setCharcode(String charcode) {
		this.charcode = charcode;
	}

	@Column(name = "couponid")
	public Long getCouponid() {
		return this.couponid;
	}

	public void setCouponid(Long couponid) {
		this.couponid = couponid;
	}

	@Column(name = "couponuuid", length = 50)
	public String getCouponuuid() {
		return this.couponuuid;
	}

	public void setCouponuuid(String couponuuid) {
		this.couponuuid = couponuuid;
	}

	@Column(name = "storeuuid", length = 50)
	public String getStoreuuid() {
		return this.storeuuid;
	}

	public void setStoreuuid(String storeuuid) {
		this.storeuuid = storeuuid;
	}

	@Column(name = "checktype", nullable = false, length = 10)
	public String getChecktype() {
		return this.checktype;
	}

	public void setChecktype(String checktype) {
		this.checktype = checktype;
	}

	@Column(name = "createtime", nullable = false, length = 30)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "printno", length = 100)
	public String getPrintno() {
		return this.printno;
	}

	public void setPrintno(String printno) {
		this.printno = printno;
	}

	@Column(name = "printresult", length = 10)
	public String getPrintresult() {
		return this.printresult;
	}

	public void setPrintresult(String printresult) {
		this.printresult = printresult;
	}

	@Column(name = "des", length = 100)
	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	@Column(name = "checkmode", nullable = false)
	public Integer getCheckmode() {
		return this.checkmode;
	}

	public void setCheckmode(Integer checkmode) {
		this.checkmode = checkmode;
	}

	@Column(name = "checknum", nullable = false)
	public Integer getChecknum() {
		return this.checknum;
	}

	public void setChecknum(Integer checknum) {
		this.checknum = checknum;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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