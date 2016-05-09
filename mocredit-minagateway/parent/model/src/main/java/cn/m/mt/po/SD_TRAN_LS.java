package cn.m.mt.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SD_TRAN_LS implements java.io.Serializable {

	private static final long serialVersionUID = 8255387998980324125L;
	private Long id;
	private String TRAN_TYPE;
	private String ORIG_TRAN_TYPE;
	private String SETTLE_DATE;
	private String TRAN_DATE;
	private String TRAN_TIME;
	private String TRAN_SERIAL;
	private String TERM_DATE;
	private String TERM_TIME;
	private String TERM_SERIAL;
	private String HOST_DATE;
	private String HOST_SERIAL;
	private String ORIG_SERIAL;
	private String INVOICE_NO;
	private String CONDITION_MODE;
	private String ORIG_INVOICE_NO;
	private String BATCH_NO;
	private String CARD_NO;
	private String CARD_TYPE;
	private String VALUECARD_TYPE;
	private BigDecimal TRAN_AMT;
	private BigDecimal REMAIN_TRAN_AMT;
	private BigDecimal ORIG_TRAN_AMT;
	private String EXP_DATE;
	private String TRAN_FLAG;
	private String REFER_NO;
	private String AUTH_NO;
	private String AUTH_DATE;
	private BigDecimal AUTH_AMT;
	private String RECV_INST;
	private String ACQ_INST;
	private String AGQ_INST;
	private String MERCH_ID;
	private String MCC;
	private String TERM_ID;
	private String TERM_TYPE;
	private String OPER_ID;
	private String CHANNEL_TYPE;
	private String INPUT_MODE;
	private String IC_55;
	private String ISS_APPDATA;
	private String ATC;
	private String AIP;
	private String CCY_CODE;
	private String RESP_CODE;
	private String TRAN_STATUS;
	private String BATCH_STATUS;
	private String BATCH_RESULT;
	private String SETTLE_FLAG;
	private String TERM_SETTLE_DATE;
	private String RESERVE;
	private String ACTIVTY_ID;
	private String WL_TERM_ID;
	private String WL_BATCH_NO;
	private String WL_POSNO;
	private String ORDERID;
	private Store store;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTRAN_TYPE() {
		return TRAN_TYPE;
	}

	public void setTRAN_TYPE(String tRAN_TYPE) {
		TRAN_TYPE = tRAN_TYPE;
	}

	public String getORIG_TRAN_TYPE() {
		return ORIG_TRAN_TYPE;
	}

	public void setORIG_TRAN_TYPE(String oRIG_TRAN_TYPE) {
		ORIG_TRAN_TYPE = oRIG_TRAN_TYPE;
	}

	public String getSETTLE_DATE() {
		return SETTLE_DATE;
	}

	public void setSETTLE_DATE(String sETTLE_DATE) {
		SETTLE_DATE = sETTLE_DATE;
	}

	public String getTRAN_DATE() {
		return TRAN_DATE;
	}

	public void setTRAN_DATE(String tRAN_DATE) {
		TRAN_DATE = tRAN_DATE;
	}

	public String getTRAN_TIME() {
		return TRAN_TIME;
	}

	public void setTRAN_TIME(String tRAN_TIME) {
		TRAN_TIME = tRAN_TIME;
	}

	public String getTRAN_SERIAL() {
		return TRAN_SERIAL;
	}

	public void setTRAN_SERIAL(String tRAN_SERIAL) {
		TRAN_SERIAL = tRAN_SERIAL;
	}

	public String getTERM_DATE() {
		return TERM_DATE;
	}

	public void setTERM_DATE(String tERM_DATE) {
		TERM_DATE = tERM_DATE;
	}

	public String getTERM_TIME() {
		return TERM_TIME;
	}

	public void setTERM_TIME(String tERM_TIME) {
		TERM_TIME = tERM_TIME;
	}

	public String getTERM_SERIAL() {
		return TERM_SERIAL;
	}

	public void setTERM_SERIAL(String tERM_SERIAL) {
		TERM_SERIAL = tERM_SERIAL;
	}

	public String getHOST_DATE() {
		return HOST_DATE;
	}

	public void setHOST_DATE(String hOST_DATE) {
		HOST_DATE = hOST_DATE;
	}

	public String getHOST_SERIAL() {
		return HOST_SERIAL;
	}

	public void setHOST_SERIAL(String hOST_SERIAL) {
		HOST_SERIAL = hOST_SERIAL;
	}

	public String getORIG_SERIAL() {
		return ORIG_SERIAL;
	}

	public void setORIG_SERIAL(String oRIG_SERIAL) {
		ORIG_SERIAL = oRIG_SERIAL;
	}

	public String getINVOICE_NO() {
		return INVOICE_NO;
	}

	public void setINVOICE_NO(String iNVOICE_NO) {
		INVOICE_NO = iNVOICE_NO;
	}

	public String getCONDITION_MODE() {
		return CONDITION_MODE;
	}

	public void setCONDITION_MODE(String cONDITION_MODE) {
		CONDITION_MODE = cONDITION_MODE;
	}

	public String getORIG_INVOICE_NO() {
		return ORIG_INVOICE_NO;
	}

	public void setORIG_INVOICE_NO(String oRIG_INVOICE_NO) {
		ORIG_INVOICE_NO = oRIG_INVOICE_NO;
	}

	public String getBATCH_NO() {
		return BATCH_NO;
	}

	public void setBATCH_NO(String bATCH_NO) {
		BATCH_NO = bATCH_NO;
	}

	public String getCARD_NO() {
		return CARD_NO;
	}

	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}

	public String getCARD_TYPE() {
		return CARD_TYPE;
	}

	public void setCARD_TYPE(String cARD_TYPE) {
		CARD_TYPE = cARD_TYPE;
	}

	public String getVALUECARD_TYPE() {
		return VALUECARD_TYPE;
	}

	public void setVALUECARD_TYPE(String vALUECARD_TYPE) {
		VALUECARD_TYPE = vALUECARD_TYPE;
	}

	public BigDecimal getTRAN_AMT() {
		return TRAN_AMT;
	}

	public void setTRAN_AMT(BigDecimal tRAN_AMT) {
		TRAN_AMT = tRAN_AMT;
	}

	public BigDecimal getREMAIN_TRAN_AMT() {
		return REMAIN_TRAN_AMT;
	}

	public void setREMAIN_TRAN_AMT(BigDecimal rEMAIN_TRAN_AMT) {
		REMAIN_TRAN_AMT = rEMAIN_TRAN_AMT;
	}

	public BigDecimal getORIG_TRAN_AMT() {
		return ORIG_TRAN_AMT;
	}

	public void setORIG_TRAN_AMT(BigDecimal oRIG_TRAN_AMT) {
		ORIG_TRAN_AMT = oRIG_TRAN_AMT;
	}

	public String getEXP_DATE() {
		return EXP_DATE;
	}

	public void setEXP_DATE(String eXP_DATE) {
		EXP_DATE = eXP_DATE;
	}

	public String getTRAN_FLAG() {
		return TRAN_FLAG;
	}

	public void setTRAN_FLAG(String tRAN_FLAG) {
		TRAN_FLAG = tRAN_FLAG;
	}

	public String getREFER_NO() {
		return REFER_NO;
	}

	public void setREFER_NO(String rEFER_NO) {
		REFER_NO = rEFER_NO;
	}

	public String getAUTH_NO() {
		return AUTH_NO;
	}

	public void setAUTH_NO(String aUTH_NO) {
		AUTH_NO = aUTH_NO;
	}

	public String getAUTH_DATE() {
		return AUTH_DATE;
	}

	public void setAUTH_DATE(String aUTH_DATE) {
		AUTH_DATE = aUTH_DATE;
	}

	public BigDecimal getAUTH_AMT() {
		return AUTH_AMT;
	}

	public void setAUTH_AMT(BigDecimal aUTH_AMT) {
		AUTH_AMT = aUTH_AMT;
	}

	public String getRECV_INST() {
		return RECV_INST;
	}

	public void setRECV_INST(String rECV_INST) {
		RECV_INST = rECV_INST;
	}

	public String getACQ_INST() {
		return ACQ_INST;
	}

	public void setACQ_INST(String aCQ_INST) {
		ACQ_INST = aCQ_INST;
	}

	public String getAGQ_INST() {
		return AGQ_INST;
	}

	public void setAGQ_INST(String aGQ_INST) {
		AGQ_INST = aGQ_INST;
	}

	public String getMERCH_ID() {
		return MERCH_ID;
	}

	public void setMERCH_ID(String mERCH_ID) {
		MERCH_ID = mERCH_ID;
	}

	public String getMCC() {
		return MCC;
	}

	public void setMCC(String mCC) {
		MCC = mCC;
	}

	public String getTERM_ID() {
		return TERM_ID;
	}

	public void setTERM_ID(String tERM_ID) {
		TERM_ID = tERM_ID;
	}

	public String getTERM_TYPE() {
		return TERM_TYPE;
	}

	public void setTERM_TYPE(String tERM_TYPE) {
		TERM_TYPE = tERM_TYPE;
	}

	public String getOPER_ID() {
		return OPER_ID;
	}

	public void setOPER_ID(String oPER_ID) {
		OPER_ID = oPER_ID;
	}

	public String getCHANNEL_TYPE() {
		return CHANNEL_TYPE;
	}

	public void setCHANNEL_TYPE(String cHANNEL_TYPE) {
		CHANNEL_TYPE = cHANNEL_TYPE;
	}

	public String getINPUT_MODE() {
		return INPUT_MODE;
	}

	public void setINPUT_MODE(String iNPUT_MODE) {
		INPUT_MODE = iNPUT_MODE;
	}

	public String getIC_55() {
		return IC_55;
	}

	public void setIC_55(String iC_55) {
		IC_55 = iC_55;
	}

	public String getISS_APPDATA() {
		return ISS_APPDATA;
	}

	public void setISS_APPDATA(String iSS_APPDATA) {
		ISS_APPDATA = iSS_APPDATA;
	}

	public String getATC() {
		return ATC;
	}

	public void setATC(String aTC) {
		ATC = aTC;
	}

	public String getAIP() {
		return AIP;
	}

	public void setAIP(String aIP) {
		AIP = aIP;
	}

	public String getCCY_CODE() {
		return CCY_CODE;
	}

	public void setCCY_CODE(String cCY_CODE) {
		CCY_CODE = cCY_CODE;
	}

	public String getRESP_CODE() {
		return RESP_CODE;
	}

	public void setRESP_CODE(String rESP_CODE) {
		RESP_CODE = rESP_CODE;
	}

	public String getTRAN_STATUS() {
		return TRAN_STATUS;
	}

	public void setTRAN_STATUS(String tRAN_STATUS) {
		TRAN_STATUS = tRAN_STATUS;
	}

	public String getBATCH_STATUS() {
		return BATCH_STATUS;
	}

	public void setBATCH_STATUS(String bATCH_STATUS) {
		BATCH_STATUS = bATCH_STATUS;
	}

	public String getBATCH_RESULT() {
		return BATCH_RESULT;
	}

	public void setBATCH_RESULT(String bATCH_RESULT) {
		BATCH_RESULT = bATCH_RESULT;
	}

	public String getSETTLE_FLAG() {
		return SETTLE_FLAG;
	}

	public void setSETTLE_FLAG(String sETTLE_FLAG) {
		SETTLE_FLAG = sETTLE_FLAG;
	}

	public String getTERM_SETTLE_DATE() {
		return TERM_SETTLE_DATE;
	}

	public void setTERM_SETTLE_DATE(String tERM_SETTLE_DATE) {
		TERM_SETTLE_DATE = tERM_SETTLE_DATE;
	}

	public String getRESERVE() {
		return RESERVE;
	}

	public void setRESERVE(String rESERVE) {
		RESERVE = rESERVE;
	}

	public String getACTIVTY_ID() {
		return ACTIVTY_ID;
	}

	public void setACTIVTY_ID(String aCTIVTY_ID) {
		ACTIVTY_ID = aCTIVTY_ID;
	}

	public String getWL_TERM_ID() {
		return WL_TERM_ID;
	}

	public void setWL_TERM_ID(String wL_TERM_ID) {
		WL_TERM_ID = wL_TERM_ID;
	}

	public String getWL_BATCH_NO() {
		return WL_BATCH_NO;
	}

	public void setWL_BATCH_NO(String wL_BATCH_NO) {
		WL_BATCH_NO = wL_BATCH_NO;
	}

	public String getWL_POSNO() {
		return WL_POSNO;
	}

	public void setWL_POSNO(String wL_POSNO) {
		WL_POSNO = wL_POSNO;
	}

	public String getORDERID() {
		return ORDERID;
	}

	public void setORDERID(String oRDERID) {
		ORDERID = oRDERID;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STOREID")
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

}