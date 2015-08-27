package com.mocredit.integral.entity;

import java.util.Date;
/**
 * 对外接口请求日志
 * @author ytq
 *
 */
public class InRequestLog {
/**
 * uuid                 int not null auto_increment,
 * ip                   varchar(20) comment '请求发起者IP',
 * interface            varchar(200) comment '请求的接口',
 * request              blob comment '请求数据 json格式',
 * timestamp            datetime comment '时间戳',
 * order_id             varchar(30) comment '订单号',
 * primary key (uuid)
 */
	private Integer uuid;
	private String ip;
	private String interfaceUrl;
	private String request;
	private Date timestamp;
	private String orderId;


	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getInterfaceUrl() {
		return interfaceUrl;
	}

	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
