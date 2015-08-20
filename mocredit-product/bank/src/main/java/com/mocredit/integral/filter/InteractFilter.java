package com.mocredit.integral.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.util.SpringContextUtils;
import com.mocredit.integral.entity.Interact;
import com.mocredit.integral.service.InteractRequestService;
import com.mocredit.integral.util.Utils;
/**
 * 请求过滤器，记录访问日志
 * @author liaoying
 * Created on 2015年8月19日
 *
 */
public class InteractFilter extends OncePerRequestFilter {
	InteractRequestService service;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		if(null==service){
			service=SpringContextUtils.getBean("interactRequestService");
		}
		
		Interact interact=new Interact();
		interact.setIp(getIpAddr(request));
		JSON.toJSON(request.getParameterMap());
		interact.setRequest(JSON.toJSON(request.getParameterMap()).toString());
		String orderId=request.getParameter("orderId");
		interact.setOrderId(null==orderId?"":orderId);
		interact.setReqInterface(request.getRequestURI());
		int request_id=service.save(interact);
		request.setAttribute("request_id", request_id);
		chain.doFilter(request, response);
	}
	
	/** 
     * 获取访问者IP 
     *  
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。 
     *  
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 
     * 如果还不存在则调用Request .getRemoteAddr()。 
     *  
     * @param request 
     * @return 
     */  
	public static String getIpAddr(HttpServletRequest request){  
        String ip = request.getHeader("X-Real-IP");  
        if (!Utils.isNullOrBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {  
            return ip;  
        }  
        ip = request.getHeader("X-Forwarded-For");  
        if (!Utils.isNullOrBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个IP值，第一个为真实IP。  
            int index = ip.indexOf(',');  
            if (index != -1) {  
                return ip.substring(0, index);  
            } else {  
                return ip;  
            }  
        } else {  
            return request.getRemoteAddr();  
        }  
    }

	public InteractRequestService getService() {
		return service;
	}

	public void setService(InteractRequestService service) {
		this.service = service;
	}  
	
}
