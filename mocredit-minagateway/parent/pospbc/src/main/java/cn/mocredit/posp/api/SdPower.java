package cn.mocredit.posp.api;
/**
 * 商户终端类型对应信息
 * @author Superdo
 *
 */
public class SdPower {
	/**
	 * 第三方商户终端ID
	 */
	private String powerId;
	/**
	 * 亿美商户终端ID
	 */
	private String powPowerid;
	/**
	 * 终端类型
	 */
	private String termType;
	/**
	 * 商户名称
	 */
	private String merchName;
	@Override
	public String toString() {
		return "SdPower [powerId=" + powerId + ", powPowerid=" + powPowerid
				+ ", termType=" + termType + ", merchName=" + merchName + "]";
	}
	public String getPowerId() {
		return powerId;
	}
	public void setPowerId(String powerId) {
		this.powerId = powerId;
	}
	public String getPowPowerid() {
		return powPowerid;
	}
	public void setPowPowerid(String powPowerid) {
		this.powPowerid = powPowerid;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public String getMerchName() {
		return merchName;
	}
	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}
}
