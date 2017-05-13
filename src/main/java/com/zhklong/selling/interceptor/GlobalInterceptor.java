package com.zhklong.selling.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 控制跨域，缓存session等
 * @author paul
 * @since 2016-12-03
 * 
 * */
public class GlobalInterceptor implements HandlerInterceptor {
	
//	private static final Logger logger = Logger.getLogger(GlobalInterceptor.class.getName());

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 允许跨域
		((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "null");
		// response.setHeader("Access-Control-Allow-Methods",
		// "POST, GET, OPTIONS, DELETE");
		// response.setHeader("Access-Control-Max-Age", "3600");
		// response.setHeader("Access-Control-Allow-Headers",
		// "x-requested-with");
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");

		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {		
	}

}
