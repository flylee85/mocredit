package com.mocredit.manage.controller;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.security.captcha.CaptchaService;
import com.mocredit.security.captcha.vo.Captcha;
import com.mocredit.security.core.ResponseData;
import com.mocredit.security.web.ShiroBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ytq on 2015/10/28.
 */
@RestController
public class IndexController extends ShiroBaseController {
    private final static String SESSION_CHECK_CODE = "checkCode";
    @Autowired
    private CaptchaService captchaService;

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

    @RequestMapping(value = "checkCode", method = RequestMethod.GET)
    public void checkCodes(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        Captcha captcha = captchaService.generateCaptcha();
        try {
            WebUtils.setSessionAttribute(request, SESSION_CHECK_CODE, captcha.getCode().toLowerCase());
            response.getOutputStream().write(captcha.getJpegBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/checkCodeVerify", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String checkCodeVerify(
            @RequestParam("checkCode") String checkCode,
            HttpServletRequest request,
            HttpServletResponse response) {
        String checkCodeReq = (String) WebUtils.getSessionAttribute(request, SESSION_CHECK_CODE);
        ResponseData resp = new ResponseData();
        if (checkCodeReq != null && checkCodeReq.equalsIgnoreCase(checkCode.toLowerCase())) {
        } else {
            resp.setSuccess(false);
            resp.setErrorMsg("验证码错误");
        }
        return JSON.toJSONString(resp);
    }

    @RequestMapping(value = "/loginAction")
    public ModelAndView loginAction(HttpServletRequest request, String username, String password, String checkCode) {
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
        String checkCodeReq = (String) WebUtils.getSessionAttribute(request, SESSION_CHECK_CODE);
        if (checkCodeReq != null && checkCodeReq.equalsIgnoreCase(checkCode.toLowerCase())) {
        } else {
            mav = new ModelAndView("login");
            mav.addObject("username", username);
            mav.addObject("error", "验证码错误");
            return mav;
        }
        Map<String, String> param = new HashMap<>();
        param.put("username", username);
        param.put("password", password);
        getMenus(PropertiesUtil.getValue("client.app.key"), PropertiesUtil.getValue("client.app.loginUrl"), JSON.toJSONString(param), request);
        if ((boolean) request.getAttribute("isSuccess")) {
            mav = new ModelAndView("index");
            mav.addObject("menus", request.getAttribute("menusList"));
            mav.addObject("username", username);
            request.getSession().setAttribute("authentication", true);
            request.getSession().setAttribute("username", username);
        } else {
            mav = new ModelAndView("login");
            mav.addObject("username", username);
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
//        mav.addObject("menus", getMenus(PropertiesUtil.getValue("client.app.key"), PropertiesUtil.getValue("client.app.loginUrl")));
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

    /**
     * 返回操作信息
     *
     * @param response
     * @param msg
     */
    protected void returnMsg(HttpServletResponse response, Object msg) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
