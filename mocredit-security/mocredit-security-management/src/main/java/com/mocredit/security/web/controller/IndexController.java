package com.mocredit.security.web.controller;

import com.alibaba.fastjson.JSON;
import com.mocredit.security.core.ResponseData;
import com.mocredit.security.dao.UserDao;
import com.mocredit.security.entity.Resource;
import com.mocredit.security.entity.User;
import com.mocredit.security.service.AppService;
import com.mocredit.security.service.AuthorizationService;
import com.mocredit.security.service.ResourceService;
import com.mocredit.security.utils.PropertiesUtil;
import com.mocredit.security.web.bind.annotation.CurrentUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

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
    @Autowired
    private CacheManager cacheManager;
    private Cache<String, AtomicInteger> passwordRetryCache;

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
    public String loginInterface(@RequestBody String param, HttpServletRequest request, HttpServletResponse response) {
        User loginUser = JSON.parseObject(param, User.class);
        ResponseData responseData = new ResponseData();
        if (isCredentialsThanCount(loginUser.getUsername(), responseData)) {
            return JSON.toJSONString(responseData);
        }
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
            responseData.setErrorMsg("用户名/密码错误");
            doCredentialsMatch(loginUser.getUsername(), responseData);
        }
        return JSON.toJSONString(responseData);
    }

    public boolean isCredentialsThanCount(String username, ResponseData responseData) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
        AtomicInteger retryCount = passwordRetryCache.get(username);
        boolean flag = false;
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        int count = retryCount.get();
        if (count >= 3) {
            responseData.setData("addCaptcha");
        }
        if (count >= 5) {
            responseData.setSuccess(false);
            responseData.setErrorMsg("登录失败次数过多");
            flag = true;
        }
        return flag;
    }

    public void doCredentialsMatch(String username, ResponseData responseData) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        int count = retryCount.incrementAndGet();
        if (count >= 3) {
            responseData.setData("addCaptcha");
        }
        if (count >= 5) {
            responseData.setSuccess(false);
            responseData.setErrorMsg("登录失败次数过多");
        }
    }
}
