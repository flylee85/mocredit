package com.mocredit.integral.vo;

import java.io.Serializable;
import java.util.List;

import com.mocredit.integral.entity.Store;

/**
 * @author ytq 2015年8月24日
 */
public class ActivityImportVo implements Serializable {

	/**
	 * activityId 活动ID activityName 活动名 productCode 银行内部代码 operCode 操作代码， 1导入 2
	 * 更新 3 取消 4 启用
	 */
	private static final long serialVersionUID = -4463645666139707545L;

	private Integer activityId;
	private String activityName;
	private String productCode;
	private Integer operCode;
	private List<Store> storeList;

	public List<Store> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getOperCode() {
		return operCode;
	}

	public void setOperCode(Integer operCode) {
		this.operCode = operCode;
	}

	@Override
	public String toString() {
		return "ActivityImportVo [activityId=" + activityId + ", activityName="
				+ activityName + ", productCode=" + productCode + ", operCode="
				+ operCode + "]";
	}

}
