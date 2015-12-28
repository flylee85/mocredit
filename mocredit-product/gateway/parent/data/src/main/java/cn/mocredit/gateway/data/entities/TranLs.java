package cn.mocredit.gateway.data.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tran_ls")
public class TranLs extends Id implements java.io.Serializable {
    private String TRAN_DATE;
    private String TRAN_TIME;
    private String TRAN_SERIAL;
    private String CARD_NO;
    private Double TRAN_AMT;
    private String EXP_DATE;
    private String TRAN_FLAG;
    private String MERCH_ID;
    private String MERCH_TYPE;
    private String TERM_ID;
    private String TERM_TYPE;
    private String CHANNEL_TYPE;
    private String OPER_ID;
    private String CCY_CODE;
    private String RESP_CODE;
    private String TRAN_STATUS;
    private String ACTIVITY_ID;
    private String MESS_INFO;

    public String getTRAN_DATE() {
        return TRAN_DATE;
    }

    public void setTRAN_DATE(String TRAN_DATE) {
        this.TRAN_DATE = TRAN_DATE;
    }

    public String getTRAN_TIME() {
        return TRAN_TIME;
    }

    public void setTRAN_TIME(String TRAN_TIME) {
        this.TRAN_TIME = TRAN_TIME;
    }

    public String getTRAN_SERIAL() {
        return TRAN_SERIAL;
    }

    public void setTRAN_SERIAL(String TRAN_SERIAL) {
        this.TRAN_SERIAL = TRAN_SERIAL;
    }

    public String getCARD_NO() {
        return CARD_NO;
    }

    public void setCARD_NO(String CARD_NO) {
        this.CARD_NO = CARD_NO;
    }

    public Double getTRAN_AMT() {
        return TRAN_AMT;
    }

    public void setTRAN_AMT(Double TRAN_AMT) {
        this.TRAN_AMT = TRAN_AMT;
    }

    public String getEXP_DATE() {
        return EXP_DATE;
    }

    public void setEXP_DATE(String EXP_DATE) {
        this.EXP_DATE = EXP_DATE;
    }

    public String getTRAN_FLAG() {
        return TRAN_FLAG;
    }

    public void setTRAN_FLAG(String TRAN_FLAG) {
        this.TRAN_FLAG = TRAN_FLAG;
    }

    public String getMERCH_ID() {
        return MERCH_ID;
    }

    public void setMERCH_ID(String MERCH_ID) {
        this.MERCH_ID = MERCH_ID;
    }

    public String getMERCH_TYPE() {
        return MERCH_TYPE;
    }

    public void setMERCH_TYPE(String MERCH_TYPE) {
        this.MERCH_TYPE = MERCH_TYPE;
    }

    public String getTERM_ID() {
        return TERM_ID;
    }

    public void setTERM_ID(String TERM_ID) {
        this.TERM_ID = TERM_ID;
    }

    public String getTERM_TYPE() {
        return TERM_TYPE;
    }

    public void setTERM_TYPE(String TERM_TYPE) {
        this.TERM_TYPE = TERM_TYPE;
    }

    public String getCHANNEL_TYPE() {
        return CHANNEL_TYPE;
    }

    public void setCHANNEL_TYPE(String CHANNEL_TYPE) {
        this.CHANNEL_TYPE = CHANNEL_TYPE;
    }

    public String getOPER_ID() {
        return OPER_ID;
    }

    public void setOPER_ID(String OPER_ID) {
        this.OPER_ID = OPER_ID;
    }

    public String getCCY_CODE() {
        return CCY_CODE;
    }

    public void setCCY_CODE(String CCY_CODE) {
        this.CCY_CODE = CCY_CODE;
    }

    public String getRESP_CODE() {
        return RESP_CODE;
    }

    public void setRESP_CODE(String RESP_CODE) {
        this.RESP_CODE = RESP_CODE;
    }

    public String getTRAN_STATUS() {
        return TRAN_STATUS;
    }

    public void setTRAN_STATUS(String TRAN_STATUS) {
        this.TRAN_STATUS = TRAN_STATUS;
    }

    public String getACTIVITY_ID() {
        return ACTIVITY_ID;
    }

    public void setACTIVITY_ID(String ACTIVITY_ID) {
        this.ACTIVITY_ID = ACTIVITY_ID;
    }

    public String getMESS_INFO() {
        return MESS_INFO;
    }

    public void setMESS_INFO(String MESS_INFO) {
        this.MESS_INFO = MESS_INFO;
    }
}
