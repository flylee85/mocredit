package com.mocredit.activity.model;

import java.io.Serializable;


/**
 * 
 * 发码批次码-发码批次码-实体类
 * @author lishoukun
 * @date 2015/07/13
 */
public class BatchCode implements Serializable{
	//序列化
	private static final long serialVersionUID = 6905308258132311722L;
	//id,id
	private String id ;
	//发码批次id,order_id
	private String orderId ;
	//码号,code
	private String code ;
	//码id,code_id
	private String codeId ;
	//客户手机号,customer_mobile
	private String customerMobile ;
	//客户名称,customer_name
	private String customerName ;
	//状态,status,暂定为01：已导入，未送码，02：已送码，未发码，03：已发码'
	private String status ;
	public String getId(){
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId(){
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCode(){
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeId(){
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCustomerMobile(){
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getCustomerName(){
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
