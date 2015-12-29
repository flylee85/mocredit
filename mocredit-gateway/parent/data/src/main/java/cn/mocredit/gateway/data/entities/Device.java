package cn.mocredit.gateway.data.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class Device extends Id implements java.io.Serializable {
    private String devcode;
    private String devcodemd5;
    private String password;
    private String temppassword;
    private String xintiaohouxu;
    private String storeCode;
    private String shopid;

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getXintiaohouxu() {
        return xintiaohouxu;
    }

    public void setXintiaohouxu(String xintiaohouxu) {
        this.xintiaohouxu = xintiaohouxu;
    }

    public String getDevcode() {
        return devcode;
    }

    public void setDevcode(String devcode) {
        this.devcode = devcode;
    }

    public String getDevcodemd5() {
        return devcodemd5;
    }

    public void setDevcodemd5(String devcodemd5) {
        this.devcodemd5 = devcodemd5;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTemppassword() {
        return temppassword;
    }

    public void setTemppassword(String temppassword) {
        this.temppassword = temppassword;
    }


}
