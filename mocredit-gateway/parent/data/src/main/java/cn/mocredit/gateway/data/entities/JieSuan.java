package cn.mocredit.gateway.data.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "jiesuan")
public class JieSuan extends Id implements java.io.Serializable {
    private String info;
    private String devcode;
    private String storeid;

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getDevcode() {
        return devcode;
    }

    public void setDevcode(String devcode) {
        this.devcode = devcode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
