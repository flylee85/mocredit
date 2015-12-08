package com.mocredit.integral.vo;

import java.util.ArrayList;
import java.util.List;

import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.Store;

/**
 * @author ytq 2015年8月24日
 */
public class ActivityVo extends Activity {
    private String enterpriseName;
    private String productCode;
    private Integer operCode;
    private List<StoreVo> storeList = new ArrayList<StoreVo>();

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
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

    public List<StoreVo> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StoreVo> storeList) {
        this.storeList = storeList;
    }

}
