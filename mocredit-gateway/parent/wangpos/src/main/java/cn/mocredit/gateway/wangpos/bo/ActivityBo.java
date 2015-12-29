package cn.mocredit.gateway.wangpos.bo;

public class ActivityBo implements java.io.Serializable {
    public String activityCode;
    public String activityOutCode;
    public String activityType;
    public String selectDate;
    public String shopName;
    public String storeCode;
    public String storeName;
    public String activitId;
    public String activit;
    public String amt;
    public String activitName;
    public String enterpriseName;
    public String activitRule;
    public String sTime;
    public String eTime;
    public String cardBin;
    public String week;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getActivitId() {
        return activitId;
    }

    public void setActivitId(String activitId) {
        this.activitId = activitId;
    }

    public String getActivit() {
        return activit;
    }

    public void setActivit(String activit) {
        this.activit = activit;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getActivitName() {
        return activitName;
    }

    public void setActivitName(String activitName) {
        this.activitName = activitName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getActivitRule() {
        return activitRule;
    }

    public void setActivitRule(String activitRule) {
        this.activitRule = activitRule;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }
}
