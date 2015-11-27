package com.mocredit.manage.controller;

import com.alibaba.fastjson.JSON;
import com.mocredit.security.web.ShiroBaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ytq on 2015/10/28.
 */
@RestController
public class IndexController extends ShiroBaseController {
    @Qualifier("shiroClient")
    @Autowired
    private Properties shiroClient;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @RequestMapping(value = "/loginAction")
    public ModelAndView loginAction(HttpServletRequest request, String username, String password) {
        ModelAndView mav;
        if (username == null || "".equals(username)) {
            mav = new ModelAndView("login");
            mav.addObject("error", "用户名不能为空");
            return mav;
        }
        if (password == null || "".equals(password)) {
            mav = new ModelAndView("login");
            mav.addObject("error", "密码不能为空");
            return mav;
        }
        Map<String, String> param = new HashMap<>();
        param.put("username", username);
        param.put("password", password);
        getMenus(shiroClient.getProperty("client.app.key"), shiroClient.getProperty("client.app.loginUrl"), JSON.toJSONString(param), request);
        if ((boolean) request.getAttribute("isSuccess")) {
            mav = new ModelAndView("index");
            mav.addObject("menus", request.getAttribute("menusList"));
            mav.addObject("username", username);
            request.getSession().setAttribute("authentication", true);
        } else {
            mav = new ModelAndView("login");
            mav.addObject("error", request.getAttribute("error"));
        }
        return mav;
    }

    /**
     * 跳转至活动管理页面
     *
     * @return model and view
     */
//    @RequestMapping("/index")
//    public ModelAndView activity() {
//        //定义页面对象，用来跳转指定页面，下面一句代码的意思是：跳转到Web容器中的index.jsp页面
//        ModelAndView mav = new ModelAndView("index");
////        List<Resource> menus = (List<Resource>) SecurityUtils.getSubject().getSession().getAttribute("menus");
//        mav.addObject("menus", getMenus(shiroClient.getProperty("client.app.key"),shiroClient.getProperty("client.app.loginUrl")));
//        return mav;
//    }
   /* @RequestMapping("/logout")
    public ModelAndView logout() {
        //定义页面对象，用来跳转指定页面，下面一句代码的意思是：跳转到Web容器中的index.jsp页面
        SecurityUtils.getSubject().logout();
        ModelAndView mav = new ModelAndView("redirect:index");
        return mav;
    }*/
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().removeAttribute("authentication");
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }
}
