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
	private String pubEnterpriseIds;
	private String supEnterpriseIds;
	private String storeIds;
	private String activityIds;
	private String exportType;

	public String getStatuses() {
		return statuses;
	}

	public void setStatuses(String statuses) {
		this.statuses = statuses;
	}

	public String getPubEnterpriseIds() {
		return pubEnterpriseIds;
	}

	public void setPubEnterpriseIds(String pubEnterpriseIds) {
		this.pubEnterpriseIds = pubEnterpriseIds;
	}

	public String getSupEnterpriseIds() {
		return supEnterpriseIds;
	}

	public void setSupEnterpriseIds(String supEnterpriseIds) {
		this.supEnterpriseIds = supEnterpriseIds;
	}

	public String getStoreIds() {
		return storeIds;
	}

	public void setStoreIds(String storeIds) {
		this.storeIds = storeIds;
	}

	public String getActivityIds() {
		return activityIds;
	}

	public void setActivityIds(String activityIds) {
		this.activityIds = activityIds;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	
}
