package cn.m.mt.po;

import java.util.Date;
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
import javax.persistence.UniqueConstraint;

/**
 * Eitem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "eitem", uniqueConstraints = @UniqueConstraint(columnNames = "numiid"))
public class Eitem implements java.io.Serializable {

	// Fields

	private Long id;
	private Eitem eitem;
	private Shop shop;
	private Eshopkey eshopkey;
	private Commodity commodity;
	private Enterprise enterprise;
	private String name;
	private String shortname;
	private Long num;
	private Double origprice;
	private Double price;
	private Double emaymoney;
	private Long numiid;
	private String type;
	private String outerid;
	private String picurl;
	private String fileurl;
	private String province;
	private String city;
	private String status;
	private String reason;
	private String descr;
	private String smscontent;
	private String createtime;
	private Long cid;
	private String cidstr;
	private String props;
	private String skuproperties;
	private String inputstr;
	private String inputpids;
	private String sellercats;
	private Integer codetimes;
	private Integer codeday;
	private String modifytime;
	private Double othermoney;
	private String firstaudittime;
	private String audittime;
	private String endtime;
	private String salesname;
	private String salesemail;
	private String salesphone;
	private Integer senttosales;
	private Integer mmspackageid;
	private Integer codeno;
	private String printstr;
	private String subject;
	private String checkinfo;
	private Integer isresend;
	private String productcode;
	private Integer iszxcheck;
	private Integer ismain;
	private Integer ispayment;
	private String content;
	private String consumption;
	private Double smsprice;
	private Double mmsprice;
	private Double checkprice;
	private Double servicerate;
	private Integer accounttype;
	private String setcreatetime;
	private String setupdatetime;
	private Double amountfee;
	private Integer isresendsms;
	private Integer sort;
	private String expenseSms;
	private String refundSms;
	private String printTitle;
	private Bank bank;
	private Integer expointType;
	private Integer point;
	private Integer pointrate;
	private String weeklimit;
	private String merchantid;
	private String merchantpassword;
	private String callbackurl;
	private Set<Eorder> eorders = new HashSet<Eorder>(0);
	private Set<Eitemstore> eitemstores = new HashSet<Eitemstore>(0);
	private Set<Batch> batchs = new HashSet<Batch>(0);
	private Set<Overorderlog> overorderlogs = new HashSet<Overorderlog>(0);
	private Set<Mmsframe> mmsframes = new HashSet<Mmsframe>(0);
	private Set<Smsmt> smsmts = new HashSet<Smsmt>(0);
	private Set<Enterprisebilldetail> enterprisebilldetails = new HashSet<Enterprisebilldetail>(
			0);
	private Set<Barcode> barcodes = new HashSet<Barcode>(0);
	private Set<Eitem> eitems = new HashSet<Eitem>(0);
	private String advertisement = "0";//是否关联广告门店 0代表无广告 1代表有广告
	private String advcontent ;//广告内容
	private String mobilecharge = "0" ;//是否是手机充值活动 0为否 1为是
	private String mchargetype ;//手机充值的类型 1为殴飞 2为其它
	/*广告活动限定周期。D：每日；W：每周；M：每月*/
	private String advertiseunit=null;
	/*'广告活动限定数量。大于0此字段有效。若0，则表示不做限制。*/
	private Integer advertisemaxnum=0;
	/*发码起始日期*/
	private String advStartTime;
	/*发码结束日期*/
	private String advEndTime;
	/*发码日期*/
	private String advSpecTime;
	/*广告优先级*/
	private Integer advPriority;
	/*规则代码*/
	private String advRule;
	/*最小金额*/
	private Double advMinAmnt;
	/*限定周期内每张卡最大可刷次数*/
	private Integer maxNumPerCard;
	/*金额(规则3的条件)*/
	private Double advRule3Amnt;
	/*是否为记账活动*/
	private String jizhang;
	// Constructors

	/** default constructor */
	public Eitem() {
	}

	/** minimal constructor */
	public Eitem(Enterprise enterprise, Double emaymoney, Integer codetimes,
			Integer codeday, Integer iszxcheck, String advertisement) {
		this.enterprise = enterprise;
		this.emaymoney = emaymoney;
		this.codetimes = codetimes;
		this.codeday = codeday;
		this.iszxcheck = iszxcheck;
		this.advertisement = advertisement;
	}

	/** full constructor */
	public Eitem(Eitem eitem, Shop shop, Eshopkey eshopkey,
			Commodity commodity, Enterprise enterprise, String name,
			String shortname, Long num, Double origprice, Double price,
			Double emaymoney, Long numiid, String type, String outerid,
			String picurl, String fileurl, String province, String city,
			String status, String reason, String descr, String smscontent,
			String createtime, Long cid, String cidstr, String props,
			String skuproperties, String inputstr, String inputpids,
			String sellercats, Integer codetimes, Integer codeday,
			String modifytime, Double othermoney, String firstaudittime,
			String audittime, String endtime, String salesname,
			String salesemail, String salesphone, Integer senttosales,
			Integer mmspackageid, Integer codeno, String printstr,
			String subject, String checkinfo, Integer isresend,
			String productcode, Integer iszxcheck, Integer ismain,
			Integer ispayment, String content, String consumption,
			Double smsprice, Double mmsprice, Double checkprice,
			Double servicerate, Integer accounttype, String setcreatetime,
			String setupdatetime, Double amountfee, Integer isresendsms,
			Integer sort, String expenseSms, String refundSms,
			String printTitle, Bank bank, Integer expointType, Integer point,
			Integer pointrate, String weeklimit, String merchantid,
			String merchantpassword, Set<Eorder> eorders,
			Set<Eitemstore> eitemstores, Set<Batch> batchs,
			Set<Overorderlog> overorderlogs, Set<Mmsframe> mmsframes,
			Set<Smsmt> smsmts, Set<Enterprisebilldetail> enterprisebilldetails,
			Set<Barcode> barcodes, Set<Eitem> eitems, String advertisement,
			String callbackurl) {
		this.eitem = eitem;
		this.shop = shop;
		this.eshopkey = eshopkey;
		this.commodity = commodity;
		this.enterprise = enterprise;
		this.name = name;
		this.shortname = shortname;
		this.num = num;
		this.origprice = origprice;
		this.price = price;
		this.emaymoney = emaymoney;
		this.numiid = numiid;
		this.type = type;
		this.outerid = outerid;
		this.picurl = picurl;
		this.fileurl = fileurl;
		this.province = province;
		this.city = city;
		this.status = status;
		this.reason = reason;
		this.descr = descr;
		this.smscontent = smscontent;
		this.createtime = createtime;
		this.cid = cid;
		this.cidstr = cidstr;
		this.props = props;
		this.skuproperties = skuproperties;
		this.inputstr = inputstr;
		this.inputpids = inputpids;
		this.sellercats = sellercats;
		this.codetimes = codetimes;
		this.codeday = codeday;
		this.modifytime = modifytime;
		this.othermoney = othermoney;
		this.firstaudittime = firstaudittime;
		this.audittime = audittime;
		this.endtime = endtime;
		this.salesname = salesname;
		this.salesemail = salesemail;
		this.salesphone = salesphone;
		this.senttosales = senttosales;
		this.mmspackageid = mmspackageid;
		this.codeno = codeno;
		this.printstr = printstr;
		this.subject = subject;
		this.checkinfo = checkinfo;
		this.isresend = isresend;
		this.productcode = productcode;
		this.iszxcheck = iszxcheck;
		this.ismain = ismain;
		this.ispayment = ispayment;
		this.content = content;
		this.consumption = consumption;
		this.smsprice = smsprice;
		this.mmsprice = mmsprice;
		this.checkprice = checkprice;
		this.servicerate = servicerate;
		this.accounttype = accounttype;
		this.setcreatetime = setcreatetime;
		this.setupdatetime = setupdatetime;
		this.amountfee = amountfee;
		this.isresendsms = isresendsms;
		this.sort = sort;
		this.expenseSms = expenseSms;
		this.refundSms = refundSms;
		this.printTitle = printTitle;
		this.bank = bank;
		this.expointType = expointType;
		this.point = point;
		this.pointrate = pointrate;
		this.weeklimit = weeklimit;
		this.merchantid = merchantid;
		this.merchantpassword = merchantpassword;
		this.eorders = eorders;
		this.eitemstores = eitemstores;
		this.batchs = batchs;
		this.overorderlogs = overorderlogs;
		this.mmsframes = mmsframes;
		this.smsmts = smsmts;
		this.enterprisebilldetails = enterprisebilldetails;
		this.barcodes = barcodes;
		this.eitems = eitems;
		this.advertisement = advertisement;
		this.callbackurl = callbackurl;
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
	@JoinColumn(name = "shopid")
	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eshopkeyid")
	public Eshopkey getEshopkey() {
		return this.eshopkey;
	}

	public void setEshopkey(Eshopkey eshopkey) {
		this.eshopkey = eshopkey;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commodityid")
	public Commodity getCommodity() {
		return this.commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entid", nullable = false)
	public Enterprise getEnterprise() {
		return this.enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "shortname", length = 100)
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "num")
	public Long getNum() {
		return this.num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	@Column(name = "origprice", precision = 22, scale = 0)
	public Double getOrigprice() {
		return this.origprice;
	}

	public void setOrigprice(Double origprice) {
		this.origprice = origprice;
	}

	@Column(name = "price", precision = 22, scale = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "emaymoney", nullable = false, precision = 22, scale = 0)
	public Double getEmaymoney() {
		return this.emaymoney;
	}

	public void setEmaymoney(Double emaymoney) {
		this.emaymoney = emaymoney;
	}

	@Column(name = "numiid", unique = true)
	public Long getNumiid() {
		return this.numiid;
	}

	public void setNumiid(Long numiid) {
		this.numiid = numiid;
	}

	@Column(name = "type", length = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "outerid", length = 20)
	public String getOuterid() {
		return this.outerid;
	}

	public void setOuterid(String outerid) {
		this.outerid = outerid;
	}

	@Column(name = "picurl", length = 200)
	public String getPicurl() {
		return this.picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	@Column(name = "fileurl", length = 200)
	public String getFileurl() {
		return this.fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	@Column(name = "province", length = 20)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city", length = 20)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "status", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 是否关联广告门店 0代表无广告 1代表有广告
	 * @return
	 */
	@Column(name = "advertisement", length = 2)
	public String getAdvertisement() {
		return advertisement;
	}

	/**
	 * 是否关联广告门店 0代表无广告 1代表有广告
	 * @param advertisement
	 */
	public void setAdvertisement(String advertisement) {
		this.advertisement = advertisement;
	}

	@Column(name = "advcontent", length = 400)
	public String getAdvcontent() {
		return advcontent;
	}

	public void setAdvcontent(String advcontent) {
		this.advcontent = advcontent;
	}

	@Column(name = "reason", length = 200)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "descr")
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "smscontent", length = 200)
	public String getSmscontent() {
		return this.smscontent;
	}

	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}

	@Column(name = "createtime", length = 50)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "cid")
	public Long getCid() {
		return this.cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	@Column(name = "cidstr", length = 100)
	public String getCidstr() {
		return this.cidstr;
	}

	public void setCidstr(String cidstr) {
		this.cidstr = cidstr;
	}

	@Column(name = "props", length = 65535)
	public String getProps() {
		return this.props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	@Column(name = "skuproperties", length = 65535)
	public String getSkuproperties() {
		return this.skuproperties;
	}

	public void setSkuproperties(String skuproperties) {
		this.skuproperties = skuproperties;
	}

	@Column(name = "inputstr", length = 65535)
	public String getInputstr() {
		return this.inputstr;
	}

	public void setInputstr(String inputstr) {
		this.inputstr = inputstr;
	}

	@Column(name = "inputpids", length = 65535)
	public String getInputpids() {
		return this.inputpids;
	}

	public void setInputpids(String inputpids) {
		this.inputpids = inputpids;
	}

	@Column(name = "sellercats", length = 2000)
	public String getSellercats() {
		return this.sellercats;
	}

	public void setSellercats(String sellercats) {
		this.sellercats = sellercats;
	}

	@Column(name = "codetimes", nullable = false)
	public Integer getCodetimes() {
		return this.codetimes;
	}

	public void setCodetimes(Integer codetimes) {
		this.codetimes = codetimes;
	}

	@Column(name = "codeday", nullable = false)
	public Integer getCodeday() {
		return this.codeday;
	}

	public void setCodeday(Integer codeday) {
		this.codeday = codeday;
	}

	@Column(name = "modifytime", length = 20)
	public String getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "othermoney", precision = 22, scale = 0)
	public Double getOthermoney() {
		return this.othermoney;
	}

	public void setOthermoney(Double othermoney) {
		this.othermoney = othermoney;
	}

	@Column(name = "firstaudittime", length = 20)
	public String getFirstaudittime() {
		return this.firstaudittime;
	}

	public void setFirstaudittime(String firstaudittime) {
		this.firstaudittime = firstaudittime;
	}

	@Column(name = "audittime", length = 20)
	public String getAudittime() {
		return this.audittime;
	}

	public void setAudittime(String audittime) {
		this.audittime = audittime;
	}

	@Column(name = "endtime", length = 20)
	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	@Column(name = "salesname", length = 10)
	public String getSalesname() {
		return this.salesname;
	}

	public void setSalesname(String salesname) {
		this.salesname = salesname;
	}

	@Column(name = "salesemail", length = 50)
	public String getSalesemail() {
		return this.salesemail;
	}

	public void setSalesemail(String salesemail) {
		this.salesemail = salesemail;
	}

	@Column(name = "salesphone", length = 20)
	public String getSalesphone() {
		return this.salesphone;
	}

	public void setSalesphone(String salesphone) {
		this.salesphone = salesphone;
	}

	@Column(name = "senttosales")
	public Integer getSenttosales() {
		return this.senttosales;
	}

	public void setSenttosales(Integer senttosales) {
		this.senttosales = senttosales;
	}

	@Column(name = "mmspackageid")
	public Integer getMmspackageid() {
		return this.mmspackageid;
	}

	public void setMmspackageid(Integer mmspackageid) {
		this.mmspackageid = mmspackageid;
	}

	@Column(name = "codeno")
	public Integer getCodeno() {
		return this.codeno;
	}

	public void setCodeno(Integer codeno) {
		this.codeno = codeno;
	}

	@Column(name = "printstr", length = 200)
	public String getPrintstr() {
		return this.printstr;
	}

	public void setPrintstr(String printstr) {
		this.printstr = printstr;
	}

	@Column(name = "subject", length = 100)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "checkinfo", length = 100)
	public String getCheckinfo() {
		return this.checkinfo;
	}

	public void setCheckinfo(String checkinfo) {
		this.checkinfo = checkinfo;
	}

	@Column(name = "isresend")
	public Integer getIsresend() {
		return this.isresend;
	}

	public void setIsresend(Integer isresend) {
		this.isresend = isresend;
	}

	@Column(name = "productcode", length = 50)
	public String getProductcode() {
		return this.productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	@Column(name = "iszxcheck", nullable = false)
	public Integer getIszxcheck() {
		return this.iszxcheck;
	}

	public void setIszxcheck(Integer iszxcheck) {
		this.iszxcheck = iszxcheck;
	}

	@Column(name = "ismain")
	public Integer getIsmain() {
		return this.ismain;
	}

	public void setIsmain(Integer ismain) {
		this.ismain = ismain;
	}

	@Column(name = "ispayment")
	public Integer getIspayment() {
		return this.ispayment;
	}

	public void setIspayment(Integer ispayment) {
		this.ispayment = ispayment;
	}

	@Column(name = "content", length = 16777215)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "consumption", length = 16777215)
	public String getConsumption() {
		return this.consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	@Column(name = "smsprice", precision = 22, scale = 0)
	public Double getSmsprice() {
		return this.smsprice;
	}

	public void setSmsprice(Double smsprice) {
		this.smsprice = smsprice;
	}

	@Column(name = "mmsprice", precision = 22, scale = 0)
	public Double getMmsprice() {
		return this.mmsprice;
	}

	public void setMmsprice(Double mmsprice) {
		this.mmsprice = mmsprice;
	}

	@Column(name = "checkprice", precision = 22, scale = 0)
	public Double getCheckprice() {
		return this.checkprice;
	}

	public void setCheckprice(Double checkprice) {
		this.checkprice = checkprice;
	}

	@Column(name = "servicerate", precision = 22, scale = 0)
	public Double getServicerate() {
		return this.servicerate;
	}

	public void setServicerate(Double servicerate) {
		this.servicerate = servicerate;
	}

	@Column(name = "accounttype")
	public Integer getAccounttype() {
		return this.accounttype;
	}

	public void setAccounttype(Integer accounttype) {
		this.accounttype = accounttype;
	}

	@Column(name = "setcreatetime", length = 20)
	public String getSetcreatetime() {
		return this.setcreatetime;
	}

	public void setSetcreatetime(String setcreatetime) {
		this.setcreatetime = setcreatetime;
	}

	@Column(name = "setupdatetime", length = 20)
	public String getSetupdatetime() {
		return this.setupdatetime;
	}

	public void setSetupdatetime(String setupdatetime) {
		this.setupdatetime = setupdatetime;
	}

	@Column(name = "amountfee", precision = 22, scale = 0)
	public Double getAmountfee() {
		return this.amountfee;
	}

	public void setAmountfee(Double amountfee) {
		this.amountfee = amountfee;
	}

	@Column(name = "isresendsms")
	public Integer getIsresendsms() {
		return this.isresendsms;
	}

	public void setIsresendsms(Integer isresendsms) {
		this.isresendsms = isresendsms;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "expenseSMS", length = 200)
	public String getExpenseSms() {
		return this.expenseSms;
	}

	public void setExpenseSms(String expenseSms) {
		this.expenseSms = expenseSms;
	}

	@Column(name = "refundSMS", length = 200)
	public String getRefundSms() {
		return this.refundSms;
	}

	public void setRefundSms(String refundSms) {
		this.refundSms = refundSms;
	}

	@Column(name = "printTitle", length = 100)
	public String getPrintTitle() {
		return this.printTitle;
	}

	public void setPrintTitle(String printTitle) {
		this.printTitle = printTitle;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bankid")
	public Bank getBank() {
		return this.bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@Column(name = "expointType")
	public Integer getExpointType() {
		return this.expointType;
	}

	public void setExpointType(Integer expointType) {
		this.expointType = expointType;
	}

	@Column(name = "point")
	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Column(name = "pointrate")
	public Integer getPointrate() {
		return this.pointrate;
	}

	public void setPointrate(Integer pointrate) {
		this.pointrate = pointrate;
	}

	@Column(name = "weeklimit", length = 20)
	public String getWeeklimit() {
		return this.weeklimit;
	}

	public void setWeeklimit(String weeklimit) {
		this.weeklimit = weeklimit;
	}

	@Column(name = "merchantid", length = 20)
	public String getMerchantid() {
		return this.merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	@Column(name = "callbackurl", length = 200)
	public String getCallbackurl() {
		return this.callbackurl;
	}

	public void setCallbackurl(String callbackurl) {
		this.callbackurl = callbackurl;
	}
	
	@Column(name = "merchantpassword", length = 20)
	public String getMerchantpassword() {
		return this.merchantpassword;
	}

	public void setMerchantpassword(String merchantpassword) {
		this.merchantpassword = merchantpassword;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eitem")
	public Set<Eorder> getEorders() {
		return this.eorders;
	}

	public void setEorders(Set<Eorder> eorders) {
		this.eorders = eorders;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eitem")
	public Set<Eitemstore> getEitemstores() {
		return this.eitemstores;
	}

	public void setEitemstores(Set<Eitemstore> eitemstores) {
		this.eitemstores = eitemstores;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eitem")
	public Set<Batch> getBatchs() {
		return this.batchs;
	}

	public void setBatchs(Set<Batch> batchs) {
		this.batchs = batchs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eitem")
	public Set<Overorderlog> getOverorderlogs() {
		return this.overorderlogs;
	}

	public void setOverorderlogs(Set<Overorderlog> overorderlogs) {
		this.overorderlogs = overorderlogs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eitem")
	public Set<Mmsframe> getMmsframes() {
		return this.mmsframes;
	}

	public void setMmsframes(Set<Mmsframe> mmsframes) {
		this.mmsframes = mmsframes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eitem")
	public Set<Smsmt> getSmsmts() {
		return this.smsmts;
	}

	public void setSmsmts(Set<Smsmt> smsmts) {
		this.smsmts = smsmts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eitem")
	public Set<Enterprisebilldetail> getEnterprisebilldetails() {
		return this.enterprisebilldetails;
	}

	public void setEnterprisebilldetails(
			Set<Enterprisebilldetail> enterprisebilldetails) {
		this.enterprisebilldetails = enterprisebilldetails;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eitem")
	public Set<Barcode> getBarcodes() {
		return this.barcodes;
	}

	public void setBarcodes(Set<Barcode> barcodes) {
		this.barcodes = barcodes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eitem")
	public Set<Eitem> getEitems() {
		return this.eitems;
	}

	public void setEitems(Set<Eitem> eitems) {
		this.eitems = eitems;
	}

	/**
	 * 是否是手机充值活动 0为否 1为是
	 * @return
	 */
	@Column(name = "mobilecharge", length = 2)
	public String getMobilecharge() {
		return mobilecharge;
	}

	/**
	 * 是否是手机充值活动 0为否 1为是
	 * @param mobilecharge
	 */
	public void setMobilecharge(String mobilecharge) {
		this.mobilecharge = mobilecharge;
	}

	/**
	 * 手机充值的类型 1为殴飞 2为其它
	 * @return
	 */
	@Column(name = "mchargetype", length = 2)
	public String getMchargetype() {
		return mchargetype;
	}

	/**
	 * 手机充值的类型 1为殴飞 2为其它
	 * @param mchargetype
	 */
	public void setMchargetype(String mchargetype) {
		this.mchargetype = mchargetype;
	}

	public String getAdvertiseunit() {
		return advertiseunit;
	}

	public void setAdvertiseunit(String advertiseunit) {
		this.advertiseunit = advertiseunit;
	}

	public String getAdvStartTime() {
		return advStartTime;
	}

	public void setAdvStartTime(String advStartTime) {
		this.advStartTime = advStartTime;
	}

	public String getAdvEndTime() {
		return advEndTime;
	}

	public void setAdvEndTime(String advEndTime) {
		this.advEndTime = advEndTime;
	}

	public String getAdvSpecTime() {
		return advSpecTime;
	}

	public void setAdvSpecTime(String advSpecTime) {
		this.advSpecTime = advSpecTime;
	}

	public String getAdvRule() {
		return advRule;
	}

	public void setAdvRule(String advRule) {
		this.advRule = advRule;
	}

	public Integer getAdvertisemaxnum() {
		return advertisemaxnum;
	}

	public void setAdvertisemaxnum(Integer advertisemaxnum) {
		this.advertisemaxnum = advertisemaxnum;
	}

	public Integer getAdvPriority() {
		return advPriority;
	}

	public void setAdvPriority(Integer advPriority) {
		this.advPriority = advPriority;
	}

	public Double getAdvMinAmnt() {
		return advMinAmnt;
	}

	public void setAdvMinAmnt(Double advMinAmnt) {
		this.advMinAmnt = advMinAmnt;
	}

	public Integer getMaxNumPerCard() {
		return maxNumPerCard;
	}

	public void setMaxNumPerCard(Integer maxNumPerCard) {
		this.maxNumPerCard = maxNumPerCard;
	}

	public Double getAdvRule3Amnt() {
		return advRule3Amnt;
	}

	public void setAdvRule3Amnt(Double advRule3Amnt) {
		this.advRule3Amnt = advRule3Amnt;
	}

	public String getJizhang() {
		return jizhang;
	}

	public void setJizhang(String jizhang) {
		this.jizhang = jizhang;
	}
	
}