package com.zhklong.selling.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zhklong.selling.util.ApplicationUtil;

public class CrossOriginInterceptor extends HandlerInterceptorAdapter{
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
//        response.setHeader("Access-Control-Max-Age", "3600");  
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        if(!ApplicationUtil.getIPMap(request.getServletContext()).containsKey(request.getRemoteAddr())){
        	ApplicationUtil.linkIPSession(request.getRemoteAddr(),request.getSession());
        }
		return true;
	}

}
