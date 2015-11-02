package com.mocredit.activitysys.controller;

import com.mocredit.security.entity.Resource;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by ytq on 2015/10/28.
 */
@RestController
public class IndexController {
    /**
     * 跳转至活动管理页面
     *
     * @return model and view
     */
    @RequestMapping("/index")
    public ModelAndView activity() {
        //定义页面对象，用来跳转指定页面，下面一句代码的意思是：跳转到Web容器中的index.jsp页面
        ModelAndView mav = new ModelAndView("index");
        List<Resource> menus = (List<Resource>) SecurityUtils.getSubject().getSession().getAttribute("menus");
        mav.addObject("menus", menus);
        return mav;
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        //定义页面对象，用来跳转指定页面，下面一句代码的意思是：跳转到Web容器中的index.jsp页面
        SecurityUtils.getSubject().logout();
        ModelAndView mav = new ModelAndView("redirect:index");
        return mav;
    }
}