package cn.mocredit.gateway.data.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "liushui")
public class LiuShui extends Id implements java.io.Serializable {
    private String devcode;
    private String wangposorderid;
    private String yimeiorderid;
    private String batchno;
    private String searchno;
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDevcode() {
        return devcode;
    }

    public void setDevcode(String devcode) {
        this.devcode = devcode;
    }

    public String getWangposorderid() {
        return wangposorderid;
    }

    public void setWangposorderid(String wangposorderid) {
        this.wangposorderid = wangposorderid;
    }

    public String getYimeiorderid() {
        return yimeiorderid;
    }

    public void setYimeiorderid(String yimeiorderid) {
        this.yimeiorderid = yimeiorderid;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getSearchno() {
        return searchno;
    }

    public void setSearchno(String searchno) {
        this.searchno = searchno;
    }
}
