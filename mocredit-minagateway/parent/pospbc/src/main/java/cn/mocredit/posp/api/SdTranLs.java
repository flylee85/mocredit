package cn.mocredit.posp.api;

import java.math.BigDecimal;

/**
 * 交易流水
 * @author Superdo
 *
 */
public class SdTranLs {
	/**
	 * 交易类型
	 */
	private String tranType;
	/**
	 * 原交易类型
	 */
	private String origTranType;
	/**
	 * 清算日期
	 */
	private String settleDate;
	/**
	 * 交易日期
	 */
	private String tranDate;
	/**
	 * 交易时间
	 */
	private String tranTime;
	/**
	 * 交易流水
	 */
	private String tranSerial;
	/**
	 * 终端日期
	 */
	private String termDate;
	/**
	 * 终端时间
	 */
	private String termTime;
	/**
	 * 终端流水
	 */
	private String termSerial;
	/**
	 * 主机交易日期
	 */
	private String hostDate;
	/**
	 * 主机流水
	 */
	private String hostSerial;
	/**
	 * 原始交易流水
	 */
	private String origSerial;
	/**
	 * 票据号
	 */
	private String invoiceNo;
	/**
	 * 服务点条件码
	 */
	private String conditionMode;
	public String getConditionMode() {
		return conditionMode;
	}

	public void setConditionMode(String conditionMode) {
		this.conditionMode = conditionMode;
	}

	/**
	 * 原始票据号
	 */
	private String origInvoiceNo;
	/**
	 * 批次号
	 */
	private String batchNo;
	/**
	 * 卡号
	 */
	private String cardNo;
	/**
	 * 卡类型
	 */
	private String cardType;
	/**
	 * 储值卡类型
	 */
	private String ValuecardType;
	/**
	 * 交易金额
	 */
	private BigDecimal tranAmt;
	/**
	 * 剩余金额
	 */
	private BigDecimal remainTranAmt;
	/**
	 * 原始交易金额
	 */
	private BigDecimal origTranAmt;
	/**
	 * 有效时间
	 */
	private String expDate;
	/**
	 * 交易标志
	 */
	private String tranFlag;
	/**
	 * 系统参考号
	 */
	private String referNO;
	/**
	 * 授权号
	 */
	private String authNo;
	/**
	 * 授权日期
	 */
	private String authDate;
	/**
	 * 授权金额
	 */
	private BigDecimal authAmt;
	/**
	 * 收单机构代码
	 */
	private String recvInst;
	/**
	 * 发卡机构代码
	 */
	private String acqInst;
	/**
	 * 代理机构代码
	 */
	private String agqInst;
	/**
	 * 商户代码
	 */
	private String merchId;
	/**
	 * 商户类型
	 */
	private String mcc;
	/**
	 * 终端代码
	 */
	private String termId;
	/**
	 * 终端标志
	 */
	private String termType;
	/**
	 * 操作员代码
	 */
	private String operId;
	/**
	 * 交易发起渠道
	 */
	private String channelType;
	/**
	 * 服务点输入方式码
	 */
	private String inputMode;
	/**
	 * IC卡55号域
	 */
	private String ic55;
	/**
	 * 发卡行应用数据
	 */
	private String issAppdata;
	/**
	 * 应用交易计数器
	 */
	private String atc;
	/**
	 * 应用交互特征
	 */
	private String aip;
	/**
	 * 交易币种代码
	 */
	private String ccyCode;

	/**
	 * 交易响应码
	 */
	private String respCode;
	/**
	 * 批次状态
	 */
	private String batchStatus;
	/**
	 * 批次结果
	 */
	private String batchResult;
	/**
	 * 清算标志
	 */
	private String settleFlag;
	/**
	 * POS结账时间
	 */
	private String termSettleDate;
	/**
	 * 交易状态
	 */
	private String tranStatus;
	/**
	 * 保留
	 */
	private String reserve;
	
	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getOrigTranType() {
		return origTranType;
	}

