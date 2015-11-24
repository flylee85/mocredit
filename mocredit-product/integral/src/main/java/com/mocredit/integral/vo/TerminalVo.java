package com.mocredit.integral.vo;

import com.mocredit.integral.entity.Terminal;

/**
 * Created by ytq on 2015/11/23.
 */
public class TerminalVo extends Terminal {
    private String oper;
    private String oldEnCode;

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getOldEnCode() {
        return oldEnCode;
    }

    public void setOldEnCode(String oldEnCode) {
        this.oldEnCode = oldEnCode;
    }
}
