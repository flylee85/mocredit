package com.mocredit.order.entity;

import java.util.Date;

/**
 * 同步接口地址管理
 * 
 * @author ytq
 * 
 */
public class ApiUrl {
	private Integer id;
	private String url;
	private String code;
	private Integer status;
	private Integer offset;
	private Integer pagesize;
	private Date offsetStartTime;
	private Integer offsetHour;
	private Date startTime;
	private Date endTime;
	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getOffsetStartTime() {
		return offsetStartTime;
	}

	public void setOffsetStartTime(Date offsetStartTime) {
		this.offsetStartTime = offsetStartTime;
	}

	public Integer getOffsetHour() {
		return offsetHour;
	}

	public void setOffsetHour(Integer offsetHour) {
		this.offsetHour = offsetHour;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
