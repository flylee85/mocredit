package com.mocredit.security.web;

import com.alibaba.fastjson.JSON;
import com.mocredit.security.core.ResponseData;
import com.mocredit.security.entity.Resource;
import com.mocredit.security.utils.HttpUtil;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
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

    public void getMenus(String appKey, String url, String param, HttpServletRequest request) {
        try {
            String resp = HttpUtil.doRestfulByHttpConnection(url, param);
            ResponseData responseData = JSON.parseObject(resp, ResponseData.class);
            if (responseData.getSuccess()) {
                List<Resource> resourceList = JSON.parseArray(responseData.getData() + "", Resource.class);
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
                request.setAttribute("isSuccess", true);
                request.setAttribute("menusList", menusList);
            } else {
                request.setAttribute("isSuccess", false);
                request.setAttribute("error", responseData.getErrorMsg());
                if ((responseData.getData() + "").equals("addCaptcha")) {
                    request.getSession().setAttribute("addCaptcha", 1);
                } else {
                    request.getSession().setAttribute("addCaptcha", 0);
                }
            }
        } catch (Exception e) {
            request.setAttribute("isSuccess", false);
            request.setAttribute("error", "认证异常");
        }
    }
}
