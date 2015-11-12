/**
 *
 */
package com.mocredit.order.vo;

import com.mocredit.order.entity.Order;

import java.util.List;

/**
 * @author ytq
 */
public class OrderVo extends Order {
    private String statuses;
    private List<String> statusList;
    private String keywords;
    private String exportType;

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        if (!"".equals(statuses)) {
            this.statuses = statuses;
        }
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        if (!"".equals(keywords)) {
            this.keywords = keywords;
        }
    }
}
