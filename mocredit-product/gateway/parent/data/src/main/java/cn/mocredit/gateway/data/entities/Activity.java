package cn.mocredit.gateway.data.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "activity")
public class Activity extends Id implements java.io.Serializable {
    private String activitId;
    private String week;
    private String startdate;
    private String enddate;
    private String cardbin;
    private String amt;
    private String eitemid;

    public String getEitemid() {
        return eitemid;
    }

    public void setEitemid(String eitemid) {
        this.eitemid = eitemid;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getActivitId() {
        return activitId;
    }

    public void setActivitId(String activitId) {
        this.activitId = activitId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getCardbin() {
        return cardbin;
    }

    public void setCardbin(String cardbin) {
        this.cardbin = cardbin;
    }
}
