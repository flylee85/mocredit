package com.mocredit.security.web.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by ytq on 2016/1/13.
 */
public class CaptchaException extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super();
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }
}
