/**
 * 
 */
package com.mocredit.order.vo;

import com.mocredit.order.entity.Order;

/**
 * @author ytq
 * 
 */
public class OrderVo extends Order {
	private String statuses;

	public String getStatuses() {
		return statuses;
	}

	public void setStatuses(String statuses) {
		this.statuses = statuses;
	}

}
