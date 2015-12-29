package com.mocredit.gateway.entity;

import java.util.Date;

/**
 * 对外接口响应日志
 *
 * @author ytq
 */
public class InResponseLog {
    private Integer id;
    private Integer requestId;
    private String response;
    private Date cTime;

    public InResponseLog(Integer requestId, String response) {
        super();
        this.requestId = requestId;
        this.response = response;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }
}
