package cn.mocredit.gateway.data.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="mp_temp_key")
public class MpTempKey extends Id implements java.io.Serializable {
	private String MERCH_ID;
	private String MERCH_TYPE;
	private String STORE_ID;
	private String TERM_ID;
	private String OPER_ID;
	private String LMK_TMK;
	private String ZMK_TMK;
	private String TMK_CHECK_VALUE;
	private String LMK_TPK;
	private String LMK_TAK;
	private String TMK_TPK;
	private String TMK_TAK;
	private String BATCH_NO;
	private String FLAG;
	private String UPDATE_TIME;

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

    public String getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID = STORE_ID;
    }

    public String getTERM_ID() {
        return TERM_ID;
    }

    public void setTERM_ID(String TERM_ID) {
        this.TERM_ID = TERM_ID;
    }

    public String getOPER_ID() {
        return OPER_ID;
    }

    public void setOPER_ID(String OPER_ID) {
        this.OPER_ID = OPER_ID;
    }

    public String getLMK_TMK() {
        return LMK_TMK;
    }

    public void setLMK_TMK(String LMK_TMK) {
        this.LMK_TMK = LMK_TMK;
    }

    public String getZMK_TMK() {
        return ZMK_TMK;
    }

    public void setZMK_TMK(String ZMK_TMK) {
        this.ZMK_TMK = ZMK_TMK;
    }

    public String getTMK_CHECK_VALUE() {
        return TMK_CHECK_VALUE;
    }

    public void setTMK_CHECK_VALUE(String TMK_CHECK_VALUE) {
        this.TMK_CHECK_VALUE = TMK_CHECK_VALUE;
    }

    public String getLMK_TPK() {
        return LMK_TPK;
    }

    public void setLMK_TPK(String LMK_TPK) {
        this.LMK_TPK = LMK_TPK;
    }

    public String getLMK_TAK() {
        return LMK_TAK;
    }

    public void setLMK_TAK(String LMK_TAK) {
        this.LMK_TAK = LMK_TAK;
    }

    public String getTMK_TPK() {
        return TMK_TPK;
    }

    public void setTMK_TPK(String TMK_TPK) {
        this.TMK_TPK = TMK_TPK;
    }

    public String getTMK_TAK() {
        return TMK_TAK;
    }

    public void setTMK_TAK(String TMK_TAK) {
        this.TMK_TAK = TMK_TAK;
    }

    public String getBATCH_NO() {
        return BATCH_NO;
    }

    public void setBATCH_NO(String BATCH_NO) {
        this.BATCH_NO = BATCH_NO;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }
}
