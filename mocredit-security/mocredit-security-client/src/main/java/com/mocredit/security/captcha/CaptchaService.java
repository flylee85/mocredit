package com.mocredit.security.captcha;

import com.mocredit.security.captcha.vo.Captcha;

/**
 * Created by IntelliJ IDEA.
 * Date: 2010-3-23
 * Time: 10:48:50
 */
public interface CaptchaService {
    Captcha generateCaptcha();
}
