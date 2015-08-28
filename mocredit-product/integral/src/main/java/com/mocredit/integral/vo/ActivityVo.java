package com.mocredit.integral.vo;

import java.util.ArrayList;
import java.util.List;

import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.Store;

/**
 * @author ytq 2015年8月24日
 */
public class ActivityVo extends Activity {
	private String productCode;
	private Integer operCode;
	private List<Store> storeList = new ArrayList<Store>();

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

	public List<Store> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

}
