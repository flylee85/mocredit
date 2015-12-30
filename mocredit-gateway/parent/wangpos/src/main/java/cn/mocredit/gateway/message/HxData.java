package cn.mocredit.gateway.message;

public class HxData {
	private String ticketTitle;
	public String getTicketTitle() {
		return ticketTitle;
	}
	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}
	private String ticketContent;
	public String getTicketContent() {
		return ticketContent;
	}
	public void setTicketContent(String ticketContent) {
		this.ticketContent = ticketContent;
	}
	private String issueEnterpriseId;
	private String activityId;
	private String activityName;
	private String issueEnterpriseName;
	private String startTime;
	private String endTime;
	private String remainTimes;
	private String amount;
	private String remainAmount;
	private String useCount;
	private String posSuccessMsg;
	
	public String getIssueEnterpriseId() {
		return issueEnterpriseId;
	}
	public void setIssueEnterpriseId(String issueEnterpriseId) {
		this.issueEnterpriseId = issueEnterpriseId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getIssueEnterpriseName() {
		return issueEnterpriseName;
	}
	public void setIssueEnterpriseName(String issueEnterpriseName) {
		this.issueEnterpriseName = issueEnterpriseName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getRemainTimes() {
		return remainTimes;
	}
	public void setRemainTimes(String remainTimes) {
		this.remainTimes = remainTimes;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(String remainAmount) {
		this.remainAmount = remainAmount;
	}
	public String getUseCount() {
		return useCount;
	}
	public void setUseCount(String useCount) {
		this.useCount = useCount;
	}
	public String getPosSuccessMsg() {
		return posSuccessMsg;
	}
	public void setPosSuccessMsg(String posSuccessMsg) {
		this.posSuccessMsg = posSuccessMsg;
	}
	
}
