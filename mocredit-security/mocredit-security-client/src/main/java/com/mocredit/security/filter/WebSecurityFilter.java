package com.mocredit.security.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ϵͳ��ԴȨ�޹�����
 *
 * @author: zhaiguangtao
 * @data: 2013-7-1
 */
public class WebSecurityFilter extends SecurityFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getServletPath();
        if (!path.matches(super.urls)) {
            Object so = req.getSession().getAttribute("authentication");
            if (so == null || so.equals("")) {
                super.forword(req, res, req.getContextPath() + "/login");
                return;
            } else {
            }
        }
        chain.doFilter(request, response);
    }

}
