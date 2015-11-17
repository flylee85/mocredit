package com.mocredit.app.web.controller;

import com.mocredit.security.entity.Resource;
import com.mocredit.security.web.ShiroBaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Properties;

/**
 * <p>User:
 * <p>Date: 14-3-13
 * <p>Version: 1.0
 */
@Controller
public class HelloController extends ShiroBaseController {
    @Qualifier("shiroClient")
    @Autowired
    private Properties shiroClient;

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("menus", getMenus(shiroClient.getProperty("client.app.key")));
        return "success";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        SecurityUtils.getSubject().getPrincipals().asList().toString();
        return "test";
    }

    @RequestMapping(value = "/attr", method = RequestMethod.POST)
    public String setAttr(
            @RequestParam("key") String key, @RequestParam("value") String value) {
        SecurityUtils.getSubject().getSession().setAttribute(key, value);
        return "success";
    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "success";
    }

    @RequestMapping(value = "/attr", method = RequestMethod.GET)
    public String getAttr(
            @RequestParam("key") String key, Model model) {
        model.addAttribute("value", SecurityUtils.getSubject().getSession().getAttribute(key));
        return "success";
    }

    @RequestMapping("/role2")
    @RequiresRoles("role2")
    public String role2() {
        return "success";
    }
}
