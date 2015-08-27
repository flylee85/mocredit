package com.mocredit.integral.entity;

import java.util.Date;

/**
 * 请求外部接口的响应日志
 * 
 * @author ytq
 * 
 */
public class OutResponseLog {
/**
   uuid                 int not null auto_increment,
   request_id           int comment '请求记录ID(ti_in_request_log)',
   response             blob comment '请求响应数据 json格式',
   timestamp            datetime comment '时间戳 ',
   primary key (uuid)
 */
	private Integer uuid;
	private Integer requestId;
	private String response;
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

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
