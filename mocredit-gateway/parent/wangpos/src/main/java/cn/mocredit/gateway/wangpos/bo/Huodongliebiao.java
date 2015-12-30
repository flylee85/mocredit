package cn.mocredit.gateway.wangpos.bo;

public class Huodongliebiao implements java.io.Serializable {
    public String state;
    public ActivityBo[] activityList;

    public ActivityBo[] getActivityList() {
        return activityList;
    }

    public void setActivityList(ActivityBo[] activityList) {
        this.activityList = activityList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
