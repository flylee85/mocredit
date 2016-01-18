package com.mocredit.security.web.controller;

import com.alibaba.fastjson.JSON;
import com.mocredit.security.entity.Captcha;
import com.mocredit.security.entity.Response;
import com.mocredit.security.entity.User;
import com.mocredit.security.service.CaptchaService;
import com.mocredit.security.web.exception.CaptchaException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>User:
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
@Controller
public class LoginController {
    public static final String KEY_CAPTCHA = "SE_KEY_MM_CODE";
    @Autowired
    private CaptchaService captchaService;

    @RequestMapping(value = "checkCode", method = RequestMethod.GET)
    public void checkCodes(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        Captcha captcha = captchaService.generateCaptcha();
        try {
            WebUtils.setSessionAttribute(request, KEY_CAPTCHA, captcha.getCode().toLowerCase());
            response.getOutputStream().write(captcha.getJpegBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/checkCodeVerify", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String checkCodeVerify(
            @RequestParam("checkCode") String checkCode,
            HttpServletRequest request,
            HttpServletResponse response) {
        String checkCodeReq = (String) WebUtils.getSessionAttribute(request, KEY_CAPTCHA);
        Response resp = new Response();
        if (checkCodeReq != null && checkCodeReq.equalsIgnoreCase(checkCode.toLowerCase())) {
        } else {
            resp.setSuccess(false);
            resp.setErrorMsg("验证码错误");
        }
        return JSON.toJSONString(resp);
    }

    @RequestMapping(value = "/login")
    public String showLoginForm(HttpServletRequest req, Model model, User user) {
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        String error = null;
        if ("".equals(user.getUsername()) || "".equals(user.getPassword())) {
            model.addAttribute("error", "用户名/密码不能为空");
            if (SecurityUtils.getSubject().isAuthenticated()) {
                return "redirect:/";
            } else {
                return "login";
            }
        }
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
            error = "该账户已被冻结";
        } else if (CaptchaException.class.getName().equals(exceptionClassName)) {
            error = "验证码错误";
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            error = "登录失败次数过多";
        } else if (exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }

        model.addAttribute("error", error);
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/";
        } else {
            if (SecurityUtils.getSubject().getSession().getAttribute(user.getUsername()) != null) {
                Integer count = (Integer) SecurityUtils.getSubject().getSession().getAttribute(user.getUsername());
                SecurityUtils.getSubject().getSession().setAttribute(user.getUsername(), count + 1);
            } else {
                SecurityUtils.getSubject().getSession().setAttribute(user.getUsername(), 1);
            }
            if ((Integer) SecurityUtils.getSubject().getSession().getAttribute(user.getUsername()) >= 3) {
                model.addAttribute("addCaptcha", 1);
            }
            model.addAttribute("username", user.getUsername());
            return "login";
        }
    }

}
