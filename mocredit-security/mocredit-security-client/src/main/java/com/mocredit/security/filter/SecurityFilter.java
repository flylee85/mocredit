package com.mocredit.security.filter;

import com.mocredit.security.utils.PublicUtils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * ��ȫ����������
 *
 * @author: zhaiguangtao
 * @data: 2013-7-1
 */
public class SecurityFilter implements Filter {
    public static String urls; // ���������� ���ö����|����

    /**
     * ������ʼ��
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        urls = filterConfig.getInitParameter("urls");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
    }

    /**
     * ҳ����ת ����Web�� ���� ʹ��iframeǶ��, ���ֱ���ض��򵽵�¼ҳ�沢����������ɵغ�����, ����HTTP�������� iframe�����ʱ��,
     * ֻ����iframe���ص�index.html, ���鲻����; ���������ｫֱ���ض����Ϊ��
     * ҳ�����һ��JS������ʵ��ʹ����window��ת��Ĭ�ϵĵ�¼ҳ��.
     * httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.html");
     */
    public void forword(HttpServletRequest req, HttpServletResponse res,
                        String url) throws IOException {
        /**
         * ��HTTP��������AJAX�����û���Session�Ѿ���ʱʱ, ��ʱҳ���û���κη�Ӧ, ��Ϊ��AJAX����
         * �ض���������һ��JSʵ����ת�����޷���ɵ�. �������ʵ�ֵ������������ʱ, ��AJAX���� ���ض��ı�ʶ(��ӵ���Ӧ������),
         * �����ڶ���AJAX������ɻص�����ʱ�õ������ʶ, ������ʾ �û������JS��ת.
         */
        /*String requestType = req.getHeader("X-Requested-With");
        if (!PublicUtils.isEmpty(requestType)
                && requestType.equalsIgnoreCase("XMLHttpRequest")) {
            res.setHeader("sessionstatus", "timeout");
            res.sendError(518, "session timeout.");
            return;
        } else {
            PrintWriter out = res.getWriter();
            out.print("<!DOCTYPE html>");
            out.print("<html>");
            out.print("<script> ");
            out.print("var p=window;while(p!=p.parent){p=p.parent; } p.location.href='"
                    + url + "';");
            out.print("</script>");
            out.print("</html>");
        }*/
        PrintWriter out = res.getWriter();
        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("<script> ");
        out.print("var p=window;while(p!=p.parent){p=p.parent; } p.location.href='"
                + url + "';");
        out.print("</script>");
        out.print("</html>");
    }

    /**
     * ϵͳ�ر�ʱ����Դ�ͷŲ���
     */
    public void destroy() {

    }
}
