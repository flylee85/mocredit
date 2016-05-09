package cn.mocredit.posp.api;
/**
 * 虚拟终端信息
 * @author Superdo
 *
 */
public class SdPosTerm {
	/**
	 * 终端编号
	 */
	private String termId;
	/**
	 * 商户编号
	 */
	private String merchId;
	/**
	 * 终端类型
	 */
	private String termType;
	/**
	 * 操作员
	 */
	private String operId;
	/**
	 * 虚拟终端流水
	 */
	private String posNo;
	/**
	 * 虚拟终端批次号
	 */
	private String batchNo;
	/**
	 * 终端状态
	 * 0-正常 1-签到 2-签退 3-异常
	 */
	private String status;
	/**
	 * 更新时间
	 */
	private String updateTime;
	@Override
	public String toString() {
		return "SdPosTerm [termId=" + termId + ", merchId=" + merchId
				+ ", termType=" + termType + ", operId=" + operId + ", posNo="
				+ posNo + ", batchNo=" + batchNo + ", status=" + status
				+ ", updateTime=" + updateTime + "]";
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getPosNo() {
		return posNo;
	}
	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
