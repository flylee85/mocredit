package com.mocredit.verifyCode.model;

/**
 * 活动的其他一些基本信息
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class TActivityInfo {

    private String id;

    private String activityId;


    private String ticketTitle;


    private String ticketContent;


    private String posSuccessMsg;


    private String successSmsMsg;

    private String status;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public String getTicketContent() {
        return ticketContent;
    }

    public void setTicketContent(String ticketContent) {
        this.ticketContent = ticketContent;
    }

    public String getPosSuccessMsg() {
        return posSuccessMsg;
    }

    public void setPosSuccessMsg(String posSuccessMsg) {
        this.posSuccessMsg = posSuccessMsg;
    }

    public String getSuccessSmsMsg() {
        return successSmsMsg;
    }

    public void setSuccessSmsMsg(String successSmsMsg) {
        this.successSmsMsg = successSmsMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
