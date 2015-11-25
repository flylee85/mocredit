package com.mocredit.integral.vo;

import com.mocredit.integral.entity.Store;
import com.mocredit.integral.entity.Terminal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytq on 2015/11/12.
 */
public class StoreVo extends Store {
    private String id;
    private String merchantId;
    private String merchantName;
    private String name;
    private List<Terminal> terminals = new ArrayList<Terminal>();

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }

    public void setTerminals(List<Terminal> terminals) {
        this.terminals = terminals;
    }
}
