package com.mocredit.security.core;

import java.io.Serializable;

/**
 * Created by ytq on 2016/1/13.
 */
public class LoginErrorData implements Serializable {
    private int addCaptcha;
    private String errorMsg;

    public int getAddCaptcha() {
        return addCaptcha;
    }

    public void setAddCaptcha(int addCaptcha) {
        this.addCaptcha = addCaptcha;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
