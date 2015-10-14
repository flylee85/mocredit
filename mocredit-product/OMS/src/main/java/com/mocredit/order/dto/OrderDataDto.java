package com.mocredit.order.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderDataDto {
	private Integer code;
	private String msg;
	private Integer total;
	private List<OrderDto> data = new ArrayList<OrderDto>();

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<OrderDto> getData() {
		return data;
	}

	public void setData(List<OrderDto> data) {
		this.data = data;
	}

}
