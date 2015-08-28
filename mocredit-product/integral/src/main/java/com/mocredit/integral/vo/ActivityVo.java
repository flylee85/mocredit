package com.mocredit.integral.vo;

import com.mocredit.integral.entity.Activity;

/**
 * @author ytq 2015年8月24日
 */
public class ActivityVo extends Activity {
	private String productCode;
	private Integer operCode;
	private String storeList;

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

	public String getStoreList() {
		return storeList;
	}

	public void setStoreList(String storeList) {
		this.storeList = storeList;
	}

}
