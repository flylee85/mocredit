package com.mocredit.integral.entity;

import java.util.Date;
/**
 * 请求外部接口的请求日志
 * @author ytq
 *
 */
public class OutRequestLog {
/**
   uuid                 int not null auto_increment,
   request_id           int comment '请求记录ID(ti_in_request_log)',
   url                  varchar(200) comment '请求的接口',
   request              blob comment '请求数据 json格式',
   timestamp            datetime comment '时间戳 ',
   primary key (uuid)
 */
	private Integer uuid;
	private Integer requestId;
	private String url;
	private String request;
	private Date timestamp;
	public Integer getUuid() {
		return uuid;
	}
	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	

}
