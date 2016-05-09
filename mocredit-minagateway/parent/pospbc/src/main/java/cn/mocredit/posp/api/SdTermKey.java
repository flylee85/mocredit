package cn.mocredit.posp.api;

/**
 * 终端密钥信息
 * @author Superdo
 */
public class SdTermKey {
	/**
	 * 终端编号
	 */
	private String  termId;
	/**
	 * 商户名称
	 */
	private String merchId;
	/**
	 * 终端类型
	 */
	private String termType;
	/**
	 * 操作代码
	 */
	private String operId;
	/**
	 * 加密机主密钥保存的终端主密钥
	 */
	private String lmkTmk;
	/**
	 * tmk校验值
	 */
	private String tmkCheckValue;
	/**
	 * lmk保护下的tpk
	 */
	private String lmkTpk;
	/**
	 * lmk保护下的tak
	 */
	private String lmkTak;
	/**
	 * 终端密钥保存下的pin key
	 */
	private String tmkTpk;
	/**
	 * 终端密钥保存下的mac key
	 */
	private String tmkTak;
	/**
	 * 批次号
	 */
	private String batchNo;
	/**
	 * 更新时间
	 */
	private String updateTime;
	@Override
	public String toString() {
		return "SdTermKey [termId=" + termId + ", merchId=" + merchId
				+ ", termType=" + termType + ", operId=" + operId + ", lmkTmk="
				+ lmkTmk + ", tmkCheckValue=" + tmkCheckValue + ", lmkTpk="
				+ lmkTpk + ", lmkTak=" + lmkTak + ", tmkTpk=" + tmkTpk
				+ ", tmkTak=" + tmkTak + ", batchNo=" + batchNo
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
	public String getLmkTmk() {
		return lmkTmk;
	}
	public void setLmkTmk(String lmkTmk) {
		this.lmkTmk = lmkTmk;
	}
	public String getTmkCheckValue() {
		return tmkCheckValue;
	}
	public void setTmkCheckValue(String tmkCheckValue) {
		this.tmkCheckValue = tmkCheckValue;
	}
	public String getLmkTpk() {
		return lmkTpk;
	}
	public void setLmkTpk(String lmkTpk) {
		this.lmkTpk = lmkTpk;
	}
	public String getLmkTak() {
		return lmkTak;
	}
	public void setLmkTak(String lmkTak) {
		this.lmkTak = lmkTak;
	}
	public String getTmkTpk() {
		return tmkTpk;
	}
	public void setTmkTpk(String tmkTpk) {
		this.tmkTpk = tmkTpk;
	}
	public String getTmkTak() {
		return tmkTak;
	}
	public void setTmkTak(String tmkTak) {
		this.tmkTak = tmkTak;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
