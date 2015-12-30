package com.mocredit.security.web.controller;

import com.alibaba.fastjson.JSON;
import com.mocredit.security.dao.UserDao;
import com.mocredit.security.entity.Resource;
import com.mocredit.security.core.ResponseData;
import com.mocredit.security.entity.User;
import com.mocredit.security.service.AppService;
import com.mocredit.security.service.AuthorizationService;
import com.mocredit.security.service.ResourceService;
import com.mocredit.security.utils.MD5Util;
import com.mocredit.security.utils.PropertiesUtil;
import com.mocredit.security.web.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * <p>User:
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Controller
public class IndexController {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private AppService appService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UserDao userDao;

    @RequestMapping("/")
    public String index(@CurrentUser User loginUser, Model model) {
        Long appId = appService.findAppIdByAppKey(PropertiesUtil.getValue("client.app.key"));
        Set<String> permissions = authorizationService.findPermissions(PropertiesUtil.getValue("client.app.key"), loginUser.getUsername());
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

    @RequestMapping(value = "/loginInterface", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String loginInterface(@RequestBody String param) {
        User loginUser = JSON.parseObject(param, User.class);
        ResponseData responseData = new ResponseData();
        User user = userDao.findByUsername(loginUser.getUsername());
        if (user != null && user.getPassword() != null && user.getMd5Pwd() != null && user.getMd5Pwd().equals(loginUser.getPassword())) {
            Set<String> permissions = authorizationService.findPermissions(user.getUsername());
            TreeMap<Long, Resource> treeMap = new TreeMap<Long, Resource>();
            for (Resource resource : resourceService.findMenus(permissions)) {
                if (resource.getParentId() == 1) {
                    treeMap.put(resource.getId(), resource);
                } else {
                    if (treeMap.containsKey(resource.getParentId())) {
                        treeMap.get(resource.getParentId()).getSecondResourceList().add(resource);
                    } else {
                        treeMap.put(resource.getParentId(), resource);
                    }
                }

            }
            List<Resource> menus = new ArrayList<Resource>();
            for (Long key : treeMap.keySet()) {
                menus.add(treeMap.get(key));
            }
            responseData.setData(menus);
        } else {
            responseData.setSuccess(false);
            responseData.setErrorMsg("认证异常");
        }
        return JSON.toJSONString(responseData);
    }
}