	public void setOrigTranType(String origTranType) {
		this.origTranType = origTranType;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public String getTranSerial() {
		return tranSerial;
	}

	public void setTranSerial(String tranSerial) {
		this.tranSerial = tranSerial;
	}

	public String getTermDate() {
		return termDate;
	}

	public void setTermDate(String termDate) {
		this.termDate = termDate;
	}

	public String getTermTime() {
		return termTime;
	}

	public void setTermTime(String termTime) {
		this.termTime = termTime;
	}

	public String getTermSerial() {
		return termSerial;
	}

	public void setTermSerial(String termSerial) {
		this.termSerial = termSerial;
	}

	public String getHostDate() {
		return hostDate;
	}

	public void setHostDate(String hostDate) {
		this.hostDate = hostDate;
	}

	public String getHostSerial() {
		return hostSerial;
	}

	public void setHostSerial(String hostSerial) {
		this.hostSerial = hostSerial;
	}

	public String getOrigSerial() {
		return origSerial;
	}

	public void setOrigSerial(String origSerial) {
		this.origSerial = origSerial;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getOrigInvoiceNo() {
		return origInvoiceNo;
	}

	public void setOrigInvoiceNo(String origInvoiceNo) {
		this.origInvoiceNo = origInvoiceNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getValuecardType() {
		return ValuecardType;
	}

	public void setValuecardType(String valuecardType) {
		ValuecardType = valuecardType;
	}

	public BigDecimal getTranAmt() {
		return tranAmt;
	}

	public void setTranAmt(BigDecimal tranAmt) {
		this.tranAmt = tranAmt;
	}

	public BigDecimal getRemainTranAmt() {
		return remainTranAmt;
	}

	public void setRemainTranAmt(BigDecimal remainTranAmt) {
		this.remainTranAmt = remainTranAmt;
	}

	public BigDecimal getOrigTranAmt() {
		return origTranAmt;
	}

	public void setOrigTranAmt(BigDecimal origTranAmt) {
		this.origTranAmt = origTranAmt;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getTranFlag() {
		return tranFlag;
	}

	public void setTranFlag(String tranFlag) {
		this.tranFlag = tranFlag;
	}

	public String getReferNO() {
		return referNO;
	}

	public void setReferNO(String referNO) {
		this.referNO = referNO;
	}

	public String getAuthNo() {
		return authNo;
	}

	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}

	public String getAuthDate() {
		return authDate;
	}

	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}

	public BigDecimal getAuthAmt() {
		return authAmt;
	}

	public void setAuthAmt(BigDecimal authAmt) {
		this.authAmt = authAmt;
	}

	public String getRecvInst() {
		return recvInst;
	}

	public void setRecvInst(String recvInst) {
		this.recvInst = recvInst;
	}

	public String getAcqInst() {
		return acqInst;
	}

	public void setAcqInst(String acqInst) {
		this.acqInst = acqInst;
	}

	public String getAgqInst() {
		return agqInst;
	}

	public void setAgqInst(String agqInst) {
		this.agqInst = agqInst;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
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

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getInputMode() {
		return inputMode;
	}

	public void setInputMode(String inputMode) {
		this.inputMode = inputMode;
	}

	public String getIc55() {
		return ic55;
	}

	public void setIc55(String ic55) {
		this.ic55 = ic55;
	}

	public String getIssAppdata() {
		return issAppdata;
	}

	public void setIssAppdata(String issAppdata) {
		this.issAppdata = issAppdata;
	}

	public String getAtc() {
		return atc;
	}

	public void setAtc(String atc) {
		this.atc = atc;
	}

	public String getAip() {
		return aip;
	}

	public void setAip(String aip) {
		this.aip = aip;
	}

	public String getCcyCode() {
		return ccyCode;
	}

	public void setCcyCode(String ccyCode) {
		this.ccyCode = ccyCode;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	public String getBatchResult() {
		return batchResult;
	}

	public void setBatchResult(String batchResult) {
		this.batchResult = batchResult;
	}

	public String getSettleFlag() {
		return settleFlag;
	}

	public void setSettleFlag(String settleFlag) {
		this.settleFlag = settleFlag;
	}

	public String getTermSettleDate() {
		return termSettleDate;
	}

	public void setTermSettleDate(String termSettleDate) {
		this.termSettleDate = termSettleDate;
	}

	public String getTranStatus() {
		return tranStatus;
	}

	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	@Override
	public String toString() {
		return "SdTranLs [tranType=" + tranType + ", origTranType="
				+ origTranType + ", settleDate=" + settleDate + ", tranDate="
				+ tranDate + ", tranTime=" + tranTime + ", tranSerial="
				+ tranSerial + ", termDate=" + termDate + ", termTime="
				+ termTime + ", termSerial=" + termSerial + ", hostDate="
				+ hostDate + ", hostSerial=" + hostSerial + ", origSerial="
				+ origSerial + ", invoiceNo=" + invoiceNo + ",conditionMode=" + conditionMode + ", origInvoiceNo="
				+ origInvoiceNo + ", batchNo=" + batchNo + ", cardNo=" + cardNo
				+ ", cardType=" + cardType + ", ValuecardType=" + ValuecardType
				+ ", tranAmt=" + tranAmt + ", remainTranAmt=" + remainTranAmt
				+ ", origTranAmt=" + origTranAmt + ", expDate=" + expDate
				+ ", tranFlag=" + tranFlag + ", referNO=" + referNO
				+ ", authNo=" + authNo + ", authDate=" + authDate
				+ ", authAmt=" + authAmt + ", recvInst=" + recvInst
				+ ", acqInst=" + acqInst + ", agqInst=" + agqInst
				+ ", merchId=" + merchId + ", mcc=" + mcc + ", termId="
				+ termId + ", termType=" + termType + ", operId=" + operId
				+ ", channelType=" + channelType + ", inputMode=" + inputMode
				+ ", ic55=" + ic55 + ", issAppdata=" + issAppdata + ", atc="
				+ atc + ", aip=" + aip + ", ccyCode=" + ccyCode + ", respCode="
				+ respCode + ", batchStatus=" + batchStatus + ", batchResult="
				+ batchResult + ", settleFlag=" + settleFlag
				+ ", termSettleDate=" + termSettleDate + ", tranStatus="
				+ tranStatus + ", reserve=" + reserve + "]";
	}
}
	
