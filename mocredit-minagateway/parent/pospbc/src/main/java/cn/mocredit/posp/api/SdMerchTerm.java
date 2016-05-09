package cn.mocredit.posp.api;
/**
 * 商户终端信息
 * @author Superdo
 *
 */
public class SdMerchTerm {
	/**
	 * 商户终端标识
	 */
	private String id;
	/**
	 * 商户编号
	 */
	private String merchId;
	/**
	 * 终端编号
	 */
	private String termId;
	/**
	 * 终端类型
	 */
	private String termType;
	/**
	 * 商户类型
	 */
	private String mcc;
	/**
	 * 商户名称
	 */
	private String merchName;
	/**
	 * 分店名称
	 */
	private String shopName;
	/**
	 * 终端标识
	 * 1-未开通、2-签到、3-签退、4-异常 
	 */
	private String falg;
	/**
	 * 备注
	 */
	private String remark;
	@Override
	public String toString() {
		return "SdMerchTerm [id=" + id + ", merchId=" + merchId + ", termId="
				+ termId + ", termType=" + termType + ", mcc=" + mcc
				+ ", merchName=" + merchName + ", shopName=" + shopName
				+ ", falg=" + falg + ", remark=" + remark + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getMerchName() {
		return merchName;
	}
	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getFalg() {
		return falg;
	}
	public void setFalg(String falg) {
		this.falg = falg;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
