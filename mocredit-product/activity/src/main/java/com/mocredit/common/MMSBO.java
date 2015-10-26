package com.mocredit.common;

import java.io.Serializable;


public class MMSBO implements Serializable {
	   
	/**  
	 * @Fields serialVersionUID : TODO  
	 */  
	private static final long serialVersionUID = 1616739421759262763L;
	private Long id;
	//0短信，1彩信,2短彩
	private Integer type;
	private Long eorderid;
	private Long batchid;
	private Long eitemid;
	private Long entid;
	private Long couponid;
	private Integer mttype;//0:系统导入发送 ；1：直发；2：手动补发
	private String content;
	private String mobile;
	private Integer packageid;
	private String customer;
	private String numberpwd;
	private String extfield1;
	private String extfield2;
	private String extfield3;
	private Integer status;
	private Integer state;
	private Integer isresend;
	private String errorinfo;
	private String statuscode;//发送者用户名
	private String tid;
	private String createtime;
	private String sendtime;
	private String charcode;
	private Integer barcodeno;
	private String updatetime;
	private Long contatsid;
	private Long barcodeid;
	private String channleno;
	
	private String importId;//新添加一个属性，用于标注相关联的导入数据
	public MMSBO(){}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEorderid() {
		return eorderid;
	}
	public void setEorderid(Long eorderid) {
		this.eorderid = eorderid;
	}
	public Long getBatchid() {
		return batchid;
	}
	public void setBatchid(Long batchid) {
		this.batchid = batchid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getPackageid() {
		return packageid;
	}
	public void setPackageid(Integer packageid) {
		this.packageid = packageid;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getNumberpwd() {
		return numberpwd;
	}
	public void setNumberpwd(String numberpwd) {
		this.numberpwd = numberpwd;
	}
	public String getExtfield1() {
		return extfield1;
	}
	public void setExtfield1(String extfield1) {
		this.extfield1 = extfield1;
	}
	public String getExtfield2() {
		return extfield2;
	}
	public void setExtfield2(String extfield2) {
		this.extfield2 = extfield2;
	}
	public String getExtfield3() {
		return extfield3;
	}
	public void setExtfield3(String extfield3) {
		this.extfield3 = extfield3;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsresend() {
		return isresend;
	}
	public void setIsresend(Integer isresend) {
		this.isresend = isresend;
	}
	public String getErrorinfo() {
		return errorinfo;
	}
	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}
	public String getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	public String getCharcode() {
		return charcode;
	}
	public void setCharcode(String charcode) {
		this.charcode = charcode;
	}
	public Integer getBarcodeno() {
		return barcodeno;
	}
	public void setBarcodeno(Integer barcodeno) {
		this.barcodeno = barcodeno;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public Long getEitemid() {
		return eitemid;
	}
	public void setEitemid(Long eitemid) {
		this.eitemid = eitemid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getCouponid() {
		return couponid;
	}
	public void setCouponid(Long couponid) {
		this.couponid = couponid;
	}
	public Integer getMttype() {
		return mttype;
	}
	public void setMttype(Integer mttype) {
		this.mttype = mttype;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	public Long getContatsid() {
		return contatsid;
	}

	public void setContatsid(Long contatsid) {
		this.contatsid = contatsid;
	}

	public Long getEntid() {
		return entid;
	}

	public void setEntid(Long entid) {
		this.entid = entid;
	}

	public Long getBarcodeid() {
		return barcodeid;
	}

	public void setBarcodeid(Long barcodeid) {
		this.barcodeid = barcodeid;
	}

	public String getChannleno() {
		return channleno;
	}

	public void setChannleno(String channleno) {
		this.channleno = channleno;
	}

	public String getImportId() {
		return importId;
	}

	public void setImportId(String importId) {
		this.importId = importId;
	}
	
}
