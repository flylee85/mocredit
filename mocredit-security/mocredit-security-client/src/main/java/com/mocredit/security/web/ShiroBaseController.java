package com.mocredit.security.web;

import com.mocredit.security.entity.Resource;
import org.apache.shiro.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytq on 2015/11/2.
 */
public class ShiroBaseController {
    public List<Resource> getMenus(String appKey) {
        List<Resource> resourceList = (List<Resource>) SecurityUtils.getSubject().getSession().getAttribute("menusList");
        List<Resource> menusList = new ArrayList<Resource>();
        if (resourceList != null) {
            for (int i = 0; i < resourceList.size(); i++) {
                Resource resource = resourceList.get(i);
                if (resource.getAppKey() != null && !resource.getAppKey().equals(appKey)) {
                } else {
                    menusList.add(resource);
                }
            }
        }
        return menusList;
    }
}
