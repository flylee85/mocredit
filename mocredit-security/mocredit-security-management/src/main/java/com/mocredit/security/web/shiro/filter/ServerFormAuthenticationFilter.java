package com.mocredit.security.web.shiro.filter;

import com.mocredit.security.dao.UserDao;
import com.mocredit.security.entity.App;
import com.mocredit.security.entity.Resource;
import com.mocredit.security.entity.User;
import com.mocredit.security.service.AppService;
import com.mocredit.security.service.AuthorizationService;
import com.mocredit.security.service.ResourceService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * <p>User:
 * <p>Date: 14-3-16
 * <p>Version: 1.0
 */
public class ServerFormAuthenticationFilter extends FormAuthenticationFilter {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private AppService appService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthorizationService authorizationService;

    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        String fallbackUrl = (String) getSubject(request, response)
                .getSession().getAttribute("authc.fallbackUrl");
        String appKey = (String) getSubject(request, response)
                .getSession().getAttribute("appKey");
        getSubject(request, response).getSession().setAttribute("menusList", getMenus(appKey));
        if (StringUtils.isEmpty(fallbackUrl)) {
            fallbackUrl = getSuccessUrl();
        }
        WebUtils.redirectToSavedRequest(request, response, fallbackUrl);
    }

    public List<Resource> getMenus(String appKey) {
        if (SecurityUtils.getSubject() != null && SecurityUtils.getSubject().getPrincipal() != null) {
            User user = userDao.findByUsername(SecurityUtils.getSubject().getPrincipal().toString());
//            Long appId = appService.findAppIdByAppKey(appKey);
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
            return menus;
        }
        return new ArrayList<Resource>();
    }
}
