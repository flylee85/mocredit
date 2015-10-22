package com.mocredit.sys.model;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 操作日志-操作日志-实体类
 * @author lishoukun
 * @date 2015/07/18
 */
public class OptLog implements Serializable{
	//序列化
	private static final long serialVersionUID = 6905308258132311722L;
	//主键,id
	private String id ;
	//操作编码,opt_code
	private String optCode ;
	//操作人,operator
	private String operator ;
	//操作时间,opt_time
	private Date optTime ;
	//操作信息,opt_info
	private String optInfo ;
	public String getId(){
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOptCode(){
		return optCode;
	}
	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}
	public String getOperator(){
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOptTime() {
		return optTime;
	}
	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}
	public String getOptInfo(){
		return optInfo;
	}
	public void setOptInfo(String optInfo) {
		this.optInfo = optInfo;
	}
}
