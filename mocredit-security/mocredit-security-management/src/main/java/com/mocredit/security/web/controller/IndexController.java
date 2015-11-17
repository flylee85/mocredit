package com.mocredit.security.web.controller;

import com.mocredit.security.Constants;
import com.mocredit.security.entity.App;
import com.mocredit.security.entity.Resource;
import com.mocredit.security.entity.User;
import com.mocredit.security.service.AppService;
import com.mocredit.security.service.AuthorizationService;
import com.mocredit.security.service.ResourceService;
import com.mocredit.security.web.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * <p>User:
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Controller
public class IndexController {
    @Qualifier("shiroClient")
    @Autowired
    private Properties shiroClient;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private AppService appService;
    @Autowired
    private AuthorizationService authorizationService;

    @RequestMapping("/")
    public String index(@CurrentUser User loginUser, Model model) {
        Long appId = appService.findAppIdByAppKey(shiroClient.getProperty("client.app.key"));
        Set<String> permissions = authorizationService.findPermissions(shiroClient.getProperty("client.app.key"), loginUser.getUsername());
        List<Resource> menus = resourceService.findMenus(appId, permissions);
        model.addAttribute("menus", menus);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/unauthorized")
    public String menus() {
        return "unauthorized";
    }

}
