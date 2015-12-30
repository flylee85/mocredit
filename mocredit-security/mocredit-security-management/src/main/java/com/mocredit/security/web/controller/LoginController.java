package com.mocredit.security.web.controller;

import com.mocredit.security.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>User:
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
@Controller
public class LoginController {

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
        } else if (exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/";
        } else {
            return "login";
        }
    }
}
